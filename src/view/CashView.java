package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import main.Shop;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class CashView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField availableMoney;
	private JToggleButton okButton;
	private JLabel cashImage;
	private JLabel moneyIncashText;
	private JLabel arrowImage;
	private Shop shop;

	public CashView(Shop shop) {
		
		this.shop = shop;
		initWindowUI();
		cashViewUI();
		registerFonts();
	}

	public void initWindowUI() {
		
		// Window title.
		setTitle("CASH");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		// Size of the window when executing.
		setSize(345, 400);
		// We define our background color.
		setBackground(new Color(237, 237, 233));

	}

	public void cashViewUI() {
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		Font textFont = new Font("Poppins", Font.PLAIN, 17);
		getContentPane().setLayout(null);

		// Cash Image.
		cashImage = new JLabel("");
		cashImage.setIcon(new ImageIcon(CashView.class.getResource("/resources/img/cashImage.png")));
		cashImage.setBounds(104, 53, 133, 135);
		getContentPane().add(cashImage);

		// Money in cash text.
		moneyIncashText = new JLabel("Money in cash.");
		moneyIncashText.setFont(textFont);
		moneyIncashText.setBounds(104, 37, 209, 41);
		moneyIncashText.setForeground(new Color(0,108,84));
		getContentPane().add(moneyIncashText);
		
		// Case 1 money.
		availableMoney = new JTextField();
		availableMoney.setEditable(false);
		// We get the cashValue from the getter of the clash Shop.
		availableMoney.setText(shop.getCashValue());
		availableMoney.setHorizontalAlignment(JTextField.CENTER);
		availableMoney.setBounds(120, 249, 96, 30);
		getContentPane().add(availableMoney);

		// Arrow image
		arrowImage = new JLabel("");
		arrowImage.setIcon(new ImageIcon(CashView.class.getResource("/resources/img/arrowImage.png")));
		arrowImage.setBounds(135, 173, 72, 72);
		getContentPane().add(arrowImage);

		// Ok button.
		okButton = new JToggleButton("OK");
		okButton.setBounds(117, 297, 104, 43);
		okButton.addActionListener(this);
		getContentPane().add(okButton);

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
			System.out.println("Error, font not found: " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent buttonInteraction) {

		if (buttonInteraction.getSource() == okButton) {
			dispose();

		}
	}
}
