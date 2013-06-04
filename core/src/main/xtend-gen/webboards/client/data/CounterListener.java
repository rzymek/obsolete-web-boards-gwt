package webboards.client.data;

import webboards.client.data.CounterChangeEvent;

@SuppressWarnings("all")
public interface CounterListener {
  public abstract void counterChanged(final CounterChangeEvent e);
}
