import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CreateYourOwnQuizController {

    static ArrayList<Question> questions = new ArrayList<>();
    private final DataBaseManager        db        = DataBaseManager.getInstance();    public Scene buildScene() {
        Label title = new Label("Create Your Own Quiz");
        ComboBox<String> subjectBox = new ComboBox<>();
        subjectBox.getItems().addAll("Math", "Physics", "Literature", "Music", "Custom", "From Existing Quiz");
        TextField customSubject = new TextField();
        customSubject.setPromptText("Or custom subject");
        customSubject.setVisible(false);
        customSubject.setManaged(false);





        subjectBox.setOnAction(e -> {
            if ("Custom".equals(subjectBox.getValue())) {
                customSubject.setVisible(true);
                customSubject.setManaged(true);
            } else {
                customSubject.setVisible(false);
                customSubject.setManaged(false);
                customSubject.clear();
            }
        });

        subjectBox.setOnAction(e ->{
            if ("From Existing Quiz".equals(subjectBox.getValue())){
                SceneManager.getInstance().navigateTo(SceneType.CREATE_FROM_EXISTING);
            }
        });
        CheckBox multipleAnswers = new CheckBox("Multiple correct answers");
        TextField questionField = new TextField();
        questionField.setPromptText("Question");

        TextField a1 = new TextField();
        TextField a2 = new TextField();
        TextField a3 = new TextField();
        TextField a4 = new TextField();

        CheckBox c1 = new CheckBox();
        CheckBox c2 = new CheckBox();
        CheckBox c3 = new CheckBox();
        CheckBox c4 = new CheckBox();

        HBox row1 = new HBox(10, a1, c1);
        HBox row2 = new HBox(10, a2, c2);
        HBox row3 = new HBox(10, a3, c3);
        HBox row4 = new HBox(10, a4, c4);

        Label status = new Label();
        Button addQuestion = new Button("Add Question");
        Button finish = new Button("Finish Quiz");
        Button backHome = new Button("Back");

        addQuestion.setOnAction(e -> {
            String subject = subjectBox.getValue();
            if (subject == null || subject.isEmpty()) {
                subject = customSubject.getText();
            }
            if ("Custom".equals(subject)) {
                subject = customSubject.getText();
            }
            if (subject == null || subject.isEmpty()) {
                status.setText("Select or enter a subject");
                return;
            }
            if (questionField.getText().isEmpty()) {
                status.setText("Enter a question");
                return;
            }

            ArrayList<String> answers = new ArrayList<>();
            answers.add(a1.getText());
            answers.add(a2.getText());
            answers.add(a3.getText());
            answers.add(a4.getText());
            ArrayList<Integer> correctIndexes = new ArrayList<>();
            if (c1.isSelected()) correctIndexes.add(0);
            if (c2.isSelected()) correctIndexes.add(1);
            if (c3.isSelected()) correctIndexes.add(2);
            if (c4.isSelected()) correctIndexes.add(3);

            if (correctIndexes.isEmpty()) {
                status.setText("Select at least one correct answer");
                return;
            }
            if (!multipleAnswers.isSelected() && correctIndexes.size() > 1) {
                status.setText("Only one correct answer allowed");
                return;
            }
            Question q = new Question(questionField.getText(), answers, correctIndexes, subject, multipleAnswers.isSelected());
            questions.add(q);
            questionField.clear();
            a1.clear(); a2.clear(); a3.clear(); a4.clear();
            c1.setSelected(false);
            c2.setSelected(false);
            c3.setSelected(false);
            c4.setSelected(false);
            status.setText("Question added: " + questions.size());
        });
        finish.setOnAction(e -> {
            if (questions.isEmpty()) {
                status.setText("Add at least one question");
                return;
            }
            String subject = subjectBox.getValue();
            if (subject == null || "Custom".equals(subject)) {
                subject = customSubject.getText();
            }
            if (subject == null || subject.isEmpty()) {
                status.setText("Select or enter a subject");
                return;
            }
            int userId = Session.currentUserId;
            int quizId = db.addQuiz(userId, subject + " Quiz", subject);
            if (quizId == -1) {
                status.setText("Failed to save quiz");
                return;
            }
            for (Question q : questions) {
                String correctAnswer = "";
                if (!q.correctIndexes.isEmpty()) {
                    correctAnswer = q.answers.get(q.correctIndexes.get(0));
                }
                db.addQuestion(quizId, q.question, q.answers.get(0), q.answers.get(1), q.answers.get(2), q.answers.get(3), correctAnswer);
            }
            status.setText("Quiz saved successfully!");
            questions.clear();
        });
        backHome.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.HOME)
        );
        VBox layout = new VBox(10, title, subjectBox, customSubject, multipleAnswers, questionField, row1, row2, row3, row4, addQuestion, finish, backHome, status);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        return new Scene(layout, 450, 600);
    }
}