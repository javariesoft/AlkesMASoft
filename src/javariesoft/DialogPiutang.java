/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DialogPiutang.java
 *
 * Created on Dec 29, 2011, 9:34:00 AM
 */
package javariesoft;

import com.erv.db.bankDao;
import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.db.penjualanDao;
import com.erv.db.piutangDao;
import com.erv.db.piutangbayarDao;
import com.erv.db.settingDao;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.fungsi.DecimalFormatRenderer;
import com.erv.fungsi.Fungsi;
import com.erv.model.jurnal;
import com.erv.model.pelanggan;
import com.erv.model.penjualan;
import com.erv.model.piutang;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.controller.PiutangController;
import com.erv.db.KontrolTanggalDao;
import com.erv.exception.JavarieException;
import com.erv.function.PrintfFormat;
import com.erv.model.pesan;
import com.erv.model.piutangbayar;
import java.awt.HeadlessException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

/**
 *
 * @author JAVARIESOFT
 */
public class DialogPiutang extends javax.swing.JDialog {

    int IDJurnal = 0, IDjrn = 0;
    String kodePelanggan = "";
    String ID[];
    String IDAKUN[];
    int IDJUAL = 0;
    int IDPIUTANG = 0;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("###,###,###,###.00");
    Connection cm;
    Statement statm;
    piutang p;
    String pilPelanggan = "0";
    String IdkodePelanggan = "";
    pelangganDao dbpel;
    penjualanDao dbpj;
    int piutangid = 0;
    int piutangidKhusus = 0;
    int piutangidKhususcounter = 0;
    int countId = 0;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    int ptid = 0;
    String ptkd = "";
    List<pesan> pesan = new ArrayList<pesan>();
    String kodeakun[];
    String namaAkun[];
    Connection c = null;
    PiutangController controller;

    /**
     * Creates new form DialogPiutang
     */
    public DialogPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            controller = new PiutangController(this);
            c = koneksi.getKoneksiJ();
            setting(c);
            txtTotalPiutang.setValue(0);
            txtJumlahBayar.setValue(0);
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DialogPiutang(java.awt.Frame parent, boolean modal, String idpenjualan) {
        super(parent, modal);
        initComponents();
        penjualan pj;
        try {
            controller = new PiutangController(this);
            c = koneksi.getKoneksiJ();
            setting(c);
            pj = dbpj.getDetails(c, Integer.parseInt(idpenjualan));
            txtKodePelanggan1.setText(pj.getKODEPELANGGAN());
            txtNamaPelanggan1.setText(dbpel.getDetails(pj.getKODEPELANGGAN()).getNAMA());
            reloadDataPiutangBayar(c);
            jTabbedPane1.setSelectedIndex(1);
        } catch (SQLException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JTextField getTxtNoRef() {
        return txtNoRef;
    }

    public JTable getjTable6() {
        return jTable6;
    }

    private void setting(Connection c) throws SQLException, ClassNotFoundException {
        cm = koneksi.getKoneksiM();
        statm = cm.createStatement();
        p = new piutang();
        kosongPiutang(c);
        //kosongBayar();
        kosongBayarAwal(c);
        kosongBayarFaktur(c);
        kosongBayarKhusus(c);
        isiCombo(c);
        buatTabel();
        dbpel = new pelangganDao(c);
        dbpj = new penjualanDao();
        lh = new loghistory();
        lhdao = new loghistoryDao();
        tglPiutang.setDateFormat(d);
        tglBayar.setDateFormat(d);
        tglBayar1.setDateFormat(d);
        tglPenerimaanKhusus.setDateFormat(d);
        tglJatuhTempo.setDateFormat(d);
        jScrollPane4.setVisible(false);
        jScrollPane4.setSize(700, 150);
        cektombol();
        jTabbedPane1.setTitleAt(1, "");
        jTabbedPane1.setEnabledAt(1, false);
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
        txtKodePiutang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoReff = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tglPiutang = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        tglJatuhTempo = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        txtKodePelanggan = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtJumlahPiutang = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        LabelTotalPiutang1 = new javax.swing.JLabel();
        cboAkun = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtNoTrans = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tglBayar = new datechooser.beans.DateChooserCombo();
        jLabel14 = new javax.swing.JLabel();
        cboCaraBayar = new javax.swing.JComboBox();
        cboBank = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtKodePelanggan1 = new javax.swing.JTextField();
        txtNamaPelanggan1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtTotalPiutang = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJumlahBayar = new javax.swing.JFormattedTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        pilihan = new javax.swing.JCheckBox();
        btnHapusBayarPiutang = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtDesk = new javax.swing.JTextField();
        btnClear1 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        TxtFilter = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtKetTerimaPiutang = new javax.swing.JTextField();
        btnKeluar1 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        LabelTotalPiutang2 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtNoRef2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        KodeTransaksiKhusus = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tglPenerimaanKhusus = new datechooser.beans.DateChooserCombo();
        jLabel9 = new javax.swing.JLabel();
        txtKodePelangganKhusus = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cboCaraBayar1 = new javax.swing.JComboBox();
        cboBank1 = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtJumlahBayarKhusus = new javax.swing.JFormattedTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        txtNamaPelangganKhusus = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtSisa = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTotalPiutang1 = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        txtNoRef1 = new javax.swing.JTextField();
        pilihKhusus = new javax.swing.JCheckBox();
        txtcari = new javax.swing.JTextField();
        TxtFilter1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtKetTerimaPiutangKhusus = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        LabelTotalPiutang3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnTambahPiutangKhusus = new javax.swing.JButton();
        btnHapusK = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnKeluar2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        btnSimpanSemua = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        txtTotalKhusus = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtNoRef = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        btnDeletePK = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        txtNoTrans1 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        tglBayar1 = new datechooser.beans.DateChooserCombo();
        jLabel36 = new javax.swing.JLabel();
        cboCaraBayar2 = new javax.swing.JComboBox();
        cboBank2 = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        txtKodePelanggan2 = new javax.swing.JTextField();
        txtNamaPelanggan2 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        txtTotalPiutang2 = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        txtJumlahBayar1 = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btnSimpan1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        pilihan1 = new javax.swing.JCheckBox();
        btnHapusBayarPiutang1 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        txtDesk1 = new javax.swing.JTextField();
        btnClear2 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        TxtFilter2 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtKetTerimaPiutang1 = new javax.swing.JTextField();
        btnKeluar3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogPiutang.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
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
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable4.columnModel.title0")); // NOI18N
            jTable4.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable4.columnModel.title1")); // NOI18N
            jTable4.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable4.columnModel.title2")); // NOI18N
            jTable4.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable4.columnModel.title3")); // NOI18N
        }

        panelCool1.add(jScrollPane4);
        jScrollPane4.setBounds(150, 150, 50, 20);

        jTabbedPane1.setFont(resourceMap.getFont("jTabbedPane1.font")); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        txtKodePiutang.setEditable(false);
        txtKodePiutang.setFont(resourceMap.getFont("txtKodePiutang.font")); // NOI18N
        txtKodePiutang.setText(resourceMap.getString("txtKodePiutang.text")); // NOI18N
        txtKodePiutang.setName("txtKodePiutang"); // NOI18N
        jPanel1.add(txtKodePiutang);
        txtKodePiutang.setBounds(140, 10, 160, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 10, 100, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(330, 10, 100, 15);

        txtNoReff.setFont(resourceMap.getFont("txtNoReff.font")); // NOI18N
        txtNoReff.setText(resourceMap.getString("txtNoReff.text")); // NOI18N
        txtNoReff.setName("txtNoReff"); // NOI18N
        jPanel1.add(txtNoReff);
        txtNoReff.setBounds(450, 10, 160, 21);

        jLabel8.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(330, 40, 100, 15);

        tglPiutang.setFieldFont(resourceMap.getFont("tglPiutang.dch_combo_fieldFont")); // NOI18N
        tglPiutang.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglPiutangOnCommit(evt);
            }
        });
        jPanel1.add(tglPiutang);
        tglPiutang.setBounds(140, 40, 155, 20);

        jLabel11.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel1.add(jLabel11);
        jLabel11.setBounds(20, 40, 100, 15);

        tglJatuhTempo.setFieldFont(resourceMap.getFont("tglJatuhTempo.dch_combo_fieldFont")); // NOI18N
        jPanel1.add(tglJatuhTempo);
        tglJatuhTempo.setBounds(450, 40, 155, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 160, 100, 15);

        txtKodePelanggan.setFont(resourceMap.getFont("txtKodePelanggan.font")); // NOI18N
        txtKodePelanggan.setText(resourceMap.getString("txtKodePelanggan.text")); // NOI18N
        txtKodePelanggan.setName("txtKodePelanggan"); // NOI18N
        txtKodePelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePelangganActionPerformed(evt);
            }
        });
        txtKodePelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelangganKeyPressed(evt);
            }
        });
        jPanel1.add(txtKodePelanggan);
        txtKodePelanggan.setBounds(140, 70, 160, 21);

        txtNamaPelanggan.setEditable(false);
        txtNamaPelanggan.setFont(resourceMap.getFont("txtNamaPelanggan.font")); // NOI18N
        txtNamaPelanggan.setText(resourceMap.getString("txtNamaPelanggan.text")); // NOI18N
        txtNamaPelanggan.setName("txtNamaPelanggan"); // NOI18N
        jPanel1.add(txtNamaPelanggan);
        txtNamaPelanggan.setBounds(310, 70, 340, 21);

        jLabel12.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 70, 100, 15);

        txtJumlahPiutang.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahPiutang.setFont(resourceMap.getFont("txtJumlahPiutang.font")); // NOI18N
        txtJumlahPiutang.setName("txtJumlahPiutang"); // NOI18N
        txtJumlahPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJumlahPiutangKeyPressed(evt);
            }
        });
        jPanel1.add(txtJumlahPiutang);
        txtJumlahPiutang.setBounds(140, 100, 280, 21);

        jLabel13.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 100, 100, 15);

        txtKeterangan.setFont(resourceMap.getFont("txtKeterangan.font")); // NOI18N
        txtKeterangan.setText(resourceMap.getString("txtKeterangan.text")); // NOI18N
        txtKeterangan.setName("txtKeterangan"); // NOI18N
        jPanel1.add(txtKeterangan);
        txtKeterangan.setBounds(140, 130, 510, 21);

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
        btnInsert.setBounds(20, 190, 100, 30);

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
        btnUpdate.setBounds(130, 190, 110, 30);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jPanel1.add(jSeparator2);
        jSeparator2.setBounds(20, 230, 860, 10);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(20, 240, 860, 360);

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
        btnDelete.setBounds(250, 190, 110, 30);

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
        btnCancel.setBounds(370, 190, 110, 30);

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
        btnKeluar.setBounds(490, 190, 120, 30);

        jLabel31.setFont(resourceMap.getFont("jLabel31.font")); // NOI18N
        jLabel31.setForeground(resourceMap.getColor("jLabel31.foreground")); // NOI18N
        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N
        jPanel1.add(jLabel31);
        jLabel31.setBounds(560, 100, 120, 20);

        LabelTotalPiutang1.setFont(resourceMap.getFont("jLabel31.font")); // NOI18N
        LabelTotalPiutang1.setForeground(resourceMap.getColor("LabelTotalPiutang1.foreground")); // NOI18N
        LabelTotalPiutang1.setText(resourceMap.getString("LabelTotalPiutang1.text")); // NOI18N
        LabelTotalPiutang1.setName("LabelTotalPiutang1"); // NOI18N
        jPanel1.add(LabelTotalPiutang1);
        LabelTotalPiutang1.setBounds(680, 100, 210, 20);

        cboAkun.setFont(resourceMap.getFont("cboAkun.font")); // NOI18N
        cboAkun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboAkun.setName("cboAkun"); // NOI18N
        jPanel1.add(cboAkun);
        cboAkun.setBounds(140, 158, 190, 22);

        jLabel29.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N
        jPanel1.add(jLabel29);
        jLabel29.setBounds(20, 130, 100, 15);

        jTabbedPane1.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(null);

        jLabel15.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel2.add(jLabel15);
        jLabel15.setBounds(20, 20, 110, 15);

        txtNoTrans.setEditable(false);
        txtNoTrans.setFont(resourceMap.getFont("txtNoTrans.font")); // NOI18N
        txtNoTrans.setText(resourceMap.getString("txtNoTrans.text")); // NOI18N
        txtNoTrans.setName("txtNoTrans"); // NOI18N
        jPanel2.add(txtNoTrans);
        txtNoTrans.setBounds(150, 20, 150, 22);

        jLabel16.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel2.add(jLabel16);
        jLabel16.setBounds(20, 50, 110, 15);

        tglBayar.setFieldFont(resourceMap.getFont("tglBayar.dch_combo_fieldFont")); // NOI18N
        jPanel2.add(tglBayar);
        tglBayar.setBounds(150, 50, 155, 20);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel2.add(jLabel14);
        jLabel14.setBounds(320, 50, 100, 16);

        cboCaraBayar.setFont(resourceMap.getFont("cboCaraBayar.font")); // NOI18N
        cboCaraBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK" }));
        cboCaraBayar.setName("cboCaraBayar"); // NOI18N
        jPanel2.add(cboCaraBayar);
        cboCaraBayar.setBounds(410, 50, 80, 21);

        cboBank.setFont(resourceMap.getFont("cboBank.font")); // NOI18N
        cboBank.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank.setName("cboBank"); // NOI18N
        jPanel2.add(cboBank);
        cboBank.setBounds(510, 50, 140, 21);

        jLabel17.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel2.add(jLabel17);
        jLabel17.setBounds(20, 80, 100, 15);

        txtKodePelanggan1.setFont(resourceMap.getFont("txtKodePelanggan1.font")); // NOI18N
        txtKodePelanggan1.setText(resourceMap.getString("txtKodePelanggan1.text")); // NOI18N
        txtKodePelanggan1.setName("txtKodePelanggan1"); // NOI18N
        txtKodePelanggan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePelanggan1ActionPerformed(evt);
            }
        });
        txtKodePelanggan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelanggan1KeyPressed(evt);
            }
        });
        jPanel2.add(txtKodePelanggan1);
        txtKodePelanggan1.setBounds(150, 80, 160, 22);

        txtNamaPelanggan1.setEditable(false);
        txtNamaPelanggan1.setFont(resourceMap.getFont("txtNamaPelanggan1.font")); // NOI18N
        txtNamaPelanggan1.setText(resourceMap.getString("txtNamaPelanggan1.text")); // NOI18N
        txtNamaPelanggan1.setName("txtNamaPelanggan1"); // NOI18N
        jPanel2.add(txtNamaPelanggan1);
        txtNamaPelanggan1.setBounds(320, 80, 490, 22);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 204, 890, 140);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 350, 100, 16);

        txtTotalPiutang.setEditable(false);
        txtTotalPiutang.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalPiutang.setFont(resourceMap.getFont("txtTotalPiutang.font")); // NOI18N
        txtTotalPiutang.setName("txtTotalPiutang"); // NOI18N
        jPanel2.add(txtTotalPiutang);
        txtTotalPiutang.setBounds(140, 350, 220, 21);

        jLabel7.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 140, 100, 15);

        txtJumlahBayar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtJumlahBayar.setFont(resourceMap.getFont("txtJumlahBayar.font")); // NOI18N
        txtJumlahBayar.setName("txtJumlahBayar"); // NOI18N
        txtJumlahBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahBayarActionPerformed(evt);
            }
        });
        jPanel2.add(txtJumlahBayar);
        txtJumlahBayar.setBounds(150, 140, 260, 21);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jPanel2.add(jSeparator3);
        jSeparator3.setBounds(10, 384, 860, 10);

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
        btnSimpan.setBounds(10, 390, 100, 30);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setName("jTable3"); // NOI18N
        jScrollPane3.setViewportView(jTable3);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(10, 430, 890, 200);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        pilihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihanActionPerformed(evt);
            }
        });
        jPanel2.add(pilihan);
        pilihan.setBounds(10, 180, 120, 23);

        btnHapusBayarPiutang.setFont(resourceMap.getFont("btnHapusBayarPiutang.font")); // NOI18N
        btnHapusBayarPiutang.setIcon(resourceMap.getIcon("btnHapusBayarPiutang.icon")); // NOI18N
        btnHapusBayarPiutang.setText(resourceMap.getString("btnHapusBayarPiutang.text")); // NOI18N
        btnHapusBayarPiutang.setName("btnHapusBayarPiutang"); // NOI18N
        btnHapusBayarPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBayarPiutangActionPerformed(evt);
            }
        });
        jPanel2.add(btnHapusBayarPiutang);
        btnHapusBayarPiutang.setBounds(110, 390, 100, 30);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel2.add(jLabel23);
        jLabel23.setBounds(20, 110, 120, 15);

        txtDesk.setName("txtDesk"); // NOI18N
        jPanel2.add(txtDesk);
        txtDesk.setBounds(540, 400, 360, 20);

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
        btnClear1.setBounds(220, 390, 100, 30);

        jLabel24.setFont(resourceMap.getFont("jLabel24.font")); // NOI18N
        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N
        jPanel2.add(jLabel24);
        jLabel24.setBounds(130, 180, 20, 20);

        TxtFilter.setFont(resourceMap.getFont("TxtFilter.font")); // NOI18N
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
        TxtFilter.setBounds(250, 180, 120, 22);

        jLabel26.setFont(resourceMap.getFont("jLabel26.font")); // NOI18N
        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N
        jPanel2.add(jLabel26);
        jLabel26.setBounds(430, 400, 130, 16);

        jLabel27.setFont(resourceMap.getFont("jLabel27.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel2.add(jLabel27);
        jLabel27.setBounds(140, 180, 110, 20);

        txtKetTerimaPiutang.setText(resourceMap.getString("txtKetTerimaPiutang.text")); // NOI18N
        txtKetTerimaPiutang.setName("txtKetTerimaPiutang"); // NOI18N
        jPanel2.add(txtKetTerimaPiutang);
        txtKetTerimaPiutang.setBounds(149, 110, 660, 20);

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
        btnKeluar1.setBounds(320, 390, 100, 30);

        jLabel30.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        jLabel30.setForeground(resourceMap.getColor("jLabel30.foreground")); // NOI18N
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N
        jPanel2.add(jLabel30);
        jLabel30.setBounds(570, 180, 120, 20);

        LabelTotalPiutang2.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        LabelTotalPiutang2.setForeground(resourceMap.getColor("LabelTotalPiutang2.foreground")); // NOI18N
        LabelTotalPiutang2.setText(resourceMap.getString("LabelTotalPiutang2.text")); // NOI18N
        LabelTotalPiutang2.setName("LabelTotalPiutang2"); // NOI18N
        jPanel2.add(LabelTotalPiutang2);
        LabelTotalPiutang2.setBounds(690, 180, 210, 20);

        jLabel33.setFont(resourceMap.getFont("jLabel33.font")); // NOI18N
        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N
        jPanel2.add(jLabel33);
        jLabel33.setBounds(350, 20, 60, 16);

        txtNoRef2.setEditable(false);
        txtNoRef2.setFont(resourceMap.getFont("txtNoRef2.font")); // NOI18N
        txtNoRef2.setName("txtNoRef2"); // NOI18N
        jPanel2.add(txtNoRef2);
        txtNoRef2.setBounds(410, 20, 240, 22);

        jTabbedPane1.addTab(resourceMap.getString("jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(null);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel3.add(jLabel5);
        jLabel5.setBounds(30, 20, 100, 15);

        KodeTransaksiKhusus.setEditable(false);
        KodeTransaksiKhusus.setFont(resourceMap.getFont("KodeTransaksiKhusus.font")); // NOI18N
        KodeTransaksiKhusus.setText(resourceMap.getString("KodeTransaksiKhusus.text")); // NOI18N
        KodeTransaksiKhusus.setName("KodeTransaksiKhusus"); // NOI18N
        jPanel3.add(KodeTransaksiKhusus);
        KodeTransaksiKhusus.setBounds(150, 20, 150, 21);

        jLabel6.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel3.add(jLabel6);
        jLabel6.setBounds(30, 50, 90, 15);

        tglPenerimaanKhusus.setFieldFont(resourceMap.getFont("tglPenerimaanKhusus.dch_combo_fieldFont")); // NOI18N
        jPanel3.add(tglPenerimaanKhusus);
        tglPenerimaanKhusus.setBounds(150, 50, 155, 20);

        jLabel9.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel3.add(jLabel9);
        jLabel9.setBounds(30, 80, 110, 15);

        txtKodePelangganKhusus.setFont(resourceMap.getFont("txtKodePelangganKhusus.font")); // NOI18N
        txtKodePelangganKhusus.setText(resourceMap.getString("txtKodePelangganKhusus.text")); // NOI18N
        txtKodePelangganKhusus.setName("txtKodePelangganKhusus"); // NOI18N
        txtKodePelangganKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePelangganKhususActionPerformed(evt);
            }
        });
        txtKodePelangganKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelangganKhususKeyPressed(evt);
            }
        });
        jPanel3.add(txtKodePelangganKhusus);
        txtKodePelangganKhusus.setBounds(150, 80, 160, 21);

        jLabel18.setFont(resourceMap.getFont("jLabel18.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel3.add(jLabel18);
        jLabel18.setBounds(320, 50, 100, 16);

        cboCaraBayar1.setFont(resourceMap.getFont("cboCaraBayar1.font")); // NOI18N
        cboCaraBayar1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK", "LAIN" }));
        cboCaraBayar1.setName("cboCaraBayar1"); // NOI18N
        cboCaraBayar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCaraBayar1ActionPerformed(evt);
            }
        });
        jPanel3.add(cboCaraBayar1);
        cboCaraBayar1.setBounds(410, 50, 0, 22);

        cboBank1.setFont(resourceMap.getFont("cboBank1.font")); // NOI18N
        cboBank1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank1.setName("cboBank1"); // NOI18N
        jPanel3.add(cboBank1);
        cboBank1.setBounds(530, 50, 330, 22);

        jLabel10.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel3.add(jLabel10);
        jLabel10.setBounds(30, 110, 90, 15);

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
        txtJumlahBayarKhusus.setBounds(150, 110, 220, 21);

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable5.setName("jTable5"); // NOI18N
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel3.add(jScrollPane5);
        jScrollPane5.setBounds(20, 170, 870, 150);

        txtNamaPelangganKhusus.setEditable(false);
        txtNamaPelangganKhusus.setFont(resourceMap.getFont("txtKodePelangganKhusus.font")); // NOI18N
        txtNamaPelangganKhusus.setText(resourceMap.getString("txtNamaPelangganKhusus.text")); // NOI18N
        txtNamaPelangganKhusus.setName("txtNamaPelangganKhusus"); // NOI18N
        txtNamaPelangganKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPelangganKhususActionPerformed(evt);
            }
        });
        jPanel3.add(txtNamaPelangganKhusus);
        txtNamaPelangganKhusus.setBounds(320, 80, 410, 21);

        jLabel19.setFont(resourceMap.getFont("jLabel19.font")); // NOI18N
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel3.add(jLabel19);
        jLabel19.setBounds(550, 110, 70, 14);

        txtSisa.setEditable(false);
        txtSisa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtSisa.setFont(resourceMap.getFont("txtSisa.font")); // NOI18N
        txtSisa.setName("txtSisa"); // NOI18N
        jPanel3.add(txtSisa);
        txtSisa.setBounds(590, 110, 270, 21);

        jLabel20.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel3.add(jLabel20);
        jLabel20.setBounds(20, 330, 100, 16);

        txtTotalPiutang1.setEditable(false);
        txtTotalPiutang1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalPiutang1.setFont(resourceMap.getFont("txtTotalPiutang1.font")); // NOI18N
        txtTotalPiutang1.setName("txtTotalPiutang1"); // NOI18N
        jPanel3.add(txtTotalPiutang1);
        txtTotalPiutang1.setBounds(120, 330, 190, 22);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jPanel3.add(jSeparator1);
        jSeparator1.setBounds(20, 360, 890, 10);

        jLabel21.setFont(resourceMap.getFont("jLabel21.font")); // NOI18N
        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel3.add(jLabel21);
        jLabel21.setBounds(350, 20, 60, 15);

        txtNoRef1.setEditable(false);
        txtNoRef1.setFont(resourceMap.getFont("txtNoRef1.font")); // NOI18N
        txtNoRef1.setText(resourceMap.getString("txtNoRef1.text")); // NOI18N
        txtNoRef1.setName("txtNoRef1"); // NOI18N
        jPanel3.add(txtNoRef1);
        txtNoRef1.setBounds(410, 20, 240, 21);

        pilihKhusus.setFont(resourceMap.getFont("pilihKhusus.font")); // NOI18N
        pilihKhusus.setText(resourceMap.getString("pilihKhusus.text")); // NOI18N
        pilihKhusus.setName("pilihKhusus"); // NOI18N
        pilihKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihKhususActionPerformed(evt);
            }
        });
        jPanel3.add(pilihKhusus);
        pilihKhusus.setBounds(30, 140, 130, 20);

        txtcari.setText(resourceMap.getString("txtcari.text")); // NOI18N
        txtcari.setName("txtcari"); // NOI18N
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        jPanel3.add(txtcari);
        txtcari.setBounds(410, 50, 110, 20);

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
        TxtFilter1.setBounds(300, 140, 210, 21);

        jLabel25.setFont(resourceMap.getFont("jLabel25.font")); // NOI18N
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel3.add(jLabel25);
        jLabel25.setBounds(180, 140, 120, 15);

        jLabel28.setFont(resourceMap.getFont("jLabel28.font")); // NOI18N
        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N
        jPanel3.add(jLabel28);
        jLabel28.setBounds(330, 330, 120, 16);

        txtKetTerimaPiutangKhusus.setText(resourceMap.getString("txtKetTerimaPiutangKhusus.text")); // NOI18N
        txtKetTerimaPiutangKhusus.setName("txtKetTerimaPiutangKhusus"); // NOI18N
        jPanel3.add(txtKetTerimaPiutangKhusus);
        txtKetTerimaPiutangKhusus.setBounds(450, 330, 440, 20);

        jLabel32.setFont(resourceMap.getFont("LabelTotalPiutang3.font")); // NOI18N
        jLabel32.setForeground(resourceMap.getColor("jLabel32.foreground")); // NOI18N
        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N
        jPanel3.add(jLabel32);
        jLabel32.setBounds(550, 140, 120, 20);

        LabelTotalPiutang3.setFont(resourceMap.getFont("LabelTotalPiutang3.font")); // NOI18N
        LabelTotalPiutang3.setForeground(resourceMap.getColor("LabelTotalPiutang3.foreground")); // NOI18N
        LabelTotalPiutang3.setText(resourceMap.getString("LabelTotalPiutang3.text")); // NOI18N
        LabelTotalPiutang3.setName("LabelTotalPiutang3"); // NOI18N
        jPanel3.add(LabelTotalPiutang3);
        LabelTotalPiutang3.setBounds(670, 140, 210, 20);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(null);

        btnTambahPiutangKhusus.setFont(resourceMap.getFont("btnTambahPiutangKhusus.font")); // NOI18N
        btnTambahPiutangKhusus.setIcon(resourceMap.getIcon("btnTambahPiutangKhusus.icon")); // NOI18N
        btnTambahPiutangKhusus.setText(resourceMap.getString("btnTambahPiutangKhusus.text")); // NOI18N
        btnTambahPiutangKhusus.setName("btnTambahPiutangKhusus"); // NOI18N
        btnTambahPiutangKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPiutangKhususActionPerformed(evt);
            }
        });
        jPanel5.add(btnTambahPiutangKhusus);
        btnTambahPiutangKhusus.setBounds(20, 60, 110, 30);

        btnHapusK.setFont(resourceMap.getFont("btnHapusK.font")); // NOI18N
        btnHapusK.setIcon(resourceMap.getIcon("btnHapusK.icon")); // NOI18N
        btnHapusK.setText(resourceMap.getString("btnHapusK.text")); // NOI18N
        btnHapusK.setName("btnHapusK"); // NOI18N
        btnHapusK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKActionPerformed(evt);
            }
        });
        jPanel5.add(btnHapusK);
        btnHapusK.setBounds(20, 90, 110, 30);

        btnClear.setFont(resourceMap.getFont("btnClear.font")); // NOI18N
        btnClear.setIcon(resourceMap.getIcon("btnClear.icon")); // NOI18N
        btnClear.setText(resourceMap.getString("btnClear.text")); // NOI18N
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel5.add(btnClear);
        btnClear.setBounds(20, 120, 110, 30);

        btnKeluar2.setFont(resourceMap.getFont("btnKeluar2.font")); // NOI18N
        btnKeluar2.setIcon(resourceMap.getIcon("btnKeluar2.icon")); // NOI18N
        btnKeluar2.setText(resourceMap.getString("btnKeluar2.text")); // NOI18N
        btnKeluar2.setName("btnKeluar2"); // NOI18N
        btnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar2ActionPerformed(evt);
            }
        });
        jPanel5.add(btnKeluar2);
        btnKeluar2.setBounds(20, 150, 110, 30);

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTable6.setName("jTable6"); // NOI18N
        jScrollPane6.setViewportView(jTable6);

        jPanel5.add(jScrollPane6);
        jScrollPane6.setBounds(140, 60, 710, 120);

        btnSimpanSemua.setFont(resourceMap.getFont("btnSimpanSemua.font")); // NOI18N
        btnSimpanSemua.setIcon(resourceMap.getIcon("btnSimpanSemua.icon")); // NOI18N
        btnSimpanSemua.setText(resourceMap.getString("btnSimpanSemua.text")); // NOI18N
        btnSimpanSemua.setName("btnSimpanSemua"); // NOI18N
        btnSimpanSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSemuaActionPerformed(evt);
            }
        });
        jPanel5.add(btnSimpanSemua);
        btnSimpanSemua.setBounds(140, 190, 180, 30);

        jLabel22.setFont(resourceMap.getFont("jLabel22.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel5.add(jLabel22);
        jLabel22.setBounds(610, 190, 70, 15);

        txtTotalKhusus.setEditable(false);
        txtTotalKhusus.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalKhusus.setFont(resourceMap.getFont("txtTotalKhusus.font")); // NOI18N
        txtTotalKhusus.setName("txtTotalKhusus"); // NOI18N
        jPanel5.add(txtTotalKhusus);
        txtTotalKhusus.setBounds(690, 190, 160, 21);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jLabel44.setText(resourceMap.getString("jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N
        jPanel6.add(jLabel44);

        jLabel45.setFont(resourceMap.getFont("jLabel45.font")); // NOI18N
        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N
        jPanel6.add(jLabel45);

        txtNoRef.setFont(resourceMap.getFont("txtNoRef.font")); // NOI18N
        txtNoRef.setText(resourceMap.getString("txtNoRef.text")); // NOI18N
        txtNoRef.setName("txtNoRef"); // NOI18N
        txtNoRef.setPreferredSize(new java.awt.Dimension(200, 21));
        jPanel6.add(txtNoRef);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jPanel6.add(btnFilter);

        btnDeletePK.setFont(resourceMap.getFont("btnDeletePK.font")); // NOI18N
        btnDeletePK.setText(resourceMap.getString("btnDeletePK.text")); // NOI18N
        btnDeletePK.setName("btnDeletePK"); // NOI18N
        btnDeletePK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePKActionPerformed(evt);
            }
        });
        jPanel6.add(btnDeletePK);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(140, 10, 710, 40);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(20, 370, 870, 230);

        jTabbedPane1.addTab("Penerimaan Khusus", jPanel3);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(null);

        jLabel34.setFont(resourceMap.getFont("jLabel34.font")); // NOI18N
        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N
        jPanel4.add(jLabel34);
        jLabel34.setBounds(20, 20, 110, 16);

        txtNoTrans1.setEditable(false);
        txtNoTrans1.setFont(resourceMap.getFont("txtNoTrans1.font")); // NOI18N
        txtNoTrans1.setText(resourceMap.getString("txtNoTrans1.text")); // NOI18N
        txtNoTrans1.setName("txtNoTrans1"); // NOI18N
        jPanel4.add(txtNoTrans1);
        txtNoTrans1.setBounds(150, 20, 150, 22);

        jLabel35.setFont(resourceMap.getFont("jLabel35.font")); // NOI18N
        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        jPanel4.add(jLabel35);
        jLabel35.setBounds(20, 50, 110, 16);

        tglBayar1.setFieldFont(resourceMap.getFont("tglBayar1.dch_combo_fieldFont")); // NOI18N
        jPanel4.add(tglBayar1);
        tglBayar1.setBounds(150, 50, 155, 20);

        jLabel36.setFont(resourceMap.getFont("jLabel36.font")); // NOI18N
        jLabel36.setText(resourceMap.getString("jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N
        jPanel4.add(jLabel36);
        jLabel36.setBounds(320, 50, 100, 16);

        cboCaraBayar2.setFont(resourceMap.getFont("cboCaraBayar2.font")); // NOI18N
        cboCaraBayar2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KAS", "BANK" }));
        cboCaraBayar2.setName("cboCaraBayar2"); // NOI18N
        jPanel4.add(cboCaraBayar2);
        cboCaraBayar2.setBounds(410, 50, 80, 22);

        cboBank2.setFont(resourceMap.getFont("cboBank2.font")); // NOI18N
        cboBank2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank2.setName("cboBank2"); // NOI18N
        jPanel4.add(cboBank2);
        cboBank2.setBounds(510, 50, 140, 22);

        jLabel37.setFont(resourceMap.getFont("jLabel37.font")); // NOI18N
        jLabel37.setText(resourceMap.getString("jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N
        jPanel4.add(jLabel37);
        jLabel37.setBounds(20, 80, 100, 16);

        txtKodePelanggan2.setFont(resourceMap.getFont("txtKodePelanggan2.font")); // NOI18N
        txtKodePelanggan2.setText(resourceMap.getString("txtKodePelanggan2.text")); // NOI18N
        txtKodePelanggan2.setName("txtKodePelanggan2"); // NOI18N
        txtKodePelanggan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePelanggan2ActionPerformed(evt);
            }
        });
        txtKodePelanggan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelanggan2KeyPressed(evt);
            }
        });
        jPanel4.add(txtKodePelanggan2);
        txtKodePelanggan2.setBounds(150, 80, 160, 22);

        txtNamaPelanggan2.setEditable(false);
        txtNamaPelanggan2.setFont(resourceMap.getFont("txtNamaPelanggan2.font")); // NOI18N
        txtNamaPelanggan2.setText(resourceMap.getString("txtNamaPelanggan2.text")); // NOI18N
        txtNamaPelanggan2.setName("txtNamaPelanggan2"); // NOI18N
        jPanel4.add(txtNamaPelanggan2);
        txtNamaPelanggan2.setBounds(320, 80, 490, 22);

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable7.setName("jTable7"); // NOI18N
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);

        jPanel4.add(jScrollPane7);
        jScrollPane7.setBounds(10, 164, 890, 180);

        jLabel38.setFont(resourceMap.getFont("jLabel38.font")); // NOI18N
        jLabel38.setText(resourceMap.getString("jLabel38.text")); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N
        jPanel4.add(jLabel38);
        jLabel38.setBounds(20, 350, 100, 16);

        txtTotalPiutang2.setEditable(false);
        txtTotalPiutang2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtTotalPiutang2.setFont(resourceMap.getFont("txtTotalPiutang2.font")); // NOI18N
        txtTotalPiutang2.setName("txtTotalPiutang2"); // NOI18N
        jPanel4.add(txtTotalPiutang2);
        txtTotalPiutang2.setBounds(140, 350, 220, 22);

        jLabel39.setFont(resourceMap.getFont("jLabel39.font")); // NOI18N
        jLabel39.setText(resourceMap.getString("jLabel39.text")); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N
        jPanel4.add(jLabel39);
        jLabel39.setBounds(500, 350, 100, 16);

        txtJumlahBayar1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtJumlahBayar1.setFont(resourceMap.getFont("txtJumlahBayar1.font")); // NOI18N
        txtJumlahBayar1.setName("txtJumlahBayar1"); // NOI18N
        jPanel4.add(txtJumlahBayar1);
        txtJumlahBayar1.setBounds(590, 350, 260, 22);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jPanel4.add(jSeparator4);
        jSeparator4.setBounds(10, 384, 860, 10);

        btnSimpan1.setFont(resourceMap.getFont("btnSimpan1.font")); // NOI18N
        btnSimpan1.setIcon(resourceMap.getIcon("btnSimpan1.icon")); // NOI18N
        btnSimpan1.setText(resourceMap.getString("btnSimpan1.text")); // NOI18N
        btnSimpan1.setName("btnSimpan1"); // NOI18N
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan1);
        btnSimpan1.setBounds(10, 390, 100, 30);

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTable8.setName("jTable8"); // NOI18N
        jScrollPane8.setViewportView(jTable8);

        jPanel4.add(jScrollPane8);
        jScrollPane8.setBounds(10, 430, 890, 175);

        pilihan1.setFont(resourceMap.getFont("pilihan1.font")); // NOI18N
        pilihan1.setText(resourceMap.getString("pilihan1.text")); // NOI18N
        pilihan1.setName("pilihan1"); // NOI18N
        pilihan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihan1ActionPerformed(evt);
            }
        });
        jPanel4.add(pilihan1);
        pilihan1.setBounds(10, 140, 120, 25);

        btnHapusBayarPiutang1.setFont(resourceMap.getFont("btnHapusBayarPiutang1.font")); // NOI18N
        btnHapusBayarPiutang1.setIcon(resourceMap.getIcon("btnHapusBayarPiutang1.icon")); // NOI18N
        btnHapusBayarPiutang1.setText(resourceMap.getString("btnHapusBayarPiutang1.text")); // NOI18N
        btnHapusBayarPiutang1.setName("btnHapusBayarPiutang1"); // NOI18N
        btnHapusBayarPiutang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBayarPiutang1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnHapusBayarPiutang1);
        btnHapusBayarPiutang1.setBounds(110, 390, 100, 30);

        jLabel40.setFont(resourceMap.getFont("jLabel40.font")); // NOI18N
        jLabel40.setText(resourceMap.getString("jLabel40.text")); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N
        jPanel4.add(jLabel40);
        jLabel40.setBounds(20, 110, 120, 16);

        txtDesk1.setName("txtDesk1"); // NOI18N
        jPanel4.add(txtDesk1);
        txtDesk1.setBounds(540, 400, 360, 20);

        btnClear2.setFont(resourceMap.getFont("btnClear2.font")); // NOI18N
        btnClear2.setIcon(resourceMap.getIcon("btnClear2.icon")); // NOI18N
        btnClear2.setText(resourceMap.getString("btnClear2.text")); // NOI18N
        btnClear2.setName("btnClear2"); // NOI18N
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });
        jPanel4.add(btnClear2);
        btnClear2.setBounds(220, 390, 100, 30);

        jLabel41.setFont(resourceMap.getFont("jLabel41.font")); // NOI18N
        jLabel41.setText(resourceMap.getString("jLabel41.text")); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N
        jPanel4.add(jLabel41);
        jLabel41.setBounds(130, 140, 20, 20);

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
        TxtFilter2.setBounds(250, 140, 120, 22);

        jLabel42.setFont(resourceMap.getFont("jLabel42.font")); // NOI18N
        jLabel42.setText(resourceMap.getString("jLabel42.text")); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N
        jPanel4.add(jLabel42);
        jLabel42.setBounds(430, 400, 130, 16);

        jLabel43.setFont(resourceMap.getFont("jLabel43.font")); // NOI18N
        jLabel43.setText(resourceMap.getString("jLabel43.text")); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N
        jPanel4.add(jLabel43);
        jLabel43.setBounds(140, 140, 110, 20);

        txtKetTerimaPiutang1.setName("txtKetTerimaPiutang1"); // NOI18N
        jPanel4.add(txtKetTerimaPiutang1);
        txtKetTerimaPiutang1.setBounds(149, 110, 660, 20);

        btnKeluar3.setFont(resourceMap.getFont("btnKeluar3.font")); // NOI18N
        btnKeluar3.setIcon(resourceMap.getIcon("btnKeluar3.icon")); // NOI18N
        btnKeluar3.setText(resourceMap.getString("btnKeluar3.text")); // NOI18N
        btnKeluar3.setName("btnKeluar3"); // NOI18N
        btnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnKeluar3);
        btnKeluar3.setBounds(320, 390, 100, 30);

        jTabbedPane1.addTab(resourceMap.getString("jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        panelCool1.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 910, 640);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(947, 701));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void jTable4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable4KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            pelanggan b = new pelanggan();
            b = dbpel.getDetails(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilPelanggan.equals("0")) {
                txtKodePelanggan.setText(b.getKODEPELANGGAN());
                txtNamaPelanggan.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataPiutang(c);
                reloadDataPiutangTotal(c, txtKodePelanggan.getText());
                txtJumlahPiutang.requestFocus();
            } else if (pilPelanggan.equals("1")) {
                txtKodePelanggan1.setText(b.getKODEPELANGGAN());
                txtNamaPelanggan1.setText(b.getNAMA());
                txtKetTerimaPiutang.setText("Terima Piutang Dari " + txtNamaPelanggan1.getText());
                IdkodePelanggan = txtKodePelanggan1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataPiutangBayarLunas(c);
                } else {
                    reloadDataPiutangBayar(c);
                }
                reloadDataPiutangTotal(c, txtKodePelanggan1.getText());
            } else if (pilPelanggan.equals("2")) {
                txtKodePelangganKhusus.setText(b.getKODEPELANGGAN());
                txtNamaPelangganKhusus.setText(b.getNAMA());
                txtKetTerimaPiutangKhusus.setText("Terima Piutang Dari " + txtNamaPelangganKhusus.getText());
                jScrollPane4.setVisible(false);
                piutangidKhusus = piutangbayarDao.getID(c);
                piutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6, 9));
                if (pilihKhusus.isSelected()) {
                    reloadDataPiutangBayarLunasKhusus(c);
                } else {
                    reloadDataPiutangBayarKhusus(c);
                }
                reloadDataPiutangTotal(c, txtKodePelangganKhusus.getText());
            } else if (pilPelanggan.equals("3")) {
                txtKodePelanggan2.setText(b.getKODEPELANGGAN());
                txtNamaPelanggan2.setText(b.getNAMA());
                txtKetTerimaPiutang1.setText("Terima Piutang Dari " + txtNamaPelanggan2.getText());
                IdkodePelanggan = txtKodePelanggan2.getText();
                jScrollPane4.setVisible(false);
                if (pilihan1.isSelected()) {
                    reloadDataPiutangBayarLunasFaktur(c);
                } else {
                    reloadDataPiutangBayarFaktur(c);
                }
                //reloadDataPiutangTotal(c, txtKodePelanggan2.getText());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}//GEN-LAST:event_jTable4KeyPressed

private void btnHapusBayarPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBayarPiutangActionPerformed
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
                            aksilog = "DeletePiutangBayar";
                            sf = c.createStatement();
//                        sf.execute("delete from JURNAL where KODEJURNAL='" + jTable3.getValueAt(jTable3.getSelectedRow(), 1) + "'");
                            sf.execute("delete from PIUTANGBAYAR where ID='" + jTable3.getValueAt(jTable3.getSelectedRow(), 0) + "'");
                            JOptionPane.showMessageDialog(this, "Delete Ok");
                            String sql = "SELECT JUMLAH - JUMLAHBAYAR as SISA from VIEW_PIUTANG where ID='" + piutangid + "'";
                            ResultSet rs = sf.executeQuery(sql);
                            if (rs.next()) {
                                if (rs.getDouble(1) != 0) {
                                    sf.executeUpdate("update PIUTANG set STATUS='1' where ID='" + piutangid + "'");
                                }
                            }
                            rs.close();
                            sf.close();
                            prosesUpdateLog(c);
                            reloadDataPelunasan(c);
                            if (pilihan.isSelected()) {
                                reloadDataPiutangBayarLunas(c);
                            } else {
                                reloadDataPiutangBayar(c);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                            txtKodePelanggan1.requestFocus();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data ini berkaitan dengan pembayaran piutang khusus.. !");
                    txtKodePelanggan1.requestFocus();
                }
            }
        } else {
            txtKodePelanggan1.requestFocus();
        }
    } catch (HeadlessException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback " + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex1);
        }

        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback " + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex1);
        }

        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NumberFormatException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback " + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex1);
        }

        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            c.createStatement().execute("set autocommit true");
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_btnHapusBayarPiutangActionPerformed

private void pilihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihanActionPerformed
    try {
        // TODO add your handling code here:
        if (txtKodePelanggan1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Pelanggan...");
            txtKodePelanggan1.requestFocus();
        } else {
            if (pilihan.isSelected()) {
                reloadDataPiutangBayarLunas(c);
            } else {
                reloadDataPiutangBayar(c);

            }
        }
    } catch (HeadlessException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_pilihanActionPerformed

private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            double jumlahBayar = Double.parseDouble(txtJumlahBayar.getValue().toString());
            String kodePelanggan = txtKodePelanggan1.getText();
            String desk = txtKetTerimaPiutang.getText();
            double totalPiutang = Double.parseDouble(txtTotalPiutang.getValue().toString());

            if (jumlahBayar == 0) {
                JOptionPane.showMessageDialog(null, "Jumlah Bayar Belum Masuk.. !");
                txtJumlahBayar.requestFocus();
            } else if (kodePelanggan.equals("")) {
                JOptionPane.showMessageDialog(null, "Pelanggan Belum Masuk");
                txtKodePelanggan1.requestFocus();
            } else if (desk.equals("")) {
                JOptionPane.showMessageDialog(null, "Keterangan Belum Masuk");
                txtKodePelanggan1.requestFocus();
            } else if (totalPiutang == 0) {
                JOptionPane.showMessageDialog(null, "Piutang Tidak Ada");
                txtKodePelanggan1.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglBayar.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglBayar.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertPiutangBayar";
                        List<piutang> piutangList = piutangDao.getAllPiutangPlgBL(c, kodePelanggan);
                        int kdbank = Integer.parseInt(ID[cboBank.getSelectedIndex()]);
                        piutangDao.bayarPiutang(c, piutangList, jumlahBayar, tglBayar.getText(), desk, cboCaraBayar.getSelectedIndex(), kdbank, kodePelanggan);
                        prosesUpdateLog(c);
                        reloadDataPelunasan(c);
                        if (pilihan.isSelected()) {
                            reloadDataPiutangBayarLunas(c);
                        } else {
                            reloadDataPiutangBayar(c);
                        }
                        //reloadDataPiutangBayar();
                        reloadDataPiutangTotal(c, txtKodePelanggan1.getText());
                        kosongBayar(c);
                        c.commit();
                        JOptionPane.showMessageDialog(this, "Transaksi Piutang Ok..");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodePelanggan1.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodePelanggan1.requestFocus();
                }
            }
        } else {
            txtKodePelanggan1.requestFocus();
        }
    } catch (SQLException throwable) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, new Object[]{
                "Rollback Terjadi error dengan pesan :",
                throwable.getMessage()
            });

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    } catch (HeadlessException throwable) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, new Object[]{
                "Rollback Terjadi error dengan pesan :",
                throwable.getMessage()
            });

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    } catch (NumberFormatException throwable) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, new Object[]{
                "Rollback Terjadi error dengan pesan :",
                throwable.getMessage()
            });

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    } catch (ClassNotFoundException throwable) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, new Object[]{
                "Rollback Terjadi error dengan pesan :",
                throwable.getMessage()
            });

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    } catch (JavarieException throwable) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, new Object[]{
                "Rollback Terjadi error dengan pesan :",
                throwable.getMessage()
            });

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//GEN-LAST:event_btnSimpanActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    try {
        txtTotalPiutang.setValue(jTable1.getValueAt(jTable1.getSelectedRow(), 4));
        piutangid = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        reloadDataPelunasan(c);

    } catch (NumberFormatException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable1MouseClicked

private void txtKodePelanggan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelanggan1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilPelanggan = "1";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtKodePelanggan1KeyPressed

private void txtKodePelanggan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelanggan1ActionPerformed
    try {
        // TODO add your handling code here:
        reloadPelanggan(c, "1");

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtKodePelanggan1ActionPerformed

private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
    try {
        // TODO add your handling code here:
        cektombol();
        kosongPiutang(c);

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnCancelActionPerformed

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodePelanggan.getText().equals("")) || (txtJumlahPiutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelanggan.requestFocus();
            } else {
                aksilog = "DeleteHutang";
                piutangDao.deleteFromPIUTANG(c, IDPIUTANG);
                reloadDataPiutang(c);
                prosesUpdateLog(c);
                kosongPiutang(c);
                cektombol();
            }
        } else {
            txtKodePelanggan.requestFocus();

        }
    } catch (SQLException ex) {
        Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnDeleteActionPerformed

private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
    try {
        // TODO add your handling code here:
        Calendar cld = Calendar.getInstance();
        p = piutangDao.getDetails(c, Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString()));
        IDPIUTANG = p.getID();
        txtKodePiutang.setText(p.getKODEPIUTANG());
        txtNoReff.setText(p.getIDPENJUALAN() + "");
        try {
            cld.setTime(d.parse(p.getTANGGAL()));

        } catch (ParseException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        tglPiutang.setSelectedDate(cld);
        try {
            cld.setTime(d.parse(p.getJATUHTEMPO()));

        } catch (ParseException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        tglJatuhTempo.setSelectedDate(cld);
        txtKodePelanggan.setText(p.getIDPELANGGAN());
        txtNamaPelanggan.setText(dbpel.getDetails(txtKodePelanggan.getText()).getNAMA());
        txtJumlahPiutang.setValue(p.getJUMLAH());
        txtKeterangan.setText(p.getKETERANGAN());
        cektombol();

    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class
                .getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(DialogHutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable2MouseClicked

private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
    try {
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Diedit?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodePelanggan.getText().equals("")) || (txtJumlahPiutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelanggan.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglPiutang.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglPiutang.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "UpdatePiutang";
                        prosesSimpan(c, "1");
                        reloadDataPiutang(c);
                        prosesUpdateLog(c);
                        kosongPiutang(c);
                        cektombol();
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodePelanggan.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodePelanggan.requestFocus();
                }
            }
        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (SQLException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (HeadlessException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (NumberFormatException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_btnUpdateActionPerformed

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
// TODO add your handling code here:
    try {
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodePelanggan.getText().equals("")) || (txtJumlahPiutang.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelanggan.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglPiutang.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglPiutang.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertPiutang";
                        prosesSimpan(c, "0");
                        insertJurnal(c);
                        insertRinciJurnalPiutang(c);
                        reloadDataPiutang(c);
                        prosesUpdateLog(c);
                        kosongPiutang(c);
                        cektombol();
                        reloadDataPiutangTotal(c, txtKodePelanggan.getText());
                        c.commit();
                        JOptionPane.showMessageDialog(this, "Entri Data Piutang Ok");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodePelanggan.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodePelanggan.requestFocus();
                }
            }

        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (HeadlessException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (NumberFormatException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (SQLException e) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Roolback " + e.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_btnInsertActionPerformed

private void txtJumlahPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahPiutangKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtKeterangan.requestFocus();
    }
}//GEN-LAST:event_txtJumlahPiutangKeyPressed

private void txtKodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelangganKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilPelanggan = "0";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtKodePelangganKeyPressed

private void txtKodePelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelangganActionPerformed
    try {
        // TODO add your handling code here:
        reloadPelanggan(c, "0");

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtKodePelangganActionPerformed

private void tglPiutangOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglPiutangOnCommit
    try {
        // TODO add your handling code here:
        settingTgl(c);

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_tglPiutangOnCommit

private void txtKodePelangganKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelangganKhususKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        pilPelanggan = "2";
        jScrollPane4.setVisible(true);
        jTable4.requestFocus();
        jTable4.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane4.setVisible(false);
    }
}//GEN-LAST:event_txtKodePelangganKhususKeyPressed

private void txtKodePelangganKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelangganKhususActionPerformed
    try {
        // TODO add your handling code here:
        reloadPelanggan(c, "2");

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtKodePelangganKhususActionPerformed

private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
    try {
        // TODO add your handling code here:
        txtTotalPiutang1.setValue(jTable5.getValueAt(jTable5.getSelectedRow(), 4));
        piutangid = Integer.parseInt(jTable5.getValueAt(jTable5.getSelectedRow(), 0).toString());
        //reloadDataPelunasanKhusus();

    } catch (NumberFormatException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable5MouseClicked

private void btnTambahPiutangKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPiutangKhususActionPerformed
// TODO add your handling code here:
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Tambah Data..?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodePelangganKhusus.getText().equals("")) || (txtJumlahBayarKhusus.getText().equals("")) || (txtTotalPiutang1.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelangganKhusus.requestFocus();
            } else {
                if (txtJumlahBayarKhusus.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Jumlah Bayar Harap Diisi .. !");
                    txtJumlahBayarKhusus.requestFocus();
                } else {
                    if (txtSisa.getText().equals("0")) {
                        JOptionPane.showMessageDialog(null, "Jumlah Piutang yang akan dibayarkan berlebih .. !");
                        txtKodePelangganKhusus.requestFocus();
                    } else {
                        String tgal[] = Util.split(tglPenerimaanKhusus.getText(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c, per)) {
                                statm.execute("INSERT INTO PIUTANGBAYAR "
                                        + "VALUES (" + (piutangidKhusus) + ",\n"
                                        + "        '" + (KodeTransaksiKhusus.getText().substring(0, 5) + "" + new PrintfFormat("%04d").sprintf(piutangidKhususcounter)) + "',\n"
                                        + "        " + piutangid + ",\n"
                                        + "        '" + tglPenerimaanKhusus.getText() + "',\n"
                                        + "        " + ((Double.parseDouble(txtSisa.getValue().toString()) > Double.parseDouble(txtTotalPiutang1.getValue().toString())) ? txtTotalPiutang1.getValue() : txtSisa.getValue()) + ",\n"
                                        + "        '" + txtNoRef1.getText() + "',\n"
                                        + "        '" + ((Double.parseDouble(txtSisa.getValue().toString()) >= Double.parseDouble(txtTotalPiutang1.getValue().toString())) ? "0" : "1") + "');");
                                reloadDataPelunasanKhusus();
                                txtSisa.setValue((Double.parseDouble(txtJumlahBayarKhusus.getValue().toString()) - getTotal()));
                                txtTotalKhusus.setValue(getTotal());
                                piutangidKhusus++;
                                piutangidKhususcounter++;
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                txtKodePelangganKhusus.requestFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            txtKodePelangganKhusus.requestFocus();
                        }
                    }
                }
            }
        } else {
            txtKodePelangganKhusus.requestFocus();

        }
    } catch (HeadlessException ex) {
        Logger.getLogger(DialogHutang.class
                .getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
        Logger.getLogger(DialogHutang.class
                .getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(DialogHutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnTambahPiutangKhususActionPerformed

private void txtJumlahBayarKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususActionPerformed
// TODO add your handling code here:
    txtSisa.setValue(txtJumlahBayarKhusus.getValue());
    jTable5.requestFocus();
}//GEN-LAST:event_txtJumlahBayarKhususActionPerformed

private void txtJumlahBayarKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_txtJumlahBayarKhususKeyPressed

private void btnHapusKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            statm.execute("delete from piutangbayar where ID=" + jTable6.getValueAt(jTable6.getSelectedRow(), 0) + "");
            txtSisa.setValue((Double.parseDouble(txtJumlahBayarKhusus.getValue().toString()) - getTotal()));
            txtTotalKhusus.setValue(getTotal());
            reloadDataPelunasanKhusus();
            piutangidKhusus = piutangidKhusus - 1;
            piutangidKhususcounter = piutangidKhususcounter - 1;
        } else {
            txtKodePelangganKhusus.requestFocus();

        }
    } catch (SQLException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnHapusKActionPerformed

private void txtJumlahBayarKhususFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJumlahBayarKhususFocusLost
// TODO add your handling code here:
    txtSisa.setText(txtJumlahBayarKhusus.getText());
    jTable5.requestFocus();
}//GEN-LAST:event_txtJumlahBayarKhususFocusLost

private void btnSimpanSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSemuaActionPerformed
    Statement stat = null;
    try {
        c.createStatement().execute("set autocommit false");
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodePelangganKhusus.getText().equals("")) || (txtJumlahBayarKhusus.getText().equals("")) || (cboBank1.getSelectedItem().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelangganKhusus.requestFocus();
            } else if (getFormatTextDouble(txtJumlahBayarKhusus) != getFormatTextDouble(txtTotalKhusus)) {
                JOptionPane.showMessageDialog(null, "Nilai Jumlah Bayar Tidak sama dengan Total Invoice,Pilih Nilai Piutang berikutnya... !");
                txtKodePelangganKhusus.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglPenerimaanKhusus.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglPenerimaanKhusus.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        aksilog = "InsertJurnal";
                        stat = c.createStatement();
                        pelangganDao dbplg = new pelangganDao(c);
                        ResultSet rs = statm.executeQuery("select * from piutangbayar order by 1");
                        stat.addBatch("INSERT INTO JURNAL "
                                + "VALUES (" + jurnalDao.getIDJurnal(c) + ",\n"
                                + "        '" + txtNoRef1.getText() + "',\n"
                                + "        '" + tglPenerimaanKhusus.getText() + "',\n"
                                + "        '" + txtKetTerimaPiutangKhusus.getText() + "');");
                        // if (cboCaraBayar1.getSelectedIndex() == 0) {
                        //    stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + settingDao.getAkun(c, "KAS") + "'," + txtJumlahBayarKhusus.getValue().toString() + ",0,1)");
                        // } else {
                        stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + IDAKUN[cboBank1.getSelectedIndex()] + "'," + txtJumlahBayarKhusus.getValue().toString() + ",0,1,'')");
                        // }
                        stat.addBatch("insert into RINCIJURNAL values(" + jurnalDao.getIDJurnal(c) + ",'" + dbplg.getDetails(txtKodePelangganKhusus.getText()).getKODEAKUN() + "',0," + txtJumlahBayarKhusus.getValue().toString() + ",2,'')");
                        stat.executeBatch();
                        prosesUpdateLog(c);
                        while (rs.next()) {
                            stat.addBatch("INSERT INTO PIUTANGBAYAR "
                                    + "VALUES (" + rs.getInt(1) + ",\n"
                                    + "        '" + rs.getString(2) + "',\n"
                                    + "        " + rs.getInt(3) + ",\n"
                                    + "        '" + rs.getDate(4) + "',\n"
                                    + "        " + rs.getDouble(5) + ",\n"
                                    + "        '" + rs.getString(6) + "');");
                            if (rs.getString(7).equals("0")) {
                                stat.addBatch("update piutang set status='" + 0 + "' where ID=" + rs.getInt(3) + "");
                            }
                            aksilog = "InsertPiutangBayar";
                            ptid = rs.getInt(3);
                            ptkd = rs.getString(2);
                            prosesUpdateLogKhusus(c);
                        }
                        stat.executeBatch();
                        // stat.execute("SET TRACE_LEVEL_FILE 0");
                        hapusTabel();
                        buatTabel();
                        reloadDataPelunasanKhusus();
                        kosongBayarKhusus(c);
                        txtKodePelangganKhusus.requestFocus();
                        if (pilihKhusus.isSelected()) {
                            reloadDataPiutangBayarLunasKhusus(c);
                        } else {
                            reloadDataPiutangBayarKhusus(c);
                        }
                        reloadDataPiutangTotal(c, txtKodePelangganKhusus.getText());
                        dbplg = null;
                        c.commit();
                        JOptionPane.showMessageDialog(null, "Simpan Data Ok");
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodePelangganKhusus.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodePelangganKhusus.requestFocus();
                }
            }
        } else {
            txtKodePelangganKhusus.requestFocus();
        }
        stat.close();
    } catch (Exception ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, ex.getMessage());

        } catch (SQLException ex1) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
    } finally {
        try {
            c.createStatement().execute("set autocommit true");
        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}//GEN-LAST:event_btnSimpanSemuaActionPerformed

private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    try {
        // TODO add your handling code here:
        hapusTabel();
        buatTabel();
        txtKodePelangganKhusus.requestFocus();
        reloadDataPelunasanKhusus();
        kosongBayarKhusus(c);

    } catch (SQLException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnClearActionPerformed

private void pilihKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihKhususActionPerformed
    try {
        // TODO add your handling code here:
        if (txtKodePelangganKhusus.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Pelanggan...");
            txtKodePelangganKhusus.requestFocus();
        } else {
            if (pilihKhusus.isSelected()) {
                reloadDataPiutangBayarLunasKhusus(c);
            } else {
                reloadDataPiutangBayarKhusus(c);

            }
        }
    } catch (HeadlessException ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_pilihKhususActionPerformed

private void btnClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear1ActionPerformed
    try {
        // TODO add your handling code here:
        kosongBayarAwal(c);
        jScrollPane1.getViewport().remove(jTable1);
        jScrollPane3.getViewport().remove(jTable3);
        txtKodePelanggan1.requestFocus();

    } catch (Exception ex) {
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnClear1ActionPerformed

private void TxtFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilterActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TxtFilterActionPerformed

private void TxtFilterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilterKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            pelanggan b = new pelanggan();
            b = dbpel.getDetails(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilPelanggan.equals("0")) {
                //txtKodePelanggan.setText(b.getKODEPELANGGAN());
                //txtNamaPelanggan.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataPiutang(c);
                txtJumlahPiutang.requestFocus();
            } else if (pilPelanggan.equals("1")) {
                //txtKodePelanggan1.setText(b.getKODEPELANGGAN());
                //txtNamaPelanggan1.setText(b.getNAMA());
                IdkodePelanggan = txtKodePelanggan1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataPiutangBayarLunas(c);
                } else {
                    reloadDataPiutangBayar(c);
                }
            } else if (pilPelanggan.equals("2")) {
                txtKodePelangganKhusus.setText(b.getKODEPELANGGAN());
                txtNamaPelangganKhusus.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                piutangidKhusus = piutangbayarDao.getID(c);
                piutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6, 9));

                if (pilihKhusus.isSelected()) {
                    reloadDataPiutangBayarLunasKhusus(c);
                } else {
                    reloadDataPiutangBayarKhusus(c);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}//GEN-LAST:event_TxtFilterKeyPressed

private void txtNamaPelangganKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPelangganKhususActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtNamaPelangganKhususActionPerformed

private void cboCaraBayar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCaraBayar1ActionPerformed
}//GEN-LAST:event_cboCaraBayar1ActionPerformed

private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
// TODO add your handling code here:
    //if (cboCaraBayar1.getSelectedItem().equals("LAIN")) {
    cboBank1.removeAllItems();
    try {
        String sql = "";
        if (JavarieSoftApp.groupuser.equals("Administrator")) {
            sql = "select * from PERKIRAAN where (TIPE='D' or TIPE='SD') and (kodeperkiraan like '%" + txtcari.getText() + "%' OR lower(NAMAPERKIRAAN) like '%" + txtcari.getText().toLowerCase() + "%') order by 2";
        } else {
            sql = "select * from PERKIRAAN where (kodeperkiraan ='11110' /*KAS(D)*/\n"
                    + "or kodeperkiraan like '11120.%' /*Bank(SD)*/\n"
                    + "or kodeperkiraan= '11202.2'   /*PPH Pasal 22*/\n"
                    + "or kodeperkiraan= '61101.21'   /*Ongkos Transfer*/\n"
                    + "or kodeperkiraan= '61101.30'   /*Biaya Promosi*/\n"
                    + "or kodeperkiraan= '21111.1'   /*PPN Keluaran*/\n"
                    + "or kodeperkiraan= '61101.27'   /*Biaya Rupa-rupa*/\n"
                    + "or kodeperkiraan= '61101.35'   /*Biaya Kerugian Brg Expired*/\n"
                    + "or kodeperkiraan= '61101.36'   /*Biaya Kerugian Brg Rusak*/\n"
                    + "or kodeperkiraan= '61101.40'   /*Biaya Potongan Tagihan Pelanggan*/\n"
                    + "or kodeperkiraan= '61101.34')   /*Biaya Piutang Tak Tertagih*/\n "
                    + "and (kodeperkiraan like '%" + txtcari.getText() + "%' OR lower(NAMAPERKIRAAN) like '%" + txtcari.getText().toLowerCase() + "%') order by 2";
        }
        Statement st1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //ResultSet rs1 = st1.executeQuery("select * from perkiraan where (tipe='D' or tipe='SD') and ( kodeperkiraan like '" + txtcari.getText() + "%' or lower(namaperkiraan) like '%" + txtcari.getText().toLowerCase() + "%')");
//        ResultSet rs1 = st1.executeQuery("select * from perkiraan where (tipe='SD' or kodeperkiraan ='11110' or kodeperkiraan ='31101' or kodeperkiraan ='41102') and ( kodeperkiraan like '%" + txtcari.getText() + "%' or lower(namaperkiraan) like '%" + txtcari.getText().toLowerCase() + "%')");
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
        Logger.getLogger(DialogPiutang.class
                .getName()).log(Level.SEVERE, null, ex);
    }
    // }
}//GEN-LAST:event_txtcariActionPerformed

private void TxtFilter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilter1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TxtFilter1ActionPerformed

private void TxtFilter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilter1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            pelanggan b = new pelanggan();
            b = dbpel.getDetails(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            if (pilPelanggan.equals("0")) {
                //txtKodePelanggan.setText(b.getKODEPELANGGAN());
                //txtNamaPelanggan.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                reloadDataPiutang(c);
                txtJumlahPiutang.requestFocus();
            } else if (pilPelanggan.equals("1")) {
                //txtKodePelanggan1.setText(b.getKODEPELANGGAN());
                //txtNamaPelanggan1.setText(b.getNAMA());
                IdkodePelanggan = txtKodePelanggan1.getText();
                jScrollPane4.setVisible(false);
                if (pilihan.isSelected()) {
                    reloadDataPiutangBayarLunas(c);
                } else {
                    reloadDataPiutangBayar(c);
                }
            } else if (pilPelanggan.equals("2")) {
                //txtKodePelangganKhusus.setText(b.getKODEPELANGGAN());
                //txtNamaPelangganKhusus.setText(b.getNAMA());
                jScrollPane4.setVisible(false);
                piutangidKhusus = piutangbayarDao.getID(c);
                piutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6, 9));
                if (pilihKhusus.isSelected()) {
                    reloadDataPiutangBayarLunasKhusus(c);
                } else {
                    reloadDataPiutangBayarKhusus(c);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
}//GEN-LAST:event_TxtFilter1KeyPressed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        dispose();

    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void btnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar2ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar2ActionPerformed

    private void txtJumlahBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBayarActionPerformed
        // TODO add your handling code here:
        btnSimpan.requestFocus();
    }//GEN-LAST:event_txtJumlahBayarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            statm.close();
            cm.close();
            c.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void txtKodePelanggan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelanggan2ActionPerformed
        // TODO add your handling code here:
        reloadPelanggan(c, "3");
    }//GEN-LAST:event_txtKodePelanggan2ActionPerformed

    private void txtKodePelanggan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelanggan2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            pilPelanggan = "3";
            jScrollPane4.setVisible(true);
            jTable4.requestFocus();
            jTable4.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane4.setVisible(false);
        }
    }//GEN-LAST:event_txtKodePelanggan2KeyPressed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        txtTotalPiutang2.setValue(jTable7.getValueAt(jTable7.getSelectedRow(), 4));
        piutangid = Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString());
        reloadDataPelunasanFaktur(c);
    }//GEN-LAST:event_jTable7MouseClicked

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        try {
            // TODO add your handling code here:
            c.createStatement().execute("set autocommit false");
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                if ((txtKodePelanggan2.getText().equals("")) || (txtTotalPiutang2.getText().equals("")) || (txtJumlahBayar1.getText().equals(""))) {
                    JOptionPane.showMessageDialog(this, "Data Belum Lengkap.. !");
                    txtKodePelanggan2.requestFocus();
                } else if (txtJumlahBayar1.getText().equals("0")) {
                    JOptionPane.showMessageDialog(this, "Jumlah Bayar Tidak Boleh 0");
                } else if (!KontrolTanggalDao.cekHarian(c, tglBayar1.getText())) {
                    JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                            + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                            + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                } else {
                    String tgal[] = Util.split(tglBayar1.getText(), "-");
                    String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                    if (cekperiodeAda(c, per)) {
                        if (cekperiode(c, per)) {
                            if (Double.parseDouble(txtTotalPiutang2.getValue().toString()) < Double.parseDouble(txtJumlahBayar1.getValue().toString())) {
                                JOptionPane.showMessageDialog(null, "Jumlah Bayar Besar Dari Jumlah Piutang.. !");
                                txtJumlahBayar1.requestFocus();
                            } else {
                                aksilog = "InsertPiutangBayar";
                                piutangbayar h = new piutangbayar();
                                h.setID(piutangbayarDao.getID(c));
                                h.setIDPIUTANG(Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString()));
                                h.setKODEPIUTANGBAYAR(txtNoTrans1.getText());
                                h.setTANGGAL(tglBayar1.getText());
                                h.setJUMLAH(Double.parseDouble(txtJumlahBayar1.getValue().toString()));
                                h.setREF("0");
                                if (piutangbayarDao.insertIntoPIUTANGBAYAR(c, h)) {
                                    JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Ok");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Entri Data Pelunasan Hutang Gagal");
                                }
                                if (Double.parseDouble(txtTotalPiutang2.getValue().toString()) == Double.parseDouble(txtJumlahBayar1.getValue().toString())) {
                                    p = piutangDao.getDetails(c, Integer.parseInt(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString()));
                                    p.setSTATUS("0");
                                    piutangDao.updatePIUTANG(c, p);
                                }
                                insertJurnalFaktur();
                                insertRinciJurnalFaktur();
                                prosesUpdateLog(c);
                                reloadDataPelunasanFaktur(c);
                                if (pilihan1.isSelected()) {
                                    reloadDataPiutangBayarLunasFaktur(c);
                                } else {
                                    reloadDataPiutangBayarFaktur(c);
                                }
                                //reloadDataPiutangBayar();
                                kosongBayarFaktur(c);
                                c.commit();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                            txtKodePelanggan1.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                        txtKodePelanggan1.requestFocus();
                    }
                }
            } else {
                txtKodePelanggan1.requestFocus();
            }
        } catch (HeadlessException ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "RollBack " + ex.getMessage());

            } catch (SQLException ex1) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            Logger
                    .getLogger(DialogHutang.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "RollBack " + ex.getMessage());

            } catch (SQLException ex1) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            Logger
                    .getLogger(DialogHutang.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "RollBack " + ex.getMessage());

            } catch (SQLException ex1) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            Logger
                    .getLogger(DialogHutang.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "RollBack " + ex.getMessage());

            } catch (SQLException ex1) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            Logger
                    .getLogger(DialogHutang.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.createStatement().execute("set autocommit true");

            } catch (SQLException ex) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void pilihan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihan1ActionPerformed
        // TODO add your handling code here:
        if (txtKodePelanggan2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Dulu Data Pelanggan...");
            txtKodePelanggan2.requestFocus();
        } else {
            if (pilihan1.isSelected()) {
                reloadDataPiutangBayarLunasFaktur(c);
            } else {
                reloadDataPiutangBayarFaktur(c);
            }
        }
    }//GEN-LAST:event_pilihan1ActionPerformed

    private void btnHapusBayarPiutang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBayarPiutang1ActionPerformed
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
                                aksilog = "DeletePiutangBayarF";
                                sf = c.createStatement();
                                sf.execute("delete from JURNAL where KODEJURNAL='" + jTable8.getValueAt(jTable8.getSelectedRow(), 1) + "'");
                                sf.execute("delete from PIUTANGBAYAR where ID='" + jTable8.getValueAt(jTable8.getSelectedRow(), 0) + "'");
                                JOptionPane.showMessageDialog(this, "Delete Ok");
                                String sql = "SELECT JUMLAH - JUMLAHBAYAR as SISA from VIEW_PIUTANG where ID='" + piutangid + "'";
                                ResultSet rs = sf.executeQuery(sql);
                                if (rs.next()) {
                                    if (rs.getDouble(1) != 0) {
                                        sf.executeUpdate("update PIUTANG set STATUS='1' where ID='" + piutangid + "'");
                                    }
                                }
                                rs.close();
                                sf.close();
                                prosesUpdateLog(c);
                                reloadDataPelunasanFaktur(c);
                                if (pilihan1.isSelected()) {
                                    reloadDataPiutangBayarLunasFaktur(c);
                                } else {
                                    reloadDataPiutangBayarFaktur(c);
                                }
                                c.commit();
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                txtKodePelanggan1.requestFocus();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Data ini berkaitan dengan pembayaran piutang khusus.. !");
                        txtKodePelanggan1.requestFocus();
                    }
                }
            } else {
                txtKodePelanggan1.requestFocus();
            }
        } catch (Exception ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "RollBack " + ex.getMessage());

            } catch (SQLException ex1) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            Logger
                    .getLogger(FormPenjualan.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.createStatement().execute("set autocommit false");

            } catch (SQLException ex) {
                Logger.getLogger(DialogPiutang.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnHapusBayarPiutang1ActionPerformed

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
        kosongBayarAwalFaktur();
        jScrollPane1.getViewport().remove(jTable1);
        jScrollPane3.getViewport().remove(jTable3);
        txtKodePelanggan1.requestFocus();
        //    if (pilihan.isSelected()) {
        //        reloadDataPiutangBayarLunas();
        //    } else {
        //        reloadDataPiutangBayar();
        //    }
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void TxtFilter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFilter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtFilter2ActionPerformed

    private void TxtFilter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFilter2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                pelanggan b = new pelanggan();
                b = dbpel.getDetails(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
                if (pilPelanggan.equals("0")) {
                    //txtKodePelanggan.setText(b.getKODEPELANGGAN());
                    //txtNamaPelanggan.setText(b.getNAMA());
                    jScrollPane4.setVisible(false);
                    reloadDataPiutang(c);
                    txtJumlahPiutang.requestFocus();
                } else if (pilPelanggan.equals("1")) {
                    //txtKodePelanggan1.setText(b.getKODEPELANGGAN());
                    //txtNamaPelanggan1.setText(b.getNAMA());
                    IdkodePelanggan = txtKodePelanggan1.getText();
                    jScrollPane4.setVisible(false);
                    if (pilihan.isSelected()) {
                        reloadDataPiutangBayarLunas(c);
                    } else {
                        reloadDataPiutangBayar(c);
                    }
                } else if (pilPelanggan.equals("2")) {
                    txtKodePelangganKhusus.setText(b.getKODEPELANGGAN());
                    txtNamaPelangganKhusus.setText(b.getNAMA());
                    jScrollPane4.setVisible(false);
                    piutangidKhusus = piutangbayarDao.getID(c);
                    piutangidKhususcounter = Integer.parseInt(KodeTransaksiKhusus.getText().substring(6, 9));
                    if (pilihKhusus.isSelected()) {
                        reloadDataPiutangBayarLunasKhusus(c);
                    } else {
                        reloadDataPiutangBayarKhusus(c);
                    }
                }

            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_TxtFilter2KeyPressed

    private void btnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar3ActionPerformed
        // TODO add your handling code here:
        try {
            dispose();
            c.close();
            statm.close();
            cm.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnKeluar3ActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        controller.onClickFilter();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnDeletePKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePKActionPerformed
        // TODO add your handling code here:
        controller.deletePK();
    }//GEN-LAST:event_btnDeletePKActionPerformed

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
            java.util.logging.Logger.getLogger(DialogPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DialogPiutang dialog = new DialogPiutang(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel LabelTotalPiutang1;
    private javax.swing.JLabel LabelTotalPiutang2;
    private javax.swing.JLabel LabelTotalPiutang3;
    private javax.swing.JTextField TxtFilter;
    private javax.swing.JTextField TxtFilter1;
    private javax.swing.JTextField TxtFilter2;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClear1;
    private javax.swing.JButton btnClear2;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeletePK;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnHapusBayarPiutang;
    private javax.swing.JButton btnHapusBayarPiutang1;
    private javax.swing.JButton btnHapusK;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnKeluar2;
    private javax.swing.JButton btnKeluar3;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JButton btnSimpanSemua;
    private javax.swing.JButton btnTambahPiutangKhusus;
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
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
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
    private datechooser.beans.DateChooserCombo tglJatuhTempo;
    private datechooser.beans.DateChooserCombo tglPenerimaanKhusus;
    private datechooser.beans.DateChooserCombo tglPiutang;
    private javax.swing.JTextField txtDesk;
    private javax.swing.JTextField txtDesk1;
    public static javax.swing.JFormattedTextField txtJumlahBayar;
    public static javax.swing.JFormattedTextField txtJumlahBayar1;
    private javax.swing.JFormattedTextField txtJumlahBayarKhusus;
    private javax.swing.JFormattedTextField txtJumlahPiutang;
    private javax.swing.JTextField txtKetTerimaPiutang;
    private javax.swing.JTextField txtKetTerimaPiutang1;
    private javax.swing.JTextField txtKetTerimaPiutangKhusus;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtKodePelanggan1;
    private javax.swing.JTextField txtKodePelanggan2;
    private javax.swing.JTextField txtKodePelangganKhusus;
    private javax.swing.JTextField txtKodePiutang;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNamaPelanggan1;
    private javax.swing.JTextField txtNamaPelanggan2;
    private javax.swing.JTextField txtNamaPelangganKhusus;
    private javax.swing.JTextField txtNoRef;
    private javax.swing.JTextField txtNoRef1;
    private javax.swing.JTextField txtNoRef2;
    private javax.swing.JTextField txtNoReff;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtNoTrans1;
    private javax.swing.JFormattedTextField txtSisa;
    private javax.swing.JFormattedTextField txtTotalKhusus;
    private javax.swing.JFormattedTextField txtTotalPiutang;
    private javax.swing.JFormattedTextField txtTotalPiutang1;
    private javax.swing.JFormattedTextField txtTotalPiutang2;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables

    void kosongPiutang(Connection c) {
        txtKodePiutang.setText("");
        txtJumlahPiutang.setText("");
        txtNoReff.setText("0");
        txtKodePelanggan.setText("");
        txtNamaPelanggan.setText("");
        txtKeterangan.setText("");
        txtKodePiutang.setText(piutangDao.getKodePiutang(c));
    }

    void kosongBayar(Connection c) {
        txtNoTrans.setText("");
        //txtKodePelanggan1.setText("");
        //txtNamaPelanggan1.setText("");
        txtTotalPiutang.setText("");
        txtJumlahBayar.setText("");
        txtNoTrans.setText(piutangbayarDao.getKodePiutangBayar(c));
    }

    void kosongBayarFaktur(Connection c) {
        txtNoTrans1.setText("");
        txtKodePelanggan2.setText("");
        txtNamaPelanggan2.setText("");
        txtTotalPiutang2.setText("");
        txtJumlahBayar1.setText("");
        txtNoTrans1.setText(piutangbayarDao.getKodePiutangBayar(c));
    }

    void kosongBayarAwal(Connection c) {
        txtNoTrans.setText("");
        txtKodePelanggan1.setText("");
        txtNamaPelanggan1.setText("");
        txtKetTerimaPiutang.setText("");
        txtTotalPiutang.setText("");
        txtJumlahBayar.setText("");
        txtDesk.setText("");
        txtNoTrans.setText(piutangbayarDao.getKodePiutangBayar(c));
    }

    void kosongBayarKhusus(Connection c) throws SQLException {
        KodeTransaksiKhusus.setText("");
        //txtKodePelangganKhusus.setText("");
        //txtNamaPelangganKhusus.setText("");
        txtJumlahBayarKhusus.setText("0");
        txtTotalPiutang1.setText("");
        txtSisa.setText("");
        txtTotalKhusus.setText("");
        setKodeJurnal(c);
    }

    void setKodeJurnal(Connection c) throws SQLException {
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        IDJurnal = jurnalDao.getIDJurnal(c);
        //txtNoRef1.setText("PK" + com.erv.function.Util.getbln(tgl) + com.erv.function.Util.getthn(tgl) + IDJurnal);
        txtNoRef1.setText(jurnalDao.getGenKodeJurnal(c, "PK"));
        KodeTransaksiKhusus.setText(piutangbayarDao.getKodePiutangBayarRef(c));
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
        rs.close();
        stat.close();

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

    void insertPiutang(Connection c) throws SQLException {
        p.setID(piutangDao.getID(c));
        p.setKODEPIUTANG(txtKodePiutang.getText());
        p.setIDPENJUALAN(IDJUAL);
        p.setJUMLAH(Double.parseDouble(txtJumlahBayar.getValue().toString()));
        p.setTANGGAL(tglPiutang.getText());
        if (piutangDao.insertIntoPIUTANG(c, p)) {
            JOptionPane.showMessageDialog(this, "Entri Data Ok");
        }
    }

    void insertJurnal(Connection c) throws SQLException {
        jurnal j = new jurnal();
        if (jTabbedPane1.getSelectedIndex() == 0) {
            String tgl = com.erv.function.Util.toDateStringSql(new Date());
            String kdjrn = "";
            kdjrn = jurnalDao.getGenKodeJurnal(c, "PA");
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(kdjrn);
            j.setTANGGAL(tglPiutang.getText());
            j.setDESKRIPSI(txtKeterangan.getText() + " " + txtNamaPelanggan.getText());
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(txtNoTrans.getText());
            j.setTANGGAL(tglBayar.getText());
            j.setDESKRIPSI(txtKetTerimaPiutang.getText());
        }
        jurnalDao.insertIntoJURNAL(c, j);
    }

    void insertRinciJurnal(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        pelangganDao dbplg = new pelangganDao(c);
        s = c.createStatement();
        if (cboCaraBayar.getSelectedIndex() == 0) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + txtJumlahBayar.getValue().toString() + ",0,1,'')");
        } else {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bankDao.getDetails(c, Integer.parseInt(ID[cboBank.getSelectedIndex()])).getKODEAKUN() + "'," + txtJumlahBayar.getValue().toString() + ",0,1,'')");
        }
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(txtKodePelanggan1.getText()).getKODEAKUN() + "',0," + txtJumlahBayar.getValue().toString() + ",2,'')");
        s.close();
        dbplg = null;
    }

    void reloadPelanggan(Connection c, String pil) {
        String kodepelanggan = "";
        String namapelanggan = "";
        if (pil.equals("0")) {
            kodepelanggan = txtKodePelanggan.getText();
            namapelanggan = txtNamaPelanggan.getText();
        } else if (pil.equals("1")) {
            kodepelanggan = txtKodePelanggan1.getText();
            namapelanggan = txtNamaPelanggan1.getText();
        } else if (pil.equals("2")) {
            kodepelanggan = txtKodePelangganKhusus.getText();
            namapelanggan = txtNamaPelangganKhusus.getText();
        } else if (pil.equals("3")) {
            kodepelanggan = txtKodePelanggan2.getText();
            namapelanggan = txtNamaPelanggan2.getText();
        }
        jScrollPane4.setVisible(true);
        JDBCAdapter ja = new JDBCAdapter(c);
        ja.executeQuery("select KODEPELANGGAN AS KODE,NAMA,ALAMAT from PELANGGAN where STATUSAKTIF='0' AND (KODEPELANGGAN like '" + kodepelanggan + "%' or lower(NAMA) like '%" + kodepelanggan.toLowerCase() + "%')");
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
    }

    void reloadDataPiutang(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelanggan.getText() + "'";
        //String sql = "SELECT ID,KETERANGAN,NOFAKTUR,CAST(JUMLAH as varchar(50)) as JUMLAH,CAST(JUMLAH - JUMLAHBAYAR AS varchar(50)) as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelanggan.getText() + "'";
        th.executeQuery(sql);
        jScrollPane2.getViewport().remove(jTable2);
        jTable2 = new JTable(th);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        TableColumn col = jTable2.getColumnModel().getColumn(0);
        col.setPreferredWidth(30);
        col = jTable2.getColumnModel().getColumn(1);
        col.setPreferredWidth(300);
        col = jTable2.getColumnModel().getColumn(2);
        col.setPreferredWidth(50);
        col = jTable2.getColumnModel().getColumn(3);
        col.setPreferredWidth(90);
        col = jTable2.getColumnModel().getColumn(4);
        col.setPreferredWidth(90);
        col = jTable2.getColumnModel().getColumn(5);
        col.setPreferredWidth(60);
        col = jTable2.getColumnModel().getColumn(6);
        col.setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
        jTable2.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable2.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane2.getViewport().add(jTable2);
        jScrollPane2.repaint();
    }

    private void reloadDataPiutangBayar(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        String sql = "";
        if (TxtFilter.getText().equals("")) {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND STATUS='BELUM LUNAS'";
        } else {
            sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND STATUS='BELUM LUNAS'";
        }
        th.executeQuery(sql);
        jScrollPane1.getViewport().remove(jTable1);
        jTable1 = new JTable(th);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane1.repaint();
    }

    private void reloadDataPiutangBayarFaktur(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter2.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND STATUS='BELUM LUNAS'";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND STATUS='BELUM LUNAS'";
            }
            th.executeQuery(sql);
            jScrollPane7.getViewport().remove(jTable7);
            jTable7 = new JTable(th);
            jTable7.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable7MouseClicked(evt);
                }
            });
            TableColumn col = jTable7.getColumnModel().getColumn(0);
            col.setPreferredWidth(30);
            col = jTable7.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            col = jTable7.getColumnModel().getColumn(2);
            col.setPreferredWidth(50);
            col = jTable7.getColumnModel().getColumn(3);
            col.setPreferredWidth(90);
            col = jTable7.getColumnModel().getColumn(4);
            col.setPreferredWidth(90);
            col = jTable7.getColumnModel().getColumn(5);
            col.setPreferredWidth(60);
            col = jTable7.getColumnModel().getColumn(6);
            col.setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
            jTable7.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable7.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane7.getViewport().add(jTable7);
            jScrollPane7.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPiutangBayarLunas(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            }
            th.executeQuery(sql);
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(th);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jTable1.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable1.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPiutangBayarLunasFaktur(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter2.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + IdkodePelanggan + "' AND NOFAKTUR='" + TxtFilter.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            }
            th.executeQuery(sql);
            jScrollPane7.getViewport().remove(jTable7);
            jTable7 = new JTable(th);
            jTable7.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable7MouseClicked(evt);
                }
            });
            TableColumn col = jTable7.getColumnModel().getColumn(0);
            col.setPreferredWidth(30);
            col = jTable7.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            col = jTable7.getColumnModel().getColumn(2);
            col.setPreferredWidth(50);
            col = jTable7.getColumnModel().getColumn(3);
            col.setPreferredWidth(90);
            col = jTable7.getColumnModel().getColumn(4);
            col.setPreferredWidth(90);
            col = jTable7.getColumnModel().getColumn(5);
            col.setPreferredWidth(60);
            col = jTable7.getColumnModel().getColumn(6);
            col.setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
            jTable7.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable7.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane7.getViewport().add(jTable7);
            jScrollPane7.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPiutangBayarKhusus(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter1.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelangganKhusus.getText() + "' AND STATUS='BELUM LUNAS'";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelangganKhusus.getText() + "' AND NOFAKTUR='" + TxtFilter1.getText() + "' AND STATUS='BELUM LUNAS'";
            }
            th.executeQuery(sql);
            jScrollPane5.getViewport().remove(jTable5);
            jTable5 = new JTable(th);
            jTable5.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable5MouseClicked(evt);
                }
            });
            TableColumn col = jTable5.getColumnModel().getColumn(0);
            col.setPreferredWidth(30);
            col = jTable5.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            col = jTable5.getColumnModel().getColumn(2);
            col.setPreferredWidth(50);
            col = jTable5.getColumnModel().getColumn(3);
            col.setPreferredWidth(90);
            col = jTable5.getColumnModel().getColumn(4);
            col.setPreferredWidth(90);
            col = jTable5.getColumnModel().getColumn(5);
            col.setPreferredWidth(60);
            col = jTable5.getColumnModel().getColumn(6);
            col.setPreferredWidth(60);
            jTable5.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
            jTable5.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable5.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane5.getViewport().add(jTable5);
            jScrollPane5.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPiutangBayarLunasKhusus(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            String sql = "";
            if (TxtFilter1.getText().equals("")) {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelangganKhusus.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            } else {
                sql = "SELECT ID,KETERANGAN,NOFAKTUR,JUMLAH,JUMLAH - JUMLAHBAYAR as SISA,JATUHTEMPO,STATUS from VIEW_PIUTANG where IDPELANGGAN='" + txtKodePelangganKhusus.getText() + "' AND NOFAKTUR='" + TxtFilter1.getText() + "' AND (STATUS='LUNAS' OR STATUS='BELUM LUNAS')";
            }
            th.executeQuery(sql);
            jScrollPane5.getViewport().remove(jTable5);
            jTable5 = new JTable(th);
            jTable5.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            TableColumn col = jTable5.getColumnModel().getColumn(0);
            col.setPreferredWidth(30);
            col = jTable5.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            col = jTable5.getColumnModel().getColumn(2);
            col.setPreferredWidth(50);
            col = jTable5.getColumnModel().getColumn(3);
            col.setPreferredWidth(90);
            col = jTable5.getColumnModel().getColumn(4);
            col.setPreferredWidth(90);
            col = jTable5.getColumnModel().getColumn(5);
            col.setPreferredWidth(60);
            col = jTable5.getColumnModel().getColumn(6);
            col.setPreferredWidth(60);
            jTable5.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
            jTable5.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable5.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane5.getViewport().add(jTable5);
            jScrollPane5.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPiutangTotal(Connection c, String idp) throws SQLException {
        String sql = "select sum(vp.JUMLAH - vp.JUMLAHBAYAR) from view_piutang vp where vp.idpelanggan='" + idp + "'";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);

        if (rs.next()) {
            if (rs.getDouble(1) != 0) {
                if (pilPelanggan.equals("0")) {
                    LabelTotalPiutang1.setText(df.format(rs.getDouble(1)));
                } else if (pilPelanggan.equals("1")) {
                    LabelTotalPiutang2.setText(df.format(rs.getDouble(1)));
                    txtTotalPiutang.setValue(rs.getDouble(1));
                } else if (pilPelanggan.equals("2")) {
                    LabelTotalPiutang3.setText(df.format(rs.getDouble(1)));
                }
            }
        }
        rs.close();
        s.close();

    }

    void prosesSimpan(Connection c, String pil) throws SQLException {
        p.setKODEPIUTANG(txtKodePiutang.getText());
        p.setIDPENJUALAN(0);
        p.setTANGGAL(tglPiutang.getText());
        p.setJUMLAH(Double.parseDouble(txtJumlahPiutang.getValue().toString()));
        p.setIDPELANGGAN(txtKodePelanggan.getText());
        p.setJATUHTEMPO(tglJatuhTempo.getText());
        p.setKETERANGAN(txtKeterangan.getText());
        p.setSTATUS("1");
        boolean stat;
        if (pil.equals("0")) {
            p.setID(piutangDao.getID(c));
            stat = piutangDao.insertIntoPIUTANG(c, p);
        } else {
            stat = piutangDao.updatePIUTANG(c, p);
        }
        if (stat) {
            JOptionPane.showMessageDialog(this, "Update Data Ok");
        } else {
            JOptionPane.showMessageDialog(this, "Update Data Gagal");
        }

    }

    void reloadDataPelunasan(Connection c) {
        try {
            JDBCAdapter th = new JDBCAdapter(c);
            //String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + piutangid + "";
            th.executeQuery(sql);
            jScrollPane3.getViewport().remove(jTable3);
            jTable3 = new JTable(th);
            jTable3.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable3.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane3.getViewport().add(jTable3);
            jScrollPane3.repaint();
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadDataPelunasanFaktur(Connection c) {
        JDBCAdapter th = new JDBCAdapter(c);
        //String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
        String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + piutangid + "";
        th.executeQuery(sql);
        jScrollPane8.getViewport().remove(jTable8);
        jTable8 = new JTable(th);
        jTable8.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
        jTable8.setFont(new Font("Tahoma", Font.BOLD, 11));
        jScrollPane8.getViewport().add(jTable8);
        jScrollPane8.repaint();
    }

    void reloadDataPelunasanKhusus() {
        try {
            JDBCAdapter th = new JDBCAdapter(cm);
            //String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM PIUTANGBAYAR order by 1";
            th.executeQuery(sql);
            jScrollPane6.getViewport().remove(jTable6);
            jTable6 = new JTable(th);
            jTable6.getColumnModel().getColumn(4).setCellRenderer(new DecimalFormatRenderer());
            jTable6.setFont(new Font("Tahoma", Font.BOLD, 11));
            jScrollPane6.getViewport().add(jTable6);
            jScrollPane6.repaint();
        } catch (Exception e) {
        }
    }

    public void settingTgl(Connection c) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tglPiutang.getText())));
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

        String sqlJurnal = "CREATE TABLE PIUTANGBAYAR (\n"
                + "    `ID` INT(10) primary key ,\n"
                + "    `KODEPIUTANGBAYAR` VARCHAR(20),\n"
                + "    `IDPIUTANG` INT(10) unique,\n"
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
            statm.execute("drop table piutangbayar");
        } catch (SQLException ex) {
        }
    }

    double getTotal() {
        double temp = 0.0;
        try {
            ResultSet rs = statm.executeQuery("select sum(jumlah) from piutangbayar");
            if (rs.next()) {
                temp = rs.getDouble(1);
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DialogPiutang.class
                    .getName()).log(Level.SEVERE, null, ex);
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
        btnHapusBayarPiutang1.setEnabled(hapus);
    }

    void prosesUpdateLog(Connection c) throws SQLException {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        String ketlog = "";
        lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
        lh.setTANGGAL(tanggallog);
        lh.setJAM(jamlog);
        if (aksilog.equals("InsertPiutang")) {
            lh.setTABEL("TPIUTANG");
            lh.setNOREFF(txtKodePiutang.getText());
            ketlog = "Insert Data Piutang " + txtKodePiutang.getText() + " Pelanggan " + txtNamaPelanggan.getText() + " (" + txtKodePelanggan.getText() + ")";
        } else if (aksilog.equals("UpdatePiutang")) {
            lh.setTABEL("TPIUTANG");
            lh.setNOREFF(txtKodePiutang.getText());
            ketlog = "Update Data Piutang " + txtKodePiutang.getText() + " Pelanggan " + txtNamaPelanggan.getText() + " (" + txtKodePelanggan.getText() + ")";
        } else if (aksilog.equals("DeletePiutang")) {
            lh.setTABEL("TPIUTANG");
            lh.setNOREFF(txtKodePiutang.getText());
            ketlog = "Delete Data Piutang " + txtKodePiutang.getText() + " Pelanggan " + txtNamaPelanggan.getText() + " (" + txtKodePelanggan.getText() + ")";
        } else if (aksilog.equals("InsertPiutangBayar")) {
            lh.setTABEL("TPIUTANGBAYAR");
            lh.setNOREFF(txtNoTrans.getText());
            ketlog = "Insert Data Piutang Bayar " + txtNoTrans.getText() + " IDPIUTANG " + piutangid + " Pelanggan " + txtNamaPelanggan2.getText() + " (" + txtKodePelanggan2.getText() + ")";
        } else if (aksilog.equals("DeletePiutangBayar")) {
            lh.setTABEL("TPIUTANGBAYAR");
            lh.setNOREFF(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
            ketlog = "Delete Data Piutang Bayar " + jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString() + " IDPIUTANG " + jTable3.getValueAt(jTable3.getSelectedRow(), 2) + " Pelanggan " + txtNamaPelanggan1.getText() + " (" + txtKodePelanggan1.getText() + ") Keterangan " + txtDesk.getText();
        } else if (aksilog.equals("DeletePiutangBayarF")) {
            lh.setTABEL("TPIUTANGBAYAR");
            lh.setNOREFF(jTable8.getValueAt(jTable8.getSelectedRow(), 1).toString());
            ketlog = "Delete Data Piutang Bayar " + jTable8.getValueAt(jTable8.getSelectedRow(), 1).toString() + " IDPIUTANG " + jTable8.getValueAt(jTable8.getSelectedRow(), 2) + " Pelanggan " + txtNamaPelanggan2.getText() + " (" + txtKodePelanggan2.getText() + ") Keterangan " + txtDesk1.getText();
        } else if (aksilog.equals("InsertJurnal")) {
            lh.setTABEL("TJURNAL");
            lh.setNOREFF(txtNoRef1.getText());
            ketlog = "Insert Data Jurnal " + txtNoRef1.getText() + " Pelanggan " + txtNamaPelangganKhusus.getText() + " (" + txtKodePelangganKhusus.getText() + ")";
        }

        lh.setAKSI(aksilog);
        lh.setKETERANGAN(ketlog);
        lhdao.insert(c, lh);
    }

    void prosesUpdateLogKhusus(Connection c) throws SQLException {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        String ketlog = "";
        lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
        lh.setTANGGAL(tanggallog);
        lh.setJAM(jamlog);
        if (aksilog.equals("InsertPiutangBayar")) {
            lh.setTABEL("TPIUTANGBAYAR");
            lh.setNOREFF(ptkd);
            ketlog = "Insert Data Piutang Bayar " + ptkd + " IDPIUTANG " + ptid + " Pelanggan " + txtNamaPelangganKhusus.getText() + " (" + txtKodePelangganKhusus.getText() + ")";
        }
        lh.setAKSI(aksilog);
        lh.setKETERANGAN(ketlog);
        lhdao.insert(c, lh);

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

    void insertRinciJurnalPiutang(Connection c) throws SQLException {
        Statement s;
        pelangganDao dbplg = new pelangganDao(c);
        s = c.createStatement();
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(txtKodePelanggan.getText()).getKODEAKUN() + "'," + txtJumlahPiutang.getValue().toString() + ",0,1,'')");
        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + kodeakun[cboAkun.getSelectedIndex()] + "',0," + txtJumlahPiutang.getValue().toString() + ",2,'')");
        s.close();
        dbplg = null;
    }

    public double getFormatTextDouble(JFormattedTextField t) {
        double h = 0;
        if (t.getValue() != null) {
            h = Double.parseDouble(t.getValue().toString());
        }
        return h;
    }

    void insertJurnalFaktur() {
        jurnal j = new jurnal();
        try {
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            j.setKODEJURNAL(txtNoTrans1.getText());
            j.setTANGGAL(tglBayar1.getText());
            j.setDESKRIPSI(txtKetTerimaPiutang1.getText());
            jurnalDao.insertIntoJURNAL(c, j);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    void insertRinciJurnalFaktur() {
        Statement s;
        try {
            pelangganDao dbplg = new pelangganDao(c);
            s = c.createStatement();
            if (cboCaraBayar2.getSelectedIndex() == 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + txtJumlahBayar1.getValue().toString() + ",0,1,'')");
            } else {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bankDao.getDetails(c, Integer.parseInt(ID[cboBank2.getSelectedIndex()])).getKODEAKUN() + "'," + txtJumlahBayar1.getValue().toString() + ",0,1,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(txtKodePelanggan2.getText()).getKODEAKUN() + "',0," + txtJumlahBayar1.getValue().toString() + ",2,'')");
            s.close();
            dbplg = null;
        } catch (Exception ex) {
        }
    }

    void kosongBayarAwalFaktur() {
        txtNoTrans1.setText("");
        txtKodePelanggan2.setText("");
        txtNamaPelanggan2.setText("");
        txtKetTerimaPiutang1.setText("");
        txtTotalPiutang2.setText("");
        txtJumlahBayar1.setText("");
        txtDesk1.setText("");
        txtNoTrans1.setText(piutangbayarDao.getKodePiutangBayar(c));
    }

}
