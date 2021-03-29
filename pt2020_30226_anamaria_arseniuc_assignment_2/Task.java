package TS;

public class Task implements Comparable<Task> {
	private int id;
	private int arrivalTime;
	private int serviceTime;

	//constructor
	public Task(int id, int arrivalTime, int serviceTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}

	//gettere and settere
	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//sorteaza o lista crescator dupa arrivalTime
	public int compareTo(Task t) {
		return this.arrivalTime - t.arrivalTime;
	}

	//metoda pentru afisarea unui task
	public String toString() {
		return "(" + this.id + "," + this.arrivalTime + "," + this.serviceTime + ")";
	}

}
