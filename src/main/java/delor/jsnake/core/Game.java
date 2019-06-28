package delor.jsnake.core;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public abstract class Game {
	protected final Snake snake;
	protected Apple apple;
	protected int tick = 0;

	protected BlockingQueue<Msg> msg = new LinkedBlockingQueue<>();
	protected Semaphore snakeAccessSemaphore = new Semaphore(1);

	public interface Msg {
		int getID();

		Object getData();
	}

	protected class MsgRefresh implements Msg {
		@Override
		public int getID() {
			return 1;
		}

		@Override
		public Object getData() {
			return null;
		}
	}

	protected class MsgEatingBad implements Msg {
		@Override
		public int getID() {
			return 10;
		}

		@Override
		public Object getData() {
			return null;
		}
	}

	protected class MsgEatingApple implements Msg {
		public MsgEatingApple(Apple apple) {
			this.apple = apple;
		}

		Apple apple;

		@Override
		public int getID() {
			return 3;
		}

		@Override
		public Apple getData() {
			return apple;
		}
	}

	protected Boolean DispatchMsg(Msg m) throws InterruptedException {
		switch (m.getID()) {
		case 1:// redraw
			ReDrawGameField();
			break;
		case 3:// eating apple
			eatApple((Apple) m.getData());
			break;
		case 10:// Oops
			showBadEating();
			return false;
		}
		return true;
	}

	protected void showBadEating() throws InterruptedException {
	}

	int width;
	int height;

	public Game(Snake s, int x, int y) {
		snake = s;
		width = x;
		height = y;
	}

	private static final Random random = new Random();

	protected void eatApple(delor.jsnake.core.Apple a) throws InterruptedException {
		snakeAccessSemaphore.acquire();
		snake.AddNewPart(a);
		snakeAccessSemaphore.release();
		apple = null;
		makeNewApple();
		addMsg(new MsgRefresh());
	}

	protected void makeNewApple() throws InterruptedException {
		snakeAccessSemaphore.acquire();
		int x = snake.column;
		int y = snake.row;
		while (snake.contains(x, y)) {
			x = random.nextInt(width);
			y = random.nextInt(height);
		}
		apple = createApple(x, y);
		snakeAccessSemaphore.release();
	}

	protected Apple createApple(int x, int y) {
		return new Apple(x, y);
	}

	public abstract void PaintGameField();

	public abstract void ReDrawGameField();

	public void startGame() throws InterruptedException {
		StartSnakeTimer();
		makeNewApple();
	}

	protected void addMsg(Msg m) {
		try {
			msg.put(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getTick() {
		return tick;
	}

	Timer snakeTimer;

	public void StartSnakeTimer() {
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					snakeAccessSemaphore.acquire();
					snake.Move();
					addMsg(new MsgRefresh());
					if (snake.badEating()) {
						addMsg(new MsgEatingBad());
					} else if (snake.appleEating(apple.x, apple.y)) {
						addMsg(new MsgEatingApple(apple));
					}
					tick++;
					snakeAccessSemaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		snakeTimer = new Timer("SnakeTimer");

		long delay = 300L;

		snakeTimer.schedule(task, delay, delay);

	}

	public void closeGame() {
		snakeTimer.cancel();
	}

}
