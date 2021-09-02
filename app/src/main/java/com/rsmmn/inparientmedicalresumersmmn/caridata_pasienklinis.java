package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_IdentitasPasien;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_KlinisPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_IdentitasPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_KlinisPasien;

import java.util.ArrayList;
import java.util.List;

public class caridata_pasienklinis extends AppCompatActivity {

    ImageView btn_kembaliCariKlinisPasien;
    EditText et_cariKlinisPasien;
    RecyclerView rv_cariPasienKlinis;

    List<Model_KlinisPasien> modelKlinisPasiens = new ArrayList<>();
    Adapter_KlinisPasien adapterKlinisPasien;

    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caridata_pasienklinis);

        loadDataPasien();

        btn_kembaliCariKlinisPasien = findViewById(R.id.btn_kembaliCariKlinisPasien);
        et_cariKlinisPasien = findViewById(R.id.et_cariKlinisPasien);
        rv_cariPasienKlinis = findViewById(R.id.rv_cariPasienKlinis);
        rv_cariPasienKlinis.setLayoutManager(new LinearLayoutManager(this));

        btn_kembaliCariKlinisPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_cariKlinisPasien.addTextChangedListener(new TextWatcher() {
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
    }

    private void loadDataPasien(){
        ProgressDialog progressDialog = new ProgressDialog(caridata_pasienklinis.this);
        progressDialog.setMessage("Memuat data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("identitasPasien");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelKlinisPasiens.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model_KlinisPasien model = dataSnapshot.getValue(Model_KlinisPasien.class);
                    modelKlinisPasiens.add(model);

                    adapterKlinisPasien = new Adapter_KlinisPasien(caridata_pasienklinis.this, modelKlinisPasiens);
                    rv_cariPasienKlinis.setAdapter(adapterKlinisPasien);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cariPasien(final String s) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("identitasPasien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelKlinisPasiens.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Model_KlinisPasien model = ds.getValue(Model_KlinisPasien.class);

                    Log.d("cek1", model.getNomor_rm());
                    Log.d("cek2", model.getNama_pasien());
                    if (model.getNomor_rm().toLowerCase().contains(s.toLowerCase()) ||
                            model.getNama_pasien().toLowerCase().contains(s.toLowerCase())){
                        modelKlinisPasiens.add(model);
                    }

                }
                adapterKlinisPasien = new Adapter_KlinisPasien(caridata_pasienklinis.this, modelKlinisPasiens);
                rv_cariPasienKlinis.setAdapter(adapterKlinisPasien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}