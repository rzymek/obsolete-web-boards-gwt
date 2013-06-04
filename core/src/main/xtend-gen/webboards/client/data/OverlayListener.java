package webboards.client.data;

import webboards.client.data.OverlayChangeEvent;

@SuppressWarnings("all")
public interface OverlayListener {
  public abstract void overlayChanged(final OverlayChangeEvent e);
  
  public abstract void overlayRemoved(final OverlayChangeEvent e);
  
  public abstract void overlayCreated(final OverlayChangeEvent e);
}
