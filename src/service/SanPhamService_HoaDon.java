/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.SanPhamTrang;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 *
 * @author KTC
 */
public class SanPhamService_HoaDon {

    private Connection cn = DBContext1.getConnection();

    public List<SanPhamTrang> getAllPhanTrang(int pageNumberSP, int pageSizeSP) {
        try {
            int offset = (pageNumberSP - 1) * pageSizeSP;
            List<SanPhamTrang> list = new ArrayList<>();
            String sql = """
    SELECT SPCHITIET.MaSPChiTiet, SPCHITIET.TenSanPham, SPCHITIET.SoLuong, SPCHITIET.Gia, 
              MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu, SANPHAM.MaSanPham
   		   , KHUYENMAI.TenChuongTrinh, KhuyenMai.NgayKetThuc, KhuyenMai.MucKhuyenMai
              
       FROM SPCHITIET
   	JOIN KhuyenMaiChiTiet ON SPCHITIET.MaSPChiTiet = KhuyenMaiChiTiet.MaSPChiTiet
   	JOIN KhuyenMai ON KhuyenMaiChiTiet.MaKhuyenMai = KhuyenMai.MaKhuyenMai
       JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham
       JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau
       JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc
       JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu
       ORDER BY SPCHITIET.MaSPChiTiet
    OFFSET ? ROWS
    FETCH NEXT ? ROWS ONLY;
""";

            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSizeSP);
            ResultSet rs = stm.executeQuery();
           
            while (rs.next()) {

                SanPhamTrang sp = new SanPhamTrang();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSP(rs.getString(2));
                sp.setSoLuong(rs.getInt(3));
                sp.setGia(rs.getDouble(4));
                sp.setChatLieu(rs.getString(5));
                sp.setKichThuoc(rs.getString(6));
                sp.setMauSac(rs.getString(7));
                sp.setMaSP(rs.getString(8));
                sp.setTenKM(rs.getString(9));
                sp.setNgayKetThuc(rs.getString(10));
              
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        int count = 0;
        String sql = "select count(masanpham) from sanpham";
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<SanPhamTrang> findSanPhamByName(String tenSP) {
        List<SanPhamTrang> listSP = new ArrayList<>();
        Connection con = DBContext1.getConnection();
        try {
            String sql = """
                         SELECT [MaSPChiTiet]
                               ,[TenSanPham]
                               ,[SoLuong]
                               ,[Gia]
                               ,[MaChatLieu]
                               ,[MaKichThuoc]
                               ,[MaMau]
                               ,[MaSanPham]
                               ,[LoaiSP]
                           FROM [dbo].[SPCHITIET]
                         WHERE [TenSanPham] LIKE ? 
                         """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamTrang sp = new SanPhamTrang();
                sp.setMaSPCT(rs.getString("MaSPChiTiet"));
                sp.setTenSP(rs.getString("TenSanPham"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setGia(rs.getDouble("Gia"));
                sp.setChatLieu(rs.getString("MaChatLieu"));
                sp.setKichThuoc(rs.getString("MaKichThuoc"));
                sp.setMauSac(rs.getString("MaMau"));
                sp.setMaSP(rs.getString("MaSanPham"));
                sp.setLoaiSP(rs.getString("LoaiSP"));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public int getSoLuongSPByMaSP(String maSP) {
        int soLuong = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBContext1.getConnection();
            String sql = "SELECT SoLuong FROM SPCHITIET WHERE MaSanPham = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            rs = ps.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return soLuong;
    }

    public Integer capNhatSoLuongSPTrongSPChiTiet(String maSanPham, int soLuongMoi) {
        Integer row = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBContext1.getConnection();
            String updateQuery = "UPDATE HOADONCHITIET SET SoLuong = ? WHERE MaSPChiTiet = ?";
            ps = con.prepareStatement(updateQuery);
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maSanPham);
            row = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return row;
    }

}
