package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import main.Shop;
import model.Employee;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class ShopGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	Shop shop = new Shop();
	Employee employee = new Employee();
	// Boolean to help us with the credentials validation.
	private boolean isLogged;
	// Boolean to help us with the credentials validation.
	public boolean isCredentialsValid = false;
	private JTextField validateCredentials;
	private JPasswordField employeePass;
	private JTextField employeeUser;
	private JTextField introduceYourUsername;
	private JTextField introduceYourPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// Set the interface visible.
				ShopGUI shopGUI = new ShopGUI();
				shopGUI.setVisible(true);
			}
		});
	}

	public ShopGUI() {

		initUI();
		loginUI();
		registerFonts();
		loadIcon();
		loadImages();
	}

	public void initUI() {

		// Window title.
		setTitle("Shop GUI");
		// Size of the window when executing.
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		contentPane.setBackground(new Color(80, 80, 80));
		setContentPane(contentPane);
	}

	public void registerFonts() {
		try {
			// Add 'Poppins-Bold.ttf as a resource font.
			InputStream inputStream = ShopGUI.class.getResourceAsStream("/resources/fonts/Poppins-Italic.ttf");
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(24f);
			// Register the font on our graphic environment.
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
			ge.registerFont(customFont);

		} catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	public void loadIcon() {

		// Load image for the taskbar and title bar.
		ImageIcon icon = new ImageIcon(ShopGUI.class.getResource("/resources/img/shopIcon.png"));
		// Increaze the image to 128x128.
		Image scaledImage = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		// We set the image to our window.
		setIconImage(scaledImage);
	}

	public void loadImages() {

		JLabel keyImage = new JLabel(new ImageIcon(ShopGUI.class.getResource("/resources/img/keyImage.png")));
		keyImage.setBounds(419, 59, 64, 64);
		contentPane.add(keyImage);
	}

	public void loginUI() {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3), "", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(262, 145, 350, 320);
		contentPane.add(panel);
		// We add the new font to our texts.
		Font titleFont = new Font("Poppins", Font.PLAIN, 14);
		Font customFont = new Font("Poppins", Font.PLAIN, 11);

		// CREDENTIAL VALIDATION TEXT.
		validateCredentials = new JTextField();
		validateCredentials.setHorizontalAlignment(SwingConstants.CENTER);
		validateCredentials.setBackground(UIManager.getColor("Button.shadow"));
		validateCredentials.setText("  EMPLOYEE CREDENTIALS VALIDATION");
		validateCredentials.setBounds(47, 61, 259, 46);
		validateCredentials.setEditable(false);
		validateCredentials.setForeground(new Color(0, 108, 84));
		validateCredentials.setFont(titleFont);
		validateCredentials.setColumns(10);
		panel.add(validateCredentials);

		// EMPLOYEE PASSWORD TEXT.
		employeePass = new JPasswordField();
		employeePass.setHorizontalAlignment(SwingConstants.CENTER);
		employeePass.setBounds(145, 232, 105, 20);
		panel.add(employeePass);

		// EMPLOYEE USER TEXT.
		employeeUser = new JTextField();
		employeeUser.setHorizontalAlignment(SwingConstants.CENTER);
		employeeUser.setBounds(145, 171, 105, 20);
		employeeUser.setColumns(10);
		employeeUser.setFont(customFont);
		panel.add(employeeUser);

		// INTRODUCE USERNAME TEXT.
		introduceYourUsername = new JTextField();
		introduceYourUsername.setHorizontalAlignment(SwingConstants.CENTER);
		introduceYourUsername.setEditable(false);
		introduceYourUsername.setText("Introduce your employeeID :");
		introduceYourUsername.setBounds(83, 131, 190, 32);
		introduceYourUsername.setColumns(10);
		introduceYourUsername.setFont(customFont);
		panel.add(introduceYourUsername);

		// INTRODUCE PASSWORD TEXT.
		introduceYourPassword = new JTextField();
		introduceYourPassword.setHorizontalAlignment(SwingConstants.CENTER);
		introduceYourPassword.setEditable(false);
		introduceYourPassword.setText("Introduce your password :");
		introduceYourPassword.setColumns(10);
		introduceYourPassword.setBounds(83, 199, 190, 27);
		introduceYourPassword.setFont(customFont);
		panel.add(introduceYourPassword);

		// VALIDATION IMAGE.
		JLabel validationWarningImage = new JLabel(
				new ImageIcon(ShopGUI.class.getResource("/resources/img/validationWarning.png")));
		validationWarningImage.setBounds(162, 11, 48, 45);
		panel.add(validationWarningImage);

		// PASSWORD IMAGE.
		JLabel passImage = new JLabel(new ImageIcon(ShopGUI.class.getResource("/resources/img/passImage.png")));
		passImage.setBounds(105, 223, 32, 32);
		panel.add(passImage);

		// EMPLOYEEID IMAGEE.
		JLabel idImage = new JLabel(new ImageIcon(ShopGUI.class.getResource("/resources/img/idImage.png")));
		idImage.setBounds(105, 164, 34, 34);
		panel.add(idImage);

		// LOG IN BUTTON.
		JToggleButton submit = new JToggleButton("LOGIN");
		submit.setBackground(new Color(135, 206, 235));
		submit.setBounds(59, 263, 232, 25);
		panel.add(submit);

		// We add the button to the actionListener.
		addLoginButtonListener(submit);

		contentPane.setLayout(null); // Absolute layout.
	}

	public void addLoginButtonListener(JToggleButton button) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateCredentials();
			}
		});
	}

	public void validateCredentials() {

		String stringEmployeeID = employeeUser.getText();
		int userID;

		try {
			// userID = the input introduced by the user and we parse it to be able to match as an argument.
			userID = Integer.parseInt(stringEmployeeID);
		} catch (NumberFormatException e) {
			// Notification in case user doesn't introduce an Integer.
			JOptionPane.showMessageDialog(ShopGUI.this, "Employee ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// We assign the password chars to a new array.
		char[] passwordChars = employeePass.getPassword();
		// We transform the chars into a String. 
		String userPassword = new String(passwordChars);

		isLogged = employee.login(userID, userPassword);

		// If employee introduces wrong credentials.
		if (!isLogged) {
			// Error message display.
			JOptionPane.showMessageDialog(ShopGUI.this, "Invalid credentials, try again.", "Repeat again", JOptionPane.ERROR_MESSAGE);
		} else {
			isCredentialsValid = true;
			dispose();
		}
	}
}
