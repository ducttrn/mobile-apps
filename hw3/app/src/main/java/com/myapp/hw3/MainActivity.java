package com.myapp.hw3;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        setContentView(R.layout.activity_main);
        initDetectButton();
    }

    private void initDetectButton(){
        Button detectButton = findViewById(R.id.button);
        detectButton.setOnClickListener(view -> {
            imageView = findViewById(R.id.imageView);
            EditText sentence = findViewById(R.id.sentence);
            String sentence_text = sentence.getText().toString();
            Prediction prediction = null;

            try {
                // Make API Call to detect the sentiment
                prediction = detect(sentence_text);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Display a meme based on sentiment
            if (prediction != null) {
                if (prediction.sentiment == 0) {
                    imageView.setImageResource(R.drawable.awesome);
                }
                else {
                    imageView.setImageResource(R.drawable.get_well_soon_26);
                }
            }
        });
    }

    private Prediction detect(String sentence_text) throws IOException {
        // API to detect sentiment, made and hosted by myself
        URL url = new URL("https://sentiment.billtrn.com/api/v1/sentiments/predict");

        // Establish HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        // Request body as JSON
        String jsonInputString = String.format("{\"text\": \"%s\"}", sentence_text);

        // Asynchronous request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Fetch results
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read fetched results as StringBuilder
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Convert result string to JSON object to get fields
            JSONObject json = new JSONObject(response.toString());
            int sentiment = (int) json.getInt("sentiment");
            double confidence = json.getDouble("confidence");

            return new Prediction(sentiment, confidence);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    // Class for fetched prediction
    private static class Prediction {
        public final int sentiment;
        public final double confidence;

        public Prediction(int sentiment, double confidence) {
            this.sentiment = sentiment;
            this.confidence = confidence;
        }
    }
}
