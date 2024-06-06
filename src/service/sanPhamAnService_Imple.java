/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Interface.sanPhamAnService;
import java.util.List;
import model.sanPham;

import java.sql.*;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class sanPhamAnService_Imple implements sanPhamAnService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<sanPham> getMaSP(int pageNumber, int pageSize) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<sanPham> list = new ArrayList<>();
            String sql = """
                         select MASANPHAM, TENSANPHAM, TRANGTHAI from SANPHAM
                         WHERE TRANGTHAI = 1
                         ORDER BY masanpham
                         OFFSET ? ROWS
                         FETCH NEXT ? ROWS ONLY;""";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                sanPham sp = new sanPham();
                sp.setMaSP(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        int count = 0;
        String sql = "select count(masanpham) from sanpham where trangThai = 1";
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

    @Override
    public void hienThiSP(String maSP) {
        String sql = "UPDATE SANPHAM SET TRANGTHAI = 0 WHERE MASANPHAM = ? ";
        try {
            List<sanPham> list = new ArrayList<>();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, maSP);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
