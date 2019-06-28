package delor.jsnake.core;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import delor.jsnake.console.Apple;

public abstract class Game {
	protected final Snake snake;
	protected Apple apple;
	protected int tick = 0;

	protected BlockingQueue<Msg> msg = new LinkedBlockingQueue<>();

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

	protected Boolean DispatchMsg(Msg m) throws InterruptedException {
		switch (m.getID()) {
		case 1:
			ReDrawGameField();
			break;
		case 10:
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

	protected void makeNewApple() {
		//I need to protect snake.contains from access by concurrent threads
		int x = snake.column;
		int y = snake.row;
		while (snake.contains(x, y)) {
			x = random.nextInt(width);
			y = random.nextInt(height);
		}
		apple = createApple(x, y);
	}

	protected Apple createApple(int x, int y) {
		return new Apple(x, y);
	}

	public abstract void PaintGameField();

	public abstract void ReDrawGameField();

	public void startGame() {
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
				snake.Move();
				addMsg(new MsgRefresh());
				if (snake.badEating()) {
					addMsg(new MsgEatingBad());
				}
				tick++;
				// System.out.println("Task performed on: " + new Date() + "n" + "Thread's name:
				// "
				// + Thread.currentThread().getName());
			}
		};
		snakeTimer = new Timer("SnakeTimer");

		long delay = 1000L;

		snakeTimer.schedule(task, delay, delay);

	}

	public void closeGame() {
		snakeTimer.cancel();
	}

}
