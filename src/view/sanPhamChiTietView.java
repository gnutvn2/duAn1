/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.chatLieu;
import model.kichThuoc;
import model.mauSac;
import model.sanPham;
import model.sanPhamChiTiet;
import Interface.chatLieuService;
import Interface.kichThuocService;
import Interface.mauSacService;
import service.chatLieuServiceImple;
import service.kichThuocServiceImple;
import service.mauSacServiceImple;
import service.sanPhamCTServiceImpl;
import Interface.sanPhamCTService;
import Interface.sanPhamService;
import service.DBContext1;
import service.sanPhamServiceImple;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class sanPhamChiTietView extends javax.swing.JFrame {

    private sanPhamCTService service = new sanPhamCTServiceImpl();
    private DefaultTableModel mol = new DefaultTableModel();
    private chatLieuService clservice = new chatLieuServiceImple();
    private kichThuocService ktservice = new kichThuocServiceImple();
    private mauSacService msservice = new mauSacServiceImple();
    private sanPhamService spservice = new sanPhamServiceImple();
    private int index = -1;
    private String maSP;
    private String mauSac = "";
    private String kichThuoc = "";
    private String chatLieu = "";
    int pageSize = 5;
    int pageNumber = 1;
    int maxPage = 0;

    /**
     * Creates new form sanPhamView
     */
    public sanPhamChiTietView(String maSP) {
        initComponents();

        this.loadComboBox();
        sanPham sp = spservice.getByMaSP(maSP);
        lblMaSP.setText(sp.getMaSP());
        lblTenSP.setText(sp.getTenSP());
        this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
        //phân trang
        int count = service.getCount(maSP);
        setMaxPage(count);
        //
        this.cboLocSP();
    }

    public void setMaxPage(int count) {
        System.out.println(count);
        if (count > 0) {
            int number = count % pageSize;
            if (number > 0) {
                maxPage = Math.round(count / pageSize) + 1;
            } else if (number == 0) {
                maxPage = 1;
            } else {
                maxPage = Math.round(count / pageSize);
            }
            lbl_maxPage.setText(Integer.toString(maxPage));
        }
    }

    public boolean validateSP() {
        String maSPCT = txtMaSPCT.getText().trim();
        String donGiaText = txtDonGia.getText().trim();
        String soLuongText = txtSoLuong.getText().trim();
        String selectedMauSac = cboMauSac.getSelectedItem().toString();
        String selectedKichThuoc = cboKichThuoc.getSelectedItem().toString();
        String selectedChatLieu = cboChatLieu.getSelectedItem().toString();

        if (maSPCT.isEmpty() || donGiaText.isEmpty() || soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập đầy đủ thông tin");
            return false;
        }
        try {
            double donGia = Double.parseDouble(donGiaText);
            int soLuong = Integer.parseInt(soLuongText);

            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá sản phẩm lớn hơn 0");
                return false;
            } else if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nhập định dạng số không hợp lệ");
            return false;
        }
    }

    private boolean MaSPCTTrung(String maSPCT) {
        String maSP = lblMaSP.getText();
        boolean isExits = false;
        for (sanPhamChiTiet sp : service.getByMaSPCT(maSP, 1, 1000, mauSac, kichThuoc, chatLieu)) {
            if (sp.getMaSPCT().equals(maSPCT)) {
                isExits = true;
                break;
            }
        }
        return isExits;
    }

    public void fillTableSPCT(List<sanPhamChiTiet> list) {
        System.out.println(list);
        mol = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        mol.setRowCount(0);
        for (sanPhamChiTiet spct : list) {
            Object[] rowData = {
                spct.getMaSPCT(),
                spct.getMaSP(),
                spct.getTenSP(),
                spct.getGia(),
                spct.getSoLuong(),
                spct.getMauSac(),
                spct.getKichThuoc(),
                spct.getChatLieu()
            };
            mol.addRow(rowData);
        }
    }

    public void loadComboBox() {
        cboMauSac.removeAllItems();
        cboKichThuoc.removeAllItems();
        cboChatLieu.removeAllItems();

        Set<String> uniqueTenSp = new HashSet<>();
        Set<String> uniqueChatLieu = new HashSet<>();
        Set<String> uniqueKichThuoc = new HashSet<>();
        Set<String> uniqueMauSac = new HashSet<>();

        for (chatLieu cl : clservice.getAll(1, 100)) {
            String tenChatLieu = cl.getTenChatLieu();
            if (!uniqueChatLieu.contains(tenChatLieu)) {
                cboChatLieu.addItem(tenChatLieu);
                uniqueChatLieu.add(tenChatLieu);
            }
        }

        for (kichThuoc kt : ktservice.getAll(1, 100)) {
            String tenKichThuoc = kt.getTenKichThuoc();
            if (!uniqueKichThuoc.contains(tenKichThuoc)) {
                cboKichThuoc.addItem(tenKichThuoc);
                uniqueKichThuoc.add(tenKichThuoc);
            }
        }

        for (mauSac ms : msservice.getAll(1, 100)) {
            String tenMau = ms.getTenMau();
            if (!uniqueMauSac.contains(tenMau)) {
                cboMauSac.addItem(tenMau);
                uniqueMauSac.add(tenMau);
            }
        }
    }

    public void reload() {
        cboLocSP();
        cboMauSac.removeAllItems();
        cboKichThuoc.removeAllItems();
        cboChatLieu.removeAllItems();

        Set<String> uniqueChatLieu = new HashSet<>();
        Set<String> uniqueKichThuoc = new HashSet<>();
        Set<String> uniqueMauSac = new HashSet<>();

        for (chatLieu cl : clservice.getAll(1, 100)) {
            String tenChatLieu = cl.getTenChatLieu();
            if (!uniqueChatLieu.contains(tenChatLieu)) {
                cboChatLieu.addItem(tenChatLieu);
                uniqueChatLieu.add(tenChatLieu);
            }
        }
        for (kichThuoc kt : ktservice.getAll(1, 100)) {
            String tenKichThuoc = kt.getTenKichThuoc();
            if (!uniqueKichThuoc.contains(tenKichThuoc)) {
                cboKichThuoc.addItem(tenKichThuoc);
                uniqueKichThuoc.add(tenKichThuoc);
            }
        }
        for (mauSac ms : msservice.getAll(1, 100)) {
            String tenMau = ms.getTenMau();
            if (!uniqueMauSac.contains(tenMau)) {
                cboMauSac.addItem(tenMau);
                uniqueMauSac.add(tenMau);
            }
        }
    }

    public void cboLocSP() {
        cboMauSacLoc.removeAllItems();
        cboKichThuocLoc.removeAllItems();
        cboChatLieuLoc.removeAllItems();

        Set<String> uniqueChatLieu = new HashSet<>();
        Set<String> uniqueKichThuoc = new HashSet<>();
        Set<String> uniqueMauSac = new HashSet<>();

        cboChatLieuLoc.addItem("Tất cả");
        cboMauSacLoc.addItem("Tất cả");
        cboKichThuocLoc.addItem("Tất cả");

        for (chatLieu cl : clservice.getAll(1, 100)) {
            String tenChatLieu = cl.getTenChatLieu();
            if (!uniqueChatLieu.contains(tenChatLieu)) {
                cboChatLieuLoc.addItem(tenChatLieu);
                uniqueChatLieu.add(tenChatLieu);
            }
        }
        for (kichThuoc kt : ktservice.getAll(1, 100)) {
            String tenKichThuoc = kt.getTenKichThuoc();
            if (!uniqueKichThuoc.contains(tenKichThuoc)) {
                cboKichThuocLoc.addItem(tenKichThuoc);
                uniqueKichThuoc.add(tenKichThuoc);
            }
        }
        for (mauSac ms : msservice.getAll(1, 100)) {
            String tenMau = ms.getTenMau();
            if (!uniqueMauSac.contains(tenMau)) {
                cboMauSacLoc.addItem(tenMau);
                uniqueMauSac.add(tenMau);
            }
        }
    }

    public void showSPCT(int index) {
        index = tblSanPhamChiTiet.getSelectedRow();
        if (index >= 0 && index < tblSanPhamChiTiet.getRowCount()) {
            txtMaSPCT.setText(getCellValue(index, 0));
            lblMaSP.setText(getCellValue(index, 1));
            lblTenSP.setText(getCellValue(index, 2));
            txtDonGia.setText(getCellValue(index, 3));
            txtSoLuong.setText(getCellValue(index, 4));
            cboMauSac.setSelectedItem(getCellValue(index, 5));
            cboKichThuoc.setSelectedItem(getCellValue(index, 6));
            cboChatLieu.setSelectedItem(getCellValue(index, 7));
        }
    }

// Hàm hỗ trợ lấy giá trị từ ô trong bảng
    private String getCellValue(int row, int col) {
        Object value = tblSanPhamChiTiet.getValueAt(row, col);
        return value != null ? value.toString() : "";
    }

    public void clearSPCT() {
        txtMaSPCT.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        cboMauSac.setSelectedItem("Đen");
        cboKichThuoc.setSelectedItem("S");
        cboChatLieu.setSelectedItem("Vải cotton");
    }

    private void addSPCT() {
        if (!validateSP()) {
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại");
            return;
        }

        sanPhamChiTiet sp = new sanPhamChiTiet();
        String maSPCT = txtMaSPCT.getText();
        String maSP = lblMaSP.getText();
        String tenSP = lblTenSP.getText();
        double donGia = Double.parseDouble(txtDonGia.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        String mauSac = cboMauSac.getSelectedItem().toString();
        String kichThuoc = cboKichThuoc.getSelectedItem().toString();
        String chatLieu = cboChatLieu.getSelectedItem().toString();
        System.out.println(MaSPCTTrung(maSPCT));
        if (MaSPCTTrung(maSPCT)) {
            JOptionPane.showMessageDialog(this, "Trung ma !");

        } else {
            sp.setMaSPCT(maSPCT);
            sp.setMaSP(maSP);
            sp.setTenSP(tenSP);
            sp.setGia(donGia);
            sp.setSoLuong(soLuong);
            sp.setMauSac(mauSac);
            sp.setKichThuoc(kichThuoc);
            sp.setChatLieu(chatLieu);
            if (validateSP()) {
                service.addSPCT(sp);

                JOptionPane.showMessageDialog(this, "Thêm sp thành công !");
                this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sp thất bại !");
            }
        }
    }

    public void updateSPCT() {
        if (validateSP()) {
            String maSPCT = txtMaSPCT.getText();
            String maSP = lblMaSP.getText();
            String tenSP = lblTenSP.getText();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String mauSac = cboMauSac.getSelectedItem().toString();
            String kichThuoc = cboKichThuoc.getSelectedItem().toString();
            String chatLieu = cboChatLieu.getSelectedItem().toString();

            sanPhamChiTiet spct = new sanPhamChiTiet();
            spct.setMaSPCT(maSPCT);
            spct.setMaSP(maSP);
            spct.setTenSP(tenSP);
            spct.setGia(donGia);
            spct.setSoLuong(soLuong);
            spct.setMauSac(mauSac);
            spct.setKichThuoc(kichThuoc);
            spct.setChatLieu(chatLieu);
            try {
                service.update(spct);
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công");
                // Cập nhật danh sách sau khi thêm sản phẩm
//                service.getByMaSPCT(maSP, pageNumber, pageSize);
                this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabsSanPham = new javax.swing.JTabbedPane();
        tabs2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboKichThuoc = new javax.swing.JComboBox<>();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnUpdateSPCT = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnThemMauMoi = new javax.swing.JButton();
        btnThemKichThuocMoi = new javax.swing.JButton();
        btnThemChatLieuMoi = new javax.swing.JButton();
        lblTenSP = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        btnLocSP = new javax.swing.JButton();
        cboMauSacLoc = new javax.swing.JComboBox<>();
        cboKichThuocLoc = new javax.swing.JComboBox<>();
        cboChatLieuLoc = new javax.swing.JComboBox<>();
        btnFirst1 = new javax.swing.JButton();
        lbl_maxPage = new javax.swing.JLabel();
        lbl_currentPage = new javax.swing.JLabel();
        btnLast1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý sản phẩm"));

        jLabel2.setText("Mã sản phẩm chi tiết");

        jLabel3.setText("Tên sản phẩm");

        jLabel5.setText("Đơn giá");

        jLabel6.setText("Số lượng");

        jLabel7.setText("Màu sắc");

        jLabel8.setText("Kích thước");

        jLabel9.setText("Chất liệu");

        jLabel4.setText("Mã sản phẩm");

        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnUpdateSPCT.setText("Sửa");
        btnUpdateSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSPCTActionPerformed(evt);
            }
        });

        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnThemMauMoi.setText("+");
        btnThemMauMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMauMoiActionPerformed(evt);
            }
        });

        btnThemKichThuocMoi.setText("+");
        btnThemKichThuocMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKichThuocMoiActionPerformed(evt);
            }
        });

        btnThemChatLieuMoi.setText("+");
        btnThemChatLieuMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChatLieuMoiActionPerformed(evt);
            }
        });

        jButton2.setText("Quay lại sản phẩm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemChatLieuMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemKichThuocMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemMauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateSPCT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(133, 133, 133))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(btnThemMauMoi)
                                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThemKichThuocMoi))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClear)
                                    .addComponent(btnUpdateSPCT)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemChatLieuMoi)
                            .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addContainerGap(15, Short.MAX_VALUE))))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin sản phẩm"));

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

        btnLocSP.setText("Lọc sản phẩm");
        btnLocSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocSPActionPerformed(evt);
            }
        });

        cboMauSacLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboMauSacLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacLocActionPerformed(evt);
            }
        });

        cboKichThuocLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocLocActionPerformed(evt);
            }
        });

        cboChatLieuLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboMauSacLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboKichThuocLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboChatLieuLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnLocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocSP)
                    .addComponent(cboMauSacLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKichThuocLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChatLieuLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        btnFirst1.setText("<<");
        btnFirst1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst1ActionPerformed(evt);
            }
        });

        lbl_maxPage.setText("10");

        lbl_currentPage.setText("1");

        btnLast1.setText(">>");
        btnLast1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast1ActionPerformed(evt);
            }
        });

        jLabel1.setText("/");

        javax.swing.GroupLayout tabs2Layout = new javax.swing.GroupLayout(tabs2);
        tabs2.setLayout(tabs2Layout);
        tabs2Layout.setHorizontalGroup(
            tabs2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabs2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(tabs2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(tabs2Layout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addComponent(btnFirst1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(lbl_currentPage)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_maxPage, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabs2Layout.setVerticalGroup(
            tabs2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabs2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabs2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast1)
                    .addComponent(lbl_currentPage)
                    .addComponent(btnFirst1)
                    .addComponent(lbl_maxPage)
                    .addComponent(jLabel1))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tabsSanPham.addTab("Thông tin chi tiết", tabs2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabsSanPham)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsSanPham)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocSPActionPerformed
        System.out.println(pageNumber);
        System.out.println(pageSize);
        try {
            String maSP = lblMaSP.getText();
            if (maSP != null) {
                chatLieu = !cboChatLieuLoc.getSelectedItem().equals("Tất cả") ? cboChatLieuLoc.getItemAt(cboChatLieuLoc.getSelectedIndex()) : "";
                mauSac = !cboMauSacLoc.getSelectedItem().equals("Tất cả") ? cboMauSacLoc.getItemAt(cboMauSacLoc.getSelectedIndex()) : "";
                kichThuoc = !cboKichThuocLoc.getSelectedItem().equals("Tất cả") ? cboKichThuocLoc.getItemAt(cboKichThuocLoc.getSelectedIndex()) : "";
                List<sanPhamChiTiet> list = service.locSP(maSP, mauSac, kichThuoc, chatLieu);
                int countDocuemnt = service.getCountFilter(maSP, mauSac, kichThuoc, chatLieu);
                if (list != null && !list.isEmpty()) {
                    pageNumber = 1;
                    lbl_currentPage.setText(Integer.toString(pageNumber));
                    setMaxPage(countDocuemnt);
                    this.fillTableSPCT(list);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: MaSP là null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi lọc sản phẩm");
        }
    }//GEN-LAST:event_btnLocSPActionPerformed


    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
        // TODO add your handling code here:
        int index = tblSanPhamChiTiet.getSelectedRow();
        this.showSPCT(index);
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.clearSPCT();
        this.reload();
    }//GEN-LAST:event_btnClearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String maSP = lblMaSP.getText();
        this.addSPCT();
        this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUpdateSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSPCTActionPerformed
        // TODO add your handling code here:
        String maSP = lblMaSP.getText();
        this.updateSPCT();
        this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
    }//GEN-LAST:event_btnUpdateSPCTActionPerformed

    private void btnThemMauMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMauMoiActionPerformed
        // TODO add your handling code here:
        mauSacView msv = new mauSacView(this, rootPaneCheckingEnabled);
        msv.setVisible(true);
    }//GEN-LAST:event_btnThemMauMoiActionPerformed

    private void btnThemKichThuocMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKichThuocMoiActionPerformed
        // TODO add your handling code here:
        kichThuocView ktv = new kichThuocView(this, rootPaneCheckingEnabled);
        ktv.setVisible(true);
    }//GEN-LAST:event_btnThemKichThuocMoiActionPerformed

    private void btnThemChatLieuMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChatLieuMoiActionPerformed
        // TODO add your handling code here:
        chatLieuView clv = new chatLieuView(this, rootPaneCheckingEnabled);
        clv.setVisible(true);
    }//GEN-LAST:event_btnThemChatLieuMoiActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        sanPhamView spv = new sanPhamView();
        spv.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboMauSacLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacLocActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMauSacLocActionPerformed

    private void cboKichThuocLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichThuocLocActionPerformed

    private void cboChatLieuLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieuLocActionPerformed

    private void btnFirst1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst1ActionPerformed
        String maSP = lblMaSP.getText();
        if (pageNumber > 1) {
            pageNumber--;
            lbl_currentPage.setText(Integer.toString(pageNumber));
            this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));

        }
    }//GEN-LAST:event_btnFirst1ActionPerformed

    private void btnLast1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast1ActionPerformed
        String maSP = lblMaSP.getText();
        if (pageNumber >= maxPage) {
            return;
        }
        pageNumber++;
        lbl_currentPage.setText(Integer.toString(pageNumber));
        this.fillTableSPCT(service.getByMaSPCT(maSP, pageNumber, pageSize, mauSac, kichThuoc, chatLieu));

    }//GEN-LAST:event_btnLast1ActionPerformed

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
            java.util.logging.Logger.getLogger(sanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new sanPhamChiTietView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnFirst1;
    private javax.swing.JButton btnLast1;
    private javax.swing.JButton btnLocSP;
    private javax.swing.JButton btnThemChatLieuMoi;
    private javax.swing.JButton btnThemKichThuocMoi;
    private javax.swing.JButton btnThemMauMoi;
    private javax.swing.JButton btnUpdateSPCT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboChatLieuLoc;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboKichThuocLoc;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboMauSacLoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lbl_currentPage;
    private javax.swing.JLabel lbl_maxPage;
    private javax.swing.JPanel tabs2;
    private javax.swing.JTabbedPane tabsSanPham;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
