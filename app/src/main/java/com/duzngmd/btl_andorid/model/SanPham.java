package com.duzngmd.btl_andorid.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class SanPham implements Serializable, Comparable {
    private String ten;
    private String anh_chinh;
    private double gia;
    private String anh_phu;
    private String mota;
    @Exclude
    private int soLuong;

    public SanPham() {

    }

    public SanPham(String ten, String anh_chinh, double gia, String anh_phu, String mota) {
        this.ten = ten;
        this.anh_chinh = anh_chinh;
        this.gia = gia;
        this.anh_phu = anh_phu;
        this.mota = mota;
    }

    public SanPham(String ten, String anh_chinh, double gia, String anh_phu, String mota, int soLuong) {
        this.ten = ten;
        this.anh_chinh = anh_chinh;
        this.gia = gia;
        this.anh_phu = anh_phu;
        this.mota = mota;
        this.soLuong = soLuong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh_chinh() {
        return anh_chinh;
    }

    public void setAnh_chinh(String anh_chinh) {
        this.anh_chinh = anh_chinh;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getAnh_phu() {
        return anh_phu;
    }

    public void setAnh_phu(String anh_phu) {
        this.anh_phu = anh_phu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Exclude
    public int getSoLuong() {
        return soLuong;
    }

    @Exclude
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
