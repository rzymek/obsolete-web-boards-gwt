package webboards.tools.svg

import java.io.File
import java.io.StringWriter
import java.util.Map
import javax.xml.namespace.NamespaceContext
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.apache.commons.io.FileUtils
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

import static extension webboards.tools.svg.Main.*

class SvgCleaner {
	val XPath xpath
	val Document doc;

	new(Document doc, NamespaceContext nsCtx) {
		this.doc = doc;
		val factory = XPathFactory::newInstance
		xpath = factory.newXPath
		xpath.namespaceContext = nsCtx;
	}

	def go(String expr, (Node)=>void processor) {
		val xp = xpath.compile(expr)
		val results = xp.evaluate(doc, XPathConstants::NODESET) as NodeList;
		if (results.length == 0)
			throw new RuntimeException('''No elements matching «expr»''');
		for (i : 0 ..< results.length)
			processor.apply(results.item(i));
	}
}

class NamespaceContextHandler extends DefaultHandler implements NamespaceContext {
	private val Map<String, String> ns = newHashMap()

	override startPrefixMapping(String prefix, String uri) throws SAXException {
		ns.put(prefix, uri);
		println(prefix + ":" + uri);
	}

	override getNamespaceURI(String prefix) {
		return ns.get(prefix)
	}

	override getPrefix(String namespaceURI) {
		ns.entrySet.findFirst[value == namespaceURI]?.key
	}

	override getPrefixes(String namespaceURI) {
		ns.keySet.iterator
	}

}

class Main {
	static val BASEDIR = '../engine/'
	static val SRCDIR = BASEDIR + 'src/main/svg/'

	static def createNamespaceContext(File in) {

		//		val factory = SAXParserFactory::newInstance
		//		factory.namespaceAware = true
		//		val sax = factory.newSAXParser
		val nsHandler = new NamespaceContextHandler()
		nsHandler.startPrefixMapping('', 'http://www.w3.org/2000/svg')
		nsHandler.startPrefixMapping('svg', 'http://www.w3.org/2000/svg')
		nsHandler.startPrefixMapping('xlink', 'http://www.w3.org/1999/xlink')
		nsHandler.startPrefixMapping('inkscape', 'http://www.inkscape.org/namespaces/inkscape')

		//		sax.parse(in, nsHandler);
		nsHandler
	}

	static def readDOM(File in) {
		val factory = DocumentBuilderFactory::newInstance
		factory.namespaceAware = true
		val builder = factory.newDocumentBuilder
		builder.parse(in)
	}

	static def void main(String[] args) throws Exception {
		val in = new File(SRCDIR + 'bastogne-orig.svg')
		val doc = readDOM(in);
		val svg = new SvgCleaner(doc, createNamespaceContext(in))

		svg.go("//:g[@inkscape:label]",
			[ node |
				val e = node as Element
				val label = e.attributes.getNamedItemNS("http://www.inkscape.org/namespaces/inkscape", "label").
					nodeValue
				e.attributes.getNamedItem("id").nodeValue = label
			])
		svg.go("//*[namespace-uri()!='http://www.w3.org/2000/svg']", [remove()])
		svg.go(
			"//@*[namespace-uri() and namespace-uri()!='http://www.w3.org/2000/svg' and namespace-uri()!='http://www.w3.org/1999/xlink']",
			[removeAttr()])
		svg.go("/:svg/:metadata", [remove()])

		val xml = doc.toXML();
		FileUtils::write(new File("bastogne-out.xml"), xml);
	}

	static def toXML(Document document) {
		val transFactory = TransformerFactory::newInstance();
		val transformer = transFactory.newTransformer();
		val buffer = new StringWriter();
		transformer.transform(new DOMSource(document), new StreamResult(buffer));
		return buffer.toString;
	}

	static def removeAttr(Node node) {
		val attr = node as Attr
		attr.ownerElement.removeAttributeNS(attr.namespaceURI, attr.localName);

	}

	static def remove(Node node) {
		val parent = node.parentNode
		parent.removeChild(node)
	}

}
