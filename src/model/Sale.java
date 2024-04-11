package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {
	// Colors start.
	final String RESET = "\u001B[0m";
	final String AZUL_CLARO = "\u001B[94m";
	final String ORANGE = "\u001B[38;5;208m";
	final String VERDE_CLARO = "\u001B[92m";
	final String AMARILLO = "\u001B[33m";
	final String MAGENTA = "\u001B[35m";
	final String RED = "\u001B[31m";
	// Colors end.
	private Client client;

	// Product[] products; 

	private ArrayList<Product> products = new ArrayList<>(); // New arraylist.

	private Amount amount;
	private LocalDateTime date; // New atribute for the date and time.

	public Sale(String client, ArrayList<Product> products, double totalAmount, LocalDateTime date) {
		super();
		this.client = new Client(client);
		this.products = products;
		this.amount = new Amount(totalAmount);
		this.date = date; // I add the new atribute to the constructor and inicialize it.
	}

	public String getDate() {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // Example: 23-03-1934 14:43:54
		String formattedDate = date.format(myFormatObj);
		return formattedDate;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		String result = "Client: " + client + ".\n"; // The client name is always going to be in mayus (toUpperCase).

		// 1. Product --> Reference to the class 'Product'
		// 2. products --> Creates a new variable named 'product'
		// 3. products --> Fills out the 'product' variable with information from the array 'products'.

		for (Product product : products) {
			if (product != null) {
				result = result + product.toString();
			}
		}
		result = result + VERDE_CLARO + "\nDate of the purchase: " + this.getDate() + "\nTotal Amount: " + amount
				+ RESET + "\n";
		return result;
	}

	// I created a new toString for the feature 9, to control better the information that I want to show to the user.
	public String toStringAmount() {
		String result = ""; // We need to declare the variable out of the loop.
		int contador = 1; // To count the different purchase's from the clients.
		System.out.println("\nCliente " + client + "."); // The client name is always going to be in mayus (toUpperCase).

		// 1. Product --> Reference to the class 'Product'
		// 2. products --> Creates a new variable named 'product'
		// 3. products --> Fills out the 'product' variable with information from the array 'products'.

		for (Product product : products) {

			if (product != null) {
				result = result + "\nPurchase " + contador + VERDE_CLARO + product.toStringAmount() + RESET;
				contador++;
			}

		}
		result = result + RED + "\nPurchase charge: " + amount + RESET + "\n";

		return result;
	}

}