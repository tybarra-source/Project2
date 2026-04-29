import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> buildLoginScene(stage);
            case SIGNUP -> buildSignupScene(stage);
            case CREATE_QUIZ -> buildCreateQuizScene(stage);
        };
    }
    private static Scene buildLoginScene(Stage stage) {
        return LoginScene.getScene(stage);
    }
    private static Scene buildSignupScene(Stage stage) {
        return SignUpScene.getScene(stage);
    }
    private static Scene buildCreateQuizScene(Stage stage) {
        return CreateYourOwnQuiz.getScene(stage);
    }
}