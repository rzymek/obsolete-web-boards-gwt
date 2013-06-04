package webboards.client.data;

import webboards.client.data.Board;
import webboards.client.data.Overlay;
import webboards.client.data.OverlayChangeEvent;

@SuppressWarnings("all")
public final class OverlayChangeEventBuilder {
  public OverlayChangeEvent build() {
    return new OverlayChangeEvent(board,overlay);
    
  }
  
  public OverlayChangeEventBuilder board(final Board board) {
    this.board = board;
    return this;
    
  }
  
  private Board board;
  
  public OverlayChangeEventBuilder overlay(final Overlay overlay) {
    this.overlay = overlay;
    return this;
    
  }
  
  private Overlay overlay;
}
