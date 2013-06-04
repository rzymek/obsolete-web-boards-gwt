package webboards.client.data;

import de.oehme.xtend.annotation.data.Immutable;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import webboards.client.data.Board;
import webboards.client.data.Overlay;
import webboards.client.data.OverlayChangeEventBuilder;

@Immutable
@SuppressWarnings("all")
public final class OverlayChangeEvent {
  public static OverlayChangeEvent build(final Procedure1<OverlayChangeEventBuilder> init) {
    webboards.client.data.OverlayChangeEventBuilder builder = builder();
    init.apply(builder);
    return builder.build();
    
  }
  
  public static OverlayChangeEventBuilder builder() {
    return new webboards.client.data.OverlayChangeEventBuilder();
    
  }
  
  public OverlayChangeEvent(final Board board, final Overlay overlay) {
    this.board = board;
    this.overlay = overlay;
    
  }
  
  public Board getBoard() {
    return board;
    
  }
  
  private Board board;
  
  public Overlay getOverlay() {
    return overlay;
    
  }
  
  private Overlay overlay;
  
  public boolean equals(final Object o) {
    if (o instanceof OverlayChangeEvent) {
    	OverlayChangeEvent other = (OverlayChangeEvent) o;
    	return com.google.common.base.Objects.equal(board, other.board)
    	&& com.google.common.base.Objects.equal(overlay, other.overlay);
    }
    return false;
    
  }
  
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(board,overlay);
  }
  
  public String toString() {
    return "OverlayChangeEvent{"+board+", "+overlay+"}";
  }
}
