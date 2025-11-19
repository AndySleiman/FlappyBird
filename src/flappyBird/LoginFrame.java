package flappyBird;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JTextField passwordField;

	public LoginFrame() {
		setTitle("Login");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(4, 1));

		// username
		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("Username: "));
		usernameField = new JTextField(15);
		userPanel.add(usernameField);

		// password
		JPanel passPanel = new JPanel();
		passPanel.add(new JLabel("Password: "));
		passwordField = new JPasswordField(15);
		passPanel.add(passwordField);

		// buttons

		JPanel buttonPanel = new JPanel();
		JButton loginBtn = new JButton("Login");
		JButton signupBtn = new JButton("Sign up");

		buttonPanel.add(loginBtn);
		buttonPanel.add(signupBtn);

		add(userPanel);
		add(passPanel);
		add(buttonPanel);

		// login logic

		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginUser();
			}
		});

		// go to signup window

		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SignupFrame().setVisible(true);
				dispose();
			}
		});

	}

	private void loginUser() {
		String username = usernameField.getText();
		String password = new String(((JPasswordField) passwordField).getPassword());

		if (Database.login(username, password)) {

			// start the game after login

			int boardWidth = 360;
			int boardHeight = 640;

			JFrame frame = new JFrame("FlappyBird");
			frame.setSize(boardWidth, boardHeight);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			FlappyBird flappyBird = new FlappyBird(username); // your existing game panel
			frame.add(flappyBird);
			frame.pack();
			frame.setVisible(true);

			dispose(); // close login window

		} else {
			JOptionPane.showMessageDialog(this, "Invalid username or password.");
		}
	}
}
