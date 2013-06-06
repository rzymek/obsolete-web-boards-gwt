package webboards.client.games.scs.ops;

import webboards.client.data.GameCtx;
import webboards.client.data.Overlay;

@SuppressWarnings("all")
public abstract class LabeledOverlay extends Overlay {
  public LabeledOverlay(final String id) {
    super(id);
  }
  
  public abstract String label(final GameCtx ctx);
  
  public void update(final GameCtx ctx) {
    String _label = this.label(ctx);
    ctx.display.updateText(this.id, _label);
  }
}
