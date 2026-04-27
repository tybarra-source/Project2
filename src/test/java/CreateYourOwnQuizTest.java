import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateYourOwnQuizTest {
    @Test
    void testNoCorrectAnswer() {
        ArrayList<Integer> correct = new ArrayList<>();
        assertTrue(correct.isEmpty());
    }
    @Test
    void testTooManyCorrectAnswers() {
        boolean multipleAnswers = false;
        ArrayList<Integer> correct = new ArrayList<>();
        correct.add(0);
        correct.add(1);
        assertTrue(correct.size() > 1 && !multipleAnswers);
    }
}
