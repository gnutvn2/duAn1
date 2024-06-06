/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.khuyenmai;
import Interface.KhuyenMaiService;
import java.sql.*;
import model.KMChiTiet;

/**
 *
 * @author Admin
 */
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<khuyenmai> getAll() {
        try {
            List<khuyenmai> list = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT MaKhuyenMai, TenChuongTrinh, NgayBatDau, NgayKetThuc, MucKhuyenMai, TinhTrang FROM  khuyenmai ";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String maKM = rs.getString(1);
                String tenKM = rs.getString(2);
                String ngayBatDau = rs.getString(3);
                String ngayKetThuc = rs.getString(4);
                Double giamGia = rs.getDouble(5);
                boolean tinhTrang = rs.getBoolean(6);

                khuyenmai km = new khuyenmai();
                km.setMaKM(maKM);
                km.setTenKM(tenKM);
                km.setNgayBatDau(ngayBatDau);
                km.setNgayKetThuc(ngayKetThuc);
                km.setGiamGia(giamGia);
                km.setTinhTrang(tinhTrang);
                list.add(km);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(khuyenmai khuyenmai) {
        String sql = "INSERT INTO khuyenmai (MaKhuyenMai, TenChuongTrinh, NgayBatDau, NgayKetThuc, MucKhuyenMai, TinhTrang) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, khuyenmai.getMaKM());
            stm.setString(2, khuyenmai.getTenKM());
            stm.setString(3, khuyenmai.getNgayBatDau());
            stm.setString(4, khuyenmai.getNgayKetThuc());
            stm.setDouble(5, khuyenmai.getGiamGia());
            stm.setBoolean(6, khuyenmai.isTinhTrang());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(khuyenmai khuyenmai) {
        System.out.println(khuyenmai.isTinhTrang());
        String sql = "UPDATE khuyenmai SET TenChuongTrinh = ?,  NgayBatDau = ? , NgayKetThuc = ?, MucKhuyenMai = ?, TinhTrang = ?  WHERE MaKhuyenMai = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, khuyenmai.getTenKM());
            stm.setString(2, khuyenmai.getNgayBatDau());
            stm.setString(3, khuyenmai.getNgayKetThuc());
            stm.setDouble(4, khuyenmai.getGiamGia());
            stm.setBoolean(5, khuyenmai.isTinhTrang());
            stm.setString(6, khuyenmai.getMaKM());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(khuyenmai khuyenmai) {
        String sql = "DELETE FROM khuyenmai WHERE MaKhuyenMai = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, khuyenmai.getMaKM());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(view.KhuyenMaiView khuyenmai) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<khuyenmai> timKiemKM(String maKM) {
        List<khuyenmai> getList = new ArrayList<>();
        try {
            String sql = "SELECT MaKhuyenMai ,  TenChuongTrinh, NgayBatDau, NgayKetThuc, MucKhuyenMai, TinhTrang FROM khuyenmai where MaKhuyenMai = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, maKM);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                String tenKM = rs.getString(2);
                String ngayBatDau = rs.getString(3);
                String ngayKetThuc = rs.getString(4);
                Double giamGia = rs.getDouble(5);
                boolean tinhTrang = rs.getBoolean(6);

                khuyenmai km = new khuyenmai();
                km.setMaKM(maKM);
                km.setTenKM(tenKM);
                km.setNgayBatDau(ngayBatDau);
                km.setNgayKetThuc(ngayKetThuc);
                km.setGiamGia(giamGia);
                km.setTinhTrang(tinhTrang);
                getList.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getList;
    }

    @Override
    public List<KMChiTiet> getAllKMChiTiet(String maKM) {
        try {
            List<KMChiTiet> list = new ArrayList<>();
            String sql = "select Id,KHUYENMAICHITIET.MaSPChiTiet,TenSanPham , SoLuong,Gia,MAUSAC.TenMau,KICHTHUOC.TenKichThuoc,CHATLIEU.TenChatLieu\n"
                    + "                    from KhuyenMaiChiTiet \n"
                    + "                    INNER JOIN SPChiTiet on KhuyenMaiChiTiet.MaSPChiTiet = SPChiTiet.MaSPChiTiet\n"
                    + "                    INNER JOIN MAUSAC on SPChiTiet.MaMau = MAUSAC.MaMau\n"
                    + "                    INNER JOIN KICHTHUOC on KICHTHUOC.MaKichThuoc = SPChiTiet.MaKichThuoc\n"
                    + "                    INNER JOIN CHATLIEU on SPChiTiet.MaChatLieu = CHATLIEU.MaChatLieu\n"
                    + "                    where MaKhuyenMai = ?";

            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, maKM);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                String maKMCT = rs.getString(1);
                String maSPCT = rs.getString(2);
                String tenSP = rs.getString(3);
                String SoLuong = rs.getString(4);
                Double Gia = rs.getDouble(5);
                String MAUSAC = rs.getString(6);
                String KichThuoc = rs.getString(7);
                KMChiTiet km = new KMChiTiet();
                km.setId(Integer.parseInt(maKMCT));
                km.setTenSp(tenSP);
                km.setSoLuong(Integer.parseInt(SoLuong));
                km.setMauSac(MAUSAC);
                km.setKichThuoc(KichThuoc);
                km.setGia(Gia);

                list.add(km);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
