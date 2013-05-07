package webboards.client.overlay;

import webboards.client.data.ref.CounterId;
import webboards.client.games.Position;

@SuppressWarnings("all")
public class Attachable {
  private CounterId counterId;
  
  private Position position;
  
  public Attachable(final CounterId counterId) {
    this.counterId = counterId;
    this.position = null;
  }
  
  public Attachable(final Position position) {
    this.counterId = null;
    this.position = position;
  }
}
