public class Question {
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
