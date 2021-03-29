package model;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5657065827661974336L;
	private String name;
    private List<MenuItem> items;

    public CompositeProduct() {

    }

    public CompositeProduct(String name) {
        super();
        items = new ArrayList<>();
        this.name = name;
    }

    public void addMenuItem(MenuItem item){
        this.items.add(item);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public Double getPrice() {
        double total = 0.0;
        for (MenuItem menuItem: this.items) {
            total += menuItem.getPrice();
        }

        return total;
    }

    @Override
    public String toString() {
        return name;
    }
}
