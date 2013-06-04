package webboards.client.games.scs.ops

import java.util.Arrays
import java.util.Set
import webboards.client.data.Board
import webboards.client.data.GameCtx
import webboards.client.data.OverlayChangeEvent
import webboards.client.games.Hex
import webboards.client.games.scs.SCSBoard
import webboards.client.games.scs.SCSHex
import webboards.client.data.PositionOverlay

class CombatOverlay extends LabeledOverlay implements PositionOverlay {
	val Set<Hex> from = newHashSet
	val Hex target

	new(Hex target) {
		super('combatAt'+target)
		this.target = target
	}

	def toggle(Board board, Hex hex) {
		if(from.contains(hex)) {
			from.remove(hex)
			if (from.empty) {				
				board.removeOverlayAt(this, hex)
				return				
			}		
		}else{
			from.add(hex);		
		}
		board.fireOverlayChanged(new OverlayChangeEvent(board, this))
	}
	
	override label(GameCtx ctx) {
		var targetHex = (ctx.board.getInfo(target) as SCSHex)
		val board = ctx.board as SCSBoard
		val attacking = from.map[board.getInfo(it)]
		var odds = SCSBoard::calculateOdds(targetHex, attacking, target)
		Arrays::asList(odds).join(':')
	}
	
	override getTemplateId() {
		return "target"
	}
	
	override getPosition() {
		target
	}
	
}
