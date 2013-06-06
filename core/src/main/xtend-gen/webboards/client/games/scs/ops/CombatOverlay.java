package webboards.client.games.scs.ops;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import webboards.client.data.Board;
import webboards.client.data.GameCtx;
import webboards.client.data.HexInfo;
import webboards.client.data.OverlayChangeEvent;
import webboards.client.data.PositionOverlay;
import webboards.client.games.Hex;
import webboards.client.games.Position;
import webboards.client.games.scs.SCSBoard;
import webboards.client.games.scs.SCSBoard.Odds;
import webboards.client.games.scs.SCSHex;
import webboards.client.games.scs.ops.LabeledOverlay;

@SuppressWarnings("all")
public class CombatOverlay extends LabeledOverlay implements PositionOverlay {
  private final Set<Hex> from = new Function0<Set<Hex>>() {
    public Set<Hex> apply() {
      HashSet<Hex> _newHashSet = CollectionLiterals.<Hex>newHashSet();
      return _newHashSet;
    }
  }.apply();
  
  private final Hex target;
  
  public CombatOverlay(final Hex target) {
    super(new Function0<String>() {
      public String apply() {
        String _plus = ("combatAt" + target);
        return _plus;
      }
    }.apply());
    this.target = target;
  }
  
  public void toggle(final Board board, final Hex hex) {
    boolean _contains = this.from.contains(hex);
    if (_contains) {
      this.from.remove(hex);
      boolean _isEmpty = this.from.isEmpty();
      if (_isEmpty) {
        board.removeOverlayAt(this, this.target);
        return;
      }
    } else {
      this.from.add(hex);
    }
    OverlayChangeEvent _overlayChangeEvent = new OverlayChangeEvent(board, this);
    board.fireOverlayChanged(_overlayChangeEvent);
  }
  
  public String label(final GameCtx ctx) {
    String _xblockexpression = null;
    {
      HexInfo _info = ctx.board.getInfo(this.target);
      SCSHex targetHex = ((SCSHex) _info);
      final SCSBoard board = ((SCSBoard) ctx.board);
      final Function1<Hex,SCSHex> _function = new Function1<Hex,SCSHex>() {
          public SCSHex apply(final Hex it) {
            SCSHex _info = board.getInfo(it);
            return _info;
          }
        };
      final Iterable<SCSHex> attacking = IterableExtensions.<Hex, SCSHex>map(this.from, _function);
      Odds odds = SCSBoard.calculateOdds(targetHex, attacking, this.target);
      List<Odds> _asList = Arrays.<Odds>asList(odds);
      String _join = IterableExtensions.join(_asList, ":");
      _xblockexpression = (_join);
    }
    return _xblockexpression;
  }
  
  public String getTemplateId() {
    return "target";
  }
  
  public Position getPosition() {
    return this.target;
  }
}
