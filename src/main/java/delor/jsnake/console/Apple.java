package delor.jsnake.console;

import com.googlecode.lanterna.TextColor;

public class Apple extends delor.jsnake.core.Apple {

	public Apple(int x, int y) {
		super(x, y);
	}

	public Apple(int x, int y, TextColor color) {
		this(x, y);
		this.fgColor = color;
	}

	public Apple(int x, int y, char apperance, TextColor color) {
		this(x, y, color);
		this.apperance = apperance;
	}

	public TextColor fgColor = TextColor.ANSI.GREEN;
	public char apperance = 'A';
}
