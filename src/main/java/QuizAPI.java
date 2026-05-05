
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;


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


    private static final String URL = "https://opentdb.com/api.php";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder().create();




    private String run(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected response code: " + response.code());
            }
            return response.body().string();
        }

    }
//thats the whole inner class thing
    public List<TriviaResponse.TriviaQuestions> getQuestions(int amount, int category) throws IOException{
        String url = URL + "?amount=" + amount + "&category=" + category + "&type=multiple";
        String json = run(url);
        TriviaResponse response = gson.fromJson(json, TriviaResponse.class);
        return response.getResults();
    }


}
