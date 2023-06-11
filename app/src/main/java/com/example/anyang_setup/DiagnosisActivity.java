package com.example.anyang_setup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class DiagnosisActivity extends AppCompatActivity {
    private WebView myWebView;
    private Button button;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosis_activity);

        myWebView = findViewById(R.id.webview_diagnosis);
        button = findViewById(R.id.Update_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initView();
            }
        });


    }

    private void initView() {
        WebSettings webSettings = myWebView.getSettings();
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



        myWebView.setWebViewClient(new WebViewClient() {



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("https://tis.anyang.ac.kr/index.jsp")) {
                    myWebView.evaluateJavascript(
                            "(function() {" +
                                    "    var Button1 = document.getElementById('mainframe_childframe_form_leftContentDiv_widType_BTN_MENU_DIV_menuDiv_DG_LEFT_MENU_body_gridrow_5');" +
                                    "    if (Button1) {" +
                                    "        Button1.addEventListener('click', function() {" +
                                    "            var Button2 = document.getElementById('mainframe_childframe_form_leftContentDiv_widType_BTN_MENU_DIV_menuDiv_DG_LEFT_MENU_body_gridrow_9GridAreaContainerElement');" +
                                    "            window.location.href = 'https://tis.anyang.ac.kr/index.jsp'; " +
                                    "            if (Button2) {" +
                                    "                Button2.addEventListener('click', function() {" +
                                    "                window.location.href = 'https://tis.anyang.ac.kr/index.jsp'; " +
                                    "                });" +
                                    "            }" +
                                    "        });" +
                                    "    }" +
                                    "})();",
                            null
                    );
                }
                view.loadUrl("javascript:var parentDiv = document.querySelector('div[style=\"user-select: none; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 747px; height: 937px;\"]'); var childDiv = parentDiv.querySelector('div#mainframe_childframe'); window.Android.getHtml(childDiv.innerHTML);");

            }
        });

        myWebView.addJavascriptInterface(new MyJavascriptInterface(), "Android");

        myWebView.loadUrl("https://tis.anyang.ac.kr/main.do");



    }

    private class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {


            Log.d("테스트1", "HTML: " + html);
            saveHtmlToFileTXT(html);


        }
    }
    private void saveHtmlToFileTXT(String html) {
        String filename = "webpage.txt";

        try {
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(html.getBytes());
            outputStream.close();
            Log.d("테스트2", "텍스트 파일이 저장되었습니다: " + getFilesDir() + "/" + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}