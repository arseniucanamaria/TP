package Model;

public class Seller {
	private int id;
	private String name;
	private String address;
	private int noOfSoldProducts;

	/**
	 * constructor
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param noOfSoldProducts
	 */
	public Seller(int id, String name, String address, int noOfSoldProducts) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.noOfSoldProducts = noOfSoldProducts;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNoOfSoldProducts() {
		return noOfSoldProducts;
	}

	public void setNoOfSoldProducts(int noOfSoldProducts) {
		this.noOfSoldProducts = noOfSoldProducts;
	}
}
