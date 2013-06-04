package webboards.client.games.scs.ops;

import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import webboards.client.data.GameCtx;
import webboards.client.data.HexInfo;
import webboards.client.display.Color;
import webboards.client.display.VisualCoords;
import webboards.client.games.Hex;
import webboards.client.games.scs.SCSBoard;
import webboards.client.games.scs.SCSColor;
import webboards.client.games.scs.SCSHex;
import webboards.client.ops.Operation;

@SuppressWarnings("all")
public class DeclareAttack extends Operation {
  private final static long serialVersionUID = 1L;
  
  private Hex from;
  
  private Hex target;
  
  @SuppressWarnings("unused")
  private DeclareAttack() {
  }
  
  public DeclareAttack(final Hex from, final Hex target) {
    this.from = from;
    this.target = target;
  }
  
  public void draw(final GameCtx ctx) {
    SCSBoard board = ((SCSBoard) ctx.board);
    Collection<Hex> attacking = board.getAttacking(this.target);
    boolean _contains = attacking.contains(this.from);
    if (_contains) {
      board.undeclareAttack(this.from);
      String _sVGId = this.from.getSVGId();
      String _plus = ("combat_" + _sVGId);
      ctx.display.clearArrow(_plus);
      this.updateOdsDisplay(ctx, this.target, board);
    } else {
      Hex prevTarget = board.getAttacks(this.from);
      board.declareAttack(this.from, this.target);
      boolean _tripleNotEquals = (prevTarget != null);
      if (_tripleNotEquals) {
        this.updateOdsDisplay(ctx, prevTarget, board);
      }
      String _sVGId_1 = this.from.getSVGId();
      String _plus_1 = ("combat_" + _sVGId_1);
      Color _color = SCSColor.DELCARE.getColor();
      ctx.display.drawArrow(this.from, this.target, _plus_1, _color);
      this.updateOdsDisplay(ctx, this.target, board);
    }
  }
  
  private void updateOdsDisplay(final GameCtx ctx, final Hex target, final SCSBoard board) {
    HexInfo _info = ctx.board.getInfo(target);
    SCSHex targetHex = ((SCSHex) _info);
    Collection<SCSHex> attacking = board.getAttackingInfo(target);
    boolean _isEmpty = attacking.isEmpty();
    if (_isEmpty) {
      String _sVGId = target.getSVGId();
      ctx.display.clearOds(_sVGId);
    } else {
      int[] odds = SCSBoard.calculateOdds(targetHex, attacking, target);
      final int[] _converted_odds = (int[])odds;
      String text = IterableExtensions.join(((Iterable<? extends Object>)Conversions.doWrapArray(_converted_odds)), ":");
      VisualCoords _center = ctx.display.getCenter(target);
      String _sVGId_1 = target.getSVGId();
      ctx.display.drawOds(_center, text, _sVGId_1);
    }
  }
  
  public String toString() {
    String _plus = ("Declare attack " + this.from);
    String _plus_1 = (_plus + " -> ");
    String _plus_2 = (_plus_1 + this.target);
    return _plus_2;
  }
}
