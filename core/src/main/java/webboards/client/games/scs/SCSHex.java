package webboards.client.games.scs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webboards.client.data.CounterInfo;
import webboards.client.data.HexInfo;
import webboards.client.games.Hex;
import webboards.client.games.Position;
import webboards.client.games.scs.bastogne.HexTraits;
import webboards.client.overlay.Attach;
import webboards.client.overlay.Overlay;

public class SCSHex extends HexInfo {
	private static final long serialVersionUID = 1L;
	private Set<HexTraits> traits = null;

	public SCSHex() {
		traits = Collections.emptySet();
	}

	public SCSHex(HexTraits... traits) {
		this.traits = new HashSet<HexTraits>(Arrays.asList(traits));
	}
	
	public List<SCSCounter> getUnits() {
		List<SCSCounter> units = new ArrayList<SCSCounter>();
		for (CounterInfo piece : pieces) {
			if(piece instanceof SCSCounter) {
				units.add((SCSCounter) piece);
			}
		}
		return Collections.unmodifiableList(units);
	}
	public List<SCSMarker> getMarkers() {
		List<SCSMarker> result = new ArrayList<SCSMarker>();
		for (CounterInfo piece : pieces) {
			if(piece instanceof SCSMarker) {
				result.add((SCSMarker) piece);
			}
		}
		return Collections.unmodifiableList(result);
	}

	public Set<HexTraits> getTraits() {
		return traits;
	}

	public static boolean isAdjacent(Position pa, Position pb) {
		if (pa instanceof Hex && pb instanceof Hex) {
			Hex a = (Hex) pa;
			Hex b = (Hex) pb;
			int o = (b.x % 2 == 0) ? 0 : -1;
			//@formatter:off
			return 					(equals(a, b.x,b.y+1) || 
					equals(a, b.x-1,b.y+1+o) || equals(a, b.x+1,b.y+1+o) ||	
					equals(a, b.x-1,b.y+o)   ||	equals(a, b.x+1,b.y+o) ||
									equals(a, b.x,b.y-1));
			//@formatter:on
		} else {
			return false;
		}
	}
	
	private static boolean equals(Hex a, int x, int y) {
		return a.x == x && a.y == y;
	}

	public double getDefenceCombatModifier() {
		double modifier = 1.0;
		if(traits.contains(HexTraits.FOREST)) {
			modifier *= 2.0;
		}
		if(traits.contains(HexTraits.CITY)) {
			modifier *= 2.0;
		}
		if(hasDG()) {
			modifier /= 2.0;
		}
		return modifier;
	}

	public boolean hasDG() {
		for (CounterInfo piece : pieces) {
			if(piece instanceof SCSMarker) {
				//TODO: check if marker is realy DG
				return true;
			}
		}
		return false;
	}

	public float applyBarrageModifiers(float attack) {
		if(hasDG()) {
			attack /= 2;
		}
		if(traits.contains(HexTraits.FOREST)) {
			attack += 1;
		}
		if(traits.contains(HexTraits.CITY)) {
			attack -= 1;
		}
		return attack;
	}

	public void setStackOverlay(Overlay overlay, Attach how) {
		
	}
}
