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
import model.KhachHang;

/**
 *
 * @author Admin
 */
public class KhachHangService {

    public List<KhachHang> getAllKhachHang() {
        String sql = """
                    SELECT [MaKhachHang]
                          ,[HoVaTen]
                          ,[GioiTinh]
                          ,[DiaChi]
                          ,[Email]
                          ,[SDT]
                      FROM [dbo].[KHACHHANG]
                     """;

        try (Connection cn = DBContext1.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<KhachHang> list = new ArrayList<>();

            while (rs.next()) {
                KhachHang sp = new KhachHang();
                sp.setMaKh(rs.getString(1));
                sp.setHoTen(rs.getString(2));
                sp.setGioiTih(rs.getBoolean(3));
                sp.setDiaChi(rs.getString(4));
                sp.setEmail(rs.getString(5));
                sp.setSoDienThoai(rs.getInt(6));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        List<SanPham> listsp = new SanPhamService().getAllSanPham();
//        System.out.println(listsp);
//    }
//
    public Integer addKhachHang(KhachHang kh) {
        Integer row = null;
        String sql = """
                   INSERT INTO [dbo].[KHACHHANG]
                              ([MaKhachHang]
                              ,[HoVaTen]
                              ,[GioiTinh]
                              ,[DiaChi]
                              ,[Email]
                              ,[SDT])
                        VALUES(?,?,?,?,?,?)
                     """;

        Connection cn = DBContext1.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, kh.getMaKh());
            pstm.setString(2, kh.getHoTen());
            pstm.setBoolean(3, kh.isGioiTih());
            pstm.setString(4, kh.getDiaChi());
            pstm.setString(5, kh.getEmail());
            pstm.setInt(6, kh.getSoDienThoai());

            row = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public Integer updateKhachHang(KhachHang kh) {
        Integer row = null;
        String sql = """
                   UPDATE [dbo].[KHACHHANG]
                      SET 
                   	   [HoVaTen] = ?
                         ,[GioiTinh] = ?
                         ,[DiaChi] = ?
                         ,[Email] = ?
                         ,[SDT] = ?
                    WHERE [MaKhachHang] = ?
                     """;

        Connection cn = DBContext1.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setString(1, kh.getHoTen());
            pstm.setBoolean(2, kh.isGioiTih());
            pstm.setString(3, kh.getDiaChi());
            pstm.setString(4, kh.getEmail());
            pstm.setInt(5, kh.getSoDienThoai());
            pstm.setString(6, kh.getMaKh());

            row = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public Integer deleteKhachHang(String ma) {
        Integer row = null;
        String sql = """
                     DELETE FROM KhachHang where MaKhachHang= ?
                     """;

        Connection cn = DBContext1.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, ma);

            row = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    ////Phan trang
    public List<KhachHang> getAllPhanTrang(int pageNumber, int pageSize) {
        List<KhachHang> list = new ArrayList<>();
        try (Connection cn = DBContext1.getConnection()) {
            int offset = (pageNumber - 1) * pageSize; //// tinh offset 
            String sql = """
                          SELECT * FROM (SELECT ROW_NUMBER() 
                          OVER (ORDER BY MaKhachHang) AS RowNum, * FROM KHACHHANG) 
                          AS UsersWithRowNumbers WHERE RowNum > ? AND RowNum <= ?
                          """;
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, offset);
                ps.setInt(2, offset + pageSize);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String maKH = rs.getString("MaKhachHang");
                        String tenKH = rs.getString("HoVaTen");
                        boolean gioiTinh = rs.getBoolean("GioiTinh");
                        String diaChi = rs.getString("DiaChi");
                        String email = rs.getString("Email");
                        int sdt = rs.getInt("SDT");
                        KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt);
                        list.add(kh);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhachHang> findKhachHangByName(String ten) {
        List<KhachHang> listkh = new ArrayList<>();
        Connection con = DBContext1.getConnection();
        try {
            String sql = "SELECT * FROM [dbo].[KHACHHANG]\n"
                    + "WHERE [HoVaTen] LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + ten + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKh(rs.getString("MaKhachHang"));
                kh.setHoTen(rs.getString("HoVaTen"));
                kh.setGioiTih(rs.getBoolean("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setEmail(rs.getString("Email"));
                kh.setSoDienThoai(rs.getInt("SDT"));
                listkh.add(kh);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listkh;
    }
}
