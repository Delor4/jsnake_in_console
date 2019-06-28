package delor.jsnake.console;

import java.io.IOException;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Snake!!!
 *
 */
public class App {

//	public static void mainLoop(Terminal terminal, Snake snake) throws IOException {
//
//		snake.Show();
//
//		KeyStroke key = terminal.readInput();
//		int i = 0;
//		while (key.getKeyType() != KeyType.Escape) {
//			if (i % 5 == 0) {
//				snake.AddNewPart(Snake.randomColor());
//			}
//			switch (key.getKeyType()) {
//			case ArrowLeft:
//				snake.Move(Dir.WEST);
//				break;
//			case ArrowRight:
//				snake.Move(Dir.EAST);
//				break;
//			case ArrowUp:
//				snake.Move(Dir.NORTH);
//				break;
//			case ArrowDown:
//				snake.Move(Dir.SOUTH);
//				break;
//			default:
//				break;
//			}
//			terminal.clearScreen();
//			snake.Show();
//			if (snake.badEating()) {
//				printxy(terminal, 0, 0, "Eat self!");
//			}
//			;
//			terminal.flush();
//			key = terminal.readInput();
//			i++;
//		}
//	}
//
	

	public static void runTerminal() throws IOException {
		// Setup terminal and screen layers
		Terminal terminal = new DefaultTerminalFactory().createTerminal();

		Snake snake = new Snake(terminal);
		
		Game game = new Game(snake, terminal);
		
		game.startGame();

		//mainLoop(terminal, snake);

		// cleaning
		terminal.close();
	}

	public static void main(String[] args) throws IOException {
		runTerminal();
	}
}
