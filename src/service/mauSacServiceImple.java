/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Interface.mauSacService;
import java.sql.Connection;
import java.util.List;
import model.mauSac;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class mauSacServiceImple implements mauSacService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<mauSac> getAll(int pageNumber, int pageSize) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<mauSac> list = new ArrayList<>();
            String sql = "select * from MAUSAC\n"
                    + "ORDER BY MaMau\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                mauSac ms = new mauSac();
                ms.setMaMau(rs.getString(1));
                ms.setTenMau(rs.getString(2));
                list.add(ms);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMauSac(mauSac ms) {
        String sql = "INSERT INTO MAUSAC(MAMAU, TENMAU) VALUES(?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, ms.getMaMau());
            pstm.setString(2, ms.getTenMau());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMauSac(mauSac ms) {
        String sql = "UPDATE MAUSAC SET TENMAU = ? WHERE MAMAU = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, ms.getTenMau());
            pstm.setString(2, ms.getMaMau());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        String sql = "select COUNT(MAMAU) from MAUSAC";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
