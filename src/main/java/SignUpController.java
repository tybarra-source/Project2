/**
 * Description: The SignUp Scene for users to signup for a new account on our website.
 * April 26, 2026
 * @author Anjelina Jasso
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpController {
    private final DataBaseManager        db        = DataBaseManager.getInstance();
    public Scene buildScene() {
        Label title = new Label("Sign Up");
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        Label errorLabel = new Label("");
        RadioButton adminBtn = new RadioButton("Admin");
        RadioButton userBtn = new RadioButton("User");
        ToggleGroup role = new ToggleGroup();
        userBtn.setToggleGroup(role);
        adminBtn.setToggleGroup(role);
        Button loginBtn = new Button("Back to Log In");
        Button signUpBtn = new Button("Sign Up");
      
        loginBtn.setOnAction(e ->
                SceneManager.getInstance().navigateTo(SceneType.LOGIN)
        );

        signUpBtn.setOnAction(event -> {
            if (!password.getText().equals(confirmPassword.getText())) {
                errorLabel.setVisible(true);
                errorLabel.setText("Passwords do not match!");
            } else if (password.getText().isEmpty() || confirmPassword.getText().isEmpty() || userName.getText().isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Please complete all fields.");
            } else if (userName.getText().length() < 5) {
                errorLabel.setVisible(true);
                errorLabel.setText("Username must be at least 5 characters!");
            } else if (password.getText().length() < 8) {
                errorLabel.setVisible(true);
                errorLabel.setText("Password must be at least 8 characters!");
            } else {
                errorLabel.setVisible(false);
                db.addUser(userName.getText(), password.getText());
                if (adminBtn.isSelected()) {
                    SceneManager.getInstance().navigateTo(SceneType.ADMIN_HOME);
                } else {
                    SceneManager.getInstance().navigateTo(SceneType.LOGIN);
                }
            }
        });
        VBox layout = new VBox(10, title, userName, password, confirmPassword, adminBtn, userBtn, errorLabel, loginBtn, signUpBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        return new Scene(layout, 400, 300);
    }
}
