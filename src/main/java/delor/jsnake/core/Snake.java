package delor.jsnake.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Snake {

	final int initialSnakeParts = 2;

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
		for (int i = 0; i < initialSnakeParts; i++) {
			AddNewPart();
		}
	}

	protected void AddHead() {
		AddNewPart();
	}

	protected boolean AddNewPart(SnakePart p) {
		return this.parts.add(p);
	}

	protected boolean AddNewPart() {
		return AddNewPart(new SnakePart(column, row));
	}

	boolean AddNewPart(Dir d) {
		return AddNewPart(new SnakePart(column, row, d));
	}

	protected boolean badEating() {
		Iterator<SnakePart> i = parts.iterator();
		SnakePart head = i.next();
		while (i.hasNext()) {
			SnakePart s = i.next();
			if (s.getX() == head.getX() && s.getY() == head.getY())
				return true;
		}
		return false;
	}

	public void Move() {
		Move(parts.get(0).dir);
	}

	public void setDir(Dir dir) {
		parts.get(0).dir = dir;
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
