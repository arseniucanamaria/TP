package business;

import model.MenuItem;
import model.Order;

import java.util.List;
import java.util.Map;

public interface IRestaurantProcessing {
    /**
     * @pre price >=0.0
     * @pre !name.isEmpty()
     */
    MenuItem createMenuItem(Double price, String name) throws Exception;

    /**
     * @pre menuItems.size() > 0
     * @pre !name.isEmpty()
     */
    MenuItem createMenuItem(String name, List<MenuItem> menuItems) throws Exception;
    void deleteMenuItem(Integer idMenuItem);
    void updateMenuItem(MenuItem menuItem);

    /**
     * @pre menuItems.size() > 0
     * @pre tableId > 0
     */
    Order createOrder(List<MenuItem> menuItems, Integer tableId);

    /**
     * @pre idOrder >= 0
     * @post price > 0
     */
    Double computePrice(Integer idOrder);
    void generateBill(Integer idOrder) throws Exception;

    Map<Order, List<MenuItem>> getOrders();
    List<MenuItem> getItems();
    MenuItem getItem(Integer valueAt);
}