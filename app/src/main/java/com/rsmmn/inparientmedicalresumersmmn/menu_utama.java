package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu_utama extends AppCompatActivity {

    private Boolean keluar = false;

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

    @Override
    public void onBackPressed() {
        if (keluar){
            finish();
            System.exit(0);
        } else {
            Toast.makeText(menu_utama.this, "Tekan back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    keluar = true;
                }
            }, 1000);
        }
    }
}