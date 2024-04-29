package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Shop;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class CashView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	private JTextField availableMoney;
	
	Shop shop = new Shop();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				CashView cash = new CashView();
				cash.setVisible(true);
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public CashView() {
		
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
		setResizable(false);
		// Window at the center of the screen.
		setLocationRelativeTo(null);
		// We define our background color.
		setBackground(new Color(237, 237, 233));

	}

	public void cashViewUI() {
		
		Font textFont = new Font("Poppins", Font.PLAIN, 17);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		
		// Pane for the buttons.
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(-177, 300, 411, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		// Ok button.
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		// Cancel Button.
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.setActionCommand("Cancel");

		// Cash Image.
		JLabel cashImage = new JLabel("");
		cashImage.setIcon(new ImageIcon(CashView.class.getResource("/resources/img/cashImage.png")));
		cashImage.setBounds(104, 53, 133, 135);
		getContentPane().add(cashImage);
		
		// Money in cash text.
		JLabel moneyIncashText = new JLabel("Money in cash.");
		moneyIncashText.setFont(textFont);
		moneyIncashText.setBounds(104, 37, 209, 41);
		getContentPane().add(moneyIncashText);
		
		// case1 money.
		availableMoney = new JTextField();
		availableMoney.setEditable(false);
		availableMoney.setText(shop.getCashValue());
		availableMoney.setHorizontalAlignment(JTextField.CENTER);
		availableMoney.setBounds(120, 249, 96, 30);
		getContentPane().add(availableMoney);

		// Row image
		JLabel rowImage = new JLabel("");
		rowImage.setIcon(new ImageIcon(CashView.class.getResource("/resources/img/rowImage.png")));
		rowImage.setBounds(135, 173, 72, 72);
		getContentPane().add(rowImage);
		
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
}
