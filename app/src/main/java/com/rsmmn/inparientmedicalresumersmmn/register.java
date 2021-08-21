package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    ImageView btn_kembaliReg;
    Button btn_daftarReg;
    EditText et_namaLengkapReg, et_idCardReg, et_emailReg, et_pwReg;

    ProgressDialog progressDialog;

    DatabaseReference dRef;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_kembaliReg = findViewById(R.id.btn_kembaliReg);
        btn_daftarReg = findViewById(R.id.btn_daftarReg);
        et_namaLengkapReg = findViewById(R.id.et_namaLengkapReg);
        et_idCardReg = findViewById(R.id.et_idCardReg);
        et_emailReg = findViewById(R.id.et_emailReg);
        et_pwReg = findViewById(R.id.et_pwReg);

        fAuth = FirebaseAuth.getInstance();

        btn_daftarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final String nama_lengkap = et_namaLengkapReg.getText().toString();
                    final String idcard = et_idCardReg.getText().toString();
                    final String email = et_emailReg.getText().toString();
                    final String password = et_pwReg.getText().toString();

                    if (nama_lengkap.isEmpty()){
                        et_namaLengkapReg.setError("Nama lengkap belum diisi");
                        et_namaLengkapReg.setFocusable(true);
                        return;
                    } else if (idcard.isEmpty()){
                        et_idCardReg.setError("Id Card belum diisi");
                        et_idCardReg.setFocusable(true);
                        return;
                    } else if (email.isEmpty()){
                        et_emailReg.setError("Email belum diisi");
                        et_emailReg.setFocusable(true);
                        return;
                    }  else if (password.isEmpty()){
                        et_pwReg.setError("Password belum diisi");
                        et_pwReg.setFocusable(true);
                        return;
                    } else if (password.length()<6){
                        et_pwReg.setError("Password minimal 6 karakter");
                        et_pwReg.setFocusable(true);
                        return;
                    } else {
                        progressDialog = new ProgressDialog(register.this);
                        progressDialog.setMessage("Mohon Menunggu...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference().child("Users").child(et_idCardReg.getText().toString());
                        dRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(register.this, "ID Card telah digunakan", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    fAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(register.this, "Email tidak sesuai atau telah digunakan", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    } else {
                                                        snapshot.getRef().child("nama_lengkap").setValue(nama_lengkap);
                                                        snapshot.getRef().child("idcard").setValue(idcard);
                                                        snapshot.getRef().child("email").setValue(email);
                                                        snapshot.getRef().child("password").setValue(password);

                                                        Toast.makeText(register.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(register.this, "Server sedang gangguan karena" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(register.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_kembaliReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}