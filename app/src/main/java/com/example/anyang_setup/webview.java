package com.example.anyang_setup;


import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class webview extends AppCompatActivity {

    private WebView webView;
    private Button update_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosis_activity);

        webView = findViewById(R.id.webview_diagnosis);
        //webView.addJavascriptInterface(new MyJavascriptInterface(), "Android"); // 수정된 부분
        UpdateView();
    }

    private void UpdateView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportMultipleWindows(false);
        webSettings.setSupportZoom(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webView.loadUrl("https://tis.anyang.ac.kr/main.do");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                update_button = findViewById(R.id.Update_button);
                update_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webView.evaluateJavascript(
                                "var result1 = document.getElementById('mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_2GridCellTextContainerElement').innerText;"+
                                        "var result2 = document.getElementById('mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_28GridCellTextContainerElement').innerText;"+
                                        "var result = result1 + ',' + result2;"+
                                        "result;",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        String result = value.replaceAll("^\"|\"$", "");
                                        String[] resultArray = result.split(",");
                                        String result1 = resultArray[0];
                                        String result2 = resultArray[1];

                                        int result1_int = Integer.parseInt(result1);
                                        int result2_int = Integer.parseInt(result2);

                                        int result_int = result1_int - result2_int;
                                        int a = 1;

                                        if(result_int <= 0 ){
                                            a = 0;
                                        }if (result_int > 0){
                                            a = result_int;
                                        }

                                        TextView textView = findViewById(R.id.General_Elective);
                                        textView.setText("교양선택 기준:"+result1+" 취득:"+result2+" 잔여:"+ a);

                                    }
                                });


                        //D/테스트1: json: {\"result1\":\"14\",\"result2\":\"25\"}


                    }
                });
            }
        });

    }

}


//    private void saveHtmlToFileTXT(String html) {
//        String filename = "webpage.txt";
//
//        try {
//            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.write(html.getBytes());
//            outputStream.close();
//            Log.d("테스트2", "텍스트 파일이 저장되었습니다: " + getFilesDir() + "/" + filename);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }





