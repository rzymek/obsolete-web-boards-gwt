package webboards.server;

import java.util.Collection;
import java.util.List;

import webboards.client.data.Board;
import webboards.client.data.CounterInfo;
import webboards.client.data.ref.CounterId;
import webboards.client.display.Color;
import webboards.client.display.Display;
import webboards.client.display.VisualCoords;
import webboards.client.games.Position;

public class NoopDisplay implements Display {

	@Override
	public void drawArrow(Position from, Position to, String id, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(Position fromRef, Position toRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mark(Collection<? extends Position> hexes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markUsed(CounterId ref) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResults(VisualCoords center, String result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawOds(VisualCoords center, String text, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void select(CounterInfo i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showStackSelector(List<CounterInfo> stack, Position pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearMarks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearArrow(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearResults(VisualCoords center) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTraces() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOds(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearLine(Position from, Position to) {
		// TODO Auto-generated method stub

	}

	@Override
	public VisualCoords getCenter(Position to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createCounter(CounterInfo counter, Board board) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alignStack(Position ref) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(CounterId counter, String state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(String id, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMode(Mode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawFromTemplate(VisualCoords center, String templateId, String text, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateText(String spriteId, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeElement(String id) {
		// TODO Auto-generated method stub

	}

}
