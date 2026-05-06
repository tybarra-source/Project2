import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UserResultsController {
    private final DataBaseManager db = new DataBaseManager();
    public Scene buildScene() {
        Label welcome = new Label("Welcome " + Session.currentUsername);
        int userId = Session.currentUserId; // getting the id of the user currently logged in
        ArrayList<DataBaseManager.Quiz> quizzes = db.getUserQuizzes(userId);  // getting all the quizzes the current user has ever taken

        // getting the avg score of the user
        int total = 0;
        int count = 0;
        double avg;
        for (DataBaseManager.Quiz quiz : quizzes) {
            int score = db.getScore(userId, quiz.getQuizId());
            int totalQuestions = db.getQuestionsForQuiz(quiz.getQuizId()).size();
            if (score != -1 && totalQuestions > 0) {
                total += (int) Math.round((double) score / totalQuestions * 100);
                count++;
            }
        }
        if (count > 0) {
            avg = (double) total / count;
        } else {
            avg = 0;
        }

         // displays the avg score
        Label avgLabel = new Label("Average Score: " + avg + "%");
        avgLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
         // displays the quizzes the user has made & their scores
        Label createdQuizLabel = new Label("Quizzes You Created:");
        createdQuizLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        // creating the display to see the quizzes and scores
        GridPane createdTable = new GridPane();
        createdTable.setHgap(20);
        createdTable.setVgap(10);

        Label quizHeader = new Label("Quiz");
        Label scoreHeader = new Label("Score");
        quizHeader.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 20px; -fx-font-style: italic; -fx-font-weight: bold;");
        scoreHeader.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 20px; -fx-font-style: italic; -fx-font-weight: bold;");
        createdTable.add(quizHeader, 0, 0);
        createdTable.add(scoreHeader, 1, 0);


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

            createdTable.add(new Label(quiz.getTitle()), 0, row);
            createdTable.add(new Label(scoreDisplay), 1, row);
            row++;
        }

        // displays the quizzes the user has taken
        Label takenQuizLabel = new Label("Quizzes You've Taken:");
        takenQuizLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        GridPane takenTable = new GridPane();
        takenTable.setHgap(20);
        takenTable.setVgap(10);
        takenTable.add(new Label("Quiz"), 0, 0);
        takenTable.add(new Label("Score"), 1, 0);

        Button backBtn = new Button("Back to Home");
        backBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.HOME));

        welcome.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 22px; -fx-font-weight: bold;");

        // wraping the table in HBox to center it on the page
        HBox createdTableWrapper = new HBox(createdTable);
        createdTableWrapper.setAlignment(Pos.CENTER);

        HBox takenTableWrapper = new HBox(takenTable);
        takenTableWrapper.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, welcome, avgLabel, createdQuizLabel, createdTableWrapper, takenQuizLabel, takenTableWrapper, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 30, 30, 30));
        return new Scene(layout, 500, 600);
    }
}
