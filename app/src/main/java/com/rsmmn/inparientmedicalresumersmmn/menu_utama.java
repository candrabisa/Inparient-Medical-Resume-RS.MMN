package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu_utama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        if (savedInstanceState == null){
            fragment_home fragmentHome =new fragment_home();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragmentHome)
                    .commit();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navBeranda:
                    fragment_home fragmentHome = new fragment_home();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragmentHome)
                            .commit();
                    return true;
                case R.id.navFolder:
                    fragment_folder fragmentFolder = new fragment_folder();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragmentFolder)
                            .commit();
                    return true;
                case R.id.navAccount:
                    fragment_akun fragmentAkun = new fragment_akun();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragmentAkun)
                            .commit();
                    return true;
            }
            return false;
        }
    };
}