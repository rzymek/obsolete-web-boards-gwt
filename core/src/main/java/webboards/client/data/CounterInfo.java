	package webboards.client.data;
	
	import java.io.Serializable;
	import java.util.List;
	
	import webboards.client.data.ref.CounterId;
	import webboards.client.games.Position;
	import webboards.client.games.scs.ops.Move;
	import webboards.client.ops.Operation;
	
	public abstract class CounterInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		protected Position position = null;
		protected boolean flipped = false;
		protected CounterId ref = null;
		
		protected CounterInfo(CounterId ref) {
			this.ref = ref;
		}
		
		public CounterId ref() {
			return ref;
		}
		
		public Position getPosition() {
			return position;
		}
	
		protected void setPosition(Position pos) {
			this.position = pos;
		}
	
		public abstract String getState();
		
		public boolean isFlipped() {
			return flipped;
		}
	
		@Override
		public String toString() {
			return ref()+":"+getPosition().getSVGId();
		}
	
		public Operation onPointTo(GameCtx ctx, CounterInfo counter) {
			if(counter == this) {
				ctx.display.select(null);
				return null;
			}else{
				return onPointToEmpty(ctx, counter.getPosition());
			}
		}
	
		public Operation onPointToEmpty(GameCtx ctx, Position pos) {
	//		ctx.display.select(null);
			return new Move(this, pos);
		}
	
		public Operation onSingleClicked(GameCtx ctx) {		
			ctx.display.select(this);
			return null;
		}
	
		public Operation onPointToStack(GameCtx ctx, List<CounterInfo> stack, Position pos) {
			if(pos.equals(getPosition())) {
				ctx.display.showStackSelector(stack, pos);
				return null;
			}else{
				return onPointToEmpty(ctx, pos);
			}
		}

		protected boolean flip() {
			flipped = !flipped;
			return true;
		}
	
	}
