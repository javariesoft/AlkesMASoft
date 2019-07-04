/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

/**
 *
 * @author USER
 */
public class ImportBarang {
    private String ob;
    private String kode_objek;
    private String nama;
    private double harga_satuan;

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }

    public String getKode_objek() {
        return kode_objek;
    }

    public void setKode_objek(String kode_objek) {
        this.kode_objek = kode_objek;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga_satuan() {
        return harga_satuan;
    }

    public void setHarga_satuan(double harga_satuan) {
        this.harga_satuan = harga_satuan;
    }
}
