package com.example.anyang_setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private CheckBox mAutoLoginCheckBox;
    private String csrfToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        mLoginButton = findViewById(R.id.login_button);
        mAutoLoginCheckBox = findViewById(R.id.my_checkbox);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "학번을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // AsyncTask 실행
                new LoginTask().execute(username, password);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {



            String username = strings[0];
            String password = strings[1];

            String loginUrl = "https://tis.anyang.ac.kr/main.do";
            // SSL 인증서 검증을 무시하도록 설정합니다.
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            } };
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                // Jsoup을 사용하여 로그인 페이지를 가져옵니다.
                Document loginPage = Jsoup.connect(loginUrl)
                        .timeout(3000000)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                        .referrer("https://tis.anyang.ac.kr/main.do")
                        .method(Connection.Method.GET)
                        .execute().parse();

                // 로그인 페이지에서 CSRF 토큰을 가져옵니다.
                csrfToken = loginPage.select("input[name=_csrf]").val();

                // 로그인에 필요한 POST 요청을 생성합니다.
                Connection.Response loginResponse = Jsoup.connect(loginUrl)
                        .timeout(3000000)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                        .referrer("https://tis.anyang.ac.kr/main.do")
                        .data("_csrf", csrfToken)
                        .data("userId", username)
                        .data("password", password)
                        .method(Connection.Method.POST)
                        .execute();

                return loginResponse.statusCode() == 302; // 로그인에 성공하면 302 상태코드를 반환합니다.
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @SuppressLint("ApplySharedPref")
        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                // 로그인에 성공한 경우

                Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                if (mAutoLoginCheckBox.isChecked()) {
                    // 자동 로그인 체크박스가 선택된 경우, SharedPreferences를 사용하여 아이디와 비밀번호를 저장합니다.
                    getSharedPreferences("login", MODE_PRIVATE).edit()
                            .putString("id", mUsernameEditText.getText().toString())
                            .putString("pw", mPasswordEditText.getText().toString())
                            .putBoolean("auto_login", true)
                            .apply();
                }

                // MainActivity로 이동합니다.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                // LoginActivity를 종료합니다.
                finish();
            } else {
                // 로그인에 실패한 경우
                Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
