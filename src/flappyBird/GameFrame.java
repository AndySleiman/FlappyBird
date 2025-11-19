package flappyBird;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private String username;

	public GameFrame(String username) {
		this.username = username;

		setTitle("Flappy Bird");
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Create game panel with username
		FlappyBird game = new FlappyBird(username);
		add(game);

		pack();
		setVisible(true);
	}

}
