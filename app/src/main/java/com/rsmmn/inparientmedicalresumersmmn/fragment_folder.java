package com.rsmmn.inparientmedicalresumersmmn;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsmmn.inparientmedicalresumersmmn.adapter.Adapter_FolderPasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_FolderPasien;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    List<Model_FolderPasien> modelFolderPasienList = new ArrayList<>();
    Adapter_FolderPasien adapterFolderPasien;

    DatabaseReference dRef, dRef1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataPasien();
        btn_folderExportData = view.findViewById(R.id.btn_folderExportData);
        btn_kembaliFolderPasien = view.findViewById(R.id.btn_kembaliFolderPasien);
        rv_pasienfolder = view.findViewById(R.id.rv_pasienfolder);
        et_cariPasienNama = view.findViewById(R.id.et_cariPasienFolderNama);
        et_cariTglFolder = view.findViewById(R.id.et_cariTglFolder);

        rv_pasienfolder.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_folderExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fnamexls="Laporan-"+System.currentTimeMillis()+ ".xls";
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File (sdCard.getAbsolutePath() + "/Inparient Medical Resume RS. MMN");
                directory.mkdirs();
                File file = new File(directory, Fnamexls);

                WorkbookSettings wbSettings = new WorkbookSettings();

                wbSettings.setLocale(new Locale("en", "EN"));

                WritableWorkbook workbook;
                try {
                    int a = 1;
                    workbook = Workbook.createWorkbook(file, wbSettings);
                    //workbook.createSheet("Report", 0);
                    WritableSheet sheet = workbook.createSheet("First Sheet", 0);
                    Label heading0 = new Label(0, 0, "No RM");
                    Label heading1 = new Label(1,0, "Nama Pasien");
                    Label heading2 = new Label(2,0, "Dx");
                    Label heading3 = new Label(3,0,"Tgl Masuk");
                    Label heading4 = new Label(4,0,"Tgl Keluar");

                    try {
                        sheet.addCell(new Label(0, 0, "No RM"));
                        sheet.addCell(new Label(1,0, "Nama Pasien"));
                        sheet.addCell(heading2);
                        sheet.addCell(heading3);
                        sheet.addCell(heading4);

                        for (int i = 0; i<modelFolderPasienList.size(); i++){
                            sheet.addCell(new Label(0 ,i+1, modelFolderPasienList.get(i).getNomor_rm()));
                            sheet.addCell(new Label(1, i+1, modelFolderPasienList.get(i).getNama_pasien()));
                            sheet.addCell(new Label(2, i+1, modelFolderPasienList.get(i).getDiagnosis_utama_pasien()));
                            sheet.addCell(new Label(3, i+1, modelFolderPasienList.get(i).getTgl_masuk_pasien()));
                            sheet.addCell(new Label(4, i+1, modelFolderPasienList.get(i).getTgl_keluar_pasien()));
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false);
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

                    adapterFolderPasien = new Adapter_FolderPasien(getContext(), modelFolderPasienList);
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