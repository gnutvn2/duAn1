/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import model.NhanVien;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface NhanVienService {
    public List<NhanVien> getAll();
    public void addNhanVien(NhanVien nv);
    public void updateNhanVien(NhanVien nv);
    public void deleteNhanVien(NhanVien nv);
    public List<NhanVien> timKiem(String keyword);
     public List<NhanVien> getAllPhanTrang(int pageNumber, int pageSize);
}
