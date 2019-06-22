package delor.jsnake.console;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;

import delor.jsnake.core.Snake.Dir;

public class SnakePart extends delor.jsnake.core.SnakePart {
	char appearance = 'o';
	TextColor fgColor = ANSI.DEFAULT;
	
	SnakePart(int x, int y, TextColor color) {
		super(x, y);
		this.fgColor = color;
	}
	SnakePart(int x, int y, char apperance) {
		super(x, y);
		this.appearance = apperance;
	}
	SnakePart(int x, int y, Dir dir, char apperance) {
		super(x, y, dir);
		this.appearance = apperance;
	}
	SnakePart(int x, int y, Dir dir, TextColor color) {
		super(x, y, dir);
		this.fgColor = color;
	}	
	SnakePart(int x, int y, TextColor color, char apperance) {
		this(x, y, color);
		this.appearance = apperance;
	}
	SnakePart(int x, int y, Dir dir, TextColor color, char apperance) {
		this(x, y, dir, color);
		this.appearance = apperance;
	}


}
