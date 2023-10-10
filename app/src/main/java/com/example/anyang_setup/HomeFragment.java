package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ScrollView scrollView;
    private Button button;
    private String IDhere;
    private String PWhere;

    private  TextView Name;

    private TextView mon_1;
    private TextView tues_1;
    private TextView wed_1;
    private TextView thur_1;
    private TextView fri_1;

    private TextView mon_2;
    private TextView tues_2;
    private TextView wed_2;
    private TextView thur_2;
    private TextView fri_2;

    private TextView mon_3;
    private TextView tues_3;
    private TextView wed_3;
    private TextView thur_3;
    private TextView fri_3;

    private TextView mon_4;
    private TextView tues_4;
    private TextView wed_4;
    private TextView thur_4;
    private TextView fri_4;

    private TextView mon_5;
    private TextView tues_5;
    private TextView wed_5;
    private TextView thur_5;
    private TextView fri_5;

    private TextView mon_6;
    private TextView tues_6;
    private TextView wed_6;
    private TextView thur_6;
    private TextView fri_6;

    private TextView mon_7;
    private TextView tues_7;
    private TextView wed_7;
    private TextView thur_7;
    private TextView fri_7;

    private TextView mon_8;
    private TextView tues_8;
    private TextView wed_8;
    private TextView thur_8;
    private TextView fri_8;

    private TextView mon_9;
    private TextView tues_9;
    private TextView wed_9;
    private TextView thur_9;
    private TextView fri_9;

    private TextView mon_10;
    private TextView tues_10;
    private TextView wed_10;
    private TextView thur_10;
    private TextView fri_10;

    private TextView mon_11;
    private TextView tues_11;
    private TextView wed_11;
    private TextView thur_11;
    private TextView fri_11;

    private TextView mon_12;
    private TextView tues_12;
    private TextView wed_12;
    private TextView thur_12;
    private TextView fri_12;

    private TextView mon_13;
    private TextView tues_13;
    private TextView wed_13;
    private TextView thur_13;
    private TextView fri_13;

    private TextView mon_14;
    private TextView tues_14;
    private TextView wed_14;
    private TextView thur_14;
    private TextView fri_14;

    private TextView mon_15;
    private TextView tues_15;
    private TextView wed_15;
    private TextView thur_15;
    private TextView fri_15;


    public void receiveData(String ID, String PW) {
        IDhere = ID;
        PWhere = PW;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = view.findViewById(R.id.scrollView_home);
        button = view.findViewById(R.id.diagnosis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DiagnosisActivity.class);
                startActivity(intent);
            }
        });

        final TextView textView = view.findViewById(R.id.Name);

        final TextView textView2 = view.findViewById(R.id.Major);

        WebView webView = view.findViewById(R.id.webview_3);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.evaluateJavascript(
                                "(function() { " +
                                        "  var tdElement = document.getElementById('NS801402');" +
                                        "  if (tdElement) {" +
                                        "    var onmouseoverValue = tdElement.getAttribute('onmouseover');" +
                                        "    if (onmouseoverValue) {" +
                                        "      onmouseoverValue;" +
                                        "    } else {" +
                                        "      'onmouseover attribute not found';" +
                                        "    }" +
                                        "  } else {" +
                                        "    'Element with id \"NS801402\" not found';" +
                                        "  }" +
                                        "})()",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        // JavaScript 코드의 결과 값(value)을 받습니다.
                                        if (value != null) {
                                            Log.d("WebViewResult", "onmouseover value: " + value);


                                        } else {
                                            Log.d("WebViewResult", "JavaScript execution failed or returned null");
                                        }
                                    }
                                }
                        );

                    }
                }, 3000); // 1초 대기
            }
        });


        webView.loadUrl("https://portal.anyang.ac.kr/web/15139/1");

        return view;
    }
}