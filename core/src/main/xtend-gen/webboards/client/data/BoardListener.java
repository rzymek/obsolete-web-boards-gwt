package webboards.client.data;

import webboards.client.data.CounterChangeEvent;
import webboards.client.data.CounterListener;
import webboards.client.data.OverlayChangeEvent;
import webboards.client.data.OverlayListener;
import webboards.client.data.PositionChangeEvent;
import webboards.client.data.PositionListener;

@SuppressWarnings("all")
public abstract class BoardListener implements CounterListener, PositionListener, OverlayListener {
  public void counterChanged(final CounterChangeEvent e) {
  }
  
  public void positionChanged(final PositionChangeEvent e) {
  }
  
  public void overlayChanged(final OverlayChangeEvent e) {
  }
}
