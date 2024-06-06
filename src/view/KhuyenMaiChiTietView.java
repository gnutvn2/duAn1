/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import service.KhuyenMaiSPServiceImpl;
import service.KhuyenMaiServiceImpl;
import service.sanPhamCTServiceImpl;
import service.sanPhamCTServiceImpl;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KMChiTiet;
import model.sanPhamChiTiet;
import Interface.sanPhamCTService;

public class KhuyenMaiChiTietView extends javax.swing.JFrame {

    private DefaultTableModel mol = new DefaultTableModel();
    private sanPhamCTService service = new sanPhamCTServiceImpl();
    private KhuyenMaiServiceImpl serviceKMCT = new KhuyenMaiServiceImpl();
    private KhuyenMaiSPServiceImpl serviceCT = new KhuyenMaiSPServiceImpl();

    String maKMViewDetail = "";
    KMChiTiet kmct =null;
    int id;

    public KhuyenMaiChiTietView(String maKM) {

        initComponents();
        maKMViewDetail = maKM;
        fillTableSPCT();
        fillTableKMCT();
        System.out.println(maKMViewDetail);
    }

    public void fillTableSPCT() {
        mol = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        mol.setRowCount(0);
        for (sanPhamChiTiet spct : service.getAll()) {
            Object[] toDataSP = new Object[]{spct.getMaSPCT(), spct.getMaSP(), spct.getTenSP(), spct.getGia(), spct.getSoLuong(), spct.getMauSac(), spct.getKichThuoc(), spct.getChatLieu()};
            mol.addRow(toDataSP);
        }
    }

    public void fillTableKMCT() {
        mol = (DefaultTableModel) tbl_KMCT.getModel();
        mol.setRowCount(0);
        for (KMChiTiet spct : serviceKMCT.getAllKMChiTiet(maKMViewDetail)) {
            Object[] toDataSP = new Object[]{spct.getId(), spct.getTenSp(), spct.getSoLuong(), spct.getMauSac(), spct.getKichThuoc(), spct.getGia()};
            mol.addRow(toDataSP);
        }
    }

    private void deleteKMCT() {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_KMCT = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnXoaKMCT = new javax.swing.JButton();
        btnThemKMCT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Khuyến mại chi tiết"));

        tbl_KMCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên SP", "SoLuong", "MAUSAC", "KichThuoc", "Gia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_KMCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KMCTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_KMCT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng", "Màu sắc", "Kích thước", "Chất liệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamChiTiet);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2)
                    .addContainerGap()))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnXoaKMCT.setText("Xóa");
        btnXoaKMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKMCTActionPerformed(evt);
            }
        });

        btnThemKMCT.setText("Thêm");
        btnThemKMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKMCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(41, 41, 41)
                .addComponent(btnXoaKMCT)
                .addGap(31, 31, 31)
                .addComponent(btnThemKMCT)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnXoaKMCT)
                    .addComponent(btnThemKMCT))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_KMCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KMCTMouseClicked
        // TODO add your handling code here:
        int index = tbl_KMCT.getSelectedRow();
        id = (int) tbl_KMCT.getValueAt(index, 0);
    }//GEN-LAST:event_tbl_KMCTMouseClicked

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
        // TODO add your handling code here:
        int index = tblSanPhamChiTiet.getSelectedRow();
        String maSPCT = (String) tblSanPhamChiTiet.getValueAt(index, 0);
        String tenSPCT = (String) tblSanPhamChiTiet.getValueAt(index, 2);
        kmct = new KMChiTiet(maSPCT, tenSPCT);
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        KhuyenMaiView view = new KhuyenMaiView();
        view.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnXoaKMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKMCTActionPerformed
        if (serviceCT.deleteSPCT(maKMViewDetail, id) > 0) {
            JOptionPane.showMessageDialog(this, "Xoa thanh cong");
            fillTableKMCT();
        }

    }//GEN-LAST:event_btnXoaKMCTActionPerformed

    private void btnThemKMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKMCTActionPerformed
        int count = tbl_KMCT.getRowCount();
        boolean isExist = false;
        for (int i = 0; i < count; i++) {
           
            if (tbl_KMCT.getValueAt(i, 1).equals(kmct.getTenSp())) {
                isExist = true;
            }
        }

        if (isExist == false) {
            if (serviceCT.addSPCT(maKMViewDetail, kmct.getMaSPCT()) > 0) {
                JOptionPane.showMessageDialog(this, "Tao moi thanh cong");
                fillTableKMCT();

            }
        } else {
            JOptionPane.showMessageDialog(this, "SP da ton tai");
        }
    }//GEN-LAST:event_btnThemKMCTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhuyenMaiChiTietView("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemKMCT;
    private javax.swing.JButton btnXoaKMCT;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTable tbl_KMCT;
    // End of variables declaration//GEN-END:variables
}
