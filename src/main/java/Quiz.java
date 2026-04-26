import java.util.ArrayList;

public class Quiz {

    public Quiz(String id){
        //placeholder constructor;
    }

    private ArrayList<Question> questions;

    public int getQuestionCount(){
        return questions.size();
    }

    //Private because intended for use only within quiz.take()
    private ArrayList<Question> getQuestions(){
        return questions;
    }

    /**
     * Take each question in the quiz
     * @return Total score for quiz
     */
    public float take(){
        return 0;
    }

    //Question class is tied to quiz and thus only accessible within quiz
    public static class Question {
    /*Questions can also have their own score instead of all being worth an equal amount, if wanted,
    in which case implementation would be slightly different*/

        private String type; //Use enum, decides whether a question is multiple answer or single answer

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
