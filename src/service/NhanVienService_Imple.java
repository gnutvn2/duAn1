/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import model.NhanVien;
import Interface.NhanVienService;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhanVienService_Imple implements NhanVienService {

    private Connection con = DBContext1.getConnection();

    @Override
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "Select NhanVien.MaNhanVien, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.DienThoai, NhanVien.Email, NhanVien.MatKhau, CHUCVU.TenChucVu\n"
                + "from NHANVIEN join CHUCVU on NHANVIEN.MaChucVu = CHUCVU.MaChucVu";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setGioiTinh(rs.getString(3));
                nv.setDiaChi(rs.getString(4));
                nv.setSoDienThoai(rs.getInt(5));
                nv.setEmail(rs.getString(6));
                nv.setMatKhau(rs.getString(7));
                nv.setChucVu(rs.getBoolean(8));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMaChucVu(boolean isQuanLy) {
        return isQuanLy ? "CV1" : "CV02";
    }

    @Override
    public void addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien(MaNhanVien, HoTen, GioiTinh, DiaChi, DienThoai, Email, MatKhau, MaChucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nv.getMaNhanVien());
            pstm.setString(2, nv.getTenNhanVien());
            pstm.setString(3, nv.getGioiTinh());
            pstm.setString(4, nv.getDiaChi());
            pstm.setInt(5, nv.getSoDienThoai());
            pstm.setString(6, nv.getEmail());
            pstm.setString(7, nv.getMatKhau());
            pstm.setString(8, getMaChucVu(nv.isChucVu()));

            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, DiaChi=?, DienThoai=?, Email=?, MatKhau=?, MaChucVu=? WHERE MaNhanVien=?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nv.getTenNhanVien());
            pstm.setString(2, nv.getGioiTinh());
            pstm.setString(3, nv.getDiaChi());
            pstm.setInt(4, nv.getSoDienThoai());
            pstm.setString(5, nv.getEmail());
            pstm.setString(6, nv.getMatKhau());

            if (nv.isChucVu()) {
                pstm.setString(7, "CV1");
            } else {
                pstm.setString(7, "CV02");
            }

            pstm.setString(8, nv.getMaNhanVien());

            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNhanVien(NhanVien nv) {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien=?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nv.getMaNhanVien());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NhanVien> timKiem(String keyword) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT NhanVien.MaNhanVien, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.DienThoai, NhanVien.Email, NhanVien.MatKhau, CHUCVU.TenChucVu\n"
                + "FROM NHANVIEN JOIN CHUCVU ON NHANVIEN.MaChucVu = CHUCVU.MaChucVu\n"
                + "WHERE NhanVien.MaNhanVien LIKE ? OR NhanVien.HoTen LIKE ?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "%" + keyword + "%");
            pstm.setString(2, "%" + keyword + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setGioiTinh(rs.getString(3));
                nv.setDiaChi(rs.getString(4));
                nv.setSoDienThoai(rs.getInt(5));
                nv.setEmail(rs.getString(6));
                nv.setMatKhau(rs.getString(7));
                nv.setChucVu(rs.getBoolean(8));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<NhanVien> getAllPhanTrang(int pageNumber, int pageSize) {

        List<NhanVien> list = new ArrayList<>();
        try (Connection cn = DBContext1.getConnection()) {
            int offset = (pageNumber - 1) * pageSize; //// tinh offset 
            String sql = """
                         SELECT *
                         FROM (
                             SELECT ROW_NUMBER() OVER (ORDER BY NHANVIEN.MaNhanVien) AS RowNum,
                                    NHANVIEN.MaNhanVien, 
                                    NHANVIEN.HoTen, 
                                    NHANVIEN.GioiTinh, 
                                    NHANVIEN.DiaChi, 
                                    NHANVIEN.DienThoai, 
                                    NHANVIEN.Email, 
                                    NHANVIEN.MatKhau, 
                                    CHUCVU.TenChucVu
                             FROM NHANVIEN 
                             JOIN CHUCVU ON NHANVIEN.MaChucVu = CHUCVU.MaChucVu
                         ) AS NVWithRowNumbers
                         WHERE RowNum > ? AND RowNum <= ?
                        """;
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, offset);
                ps.setInt(2, offset + pageSize);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String maNhanVien = rs.getString("MaNhanVien");
                        String hoTen = rs.getString("HoTen");
                        String gioiTinh = rs.getString("GioiTinh");
                        String diaChi = rs.getString("DiaChi");
                        int dienThoai = rs.getInt("DienThoai");
                        String email = rs.getString("Email");
                        String matKhau = rs.getString("MatKhau");
                        boolean tenChucVu = rs.getBoolean("TenChucVu");
                        
                        NhanVien nv = new NhanVien(maNhanVien, maNhanVien, gioiTinh, diaChi, dienThoai, email, matKhau, tenChucVu);
                        list.add(nv);
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
