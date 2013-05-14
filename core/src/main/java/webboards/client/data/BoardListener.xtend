package webboards.client.data

import webboards.client.games.Position

class PositionChangeEvent {
	Board board
	Position position
	new(Board b, Position p){}
}

class CounterChangeEvent {
	Board board
	CounterInfo counter
	new(Board b, CounterInfo p){}
}

interface BoardListener {
	def void counterChanged(CounterChangeEvent e)
	def void positionChanged(PositionChangeEvent e)
}