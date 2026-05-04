/**
 * Testing for Welcome Admin, right now it doesn't do much other
 * than verify you can actually make the UI
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class WelcomeAdminControllerTest {

    @Test
    public void doesExist(){
        WelcomeAdminController app = new WelcomeAdminController();
        assertNotNull(app);
    }

}
