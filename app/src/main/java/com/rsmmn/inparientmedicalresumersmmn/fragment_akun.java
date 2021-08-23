package com.rsmmn.inparientmedicalresumersmmn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class fragment_akun extends Fragment {

    Button btn_batalEditAkun, btn_SimpanEditAkun, btn_LogoutAkun, btn_editAkun;
    EditText et_passwordAkun, et_emailAkun, et_idCardAkun, et_namaLengkapAkun;
    Spinner sp_jabatanAkun;

    DatabaseReference dRef, dRef1;

    String userkey_ = "userkey";
    String userkey = "";
    String userkekey = "";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUserLocal();

        btn_batalEditAkun = view.findViewById(R.id.btn_batalEditAkun);
        btn_SimpanEditAkun = view.findViewById(R.id.btn_SimpanEditAkun);
        btn_LogoutAkun = view.findViewById(R.id.btn_LogoutAkun);
        btn_editAkun = view.findViewById(R.id.btn_editAkun);

        et_passwordAkun = view.findViewById(R.id.et_passwordAkun);
        et_emailAkun = view.findViewById(R.id.et_emailAkun);
        et_idCardAkun = view.findViewById(R.id.et_idCardAkun);
        et_namaLengkapAkun = view.findViewById(R.id.et_namaLengkapAkun);
        sp_jabatanAkun = view.findViewById(R.id.sp_jabatanAkun);

        et_namaLengkapAkun.setEnabled(false);
        et_idCardAkun.setEnabled(false);
        et_emailAkun.setEnabled(false);
        et_passwordAkun.setEnabled(false);
        sp_jabatanAkun.setEnabled(false);
        btn_batalEditAkun.setVisibility(View.GONE);
        btn_SimpanEditAkun.setVisibility(View.GONE);

        try {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Mohon menunggu...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Query query = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users").child(userkekey);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    et_namaLengkapAkun.setText(snapshot.child("nama_lengkap").getValue().toString());
                    et_idCardAkun.setText(snapshot.child("idcard").getValue().toString());
                    et_emailAkun.setText(snapshot.child("email").getValue().toString());
                    et_passwordAkun.setText(snapshot.child("password").getValue().toString());
                    sp_jabatanAkun.equals(snapshot.child("jabatan").getValue().toString());
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e){
            Toast.makeText(getContext(), "Error karena " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btn_LogoutAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sPref = getContext().getSharedPreferences(userkey_, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString(userkey, null);
                editor.apply();
                startActivity(new Intent(getContext(), dashboard.class));
                getActivity().finish();
            }
        });

        btn_editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_namaLengkapAkun.setEnabled(true);
                et_emailAkun.setEnabled(true);
                et_passwordAkun.setEnabled(true);
                btn_batalEditAkun.setVisibility(View.VISIBLE);
                btn_SimpanEditAkun.setVisibility(View.VISIBLE);
                btn_editAkun.setVisibility(View.GONE);
                btn_LogoutAkun.setVisibility(View.GONE);
            }
        });

        btn_SimpanEditAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dRef = FirebaseDatabase.getInstance("https://imr-rsmmn-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Users").child(userkekey);
                dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("nama_lengkap").setValue(et_namaLengkapAkun.getText().toString());
                        snapshot.getRef().child("email").setValue(et_emailAkun.getText().toString());
                        snapshot.getRef().child("password").setValue(et_passwordAkun.getText().toString());

                        Toast.makeText(getActivity(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                        et_namaLengkapAkun.setEnabled(false);
                        et_emailAkun.setEnabled(false);
                        et_passwordAkun.setEnabled(false);
                        btn_batalEditAkun.setVisibility(View.GONE);
                        btn_SimpanEditAkun.setVisibility(View.GONE);
                        btn_editAkun.setVisibility(View.VISIBLE);
                        btn_LogoutAkun.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_batalEditAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_namaLengkapAkun.setEnabled(false);
                et_emailAkun.setEnabled(false);
                et_passwordAkun.setEnabled(false);
                btn_batalEditAkun.setVisibility(View.GONE);
                btn_SimpanEditAkun.setVisibility(View.GONE);
                btn_editAkun.setVisibility(View.VISIBLE);
                btn_LogoutAkun.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun, container, false);
    }

    public void getUserLocal(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(userkey_, Context.MODE_PRIVATE);
        userkekey = sharedPreferences.getString(userkey,"");
    }
}