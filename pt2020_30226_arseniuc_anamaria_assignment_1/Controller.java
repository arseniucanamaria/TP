package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Polinom.Polinom;
import PolinomFloat.PolinomF;

public class Controller {
	//Controller-ul trebuie sa interactioneze cu Modelul si View-ul
	private View view;
	private Model model;

	//=======================================================Constructor
	public Controller(View theView, Model theModel) {
		this.view = theView;
		this.model = theModel;

		this.view.addAdunareListener(new AdunareListener());
		this.view.addScadereListener(new ScadereListener());
		this.view.addInmultireListener(new InmultireListener());
		this.view.addDerivare1Listener(new Derivare1Listener());
		this.view.addDerivare2Listener(new Derivare2Listener());
		this.view.addIntegrareListener(new IntegrareListener());
		this.view.addIntegrare2Listener(new Integrare2Listener());
		this.view.addResetListener(new ResetListener());
	}
    
	//=====================================Inner classes pentru operatii=======================
	
	//-------------------------------------------------Adunare
	class AdunareListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str1 = "";
			String str2 = "";
			try {
				str1 = view.getUserPolinom1();
				str2 = view.getUserPolinom2();
				Polinom polinom1 = model.toInteger(str1);
				Polinom polinom2 = model.toInteger(str2);
				Polinom rezultat = model.adunare(polinom1, polinom2);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str1 + "'");
			}
		}
	}

	//-------------------------------------------------Scadere
	class ScadereListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str1 = "";
			String str2 = "";
			try {
				str1 = view.getUserPolinom1();
				str2 = view.getUserPolinom2();
				Polinom polinom1 = model.toInteger(str1);
				Polinom polinom2 = model.toInteger(str2);
				Polinom rezultat = model.scadere(polinom1, polinom2);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str1 + "'");
			}

		}
	}

	class InmultireListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str1 = "";
			String str2 = "";
			try {
				str1 = view.getUserPolinom1();
				str2 = view.getUserPolinom2();
				Polinom polinom1 = model.toInteger(str1);
				Polinom polinom2 = model.toInteger(str2);
				Polinom rezultat = model.inmultire(polinom1, polinom2);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str1 + "'");
			}

		}
	}

	//-------------------------------------------------Derivare polinom1
	class Derivare1Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str1 = "";
			try {
				str1 = view.getUserPolinom1();
				Polinom polinom1 = model.toInteger(str1);
				// Polinom polinom2 = model.toInteger(str2);
				Polinom rezultat = model.derivare(polinom1);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str1 + "'");
			}

		}
	}
	//-----------------------------------------------------Derivare polinom2
	class Derivare2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str2 = "";
			try {
				str2 = view.getUserPolinom2();
				Polinom polinom2 = model.toInteger(str2);
				Polinom rezultat = model.derivare(polinom2);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str2 + "'");
			}

		}
	}

	//---------------------------------------------------Integrare Polinom1
	class IntegrareListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str1 = "";
			try {
				str1 = view.getUserPolinom1();
				Polinom polinom1 = model.toInteger(str1);
				PolinomF rezultat = model.integrare(polinom1);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str1 + "'");
			}
		}
	}
	//====================================================integrare Polinom2
	class Integrare2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str2 = "";
			try {
				str2 = view.getUserPolinom2();
				Polinom polinom1 = model.toInteger(str2);
				PolinomF rezultat = model.integrare(polinom1);
				view.setRezultat(rezultat.toString(rezultat));

			} catch (NumberFormatException nfex) {
				view.showMessage("Bad input: '" + str2 + "'");
			}
		}
	}

	/** Se reseteaza interfata (se sterg informatiile de la polinoame) */
	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.reset();
		}
	}
	
	
}
