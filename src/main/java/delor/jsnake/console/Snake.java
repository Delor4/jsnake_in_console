package delor.jsnake.console;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Snake extends delor.jsnake.core.Snake {

	Terminal terminal;

	public Snake(Terminal terminal) throws IOException {
		super(terminal.getTerminalSize().getColumns() / 2, terminal.getTerminalSize().getRows() / 2);
		this.terminal = terminal;
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

	protected boolean AddNewPart() {
		return AddNewPart(randomColor());
	}

	boolean AddNewPart(char c) {
		return AddNewPart(new SnakePart(column, row, c));
	}

	public boolean AddNewPart(TextColor color) {
		return AddNewPart(new SnakePart(column, row, color));
	}

	protected boolean badEating() {
		if (super.badEating()) {
			return true;
		}
		Iterator<delor.jsnake.core.SnakePart> i = parts.iterator();
		delor.jsnake.core.SnakePart head = i.next();
		try {
			if (head.getX() < 0 || head.getX() >= terminal.getTerminalSize().getColumns() || head.getY() < 0
					|| head.getY() >= terminal.getTerminalSize().getRows()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void Show() throws IOException {

		TerminalSize screenSize = terminal.getTerminalSize();
		final int screenX = screenSize.getColumns();
		final int screenY = screenSize.getRows();

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
