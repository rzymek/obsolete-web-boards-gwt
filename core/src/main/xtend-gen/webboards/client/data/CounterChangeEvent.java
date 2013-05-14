package webboards.client.data;

import de.oehme.xtend.annotation.data.Immutable;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import webboards.client.data.Board;
import webboards.client.data.CounterChangeEventBuilder;
import webboards.client.data.CounterInfo;

@Immutable
@SuppressWarnings("all")
public final class CounterChangeEvent {
  public static CounterChangeEvent build(final Procedure1<CounterChangeEventBuilder> init) {
    webboards.client.data.CounterChangeEventBuilder builder = builder();
    init.apply(builder);
    return builder.build();
    
  }
  
  public static CounterChangeEventBuilder builder() {
    return new webboards.client.data.CounterChangeEventBuilder();
    
  }
  
  public CounterChangeEvent(final Board board, final CounterInfo counter) {
    this.board = board;
    this.counter = counter;
    
  }
  
  public Board getBoard() {
    return board;
    
  }
  
  private Board board;
  
  public CounterInfo getCounter() {
    return counter;
    
  }
  
  private CounterInfo counter;
  
  public boolean equals(final Object o) {
    if (o instanceof CounterChangeEvent) {
    	CounterChangeEvent other = (CounterChangeEvent) o;
    	return com.google.common.base.Objects.equal(board, other.board)
    	&& com.google.common.base.Objects.equal(counter, other.counter);
    }
    return false;
    
  }
  
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(board,counter);
  }
  
  public String toString() {
    return "CounterChangeEvent{"+board+", "+counter+"}";
  }
}
