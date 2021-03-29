package Simulation;

import TS.*;
import Scheduler.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimulationManager implements Runnable {
	private int numberOfClients = -1;
	private int numberOfServers = -1;
	private int maxSimulationTime = -1;
	private int maxServiceTime = -1;
	private int minServiceTime = -1;
	private int maxArrivalTime = -1;
	private int minArrivalTime = -1;
	private double averageWaitingTime = -1;
	
	private String inputFile;
	private String outputFile;

	private Scheduler scheduler;
	private List<Task> generatedTasks;

	
	//constructor care are ca parametri denumirea fisierelor "in" si "out"
	public SimulationManager(String pathIn, String pathOut) {
		this.inputFile = pathIn;
		this.outputFile = pathOut;
		this.readFromFile();
		scheduler = new Scheduler(numberOfServers);
		generatedTasks = generateNRandomTasks(numberOfClients);

	}

	//metoda de generare Random a "N" task-uri
	public List<Task> generateNRandomTasks(int noClients) {
		List<Task> tasks = new ArrayList<Task>(noClients);
		this.readFromFile();
		for (int i = 1; i <= noClients; i++) {
			try {
				int at = getRandomNumberInRange(minArrivalTime, maxArrivalTime);
				int st = getRandomNumberInRange(minServiceTime, maxServiceTime);
				Task t = new Task(i, at, st);
				tasks.add(t);
			} catch (Exception E) {
			}

		}
		Collections.sort(tasks);
		return tasks;
	}

	//generare Random a unui numar cuprins intre "min" si "max" date
	public int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public void run() {
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(this.outputFile));
		
			int currentTime = 0;
			int nr = 0;
			boolean stop = false;

			int waitingTimeTotal = 0;
			for (Task t : generatedTasks) {
				waitingTimeTotal += t.getServiceTime();
			}

			List<Task> deleteTask = new ArrayList<Task>();
			while (currentTime < maxSimulationTime && stop == false) {

				if (generatedTasks.isEmpty() == true) {
					if (nr == numberOfServers) {
						stop = true;
						break;
					}
				}

				scheduler.decrementareServere();
				for (Task t : generatedTasks) {
					if (currentTime == t.getArrivalTime()) {
						scheduler.dispatchTask(t);
						deleteTask.add(t);
					}
				}

				generatedTasks.removeAll(deleteTask);
				f.write("Time " + currentTime);
				f.newLine();
				f.write("Waiting Clients: ");

				for (Task i : generatedTasks) {
					f.write(i.toString() + ";");
				}

				nr = 0;
				f.newLine();
				for (Server s : scheduler.getServers()) {
					f.write("Queue " + (scheduler.getServers().indexOf(s) + 1) + ":");

					if (s.getTasks().isEmpty()) {
						f.write(" closed");
						nr++;
						f.newLine();
					} else {
						f.write(s.toString());
						f.newLine();
					}
				}
				f.newLine();
				currentTime++;
			}

			averageWaitingTime = (double) waitingTimeTotal / numberOfClients;
			f.write("Average waiting time: " + averageWaitingTime);

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//metoda de citire a datelor din fisierul de intrare si puse in variabilele clasei actuale
	private void readFromFile() {
		try {
			File in = new File(this.inputFile);
			Scanner srcReader = new Scanner(in);

			this.numberOfClients = Integer.parseInt(srcReader.nextLine());
			this.numberOfServers = Integer.parseInt(srcReader.nextLine());
			this.maxSimulationTime = Integer.parseInt(srcReader.nextLine());
			String[] rez1 = srcReader.nextLine().split(",");
			this.minArrivalTime = Integer.parseInt(rez1[0]);
			this.maxArrivalTime = Integer.parseInt(rez1[1]);
			String[] rez2 = srcReader.nextLine().split(",");
			this.minServiceTime = Integer.parseInt(rez2[0]);
			this.maxServiceTime = Integer.parseInt(rez2[1]);

			srcReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Sorry! File doesn't exist!");
			ex.printStackTrace();
		}

	}

}
