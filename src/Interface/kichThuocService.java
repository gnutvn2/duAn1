/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.kichThuoc;

/**
 *
 * @author Admin
 */
public interface kichThuocService {
    public List<kichThuoc> getAll(int pageNumber,int pageSize);
    public void addkichThuoc(kichThuoc kt);
    public void updatekichThuoc(kichThuoc kt);
    public int getCount();
}
