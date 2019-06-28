package delor.jsnake.core;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Game {
	protected Snake snake;
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

	public Game(Snake s) {
		snake = s;
	}

	public abstract void PaintGameField();

	public abstract void ReDrawGameField();

	public void startGame() {
		StartSnakeTimer();
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
				System.out.println("Task performed on: " + new Date() + "n" + "Thread's name: "
						+ Thread.currentThread().getName());
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
