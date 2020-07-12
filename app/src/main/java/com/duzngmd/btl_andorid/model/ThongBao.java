package com.duzngmd.btl_andorid.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private String tieude;
    private String ngay;
    private String noidung;
    private String noidung1;

    public ThongBao(String tieude, String ngay, String noidung, String noidung1) {
        this.tieude = tieude;
        this.ngay = ngay;
        this.noidung = noidung;
        this.noidung1 = noidung1;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNoidung1() {
        return noidung1;
    }

    public void setNoidung1(String noidung1) {
        this.noidung1 = noidung1;
    }
}
