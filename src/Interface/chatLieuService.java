/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.chatLieu;

/**
 *
 * @author Admin
 */
public interface chatLieuService {
    public List<chatLieu> getAll(int pageNumber,int pageSize);
    public void addChatLieu(chatLieu cl);
    public void updateChatLieu(chatLieu cl);
    public int getCount();
}
