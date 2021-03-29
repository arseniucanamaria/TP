package Polinom;

public class Monom implements Comparable<Monom> {
	private int coeficient;
	private int putere;

	public Monom(int coeficient, int putere) {
		this.coeficient = coeficient;
		this.putere = putere;
	}

	public int getCoeficient() {
		return coeficient;
	}

	public void setCoeficient(int coeficient) {
		this.coeficient = coeficient;
	}

	public int getPutere() {
		return putere;
	}

	public void setPutere(int putere) {
		this.putere = putere;
	}

	public String toString() {
		if (coeficient == 0 && putere == 0 )
			return "";
        
		if(coeficient == 0)
			return "";
		
		if (putere == 0 && coeficient > 0)
			return "+" + coeficient;

		if (putere == 0 && coeficient < 0)
			return coeficient + "";

		if (coeficient == 1 && putere == 1)
			return "X";

		if (putere > 0 && coeficient > 0)
			return "+" + coeficient + "X^" + putere;
		else
			return coeficient + "X^" + putere;
	}

	public int compareTo(Monom o) {
		return o.putere - this.putere;
	}
}