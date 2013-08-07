package webboards.client.data;

import com.google.common.base.Objects;
import java.io.Serializable;
import webboards.client.data.GameCtx;

@SuppressWarnings("all")
public abstract class Overlay implements Serializable {
  public final String id;
  
  public Overlay(final String id) {
    this.id = id;
  }
  
  public abstract String getTemplateId();
  
  public int hashCode() {
    int _hashCode = Objects.hashCode(this.id);
    return _hashCode;
  }
  
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof Overlay)) {
      boolean _equals = Objects.equal(this.id, ((Overlay) obj).id);
      _xifexpression = _equals;
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  public void create(final GameCtx ctx) {
  }
  
  public void update(final GameCtx ctx) {
  }
  
  public void delete(final GameCtx ctx) {
    ctx.display.removeElement(this.id);
  }
}
