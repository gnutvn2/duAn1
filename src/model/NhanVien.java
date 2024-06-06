/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String gioiTinh;
    private String diaChi;
    private int soDienThoai;
    private String email;
    private String matKhau;
    private boolean chucVu;
}
