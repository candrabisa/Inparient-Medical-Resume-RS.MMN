package com.rsmmn.inparientmedicalresumersmmn.model;

public class Model_FolderPasien {

    String nomor_rm, nama_pasien, diagnosis_utama_pasien,
    tgl_masuk_pasien, tgl_keluar_pasien;

    public Model_FolderPasien() {
    }

    public Model_FolderPasien(String nomor_rm, String nama_pasien, String diagnosis_utama_pasien, String tgl_masuk_pasien, String tgl_keluar_pasien) {
        this.nomor_rm = nomor_rm;
        this.nama_pasien = nama_pasien;
        this.diagnosis_utama_pasien = diagnosis_utama_pasien;
        this.tgl_masuk_pasien = tgl_masuk_pasien;
        this.tgl_keluar_pasien = tgl_keluar_pasien;
    }

    public String getNomor_rm() {
        return nomor_rm;
    }

    public void setNomor_rm(String nomor_rm) {
        this.nomor_rm = nomor_rm;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getDiagnosis_utama_pasien() {
        return diagnosis_utama_pasien;
    }

    public void setDiagnosis_utama_pasien(String diagnosis_utama_pasien) {
        this.diagnosis_utama_pasien = diagnosis_utama_pasien;
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
}
