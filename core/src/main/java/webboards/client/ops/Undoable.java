package webboards.client.ops;

import webboards.client.data.GameCtx;

public interface Undoable {
	void undoUpdate(GameCtx board);
	void undoDraw(GameCtx ctx);
}
