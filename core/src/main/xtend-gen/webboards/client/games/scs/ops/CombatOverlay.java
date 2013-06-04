package webboards.client.games.scs.ops;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import webboards.client.data.Board;
import webboards.client.data.Overlay;
import webboards.client.games.Hex;

@SuppressWarnings("all")
public class CombatOverlay extends Overlay {
  private final Set<Hex> from = new Function0<Set<Hex>>() {
    public Set<Hex> apply() {
      HashSet<Hex> _newHashSet = CollectionLiterals.<Hex>newHashSet();
      return _newHashSet;
    }
  }.apply();
  
  private final Hex target;
  
  public CombatOverlay(final Hex target) {
    this.target = target;
  }
  
  public boolean toggle(final Board board, final Hex hex) {
    boolean _xblockexpression = false;
    {
      this.from.remove(hex);
      boolean _isEmpty = this.from.isEmpty();
      if (_isEmpty) {
        board.removeOverlayAt(this, hex);
      }
      boolean _add = this.from.add(hex);
      _xblockexpression = (_add);
    }
    return _xblockexpression;
  }
}
