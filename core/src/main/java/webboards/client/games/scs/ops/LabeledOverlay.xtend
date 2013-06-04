package webboards.client.games.scs.ops

import webboards.client.data.GameCtx
import webboards.client.data.Overlay

abstract class LabeledOverlay extends Overlay {
	new(String id) {
		super(id)
	}
	def String label(GameCtx ctx)		
}