/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.KhuyenMaiSP;

/**
 *
 * @author Admin
 */
public interface KhuyenMaiSPService {
    
    List<KhuyenMaiSP> getAll();
    
    public Integer addSPCT(String makm , String maSpct);
    public Integer deleteSPCT(String makm , int id);

}
