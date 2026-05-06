import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdminHomeController {
    private final DataBaseManager        db        = DataBaseManager.getInstance();
    public Scene buildScene() {
        Label welcome = new Label("Welcome Admin");
        Button newQuiz = new Button("Create New Quiz");
        ArrayList<DataBaseManager.Quiz> quizzes = db.getUserQuizzes(Session.currentUserId);
        Label result = new Label();
        VBox layout = new VBox(12);
        layout.getChildren().addAll(welcome, newQuiz, result);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        newQuiz.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ)
        );
        for (DataBaseManager.Quiz quiz : quizzes) {
            Button edit = new Button("Edit Quiz: " + quiz.getTitle());
            edit.setOnAction(e -> {
                Session.currentQuizId = quiz.getQuizId();
                SceneManager.getInstance().navigateTo(SceneType.ADMIN_EDIT);
            });
            layout.getChildren().add(edit);
        }
        Button adminResultsBtn = new Button("View Student Results");
        adminResultsBtn.setOnAction(e -> SceneManager.getInstance().navigateTo(SceneType.ADMIN_RESULTS));
        layout.getChildren().add(adminResultsBtn);
        return new Scene(layout, 400, 300);

    }

}