package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Premium;
import model.Product;
import model.Sale;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Scanner;

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
	private int numberProducts;
	final static double TAX_RATE = 1.04; // We change the IVA to a 4%
	int salesCounter = 0; // Variable to count the index of the case 6 array.
	private Scanner sc = new Scanner(System.in);

	public Shop() {

		// Old code (now we have arraylists with no limits).

		// inventory = new Product[10];
		// sales = new Sale[10];

	}

	public static void main(String[] args) {
		Shop shop = new Shop();

		// We load our inventory.
		shop.loadInventory();

		// We call our initSession method to identify the user.
		shop.initSession();

		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		do {
			// We display the menu 
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
					shop.addStock();
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
				System.err.println("Invalid input. Please enter a valid number between 1 and 5. \n"); // Validation error if the user introduces a letter 
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

		// We return true or false and we assign it to the static boolean premium that we declared on our Shop class.
		return answer;

	}

	// Logeo:

	public void initSession() {

		// We create the object worker from the class Employee, so we can be able to invoke all the methods from Employee and Person class.
		Employee worker = new Employee();

		// We declare our variables.
		int user = 0;
		String password = null;
		boolean login = false;

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println(VERDE_CLARO + "Introduce your user:" + RESET);

			if (sc.hasNextInt()) { // Validation error if the user is not a number.
				user = sc.nextInt();
			} else {

				System.err.println("Only numbers allowed, try again please:");
				sc.next(); // Clear the buffer reader.
				continue;

			}

			System.out.println(VERDE_CLARO + "Introduce your password:" + RESET);
			password = sc.next();

			// If login = true, we exit the do while.
			login = worker.login(user, password);

			// Message error if the login credentials are incorrect.
			if (!login) {
				System.err.println("Incorrect user or password. Please try again.");
			}

		} while (!login);

		// Message if the login was successful.
		if (login) {
			System.out.println(VERDE_CLARO + "\nWelcome to our store!" + RESET);
		}

	}
	// load initial inventory to shop:

	public void loadInventory() {

		// We call the method readFile which have all the default products on the .txt
		readFile();

		// Old Code:

		/* addProduct(new Product("Manzana", 10.00, true, 10));
		addProduct(new Product("Hamburguesa", 30.00, true, 30));
		addProduct(new Product("Fresa", 5.00, true, 20));
		addProduct(new Product("Melocoton", 20.00, true, 20)); */

	}

	public double totalAmount() {

		Amount totalAmount = new Amount(0.0); // Initialize the 'totalAmount' object from the class amount with a value of 0.
		double resultado = 0;
		// 1. Sale --> Reference to the class 'Sale'
		// 2. sale --> Creates a new variable named 'sale'
		// 3. sales --> Fills out the 'sale' variable with information from the array 'sales'.

		for (Sale sale : sales) {
			if (sale != null) {
				resultado += sale.getAmount().getValue();
				totalAmount.setValue(resultado); // We set the totalAmount with all the amount of money that we got with all the sales.
				System.out.println(sale.toStringAmount()); // We use 'toString' to print all the information we want to show to the user.
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
		String borrarProducto = sc.next();

		Product product = findProduct(borrarProducto); // We create a new object from the class Product and we assign findProduct with the argument borrarProducto.

		// If the ArrayList contains the product introduced by the user, we remove it.
		if (inventory.contains(product)) {
			inventory.remove(product);
			System.out.println(VERDE_CLARO + "\nThe product has been succesfully deleted." + RESET);
		} else {
			System.err
					.println("\nThe product " + ORANGE + borrarProducto + RESET + " doesn't exists on our inventory.");
			sc.nextLine(); // Clear .
		}
	}

	// DISPLAY MENU 
	private static void displayMenu() {
		System.out.println("\n");
		System.out.println("===========================");
		System.out.println("Menu principal miTienda.com");
		System.out.println("===========================");
		System.out.println("1) Contar caja");
		System.out.println("2) Añadir producto");
		System.out.println("3) Añadir stock");
		System.out.println("4) Marcar producto proxima caducidad");
		System.out.println("5) Ver inventario");
		System.out.println("6) Venta");
		System.out.println("7) Ver ventas");
		System.out.println("8) Ver venta totales");
		System.out.println("9) Eliminar un producto");
		System.out.println("10) Salir programa");
		System.out.print("Seleccione una opción: ");
	}

	// SHOW CURRENT TOTAL CASH
	private void showCash() {
		System.out.println(VERDE_CLARO + "\nDinero actual: " + cash.toString() + RESET); // We call the 'toString' from the class amount so we can print the cash with the '€'
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos.");
			return;
		}

		Scanner sc = new Scanner(System.in);

		System.out.print("Nombre: ");
		String name = sc.nextLine();
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = sc.nextDouble();

		/* We don't need this line anymore since we use it from the class AMOUNT.
		double publicPrice = wholesalerPrice * 2; */
		System.out.print("Stock: ");

		int stock = sc.nextInt();
		sc.nextLine();
		addProduct(new Product(name, wholesalerPrice, true, stock));
		System.out.println(VERDE_CLARO + "The product " + ORANGE + name + RESET
				+ " has been succesfully added to the inventary." + RESET);
	}

	// ADD STOCK FOR SPECIFIC PRODUCT.
	public void addStock() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = sc.next();
		Product product = findProduct(name);
		if (product != null) {
			// ask for stock
			System.out.print("Seleccione la cantidad a añadir: ");
			int stock = sc.nextInt();
			// update stock product
			int addStock = product.getStock() + stock; // We add the new stock number that the user introduced to the actual stock.
			product.setStock(addStock);

			System.out.println(
					VERDE_CLARO + "\nEl stock del producto " + name + " ha sido actualizado a " + addStock + RESET);

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
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

		System.out.println("\nContenido actual de la tienda:");
		for (Product product : inventory) {
			if (product != null) {
				System.out.println(product.toString()); // We use toString to get all the information we want from our inventory.

			}
		}
	}

	// MAKE A SALE OF PRODUCTS TO A CLIENT. 
	public void sale() {

		LocalDateTime saleDate = null;
		Scanner sc = new Scanner(System.in);

		// We assign if user is premium or not on our premium variable.
		boolean premium = premiumUser();

		System.out.println("Realizar venta, escribir nombre cliente");
		String client = sc.nextLine();

		ArrayList<Product> saleProducts = new ArrayList<>();
		// Product[] saleProducts = new Product[10]; 

		// Object named consumer from the client class, so we can be able to invoke all client methods.
		Client consumer = new Client(client);

		System.out.println("");
		double total = 0.0;
		String name = "";

		while (!name.equals("0")) {
			System.out.println("Productos disponibles: \n");

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
				total += product.getPublicPrice().getValue(); // We add the price of the product to the 'totalAmount'spent by the user.
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
		cash.setValue(totalCash + total);// We set the new cash that we have on our shop and we use the 'Amount' so we can have the '€' at the end.
		sales.add(new Sale(client, saleProducts, total, saleDate)); // It's going to count the sales with the information of the client, what he bought, the amount of the purchased products and the date of the sale.
		Amount totalAmount = new Amount(total);
		System.out.println(VERDE_CLARO + "Venta realizada con éxito, total: " + totalAmount + RESET);

		// User introduces 'TRUE':
		if (premium) {
			// We create a premiumClient object so we can be able to invoke the method premiumPoints().
			Premium premiumClient = new Premium();
			premiumClient.premiumPoints(totalAmount);
		}

		// We create a new boolean with the method pay with the parameter totalAmount (we can do it because the method is a boolean type).
		consumer.pay(totalAmount);

	}

	// Show all sales:
	public void showSales() {
		System.out.println("\nLista de ventas:\n");

		// 1. Sale --> Reference to the class 'Sale'
		// 2. sale --> Creates a new variable named 'sale'
		// 3. sales --> Fills out the 'sale' variable with information from the array 'sales'.5

		for (Sale sale : sales) {
			if (sale != null) {
				System.out.println(sale.toString());
			}
		}

		// Prompt user if they want to export sales data
		System.out.println("\nDo you want to export sales to a file? (Yes/No)");
		String answer = sc.nextLine().toLowerCase();

		if (answer.equals("yes")) {
			writeFile();
			System.out.println(VERDE_CLARO + "\nSales data exported successfully." + RESET);
		}
	}

	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
			return;
		}

		inventory.add(product);

	}

	/**
	 * check if inventory is full or not
	 */
	public boolean isInventoryFull() {
		if (numberProducts == 10) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * find product by name
	 *
	 * @param product name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) { // We fix the key sensitive (equalsIgnoreCase).
				return inventory.get(i); // Adapt the arraylist.
			}
		}
		return null;

	}

	public void readFile() {

		try {

			// Path to our .txt with the default products.
			File files = new File("files/inputInventory.txt"); // Observation: it's not key sensitive.
			// This scanned is going to read all our inputInventory.txt
			Scanner scanner = new Scanner(files);

			// While .txt has text in the line:
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				// We split all the ';' of the text.
				String[] line1 = data.split(";");

				// We need to declare variables before the loop.
				String nombre = null;
				double wholesalerPrice = 0.0;
				int stock = 0;

				// line1.length = lines on the .txt.
				for (int i = 0; i < line1.length; i++) {
					// We split with differents lines by ":"
					String[] line2 = line1[i].split(":");

					switch (i) {

					// We need to parse every single variable that is not string.
					case 0:
						nombre = line2[1];
						break;
					case 1:
						// Parse to Double because its settled as string.
						wholesalerPrice = Double.parseDouble(line2[1]);
						break;
					case 2:
						// Parse to Integer because its settled as string.
						stock = Integer.parseInt(line2[1]);
						break;
					default:
						System.err.println("Error in the array position.");
					}
				}
				// We pass all the variables as an argument to the constructor Product of class Product.
				addProduct(new Product(nombre, wholesalerPrice, true, stock));

			}
			scanner.close();
		} catch (FileNotFoundException error) {
			System.err.println("An error occurred.");
			error.printStackTrace();
		}

	}

	public void writeFile() {

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

				} else { // If the file already exists, its not going to be created and we are displaying the error to the user.

					System.out.println("File already exists.");
				}
			} catch (IOException error) {
				System.out.println("An error occurred: " + error.getMessage());
				error.printStackTrace();
			}

			FileWriter writer = new FileWriter(fileName, true); // We allow to add new purchases instead of overwritting them.
			int saleIndex = 1; // Variable to help us track the numbers of purchases.
			// The numbers of each purchase.
			for (int i = 0; i < sales.size(); i++) {
				Sale sale = sales.get(i);
				if (sale != null) {

					// Write client and date
					writer.write(saleIndex + ";Client=" + sale.getClient() + ";Date=" + sale.getDate() + ";\n");
					// Write products
					writer.write(saleIndex + ";Products=");
					for (Product product : sale.getProducts()) {
						writer.write(product.getName() + "," + product.getPublicPrice() + ";");
					}
					writer.write("\n");
					// Write amount
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
}
