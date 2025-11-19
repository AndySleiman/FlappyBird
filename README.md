# 🐦 Flappy Bird — Java Swing Edition

A fully playable *Flappy Bird* clone written in *Java (Swing)* with a **login system**, **SQLite database**, **per-user high scores**, and smooth physics.

This project was built entirely from scratch for learning and portfolio purposes.

---

## ⭐ Features

✅ *User Accounts (Signup + Login GUI)*
Users can create accounts and automatically save their high scores locally.

✅ *SQLite Database Integration*

* users table
* stores username, password, and best score
* database auto-creates on first run
* no external server required

✅ *Smooth Flappy Bird Physics*

* gravity, lift, max velocity limits
* rotating bird sprite
* frame-based updates (60 FPS)

✅ *Pipes + Difficulty Scaling*

* pipes spawn continuously
* gap reduces over time
* pipes speed up every 5 points
* spacing shrinks every 10 points

✅ *Restart + Game Over UI*

* "Replay" button
* score label
* animated pipe movement

---

## 🎮 Controls

| Key   | Action               |
| ----- | -------------------- |
| SPACE | Jump / flap          |
| R     | Restart after losing |

---

## 📦 Project Structure


src/flappyBird/

* Main.java

* FlappyBird.java

* LoginFrame.java

* SignupFrame.java

* GameFrame.java

* Database.java

resources/

* flappybirdbg.png

* flappybird.png

* toppipe.png

* bottompipe.png

* floor.png

lib/

* sqlite-jdbc.jar

game.db (auto-generated — ignored by git)


---

## 🚀 How to Run the Game

### 1. Install Java

Requires *JDK 17+* (JDK 22 tested).

### 2. Clone the repository

bash
git clone https://github.com/AndySleiman/FlappyBird.git
cd FlappyBird


### 3. Open the project in Eclipse / IntelliJ

### 4. Make sure SQLite JDBC is on your classpath

If using Eclipse
→ Right-click project → Build Path → Add External JAR…
→ Select: lib/sqlite-jdbc.jar

### 5. Run the game

Run:


Main.java


### 6. Create an account → login → play!

A local SQLite file game.db is created automatically and saved in the project folder.

---

## 🗄 Database Schema


users (
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   username TEXT UNIQUE NOT NULL,
   password TEXT NOT NULL,
   high_score INTEGER NOT NULL DEFAULT 0
)


---

## 🔧 Future Improvements / Ideas

* Sound effects (flap, hit, point)
* Animated bird sprite (wing flapping)
* Leaderboard screen
* Difficulty settings (easy / hard)
* Export as runnable JAR

---

## 📜 License

This project is licensed under the *MIT License*.

---

## 🙋 Author

*Andy Sleiman*
Computer Science Student
American University of Beirut (AUB)
