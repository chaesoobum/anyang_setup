/*package com.example.anyang_setup;


 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;

public class SpecActivity extends AppCompatActivity {
    private LinearLayout certificationTable;
    Button addCertificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spec_layout_3);



        certificationTable = findViewById(R.id.certificationTable);

        findViewById(R.id.addCertificationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SpecActivity.this, CertificateActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String certification = intent.getStringExtra("certification");

            // 받은 데이터를 새로운 텍스트뷰로 표시합니다.
            addCertificationTextView(certification);
        }




    }

    private void addCertificationTextView(String text) {
        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(20);

        // 텍스트뷰를 certificationTable에 추가합니다.
        certificationTable.addView(newCertificationTextView);
    }
}
*/