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
        ArrayList<DataBaseManager.Quiz> takenQuizzes = db.getQuizzesUserTook(userId); // getting all the quizzes the current user taken

        // avg score
        int total = 0;
        for (DataBaseManager.Quiz quiz : takenQuizzes) {
            int score = db.getScore(userId, quiz.getQuizId());
            int totalQuestions = db.getQuestionsForQuiz(quiz.getQuizId()).size();
            if (score != -1 && totalQuestions > 0)
                total += (int) Math.round((double) score / totalQuestions * 100);
        }
        double avg;
        if (takenQuizzes.isEmpty()) {
            avg = 0;
        } else {
            avg = (double) total / takenQuizzes.size();
        }

        // creating the table thats gonna show everything
        GridPane table = new GridPane();
        table.setHgap(20);
        table.setVgap(10);
        table.add(new Label("Quiz"), 0, 0);
        table.add(new Label("Score"), 1, 0);

        // getting the score info from quizzes
        int row = 1;
        for (DataBaseManager.Quiz quiz : takenQuizzes) {
            int score = db.getScore(userId, quiz.getQuizId());
            int totalQuestions = db.getQuestionsForQuiz(quiz.getQuizId()).size();
            String show;
            if (score == -1 || totalQuestions == 0) {
                show = "incomplete";
            } else {
                show = (int) Math.round((double) score / totalQuestions * 100) + "%";
            }
            table.add(new Label(quiz.getTitle()), 0, row);
            table.add(new Label(show), 1, row++);
        }

        // wrapping everything in hbox so that its all centered
        HBox tableWrapper = new HBox(table);
        tableWrapper.setAlignment(Pos.CENTER);

        welcome.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 22px; -fx-font-weight: bold;");
        Label avgLabel = new Label("Average Score: " + avg + "%");
        avgLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label takenLabel = new Label("Quizzes You've Taken:");
        takenLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        Button backBtn = new Button("Back to Home");
        backBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.HOME));

        VBox layout = new VBox(25, welcome, avgLabel, takenLabel, tableWrapper, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 30, 30, 30));
        return new Scene(layout, 500, 600);
    }
}