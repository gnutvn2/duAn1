/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import java.sql.Connection;
import java.util.List;
import model.chatLieu;
import Interface.chatLieuService;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class chatLieuServiceImple implements chatLieuService {

    private Connection conn = DBContext1.getConnection();

    @Override
    public List<chatLieu> getAll(int pageNumber, int pageSize) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<chatLieu> list = new ArrayList<>();
            String sql = "select * from CHATLIEU\n"
                    + "ORDER BY MaChatLieu\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                chatLieu cl = new chatLieu();
                cl.setMaChatLieu(rs.getString(1));
                cl.setTenChatLieu(rs.getString(2));
                list.add(cl);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addChatLieu(chatLieu cl) {
        String sql = "INSERT INTO CHATLIEU(MACHATLIEU, TENCHATLIEU) VALUES(?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, cl.getMaChatLieu());
            pstm.setString(2, cl.getTenChatLieu());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateChatLieu(chatLieu cl) {
        String sql = "UPDATE CHATLIEU SET TENCHATLIEU = ? WHERE MACHATLIEU = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, cl.getTenChatLieu());
            pstm.setString(2, cl.getMaChatLieu());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        String sql = "select COUNT(MaChatLieu) from CHATLIEU";
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
