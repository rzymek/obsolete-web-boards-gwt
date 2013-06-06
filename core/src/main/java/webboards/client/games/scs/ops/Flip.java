package webboards.client.games.scs.ops;

import webboards.client.data.CounterInfo;
import webboards.client.data.GameCtx;
import webboards.client.data.ref.CounterId;
import webboards.client.ops.Operation;

public class Flip extends Operation{
	private static final long serialVersionUID = 1L;
	private CounterId counterRef;
	protected Flip() {
	}
	
	public Flip(CounterId counterRef) {
		this.counterRef = counterRef;
	}

	@Override
	public void updateBoard(GameCtx ctx) {
		CounterInfo counter = ctx.board.getInfo(counterRef);
		ctx.board.flip(counter);
	}

	@Override
	public String toString() {
		return counterRef+" flipped.";
	}
	
	@Override
	public void undoUpdate(GameCtx ctx) {
		updateBoard(ctx);
	}
	@Override
	public void undoDraw(GameCtx ctx) {
		draw(ctx);		
	}
}
