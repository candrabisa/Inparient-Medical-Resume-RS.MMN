package com.rsmmn.inparientmedicalresumersmmn.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsmmn.inparientmedicalresumersmmn.R;
import com.rsmmn.inparientmedicalresumersmmn.identitas_pasien;
import com.rsmmn.inparientmedicalresumersmmn.klinis_pasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_FolderPasien;
import com.rsmmn.inparientmedicalresumersmmn.resume_pasien;

import java.util.ArrayList;
import java.util.List;

public class Adapter_FolderPasien extends RecyclerView.Adapter<Adapter_FolderPasien.MyHolder> {

    Context context;
    List<Model_FolderPasien> modelFolderPasiens;
    int dipencet = -1;

    public Adapter_FolderPasien(Context context, List<Model_FolderPasien> modelFolderPasiens) {
        this.context = context;
        this.modelFolderPasiens = modelFolderPasiens;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_folder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {


//        final String diagnosa_utama = modelFolderPasiens.get(position).getDiagnosis_utama_pasien();

        //Identitas Pasien
        final String no_rm = modelFolderPasiens.get(position).getNomor_rm();
        final String nama_pasien = modelFolderPasiens.get(position).getNama_pasien();
        final String tgl_lahir = modelFolderPasiens.get(position).getTgl_lahir_pasien();
        final String jenis_kelamin = modelFolderPasiens.get(position).getJenis_kelamin_pasien();
        final String umur = modelFolderPasiens.get(position).getUmur_pasien();
        final String tgl_masuk = modelFolderPasiens.get(position).getTgl_masuk_pasien();
        final String tgl_keluar = modelFolderPasiens.get(position).getTgl_keluar_pasien();
        final String ruang_perawatan = modelFolderPasiens.get(position).getRuang_perawatan_pasien();
        final String jaminan = modelFolderPasiens.get(position).getJaminan_pasien();

        //Data Klinis
        final String alergi = modelFolderPasiens.get(position).getAlergi();
        final String diagnosa_awal = modelFolderPasiens.get(position).getDiagnosa_awal();
        final String ttd1 = modelFolderPasiens.get(position).getTtd1();
        final String ttd2 = modelFolderPasiens.get(position).getTtd2();
        final String nadi = modelFolderPasiens.get(position).getNadi();
        final String resusitasi = modelFolderPasiens.get(position).getResusitasi();
        final String suhu = modelFolderPasiens.get(position).getSuhu();
        final String spo2 = modelFolderPasiens.get(position).getSpo2();
        final String pupil = modelFolderPasiens.get(position).getPupil();

        //Resume Dokter
        final String ringkasan_klinis = modelFolderPasiens.get(position).getRingkasan_pasien();
        final String terapi_pengobatan = modelFolderPasiens.get(position).getPengobatan_pasien();
        final String konsultasi = modelFolderPasiens.get(position).getKonsultasi_pasien();
        final String diagnosis_utama = modelFolderPasiens.get(position).getDiagnosis_utama_pasien();
        final String diagnosis_sekunder = modelFolderPasiens.get(position).getDiagnosis_sekunder_pasien();
        final String tindakan = modelFolderPasiens.get(position).getTindakan_pasien();
        final String obat_pulang = modelFolderPasiens.get(position).getObat_pulang();
        final String kondisi_pulang = modelFolderPasiens.get(position).getKondisi_pulang_pasien();
        final String pengobatan_selanjutnya = modelFolderPasiens.get(position).getPengobatan_selanjutnya_pasien();
        final String dokter_penanggungjawab = modelFolderPasiens.get(position).getDokter_penanggung_jawab();


        holder.tv_noRmFolder.setText(no_rm);
        holder.tv_namaPasienFolder.setText(nama_pasien);
        holder.tv_noDxFolder.setText(diagnosis_utama);
        holder.tv_noTglMasukFolder.setText(tgl_masuk);
        holder.tv_noTglKeluarFolder.setText(tgl_keluar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dipencet = position;

                String [] pilihan = {"Hapus", "Edit Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Silahkan Pilih");
                builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(builder.getContext());
                            builder1.setTitle("Hapus Data");
                            builder1.setMessage("Apakah kamu yakin ingin menghapus data?");
                            builder1.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("folderPasien").child(no_rm);
                                    dRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(v.getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    DatabaseReference dRef1 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("identitasPasien").child(no_rm);
                                    dRef1.removeValue();

                                    DatabaseReference dRef2 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("dataklinisPasien").child(no_rm);
                                    dRef2.removeValue();

                                    DatabaseReference dRef3 = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("resumePasien").child(no_rm);
                                    dRef3.removeValue();
                                }
                            });
                            builder1.setNegativeButton("batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                        } else {
                            String[] pilihan_maning = {"Identitas Pasien", "Data Klinis", "Resume Dokter"};
                            AlertDialog.Builder builder_lagi = new AlertDialog.Builder(builder.getContext());
                            builder_lagi.setTitle("Silahkan Pilih Menu");
                            builder_lagi.setItems(pilihan_maning, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0){
                                        Intent intentIden = new Intent(context, identitas_pasien.class);
                                        intentIden.putExtra("no_rm", no_rm);
                                        intentIden.putExtra("nama_pasien", nama_pasien);
                                        intentIden.putExtra("tgl_lahir", tgl_lahir);
                                        intentIden.putExtra("jenis_kelamin", jenis_kelamin);
                                        intentIden.putExtra("umur", umur);
                                        intentIden.putExtra("tgl_masuk", tgl_masuk);
                                        intentIden.putExtra("tgl_keluar", tgl_keluar);
                                        intentIden.putExtra("ruang_perawatan", ruang_perawatan);
                                        intentIden.putExtra("jaminan", jaminan);
                                        context.startActivity(intentIden);
                                    } else if (which == 1){
                                        Intent intentKlinis = new Intent(context, klinis_pasien.class);
                                        intentKlinis.putExtra("no_rm", no_rm);
                                        intentKlinis.putExtra("alergi", alergi);
                                        intentKlinis.putExtra("diagnosis_awal", diagnosa_awal);
                                        intentKlinis.putExtra("ttd1", ttd1);
                                        intentKlinis.putExtra("ttd2", ttd2);
                                        intentKlinis.putExtra("nadi", nadi);
                                        intentKlinis.putExtra("resusitasi", resusitasi);
                                        intentKlinis.putExtra("suhu", suhu);
                                        intentKlinis.putExtra("spo2", spo2);
                                        intentKlinis.putExtra("pupil", pupil);
                                        context.startActivity(intentKlinis);
                                    } else {
                                        Intent intentResume = new Intent(context, resume_pasien.class);
                                        intentResume.putExtra("no_rm", no_rm);
                                        intentResume.putExtra("ringkasan", ringkasan_klinis);
                                        intentResume.putExtra("terapi", terapi_pengobatan);
                                        intentResume.putExtra("konsultasi", konsultasi);
                                        intentResume.putExtra("diagnosis_utama", diagnosis_utama);
                                        intentResume.putExtra("diagnosis_sekunder", diagnosis_sekunder);
                                        intentResume.putExtra("tindakan", tindakan);
                                        intentResume.putExtra("obat_pulang", obat_pulang);
                                        intentResume.putExtra("kondisi_pulang", kondisi_pulang);
                                        intentResume.putExtra("pengobatan_selanjutnya", pengobatan_selanjutnya);
                                        intentResume.putExtra("dokter_penanggung_jawab", dokter_penanggungjawab);
                                        context.startActivity(intentResume);
                                    }
                                }
                            });
                            builder_lagi.show();
                        }
                    }
                });
                builder.show();
            }
        });

        if (dipencet == position){
            holder.tv_noRmFolder.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_namaPasienFolder.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_noDxFolder.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_noTglMasukFolder.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_noTglKeluarFolder.setBackgroundResource(R.drawable.bg_table_content_dipencet);

        } else {
            holder.tv_noRmFolder.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_namaPasienFolder.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_noDxFolder.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_noTglMasukFolder.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_noTglKeluarFolder.setBackgroundResource(R.drawable.bg_table_content_cell);
        }

    }

    @Override
    public int getItemCount() {
        return modelFolderPasiens.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_noRmFolder, tv_namaPasienFolder,tv_noDxFolder,
                tv_noTglMasukFolder, tv_noTglKeluarFolder;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_noRmFolder = itemView.findViewById(R.id.tv_noRmFolder);
            tv_namaPasienFolder = itemView.findViewById(R.id.tv_namaPasienFolder);
            tv_noDxFolder = itemView.findViewById(R.id.tv_noDxFolder);
            tv_noTglMasukFolder = itemView.findViewById(R.id.tv_noTglMasukFolder);
            tv_noTglKeluarFolder = itemView.findViewById(R.id.tv_noTglKeluarFolder);

        }
    }
}
