import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TriviaResponse {

    @SerializedName("response_code")
    private int responseCode;
    private List<TriviaQuestions> results;

    public List<TriviaQuestions> getResults(){
        return results;
    }
    public int getResponseCode(){
        return responseCode;
    }


    class TriviaQuestions{
        private String category;
        private String difficulty;
        private String question;

        @SerializedName("correct_answer")
        private String correctAnswer;

        //THE INCORRECT ARE A LIST
        @SerializedName("incorrect_answers")
        private List<String> incorrectAnswers;

        public String getQuestion(){
            return question;
        }
        public String getCorrectAnswer(){
            return correctAnswer;
        }
        public List<String> getIncorrectAnswers(){
            return incorrectAnswers;
        }
        public String getDifficulty(){
            return difficulty;
        }
}

}