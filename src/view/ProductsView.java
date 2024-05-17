package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

		// Inicialize the same instance and option from ShopView class.
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

		// List of Product class with the inventory information.
		ArrayList<Product> productsInfo = shop.getInventory();
		// Table column information.
		String[] columnNames = { "Id", "Name", "PublicPrice", "WholesalerPrice ", "Stock" };
		Object[][] data = new Object[productsInfo.size()][5];

		for (int i = 0; i < productsInfo.size(); i++) {
			Product product = productsInfo.get(i);
			data[i][0] = product.getId();
			data[i][1] = product.getName();
			data[i][2] = product.getPublicPrice();
			data[i][3] = product.getWholesalerPrice();
			data[i][4] = product.getStock();
		}

		productTable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(productTable);

		// Modify width for each column.
		productTable.getColumnModel().getColumn(0).setPreferredWidth(20); // Id.
		productTable.getColumnModel().getColumn(1).setPreferredWidth(80); // Name.
		productTable.getColumnModel().getColumn(2).setPreferredWidth(80); // PublicPrice.
		productTable.getColumnModel().getColumn(3).setPreferredWidth(110); // WholeSalerPrice.
		productTable.getColumnModel().getColumn(4).setPreferredWidth(40); // Stock
		// Set size of the table.
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
