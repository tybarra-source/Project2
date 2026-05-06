import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdminResultsController {
    private final DataBaseManager db = new DataBaseManager();
    public Scene buildScene() {
        Label welcome = new Label("Welcome Admin!");
        welcome.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 22px; -fx-font-weight: bold;");
        // this creates a dropdown of all the student's usernames
        ComboBox<String> studentDropdown = new ComboBox<>();
        ArrayList<String> usernames = db.getAllUsernames();
        studentDropdown.getItems().addAll(usernames);
        studentDropdown.setPromptText("Select a student...");

        Label selectPrompt = new Label("Select a student to view their results.");
        selectPrompt.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        VBox resultsBox = new VBox(10, selectPrompt);
        resultsBox.setAlignment(Pos.CENTER);

        // actually making that dropdown clickable
        studentDropdown.setOnAction(e -> {
            String selectedUsername = studentDropdown.getValue();
            if (selectedUsername == null) return;

            int userId = db.getUserId(selectedUsername);
            ArrayList<DataBaseManager.Quiz> quizzes = db.getQuizzesUserTook(userId);

            // showing the avg score the student has
            int total = 0;
            int count = 0;
            for (DataBaseManager.Quiz quiz : quizzes) {
                int score = db.getScore(userId, quiz.getQuizId());
                int totalQuestions = db.getQuestionsForQuiz(quiz.getQuizId()).size();
                if (score != -1 && totalQuestions > 0) {
                    total += (int) Math.round((double) score / totalQuestions * 100);
                    count++;
                }
            }
            double avg = count > 0 ? (double) total / count : 0;

            Label avgLabel = new Label("Average Score: " + avg + "%");
            avgLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // building the same sort of table/grid i made in the userResultsController to actually display everything
            Label quizHeader = new Label("Quiz");
            Label scoreHeader = new Label("Score");
            quizHeader.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14px; -fx-font-style: italic; -fx-font-weight: bold;");
            scoreHeader.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14px; -fx-font-style: italic; -fx-font-weight: bold;");

            GridPane table = new GridPane();
            table.setHgap(20);
            table.setVgap(10);
            table.add(quizHeader, 0, 0);
            table.add(scoreHeader, 1, 0);

            int row = 1;
            for (DataBaseManager.Quiz quiz : quizzes) {
                int score = db.getScore(userId, quiz.getQuizId());
                int totalQuestions = db.getQuestionsForQuiz(quiz.getQuizId()).size();

                String scoreDisplay;
                if (score == -1 || totalQuestions == 0) {
                    scoreDisplay = "incomplete quiz";
                } else {
                    int percentage = (int) Math.round((double) score / totalQuestions * 100);
                    scoreDisplay = percentage + "%";
                }

                table.add(new Label(quiz.getTitle()), 0, row);
                table.add(new Label(scoreDisplay), 1, row);
                row++;
            }
            // center the table by wrapping in hbox
            HBox tableWrapper = new HBox(table);
            tableWrapper.setAlignment(Pos.CENTER);

            resultsBox.getChildren().setAll(avgLabel, tableWrapper);
        });
            Button backBtn = new Button("Back to Home");
        backBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.ADMIN_HOME));

            VBox layout = new VBox(25, welcome, studentDropdown, resultsBox, backBtn);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(50, 30, 30, 30));

            return new Scene(layout, 500, 600);
        }
}
