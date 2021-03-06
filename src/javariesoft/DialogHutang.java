/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogHutang.java
 *
 * Created on Dec 29, 2011, 5:52:28 PM
 */
package javariesoft;

import com.erv.db.bankDao;
import com.erv.db.hutangDao;
import com.erv.db.hutangbayarDao;
import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.db.pembelianDao;
import com.erv.db.settingDao;
import com.erv.db.supplierDao;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.fungsi.DecimalFormatRenderer;
import com.erv.fungsi.Fungsi;
import com.erv.model.hutang;
import com.erv.model.hutangbayar;
import com.erv.model.jurnal;
import com.erv.model.pembelian;
import com.erv.model.supplier;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.controller.HutangController;
import com.erv.db.KontrolTanggalDao;
import com.erv.function.PrintfFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

/**
 *
 * @author JAVARIESOFT
 */
public class DialogHutang extends javax.swing.JDialog {

    String ID[];
    String IDAKUN[];
    int IDJurnal = 0;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("###,###,###,###.00");
    Connection cm;
    Statement statm;
    hutang ht;
    String pilSupplier = "0";
    supplierDao dbsup;
    int IDBELI = 0;
    String IdkodeSupplier = "";
    int IDHUTANG = 0;
    pembelian p;
    int hutangid = 0;
    int hutangidKhusus = 0;
    int hutangidKhususcounter = 0;
    int countId = 0;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    int htid = 0;
    String htkd = "";
    public static String ref = "";
    String kodeakun[];
    String namaAkun[];
    Connection c = null;
    HutangController controller;
    /**
     * Creates new form DialogHutang
     */
    public DialogHutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        controller = new HutangController(this);
        try {
            c = koneksi.getKoneksiJ();
            setting(c);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DialogHutang(java.awt.Frame parent, boolean modal, String idpembelian) {
        super(parent, modal);
        initComponents();
        controller = new HutangController(this);
        try {
            c = koneksi.getKoneksiJ();
            setting(c);
            p = pembelianDao.getDetails(c, Integer.parseInt(idpembelian));
            txtKodeSupplier1.setText(p.getIDSUPPLIER());
            txtNamaSupplier1.setText(supplierDao.getDetails(c, p.getIDSUPPLIER()).getNAMA());
            reloadDataHutangBayar(c);
            jTabbedPane1.setSelectedIndex(1);
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DialogHutang(java.awt.Frame parent, boolean modal, String idsupplier, String supplier, double nilai) {
        super(parent, modal);
        initComponents();
        controller = new HutangController(this);
        try {
            c = koneksi.getKoneksiJ();
            setting(c);
            txtKodeSupplierKhusus.setText(idsupplier);
            txtNamaSupplierKhusus.setText(supplier);
            txtKetBayarHutangKhusus.setText("Bayar Hutang Kpd " + txtNamaSupplierKhusus.getText());
            IdkodeSupplier = txtKodeSupplierKhusus.getText();
            //jScrollPane4.setVisible(false);
            hutangidKhusus = hutangbayarDao.getID(c);
            hutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6,9));
            if (pilihKhusus.isSelected()) {
                reloadDataHutangBayarLunasKhusus(c);
            } else {
                reloadDataHutangBayarKhusus(c);
            }
            reloadDataHutangTotal(c, txtKodeSupplierKhusus.getText());
            jTabbedPane1.setSelectedIndex(2); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public JTextField getTxtNorefHK() {
        return txtNorefHK;
    }

    public JTable getjTable6() {
        return jTable6;
    }
    
    private void setting(Connection c) throws ClassNotFoundException, SQLException {
        cm = koneksi.getKoneksiM();
        statm = cm.createStatement();
        ht = new hutang();
        kosongHutang(c);
        kosongBayarAwal(c);
        kosongBayarAwalFaktur();
        kosongBayarKhusus(c);
        isiCombo(c);
        buatTabel();
////            dbsup = new supplierDao(c);
        tglHutang.setDateFormat(d);
        jScrollPane4.setVisible(false);
        jScrollPane4.setSize(700, 150);
        tglBayar.setDateFormat(d);
        tglBayar1.setDateFormat(d);
        tglHutang.setDateFormat(d);
        tglJatuhTempo.setDateFormat(d);
        tglPembayaranKhusus.setDateFormat(d);
        lh = new loghistory();
        lhdao = new loghistoryDao();
        cektombol();
        jTabbedPane1.setTitleAt(1, "");
        jTabbedPane1.setEnabledAt(1, false);
    }

    public JFormattedTextField getTxtJumlahBayarKhusus() {
        return txtJumlahBayarKhusus;
    }

    public void setTxtJumlahBayarKhusus(JFormattedTextField txtJumlahBayarKhusus) {
        this.txtJumlahBayarKhusus = txtJumlahBayarKhusus;
    }

    public JFormattedTextField getTxtSisa() {
        return txtSisa;
    }

    public void setTxtSisa(JFormattedTextField txtSisa) {
        this.txtSisa = txtSisa;
    }

    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCool1 = new com.erv.function.PanelCool();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtKodeHutang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoref = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tglHutang = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        tglJatuhTempo = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        txtSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtJumlahHutang = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        LabelTotalHutang1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cboAkun = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        tglBayar = new datechooser.beans.DateChooserCombo();
        jLabel15 = new javax.swing.JLabel();
        txtKodeSupplier1 = new javax.swing.JTextField();
        txtNamaSupplier1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboCaraBayar = new javax.swing.JComboBox();
        cboBank = new javax.swing.JComboBox();
        txtJumlahBayar = new javax.swing.JFormattedTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtTotalHutang = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtNoTrans = new javax.swing.JTextField();
        pilihan = new javax.swing.JCheckBox();
        btnHapusBayarHutang = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnClear1 = new javax.swing.JButton();
        txtDesk = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        TxtFilter = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtKetBayarHutang = new javax.swing.JTextField();
        btnKeluar1 = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        LabelTotalHutang2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        KodeTransaksiKhusus = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtNoRef1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tglPembayaranKhusus = new datechooser.beans.DateChooserCombo();
        jLabel18 = new javax.swing.JLabel();
        cboCaraBayar1 = new javax.swing.JComboBox();
        cboBank1 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtKodeSupplierKhusus = new javax.swing.JTextField();
        txtNamaSupplierKhusus = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtJumlahBayarKhusus = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        txtSisa = new javax.swing.JFormattedTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        txtTotalHutang1 = new javax.swing.JFormattedTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        btnTambahHutangKhusus = new javax.swing.JButton();
        btnHapusHutangKhusus = new javax.swing.JButton();
        btnSimpanSemua = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtTotalKhusus = new javax.swing.JFormattedTextField();
        pilihKhusus = new javax.swing.JCheckBox();
        btnClear = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        TxtFilter1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtKetBayarHutangKhusus = new javax.swing.JTextField();
        btnKeluar2 = new javax.swing.JButton();
        txtGenerate1 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        LabelTotalHutang3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        txtNorefHK = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        btnDeleteHK = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        tglBayar1 = new datechooser.beans.DateChooserCombo();
        jLabel30 = new javax.swing.JLabel();
        txtKodeSupplier2 = new javax.swing.JTextField();
        txtNamaSupplier2 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cboCaraBayar2 = new javax.swing.JComboBox();
        cboBank2 = new javax.swing.JComboBox();
        txtJumlahBayar1 = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        txtTotalHutang2 = new javax.swing.JFormattedTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        btnSimpan1 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtNoTrans1 = new javax.swing.JTextField();
        pilihan1 = new javax.swing.JCheckBox();
        btnHapusBayarHutang1 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        btnClear2 = new javax.swing.JButton();
        txtDesk1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        TxtFilter2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtKetBayarHutang1 = new javax.swing.JTextField();
        btnKeluar3 = new javax.swing.JButton();
        btnGenerate1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.setName("jTable4"); // NOI18N
        jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable4KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        panelCool1.add(jScrollPane4);
        jScrollPane4.setBounds(150, 150, 50, 20);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogHutang.class);
        jTabbedPane1.setFont(resourceMap.getFont("jTabbedPane1.font")); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        txtKodeHutang.setEditable(false);
        txtKodeHutang.setFont(resourceMap.getFont("txtKodeHutang.font")); // NOI18N
        txtKodeHutang.setText(resourceMap.getString("txtKodeHutang.text")); // NOI18N
        txtKodeHutang.setName("txtKodeHutang"); // NOI18N
        txtKodeHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeHutangKeyPressed(evt);
            }
        });
        jPanel1.add(txtKodeHutang);
        txtKodeHutang.setBounds(140, 20, 160, 22);

        jLabel2.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 20, 100, 15);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(320, 20, 100, 16);

        txtNoref.setFont(resourceMap.getFont("txtNoref.font")); // NOI18N
        txtNoref.setText(resourceMap.getString("txtNoref.text")); // NOI18N
        txtNoref.setName("txtNoref"); // NOI18N
        txtNoref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNorefKeyPressed(evt);
            }
        });
        jPanel1.add(txtNoref);
        txtNoref.setBounds(420, 20, 160, 22);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(320, 50, 100, 16);

        tglHutang.setFieldFont(resourceMap.getFont("tglHutang.dch_combo_fieldFont")); // NOI18N
        tglHutang.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglHutangOnCommit(evt);
            }
        });
        jPanel1.add(tglHutang);
        tglHutang.setBounds(140, 50, 155, 20);

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel1.add(jLabel11);
        jLabel11.setBounds(20, 50, 100, 15);

        tglJatuhTempo.setFieldFont(resourceMap.getFont("tglJatuhTempo.dch_combo_fieldFont")); // NOI18N
        jPanel1.add(tglJatuhTempo);
        tglJatuhTempo.setBounds(420, 50, 155, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 140, 100, 15);

        txtSupplier.setFont(resourceMap.getFont("txtSupplier.font")); // NOI18N
        txtSupplier.setText(resourceMap.getString("txtSupplier.text")); // NOI18N
        txtSupplier.setName("txtSupplier"); // NOI18N
        txtSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupplierActionPerformed(evt);
            }
        });
        txtSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupplierKeyPressed(evt);
            }
        });
        jPanel1.add(txtSupplier);
        txtSupplier.setBounds(140, 80, 130, 21);

        txtNamaSupplier.setEditable(false);
        txtNamaSupplier.setFont(resourceMap.getFont("txtNamaSupplier.font")); // NOI18N
        txtNamaSupplier.setText(resourceMap.getString("txtNamaSupplier.text")); // NOI18N
        txtNamaSupplier.setName("txtNamaSupplier"); // NOI18N
        jPanel1.add(txtNamaSupplier);
        txtNamaSupplier.setBounds(270, 80, 380, 21);

        jLabel12.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 80, 100, 15);

        txtJumlahHutang.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahHutang.setFont(resourceMap.getFont("txtJumlahHutang.font")); // NOI18N
        txtJumlahHutang.setName("txtJumlahHutang"); // NOI18N
        txtJumlahHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJumlahHutangKeyPressed(evt);
            }
        });
        jPanel1.add(txtJumlahHutang);
        txtJumlahHutang.setBounds(140, 110, 290, 22);

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 110, 100, 15);

        txtKeterangan.setFont(resourceMap.getFont("txtKeterangan.font")); // NOI18N
        txtKeterangan.setText(resourceMap.getString("txtKeterangan.text")); // NOI18N
        txtKeterangan.setName("txtKeterangan"); // NOI18N
        txtKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKeteranganKeyPressed(evt);
            }
        });
        jPanel1.add(txtKeterangan);
        txtKeterangan.setBounds(140, 140, 510, 21);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsert);
        btnInsert.setBounds(20, 210, 100, 30);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(20, 258, 860, 370);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(20, 248, 730, 2);

        btnUpdate.setFont(resourceMap.getFont("btnUpdate.font")); // NOI18N
        btnUpdate.setIcon(resourceMap.getIcon("btnUpdate.icon")); // NOI18N
        btnUpdate.setText(resourceMap.getString("btnUpdate.text")); // NOI18N
        btnUpdate.setName("btnUpdate"); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate);
        btnUpdate.setBounds(130, 210, 110, 30);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);
        btnDelete.setBounds(250, 210, 110, 30);

        btnCancel.setFont(resourceMap.getFont("btnCancel.font")); // NOI18N
        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel);
        btnCancel.setBounds(370, 210, 110, 30);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        jPanel1.add(btnKeluar);
        btnKeluar.setBounds(490, 210, 110, 30);

        jLabel31.setFont(resourceMap.getFont("jLabel31.font")); // NOI18N
        jLabel31.setForeground(resourceMap.getColor("jLabel31.foreground")); // NOI18N
        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N
        jPanel1.add(jLabel31);
        jLabel31.setBounds(550, 110, 120, 20);

        LabelTotalHutang1.setFont(resourceMap.getFont("LabelTotalHutang1.font")); // NOI18N
        LabelTotalHutang1.setForeground(resourceMap.getColor("LabelTotalHutang1.foreground")); // NOI18N
        LabelTotalHutang1.setText(resourceMap.getString("LabelTotalHutang1.text")); // NOI18N
        LabelTotalHutang1.setName("LabelTotalHutang1"); // NOI18N
        jPanel1.add(LabelTotalHutang1);
        LabelTotalHutang1.setBounds(670, 110, 210, 20);

        jLabel28.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N
        jPanel1.add(jLabel28);
        jLabel28.setBounds(20, 170, 100, 15);

        cboAkun.setFont(resourceMap.getFont("cboAkun.font")); // NOI18N
        cboAkun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboAkun.setName("cboAkun"); // NOI18N
        jPanel1.add(cboAkun);
        cboAkun.setBounds(140, 170, 170, 22);

        jTabbedPane1.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(null);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel2.add(jLabel14);
        jLabel14.setBounds(160, 180, 140, 20);

        tglBayar.setFieldFont(resourceMap.getFont("tglBayar.dch_combo_fieldFont")); // NOI18N
        jPanel2.add(tglBayar);
        tglBayar.setBounds(150, 50, 155, 20);

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel2.add(jLabel15);
        jLabel15.setBounds(20, 20, 110, 15);

        txtKodeSupplier1.setFont(resourceMap.getFont("txtKodeSupplier1.font")); // NOI18N
        txtKodeSupplier1.setText(resourceMap.getString("txtKodeSupplier1.text")); // NOI18N
        txtKodeSupplier1.setName("txtKodeSupplier1"); // NOI18N
        txtKodeSupplier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplier1ActionPerformed(evt);
            }
        });
        txtKodeSupplier1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeSupplier1KeyPressed(evt);
            }
        });
        jPanel2.add(txtKodeSupplier1);
        txtKodeSupplier1.setBounds(150, 80, 130, 21);

        txtNamaSupplier1.setEditable(false);
        txtNamaSupplier1.setFont(resourceMap.getFont("txtNamaSupplier1.font")); // NOI18N
        txtNamaSupplier1.setText(resourceMap.getString("txtNamaSupplier1.text")); // NOI18N
        txtNamaSupplier1.setName("txtNamaSupplier1"); // NOI18N
        jPanel2.add(txtNamaSupplier1);
        txtNamaSupplier1.setBounds(290, 80, 450, 21);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(20, 210, 860, 140);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(30, 380, 100, 16);

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setForeground(resourceMap.getColor("jLabel9.foreground")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel2.add(jLabel9);
        jLabel9.setBounds(320, 50, 100, 16);

        cboCaraBayar.setFont(resourceMap.getFont("cboCaraBayar.font")); // NOI18N
        cboCaraBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK" }));
        cboCaraBayar.setName("cboCaraBayar"); // NOI18N
        jPanel2.add(cboCaraBayar);
        cboCaraBayar.setBounds(410, 50, 100, 22);

        cboBank.setFont(resourceMap.getFont("cboBank.font")); // NOI18N
        cboBank.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank.setName("cboBank"); // NOI18N
        jPanel2.add(cboBank);
        cboBank.setBounds(520, 50, 100, 22);

        txtJumlahBayar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahBayar.setText(resourceMap.getString("txtJumlahBayar.text")); // NOI18N
        txtJumlahBayar.setFont(resourceMap.getFont("txtJumlahBayar.font")); // NOI18N
        txtJumlahBayar.setName("txtJumlahBayar"); // NOI18N
        jPanel2.add(txtJumlahBayar);
        txtJumlahBayar.setBounds(150, 140, 270, 21);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jPanel2.add(jSeparator2);
        jSeparator2.setBounds(20, 410, 860, 10);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 140, 100, 15);

        txtTotalHutang.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalHutang.setEnabled(false);
        txtTotalHutang.setFont(resourceMap.getFont("txtTotalHutang.font")); // NOI18N
        txtTotalHutang.setName("txtTotalHutang"); // NOI18N
        jPanel2.add(txtTotalHutang);
        txtTotalHutang.setBounds(150, 380, 280, 21);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setName("jTable3"); // NOI18N
        jScrollPane3.setViewportView(jTable3);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(10, 460, 870, 200);

        btnSimpan.setFont(resourceMap.getFont("btnSimpan.font")); // NOI18N
        btnSimpan.setIcon(resourceMap.getIcon("btnSimpan.icon")); // NOI18N
        btnSimpan.setText(resourceMap.getString("btnSimpan.text")); // NOI18N
        btnSimpan.setName("btnSimpan"); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpan);
        btnSimpan.setBounds(10, 420, 100, 30);

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel2.add(jLabel16);
        jLabel16.setBounds(20, 50, 110, 15);

        txtNoTrans.setEditable(false);
        txtNoTrans.setFont(resourceMap.getFont("txtNoTrans.font")); // NOI18N
        txtNoTrans.setText(resourceMap.getString("txtNoTrans.text")); // NOI18N
        txtNoTrans.setName("txtNoTrans"); // NOI18N
        jPanel2.add(txtNoTrans);
        txtNoTrans.setBounds(150, 20, 150, 21);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        pilihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihanActionPerformed(evt);
            }
        });
        jPanel2.add(pilihan);
        pilihan.setBounds(20, 180, 120, 23);

        btnHapusBayarHutang.setFont(resourceMap.getFont("btnHapusBayarHutang.font")); // NOI18N
        btnHapusBayarHutang.setIcon(resourceMap.getIcon("btnHapusBayarHutang.icon")); // NOI18N
        btnHapusBayarHutang.setText(resourceMap.getString("btnHapusBayarHutang.text")); // NOI18N
        btnHapusBayarHutang.setName("btnHapusBayarHutang"); // NOI18N
        btnHapusBayarHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBayarHutangActionPerformed(evt);
            }
        });
        jPanel2.add(btnHapusBayarHutang);
        btnHapusBayarHutang.setBounds(110, 420, 100, 30);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(440, 430, 170, 15);

        btnClear1.setFont(resourceMap.getFont("btnClear1.font")); // NOI18N
        btnClear1.setIcon(resourceMap.getIcon("btnClear1.icon")); // NOI18N
        btnClear1.setText(resourceMap.getString("btnClear1.text")); // NOI18N
        btnClear1.setName("btnClear1"); // NOI18N
        btnClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnClear1);
        btnClear1.setBounds(220, 420, 100, 30);

        txtDesk.setText(resourceMap.getString("txtDesk.text")); // NOI18N
        txtDesk.setName("txtDesk"); // NOI18N
        jPanel2.add(txtDesk);
        txtDesk.setBounds(590, 430, 290, 20);

        jLabel24.setFont(resourceMap.getFont("jLabel24.font")); // NOI18N
        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N
        jPanel2.add(jLabel24);
        jLabel24.setBounds(20, 80, 110, 15);

        TxtFilter.setFont(resourceMap.getFont("TxtFilter.font")); // NOI18N
        TxtFilter.setText(resourceMap.getString("TxtFilter.text")); // NOI18N
        TxtFilter.setToolTipText(resourceMap.getString("TxtFilter.toolTipText")); // NOI18N
        TxtFilter.setName("TxtFilter"); // NOI18N
        TxtFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtFilterActionPerformed(evt);
            }
        });
        TxtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFilterKeyPressed(evt);
            }
        });
        jPanel2.add(TxtFilter);
        TxtFilter.setBounds(290, 180, 150, 21);

        jLabel26.setFont(resourceMap.getFont("jLabel26.font")); // NOI18N
        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N
        jPanel2.add(jLabel26);
        jLabel26.setBounds(20, 110, 130, 15);

        txtKetBayarHutang.setText(resourceMap.getString("txtKetBayarHutang.text")); // NOI18N
        txtKetBayarHutang.setName("txtKetBayarHutang"); // NOI18N
        jPanel2.add(txtKetBayarHutang);
        txtKetBayarHutang.setBounds(150, 110, 590, 20);

        btnKeluar1.setFont(resourceMap.getFont("btnKeluar1.font")); // NOI18N
        btnKeluar1.setIcon(resourceMap.getIcon("btnKeluar1.icon")); // NOI18N
        btnKeluar1.setText(resourceMap.getString("btnKeluar1.text")); // NOI18N
        btnKeluar1.setName("btnKeluar1"); // NOI18N
        btnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnKeluar1);
        btnKeluar1.setBounds(320, 420, 100, 30);

        btnGenerate.setFont(resourceMap.getFont("btnGenerate.font")); // NOI18N
        btnGenerate.setText(resourceMap.getString("btnGenerate.text")); // NOI18N
        btnGenerate.setName("btnGenerate"); // NOI18N
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerate);
        btnGenerate.setBounds(310, 13, 110, 30);

        jLabel32.setFont(resourceMap.getFont("jLabel32.font")); // NOI18N
        jLabel32.setForeground(resourceMap.getColor("jLabel32.foreground")); // NOI18N
        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N
        jPanel2.add(jLabel32);
        jLabel32.setBounds(530, 180, 120, 20);

        LabelTotalHutang2.setFont(resourceMap.getFont("jLabel32.font")); // NOI18N
        LabelTotalHutang2.setForeground(resourceMap.getColor("LabelTotalHutang2.foreground")); // NOI18N
        LabelTotalHutang2.setText(resourceMap.getString("LabelTotalHutang2.text")); // NOI18N
        LabelTotalHutang2.setName("LabelTotalHutang2"); // NOI18N
        jPanel2.add(LabelTotalHutang2);
        LabelTotalHutang2.setBounds(650, 180, 210, 20);

        jTabbedPane1.addTab(resourceMap.getString("jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(null);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel3.add(jLabel5);
        jLabel5.setBounds(30, 20, 100, 16);

        KodeTransaksiKhusus.setEditable(false);
        KodeTransaksiKhusus.setFont(resourceMap.getFont("KodeTransaksiKhusus.font")); // NOI18N
        KodeTransaksiKhusus.setText(resourceMap.getString("KodeTransaksiKhusus.text")); // NOI18N
        KodeTransaksiKhusus.setName("KodeTransaksiKhusus"); // NOI18N
        jPanel3.add(KodeTransaksiKhusus);
        KodeTransaksiKhusus.setBounds(150, 20, 150, 22);

        jLabel21.setFont(resourceMap.getFont("jLabel21.font")); // NOI18N
        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel3.add(jLabel21);
        jLabel21.setBounds(350, 20, 60, 16);

        txtNoRef1.setEditable(false);
        txtNoRef1.setFont(resourceMap.getFont("txtNoRef1.font")); // NOI18N
        txtNoRef1.setText(resourceMap.getString("txtNoRef1.text")); // NOI18N
        txtNoRef1.setName("txtNoRef1"); // NOI18N
        jPanel3.add(txtNoRef1);
        txtNoRef1.setBounds(410, 20, 170, 22);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel3.add(jLabel10);
        jLabel10.setBounds(30, 50, 90, 16);

        tglPembayaranKhusus.setFieldFont(resourceMap.getFont("tglPembayaranKhusus.dch_combo_fieldFont")); // NOI18N
        jPanel3.add(tglPembayaranKhusus);
        tglPembayaranKhusus.setBounds(150, 50, 155, 20);

        jLabel18.setFont(resourceMap.getFont("jLabel18.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel3.add(jLabel18);
        jLabel18.setBounds(320, 50, 100, 16);

        cboCaraBayar1.setFont(resourceMap.getFont("cboCaraBayar1.font")); // NOI18N
        cboCaraBayar1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK", "LAIN" }));
        cboCaraBayar1.setName("cboCaraBayar1"); // NOI18N
        jPanel3.add(cboCaraBayar1);
        cboCaraBayar1.setBounds(410, 50, 0, 22);

        cboBank1.setFont(resourceMap.getFont("cboBank1.font")); // NOI18N
        cboBank1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank1.setName("cboBank1"); // NOI18N
        jPanel3.add(cboBank1);
        cboBank1.setBounds(530, 50, 330, 22);

        jLabel17.setFont(resourceMap.getFont("jLabel17.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel3.add(jLabel17);
        jLabel17.setBounds(30, 80, 110, 16);

        txtKodeSupplierKhusus.setFont(resourceMap.getFont("txtKodeSupplierKhusus.font")); // NOI18N
        txtKodeSupplierKhusus.setText(resourceMap.getString("txtKodeSupplierKhusus.text")); // NOI18N
        txtKodeSupplierKhusus.setName("txtKodeSupplierKhusus"); // NOI18N
        txtKodeSupplierKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplierKhususActionPerformed(evt);
            }
        });
        txtKodeSupplierKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeSupplierKhususKeyPressed(evt);
            }
        });
        jPanel3.add(txtKodeSupplierKhusus);
        txtKodeSupplierKhusus.setBounds(150, 80, 160, 22);

        txtNamaSupplierKhusus.setEditable(false);
        txtNamaSupplierKhusus.setFont(resourceMap.getFont("txtNamaSupplierKhusus.font")); // NOI18N
        txtNamaSupplierKhusus.setText(resourceMap.getString("txtNamaSupplierKhusus.text")); // NOI18N
        txtNamaSupplierKhusus.setName("txtNamaSupplierKhusus"); // NOI18N
        jPanel3.add(txtNamaSupplierKhusus);
        txtNamaSupplierKhusus.setBounds(320, 80, 360, 22);

        jLabel19.setFont(resourceMap.getFont("jLabel19.font")); // NOI18N
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel3.add(jLabel19);
        jLabel19.setBounds(30, 110, 120, 16);

        txtJumlahBayarKhusus.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahBayarKhusus.setFont(resourceMap.getFont("txtJumlahBayarKhusus.font")); // NOI18N
        txtJumlahBayarKhusus.setName("txtJumlahBayarKhusus"); // NOI18N
        txtJumlahBayarKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahBayarKhususActionPerformed(evt);
            }
        });
        txtJumlahBayarKhusus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJumlahBayarKhususFocusLost(evt);
            }
        });
        txtJumlahBayarKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJumlahBayarKhususKeyPressed(evt);
            }
        });
        jPanel3.add(txtJumlahBayarKhusus);
        txtJumlahBayarKhusus.setBounds(150, 110, 220, 22);

        jLabel20.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel3.add(jLabel20);
        jLabel20.setBounds(550, 110, 70, 16);

        txtSisa.setEditable(false);
        txtSisa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtSisa.setFont(resourceMap.getFont("txtSisa.font")); // NOI18N
        txtSisa.setName("txtSisa"); // NOI18N
        jPanel3.add(txtSisa);
        txtSisa.setBounds(600, 110, 260, 22);

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable5.setName("jTable5"); // NOI18N
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel3.add(jScrollPane5);
        jScrollPane5.setBounds(20, 170, 870, 180);

        jLabel22.setFont(resourceMap.getFont("jLabel22.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel3.add(jLabel22);
        jLabel22.setBounds(330, 360, 100, 16);

        txtTotalHutang1.setEditable(false);
        txtTotalHutang1.setFont(resourceMap.getFont("txtTotalHutang1.font")); // NOI18N
        txtTotalHutang1.setName("txtTotalHutang1"); // NOI18N
        jPanel3.add(txtTotalHutang1);
        txtTotalHutang1.setBounds(140, 360, 160, 22);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jPanel3.add(jSeparator3);
        jSeparator3.setBounds(20, 410, 840, 10);

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTable6.setName("jTable6"); // NOI18N
        jScrollPane6.setViewportView(jTable6);

        jPanel3.add(jScrollPane6);
        jScrollPane6.setBounds(150, 460, 740, 120);

        btnTambahHutangKhusus.setFont(resourceMap.getFont("btnTambahHutangKhusus.font")); // NOI18N
        btnTambahHutangKhusus.setIcon(resourceMap.getIcon("btnTambahHutangKhusus.icon")); // NOI18N
        btnTambahHutangKhusus.setText(resourceMap.getString("btnTambahHutangKhusus.text")); // NOI18N
        btnTambahHutangKhusus.setName("btnTambahHutangKhusus"); // NOI18N
        btnTambahHutangKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHutangKhususActionPerformed(evt);
            }
        });
        jPanel3.add(btnTambahHutangKhusus);
        btnTambahHutangKhusus.setBounds(30, 460, 110, 30);

        btnHapusHutangKhusus.setFont(resourceMap.getFont("btnHapusHutangKhusus.font")); // NOI18N
        btnHapusHutangKhusus.setIcon(resourceMap.getIcon("btnHapusHutangKhusus.icon")); // NOI18N
        btnHapusHutangKhusus.setText(resourceMap.getString("btnHapusHutangKhusus.text")); // NOI18N
        btnHapusHutangKhusus.setName("btnHapusHutangKhusus"); // NOI18N
        btnHapusHutangKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusHutangKhususActionPerformed(evt);
            }
        });
        jPanel3.add(btnHapusHutangKhusus);
        btnHapusHutangKhusus.setBounds(30, 490, 110, 30);

        btnSimpanSemua.setFont(resourceMap.getFont("btnSimpanSemua.font")); // NOI18N
        btnSimpanSemua.setIcon(resourceMap.getIcon("btnSimpanSemua.icon")); // NOI18N
        btnSimpanSemua.setText(resourceMap.getString("btnSimpanSemua.text")); // NOI18N
        btnSimpanSemua.setName("btnSimpanSemua"); // NOI18N
        btnSimpanSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSemuaActionPerformed(evt);
            }
        });
        jPanel3.add(btnSimpanSemua);
        btnSimpanSemua.setBounds(150, 590, 190, 30);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel3.add(jLabel23);
        jLabel23.setBounds(620, 590, 70, 16);

        txtTotalKhusus.setEditable(false);
        txtTotalKhusus.setFont(resourceMap.getFont("txtTotalKhusus.font")); // NOI18N
        txtTotalKhusus.setName("txtTotalKhusus"); // NOI18N
        jPanel3.add(txtTotalKhusus);
        txtTotalKhusus.setBounds(670, 590, 200, 22);

        pilihKhusus.setFont(resourceMap.getFont("pilihKhusus.font")); // NOI18N
        pilihKhusus.setText(resourceMap.getString("pilihKhusus.text")); // NOI18N
        pilihKhusus.setName("pilihKhusus"); // NOI18N
        pilihKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihKhususActionPerformed(evt);
            }
        });
        jPanel3.add(pilihKhusus);
        pilihKhusus.setBounds(40, 140, 140, 23);

        btnClear.setFont(resourceMap.getFont("btnClear.font")); // NOI18N
        btnClear.setIcon(resourceMap.getIcon("btnClear.icon")); // NOI18N
        btnClear.setText(resourceMap.getString("btnClear.text")); // NOI18N
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear);
        btnClear.setBounds(30, 520, 110, 30);

        txtcari.setFont(resourceMap.getFont("txtcari.font")); // NOI18N
        txtcari.setName("txtcari"); // NOI18N
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        jPanel3.add(txtcari);
        txtcari.setBounds(410, 50, 110, 21);

        jLabel25.setFont(resourceMap.getFont("jLabel25.font")); // NOI18N
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel3.add(jLabel25);
        jLabel25.setBounds(180, 140, 120, 20);

        TxtFilter1.setFont(resourceMap.getFont("TxtFilter1.font")); // NOI18N
        TxtFilter1.setText(resourceMap.getString("TxtFilter1.text")); // NOI18N
        TxtFilter1.setName("TxtFilter1"); // NOI18N
        TxtFilter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtFilter1ActionPerformed(evt);
            }
        });
        TxtFilter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFilter1KeyPressed(evt);
            }
        });
        jPanel3.add(TxtFilter1);
        TxtFilter1.setBounds(300, 140, 190, 21);

        jLabel27.setFont(resourceMap.getFont("jLabel27.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel3.add(jLabel27);
        jLabel27.setBounds(20, 360, 100, 16);

        txtKetBayarHutangKhusus.setText(resourceMap.getString("txtKetBayarHutangKhusus.text")); // NOI18N
        txtKetBayarHutangKhusus.setName("txtKetBayarHutangKhusus"); // NOI18N
        jPanel3.add(txtKetBayarHutangKhusus);
        txtKetBayarHutangKhusus.setBounds(440, 360, 420, 20);

        btnKeluar2.setFont(resourceMap.getFont("btnKeluar2.font")); // NOI18N
        btnKeluar2.setIcon(resourceMap.getIcon("btnKeluar2.icon")); // NOI18N
        btnKeluar2.setText(resourceMap.getString("btnKeluar2.text")); // NOI18N
        btnKeluar2.setName("btnKeluar2"); // NOI18N
        btnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnKeluar2);
        btnKeluar2.setBounds(30, 550, 110, 30);

        txtGenerate1.setFont(resourceMap.getFont("txtGenerate1.font")); // NOI18N
        txtGenerate1.setText(resourceMap.getString("txtGenerate1.text")); // NOI18N
        txtGenerate1.setName("txtGenerate1"); // NOI18N
        txtGenerate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGenerate1ActionPerformed(evt);
            }
        });
        jPanel3.add(txtGenerate1);
        txtGenerate1.setBounds(590, 13, 150, 30);

        jLabel33.setForeground(resourceMap.getColor("jLabel33.foreground")); // NOI18N
        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N
        jPanel3.add(jLabel33);
        jLabel33.setBounds(550, 140, 120, 20);

        LabelTotalHutang3.setFont(resourceMap.getFont("LabelTotalHutang3.font")); // NOI18N
        LabelTotalHutang3.setForeground(resourceMap.getColor("LabelTotalHutang3.foreground")); // NOI18N
        LabelTotalHutang3.setText(resourceMap.getString("LabelTotalHutang3.text")); // NOI18N
        LabelTotalHutang3.setName("LabelTotalHutang3"); // NOI18N
        jPanel3.add(LabelTotalHutang3);
        LabelTotalHutang3.setBounds(670, 140, 210, 20);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jLabel41.setFont(resourceMap.getFont("jLabel41.font")); // NOI18N
        jLabel41.setText(resourceMap.getString("jLabel41.text")); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N
        jPanel5.add(jLabel41);

        txtNorefHK.setFont(resourceMap.getFont("txtNorefHK.font")); // NOI18N
        txtNorefHK.setText(resourceMap.getString("txtNorefHK.text")); // NOI18N
        txtNorefHK.setName("txtNorefHK"); // NOI18N
        txtNorefHK.setPreferredSize(new java.awt.Dimension(200, 20));
        jPanel5.add(txtNorefHK);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jPanel5.add(btnFilter);

        btnDeleteHK.setFont(resourceMap.getFont("btnDeleteHK.font")); // NOI18N
        btnDeleteHK.setText(resourceMap.getString("btnDeleteHK.text")); // NOI18N
        btnDeleteHK.setName("btnDeleteHK"); // NOI18N
        btnDeleteHK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteHKActionPerformed(evt);
            }
        });
        jPanel5.add(btnDeleteHK);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(150, 410, 740, 40);

        jTabbedPane1.addTab(resourceMap.getString("jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(null);

        jLabel29.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N
        jPanel4.add(jLabel29);
        jLabel29.setBounds(170, 140, 140, 20);

        tglBayar1.setFieldFont(resourceMap.getFont("tglBayar1.dch_combo_fieldFont")); // NOI18N
        jPanel4.add(tglBayar1);
        tglBayar1.setBounds(150, 50, 155, 20);

        jLabel30.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N
        jPanel4.add(jLabel30);
        jLabel30.setBounds(20, 20, 110, 16);

        txtKodeSupplier2.setFont(resourceMap.getFont("txtKodeSupplier2.font")); // NOI18N
        txtKodeSupplier2.setText(resourceMap.getString("txtKodeSupplier2.text")); // NOI18N
        txtKodeSupplier2.setName("txtKodeSupplier2"); // NOI18N
        txtKodeSupplier2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplier2ActionPerformed(evt);
            }
        });
        txtKodeSupplier2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeSupplier2KeyPressed(evt);
            }
        });
        jPanel4.add(txtKodeSupplier2);
        txtKodeSupplier2.setBounds(150, 80, 130, 22);

        txtNamaSupplier2.setEditable(false);
        txtNamaSupplier2.setFont(resourceMap.getFont("txtNamaSupplier2.font")); // NOI18N
        txtNamaSupplier2.setText(resourceMap.getString("txtNamaSupplier2.text")); // NOI18N
        txtNamaSupplier2.setName("txtNamaSupplier2"); // NOI18N
        jPanel4.add(txtNamaSupplier2);
        txtNamaSupplier2.setBounds(290, 80, 450, 22);

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTable7.setName("jTable7"); // NOI18N
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);

        jPanel4.add(jScrollPane7);
        jScrollPane7.setBounds(20, 170, 860, 200);

        jLabel34.setFont(resourceMap.getFont("jLabel34.font")); // NOI18N
        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N
        jPanel4.add(jLabel34);
        jLabel34.setBounds(30, 380, 100, 16);

        jLabel35.setFont(resourceMap.getFont("jLabel35.font")); // NOI18N
        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        jPanel4.add(jLabel35);
        jLabel35.setBounds(320, 50, 100, 16);

        cboCaraBayar2.setFont(resourceMap.getFont("cboCaraBayar2.font")); // NOI18N
        cboCaraBayar2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK" }));
        cboCaraBayar2.setName("cboCaraBayar2"); // NOI18N
        jPanel4.add(cboCaraBayar2);
        cboCaraBayar2.setBounds(410, 50, 100, 22);

        cboBank2.setFont(resourceMap.getFont("cboBank2.font")); // NOI18N
        cboBank2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank2.setName("cboBank2"); // NOI18N
        jPanel4.add(cboBank2);
        cboBank2.setBounds(520, 50, 100, 22);

        txtJumlahBayar1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahBayar1.setFont(resourceMap.getFont("txtJumlahBayar1.font")); // NOI18N
        txtJumlahBayar1.setName("txtJumlahBayar1"); // NOI18N
        jPanel4.add(txtJumlahBayar1);
        txtJumlahBayar1.setBounds(590, 380, 270, 22);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jPanel4.add(jSeparator4);
        jSeparator4.setBounds(20, 410, 860, 10);

        jLabel36.setFont(resourceMap.getFont("jLabel36.font")); // NOI18N
        jLabel36.setText(resourceMap.getString("jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N
        jPanel4.add(jLabel36);
        jLabel36.setBounds(500, 380, 100, 16);

        txtTotalHutang2.setEditable(false);
        txtTotalHutang2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalHutang2.setFont(resourceMap.getFont("txtTotalHutang2.font")); // NOI18N
        txtTotalHutang2.setName("txtTotalHutang2"); // NOI18N
        jPanel4.add(txtTotalHutang2);
        txtTotalHutang2.setBounds(150, 380, 280, 22);

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTable8.setName("jTable8"); // NOI18N
        jScrollPane8.setViewportView(jTable8);

        jPanel4.add(jScrollPane8);
        jScrollPane8.setBounds(10, 460, 870, 168);

        btnSimpan1.setFont(resourceMap.getFont("btnSimpan1.font")); // NOI18N
        btnSimpan1.setText(resourceMap.getString("btnSimpan1.text")); // NOI18N
        btnSimpan1.setName("btnSimpan1"); // NOI18N
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan1);
        btnSimpan1.setBounds(10, 420, 100, 30);

        jLabel37.setFont(resourceMap.getFont("jLabel37.font")); // NOI18N
        jLabel37.setText(resourceMap.getString("jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N
        jPanel4.add(jLabel37);
        jLabel37.setBounds(20, 50, 110, 16);

        txtNoTrans1.setEditable(false);
        txtNoTrans1.setFont(resourceMap.getFont("txtNoTrans1.font")); // NOI18N
        txtNoTrans1.setText(resourceMap.getString("txtNoTrans1.text")); // NOI18N
        txtNoTrans1.setName("txtNoTrans1"); // NOI18N
        jPanel4.add(txtNoTrans1);
        txtNoTrans1.setBounds(150, 20, 150, 22);

        pilihan1.setFont(resourceMap.getFont("pilihan1.font")); // NOI18N
        pilihan1.setText(resourceMap.getString("pilihan1.text")); // NOI18N
        pilihan1.setName("pilihan1"); // NOI18N
        pilihan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihan1ActionPerformed(evt);
            }
        });
        jPanel4.add(pilihan1);
        pilihan1.setBounds(30, 140, 120, 25);

        btnHapusBayarHutang1.setFont(resourceMap.getFont("btnHapusBayarHutang1.font")); // NOI18N
        btnHapusBayarHutang1.setText(resourceMap.getString("btnHapusBayarHutang1.text")); // NOI18N
        btnHapusBayarHutang1.setName("btnHapusBayarHutang1"); // NOI18N
        btnHapusBayarHutang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBayarHutang1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnHapusBayarHutang1);
        btnHapusBayarHutang1.setBounds(110, 420, 100, 30);

        jLabel38.setFont(resourceMap.getFont("jLabel38.font")); // NOI18N
        jLabel38.setText(resourceMap.getString("jLabel38.text")); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N
        jPanel4.add(jLabel38);
        jLabel38.setBounds(440, 430, 170, 16);

        btnClear2.setFont(resourceMap.getFont("btnClear2.font")); // NOI18N
        btnClear2.setText(resourceMap.getString("btnClear2.text")); // NOI18N
        btnClear2.setName("btnClear2"); // NOI18N
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });
        jPanel4.add(btnClear2);
        btnClear2.setBounds(220, 420, 100, 30);

        txtDesk1.setName("txtDesk1"); // NOI18N
        jPanel4.add(txtDesk1);
        txtDesk1.setBounds(590, 430, 290, 20);

        jLabel39.setFont(resourceMap.getFont("jLabel39.font")); // NOI18N
        jLabel39.setText(resourceMap.getString("jLabel39.text")); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N
        jPanel4.add(jLabel39);
        jLabel39.setBounds(20, 80, 110, 16);

        TxtFilter2.setFont(resourceMap.getFont("TxtFilter2.font")); // NOI18N
        TxtFilter2.setToolTipText(resourceMap.getString("TxtFilter2.toolTipText")); // NOI18N
        TxtFilter2.setName("TxtFilter2"); // NOI18N
        TxtFilter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtFilter2ActionPerformed(evt);
            }
        });
        TxtFilter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFilter2KeyPressed(evt);
            }
        });
        jPanel4.add(TxtFilter2);
        TxtFilter2.setBounds(300, 140, 200, 22);

        jLabel40.setFont(resourceMap.getFont("jLabel40.font")); // NOI18N
        jLabel40.setText(resourceMap.getString("jLabel40.text")); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N
        jPanel4.add(jLabel40);
        jLabel40.setBounds(20, 110, 130, 16);

        txtKetBayarHutang1.setName("txtKetBayarHutang1"); // NOI18N
        jPanel4.add(txtKetBayarHutang1);
        txtKetBayarHutang1.setBounds(150, 110, 590, 20);

        btnKeluar3.setFont(resourceMap.getFont("btnKeluar3.font")); // NOI18N
        btnKeluar3.setText(resourceMap.getString("btnKeluar3.text")); // NOI18N
        btnKeluar3.setName("btnKeluar3"); // NOI18N
        btnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnKeluar3);
        btnKeluar3.setBounds(320, 420, 100, 30);

        btnGenerate1.setFont(resourceMap.getFont("btnGenerate1.font")); // NOI18N
        btnGenerate1.setText(resourceMap.getString("btnGenerate1.text")); // NOI18N
        btnGenerate1.setName("btnGenerate1"); // NOI18N
        btnGenerate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerate1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnGenerate1);
        btnGenerate1.setBounds(310, 13, 110, 30);

        jTabbedPane1.addTab(resourceMap.getString("jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        panelCool1.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 910, 660);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(951, 726));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
// TODO add your handling code here:
    try {
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtSupplier.getText().equals("")) || (txtJumlahHutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtSupplier.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglHutang.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglHutang.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertHutang";
                        prosesSimpan(c, "0");
                        reloadDataHutang(c);
                        insertJurnal(c);
                        insertRinciJurnalHutang(c);
                        prosesUpdateLog(c);
                        kosongHutang(c);
                        cektombol();
                        reloadDataHutangTotal(c, txtSupplier.getText());
                        c.commit();
                        JOptionPane.showMessageDialog(this, "Entri Data Ok");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodeSupplier1.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodeSupplier1.requestFocus();
                }
            }
        } else {
            txtSupplier.requestFocus();
        }
    } catch (Exception e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");

        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//GEN-LAST:event_btnInsertActionPerformed

private void txtSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupplierActionPerformed
    try {
        // TODO add your handling code here:
        reloadSupplier(c, "0");
//    jScrollPane4.setVisible(true);
//    JDBCAdapter ja = new JDBCAdapter(c);
//    ja.executeQuery("select IDSUPPLIER,NAMA from SUPPLIER where IDSUPPLIER like '" + txtSupplier.getText() + "%' or lower(NAMA) like '%" + txtSupplier.getText().toLowerCase() + "%'");
//    jScrollPane4.getViewport().remove(jTable4);
//    jTable4 = new JTable(ja);
//    jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
//
//        @Override
//        public void keyPressed(java.awt.event.KeyEvent evt) {
//            jTable4KeyPressed(evt);
//        }
//    });
//    jTable4.setFont(new Font("Tahoma", Font.BOLD, 12));
//    jScrollPane4.getViewport().add(jTable4);
//    jScrollPane4.repaint();
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtSupplierActionPerformed

private void jTable4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable4KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            supplier b = new supplier();
            b = supplierDao.getDetails(c, jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilSupplier.equals("0")) {
                txtSupplier.setText(b.getIDSUPPLIER());
                txtNamaSupplier.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataHutang(c);
                reloadDataHutangTotal(c, txtSupplier.getText());
                txtJumlahHutang.requestFocus();
            } else if (pilSupplier.equals("1")) {
                txtKodeSupplier1.setText(b.getIDSUPPLIER());
                txtNamaSupplier1.setText(b.getNAMA());
                txtKetBayarHutang.setText("Bayar Hutang Kpd " + txtNamaSupplier1.getText());
                IdkodeSupplier = txtKodeSupplier1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataHutangBayarLunas(c);
                } else {
                    reloadDataHutangBayar(c);
                }
                reloadDataHutangTotal(c, txtKodeSupplier1.getText());
            } else if (pilSupplier.equals("2")) {
                txtKodeSupplierKhusus.setText(b.getIDSUPPLIER());
                txtNamaSupplierKhusus.setText(b.getNAMA());
                txtKetBayarHutangKhusus.setText("Bayar Hutang Kpd " + txtNamaSupplierKhusus.getText());
                IdkodeSupplier = txtKodeSupplierKhusus.getText();
                jScrollPane4.setVisible(false);
                hutangidKhusus = hutangbayarDao.getID(c);
                hutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6,9));
                if (pilihKhusus.isSelected()) {
                    reloadDataHutangBayarLunasKhusus(c);
                } else {
                    reloadDataHutangBayarKhusus(c);
                }
                reloadDataHutangTotal(c, txtKodeSupplierKhusus.getText());
                //
            } else if (pilSupplier.equals("3")) {
                txtKodeSupplier2.setText(b.getIDSUPPLIER());
                txtNamaSupplier2.setText(b.getNAMA());
                txtKetBayarHutang1.setText("Bayar Hutang Kpd " + txtNamaSupplier2.getText());
                IdkodeSupplier = txtKodeSupplier2.getText();
                jScrollPane4.setVisible(false);
                if (pilihan1.isSelected()) {
                    reloadDataHutangBayarLunasFaktur();
                } else {
                    reloadDataHutangBayarFaktur();
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}//GEN-LAST:event_jTable4KeyPressed

private void txtSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilSupplier = "0";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtSupplierKeyPressed

private void txtKodeHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeHutangKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtNoref.requestFocus();
    }
}//GEN-LAST:event_txtKodeHutangKeyPressed

private void txtNorefKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNorefKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtSupplier.requestFocus();
    }
}//GEN-LAST:event_txtNorefKeyPressed

private void txtJumlahHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahHutangKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtKeterangan.requestFocus();
    }
}//GEN-LAST:event_txtJumlahHutangKeyPressed

private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
    try {
        // TODO add your handling code here:
        Calendar cld = Calendar.getInstance();
        ht = hutangDao.getDetails(c, Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString()));
        IDHUTANG = ht.getID();
        txtKodeHutang.setText(ht.getKODEHUTANG());
        txtNoref.setText(ht.getIDPEMBELIAN() + "");
        try {
            cld.setTime(d.parse(ht.getTANGGAL()));
        } catch (ParseException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
        tglHutang.setSelectedDate(cld);
        try {
            cld.setTime(d.parse(ht.getJATUHTEMPO()));
        } catch (ParseException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
        tglJatuhTempo.setSelectedDate(cld);
        txtSupplier.setText(ht.getIDSUPPLIER());
        txtNamaSupplier.setText(supplierDao.getDetails(c, ht.getIDSUPPLIER()).getNAMA());
        txtJumlahHutang.setValue(ht.getJUMLAH());
        txtKeterangan.setText(ht.getKETERANGAN());
        cektombol();
    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_jTable2MouseClicked

private void txtKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeteranganKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        btnSimpan.requestFocus();
    }
}//GEN-LAST:event_txtKeteranganKeyPressed

private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Diedit?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtSupplier.getText().equals("")) || (txtJumlahHutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtSupplier.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglHutang.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglHutang.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "UpdateHutang";
                        prosesSimpan(c, "1");
                        reloadDataHutang(c);
                        prosesUpdateLog(c);
                        kosongHutang(c);
                        cektombol();
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodeSupplier1.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodeSupplier1.requestFocus();
                }
            }
        } else {
            txtSupplier.requestFocus();
        }
    } catch (SQLException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");

        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_btnUpdateActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    try {
        txtTotalHutang.setValue(jTable1.getValueAt(jTable1.getSelectedRow(), 5));
        hutangid = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        reloadDataPelunasan(c);
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_jTable1MouseClicked

private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodeSupplier1.getText().equals("")) || (txtTotalHutang.getText().equals("")) || (txtJumlahBayar.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodeSupplier1.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglBayar.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglBayar.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertHutangBayar";
                        double jumlahBayar = Double.parseDouble(txtJumlahBayar.getValue().toString());
                        int kdbank = Integer.parseInt(ID[cboBank.getSelectedIndex()]);
                        List<hutang> hutangList = hutangDao.getAllHutangBL(c, IdkodeSupplier);
                        hutangDao.bayarHutang(hutangList, jumlahBayar, tglBayar.getText(), txtKetBayarHutang.getText(), cboCaraBayar.getSelectedIndex(), kdbank, IdkodeSupplier);
                        prosesUpdateLog(c);
                        reloadDataPelunasan(c);
                        if (pilihan.isSelected()) {
                            reloadDataHutangBayarLunas(c);
                        } else {
                            reloadDataHutangBayar(c);
                        }
                        //reloadDataHutangBayar();
                        reloadDataHutangTotal(c, txtKodeSupplier1.getText());
                        kosongBayar(c);
                        ref = "";
                        c.commit();
                        JOptionPane.showMessageDialog(this, "Entri Data Ok");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodeSupplier1.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodeSupplier1.requestFocus();
                }
            }
        } else {
            txtKodeSupplier1.requestFocus();
        }
    } catch (Exception ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}//GEN-LAST:event_btnSimpanActionPerformed

private void txtKodeSupplier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplier1ActionPerformed
    try {
        // TODO add your handling code here:
        reloadSupplier(c, "1");
//    jScrollPane4.setVisible(true);
//    JDBCAdapter ja = new JDBCAdapter(c);
//    ja.executeQuery("select IDSUPPLIER,NAMA from SUPPLIER where IDSUPPLIER like '" + txtKodeSupplier1.getText() + "%' or lower(NAMA) like '%" + txtKodeSupplier1.getText().toLowerCase() + "%'");
//    jScrollPane4.getViewport().remove(jTable4);
//    jTable4 = new JTable(ja);
//    jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
//
//        @Override
//        public void keyPressed(java.awt.event.KeyEvent evt) {
//            jTable4KeyPressed(evt);
//        }
//    });
//    jTable4.setFont(new Font("Tahoma", Font.BOLD, 12));
//    jScrollPane4.getViewport().add(jTable4);
//    jScrollPane4.repaint();
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtKodeSupplier1ActionPerformed

private void txtKodeSupplier1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeSupplier1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilSupplier = "1";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtKodeSupplier1KeyPressed

private void tglHutangOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglHutangOnCommit
    try {
        // TODO add your handling code here:
        settingTgl(c);
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_tglHutangOnCommit

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtSupplier.getText().equals("")) || (txtJumlahHutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtSupplier.requestFocus();
            } else {
                aksilog = "DeleteHutang";
                hutangDao.deleteFromHutang(c, IDHUTANG);
                reloadDataHutang(c);
                prosesUpdateLog(c);
                kosongHutang(c);
                cektombol();
            }
        } else {
            txtSupplier.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnDeleteActionPerformed

private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
    try {
        // TODO add your handling code here:
        kosongHutang(c);
        cektombol();
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnCancelActionPerformed

private void btnHapusBayarHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBayarHutangActionPerformed
// TODO add your handling code here:
    try {
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if (txtDesk.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Keterangan Harus Diisi.. !");
                txtDesk.requestFocus();

            } else {
                if (jTable3.getValueAt(jTable3.getSelectedRow(), 5).toString().equals("0")) {
                    if (!KontrolTanggalDao.cekHarian(c, jTable3.getValueAt(jTable3.getSelectedRow(), 3).toString())) {
                        JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                                + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                                + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                    } else {
                        Statement sf;
                        String tgal[] = Util.split(jTable3.getValueAt(jTable3.getSelectedRow(), 3).toString(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiode(c, per)) {
                            aksilog = "DeleteHutangBayar";
                            sf = c.createStatement();
//                        sf.execute("delete from JURNAL where KODEJURNAL='" + jTable3.getValueAt(jTable3.getSelectedRow(), 1) + "'");
                            sf.execute("delete from HUTANGBAYAR where ID='" + jTable3.getValueAt(jTable3.getSelectedRow(), 0) + "'");
                            JOptionPane.showMessageDialog(this, "Delete Ok");
                            String sql = "SELECT JUMLAH - JUMLAHBAYAR as SISA from VIEW_HUTANG where ID='" + hutangid + "'";
                            ResultSet rs = sf.executeQuery(sql);
                            if (rs.next()) {
                                if (rs.getDouble(1) != 0) {
                                    sf.executeUpdate("update HUTANG set STATUS='1' where ID='" + hutangid + "'");
                                }
                            }
                            sf.close();
                            prosesUpdateLog(c);
                            reloadDataPelunasan(c);
                            if (pilihan.isSelected()) {
                                reloadDataHutangBayarLunas(c);
                            } else {
                                reloadDataHutangBayar(c);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                            txtKodeSupplier1.requestFocus();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data ini berkaitan dengan pembayaran hutang khusus.. !");
                    txtKodeSupplier1.requestFocus();
                }
            }
        } else {
            txtKodeSupplier1.requestFocus();
        }
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnHapusBayarHutangActionPerformed

private void pilihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihanActionPerformed
    try {
        // TODO add your handling code here:
        if (txtKodeSupplier1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Supplier...");
            txtKodeSupplier1.requestFocus();
        } else {

            if (pilihan.isSelected()) {
                reloadDataHutangBayarLunas(c);
            } else {
                reloadDataHutangBayar(c);
            }
        }
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_pilihanActionPerformed

private void txtKodeSupplierKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplierKhususActionPerformed
    try {
        // TODO add your handling code here:
        reloadSupplier(c, "2");
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtKodeSupplierKhususActionPerformed

private void txtKodeSupplierKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeSupplierKhususKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilSupplier = "2";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtKodeSupplierKhususKeyPressed

private void txtJumlahBayarKhususFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususFocusLost
// TODO add your handling code here:
    txtSisa.setText(txtJumlahBayarKhusus.getText());
    jTable5.requestFocus();
}//GEN-LAST:event_txtJumlahBayarKhususFocusLost

private void txtJumlahBayarKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtSisa.setValue(txtJumlahBayarKhusus.getValue());
        //txtSisa.setText(txtJumlahBayarKhusus.getText());
        jTable5.requestFocus();
    }
}//GEN-LAST:event_txtJumlahBayarKhususKeyPressed

private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
    try {
        // TODO add your handling code here:
        txtTotalHutang1.setValue(jTable5.getValueAt(jTable5.getSelectedRow(), 5));
        hutangid = Integer.parseInt(jTable5.getValueAt(jTable5.getSelectedRow(), 0).toString());
        //reloadDataPelunasanKhusus();
    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable5MouseClicked

private void btnTambahHutangKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHutangKhususActionPerformed
// TODO add your handling code here:
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Tambah Data ..?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodeSupplierKhusus.getText().equals("")) || (txtJumlahBayarKhusus.getText().equals("")) || (txtTotalHutang1.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodeSupplierKhusus.requestFocus();
            } else {
                if (txtJumlahBayarKhusus.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Jumlah Bayar Harap Diisi .. !");
                    txtJumlahBayarKhusus.requestFocus();
                } else {
                    if (txtSisa.getText().equals("0")) {
                        JOptionPane.showMessageDialog(null, "Jumlah Hiutang yang akan dibayarkan berlebih .. !");
                        txtKodeSupplierKhusus.requestFocus();
                    } else {
                        String tgal[] = Util.split(tglPembayaranKhusus.getText(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c, per)) {
                                String sql = "INSERT INTO HUTANGBAYAR "
                                        + "VALUES (" + (hutangidKhusus) + ",\n"
                                        + "        '" + (KodeTransaksiKhusus.getText().substring(0, 5) + "" + new PrintfFormat("%04d").sprintf(hutangidKhususcounter)) + "',\n"
                                        + "        " + hutangid + ",\n"
                                        + "        '" + tglPembayaranKhusus.getText() + "',\n"
                                        + "        " + ((Double.parseDouble(txtSisa.getValue().toString()) > Double.parseDouble(txtTotalHutang1.getValue().toString())) ? txtTotalHutang1.getValue() : txtSisa.getValue()) + ",\n"
                                        + "        '" + txtNoRef1.getText() + "',\n"
                                        + "        '" + ((Double.parseDouble(txtSisa.getValue().toString()) >= Double.parseDouble(txtTotalHutang1.getValue().toString())) ? "0" : "1") + "')";
                                System.out.println(sql);
                                statm.execute(sql);
                                reloadDataPelunasanKhusus();
                                txtSisa.setValue((Double.parseDouble(txtJumlahBayarKhusus.getValue().toString()) - getTotal()));
                                txtTotalKhusus.setValue(getTotal());
                                hutangidKhusus++;
                                hutangidKhususcounter++;

                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                txtKodeSupplierKhusus.requestFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            txtKodeSupplierKhusus.requestFocus();
                        }
                    }
                }
            }
        } else {
            txtKodeSupplierKhusus.requestFocus();
        }
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnTambahHutangKhususActionPerformed

private void btnHapusHutangKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusHutangKhususActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            statm.execute("delete from hutangbayar where ID=" + jTable6.getValueAt(jTable6.getSelectedRow(), 0) + "");
            txtSisa.setValue((Double.parseDouble(txtJumlahBayarKhusus.getValue().toString()) - getTotal()));
            txtTotalKhusus.setValue(getTotal());
            reloadDataPelunasanKhusus();
            hutangidKhusus=hutangidKhusus-1;
            hutangidKhususcounter=hutangidKhususcounter-1;
        } else {
            txtKodeSupplierKhusus.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnHapusHutangKhususActionPerformed

private void btnSimpanSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSemuaActionPerformed
    Statement stat = null;
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodeSupplierKhusus.getText().equals("")) || (txtJumlahBayarKhusus.getText().equals("")) || (cboBank1.getSelectedItem().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodeSupplierKhusus.requestFocus();
            } else if (getFormatTextDouble(txtJumlahBayarKhusus) != getFormatTextDouble(txtTotalKhusus)) {
                JOptionPane.showMessageDialog(null, "Nilai Jumlah Bayar Tidak sama dengan Total Invoice,Pilih Nilai Piutang berikutnya... !");
                txtKodeSupplierKhusus.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglPembayaranKhusus.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglPembayaranKhusus.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertJurnal";
                        stat = c.createStatement();
                        ResultSet rs = statm.executeQuery("select * from hutangbayar order by 1");
                        stat.addBatch("INSERT INTO JURNAL "
                                + "VALUES (" + jurnalDao.getIDJurnal(c) + ",\n"
                                + "        '" + txtNoRef1.getText() + "',\n"
                                + "        '" + tglPembayaranKhusus.getText() + "',\n"
                                + "        '" + txtKetBayarHutangKhusus.getText() + "');");
                        //if (cboCaraBayar1.getSelectedIndex() == 0) {
                        //     stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + settingDao.getAkun(c, "KAS") + "',0," + txtJumlahBayarKhusus.getValue().toString() + ",2)");
                        // } else {
                        stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + IDAKUN[cboBank1.getSelectedIndex()] + "',0," + txtJumlahBayarKhusus.getValue().toString() + ",2,'')");
                        //  }
                        stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + supplierDao.getDetails(c, txtKodeSupplierKhusus.getText()).getKODEAKUN() + "'," + txtJumlahBayarKhusus.getValue().toString() + ",0,1,'')");

                        stat.executeBatch();
                        prosesUpdateLog(c);
                        while (rs.next()) {
                            stat.addBatch("INSERT INTO HUTANGBAYAR "
                                    + "VALUES (" + rs.getInt(1) + ",\n"
                                    + "        '" + rs.getString(2) + "',\n"
                                    + "        " + rs.getInt(3) + ",\n"
                                    + "        '" + rs.getDate(4) + "',\n"
                                    + "        " + rs.getDouble(5) + ",\n"
                                    + "        '" + rs.getString(6) + "');");
                            //if (Double.parseDouble(rs.getString(7))<10) {
                            if (rs.getString(7).equals("0")) {
                                //stat.addBatch("update hutang set status='0' where ID=" + rs.getInt(3) + "");
                                stat.addBatch("update hutang set status='" + 0 + "' where ID=" + rs.getInt(3) + "");
                            }
                            aksilog = "InsertHutangBayar";
                            htid = rs.getInt(3);
                            htkd = rs.getString(2);
                            prosesUpdateLogKhusus(c);
                        }
                        stat.executeBatch();
                        hapusTabel();
                        buatTabel();
                        reloadDataPelunasanKhusus();
                        kosongBayarKhusus(c);
                        txtKodeSupplierKhusus.requestFocus();
                        if (pilihKhusus.isSelected()) {
                            reloadDataHutangBayarLunasKhusus(c);
                        } else {
                            reloadDataHutangBayarKhusus(c);
                        }
                        reloadDataHutangTotal(c, txtKodeSupplierKhusus.getText());
                        c.commit();
                        JOptionPane.showMessageDialog(null, "Simpan Data Ok");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodeSupplierKhusus.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodeSupplierKhusus.requestFocus();
                }
            }
        } else {
            txtKodeSupplierKhusus.requestFocus();
        }
        stat.close();
    } catch (Exception ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

}//GEN-LAST:event_btnSimpanSemuaActionPerformed

private void pilihKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihKhususActionPerformed
    try {
        // TODO add your handling code here:
        if (txtKodeSupplierKhusus.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Supplier...");
            txtKodeSupplierKhusus.requestFocus();
        } else {
            if (pilihKhusus.isSelected()) {
                reloadDataHutangBayarLunasKhusus(c);
            } else {
                reloadDataHutangBayarKhusus(c);
            }
        }
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_pilihKhususActionPerformed

private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    try {
        // TODO add your handling code here:
        hapusTabel();
        buatTabel();
        txtKodeSupplierKhusus.requestFocus();
        reloadDataPelunasanKhusus();
        kosongBayarKhusus(c);
    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnClearActionPerformed

private void txtJumlahBayarKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususActionPerformed
// TODO add your handling code here:
    txtSisa.setValue(txtJumlahBayarKhusus.getValue());
    jTable5.requestFocus();
}//GEN-LAST:event_txtJumlahBayarKhususActionPerformed

private void btnClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear1ActionPerformed
    try {
        // TODO add your handling code here:
        kosongBayarAwal(c);
    } catch (Exception ex) {
        Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnClear1ActionPerformed

private void TxtFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilterActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TxtFilterActionPerformed

private void TxtFilterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilterKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            supplier b = new supplier();
            b = supplierDao.getDetails(c, jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilSupplier.equals("0")) {
                //txtSupplier.setText(b.getIDSUPPLIER());
                //txtNamaSupplier.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataHutang(c);
                txtJumlahHutang.requestFocus();
            } else if (pilSupplier.equals("1")) {
                //txtKodeSupplier1.setText(b.getIDSUPPLIER());
                //txtNamaSupplier1.setText(b.getNAMA());
                IdkodeSupplier = txtKodeSupplier1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataHutangBayarLunas(c);
                } else {
                    reloadDataHutangBayar(c);
                }
            } else if (pilSupplier.equals("2")) {
                txtKodeSupplierKhusus.setText(b.getIDSUPPLIER());
                txtNamaSupplierKhusus.setText(b.getNAMA());
                IdkodeSupplier = txtKodeSupplierKhusus.getText();
                jScrollPane4.setVisible(false);
                hutangidKhusus = hutangbayarDao.getID(c);
                if (pilihKhusus.isSelected()) {
                    reloadDataHutangBayarLunasKhusus(c);
                } else {
                    reloadDataHutangBayarKhusus(c);
                }
                //
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}//GEN-LAST:event_TxtFilterKeyPressed

private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
// TODO add your handling code here:
    //if (cboCaraBayar1.getSelectedItem().equals("LAIN")) {
    cboBank1.removeAllItems();
    try {
        String sql = "";
        if (JavarieSoftApp.groupuser.equals("Administrator")) {
            sql = "select * from PERKIRAAN where (TIPE='D' or TIPE='SD') and (kodeperkiraan like '%" + txtcari.getText() + "%' OR lower(NAMAPERKIRAAN) like '%" + txtcari.getText().toLowerCase() + "%') order by 2";
        } else {
            sql = "select * from PERKIRAAN where (kodeperkiraan ='11110' /*KAS(D)*/\n" +
"or kodeperkiraan like '11120.%') /*Bank(SD)*/\n" +
"and (kodeperkiraan like '%" + txtcari.getText() + "%' OR lower(NAMAPERKIRAAN) like '%" + txtcari.getText().toLowerCase() + "%') order by 2";
        }
        Statement st1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //ResultSet rs1 = st1.executeQuery("select * from perkiraan where (tipe='D' or tipe='SD') and ( kodeperkiraan like '" + txtcari.getText() + "%' or lower(namaperkiraan) like '%" + txtcari.getText().toLowerCase() + "%')");
//        ResultSet rs1 = st1.executeQuery("select * from perkiraan where (tipe='SD' or kodeperkiraan ='11110' or kodeperkiraan ='31101') and ( kodeperkiraan like '%" + txtcari.getText() + "%' or lower(namaperkiraan) like '%" + txtcari.getText().toLowerCase() + "%')");
        ResultSet rs1 = st1.executeQuery(sql);
        rs1.last();
        if (rs1.getRow() > 0) {
            IDAKUN = new String[rs1.getRow()];
            rs1.beforeFirst();
            int i = 0;
            while (rs1.next()) {
                IDAKUN[i] = rs1.getString(1);
                cboBank1.addItem(rs1.getString(1) + "-" + rs1.getString(2));
                i++;
            }
        }
        rs1.close();
        st1.close();
    } catch (SQLException ex) {
        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    }
   // }
}//GEN-LAST:event_txtcariActionPerformed

private void TxtFilter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilter1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            supplier b = new supplier();
            b = supplierDao.getDetails(c, jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilSupplier.equals("0")) {
                //txtSupplier.setText(b.getIDSUPPLIER());
                //txtNamaSupplier.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataHutang(c);
                txtJumlahHutang.requestFocus();
            } else if (pilSupplier.equals("1")) {
                //txtKodeSupplier1.setText(b.getIDSUPPLIER());
                //txtNamaSupplier1.setText(b.getNAMA());
                IdkodeSupplier = txtKodeSupplier1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataHutangBayarLunas(c);
                } else {
                    reloadDataHutangBayar(c);
                }
            } else if (pilSupplier.equals("2")) {
                //txtKodeSupplierKhusus.setText(b.getIDSUPPLIER());
                //txtNamaSupplierKhusus.setText(b.getNAMA());
                IdkodeSupplier = txtKodeSupplierKhusus.getText();
                jScrollPane4.setVisible(false);
                hutangidKhusus = hutangbayarDao.getID(c);
                hutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6,9));
                if (pilihKhusus.isSelected()) {
                    reloadDataHutangBayarLunasKhusus(c);
                } else {
                    reloadDataHutangBayarKhusus(c);
                }
                //
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}//GEN-LAST:event_TxtFilter1KeyPressed

private void TxtFilter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilter1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TxtFilter1ActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        dispose();

    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void btnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar2ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar2ActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        try {
            // TODO add your handling code here:
            txtNoTrans.setText(hutangbayarDao.getKodeHutangBayar(c));
            txtKetBayarHutang.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void txtGenerate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGenerate1ActionPerformed
        try {
            // TODO add your handling code here:
            setKodeJurnal(c);
            hutangidKhusus = hutangbayarDao.getID(c);
            hutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6,9));
            txtKetBayarHutangKhusus.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtGenerate1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            cm.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void txtKodeSupplier2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplier2ActionPerformed
        // TODO add your handling code here:
        reloadSupplier(c, "3");
        //    jScrollPane4.setVisible(true);
        //    JDBCAdapter ja = new JDBCAdapter(c);
        //    ja.executeQuery("select IDSUPPLIER,NAMA from SUPPLIER where IDSUPPLIER like '" + txtKodeSupplier1.getText() + "%' or lower(NAMA) like '%" + txtKodeSupplier1.getText().toLowerCase() + "%'");
        //    jScrollPane4.getViewport().remove(jTable4);
        //    jTable4 = new JTable(ja);
        //    jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
        //
        //        @Override
        //        public void keyPressed(java.awt.event.KeyEvent evt) {
        //            jTable4KeyPressed(evt);
        //        }
        //    });
        //    jTable4.setFont(new Font("Tahoma", Font.BOLD, 12));
        //    jScrollPane4.getViewport().add(jTable4);
        //    jScrollPane4.repaint();
    }//GEN-LAST:event_txtKodeSupplier2ActionPerformed

    private void txtKodeSupplier2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeSupplier2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            pilSupplier = "3";
            jScrollPane4.setVisible(true);
            jTable4.requestFocus();
            jTable4.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane4.setVisible(false);
        }
    }//GEN-LAST:event_txtKodeSupplier2KeyPressed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked

        txtTotalHutang2.setValue(jTable7.getValueAt(jTable7.getSelectedRow(), 5));
        hutangid = Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString());
        reloadDataPelunasanFaktur(c);
    }//GEN-LAST:event_jTable7MouseClicked

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        try {
            // TODO add your handling code here:
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                if ((txtKodeSupplier2.getText().equals("")) || (txtTotalHutang2.getText().equals("")) || (txtJumlahBayar1.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                    txtKodeSupplier2.requestFocus();
                } else if (!KontrolTanggalDao.cekHarian(c, tglBayar1.getText())) {
                    JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                            + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                            + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                } else {
                    String tgal[] = Util.split(tglBayar1.getText(), "-");
                    String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                    if (cekperiodeAda(c, per)) {
                        if (cekperiode(c, per)) {
                            if (Double.parseDouble(txtTotalHutang2.getValue().toString()) < Double.parseDouble(txtJumlahBayar1.getValue().toString())) {
                                JOptionPane.showMessageDialog(null, "Jumlah Bayar Besar Dari Jumlah Hutang.. !");
                                txtKodeSupplier2.requestFocus();
                            } else {
                                aksilog = "InsertHutangBayarF";
                                c.createStatement().execute("set autocommit false");
                                hutangbayar h = new hutangbayar();
                                h.setID(hutangbayarDao.getID(c));
                                h.setIDHUTANG(Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString()));
                                h.setKODEHUTANGBAYAR(txtNoTrans1.getText());
                                h.setTANGGAL(tglBayar1.getText());
                                h.setJUMLAH(Double.parseDouble(txtJumlahBayar1.getValue().toString()));
                                if (!ref.equals("")) {
                                    h.setREF(ref);
                                    DialogPembelian.status = true;
                                } else {
                                    h.setREF("0");
                                    DialogPembelian.status = false;
                                }
                                if (hutangbayarDao.insertIntoHUTANGBAYAR(c, h)) {
                                    JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Ok");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Gagal");
                                }
                                if ((Double.parseDouble(txtTotalHutang2.getValue().toString()) - Double.parseDouble(txtJumlahBayar1.getValue().toString()))<10) {
                                    ht = hutangDao.getDetails(c, Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString()));
                                    ht.setSTATUS("0");
                                    hutangDao.updateHUTANG(c, Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString()), ht);
                                }
                                if (ref.equals("")) {
                                    insertJurnalFaktur();
                                    insertRinciJurnalFaktur();
                                }
                                prosesUpdateLog(c);
                                reloadDataPelunasanFaktur(c);
                                if (pilihan1.isSelected()) {
                                    reloadDataHutangBayarLunasFaktur();
                                } else {
                                    reloadDataHutangBayarFaktur();
                                }
                                //reloadDataHutangBayar();
                                kosongBayarFaktur();
                                ref = "";
                                c.commit();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                            txtKodeSupplier1.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                        txtKodeSupplier1.requestFocus();
                    }
                }
            } else {
                txtKodeSupplier1.requestFocus();
            }
        } catch (Exception ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "Rollback " + ex.getMessage());
            } catch (SQLException ex1) {
                Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.createStatement().execute("set autocommit true");
            } catch (SQLException ex) {
                Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void pilihan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihan1ActionPerformed
        // TODO add your handling code here:
        if (txtKodeSupplier2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Supplier...");
            txtKodeSupplier2.requestFocus();
        } else {
            if (pilihan1.isSelected()) {
                reloadDataHutangBayarLunasFaktur();
            } else {
                reloadDataHutangBayarFaktur();
            }
        }
    }//GEN-LAST:event_pilihan1ActionPerformed

    private void btnHapusBayarHutang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBayarHutang1ActionPerformed
        // TODO add your handling code here:
        try {
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                if (txtDesk1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Keterangan Harus Diisi.. !");
                    txtDesk1.requestFocus();
                } else {
                    if (jTable8.getValueAt(jTable8.getSelectedRow(), 5).toString().equals("0")) {
                        if (!KontrolTanggalDao.cekHarian(c, jTable8.getValueAt(jTable8.getSelectedRow(), 3).toString())) {
                            JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                                    + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                                    + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                        } else {
                            Statement sf;
                            String tgal[] = Util.split(jTable8.getValueAt(jTable8.getSelectedRow(), 3).toString(), "-");
                            String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                            if (cekperiode(c, per)) {
                                c.createStatement().execute("set autocommit false");
                                aksilog = "DeleteHutangBayarF";
                                sf = koneksi.getKoneksiJ().createStatement();
                                sf.execute("delete from JURNAL where KODEJURNAL='" + jTable8.getValueAt(jTable8.getSelectedRow(), 1) + "'");
                                sf.execute("delete from HUTANGBAYAR where ID='" + jTable8.getValueAt(jTable8.getSelectedRow(), 0) + "'");
                                JOptionPane.showMessageDialog(this, "Delete Ok");
                                String sql = "SELECT JUMLAH - JUMLAHBAYAR as SISA from VIEW_HUTANG where ID='" + hutangid + "'";
                                ResultSet rs = sf.executeQuery(sql);
                                if (rs.next()) {
                                    if (rs.getDouble(1) != 0) {
                                        sf.executeUpdate("update HUTANG set STATUS='1' where ID='" + hutangid + "'");
                                    }
                                }
                                sf.close();
                                prosesUpdateLog(c);
                                reloadDataPelunasanFaktur(c);
                                if (pilihan.isSelected()) {
                                    reloadDataHutangBayarLunasFaktur();
                                } else {
                                    reloadDataHutangBayarFaktur();
                                }
                                c.commit();
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                txtKodeSupplier2.requestFocus();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Data ini berkaitan dengan pembayaran hutang khusus.. !");
                        txtKodeSupplier2.requestFocus();
                    }
                }
            } else {
                txtKodeSupplier2.requestFocus();
            }
        } catch (Exception ex) {
            try {
                c.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.createStatement().execute("set autocommit true");
            } catch (SQLException ex) {
                Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnHapusBayarHutang1ActionPerformed

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
        kosongBayarAwalFaktur();
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void TxtFilter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtFilter2ActionPerformed

    private void TxtFilter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilter2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                supplier b = new supplier();
                b = supplierDao.getDetails(c, jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
                if (pilSupplier.equals("0")) {
                    //txtSupplier.setText(b.getIDSUPPLIER());
                    //txtNamaSupplier.setText(b.getNAMA());
                    jScrollPane4.setVisible(false);
                    reloadDataHutang(c);
                    txtJumlahHutang.requestFocus();
                } else if (pilSupplier.equals("1")) {
                    //txtKodeSupplier1.setText(b.getIDSUPPLIER());
                    //txtNamaSupplier1.setText(b.getNAMA());
                    IdkodeSupplier = txtKodeSupplier1.getText();
                    jScrollPane4.setVisible(false);
                    if (pilihan.isSelected()) {
                        reloadDataHutangBayarLunasFaktur();
                    } else {
                        reloadDataHutangBayarFaktur();
                    }
                } else if (pilSupplier.equals("2")) {
                    txtKodeSupplierKhusus.setText(b.getIDSUPPLIER());
                    txtNamaSupplierKhusus.setText(b.getNAMA());
                    IdkodeSupplier = txtKodeSupplierKhusus.getText();
                    jScrollPane4.setVisible(false);
                    hutangidKhusus = hutangbayarDao.getID(c);
                    hutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6,9));
                    if (pilihKhusus.isSelected()) {
                        reloadDataHutangBayarLunasKhusus(c);
                    } else {
                        reloadDataHutangBayarKhusus(c);
                    }
                    //
                }

            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_TxtFilter2KeyPressed

    private void btnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar3ActionPerformed
        // TODO add your handling code here:
        try {
            dispose();
            statm.close();
            cm.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnKeluar3ActionPerformed

    private void btnGenerate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerate1ActionPerformed
        // TODO add your handling code here:
        txtNoTrans1.setText(hutangbayarDao.getKodeHutangBayar(c));
        txtKetBayarHutang.requestFocus();
    }//GEN-LAST:event_btnGenerate1ActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        controller.onClickFilter();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnDeleteHKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteHKActionPerformed
        // TODO add your handling code here:
        controller.deleteHK();
    }//GEN-LAST:event_btnDeleteHKActionPerformed

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
            java.util.logging.Logger.getLogger(DialogHutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogHutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogHutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogHutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DialogHutang dialog = new DialogHutang(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField KodeTransaksiKhusus;
    private javax.swing.JLabel LabelTotalHutang1;
    private javax.swing.JLabel LabelTotalHutang2;
    private javax.swing.JLabel LabelTotalHutang3;
    private javax.swing.JTextField TxtFilter;
    private javax.swing.JTextField TxtFilter1;
    private javax.swing.JTextField TxtFilter2;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClear1;
    private javax.swing.JButton btnClear2;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteHK;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnGenerate1;
    private javax.swing.JButton btnHapusBayarHutang;
    private javax.swing.JButton btnHapusBayarHutang1;
    private javax.swing.JButton btnHapusHutangKhusus;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnKeluar2;
    private javax.swing.JButton btnKeluar3;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JButton btnSimpanSemua;
    private javax.swing.JButton btnTambahHutangKhusus;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cboAkun;
    private javax.swing.JComboBox cboBank;
    private javax.swing.JComboBox cboBank1;
    private javax.swing.JComboBox cboBank2;
    private javax.swing.JComboBox cboCaraBayar;
    private javax.swing.JComboBox cboCaraBayar1;
    private javax.swing.JComboBox cboCaraBayar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JCheckBox pilihKhusus;
    private javax.swing.JCheckBox pilihan;
    private javax.swing.JCheckBox pilihan1;
    private datechooser.beans.DateChooserCombo tglBayar;
    private datechooser.beans.DateChooserCombo tglBayar1;
    private datechooser.beans.DateChooserCombo tglHutang;
    private datechooser.beans.DateChooserCombo tglJatuhTempo;
    private datechooser.beans.DateChooserCombo tglPembayaranKhusus;
    private javax.swing.JTextField txtDesk;
    private javax.swing.JTextField txtDesk1;
    private javax.swing.JButton txtGenerate1;
    public static javax.swing.JFormattedTextField txtJumlahBayar;
    public static javax.swing.JFormattedTextField txtJumlahBayar1;
    private javax.swing.JFormattedTextField txtJumlahBayarKhusus;
    private javax.swing.JFormattedTextField txtJumlahHutang;
    private javax.swing.JTextField txtKetBayarHutang;
    private javax.swing.JTextField txtKetBayarHutang1;
    private javax.swing.JTextField txtKetBayarHutangKhusus;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtKodeHutang;
    private javax.swing.JTextField txtKodeSupplier1;
    private javax.swing.JTextField txtKodeSupplier2;
    private javax.swing.JTextField txtKodeSupplierKhusus;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNamaSupplier1;
    private javax.swing.JTextField txtNamaSupplier2;
    private javax.swing.JTextField txtNamaSupplierKhusus;
    private javax.swing.JTextField txtNoRef1;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtNoTrans1;
    private javax.swing.JTextField txtNoref;
    private javax.swing.JTextField txtNorefHK;
    private javax.swing.JFormattedTextField txtSisa;
    private javax.swing.JTextField txtSupplier;
    private javax.swing.JFormattedTextField txtTotalHutang;
    private javax.swing.JFormattedTextField txtTotalHutang1;
    private javax.swing.JFormattedTextField txtTotalHutang2;
    private javax.swing.JFormattedTextField txtTotalKhusus;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables

    void kosongHutang(Connection c) {
        txtKodeHutang.setText("");
        txtNoref.setText("0");
        txtSupplier.setText("");
        txtNamaSupplier.setText("");
        txtKeterangan.setText("");
        txtJumlahHutang.setText("");
        txtKodeHutang.setText(hutangDao.getKodeHutang(c));
    }

    void kosongBayar(Connection c) {
        txtNoTrans.setText("");
        //txtKodeSupplier1.setText("");
        //txtNamaSupplier1.setText("");
        txtTotalHutang.setText("");
        txtJumlahBayar.setText("");
        txtNoTrans.setText(hutangbayarDao.getKodeHutangBayar(c));
    }

    void kosongBayarAwal(Connection c) {
        txtNoTrans.setText("");
        txtKodeSupplier1.setText("");
        txtNamaSupplier1.setText("");
        txtKetBayarHutang.setText("");
        TxtFilter.setText("");
        txtTotalHutang.setText("");
        txtJumlahBayar.setText("");
        txtDesk.setText("");
        txtNoTrans.setText(hutangbayarDao.getKodeHutangBayar(c));
    }

    void kosongBayarAwalFaktur() {
        txtNoTrans1.setText("");
        txtKodeSupplier2.setText("");
        txtNamaSupplier2.setText("");
        txtKetBayarHutang1.setText("");
        TxtFilter2.setText("");
        txtTotalHutang2.setText("");
        txtJumlahBayar1.setText("");
        txtDesk1.setText("");
        txtNoTrans1.setText(hutangbayarDao.getKodeHutangBayar(c));
    }

    void kosongBayarKhusus(Connection c) throws SQLException {
        KodeTransaksiKhusus.setText("");
        //txtKodeSupplierKhusus.setText("");
        //txtNamaSupplierKhusus.setText("");
        txtJumlahBayarKhusus.setText("0");
        txtTotalHutang1.setText("");
        txtSisa.setText("");
        txtTotalKhusus.setText("");
        setKodeJurnal(c);
    }

    void setKodeJurnal(Connection c) throws SQLException {
        IDJurnal = jurnalDao.getIDJurnal(c);
        txtNoRef1.setText(jurnalDao.getGenKodeJurnal(c, "HK"));
        KodeTransaksiKhusus.setText(hutangbayarDao.getKodeHutangBayarRef(c));

    }

    void isiCombo(Connection c) throws SQLException {
        cboBank.removeAllItems();
        cboBank1.removeAllItems();
        cboAkun.removeAllItems();
        cboBank2.removeAllItems();
        Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stat.executeQuery("select * from BANK");
        rs.last();
        if (rs.getRow() > 0) {
            ID = new String[rs.getRow()];
        }
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            ID[i] = rs.getString(1);
            cboBank.addItem(rs.getString(2));
            cboBank2.addItem(rs.getString(2));
            i++;
        }

        String sql = "select * from PERKIRAAN where KODEPERKIRAAN='11110' or KODEPERKIRAAN='31101' order by KODEPERKIRAAN";

        Statement stat1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stat1.executeQuery(sql);

        int j1 = 0;
        if (rs.last()) {
            j1 = rs.getRow();
        }
        if (j1 > 0) {
            kodeakun = new String[j1];
            namaAkun = new String[j1];
            rs.beforeFirst();
            int c1 = 0;
            while (rs.next()) {
                cboAkun.addItem(rs.getString(1) + "-" + rs.getString(2));
                kodeakun[c1] = rs.getString(1);
                namaAkun[c1] = rs.getString(2);
                c1++;
            }
        }
        rs.close();
        stat1.close();

    }

    void insertHutang(Connection c) throws SQLException {

        ht.setID(hutangDao.getID(c));
        ht.setKODEHUTANG(txtKodeHutang.getText());
        ht.setIDPEMBELIAN(IDBELI);
        ht.setJUMLAH(Double.parseDouble(txtTotalHutang.getValue().toString()));
        //ht.setTANGGAL(java.sql.Date.valueOf(tglHutang.getText()));
        ht.setTANGGAL(tglHutang.getText());
        if (hutangDao.insertIntoHUTANG(c, ht)) {
            JOptionPane.showMessageDialog(this, "Entri Data Ok");
        }

    }

    void insertJurnal(Connection c) throws SQLException {
        jurnal j = new jurnal();
        jurnalDao dbjurnal = new jurnalDao();
        if (jTabbedPane1.getSelectedIndex() == 0) {
            String tgl = com.erv.function.Util.toDateStringSql(new Date());
            String kdjrn = "";
            kdjrn = jurnalDao.getGenKodeJurnal(c, "HA");
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(kdjrn);
            j.setTANGGAL(tglHutang.getText());
            j.setDESKRIPSI(txtKeterangan.getText() + " " + txtNamaSupplier.getText());
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(txtNoTrans.getText());
            //j.setTANGGAL(java.sql.Date.valueOf(tglBayar.getText()));
            j.setTANGGAL(tglBayar.getText());
            j.setDESKRIPSI(txtKetBayarHutang.getText());
        }
        boolean insertIntoJURNAL = jurnalDao.insertIntoJURNAL(c, j);
    }

    void insertRinciJurnal(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        s = c.createStatement();
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, txtKodeSupplier1.getText()).getKODEAKUN() + "'," + txtJumlahBayar.getValue().toString() + ",0,1,'')");
        if (cboCaraBayar.getSelectedIndex() == 0) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + txtJumlahBayar.getValue().toString() + ",2,'')");
        } else {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bankDao.getDetails(c, Integer.parseInt(ID[cboBank.getSelectedIndex()])).getKODEAKUN() + "',0," + txtJumlahBayar.getValue().toString() + ",2,'')");
        }
        s.close();
    }

    void insertRinciJurnalHutang(Connection c) throws SQLException {
        Statement s;
        s = c.createStatement();
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + kodeakun[cboAkun.getSelectedIndex()] + "'," + txtJumlahHutang.getValue().toString() + ",0,1,'')");
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, txtSupplier.getText()).getKODEAKUN() + "',0," + txtJumlahHutang.getValue().toString() + ",2,'')");
        s.close();
    }

    void reloadSupplier(Connection c, String pil) {
        try {
            String kodesupplier = "";
            String namasupplier = "";
            if (pil.equals("0")) {
                kodesupplier = txtSupplier.getText();
                namasupplier = txtNamaSupplier.getText();
            } else if (pil.equals("1")) {
                kodesupplier = txtKodeSupplier1.getText();
                namasupplier = txtNamaSupplier1.getText();
            } else if (pil.equals("2")) {
                kodesupplier = txtKodeSupplierKhusus.getText();
                namasupplier = txtNamaSupplierKhusus.getText();
            } else if (pil.equals("3")) {
                kodesupplier = txtKodeSupplier2.getText();
                namasupplier = txtNamaSupplier2.getText();
            }
            jScrollPane4.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            ja.executeQuery("select IDSUPPLIER AS ID,NAMA,ALAMAT from SUPPLIER  where STATUSAKTIF='0' AND (IDSUPPLIER like '" + kodesupplier + "%' or lower(NAMA) like '%" + kodesupplier.toLowerCase() + "%')");
            jScrollPane4.getViewport().remove(jTable4);
            jTable4 = new JTable(ja);
            jTable4.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable4KeyPressed(evt);
                }
            });
            TableColumn col = jTable4.getColumnModel().getColumn(0);
            col.setPreferredWidth(4);
            col = jTable4.getColumnModel().getColumn(1);
            col.setPreferredWidth(250);
            col = jTable4.getColumnModel().getColumn(2);
            col.setPreferredWidth(300);
            jTable4.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane4.getViewport().add(jTable4);
            jScrollPane4.repaint();
            ja.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataHutang(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtSupplier.getText() + "'";
        //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,CAST(JUMLAH as varchar(50)) as JUMLAH,CAST(JUMLAH - JUMLAHBAYAR AS varchar(50)) as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtSupplier.getText() + "'";
        th.executeQuery(sql);
        jScrollPane2.getViewport().remove(jTable2);
        jTable2 = new JTable(th);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable2.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
        jTable2.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane2.getViewport().add(jTable2);
        jScrollPane2.repaint();
    }

    private void reloadDataHutangBayar(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "";
        if (TxtFilter.getText().equals("")) {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND STATUS='BELUM LUNAS'";
        } else {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND STATUS='BELUM LUNAS'";
        }
        //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND STATUS='BELUM LUNAS'";
        th.executeQuery(sql);
        jScrollPane1.getViewport().remove(jTable1);
        jTable1 = new JTable(th);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane1.repaint();
    }

    void reloadDataHutangBayarLunas(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "";
        if (TxtFilter.getText().equals("")) {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        } else {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        }

        //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        th.executeQuery(sql);
        jScrollPane1.getViewport().remove(jTable1);
        jTable1 = new JTable(th);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane1.repaint();
    }

    void reloadDataHutangBayarKhusus(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "";
        if (TxtFilter1.getText().equals("")) {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplierKhusus.getText() + "' AND STATUS='BELUM LUNAS'";
        } else {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplierKhusus.getText() + "' AND NOFAKTUR='" + TxtFilter1.getText() + "' AND STATUS='BELUM LUNAS'";
        }
        // String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,CAST(JUMLAH as varchar(50)) as JUMLAH,CAST(JUMLAH - JUMLAHBAYAR AS varchar(50)) as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND STATUS='BELUM LUNAS'";
        th.executeQuery(sql);
        jScrollPane5.getViewport().remove(jTable5);
        jTable5 = new JTable(th);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jTable5.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable5.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
        jTable5.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane5.getViewport().add(jTable5);
        jScrollPane5.repaint();
    }

    void reloadDataHutangBayarLunasKhusus(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "";
        if (TxtFilter1.getText().equals("")) {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplierKhusus.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        } else {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplierKhusus.getText() + "' AND NOFAKTUR='" + TxtFilter1.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        }
        //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
        th.executeQuery(sql);
        jScrollPane5.getViewport().remove(jTable5);
        jTable5 = new JTable(th);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jTable5.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable5.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
        jTable5.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane5.getViewport().add(jTable5);
        jScrollPane5.repaint();
    }

    void prosesSimpan(Connection c, String pil) throws SQLException {
        ht.setKODEHUTANG(txtKodeHutang.getText());
        ht.setIDPEMBELIAN(0);
        //ht.setTANGGAL(java.sql.Date.valueOf(tglHutang.getText()));
        ht.setTANGGAL(tglHutang.getText());
        ht.setJUMLAH(Double.parseDouble(txtJumlahHutang.getValue().toString()));
        ht.setIDSUPPLIER(txtSupplier.getText());
        //ht.setJATUHTEMPO(java.sql.Date.valueOf(tglJatuhTempo.getText()));
        ht.setJATUHTEMPO(tglJatuhTempo.getText());
        ht.setKETERANGAN(txtKeterangan.getText());
        ht.setSTATUS("1");
        boolean stat;
        if (pil.equals("0")) {
            ht.setID(hutangDao.getID(c));
            stat = hutangDao.insertIntoHUTANG(c, ht);
        } else {
            stat = hutangDao.updateHUTANG(c, IDHUTANG, ht);
        }
    }

    void reloadDataPelunasan(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            //String sql = "SELECT * FROM HUTANGBAYAR where IDHUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM HUTANGBAYAR where IDHUTANG=" + hutangid + "";
            th.executeQuery(sql);
            jScrollPane3.getViewport().remove(jTable3);
            jTable3 = new JTable(th);
            jTable3.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable3.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane3.getViewport().add(jTable3);
            jScrollPane3.repaint();
            th.close();
        } catch (Exception e) {
        }
    }

    void reloadDataPelunasanFaktur(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            //String sql = "SELECT * FROM HUTANGBAYAR where IDHUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM HUTANGBAYAR where IDHUTANG=" + hutangid + "";
            th.executeQuery(sql);
            jScrollPane8.getViewport().remove(jTable8);
            jTable8 = new JTable(th);
            jTable8.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable8.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane8.getViewport().add(jTable8);
            jScrollPane8.repaint();
            th.close();
        } catch (Exception e) {
        }
    }

    void reloadDataPelunasanKhusus() {
        try {
            JDBCAdapter th = new JDBCAdapter(cm);
            //String sql = "SELECT * FROM HUTANGBAYAR where IDHUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM HUTANGBAYAR order by 1";
            th.executeQuery(sql);
            jScrollPane6.getViewport().remove(jTable6);
            jTable6 = new JTable(th);
            jTable6.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable6.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane6.getViewport().add(jTable6);
            jScrollPane6.repaint();
            th.close();
        } catch (Exception e) {
        }
    }

    public void settingTgl(Connection c) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tglHutang.getText())));
        tglJatuhTempo.setSelectedDate(cal);
    }

    public boolean cekperiode(Connection c, String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSBUKU='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }
        rs.close();
        s.close();
        return hasil1;

    }

    public boolean cekperiodeAda(Connection c, String bul) throws SQLException {
        //String periode = thn + "." + bln;
        boolean ada = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + bul + "'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                ada = true;
            }
        }
        rs.close();
        s.close();
        return ada;
    }

    private void buatTabel() {

        String sqlJurnal = "CREATE TABLE HUTANGBAYAR (\n"
                + "    `ID` INT(10) primary key ,\n"
                + "    `KODEHUTANGBAYAR` VARCHAR(20),\n"
                + "    `IDHUTANG` INT(10) unique,\n"
                + "    `TANGGAL` DATE,\n"
                + "    `JUMLAH` DOUBLE(17),\n"
                + "    `REF` VARCHAR(20) DEFAULT '0',\n"
                + "    STATUS varchar(1) DEFAULT '0');";
        //String sqlDO = "create table dopenjualan(IDPENJUALAN int,IDDO int, KODEBARANG varchar(20))";
        try {
            statm.execute(sqlJurnal);
            //  stat.execute(sqlDO);
        } catch (SQLException ex) {
        }
    }

    private void hapusTabel() {
        try {
            statm.execute("drop table hutangbayar");
        } catch (SQLException ex) {
        }
    }

    double getTotal() {
        double temp = 0.0;
        try {
            ResultSet rs = statm.executeQuery("select sum(jumlah) from hutangbayar");
            if (rs.next()) {
                temp = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    void settingtombol(boolean simp, boolean edit, boolean hapus) {
        btnInsert.setEnabled(simp);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(hapus);
    }

    void settingtombol1(boolean simp, boolean hapus) {
        btnSimpan.setEnabled(simp);
        btnHapusBayarHutang.setEnabled(hapus);
        btnSimpan1.setEnabled(simp);
        btnHapusBayarHutang1.setEnabled(hapus);
    }

    void prosesUpdateLog(Connection c) {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        try {
            String ketlog = "";
            lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
            lh.setTANGGAL(tanggallog);
            lh.setJAM(jamlog);
            if (aksilog.equals("InsertHutang")) {
                lh.setTABEL("THUTANG");
                lh.setNOREFF(txtKodeHutang.getText());
                ketlog = "Insert Data Hutang " + txtKodeHutang.getText() + " Supplier " + txtNamaSupplier.getText() + " (" + txtSupplier.getText() + ")";
            } else if (aksilog.equals("UpdateHutang")) {
                lh.setTABEL("THUTANG");
                lh.setNOREFF(txtKodeHutang.getText());
                ketlog = "Update Data Hutang " + txtKodeHutang.getText() + " Supplier " + txtNamaSupplier.getText() + " (" + txtSupplier.getText() + ")";
            } else if (aksilog.equals("DeleteHutang")) {
                lh.setTABEL("THUTANG");
                lh.setNOREFF(txtKodeHutang.getText());
                ketlog = "Delete Data Hutang " + txtKodeHutang.getText() + " Supplier " + txtNamaSupplier.getText() + " (" + txtSupplier.getText() + ")";
            } else if (aksilog.equals("InsertHutangBayar")) {
                lh.setTABEL("THUTANGBAYAR");
                lh.setNOREFF(txtNoTrans.getText());
                ketlog = "Insert Data Hutang Bayar " + txtNoTrans.getText() + " IDHUTANG " + hutangid + " Supplier " + txtNamaSupplier1.getText() + " (" + txtKodeSupplier1.getText() + ")";
            } else if (aksilog.equals("DeleteHutangBayar")) {
                lh.setTABEL("THUTANGBAYAR");
                lh.setNOREFF(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
                ketlog = "Delete Data Hutang Bayar " + jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString() + " IDHUTANG " + jTable3.getValueAt(jTable3.getSelectedRow(), 2) + " Supplier " + txtNamaSupplier1.getText() + " (" + txtKodeSupplier1.getText() + ") Keterangan " + txtDesk.getText();
            } else if (aksilog.equals("InsertHutangBayarF")) {
                lh.setTABEL("THUTANGBAYAR");
                lh.setNOREFF(txtNoTrans1.getText());
                ketlog = "Insert Data Hutang Bayar " + txtNoTrans1.getText() + " IDHUTANG " + hutangid + " Supplier " + txtNamaSupplier2.getText() + " (" + txtKodeSupplier2.getText() + ")";
            } else if (aksilog.equals("DeleteHutangBayarF")) {
                lh.setTABEL("THUTANGBAYAR");
                lh.setNOREFF(jTable8.getValueAt(jTable8.getSelectedRow(), 1).toString());
                ketlog = "Delete Data Hutang Bayar " + jTable8.getValueAt(jTable8.getSelectedRow(), 1).toString() + " IDHUTANG " + jTable8.getValueAt(jTable8.getSelectedRow(), 2) + " Supplier " + txtNamaSupplier2.getText() + " (" + txtKodeSupplier2.getText() + ") Keterangan " + txtDesk1.getText();
            } else if (aksilog.equals("InsertJurnal")) {
                lh.setTABEL("TJURNAL");
                lh.setNOREFF(txtNoRef1.getText());
                ketlog = "Insert Data Jurnal " + txtNoRef1.getText() + " Supplier " + txtNamaSupplierKhusus.getText() + " (" + txtKodeSupplierKhusus.getText() + ")";
            }

            lh.setAKSI(aksilog);
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    void prosesUpdateLogKhusus(Connection c) {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        try {
            String ketlog = "";
            lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
            lh.setTANGGAL(tanggallog);
            lh.setJAM(jamlog);
            if (aksilog.equals("InsertHutangBayar")) {
                lh.setTABEL("THUTANGBAYAR");
                lh.setNOREFF(htkd);
                ketlog = "Insert Data Hutang Bayar " + htkd + " IDHUTANG " + htid + " Supplier " + txtNamaSupplierKhusus.getText() + " (" + txtKodeSupplierKhusus.getText() + ")";
            }
            lh.setAKSI(aksilog);
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    void cektombol() {
        if (JavarieSoftApp.groupuser.equals("Accounting")) {
            settingtombol(false, false, false);
            settingtombol1(true, false);
        } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
            settingtombol(true, false, false);
            settingtombol1(true, true);
        } else if (JavarieSoftApp.groupuser.equals("KaGudang")) {
            settingtombol(false, false, false);
            settingtombol1(false, false);
        } else if (JavarieSoftApp.groupuser.equals("Operator")) {
            settingtombol(false, false, false);
            settingtombol1(false, false);
        } else if (JavarieSoftApp.groupuser.equals("Pajak")) {
            settingtombol(false, false, false);
            settingtombol1(false, false);
        }
    }

    void reloadDataHutangTotal(Connection c, String idp) {
        try {
            String sql = "select sum(vh.JUMLAH - vh.JUMLAHBAYAR) from view_hutang vh where vh.idsupplier='" + idp + "'";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                if (rs.getDouble(1) != 0) {
                    if (pilSupplier.equals("0")) {
                        LabelTotalHutang1.setText(df.format(rs.getDouble(1)));
                    } else if (pilSupplier.equals("1")) {
                        LabelTotalHutang2.setText(df.format(rs.getDouble(1)));
                        txtTotalHutang.setValue(rs.getDouble(1));
                    } else if (pilSupplier.equals("2")) {
                        LabelTotalHutang3.setText(df.format(rs.getDouble(1)));
                    }
                }
            }
            rs.close();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getFormatTextDouble(JFormattedTextField t) {
        double h = 0;
        if (t.getValue() != null) {
            h = Double.parseDouble(t.getValue().toString());
        }
        return h;
    }

    void simpan(Connection c) throws SQLException, ClassNotFoundException {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodeSupplier1.getText().equals("")) || (txtTotalHutang.getText().equals("")) || (txtJumlahBayar.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodeSupplier1.requestFocus();
            } else {
                String tgal[] = Util.split(tglBayar.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertHutangBayar";
                        hutangbayar h = new hutangbayar();
                        h.setID(hutangbayarDao.getID(c));
                        h.setIDHUTANG(Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
                        h.setKODEHUTANGBAYAR(txtNoTrans.getText());
                        h.setTANGGAL(tglBayar.getText());
                        h.setJUMLAH(Double.parseDouble(txtJumlahBayar.getValue().toString()));
                        if (!ref.equals("")) {
                            h.setREF(ref);
                            DialogPembelian.status = true;
                        } else {
                            h.setREF("0");
                            DialogPembelian.status = false;
                        }
                        if (hutangbayarDao.insertIntoHUTANGBAYAR(c, h)) {
                            JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Ok");
                        } else {
                            JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Gagal");
                        }
                        if (Double.parseDouble(txtTotalHutang.getValue().toString()) == Double.parseDouble(txtJumlahBayar.getValue().toString())) {
                            ht = hutangDao.getDetails(c, Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
                            ht.setSTATUS("0");
                            hutangDao.updateHUTANG(c, Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()), ht);
                        }
                        if (ref.equals("")) {
                            insertJurnal(c);
                            insertRinciJurnal(c);
                        }
                        prosesUpdateLog(c);
                        reloadDataPelunasan(c);
                        if (pilihan.isSelected()) {
                            reloadDataHutangBayarLunas(c);
                        } else {
                            reloadDataHutangBayar(c);
                        }
                        //reloadDataHutangBayar();
                        reloadDataHutangTotal(c, txtKodeSupplier1.getText());
                        kosongBayar(c);
                        ref = "";

//                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodeSupplier1.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodeSupplier1.requestFocus();
                }
            }
        } else {
            txtKodeSupplier1.requestFocus();
        }
    }

    void insertJurnalFaktur() {
        jurnal j = new jurnal();
        jurnalDao dbjurnal = new jurnalDao();

        try {
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(txtNoTrans1.getText());
            //j.setTANGGAL(java.sql.Date.valueOf(tglBayar.getText()));
            j.setTANGGAL(tglBayar1.getText());
            j.setDESKRIPSI(txtKetBayarHutang1.getText());
            boolean insertIntoJURNAL = jurnalDao.insertIntoJURNAL(c, j);
        } catch (Exception ex) {
        }
    }

    void insertRinciJurnalFaktur() {
        Statement s;
        try {
            s = c.createStatement();
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, txtKodeSupplier2.getText()).getKODEAKUN() + "'," + txtJumlahBayar1.getValue().toString() + ",0,1,'')");
            if (cboCaraBayar2.getSelectedIndex() == 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + txtJumlahBayar1.getValue().toString() + ",2,'')");
            } else {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bankDao.getDetails(c, Integer.parseInt(ID[cboBank2.getSelectedIndex()])).getKODEAKUN() + "',0," + txtJumlahBayar1.getValue().toString() + ",2,'')");
            }
            s.close();
        } catch (Exception ex) {
        }
    }

    private void reloadDataHutangBayarFaktur() {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter2.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND STATUS='BELUM LUNAS'";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND STATUS='BELUM LUNAS'";
            }
            //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND STATUS='BELUM LUNAS'";
            th.executeQuery(sql);
            jScrollPane7.getViewport().remove(jTable7);
            jTable7 = new JTable(th);
            jTable7.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable7MouseClicked(evt);
                }
            });
            jTable7.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable7.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
            jTable7.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane7.getViewport().add(jTable7);
            jScrollPane7.repaint();
            th.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataHutangBayarLunasFaktur() {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter2.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + IdkodeSupplier + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            }

            //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,NOFAKTURSUPPLIER,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_HUTANG where IDSUPPLIER='" + txtKodeSupplier1.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            th.executeQuery(sql);
            jScrollPane7.getViewport().remove(jTable7);
            jTable7 = new JTable(th);
            jTable7.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable7MouseClicked(evt);
                }
            });
            jTable7.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable7.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
            jTable7.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane7.getViewport().add(jTable7);
            jScrollPane7.repaint();
            th.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void kosongBayarFaktur() {
        txtNoTrans1.setText("");
        //txtKodeSupplier1.setText("");
        //txtNamaSupplier1.setText("");
        txtTotalHutang2.setText("");
        txtJumlahBayar1.setText("");
        txtNoTrans1.setText(hutangbayarDao.getKodeHutangBayar(c));
    }
}
