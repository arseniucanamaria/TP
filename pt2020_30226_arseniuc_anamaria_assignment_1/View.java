package MVC;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class View extends JFrame {

	private Model model;

	JTextField tf1 = new JTextField(40);
	JTextField tf2 = new JTextField(40);
	JTextField tfRez = new JTextField(40);
	private JButton adunare = new JButton("+");
	private JButton scadere = new JButton("-");
	private JButton inmultire = new JButton("*");
	private JButton impartire = new JButton(":");
	private JButton derivare = new JButton("(P1)'");
	private JButton derivare2 = new JButton("(P2)'");
	private JButton integrare = new JButton("S(P1)");
	private JButton integrare2 = new JButton("S(P2)");
    JButton reset = new JButton("Reset");

    //==============================constructor
	public View(Model model) {
		this.model = model;

		//=======================================creare interfata
	
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Polinom 1: "));
		p1.add(tf1);
		p1.setLayout(new FlowLayout());

		JPanel p2 = new JPanel();
		p2.add(new JLabel("Polinom 2: "));
		p2.add(tf2);
		p2.setLayout(new FlowLayout());

		JPanel p3 = new JPanel();
		p3.add(adunare);
		p3.add(scadere);
		p3.add(inmultire);
		p3.add(impartire);
		p3.add(derivare);
		p3.add(derivare2);
		p3.add(integrare);
		p3.add(integrare2);
		p3.add(reset);

		JPanel p4 = new JPanel();
		p4.add(new JLabel("Rezultat: "));
		p4.add(tfRez);
		p4.setLayout(new FlowLayout());

		JPanel p = new JPanel();
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		this.setTitle("Calculator Polinoame");
		this.setContentPane(p);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	//------------------->Listenere pentru butoane
	public void addAdunareListener(ActionListener a) {
		adunare.addActionListener(a);
	}

	public void addScadereListener(ActionListener a) {
		scadere.addActionListener(a);
	}

	public void addInmultireListener(ActionListener a) {
		inmultire.addActionListener(a);
	}

	public void addImpartireListener(ActionListener a) {
		impartire.addActionListener(a);
	}

	public void addDerivare1Listener(ActionListener a) {
		derivare.addActionListener(a);
	}
	
	public void addDerivare2Listener(ActionListener a) {
		derivare2.addActionListener(a);
	}

	public void addIntegrareListener(ActionListener a) {
		integrare.addActionListener(a);
	}
	
	public void addIntegrare2Listener(ActionListener a) {
		integrare2.addActionListener(a);
	}

	public void addResetListener(ActionListener a) {
		reset.addActionListener(a);
	}
	//-------> sfarsit Listenere pentru butoane

	
	/** Mesaj de eroare in caz ca nu se introduc bine datele */
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/** Primul polinom dat de utilizator*/
	public String getUserPolinom1() {
		return tf1.getText();
	}

	/** Al doilea polinom dat de utilizator*/
	public String getUserPolinom2() {
		return tf2.getText();
	}

	/** Seteaza rezultatul care trebuie afisat in interfata*/
	public void setRezultat(String polinom) {
		tfRez.setText(polinom);
	}
	
	public void reset() {
		tf1.setText("");
		tf2.setText("");
		tfRez.setText("");
	}

}
