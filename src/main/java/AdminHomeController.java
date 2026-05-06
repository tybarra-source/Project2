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
        Button logoutBtn = new Button("Log Out");
        VBox layout = new VBox(12);
        layout.getChildren().addAll(welcome, newQuiz, result, logoutBtn);
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
        logoutBtn.setOnAction(e -> {
            Session.currentUserId = -1;
            Session.currentUsername = null;
            Session.currentQuizId = -1;
            Session.quizIndex = 0;
            Session.finalScore = 0;
            Session.totalQuestions = 0;
            SceneManager.getInstance().navigateFresh(SceneType.LOGIN);
        });
        return new Scene(layout, 400, 300);

    }

}