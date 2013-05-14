package webboards.client.data;

import webboards.client.data.CounterChangeEvent;
import webboards.client.data.PositionChangeEvent;

@SuppressWarnings("all")
public interface BoardListener {
  public abstract void counterChanged(final CounterChangeEvent e);
  
  public abstract void positionChanged(final PositionChangeEvent e);
}
