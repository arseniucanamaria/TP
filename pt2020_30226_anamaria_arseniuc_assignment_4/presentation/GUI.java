package presentation;

import business.IRestaurantProcessing;
import data.RestaurantSerialization;
import model.BaseProduct;
import model.CompositeProduct;
import model.MenuItem;
import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6688005117392309831L;
	private IRestaurantProcessing restaurant;
    private RestaurantSerialization restaurantSerialization;

    private JPanel administratorPanel;
    private JPanel waiterPanel = new JPanel();
    private JTextField itemIdTxtField;
    private JTextField itemNameTxtField;
    private JList<MenuItem> itemsJList;
    private JTextField itemPriceTxtField;
    private JTable existingItems;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem administratorItem;
    private JMenuItem waiterItem;

    private JTextField tableIdTxtField;
    private JList<MenuItem> listOrder;
    private JTable orderTable;
    private JTextField orderIdForBillTxtField;

    public GUI(IRestaurantProcessing restaurant, RestaurantSerialization restaurantSerialization) {
        this.restaurant = restaurant;
        this.restaurantSerialization = restaurantSerialization;
        this.initialize();
    }

    private void initialize() {
        menuBar = new JMenuBar();
        menu = new JMenu("Choose window");
        administratorItem = new JMenuItem("Administrator");
        waiterItem = new JMenuItem("Waiter");

        administratorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAdministratorPanel(); // se seteaza ca si fereastra cea a administratorului
            }
        });

        waiterItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setWaiterPanel(); // se seteaza ca si fereastra cea a ospatarului
            }
        });

        menu.add(administratorItem);
        menu.add(waiterItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        setBounds(600, 100, 1012, 431);
        setVisible(true);

        initializeAdministratorPanel(); // se initializeaza cele doua ferestre(administrator si waiter)
        initializeWaiterPanel();
        setAdministratorPanel(); // se seteaza ca fereastrra de inceput cea de administrator
    }

    /**
     *
     */
    private void initializeWaiterPanel() {
        waiterPanel = new JPanel();
        waiterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(waiterPanel);
        waiterPanel.setLayout(null);

        JLabel lblSelectItems = new JLabel("Select items");
        lblSelectItems.setBounds(28, 46, 80, 14);
        waiterPanel.add(lblSelectItems);

        listOrder = new JList<>();
        listOrder.setBounds(114, 46, 135, 213);
        waiterPanel.add(listOrder);

        Button generateOrderBtn = new Button("Generate order");
        generateOrderBtn.setBounds(114, 329, 135, 22);
        waiterPanel.add(generateOrderBtn);

        orderTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBounds(309, 46, 496, 258);
        waiterPanel.add(scrollPane);

        Label label = new Label("Orders");
        label.setBounds(309, 10, 62, 22);
        waiterPanel.add(label);

        Label label_1 = new Label("Table id");
        label_1.setBounds(25, 282, 62, 22);
        waiterPanel.add(label_1);

        tableIdTxtField = new JTextField();
        tableIdTxtField.setBounds(114, 284, 135, 20);
        waiterPanel.add(tableIdTxtField);
        tableIdTxtField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Order id");
        lblNewLabel.setBounds(309, 329, 72, 14);
        waiterPanel.add(lblNewLabel);

        orderIdForBillTxtField = new JTextField();
        orderIdForBillTxtField.setBounds(362, 329, 86, 20);
        waiterPanel.add(orderIdForBillTxtField);
        orderIdForBillTxtField.setColumns(10);

        JButton generateBillBtn = new JButton("Generate bill");
        generateBillBtn.setBounds(461, 329, 128, 23);
        waiterPanel.add(generateBillBtn);

        generateBillBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer orderId = Integer.parseInt(orderIdForBillTxtField.getText());
                    restaurant.generateBill(orderId); // se apeleaza metoda de generate bill a restaurant-ului
                    JOptionPane.showMessageDialog(null, "Bill generated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        generateOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<MenuItem> menuItemList = listOrder.getSelectedValuesList(); // se iau produsele selectate
                    if (menuItemList.size() == 0) {
                        throw new Exception("No item is selected");
                    }
                    Integer tableId = Integer.parseInt(tableIdTxtField.getText()); // id-ul mesei introduse

                    restaurant.createOrder(menuItemList, tableId); // se apeleaza metoda de generate order a restaurantului cu produsele selecte si id-ul mesei
                    setOrderJTable();
                }catch ( Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setWaiterPanel() {
        setItemList();
        setOrderJTable();
        setContentPane(this.waiterPanel);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    private void setOrderJTable() {
        Object[] columns = {"id", "date", "table number", "price"};
        Object[][] data = new Object[restaurant.getOrders().size()][4];

        int row = 0;
        for (Map.Entry<Order, List<MenuItem>> o: restaurant.getOrders().entrySet()) { // pentru fiecare entry din hashmap-ul in care sunt salvate order-ele facute
            data[row][0] = o.getKey().getOrderId();
            data[row][1] = o.getKey().getDate();
            data[row][2] = o.getKey().getTableNumber();
            data[row][3] = restaurant.computePrice(o.getKey().getOrderId()); // se calculeaza pret-ul order-ului
            row++;
        }

        this.orderTable.setModel(new DefaultTableModel(data, columns));
    }

    private void setAdministratorPanel() {
        setItemsJTable();
        setItemList();

        setContentPane(this.administratorPanel);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    private void initializeAdministratorPanel() {
        administratorPanel = new JPanel();
        administratorPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        administratorPanel.setLayout(null);

        itemIdTxtField = new JTextField();
        itemIdTxtField.setBounds(87, 82, 104, 22);
        administratorPanel.add(itemIdTxtField);

        Label label = new Label("id");
        label.setBounds(19, 82, 62, 22);
        administratorPanel.add(label);

        itemNameTxtField = new JTextField();
        itemNameTxtField.setBounds(87, 126, 104, 22);
        administratorPanel.add(itemNameTxtField);

        Label label_1 = new Label("name");
        label_1.setBounds(10, 126, 62, 22);
        administratorPanel.add(label_1);

        itemsJList = new JList<>();
        JScrollPane itemsScrollPane = new JScrollPane(itemsJList);
        itemsScrollPane.setBounds(231, 82, 165, 116);
        administratorPanel.add(itemsScrollPane);

        itemPriceTxtField = new JTextField();
        itemPriceTxtField.setBounds(87, 176, 104, 22);
        administratorPanel.add(itemPriceTxtField);

        Label label_2 = new Label("price");
        label_2.setBounds(10, 176, 62, 22);
        administratorPanel.add(label_2);

        JButton itemSaveBtn = new JButton("Save");
        itemSaveBtn.setBounds(87, 220, 154, 23);
        administratorPanel.add(itemSaveBtn);

        JButton itemSaveInformationBtn = new JButton("Save information");
        itemSaveInformationBtn.setBounds(87, 254, 154, 23);
        administratorPanel.add(itemSaveInformationBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(87, 288, 154, 23);
        administratorPanel.add(deleteBtn);

        Label label_3 = new Label("Existing items");
        label_3.setBounds(509, 37, 79, 22);
        administratorPanel.add(label_3);

        existingItems = new JTable();
        JScrollPane scrollPane = new JScrollPane(existingItems);
        scrollPane.setBounds(508, 82, 437, 282);
        administratorPanel.add(scrollPane);

        itemSaveInformationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(restaurant.getOrders());
                    restaurantSerialization.writeOrders(restaurant.getOrders(), Order.getAutoIncrement()); // se scriu informatiile aplicatiei in fisier(produse si order-e)
                    restaurantSerialization.writeMenuItems(restaurant.getItems(), MenuItem.getAutoIncrement());
                } catch (Exception ex) {
                    System.out.println("hello");
                    System.out.println(ex.getMessage());
                }
            }
        });

        existingItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = existingItems.getSelectedRow(); // se ia rand-ul selectat din tabela
                if (row > -1) {
                    MenuItem m = restaurant.getItem((Integer) existingItems.getValueAt(row, 0)); // se face get la produsul din restaurant dupa id
                    itemIdTxtField.setText(String.valueOf(m.getId()));
                    itemNameTxtField.setText(m.getName());
                    itemPriceTxtField.setText(String.valueOf(m.getPrice()));

                    if (m instanceof CompositeProduct) {
                        itemsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        List<MenuItem> itemList = restaurant.getItems();
                        List<Integer> select = new ArrayList<>();
                        for (MenuItem mx: ((CompositeProduct) m).getItems()) { // se seteaza subprodusele produsului ca fiind selectate in lista de produse
                            for (int x = 0 ; x < itemList.size(); x++) {
                                if (itemList.get(x).getId().equals(mx.getId())) {
                                    select.add(x);
                                }
                            }
                        }
                        int selectArray[] = select.stream().mapToInt(Integer::intValue).toArray();

                        itemsJList.setSelectedIndices(selectArray);
                    } else {
                        itemsJList.clearSelection();
                    }
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() { // action listener pentru butonul de delete product
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer id = Integer.parseInt(itemIdTxtField.getText());  // stergerea se face dupa un id
                    restaurant.deleteMenuItem(id);
                    setItemsJTable();
                    setItemList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        itemSaveBtn.addActionListener(new ActionListener() { // action listener pentru butonul de save product, itemIdTxtField este empty se face insert, altfel se face update
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer id = (itemIdTxtField.getText().isEmpty()) ? null : Integer.parseInt(itemIdTxtField.getText());
                    String name = itemNameTxtField.getText();
                    if (name.isEmpty()) {
                        throw new Exception("Name is required");
                    }
                    Double price = (itemPriceTxtField.getText().isEmpty())? null: Double.parseDouble(itemPriceTxtField.getText());
                    List<MenuItem> items = itemsJList.getSelectedValuesList();
                    if (items.size() == 0 && price == null) {
                        throw new Exception("price need to be set");
                    }

                    if (id == null) {
                        MenuItem m = (items.size() == 0) ? restaurant.createMenuItem(price, name) : restaurant.createMenuItem(name, items);
                    } else {
                        MenuItem m = (items.size() == 0) ? new BaseProduct() : new CompositeProduct();
                        if (items.size() == 0) {
                            ((BaseProduct)m).setPrice(price);
                            ((BaseProduct)m).setName(name);
                        } else {
                            ((CompositeProduct)m).setName(name);
                            ((CompositeProduct)m).setItems(items);
                        }
                        m.setId(id);
                        restaurant.updateMenuItem(m);
                    }
                    setItemsJTable();
                    setItemList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setItemsJTable() {
        Object[] columns = {"id", "name", "price"};
        Object[][] data = new Object[restaurant.getItems().size()][3];
        int row = 0;
        for (MenuItem m: restaurant.getItems()) {
            data[row][0] = m.getId();
            data[row][1] = m.getName();
            data[row][2] = m.getPrice();
            row++;
        }

        this.existingItems.setModel(new DefaultTableModel(data, columns));
    }

    private void setItemList() {
        MenuItem[] menuItems = restaurant.getItems().stream().toArray(MenuItem[]::new);
        this.itemsJList.setListData(menuItems);
        this.listOrder.setListData(menuItems);
    }
}
