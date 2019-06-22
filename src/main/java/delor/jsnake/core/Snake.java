package delor.jsnake.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Snake {

	public enum Dir {
		NORTH, WEST, SOUTH, EAST
	}

	protected List<SnakePart> parts = new ArrayList<SnakePart>();
	protected int column;
	protected int row;

	protected Snake(int column, int row) {
		this.column = column;
		this.row = row;
		AddHead();
	}

	protected void AddHead() {
		AddNewPart();
	}

	protected boolean AddNewPart(SnakePart p) {
		return this.parts.add(p);
	}

	boolean AddNewPart() {
		return AddNewPart(new SnakePart(column, row));
	}

	boolean AddNewPart(Dir d) {
		return AddNewPart(new SnakePart(column, row, d));
	}

	protected boolean badEating(int screenX, int screenY) {
		Iterator<SnakePart> i = parts.iterator();
		SnakePart head = i.next();
		if (head.getX() < 0 || head.getX() >= screenX || head.getY() < 0 || head.getY() >= screenY) {
			return true;
		}
		while (i.hasNext()) {
			SnakePart s = i.next();
			if (s.getX() == head.getX() && s.getY() == head.getY())
				return true;
		}
		return false;
	}

	public void Move(Dir dir) {

		ListIterator<SnakePart> ri = parts.listIterator(parts.size());
		SnakePart s = ri.previous();
		while (ri.hasPrevious()) {
			SnakePart p = ri.previous();
			s.changeDir(p.dir, p.getX(), p.getY());
			s = p;
		}

		switch (dir) {
		case NORTH:
			row -= 1;
			break;
		case SOUTH:
			row += 1;
			break;
		case WEST:
			column -= 1;
			break;
		case EAST:
			column += 1;
			break;

		}
		s.changeDir(dir, column, row);
	}

}
