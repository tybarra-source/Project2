import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TakeQuizController {
    private final DataBaseManager db = DataBaseManager.getInstance();
    public Scene buildScene(int quizId) {
        ArrayList<Question> questions = db.getQuestionsForQuiz(quizId);
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        if (questions == null || questions.isEmpty()) {
            layout.getChildren().add(new Label("No questions in this Quiz"));
            return new Scene(layout, 400, 300);
        }
        Session.quizIndex = 0;
        Session.finalScore = 0;
        Label title = new Label("Taking Quiz");
        Label questionLabel = new Label();
        Label status = new Label();
        ToggleGroup group = new ToggleGroup();
        RadioButton a = new RadioButton();
        RadioButton b = new RadioButton();
        RadioButton c = new RadioButton();
        RadioButton d = new RadioButton();

        a.setToggleGroup(group);
        b.setToggleGroup(group);
        c.setToggleGroup(group);
        d.setToggleGroup(group);
        Button nextButton = new Button("Next Question");
        Button backButton = new Button("Back Home");
        Runnable loadQuestion = () -> {
            Question q = questions.get(Session.quizIndex);
            questionLabel.setText((Session.quizIndex + 1) + ". " + q.question);
            a.setText(q.answers.get(0));
            b.setText(q.answers.get(1));
            c.setText(q.answers.get(2));
            d.setText(q.answers.get(3));
            group.selectToggle(null);
            status.setText("");
            if (Session.quizIndex == questions.size() - 1) {
                nextButton.setText("Finish Quiz");
            } else {
                nextButton.setText("Next Question");
            }
        };
        loadQuestion.run();
        nextButton.setOnAction(e -> {
            Question currentQuestion = questions.get(Session.quizIndex);
            int selectedIndex = -1;
            if (a.isSelected()) selectedIndex = 0;
            else if (b.isSelected()) selectedIndex = 1;
            else if (c.isSelected()) selectedIndex = 2;
            else if (d.isSelected()) selectedIndex = 3;
            if (selectedIndex == -1) {
                status.setText("Select an answer first.");
                return;
            }
            if (currentQuestion.correctIndexes.contains(selectedIndex)) {
                Session.finalScore++;
            }
            if (Session.quizIndex == questions.size() - 1) {
                db.saveScore(Session.currentUserId, quizId, Session.finalScore);

                Session.totalQuestions = questions.size();
                SceneManager.getInstance().navigateFresh(SceneType.FINISH);
                return;
            }
            Session.quizIndex++;
            loadQuestion.run();
        });
        backButton.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.HOME)
        );
        layout.getChildren().addAll(title, questionLabel, a, b, c, d, nextButton, backButton, status);
        return new Scene(layout, 500, 450);
    }
}