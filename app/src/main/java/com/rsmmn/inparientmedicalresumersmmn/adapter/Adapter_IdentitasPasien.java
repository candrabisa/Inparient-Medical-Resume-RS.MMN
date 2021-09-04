package com.rsmmn.inparientmedicalresumersmmn.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.rsmmn.inparientmedicalresumersmmn.R;
import com.rsmmn.inparientmedicalresumersmmn.caridata_pasien;
import com.rsmmn.inparientmedicalresumersmmn.klinis_pasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_IdentitasPasien;

import java.util.ArrayList;
import java.util.List;

public class Adapter_IdentitasPasien extends RecyclerView.Adapter<Adapter_IdentitasPasien.MyHolder> {

    Context context;
    List<Model_IdentitasPasien>modelIdentitasPasiens = new ArrayList<>();
    int dipencet = -1;

    public Adapter_IdentitasPasien(Context context, List<Model_IdentitasPasien> modelIdentitasPasiens) {
        this.context = context;
        this.modelIdentitasPasiens = modelIdentitasPasiens;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_caridata, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_IdentitasPasien.MyHolder holder, @SuppressLint("RecyclerView") int position) {

        final String nama_pasien = modelIdentitasPasiens.get(position).getNama_pasien();
        final String no_rm = modelIdentitasPasiens.get(position).getNomor_rm();
        final String tgl_lahir = modelIdentitasPasiens.get(position).getTgl_lahir_pasien();
        final String tgl_masuk = modelIdentitasPasiens.get(position).getTgl_masuk_pasien();
        final String tgl_keluar = modelIdentitasPasiens.get(position).getTgl_keluar_pasien();
        final String ruang_perawatan = modelIdentitasPasiens.get(position).getRuang_perawatan_pasien();

        holder.tv_namaPasienCari.setText(nama_pasien);
        holder.tv_noRmCari.setText(no_rm);
        holder.tv_tglLahirCari.setText(tgl_lahir);
        holder.tv_TglKeluarCari.setText(tgl_keluar);
        holder.tv_TglMasukCari.setText(tgl_masuk);
        holder.tv_ruanganCari.setText(ruang_perawatan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dipencet = position;
                notifyDataSetChanged();

                Log.d("cek1", no_rm);
                Intent intent = new Intent(context, klinis_pasien.class);
                intent.putExtra("no_rm", no_rm);
                context.startActivity(intent);
            }

        });

        if (dipencet == position){
            holder.tv_noRmCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_namaPasienCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_tglLahirCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_TglMasukCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_TglKeluarCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);
            holder.tv_ruanganCari.setBackgroundResource(R.drawable.bg_table_content_dipencet);

        } else {
            holder.tv_noRmCari.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_namaPasienCari.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_tglLahirCari.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_TglMasukCari.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_TglKeluarCari.setBackgroundResource(R.drawable.bg_table_content_cell);
            holder.tv_ruanganCari.setBackgroundResource(R.drawable.bg_table_content_cell);
        }

    }

    @Override
    public int getItemCount() {
        return modelIdentitasPasiens.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_namaPasienCari, tv_noRmCari, tv_tglLahirCari,
                tv_TglMasukCari,tv_TglKeluarCari,tv_ruanganCari;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_namaPasienCari = itemView.findViewById(R.id.tv_namaPasienCari);
            tv_noRmCari = itemView.findViewById(R.id.tv_noRmCari);
            tv_tglLahirCari = itemView.findViewById(R.id.tv_tglLahirCari);
            tv_TglMasukCari = itemView.findViewById(R.id.tv_TglMasukCari);
            tv_TglKeluarCari = itemView.findViewById(R.id.tv_TglKeluarCari);
            tv_ruanganCari = itemView.findViewById(R.id.tv_ruanganCari);

        }
    }
}
