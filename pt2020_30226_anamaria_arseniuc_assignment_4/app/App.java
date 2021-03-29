package app;

import business.Restaurant;
import data.RestaurantSerialization;
import javafx.util.Pair;
import model.MenuItem;
import model.Order;
import presentation.ChefGUI;
import presentation.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        RestaurantSerialization restaurantSerialization = new RestaurantSerialization();
        Pair<List<MenuItem>, Integer> menuItems;
        Pair<Map<Order, List<MenuItem>>, Integer> orders;
        
        try {
            menuItems = restaurantSerialization.readMenuItems(); // se citesc produsele restaurantului si ultimul id al produselor din fisier
            orders = restaurantSerialization.readOrders(); // se citesc orderele restaurantului si ultimul al orderelor din fisier
        } catch (Exception e) {
            System.out.println("Error loading db");
            menuItems = new Pair<>(new ArrayList<>(), 0);
            orders = new Pair<>(new HashMap<>(), 0);
        }

        MenuItem.setAutoIncrement(menuItems.getValue()); // se seteaza ultimul id al produselor, citit din fisier
        Order.setAutoIncrement(orders.getValue()); // se seteaza ultimul id al orderelor, citit din fisier

        ChefGUI chefGUI = new ChefGUI(); // se creeaza fereastra pentru bucatar
        Restaurant restaurant = new Restaurant(); // se creeaza obiectul de restaurant
        restaurant.addObserver(chefGUI); // se adauga chefGUI ca si observer al restaurantului

        restaurant.setItems(menuItems.getKey()); // se seteaza produsele citite pentru restaurant
        restaurant.setOrders(orders.getKey()); // se seteaza orderele citite ale restaurant-ului

        GUI gui = new GUI(restaurant, restaurantSerialization);


    }
}
