package com.example.demo.models;

import lombok.Builder;


public class TKDTTKhachHang extends User{
    private double doanhthu;

    public double getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(double doanhthu) {
        this.doanhthu = doanhthu;
    }
}
