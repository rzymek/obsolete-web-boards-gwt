package webboards.client.games.scs.ops;

import webboards.client.data.Board;
import webboards.client.data.GameCtx;
import webboards.client.games.Hex;
import webboards.client.games.scs.SCSCounter;
import webboards.client.games.scs.SCSHex;
import webboards.client.ops.Operation;
import webboards.client.ops.RemoveOverlay;
import webboards.client.overlay.ArrowOverlay;
import webboards.client.overlay.ArtyTargetOverlay;
import webboards.client.overlay.Attach;
import webboards.client.overlay.Overlay;
import webboards.client.overlay.OverlayLine;

public class DeclareBarrage extends Operation {
	private static final long serialVersionUID = 1L;
	private SCSCounter arty;
	private Hex target;

	@SuppressWarnings("unused")
	private DeclareBarrage() {
	}

	public DeclareBarrage(SCSCounter arty, Hex target) {
		this.arty = arty;
		this.target = target;
	}

	@Override
	public void updateBoard(Board board) {
		SCSHex hex = (SCSHex) board.getInfo(target);
		int value = (int) hex.applyBarrageModifiers(arty.getAttack());
		
		final Overlay artyTarget = new ArtyTargetOverlay(value);
//		if(ctx.side == arty.getOwner()) {
			artyTarget.onClick = new Overlay.Handler() {
				@Override
				public Operation onClick(GameCtx ctx) {
					if(ctx.selected == arty) {
						return new RemoveOverlay(artyTarget);
					}else{
						return new PerformBarrage(arty, target);
					}				
				}
			};
//		}
		
		hex.setStackOverlay(artyTarget, Attach.REMOVE_WHEN_MOVED);
	}
	@Override
	public void draw(GameCtx ctx) {
//		ctx.board.attach(target, artyTarget, Attach.REMOVE_WHEN_MOVED);
//		artyTarget.attach(ctx.board, target, Attach.REMOVE_WHEN_MOVED);
		
		OverlayLine arrow = new ArrowOverlay();
		arrow.attachStart(arty, Attach.MOVE_WITH);
		arrow.attachEnd(target, Attach.REMOVE_WHEN_MOVED);
		
//		ctx.display.drawOverlaysAt(target);
		ctx.display.draw(arrow);
	} 
  
	@Override
	public String toString() {
		return "Declare barrage " + arty + " -> " + target;
	}

}
