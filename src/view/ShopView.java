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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;


public class ShopView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JPanel panel;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
					ShopView shopFrame = new ShopView();
					shopFrame.setVisible(true);
			}
		});
	}


	public ShopView() {
		
		initUI();
		menuUI();
		loadIcon();
		registerFonts();
	}

	public void initUI() {

		// Window title.
		setTitle("SHOP GUI");
		// Size of the window when executing.
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		contentPane.setBackground(new Color(120, 120, 120));
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

	public void menuUI() {
		
		// PANEL CREATION.
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3)));
		panel.setLayout(null);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(77, 62, 725, 544);
		contentPane.add(panel);
		// Set panel with absolut layout.
		contentPane.setLayout(null);

		// We add the new font to our texts.
		Font titleFont = new Font("Poppins", Font.PLAIN, 32);
		Font customFont = new Font("Poppins", Font.PLAIN, 11);
		
		// HEADER PANEL.
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(224, 255, 255));
		headerPanel.setBounds(3, 3, 719, 73);
		panel.add(headerPanel);
		headerPanel.setLayout(null);
		
		// WELCOME TITLE TEXT.
		JLabel welcomeText = new JLabel("M05 SHOP - WELCOME TO OUR STORE");
		welcomeText.setBounds(87, 27, 606, 31);
		welcomeText.setForeground(new Color(0, 108, 84));
		welcomeText.setFont(titleFont);
		headerPanel.add(welcomeText);
		
		// SHOP IMAGE.
		JLabel shopImage = new JLabel("");
		shopImage.setBounds(11, 0, 72, 72);
		headerPanel.add(shopImage);
		shopImage.setIcon(new ImageIcon(ShopView.class.getResource("/resources/img/shopImage.png")));
		
		// LEFT PANEL.
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(3, 80, 362, 461);
		panel.add(leftPanel);
		
		
	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
}
