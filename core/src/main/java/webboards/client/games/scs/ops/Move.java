package webboards.client.games.scs.ops;

import webboards.client.data.Board;
import webboards.client.data.CounterInfo;
import webboards.client.data.GameCtx;
import webboards.client.data.ref.CounterId;
import webboards.client.games.Position;
import webboards.client.ops.Operation;
import webboards.client.ops.Undoable;

public class Move extends Operation implements Undoable {
	private static final long serialVersionUID = 1L;
	public CounterId counterRef;
	public Position from;
	public Position to;

	protected Move() {
	}

	public Move(CounterInfo counter, Position to) {
		counterRef = counter.ref();
		this.from = counter.getPosition();
		this.to = to;
	}

	@Override
	public void updateBoard(GameCtx ctx) {
		CounterInfo counter = ctx.board.getInfo(counterRef);
		ctx.board.move(to, counter);
	}

	@Override
	public void undoUpdate(GameCtx ctx) {
		CounterInfo counter = ctx.board.getInfo(counterRef);
		ctx.board.move(from, counter);
	}

	@Override
	public void undoDraw(GameCtx ctx) {
		draw(ctx);
		ctx.display.clearLine(from, to);
	}

	@Override
	public void drawDetails(GameCtx ctx) {
		ctx.display.drawLine(from, to);
	}

	@Override
	public String toString() {
		return counterRef + " moves from " + from + " to " + to;
	}
}
