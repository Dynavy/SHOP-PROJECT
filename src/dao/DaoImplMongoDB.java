package dao;

import java.util.ArrayList;

import java.util.Date;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {

	private MongoClient mongoClient;
	private MongoDatabase database;

	public DaoImplMongoDB() {
		connect();
	}

	@Override
	public void connect() {
		if (mongoClient == null) {
			try {
				// Load the .env file.
				Dotenv dotenv = Dotenv.load();
				// Get the URI from the .env.
				String mongoUri = dotenv.get("MONGO_URI");

				if (mongoUri == null || mongoUri.isEmpty()) {

					System.err.println("Error: MONGO_URI environment variable is not set.");
					return;
				}

				// Creates a single instance of MongoClient (Singleton).
				mongoClient = MongoClients.create(mongoUri);
				database = mongoClient.getDatabase("Shop");

				System.out.println("Database connected successfully!");

			} catch (Exception e) {

				System.err.println("Error connecting to the database: " + e.getMessage());
			}
		}

		if (database != null) {

			System.out.println("Database retrieved successfully.");

		} else {

			System.err.println("Error retrieving the database.");
		}
	}

	@Override
	public void disconnect() {

		if (mongoClient != null) {

			mongoClient.close();
		}
	}

	@Override
	public Employee getEmployee(String username, String pw) {

		// Get the "users" collection from the database.
		MongoCollection<Document> collection = database.getCollection("users");

		// Create a query to find the user by username and password.
		Document query = new Document("username", username).append("password", pw);

		// Search for the first document that matches the query
		Document doc = collection.find(query).first();

		// Return a new Employee object if found, otherwise return null.
		return (doc != null) ? new Employee(username, pw) : null;
	}

	@Override
	public ArrayList<Product> getInventory() {

		ArrayList<Product> inventory = new ArrayList<>();

		try {

			MongoCollection<Document> collection = database.getCollection("inventory");
			FindIterable<Document> documents = collection.find();

			for (Document doc : documents) {

				// Get the 'id' field.
				Integer id = doc.getInteger("id", 0);

				// Handle wholesalerPrice properly.
				Amount wholesalerPrice = null;
				Document priceDoc = (Document) doc.get("wholesalerPrice");

				if (priceDoc != null) {
					// Get the value of 'value' field and determine its type.
					Object priceObj = priceDoc.get("value");
					double price = 0.0;

					if (priceObj instanceof Integer) {
						// Convert Integer to Double.
						price = ((Integer) priceObj).doubleValue();

					} else if (priceObj instanceof Double) {
						price = (Double) priceObj;
					}

					String currency = priceDoc.getString("currency");

					// Debugging the price and currency.
					System.out.println("Price: " + price + " " + currency);

					wholesalerPrice = new Amount(price, currency);

				} else {

					System.out.println("No wholesalerPrice found.");
				}

				// Get name, availability, and stock.
				String name = doc.getString("name");
				boolean available = doc.getBoolean("available", false);
				int stock = doc.getInteger("stock", 0);

				// Debugging product details.
				System.out.println("ID: " + id + ", Name: " + name + ", Available: " + available + ", Stock: " + stock);

				// Create the product and add it to the inventory list.
				Product product = new Product(id, name, wholesalerPrice, available, stock);

				inventory.add(product);
			}

		} catch (Exception e) {

			System.err.println("Error retrieving inventory: " + e.getMessage());
		}

		return inventory;
	}

	@Override
	public void addProduct(Product product) {
		MongoCollection<Document> collection = database.getCollection("inventory");

		// Query to find the highest current id to increment for the new product.
		Document lastProduct = collection.find().sort(new Document("id", -1)).first();

		int newId = 1;

		if (lastProduct != null) {
			// Increment the last id by 1.
			newId = lastProduct.getInteger("id") + 1;
		}

		// Create the document to insert.
		Document doc = new Document("id", newId).append("name", product.getName())
				.append("wholesalerPrice",
						new Document("value", product.getWholesalerPrice().getValue()).append("currency",
								product.getWholesalerPrice().getCurrency())) // Make sure currency is dynamic.
				.append("available", product.isAvailable()).append("stock", product.getStock());

		// Insert the document into the MongoDB collection.
		collection.insertOne(doc);

		// Log to see if the addition is successful.
		System.out.println("Product added with id: " + newId);
	}

	@Override
	public void updateProduct(Product product) {

		MongoCollection<Document> collection = database.getCollection("inventory");

		// Query to find the product by ID.
		Document query = new Document("id", product.getId());

		// Document to store fields to be updated
		Document setFields = new Document();

		// Update 'name' if it is not null.
		if (product.getName() != null) {
			setFields.append("name", product.getName());
		}

		// Update 'wholesalerPrice' if it is not null
		if (product.getWholesalerPrice() != null) {

			setFields.append("wholesalerPrice", new Document("value", product.getWholesalerPrice().getValue())
					.append("currency", product.getWholesalerPrice().getCurrency()));
		}

		// Always update 'available' and 'stock' fields.
		setFields.append("available", product.isAvailable());
		setFields.append("stock", product.getStock());

		// Create update document with '$set'
		Document update = new Document("$set", setFields);

		// Execute update operation.
		collection.updateOne(query, update);
	}

	@Override
	public void deleteProduct(Product product) {

		// Get the collection "inventory" from the database,
		MongoCollection<Document> collection = database.getCollection("inventory");

		// Query to find the product by its ID.
		Document query = new Document("id", product.getId());

		// Console debugging.
		System.out.println("Trying to delete product with ID: " + product.getId());

		// Execute delete operation.
		collection.deleteOne(query);
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {

		// Get the collection "historical_inventory" from the database.
		MongoCollection<Document> collection = database.getCollection("historical_inventory");

		// Create an ArrayList of documents to insert
		ArrayList<Document> documents = new ArrayList<>();

		// Convert each product to a Document and add it to the list.
		for (Product product : products) {

			documents.add(new Document("id_product", product.getId()).append("name", product.getName())
					.append("wholesalerPrice",
							new Document("value", product.getWholesalerPrice().getValue()).append("currency", "EUR"))
					.append("available", product.isAvailable()).append("stock", product.getStock())
					.append("created_at", new Date()));
		}

		// Insert all documents into the collection.
		collection.insertMany(documents);

		return true;
	}
}
