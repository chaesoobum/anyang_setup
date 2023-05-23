package com.example.anyang_setup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        myWebView = findViewById(R.id.webview);
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);




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
        //myWebView.setVisibility(View.GONE);




        // 로그인 버튼 클릭 시 WebView에서 JavaScript 실행
        mLoginButton.setOnClickListener(v -> {
            String username = mUsernameEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(LoginActivity.this, "학번을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            myWebView.evaluateJavascript(
                    "(function() { " +
                            "    document.getElementById('id').value = '" + username + "'; " +
                            "    document.getElementById('password').value = '" + password + "'; " +
                            "    var loginButton = document.getElementById('loginImg'); " +
                            "    if (loginButton) { " +
                            "        loginButton.click(); " +
                            "    } " +
                            "})();",
                    null
            );

            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    // 로그인 버튼의 id가 "loginImg"인 경우 클릭 이벤트 처리
                    if (url.equals("https://tis.anyang.ac.kr/index.jsp")) {
                        myWebView.evaluateJavascript(
                                "(function() { " +
                                        "    var loginButton = document.getElementById('loginImg'); " +
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

            // 팝업 확인 버튼 자동 클릭
            myWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                    result.confirm();
                    return true;
                }
            });


            myWebView.postDelayed(() -> {
                myWebView.evaluateJavascript(
                        "(function() { " +
                                "    var mainframeChildFrame = document.getElementById('mainframe_childframe'); " +
                                "    return mainframeChildFrame !== null; " +
                                "})();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String result) {
                                boolean isLoginSuccessful = Boolean.parseBoolean(result);
                                if (isLoginSuccessful) {
                                    // 로그인에 성공한 경우 메인 액티비티로 이동
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 로그인에 실패한 경우 처리
                                    // 예: Toast로 실패 메시지 출력
                                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }, 5000); // 10초 대기 후에 확인

        });

        // JavaScript로 로그인 버튼 클릭 이벤트 처리
        myWebView.loadUrl("https://tis.anyang.ac.kr/main.do#");


    }
}
