package delor.jsnake.console;

import java.io.IOException;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Snake!!!
 *
 */
public class App {
	public static void runTerminal() throws IOException {
		// Setup terminal and screen layers
		Terminal terminal = new DefaultTerminalFactory().createTerminal();

		Snake snake = new Snake(terminal);

		Game game = new Game(snake, terminal);

		try {
			game.startGame();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// cleaning
		terminal.close();
	}

	public static void main(String[] args) throws IOException {
		runTerminal();
	}
}
