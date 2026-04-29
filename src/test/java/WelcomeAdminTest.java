/**
 * Testing for Welcome Admin, right now it doesn't do much other
 * than verify you can actually make the UI
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class WelcomeAdminTest{

    @Test
    public void doesExist(){
        WelcomeAdmin app = new WelcomeAdmin();
        assertNotNull(app);
    }

}
