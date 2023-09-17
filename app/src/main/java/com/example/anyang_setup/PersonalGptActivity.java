package com.example.anyang_setup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        // 여기서 ChatGPT를 호출하고 응답을 받아오는 코드를 추가하세요.
        // OpenAI API를 사용하여 실제로 메시지를 처리하는 로직을 작성해야 합니다.

        // 예를 들어, 아래와 같이 가상의 응답을 생성하는 코드를 사용할 수 있습니다.
        String response = generateResponse(message);
        appendMessage("ChatGPT: " + response);

        // 메시지 입력창 초기화
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

    // 가상의 응답을 생성하는 함수 (실제로는 OpenAI API를 사용해야 합니다)
    private String generateResponse(String message) {
        // 여기에 ChatGPT 호출 및 응답을 받아오는 로직을 작성하세요.
        // 이 함수는 가상의 응답을 반환합니다.
        return "가상의 응답입니다.";
    }
}
