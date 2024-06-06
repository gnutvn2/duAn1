/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KhuyenMaiSP;
import Interface.KhuyenMaiSPService;
import java.sql.*;
//import Interface.SP_ChiTietSanPhamService;
/**
 *
 * @author Admin
 */
public class KhuyenMaiSPServiceImpl implements KhuyenMaiSPService{
    private Connection conn = DBContext1.getConnection();

    @Override
    public List<KhuyenMaiSP> getAll() {
        try {
            List<KhuyenMaiSP> list = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT MaSanPham, TenSanPham, MaKichThuoc, MaMau, MaChatLieu, Gia, TrangThai FROM  SPCHITIET ";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String kichThuoc = rs.getString(3);
                String mauSac = rs.getString(4);
                String chatLieu = rs.getString(5);
                double donGia = rs.getDouble(6);
                boolean trangThai = rs.getBoolean(7);

                KhuyenMaiSP kmsp = new KhuyenMaiSP();
                kmsp.setMaSP(maSP);
                kmsp.setTenSP(tenSP);
                kmsp.setKichThuoc(kichThuoc);
                kmsp.setMauSac(mauSac);
                kmsp.setChatLieu(chatLieu);
                kmsp.setDonGia(donGia);
                kmsp.setTrangThai(trangThai);
                list.add(kmsp);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    

    @Override
    public Integer addSPCT(String makm , String maSpct) {
        Integer count = 0;
        String sql = "INSERT INTO KhuyenMaiChiTiet VALUES( ?,?)";
        String queryUpdate = "delete from khuyenmaichitiet where MaSPChiTiet = ?";
        
        try {
            
            PreparedStatement pstm2 = conn.prepareStatement(queryUpdate);
            pstm2.setString(1,maSpct);
            pstm2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1,makm );
             pstm.setString(2,maSpct );
            count = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer deleteSPCT(String makm, int id) {
        Integer count = 0;
        String sql = "delete from KhuyenMaiChiTiet where MaKhuyenMai = ? and id = ? ";
        try {
            
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1,makm );
             pstm.setInt(2,id );
            count = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
}
