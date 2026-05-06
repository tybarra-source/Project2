import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;

// got this from the past hw we had
public class SceneManager {

    private static SceneManager instance;

    private final Stage stage;
    private final Map<SceneType, Scene> cache = new EnumMap<>(SceneType.class);

    private SceneManager(Stage stage) {
        this.stage = stage;
    }
    public static void init(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }
    public static SceneManager getInstance() {
        if (instance == null){
            throw new IllegalStateException
                    ("SceneManager not initialised");
        }
        return instance;
    }
    public void navigateTo(SceneType type) {
        if (type == SceneType.ADMIN_EDIT) {
            stage.setScene(SceneFactory.create(type));
            return;
        }
        Scene scene = cache.computeIfAbsent(type, t -> SceneFactory.create(t));
        stage.setScene(scene);
    }
    public void navigateFresh(SceneType type) {
        Scene scene = SceneFactory.create(type);
        cache.put(type, scene);
        stage.setScene(scene);
    }
}
