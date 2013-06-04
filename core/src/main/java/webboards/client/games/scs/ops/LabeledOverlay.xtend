package webboards.client.games.scs.ops

import webboards.client.data.GameCtx

interface LabeledOverlay {
	def String label(GameCtx ctx)
}