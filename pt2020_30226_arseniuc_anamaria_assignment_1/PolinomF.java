package PolinomFloat;

import java.util.ArrayList;

public class PolinomF {
	private ArrayList<MonomF> polinom;

	public PolinomF() {
		polinom = new ArrayList<MonomF>();
	}

	public ArrayList<MonomF> getPolinomF() {
		return polinom;
	}

	public void setPolinomF(ArrayList<MonomF> polinom) {
		this.polinom = polinom;
	}

	public void add(MonomF m) {
		polinom.add(m);
	}

	public MonomF get(int i) {
		polinom = new ArrayList<MonomF>();
		return polinom.get(i);

	}

	public String toString(PolinomF p) {
		String rez = "";
		for (MonomF m : p.getPolinomF()) {
			rez = rez + m.toString();
		}
		return rez;
	}
}
