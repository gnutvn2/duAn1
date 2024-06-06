/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 *
 * @author Admin
 */
public class HoaDonChiTiet {

    private int maHDCT;
    private int maHoaDon;
    private String maSPCT;
    private String tenSP;
    private String chatLieu;
    private String kichThuoc;
    private String mauSac;
    private int soLuong;
    private double gia;
    private double tongTien;
    private String tenKM;
    private double mucGiamGia;
    private int trangThaiGiamGia;
    

    public HoaDonChiTiet(int maHDCT, int maHoaDon, String maSPCT, int soLuong, double tongTien) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.tongTien = tongTien;

    }
    
    public HoaDonChiTiet(int maHDCT, int maHoaDon, String maSPCT, int soLuong, double tongTien,double mucGiamGia) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.mucGiamGia = mucGiamGia;
    }

//    public HoaDonChiTiet(int maHDCT, int maHoaDon, String maSPCT, String tenSP, String chatLieu, String kichThuoc, String mauSac, int soLuong, double gia, double tongTien, String tenKM, double mucGiamGia, int trangThaiGiamGia) {
//        this.maHDCT = maHDCT;
//        this.maHoaDon = maHoaDon;
//        this.maSPCT = maSPCT;
//        this.tenSP = tenSP;
//        this.chatLieu = chatLieu;
//        this.kichThuoc = kichThuoc;
//        this.mauSac = mauSac;
//        this.soLuong = soLuong;
//        this.gia = gia;
//        this.tongTien = tongTien;
//        this.tenKM = tenKM;
//        this.mucGiamGia = mucGiamGia;
//        this.trangThaiGiamGia = trangThaiGiamGia;
//    }

    
}
