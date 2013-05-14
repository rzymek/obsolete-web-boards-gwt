package webboards.client.data;

import de.oehme.xtend.annotation.data.Immutable;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import webboards.client.data.Board;
import webboards.client.data.PositionChangeEventBuilder;
import webboards.client.games.Position;

@Immutable
@SuppressWarnings("all")
public final class PositionChangeEvent {
  public static PositionChangeEvent build(final Procedure1<PositionChangeEventBuilder> init) {
    webboards.client.data.PositionChangeEventBuilder builder = builder();
    init.apply(builder);
    return builder.build();
    
  }
  
  public static PositionChangeEventBuilder builder() {
    return new webboards.client.data.PositionChangeEventBuilder();
    
  }
  
  public PositionChangeEvent(final Board board, final Position position) {
    this.board = board;
    this.position = position;
    
  }
  
  public Board getBoard() {
    return board;
    
  }
  
  private Board board;
  
  public Position getPosition() {
    return position;
    
  }
  
  private Position position;
  
  public boolean equals(final Object o) {
    if (o instanceof PositionChangeEvent) {
    	PositionChangeEvent other = (PositionChangeEvent) o;
    	return com.google.common.base.Objects.equal(board, other.board)
    	&& com.google.common.base.Objects.equal(position, other.position);
    }
    return false;
    
  }
  
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(board,position);
  }
  
  public String toString() {
    return "PositionChangeEvent{"+board+", "+position+"}";
  }
}
