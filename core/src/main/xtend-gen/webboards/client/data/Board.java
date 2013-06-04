package webboards.client.data;

import com.google.common.base.Objects;
import de.oehme.xtend.annotation.events.FiresEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import webboards.client.data.CounterChangeEvent;
import webboards.client.data.CounterInfo;
import webboards.client.data.CounterListener;
import webboards.client.data.HexInfo;
import webboards.client.data.Overlay;
import webboards.client.data.OverlayChangeEvent;
import webboards.client.data.OverlayListener;
import webboards.client.data.PositionChangeEvent;
import webboards.client.data.PositionListener;
import webboards.client.data.ref.CounterId;
import webboards.client.ex.WebBoardsException;
import webboards.client.games.Hex;
import webboards.client.games.Position;
import webboards.client.games.scs.ops.CombatOverlay;

@FiresEvent({ CounterListener.class, PositionListener.class, OverlayListener.class })
@SuppressWarnings("all")
public abstract class Board implements Serializable {
  private Map<CounterId,CounterInfo> counters = null;
  
  private Map<CounterId,CounterInfo> placed = null;
  
  private HashMap<Hex,List<Overlay>> hexOverlays = new Function0<HashMap<Hex,List<Overlay>>>() {
    public HashMap<Hex,List<Overlay>> apply() {
      HashMap<Hex,List<Overlay>> _newHashMap = CollectionLiterals.<Hex, List<Overlay>>newHashMap();
      return _newHashMap;
    }
  }.apply();
  
  public Board() {
    HashMap<CounterId,CounterInfo> _hashMap = new HashMap<CounterId,CounterInfo>();
    this.counters = _hashMap;
    HashMap<CounterId,CounterInfo> _hashMap_1 = new HashMap<CounterId,CounterInfo>();
    this.placed = _hashMap_1;
  }
  
  public HashSet<Position> getStacks() {
    HashSet<Position> _xblockexpression = null;
    {
      HashSet<Position> _hashSet = new HashSet<Position>();
      HashSet<Position> stacks = _hashSet;
      Set<Entry<CounterId,CounterInfo>> entrySet = this.counters.entrySet();
      for (final Entry<CounterId,CounterInfo> entry : entrySet) {
        CounterInfo _value = entry.getValue();
        stacks.add(_value.position);
      }
      _xblockexpression = (stacks);
    }
    return _xblockexpression;
  }
  
  public Collection<CounterInfo> getCounters() {
    Collection<CounterInfo> _values = this.counters.values();
    Collection<CounterInfo> _unmodifiableCollection = Collections.<CounterInfo>unmodifiableCollection(_values);
    return _unmodifiableCollection;
  }
  
  public CounterInfo getCounter(final CounterId id) {
    CounterInfo c = null;
    CounterInfo _get = this.counters.get(id);
    c = _get;
    boolean _notEquals = (!Objects.equal(c, null));
    if (_notEquals) {
      return c;
    }
    CounterInfo _get_1 = this.placed.get(id);
    c = _get_1;
    boolean _notEquals_1 = (!Objects.equal(c, null));
    if (_notEquals_1) {
      return c;
    }
    String _plus = ("Counter " + id);
    String _plus_1 = (_plus + " not found.");
    WebBoardsException _webBoardsException = new WebBoardsException(_plus_1);
    throw _webBoardsException;
  }
  
  public void place(final Position to, final CounterInfo counter) {
    this.placed.put(counter.ref, counter);
    this.move(to, counter);
  }
  
  public void setup(final Position to, final CounterInfo counter) {
    CounterId id = counter.ref;
    CounterInfo prev = this.counters.put(id, counter);
    boolean _notEquals = (!Objects.equal(prev, null));
    if (_notEquals) {
      String _plus = (id + " aleader placed");
      WebBoardsException _webBoardsException = new WebBoardsException(_plus);
      throw _webBoardsException;
    }
    this.move(to, counter);
  }
  
  public void flip(final CounterInfo counter) {
    boolean _flip = counter.flip();
    if (_flip) {
      CounterChangeEvent _counterChangeEvent = new CounterChangeEvent(this, counter);
      this.fireCounterChanged(_counterChangeEvent);
    }
  }
  
  public void move(final Position to, final CounterInfo counter) {
    Position from = counter.position;
    boolean _notEquals = (!Objects.equal(from, null));
    if (_notEquals) {
      HexInfo _info = this.getInfo(from);
      _info.pieces.remove(counter);
    }
    counter.setPosition(to);
    HexInfo _info_1 = this.getInfo(to);
    _info_1.pieces.add(counter);
    PositionChangeEvent _positionChangeEvent = new PositionChangeEvent(this, from);
    this.firePositionChanged(_positionChangeEvent);
    PositionChangeEvent _positionChangeEvent_1 = new PositionChangeEvent(this, to);
    this.firePositionChanged(_positionChangeEvent_1);
  }
  
  public ArrayList<HexInfo> getAdjacent(final Hex p) {
    ArrayList<HexInfo> _xblockexpression = null;
    {
      ArrayList<HexInfo> _arrayList = new ArrayList<HexInfo>(6);
      ArrayList<HexInfo> adj = _arrayList;
      int _xifexpression = (int) 0;
      int _modulo = (p.x % 2);
      boolean _tripleEquals = (Integer.valueOf(_modulo) == Integer.valueOf(0));
      if (_tripleEquals) {
        _xifexpression = 0;
      } else {
        int _minus = (-1);
        _xifexpression = _minus;
      }
      int o = _xifexpression;
      int _plus = (p.y + 1);
      HexInfo _id = this.toId(p.x, _plus);
      adj.add(_id);
      int _minus_1 = (p.x - 1);
      int _plus_1 = (p.y + 1);
      int _plus_2 = (_plus_1 + o);
      HexInfo _id_1 = this.toId(_minus_1, _plus_2);
      adj.add(_id_1);
      int _plus_3 = (p.x + 1);
      int _plus_4 = (p.y + 1);
      int _plus_5 = (_plus_4 + o);
      HexInfo _id_2 = this.toId(_plus_3, _plus_5);
      adj.add(_id_2);
      int _minus_2 = (p.x - 1);
      int _plus_6 = (p.y + o);
      HexInfo _id_3 = this.toId(_minus_2, _plus_6);
      adj.add(_id_3);
      int _plus_7 = (p.x + 1);
      int _plus_8 = (p.y + o);
      HexInfo _id_4 = this.toId(_plus_7, _plus_8);
      adj.add(_id_4);
      int _minus_3 = (p.y - 1);
      HexInfo _id_5 = this.toId(p.x, _minus_3);
      adj.add(_id_5);
      _xblockexpression = (adj);
    }
    return _xblockexpression;
  }
  
  private HexInfo toId(final int x, final int y) {
    Hex _hex = new Hex(x, y);
    HexInfo _info = this.getInfo(_hex);
    return _info;
  }
  
  public abstract HexInfo getInfo(final Position area);
  
  public CounterInfo getInfo(final CounterId ref) {
    CounterInfo _counter = this.getCounter(ref);
    return _counter;
  }
  
  public Collection<CounterInfo> getPlaced() {
    Collection<CounterInfo> _values = this.placed.values();
    Collection<CounterInfo> _unmodifiableCollection = Collections.<CounterInfo>unmodifiableCollection(_values);
    return _unmodifiableCollection;
  }
  
  public Collection<Overlay> overlaysAt(final Hex hex) {
    List<Overlay> _elvis = null;
    List<Overlay> _get = this.hexOverlays.get(hex);
    if (_get != null) {
      _elvis = _get;
    } else {
      List<Overlay> _emptyList = CollectionLiterals.<Overlay>emptyList();
      _elvis = ObjectExtensions.<List<Overlay>>operator_elvis(_get, _emptyList);
    }
    Collection<Overlay> _unmodifiableCollection = Collections.<Overlay>unmodifiableCollection(_elvis);
    return _unmodifiableCollection;
  }
  
  public <T extends Overlay> T placeAt(final T overlay, final Hex hex) {
    T _xblockexpression = null;
    {
      List<Overlay> overlays = this.hexOverlays.get(hex);
      boolean _equals = Objects.equal(overlays, null);
      if (_equals) {
        ArrayList<Overlay> _newArrayList = CollectionLiterals.<Overlay>newArrayList();
        List<Overlay> _overlays = overlays = _newArrayList;
        this.hexOverlays.put(hex, _overlays);
      }
      overlays.add(overlay);
      OverlayChangeEvent _overlayChangeEvent = new OverlayChangeEvent(this, overlay);
      this.fireOverlayCreated(_overlayChangeEvent);
      _xblockexpression = (overlay);
    }
    return _xblockexpression;
  }
  
  public CombatOverlay removeOverlayAt(final CombatOverlay overlay, final Hex hex) {
    CombatOverlay _xblockexpression = null;
    {
      List<Overlay> _elvis = null;
      List<Overlay> _get = this.hexOverlays.get(hex);
      if (_get != null) {
        _elvis = _get;
      } else {
        List<Overlay> _emptyList = CollectionLiterals.<Overlay>emptyList();
        _elvis = ObjectExtensions.<List<Overlay>>operator_elvis(_get, _emptyList);
      }
      final List<Overlay> overlays = _elvis;
      overlays.remove(overlay);
      boolean _isEmpty = overlays.isEmpty();
      if (_isEmpty) {
        this.hexOverlays.remove(hex);
      }
      OverlayChangeEvent _overlayChangeEvent = new OverlayChangeEvent(this, overlay);
      this.fireOverlayRemoved(_overlayChangeEvent);
      _xblockexpression = (overlay);
    }
    return _xblockexpression;
  }
  
  private List<CounterListener> _counterListeners = new ArrayList<CounterListener>();
  
  public void fireCounterChanged(final CounterChangeEvent counterChangeEvent) {
    for(CounterListener listener : _counterListeners) {
    	listener.counterChanged(counterChangeEvent);
    }
    
  }
  
  public void addCounterListener(final CounterListener listener) {
    _counterListeners.add(listener);
    
  }
  
  public void removeCounterListener(final CounterListener listener) {
    _counterListeners.remove(listener);
    
  }
  
  private List<PositionListener> _positionListeners = new ArrayList<PositionListener>();
  
  public void firePositionChanged(final PositionChangeEvent positionChangeEvent) {
    for(PositionListener listener : _positionListeners) {
    	listener.positionChanged(positionChangeEvent);
    }
    
  }
  
  public void addPositionListener(final PositionListener listener) {
    _positionListeners.add(listener);
    
  }
  
  public void removePositionListener(final PositionListener listener) {
    _positionListeners.remove(listener);
    
  }
  
  private List<OverlayListener> _overlayListeners = new ArrayList<OverlayListener>();
  
  public void fireOverlayRemoved(final OverlayChangeEvent overlayChangeEvent) {
    for(OverlayListener listener : _overlayListeners) {
    	listener.overlayRemoved(overlayChangeEvent);
    }
    
  }
  
  public void fireOverlayCreated(final OverlayChangeEvent overlayChangeEvent) {
    for(OverlayListener listener : _overlayListeners) {
    	listener.overlayCreated(overlayChangeEvent);
    }
    
  }
  
  public void fireOverlayChanged(final OverlayChangeEvent overlayChangeEvent) {
    for(OverlayListener listener : _overlayListeners) {
    	listener.overlayChanged(overlayChangeEvent);
    }
    
  }
  
  public void addOverlayListener(final OverlayListener listener) {
    _overlayListeners.add(listener);
    
  }
  
  public void removeOverlayListener(final OverlayListener listener) {
    _overlayListeners.remove(listener);
    
  }
}
