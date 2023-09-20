package com.example.anyang_setup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PersonalGptActivity extends AppCompatActivity {

    private TextView chatTextView;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_gpt);

        chatTextView = findViewById(R.id.chatTextView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString();
        appendMessage("You: " + message);

        String response = generateResponse(message);
        appendMessage("ChatGPT: " + response);

        messageEditText.setText("");
    }

    private void appendMessage(String message) {
        chatTextView.append(message + "\n");
        scrollToBottom();
    }

    private void scrollToBottom() {
        final ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private String generateResponse(String message) {
        OkHttpClient client = new OkHttpClient();

        String apiUrl = "https://api.openai.com/v1/completions";
        String requestBody = "{\"model\":\"davinci\", \"prompt\":\"" + message + "\", \"max_tokens\":50}";

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .addHeader("Authorization", "Bearer <openai API Keys>")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            String completion = new JSONObject(responseBody).getJSONArray("choices").getJSONObject(0).getString("text");

            return completion;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "오류가 발생했습니다.";
        }
    }
}
