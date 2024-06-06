/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Interface.sanPhamCTService;
import java.sql.Connection;
import java.util.List;
import model.sanPham;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.text.StyleConstants;
import model.sanPhamChiTiet;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class sanPhamCTServiceImpl implements sanPhamCTService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<sanPhamChiTiet> getByMaSPCT(String maSP, int pageNumber, int pageSize, String mauSac, String kichThuoc, String chatLieu) {
        int offset = (pageNumber - 1) * pageSize;
        List<sanPhamChiTiet> list = new ArrayList<>();
        String sql = "SELECT SPCHITIET.MaSPChiTiet, SANPHAM.MaSanPham, SANPHAM.TenSanPham, SPCHITIET.SoLuong, SPCHITIET.Gia, MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu\n"
                + "FROM SPCHITIET\n"
                + "JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham\n"
                + "JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau\n"
                + "JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                + "JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu\n"
                + "WHERE SANPHAM.MaSanPham = ?\n";

        if (mauSac != "") {
            sql += "AND MAUSAC.TenMau = ?\n";
        }
        if (kichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?\n";
        }
        if (chatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?\n";
        }
        sql += "ORDER BY SPCHITIET.MaSPChiTiet\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";
        int index = 1;

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(index, maSP);

            if (mauSac != "") {
                index++;
                pstm.setString(index, mauSac);
            }
            if (kichThuoc != "") {
                index++;
                pstm.setString(index, kichThuoc);
            }
            if (chatLieu != "") {
                index++;
                pstm.setString(index, chatLieu);
            }
            index++;
            pstm.setInt(index, offset);
            index++;
            pstm.setInt(index, pageSize);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sanPhamChiTiet sp = new sanPhamChiTiet();
                sp.setMaSPCT(rs.getString(1));
                sp.setMaSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getDouble(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addSPCT(sanPhamChiTiet spct) {
        String sql = "INSERT INTO SPCHITIET(MASPCHITIET, MASANPHAM, TENSANPHAM, GIA, SOLUONG, MAMAU, MAKICHTHUOC, MACHATLIEU) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, spct.getMaSPCT());
            pstm.setString(2, spct.getMaSP());
            pstm.setString(3, spct.getTenSP());
            pstm.setDouble(4, spct.getGia());
            pstm.setInt(5, spct.getSoLuong());

            // Chuyển đổi tên màu thành mã màu
            String maMau = getMaMau(spct.getMauSac());
            pstm.setString(6, maMau);

            String maKichThuoc = getMaKichThuoc(spct.getKichThuoc());
            pstm.setString(7, maKichThuoc);

            String maChatLieu = getMaChatLieu(spct.getChatLieu());
            pstm.setString(8, maChatLieu);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Phương thức lấy mã màu từ tên màu
    private String getMaMau(String tenMau) {
        String sql = "SELECT MaMau FROM MAUSAC WHERE TenMau = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, tenMau);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("MaMau");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trong trường hợp không tìm thấy mã màu, bạn có thể xử lý tùy ý, ví dụ: trả về null hoặc hiển thị thông báo lỗi
    }

    private String getMaKichThuoc(String tenKichThuoc) {
        String sql = "SELECT MaKichThuoc FROM KICHTHUOC WHERE TenKichThuoc = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, tenKichThuoc);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("MaKichThuoc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trong trường hợp không tìm thấy mã màu, bạn có thể xử lý tùy ý, ví dụ: trả về null hoặc hiển thị thông báo lỗi
    }

    private String getMaChatLieu(String tenChatLieu) {
        String sql = "SELECT MaChatLieu FROM CHATLIEU WHERE TenChatLieu = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, tenChatLieu);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("MaChatLieu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trong trường hợp không tìm thấy mã màu, bạn có thể xử lý tùy ý, ví dụ: trả về null hoặc hiển thị thông báo lỗi
    }

    @Override
    public void update(sanPhamChiTiet sp) {
        String sql = "UPDATE SPCHITIET SET GIA = ?, SOLUONG = ?, MAMAU = ?, MAKICHTHUOC = ?, MACHATLIEU = ? WHERE MASPCHITIET = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, sp.getGia());
            pstm.setInt(2, sp.getSoLuong());

            // Chuyển đổi tên màu thành mã màu
            String maMau = getMaMau(sp.getMauSac());
            pstm.setString(3, maMau);

            String maKichThuoc = getMaKichThuoc(sp.getKichThuoc());
            pstm.setString(4, maKichThuoc);

            String maChatLieu = getMaChatLieu(sp.getChatLieu());
            pstm.setString(5, maChatLieu);

            pstm.setString(6, sp.getMaSPCT());

            // Thực hiện cập nhật vào cơ sở dữ liệu
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(sanPhamChiTiet sp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<sanPhamChiTiet> locSP(String maSP, String tenMau, String tenKichThuoc, String tenChatLieu) {
        String sql = "SELECT SPCHITIET.MaSPChiTiet, SANPHAM.MaSanPham, SANPHAM.TenSanPham, SPCHITIET.SoLuong, SPCHITIET.Gia, MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu\n"
                + "FROM SPCHITIET\n"
                + "JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham\n"
                + "JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau\n"
                + "JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                + "JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu\n"
                + "WHERE SPCHITIET.MaSanPham = ? ";

        if (tenMau != "") {
            sql += "AND MAUSAC.TenMau = ?";
        }
        if (tenKichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?";
        }
        if (tenChatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?";
        }
        int index = 1;
        try {
            List<sanPhamChiTiet> list = new ArrayList<>();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(index, maSP);

            if (tenMau != "") {
                index++;
                pstm.setString(index, tenMau);
            }
            if (tenKichThuoc != "") {
                index++;
                pstm.setString(index, tenKichThuoc);
            }
            if (tenChatLieu != "") {
                index++;
                pstm.setString(index, tenChatLieu);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sanPhamChiTiet sp = new sanPhamChiTiet();
                sp.setMaSPCT(rs.getString(1));
                sp.setMaSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getDouble(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount(String maSP) {
        int count = 0;
        String sql = "select count(SPCHITIET.MaSPChiTiet) \n"
                + "from SPCHITIET\n"
                + "where MaSanPham = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, maSP);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getCountFilter(String maSP, String tenMau, String tenKichThuoc, String tenChatLieu) {
        int count = 0;
        String sql = "select count(SPCHITIET.MaSPChiTiet) FROM SPCHITIET\n"
                + "          JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham\n"
                + "            JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau\n"
                + "             JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                + "              JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu\n"
                + "             WHERE SPCHITIET.MaSanPham = ? ";

        if (tenMau != "") {
            sql += "AND MAUSAC.TenMau = ?";
        }
        if (tenKichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?";
        }
        if (tenChatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?";
        }
        int index = 1;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(index, maSP);

            if (tenMau != "") {
                index++;
                pstm.setString(index, tenMau);
            }
            if (tenKichThuoc != "") {
                index++;
                pstm.setString(index, tenKichThuoc);
            }
            if (tenChatLieu != "") {
                index++;
                pstm.setString(index, tenChatLieu);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<sanPhamChiTiet> locSP(String tenMau, String tenKichThuoc, String tenChatLieu, int pageSize) {

        String sql = "SELECT SPCHITIET.MaSPChiTiet, SANPHAM.MaSanPham, SANPHAM.TenSanPham, SPCHITIET.SoLuong, SPCHITIET.Gia, MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu\n"
                + "FROM SPCHITIET\n"
                + "JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham\n"
                + "JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau\n"
                + "JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                + "JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu\n";

        if (tenMau != "") {

            sql += " WHERE MAUSAC.TenMau = ?\n";
        }
        if (tenKichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?\n";
        }
        if (tenChatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?\n";
        }
        sql += "ORDER BY SPCHITIET.MaSPChiTiet\n"
                + "OFFSET 0 ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";
        int index = 1;
        try {
            List<sanPhamChiTiet> list = new ArrayList<>();
            PreparedStatement pstm = conn.prepareStatement(sql);

            if (tenMau != "") {

                pstm.setString(index, tenMau);
                index++;
            }
            if (tenKichThuoc != "") {

                pstm.setString(index, tenKichThuoc);
                index++;
            }
            if (tenChatLieu != "") {

                pstm.setString(index, tenChatLieu);
                index++;
            }
            pstm.setInt(index, pageSize);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sanPhamChiTiet sp = new sanPhamChiTiet();
                sp.setMaSPCT(rs.getString(1));
                sp.setMaSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getDouble(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCountFilter(String tenMau, String tenKichThuoc, String tenChatLieu) {
        int count = 0;
        String sql = "select count(SPCHITIET.MaSPChiTiet) FROM SPCHITIET\n"
                + "          JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham\n"
                + "            JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau\n"
                + "             JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                + "              JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu\n";

        if (tenMau != "") {
            sql += "AND MAUSAC.TenMau = ?";
        }
        if (tenKichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?";
        }
        if (tenChatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?";
        }
        int index = 1;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);

            if (tenMau != "") {

                pstm.setString(index, tenMau);
                index++;
            }
            if (tenKichThuoc != "") {

                pstm.setString(index, tenKichThuoc);
                index++;
            }
            if (tenChatLieu != "") {

                pstm.setString(index, tenChatLieu);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<sanPhamChiTiet> getByMaSPCT(int pageNumber, int pageSize, String mauSac, String kichThuoc, String chatLieu) {
        int offset = (pageNumber - 1) * pageSize;
        List<sanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT SPCHITIET.MaSPChiTiet, SANPHAM.MaSanPham, SANPHAM.TenSanPham, SPCHITIET.Gia,  SPCHITIET.SoLuong,MAUSAC.TenMau, KICHTHUOC.TenKichThuoc,
                     	 CHATLIEU.TenChatLieu, KHUYENMAI.TenChuongTrinh, KhuyenMai.NgayKetThuc, KhuyenMai.MucKhuyenMai
                     
                                        FROM SPCHITIET
                                          JOIN SANPHAM ON SPCHITIET.MaSanPham = SANPHAM.MaSanPham
                                          left JOIN KhuyenMaiChiTiet ON SPCHITIET.MaSPChiTiet = KhuyenMaiChiTiet.MaSPChiTiet
                                          left JOIN KhuyenMai ON KhuyenMaiChiTiet.MaKhuyenMai = KhuyenMai.MaKhuyenMai
                                          JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau
                                          JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc
                                          JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu
                     """;

        if (mauSac != "") {
            sql += "AND MAUSAC.TenMau = ?";
        }
        if (kichThuoc != "") {
            sql += " AND KICHTHUOC.TenKichThuoc = ?";
        }
        if (chatLieu != "") {
            sql += " AND CHATLIEU.TenChatLieu = ?\n";
        }
        sql += "ORDER BY SPCHITIET.MaSPChiTiet\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";
        int index = 1;
        System.out.println("query : " + sql);
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);

            if (mauSac != "") {

                pstm.setString(index, mauSac);
                index++;
            }
            if (kichThuoc != "") {

                pstm.setString(index, kichThuoc);
                index++;
            }
            if (chatLieu != "") {

                pstm.setString(index, chatLieu);
                index++;
            }

            pstm.setInt(index, offset);
            index++;
            pstm.setInt(index, pageSize);
            ResultSet rs = pstm.executeQuery();
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/mm/yyyy");
            Date currentDate = sdformat.parse(sdformat.format(new Date()));
            while (rs.next()) {
                sanPhamChiTiet sp = new sanPhamChiTiet();
                sp.setMaSPCT(rs.getString(1));
                sp.setMaSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getDouble(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                sp.setTenKM(rs.getString(9));
                sp.setNgayKetThuc(rs.getString(10));

                if (rs.getString(10) != null) {
                    if (sdformat.parse(rs.getString(10)).compareTo(currentDate) < 0) {
                        sp.setMucGiamGia(0);

                    } else {
                        sp.setMucGiamGia(rs.getDouble(11));
                    }
                    System.out.println("Ngay" + sdformat.parse(rs.getString(10)).compareTo(currentDate));
                } else {
                    sp.setMucGiamGia(rs.getDouble(11));

                }

                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<sanPhamChiTiet> getAll() {
        List<sanPhamChiTiet> list = new ArrayList<>();
        try {
            String sql = "select SPCHITIET.MaSPChiTiet ,SANPHAM.MaSanPham, SANPHAM.TenSanPham, SPCHITIET.Gia, SPCHITIET.SoLuong, MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu\n"
                    + "from SANPHAM\n"
                    + "join SPCHITIET on SANPHAM.MaSanPham = SPCHITIET.MaSanPham\n"
                    + "join KICHTHUOC on SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc\n"
                    + "join MAUSAC on SPCHITIET.MaMau = MAUSAC.MaMau\n"
                    + "join CHATLIEU on SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                sanPhamChiTiet sp = new sanPhamChiTiet();
                sp.setMaSPCT(rs.getString(1));
                sp.setMaSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getDouble(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setMauSac(rs.getString(6));
                sp.setKichThuoc(rs.getString(7));
                sp.setChatLieu(rs.getString(8));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
