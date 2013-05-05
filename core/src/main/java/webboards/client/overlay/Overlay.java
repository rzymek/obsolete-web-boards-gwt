package webboards.client.overlay;

import java.io.Serializable;

import webboards.client.data.Board;
import webboards.client.data.GameCtx;
import webboards.client.games.Position;
import webboards.client.ops.Operation;

public class Overlay implements Serializable {
	private static final long serialVersionUID = 1L;

	public interface Handler {
		Operation onClick(GameCtx ctx);
	}

	public Handler onClick;

}
