package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    ImageView btn_kembaliLog;
    TextView et_idCardLogin, et_pwLogin;
    Button btn_loginLog;

    FirebaseAuth fAuth;
    DatabaseReference dRef;

    String userkey_ = "userkey";
    String userkey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_kembaliLog = findViewById(R.id.btn_kembaliLog);
        btn_loginLog = findViewById(R.id.btn_loginLog);
        et_idCardLogin = findViewById(R.id.et_idCardLogin);
        et_pwLogin = findViewById(R.id.et_pwLogin);

        btn_loginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String idcard = et_idCardLogin.getText().toString();
                final String password = et_pwLogin.getText().toString();

                if (idcard.isEmpty()){
                    et_idCardLogin.setError("Belum diisi");
                    et_idCardLogin.setFocusable(true);
                    return;
                } else if (password.isEmpty() && password.length()<6){
                    et_pwLogin.setError("Belum diisi / kurang dari 6 karakter");
                    et_pwLogin.setFocusable(true);
                    return;
                } else {
                    ProgressDialog progressDialog = new ProgressDialog(login.this);
                    progressDialog.setMessage("Mohon menunggu...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();

                    dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("Users").child(idcard);
                    dRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                 String pwFromFirebase = snapshot.child("password").getValue().toString();
                                if (password.equals(pwFromFirebase)){
                                    SharedPreferences sPref = getSharedPreferences(userkey_, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sPref.edit();
                                    editor.putString(userkey, idcard);
                                    editor.apply();
                                    progressDialog.dismiss();

                                    startActivity(new Intent(login.this, menu_utama.class));
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "ID Card atau Password salah", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        btn_kembaliLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}