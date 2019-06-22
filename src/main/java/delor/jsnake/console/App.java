package delor.jsnake.console;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import delor.jsnake.core.Snake.Dir;

/**
 * Snake!!!
 *
 */
public class App {

	public static void mainLoop(Terminal terminal, Snake snake) throws IOException {

		snake.Show(terminal);

		KeyStroke key = terminal.readInput();
		int i = 0;
		while (key.getKeyType() != KeyType.Escape) {
			if (i % 5 == 0) {
				snake.AddNewPart(Snake.randomColor());
			}
			switch (key.getKeyType()) {
			case ArrowLeft:
				snake.Move(Dir.WEST);
				break;
			case ArrowRight:
				snake.Move(Dir.EAST);
				break;
			case ArrowUp:
				snake.Move(Dir.NORTH);
				break;
			case ArrowDown:
				snake.Move(Dir.SOUTH);
				break;
			default:
				break;
			}
			terminal.clearScreen();
			snake.Show(terminal);
			if(snake.badEating(terminal)) {
				printxy(terminal, 0, 0, "Eat self!");
			};
			terminal.flush();
			key = terminal.readInput();
			i++;
		}
	}

	public static void printxy(Terminal terminal, int x, int y, String s) throws IOException {
		terminal.setCursorPosition(x, y);
		for (char c : s.toCharArray()) {
			terminal.putCharacter(c);
		}
	}

	public static void main(String[] args) throws IOException {

		// Setup terminal and screen layers
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		
		// greetings
		printxy(terminal, 10, 5, "Hello snake!");

		TerminalSize screenSize = terminal.getTerminalSize();
		Snake snake = new Snake(screenSize.getColumns() / 2, screenSize.getRows() / 2);

		mainLoop(terminal, snake);

		// cleaning
		terminal.close();
	}
}
