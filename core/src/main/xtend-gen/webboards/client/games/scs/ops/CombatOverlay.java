package webboards.client.games.scs.ops;

import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import webboards.client.data.GameCtx;
import webboards.client.data.HexInfo;
import webboards.client.data.OverlayChangeEvent;
import webboards.client.data.PositionChangeEvent;
import webboards.client.data.PositionListener;
import webboards.client.display.Color;
import webboards.client.display.VisualCoords;
import webboards.client.games.Hex;
import webboards.client.games.Position;
import webboards.client.games.scs.SCSBoard;
import webboards.client.games.scs.SCSBoard.Odds;
import webboards.client.games.scs.SCSColor;
import webboards.client.games.scs.SCSHex;
import webboards.client.games.scs.ops.LabeledOverlay;

@SuppressWarnings("all")
public class CombatOverlay extends LabeledOverlay {
  private final Set<Hex> from = new Function0<Set<Hex>>() {
    public Set<Hex> apply() {
      HashSet<Hex> _newHashSet = CollectionLiterals.<Hex>newHashSet();
      return _newHashSet;
    }
  }.apply();
  
  private final Hex target;
  
  public CombatOverlay(final GameCtx ctx, final Hex target) {
    super(new Function0<String>() {
      public String apply() {
        String _plus = ("combatAt" + target);
        return _plus;
      }
    }.apply());
    this.target = target;
    final PositionListener _function = new PositionListener() {
        public void positionChanged(final PositionChangeEvent it) {
          Position _position = it.getPosition();
          final Hex hex = ((Hex) _position);
          final Function1<Hex,Boolean> _function = new Function1<Hex,Boolean>() {
              public Boolean apply(final Hex it) {
                boolean _equals = Objects.equal(it, hex);
                return Boolean.valueOf(_equals);
              }
            };
          Iterable<Hex> _filter = IterableExtensions.<Hex>filter(CombatOverlay.this.from, _function);
          final Procedure1<Hex> _function_1 = new Procedure1<Hex>() {
              public void apply(final Hex it) {
                CombatOverlay.this.toggle(ctx, hex);
              }
            };
          IterableExtensions.<Hex>forEach(_filter, _function_1);
        }
      };
    ctx.board.addPositionListener(_function);
  }
  
  public void toggle(final GameCtx ctx, final Hex hex) {
    boolean _contains = this.from.contains(hex);
    if (_contains) {
      this.from.remove(hex);
      String _sVGId = hex.getSVGId();
      ctx.display.clearArrow(_sVGId);
      boolean _isEmpty = this.from.isEmpty();
      if (_isEmpty) {
        ctx.board.removeOverlayAt(this, this.target);
        return;
      }
    } else {
      this.from.add(hex);
      String _sVGId_1 = hex.getSVGId();
      Color _color = SCSColor.DELCARE.getColor();
      ctx.display.drawArrow(hex, this.target, _sVGId_1, _color);
    }
    OverlayChangeEvent _overlayChangeEvent = new OverlayChangeEvent(ctx.board, this);
    ctx.board.fireOverlayChanged(_overlayChangeEvent);
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
  
  public void create(final GameCtx ctx) {
    VisualCoords center = ctx.display.getCenter(this.target);
    String text = this.label(ctx);
    String _templateId = this.getTemplateId();
    ctx.display.drawFromTemplate(center, _templateId, text, this.id);
  }
  
  public void delete(final GameCtx ctx) {
    super.delete(ctx);
    final Procedure1<Hex> _function = new Procedure1<Hex>() {
        public void apply(final Hex it) {
          String _sVGId = it.getSVGId();
          ctx.display.clearArrow(_sVGId);
        }
      };
    IterableExtensions.<Hex>forEach(this.from, _function);
  }
}
