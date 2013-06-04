package webboards.client.data

import webboards.client.data.ref.CounterId
import webboards.client.games.Position

abstract class Overlay {
	public val String id;
	new(String id){
		this.id=id
	}
	def String getTemplateId()	
}

interface PositionOverlay {
	def Position getPosition()
}

interface CounterOverlay {
	def CounterId getCounterId()
}
