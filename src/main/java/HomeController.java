import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
    public Scene buildScene() {
        Label welcome = new Label("Welcome Admin");

        Button editQ1 = new Button("Edit Quiz 1");
        Button editQ2 = new Button("Edit Quiz 2");
        Button editQ3 = new Button("Edit Quiz 3");
        Button newQuiz = new Button("Create New Quiz");
        Label result = new Label();
        VBox layout = new VBox(12);
        layout.getChildren().addAll(welcome, editQ1, editQ2, editQ3, newQuiz, result);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));


        //Create method getscene in welcome admin
        editQ1.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });
        editQ2.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });
        editQ3.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });

        newQuiz.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ)
        );
        return new Scene(layout, 400, 300);

    }

}