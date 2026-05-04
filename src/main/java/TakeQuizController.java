import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TakeQuizController {
    public Scene buildScene( int quizId) {
        DataBaseManager db = new DataBaseManager();
        ArrayList<Question> questions = db.getQuestionsForQuiz(quizId);
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        if (questions.isEmpty()) {
            layout.getChildren().add(new Label("No questions in this Quiz"));
            return new Scene(layout, 400, 300);
        }
        final int[] currentIndex = {0};
        final int[] score = {0};
        Label title = new Label("Taking Quiz");
        Label questionLabel = new Label();

        ToggleGroup group = new ToggleGroup();
        RadioButton a = new RadioButton();
        RadioButton b = new RadioButton();
        RadioButton c = new RadioButton();
        RadioButton d = new RadioButton();

        a.setToggleGroup(group);
        b.setToggleGroup(group);
        c.setToggleGroup(group);
        d.setToggleGroup(group);

        Label status = new Label();
        Button nextButton = new Button("Next Question");
        Button backButton = new Button("Back Home");
        Runnable loadQuestion = () -> {
            Question q = questions.get(currentIndex[0]);
            questionLabel.setText((currentIndex[0] + 1) + ". " + q.question);
            a.setText(q.answers.get(0));
            b.setText(q.answers.get(1));
            c.setText(q.answers.get(2));
            d.setText(q.answers.get(3));
            group.selectToggle(null);
            status.setText("");
            if (currentIndex[0] == questions.size() - 1) {
                nextButton.setText("Finish Quiz");
            } else {
                nextButton.setText("Next Question");
            }
        };
        nextButton.setOnAction(e -> {
            Question currentQuestion = questions.get(currentIndex[0]);
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
                score[0]++;
            }
            if (currentIndex[0] == questions.size() - 1) {
                db.saveScore(Session.currentUserId, quizId, score[0]);
                VBox finishRoot = new VBox(20);
                finishRoot.setAlignment(Pos.CENTER);
                finishRoot.setPadding(new Insets(30));
                Label finished = new Label("Quiz Finished!");
                Label finalScore = new Label("Score: " + score[0] + " / " + questions.size());
                Button homeButton = new Button("Back Home");
                homeButton.setOnAction(home ->
                        SceneManager.getInstance().navigateTo(SceneType.HOME)
                );

            } else {
                currentIndex[0]++;
                loadQuestion.run();
            }
        });
        backButton.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.HOME)
                );
        layout.getChildren().addAll(title, questionLabel, a, b, c, d, nextButton, backButton, status);
        loadQuestion.run();
        return new Scene(layout, 500, 450);
    }
}