/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.sanPham;
import model.sanPhamChiTiet;

/**
 *
 * @author Admin
 */
public interface sanPhamCTService {
//    public List<sanPhamChiTiet> getAll();
    public void addSPCT(sanPhamChiTiet spct);
    public void update(sanPhamChiTiet spct);
    public void delete(sanPhamChiTiet sp);
    public List<sanPhamChiTiet> locSP(String maSP, String tenMau, String tenKichThuoc, String tenChatLieu); 
    public List<sanPhamChiTiet> locSP(String tenMau, String tenKichThuoc, String tenChatLieu, int pageSize); 
    public List<sanPhamChiTiet> getByMaSPCT(String maSP, int pageNumber, int pageSize, String mauSac, String kichThuoc, String chatLieu);
    public List<sanPhamChiTiet> getByMaSPCT(int pageNumber, int pageSize, String mauSac, String kichThuoc, String chatLieu);
    public int getCount(String maSP);
    public int getCountFilter(String maSP, String tenMau, String tenKichThuoc, String tenChatLieu);
    public int getCountFilter(String tenMau, String tenKichThuoc, String tenChatLieu);
    
    public List<sanPhamChiTiet> getAll();

}
