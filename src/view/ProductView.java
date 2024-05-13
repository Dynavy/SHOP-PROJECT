package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import main.Shop;
import model.Product;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class ProductView extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Object from Shop class.
	private JTextField productName;
	private JTextField productStock;
	private JTextField productPrice;
	private JLabel introduceDataTitle;
	private JSeparator separatorLine;
	private JButton backButton;
	private JButton okeyButton;
	private JLabel introduceProductName;
	private JLabel introduceProductStock;
	private JLabel introduceProductPrice;

	// Here we will save the instance that came from ShopView.
	private Shop shop;

	private int option;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {

			}
		});
	}

	
	public ProductView(int option, Shop shop) {
		
		// Inicialize the shop and option from ShopView class.
		this.shop = shop;
		this.option = option;
		
		initWindowUI();
		productViewUI();

	}

	public void initWindowUI() {

		// Window title.
		setTitle("ADD PRODUCT");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		// Size of the window when executing.
		setSize(345, 400);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		setBackground(new Color(237, 237, 233));

	}

	public void productViewUI() {

		getContentPane().setBackground(Color.LIGHT_GRAY);
		Font titleFont = new Font("Poppins", Font.PLAIN, 19);
		Font textFont = new Font("Poppins", Font.PLAIN, 12);
		getContentPane().setLayout(null);

		// Introduce data title.
		introduceDataTitle = new JLabel("INTRODUCE THE FOLLOWING DATA");
		introduceDataTitle.setBounds(10, 30, 326, 16);
		introduceDataTitle.setFont(titleFont);
		introduceDataTitle.setForeground(new Color(0, 108, 34));
		getContentPane().add(introduceDataTitle);

		// Introduce product name text.
		introduceProductName = new JLabel("Product name :");
		introduceProductName.setBounds(50, 110, 92, 16);
		introduceProductName.setFont(textFont);
		getContentPane().add(introduceProductName);

		// Introduce product stock text.
		introduceProductStock = new JLabel("Product stock :");
		introduceProductStock.setBounds(50, 172, 92, 16);
		introduceProductStock.setFont(textFont);
		getContentPane().add(introduceProductStock);

		// Introduce product price text.
		introduceProductPrice = new JLabel("Product price :");
		introduceProductPrice.setBounds(50, 234, 92, 16);
		introduceProductPrice.setFont(textFont);
		getContentPane().add(introduceProductPrice);

		// Product name input.
		productName = new JTextField();
		productName.setBounds(148, 110, 124, 16);
		getContentPane().add(productName);
		productName.setColumns(10);

		// Product stock input.
		productStock = new JTextField();
		productStock.setColumns(10);
		productStock.setBounds(148, 172, 124, 16);
		getContentPane().add(productStock);

		// Product price input.
		productPrice = new JTextField();
		productPrice.setColumns(10);
		productPrice.setBounds(148, 234, 124, 16);
		getContentPane().add(productPrice);

		// Green line.
		separatorLine = new JSeparator();
		separatorLine.setBounds(9, 47, 313, 11);
		separatorLine.setForeground(new Color(0, 108, 34));
		separatorLine.setBackground(new Color(0, 108, 34));
		getContentPane().add(separatorLine);

		// Add button.
		okeyButton = new JButton("OK");
		okeyButton.setBounds(45, 284, 117, 29);
		okeyButton.addActionListener(this);
		getContentPane().add(okeyButton);

		// Back button.
		backButton = new JButton("BACK");
		backButton.addActionListener(this);
		backButton.setBounds(175, 284, 117, 29);
		getContentPane().add(backButton);

		// KeyListener so we can detect when the user uses a key when on those inputs/buttons.
		productName.addKeyListener(this);
		productPrice.addKeyListener(this);
		productStock.addKeyListener(this);
		okeyButton.addKeyListener(this);
		backButton.addKeyListener(this);

		// Focus on productName input.
		this.productName.requestFocusInWindow();

		if (option == 3) {
			setTitle("ADD STOCK");
			introduceProductPrice.setVisible(false);
			productPrice.setVisible(false);

		} else if (option == 9) {
			setTitle("DELETE PRODUCT");
			introduceProductPrice.setVisible(false);
			productPrice.setVisible(false);
			introduceProductStock.setVisible(false);
			productStock.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent inputButton) {

		if (inputButton.getSource() == backButton) {
			dispose();

			// If user selected option 2, we invoke addProducty() method.
		} else if (inputButton.getSource() == okeyButton && option == 2) {
			addProduct();
			// If user selected option 3, we invoke addStock() method.
		} else if (inputButton.getSource() == okeyButton && option == 3) {
			addStock();
		} else if (inputButton.getSource() == okeyButton && option == 9) {
			deleteProduct();
		}

	}

	public void addProduct() {

		// Recopile  data from the user.
		String product = productName.getText();
		double price = 0.0;
		int stock = 0;

		try {

			if (product.isEmpty()) {
				// If the product is empty, we throw the exception.
				throw new IllegalArgumentException("PRODUCT NAME CAN'T BE EMPTY.");
			}
			// If its not empty, we parse it in order to match the constructor parameters.
			if (!productPrice.getText().isEmpty() && !productStock.getText().isEmpty()) {
				price = Double.parseDouble(productPrice.getText());
				stock = Integer.parseInt(productStock.getText());

			} else {
				// If price and stock are empty, we throw the exception.
				throw new NumberFormatException("PRICE AND STOCK CAN'T BE EMPTY.");
			}

			// Using the constructor from Product class with our arguments.
			Product newProduct = new Product(product, price, true, stock);

			// Check if the product already exists on our inventory.
			// We are operating on the same instance than ShopView.
			if (shop.findProduct(product) == null) {
				shop.addProduct(newProduct);
				shop.showInventory();
				showProductAddedMessage();
				dispose();

			} else { // If product exists:
				JOptionPane.showMessageDialog(ProductView.this, "PRODUCT ALREADY EXISTS, TRY AGAIN.",
						"WARNING! CAN'T ADD THE PRODUCT", JOptionPane.ERROR_MESSAGE);
			}

			// Exception to control if price and stock are empty.
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ProductView.this, e.getMessage(), "WARNING, WE DETECTED AN ERROR",
					JOptionPane.ERROR_MESSAGE);
			// Exception to control if product is empty.
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(ProductView.this, e.getMessage(), "WARNING, WE DETECTED AN ERROR.",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showProductAddedMessage() {

		// Just did this modification on the JOption pane so the button says "Back to the menu" instead of "OK".
		ImageIcon customIcon = new ImageIcon(getClass().getResource("/resources/img/checkImage.png"));
		JOptionPane newProductPane = new JOptionPane("Congratulations, your petition has been done successfuly!",
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, customIcon,
				new Object[] { "BACK TO THE MENU" });
		JDialog productAdded = newProductPane.createDialog("PETITION DONE.");
		productAdded.setVisible(true);
	}

	public void addStock() {

		// Recopile data from user.
		String product = productName.getText();
		// Create a variable of type Product, invoke findProduct method, use the inputVariable to find product.
		Product productName = shop.findProduct(product);
		int stock = 0;

		try {

			if (product.isEmpty()) {
				// If the product is empty, we throw the exception.
				throw new IllegalArgumentException("PRODUCT NAME CAN'T BE EMPTY.");
			}
			// If its not empty, we parse it in order to match the getter parameter (int).
			if (!productStock.getText().isEmpty()) {
				stock = Integer.parseInt(productStock.getText());

			} else {
				// If stock is empty we throw the exception.
				throw new NumberFormatException("STOCK CAN'T BE EMPTY.");
			}
			// Add stock if the product exists on our inventory.
			if (product != null) {
				int addStock = productName.getStock() + stock;
				productName.setStock(addStock);
				showProductAddedMessage();
				dispose();

			}
			
			// Logic to control when product doesn't exists on our array.
			if (product == null) {
				
			}

			// Exception to control if stock is empty.
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ProductView.this, e.getMessage(), "WARNING, WE DETECTED AN ERROR.",
					JOptionPane.ERROR_MESSAGE);

			// Exception to control if product is empty.
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(ProductView.this, e.getMessage(), "WARNING, WE DETECTED AN ERROR.",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void deleteProduct() {

		// Recopile data from user.
		String product = productName.getText();
		Product productName = shop.findProduct(product);

		try {

			if (product.isEmpty()) {
				// If the product is empty, we throw the exception.
				throw new IllegalArgumentException("PRODUCT NAME CAN'T BE EMPTY.");
				
			} 
			// Executes when product exists on our inventory.
			if (productName != null) {
				shop.getInventory().remove(productName);
				shop.showInventory();
				showProductAddedMessage();
				dispose();
			} 
			else {
				// Product doesn't exit popup.
				JOptionPane.showMessageDialog(ProductView.this, "PRODUCT DOESN'T EXISTS, TRY AGAIN.",
						"WARNING! CAN'T REMOVE THE PRODUCT", JOptionPane.ERROR_MESSAGE);
			}
			

		} catch (IllegalArgumentException emptyName) {
			JOptionPane.showMessageDialog(ProductView.this, emptyName.getMessage(), "WARNING, WE DETECTED AN ERROR.",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void keyPressed(KeyEvent buttonEnter) {
		// This logic only works when user uses the key 'enter'.
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
			componentList.add(productPrice);

			// If user uses enter on the ArrayList components and the chosed option is 2, we invoke addProduct().
			if (componentList.contains(focusedComponent) && option == 2) {
				addProduct();

				// If user uses enter on the ArrayList components and the chosed option is 3, we invoke addStock().
			} else if (componentList.contains(focusedComponent) && option == 3) {
				addStock();

				// When users uses enter on the backButton, we dispose() the JDialog.
			} else if (componentList.contains(focusedComponent) && option == 9) {
				deleteProduct();

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
