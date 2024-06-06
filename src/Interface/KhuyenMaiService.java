/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.KMChiTiet;
import model.khuyenmai;

/**
 *
 * @author Admin
 */
public interface KhuyenMaiService {
    List<khuyenmai> getAll();
    List<KMChiTiet> getAllKMChiTiet(String makm);
    
    public void add(khuyenmai khuyenmai);
    
    public void update(khuyenmai khuyenmai);
    
    public void delete(khuyenmai khuyenmai);

    public void add(view.KhuyenMaiView khuyenmai);
    
    List<khuyenmai> timKiemKM(String maKM);
}
