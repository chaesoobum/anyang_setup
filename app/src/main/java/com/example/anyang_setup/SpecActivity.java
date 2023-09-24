package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SpecActivity extends AppCompatActivity {
    private LinearLayout certificationTable;
    private Button addCertificationButton;
    private List<String> certificationDataList;

    private LinearLayout externalActivitiesTable;
    private Button addExternalActivitiesButton;
    private List<String> externalActivitiesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spec_layout_3);

        certificationTable = findViewById(R.id.certificationTable);
        addCertificationButton = findViewById(R.id.addCertificationButton);
        certificationDataList = new ArrayList<>(); // 데이터를 저장할 리스트 초기화

        externalActivitiesTable = findViewById(R.id.external_activitiesTable); // 이름 변경
        addExternalActivitiesButton = findViewById(R.id.add_external_activitiesButton); // 이름 변경
        externalActivitiesDataList = new ArrayList<>(); // 데이터를 저장할 리스트 초기화

        addCertificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CertificateActivity를 호출하여 데이터를 입력받습니다.
                Intent intent = new Intent(SpecActivity.this, CertificateActivity.class);
                startActivityForResult(intent, 1); // requestCode
            }
        });

        addExternalActivitiesButton.setOnClickListener(new View.OnClickListener() { // 이름 변경
            @Override
            public void onClick(View v) {
                // CertificateActivity를 호출하여 데이터를 입력받습니다.
                Intent intent = new Intent(SpecActivity.this, ExternalActivitiesActivity.class);
                startActivityForResult(intent, 2); // requestCode
            }
        });

        // 액티비티가 시작될 때 저장된 데이터를 표시합니다.
        for (String certification : certificationDataList) {
            addCertificationRow(certification);
        }

        for (String externalActivities : externalActivitiesDataList) { // 이름 변경
            addExternalActivitiesRow(externalActivities); // 이름 변경
        }
    }

    private void addCertificationRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newCertificationTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newCertificationTextView, params);

        // TableRow를 certificationTable에 추가합니다.
        certificationTable.addView(newRow);
    }

    private void addExternalActivitiesRow(String text) { // 이름 변경
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newExternalActivitiesTextView = new TextView(this); // 이름 변경
        newExternalActivitiesTextView.setText(text); // 이름 변경
        newExternalActivitiesTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newExternalActivitiesTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newExternalActivitiesTextView, params); // 이름 변경

        // TableRow를 externalActivitiesTable에 추가합니다.
        externalActivitiesTable.addView(newRow); // 이름 변경
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // CertificateActivity에서 전달된 데이터를 가져옵니다.
            String newData_1 = data.getStringExtra("certification");
            String newData_2 = data.getStringExtra("externalActivities");
            // requestCode에 따라 데이터를 저장하고 표시합니다.
            if (requestCode == 1) {
                certificationDataList.add(newData_1);
                addCertificationRow(newData_1);
            } else if (requestCode == 2) { // 이름 변경
                externalActivitiesDataList.add(newData_2); // 이름 변경
                addExternalActivitiesRow(newData_2); // 이름 변경
            }
        }
    }
}
