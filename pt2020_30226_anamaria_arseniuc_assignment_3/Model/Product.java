package Model;

public class Product {
	int id;
	private String name;
	private int quantity;
	private float price;

	/**
	 * constructor
	 * 
	 * @param id
	 * @param name
	 * @param quantity
	 * @param price
	 */
	public Product(int id, String name, int quantity, float price) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" + "name='" + name + '\'' + ", quantity=" + quantity + '\'' + ", price=" + price + '}';
	}
}
