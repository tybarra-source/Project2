/**
 * Description: The Log in Scene for users to sign in to their pre-existing account on our website.
 * April 26, 2026
 * @author Anjelina Jasso
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
    public Scene buildScene() {
        Label title = new Label("Login");
        Label errorLabel = new Label("");
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        Button loginBtn = new Button("Log In");
        Button signUpBtn = new Button ("Sign Up");
        Button createYourOwnQuiz = new Button ("Create your own quiz");

        signUpBtn.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.SIGNUP)
        );

        //for testing purposes
        createYourOwnQuiz.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ)
        );
        loginBtn.setOnAction(event -> {
            if (userName.getText().isEmpty() || password.getText().isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Please complete all fields.");
            } else {
                DataBaseManager db = new DataBaseManager();
                boolean valid = db.validateUser(userName.getText(), password.getText());
                if (valid) {
                    errorLabel.setVisible(false);
                    int userId = db.getUserId(userName.getText());
                    Session.currentUserId = userId;
                    Session.currentUsername = userName.getText();
                    SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ);
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Username or password not found. Please sign up!");
                }
            }
        });
        VBox layout = new VBox(10, title, userName, password, errorLabel, loginBtn, signUpBtn, createYourOwnQuiz);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        return new Scene(layout, 400, 300);
    }
}