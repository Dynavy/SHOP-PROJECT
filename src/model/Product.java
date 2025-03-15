package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.persistence.*;

@Entity
@Table(name = "Inventory")
public class Product {
	// Console colors.
	@Transient
	final String RESET = "\u001B[0m";
	@Transient
	final String AZUL_CLARO = "\u001B[94m";
	@Transient
	final String ORANGE = "\u001B[38;5;208m";
	@Transient
	final String VERDE_CLARO = "\u001B[92m";
	@Transient
	final String AMARILLO = "\u001B[33m";
	@Transient
	final String MAGENTA = "\u001B[35m";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;

	@Transient
	private Amount publicPrice;

	@Transient
	private Amount wholesalerPrice;

	@Column(name = "price")
	private double price;

	@Column(name = "available")
	private boolean available;

	@Column(name = "stock")
	private int stock;

	private static int totalProducts;
	static double EXPIRATION_RATE = 0.60;

	@Transient
	private String currency;

	// Added productId for shared product_id.
	@Transient
	private int productId;

	public Product(String name, double wholesalerPrice, boolean available, int stock) {
	//	this.id = ++totalProducts;
		this.name = name;
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.available = available;
		this.stock = stock;
	}

	// New constructor for MongoDB.
	public Product(int id, String name, Amount wholesalerPrice, boolean available, int stock) {
	 //	this.id = ++totalProducts;
	 	this.id = id;
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
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
		}
	}

	public void updatePricesFromWholesaler() {
		
		if (this.wholesalerPrice != null) {

			this.price = this.wholesalerPrice.getValue();
			this.publicPrice = new Amount(this.price * 2);
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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