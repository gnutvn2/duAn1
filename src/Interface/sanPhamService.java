/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.sanPham;

/**
 *
 * @author Admin
 */
public interface sanPhamService {
     public List<sanPham> getAll(int pageNumber,int pageSize);
    public void addSanPham(sanPham sp);
    public void suaSanPham(sanPham sp);
    public void anSanPham(String maSP);
    public List<sanPham> timKiemSanPham(String ten);
    public sanPham getByMaSP(String maSP);
    public int getCount();
}
