package webboards.client.data;

import webboards.client.data.Board;
import webboards.client.data.PositionChangeEvent;
import webboards.client.games.Position;

@SuppressWarnings("all")
public final class PositionChangeEventBuilder {
  public PositionChangeEvent build() {
    return new PositionChangeEvent(board,position);
    
  }
  
  public PositionChangeEventBuilder board(final Board board) {
    this.board = board;
    return this;
    
  }
  
  private Board board;
  
  public PositionChangeEventBuilder position(final Position position) {
    this.position = position;
    return this;
    
  }
  
  private Position position;
}
