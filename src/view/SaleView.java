package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import dao.Dao;
import dao.DaoImplHibernate;
import main.Shop;
import model.Amount;
import model.Client;
import model.Product;
import model.Sale;

import javax.swing.JComboBox;

public class SaleView extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Shop shop;
	private JLabel makeSaleProduct;
	private JSeparator separatorLine;
	private JLabel introduceClientName;
	private JLabel introduceProductName;
	private JLabel introduceProductStock;
	private JLabel isPremiumText;
	private JTextField clientName;
	private JTextField productName;
	private JTextField productStock;
	private JComboBox<String> isPremiumBox;
	private JButton okeyButton;
	private JButton backButton;
	private Client newClient;
	
	public SaleView(Shop shop) {

		this.shop = shop;

		initWindowUI();
		salesViewUI();
	}

	public void initWindowUI() {

		// Window title.
		setTitle("MAKE A SALE");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Size of the window when executing.
		setSize(345, 400);
		setResizable(false);
		// We define our background color.
		setBackground(new Color(237, 237, 233));
		getContentPane().setLayout(null);
	}

	public void salesViewUI() {

		getContentPane().setBackground(Color.LIGHT_GRAY);
		Font titleFont = new Font("Poppins", Font.PLAIN, 19);
		Font textFont = new Font("Poppins", Font.PLAIN, 12);
		// Title text.
		makeSaleProduct = new JLabel("MAKE YOUR PURCHASE");
		makeSaleProduct.setBounds(56, 20, 212, 26);
		makeSaleProduct.setForeground(new Color(0, 108, 34));
		makeSaleProduct.setFont(titleFont);
		getContentPane().add(makeSaleProduct);

		// Green line.
		separatorLine = new JSeparator();
		separatorLine.setBounds(54, 43, 212, 11);
		separatorLine.setForeground(new Color(0, 108, 34));
		separatorLine.setBackground(new Color(0, 108, 34));
		getContentPane().add(separatorLine);

		// Introduce product name text.
		introduceClientName = new JLabel("Client name :");
		introduceClientName.setBounds(37, 142, 96, 16);
		introduceClientName.setFont(textFont);
		getContentPane().add(introduceClientName);

		// Introduce product stock text.
		introduceProductName = new JLabel("Product name :");
		introduceProductName.setBounds(37, 205, 112, 16);
		introduceProductName.setFont(textFont);
		getContentPane().add(introduceProductName);

		// Introduce product price text.
		introduceProductStock = new JLabel("Product stock :");
		introduceProductStock.setBounds(37, 268, 92, 16);
		introduceProductStock.setFont(textFont);
		getContentPane().add(introduceProductStock);

		// Product name input.
		clientName = new JTextField();
		clientName.setBounds(145, 142, 124, 16);
		getContentPane().add(clientName);
		clientName.setColumns(10);

		// Product stock input.
		productName = new JTextField();
		productName.setColumns(10);
		productName.setBounds(145, 206, 124, 16);
		getContentPane().add(productName);

		// Product price input.
		productStock = new JTextField();
		productStock.setColumns(10);
		productStock.setBounds(145, 267, 124, 16);
		getContentPane().add(productStock);

		// Premium text.
		isPremiumText = new JLabel("Are you premium :");
		isPremiumText.setBounds(71, 85, 130, 20);
		isPremiumText.setFont(textFont);
		getContentPane().add(isPremiumText);

		// Premium box.
		String[] opciones = { "Yes", "No" };
		isPremiumBox = new JComboBox<>(opciones);
		isPremiumBox.setBounds(188, 84, 53, 22);
		getContentPane().add(isPremiumBox);

		okeyButton = new JButton("OK");
		okeyButton.setBounds(37, 313, 117, 29);
		okeyButton.addActionListener(this);
		getContentPane().add(okeyButton);

		// Back button.
		backButton = new JButton("BACK");
		backButton.addActionListener(this);
		backButton.setBounds(167, 313, 117, 29);
		getContentPane().add(backButton);

		productName.addKeyListener(this);
		productStock.addKeyListener(this);
		clientName.addKeyListener(this);
		okeyButton.addKeyListener(this);
		backButton.addKeyListener(this);

		// Disable tabulation on the premium box.
		isPremiumBox.setFocusable(false);
	}


	public void makeSale() {
	    String client = clientName.getText();
	    String product = productName.getText();
	    String isPremiumString = (String) isPremiumBox.getSelectedItem();
	    boolean isPremium = "Yes".equals(isPremiumString);
	    LocalDateTime saleDate = null;
	    ArrayList<Product> saleProducts = new ArrayList<>();
	    double total = 0.0;
	    int stock = 0;

	    try {
	        stock = Integer.parseInt(productStock.getText());

	        if (product.isEmpty() || client.isEmpty()) {
	            throw new IllegalArgumentException("CLIENT AND PRODUCT NAME CAN'T BE EMPTY.");
	        }

	        Product checkProduct = shop.findProduct(product);
	        if (checkProduct == null) {
	            throw new IllegalArgumentException("PRODUCT DOESN'T EXIST, TRY AGAIN.");
	        }

	        // We set the wholesaler thanks to the Price Column.
	        checkProduct.setWholesalerPrice(new Amount(checkProduct.getPrice()));
	        
	        checkProduct.publicPriceCalculation();

	        // Check if the requested stock is available.
	        checkStock(stock, checkProduct.getStock());

	        // Create a new Client object if it hasn't been created yet.
	        if (newClient == null) {
	            newClient = new Client(client);
	        }

	        // Add the product to the list of products being sold.
	        saleProducts.add(checkProduct);

	        // Calculate the total amount the client will spend.
	        total += checkProduct.getPublicPrice().getValue() * stock;

	        // Apply the tax rate (e.g., 4% IVA).
	        total = total * Shop.TAX_RATE;

	        // Create an Amount object for the total sale amount.
	        Amount totalAmount = new Amount(total);

	        // Add the total sale amount to the shop's cash register.
	        addCashValue(total);

	        // Check if the client has enough money to make the purchase.
	        pay(totalAmount);

	        // Subtract the purchased stock from the product's inventory.
	        int subtractStock = checkProduct.getStock() - stock;
	        checkProduct.setStock(subtractStock);

	        // Update the product in the database to reflect the new stock
	        shop.updateProduct(checkProduct);

	        // Add the sale to the shop's sales history.
	        shop.getSales().add(new Sale(client, saleProducts, total, saleDate));

	        // Display the updated inventory.
	        shop.showInventory();
	        
	    } catch (NumberFormatException emptyError) {
	        // Handle the case where the stock input is not a valid number.
	        JOptionPane.showMessageDialog(SaleView.this, "STOCK MUST BE A NUMBER.", "STOCK CAN'T BE EMPTY",
	                JOptionPane.ERROR_MESSAGE);
	    } catch (IllegalArgumentException | IllegalStateException emptyError) {
	        // Handle other validation errors or invalid states.
	        JOptionPane.showMessageDialog(SaleView.this, emptyError.getMessage(), "WARNING, WE DETECTED AN ERROR.",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}

	public boolean pay(Amount amount) {

		// Calculates the remaining money of the client after a purchase.
		double newBalance = newClient.getBalance().getValue() - amount.getValue();
		Amount finalBalance = new Amount(newBalance);
		// Update client balance.
		newClient.setBalance(finalBalance);

		if (newBalance < 0) {
			JOptionPane.showMessageDialog(SaleView.this, "YOU DIDN'T HAVE ENOUGH MONEY, YOU NOW OWE " + finalBalance,
					"YOU ARE ON NEGATIVE NUMBERS.", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			showProductBought(finalBalance);
			return false;
		}
	}
	
	public void addCashValue(double total) {
		
		// Storage the current cashValue on a variable.
		Amount cashValue = shop.getCashValue();
		double newCashValue = cashValue.getValue() + total;
		// Create a new instance of Amount so we can work with Amount objects.
		Amount updatedCashValue = new Amount(newCashValue);
		// Set the new value of CashValue in Shop.
		shop.setCashValue(updatedCashValue);
	}

	private void showProductBought(Amount remainingMoney) {

		// Just did this modification on the JOption pane so the button says "Back to the menu" instead of "OK".
		ImageIcon customIcon = new ImageIcon(getClass().getResource("/resources/img/checkImage.png"));
		JOptionPane newProductPane = new JOptionPane(
				"Congratulations, your purchase has been done successfuly!\nCurrent money: " + remainingMoney,
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, customIcon,
				new Object[] { "BACK TO THE MENU" });
		JDialog productAdded = newProductPane.createDialog("PETITION DONE.");
		productAdded.setVisible(true);
	}

	public boolean checkStock(int stockInput, int availableStock) {

		if (stockInput > availableStock) {
			throw new IllegalArgumentException("STOCK NOT AVAILABLE, BUY FEWER UNITS.");
		}

		return false;
	}
	

	@Override
	public void actionPerformed(ActionEvent inputButton) {

		if (inputButton.getSource() == okeyButton) {
			makeSale();

		} else if (inputButton.getSource() == backButton) {
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent buttonEnter) {

		if (buttonEnter.getKeyCode() == KeyEvent.VK_ENTER) {

			// This line is going to get the current key that user is focused into.
			// We can check in which key the user is pressing enter and work with that information.
			// We need it since I want to do different behaviors between the keys.
			Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

			// This array is going to have the differents buttons/inputs.
			ArrayList<Component> componentList = new ArrayList<>();
			// Add inputs/buttons to the array.
			componentList.add(okeyButton);
			componentList.add(productName);
			componentList.add(productStock);
			componentList.add(clientName);

			// If user uses enter on the ArrayList components and the chosed option is 2, we invoke addProduct().
			if (componentList.contains(focusedComponent)) {
				makeSale();
			} else if (focusedComponent == backButton) {
				dispose();
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyReleased(KeyEvent e) {

	}
}
