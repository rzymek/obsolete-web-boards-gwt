package webboards.client.data;

import webboards.client.data.Board;
import webboards.client.data.CounterChangeEvent;
import webboards.client.data.CounterInfo;

@SuppressWarnings("all")
public final class CounterChangeEventBuilder {
  public CounterChangeEvent build() {
    return new CounterChangeEvent(board,counter);
    
  }
  
  public CounterChangeEventBuilder board(final Board board) {
    this.board = board;
    return this;
    
  }
  
  private Board board;
  
  public CounterChangeEventBuilder counter(final CounterInfo counter) {
    this.counter = counter;
    return this;
    
  }
  
  private CounterInfo counter;
}
