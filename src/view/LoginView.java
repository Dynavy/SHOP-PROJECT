package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Employee;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import exception.LimitLoginException;

import java.awt.Component;

public class LoginView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	Employee employee = new Employee();
	// Boolean to help us with the credentials validation.
	public boolean isLogged;
	private JTextField validateCredentials;
	private JPasswordField employeePass;
	private JTextField employeeUser;
	private JTextField introduceYourUsername;
	private JTextField introduceYourPassword;
	private JToggleButton submit;
	private JPanel panel;
	private JLabel validationWarningImage;
	private JLabel passImage;
	private JLabel idImage;
	
	// We need this variable to manage the user attempts. 
	private int countError = 0;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LoginView loginFrame = new LoginView();
				// First input from the user when executing is going to be the employeeUser Jtext.
				loginFrame.employeeUser.requestFocusInWindow();
				loginFrame.setVisible(true);
			}
		});
	}

	public LoginView() {

		initWindowUI();
		loginUI();
		tabulation();
		registerFonts();
		loadIcon();
		loadImages();

	}

	public void initWindowUI() {

		// Window title.
		setTitle("LOGIN GUI");
		// Size of the window when executing.
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
	}

	public void registerFonts() {
		try {

			// Add 'Poppins-Bold.ttf as a resource font.
			InputStream inputStream = LoginView.class.getResourceAsStream("/resources/fonts/Poppins-Italic.ttf");
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(24f);
			// Register the font on our graphic environment.
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

		} catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	public void loadIcon() {

		// Load image for the taskbar and title bar.
		ImageIcon icon = new ImageIcon(LoginView.class.getResource("/resources/img/shopIcon.png"));
		// Increase the image to 128x128.
		Image scaledImage = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		// We set the image to our window.
		setIconImage(scaledImage);
	}

	public void loadImages() {

		JLabel keyImage = new JLabel(new ImageIcon(LoginView.class.getResource("/resources/img/keyImage.png")));
		keyImage.setBounds(419, 94, 64, 64);
		contentPane.add(keyImage);
	}

	public void loginUI() {

		// PANEL CREATION.
		panel = new JPanel();
		// Set panel with absolut layout.
		contentPane.setLayout(null);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 108, 84), 3)));
		panel.setBackground(new Color(238, 238, 238));
		panel.setBounds(269, 187, 350, 320);
		panel.setLayout(null);
		contentPane.add(panel);
		// We add the new font to our texts.
		Font titleFont = new Font("Poppins", Font.PLAIN, 14);
		Font customFont = new Font("Poppins", Font.PLAIN, 11);

		// CREDENTIAL VALIDATION TEXT.
		validateCredentials = new JTextField();
		validateCredentials.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		validateCredentials.setHorizontalAlignment(SwingConstants.CENTER);
		validateCredentials.setBackground(UIManager.getColor("Button.shadow"));
		validateCredentials.setText("  EMPLOYEE CREDENTIALS VALIDATION");
		validateCredentials.setBounds(47, 61, 263, 46);
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
		introduceYourUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		introduceYourUsername.setHorizontalAlignment(SwingConstants.CENTER);
		introduceYourUsername.setEditable(false);
		introduceYourUsername.setText("Introduce your employeeID :");
		introduceYourUsername.setBounds(83, 131, 190, 32);
		introduceYourUsername.setColumns(10);
		introduceYourUsername.setFont(customFont);
		panel.add(introduceYourUsername);

		// INTRODUCE PASSWORD TEXT.
		introduceYourPassword = new JTextField();
		introduceYourPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		introduceYourPassword.setHorizontalAlignment(SwingConstants.CENTER);
		introduceYourPassword.setEditable(false);
		introduceYourPassword.setText("Introduce your password :");
		introduceYourPassword.setColumns(10);
		introduceYourPassword.setBounds(83, 199, 190, 27);
		introduceYourPassword.setFont(customFont);
		panel.add(introduceYourPassword);

		// VALIDATION IMAGE.
		validationWarningImage = new JLabel(
				new ImageIcon(LoginView.class.getResource("/resources/img/validationWarning.png")));
		validationWarningImage.setBounds(151, 11, 48, 45);
		panel.add(validationWarningImage);

		// PASSWORD IMAGE.
		passImage = new JLabel(new ImageIcon(LoginView.class.getResource("/resources/img/passImage.png")));
		passImage.setBounds(105, 223, 32, 32);
		panel.add(passImage);

		// EMPLOYEEID IMAGEE.
		idImage = new JLabel(new ImageIcon(LoginView.class.getResource("/resources/img/idImage.png")));
		idImage.setBounds(105, 164, 34, 34);
		panel.add(idImage);

		// LOG IN BUTTON.
		submit = new JToggleButton("LOGIN");
		submit.setBackground(new Color(135, 206, 235));
		submit.setBounds(61, 263, 232, 25);
		submit.addActionListener(this);
		submit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.add(submit);

		// KeyListener so we can detect when the user uses a key when on those inputs.
		employeePass.addKeyListener(this);
		employeeUser.addKeyListener(this);
		submit.addKeyListener(this);

	}

	public void tabulation() {

		// We enable the tabulation.
		employeeUser.setFocusTraversalKeysEnabled(true);
		employeePass.setFocusTraversalKeysEnabled(true);
		submit.setFocusTraversalKeysEnabled(true);

		// We disable the tabulation for useless inputs.

		introduceYourPassword.setFocusTraversalKeysEnabled(false);
		introduceYourUsername.setFocusTraversalKeysEnabled(false);

		// Order of tabulation.
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { employeeUser, employeePass, submit }));
	}

	public void validateCredentials() {
		// Every user introduces wrong credentials, we add 1 to the class variable. 
		countError++;
		String stringEmployeeID = employeeUser.getText();
		// We have to assign the password chars to a new array because is a JpasswordField.
		char[] passwordChars = employeePass.getPassword();
		// We transform the chars into a String. 
		String userPassword = new String(passwordChars);
		// Need this variable to parse the stringEmployeeID.
		int userID = 0;

		try {

			// The first thing that is going check is if credentials are invalid for the third consecutive time .
			if (countError == 3 && !isLogged) {
				throw new LimitLoginException();
			}
			
			// Since we need an int, we parse the variable so it can match as an argument.
			userID = Integer.parseInt(stringEmployeeID);

			// It will return true/false to our boolean IsLogged
			isLogged = employee.login(userID, userPassword);

			// Wrong credentials --> returns false.
			if (!isLogged) {
				// Error message display when incorrect credentials.
				JOptionPane.showMessageDialog(LoginView.this, "Invalid credentials, try again " + countError + "/3.",
						"WARNING! VALIDATION WENT WRONG", JOptionPane.ERROR_MESSAGE);
			} else {
				// Good credentials --> returns true.
				dispose();
				ShopView shopFrame = new ShopView();
				// After doing the dispose on the loginView, we set visible the ShopView.
				shopFrame.setVisible(true);
				shopFrame.requestFocus();
			}

			// Only executes when the count error is == 3.
		} catch (LimitLoginException maxAttempts) {
			JOptionPane.showMessageDialog(LoginView.this, maxAttempts.getMessage(), "LEAVING THE PROGRAM.",
					JOptionPane.ERROR_MESSAGE);
			dispose();

			// If it can't parse (its not an int), the catch is going to execute.
		} catch (NumberFormatException invalidInput) {
			// User notification about only introducing a natural number.
			JOptionPane.showMessageDialog(LoginView.this,
					"Employee ID must be a natural number, try again " + countError + "/3.", "ERROR MESSAGE DISPLAY.",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent loginButton) {

		// Detects if user uses the submit button.
		if (loginButton.getSource() == submit) {
			// We invoke the method that validates if the introduced credentials are correct or not.
			validateCredentials();
		}
	}

	@Override
	public void keyPressed(KeyEvent buttonEnter) {
		
		// When user press 'enter', it invokes the logic of validateCredentials().
		// This only applicates on the KeyListeners inputs, defined above.
		if (buttonEnter.getKeyCode() == KeyEvent.VK_ENTER) {
			validateCredentials();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
