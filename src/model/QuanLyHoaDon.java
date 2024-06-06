/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/**
 *
 * @author Admin
 */
public class QuanLyHoaDon {
    private int maHoaDon;
    private String nhanVien;
    private String khachHang;
    private int soLuong;
    private Date thoiGian;
    private double thanhTien;
    private boolean trangThai;
    
    
}
