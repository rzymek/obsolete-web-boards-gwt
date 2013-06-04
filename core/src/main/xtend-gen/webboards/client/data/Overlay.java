package webboards.client.data;

@SuppressWarnings("all")
public abstract class Overlay {
  public final String id;
  
  public Overlay(final String id) {
    this.id = id;
  }
  
  public abstract String getTemplateId();
}
