package webboards.client.overlay;

import java.io.Serializable;
import java.util.List;

import webboards.client.data.GameCtx;
import webboards.client.ops.Operation;

public class Overlay implements Serializable {
	private static final long serialVersionUID = 1L;

	public interface Handler {
		Operation onClick(GameCtx ctx);
	}

	public Handler onClick;

	public List<Attachable> attachedTo;

	public void onAttachedToChange() {

	}
}
