package webboards.client.data;

import com.google.common.base.Objects;
import de.oehme.xtend.annotation.events.api.FiresEvent;
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
import webboards.client.data.BoardListener;
import webboards.client.data.CounterChangeEvent;
import webboards.client.data.CounterInfo;
import webboards.client.data.HexInfo;
import webboards.client.data.PositionChangeEvent;
import webboards.client.data.ref.CounterId;
import webboards.client.ex.WebBoardsException;
import webboards.client.games.Hex;
import webboards.client.games.Position;

@FiresEvent(BoardListener.class)
@SuppressWarnings("all")
public abstract class Board implements Serializable {
  private Map<CounterId,CounterInfo> counters = null;
  
  private Map<CounterId,CounterInfo> placed = null;
  
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
  
  private List<BoardListener> _boardListeners = new ArrayList<BoardListener>();
  
  public void firePositionChanged(final PositionChangeEvent positionChangeEvent) {
    if(_boardListeners == null) return;
    for(BoardListener listener : _boardListeners) {
    	listener.positionChanged(positionChangeEvent);
    }
    
  }
  
  public void fireCounterChanged(final CounterChangeEvent counterChangeEvent) {
    if(_boardListeners == null) return;
    for(BoardListener listener : _boardListeners) {
    	listener.counterChanged(counterChangeEvent);
    }
    
  }
  
  public void addBoardListener(final BoardListener listener) {
    _boardListeners.add(listener);
    
  }
  
  public void removeBoardListener(final BoardListener listener) {
    _boardListeners.remove(listener);
    
  }
}
