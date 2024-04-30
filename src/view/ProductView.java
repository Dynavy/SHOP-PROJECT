package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

public class ProductView extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	Shop shop = new Shop();
	private JTextField productName;
	private JTextField productStock;
	private JTextField productPrice;
	private JLabel introduceDataTitle;
	private JSeparator separatorLine;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				ProductView dialog = new ProductView();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);

			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ProductView() {

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
		
		Font titleFont = new Font("Poppins", Font.PLAIN, 19);
		getContentPane().setLayout(null);
		
		
		// Introduce data title.
		introduceDataTitle = new JLabel("INTRODUCE THE FOLLOWING DATA");
		introduceDataTitle.setBounds(13, 46, 326, 16);
		introduceDataTitle.setFont(titleFont);
		introduceDataTitle.setForeground(new Color (0, 108, 34));
		getContentPane().add(introduceDataTitle);

		
		// Introduce product name text.
		JLabel introduceProductName = new JLabel("Product name:   ->");
		introduceProductName.setBounds(13, 109, 136, 16);
		getContentPane().add(introduceProductName);
		
		// Introduce product stock text.
		JLabel introduceProductStock = new JLabel("Product stock:   ->");
		introduceProductStock.setBounds(13, 171, 152, 16);
		getContentPane().add(introduceProductStock);
		
		// Introduce product price text.
		JLabel introduceProductPrice = new JLabel("Product price:    ->");
		introduceProductPrice.setBounds(13, 233, 136, 16);
		getContentPane().add(introduceProductPrice);
		
		// Product name input.
		productName = new JTextField();
		productName.setBounds(152, 110, 124, 16);
		getContentPane().add(productName);
		productName.setColumns(10);
		
		// Product stock input.
		productStock = new JTextField();
		productStock.setColumns(10);
		productStock.setBounds(152, 170, 124, 16);
		getContentPane().add(productStock);
		
		// Product price input.
		productPrice = new JTextField();
		productPrice.setColumns(10);
		productPrice.setBounds(152, 233, 124, 16);
		getContentPane().add(productPrice);
		
		// Green line.
		separatorLine = new JSeparator();
		separatorLine.setBounds(6, 61, 330, 11);
		separatorLine.setForeground(new Color(0, 108, 84));
		separatorLine.setBackground(new Color(0, 108, 84));
		getContentPane().add(separatorLine);
		
		JButton okButton = new JButton("OK");
		okButton.setBounds(45, 284, 117, 29);
		getContentPane().add(okButton);
		
		JButton backButoon = new JButton("BACK");
		backButoon.setBounds(175, 284, 117, 29);
		getContentPane().add(backButoon);

		// Add button.
	

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
