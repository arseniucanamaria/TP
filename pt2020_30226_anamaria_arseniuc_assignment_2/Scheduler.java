package Scheduler;

import TS.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import TS.Server;

public class Scheduler {
	private List<Server> servers;

	// constructor
	public Scheduler(int noOfServers) {
		servers = new ArrayList<Server>(noOfServers);
		// create server object
		// create thread with the object
		for (int i = 0; i < noOfServers; i++) { // creeaza pt fiecare coada un THREAD si il porneste
			Server s = new Server();
			servers.add(s);
			Thread t = new Thread(s);
			t.start();
		}
	}

	// metoda de adaugare unui task in coada cea mai potrivita,unde are de asteptat
	// cel mai putin
	public void dispatchTask(Task t) {
		AtomicInteger minTime = new AtomicInteger(Integer.MAX_VALUE);
		Server chosenServer = servers.get(0);
		for (Server i : servers) {
			if (i.getWaitingPeriod().get() < minTime.get()) {
				minTime = i.getWaitingPeriod();
				chosenServer = i;
			}
		}
		chosenServer.addTask(t);
	}

	// metoda care decrementeaza timpii de asteptare a taskurilor

	public void decrementareServere() {
		for (Server s : servers) {
			if (!s.getTasks().isEmpty()) {
				Task t = s.getTasks().peek();
				if (t.getServiceTime() == 1) {
					t.setServiceTime(t.getServiceTime() - 1);
					s.getTasks().remove(t);
				} else {
					t.setServiceTime(t.getServiceTime() - 1);
				}
			}

		}
	}

	// gettere
	public List<Server> getServers() {
		return servers;
	}
}
