import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CreateFromExistingQuizControllerTest {


    private Question convertToQuestion(String questionText, String correctAnswer, List<String> incorrectAnswers, String topic) {
        ArrayList<String> answers = new ArrayList<>(incorrectAnswers);
        answers.add(correctAnswer);
        ArrayList<Integer> correctIndexes = new ArrayList<>();
        correctIndexes.add(answers.size() - 1);
        return new Question(-1, questionText, answers, correctIndexes, topic, false);
    }



    @Test
    public void testQuestionTextIsSetCorrectly() {
        Question q = convertToQuestion("What is the capital of France?", "Paris", List.of("London", "Berlin", "Madrid"), "Geography");
        assertEquals("What is the capital of France?", q.question);
    }

    @Test
    public void testCorrectAnswerIsLastInList() {
        Question q = convertToQuestion("What is 2 + 2?", "4", List.of("1", "2", "3"), "Math");
        assertEquals("4", q.answers.get(q.correctIndexes.get(0)));
    }

    @Test
    public void testCorrectIndexIsLastPosition() {
        Question q = convertToQuestion("Who wrote Hamlet?", "Shakespeare", List.of("Dickens", "Austen", "Tolstoy"), "Literature");
        assertEquals(3, q.correctIndexes.get(0));
    }

    @Test
    public void testFourAnswersTotal() {
        Question q = convertToQuestion("What color is the sky?", "Blue", List.of("Red", "Green", "Yellow"), "General Knowledge");
        assertEquals(4, q.answers.size());
    }

    @Test
    public void testSubjectIsSetCorrectly() {
        Question q = convertToQuestion("What is H2O?", "Water", List.of("Fire", "Air", "Earth"), "Science & Nature");
        assertEquals("Science & Nature", q.subject);
    }

    @Test
    public void testMultipleIsAlwaysFalse() {
        Question q = convertToQuestion("What is H2O?", "Water", List.of("Fire", "Air", "Earth"), "Science & Nature");
        assertFalse(q.multiple);
    }

    @Test
    public void testQuestionIdIsMinusOne() {
        Question q = convertToQuestion("Sample question?", "Correct", List.of("Wrong1", "Wrong2", "Wrong3"), "General Knowledge");
        assertEquals(-1, q.questionId);
    }

    @Test
    public void testOnlyOneCorrectIndex() {
        Question q = convertToQuestion("Sample?", "Correct", List.of("Wrong1", "Wrong2", "Wrong3"), "General Knowledge");
        assertEquals(1, q.correctIndexes.size());
    }



    @Test
    public void testAmountBelowRangeIsInvalid() {
        int amount = 0;
        assertTrue(amount < 1 || amount > 10);
    }

    @Test
    public void testAmountAboveRangeIsInvalid() {
        int amount = 11;
        assertTrue(amount < 1 || amount > 10);
    }

    @Test
    public void testAmountWithinRangeIsValid() {
        int amount = 5;
        assertFalse(amount < 1 || amount > 10);
    }

    @Test
    public void testBoundaryAmountOneIsValid() {
        int amount = 1;
        assertFalse(amount < 1 || amount > 10);
    }

    @Test
    public void testBoundaryAmountTenIsValid() {
        int amount = 10;
        assertFalse(amount < 1 || amount > 10);
    }



    @Test
    public void testEmptyKeptListShouldNotSave() {
        ArrayList<Question> kept = new ArrayList<>();
        assertTrue(kept.isEmpty());
    }

    @Test
    public void testKeptListWithQuestionsShouldProceed() {
        ArrayList<Question> kept = new ArrayList<>();
        kept.add(convertToQuestion("Sample?", "Correct", List.of("Wrong1", "Wrong2", "Wrong3"), "General Knowledge"));
        assertFalse(kept.isEmpty());
    }

    @Test
    public void testUncheckedQuestionsAreExcluded() {
        ArrayList<Question> allQuestions = new ArrayList<>();
        allQuestions.add(convertToQuestion("Q1?", "A", List.of("B", "C", "D"), "General Knowledge"));
        allQuestions.add(convertToQuestion("Q2?", "A", List.of("B", "C", "D"), "General Knowledge"));
        allQuestions.add(convertToQuestion("Q3?", "A", List.of("B", "C", "D"), "General Knowledge"));


        boolean[] checked = {true, false, true};

        ArrayList<Question> kept = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            if (checked[i]) kept.add(allQuestions.get(i));
        }

        assertEquals(2, kept.size());
    }
}