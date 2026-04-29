import java.util.ArrayList;

public class Question {
    String question;
    ArrayList<String> answers;
    ArrayList<Integer> correctIndexes;
    String subject;
    boolean multiple;
    public Question(String question, ArrayList<String> answers, ArrayList<Integer> correctIndexes, String subject, boolean multiple) {
        this.question = question;
        this.answers = answers;
        this.correctIndexes = correctIndexes;
        this.subject = subject;
        this.multiple = multiple;
    }
}
