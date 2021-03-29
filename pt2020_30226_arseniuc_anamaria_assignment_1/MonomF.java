package PolinomFloat;

public class MonomF implements Comparable<MonomF> {
	private double coeficient;
	private int putere;

	public MonomF(double coeficient, int putere) {
			this.coeficient = coeficient;
			this.putere = putere;
		}

	public double getCoeficientF() {
		return coeficient;
	}

	public void setCoeficientF(double coeficient) {
		this.coeficient = coeficient;
	}

	public int getPutere() {
		return putere;
	}

	public void setPutere(int putere) {
		this.putere = putere;
	}

	public String toString() {
		if (coeficient == 0 && putere == 0)
			return "";

		if (coeficient == 0)
			return "";

		if (putere == 0 && coeficient > 0)
			return "+" + coeficient;

		if (putere == 0 && coeficient < 0)
			return coeficient + "";

		if (coeficient == 1 && putere == 1)
			return "X";

		if (coeficient == 1)
			return "X^" + putere;

		if (putere > 0 && coeficient > 0)
			return "+" + coeficient + "X^" + putere;
		else
			return coeficient + "X^" + putere;
	}

	public int compareTo(MonomF o) {
		return o.putere - this.putere;
	}
}
