package Simulation;

public class Main {

	public static void main(String[] args) {
		SimulationManager gen = new SimulationManager(args[0], args[1]);
		Thread t=new Thread(gen);
		t.start();
	}

}
