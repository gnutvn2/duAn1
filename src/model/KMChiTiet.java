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
public class KMChiTiet {
    private int Id;
    private String maSPCT;
    private String tenSp;
    private int soLuong;
    private String kichThuoc;
    private String mauSac;
    private double gia;

    public KMChiTiet(String maSPCT, String tenSp) {
        this.maSPCT = maSPCT;
        this.tenSp = tenSp;
    }

    
    
}
