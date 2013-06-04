package webboards.client.data;

import webboards.client.data.PositionChangeEvent;

@SuppressWarnings("all")
public interface PositionListener {
  public abstract void positionChanged(final PositionChangeEvent e);
}
