package webboards.client.games.scs.ops;

import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import webboards.client.data.Board;
import webboards.client.data.Overlay;
import webboards.client.games.Hex;
import webboards.client.games.scs.ops.CombatOverlay;
import webboards.client.ops.Operation;

@SuppressWarnings("all")
public class DeclareAttack extends Operation {
  private Hex from;
  
  private Hex target;
  
  @SuppressWarnings("unused")
  private DeclareAttack() {
  }
  
  public DeclareAttack(final Hex from, final Hex target) {
    this.from = from;
    this.target = target;
  }
  
  public void updateBoard(final Board board) {
    CombatOverlay _elvis = null;
    Collection<Overlay> _overlaysAt = board.overlaysAt(this.target);
    final Function1<Overlay,Boolean> _function = new Function1<Overlay,Boolean>() {
        public Boolean apply(final Overlay it) {
          return Boolean.valueOf((it instanceof CombatOverlay));
        }
      };
    Iterable<Overlay> _filter = IterableExtensions.<Overlay>filter(_overlaysAt, _function);
    Overlay _head = IterableExtensions.<Overlay>head(_filter);
    if (((CombatOverlay) _head) != null) {
      _elvis = ((CombatOverlay) _head);
    } else {
      CombatOverlay _combatOverlay = new CombatOverlay(this.target);
      CombatOverlay _placeAt = board.<CombatOverlay>placeAt(_combatOverlay, this.target);
      _elvis = ObjectExtensions.<CombatOverlay>operator_elvis(
        ((CombatOverlay) _head), _placeAt);
    }
    final CombatOverlay overlay = _elvis;
    overlay.toggle(board, this.from);
  }
  
  /**
   * override draw(GameCtx ctx) {
   * var board = (ctx.board as SCSBoard)
   * var attacking = board.getAttacking(target)
   * if (attacking.contains(from)) {
   * board.undeclareAttack(from)
   * ctx.display.clearArrow('combat_' + from.SVGId)
   * updateOdsDisplay(ctx, target, board)
   * } else {
   * var prevTarget = board.getAttacks(from)
   * board.declareAttack(from, target)
   * if (prevTarget !== null) {
   * updateOdsDisplay(ctx, prevTarget, board)
   * }
   * ctx.display.drawArrow(from, target, 'combat_' + from.SVGId, SCSColor::DELCARE.color)
   * updateOdsDisplay(ctx, target, board)
   * }
   * }
   * 
   * private def updateOdsDisplay(GameCtx ctx, Hex target, SCSBoard board) {
   * var targetHex = (ctx.board.getInfo(target) as SCSHex)
   * var attacking = board.getAttackingInfo(target)
   * if (attacking.empty) {
   * ctx.display.clearOds(target.SVGId)
   * } else {
   * var odds = SCSBoard::calculateOdds(targetHex, attacking, target)
   * var text = Arrays::asList(odds).join(':')
   * ctx.display.drawOds(ctx.display.getCenter(target), text, target.SVGId)
   * }
   * }
   */
  public String toString() {
    String _plus = ("Declare attack " + this.from);
    String _plus_1 = (_plus + " -> ");
    String _plus_2 = (_plus_1 + this.target);
    return _plus_2;
  }
}
