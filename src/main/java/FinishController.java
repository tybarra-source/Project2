import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FinishController {
    public Scene buildScene(int score, int total) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label finished = new Label("Quiz Finished!");
        Label finalScore = new Label("Score: " + score + " / " + total);

        Button homeButton = new Button("Back Home");
        homeButton.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.HOME)
        );
        layout.getChildren().addAll(finished, finalScore, homeButton);
        return new Scene(layout, 400, 300);
    }


}