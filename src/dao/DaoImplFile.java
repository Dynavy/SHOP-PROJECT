package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import model.Product;
import model.Employee;

public class DaoImplFile implements Dao {

	ArrayList<Product> inventory = new ArrayList<Product>();

	@Override
	public ArrayList<Product> getInventory() {

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
					// We split with different lines by ":"
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
				// We pass all the variables as an argument to the addProduct method.
				Product product = (new Product(nombre, wholesalerPrice, true, stock));
				inventory.add(product);
			}
			scanner.close();
		} catch (FileNotFoundException error) {
			System.err.println("An error occurred.");
			error.printStackTrace();
		}

		return inventory;

	}

	@Override
	public boolean writeInventory(ArrayList<Product> productList) {

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
			int counter = 0; // Variable to help us track the numbers of purchases.

			for (Product product : inventory) {
				writer.write(product.getId() + ";" + "product:" + product.getName() + ";" + "stock:"
						+ product.getPublicPrice() + ";");
				writer.write("\n");
				counter++;
				
			}
			writer.write("Total of products:" + counter + ";");

			// Close the file writer.
			writer.close();
			return true;
		} catch (IOException e) {
			System.err.println("Error writting on the file: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void connect()  {

	}

	@Override
	public void disconnect(){

	}

	@Override
	public Employee getEmployee(int user, String pw) {
		
		return null;
	}

}