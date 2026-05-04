
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.*;

import java.io.IOException;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



//https://opentdb.com/api_config.php
// YOUTUBE VIDEO TUT: https://youtu.be/MAw5Ku1OVFA?si=rYyju0S1d8JN3Csq
public class QuizAPI {

    /*
     * This thing gets the url
     * Here's the website you got it from: https://square.github.io/okhttp/
     */


    private static final String KEY = "qa_sk_73f3bc15fdd19ab323c2ff26b78ff0ded02eff36";
    private static final String URL = "https://quizapi.io/api/v1/quizzes?limit=5";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder().create();


    private String run(String url) throws IOException{
        //String url = "https://quizapi.io/api/v1/quizzes?limit=5";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Api-Key", KEY)
                .build();
        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected response code: " + response.code());
            }
            return response.body().string();
        }

    }

    public Question[] getQuestions(int amount) throws IOException{
        String url = URL + "?limit=" + amount;
        String json = run(url);
        return gson.fromJson(json, Question[].class);

    }


}
