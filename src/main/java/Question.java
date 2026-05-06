import java.util.ArrayList;

public class Question {
    String question;
    ArrayList<String> answers;
    ArrayList<Integer> correctIndexes;
    String subject;
    int questionId;
    boolean multiple;
    public Question(int questionId, String question, ArrayList<String> answers, ArrayList<Integer> correctIndexes, String subject, boolean multiple) {
        this.question = question;
        this.questionId=questionId;
        this.answers = answers;
        this.correctIndexes = correctIndexes;
        this.subject = subject;
        this.multiple = multiple;
    }
}
