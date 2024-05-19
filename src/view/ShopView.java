package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import util.Constants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Shop;
import model.Client;

import javax.swing.JLabel;
import javax.swing.JSeparator;

public class ShopView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JPanel panel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel headerPanel;
	private JToggleButton checkMoney;
	private JToggleButton addProducts;
	private JToggleButton addStock;
	private JToggleButton deleteProduct;
	private JToggleButton makeSale;
	private JToggleButton showInventory;
	private JLabel menu;
	private JLabel welcomeText;
	private JLabel shopImage;
	// Buttons color:
	private Color originalColor = new Color(184, 207, 229, 255);
	private JSeparator separatorLine;
	// This instance is going to be used by different classes so we don't have any inconsistencies.
	private Shop shop;
	private Client client;

	public ShopView() {

		// We inicialize a shop instance and load the inventory.
		this.shop = new Shop();
		this.client = new Client();
		shop.loadInventory();
		shop.showInventory();
		// Invoke all the methods.
		initWindowUI();
		menuUI();
		loadIcon();
	}

	public void initWindowUI() {

		// Window title.
		setTitle("SHOP GUI");
		// Size of the window when executing.
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
		setFocusable(true);
	}

	public void loadIcon() {

		// Load image for the taskbar and title bar.
		ImageIcon icon = new ImageIcon(LoginView.class.getResource("/resources/img/shopIcon.png"));
		// Increase the image to 128x128.
		Image scaledImage = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		// We set the image to our window.
		setIconImage(scaledImage);
	}

	public void menuUI() {

		// PANEL CREATION.
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 108, 84), 3)));
		panel.setLayout(null);
		panel.setBackground(new Color(0, 108, 84));
		panel.setBounds(77, 62, 725, 544);
		contentPane.add(panel);
		// Set panel with absolut layout.
		contentPane.setLayout(null);

		// We add the new font to our texts.
		Font titleFont = new Font("Poppins", Font.PLAIN, 32);
		Font subTitle = new Font("Poppins", Font.PLAIN, 21);
		Font textFont = new Font("Poppins", Font.PLAIN, 17);

		// HEADER PANEL.
		headerPanel = new JPanel();
		headerPanel.setBackground(new Color(224, 255, 255));
		headerPanel.setBounds(3, 3, 719, 73);
		headerPanel.setLayout(null);
		panel.add(headerPanel);

		// WELCOME TITLE TEXT.
		welcomeText = new JLabel();
		welcomeText.setText("M05 SHOP - WELCOME TO OUR STORE");
		welcomeText.setBounds(92, 26, 606, 31);
		welcomeText.setForeground(new Color(0, 108, 84));
		welcomeText.setFont(titleFont);
		headerPanel.add(welcomeText);

		// SHOP IMAGE.
		shopImage = new JLabel("");
		shopImage.setBounds(13, 0, 72, 72);
		headerPanel.add(shopImage);
		shopImage.setIcon(new ImageIcon(ShopView.class.getResource("/resources/img/shopImage.png")));

		// LEFT PANEL.
		leftPanel = new JPanel();
		leftPanel.setBounds(3, 79, 362, 462);
		panel.add(leftPanel);
		leftPanel.setLayout(null);

		// RIGHT PANEL.
		rightPanel = new JPanel();
		rightPanel.setBounds(368, 79, 354, 462);
		panel.add(rightPanel);
		rightPanel.setLayout(null);

		// MENU TITLE
		menu = new JLabel();
		menu.setText(" MENU - CHOOSE AN OPTION :");
		menu.setBounds(22, 26, 314, 33);
		leftPanel.add(menu);
		menu.setFont(subTitle);
		menu.setForeground(new Color(0, 108, 84));

		// Green line.
		separatorLine = new JSeparator();
		separatorLine.setBounds(20, 51, 315, 11);
		separatorLine.setForeground(new Color(0, 108, 84));
		separatorLine.setBackground(new Color(0, 108, 84));
		leftPanel.add(separatorLine);

		// CASE 1 BUTTON (checkMoney).
		checkMoney = new JToggleButton("1. Check money.");
		checkMoney.setBounds(93, 97, 173, 25);
		checkMoney.setFont(textFont);
		checkMoney.setBackground(originalColor);
		checkMoney.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		leftPanel.add(checkMoney);

		// CASE 2 BUTTON (addProducts).
		addProducts = new JToggleButton("2. Add products.");
		addProducts.setFont(new Font("Poppins", Font.PLAIN, 17));
		addProducts.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		addProducts.setBounds(93, 157, 173, 25);
		addProducts.setBackground(originalColor);
		leftPanel.add(addProducts);

		// CASE 3 BUTTON (addStock).
		addStock = new JToggleButton("3. Update stock.");
		addStock.setFont(new Font("Poppins", Font.PLAIN, 17));
		addStock.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		addStock.setBounds(93, 217, 173, 25);
		addStock.setBackground(originalColor);

		leftPanel.add(addStock);

		// Case 9 button (deleteProduct).
		deleteProduct = new JToggleButton("9. Delete product.");
		deleteProduct.setFont(new Font("Poppins", Font.PLAIN, 17));
		deleteProduct.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		deleteProduct.setBounds(93, 397, 173, 25);
		deleteProduct.setBackground(originalColor);
		leftPanel.add(deleteProduct);

		// Case 5 button (showInventory).
		makeSale = new JToggleButton("6. Make purchase.");
		makeSale.setFont(new Font("Poppins", Font.PLAIN, 17));
		makeSale.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		makeSale.setBackground(new Color(184, 207, 229));
		makeSale.setBounds(93, 337, 173, 25);
		leftPanel.add(makeSale);

		// Case 6 button (makeSale).
		showInventory = new JToggleButton("5. Show inventory.");
		showInventory.setFont(new Font("Poppins", Font.PLAIN, 17));
		showInventory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		showInventory.setBackground(new Color(184, 207, 229));
		showInventory.setBounds(93, 277, 173, 25);
		leftPanel.add(showInventory);

		// Allow button interaction.
		checkMoney.addActionListener(this);
		addStock.addActionListener(this);
		addProducts.addActionListener(this);
		deleteProduct.addActionListener(this);
		makeSale.addActionListener(this);
		showInventory.addActionListener(this);

		// KeyListener so we can detect when the user the keyboard.
		this.addKeyListener(this);

	}

	// Buttons interaction.
	public void actionPerformed(ActionEvent buttonInteraction) {

		if (buttonInteraction.getSource() == checkMoney) {
			// Invoke the check money method.
			openCashView();

		} else if (buttonInteraction.getSource() == addProducts) {
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.ADD_PRODUCTS);

		} else if (buttonInteraction.getSource() == addStock) {
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.ADD_STOCK);

		} else if (buttonInteraction.getSource() == deleteProduct) {
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.DELETE_PRODUCT);

		} else if (buttonInteraction.getSource() == makeSale) {
			// We invoke the openInventoryView() with his constant as an argument.
			openSalesView();
			
			// We invoke the openSalesView().
		} else if (buttonInteraction.getSource() == showInventory) {
			openInventoryView(Constants.SHOW_INVENTORY);
		}

		// Focus on the frame after clicking a button.
		this.requestFocus();
	}

	public void openCashView() {

		CashView cashDialog = new CashView(shop);
		// Invoke the animation method.
		cashDialog.setVisible(true);
		cashDialog.setLocation(calculateDialogPosition());
	}

	// Option parameter is the chosen option by the user.
	public void openProductView(int option) {

		// Create a ProductView object passing our instance of shop and chosen open as an argument.
		ProductView openProductDialog = new ProductView(option, shop);
		openProductDialog.setVisible(true);
		openProductDialog.setLocation(calculateDialogPosition());
	}

	public void openInventoryView(int option) {

		ProductsView openInventoryDialog = new ProductsView(option, shop);
		openInventoryDialog.setVisible(true);
		openInventoryDialog.setLocation(calculateDialogPosition());
	}

	public void openSalesView() {

		SaleView openSaleDialog = new SaleView(shop, client);
		openSaleDialog.setVisible(true);
		openSaleDialog.setLocation(calculateDialogPosition());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent numberInteraction) {

		int key = numberInteraction.getKeyCode();

		switch (key) {

		case KeyEvent.VK_1:
			// We invoke the openCashView() with his constant as an argument.
			openCashView();
			break;

		case KeyEvent.VK_2:
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.ADD_PRODUCTS);
			break;

		case KeyEvent.VK_3:
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.ADD_STOCK);
			break;

		case KeyEvent.VK_9:
			// We invoke the openProductView() with his constant as an argument.
			openProductView(Constants.DELETE_PRODUCT);
			break;

		case KeyEvent.VK_5:
			// We invoke the openInventoryView() with his constant as an argument.
			openInventoryView(Constants.SHOW_INVENTORY);
			break;

		case KeyEvent.VK_6:
			// We invoke the openSalesView().
			openSalesView();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	// Method to calculate the default relative dimensions. 
	private Point calculateDialogPosition(double relativeX, double relativeY) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (screenSize.getWidth() * relativeX);
		int y = (int) (screenSize.getHeight() * relativeY);
		return new Point(x, y);
	}

	// Method to open our JDialogs on the correct position.
	private Point calculateDialogPosition() {
		// We use the dimensions that we want for our popups.
		return calculateDialogPosition(Constants.DEFAULT_RELATIVE_X, Constants.DEFAULT_RELATIVE_Y);
	}
}
