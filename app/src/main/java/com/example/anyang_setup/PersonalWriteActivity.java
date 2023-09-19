package com.example.anyang_setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalWriteActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    Button resetButton;
    Button saveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_write);

        editText1 = findViewById(R.id.personal_question);
        editText2 = findViewById(R.id.personal_text);
        resetButton = findViewById(R.id.personal_write_reset_button);
        saveButton = findViewById(R.id.personal_write_save_button);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setText("");
                editText2.setText("");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Question = editText2.getText().toString();
                String userText = editText1.getText().toString();

                // Intent를 생성하여 PersonalLockerActivity로 전달
                Intent intent = new Intent(PersonalWriteActivity.this, PersonalLockerActivity.class);

                // 데이터를 intent에 담아 전달
                intent.putExtra("Question", Question);
                intent.putExtra("userText", userText);

                startActivity(intent);
            }
        });
    }
}
