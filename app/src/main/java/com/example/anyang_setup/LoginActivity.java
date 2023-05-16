package com.example.anyang_setup;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private CheckBox mAutoLoginCheckBox;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex);

        myWebView = findViewById(R.id.web_view);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("tis.anyang.ac.kr")) {
                    // 해당 도메인에 대해서는 WebView에서 처리
                    return false;
                } else {
                    // 다른 도메인에 대해서는 해당 도메인으로 WebView에서 페이지 이동
                    view.loadUrl(url);
                    return true;
                }
            }
        };

        myWebView.setWebViewClient(webViewClient);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setJavaScriptEnabled(true); // JavaScript 활성화

        // 쿠키 관리를 위한 설정
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);
        CookieSyncManager.createInstance(this);

        myWebView.loadUrl("https://tis.anyang.ac.kr/main.do#");

        // JavaScript로 로그인 버튼 클릭 이벤트 처리
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // 로그인 버튼의 class 이름이 "login_button"인 경우 클릭 이벤트 처리
                if (url.equals("https://tis.anyang.ac.kr/main.do#")) {
                    myWebView.evaluateJavascript(
                            "javascript:(function() { " +
                                    "    var loginButton = document.querySelector('.login_button'); " +
                                    "    if (loginButton) { " +
                                    "        loginButton.addEventListener('click', function() { " +
                                    "            window.location.href = 'https://tis.anyang.ac.kr/main.do'; " +
                                    "        }); " +
                                    "    } " +
                                    "})();",
                            null
                    );
                }
            }
        });
    }
}

