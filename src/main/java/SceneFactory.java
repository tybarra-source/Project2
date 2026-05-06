import javafx.scene.Scene;

public class SceneFactory {

    public static Scene create(SceneType type) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignUpController().buildScene();
            case CREATE_QUIZ -> new CreateYourOwnQuizController().buildScene();
            case ADMIN_HOME -> new AdminHomeController().buildScene();
            case CREATE_FROM_EXISTING -> new CreateFromExistingQuizController().buildScene();
            case HOME -> new HomeController().buildScene();
            case TAKE_QUIZ -> new TakeQuizController().buildScene(Session.currentQuizId);
            case FINISH -> new FinishController().buildScene(Session.finalScore, Session.totalQuestions);
            case ADMIN_RESULTS -> new AdminResultsController().buildScene();
            case USER_RESULTS -> new UserResultsController().buildScene();
            case ADMIN_EDIT -> new AdminEditQuizController().buildScene(Session.currentQuizId);
        };
    }
}