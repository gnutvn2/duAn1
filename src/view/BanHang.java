/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Interface.chatLieuService;
import Interface.kichThuocService;
import Interface.mauSacService;
import Interface.sanPhamCTService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.HDModel;
import model.SanPhamTrang;
import service.DBContext1;
import service.HoaDonService;
import service.SanPhamService_HoaDon;
import java.sql.Connection;
import java.sql.Statement;
import model.HoaDonChiTiet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import model.QuanLyHoaDon;
import model.chatLieu;
import model.kichThuoc;
import model.mauSac;
import model.sanPhamChiTiet;
import service.QLHoaDonService;
import service.chatLieuServiceImple;
import service.kichThuocServiceImple;
import service.mauSacServiceImple;
import service.sanPhamCTServiceImpl;
import static view.ManChinh.tenDN;

public class BanHang extends javax.swing.JFrame {

    /**
     * Creates new form BanHang
     */
    private int index = -1;

    private DefaultTableModel mol = new DefaultTableModel();
    private List<SanPhamTrang> listSP = new ArrayList<>();
    private List<HDModel> listHD = new ArrayList<>();
    private SanPhamService_HoaDon SPService = new SanPhamService_HoaDon();
    private HoaDonService HDService = new HoaDonService();
    private QLHoaDonService serviceHD = new QLHoaDonService();
    private sanPhamCTService service = new sanPhamCTServiceImpl();
    private chatLieuService clservice = new chatLieuServiceImple();
    private kichThuocService ktservice = new kichThuocServiceImple();
    private mauSacService msservice = new mauSacServiceImple();
    int pageSize = 5;
    int pageNumber = 1;
    int maxPageHD = 0;
    int maxPageSpct = 0;
    private String maSP;
    private String mauSac = "";
    private String kichThuoc = "";
    private String chatLieu = "";
    private String makh;

    SanPhamTrang sp = new SanPhamTrang();

    public BanHang() {
        initComponents();
        // khai báo cho sản phẩm 
        fillTableSanPham();
        int count = service.getCountFilter(mauSac, kichThuoc, chatLieu);
        setMaxPage(count);

        // khai báo cho hóa đơn
        fillTableHD();

        int countHD = HDService.getCount();
        setMaxPageHD(countHD);
        this.cboLocSP();

    }

    public void setText(String text) {
        txtMaKhachHang.setText(text);
    }

    public void setMaxPage(int count) {
//        System.out.println("so ban ghi : " + count);
        if (count > 0) {
            int number = count % pageSize;
            if (number > 0) {
                maxPageSpct = Math.round(count / pageSize) + 1;
            } else if (number == 0) {
                maxPageSpct = count / pageSize;
            } else {
                maxPageSpct = 1;
            }
            lbl_maxPage.setText(Integer.toString(maxPageSpct));
        }
    }

    public void setMaxPageHD(int count) {
//        System.out.println(count);
        if (count > 0) {
            int number = count % pageSize;
            if (number > 0) {
                maxPageHD = Math.round(count / pageSize) + 1;
            } else if (number == 0) {
                maxPageHD = count / pageSize;
            } else {
                maxPageHD = 1;
            }
            lbl_maxPageHoaDon.setText(Integer.toString(maxPageHD));
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

        for (chatLieu cl : clservice.getAll(pageNumber, pageSize)) {
            String tenChatLieu = cl.getTenChatLieu();
            if (!uniqueChatLieu.contains(tenChatLieu)) {
                cboChatLieuLoc.addItem(tenChatLieu);
                uniqueChatLieu.add(tenChatLieu);
            }
        }
        for (kichThuoc kt : ktservice.getAll(pageNumber, pageSize)) {
            String tenKichThuoc = kt.getTenKichThuoc();
            if (!uniqueKichThuoc.contains(tenKichThuoc)) {
                cboKichThuocLoc.addItem(tenKichThuoc);
                uniqueKichThuoc.add(tenKichThuoc);
            }
        }
        for (mauSac ms : msservice.getAll(pageNumber, pageSize)) {
            String tenMau = ms.getTenMau();
            if (!uniqueMauSac.contains(tenMau)) {
                cboMauSacLoc.addItem(tenMau);
                uniqueMauSac.add(tenMau);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void fillTableSPCT(List<sanPhamChiTiet> list) {
        System.out.println(list);
        mol = (DefaultTableModel) tblDSSP.getModel();
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
                spct.getChatLieu(),
                spct.getTenKM(), spct.getMucGiamGia(), spct.getNgayKetThuc()
            };
            mol.addRow(rowData);
        }
    }

    public void resetForm() {
        mol = (DefaultTableModel) tblGioHang.getModel();
        txtMaKhachHang.setText("");
        txtTongTien.setText("");
        mol.setRowCount(0);
        txtTienKhachDua.setText("");
        txtTienThua.setText("");
    }

    public void fillTableSanPham() {
        List<sanPhamChiTiet> list = service.getByMaSPCT(pageNumber, pageSize, mauSac, kichThuoc, chatLieu);
        mol = (DefaultTableModel) tblDSSP.getModel();
        mol.setRowCount(0);
        for (sanPhamChiTiet sp : list) {
            mol.addRow(new Object[]{sp.getMaSPCT(), sp.getMaSP(), sp.getTenSP(), sp.getGia(), sp.getSoLuong(),
                sp.getChatLieu(), sp.getKichThuoc(), sp.getMauSac(), sp.getTenKM(), sp.getMucGiamGia(), sp.getNgayKetThuc()});
        }
    }

    public void fillTableHD() {
        mol = (DefaultTableModel) tblDSHD.getModel();
        mol.setRowCount(0);
        List<HDModel> list = HDService.getAllPhanTrang(pageNumber, pageSize);
        for (HDModel hd : list) {
            String tinhTrang = hd.isTinhTrang() == true ? "Đã thanh toán " : "Chưa thanh toán";
            Object[] ob2 = {
                hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayBan(), tinhTrang
            };
            mol.addRow(ob2);
        }
    }

    public void showHoaDonChiTiet(int maHoaDon) {
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
        double thanhTien = 0;

        List<HoaDonChiTiet> list = HDService.getAllHoaDonChiTiet(maHoaDon);
        for (HoaDonChiTiet hd : list) {
            mol.addRow(new Object[]{hd.getMaHDCT(), hd.getMaSPCT(), hd.getTenSP(), hd.getChatLieu(), hd.getKichThuoc(), hd.getMauSac(), hd.getSoLuong(),
                hd.getGia(), hd.getTenKM(), (hd.getMucGiamGia() *hd.getSoLuong() ), hd.getTongTien()});
            thanhTien += hd.getTongTien();
        }
        txtTongTien.setText(Double.toString(Math.round(thanhTien)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtTim1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDSHD = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblcurrentPageHoaDon = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_maxPageHoaDon = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        lblMahoadON = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnPrievous = new javax.swing.JButton();
        btnNextSP = new javax.swing.JButton();
        lblcurrentPage = new javax.swing.JLabel();
        lbl_maxPage = new javax.swing.JLabel();
        cboMauSacLoc = new javax.swing.JComboBox<>();
        cboKichThuocLoc = new javax.swing.JComboBox<>();
        cboChatLieuLoc = new javax.swing.JComboBox<>();
        btnLocSP = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDSSP = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnTaoHoaDon = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        btnXemHoaDon = new javax.swing.JButton();
        btnThemTenKH = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblSanPham = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton5.setText("<<");

        jButton6.setText(">>");

        jLabel15.setText("Tìm");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(33, 33, 33)
                        .addComponent(jButton6)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtTim1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTim1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(10, 10, 10))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

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
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));

        tblDSHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HD", "Mã Nhân Viên", "Mã Khách Hàng", "Ngày Bán", "Tình Trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDSHD);

        btnBack.setText("<<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblcurrentPageHoaDon.setText("1");

        jLabel8.setText("/");

        lbl_maxPageHoaDon.setText("3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblcurrentPageHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_maxPageHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnNext))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcurrentPageHoaDon)
                    .addComponent(jLabel8)
                    .addComponent(lbl_maxPageHoaDon))
                .addGap(44, 44, 44))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HDCT", "Mã SP", "Tên SP", "Chất liệu", "Kích Thước", "Màu Sắc", "Số Lượng", "Giá", "Tên CT", "Giảm giá", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_6861358 (2).png"))); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel16.setText("Mã hóa đơn:");

        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(455, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMahoadON, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(30, 30, 30))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel16))
                        .addComponent(lblMahoadON, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSua)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm"));

        btnPrievous.setText("<<");
        btnPrievous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrievousActionPerformed(evt);
            }
        });

        btnNextSP.setText(">>");
        btnNextSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextSPActionPerformed(evt);
            }
        });

        lblcurrentPage.setText("1   /");

        lbl_maxPage.setText("3");

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

        btnLocSP.setText("Lọc sản phẩm");
        btnLocSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocSPActionPerformed(evt);
            }
        });

        tblDSSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng", "Màu sắc", "Kích thước", "Chất liệu", "Tên KM", "Giảm giá", "Thời gian KT "
            }
        ));
        tblDSSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSSPMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDSSP);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(281, 281, 281)
                .addComponent(cboMauSacLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(cboKichThuocLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboChatLieuLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnLocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(btnPrievous, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblcurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_maxPage, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnNextSP, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMauSacLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKichThuocLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChatLieuLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocSP))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrievous)
                    .addComponent(btnNextSP)
                    .addComponent(lblcurrentPage)
                    .addComponent(lbl_maxPage))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh toán"));

        jLabel20.setText("Tổng tiền");

        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });

        jLabel21.setText("Tiền khách đưa");

        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });

        jLabel22.setText("Tiền thừa");

        txtTienThua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienThuaActionPerformed(evt);
            }
        });

        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setText("TẠO HÓA ĐƠN");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        jLabel7.setText("Mã Khách Hàng ");

        txtMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachHangActionPerformed(evt);
            }
        });

        btnXemHoaDon.setText("XEM HÓA ĐƠN");
        btnXemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemHoaDonActionPerformed(evt);
            }
        });

        btnThemTenKH.setText("+");
        btnThemTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTenKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThemTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(55, 55, 55)
                        .addComponent(txtTongTien))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienThua)
                            .addComponent(txtTienKhachDua)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXemHoaDon)
                .addGap(85, 85, 85))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemTenKH))
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnXemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(253, 164, 158));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("THỐNG KÊ");
        jLabel6.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel6AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        lblSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSanPham.setText("SẢN PHẨM");
        lblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("NHÂN VIÊN");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("BÁN HÀNG");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("KHÁCH HÀNG");
        jLabel11.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel11AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("KHUYẾN MẠI");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("ĐĂNG XUẤT");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("HÓA ĐƠN");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblSanPham)
                                .addComponent(jLabel6))
                            .addComponent(jLabel10)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel6)
                .addGap(30, 30, 30)
                .addComponent(lblSanPham)
                .addGap(29, 29, 29)
                .addComponent(jLabel9)
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addGap(30, 30, 30)
                .addComponent(jLabel17)
                .addGap(29, 29, 29)
                .addComponent(jLabel11)
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addGap(32, 32, 32)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed

        HDModel hd = new HDModel();

        if (HDService.add(tenDN)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
        fillTableHD();

    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        if (pageNumber > 1) {
            pageNumber--;
            lblcurrentPageHoaDon.setText(Integer.toString(pageNumber));
            fillTableHD();
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if (pageNumber >= maxPageHD) {
            return;
        }
        pageNumber++;
        lblcurrentPageHoaDon.setText(Integer.toString(pageNumber));
        fillTableHD();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrievousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrievousActionPerformed
        // TODO add your handling code here:
        if (pageNumber > 1) {
            pageNumber--;
            lblcurrentPage.setText(Integer.toString(pageNumber));
            this.fillTableSPCT(service.getByMaSPCT(pageNumber, pageSize, mauSac, kichThuoc, chatLieu));

        }
    }//GEN-LAST:event_btnPrievousActionPerformed

    private void btnNextSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSPActionPerformed
        // TODO add your handling code here:
        System.out.println("max page : " + maxPageSpct);
        System.out.println(" pageNumber : " + pageNumber);
        if (pageNumber >= maxPageSpct) {
            return;
        }
        pageNumber++;
        lblcurrentPage.setText(Integer.toString(pageNumber));

        this.fillTableSPCT(service.getByMaSPCT(pageNumber, pageSize, mauSac, kichThuoc, chatLieu));
    }//GEN-LAST:event_btnNextSPActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
//        int selectRow = 0;
//        int soLuong = (int) tblDSSP.getValueAt(selectRow, 3);
//        int row = tblGioHang.getSelectedRow();


    }//GEN-LAST:event_tblGioHangMouseClicked

    private void tblDSHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSHDMouseClicked
        // TODO add your handling code here:
        index = tblDSHD.getSelectedRow();

        int mahoaDon = (int) tblDSHD.getValueAt(index, 0);
        lblMahoadON.setText(Integer.toString(mahoaDon));
        System.out.println(mahoaDon);
        showHoaDonChiTiet(mahoaDon);
    }//GEN-LAST:event_tblDSHDMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        int rows = tblGioHang.getRowCount();
        List<sanPhamChiTiet> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            list.add(new sanPhamChiTiet((String) tblGioHang.getValueAt(i, 1),
                    (int) tblGioHang.getValueAt(i, 6)));
        }

        try {
            double tienKhDua = Double.parseDouble(txtTienKhachDua.getText().trim());
            if (tienKhDua <= 0) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa phải lớn hơn 0!");
            } else {
                int selectedRow = tblDSHD.getSelectedRow();
                if (selectedRow != -1) {
                    // Thay đổi giá trị cột "Tình trạng" của dòng được chọn thành "đã thanh toán"
                    tblDSHD.setValueAt("Đã thanh toán", selectedRow, 4);

                    try (Connection con = DBContext1.getConnection(); Statement stmt = con.createStatement()) {
                        for (sanPhamChiTiet sp : list) {
                            String updateQuery = "UPDATE SPCHITIET SET SoLuong = SoLuong - " + sp.getSoLuong() + " WHERE MaSPChiTiet =  '" + sp.getMaSPCT() + "'";
                            stmt.executeUpdate(updateQuery);
                        }
                        // Lấy giá trị MaHoaDon 
                        String makh = txtMaKhachHang.getText();
                        Object maHoaDon = tblDSHD.getValueAt(selectedRow, 0); // hoặc indexOfMaHoaDonColumn
                        String updateQuery = "UPDATE HOADON SET TinhTrang = 1 , " + " makhachhang = ?" + " " + " WHERE MaHoaDon = '" + maHoaDon + "'";
                        System.out.println("query : " + updateQuery);
                        PreparedStatement pstm = con.prepareStatement(updateQuery);
                        pstm.setString(1, makh);
                        int row = pstm.executeUpdate();
                        if (row > 0) {
                            resetForm();
                            JOptionPane.showMessageDialog(this, "Thanh toán thành công ! ");
                            fillTableHD();
                            fillTableSanPham();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa hợp lệ!");
        }


    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:

        try {
            double soTienKhachDua = Math.round(Double.parseDouble(txtTienKhachDua.getText()));
            if (soTienKhachDua >= 0) { // Kiểm tra số tiền không âm
                double tongTien = Math.round(Double.parseDouble(txtTongTien.getText()));
                double tienThua = soTienKhachDua - tongTien;
                txtTienThua.setText(String.valueOf(tienThua));
            } else {
                JOptionPane.showMessageDialog(null, "Số tiền khách đưa không hợp lệ!");
                // Xử lý khi số tiền không âm
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số!");
            // Xử lý khi nhập không hợp lệ
        }


    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void txtMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachHangActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:

        try {
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Vui long chon dong can xoa");
                return;
            }
            int cf = JOptionPane.showConfirmDialog(this, "Ban co muon xoa khong");
            if (cf != JOptionPane.YES_OPTION) {
                return;
            }
            index = tblGioHang.getSelectedRow();
            int mahoaDon = (int) tblGioHang.getValueAt(index, 0);
            if (HDService.deleteGioHang(Integer.toString(mahoaDon)) > 0) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                showHoaDonChiTiet(Integer.parseInt(lblMahoadON.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Xoa that bai");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTongTienActionPerformed

    private void txtTienThuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienThuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienThuaActionPerformed

    private void jLabel6AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel6AncestorAdded

    }//GEN-LAST:event_jLabel6AncestorAdded

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        ThongKe tk = new ThongKe();
        this.dispose();
        tk.setVisible(true);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void lblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseClicked
        // TODO add your handling code here:
        sanPhamView sp = new sanPhamView();
        this.dispose();
        sp.setVisible(true);
    }//GEN-LAST:event_lblSanPhamMouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        NhanVien nv = new NhanVien();
        this.dispose();
        nv.setVisible(true);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        BanHang hd = new BanHang();
        this.dispose();
        hd.setVisible(true);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel11AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11AncestorAdded

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        KhachHang kh = new KhachHang();
        this.dispose();
        kh.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        KhuyenMaiView km = new KhuyenMaiView();
        this.dispose();
        km.setVisible(true);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
        QLHoaDon qlhd = new QLHoaDon();
        this.dispose();
        qlhd.setVisible(true);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void btnXemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemHoaDonActionPerformed
        // TODO add your handling code here:

        new QLHoaDon().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnXemHoaDonActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:

        int selectedRow = tblGioHang.getSelectedRow();
        if (selectedRow != -1) {
            // Xử lý chỉnh sửa số lượng ở đây
            String input = JOptionPane.showInputDialog(null, "Nhập số lượng mới:");
            if (input != null && !input.isEmpty()) {
                try {
                    int soLuongMoi = Integer.parseInt(input);
                    if (soLuongMoi >= 0) { // Kiểm tra số lượng mới không âm
                        int mahd = Integer.parseInt(tblGioHang.getValueAt(selectedRow, 0).toString());
                        QuanLyHoaDon ql = new QuanLyHoaDon();
                        ql.setSoLuong(soLuongMoi);
                        ql.setMaHoaDon(mahd);
                        if (serviceHD.updateSoLuongGioHang(ql) != null) {
                            JOptionPane.showMessageDialog(this, "Sửa thành công");
                            index = tblDSHD.getSelectedRow();
                            int mahoaDon = (int) tblDSHD.getValueAt(index, 0);
                            showHoaDonChiTiet(mahoaDon);
                        } else {
                            JOptionPane.showMessageDialog(this, "Sửa thất bại");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng không nhập số không âm!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để sửa số lượng.");
        }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void cboMauSacLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacLocActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMauSacLocActionPerformed

    private void cboKichThuocLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichThuocLocActionPerformed

    private void cboChatLieuLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieuLocActionPerformed

    private void btnLocSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocSPActionPerformed

        try {

            chatLieu = !cboChatLieuLoc.getSelectedItem().equals("Tất cả") ? cboChatLieuLoc.getItemAt(cboChatLieuLoc.getSelectedIndex()) : "";
            mauSac = !cboMauSacLoc.getSelectedItem().equals("Tất cả") ? cboMauSacLoc.getItemAt(cboMauSacLoc.getSelectedIndex()) : "";
            kichThuoc = !cboKichThuocLoc.getSelectedItem().equals("Tất cả") ? cboKichThuocLoc.getItemAt(cboKichThuocLoc.getSelectedIndex()) : "";
            List<sanPhamChiTiet> list = service.locSP(mauSac, kichThuoc, chatLieu, pageSize);
            int countDocuemnt = service.getCountFilter(mauSac, kichThuoc, chatLieu);
            if (list != null && !list.isEmpty()) {
                pageNumber = 1;
                lblcurrentPage.setText(Integer.toString(pageNumber));
                setMaxPage(countDocuemnt);
                this.fillTableSPCT(list);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi lọc sản phẩm");
        }
    }//GEN-LAST:event_btnLocSPActionPerformed

    private void tblDSSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSSPMouseClicked
        // TODO add your handling code here:

        int selectRow = tblDSSP.getSelectedRow();
        int row = tblGioHang.getRowCount();
        System.out.println(row);
        if (selectRow != -1) {
            // Lấy số lượng hiện có từ bảng sản phẩm
            int soLuongHienCo = (int) tblDSSP.getValueAt(selectRow, 4);

            int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm vào giỏ hàng không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                String maSPCT = (String) tblDSSP.getValueAt(selectRow, 0);
                double gia = (double) tblDSSP.getValueAt(selectRow, 3);
                double giamGia = (double) tblDSSP.getValueAt(selectRow, 9);

                String soLuongStr = JOptionPane.showInputDialog(null, "Nhập số lượng cần mua:");
                int soLuongNhap = Integer.parseInt(soLuongStr);

                if (soLuongNhap > 0 && soLuongNhap <= soLuongHienCo) {
                    boolean isExit = false;

                    for (int i = 0; i < row; i++) {
                        System.out.println(tblGioHang.getValueAt(i, 1));
                        System.out.println(maSPCT);
                        if (tblGioHang.getValueAt(i, 1).equals(maSPCT)) {
                            isExit = true;
                            soLuongNhap += (int) tblGioHang.getValueAt(i, 6);
//                            System.out.println(soLuongNhap);
                        }

                    }
                    if (soLuongNhap > soLuongHienCo) {
                        JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ hoặc vượt quá số lượng có sẵn.");
                        return;
                    }
                    System.out.println(giamGia);

                    if (!isExit) {
                        double tongGiamGia = (giamGia * soLuongNhap);
                        double thanhTien = (soLuongNhap * gia) - tongGiamGia;
                        System.out.println("thanhTien : " + thanhTien);
                        System.out.println("soLuongNhap : " + soLuongNhap);
                        HoaDonChiTiet hd = new HoaDonChiTiet(1, Integer.parseInt(lblMahoadON.getText()), maSPCT, soLuongNhap, thanhTien,tongGiamGia);
                        if (HDService.addHoaDonChiTiet(hd)) {
                            showHoaDonChiTiet(Integer.parseInt(lblMahoadON.getText()));
                        }
                    } else {
                        if (SPService.capNhatSoLuongSPTrongSPChiTiet(maSPCT, soLuongNhap) != 0) {
                            showHoaDonChiTiet(Integer.parseInt(lblMahoadON.getText()));
                            JOptionPane.showMessageDialog(this, "cap nhat so luong thanh ");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ hoặc vượt quá số lượng có sẵn.");
                }
            }
        }

    }//GEN-LAST:event_tblDSSPMouseClicked

    private void btnThemTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTenKHActionPerformed
        // TODO add your handling code here:
        KhachHang khh = new KhachHang(this);
        khh.setVisible(true);
    }//GEN-LAST:event_btnThemTenKHActionPerformed

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
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHang().setVisible(true);
            }
        });
    }

    public void fillPhanTrang(List<model.HDModel> list) {
        mol = (DefaultTableModel) tblDSHD.getModel();
        mol.setRowCount(0);

        for (HDModel hd : list) {
            String tinhTrang = hd.isTinhTrang() == true ? "Đã thanh toán " : "Chưa thanh toán";
            Object[] ob2 = {
                hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayBan(), tinhTrang
            };
            mol.addRow(ob2);
        }

    }

    public HDModel getFromInput() {
        HDModel hd = new HDModel();
        hd.setMaKhachHang(txtMaKhachHang.getText());
        return hd;

    }

//    public void fillPhanTrangSP(List<model.SanPhamTrang> list) {
//        dtm.setRowCount(0);
//        for (SanPhamTrang sp : list) {
//            Object data[] = {sp.getMaSPCT(), sp.getTenSP(), sp.getSoLuong(), sp.getGia(), sp.getMaChatLieu(),
//                sp.getMaKichThuoc(), sp.getMaMau(), sp.getMaSP(), sp.getLoaiSP()};
//            dtm.addRow(data);
//        }
//    }
    // show hoa don

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnLocSP;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextSP;
    private javax.swing.JButton btnPrievous;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemTenKH;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboChatLieuLoc;
    private javax.swing.JComboBox<String> cboKichThuocLoc;
    private javax.swing.JComboBox<String> cboMauSacLoc;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblMahoadON;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lbl_maxPage;
    private javax.swing.JLabel lbl_maxPageHoaDon;
    private javax.swing.JLabel lblcurrentPage;
    private javax.swing.JLabel lblcurrentPageHoaDon;
    private javax.swing.JTable tblDSHD;
    private javax.swing.JTable tblDSSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTim1;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
