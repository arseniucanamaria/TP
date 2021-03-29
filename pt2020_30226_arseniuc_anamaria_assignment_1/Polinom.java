package Polinom;

import java.util.ArrayList;

public class Polinom {
	private ArrayList<Monom> polinom;

	public Polinom() {
		polinom = new ArrayList<Monom>();
	}

	public ArrayList<Monom> getPolinom() {
		return polinom;
	}

	public void setPolinom(ArrayList<Monom> polinom) {
		this.polinom = polinom;
	}

	public void add(Monom m) {
		polinom.add(m);
	}

	public Monom get(int i) {
		polinom = new ArrayList<Monom>();
		return polinom.get(i);

	}

	public String toString(Polinom p) {
		String rez = "";
		for (Monom m : p.getPolinom()) {
			rez = rez + m.toString();
		}
		return rez;
	}
}
