package flappyBird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	int boardWidth = 360;
	int boardHeight = 640;
	int floorHeight = 100;

	// images

	Image backgroundImg;
	Image birdImg;
	Image topPipeImg;
	Image bottomPipeImg;
	Image floorImg;

	// bird

	int birdX = boardWidth / 8;
	double birdY = boardHeight / 2;
	int birdWidth = 34;
	int birdHeight = 24;

	class Bird {
		int x = birdX;
		double y = birdY;
		int width = birdWidth;
		int height = birdHeight;
		Image img;

		Bird(Image img) {
			this.img = img;
		}
	}

	JLabel gameOver;
	JLabel scoreLabel;

	Bird bird;
	int velocityY = 0;

	private Timer gameLoop;
	JButton jump = new JButton();
	JButton replay = new JButton("Replay");

	Random random = new Random();
	double pipeGap = 150.0;

	int newWidth = 50;
	int pipeWidth = newWidth;
	double pipeSpeed = 5.0;

	int score = 0;
	boolean gameEnded = false;

	class Pipe {
		double x;
		double topY;
		double bottomY;
		boolean passed;

		Pipe(double x) {
			this.x = x;
			randomizeY();
		}

		void randomizeY() {
			topY = random.nextDouble() * (boardHeight - pipeGap - 200) + 100.0;
			bottomY = topY + pipeGap;
			passed = false;
		}
	}

	List<Pipe> pipes = new ArrayList<>();
	int PIPE_COUNT = 3;
	double PIPE_SPACING = 250.0;
	double START_X = 300.0;

	double vy = 0.0;
	double ay = 0.6;
	double maxVy = 10.0;
	double lift = -7.0;
	double angleRad = 0.0;
	boolean spaceDown = false;

	private String username;

	FlappyBird(String username) {
		this.username = username;

		setPreferredSize(new Dimension(boardWidth, boardHeight));

		backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
		birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();
		floorImg = new ImageIcon(getClass().getResource("/floor.png")).getImage();

		gameOver = new JLabel("Game Over!");
		gameOver.setBounds(0, boardHeight / 2 - 80, boardWidth, 50);
		gameOver.setHorizontalAlignment(JLabel.CENTER);
		gameOver.setFont(new Font("Arial", Font.BOLD, 26));
		gameOver.setForeground(Color.BLACK);
		gameOver.setVisible(false);
		add(gameOver);

		scoreLabel = new JLabel("0");
		scoreLabel.setBounds(0, 10, boardWidth, 40);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);

		bird = new Bird(birdImg);
		jump.setVisible(false);
		jump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vy = lift;
			}
		});
		add(jump);

		replay.setBounds(boardWidth / 2 - 40, boardHeight / 2 - 20, 80, 30);
		replay.setVisible(false);
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameEnded)
					resetGame();
			}
		});
		add(replay);

		setFocusable(true);
		requestFocusInWindow();

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed SPACE"), "spacePressed");
		getActionMap().put("spacePressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!spaceDown) {
					vy = lift;
					spaceDown = true;
				}
			}
		});
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SPACE"), "spaceReleased");
		getActionMap().put("spaceReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spaceDown = false;
			}
		});

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restart");
		getActionMap().put("restart", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameEnded)
					resetGame();
			}
		});

		for (int i = 0; i < PIPE_COUNT; i++) {
			pipes.add(new Pipe(START_X + i * PIPE_SPACING));
		}

		gameLoop = new Timer(1000 / 60, this);
		gameLoop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
		drawPipes(g);
		drawFlappy(g);

		g.drawImage(floorImg, 0, boardHeight - floorHeight, boardWidth, floorHeight, null);

	}

	public void drawFlappy(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		int cx = birdX + birdWidth / 2;
		int cy = (int) birdY + birdHeight / 2;
		g2.rotate(angleRad, cx, cy);
		g2.drawImage(birdImg, birdX, (int) birdY, birdWidth, birdHeight, null);
		g2.dispose();
	}

	public void drawPipes(Graphics g) {
		int originalWidth = topPipeImg.getWidth(null);
		int originalHeight = topPipeImg.getHeight(null);
		int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);
		for (Pipe p : pipes) {
			g.drawImage(topPipeImg, (int) p.x, 0, newWidth, (int) p.topY, null);
			g.drawImage(bottomPipeImg, (int) p.x, (int) p.bottomY, newWidth, newHeight, null);
		}
	}

	private boolean collidesWithPipe(double bx, double by, int bw, int bh, Pipe p) {
		double bx2 = bx + bw;
		double by2 = by + bh;
		boolean hitTop = (bx2 >= p.x && bx <= p.x + pipeWidth && by <= p.topY);
		boolean hitBottom = (bx2 >= p.x && bx <= p.x + pipeWidth && by2 >= p.bottomY);
		return hitTop || hitBottom;
	}

	private void endGame() {
		if (gameLoop.isRunning())
			gameLoop.stop();
		gameOver.setVisible(true);
		replay.setVisible(true);
		gameEnded = true;

		Database.updateHighScore(username, score);

	}

	private void resetGame() {
		birdY = boardHeight / 2.0;
		vy = 0.0;

		for (int i = 0; i < pipes.size(); i++) {
			Pipe p = pipes.get(i);
			p.x = START_X + i * PIPE_SPACING;
			p.randomizeY();
		}

		score = 0;
		scoreLabel.setText("0");

		gameEnded = false;
		gameOver.setVisible(false);
		replay.setVisible(false);

		pipeSpeed = 5.0;
		gameLoop.start();
		requestFocusInWindow();
	}

	public void actionPerformed(ActionEvent e) {
		for (Pipe p : pipes)
			p.x -= pipeSpeed;

		double maxX = -1e9;
		for (Pipe p : pipes)
			if (p.x > maxX)
				maxX = p.x;

		for (Pipe p : pipes) {
			if (p.x < -pipeWidth) {
				p.x = maxX + PIPE_SPACING;
				p.randomizeY();
				maxX = p.x;
			}
		}

		vy += ay;
		if (vy > maxVy)
			vy = maxVy;
		birdY += vy;
		double t = Math.max(-25.0, Math.min(90.0, vy * 8.0));
		angleRad = Math.toRadians(t);

		if (birdY < 0)
			birdY = 0;

		double groundY = boardHeight - floorHeight - birdHeight;
		if (birdY >= groundY) {
			birdY = groundY; // sit on the floor nicely
			endGame();
		}

		for (Pipe p : pipes) {
			if (!gameEnded && collidesWithPipe(birdX, birdY, birdWidth, birdHeight, p)) {
				endGame();
				break;
			}
		}

		for (Pipe p : pipes) {
			if (!p.passed && p.x + pipeWidth < birdX) {
				p.passed = true;
				score++;
				scoreLabel.setText(String.valueOf(score));

				// make the game harder
				if (score % 5 == 0) {
					pipeSpeed += 0.2;
					if (score % 10 == 0 && PIPE_SPACING >= 180) {
						PIPE_SPACING -= 14;
					}
				}
			}
		}

		revalidate();
		repaint();
	}
}
