/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Interface.kichThuocService;
import java.sql.Connection;
import java.util.List;
import model.kichThuoc;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class kichThuocServiceImple implements kichThuocService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<kichThuoc> getAll(int pageNumber, int pageSize) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<kichThuoc> list = new ArrayList<>();
            String sql = "select * from KICHTHUOC\n"
                    + "ORDER BY MaKichThuoc\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                kichThuoc kt = new kichThuoc();
                kt.setMaKichThuoc(rs.getString(1));
                kt.setTenKichThuoc(rs.getString(2));
                list.add(kt);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addkichThuoc(kichThuoc kt) {
        String sql = "INSERT INTO KICHTHUOC(MAKICHTHUOC, TENKICHTHUOC) VALUES(?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, kt.getMaKichThuoc());
            pstm.setString(2, kt.getTenKichThuoc());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatekichThuoc(kichThuoc kt) {
        String sql = "UPDATE KICHTHUOC SET TENKICHTHUOC = ? WHERE MAKICHTHUOC = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, kt.getTenKichThuoc());
            pstm.setString(2, kt.getMaKichThuoc());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        String sql = "select COUNT(MAKICHTHUOC) from KICHTHUOC";
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
