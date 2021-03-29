package data;


import config.PersistenceProperties;
import javafx.util.Pair;
import model.MenuItem;
import model.Order;

import java.io.*;
import java.util.*;
import java.util.List;

public class RestaurantSerialization {

    public RestaurantSerialization(){

    }

    /**
     * Citesc produsele din fisier si ultimul id al vreunui produs creat in aplicatie(autoincrement)
     */
    public Pair<List<MenuItem>, Integer> readMenuItems() throws Exception {
        FileInputStream fileIn = new FileInputStream(PersistenceProperties.getProperty("ITEMS_FILE_NAME"));
        ObjectInputStream objectIn = new ObjectInputStream(fileIn); // de tip fisier; il serializez ca si obiect

        List<MenuItem> menuItems = new ArrayList<>();
        int autoIncrement = objectIn.readInt(); // ultimul id al vreunui produs creat
        int totals = objectIn.readInt(); // numarul total de produse care se vor citi

        for (int i = 0; i < totals; i++) { // citire menu item
            MenuItem menuItem = (MenuItem)objectIn.readObject();
            menuItems.add(menuItem);
        }

        objectIn.close(); // inchidere fisier
        fileIn.close();

        return new Pair<>(menuItems, autoIncrement); // returnare rezultat
    }

    /**
     * Se scriu in fisier produsele restaurantului si ultimul id al produselor create in aplicatie
     */
    public void writeMenuItems(List<MenuItem> menuItems, Integer autoIncrement) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(PersistenceProperties.getProperty("ITEMS_FILE_NAME"));
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeInt(autoIncrement); // se scrie valoarea ultimul id
        objOut.writeInt(menuItems.size()); // se scrie numarul total de produse

        for (MenuItem menuItem: menuItems) {
            objOut.writeObject(menuItem); // se scrie fiecare produs in fisier
        }

        objOut.close();
        fileOut.close(); // se inchide fisierul
    }

    /**
     * se citesc orderele din fisier si ultimul id al vreunui order creat in aplicatie
     */
    public Pair<Map<Order, List<MenuItem>>, Integer> readOrders() throws Exception {
        FileInputStream fileIn = new FileInputStream(PersistenceProperties.getProperty("ORDERS_FILE_NAME"));
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        int autoIncrement = objectIn.readInt(); // valoare ultimului id
        Map<Order, List<MenuItem>> orders = new HashMap<>();
        int totals = objectIn.readInt(); // numarul total de ordere
        for (int i = 0; i < totals; i++) { // se citeste fiecare order
            Order order = (Order)objectIn.readObject();
            List<MenuItem> items = new ArrayList<>();
            int itemsTotal = objectIn.readInt(); // numarul de produse comandate pe order-ul respectiv
            for (int x = 0; x < itemsTotal; x++) { // se citesc item-urile per order
                items.add((MenuItem)objectIn.readObject());
            }
            orders.put(order, items);
        }

        objectIn.close();
        fileIn.close();

        return new Pair<>(orders, autoIncrement);
    }

    /**
     * se scriu orderele din fisier si ultimul id al vreunui order creat in aplicatie
     */
    public void writeOrders(Map<Order, List<MenuItem>> orders, Integer autoIncrement) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(PersistenceProperties.getProperty("ORDERS_FILE_NAME"));
        ObjectOutputStream objOut = new ObjectOutputStream(fileOutputStream);
        objOut.writeInt(autoIncrement);
        objOut.writeInt(orders.size());
        for (Map.Entry<Order, List<MenuItem>> o: orders.entrySet()) {
            objOut.writeObject(o.getKey());
            objOut.writeInt(o.getValue().size());
            for (MenuItem m : o.getValue()) {
                objOut.writeObject(m);
            }
        }

        objOut.close();
        fileOutputStream.close();
    }
}
