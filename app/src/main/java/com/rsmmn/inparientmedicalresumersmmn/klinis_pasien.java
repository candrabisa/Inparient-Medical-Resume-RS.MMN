package com.rsmmn.inparientmedicalresumersmmn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.ImagePickerActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_IdentitasPasien;
import com.squareup.picasso.Picasso;

public class klinis_pasien extends AppCompatActivity {

    LinearLayout btn_cariDataPasien, ll_ttd;
    EditText et_nomorRmKlinis, et_alergiPasien, et_diagnosaAwalPasien
            ,et_ttdPasien1, et_ttdPasien2, et_nadiPasien, et_resusitasiPasien,
            et_suhuPasien,et_spo2Pasien, et_pupilPasien;
    ImageButton btn_hasilLaboPasien, btn_hasilRadioPasien, btn_hasilPenunjangPasien;
    Button btn_simpanKlinis;
    ImageView iv_doneHasilPenunjang, iv_doneHasilRadio, iv_doneHasilLabo
            ,btn_kembaliKlinisPasien;

    String norm_ = "norm";
    String norm = "";
    String normkey = "";

    DatabaseReference dRef, dRef1;
    StorageReference sPref;
    Uri lokasi_foto, lokasi_foto1, lokasi_foto2;

    int requestCode;
    private final int request_pertama = 1;
    private final int request_kedua = 2;
    private final int request_ketiga = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinis_pasien);

        btn_cariDataPasien = findViewById(R.id.ll_cariDataPasien);
        btn_hasilLaboPasien = findViewById(R.id.btn_hasilLaboPasien);
        btn_hasilRadioPasien = findViewById(R.id.btn_hasilRadioPasien);
        btn_hasilPenunjangPasien = findViewById(R.id.btn_hasilPenunjangPasien);
        btn_simpanKlinis = findViewById(R.id.btn_simpanKlinis);
        btn_kembaliKlinisPasien = findViewById(R.id.btn_kembaliKlinisPasien);

        ll_ttd = findViewById(R.id.ll_ttd);

        iv_doneHasilLabo = findViewById(R.id.iv_doneHasilLabo);
        iv_doneHasilRadio = findViewById(R.id.iv_doneHasilRadio);
        iv_doneHasilPenunjang = findViewById(R.id.iv_doneHasilPenunjang);
        iv_doneHasilLabo.setVisibility(View.GONE);
        iv_doneHasilPenunjang.setVisibility(View.GONE);
        iv_doneHasilRadio.setVisibility(View.GONE);

        et_nomorRmKlinis = findViewById(R.id.et_nomorRmKlinis);
        et_nomorRmKlinis.setEnabled(false);
        et_alergiPasien = findViewById(R.id.et_alergiPasien);
        et_diagnosaAwalPasien = findViewById(R.id.et_diagnosaAwalPasien);
        et_ttdPasien1 = findViewById(R.id.et_ttdPasien1);
        et_ttdPasien2 = findViewById(R.id.et_ttdPasien2);
        et_nadiPasien = findViewById(R.id.et_nadiPasien);
        et_resusitasiPasien = findViewById(R.id.et_resusitasiPasien);
        et_suhuPasien = findViewById(R.id.et_suhuPasien);
        et_spo2Pasien = findViewById(R.id.et_spo2Pasien);
        et_pupilPasien = findViewById(R.id.et_pupilPasien);

        Bundle bundle = getIntent().getExtras();
        final String nomor_rm = bundle.getString("nomor_rm");
//        final String nama_pasien = bundle.getString("nama_pasien");
//        final String tgl_lahir = bundle.getString("tgl_lahir");
//        final String tgl_masuk = bundle.getString("tgl_masuk");
//        final String ruang_perawatan = bundle.getString("ruang_perawatan");
        et_nomorRmKlinis.setText(nomor_rm);

        et_ttdPasien1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_ttdPasien1.getText().toString().length() == 3){
                    et_ttdPasien2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_simpanKlinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alergi_pasien = et_alergiPasien.getText().toString();
                final String diagnosa_awal = et_diagnosaAwalPasien.getText().toString();
                final String ttd_pasien1 = et_ttdPasien1.getText().toString();
                final String ttd_pasien2 = et_ttdPasien2.getText().toString();
                final String nadi_pasien = et_nadiPasien.getText().toString();
                final String resusitasi_pasien = et_resusitasiPasien.getText().toString();
                final String suhu_pasien = et_suhuPasien.getText().toString();
                final String spo2_pasien = et_spo2Pasien.getText().toString();
                final String pupil_pasien = et_pupilPasien.getText().toString();

                if (nomor_rm.isEmpty()){
                    et_nomorRmKlinis.setError("Belum diisi");
                    Toast.makeText(klinis_pasien.this, "Kamu belum memilih No.RM", Toast.LENGTH_SHORT).show();
                } else if (alergi_pasien.isEmpty()){
                    et_alergiPasien.setError("Belum diisi");
                    et_alergiPasien.setFocusable(true);
                    return;
                } else if (diagnosa_awal.isEmpty()){
                    et_diagnosaAwalPasien.setError("Belum diisi");
                    et_diagnosaAwalPasien.setFocusable(true);
                    return;
                } else if (ttd_pasien1.isEmpty()){
                    et_ttdPasien1.setError("Belum diisi");
                    et_ttdPasien1.setFocusable(true);
                    return;
                } else if (ttd_pasien2.isEmpty()){
                    et_ttdPasien2.setError("Belum diisi");
                    et_ttdPasien2.setFocusable(true);
                    return;
                } else if (nadi_pasien.isEmpty()){
                    et_nadiPasien.setError("Belum diisi");
                    et_nadiPasien.setFocusable(true);
                    return;
                } else if (resusitasi_pasien.isEmpty()){
                    et_resusitasiPasien.setError("Belum diisi");
                    et_resusitasiPasien.setFocusable(true);
                    return;
                } else if (suhu_pasien.isEmpty()){
                    et_suhuPasien.setError("Belum diisi");
                    et_suhuPasien.setFocusable(true);
                    return;
                } else if (spo2_pasien.isEmpty()){
                    et_spo2Pasien.setError("Belum diisi");
                    et_spo2Pasien.setFocusable(true);
                    return;
                } else if (pupil_pasien.isEmpty()){
                    et_pupilPasien.setError("Belum diisi");
                    et_spo2Pasien.setFocusable(true);
                    return;
                } else if (lokasi_foto == null || lokasi_foto1 == null || lokasi_foto2 == null){
                    Toast.makeText(klinis_pasien.this, "Anda harus melampirkan hasil secara lengkap", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(klinis_pasien.this);
                    builder.setTitle("Perhatian");
                    builder.setMessage("Pastikan ada yang akan disimpan sudah sesuai!");
                    builder.setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = new ProgressDialog(klinis_pasien.this);
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Mohon menunggu...");
                            progressDialog.show();
                            dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("dataklinisPasien").child(nomor_rm);
                            dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(klinis_pasien.this);
                                        builder1.setTitle("Data sudah ada");
                                        builder1.setMessage("Data klinis pada nomor RM tersebut sudah pernah di input, Jika dilanjutkan akan merubah data sebelumnya");
                                        builder1.setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                snapshot.getRef().child("alergi").setValue(alergi_pasien);
                                                snapshot.getRef().child("diagnosa_awal").setValue(diagnosa_awal);
                                                snapshot.getRef().child("nadi").setValue(nadi_pasien);
                                                snapshot.getRef().child("ttd").setValue(ttd_pasien1 + "/" +ttd_pasien2);
                                                snapshot.getRef().child("resusitasi").setValue(resusitasi_pasien);
                                                snapshot.getRef().child("suhu").setValue(suhu_pasien);
                                                snapshot.getRef().child("spo2").setValue(spo2_pasien);
                                                snapshot.getRef().child("pupil").setValue(pupil_pasien);
                                                progressDialog.dismiss();

                                                et_nomorRmKlinis.setText("");
                                                et_alergiPasien.setText("");
                                                et_nadiPasien.setText("");
                                                et_pupilPasien.setText("");
                                                et_spo2Pasien.setText("");
                                                et_diagnosaAwalPasien.setText("");
                                                et_suhuPasien.setText("");
                                                et_ttdPasien1.setText("");
                                                et_ttdPasien2.setText("");
                                                et_resusitasiPasien.setText("");

                                                if (lokasi_foto != null && lokasi_foto1 !=null && lokasi_foto2 !=null) {
                                                    progressDialog.show();
                                                    sPref = FirebaseStorage.getInstance("gs://imr-rsmmn.appspot.com/")
                                                            .getReference("bukti_hasil_klinis").child(et_nomorRmKlinis.getText().toString());
                                                    final StorageReference storageReference = sPref.child(System.currentTimeMillis() + "." +
                                                            ambilExtensiGambar(lokasi_foto));
                                                    storageReference.putFile(lokasi_foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    String uri_photo = uri.toString();
                                                                    dRef.getRef().child("url_images_hasilLab").setValue(uri_photo);
//                                                                    dRef1.getRef().child("url_images_hasilLab").setValue(uri_photo);
                                                                }
                                                            });
                                                        }
                                                    });
                                                    final StorageReference storageReference1 = sPref.child(System.currentTimeMillis() + "." +
                                                            ambilExtensiGambar(lokasi_foto1));
                                                    storageReference1.putFile(lokasi_foto1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    String uri_photo1 = uri.toString();
                                                                    dRef.getRef().child("url_images_hasilRadio").setValue(uri_photo1);
//                                                                    dRef1.getRef().child("url_images_hasilRadio").setValue(uri_photo1);
                                                                }
                                                            });
                                                        }
                                                    });
                                                    final StorageReference storageReference2 = sPref.child(System.currentTimeMillis() + "." +
                                                            ambilExtensiGambar(lokasi_foto2));
                                                    storageReference2.putFile(lokasi_foto2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    String uri_photo2 = uri.toString();
                                                                    dRef.getRef().child("url_images_hasilPenunjang").setValue(uri_photo2);
//                                                                    dRef1.getRef().child("url_images_hasilPenunjang").setValue(uri_photo2);
                                                                    progressDialog.dismiss();

                                                                    AlertDialog.Builder alert = new AlertDialog.Builder(klinis_pasien.this);
                                                                    alert.setTitle("Success");
                                                                    alert.setMessage("Berhasil menyimpan data, Apakah ingin melanjutkan input data Resume?");
                                                                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Intent intent = new Intent(klinis_pasien.this, resume_pasien.class);
                                                                            intent.putExtra("nomor_rm", nomor_rm);
                                                                            startActivity(intent);
                                                                        }
                                                                    });
                                                                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.cancel();
                                                                        }
                                                                    });
                                                                    alert.show();
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                                dRef1 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                                        .getReference("folderPasien").child(nomor_rm);
                                                dRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                        snapshot.getRef().child("alergi").setValue(alergi_pasien);
                                                        snapshot.getRef().child("diagnosa_awal").setValue(diagnosa_awal);
                                                        snapshot.getRef().child("nadi").setValue(nadi_pasien);
                                                        snapshot.getRef().child("ttd").setValue(ttd_pasien1 + "/" +ttd_pasien2);
                                                        snapshot.getRef().child("resusitasi").setValue(resusitasi_pasien);
                                                        snapshot.getRef().child("suhu").setValue(suhu_pasien);
                                                        snapshot.getRef().child("spo2").setValue(spo2_pasien);
                                                        snapshot.getRef().child("pupil").setValue(pupil_pasien);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        });
                                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                progressDialog.dismiss();
                                            }
                                        });
                                        builder1.show();

                                    } else {
                                        snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                        snapshot.getRef().child("alergi").setValue(alergi_pasien);
                                        snapshot.getRef().child("diagnosa_awal").setValue(diagnosa_awal);
                                        snapshot.getRef().child("nadi").setValue(nadi_pasien);
                                        snapshot.getRef().child("ttd").setValue(ttd_pasien1 + "/" +ttd_pasien2);
                                        snapshot.getRef().child("resusitasi").setValue(resusitasi_pasien);
                                        snapshot.getRef().child("suhu").setValue(suhu_pasien);
                                        snapshot.getRef().child("spo2").setValue(spo2_pasien);
                                        snapshot.getRef().child("pupil").setValue(pupil_pasien);
                                        progressDialog.dismiss();

                                        if (lokasi_foto != null && lokasi_foto1 !=null && lokasi_foto2 !=null) {
                                            progressDialog.show();
                                            sPref = FirebaseStorage.getInstance("gs://imr-rsmmn.appspot.com/")
                                                    .getReference("bukti_hasil_klinis").child(et_nomorRmKlinis.getText().toString());
                                            final StorageReference storageReference = sPref.child(System.currentTimeMillis() + "." +
                                                    ambilExtensiGambar(lokasi_foto));
                                            storageReference.putFile(lokasi_foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String uri_photo = uri.toString();
                                                            dRef.getRef().child("url_images_hasilLab").setValue(uri_photo);
//                                                            dRef1.getRef().child("url_images_hasilLab").setValue(uri_photo);
                                                        }
                                                    });
                                                }
                                            });
                                            final StorageReference storageReference1 = sPref.child(System.currentTimeMillis() + "." +
                                                    ambilExtensiGambar(lokasi_foto1));
                                            storageReference1.putFile(lokasi_foto1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String uri_photo1 = uri.toString();
                                                            dRef.getRef().child("url_images_hasilRadio").setValue(uri_photo1);
//                                                            dRef1.getRef().child("url_images_hasilRadio").setValue(uri_photo1);
                                                        }
                                                    });
                                                }
                                            });
                                            final StorageReference storageReference2 = sPref.child(System.currentTimeMillis() + "." +
                                                    ambilExtensiGambar(lokasi_foto2));
                                            storageReference2.putFile(lokasi_foto2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String uri_photo2 = uri.toString();
                                                            dRef.getRef().child("url_images_hasilPenunjang").setValue(uri_photo2);
//                                                            dRef1.getRef().child("url_images_hasilPenunjang").setValue(uri_photo2);
                                                            progressDialog.dismiss();

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(klinis_pasien.this);
                                                            alert.setTitle("Success");
                                                            alert.setMessage("Berhasil menyimpan data, Apakah ingin melanjutkan input data Resume?");
                                                            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(klinis_pasien.this, resume_pasien.class);
                                                                    intent.putExtra("nomor_rm", nomor_rm);
                                                                    startActivity(intent);

                                                                    et_nomorRmKlinis.setText("");
                                                                    et_alergiPasien.setText("");
                                                                    et_nadiPasien.setText("");
                                                                    et_pupilPasien.setText("");
                                                                    et_spo2Pasien.setText("");
                                                                    et_diagnosaAwalPasien.setText("");
                                                                    et_suhuPasien.setText("");
                                                                    et_ttdPasien1.setText("");
                                                                    et_ttdPasien2.setText("");
                                                                    et_resusitasiPasien.setText("");
                                                                }
                                                            });
                                                            alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.cancel();
                                                                    et_nomorRmKlinis.setText("");
                                                                    et_alergiPasien.setText("");
                                                                    et_nadiPasien.setText("");
                                                                    et_pupilPasien.setText("");
                                                                    et_spo2Pasien.setText("");
                                                                    et_diagnosaAwalPasien.setText("");
                                                                    et_suhuPasien.setText("");
                                                                    et_ttdPasien1.setText("");
                                                                    et_ttdPasien2.setText("");
                                                                    et_resusitasiPasien.setText("");

                                                                }
                                                            });
                                                            alert.show();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        dRef1 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                                .getReference("folderPasien").child(nomor_rm);
                                        dRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                snapshot.getRef().child("nomor_rm").setValue(nomor_rm);
                                                snapshot.getRef().child("alergi").setValue(alergi_pasien);
                                                snapshot.getRef().child("diagnosa_awal").setValue(diagnosa_awal);
                                                snapshot.getRef().child("nadi").setValue(nadi_pasien);
                                                snapshot.getRef().child("ttd").setValue(ttd_pasien1 + "/" +ttd_pasien2);
                                                snapshot.getRef().child("resusitasi").setValue(resusitasi_pasien);
                                                snapshot.getRef().child("suhu").setValue(suhu_pasien);
                                                snapshot.getRef().child("spo2").setValue(spo2_pasien);
                                                snapshot.getRef().child("pupil").setValue(pupil_pasien);
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

        btn_cariDataPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(klinis_pasien.this, caridata_pasien.class));
            }
        });

        btn_hasilLaboPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pilihan[] = {"Upload Gambar", "Lihat Gambar"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(klinis_pasien.this);
                builder1.setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which ==0){
                            ImagePicker.Companion.with(klinis_pasien.this)
                                    .crop()
                                    .compress(1024)
                                    .maxResultSize(1080, 1080)
                                    .start(request_pertama);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(klinis_pasien.this);
                            builder.setTitle("Preview Image");
                            LinearLayout linearLayout = new LinearLayout(klinis_pasien.this);
                            linearLayout.setOrientation(LinearLayout.VERTICAL);
                            final View previewImage = getLayoutInflater().inflate(R.layout.preview_image, null);
                            ImageView imageView = previewImage.findViewById(R.id.iv_previewImage);
                            builder.setView(previewImage);
                            if (lokasi_foto == null){
                                builder.setMessage("Anda belum upload hasil laboratorium");
                                imageView.setVisibility(View.GONE);
                            } else {
                                imageView.setVisibility(View.VISIBLE);
                                Glide.with(klinis_pasien.this).load(lokasi_foto)
                                        .fitCenter().into(imageView);
                                builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                            }
                            builder.show();
//                            if (previewImage.getParent() != null)
//                                ((ViewGroup)previewImage.getParent()).removeView(previewImage);
                        }
                    }
                });
                builder1.show();
            }
        });
        btn_hasilRadioPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pilihan[] = {"Upload Gambar", "Lihat Gambar"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(klinis_pasien.this);
                builder1.setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which ==0){
                            ImagePicker.Companion.with(klinis_pasien.this)
                                    .crop()
                                    .compress(1024)
                                    .maxResultSize(1080, 1080)
                                    .start(request_kedua);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(klinis_pasien.this);
                            builder.setTitle("Preview Image");
                            final View previewImage = getLayoutInflater().inflate(R.layout.preview_image, null);
                            ImageView imageView = previewImage.findViewById(R.id.iv_previewImage);
                            builder.setView(previewImage);

                            if (lokasi_foto1 == null) {
                                builder.setMessage("Anda belum mengupload hasil radiologi pasien");
                                imageView.setVisibility(View.GONE);
                            } else {
                                imageView.setVisibility(View.VISIBLE);
                                Glide.with(klinis_pasien.this).load(lokasi_foto1)
                                        .centerCrop().fitCenter().into(imageView);
                                builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                            }
                            builder.show();
//                            if (previewImage.getParent() != null)
//                                ((ViewGroup)previewImage.getParent()).removeView(previewImage);
                        }
                    }
                });
                builder1.show();
            }
        });
        btn_hasilPenunjangPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pilihan[] = {"Upload Gambar", "Lihat Gambar"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(klinis_pasien.this);
                builder1.setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which ==0){
                            ImagePicker.Companion.with(klinis_pasien.this)
                                    .crop()
                                    .compress(1024)
                                    .maxResultSize(1080, 1080)
                                    .start(request_ketiga);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(klinis_pasien.this);
                            builder.setTitle("Preview Image");
                            final View previewImage = getLayoutInflater().inflate(R.layout.preview_image, null);
                            ImageView imageView = previewImage.findViewById(R.id.iv_previewImage);
                            builder.setView(previewImage);

                            if (lokasi_foto2 == null){
                                builder.setMessage("Anda belum mengupload hasil penunjang");
                                imageView.setVisibility(View.GONE);
                            } else {
                                imageView.setVisibility(View.VISIBLE);
                                Glide.with(klinis_pasien.this).load(lokasi_foto2)
                                        .centerCrop().fitCenter().into(imageView);
                                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                            }
                            builder.show();
//                            if (previewImage.getParent() != null)
//                                ((ViewGroup)previewImage.getParent()).removeView();
                        }
                    }
                });
                builder1.show();
            }
        });
        btn_kembaliKlinisPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String ambilExtensiGambar(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_pertama && resultCode == Activity.RESULT_OK){
            assert data != null;
            lokasi_foto = data.getData();
            iv_doneHasilLabo.setVisibility(View.VISIBLE);
        }
        if (requestCode == request_kedua && resultCode == Activity.RESULT_OK){
            assert data != null;
            lokasi_foto1 = data.getData();
            iv_doneHasilRadio.setVisibility(View.VISIBLE);
        }
        if (requestCode == request_ketiga && resultCode == Activity.RESULT_OK){
            assert data != null;
            lokasi_foto2 = data.getData();
            iv_doneHasilPenunjang.setVisibility(View.VISIBLE);
        }
    }
}