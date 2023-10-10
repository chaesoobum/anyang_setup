package com.example.anyang_setup;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ResumeWriteActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private boolean isFabOpen = false;
    private FloatingActionButton fabMain, fabUpload, fabDelete;
    private ArrayList<String> uploadedFiles = new ArrayList<>();
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_write);

        fabMain = findViewById(R.id.fab_main);
        fabUpload = findViewById(R.id.fab_upload);
        fabDelete = findViewById(R.id.fab_delete);

        ListView fileListView = findViewById(R.id.file_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, uploadedFiles);
        fileListView.setAdapter(adapter);

        // 파일 목록에서 항목을 클릭하면 해당 파일을 열 수 있도록 설정
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 여기에서 파일을 열거나 관련 작업을 수행할 수 있습니다.
                String fileName = uploadedFiles.get(position);
                Toast.makeText(ResumeWriteActivity.this, "파일 열기: " + fileName, Toast.LENGTH_SHORT).show();
            }
        });

        Uri uri;
        ImageView imageView;

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab();
            }
        });
        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileSelector();
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResumeWriteActivity.this, "삭제 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 파일 선택 다이얼로그를 열기 위한 메서드
     */
    private void openFileSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // 모든 파일 타입을 선택할 수 있도록 설정
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "파일 선택"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "파일 관리자 앱을 설치해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    String fileName = getFileName(uri); // 파일 이름 가져오기
                    if (fileName != null) {
                        uploadedFiles.add(fileName);
                        adapter.notifyDataSetChanged(); // 어댑터에 변경 사항을 알립니다.
                    }
                }
            }
        }
    }

    // 파일 Uri에서 파일 이름 가져오기
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void toggleFab() {
        //Toast.makeText(this, "메인 플로팅 버튼 클릭", Toast.LENGTH_SHORT).show();

        if (isFabOpen) {
            ObjectAnimator.ofFloat(fabDelete, "translationY", 0f).start();
            ObjectAnimator.ofFloat(fabUpload, "translationY", 0f).start();
            fabMain.setImageResource(R.drawable.baseline_add_24);
        } else {
            ObjectAnimator.ofFloat(fabDelete, "translationY", -200f).start();
            ObjectAnimator.ofFloat(fabUpload, "translationY", -400f).start();
            fabMain.setImageResource(R.drawable.baseline_clear_24);
        }

        isFabOpen = !isFabOpen;
    }

}
