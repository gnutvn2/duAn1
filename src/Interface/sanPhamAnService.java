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
public interface sanPhamAnService {
    List<sanPham> getMaSP(int pageNumber, int pageSize);
    public void hienThiSP(String maSP);
    public int getCount();
}
