package com.rsmmn.inparientmedicalresumersmmn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsmmn.inparientmedicalresumersmmn.R;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_FolderPasien;

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
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String no_rm = modelFolderPasiens.get(position).getNomor_rm();
        final String nama_pasien = modelFolderPasiens.get(position).getNama_pasien();
        final String diagnosa_utama = modelFolderPasiens.get(position).getDiagnosis_utama_pasien();
        final String tgl_masuk = modelFolderPasiens.get(position).getTgl_masuk_pasien();
        final String tgl_keluar = modelFolderPasiens.get(position).getTgl_keluar_pasien();

        holder.tv_noRmFolder.setText(no_rm);
        holder.tv_namaPasienFolder.setText(nama_pasien);
        holder.tv_noDxFolder.setText(diagnosa_utama);
        holder.tv_noTglMasukFolder.setText(tgl_masuk);
        holder.tv_noTglKeluarFolder.setText(tgl_keluar);

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
