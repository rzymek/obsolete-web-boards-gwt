package webboards.client.games.scs.ops

import java.util.Set
import webboards.client.data.Board
import webboards.client.data.Overlay
import webboards.client.games.Hex

class CombatOverlay extends Overlay {
	val Set<Hex> from = newHashSet
	val Hex target

	new(Hex target) {
		this.target = target
	}

	def toggle(Board board, Hex hex) {
		from.remove(hex)
		if (from.empty) {
			board.removeOverlayAt(this, hex)
		}
		from.add(hex);
	}

}
