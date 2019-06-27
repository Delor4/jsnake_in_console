package delor.jsnake.console;

import java.io.IOException;

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

	Thread thread;

	public void keystrokeTimer() throws InterruptedException {
		Runnable task = () -> {

			while (true) {
				try {
					KeyStroke key = terminal.pollInput();
					if (key == null) {
						Thread.sleep(100);
					} else {
						msg.put(new MsgKeystroke(key));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					break;
				}
			}

		};
		thread = new Thread(task);
		thread.start();
	}

	delor.jsnake.console.Snake getSnake() {
		return (delor.jsnake.console.Snake) snake;
	}

	@Override
	public void PaintGameField() {
		try {
			terminal.clearScreen();
			getSnake().Show(terminal);
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
		thread.interrupt();
	}

}
