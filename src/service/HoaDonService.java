/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.HDModel;
import model.HoaDonChiTiet;
import model.SanPhamTrang;

/**
 *
 * @author KTC
 */
public class HoaDonService {

    private Connection cn = DBContext1.getConnection();

    public List<HDModel> getAll() {
        String sql = """
                     SELECT [MaHoaDon]
                           ,[MaNhanVien]
                           ,[MaKhachHang]
                           ,[NgayBan]
                           ,[TinhTrang]
                       FROM [dbo].[HOADON]
                     WHERE TinhTrang = 0
                     ORDER BY NgayBan DESC
                     """;
        try (Connection con = DBContext1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<HDModel> list = new ArrayList<>();
            while (rs.next()) {
                HDModel hd = new HDModel();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getString(3));
                hd.setNgayBan(rs.getDate(4));
                hd.setTinhTrang(rs.getBoolean(5));
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean add(String maNV) {
        int check = 0;
        String sql = "INSERT INTO HOADON(MaNhanVien)VALUES(?)"; // Sử dụng DEFAULT VALUES để thêm dữ liệu tự động
        try (Connection con = DBContext1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTiet(int maHoaDon) {
        String sql = """
                     SELECT HOADONCHITIET.MaHoaDonCT, SPCHITIET.MaSPChiTiet,SPCHITIET.TenSanPham,
                     HOADONCHITIET.SoLuong, SPCHITIET.Gia, CHATLIEU.TenChatLieu, KICHTHUOC.TenKichThuoc, MAUSAC.TenMau, HOADONCHITIET.TongTien
                     ,KHUYENMAI.TenChuongTrinh, KhuyenMai.MucKhuyenMai,Hoadonchitiet.trangthaigiamgia
                                            FROM [dbo].[HOADONCHITIET]
                    
                                            JOIN SPCHITIET ON HOADONCHITIET.MaSPChiTiet = SPCHITIET.MaSPChiTiet
                                           left JOIN KhuyenMaiChiTiet ON SPCHITIET.MaSPChiTiet = KhuyenMaiChiTiet.MaSPChiTiet
                                           left JOIN KhuyenMai ON KhuyenMaiChiTiet.MaKhuyenMai = KhuyenMai.MaKhuyenMai
                     			JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau
                                     JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc
                                     JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu
                     Where MaHoaDon = ?;
                     """;
        try (Connection con = DBContext1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            List<HoaDonChiTiet> list = new ArrayList<>();
            while (rs.next()) {
                HoaDonChiTiet hd = new HoaDonChiTiet();
                hd.setMaHDCT(rs.getInt(1));
                hd.setMaSPCT(rs.getString(2));
                hd.setTenSP(rs.getString(3));
                hd.setSoLuong(rs.getInt(4));
                hd.setGia(rs.getDouble(5));
                hd.setChatLieu(rs.getString(6));
                hd.setKichThuoc(rs.getString(7));
                hd.setMauSac(rs.getString(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setTenKM(rs.getString(10));
                if(rs.getInt(12) == 1) {
                    hd.setMucGiamGia(rs.getDouble(11));
                }else {
                    hd.setMucGiamGia(0);
                }
                
                

                list.add(hd);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean addHoaDonChiTiet(HoaDonChiTiet hd) {
        int row = 0;
        System.out.println("muc giam gia : " + hd.getMucGiamGia());
        String sql = "INSERT INTO HOADONCHITIET(MaHoaDon, MaSPChiTiet, soLuong, TongTien,trangthaigiamgia) VALUES \n"
                + "(?,?,?,?,?)";
        try (Connection con = DBContext1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaHoaDon());
            ps.setString(2, hd.getMaSPCT());
            ps.setInt(3, hd.getSoLuong());
            ps.setDouble(4, hd.getTongTien());

            if(hd.getMucGiamGia() > 0) {
                ps.setInt(5, 1);
            }else {
                 ps.setInt(5, 0);
            }
            row = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row > 0;

    }

    public Integer deleteGioHang(String ma) {
        Integer row = null;
        String sql = """
                     DELETE FROM HOADONCHITIET where  MaHoaDonCT = ?
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

    public List<HDModel> getAllPhanTrang(int pageNumber, int pageSize) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<HDModel> list = new ArrayList<>();
            String sql = """
                     SELECT [MaHoaDon]
                           ,[MaNhanVien]
                           ,[MaKhachHang]
                           ,[NgayBan]
                           ,[TinhTrang]
                       FROM [dbo].[HOADON]
                     WHERE TinhTrang = 0
                      ORDER BY MaHoaDon DESC
                     OFFSET ? ROWS
                     FETCH NEXT ? ROWS ONLY
                     
                     """;

            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                HDModel hd = new HDModel();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getString(3));
                hd.setNgayBan(rs.getDate(4));
                hd.setTinhTrang(rs.getBoolean(5));
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        int count = 0;
        String sql = "select count(MaHoaDon) from HOADON WHERE TinhTrang = 0";
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

    public int getSoLuongSPTrongHoaDon(String maSP) {
        int soLuongTrongHoaDon = 0;
        String sql = "SELECT SUM(SoLuong) AS SoLuong FROM HOADONCHITIET WHERE MaHoaDonCT = ?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ps.setString(1, maSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                soLuongTrongHoaDon = rs.getInt("SoLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuongTrongHoaDon;
    }

}
