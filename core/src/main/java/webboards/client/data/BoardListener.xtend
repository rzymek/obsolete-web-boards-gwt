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

@Immutable
class OverlayChangeEvent {
	Board board
	Overlay overlay
}

interface CounterListener {
	def void counterChanged(CounterChangeEvent e)
}

interface PositionListener {
	def void positionChanged(PositionChangeEvent e)
}

interface OverlayListener {
	def void overlayChanged(OverlayChangeEvent e)
	def void overlayRemoved(OverlayChangeEvent e)
	def void overlayCreated(OverlayChangeEvent e)
}

abstract class BoardListener implements CounterListener, PositionListener, OverlayListener {
	
	override counterChanged(CounterChangeEvent e) {
	}
	
	override positionChanged(PositionChangeEvent e) {
	}
	
	override overlayChanged(OverlayChangeEvent e) {
	}
	
}