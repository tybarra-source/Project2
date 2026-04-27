import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
    private ArrayList<Question> questions;

    public Quiz(){
        questions = new ArrayList<>();
    }

    public int getQuestionCount(){
        return questions.size();
    }

    //Private because intended for use only within quiz.take()
    private ArrayList<Question> getQuestions(){
        return questions;
    }

    private void addQuestion(Question.Type type, String prompt, HashMap<String, Boolean> answers){
        questions.add(new Question(type, prompt, answers));
    }

    /**
     * Take each question in the quiz
     * @return Total score for quiz
     */
    public float take(){
        for(Question q : questions){

        }
        return 0;
    }

    //Question class is tied to quiz and thus only accessible within quiz
    public static class Question {
    /*Questions can also have their own score instead of all being worth an equal amount, if wanted,
    in which case implementation would be slightly different*/

        enum Type{
            SINGLE_CORRECT,
            MULTIPLE_CORRECT,
            MULTIPLE_ANSWER
        }

        private Type type;
        private String prompt;
        private HashMap<String, Boolean> answers;

        Question(Type type, String prompt, HashMap<String, Boolean> answers){
            this.type = type;
            this.prompt = prompt;
            this.answers = answers;
        }
        /* Values in key should correspond to indices in answer, indicating whether each answer is
        correct or incorrect. */


        /**
         *
         * @param answers A 0 or 1 for every possible answer to indicate whether it's input as an answer
         *                (can be changed based on implementation for multiple answers)
         * @return score for question
         */
        public float Answer(boolean[] answers){
            //Behavior should depend on type enum
            return 0;
        }



    }
}
