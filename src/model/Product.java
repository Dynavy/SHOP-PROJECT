package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = { "available", "wholesalerPrice", "publicPrice", "stock" })
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
	// We declare a private object of type Amount named publicPrice. This allows us
	// reference objects from the class 'Amount' on the variable publicPrice.
	private Amount publicPrice;
	private Amount wholesalerPrice;
	private boolean available;
	private int stock;
	private static int totalProducts;
	static double EXPIRATION_RATE = 0.60;
	private String currency;
	// Added productId for shared product_id
	private int productId;

	public Product(String name, double wholesalerPrice, boolean available, int stock) {
//		this.id = ++totalProducts;
		this.name = name;
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.available = available;
		this.stock = stock;
	}
	
	// New constructor for the SaxReader.
	public Product(String name) {
		this.name = name;
		this.id = ++totalProducts;
	}

	// New constructor for the Jaxb.
	public Product() {
		this.id = ++totalProducts;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void publicPriceCalculation() {
		if (this.wholesalerPrice != null) {
			this.publicPrice = new Amount(this.wholesalerPrice.getValue() * 2);
		} else {
			System.err.println("WholesalerPrice is null");
		}
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() { // CASE 5 AND CASE 7

		/*
		 * PRODUCT INFORMATION: 1. NAME || 2.PUBLICPRICE 3. WHOLESALERPRICE || 4.STOCK
		 */

		return "\nProducts ---->" + ORANGE + " Name: " + name + RESET + AMARILLO + ", PublicPrice: " + publicPrice
				+ RESET + AZUL_CLARO + ", WholesalerPrice: " + wholesalerPrice + RESET + MAGENTA + ", Stock: " + stock
				+ "." + RESET;
	}

	public String toStringAmount() { // CASE 8
		/*
		 * I only wanted to show the name of the product so I created a new toString for
		 * this case.
		 */

		return "\nProducts bought ---->" + ORANGE + " Name: " + name + RESET;
	}

	@XmlAttribute(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name = "name")
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
		return checkAvailable();
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	private boolean checkAvailable() {
		// Establish availability depending on stock.
		return this.stock > 0;
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

	// Manage id number of the products.
	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}

	public void expire() {
		// We set the value of publicPrice a 40% cheaper.
		double expiratedPrice = this.publicPrice.getValue() * (EXPIRATION_RATE);
		// The new value (40% cheaper) is setted to the variable publicPrice.
		this.publicPrice.setValue(expiratedPrice);
	}

}