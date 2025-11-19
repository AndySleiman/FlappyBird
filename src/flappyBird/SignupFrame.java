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

public class SignupFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;

	public SignupFrame() {
		setTitle("Create Account");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(4, 1));

		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("Choose username:"));
		usernameField = new JTextField(15);
		userPanel.add(usernameField);

		JPanel passPanel = new JPanel();
		passPanel.add(new JLabel("Choose password:"));
		passwordField = new JPasswordField(15);
		passPanel.add(passwordField);

		JPanel buttonPanel = new JPanel();
		JButton createBtn = new JButton("Create");
		JButton backBtn = new JButton("Back");

		buttonPanel.add(createBtn);
		buttonPanel.add(backBtn);

		add(userPanel);
		add(passPanel);
		add(buttonPanel);

		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerUser();
			}
		});

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginFrame().setVisible(true);
				dispose();
			}
		});
	}

	private void registerUser() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());

		if (Database.registerUser(username, password)) {
			JOptionPane.showMessageDialog(this, "Account created!");
			new LoginFrame().setVisible(true);
			dispose();

		} else {
			JOptionPane.showMessageDialog(this, "Username already exists.");
		}
	}
}
