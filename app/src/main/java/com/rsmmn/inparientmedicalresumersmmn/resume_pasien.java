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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class resume_pasien extends AppCompatActivity {

    EditText et_nomorRmKlinis, et_ringkasanPasien, et_pengobatanPasien,
            et_konsultasiPasien, et_diagnosisUtamaPasien, et_diagnosisSekunderPasien,
            et_tindakanPasien, et_obatPulangPasien;
    Spinner sp_kondisiPasienPulang, sp_pengobatanNextPasien, sp_dokterPenanggungJawab;
    Button btn_simpanResume;
    LinearLayout btnCariPasienKlinis;
    ImageView btn_kembaliresumePasien;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_pasien);

        btnCariPasienKlinis = findViewById(R.id.ll_cariPasienKlinis);
        btn_simpanResume = findViewById(R.id.btn_simpanResume);
        btn_kembaliresumePasien = findViewById(R.id.btn_kembaliresumePasien);
        et_nomorRmKlinis = findViewById(R.id.et_nomorRmKlinis);
        et_ringkasanPasien = findViewById(R.id.et_ringkasanPasien);
        et_pengobatanPasien = findViewById(R.id.et_pengobatanPasien);
        et_konsultasiPasien = findViewById(R.id.et_konsultasiPasien);
        et_diagnosisUtamaPasien = findViewById(R.id.et_diagnosisUtamaPasien);
        et_diagnosisSekunderPasien = findViewById(R.id.et_diagnosisSekunderPasien);
        et_tindakanPasien = findViewById(R.id.et_tindakanPasien);
        et_obatPulangPasien = findViewById(R.id.et_obatPulangPasien);
        sp_dokterPenanggungJawab = findViewById(R.id.sp_dokterPenanggungJawab);
        sp_kondisiPasienPulang = findViewById(R.id.sp_kondisiPasienPulang);
        sp_pengobatanNextPasien = findViewById(R.id.sp_pengobatanNextPasien);

        et_nomorRmKlinis.setEnabled(false);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                final String no_rm = bundle.getString("no_rm");
                final String ringkasan = bundle.getString("ringkasan");
                final String terapi = bundle.getString("terapi");
                final String konsultasi = bundle.getString("konsultasi");
                final String diagnosis_utama = bundle.getString("diagnosis_utama");
                final String diagnosis_sekunder = bundle.getString("diagnosis_sekunder");
                final String tindakan = bundle.getString("tindakan");
                final String obat_pulang = bundle.getString("obat_pulang");
                final String kondisi_pulang = bundle.getString("kondisi_pulang");
                final String pengobatan_selanjutnya = bundle.getString("pengobatan_selanjutnya");
                final String dokter_penanggung_jawab = bundle.getString("dokter_penanggung_jawab");

                et_nomorRmKlinis.setText(no_rm);
                et_ringkasanPasien.setText(ringkasan);
                et_pengobatanPasien.setText(terapi);
                et_konsultasiPasien.setText(konsultasi);
                et_diagnosisUtamaPasien.setText(diagnosis_utama);
                et_diagnosisSekunderPasien.setText(diagnosis_sekunder);
                et_tindakanPasien.setText(tindakan);
                et_obatPulangPasien.setText(obat_pulang);

                if (kondisi_pulang.equalsIgnoreCase("Sembuh")){
                    sp_kondisiPasienPulang.setSelection(0);
                } else if (kondisi_pulang.equalsIgnoreCase("Masih sakit")){
                    sp_kondisiPasienPulang.setSelection(1);
                } else if (kondisi_pulang.equals("Perbaikan")){
                    sp_kondisiPasienPulang.setSelection(2);
                } else if (kondisi_pulang.equalsIgnoreCase("Rujuk RS Lain")){
                    sp_kondisiPasienPulang.setSelection(3);
                } else if (kondisi_pulang.equalsIgnoreCase("Meninggal")){
                    sp_kondisiPasienPulang.setSelection(4);
                } else {
                    sp_kondisiPasienPulang.setSelection(5);
                }

                if (pengobatan_selanjutnya.equalsIgnoreCase("puskesmas")){
                    sp_pengobatanNextPasien.setSelection(0);
                } else if (pengobatan_selanjutnya.equalsIgnoreCase("poliklinik")){
                    sp_pengobatanNextPasien.setSelection(1);
                } else if (pengobatan_selanjutnya.equalsIgnoreCase("rs lain")){
                    sp_pengobatanNextPasien.setSelection(2);
                } else {
                    sp_pengobatanNextPasien.setSelection(3);
                }

                if (dokter_penanggung_jawab.equals("dr. Rinaldy Panusunan Lubis, Sp.P")){
                    sp_dokterPenanggungJawab.setSelection(0);
                } else if (dokter_penanggung_jawab.equals("dr. Ira, Sp.A")){
                    sp_dokterPenanggungJawab.setSelection(1);
                } else if (dokter_penanggung_jawab.equals("dr. Jansu S, Sp.B")){
                    sp_dokterPenanggungJawab.setSelection(2);
                } else if (dokter_penanggung_jawab.equals("dr. Frans, Sp.S")){
                    sp_dokterPenanggungJawab.setSelection(3);
                } else if (dokter_penanggung_jawab.equals("dr. Hendrik, Sp.OG")){
                    sp_dokterPenanggungJawab.setSelection(4);
                } else {
                    sp_dokterPenanggungJawab.setSelection(5);
                }
            }
        } catch (Exception e){
         e.printStackTrace();
        }

        btn_simpanResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomor_rm = et_nomorRmKlinis.getText().toString();
                final String ringkasan = et_ringkasanPasien.getText().toString();
                final String pengobatan = et_pengobatanPasien.getText().toString();
                final String konsultasi = et_konsultasiPasien.getText().toString();
                final String diagnosisUtama = et_diagnosisUtamaPasien.getText().toString();
                final String diagnosisSekunder = et_diagnosisSekunderPasien.getText().toString();
                final String tindakan = et_tindakanPasien.getText().toString();
                final String obatPulang = et_obatPulangPasien.getText().toString();
                final String kondisi_pulang = sp_kondisiPasienPulang.getSelectedItem().toString();
                final String pengobatan_selanjutnya = sp_pengobatanNextPasien.getSelectedItem().toString();
                final String dokter_penanggungjawab = sp_dokterPenanggungJawab.getSelectedItem().toString();

                if (nomor_rm.equals("")){
                    et_nomorRmKlinis.setError("Belum dipilih");
                    Toast.makeText(resume_pasien.this, "No.RM belum dipilih", Toast.LENGTH_SHORT).show();
                } else if(ringkasan.isEmpty()){
                    et_ringkasanPasien.setError("Belum diisi");
                    et_ringkasanPasien.setFocusable(true);
                    return;
                } else if (pengobatan.isEmpty()){
                    et_pengobatanPasien.setError("Belum diisi");
                    et_pengobatanPasien.setFocusable(true);
                    return;
                } else if (konsultasi.isEmpty()){
                    et_konsultasiPasien.setError("Belum diisi");
                    et_konsultasiPasien.setFocusable(true);
                    return;
                } else if (diagnosisUtama.isEmpty()){
                    et_diagnosisUtamaPasien.setError("Belum diisi");
                    et_diagnosisUtamaPasien.setFocusable(true);
                    return;
                } else if (diagnosisSekunder.isEmpty()){
                    et_diagnosisSekunderPasien.setError("Belum diisi");
                    et_diagnosisSekunderPasien.setFocusable(true);
                    return;
                } else if (tindakan.isEmpty()){
                    et_tindakanPasien.setError("Belum diisi");
                    et_tindakanPasien.setFocusable(true);
                    return;
                } else if (obatPulang.isEmpty()){
                    et_obatPulangPasien.setError("Belum diisi");
                    et_obatPulangPasien.setFocusable(true);
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(resume_pasien.this);
                    builder.setTitle("Perhatian");
                    builder.setMessage("Pastikan ada yang akan disimpan sudah sesuai!");
                    builder.setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = new ProgressDialog(resume_pasien.this);
                            progressDialog.setMessage("Mohon menunggu...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            Query query = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("resumePasien").child(nomor_rm);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        AlertDialog.Builder alert = new AlertDialog.Builder(resume_pasien.this);
                                        alert.setTitle("Data Sudah Ada!");
                                        alert.setMessage("Data klinis pada nomor RM tersebut sudah pernah di input, Jika dilanjutkan akan merubah data sebelumnya");
                                        alert.setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                snapshot.getRef().child("ringkasan_pasien").setValue(ringkasan);
                                                snapshot.getRef().child("pengobatan_pasien").setValue(pengobatan);
                                                snapshot.getRef().child("konsultasi_pasien").setValue(konsultasi);
                                                snapshot.getRef().child("diagnosis_utama_pasien").setValue(diagnosisUtama);
                                                snapshot.getRef().child("diagnosis_sekunder_pasien").setValue(diagnosisSekunder);
                                                snapshot.getRef().child("tindakan_pasien").setValue(tindakan);
                                                snapshot.getRef().child("obat_pulang").setValue(obatPulang);
                                                snapshot.getRef().child("kondisi_pulang_pasien").setValue(kondisi_pulang);
                                                snapshot.getRef().child("pengobatan_selanjutnya_pasien").setValue(pengobatan_selanjutnya);
                                                snapshot.getRef().child("dokter_penanggung_jawab").setValue(dokter_penanggungjawab);

                                                progressDialog.dismiss();

                                                Toast.makeText(resume_pasien.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                                                et_nomorRmKlinis.setText("");
                                                et_ringkasanPasien.setText("");
                                                et_diagnosisUtamaPasien.setText("");
                                                et_diagnosisSekunderPasien.setText("");
                                                et_konsultasiPasien.setText("");
                                                et_pengobatanPasien.setText("");
                                                et_obatPulangPasien.setText("");
                                                et_tindakanPasien.setText("");

                                                databaseReference = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                                        .getReference("folderPasien").child(nomor_rm);
                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                        snapshot.getRef().child("ringkasan_pasien").setValue(ringkasan);
                                                        snapshot.getRef().child("pengobatan_pasien").setValue(pengobatan);
                                                        snapshot.getRef().child("konsultasi_pasien").setValue(konsultasi);
                                                        snapshot.getRef().child("diagnosis_utama_pasien").setValue(diagnosisUtama);
                                                        snapshot.getRef().child("diagnosis_sekunder_pasien").setValue(diagnosisSekunder);
                                                        snapshot.getRef().child("tindakan_pasien").setValue(tindakan);
                                                        snapshot.getRef().child("obat_pulang").setValue(obatPulang);
                                                        snapshot.getRef().child("kondisi_pulang_pasien").setValue(kondisi_pulang);
                                                        snapshot.getRef().child("pengobatan_selanjutnya_pasien").setValue(pengobatan_selanjutnya);
                                                        snapshot.getRef().child("dokter_penanggung_jawab").setValue(dokter_penanggungjawab);

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        });
                                        alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                progressDialog.dismiss();
                                            }
                                        });
                                        alert.show();
                                    } else {
                                        snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                        snapshot.getRef().child("ringkasan_pasien").setValue(ringkasan);
                                        snapshot.getRef().child("pengobatan_pasien").setValue(pengobatan);
                                        snapshot.getRef().child("konsultasi_pasien").setValue(konsultasi);
                                        snapshot.getRef().child("diagnosis_utama_pasien").setValue(diagnosisUtama);
                                        snapshot.getRef().child("diagnosis_sekunder_pasien").setValue(diagnosisSekunder);
                                        snapshot.getRef().child("tindakan_pasien").setValue(tindakan);
                                        snapshot.getRef().child("obat_pulang").setValue(obatPulang);
                                        snapshot.getRef().child("kondisi_pulang_pasien").setValue(kondisi_pulang);
                                        snapshot.getRef().child("pengobatan_selanjutnya_pasien").setValue(pengobatan_selanjutnya);
                                        snapshot.getRef().child("dokter_penanggung_jawab").setValue(dokter_penanggungjawab);

                                        progressDialog.dismiss();

                                        Toast.makeText(resume_pasien.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                                        et_nomorRmKlinis.setText("");
                                        et_ringkasanPasien.setText("");
                                        et_diagnosisUtamaPasien.setText("");
                                        et_diagnosisSekunderPasien.setText("");
                                        et_konsultasiPasien.setText("");
                                        et_pengobatanPasien.setText("");
                                        et_obatPulangPasien.setText("");
                                        et_tindakanPasien.setText("");

                                        databaseReference = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                                .getReference("folderPasien").child(nomor_rm);
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                snapshot.getRef().child("ringkasan_pasien").setValue(ringkasan);
                                                snapshot.getRef().child("pengobatan_pasien").setValue(pengobatan);
                                                snapshot.getRef().child("konsultasi_pasien").setValue(konsultasi);
                                                snapshot.getRef().child("diagnosis_utama_pasien").setValue(diagnosisUtama);
                                                snapshot.getRef().child("diagnosis_sekunder_pasien").setValue(diagnosisSekunder);
                                                snapshot.getRef().child("tindakan_pasien").setValue(tindakan);
                                                snapshot.getRef().child("obat_pulang").setValue(obatPulang);
                                                snapshot.getRef().child("kondisi_pulang_pasien").setValue(kondisi_pulang);
                                                snapshot.getRef().child("pengobatan_selanjutnya_pasien").setValue(pengobatan_selanjutnya);
                                                snapshot.getRef().child("dokter_penanggung_jawab").setValue(dokter_penanggungjawab);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                }

            }
        });
        btnCariPasienKlinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(resume_pasien.this, caridata_pasienklinis.class));
            }
        });
        btn_kembaliresumePasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}