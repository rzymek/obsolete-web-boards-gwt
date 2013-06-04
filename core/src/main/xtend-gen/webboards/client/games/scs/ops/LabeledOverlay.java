package webboards.client.games.scs.ops;

import webboards.client.data.GameCtx;

@SuppressWarnings("all")
public interface LabeledOverlay {
  public abstract String label(final GameCtx ctx);
}
