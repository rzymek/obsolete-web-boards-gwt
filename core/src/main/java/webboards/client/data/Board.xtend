package webboards.client.data

import de.oehme.xtend.annotation.events.FiresEvent
import java.io.Serializable
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import webboards.client.data.ref.CounterId
import webboards.client.ex.WebBoardsException
import webboards.client.games.Hex
import webboards.client.games.Position
import webboards.client.games.scs.ops.CombatOverlay

@FiresEvent(#[typeof(CounterListener), typeof(PositionListener), typeof(OverlayListener)])
abstract class Board implements Serializable {
	var Map<CounterId, CounterInfo> counters = null
	var Map<CounterId, CounterInfo> placed = null

	new() {
		counters = new HashMap<CounterId, CounterInfo>()
		placed = new HashMap<CounterId, CounterInfo>()
	}

	def getStacks() {
		var stacks = new HashSet<Position>()
		var entrySet = counters.entrySet
		for (entry : entrySet) {
			stacks.add(entry.value.position)
		}
		stacks
	}

	def getCounters() {
		Collections::unmodifiableCollection(counters.values)
	}

	def getCounter(CounterId id) {
		var CounterInfo c
		c = counters.get(id)
		if (c != null) {
			return c;
		}
		c = placed.get(id)
		if (c != null) {
			return c;
		}
		throw new WebBoardsException("Counter " + id + " not found.");
	}

	def place(Position to, CounterInfo counter) {
		placed.put(counter.ref, counter)
		move(to, counter)
	}

	def setup(Position to, CounterInfo counter) {
		var id = counter.ref
		var prev = counters.put(id, counter)
		if (prev != null) {
			throw new WebBoardsException(id + " aleader placed");
		}
		move(to, counter)
	}

	def flip(CounterInfo counter) {
		if (counter.flip()) {
			fireCounterChanged(new CounterChangeEvent(this, counter))
		}
	}

	def move(Position to, CounterInfo counter) {
		var from = counter.position
		if (from != null) {
			getInfo(from).pieces.remove(counter)
		}
		counter.setPosition(to)
		getInfo(to).pieces.add(counter)
		firePositionChanged(new PositionChangeEvent(this, from))
		firePositionChanged(new PositionChangeEvent(this, to))
	}

	def getAdjacent(Hex p) {
		var adj = new ArrayList<HexInfo>(6)
		var o = if((p.x % 2 === 0)) 0 else -1
		adj.add(toId(p.x, p.y + 1))
		adj.add(toId(p.x - 1, p.y + 1 + o))
		adj.add(toId(p.x + 1, p.y + 1 + o))
		adj.add(toId(p.x - 1, p.y + o))
		adj.add(toId(p.x + 1, p.y + o))
		adj.add(toId(p.x, p.y - 1))
		adj
	}

	private def toId(int x, int y) {
		getInfo(new Hex(x, y))
	}

	abstract def HexInfo getInfo(Position area)

	def getInfo(CounterId ref) {
		getCounter(ref)
	}

	def getPlaced() {
		Collections::unmodifiableCollection(placed.values)
	}

	val HashMap<Hex, List<Overlay>> hexOverlays = newHashMap

	def overlaysAt(Hex hex) {
		Collections::unmodifiableCollection(hexOverlays.get(hex) ?: emptyList)
	}

	def <T extends Overlay> placeAt(T overlay, Hex hex) {
		var overlays = hexOverlays.get(hex)
		if (overlays == null)
			hexOverlays.put(hex, overlays = newArrayList());
		overlays.add(overlay)
		fireOverlayCreated(new OverlayChangeEvent(this, overlay))
		overlay
	}

	def removeOverlayAt(CombatOverlay overlay, Hex hex) {
		val overlays = hexOverlays.get(hex) ?: emptyList
		overlays.remove(overlay)
		if (overlays.empty)
			hexOverlays.remove(hex)
		fireOverlayRemoved(new OverlayChangeEvent(this, overlay))
		overlay
	}

}
