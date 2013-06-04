package webboards.client.data

import webboards.client.data.ref.CounterId
import webboards.client.games.Position
import com.google.common.base.Objects
import java.io.Serializable

abstract class Overlay implements Serializable {
	public val String id;

	new(String id) {
		this.id = id
	}

	def String getTemplateId()

	override hashCode() {
		Objects::hashCode(id)
	}
	
	override equals(Object obj) {
		if (obj instanceof Overlay) {
			id == (obj as Overlay).id
		} else {
			false;
		}
	}

}

interface PositionOverlay {
	def Position getPosition()
}

interface CounterOverlay {
	def CounterId getCounterId()
}
