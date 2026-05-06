import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CreateFromExistingQuizController {

    private final DataBaseManager db = DataBaseManager.getInstance();
    public Scene buildScene(){
        Label title = new Label("Create from Existing Quiz");

        ComboBox<String> pickExisting = new ComboBox<>();

        LinkedHashMap<String, Integer> topics= new LinkedHashMap<>();

        topics.put("General Knowledge",9);
        topics.put("Entertainment: Books", 10);
        topics.put("Entertainment: Film", 11);
        topics.put("Entertainment: Music", 12);
        topics.put("Entertainment: Musicals & Theatres",13);
        topics.put("Entertainment: Television", 14);
        topics.put("Entertainment: Video Games", 15);
        topics.put("Entertainment: Board Games", 16);
        topics.put("Science & Nature", 17);
        topics.put("Science: Computers", 18);
        topics.put("Science: Mathematics", 19);
        topics.put("Mythology", 20);
        topics.put("Sports", 21);
        topics.put("Geography", 22);
        topics.put("History", 23);
        topics.put("Politics",24);
        topics.put("Art",25);
        topics.put("Celebrities",26);
        topics.put("Vehicles",27);
        topics.put("Entertainment: Comics",28);
        topics.put("Science: Gadgets",29);
        topics.put("Entertainment: Japanese Anime & Manga",30);
        topics.put("Entertainment: Cartoon & Animations",31);
        pickExisting.getItems().addAll(topics.keySet());
        pickExisting.setPromptText("Select an Existing Topic");

        TextField numOfQuestions = new TextField();
        numOfQuestions.setPromptText("Number of Questions(1-10)");

        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Label loadingLabel = new Label("Loading questions");
        loadingLabel.setVisible(false);
        loadingLabel.setManaged(false);



        Button submit = new Button("Create Quiz");
        VBox layout = new VBox(15, title, pickExisting,numOfQuestions, errorLabel, loadingLabel, submit);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        submit.setDisable(true);

        pickExisting.valueProperty().addListener((obs, oldVal, newVal) ->
                submit.setDisable(newVal == null || numOfQuestions.getText().isEmpty()));
        numOfQuestions.textProperty().addListener((obs,oldVal,newVal)->
                submit.setDisable(newVal.isEmpty() || pickExisting.getValue() == null));

        submit.setOnAction(e-> {
                    int amount;
                    try {
                        amount = Integer.parseInt(numOfQuestions.getText());
                        if (amount > 10 || amount < 1) {
                            errorLabel.setText("Please enter a number from 1 - 10");
                            errorLabel.setVisible(true);
                            errorLabel.setVisible(true);
                            numOfQuestions.clear();
                            numOfQuestions.requestFocus();
                            return;

                        }


                    } catch (NumberFormatException ex) {
                        errorLabel.setText("Please enter a valid number");
                        errorLabel.setVisible(true);
                        errorLabel.setManaged(true);
                        numOfQuestions.clear();
                        numOfQuestions.requestFocus();
                        return;
                    }

                    //Select topic
                    String selectedTopic = pickExisting.getValue();
                    if(selectedTopic == null){
                        errorLabel.setText("Please select a topic");
                        errorLabel.setVisible(true);
                        errorLabel.setManaged(true);
                        return;
                    }

                    errorLabel.setVisible(false);
                    errorLabel.setManaged(false);

                    int categoryId = topics.get(selectedTopic);
                    int finalAmount = amount;
                    Task<List<TriviaResponse.TriviaQuestions>> task = new Task<>(){
                        @Override
                        protected List<TriviaResponse.TriviaQuestions> call() throws Exception{
                            QuizAPI api = new QuizAPI();
                            return api.getQuestions(finalAmount, categoryId);
                        }

                    };

                    task.setOnRunning(event -> {
                        loadingLabel.setVisible(true);
                        loadingLabel.setManaged(true);
                        submit.setDisable(true);
                    });

                    task.setOnSucceeded(event ->{
                        loadingLabel.setVisible(false);
                        loadingLabel.setManaged(false);

                        List<TriviaResponse.TriviaQuestions> apiQuestions = task.getValue();
                        ArrayList<Question> convertedQuestions = new ArrayList<>();

                        for(TriviaResponse.TriviaQuestions apiQ: apiQuestions) {
                            ArrayList<String> answers = new ArrayList<>(apiQ.getIncorrectAnswers());
                            answers.add(apiQ.getCorrectAnswer());

                            ArrayList<Integer> correctIndexes = new ArrayList<>();
                            correctIndexes.add(answers.size()-1);

                            convertedQuestions.add(new Question(apiQ.getQuestion(),answers,correctIndexes,selectedTopic,false));
                        }

                        Label reviewQuiz = new Label("Review Questions");
                        Label uncheck = new Label("Uncheck any questions you don't want");
                        Label status = new Label();

                        status.setVisible(false);
                        status.setManaged(false);

                        VBox questionList = new VBox(10);
                        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

                        for(Question q: convertedQuestions){
                            CheckBox cb = new CheckBox(q.question);
                            cb.setSelected(true);
                            cb.setWrapText(true);
                            checkBoxes.add(cb);
                            questionList.getChildren().add(cb);

                        }
                        ScrollPane sp = new ScrollPane(questionList);
                        sp.setFitToWidth(true);
                        sp.setPrefHeight(300);

                        Button saveButton = new Button("Save Quiz");
                        Button homeButton = new Button("Go Home");

                        saveButton.setOnAction(ev ->{
                            ArrayList<Question> kept = new ArrayList<>();
                            for(int i = 0; i<checkBoxes.size(); i++){
                                if(checkBoxes.get(i).isSelected()){
                                    kept.add(convertedQuestions.get(i));
                                }
                            }

                            if(kept.isEmpty()){
                                status.setText("Keep at least one question");
                                status.setVisible(true);
                                status.setManaged(true);
                                return;
                            }
                            int quizId = db.addQuiz(Session.currentQuizId, selectedTopic + " Quiz", selectedTopic);
                            if(quizId == -1){
                                status.setText("Failed to save quiz");
                                status.setVisible(true);
                                status.setManaged(true);
                                return;
                            }
                            for(Question q : kept){
                                String correctAnswer = q.answers.get(q.correctIndexes.get(0));
                                db.addQuestion(quizId, q.question, q.answers.get(0),q.answers.get(1),q.answers.get(2),q.answers.get(3), correctAnswer );

                            }

                            status.setText("Quiz saved with " + kept.size() + " questions.");
                            status.setVisible(true);
                            status.setManaged(true);
                            saveButton.setDisable(true);
                        });

                        homeButton.setOnAction(eve ->{
                                    SceneManager.getInstance().navigateTo(SceneType.HOME);
                                }
                                );

                        layout.getChildren().setAll(reviewQuiz, uncheck, sp, status, saveButton, homeButton );


                    });

                    task.setOnFailed(ev -> {
                        loadingLabel.setVisible(false);
                        loadingLabel.setManaged(false);
                        errorLabel.setText("Failed to load questions, check your connection");
                        errorLabel.setVisible(true);
                        errorLabel.setManaged(true);
                        submit.setDisable(false);
                    });

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();

        });

        return new Scene(layout, 450, 600);

    }
}
