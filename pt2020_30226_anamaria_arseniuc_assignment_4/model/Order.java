package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6696246822686900418L;

	private static int AUTO_INCREMENT = 0; // valoare a clasei, pentru fiecare order creat se genereaza un id unic

    private int orderId;
    private Date date;
    private int tableNumber;

    public Order(int tableNumber) {
        this.orderId = AUTO_INCREMENT++; // se seteaza id-ul order-ului si se incrementeaza AUTO_INCREMENT
        this.tableNumber = tableNumber;
        this.date = new Date();
    }

    public static void setAutoIncrement(Integer id) {
        AUTO_INCREMENT = id;
    }

    public static int getAutoIncrement() {
        return AUTO_INCREMENT;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // aceeasi referinta de obiect
        if (o == null || getClass() != o.getClass()) return false; // clase diferite
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
