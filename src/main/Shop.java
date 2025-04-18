package main;

import dao.Dao;
import dao.DaoImplMongoDB;
import model.Amount;
import model.Client;
import model.Premium;
import model.Product;
import model.Sale;
import view.LoginView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import org.jboss.jandex.Main;

import java.util.logging.Level;
import java.util.logging.LogManager;

public class Shop {
	// Colors start.
	final String RESET = "\u001B[0m";
	final String AZUL_CLARO = "\u001B[94m";
	final String ORANGE = "\u001B[38;5;208m";
	final String VERDE_CLARO = "\u001B[92m";
	final String AMARILLO = "\u001B[33m";
	final String MAGENTA = "\u001B[35m";
	// Colors end.
	// New ArrayLists for inventory and sales without any limitations.
	private ArrayList<Product> inventory = new ArrayList<>();
	private ArrayList<Sale> sales = new ArrayList<>();
	private Amount cash = new Amount(100); // Initialize the 'cash' object in the class amount with a value of 100.
	public final static double TAX_RATE = 1.04; // We change the IVA to a 4%
	int salesCounter = 0; // Variable to count the index of the case 6 array.
	private Scanner sc = new Scanner(System.in);
	private static Shop shop;

	// Creates a method that returns a shop instance.
	public static Shop getInstance() {
		if (shop == null) {
			shop = new Shop();
		}
		return shop;
	}

	/*
	 * We use Polymorphism toe create a 'dao' object using the 'Dao' interface,
	 * allowing it to use the attributes and methods of 'DaoImplHibernate'.
	 */
	private Dao dao = new DaoImplMongoDB();

	public static void main(String[] args) {

		loadLoggingConfiguration();

		Shop shop = Shop.getInstance();
		// We call our initSession method to identify the user.
		shop.initSession();
		// We load the inventory of our shop.
		shop.loadInventory();
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		do {
			// We display fthe menu
			displayMenu();

			if (scanner.hasNextInt()) {

				int opcion = 0;

				opcion = scanner.nextInt();

				switch (opcion) {

				case 1:
					shop.showCash();
					break;

				case 2:
					shop.addProduct();
					break;

				case 3:
					shop.updateStock();
					break;

				case 4:
					shop.setExpired();
					break;

				case 5:
					shop.showInventory();
					break;

				case 6:
					shop.sale();
					break;

				case 7:
					shop.showSales();
					break;

				case 8:
					shop.totalAmount();
					break;

				case 9:
					shop.deleteProduct();
					break;

				case 10:
					System.err.println("\nYou have exit the program.");
					exit = true;
					break;

				default:
					System.err.println("\nNúmero no permitido, vuelve a intentarlo. ");
					break;
				}

			} else {
				// Validation error if the user introduces a letter.
				System.err.println("Invalid input. Please enter a valid number between 1 and 5. \n");
				scanner.nextLine();
			}

		} while (!exit);
		scanner.close();
	}

	// Method to identify premium users.
	public boolean premiumUser() {

		boolean answer;

		do {

			// We ask to the client if he is a premium user or not.
			System.out.println(VERDE_CLARO + "Are you a premium user? TRUE/FALSE" + RESET);

			// The answer need toUpperCase for the if logic.
			String respuesta = sc.next().toUpperCase();

			// True = return true and break the loop.
			if (respuesta.equals("TRUE")) {
				answer = true;
				break;

				// False = return false and break the loop.
			} else if (respuesta.equals("FALSE")) {
				answer = false;
				break;
				// We manage invalids outputs on the else section.
			} else {
				System.err.println("Invalid input. Please enter TRUE or FALSE.");
			}

			// Loop is executing permanently until it finds the break on the if or elseif.
		} while (true);

		// We return true or false and we assign it to the static boolean premium that
		// we declared on our Shop class.
		return answer;

	}

	// Login:

	public void initSession() {
		LoginView loginView = new LoginView();
		// Enable the GUI display.
		loginView.setVisible(true);

		System.out.println(VERDE_CLARO + "Welcome to our store!" + RESET);

	}

	// Load the inventory of our shop.
	public void loadInventory() {

		setInventory(dao.getInventory());
	}

	public boolean writeInventory() {

		return dao.writeInventory(inventory);
	}

	public double totalAmount() {

		Amount totalAmount = new Amount(0.0); // Initialize the 'totalAmount' object from the class amount with a value
												// of 0.
		double resultado = 0;
		// 1. Sale --> Reference to the class 'Sale'
		// 2. sale --> Creates a new variable named 'sale'
		// 3. sales --> Fills out the 'sale' variable with information from the array
		// 'sales'.

		for (Sale sale : sales) {
			if (sale != null) {
				resultado += sale.getAmount().getValue();
				totalAmount.setValue(resultado); // We set the totalAmount with all the amount of money that we got with
													// all the sales.
				System.out.println(sale.toStringAmount()); // We use 'toString' to print all the information we want to
															// show to the user.
			}
		}

		System.out.println(VERDE_CLARO + "\nTotal Amount of Sales: " + totalAmount + RESET);

		Premium premiumClient = new Premium();

		// Print of total premium points.
		premiumClient.premiumPoints(totalAmount);

		return totalAmount.getValue();
	}

	public void deleteProduct() {

		System.out.println("\nWelcome to the menu 9.");

		System.out.println("\nAvailable products on our inventory:\n");
		for (Product product : inventory) {

			// We display the available products to the user.
			if (product != null) {
				System.out.print(ORANGE + product.getName() + RESET);
				System.out.println("");

			}
		}
		System.out.println("\nWritte the name of the product that you want to delete:");
		String deleteProduct = sc.next();

		Product product = findProduct(deleteProduct); // We create a new object from the class Product and we assign
														// findProduct with the argument borrarProducto.

		// If the ArrayList contains the product introduced by the user, we remove it.
		if (inventory.contains(product)) {
			dao.deleteProduct(product);
			shop.loadInventory();
			System.out.println(VERDE_CLARO + "\nThe product has been succesfully deleted." + RESET);

		} else {
			System.err.println("\nThe product " + ORANGE + deleteProduct + RESET + " doesn't exists on our inventory.");
			sc.nextLine(); // Clear .
		}
	}

	// DISPLAY MENU
	private static void displayMenu() {
		System.out.println("\n");
		System.out.println("===========================");
		System.out.println("Main menu myStore.com");
		System.out.println("===========================");
		System.out.print("Select an option:: \n\n");
		System.out.println("1) Show cash.");
		System.out.println("2) Add product.");
		System.out.println("3) Update stock.");
		System.out.println("4) Mark product expiration.");
		System.out.println("5) Show inventory.");
		System.out.println("6) Make a sale.");
		System.out.println("7) Show sales.");
		System.out.println("8) Show total sales.");
		System.out.println("9) Delete product.");
		System.out.println("10) Exit program.");
	}

	// SHOW CURRENT TOTAL CASH.
	public void showCash() {
		// We call the 'toString' from the class amount so we can print the cash with
		// the €.
		System.out.println(VERDE_CLARO + "\nRemaining money: " + cash.toString() + RESET);
	}

	public void addProduct() {

		System.out.print("Product name: ");
		String name = sc.nextLine();
		Product productName = findProduct(name);

		if (inventory.contains(productName)) {
			System.err.println("The product already exists, redirecting to the menu.");
			return;
		}
		System.out.print("WholesalerPrice: ");
		double wholesalerPrice = sc.nextDouble();

		/*
		 * We don't need this line anymore since we use it from the class AMOUNT. double
		 * publicPrice = wholesalerPrice * 2;
		 */
		System.out.print("Stock: ");

		int stock = sc.nextInt();
		sc.nextLine();
		// We use the addProduct method from DaoImplJDBC.
		dao.addProduct(new Product(name, wholesalerPrice, true, stock));
		shop.loadInventory();
		System.out.println(VERDE_CLARO + "The product " + ORANGE + name + VERDE_CLARO
				+ " has been succesfully added to the inventary." + RESET);
	}

	// ADD STOCK FOR SPECIFIC PRODUCT.
	public void updateStock() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Select a product by its name: ");
		String name = sc.next();
		Product product = findProduct(name);

		if (product != null) {
			// Ask for stock inside the loop to handle invalid input.
			int newStock;
			while (true) {
				System.out.println("Select the new stock quantity for the following product: " + product.getName());
				newStock = sc.nextInt();

				if (newStock < 0) {
					System.err.println("Stock has to be 0 or higher.");
				} else {
					// Exit the loop if the input is valid.
					break;
				}
			}

			// Set the new stock to the product instance.
			product.setStock(newStock);
			dao.updateProduct(product);
			shop.loadInventory();
			System.out.println(
					VERDE_CLARO + "\nThe stock from the product " + name + " has been updated to: " + newStock + RESET);

		} else {
			System.out.println("Couldn't find the product with the name " + name);
		}
	}

	// SET A PRODUCT AS EXPIRED.
	private void setExpired() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = sc.next();

		Product product = findProduct(name);

		if (product != null) {
			product.expire(); // We call the method expire, which sets the price of the found product.
			System.out.println(VERDE_CLARO + "\nEl stock del producto " + name + " ha sido actualizado a "
					+ product.getPublicPrice() + RESET);

		} else {
			System.err.println("El producto no existe en nuestra tienda.");
		}
	}

	// SHOW ALL INVENTORY.
	public void showInventory() {

		// Load inventory to avoid possible data errors.
		shop.loadInventory();
		System.out.println("\nAvailable products:");

		for (Product product : dao.getInventory()) {
			if (product != null) {
				System.out.println(product.toString());
			}
		}
	}

	// MAKE A SALE OF PRODUCTS TO A CLIENT.
	public void sale() {

		LocalDateTime saleDate = null;
		Scanner sc = new Scanner(System.in);

		// We assign if user is premium or not on our premium variable.
		boolean premium = premiumUser();

		System.out.println("Make a sale, write the client name.");
		String client = sc.nextLine();

		ArrayList<Product> saleProducts = new ArrayList<>();
		// Product[] saleProducts = new Product[10];

		// Object named consumer from the client class, so we can be able to invoke all
		// client methods.
		Client consumer = new Client(client);

		System.out.println("");
		double total = 0.0;
		String name = "";

		while (!name.equals("0")) {
			System.out.println("Available products: \n");

			for (Product product : inventory) {

				if (product != null) {
					System.out.print(ORANGE + product.getName() + RESET);
					System.out.print(AMARILLO + " Price: " + product.getPublicPrice() + RESET);
					System.out.print(VERDE_CLARO + " Stock: " + product.getStock() + RESET + "\n");
				}

			}
			System.out.println("\nIntroduce el nombre del producto, escribir 0 para terminar:");
			name = sc.nextLine();

			if (name.equals("0")) {
				break;
			}
			Product product = findProduct(name);
			boolean productAvailable = false;

			if (product != null && product.isAvailable()) {
				productAvailable = true;
				total += product.getPublicPrice().getValue(); // We add the price of the product to the
																// 'totalAmount'spent by the user.
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}

				// Array List
				saleProducts.add(product);

				System.out.println(VERDE_CLARO + "Producto vendido con éxito\n" + RESET);

			}

			if (!productAvailable) {
				System.err.println("\nProducto no encontrado o sin stock\n");
			}
		}

		// We assign the exact time of the purchase.
		saleDate = LocalDateTime.now();

		// show cost total
		total = total * TAX_RATE; // We add the 4% of IVA to the totalAmount.
		double totalCash = cash.getValue();
		cash.setValue(totalCash + total);// We set the new cash that we have on our shop and we use the 'Amount' so we
											// can have the '€' at the end.
		sales.add(new Sale(client, saleProducts, total, saleDate)); // It's going to count the sales with the
																	// information of the client, what he bought, the
																	// amount of the purchased products and the date of
																	// the sale.
		Amount totalAmount = new Amount(total);
		System.out.println(VERDE_CLARO + "Venta realizada con éxito, total: " + totalAmount + RESET);

		// User introduces 'TRUE':
		if (premium) {
			// We create a premiumClient object so we can be able to invoke the method
			// premiumPoints().
			Premium premiumClient = new Premium();
			premiumClient.premiumPoints(totalAmount);
		}

		// We invoke the pay method with the totalAmonut as an argument.
		consumer.pay(totalAmount);

	}

	// Show all sales:
	public void showSales() {
		System.out.println("\nLista de ventas:\n");

		// 1. Sale --> Reference to the class 'Sale'
		// 2. sale --> Creates a new variable named 'sale'
		// 3. sales --> Fills out the 'sale' variable with information from the array
		// 'sales'.5

		for (Sale sale : sales) {
			if (sale != null) {
				System.out.println(sale.toString());
			}
		}

		// Prompt user if they want to export sales data
		System.out.println("\nDo you want to export sales to a file? (Yes/No)");
		String answer = sc.nextLine().toLowerCase();

		if (answer.equals("yes")) {
			writeSaleFiles();
			System.out.println(VERDE_CLARO + "\nSales data exported successfully." + RESET);
		}
	}

	// Add the product information to the inventory array.
	public void addProduct(Product product) {

		inventory.add(product);
		dao.addProduct(product);

	}

	// Update the product through the database.
	public void updateProduct(Product product) {

		dao.updateProduct(product);
	}

	public void removeProduct(Product product) {

		dao.deleteProduct(product);
	}

	// Check if a product is on our inventory array.
	public Product findProduct(String name) {
		for (Product product : shop.getInventory()) {
			if (product != null && product.getName().equalsIgnoreCase(name)) {
				return product;
			}
		}
		return null;
	}

	// Case 7: Show sales.
	public void writeSaleFiles() {

		try {
			// Get current date for the file name.
			String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			// Create the file name with the current date.
			String fileName = "files/sales_" + currentDate + ".txt";
			// Create a new file for each different day.
			File file = new File(fileName);

			try {

				if (file.createNewFile()) { // Aware the user that the file has been created.
					System.out.println("File created: " + fileName);

				} else { // If the file already exists, its not going to be created and we are displaying
							// the error to the user.

					System.out.println("File already exists.");
				}
			} catch (IOException error) {
				System.out.println("An error occurred: " + error.getMessage());
				error.printStackTrace();
			}

			FileWriter writer = new FileWriter(fileName, true); // We allow to add new purchases instead of overwritting
																// them.
			int saleIndex = 1; // Variable to help us track the numbers of purchases.
			// The numbers of each purchase.
			for (int i = 0; i < sales.size(); i++) {
				Sale sale = sales.get(i);
				if (sale != null) {

					// Write client and date.
					writer.write(saleIndex + ";Client=" + sale.getClient() + ";Date=" + sale.getDate() + ";\n");
					// Write products.
					writer.write(saleIndex + ";Products=");
					for (Product product : sale.getProducts()) {
						writer.write(product.getName() + "," + product.getPublicPrice() + ";");
					}
					writer.write("\n");
					// Write amount.
					writer.write(saleIndex + ";Amount=" + sale.getAmount() + ";\n");

					saleIndex++; // We increment each purchase (1,2,3,4...).
				}
			}
			// Close the file writer.
			writer.close();
		} catch (IOException e) {
			System.err.println("Error al escribir en el archivo: " + e.getMessage());
		}
	}

	public Amount getCashValue() {

		return cash;
	}

	public void setCashValue(Amount cash) {

		this.cash = cash;
	}

	public ArrayList<Product> getInventory() {

		// Ensure that inventory matches the database Inventory table.
		shop.loadInventory();
		return inventory;
	}

	public void setInventory(ArrayList<Product> product) {
		// We set our inventory with the default products.
		this.inventory = product;
	}

	public ArrayList<Sale> getSales() {

		return sales;
	}

    public static void loadLoggingConfiguration() {
        try {
            
            String logConfigFile = "C:\\Users\\Dynavy\\git\\SHOP-PROJECT\\src\\main\\resources\\logging.properties";

            FileInputStream inStream = new FileInputStream(logConfigFile);

            LogManager.getLogManager().readConfiguration(inStream);
            System.out.println("Logging configuration loaded successfully");

        } catch (IOException e) {
            System.err.println("Error loading logging configuration: " + e.getMessage());
        }
    }

}