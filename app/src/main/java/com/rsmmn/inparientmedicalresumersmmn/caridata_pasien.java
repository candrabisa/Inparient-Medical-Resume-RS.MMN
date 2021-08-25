package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_IdentitasPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_IdentitasPasien;

import java.util.ArrayList;
import java.util.List;

public class caridata_pasien extends AppCompatActivity {

    RecyclerView rv_cariData;
    List<Model_IdentitasPasien> listPasienModel = new ArrayList<>();
    Adapter_IdentitasPasien listPasienAdapter;

    Button btn_lanjutkanDataResume;

    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caridata_pasien);
        btn_lanjutkanDataResume = findViewById(R.id.btn_lanjutkanDataResume);
        btn_lanjutkanDataResume.setVisibility(View.GONE);
        rv_cariData = findViewById(R.id.rv_cariDataPasien);

        rv_cariData.setLayoutManager(new LinearLayoutManager(this));

        loadDataPasien();

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
}