
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.*;

import java.io.IOException;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;


//https://opentdb.com/api_config.php
// YOUTUBE VIDEO TUT: https://youtu.be/MAw5Ku1OVFA?si=rYyju0S1d8JN3Csq
public class QuizAPI {
/*
    //private final String URL = "https://opentdb.com/api.php?amount=1";

    private final HttpClient client;

    public QuizAPI(){
        client = HttpClient.newHttpClient();
    }

    public String findAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()// creates new instance of the request
                .uri(URI.create(URL))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
    public static Scene getScene(Stage stage){
        Label question = new Label("Question");
        Button opt1 = new Button();
        Button opt2 = new Button();
        Button opt3 = new Button();
        Button opt4 = new Button();

        Button load = new Button("load");


        try{

            URL url = new URL("https://opentdb.com/api.php?amount=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.connect();

            int responseCode = con.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);

            }else{
                StringBuilder info = new StringBuilder();
                Scanner in = new Scanner(url.openStream());

                while (in.hasNext()){
                    info.append(in.nextLine());
                }



            }
        }catch(Exception e){
            e.printStackTrace();
        }

        VBox root = new VBox(10, question, opt1, opt2, opt3, opt4, load);

        Scene quizScene = new Scene(root, 500, 400);

        return quizScene;
    }




*/
    /*
     * This thing gets the url
     * Here's the website you got it from: https://square.github.io/okhttp/
     */

    //create private static final key
    // pri st fin base url
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
        String url = URL;

    }


    /*
    # Step 1: List available quizzes
        curl "https://quizapi.io/api/v1/quizzes?limit=5" \
            -H "Authorization: Bearer qa_sk_your_api_key"
    # Step 2: Fetch questions for a quiz (use an id from Step 1)
        curl "https://quizapi.io/api/v1/questions?quiz_id=QUIZ_ID&include_answers=true" \
            -H "Authorization: Bearer qa_sk_your_api_key"
     */
    //API key
    //qa_sk_73f3bc15fdd19ab323c2ff26b78ff0ded02eff36

    /* MAIN from example
        public static void main(String[] args) throws IOException {
            GetExample example = new GetExample();
            String response = example.run("https://raw.github.com/square/okhttp/master/README.md");
            System.out.println(response);
        }
     */
}
