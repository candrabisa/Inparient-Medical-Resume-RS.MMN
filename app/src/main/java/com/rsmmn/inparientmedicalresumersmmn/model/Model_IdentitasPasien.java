package com.rsmmn.inparientmedicalresumersmmn.model;

import com.rsmmn.inparientmedicalresumersmmn.caridata_pasien;

import java.util.List;

public class Model_IdentitasPasien {

    long nomor_urut;
    String nama_pasien, nomor_rm, tgl_lahir_pasien,
            tgl_masuk_pasien, tgl_keluar_pasien, ruang_perawatan_pasien;

    public Model_IdentitasPasien() {
    }

    public Model_IdentitasPasien(long nomor_urut, String nama_pasien, String nomor_rm, String tgl_lahir_pasien, String tgl_masuk_pasien, String tgl_keluar_pasien, String ruang_perawatan_pasien) {
        this.nomor_urut = nomor_urut;
        this.nama_pasien = nama_pasien;
        this.nomor_rm = nomor_rm;
        this.tgl_lahir_pasien = tgl_lahir_pasien;
        this.tgl_masuk_pasien = tgl_masuk_pasien;
        this.tgl_keluar_pasien = tgl_keluar_pasien;
        this.ruang_perawatan_pasien = ruang_perawatan_pasien;
    }

    public long getNomor_urut() {
        return nomor_urut;
    }

    public void setNomor_urut(long nomor_urut) {
        this.nomor_urut = nomor_urut;
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
