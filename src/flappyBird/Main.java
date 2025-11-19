package flappyBird;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		// Start the GUI on the Swing event thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginFrame().setVisible(true);
			}
		});
	}
}
