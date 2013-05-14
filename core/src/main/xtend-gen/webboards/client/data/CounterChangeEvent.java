package webboards.client.data;

import webboards.client.data.Board;
import webboards.client.data.CounterInfo;

@SuppressWarnings("all")
public class CounterChangeEvent {
  private Board board;
  
  private CounterInfo counter;
  
  public CounterChangeEvent(final Board b, final CounterInfo p) {
  }
}
