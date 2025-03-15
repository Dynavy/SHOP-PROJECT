package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import main.Shop;
import model.Product;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class ProductsView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable productTable;
	private JLabel availableProductsInput;
	private JSeparator separatorLine;
	private JButton backButton;
	private Shop shop;
	int option;

	public ProductsView(int option, Shop shop) {

		// Initialize the same instance and option from ShopView class.
		this.shop = shop;
		this.option = option;
		initWindowUI();
		productsViewUI();
		tableInformation();
	}

	public void initWindowUI() {

		// Window title.
		setTitle("PRODUCTS VIEW");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Size of the window when executing.
		setSize(345, 400);
		setResizable(false);
		// We define our background color.
		setBackground(new Color(237, 237, 233));
		getContentPane().setLayout(null);
	}

	public void productsViewUI() {

		getContentPane().setBackground(Color.LIGHT_GRAY);
		Font titleFont = new Font("Poppins", Font.PLAIN, 19);

		// Title text.
		availableProductsInput = new JLabel("AVAILABLE PRODUCTS");
		availableProductsInput.setBounds(74, 11, 212, 26);
		availableProductsInput.setForeground(new Color(0, 108, 34));
		availableProductsInput.setFont(titleFont);
		getContentPane().add(availableProductsInput);

		// Green line.
		separatorLine = new JSeparator();
		separatorLine.setBounds(68, 34, 212, 11);
		separatorLine.setForeground(new Color(0, 108, 34));
		separatorLine.setBackground(new Color(0, 108, 34));
		getContentPane().add(separatorLine);

		// Back button.
		backButton = new JButton("BACK");
		backButton.addActionListener(this);
		backButton.setBounds(110, 321, 117, 29);
		getContentPane().add(backButton);

		// Interaction with the button.
		backButton.addActionListener(this);

	}

	public void tableInformation() {
		List<Product> productsInfo = shop.getInventory();

		// Column names.
		String[] columnNames = { "Id", "Name", "Available", "Price", "Stock" };

		// Create data array with 4 columns.
		Object[][] data = new Object[productsInfo.size()][5];

		for (int i = 0; i < productsInfo.size(); i++) {
			
			Product product = productsInfo.get(i);
			// Product ID.
			data[i][0] = product.getId(); 
			// Product Availability.
			data[i][1] = product.getName();
			// Product Name.
			data[i][2] = product.isAvailable(); 
			// Check if price is 0.0, if so assign "N/A".
			data[i][3] = product.getWholesalerPrice() != null ? product.getWholesalerPrice().getValue() : "N/A"; 
			// Product Stock.
			data[i][4] = product.getStock(); 
		}

		// Create the table with the data and column names.
		productTable = new JTable(data, columnNames);

		// Add a scroll pane for the table.
		JScrollPane scrollPane = new JScrollPane(productTable);

		// Modify the column widths as needed.
		productTable.getColumnModel().getColumn(0).setPreferredWidth(20); 
		productTable.getColumnModel().getColumn(0).setPreferredWidth(90); 
		productTable.getColumnModel().getColumn(2).setPreferredWidth(80); 
		productTable.getColumnModel().getColumn(3).setPreferredWidth(80); 
		productTable.getColumnModel().getColumn(4).setPreferredWidth(40); 

		// Set bounds for the scroll pane.
		scrollPane.setBounds(10, 50, 310, 264);
		getContentPane().add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent inputButton) {

		if (inputButton.getSource() == backButton) {
			dispose();
		}
	}
}