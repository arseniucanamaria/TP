package MVC;

import java.util.Collections;

import Polinom.Monom;
import Polinom.Polinom;
import PolinomFloat.MonomF;
import PolinomFloat.PolinomF;

public class Model {
	private Polinom polinom = new Polinom();

	
	// =========================== OPERATII ====================================//

	// --------------------------------------------------------------------Adunare
	public static Polinom adunare(Polinom polinom1, Polinom polinom2) {
		Monom rezMonom;
		int gasit = 0;
		Polinom rezultatPolinom = new Polinom();
		for (Monom p1 : polinom1.getPolinom()) {
			gasit = 0;
			for (Monom p2 : polinom2.getPolinom()) {
				if (p1.getPutere() == p2.getPutere()) {
					rezMonom = new Monom(p1.getCoeficient() + p2.getCoeficient(), p1.getPutere());
					rezultatPolinom.add(rezMonom);
					gasit = 1;
				}
			}
			if (gasit == 0)
				rezultatPolinom.add(p1);
		}
		for (Monom p2 : polinom2.getPolinom()) {
			gasit = 0;
			for (Monom p1 : polinom1.getPolinom()) {
				if (p2.getPutere() == p1.getPutere()) {
					gasit = 1;
				}
			}
			if (gasit == 0)
				rezultatPolinom.add(p2);
		}
		Collections.sort(rezultatPolinom.getPolinom());
		return rezultatPolinom;
	}

	// -------------------------------------------------------------------Scadere
	public static Polinom scadere(Polinom polinom1, Polinom polinom2) {
		Monom rezMonom;
		int gasit = 0;
		Polinom rezultatPolinom = new Polinom();

		for (Monom p1 : polinom1.getPolinom()) {
			gasit = 0;
			for (Monom p2 : polinom2.getPolinom()) {
				if (p1.getPutere() == p2.getPutere()) {
					rezMonom = new Monom(p1.getCoeficient() - p2.getCoeficient(), p1.getPutere());
					rezultatPolinom.add(rezMonom);
					gasit = 1;
				}
			}
			if (gasit == 0)
				rezultatPolinom.add(p1);
		}
		for (Monom p2 : polinom2.getPolinom()) {
			gasit = 0;
			for (Monom p1 : polinom1.getPolinom()) {
				if (p2.getPutere() == p1.getPutere()) {
					gasit = 1;
				}
			}
			if (gasit == 0) {
				p2.setCoeficient((-1) * p2.getCoeficient());
				rezultatPolinom.add(p2);
			}
		}
		Collections.sort(rezultatPolinom.getPolinom());
		return rezultatPolinom;
	}

	// ---------------------------------------------------------------------Inmultire
	public static Polinom inmultire(Polinom polinom1, Polinom polinom2) {
		Polinom rezultatAux = new Polinom();
		Polinom rezultatPol = new Polinom();
		Monom rezMonom, rezMonom1;

		for (Monom m1 : polinom1.getPolinom()) {
			for (Monom m2 : polinom2.getPolinom()) {
				rezMonom = new Monom(m1.getCoeficient() * m2.getCoeficient(), m1.getPutere() + m2.getPutere());
				rezultatAux.add(rezMonom);
			}
		}
		Collections.sort(rezultatAux.getPolinom());

		int i = 0;
		while (i < rezultatAux.getPolinom().size() - 1) {
			int coef = rezultatAux.getPolinom().get(i).getCoeficient();
			int j = i + 1;
			while (rezultatAux.getPolinom().get(i).getPutere() == rezultatAux.getPolinom().get(j).getPutere()) {
				coef += rezultatAux.getPolinom().get(j).getCoeficient();
				j++;
			}
			rezMonom1 = new Monom(coef, rezultatAux.getPolinom().get(i).getPutere());
			rezultatPol.add(rezMonom1);
			i = j;
		}
		rezMonom1 = new Monom(rezultatAux.getPolinom().get(i).getCoeficient(),
				rezultatAux.getPolinom().get(i).getPutere());
		rezultatPol.add(rezMonom1);

		return rezultatPol;
	}

	// ------------------------------------------------------------Derivare
	public static Polinom derivare(Polinom p1) {
		for (Monom m : p1.getPolinom()) {
			m.setCoeficient(m.getCoeficient() * m.getPutere());
			m.setPutere(m.getPutere() - 1);
		}
		return p1;
	}

	// -------------------------------------------------------- Integrare
	public static PolinomF integrare(Polinom p1) {
		PolinomF rez = new PolinomF();
		for (Monom m : p1.getPolinom()) {
			double c=(double)m.getCoeficient() / (m.getPutere() + 1);
			c=(double)((int)(c*100))/100;
			MonomF m1 = new MonomF(c, m.getPutere() + 1);
			rez.add(m1);
		}
		return rez;
	}

	// =================================================================getRezultat

	/**
	 * Returneaza polinomul (rezultat) care trebuie de afisat in interfata (ca si
	 * String)
	 */
	public String getRezultat() {
		return polinom.toString(polinom);
	}

	// =================================================================toInteger

	/**
	 * Functia care imi ia monoamele (coeficientii+puterile) din polinomul scris de
	 * utilizator
	 */
	public Polinom toInteger(String polinom) {
		int i = 0;
		Polinom p = new Polinom();

		String[] coef = polinom.split("x\\^\\d+"); // coeficientii polinomului (cu tot cu semn)

		// scoaterea puterilor din polinom + construirea monomelor
		String[] terms = polinom.split("(-|\\+)"); // impartirea polinomului dupa " +/- "
		for (String term : terms) {
			String[] exp = term.split("\\^");
			if (exp.length > 1) {
				p.add(new Monom(Integer.parseInt(coef[i++]), Integer.parseInt(exp[1])));
			} else {
				p.add(new Monom(Integer.parseInt(coef[i]), 0));
			}
		}
		return p;
	}

}
