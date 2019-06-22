package delor.jsnake.core;

import delor.jsnake.core.Snake.Dir;

public class SnakePart extends Counted {
	Dir dir = Dir.NORTH;
	private int x;
	private int y;

	protected SnakePart(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	protected SnakePart(int x, int y, Dir dir) {
		this(x, y);
		this.dir = dir;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	SnakePart changeDir(Dir dir, int x, int y) {
		this.setX(x);
		this.setY(y);
		this.dir = dir;
		return this;
	}

	protected String className() {
		return "SnakePart";
	}

	public String toString() {
		return className() + "[" + super.getCounter() + "]" + "(dir:" + dir + ", x:" + getX() + ", y:" + getY() + ")";
	}
}
