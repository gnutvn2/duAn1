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
import model.HoaDonChiTiet;
import model.QuanLyHoaDon;
import model.Revenue;
import model.chatLieu;
import model.mauSac;
import static view.ThongKe.LIST_COLOR;
import static view.ThongKe.LIST_MATERIAL;
import static view.ThongKe.pageNumber;
import static view.ThongKe.pageSize;

/**
 *
 * @author Admin
 */
public class QLHoaDonService {

    public List<QuanLyHoaDon> getAllHoaDon() {
        String sql = """
                  SELECT HOADON.MaHoaDon, HOADON.MaNhanVien, HOADON.MaKhachHang,
                                      HOADON.NgayBan, SUM(HOADONCHITIET.TongTien), HOADON.TinhTrang
                                      FROM HOADON
                                      JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                                      where HOADON.TinhTrang = 1
                  					group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                  					HOADON.NgayBan,  HOADON.TinhTrang
                     """;

        try (Connection cn = DBContext1.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<QuanLyHoaDon> list = new ArrayList<>();

            while (rs.next()) {
                QuanLyHoaDon ql = new QuanLyHoaDon();
                ql.setMaHoaDon(rs.getInt(1));
                ql.setNhanVien(rs.getString(2));
                ql.setKhachHang(rs.getString(3));
                ql.setThoiGian(rs.getDate(4));
                ql.setThanhTien(rs.getDouble(5));
                ql.setTrangThai(rs.getBoolean(6));
                list.add(ql);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer updateSoLuongGioHang(QuanLyHoaDon ql) {
        Integer row = null;
        String sql = """
                     UPDATE HOADONCHITIET Set soLuong = ?
                     WHERE MaHoaDonCT = ?
                     """;

        Connection cn = DBContext1.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setInt(1, ql.getSoLuong());
            pstm.setInt(2, ql.getMaHoaDon());

            row = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;

    }

    public List<Integer> getCountOrder() {
        List<Integer> listCount = new ArrayList<>();
        String[] listQuery = {
            """
                     select count(mahoadon) from hoadon
            """,
            """
                     select count(mahoadon) from hoadon where tinhtrang = 0
            """,
            """
                     select count(mahoadon) from hoadon where tinhtrang = 1
            """,
            """
                    select count(mahoadon) from
                     (select hoadon.mahoadon from HOADON 
                          JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                          where HOADON.TinhTrang = 1 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-1,GETDATE()))
                     		group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang 	)x
            """,
            """
                     select count(mahoadon) from
                     (select hoadon.mahoadon from HOADON 
                          JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                          where HOADON.TinhTrang = 0 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-1,GETDATE()))
                     		group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang 	)x
            """,
            """
                     select count(mahoadon) from
                     (select hoadon.mahoadon from HOADON 
                          JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                          where HOADON.TinhTrang = 1 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-30,GETDATE()))
                     		group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang 	)x
            """,
            """
                   select count(mahoadon) from
                     (select hoadon.mahoadon from HOADON 
                          JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                          where HOADON.TinhTrang = 0 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-30,GETDATE()))
                     		group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang 	)x
            """,};

        Connection cn = DBContext1.getConnection();

        for (String query : listQuery) {
            try {
                PreparedStatement pstm = cn.prepareStatement(query);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    listCount.add(rs.getInt(1));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listCount;

    }
     public List<Integer> getRevenuesChart(int numberOfMonth) {
        List<Integer> listCount = new ArrayList<>();
        List<String> listQuery = new ArrayList<>();
        
        for(int i = numberOfMonth  ; i > 0 ; i--) {
            System.out.println(i);
            listQuery.add( " SELECT    SUM(TongTien) from\n" +
"                     \n" +
"                     (SELECT SUM(HOADONCHITIET.TongTien) as TongTien \n" +
"                                         FROM HOADON\n" +
"                                         JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon\n" +
"                                         where HOADON.TinhTrang = 1 and  ngayban > (DATEADD(month," + (-i) + ", GETDATE())) and ngayban < (DATEADD(month," +(1-i)+ ",GETDATE()))\n" +
"                     					group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,\n" +
"                     					HOADON.NgayBan,  HOADON.TinhTrang ) x") ;
            }


        Connection cn = DBContext1.getConnection();

        for (String query : listQuery) {
            try {
                PreparedStatement pstm = cn.prepareStatement(query);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    listCount.add(rs.getInt(1));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listCount;

    }
     
    
    public List<Integer> getRevenues() {
        List<Integer> listCount = new ArrayList<>();
        String[] listQuery = {
            """
                     SELECT    SUM(TongTien) from
                     
                     (SELECT SUM(HOADONCHITIET.TongTien) as TongTien 
                                         FROM HOADON
                                         JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                                         where HOADON.TinhTrang = 1 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-1,GETDATE()))					
                     					group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang ) x
            """,
            """
                     SELECT    SUM(TongTien) from
                     
                     (SELECT SUM(HOADONCHITIET.TongTien) as TongTien 
                                         FROM HOADON
                                         JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                                         where HOADON.TinhTrang = 1 and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-30,GETDATE()))					
                     					group by HOADON.MaNhanVien, HOADON.MaHoaDon, HOADON.MaKhachHang,
                     					HOADON.NgayBan,  HOADON.TinhTrang ) x
            """,};

        Connection cn = DBContext1.getConnection();

        for (String query : listQuery) {
            try {
                PreparedStatement pstm = cn.prepareStatement(query);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    listCount.add(rs.getInt(1));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listCount;

    }

    public List<Revenue> getRevenueStatistic(String time, String trending, String color, String material) {
        List<Revenue> revenues = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        boolean hasColor = false;
        boolean hasMaterial = false;
        String whereQuery = "";
        String orderByQuery = "order by SUM(HOADONCHITIET.soLuong)";
        String paginateQuery = "offset ? ROWS fetch next ? ROWS ONLY";
        if (time.equals("Ngày")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-1,GETDATE()))";
        }
        if (time.equals("Tuần")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-7,GETDATE()))";
        }

        if (time.equals("Tháng")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-30,GETDATE()))";
        }

        if (trending.equals("Bán chạy")) {
            orderByQuery += " desc";
        }

        for (mauSac cl : LIST_COLOR) {
            if (cl.getTenMau().equals(color)) {
                hasColor = true;
                whereQuery += " " + "and tenmau = ?";
            }
        }

        for (chatLieu mt : LIST_MATERIAL) {
            if (mt.getTenChatLieu().equals(material)) {
                hasMaterial = true;
                whereQuery += " " + "and tenchatlieu = ?";
            }
        }

        String query = " select SPCHITIET.MaSPChiTiet,SPCHITIET.MaSanPham,SUM(HOADONCHITIET.soLuong) as SoLuongBan,SPCHITIET.TenSanPham  ,TenChatLieu,TenMau,SPCHITIET.SoLuong\n"
                + "                                                        from SPCHITIET\n"
                + "                                                        inner join HOADONCHITIET on HOADONCHITIET.MaSPChiTiet = SPCHITIET.MaSPChiTiet\n"
                + "                                                        inner join Hoadon on hoadon.mahoadon = HOADONCHITIET.MaHoaDon\n"
                + "                                                        inner join SANPHAM on sanpham.MaSanPham = SPCHITIET.MaSanPham\n"
                + "                                                        inner join CHATLIEU on SPCHITIET.MaChatLieu = chatlieu.MaChatLieu\n"
                + "                                                        inner join MAUSAC  on mausac.MaMau = SPCHITIET.MaMau\n"
                + "                                                        where HOADON.TinhTrang = 1 " + " " + whereQuery + "\n"
                + "                                                        group by SPCHITIET.MaSPChiTiet,SPCHITIET.MaSanPham,SPCHITIET.SoLuong,SPCHITIET.TenSanPham,TenChatLieu,TenMau\n" + orderByQuery + "\n" + paginateQuery;

      
        Connection cn = DBContext1.getConnection();

        try {
            PreparedStatement pstm = cn.prepareStatement(query);
            int index = 1;
            if (hasColor) {
                pstm.setString(index, color);
                index++;
            }
            if (hasMaterial) {
                pstm.setString(index, material);
                index++;
            }
            pstm.setInt(index, offset);
            pstm.setInt(index + 1, pageSize);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Revenue revenue = new Revenue();
                revenue.setProductDetailCode(rs.getString(1));
                revenue.setProductCode(rs.getString(2));
                revenue.setSold(rs.getInt(3));
                revenue.setProductName(rs.getString(4));
                revenue.setMaterialName(rs.getString(5));
                revenue.setColorName(rs.getString(6));
                revenue.setNumberRemain(rs.getInt(7));
                revenues.add(revenue);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenues;

    }

    public Integer getCountProductRevenue(String time, String trending, String color, String material) {
        Integer count = 0;
        boolean hasColor = false;
        boolean hasMaterial = false;
        String whereQuery = "";
        if (time.equals("Ngày")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-1,GETDATE()))";
        }
        if (time.equals("Tuần")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-7,GETDATE()))";
        }

        if (time.equals("Tháng")) {
            whereQuery += "and  ngayban < (DATEADD(day,0, GETDATE())) and ngayban > (DATEADD(day,-30,GETDATE()))";
        }

        for (mauSac cl : LIST_COLOR) {
            if (cl.getTenMau().equals(color)) {
                hasColor = true;
                whereQuery += " " + "and tenmau = ?";
            }
        }

        for (chatLieu mt : LIST_MATERIAL) {
            if (mt.getTenChatLieu().equals(material)) {
                hasMaterial = true;
                whereQuery += " " + "and tenchatlieu = ?";
            }
        }

        String query = """
                       select count(maspchitiet) from 
                        (select SPCHITIET.MaSPChiTiet,SPCHITIET.MaSanPham,SUM(HOADONCHITIET.soLuong) as SoLuongBan,SPCHITIET.TenSanPham  ,TenChatLieu,TenMau,SPCHITIET.SoLuong
                                                                               from SPCHITIET
                                                                               inner join HOADONCHITIET on HOADONCHITIET.MaSPChiTiet = SPCHITIET.MaSPChiTiet
                                                                               inner join Hoadon on hoadon.mahoadon = HOADONCHITIET.MaHoaDon
                                                                               inner join SANPHAM on sanpham.MaSanPham = SPCHITIET.MaSanPham
                                                                               inner join CHATLIEU on SPCHITIET.MaChatLieu = chatlieu.MaChatLieu
                                                                               inner join MAUSAC  on mausac.MaMau = SPCHITIET.MaMau
                                                                               where HOADON.TinhTrang = 1 """ + " " + whereQuery + "\n"
                + "                                                        group by SPCHITIET.MaSPChiTiet,SPCHITIET.MaSanPham,SPCHITIET.SoLuong,SPCHITIET.TenSanPham,TenChatLieu,TenMau)x\n";

      
        Connection cn = DBContext1.getConnection();

        try {
            PreparedStatement pstm = cn.prepareStatement(query);
            int index = 1;
            if (hasColor) {
                pstm.setString(index, color);
                index++;
            }
            if (hasMaterial) {
                pstm.setString(index, material);
                index++;
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;

    }

    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
        String sql = """
                 SELECT HOADONCHITIET.MaHoaDonCT, HOADONCHITIET.MaSPChiTiet, SPChiTiet.TenSanPham,
                     MAUSAC.TenMau, KICHTHUOC.TenKichThuoc, CHATLIEU.TenChatLieu, 
                     SPCHITIET.Gia,
                     HOADONCHITIET.soLuong, HOADONCHITIET.TongTien
                 FROM HOADONCHITIET
                 JOIN HOADON ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                 JOIN SPCHITIET ON HOADONCHITIET.MaSPChiTiet = SPCHITIET.MaSPChiTiet
                 JOIN MAUSAC ON SPCHITIET.MaMau = MAUSAC.MaMau
                 JOIN KICHTHUOC ON SPCHITIET.MaKichThuoc = KICHTHUOC.MaKichThuoc
                 JOIN CHATLIEU ON SPCHITIET.MaChatLieu = CHATLIEU.MaChatLieu
                 WHERE HOADON.MaHoaDon = ?;
                     """;

        try (Connection cn = DBContext1.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonChiTiet> list = new ArrayList<>();

            while (rs.next()) {
                HoaDonChiTiet ql = new HoaDonChiTiet();
                ql.setMaHDCT(rs.getInt(1));
                ql.setMaSPCT(rs.getString(2));
                ql.setTenSP(rs.getString(3));
                ql.setMauSac(rs.getString(4));
                ql.setKichThuoc(rs.getString(5));
                ql.setChatLieu(rs.getString(6));
                ql.setGia(rs.getDouble(7));
                ql.setSoLuong(rs.getInt(8));
                ql.setTongTien(rs.getDouble(9));

                list.add(ql);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
