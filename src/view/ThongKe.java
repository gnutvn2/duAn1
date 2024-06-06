/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Interface.chatLieuService;
import Interface.kichThuocService;
import Interface.mauSacService;
import java.awt.BorderLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import model.Revenue;
import model.ValueComboBox;
import model.chatLieu;
import model.mauSac;
import org.jfree.chart.ChartFactory;
import service.QLHoaDonService;
import service.chatLieuServiceImple;
import service.kichThuocServiceImple;
import service.mauSacServiceImple;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author KTC
 */
public class ThongKe extends javax.swing.JFrame {

    QLHoaDonService billService = new QLHoaDonService();
    DefaultTableModel mol = new DefaultTableModel();
    private chatLieuService clservice = new chatLieuServiceImple();
    private mauSacService msservice = new mauSacServiceImple();

    public static String[] LIST_TIME = {"Ngày", "Tuần", "Tháng"};
    public static String[] LIST_TREND = {"Bán chạy", "Bán ế"};
    public static List<mauSac> LIST_COLOR = new ArrayList<>();
    public static List<chatLieu> LIST_MATERIAL = new ArrayList<>();
    public static int pageSize = 4;
    public static int pageNumber = 1;
    public int maxPage = 0;

    public ThongKe() {
        initComponents();
        
        List<Integer> listCountOrder = billService.getCountOrder();
        List<Integer> listRevenues = billService.getRevenues();
        LIST_COLOR = msservice.getAll(1, 100);
        LIST_MATERIAL = clservice.getAll(1, 100);

        setComboBox();

        lbl_countOrder.setText(Integer.toString(listCountOrder.get(0)));
        lbl_orderFailure.setText(Integer.toString(listCountOrder.get(1)));
        lbl_orderSuccess.setText(Integer.toString(listCountOrder.get(2)));
        lbl_orderSuccessDay.setText(Integer.toString(listCountOrder.get(3)));
        lbl_orderFailureDay.setText(Integer.toString(listCountOrder.get(4)));
        lbl_orderSuccessMonth.setText(Integer.toString(listCountOrder.get(5)));
        lbl_orderFailureMonth.setText(Integer.toString(listCountOrder.get(6)));
        lbl_revenueDay.setText(Integer.toString(listRevenues.get(0)));
        lbl_revenueMonth.setText(Integer.toString(listRevenues.get(1)));

        ValueComboBox values = getValueComboBox();
        fillTableRevenue(billService.getRevenueStatistic(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial()));
        int countDocument = billService.getCountProductRevenue(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial());
        setMaxPage(countDocument);
        System.out.println("count : " + countDocument);

    }

    public void setMaxPage(int count) {
        System.out.println(count);
        if (count > 0) {
            int number = count % pageSize;
            if (number > 0) {
                maxPage = Math.round(count / pageSize) + 1;
            } else if (number == 0) {
                maxPage = Math.round(count / pageSize);
            } else {
                maxPage = 1;
            }
            lbl_maxPage.setText(Integer.toString(maxPage));
        } else {
            lbl_maxPage.setText(Integer.toString(0));

        }
    }

    public void fillTableRevenue(List<Revenue> list) {
        mol = (DefaultTableModel) table_revenue.getModel();
        mol.setRowCount(0);
        for (Revenue re : list) {
            mol.addRow(re.toDataRow());
        }
    }

    public void setComboBox() {
        cbo_time.removeAllItems();
        for (String item : LIST_TIME) {
            cbo_time.addItem(item);
        }

        cbo_trending.removeAllItems();

        for (String item : LIST_TREND) {
            cbo_trending.addItem(item);
        }

        cbo_color.removeAllItems();
        cbo_color.addItem("Tất cả");
        for (mauSac item : LIST_COLOR) {
            cbo_color.addItem(item.getTenMau());
        }

        cbo_material.removeAllItems();
        cbo_material.addItem("Tất cả");
        for (chatLieu item : LIST_MATERIAL) {
            cbo_material.addItem(item.getTenChatLieu());
        }
    }

    public ValueComboBox getValueComboBox() {
        String time = cbo_time.getItemAt(cbo_time.getSelectedIndex());
        String trend = cbo_trending.getItemAt(cbo_trending.getSelectedIndex());
        String color = cbo_color.getItemAt(cbo_color.getSelectedIndex());
        String material = cbo_material.getItemAt(cbo_material.getSelectedIndex());
        System.out.println("time" + time);
        System.out.println("trend" + trend);
        System.out.println("color" + color);
        System.out.println("material" + material);

        return new ValueComboBox(time, trend, color, material);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pn_1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_countOrder = new javax.swing.JLabel();
        lbl_orderSuccess = new javax.swing.JLabel();
        lbl_orderFailure = new javax.swing.JLabel();
        pn_2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbl_revenueDay = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_orderSuccessDay = new javax.swing.JLabel();
        lbl_orderFailureDay = new javax.swing.JLabel();
        pn_3 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbl_revenueMonth = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_orderSuccessMonth = new javax.swing.JLabel();
        lbl_orderFailureMonth = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbo_time = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_revenue = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lbl_currentPage = new javax.swing.JLabel();
        lbl_maxPage = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbo_trending = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbo_color = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbo_material = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        pn_chart = new javax.swing.JPanel();
        pb_menu = new javax.swing.JPanel();
        lbl_thongke = new javax.swing.JLabel();
        lblSanPham1 = new javax.swing.JLabel();
        lbl_nv = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        pn_1.setBackground(new java.awt.Color(51, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("TỔNG ĐƠN HÀNG");

        jLabel17.setText("THÀNH CÔNG: ");

        jLabel18.setText("BỊ HỦY: ");

        lbl_countOrder.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_countOrder.setText("1");

        lbl_orderSuccess.setText("0");

        lbl_orderFailure.setText("0");

        javax.swing.GroupLayout pn_1Layout = new javax.swing.GroupLayout(pn_1);
        pn_1.setLayout(pn_1Layout);
        pn_1Layout.setHorizontalGroup(
            pn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_orderFailure))
                    .addGroup(pn_1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_orderSuccess)))
                .addGap(41, 41, 41))
            .addGroup(pn_1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel15)
                .addContainerGap(85, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_countOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );
        pn_1Layout.setVerticalGroup(
            pn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_countOrder)
                .addGap(55, 55, 55)
                .addGroup(pn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lbl_orderSuccess))
                .addGap(18, 18, 18)
                .addGroup(pn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbl_orderFailure))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pn_2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("TỔNG DOANH THU NGÀY");

        jLabel21.setText("THÀNH CÔNG: ");

        jLabel22.setText("BỊ HỦY: ");

        lbl_revenueDay.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_revenueDay.setText("1");

        jLabel6.setText("K VND");

        lbl_orderSuccessDay.setText("0");

        lbl_orderFailureDay.setText("0");

        javax.swing.GroupLayout pn_2Layout = new javax.swing.GroupLayout(pn_2);
        pn_2.setLayout(pn_2Layout);
        pn_2Layout.setHorizontalGroup(
            pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_2Layout.createSequentialGroup()
                .addComponent(lbl_revenueDay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(108, 108, 108))
            .addGroup(pn_2Layout.createSequentialGroup()
                .addGroup(pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel19))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_2Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_orderFailureDay, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_2Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_orderSuccessDay, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(66, 66, 66))
        );
        pn_2Layout.setVerticalGroup(
            pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_revenueDay)
                    .addComponent(jLabel6))
                .addGap(54, 54, 54)
                .addGroup(pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lbl_orderSuccessDay))
                .addGap(18, 18, 18)
                .addGroup(pn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lbl_orderFailureDay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_3.setBackground(new java.awt.Color(255, 204, 204));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("TỔNG DOANH THU THÁNG");

        jLabel25.setText("THÀNH CÔNG: ");

        jLabel26.setText("BỊ HỦY: ");

        lbl_revenueMonth.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_revenueMonth.setText("1");

        jLabel9.setText("K VND");

        lbl_orderSuccessMonth.setText("0");

        lbl_orderFailureMonth.setText("0");

        javax.swing.GroupLayout pn_3Layout = new javax.swing.GroupLayout(pn_3);
        pn_3.setLayout(pn_3Layout);
        pn_3Layout.setHorizontalGroup(
            pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_3Layout.createSequentialGroup()
                .addGroup(pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_3Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_orderFailureMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_orderSuccessMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(48, 48, 48))
            .addGroup(pn_3Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(lbl_revenueMonth)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(108, 108, 108))
        );
        pn_3Layout.setVerticalGroup(
            pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_revenueMonth)
                    .addComponent(jLabel9))
                .addGap(54, 54, 54)
                .addGroup(pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lbl_orderSuccessMonth))
                .addGap(18, 18, 18)
                .addGroup(pn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lbl_orderFailureMonth))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Thời gian");

        cbo_time.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_timeActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        table_revenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã sản phẩm", "Số lượng bán", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Số lượng còn"
            }
        ));
        jScrollPane2.setViewportView(table_revenue);

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("LỌC");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setText(">");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lbl_currentPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_currentPage.setText("1");

        lbl_maxPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_maxPage.setText("10");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("/");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(1472, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(lbl_currentPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_maxPage)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(406, 406, 406))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_currentPage)
                    .addComponent(lbl_maxPage)
                    .addComponent(jLabel16))
                .addContainerGap())
        );

        jTabbedPane1.addTab("SẢN PHẨM", jPanel9);

        jLabel10.setText("Xu hướng");

        cbo_trending.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_trending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_trendingActionPerformed(evt);
            }
        });

        jLabel11.setText("Màu sắc");

        cbo_color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_colorActionPerformed(evt);
            }
        });

        jLabel12.setText("Chất liệu");

        cbo_material.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_material.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_materialActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Số liệu");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Biểu đồ");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        pn_chart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jRadioButton2))
                    .addComponent(pn_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbo_time, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(cbo_trending, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel10)
                        .addGap(73, 73, 73)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel11)))
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel12)))
                .addGap(73, 73, 73))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(pn_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(pn_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pn_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(34, 34, 34)
                .addComponent(pn_chart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pn_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbo_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_trending, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pb_menu.setBackground(new java.awt.Color(253, 164, 158));
        pb_menu.setLayout(new java.awt.GridLayout(0, 1));

        lbl_thongke.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_thongke.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_thongke.setText("THỐNG KÊ");
        lbl_thongke.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_thongkeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lbl_thongke.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_thongkeMouseClicked(evt);
            }
        });
        pb_menu.add(lbl_thongke);

        lblSanPham1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSanPham1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSanPham1.setText("SẢN PHẨM");
        lblSanPham1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPham1MouseClicked(evt);
            }
        });
        pb_menu.add(lblSanPham1);

        lbl_nv.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_nv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_nv.setText("NHÂN VIÊN");
        lbl_nv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_nvMouseClicked(evt);
            }
        });
        pb_menu.add(lbl_nv);

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("BÁN HÀNG");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        pb_menu.add(jLabel33);

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("KHÁCH HÀNG");
        jLabel34.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel34AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });
        pb_menu.add(jLabel34);

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("KHUYẾN MẠI");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });
        pb_menu.add(jLabel35);

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("ĐỔI MẬT KHẨU");
        pb_menu.add(jLabel36);

        jLabel38.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("HÓA ĐƠN");
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        pb_menu.add(jLabel38);

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("ĐĂNG XUẤT");
        pb_menu.add(jLabel37);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pb_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pb_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_thongkeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_thongkeAncestorAdded

    }//GEN-LAST:event_lbl_thongkeAncestorAdded

    private void lbl_thongkeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_thongkeMouseClicked
        // TODO add your handling code here:
        ThongKe tk = new ThongKe();
        this.dispose();
        tk.setVisible(true);
    }//GEN-LAST:event_lbl_thongkeMouseClicked

    private void lblSanPham1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPham1MouseClicked
        // TODO add your handling code here:
        sanPhamView sp = new sanPhamView();
        this.dispose();
        sp.setVisible(true);
    }//GEN-LAST:event_lblSanPham1MouseClicked

    private void lbl_nvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nvMouseClicked
        // TODO add your handling code here:
        NhanVien nv = new NhanVien();
        this.dispose();
        nv.setVisible(true);
    }//GEN-LAST:event_lbl_nvMouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        BanHang hd = new BanHang();
        this.dispose();
        hd.setVisible(true);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel34AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel34AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel34AncestorAdded

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        // TODO add your handling code here:
        KhachHang kh = new KhachHang();
        this.dispose();
        kh.setVisible(true);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        // TODO add your handling code here:
        KhuyenMaiView km = new KhuyenMaiView();
        this.dispose();
        km.setVisible(true);
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
        QLHoaDon qlhd = new QLHoaDon();
        this.dispose();
        qlhd.setVisible(true);
    }//GEN-LAST:event_jLabel38MouseClicked

    private void cbo_timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_timeActionPerformed
//        ValueComboBox values = getValueComboBox();
//        fillTableRevenue(billService.getRevenueStatistic(values.getTime(),values.getTrending(),values.getColor(),values.getMaterial()));
    }//GEN-LAST:event_cbo_timeActionPerformed

    private void cbo_trendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_trendingActionPerformed
//        ValueComboBox values = getValueComboBox();
//        fillTableRevenue(billService.getRevenueStatistic(values.getTime(),values.getTrending(),values.getColor(),values.getMaterial()));
    }//GEN-LAST:event_cbo_trendingActionPerformed

    private void cbo_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_colorActionPerformed
//        ValueComboBox values = getValueComboBox();
//        fillTableRevenue(billService.getRevenueStatistic(values.getTime(),values.getTrending(),values.getColor(),values.getMaterial()));
    }//GEN-LAST:event_cbo_colorActionPerformed

    private void cbo_materialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_materialActionPerformed
//        ValueComboBox values = getValueComboBox();
//        fillTableRevenue(billService.getRevenueStatistic(values.getTime(),values.getTrending(),values.getColor(),values.getMaterial()));
    }//GEN-LAST:event_cbo_materialActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ValueComboBox values = getValueComboBox();
        pageNumber = 1;
        fillTableRevenue(billService.getRevenueStatistic(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial()));
        int countDocument = billService.getCountProductRevenue(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial());
        setMaxPage(countDocument);

        lbl_currentPage.setText(Integer.toString(pageNumber));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (pageNumber >= maxPage) {
            return;
        }
        pageNumber++;
        lbl_currentPage.setText(Integer.toString(pageNumber));
        ValueComboBox values = getValueComboBox();
        fillTableRevenue(billService.getRevenueStatistic(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (pageNumber == 1) {
            return;
        }
        pageNumber--;
        lbl_currentPage.setText(Integer.toString(pageNumber));
        ValueComboBox values = getValueComboBox();
        fillTableRevenue(billService.getRevenueStatistic(values.getTime(), values.getTrending(), values.getColor(), values.getMaterial()));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        pn_1.setVisible(false);
        pn_2.setVisible(false);
        pn_3.setVisible(false);
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 300));

        pn_chart.add(chartPanel, BorderLayout.CENTER);
        pn_chart.validate();
        pn_chart.setVisible(true);
        
        
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        pn_1.setVisible(true);
        pn_2.setVisible(true);
        pn_3.setVisible(true);
        pn_chart.setVisible(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    public  JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "DOANH THU BÁN HÀNG",
                "Tháng ", "",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private  CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LocalDate date = LocalDate.now();
        int dow = date.getMonthValue();  // Extracts a `DayOfWeek` enum object.
        List<Integer> revenuesYear = billService.getRevenuesChart(dow);
        int index = 1;
        for(int number : revenuesYear) {
            System.out.println(index + ": " + number);
            dataset.addValue(number, "Số người", Integer.toString(index));
            index++;

        }
        
        return dataset;
    }

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
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbo_color;
    private javax.swing.JComboBox<String> cbo_material;
    private javax.swing.JComboBox<String> cbo_time;
    private javax.swing.JComboBox<String> cbo_trending;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblSanPham1;
    private javax.swing.JLabel lbl_countOrder;
    private javax.swing.JLabel lbl_currentPage;
    private javax.swing.JLabel lbl_maxPage;
    private javax.swing.JLabel lbl_nv;
    private javax.swing.JLabel lbl_orderFailure;
    private javax.swing.JLabel lbl_orderFailureDay;
    private javax.swing.JLabel lbl_orderFailureMonth;
    private javax.swing.JLabel lbl_orderSuccess;
    private javax.swing.JLabel lbl_orderSuccessDay;
    private javax.swing.JLabel lbl_orderSuccessMonth;
    private javax.swing.JLabel lbl_revenueDay;
    private javax.swing.JLabel lbl_revenueMonth;
    private javax.swing.JLabel lbl_thongke;
    private javax.swing.JPanel pb_menu;
    private javax.swing.JPanel pn_1;
    private javax.swing.JPanel pn_2;
    private javax.swing.JPanel pn_3;
    private javax.swing.JPanel pn_chart;
    private javax.swing.JTable table_revenue;
    // End of variables declaration//GEN-END:variables
}
