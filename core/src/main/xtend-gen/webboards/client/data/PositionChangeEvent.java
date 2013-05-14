package webboards.client.data;

import webboards.client.data.Board;
import webboards.client.games.Position;

@SuppressWarnings("all")
public class PositionChangeEvent {
  private Board board;
  
  private Position position;
  
  public PositionChangeEvent(final Board b, final Position p) {
  }
}
