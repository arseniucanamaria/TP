package presentation;

import javafx.util.Pair;
import model.MenuItem;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChefGUI extends JFrame implements Observer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8610191119979710853L;
	private JPanel panel = new JPanel();

    public ChefGUI() {
        setBounds(100, 100, 500, 500);
        setVisible(true);
        panel.setLayout(new GridLayout(0, 1));
        this.setTitle("Chef gui");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panel);
    }

    /**
     * Metoda apelata de catre observable atunci cand se creeaza un order in aplicatie
     * Se adauga un noul label cu id-ul order-ului si produsele comandate
     */
    @Override
    public void update(Observable o, Object arg) {
        Pair<Order, List<MenuItem>> orderListEntry = (Pair<Order, List<MenuItem>>) arg;
        JPanel panelAux = new JPanel();
        panelAux.setLayout(new FlowLayout());
        panelAux.add(new JLabel("Order id" + orderListEntry.getKey().getOrderId()));

        for (MenuItem m: orderListEntry.getValue()) {
            panelAux.add(new JLabel(m.getName()));
        }

        panel.add(panelAux);
        this.invalidate();
        this.validate();
        this.repaint();
    }
}
