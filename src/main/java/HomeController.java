import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HomeController {
    public Scene buildScene() {
        Label welcome = new Label("Welcome");
        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        DataBaseManager db = new DataBaseManager();
        layout.getChildren().add(welcome);
        ArrayList<DataBaseManager.Quiz> quizzes = db.getUserQuizzes(Session.currentUserId);
        for (DataBaseManager.Quiz quiz : quizzes) {
            Button quizButton = new Button("Take: " + quiz.getTitle());
            quizButton.setOnAction(e -> {
                Session.currentQuizId = quiz.getQuizId();
                SceneManager.getInstance().navigateTo(SceneType.TAKE_QUIZ);
            });
            layout.getChildren().add(quizButton);
        }

        Button newQuiz = new Button("Create New Quiz");
        newQuiz.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ)
        );
        layout.getChildren().add(newQuiz);
        return new
                Scene(layout, 500, 500);
    }
}
