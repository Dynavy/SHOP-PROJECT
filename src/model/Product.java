package model;

public class Product {
	// Colors start.
	final String RESET = "\u001B[0m";
	final String AZUL_CLARO = "\u001B[94m";
	final String ORANGE = "\u001B[38;5;208m";
	final String VERDE_CLARO = "\u001B[92m";
	final String AMARILLO = "\u001B[33m";
	final String MAGENTA = "\u001B[35m";
	// Colors end.
	private int id;
	private String name;
	private Amount publicPrice; // We declare a private object of type Amount named publicPrice. This allows us reference objects from the class 'Amount' on the variable publicPrice.
	private Amount wholesalerPrice;
	private boolean available;
	private int stock;
	private static int totalProducts;
	static double EXPIRATION_RATE = 0.60;
	private String currency;

	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = totalProducts + 1;
		this.name = name;
		this.publicPrice = new Amount(wholesalerPrice * 2); // We create a new object named Amount to the variable.								
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.available = available;
		this.stock = stock;
		totalProducts++;

	}

	// New constructor for the SaxReader.
	public Product(String name) {
		this.name = name;
	}
	
	/**
	 * @return the badge
	 */
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override
	public String toString() { // CASE 5 AND CASE 7

		/* PRODUCT INFORMATION:
		 * 1. NAME || 2.PUBLICPRICE 3. WHOLESALERPRICE || 4.STOCK        */

		return "\nProducts ---->" + ORANGE + " Name: " + name + RESET + AMARILLO + ", PublicPrice: " + publicPrice
				+ RESET + AZUL_CLARO + ", WholesalerPrice: " + wholesalerPrice + RESET + MAGENTA + ", Stock: " + stock
				+ "." + RESET;
	}

	public String toStringAmount() { // CASE 8
		/* I only wanted to show the name of the product so I created a new toString for this case. */

		return "\nProducts bought ---->" + ORANGE + " Name: " + name + RESET;
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

	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}

	public void expire() {
		double expiratedPrice = this.publicPrice.getValue() * (EXPIRATION_RATE); // We set the value of publicPrice a															// 40% cheaper.
		this.publicPrice.setValue(expiratedPrice); // The new value (40% cheaper) is setted to the variable publicPrice.
	}

}