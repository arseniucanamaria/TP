package model;

public class BaseProduct extends MenuItem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2754959858656815606L;
	private Double price;
    private String name;

    public BaseProduct() {
    }

    public BaseProduct(Double price, String name) {
        super();
        this.price = price;
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return name;
    }
}
