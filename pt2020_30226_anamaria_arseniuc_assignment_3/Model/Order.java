package Model;

public class Order {
	private int id;
	private String client;
	private String product;
	private int quantity;

	/**
	 * constructor
	 * 
	 * @param id
	 * @param client
	 * @param product
	 * @param quantity
	 */
	public Order(int id, String client, String product, int quantity) {
		this.id = id;
		this.client = client;
		this.product = product;
		this.quantity = quantity;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Order{" + "id='" + id + '\'' + "client='" + client + '\'' + ", product=" + product + '\''
				+ ", quantity=" + quantity + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
