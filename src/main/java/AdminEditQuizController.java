import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdminEditQuizController {

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
        Label title = new Label("Edit Quiz");
        Label questionLabel = new Label();
        TextField qField = new TextField();
        TextField a1 = new TextField();
        TextField a2 = new TextField();
        TextField a3 = new TextField();
        TextField a4 = new TextField();

        ToggleGroup correctGroup = new ToggleGroup();

        RadioButton r1 = new RadioButton("A");
        RadioButton r2 = new RadioButton("B");
        RadioButton r3 = new RadioButton("C");
        RadioButton r4 = new RadioButton("D");

        r1.setToggleGroup(correctGroup);
        r2.setToggleGroup(correctGroup);
        r3.setToggleGroup(correctGroup);
        r4.setToggleGroup(correctGroup);

        Button next = new Button("Next");
        Button save = new Button("Save Changes");
        Button back = new Button("Back");
        int[] index = {0};
        Runnable load = () -> {
            Question q = questions.get(index[0]);
            qField.setText(q.question);
            a1.setText(q.answers.get(0));
            a2.setText(q.answers.get(1));
            a3.setText(q.answers.get(2));
            a4.setText(q.answers.get(3));
            correctGroup.selectToggle(null);
            questionLabel.setText("Question " + (index[0] + 1) + " / " + questions.size());
        };
        load.run();
        next.setOnAction(e -> {
            saveCurrentQuestion(questions, quizId, index[0], qField, a1, a2, a3, a4, correctGroup);
            if (index[0] < questions.size() - 1) {
                index[0]++;
                load.run();
            }
        });
        save.setOnAction(e -> {
            saveCurrentQuestion(questions, quizId, index[0], qField, a1, a2, a3, a4, correctGroup);
            SceneManager.getInstance().navigateFresh(SceneType.ADMIN_HOME);
        });
        back.setOnAction(e ->
                SceneManager.getInstance().navigateFresh(SceneType.ADMIN_HOME)
        );
        layout.getChildren().addAll(title, questionLabel, qField, a1, r1, a2, r2, a3, r3, a4, r4, next, save, back);
        return new Scene(layout, 500, 500);
    }
    private void saveCurrentQuestion(ArrayList<Question> questions, int quizId, int index, TextField qField, TextField a1, TextField a2, TextField a3, TextField a4, ToggleGroup group) {
        Question q = questions.get(index);
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected == null) return;
        String correct = switch (selected.getText()) {
            case "A" -> a1.getText();
            case "B" -> a2.getText();
            case "C" -> a3.getText();
            case "D" -> a4.getText();
            default -> "";
        };
        db.updateQuestion(q.questionId, qField.getText(), a1.getText(), a2.getText(), a3.getText(), a4.getText(), correct);
    }
}