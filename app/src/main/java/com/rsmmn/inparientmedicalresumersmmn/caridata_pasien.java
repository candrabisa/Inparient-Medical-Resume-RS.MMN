package com.rsmmn.inparientmedicalresumersmmn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_IdentitasPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_IdentitasPasien;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class caridata_pasien extends AppCompatActivity {

    ImageView btn_kembaliCariIdenPasien;
    RecyclerView rv_cariData;
    List<Model_IdentitasPasien> listPasienModel = new ArrayList<>();
    Adapter_IdentitasPasien listPasienAdapter;
    EditText et_cariPasien;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caridata_pasien);

        btn_kembaliCariIdenPasien = findViewById(R.id.btn_kembaliCariIdenPasien);
        et_cariPasien = findViewById(R.id.et_cariPasien);
        rv_cariData = findViewById(R.id.rv_cariDataPasien);
        rv_cariData.setLayoutManager(new LinearLayoutManager(this));

        loadDataPasien();

        btn_kembaliCariIdenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_cariPasien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    cariPasien(s.toString());
                } else {
                    loadDataPasien();
                }
            }
        });

        et_cariPasien.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String cari_pasien = et_cariPasien.getText().getFilters().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    cariPasien(cari_pasien);
                    return true;
                } else {
                    loadDataPasien();
                }
                return false;
            }
        });

    }

    private void loadDataPasien(){
        ProgressDialog progressDialog = new ProgressDialog(caridata_pasien.this);
        progressDialog.setMessage("Memuat data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("identitasPasien");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPasienModel.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model_IdentitasPasien model = dataSnapshot.getValue(Model_IdentitasPasien.class);
                    listPasienModel.add(model);

                    listPasienAdapter = new Adapter_IdentitasPasien(caridata_pasien.this, listPasienModel);
                    rv_cariData.setAdapter(listPasienAdapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cariPasien(final String s) {
        DatabaseReference databaseReference =FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("identitasPasien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPasienModel.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Model_IdentitasPasien model = ds.getValue(Model_IdentitasPasien.class);

                    Log.d("cek1", model.getNomor_rm());
                    Log.d("cek2", model.getNama_pasien());
                    Log.d("cek3", model.getTgl_lahir_pasien());
                    if (model.getNomor_rm().toLowerCase().contains(s.toLowerCase()) ||
                            model.getNama_pasien().toLowerCase().contains(s.toLowerCase())){
                        listPasienModel.add(model);
                    }

                }
                listPasienAdapter = new Adapter_IdentitasPasien(caridata_pasien.this, listPasienModel);
                listPasienAdapter.notifyDataSetChanged();
                rv_cariData.setAdapter(listPasienAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}