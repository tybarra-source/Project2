import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HomeController {
    private final DataBaseManager        db        = DataBaseManager.getInstance();
    public Scene buildScene() {
        Label welcome = new Label("Welcome");
        Button logoutBtn = new Button("Log Out");
        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.getChildren().add(welcome);
        ArrayList<DataBaseManager.Quiz> quizzes = db.getPublicQuizzes();        for (DataBaseManager.Quiz quiz : quizzes) {
            Button quizButton = new Button("Take: " + quiz.getTitle());
            quizButton.setOnAction(e -> {
                Session.currentQuizId = quiz.getQuizId();
                SceneManager.getInstance().navigateFresh(SceneType.TAKE_QUIZ);
            });
            layout.getChildren().add(quizButton);
        }

        Button newQuiz = new Button("Create New Quiz");
        newQuiz.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ)
        );
        layout.getChildren().add(newQuiz);
        Button resultsBtn = new Button("My Results");
        resultsBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.USER_RESULTS));
        layout.getChildren().add(resultsBtn);
        if (db.isAdmin(Session.currentUserId)) {
            Button adminResultsBtn = new Button("View Student Results");
            adminResultsBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.ADMIN_RESULTS));
            layout.getChildren().add(adminResultsBtn);
        }
        logoutBtn.setOnAction(e -> {
            Session.currentUserId = -1;
            Session.currentUsername = null;
            Session.currentQuizId = -1;
            Session.quizIndex = 0;
            Session.finalScore = 0;
            Session.totalQuestions = 0;
            SceneManager.getInstance().navigateFresh(SceneType.LOGIN);
        });
        layout.getChildren().add(logoutBtn);
        return new
                Scene(layout, 500, 500);
    }
}
