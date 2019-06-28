package delor.jsnake.console;

import com.googlecode.lanterna.TextColor;

public class Apple extends delor.jsnake.core.Apple {
	public Apple(int x, int y, char apperance, TextColor fgColor) {
		super(x, y);
		this.apperance = apperance;
		this.fgColor = fgColor;
	}

	public Apple(int x, int y) {
		super(x, y);
	}

	public TextColor fgColor = TextColor.ANSI.GREEN;
	public char apperance = 'A';
}
