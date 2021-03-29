package business;

import javafx.util.Pair;
import model.BaseProduct;
import model.CompositeProduct;
import model.MenuItem;
import model.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Restaurant extends Observable implements IRestaurantProcessing {

    private Map<Order, List<MenuItem>> orders;
    private List<MenuItem> items;

    public Restaurant() {
        items = new ArrayList<>();
        orders = new HashMap<>();
    }

    /**
     * Creeaza un base product dupa un pret si un nume, obiect adaugat in lista de item-uri ale restaurantului
     */
    @Override
    public MenuItem createMenuItem(Double price, String name) throws Exception {
        assert price >= 0 && !name.isEmpty(); // preconditie

        for (MenuItem item: this.items) {
            if (item.getName().equals(name)) {
                throw new Exception("Name need to be unique");
            }
        }

        MenuItem menuItem = new BaseProduct(price, name);
        items.add(menuItem);
        return menuItem;
    }

    /**
     * Creeaza un composite product, avand un nume si o lista de item-uri 
     */
    @Override
    public MenuItem createMenuItem(String name, List<MenuItem> menuItems) throws Exception {
        assert !name.isEmpty() && !menuItems.isEmpty();

        for (MenuItem item: this.items) {
            if (item.getName().equals(name)) {
                throw new Exception("Name need to be unique");
            }
        }

        MenuItem menuItem = new CompositeProduct(name);
        ((CompositeProduct) menuItem).setItems(menuItems);
        items.add(menuItem);
        return menuItem;
    }

    /**
     * Se sterge un item din lista de menuitem a restaurantului dupa id
     */
    @Override
    public void deleteMenuItem(Integer id) {
        for (MenuItem m: this.items) { // se cauta item-ul cu id-ul primit
            if (m.getId().equals(id)) {
                this.items.remove(m);
                break;
            }
        }
    }

    /**
     * Se face replace la menuitem din lista meniul restaurantului dupa id
     */
    @Override
    public void updateMenuItem(MenuItem menuItem) {
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId().equals(menuItem.getId())) {
                items.set(index, menuItem); // replace
                break;
            }
        }
    }

    /**
     * se creaza o noua comanda dupa o lista de iteme si id-ul mesei, dupa care se face notify la observeri(chefuserinterface)
     */
    @Override
    public Order createOrder(List<MenuItem> menuItems, Integer tableId) {
        assert menuItems.size() > 0  && tableId > 0;
        Order order = new Order(tableId);
        orders.put(order, menuItems);
        setChanged();
        notifyObservers(new Pair<>(order, menuItems));
        return order;
    }

    /**
     *  Se calculeaza pretul unei comenzi
     */
    @Override
    public Double computePrice(Integer idOrder) {
        assert idOrder >= 0;
        Double price = 0.0;

        Map.Entry<Order, List<MenuItem>> o = this.getOrderAfterId(idOrder);
        if (o == null) { // nu exista order-ul cu idOrder
            return price;
        }

        for (MenuItem m: o.getValue()) { // pentru fiecare item din comanda se face face suma, rezultatul final fiind pretul comenzii
            price += m.getPrice();
        }

        assert price > 0; // post conditie
        return price;
    }

    /**
     * Se salveaza factura ca si format txt
     */
    @SuppressWarnings("resource")
	@Override
    public void generateBill(Integer idOrder) throws Exception {
        File file = new File("bill" + idOrder + ".txt");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        Map.Entry<Order, List<MenuItem>> o = this.getOrderAfterId(idOrder);
        if (o == null) {
            throw new Exception("Order not found");
        }

        printWriter.println("Order: " + o.getKey().getOrderId());
        printWriter.println("Date: " + o.getKey().getDate());
        printWriter.println("Table: " + o.getKey().getTableNumber());
        printWriter.println("Total: " + this.computePrice(idOrder));
        printWriter.println("---------------------------------");
        printWriter.close();
        fileWriter.close();
    }

    public Map<Order, List<MenuItem>> getOrders() {
        return orders;
    }

    public void setOrders(Map<Order, List<MenuItem>> orders) {
        this.orders = orders;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Se cauta order-ul in hashmap dupa un id
     * Se returneaza null in caz ca order-ul cu id-ul respectiv nu se gaseste
     */
    private Map.Entry<Order, List<MenuItem>> getOrderAfterId(Integer id) {
        for (Map.Entry<Order, List<MenuItem>> o: orders.entrySet()) {
            if (o.getKey().getOrderId() == id) {
                return o;
            }
        }

        return null;
    }

    /**
     * Returneaza un obiect din lista de produse ale restaurantului dup id
     * In caz ca nu se gaseste se returneaza null
     */
    public MenuItem getItem(Integer integer) {
        for (MenuItem m: items) {
            if (m.getId().equals(integer)) {
                return m;
            }
        }
        return null;
    }
}