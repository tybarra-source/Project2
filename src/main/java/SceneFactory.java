import javafx.scene.Scene;

public class SceneFactory {

    public static Scene create(SceneType type) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignUpController().buildScene();
            case CREATE_QUIZ -> new CreateYourOwnQuizController().buildScene();
            case WELCOME_ADMIN -> new WelcomeAdminController().buildScene();
        };
    }
}