package webboards.server;

import webboards.client.data.Board;
import webboards.client.data.GameCtx;

public class ServerGameCtx extends GameCtx {

	public ServerGameCtx(Board board) {
		this.board = board;
		this.display = new NoopDisplay();
	}

}
