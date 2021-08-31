package com.rsmmn.inparientmedicalresumersmmn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsmmn.inparientmedicalresumersmmn.R;
import com.rsmmn.inparientmedicalresumersmmn.klinis_pasien;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_KlinisPasien;
import com.rsmmn.inparientmedicalresumersmmn.resume_pasien;

import java.util.ArrayList;
import java.util.List;

public class Adapter_KlinisPasien extends RecyclerView.Adapter<Adapter_KlinisPasien.MyHolder> {

    Context context;
    List<Model_KlinisPasien> modelKlinisPasiens = new ArrayList<>();
    int dipencet = -1;

    public Adapter_KlinisPasien(Context context, List<Model_KlinisPasien> modelKlinisPasiens) {
        this.context = context;
        this.modelKlinisPasiens = modelKlinisPasiens;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_caridata, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_KlinisPasien.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        final String nama_pasien = modelKlinisPasiens.get(position).getNama_pasien();
        final String no_rm = modelKlinisPasiens.get(position).getNomor_rm();
        final String tgl_lahir = modelKlinisPasiens.get(position).getTgl_lahir_pasien();
        final String tgl_masuk = modelKlinisPasiens.get(position).getTgl_masuk_pasien();
        final String tgl_keluar = modelKlinisPasiens.get(position).getTgl_keluar_pasien();
        final String ruang_perawatan = modelKlinisPasiens.get(position).getRuang_perawatan_pasien();

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
                Intent intent = new Intent(context, resume_pasien.class);
                intent.putExtra("nomor_rm", no_rm);
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
        return modelKlinisPasiens.size();
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
