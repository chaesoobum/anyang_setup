package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalLockerActivity extends AppCompatActivity {

    private EditText searchEditText;
    private GridLayout introductionGridLayout;
    private Button selectButton;
    private Button downloadButton;

    private String[] introductionList = {
            "자기소개서 1",
            "자기소개서 2",
            "자기소개서 3",
            "자기소개서 4",
            "자기소개서 5",
    };

    private void filterIntroductionList(String query) {
        // 검색어(query)를 기반으로 자기소개서 목록을 필터링하고 다시 표시
        // 이 예제에서는 간단하게 모든 자기소개서를 표시하도록 함
        displayIntroductionList();
    }

    private void displayIntroductionList() {
        // GridLayout에 자기소개서를 동적으로 추가

        LayoutInflater inflater = LayoutInflater.from(this);
        introductionGridLayout.removeAllViews();

        for (int i = 0; i < introductionList.length; i++) {
            View itemView = inflater.inflate(R.layout.personal_introduction_item, introductionGridLayout, false);
            TextView titleTextView = itemView.findViewById(R.id.titleTextView);
            TextView contentTextView = itemView.findViewById(R.id.contentTextView);

            titleTextView.setText("자기소개서 " + (i + 1));
            contentTextView.setText("자기소개서 내용...");

            introductionGridLayout.addView(itemView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_locker);

        searchEditText = findViewById(R.id.searchEditText);
        introductionGridLayout = findViewById(R.id.introductionGridLayout);
        selectButton = findViewById(R.id.selectButton);
        downloadButton = findViewById(R.id.downloadButton);

        // EditText에 텍스트가 입력될 때마다 검색 기능을 활성화
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterIntroductionList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 초기 자기소개서 목록 표시
        displayIntroductionList();

        // PersonalWriteActivity에서 전달된 데이터 받기
        Intent intent = getIntent();
        String selectedQuestion = intent.getStringExtra("selectedQuestion");
        String userText = intent.getStringExtra("userText");

        // 받아온 데이터 활용하기
        // 이 예제에서는 받아온 데이터를 Toast로 표시하는 예시를 들었습니다.
        if (selectedQuestion != null && userText != null) {
            Toast.makeText(this, "선택한 질문: " + selectedQuestion + "\n작성한 텍스트: " + userText, Toast.LENGTH_LONG).show();
        }
    }

    // 이하 생략
}
