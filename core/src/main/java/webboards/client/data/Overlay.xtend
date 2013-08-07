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

	def void create(GameCtx ctx) {
	}

	def void update(GameCtx ctx) {
	}

	def void delete(GameCtx ctx) {
		ctx.display.removeElement(id);
	}
}

//-------------------------------------------------------------
interface PositionOverlay {
	def Position getPosition()
}

//-------------------------------------------------------------
interface CounterOverlay {
	def CounterId getCounterId()
}
