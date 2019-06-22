package delor.jsnake.console;

import java.io.IOException;
import java.util.Random;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Snake extends delor.jsnake.core.Snake {

	Snake(int column, int row) {
		super(column, row);
	}

	private static final Random random = new Random();

	private static final TextColor[] colors = { TextColor.ANSI.RED, TextColor.ANSI.GREEN, TextColor.ANSI.YELLOW,
			TextColor.ANSI.BLUE, TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN, TextColor.ANSI.WHITE };

	public static TextColor randomColor() {
		int x = random.nextInt(colors.length);
		return colors[x];
	}

	protected void AddHead() {
		AddNewPart('O');
	}

	boolean AddNewPart(char c) {
		return AddNewPart(new SnakePart(column, row, c));
	}

	public boolean AddNewPart(TextColor color) {
		return AddNewPart(new SnakePart(column, row, color));
	}

	boolean badEating(Terminal terminal) throws IOException {
		TerminalSize screenSize = terminal.getTerminalSize();
		return badEating(screenSize.getColumns(), screenSize.getRows());
	}

	void Show(Terminal terminal) throws IOException {

		TerminalSize screenSize = terminal.getTerminalSize();
		int screenX = screenSize.getColumns();
		int screenY = screenSize.getRows();

		parts.forEach(p -> {
			SnakePart s = (SnakePart) p;
			if (s.getX() >= 0 && s.getX() < screenX && s.getY() >= 0 && s.getY() < screenY) {
				try {
					terminal.setCursorPosition(s.getX(), s.getY());
					terminal.setForegroundColor(s.fgColor);
					terminal.putCharacter(s.appearance);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		terminal.setCursorPosition(screenX - 1, screenY - 1);
		terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
	}
}
