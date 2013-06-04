package webboards.client.games.scs.ops

import webboards.client.data.GameCtx
import webboards.client.games.Hex
import webboards.client.games.scs.SCSBoard
import webboards.client.games.scs.SCSColor
import webboards.client.games.scs.SCSHex
import webboards.client.ops.Operation

class DeclareAttack extends Operation {
	static val serialVersionUID = 1L
	var Hex from
	var Hex target

	@SuppressWarnings('unused') private new() {
	}

	new(Hex from, Hex target) {
		this.from = from
		this.target = target
	}

	override draw(GameCtx ctx) {
		var board = (ctx.board as SCSBoard)
		var attacking = board.getAttacking(target)
		if (attacking.contains(from)) {
			board.undeclareAttack(from)
			ctx.display.clearArrow('combat_' + from.SVGId)
			updateOdsDisplay(ctx, target, board)
		} else {
			var prevTarget = board.getAttacks(from)
			board.declareAttack(from, target)
			if (prevTarget !== null) {
				updateOdsDisplay(ctx, prevTarget, board)
			}
			ctx.display.drawArrow(from, target, 'combat_' + from.SVGId, SCSColor::DELCARE.color)
			updateOdsDisplay(ctx, target, board)
		}
	}

	private def updateOdsDisplay(GameCtx ctx, Hex target, SCSBoard board) {
		var targetHex = (ctx.board.getInfo(target) as SCSHex)
		var attacking = board.getAttackingInfo(target)
		if (attacking.empty) {
			ctx.display.clearOds(target.SVGId)
		} else {
			var odds = SCSBoard::calculateOdds(targetHex, attacking, target)
			var text = odds.join(':')
			ctx.display.drawOds(ctx.display.getCenter(target), text, target.SVGId)
		}
	}

	override toString() {
		'Declare attack ' + from + ' -> ' + target
	}
}
