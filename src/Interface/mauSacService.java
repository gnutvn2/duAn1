/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.mauSac;

/**
 *
 * @author Admin
 */
public interface mauSacService {
    public List<mauSac> getAll(int pageNumber,int pageSize);
    public void addMauSac(mauSac ms);
    public void updateMauSac(mauSac ms);
    public int getCount();
}
