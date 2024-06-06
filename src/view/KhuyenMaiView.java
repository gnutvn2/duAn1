/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import service.KhuyenMaiServiceImpl;
import service.KhuyenMaiSPServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Spring;
import javax.swing.table.DefaultTableModel;
import Interface.KhuyenMaiService;
import Interface.KhuyenMaiSPService;
import model.khuyenmai;
import model.KhuyenMaiSP;
import model.sanPhamChiTiet;

public class KhuyenMaiView extends javax.swing.JFrame {

    private KhuyenMaiService service = new KhuyenMaiServiceImpl();
    private KhuyenMaiSPService servicesp = new KhuyenMaiSPServiceImpl();
    DefaultTableModel mol = new DefaultTableModel();
    DefaultTableModel moldel = new DefaultTableModel();
    String maKM = "";

    public KhuyenMaiView() {
        initComponents();
        this.fillTbaleKM(service.getAll());
//        this.fillTbaleKMSP();
//        tblDSSPKM.setRowSelectionInterval(0, 0);
        tblBangDSKM.setRowSelectionInterval(0, 0);
        showKM(0);
//        loadComboBx();
    }

    private void addKM() {

        if (!checkValidateKM()) {
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mại thất bại");
            return;
        }

        khuyenmai km = new khuyenmai();
        String maKM = txtMaKM.getText();
        String tenKM = txtTenChuongTrinh1.getText();
        String ngayBatDau = txtThoiGianBatDau.getText();
        String ngayKetThuc = txtThoiGianKetThuc.getText();
        double giamGia = Double.parseDouble(txtMucGiamGia.getText());
        boolean tinhTrang;

        if (rdoDangHD.isSelected()) {
            tinhTrang = true;
        } else {
            tinhTrang = false;
        }
        if (tenKMDaTonTai(maKM)) {
            JOptionPane.showMessageDialog(this, "Khuyến mại này đã có, vui lòng không thêm trùng !");
        } else {
            khuyenmai khuyenmai = new khuyenmai();
            khuyenmai.setMaKM(maKM);
            khuyenmai.setTenKM(tenKM);
            khuyenmai.setNgayBatDau(ngayBatDau);
            khuyenmai.setNgayKetThuc(ngayKetThuc);
            khuyenmai.setGiamGia(giamGia);
            khuyenmai.setTinhTrang(tinhTrang);

            if (checkValidateKM()) {
                service.add(khuyenmai);
                JOptionPane.showMessageDialog(this, "Thêm khuyến mại thành công !");
                this.fillTbaleKM(service.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mại thất bại !");
            }
        }
    }

    private void updateKM() {
        if (!checkValidateKM()) {
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mại thất bại");
            return;
        }

        khuyenmai km = new khuyenmai();
        String maKM = txtMaKM.getText();
        String tenKM = txtTenChuongTrinh1.getText();
        String ngayBatDau = txtThoiGianBatDau.getText();
        String ngayKetThuc = txtThoiGianKetThuc.getText();
        double giamGia = Double.parseDouble(txtMucGiamGia.getText());
        boolean tinhTrang;
        if (rdoDangHD.isSelected()) {
            tinhTrang = true;
        } else {
            tinhTrang = false;
        }

        khuyenmai khuyenmai = new khuyenmai();
        khuyenmai.setMaKM(maKM);
        khuyenmai.setTenKM(tenKM);
        khuyenmai.setNgayBatDau(ngayBatDau);
        khuyenmai.setNgayKetThuc(ngayKetThuc);
        khuyenmai.setGiamGia(giamGia);
        khuyenmai.setTinhTrang(tinhTrang);

        if (checkValidateKM()) {
            service.update(khuyenmai);
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mại thành công");
            this.fillTbaleKM(service.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mại thất bại");
        }
    }

    private void deleteKhuyenMai() {
        String maKM = txtMaKM.getText();
        String tenKM = txtTenChuongTrinh1.getText();
        String ngayBatDau = txtThoiGianBatDau.getText();
        String ngayKetThuc = txtThoiGianKetThuc.getText();
        double giamGia = Double.parseDouble(txtMucGiamGia.getText());
        boolean tinhTrang;
        if (rdoDangHD.isSelected()) {
            tinhTrang = true;
        } else {
            tinhTrang = false;
        }

        khuyenmai khuyenmai = new khuyenmai();
        khuyenmai.setMaKM(maKM);
        khuyenmai.setTenKM(tenKM);
        khuyenmai.setNgayBatDau(ngayBatDau);
        khuyenmai.setNgayKetThuc(ngayKetThuc);
        khuyenmai.setGiamGia(giamGia);
        khuyenmai.setTinhTrang(tinhTrang);

        if (checkValidateKM()) {
            int xacNhanXoa = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mại không ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (xacNhanXoa == JOptionPane.YES_NO_OPTION) {
                service.delete(khuyenmai);
                JOptionPane.showMessageDialog(this, "Xóa khuyến mại thành công");
                this.fillTbaleKM(service.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Không xóa khuyến mại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa khuyến mại thất bại");
        }
    }

//    public void fillTbaleKMSP() {
//        mol = (DefaultTableModel) tblDSSPKM.getModel();
//        mol.setRowCount(0);
//        for (model.KhuyenMaiSP kmsp : servicesp.getAll()) {
//            Object[] toDataSP = new Object[]{kmsp.getMaSP(), kmsp.getTenSP(), kmsp.getKichThuoc(), kmsp.getMauSac(), kmsp.getChatLieu(), kmsp.getDonGia(), kmsp.isTrangThai() ? "Đang hoạt động" : "Ngừng hoạt động"};
//            mol.addRow(toDataSP);
//        }
//    }
    public void fillTbaleKM(List<khuyenmai> list) {
        moldel.setRowCount(0);
        moldel = (DefaultTableModel) tblBangDSKM.getModel();
        for (model.khuyenmai km : list) {
            Object[] toData = new Object[]{km.getMaKM(), km.getTenKM(), km.getNgayBatDau(), km.getNgayKetThuc(), km.getGiamGia(), km.isTinhTrang() ? "Đang hoạt động" : "Ngừng hoạt động"};
            moldel.addRow(toData);
        }
    }

    public void showKM(int index) {
        index = tblBangDSKM.getSelectedRow();
        txtMaKM.setText(tblBangDSKM.getValueAt(index, 0).toString());
        txtTenChuongTrinh1.setText(tblBangDSKM.getValueAt(index, 1).toString());
        txtMucGiamGia.setText(tblBangDSKM.getValueAt(index, 4).toString());
        txtThoiGianBatDau.setText(tblBangDSKM.getValueAt(index, 2).toString());
        txtThoiGianKetThuc.setText(tblBangDSKM.getValueAt(index, 3).toString());
        String tinhTrang = tblBangDSKM.getValueAt(index, 5).toString();
        if (!tinhTrang.equalsIgnoreCase("Ngừng hoạt động")) {
            rdoDangHD.setSelected(true);
        } else {
            rdoNgungHD.setSelected(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtMaKM = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMucGiamGia = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtThoiGianBatDau = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtThoiGianKetThuc = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        rdoDangHD = new javax.swing.JRadioButton();
        rdoNgungHD = new javax.swing.JRadioButton();
        btnLuu = new javax.swing.JButton();
        btnCapNhap = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        txtTenChuongTrinh1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBangDSKM = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblSanPham1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel2.setText("DOANH THU");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel3.setText("BÁN HÀNG");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel4.setText("SẢN PHẨM");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel5.setText("BÁO CÁO - THỐNG KÊ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(85, 85, 85)
                .addComponent(jLabel3)
                .addGap(71, 71, 71)
                .addComponent(jLabel4)
                .addGap(42, 42, 42)
                .addComponent(jLabel5)
                .addGap(164, 164, 164))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách khuyến mại"));

        jLabel18.setText("Tên chương trình");

        jLabel20.setText("Mức giảm giá");

        txtMucGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMucGiamGiaActionPerformed(evt);
            }
        });

        jLabel21.setText("Thời gian bắt đầu");

        jLabel22.setText("Thời gian kết thúc");

        jLabel23.setText("Tình trạng");

        buttonGroup1.add(rdoDangHD);
        rdoDangHD.setSelected(true);
        rdoDangHD.setText("Đang hoạt động");

        buttonGroup1.add(rdoNgungHD);
        rdoNgungHD.setText("Ngừng hoạt động");

        btnLuu.setText("LƯU");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnCapNhap.setText("CẬP NHẬP");
        btnCapNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapActionPerformed(evt);
            }
        });

        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jLabel24.setText("Mã KM");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(rdoDangHD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoNgungHD)
                                .addGap(17, 17, 17))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtThoiGianKetThuc))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel18)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMucGiamGia)
                            .addComponent(txtThoiGianBatDau)
                            .addComponent(txtTenChuongTrinh1)
                            .addComponent(txtMaKM))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenChuongTrinh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtMucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtThoiGianBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiGianKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel23)
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDangHD)
                    .addComponent(rdoNgungHD))
                .addGap(20, 20, 20)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách khuyến mại"));

        jLabel19.setText("Tìm kiếm");

        tblBangDSKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KM", "Tên KM", "Ngày bắt đầu", "Ngày kết thúc", "Giảm giá", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangDSKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangDSKMMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBangDSKM);

        jButton1.setText("Tìm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        jButton2.setText("Xem chi tiết");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jButton1)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(253, 164, 158));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("THỐNG KÊ");
        jLabel7.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel7AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        lblSanPham1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSanPham1.setText("SẢN PHẨM");
        lblSanPham1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPham1MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setText("NHÂN VIÊN");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("BÁN HÀNG");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setText("KHÁCH HÀNG");
        jLabel25.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel25AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel26.setText("KHUYẾN MẠI");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel28.setText("ĐĂNG XUẤT");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel29.setText("HÓA ĐƠN");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jLabel28))
                                .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblSanPham1)
                                .addComponent(jLabel7))
                            .addComponent(jLabel15))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel7)
                .addGap(31, 31, 31)
                .addComponent(lblSanPham1)
                .addGap(32, 32, 32)
                .addComponent(jLabel15)
                .addGap(46, 46, 46)
                .addComponent(jLabel16)
                .addGap(40, 40, 40)
                .addComponent(jLabel29)
                .addGap(37, 37, 37)
                .addComponent(jLabel25)
                .addGap(37, 37, 37)
                .addComponent(jLabel26)
                .addGap(35, 35, 35)
                .addComponent(jLabel28)
                .addContainerGap())
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblBangDSKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangDSKMMouseClicked
        // TODO add your handling code here:
        int index = tblBangDSKM.getSelectedRow();
        maKM = (String) tblBangDSKM.getValueAt(index, 0);
        this.showKM(index);
    }//GEN-LAST:event_tblBangDSKMMouseClicked

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        addKM();
        fillTbaleKM(service.getAll());
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearFrom();
        fillTbaleKM(service.getAll());
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapActionPerformed
        // TODO add your handling code here:
        updateKM();
        fillTbaleKM(service.getAll());
    }//GEN-LAST:event_btnCapNhapActionPerformed

    private void txtMucGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMucGiamGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMucGiamGiaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        System.out.println("123123");
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mol = (DefaultTableModel) tblBangDSKM.getModel();
        mol.setRowCount(0);
        for (khuyenmai km : service.timKiemKM(txtTimKiem.getText())) {
            Object[] toData = new Object[]{km.getMaKM(), km.getTenKM(), km.getNgayBatDau(), km.getNgayKetThuc(), km.getGiamGia(), km.isTinhTrang() ? "Đang hoạt động" : "Ngừng hoạt động"};
            mol.addRow(toData);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed

    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteKhuyenMai();
        clearFrom();
        fillTbaleKM(service.getAll());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        KhuyenMaiChiTietView view = new KhuyenMaiChiTietView(maKM);
        view.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel7AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel7AncestorAdded

    }//GEN-LAST:event_jLabel7AncestorAdded

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        ThongKe tk = new ThongKe();
        this.dispose();
        tk.setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void lblSanPham1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPham1MouseClicked
        // TODO add your handling code here:
        sanPhamView sp = new sanPhamView();
        this.dispose();
        sp.setVisible(true);
    }//GEN-LAST:event_lblSanPham1MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        NhanVien nv = new NhanVien();
        this.dispose();
        nv.setVisible(true);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        BanHang hd = new BanHang();
        this.dispose();
        hd.setVisible(true);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel25AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel25AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel25AncestorAdded

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        // TODO add your handling code here:
        KhachHang kh = new KhachHang();
        this.dispose();
        kh.setVisible(true);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        KhuyenMaiView km = new KhuyenMaiView();
        this.dispose();
        km.setVisible(true);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        QLHoaDon qlhd = new QLHoaDon();
        this.dispose();
        qlhd.setVisible(true);
    }//GEN-LAST:event_jLabel29MouseClicked

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
            java.util.logging.Logger.getLogger(khuyenmai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(khuyenmai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(khuyenmai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(khuyenmai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhuyenMaiView().setVisible(true);
            }
        });
    }

    void clearFrom() {
        txtMaKM.setText("");
        txtTenChuongTrinh1.setText("");
        txtThoiGianBatDau.setText("");
        txtThoiGianKetThuc.setText("");
        txtMucGiamGia.setText("");
        rdoDangHD.setSelected(true);
    }

    boolean checkValidateKM() {

        if (txtMaKM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã KM đang trống !");
            return false;
        }
        if (txtTenChuongTrinh1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên chương trình đang trống !");
            return false;
        }
        if (txtMucGiamGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mức giảm giá đang trống !");
            return false;
        }
        if (txtThoiGianBatDau.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thời gian bắt đầu đang trống !");
            return false;
        }
        if (txtThoiGianKetThuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thời gian kết thúc đang trống !");
            return false;
        }

        if (!isValidNumber(txtMucGiamGia.getText())) {
            JOptionPane.showMessageDialog(this, "Mức giảm giá phải là số và lớn hơn 0!");
            return false;
        }

        if (!isValidDateFormat(txtThoiGianBatDau.getText())) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày thời gian bắt đầu không đúng! (dd/MM/yyyy)");
            return false;
        }

        if (!isValidDateFormat(txtThoiGianKetThuc.getText())) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày thời gian kết thúc không đúng! (dd/MM/yyyy)");
            return false;
        }
        return true;
    }

    private boolean isValidNumber(String value) {

        try {
            double number = Double.parseDouble(value);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Không chấp nhận ngày tháng không hợp lệ
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean tenKMDaTonTai(String maKM) {
        for (khuyenmai km : service.getAll()) {
            if (km.getMaKM().equals(maKM)) {
                return true;
            }
        }
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhap;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSanPham1;
    private javax.swing.JRadioButton rdoDangHD;
    private javax.swing.JRadioButton rdoNgungHD;
    private javax.swing.JTable tblBangDSKM;
    private javax.swing.JTextField txtMaKM;
    private javax.swing.JTextField txtMucGiamGia;
    private javax.swing.JTextField txtTenChuongTrinh1;
    private javax.swing.JTextField txtThoiGianBatDau;
    private javax.swing.JTextField txtThoiGianKetThuc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
