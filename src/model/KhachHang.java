/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class KhachHang {
    private String maKh;
    private String hoTen;
    private boolean gioiTih;
    private String diaChi;
    private String email;
     private int soDienThoai;

    public KhachHang() {
    }

    public KhachHang(String maKh, String hoTen, boolean gioiTih, String diaChi, String email, int soDienThoai) {
        this.maKh = maKh;
        this.hoTen = hoTen;
        this.gioiTih = gioiTih;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }

   

  

    

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTih() {
        return gioiTih;
    }

    public void setGioiTih(boolean gioiTih) {
        this.gioiTih = gioiTih;
    }

   

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(int soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKh=" + maKh + ", hoTen=" + hoTen + ", gioiTih=" + gioiTih + ", diaChi=" + diaChi + ", soDienThoai=" + soDienThoai + ", email=" + email + '}';
    }

    
   
    
}
