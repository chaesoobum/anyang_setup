package com.example.anyang_setup;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {



    HomeFragment homeFragment;
    InfoFragment infoFragment;
    SettingFragment settingFragment;
    SpecFragment specFragment;


    public static WebView myWebView;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String IDtomain = extras.getString("IDtomain");
            String PWtomain = extras.getString("PWtomain");

            // HomeFragment에 데이터 전달
            if (homeFragment != null) {
                homeFragment.receiveData(IDtomain, PWtomain);
            }
        }


        homeFragment = new HomeFragment();
        infoFragment = new InfoFragment();
        settingFragment = new SettingFragment();
        specFragment = new SpecFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int selectedItemId = item.getItemId();
                if (selectedItemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                    return true;
                } else if (selectedItemId == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                    return true;
                } else if (selectedItemId == R.id.info) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, infoFragment).commit();
                    return true;
                } else if (selectedItemId == R.id.spec) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, specFragment).commit();
                    return true;
                }

                return false;
            }
        });


    }


}