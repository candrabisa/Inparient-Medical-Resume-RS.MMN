package com.rsmmn.inparientmedicalresumersmmn;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_FolderPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_FolderPasien;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class fragment_folder extends Fragment {

    EditText et_cariPasienNama, et_cariTglFolder;
    RecyclerView rv_pasienfolder;
    ImageView btn_kembaliFolderPasien;
    AppCompatButton btn_folderExportData;

    private boolean keluar = false;
    private static final int ijin_penyimpanan = 100;
    List<Model_FolderPasien> modelFolderPasienList = new ArrayList<>();
    Adapter_FolderPasien adapterFolderPasien;


    DatabaseReference dRef, dRef1;
    DatePickerDialog.OnDateSetListener date;
    Calendar calendar;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataPasien();
        btn_folderExportData = view.findViewById(R.id.btn_folderExportData);
        btn_kembaliFolderPasien = view.findViewById(R.id.btn_kembaliFolderPasien);
        rv_pasienfolder = view.findViewById(R.id.rv_pasienfolder);
        et_cariPasienNama = view.findViewById(R.id.et_cariPasienFolderNama);

        rv_pasienfolder.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_folderExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportReport();
            }

        });

        btn_kembaliFolderPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keluar){
                    getActivity().finish();
                    System.exit(0);
                } else {
                    Toast.makeText(getContext(), "Tekan back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            keluar = true;
                        }
                    }, 1000);
                }
            }
        });
        et_cariPasienNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    cariDataPasien(s.toString().trim());
                } else {
                    loadDataPasien();
                }
            }
        });

        et_cariPasienNama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String caripasien = et_cariPasienNama.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    cariDataPasien(caripasien);
                    return true;
                } else {
                    loadDataPasien();
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

   private void exportReport(){
       String Fnamexls="Laporan-"+System.currentTimeMillis()+ ".xls";
       File sdCard = Environment.getExternalStorageDirectory();
       File directory = new File (sdCard.getAbsolutePath() + "/Inparient Medical Resume RS. MMN");
       directory.mkdirs();
       File file = new File(directory, Fnamexls);

       WorkbookSettings wbSettings = new WorkbookSettings();

       wbSettings.setLocale(new Locale("en", "EN"));

       WritableWorkbook workbook;
       try {
           workbook = Workbook.createWorkbook(file, wbSettings);
           //workbook.createSheet("Report", 0);
           WritableSheet sheet = workbook.createSheet("First Sheet", 0);
           Label heading0 = new Label(0, 0, "No RM");
           Label heading1 = new Label(1,0, "Nama Pasien");
           Label heading2 = new Label(2,0, "Tgl lahir");
           Label heading3 = new Label(3,0,"Jenis Kelamin");
           Label heading4 = new Label(4,0,"Umur");
           Label heading5 = new Label(5,0,"Tgl Masuk");
           Label heading6 = new Label(6,0,"Tgl Keluar");
           Label heading7 = new Label(7,0,"Ruang Perawatan");
           Label heading8 = new Label(8,0,"Jaminan");
           Label heading9 = new Label(9,0,"Alergi");
           Label heading10 = new Label(10,0,"Diagnosa Awal Masuk");
           Label heading11 = new Label(11,0,"TTD");
           Label heading12 = new Label(12,0,"Nadi");
           Label heading13 = new Label(13,0,"Resusitasi");
           Label heading14 = new Label(14,0,"Suhu");
           Label heading15 = new Label(15,0,"SPO2");
           Label heading16 = new Label(16,0,"Pupil");
           Label heading17 = new Label(17,0,"Ringkasan Klinis Pasien");
           Label heading18 = new Label(18,0,"Terapi/Pengobatan");
           Label heading19 = new Label(19,0,"Konsultasi");
           Label heading20 = new Label(20,0,"Diagnosis Utama");
           Label heading21 = new Label(21,0,"Diagnosis Skunder");
           Label heading22 = new Label(22,0,"Tindakan Pasien");
           Label heading23 = new Label(23,0,"Obat Pulang");
           Label heading24 = new Label(24,0,"Kondisi Saat Pulang");
           Label heading25 = new Label(25,0,"Pengobatan Selanjutnya");
           Label heading26 = new Label(26,0,"Dokter Penanggung Jawab");
           Label heading27 = new Label(27,0,"Admin RM");

           try {
               sheet.addCell(heading0);
               sheet.addCell(heading1);
               sheet.addCell(heading2);
               sheet.addCell(heading3);
               sheet.addCell(heading4);
               sheet.addCell(heading5);
               sheet.addCell(heading6);
               sheet.addCell(heading7);
               sheet.addCell(heading8);
               sheet.addCell(heading9);
               sheet.addCell(heading10);
               sheet.addCell(heading11);
               sheet.addCell(heading12);
               sheet.addCell(heading13);
               sheet.addCell(heading14);
               sheet.addCell(heading15);
               sheet.addCell(heading16);
               sheet.addCell(heading17);
               sheet.addCell(heading18);
               sheet.addCell(heading19);
               sheet.addCell(heading20);
               sheet.addCell(heading21);
               sheet.addCell(heading22);
               sheet.addCell(heading23);
               sheet.addCell(heading24);
               sheet.addCell(heading25);
               sheet.addCell(heading26);
               sheet.addCell(heading27);

               for (int i = 0; i<modelFolderPasienList.size(); i++){
                   sheet.addCell(new Label(0 ,i+1, modelFolderPasienList.get(i).getNomor_rm()));
                   sheet.addCell(new Label(1, i+1, modelFolderPasienList.get(i).getNama_pasien()));
                   sheet.addCell(new Label(2, i+1, modelFolderPasienList.get(i).getTgl_lahir_pasien()));
                   sheet.addCell(new Label(3, i+1, modelFolderPasienList.get(i).getJenis_kelamin_pasien()));
                   sheet.addCell(new Label(4, i+1, modelFolderPasienList.get(i).getUmur_pasien()));
                   sheet.addCell(new Label(5, i+1, modelFolderPasienList.get(i).getTgl_masuk_pasien()));
                   sheet.addCell(new Label(6, i+1, modelFolderPasienList.get(i).getTgl_keluar_pasien()));
                   sheet.addCell(new Label(7, i+1, modelFolderPasienList.get(i).getRuang_perawatan_pasien()));
                   sheet.addCell(new Label(8, i+1, modelFolderPasienList.get(i).getJaminan_pasien()));
                   sheet.addCell(new Label(9, i+1, modelFolderPasienList.get(i).getAlergi()));
                   sheet.addCell(new Label(10, i+1, modelFolderPasienList.get(i).getDiagnosa_awal()));
                   sheet.addCell(new Label(11, i+1, modelFolderPasienList.get(i).getTtd1() +"/" + modelFolderPasienList.get(i).getTtd2()));
                   sheet.addCell(new Label(12, i+1, modelFolderPasienList.get(i).getNadi()));
                   sheet.addCell(new Label(13, i+1, modelFolderPasienList.get(i).getResusitasi()));
                   sheet.addCell(new Label(14, i+1, modelFolderPasienList.get(i).getSuhu()));
                   sheet.addCell(new Label(15, i+1, modelFolderPasienList.get(i).getSpo2()));
                   sheet.addCell(new Label(16, i+1, modelFolderPasienList.get(i).getPupil()));
                   sheet.addCell(new Label(17, i+1, modelFolderPasienList.get(i).getRingkasan_pasien()));
                   sheet.addCell(new Label(18, i+1, modelFolderPasienList.get(i).getPengobatan_pasien()));
                   sheet.addCell(new Label(19, i+1, modelFolderPasienList.get(i).getKonsultasi_pasien()));
                   sheet.addCell(new Label(20, i+1, modelFolderPasienList.get(i).getDiagnosis_utama_pasien()));
                   sheet.addCell(new Label(21, i+1, modelFolderPasienList.get(i).getDiagnosis_sekunder_pasien()));
                   sheet.addCell(new Label(22, i+1, modelFolderPasienList.get(i).getTindakan_pasien()));
                   sheet.addCell(new Label(23, i+1, modelFolderPasienList.get(i).getObat_pulang()));
                   sheet.addCell(new Label(24, i+1, modelFolderPasienList.get(i).getKondisi_pulang_pasien()));
                   sheet.addCell(new Label(25, i+1, modelFolderPasienList.get(i).getPengobatan_selanjutnya_pasien()));
                   sheet.addCell(new Label(26, i+1, modelFolderPasienList.get(i).getDokter_penanggung_jawab()));
                   sheet.addCell(new Label(27, i+1, modelFolderPasienList.get(i).getNama_admin_rm()));
               }

           } catch (RowsExceededException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } catch (WriteException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           workbook.write();
           try {
               workbook.close();
           } catch (WriteException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           //createExcel(excelSheet);
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       Intent bukaEmail = new Intent(Intent.ACTION_SEND);
       bukaEmail.setType("text/plain");
//                bukaEmail.putExtra(Intent.EXTRA_EMAIL, new String []{"naufalAl@gmail.com"});
       bukaEmail.putExtra(Intent.EXTRA_SUBJECT, "Laporan Pasien ");
       bukaEmail.putExtra(Intent.EXTRA_TEXT, "body text");

       Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext()
               .getPackageName() + ".provider", file);
       bukaEmail.putExtra(Intent.EXTRA_STREAM, uri);
       bukaEmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
       startActivity(Intent.createChooser(bukaEmail, "Pilih Aplikasi Untuk Mengirim Email"));
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case ijin_penyimpanan:{
                if (grantResults.length>0){
                    boolean nulis_penyimpanan_oke = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (nulis_penyimpanan_oke){
                        exportReport();
                    } else {
                        Toast.makeText(getContext(), "Harap izinkan aplikasi membaca penyimpanan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void cariDataPasien(String s){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("folderPasien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelFolderPasienList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Model_FolderPasien model = ds.getValue(Model_FolderPasien.class);

                    Log.d("cek1", model.getNomor_rm());
                    Log.d("cek2", model.getNama_pasien());
                    if (model.getNomor_rm().toLowerCase().contains(s.toLowerCase()) ||
                            model.getNama_pasien().toLowerCase().contains(s.toLowerCase())) {
                        modelFolderPasienList.add(model);
                    }
                }
                adapterFolderPasien = new Adapter_FolderPasien(getActivity(), modelFolderPasienList);
                adapterFolderPasien.notifyDataSetChanged();
                rv_pasienfolder.setAdapter(adapterFolderPasien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadDataPasien(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon menunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("folderPasien");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelFolderPasienList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Model_FolderPasien model = ds.getValue(Model_FolderPasien.class);
                    modelFolderPasienList.add(model);

                    adapterFolderPasien = new Adapter_FolderPasien(getActivity(), modelFolderPasienList);
                    adapterFolderPasien.notifyDataSetChanged();
                    rv_pasienfolder.setAdapter(adapterFolderPasien);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}