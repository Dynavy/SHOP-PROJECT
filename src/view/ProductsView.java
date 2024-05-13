package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Shop;

import javax.swing.JLabel;
import javax.swing.JSeparator;

public class ProductsView extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JTable productTable;
	private JLabel availableProductsInput;
	private JSeparator separatorLine;
	private Shop shop;
	int option;
	

	public static void main(String[] args) {
	
	}


	public ProductsView(int option, Shop shop) {
		// Inicialize the same instance and option from ShopView class.
		this.shop = shop;
		this.option = option;
		
		initWindowUI();
		
	}

	public void initWindowUI() {

		// Window title.
		setTitle("Products View");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Size of the window when executing.
		setSize(345, 400);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		setBackground(new Color(237, 237, 233));
	}

	public void productsViewUI() {
		
		getContentPane().setLayout(null);
		Font titleFont = new Font("Poppins", Font.PLAIN, 19);
		// Product table.
		productTable = new JTable();
		productTable.setBounds(61, 57, 226, 289);
		productTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "PublicPrice", "WholesalerPrice", "Stock"
			}
		));
		getContentPane().add(productTable);
		
		
		availableProductsInput = new JLabel("AVAILABLE PRODUCTS");
		availableProductsInput.setBounds(74, 11, 212, 26);
		availableProductsInput.setForeground(new Color(0, 108, 34));
		availableProductsInput.setFont(titleFont);
		getContentPane().add(availableProductsInput);
		
		separatorLine = new JSeparator();
		separatorLine.setBounds(68, 34, 212, 11);
		separatorLine.setForeground(new Color(0, 108, 34));
		separatorLine.setBackground(new Color(0, 108, 34));
		getContentPane().add(separatorLine);

		
	}
	
}
