package flappyBird;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	// game.db will be created in your project folder
	private static final String URL = "jdbc:sqlite:game.db";

	// This runs once when the class is loaded
	static {
		createUsersTable();
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
	}

	// Create table if it doesn't exist
	private static void createUsersTable() {
		String sql = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "username TEXT UNIQUE NOT NULL, " + "password TEXT NOT NULL, "
				+ "high_score INTEGER NOT NULL DEFAULT 0" + ");";

		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace(); // <-- IMPORTANT: now we see the real error
		}
	}

	// SIGN UP: returns true if user created
	public static boolean registerUser(String username, String password) {
		String sql = "INSERT INTO users (username, password, high_score) " + "VALUES (?, ?, 0);";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace(); // see EXACT reason in console
			return false;
		}
	}

	// LOGIN
	public static boolean login(String username, String password) {
		String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?;";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int getHighScore(String username) {
		String sql = "SELECT high_score FROM users WHERE username = ?;";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("high_score");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void updateHighScore(String username, int newScore) {
		int oldScore = getHighScore(username);

		if (newScore > oldScore) {
			String sql = "UPDATE users SET high_score = ? WHERE username = ?;";

			try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, newScore);
				ps.setString(2, username);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Debug helper: print all users
	public static void printAllUsers() {
		String sql = "SELECT id, username, password, high_score FROM users;";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
