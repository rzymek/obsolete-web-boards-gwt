package webboards.client.display;

import java.util.List;

import webboards.client.OpRunner;
import webboards.client.data.CounterInfo;
import webboards.client.data.GameCtx;
import webboards.client.data.HexInfo;
import webboards.client.games.Position;
import webboards.client.ops.Operation;

public class SelectionHandler {
	protected final GameCtx ctx;
	private final OpRunner runner;

	public SelectionHandler(GameCtx ctx, OpRunner runner) {
		this.ctx = ctx;
		this.runner = runner;
	}

	public final void onClicked(Position position) {
		runner.process(onPositionClicked(position));
	}

	public final void onClicked(List<CounterInfo> stack, Position pos) {
		runner.process(onStackClicked(stack, pos));
	}

	public final void onClicked(CounterInfo counter) {
		runner.process(onSingleCounterClicked(counter));
	}

	public final void onSelect(CounterInfo counter) {
		runner.process(onSelected(counter));
	}

	public boolean canSelect(CounterInfo counter) {
		return true;
	}

	protected Operation onSelected(CounterInfo counter) {
		return null;
	}

	protected Operation onSingleCounterClicked(CounterInfo counter) {
		if (ctx.selected == null) {
			return counter.onSingleClicked(ctx);
		} else {
			return ctx.selected.onPointTo(ctx, counter);
		}
	}

	protected Operation onPositionClicked(Position position) {
		HexInfo area = ctx.board.getInfo(position);
		List<CounterInfo> stack = area.getPieces();
		if (stack.isEmpty()) {
			return onEmptyHexClicked(position);
		} else if (stack.size() == 1) {
			return onSingleCounterClicked(stack.iterator().next());
		} else {
			return onStackClicked(stack, position);
		}
	}

	protected Operation onEmptyHexClicked(Position empty) {
		if (ctx.selected == null) {
			return ctx.board.getInfo(empty).onEmptyClicked(ctx, empty);
		} else {
			return ctx.selected.onPointToEmpty(ctx, empty);
		}
	}

	protected Operation onStackClicked(List<CounterInfo> stack, Position pos) {
		if (ctx.selected == null) {
			return ctx.board.getInfo(pos).onStackClicked(ctx, stack, pos);
		} else {
			return ctx.selected.onPointToStack(ctx, stack, pos);
		}
	}
}
