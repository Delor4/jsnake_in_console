package delor.jsnake.console;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import delor.jsnake.core.Snake;
import delor.jsnake.core.Snake.Dir;

public class Game extends delor.jsnake.core.Game {

	Terminal terminal;

	protected class MsgKeystroke implements Msg {
		MsgKeystroke(KeyStroke k) {
			data = k;
		}
		KeyStroke data;

		@Override
		public KeyStroke getData() {
			return data;
		}
	}

	public Game(Snake s, Terminal terminal) throws IOException {
		super(s, terminal.getTerminalSize().getColumns(), terminal.getTerminalSize().getRows());
		this.terminal = terminal;
	}

	@Override
	public void startGame() throws InterruptedException {
		super.startGame();

		// greetings
		try {
			printxy(terminal, 10, 5, "Hello snake!");
			terminal.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			keystrokeTimer();
			mainLoop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		closeGame();
	}

	protected Apple createApple(int x, int y) {
		return new Apple(x, y, delor.jsnake.console.Snake.randomColor());
	}

	private void mainLoop() throws InterruptedException {
		while (true) {
			Msg m = msg.take();
			if (!DispatchMsg(m))
				break;
		}
	}

	protected Boolean DispatchMsg(Msg m) throws InterruptedException {
		if (!super.DispatchMsg(m))
			return false;

		if (m.getClass().getSimpleName().equals("MsgKeystroke")) {
			switch (((MsgKeystroke) m).getData().getKeyType()) {
			case ArrowLeft:
				snake.setDir(Dir.WEST);
				break;
			case ArrowRight:
				snake.setDir(Dir.EAST);
				break;
			case ArrowUp:
				snake.setDir(Dir.NORTH);
				break;
			case ArrowDown:
				snake.setDir(Dir.SOUTH);
				break;
			case Escape:
				return false;
			case Character: {
				switch (((MsgKeystroke) m).getData().getCharacter()) {
				case 'q':
					return false;
				}
			}
			default:
				break;
			}
		}
		return true;
	}

	Timer keyTimer;

	public void keystrokeTimer() {
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					KeyStroke key = terminal.pollInput();
					if (key != null) {
						addMsg(new MsgKeystroke(key));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		keyTimer = new Timer("KeystrokeTimer");

		long delay = 100L;

		keyTimer.schedule(task, delay, delay);
	}

	delor.jsnake.console.Snake getSnake() {
		return (delor.jsnake.console.Snake) snake;
	}

	private void showApple() {
		if (apple != null) {
			try {
				terminal.setCursorPosition(apple.x, apple.y);
				terminal.setForegroundColor(((delor.jsnake.console.Apple) apple).fgColor);
				terminal.putCharacter(((delor.jsnake.console.Apple) apple).apperance);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void showPoints() {
		try {
			terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
			printxy(terminal, 20, 0, "Points: " + getPoints());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void PaintGameField() {
		try {
			try {
				snakeAccessSemaphore.acquire();
				terminal.clearScreen();
				getSnake().Show();
				snakeAccessSemaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			showApple();
			showPoints();
			terminal.setCursorPosition(terminal.getTerminalSize().getColumns() - 1,
					terminal.getTerminalSize().getRows() - 1);
			terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ReDrawGameField() {
		PaintGameField();
	}

	@Override
	public void closeGame() {
		super.closeGame();
		keyTimer.cancel();
	}

	protected void showBadEating() throws InterruptedException {
		snakeAccessSemaphore.acquire();
		try {
			printxy(terminal, 0, 0, "Oops!");
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
		snakeAccessSemaphore.release();
	}

	public static void printxy(Terminal terminal, int x, int y, String s) throws IOException {
		terminal.setCursorPosition(x, y);
		for (char c : s.toCharArray()) {
			terminal.putCharacter(c);
		}
	}

}
