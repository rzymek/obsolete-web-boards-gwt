package webboards.client.ops;

import webboards.client.overlay.Overlay;

public class RemoveOverlay extends Operation {

	private static final long serialVersionUID = 1L;
	private Overlay overlay;

	public RemoveOverlay(Overlay overlay) {
		this.overlay = overlay;
	}

	@Override
	public String toString() {
		return "Remove overlay";
	}

}
