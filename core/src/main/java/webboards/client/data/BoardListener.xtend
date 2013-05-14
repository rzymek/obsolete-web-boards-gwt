package webboards.client.data

import webboards.client.games.Position
import de.oehme.xtend.annotation.data.Immutable

@Immutable
class PositionChangeEvent {
	Board board
	Position position
}

@Immutable  
class CounterChangeEvent {
	Board board
	CounterInfo counter
}

interface BoardListener {
	def void counterChanged(CounterChangeEvent e)
	def void positionChanged(PositionChangeEvent e)
}