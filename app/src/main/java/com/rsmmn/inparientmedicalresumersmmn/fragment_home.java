package com.rsmmn.inparientmedicalresumersmmn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */

public class fragment_home extends Fragment {

    LinearLayout btn_idenPasien, btn_dataKlinis, btn_resumeDokter;
    ImageView btn_back;

    private boolean keluar = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_idenPasien = view.findViewById(R.id.ll_idenPasien);
        btn_back = view.findViewById(R.id.btn_kembaliMenuUtama);
        btn_dataKlinis = view.findViewById(R.id.ll_dataKlinis);
        btn_resumeDokter = view.findViewById(R.id.ll_resumeDokter);

        btn_resumeDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), resume_pasien.class);
                intent.putExtra("nomor_rm", "");
                startActivity(intent);
            }
        });

        btn_dataKlinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), klinis_pasien.class);
                intent.putExtra("nomor_rm", "");
                startActivity(intent);
            }
        });

        btn_idenPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), identitas_pasien.class));
                getActivity();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}