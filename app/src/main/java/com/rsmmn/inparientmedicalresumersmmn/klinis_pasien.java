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
import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

public class klinis_pasien extends AppCompatActivity {

    LinearLayout btn_cariDataPasien;
    EditText et_nomorRmKlinis, et_alergiPasien, et_diagnosaAwalPasien
            ,et_ttdPasien, et_nadiPasien, et_resusitasiPasien, et_suhuPasien
            ,et_spo2Pasien, et_pupilPasien;
    ImageButton btn_hasilLaboPasien, btn_hasilRadioPasien, btn_hasilPenunjangPasien;
    Button btn_simpanKlinis;
    ImageView iv_doneHasilPenunjang, iv_doneHasilRadio, iv_doneHasilLabo;


    DatabaseReference dRef;
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

        iv_doneHasilLabo = findViewById(R.id.iv_doneHasilLabo);
        iv_doneHasilRadio = findViewById(R.id.iv_doneHasilRadio);
        iv_doneHasilPenunjang = findViewById(R.id.iv_doneHasilPenunjang);

        et_nomorRmKlinis = findViewById(R.id.et_nomorRmKlinis);
        et_nomorRmKlinis.setEnabled(false);

        iv_doneHasilLabo.setVisibility(View.GONE);
        iv_doneHasilPenunjang.setVisibility(View.GONE);
        iv_doneHasilRadio.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        final String nomor_rm = bundle.getString("nomor_rm");
        et_nomorRmKlinis.setText(nomor_rm);

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
                                Picasso.get().load(lokasi_foto2)
                                        .centerCrop().fit().into(imageView);
                                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
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
    }

    String ambilExtensiGambar (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK){
//            assert data != null;
//            lokasi_foto = data.getData();

//            ProgressDialog progressDialog =new ProgressDialog(klinis_pasien.this);
//            progressDialog.setMessage("Upload hasil lab...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            sPref = FirebaseStorage.getInstance()
//                    .getReference("hasil_lab").child(et_nomorRmKlinis.getText().toString());
//            if (lokasi_foto != null){
//                final StorageReference storageReference = sPref.child(System.currentTimeMillis() + "."+
//                        ambilExtensiGambar(lokasi_foto));
//                storageReference.putFile(lokasi_foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String uri_photo =uri.toString();
//                                dRef.getRef().child("url_images_hasilLab").setValue(uri_photo);
//                            }
//                        });
//                    }
//                });
//            }
//        }
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
//        switch (requestCode){
//            case request_pertama:
//                if (resultCode == Activity.RESULT_OK){
//                    assert data != null;
//                    lokasi_foto = data.getData();
//                    iv_doneHasilLabo.setVisibility(View.VISIBLE);
//                }
//                break;
//            case request_kedua:
//                if (resultCode == Activity.RESULT_OK){
//                    assert data != null;
//                    lokasi_foto1 = data.getData();
//                    iv_doneHasilRadio.setVisibility(View.VISIBLE);
//                }
//                break;
//            case request_ketiga:
//                if (resultCode == Activity.RESULT_OK){
//                    assert data != null;
//                    lokasi_foto2 = data.getData();
//                    iv_doneHasilPenunjang.setVisibility(View.VISIBLE);
//                }
//                break;
//            }
    }
}