package TS;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
	private BlockingQueue<Task> tasks;
	private AtomicInteger waitingPeriod;

	//constructor
	public Server() {
		this.tasks = new LinkedBlockingDeque<>();
		this.waitingPeriod = new AtomicInteger();
	}

	//adauga un task unui server
	public void addTask(Task newTask) {
		// add task to the queue
		this.tasks.add(newTask);
		this.waitingPeriod.getAndIncrement();
	}

	@Override
	public void run() {
		// take next task from queue
		Task t = null;

		// stop the thread for a time equal with the task`s processing time
		// decrement the waitingperiod
		try {
			if (this.tasks.isEmpty()) {
				Thread.sleep(1);// astept minim o secunda pana vine primul client
			} else {

				Thread.sleep(t.getServiceTime() * 1);
				t = this.tasks.take();
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	//gettere si settere
	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

	public void setTasks(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}

	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}//end gettere si settere

	//metoda de afisare a unei liste de task-uri
	public String toString() {
		String s = "";
		for (Task t : tasks)
			s += t.toString() + ";";
		return s;
	}
}
