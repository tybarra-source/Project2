
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginController {

    private final DataBaseManager db = DataBaseManager.getInstance();

    public Scene buildScene() {
        Label title = new Label("Login");
        Label errorLabel = new Label("");
        errorLabel.setVisible(false);
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        Button loginBtn = new Button("Log In");
        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.SIGNUP)
        );
        loginBtn.setOnAction(event -> {
            String username = userName.getText().trim();
            String pass = password.getText().trim();
            if (username.isEmpty() || pass.isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Please complete all fields.");
                return;
            }
            boolean valid = db.validateUser(username, pass);
            if (!valid) {
                errorLabel.setVisible(true);
                errorLabel.setText("Username or password not found. Please sign up!");
                return;
            }
            int userId = db.getUserId(username);
            if (userId <= 0) {
                errorLabel.setVisible(true);
                errorLabel.setText("Login failed: invalid user ID.");
                return;
            }
            Session.currentUserId = userId;
            Session.currentUsername = username;
            errorLabel.setVisible(false);

            if (db.isAdmin(username)) {
                SceneManager.getInstance().navigateTo(SceneType.ADMIN_HOME);
            } else {
                SceneManager.getInstance().navigateTo(SceneType.HOME);
            }
        });
        VBox layout = new VBox(10, title, userName, password, errorLabel, loginBtn, signUpBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        return new Scene(layout, 400, 300);
    }
}