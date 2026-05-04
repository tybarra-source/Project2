import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


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


}
