import java.sql.*;

public class DataBaseManager {

    private static final String DB_URL = "jdbc:sqlite:app.db";
    private Connection connection;
    public DataBaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected.");
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
            createTables();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Close failed: " + e.getMessage());
        }
    }
    private void createTables() {
        String usersTable = """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            );
        """;
        String quizzesTable = """
            CREATE TABLE IF NOT EXISTS quizzes (
                quiz_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                quiz_title TEXT NOT NULL,
                subject TEXT,
                FOREIGN KEY (user_id) REFERENCES users(user_id)
            );
        """;
        String questionsTable = """
            CREATE TABLE IF NOT EXISTS questions (
                question_id INTEGER PRIMARY KEY AUTOINCREMENT,
                quiz_id INTEGER NOT NULL,
                question_text TEXT NOT NULL,
                option_a TEXT NOT NULL,
                option_b TEXT NOT NULL,
                option_c TEXT NOT NULL,
                option_d TEXT NOT NULL,
                correct_answer TEXT NOT NULL,
                FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
            );
        """;
        String scoresTable = """
            CREATE TABLE IF NOT EXISTS scores (
                score_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                quiz_id INTEGER NOT NULL,
                score INTEGER NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(user_id),
                FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
            );
        """;
        try (Statement state = connection.createStatement()) {
            state.execute(usersTable);
            state.execute(quizzesTable);
            state.execute(questionsTable);
            state.execute(scoresTable);
        } catch (SQLException e) {
            System.err.println("createTables failed: " + e.getMessage());
        }
    }
    public int getUserId(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserId failed: " + e.getMessage());
        }
        return -1;
    }
    public void addUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("addUser failed: " + e.getMessage());
        }
    }

    //for log in. Ckecking username and password
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("validateUser failed: " + e.getMessage());
            return false;
        }
    }

    //adds the quiz
    public int addQuiz(int userId, String quizTitle, String subject) {
        String sql = "INSERT INTO quizzes(user_id, quiz_title, subject) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, quizTitle);
            pstmt.setString(3, subject);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("addQuiz failed: " + e.getMessage());
        }
        return -1;
    }
    //sets the question to its quiz
    public void addQuestion(int quizId, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        String sql = """
            INSERT INTO questions(
                quiz_id, question_text,
                option_a, option_b, option_c, option_d,
                correct_answer
            )
            VALUES(?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            pstmt.setString(2, questionText);
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setString(7, correctAnswer);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("addQuestion failed: " + e.getMessage());
        }
    }
    public void saveScore(int userId, int quizId, int score) {
        String sql = "INSERT INTO scores(user_id, quiz_id, score) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, quizId);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("saveScore failed: " + e.getMessage());
        }
    }
}
