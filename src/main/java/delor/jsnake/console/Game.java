package delor.jsnake.console;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

		@Override
		public int getID() {
			return 2;
		}

		KeyStroke data;

		@Override
		public KeyStroke getData() {
			return data;
		}
	}

	public Game(Snake s, Terminal terminal) {
		super(s);
		this.terminal = terminal;
	}

	@Override
	public void startGame() {
		super.startGame();

		// greetings
		try {
			printxy(terminal, 10, 5, "Hello snake!");
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

	private void mainLoop() throws InterruptedException {
		while (true) {
			Msg m = msg.take();
			if (!DispatchMsg(m))
				break;
		}
	}

	protected Boolean DispatchMsg(Msg m) {
		if (!super.DispatchMsg(m))
			return false;

		if (m.getID() == 2) {
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
				System.out.println("Task performed on: " + new Date() + "n" + "Thread's name: "
						+ Thread.currentThread().getName());
			}
		};
		keyTimer = new Timer("KeystrokeTimer");

		long delay = 100L;

		keyTimer.schedule(task, delay, delay);
	}

	delor.jsnake.console.Snake getSnake() {
		return (delor.jsnake.console.Snake) snake;
	}

	@Override
	public void PaintGameField() {
		try {
			terminal.clearScreen();
			getSnake().Show();
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Painting game field.");
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

	protected void showBadEating() {
		try {
			printxy(terminal, 0, 0, "Oops!");
			terminal.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void printxy(Terminal terminal, int x, int y, String s) throws IOException {
		terminal.setCursorPosition(x, y);
		for (char c : s.toCharArray()) {
			terminal.putCharacter(c);
		}
	}

}
