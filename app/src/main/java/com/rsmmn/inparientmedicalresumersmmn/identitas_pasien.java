package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class identitas_pasien extends AppCompatActivity {

    EditText et_noRmIdenPasien, et_namaIdenPasien, et_tglLahirIdenPasien
            , et_umurIdenPasien, et_tglMasukIdenPasien, et_tglKeluarIdenPasien
            , et_namaAdmin;
    Spinner sp_jekelIdenPasien, sp_ruangPerawatan, sp_Jaminan;
    Button btn_simpan;
    ImageView btn_kembaliIdenPasien;

    Calendar calendar, calendar1, calendar2;
    DatePickerDialog.OnDateSetListener date, date1, date2;
    Animation daribawah, hilang;

    DatabaseReference dRef, dRef1;

    String userkey_ = "userkey";
    String userkey = "";
    String userkekey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identitas_pasien);

        getUserLocal();

        daribawah = AnimationUtils.loadAnimation(this, R.anim.daribawah);
        hilang = AnimationUtils.loadAnimation(this, R.anim.hilang);

        btn_simpan = findViewById(R.id.btn_simpanIdenPasien);
        btn_kembaliIdenPasien = findViewById(R.id.btn_kembaliIdenPasien);
        et_noRmIdenPasien = findViewById(R.id.et_noRmIdenPasien);
        et_namaIdenPasien = findViewById(R.id.et_namaIdenPasien);
        et_tglLahirIdenPasien = findViewById(R.id.et_tglLahirIdenPasien);
        et_umurIdenPasien = findViewById(R.id.et_umurIdenPasien);
        et_tglMasukIdenPasien = findViewById(R.id.et_tglMasukIdenPasien);
        et_tglKeluarIdenPasien = findViewById(R.id.et_tglKeluarIdenPasien);
        et_namaAdmin = findViewById(R.id.et_namaAdmin);
        sp_jekelIdenPasien = findViewById(R.id.sp_jekelIdenPasien);
        sp_ruangPerawatan = findViewById(R.id.sp_ruangPerawatan);
        sp_Jaminan = findViewById(R.id.sp_Jaminan);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                final String norm = bundle.getString("no_rm");
                final String nama_pasien = bundle.getString("nama_pasien");
                final String tgl_lahir = bundle.getString("tgl_lahir");
                final String jenis_kelamin = bundle.getString("jenis_kelamin");
                final String umur = bundle.getString("umur");
                final String tgl_masuk = bundle.getString("tgl_masuk");
                final String tgl_keluar = bundle.getString("tgl_keluar");
                final String ruang_perawatan = bundle.getString("ruang_perawatan");
                final String jaminan = bundle.getString("jaminan");

                et_noRmIdenPasien.setText(norm);
                et_namaIdenPasien.setText(nama_pasien);
                et_tglLahirIdenPasien.setText(tgl_lahir);
                et_umurIdenPasien.setText(umur);
                et_tglMasukIdenPasien.setText(tgl_masuk);
                et_tglKeluarIdenPasien.setText(tgl_keluar);

                if (jenis_kelamin.equals("Laki-laki")){
                    sp_jekelIdenPasien.setSelection(0);
                } else {
                    sp_jekelIdenPasien.setSelection(1);
                }

                switch (ruang_perawatan) {
                    case "Dharma II (A)":
                        sp_ruangPerawatan.setSelection(0);
                        break;
                    case "Dharma II (B)":
                        sp_ruangPerawatan.setSelection(1);
                        break;
                    case "Dharma III":
                        sp_ruangPerawatan.setSelection(2);
                        break;
                    case "Dharma IV":
                        sp_ruangPerawatan.setSelection(3);
                        break;
                    case "Dharma V":
                        sp_ruangPerawatan.setSelection(4);
                        break;
                    case "Dharma VI":
                        sp_ruangPerawatan.setSelection(5);
                        break;
                    case "Dharma VII":
                        sp_ruangPerawatan.setSelection(6);
                        break;
                    case "Dharma VIII":
                        sp_ruangPerawatan.setSelection(7);
                        break;
                    case "Dharma IX":
                        sp_ruangPerawatan.setSelection(8);
                        break;
                }

                if (jaminan.equals("UMUM")){
                    sp_Jaminan.setSelection(0);
                } else if (jaminan.equals("KEMENKES")){
                    sp_Jaminan.setSelection(1);
                } else if (jaminan.equals("BPJS KESEHATAN")){
                    sp_Jaminan.setSelection(2);
                } else if (jaminan.equals("BPJS KETENAGAKERJAAN")){
                    sp_Jaminan.setSelection(3);
                } else if (jaminan.equals("JAMKESDA")){
                    sp_Jaminan.setSelection(4);
                } else if (jaminan.equals("JAMPERSAL")){
                    sp_Jaminan.setSelection(5);
                } else {
                    sp_Jaminan.setSelection(6);
                }
            } else {
                et_noRmIdenPasien.setText("");
                et_namaIdenPasien.setText("");
                et_tglLahirIdenPasien.setText("");
                et_umurIdenPasien.setText("");
                et_tglMasukIdenPasien.setText("");
                et_tglKeluarIdenPasien.setText("");
                sp_jekelIdenPasien.setSelection(0);
                sp_ruangPerawatan.setSelection(0);
                sp_Jaminan.setSelection(0);
            }
        }catch (Exception e){

        }

        dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child(userkekey);
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                et_namaAdmin.setText(snapshot.child("nama_lengkap").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomor_rm = et_noRmIdenPasien.getText().toString();
                final String nama_pasien = et_namaIdenPasien.getText().toString();
                final String tgl_lahir_pasien = et_tglLahirIdenPasien.getText().toString();
                final String jenis_kelamin_pasien = sp_jekelIdenPasien.getSelectedItem().toString();
                final String umur_pasien = et_umurIdenPasien.getText().toString();
                final String tgl_masuk_pasien = et_tglMasukIdenPasien.getText().toString();
                final String tgl_keluar_pasien = et_tglKeluarIdenPasien.getText().toString();
                final String ruang_perawatan_pasien = sp_ruangPerawatan.getSelectedItem().toString();
                final String jaminan_pasien = sp_Jaminan.getSelectedItem().toString();
                final String nama_admin_rm = et_namaAdmin.getText().toString();

                try {
                    if (nomor_rm.isEmpty()){
                        et_noRmIdenPasien.setError("Belum diisi");
                        et_noRmIdenPasien.setFocusable(true);
                        return;
                    } else if (nama_pasien.isEmpty()){
                        et_namaIdenPasien.setError("Belum diisi");
                        et_namaIdenPasien.setFocusable(true);
                        return;
                    } else if (umur_pasien.isEmpty()){
                        et_umurIdenPasien.setError("Belum diisi");
                        et_umurIdenPasien.setFocusable(true);
                        return;
                    } else if (tgl_lahir_pasien.isEmpty()){
                        et_tglLahirIdenPasien.setError("Belum diisi");
                        et_tglLahirIdenPasien.setFocusable(true);
                        return;
                    } else if (tgl_masuk_pasien.isEmpty()){
                        et_tglMasukIdenPasien.setError("Belum diisi");
                        et_tglMasukIdenPasien.setFocusable(true);
                        return;
                    } else if (tgl_keluar_pasien.isEmpty()){
                        et_tglKeluarIdenPasien.setError("Belum diisi");
                        et_tglKeluarIdenPasien.setFocusable(true);
                        return;
                    } else {
                        ProgressDialog progressDialog = new ProgressDialog(identitas_pasien.this);
                        progressDialog.setTitle("Memproses Data");
                        progressDialog.setMessage("Menyimpan data ke server...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        dRef1 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("identitasPasien").child(nomor_rm);
                        dRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(identitas_pasien.this);
                                    builder.setTitle("No. RM Telah Digunakan!!");
                                    builder.setMessage("No. RM sudah ada atau telah digunakan, mohon mengganti No. RM yang lain");
                                    builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                } else {
                                    snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                    snapshot.getRef().child("nama_pasien").setValue(nama_pasien);
                                    snapshot.getRef().child("tgl_lahir_pasien").setValue(tgl_lahir_pasien);
                                    snapshot.getRef().child("jenis_kelamin_pasien").setValue(jenis_kelamin_pasien);
                                    snapshot.getRef().child("umur_pasien").setValue(umur_pasien);
                                    snapshot.getRef().child("tgl_masuk_pasien").setValue(tgl_masuk_pasien);
                                    snapshot.getRef().child("tgl_keluar_pasien").setValue(tgl_keluar_pasien);
                                    snapshot.getRef().child("ruang_perawatan_pasien").setValue(ruang_perawatan_pasien);
                                    snapshot.getRef().child("jaminan_pasien").setValue(jaminan_pasien);
                                    snapshot.getRef().child("nama_admin_rm").setValue(nama_admin_rm);

                                    progressDialog.dismiss();
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(identitas_pasien.this);
                                    dialog.setTitle("Success");
                                    dialog.setMessage("Data berhasil disimpan, apakah ingin melanjutkan input data KLINIS PASIEN?");
                                    dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intentNext = new Intent(identitas_pasien.this, klinis_pasien.class);
                                            intentNext.putExtra("nomor_rm", nomor_rm);
                                            startActivity(intentNext);
                                            dialog.dismiss();

                                            et_noRmIdenPasien.setText("");
                                            et_namaIdenPasien.setText("");
                                            et_tglLahirIdenPasien.setText("");
                                            et_umurIdenPasien.setText("");
                                            et_tglMasukIdenPasien.setText("");
                                            et_tglKeluarIdenPasien.setText("");
                                            et_noRmIdenPasien.setFocusable(true);
                                        }
                                    });
                                    dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            et_noRmIdenPasien.setText("");
                                            et_namaIdenPasien.setText("");
                                            et_tglLahirIdenPasien.setText("");
                                            et_umurIdenPasien.setText("");
                                            et_tglMasukIdenPasien.setText("");
                                            et_tglKeluarIdenPasien.setText("");
                                            et_noRmIdenPasien.setFocusable(true);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                    et_noRmIdenPasien.setFocusable(true);

                                    dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("folderPasien").child(nomor_rm);
                                    dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                            snapshot.getRef().child("nama_pasien").setValue(nama_pasien);
                                            snapshot.getRef().child("tgl_lahir_pasien").setValue(tgl_lahir_pasien);
                                            snapshot.getRef().child("jenis_kelamin_pasien").setValue(jenis_kelamin_pasien);
                                            snapshot.getRef().child("umur_pasien").setValue(umur_pasien);
                                            snapshot.getRef().child("tgl_masuk_pasien").setValue(tgl_masuk_pasien);
                                            snapshot.getRef().child("tgl_keluar_pasien").setValue(tgl_keluar_pasien);
                                            snapshot.getRef().child("ruang_perawatan_pasien").setValue(ruang_perawatan_pasien);
                                            snapshot.getRef().child("jaminan_pasien").setValue(jaminan_pasien);
                                            snapshot.getRef().child("nama_admin_rm").setValue(nama_admin_rm);
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
                }catch (Exception e){
                    Toast.makeText(identitas_pasien.this, "Sedang mengalami gangguan karena" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendar = Calendar.getInstance();
        Calendar tgl_lahir = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTgl = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTgl, Locale.US);
                et_tglLahirIdenPasien.setText(sdf.format(calendar.getTime()));

                int tahun = tgl_lahir.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
                Integer umur = tahun;
                et_umurIdenPasien.setText(umur.toString());
            }
        };
        calendar1 = Calendar.getInstance();
        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTgl = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTgl, Locale.US);
                et_tglMasukIdenPasien.setText(sdf.format(calendar1.getTime()));
            }
        };
        calendar2 = Calendar.getInstance();
        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar2.set(Calendar.YEAR, year);
                calendar2.set(Calendar.MONTH, month);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTgl = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTgl, Locale.US);
                et_tglKeluarIdenPasien.setText(sdf.format(calendar2.getTime()));
            }
        };

        et_tglLahirIdenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(identitas_pasien.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        et_tglMasukIdenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(identitas_pasien.this, date1,
                        calendar1.get(Calendar.YEAR),
                        calendar1.get(Calendar.MONTH),
                        calendar1.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        et_tglKeluarIdenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(identitas_pasien.this, date2,
                        calendar2.get(Calendar.YEAR),
                        calendar2.get(Calendar.MONTH),
                        calendar2.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        btn_kembaliIdenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void getUserLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(userkey_, MODE_PRIVATE);
        userkekey =sharedPreferences.getString(userkey, "");
    }
}