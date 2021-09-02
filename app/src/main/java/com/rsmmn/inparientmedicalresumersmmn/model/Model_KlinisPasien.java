package com.rsmmn.inparientmedicalresumersmmn.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_KlinisPasien implements Parcelable {
    String nama_pasien, nomor_rm, tgl_lahir_pasien,
            tgl_masuk_pasien, tgl_keluar_pasien, ruang_perawatan_pasien;

    public Model_KlinisPasien() {

    }

    protected Model_KlinisPasien(Parcel in) {
        nama_pasien = in.readString();
        nomor_rm = in.readString();
        tgl_lahir_pasien = in.readString();
        tgl_masuk_pasien = in.readString();
        tgl_keluar_pasien = in.readString();
        ruang_perawatan_pasien = in.readString();
    }

    public static final Creator<Model_KlinisPasien> CREATOR = new Creator<Model_KlinisPasien>() {
        @Override
        public Model_KlinisPasien createFromParcel(Parcel in) {
            return new Model_KlinisPasien(in);
        }

        @Override
        public Model_KlinisPasien[] newArray(int size) {
            return new Model_KlinisPasien[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama_pasien);
        dest.writeString(nomor_rm);
        dest.writeString(tgl_lahir_pasien);
        dest.writeString(tgl_masuk_pasien);
        dest.writeString(tgl_keluar_pasien);
        dest.writeString(ruang_perawatan_pasien);
    }

    public Model_KlinisPasien(String nama_pasien, String nomor_rm, String tgl_lahir_pasien, String tgl_masuk_pasien, String tgl_keluar_pasien, String ruang_perawatan_pasien) {
        this.nama_pasien = nama_pasien;
        this.nomor_rm = nomor_rm;
        this.tgl_lahir_pasien = tgl_lahir_pasien;
        this.tgl_masuk_pasien = tgl_masuk_pasien;
        this.tgl_keluar_pasien = tgl_keluar_pasien;
        this.ruang_perawatan_pasien = ruang_perawatan_pasien;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getNomor_rm() {
        return nomor_rm;
    }

    public void setNomor_rm(String nomor_rm) {
        this.nomor_rm = nomor_rm;
    }

    public String getTgl_lahir_pasien() {
        return tgl_lahir_pasien;
    }

    public void setTgl_lahir_pasien(String tgl_lahir_pasien) {
        this.tgl_lahir_pasien = tgl_lahir_pasien;
    }

    public String getTgl_masuk_pasien() {
        return tgl_masuk_pasien;
    }

    public void setTgl_masuk_pasien(String tgl_masuk_pasien) {
        this.tgl_masuk_pasien = tgl_masuk_pasien;
    }

    public String getTgl_keluar_pasien() {
        return tgl_keluar_pasien;
    }

    public void setTgl_keluar_pasien(String tgl_keluar_pasien) {
        this.tgl_keluar_pasien = tgl_keluar_pasien;
    }

    public String getRuang_perawatan_pasien() {
        return ruang_perawatan_pasien;
    }

    public void setRuang_perawatan_pasien(String ruang_perawatan_pasien) {
        this.ruang_perawatan_pasien = ruang_perawatan_pasien;
    }
}
