import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Home {
    public static Scene getScene(Stage stage) {
        Label welcome = new Label("Welcome");
        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        DataBaseManager db = new DataBaseManager();
        root.getChildren().add(welcome);
        ArrayList<DataBaseManager.Quiz> quizzes = db.getUserQuizzes(Session.currentUserId);
        for (DataBaseManager.Quiz quiz : quizzes) {

            Button quizButton = new Button("Take: " + quiz.getTitle());

            quizButton.setOnAction(e -> {
                Session.currentQuizId = quiz.getQuizId();
                stage.setScene(TakeQuizScene.getScene(stage, quiz.getQuizId()));
            });

            root.getChildren().add(quizButton);
        }

        Button newQuiz = new Button("Create New Quiz");
        newQuiz.setOnAction(e ->
                stage.setScene(SceneFactory.create(SceneType.CREATE_QUIZ, stage))
        );
        root.getChildren().add(newQuiz);
        return new Scene(root, 500, 500);
    }
}