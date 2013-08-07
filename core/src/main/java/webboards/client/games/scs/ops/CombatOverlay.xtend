package webboards.client.games.scs.ops

import java.util.Arrays
import java.util.Set
import webboards.client.data.GameCtx
import webboards.client.data.OverlayChangeEvent
import webboards.client.games.Hex
import webboards.client.games.scs.SCSBoard
import webboards.client.games.scs.SCSColor
import webboards.client.games.scs.SCSHex

class CombatOverlay extends LabeledOverlay {
	val Set<Hex> from = newHashSet
	val Hex target

	new(GameCtx ctx, Hex target) {
		super('combatAt' + target)
		this.target = target		
		ctx.board.addPositionListener[
			val hex = it.position as Hex
			from.filter[it == hex].forEach[toggle(ctx, hex)]
		]
	}
	
	def toggle(GameCtx ctx, Hex hex) {
		if (from.contains(hex)) {
			from.remove(hex)
			ctx.display.clearArrow(hex.SVGId)
			if (from.empty) {
				ctx.board.removeOverlayAt(this, target)
				return
			}
		} else {
			from.add(hex);
			ctx.display.drawArrow(hex, target, hex.SVGId, SCSColor::DELCARE.color)
		}
		ctx.board.fireOverlayChanged(new OverlayChangeEvent(ctx.board, this))
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

	override create(GameCtx ctx) {
		var center = ctx.display.getCenter(target)
		var String text = label(ctx)
		ctx.display.drawFromTemplate(center, templateId, text, id)
	}
	
	override delete(GameCtx ctx) {
		super.delete(ctx)
		from.forEach[
			ctx.display.clearArrow(it.SVGId)
		]
	}
}
