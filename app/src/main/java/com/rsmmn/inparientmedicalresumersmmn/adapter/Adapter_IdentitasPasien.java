package com.rsmmn.inparientmedicalresumersmmn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsmmn.inparientmedicalresumersmmn.R;
import com.rsmmn.inparientmedicalresumersmmn.model.Model_IdentitasPasien;

import java.util.List;

public class Adapter_IdentitasPasien extends RecyclerView.Adapter<Adapter_IdentitasPasien.MyHolder> {

    Context context;
    List<Model_IdentitasPasien>modelIdentitasPasiens;

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
    public void onBindViewHolder(@NonNull Adapter_IdentitasPasien.MyHolder holder, int position) {
//        MyHolder myHolder = (MyHolder) holder;
        int posisiRow = holder.getAdapterPosition();
        Integer nomor_urut = 0;

        if (position == 0){
//            holder.tv_noUrutCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_noRmCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_namaPasienCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_tglLahirCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_TglMasukCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_TglKeluarCari.setBackgroundResource(R.drawable.bg_table_header_cell);
            holder.tv_ruanganCari.setBackgroundResource(R.drawable.bg_table_header_cell);

//            holder.tv_noUrutCari.setText("NO");
            holder.tv_namaPasienCari.setText("Nama Pasien");
            holder.tv_tglLahirCari.setText("Tgl Lahir");
            holder.tv_TglMasukCari.setText("Tgl Masuk");
            holder.tv_TglKeluarCari.setText("Tgl Keluar");
            holder.tv_ruanganCari.setText("Ruangan");
        } else {
//            for (int i = 0;i<=nomor_urut; i++){
//                int nomerUrut = i+1;
//
//            }
//                final int nomer_urut = modelIdentitasPasiens.get(position -1).getNomor_urut();
            final String nama_pasien = modelIdentitasPasiens.get(position -1 ).getNama_pasien();
            final String no_rm = modelIdentitasPasiens.get(position -1 ).getNomor_rm();
            final String tgl_lahir = modelIdentitasPasiens.get(position -1 ).getTgl_lahir_pasien();
            final String tgl_masuk = modelIdentitasPasiens.get(position -1).getTgl_masuk_pasien();
            final String tgl_keluar = modelIdentitasPasiens.get(position -1).getTgl_keluar_pasien();
            final String ruang_perawatan = modelIdentitasPasiens.get(position -1).getRuang_perawatan_pasien();

//            holder.tv_noUrutCari.setText(String.valueOf(nomerUrut));
            holder.tv_namaPasienCari.setText(nama_pasien);
            holder.tv_noRmCari.setText(no_rm);
            holder.tv_tglLahirCari.setText(tgl_lahir);
            holder.tv_TglKeluarCari.setText(tgl_keluar);
            holder.tv_TglMasukCari.setText(tgl_masuk);
            holder.tv_ruanganCari.setText(ruang_perawatan);

        }


    }

    @Override
    public int getItemCount() {
        return modelIdentitasPasiens.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_noUrutCari, tv_namaPasienCari, tv_noRmCari, tv_tglLahirCari,
                tv_TglMasukCari,tv_TglKeluarCari,tv_ruanganCari;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

//            tv_noUrutCari = itemView.findViewById(R.id.tv_noUrutCari);
            tv_namaPasienCari = itemView.findViewById(R.id.tv_namaPasienCari);
            tv_noRmCari = itemView.findViewById(R.id.tv_noRmCari);
            tv_tglLahirCari = itemView.findViewById(R.id.tv_tglLahirCari);
            tv_TglMasukCari = itemView.findViewById(R.id.tv_TglMasukCari);
            tv_TglKeluarCari = itemView.findViewById(R.id.tv_TglKeluarCari);
            tv_ruanganCari = itemView.findViewById(R.id.tv_ruanganCari);
        }
    }
}
