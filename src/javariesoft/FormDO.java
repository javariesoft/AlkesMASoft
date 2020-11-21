/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * FormDO.java
 *
 * Created on Mar 13, 2012, 7:10:03 AM
 */
package javariesoft;

import com.erv.db.DODao;
import com.erv.db.DORinciDao;
import com.erv.db.barangDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.db.stokDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.DO;
import com.erv.model.DORinci;
import com.erv.model.barang;
import com.erv.model.pelanggan;
import com.erv.model.stok;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.BarangstokDao;
import com.erv.db.KontrolTanggalDao;
import com.erv.db.ReturdoDao;
import com.erv.db.ReturdorinciDao;
import com.erv.exception.JavarieException;
import com.erv.function.Util;
import com.erv.model.Barangstok;
import com.erv.model.Returdo;
import com.erv.model.Returdorinci;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTabbedPane;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.jdesktop.application.ResourceMap;
import simple.escp.SimpleEscp;
import simple.escp.Template;
import simple.escp.json.JsonTemplate;

/**
 *
 * @author JavarieSoft
 */
public final class FormDO extends javax.swing.JInternalFrame {

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    Connection cm;
    Statement stat = null;
    int ID = 0;
    DO dis;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    String[] expire;
    ResourceMap resourceMap1 = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormDO.class);
    Connection c;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    int no = 0;

    /**
     * Creates new form FormDO
     */
    public FormDO() {
        try {
            initComponents();
            c = koneksi.getKoneksiJ();
            tglDO.setDateFormat(d);
            tglDO1.setDateFormat(d);
            tglExpire.setDateFormat(d);
            cm = koneksi.getKoneksiM();
            stat = cm.createStatement();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            settingtombol(true, false, false, false, false);
            buatTabel();
            kosongDO(c);
            kosongBarang();
            pilihanKertasPendek();
            settingPosisi();
            pilihJTabbedPane(jTabbedPane1, 0);
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2 - 40);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FormDO(int id, String pil) {
        try {
            initComponents();
            c = koneksi.getKoneksiJ();
            tglDO.setDateFormat(d);
            tglDO1.setDateFormat(d);
            tglExpire.setDateFormat(d);
            cm = koneksi.getKoneksiM();
            stat = cm.createStatement();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            dis = DODao.getDetails(c, id);
            kosongBarang();
            hapusTabel();
            buatTabel();
            List<DORinci> listDO = DORinciDao.getDetailDORinci(c, id);
            if (pil.equals("view")) {
                tampilDO(dis);
                tampilDORinci(listDO);
                pilihJTabbedPane(jTabbedPane1, 0);
                settingtombol(false, false, false, false, false);
            } else if (pil.equals("view retur")) {
                List<Returdorinci> listReturdorinci = ReturdorinciDao.getReturdorinciList(c, id);
                Returdo returdo = ReturdoDao.getDetails(c, id);
                tampilDORetur(returdo);
                tampilReturDoRinci(listReturdorinci);
                pilihJTabbedPane(jTabbedPane1, 1);
                settingtombol(false, false, false, false, false);
            } else if (pil.equals("retur")) {
                txtKodeRetur.setText(ReturdoDao.getKodeReturDO(c));
                txtKdDO.setText(dis.getKODEDO());
                txtKodePelanggan1.setText(dis.getKODEPELANGGAN());
                txtNamaPelanggan1.setText(new pelangganDao(c).getDetails(dis.getKODEPELANGGAN()).getNAMA());
                tampilDORinci(listDO);
                pilihJTabbedPane(jTabbedPane1, 1);
                settingtombol(false, true, false, false, false);
            }
            /////
            settingPosisi();
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2 - 40);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
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

        panelCool1 = new com.erv.function.PanelCool();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtKODEDO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tglDO = new datechooser.beans.DateChooserCombo();
        txtKodePelanggan = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        btnKeluar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtKodeRetur = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtKodePelanggan1 = new javax.swing.JTextField();
        txtNamaPelanggan1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tglDO1 = new datechooser.beans.DateChooserCombo();
        txtKdDO = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnKeluar1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JFormattedTextField();
        btnInsertBarang = new javax.swing.JButton();
        btnDeleteBarang = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tglExpire = new datechooser.beans.DateChooserCombo();
        jLabel30 = new javax.swing.JLabel();
        cboSatuan = new javax.swing.JComboBox();
        txtBatch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnRetur = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        btnNonaktif = new javax.swing.JButton();
        txtNoDOPrint = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        RadioPanjang = new javax.swing.JRadioButton();
        RadioPendek = new javax.swing.JRadioButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormDO.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jScrollPane2.setFont(resourceMap.getFont("jScrollPane2.font")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        panelCool1.add(jScrollPane2);
        jScrollPane2.setBounds(160, 110, 20, 20);

        jTabbedPane1.setFont(resourceMap.getFont("jTabbedPane1.font")); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 20, 100, 15);

        txtKODEDO.setEditable(false);
        txtKODEDO.setFont(resourceMap.getFont("txtKODEDO.font")); // NOI18N
        txtKODEDO.setName("txtKODEDO"); // NOI18N
        jPanel1.add(txtKODEDO);
        txtKODEDO.setBounds(140, 20, 190, 21);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 50, 100, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(30, 80, 100, 20);

        tglDO.setFieldFont(resourceMap.getFont("tglDO.dch_combo_fieldFont")); // NOI18N
        jPanel1.add(tglDO);
        tglDO.setBounds(140, 80, 130, 20);

        txtKodePelanggan.setEditable(false);
        txtKodePelanggan.setFont(resourceMap.getFont("txtKodePelanggan.font")); // NOI18N
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
        txtKodePelanggan.setBounds(450, 50, 70, 20);

        txtNamaPelanggan.setFont(resourceMap.getFont("txtNamaPelanggan.font")); // NOI18N
        txtNamaPelanggan.setName("txtNamaPelanggan"); // NOI18N
        txtNamaPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPelangganActionPerformed(evt);
            }
        });
        txtNamaPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaPelangganKeyPressed(evt);
            }
        });
        jPanel1.add(txtNamaPelanggan);
        txtNamaPelanggan.setBounds(140, 50, 300, 21);

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
        btnKeluar.setBounds(820, 10, 120, 39);

        jTabbedPane1.addTab("DO", jPanel1);

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(null);

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel5.add(jLabel12);
        jLabel12.setBounds(30, 20, 100, 15);

        txtKodeRetur.setEditable(false);
        txtKodeRetur.setFont(resourceMap.getFont("txtKodeRetur.font")); // NOI18N
        txtKodeRetur.setName("txtKodeRetur"); // NOI18N
        jPanel5.add(txtKodeRetur);
        txtKodeRetur.setBounds(140, 20, 170, 22);

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel5.add(jLabel13);
        jLabel13.setBounds(30, 50, 100, 20);

        txtKodePelanggan1.setEditable(false);
        txtKodePelanggan1.setFont(resourceMap.getFont("txtKodePelanggan1.font")); // NOI18N
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
        jPanel5.add(txtKodePelanggan1);
        txtKodePelanggan1.setBounds(450, 50, 70, 20);

        txtNamaPelanggan1.setEditable(false);
        txtNamaPelanggan1.setFont(resourceMap.getFont("txtNamaPelanggan1.font")); // NOI18N
        txtNamaPelanggan1.setName("txtNamaPelanggan1"); // NOI18N
        jPanel5.add(txtNamaPelanggan1);
        txtNamaPelanggan1.setBounds(140, 50, 300, 22);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel5.add(jLabel14);
        jLabel14.setBounds(30, 80, 100, 20);

        tglDO1.setFieldFont(resourceMap.getFont("tglDO1.dch_combo_fieldFont")); // NOI18N
        jPanel5.add(tglDO1);
        tglDO1.setBounds(140, 80, 130, 20);

        txtKdDO.setEditable(false);
        txtKdDO.setFont(resourceMap.getFont("txtKdDO.font")); // NOI18N
        txtKdDO.setText(resourceMap.getString("txtKdDO.text")); // NOI18N
        txtKdDO.setName("txtKdDO"); // NOI18N
        jPanel5.add(txtKdDO);
        txtKdDO.setBounds(400, 20, 120, 21);

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel5.add(jLabel9);
        jLabel9.setBounds(340, 20, 80, 15);

        btnKeluar1.setFont(resourceMap.getFont("btnKeluar1.font")); // NOI18N
        btnKeluar1.setIcon(resourceMap.getIcon("btnKeluar1.icon")); // NOI18N
        btnKeluar1.setText(resourceMap.getString("btnKeluar1.text")); // NOI18N
        btnKeluar1.setName("btnKeluar1"); // NOI18N
        btnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar1ActionPerformed(evt);
            }
        });
        jPanel5.add(btnKeluar1);
        btnKeluar1.setBounds(820, 10, 120, 39);

        jTabbedPane1.addTab(resourceMap.getString("jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        panelCool1.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 960, 170);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setName("jTable3"); // NOI18N
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        panelCool1.add(jScrollPane3);
        jScrollPane3.setBounds(20, 220, 120, 10);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(null);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(0, 0, 100, 20);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(510, 0, 110, 20);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(760, 0, 200, 20);

        txtKodeBarang.setFont(resourceMap.getFont("txtKodeBarang.font")); // NOI18N
        txtKodeBarang.setName("txtKodeBarang"); // NOI18N
        txtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeBarangActionPerformed(evt);
            }
        });
        txtKodeBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeBarangKeyPressed(evt);
            }
        });
        jPanel2.add(txtKodeBarang);
        txtKodeBarang.setBounds(0, 20, 100, 21);

        txtNamaBarang.setEditable(false);
        txtNamaBarang.setFont(resourceMap.getFont("txtNamaBarang.font")); // NOI18N
        txtNamaBarang.setName("txtNamaBarang"); // NOI18N
        jPanel2.add(txtNamaBarang);
        txtNamaBarang.setBounds(100, 20, 310, 21);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel2.add(jLabel8);
        jLabel8.setBounds(680, 0, 80, 20);

        txtJumlah.setFont(resourceMap.getFont("txtJumlah.font")); // NOI18N
        txtJumlah.setName("txtJumlah"); // NOI18N
        txtJumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJumlahFocusLost(evt);
            }
        });
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJumlahKeyPressed(evt);
            }
        });
        jPanel2.add(txtJumlah);
        txtJumlah.setBounds(680, 20, 80, 21);

        btnInsertBarang.setFont(resourceMap.getFont("btnDeleteBarang.font")); // NOI18N
        btnInsertBarang.setText(resourceMap.getString("btnInsertBarang.text")); // NOI18N
        btnInsertBarang.setName("btnInsertBarang"); // NOI18N
        btnInsertBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertBarangActionPerformed(evt);
            }
        });
        jPanel2.add(btnInsertBarang);
        btnInsertBarang.setBounds(760, 20, 100, 28);

        btnDeleteBarang.setFont(resourceMap.getFont("btnDeleteBarang.font")); // NOI18N
        btnDeleteBarang.setText(resourceMap.getString("btnDeleteBarang.text")); // NOI18N
        btnDeleteBarang.setName("btnDeleteBarang"); // NOI18N
        btnDeleteBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBarangActionPerformed(evt);
            }
        });
        jPanel2.add(btnDeleteBarang);
        btnDeleteBarang.setBounds(860, 20, 100, 28);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel2.add(jLabel10);
        jLabel10.setBounds(100, 0, 310, 20);

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel2.add(jLabel11);
        jLabel11.setBounds(410, 0, 100, 20);

        tglExpire.setFieldFont(resourceMap.getFont("tglExpire.dch_combo_fieldFont")); // NOI18N
        tglExpire.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglExpireOnCommit(evt);
            }
        });
        jPanel2.add(tglExpire);
        tglExpire.setBounds(510, 20, 110, 20);
        tglExpire.setDateFormat(d);

        jLabel30.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel30.setMaximumSize(new java.awt.Dimension(52, 20));
        jLabel30.setMinimumSize(new java.awt.Dimension(52, 20));
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(52, 20));
        jPanel2.add(jLabel30);
        jLabel30.setBounds(620, 0, 60, 20);

        cboSatuan.setFont(resourceMap.getFont("cboSatuan.font")); // NOI18N
        cboSatuan.setName("cboSatuan"); // NOI18N
        cboSatuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSatuanItemStateChanged(evt);
            }
        });
        cboSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboSatuanKeyPressed(evt);
            }
        });
        jPanel2.add(cboSatuan);
        cboSatuan.setBounds(620, 20, 60, 22);

        txtBatch.setEditable(false);
        txtBatch.setFont(resourceMap.getFont("txtBatch.font")); // NOI18N
        txtBatch.setText(resourceMap.getString("txtBatch.text")); // NOI18N
        txtBatch.setName("txtBatch"); // NOI18N
        jPanel2.add(txtBatch);
        txtBatch.setBounds(410, 20, 100, 22);

        panelCool1.add(jPanel2);
        jPanel2.setBounds(10, 180, 960, 50);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Batch", "Expire", "Jumlah", "Satuan", "Jumlah Kecil"
            }
        ));
        jTable1.setCellSelectionEnabled(true);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        }

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 230, 960, 280);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(null);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel3.add(btnInsert);
        btnInsert.setBounds(10, 10, 115, 38);

        btnRetur.setFont(resourceMap.getFont("btnRetur.font")); // NOI18N
        btnRetur.setIcon(resourceMap.getIcon("btnRetur.icon")); // NOI18N
        btnRetur.setText(resourceMap.getString("btnRetur.text")); // NOI18N
        btnRetur.setName("btnRetur"); // NOI18N
        btnRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturActionPerformed(evt);
            }
        });
        jPanel3.add(btnRetur);
        btnRetur.setBounds(130, 10, 160, 38);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnDelete);
        btnDelete.setBounds(300, 10, 120, 38);

        btnTutup.setFont(resourceMap.getFont("btnTutup.font")); // NOI18N
        btnTutup.setText(resourceMap.getString("btnTutup.text")); // NOI18N
        btnTutup.setName("btnTutup"); // NOI18N
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });
        jPanel3.add(btnTutup);
        btnTutup.setBounds(10, 50, 115, 38);

        btnNonaktif.setFont(resourceMap.getFont("btnNonaktif.font")); // NOI18N
        btnNonaktif.setText(resourceMap.getString("btnNonaktif.text")); // NOI18N
        btnNonaktif.setActionCommand(resourceMap.getString("btnNonaktif.actionCommand")); // NOI18N
        btnNonaktif.setName("btnNonaktif"); // NOI18N
        btnNonaktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonaktifActionPerformed(evt);
            }
        });
        jPanel3.add(btnNonaktif);
        btnNonaktif.setBounds(130, 50, 140, 39);

        txtNoDOPrint.setFont(resourceMap.getFont("txtNoDOPrint.font")); // NOI18N
        txtNoDOPrint.setText(resourceMap.getString("txtNoDOPrint.text")); // NOI18N
        txtNoDOPrint.setName("txtNoDOPrint"); // NOI18N
        jPanel3.add(txtNoDOPrint);
        txtNoDOPrint.setBounds(540, 20, 130, 21);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel3.add(jLabel1);
        jLabel1.setBounds(490, 23, 60, 15);

        btnPrint.setFont(resourceMap.getFont("btnPrint.font")); // NOI18N
        btnPrint.setIcon(resourceMap.getIcon("btnPrint.icon")); // NOI18N
        btnPrint.setText(resourceMap.getString("btnPrint.text")); // NOI18N
        btnPrint.setName("btnPrint"); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel3.add(btnPrint);
        btnPrint.setBounds(680, 50, 130, 39);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setIcon(resourceMap.getIcon("btnPreview.icon")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        jPanel3.add(btnPreview);
        btnPreview.setBounds(680, 10, 130, 40);

        jLabel35.setFont(resourceMap.getFont("jLabel35.font")); // NOI18N
        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        jPanel3.add(jLabel35);
        jLabel35.setBounds(830, 10, 110, 16);

        RadioPanjang.setFont(resourceMap.getFont("RadioPanjang.font")); // NOI18N
        RadioPanjang.setText(resourceMap.getString("RadioPanjang.text")); // NOI18N
        RadioPanjang.setName("RadioPanjang"); // NOI18N
        RadioPanjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPanjangActionPerformed(evt);
            }
        });
        jPanel3.add(RadioPanjang);
        RadioPanjang.setBounds(830, 50, 120, 25);

        RadioPendek.setFont(resourceMap.getFont("RadioPendek.font")); // NOI18N
        RadioPendek.setText(resourceMap.getString("RadioPendek.text")); // NOI18N
        RadioPendek.setName("RadioPendek"); // NOI18N
        RadioPendek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPendekActionPerformed(evt);
            }
        });
        jPanel3.add(RadioPendek);
        RadioPendek.setBounds(830, 30, 120, 25);

        panelCool1.add(jPanel3);
        jPanel3.setBounds(10, 510, 960, 100);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 996, 643);
    }// </editor-fold>//GEN-END:initComponents

private void txtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeBarangActionPerformed
    //Connection c = null;
    try {
        // TODO add your handling code here:
        //c = koneksi.getKoneksiJ();
        jScrollPane3.setVisible(true);
        JDBCAdapter ja = new JDBCAdapter(c);
        String sql = "select BARANG.KODEBARANG,BARANG.NAMABARANG,"
                + "bsb.KODEBATCH , EXPIRE,"
                + "bs.HARGAJUAL ,"
                + "CASEWHEN(KODEBATCH is null,bs.STOK,bsb.STOK) as STOK "
                + "from BARANG,JENISBARANG,KATEGORI "
                + "inner join BARANGSTOK bs on bs.KODEBARANG=BARANG.KODEBARANG "
                + "left join BARANGSTOKBATCH bsb on bs.ID=bsb.IDBARANGSTOK "
                + "where BARANG.IDJENIS=JENISBARANG.ID AND BARANG.STATUS='0' "
                + "AND BARANG.IDKATEGORI=KATEGORI.IDKATEGORI "
                + "AND (BARANG.KODEBARANG like '%" + txtKodeBarang.getText() + "%' "
                + "OR lower(BARANG.NAMABARANG) like '%" + txtKodeBarang.getText() + "%' "
                + "OR bsb.KODEBATCH like '%" + txtKodeBarang.getText() + "%')";
        ja.executeQuery(sql);
        jScrollPane3.getViewport().remove(jTable3);
        jTable3 = new JTable(ja);
        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumn col = jTable3.getColumnModel().getColumn(0);
        col.setPreferredWidth(100);
        col = jTable3.getColumnModel().getColumn(1);
        col.setPreferredWidth(300);
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jTable3.setFont(new Font("Tahoma", Font.BOLD, 12));
        jScrollPane3.getViewport().add(jTable3);
        jScrollPane3.repaint();
    } catch (Exception ex) {
        Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
    }
//    finally {
//        if (c != null) {
//            try {
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}//GEN-LAST:event_txtKodeBarangActionPerformed

private void txtKodeBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeBarangKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        jScrollPane3.setVisible(true);
        jTable3.requestFocus();
        jTable3.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane3.setVisible(false);
    }
}//GEN-LAST:event_txtKodeBarangKeyPressed

private void txtJumlahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJumlahFocusLost
// TODO add your handling code here:
}//GEN-LAST:event_txtJumlahFocusLost

private void txtJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            if (jTabbedPane1.getSelectedIndex() == 0) {
                Barangstok b = BarangstokDao.getDetailKodeBarang(c, txtKodeBarang.getText());
                if (b.getSTOK() < Integer.parseInt(txtJumlah.getText())) {
                    txtJumlah.requestFocus();
                    JOptionPane.showMessageDialog(null, "Stok Tinggal " + b.getSTOK());
                } else {
                    btnInsertBarang.requestFocus();
                }
                b = null;
            } else {
                int stokdo = DORinciDao.getStokDO(c, txtKdDO.getText(), txtKodePelanggan1.getText(), txtKodeBarang.getText(), txtBatch.getText());
                if (stokdo < Integer.parseInt(txtJumlah.getText())) {
                    //            kosongBarang();
                    txtJumlah.requestFocus();
                    throw new JavarieException("Jumlah Retur DO Lebih Besar Dari Jumlah Sisa DO, Cek Laporan History Barang DO ..!!");
                }
            }
        } catch (Exception ex) {
//        Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
//        finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }

    }
}//GEN-LAST:event_txtJumlahKeyPressed

private void btnInsertBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertBarangActionPerformed
    //Connection c = null;
    try {
        // c = koneksi.getKoneksiJ();
        barang b = new barangDao().getDetails(c, txtKodeBarang.getText());
        if (jTabbedPane1.getSelectedIndex() == 0) {
            Barangstok bs = BarangstokDao.getDetailKodeBarang(c, txtKodeBarang.getText());
            if (barangDao.cekKodeBarang(c, txtKodeBarang.getText()) != true) {
                JOptionPane.showMessageDialog(null, "Produk Ini Tidak Ada..!!");
                kosongBarang();
                txtKodeBarang.requestFocus();
            } else if (bs.getSTOK() < Integer.parseInt(txtJumlah.getText())) {
                txtJumlah.requestFocus();
                JOptionPane.showMessageDialog(null, "Stok Tinggal " + bs.getSTOK());
            } else if (bs.getCOGS() <= 0) {
                kosongBarang();
                txtKodeBarang.requestFocus();
                throw new JavarieException("Harga Modal Barang Belum Ada..!!");
            } else {
                if (btnInsertBarang.getText().equals("Insert")) {
                    String s = "select max(no) from rinci";
                    Statement stemp = cm.createStatement();
                    ResultSet r = stemp.executeQuery(s);
                    if (r.next()) {
                        no = r.getInt(1) + 1;
                    } else {
                        no = 1;
                    }
                } else {
                    btnInsertBarang.setText("Insert");
                }

                stat.execute("insert into rinci values("
                        + no //0
                        + ",'" + txtKodeBarang.getText() //1
                        + "','" + txtNamaBarang.getText() //2
                        + "','" + txtBatch.getText() //3
                        ////                + "','" + ((txtBatch.getText().length() > 0) ? txtBatch.getText() : "")
                        + "','" + ((txtBatch.getText().equals("")) ? "" : tglExpire.getText()) //
                        ////                + "','" + tglExpire.getText()
                        + "'," + txtJumlah.getText() //5
                        + ",'" + cboSatuan.getSelectedItem() //6
                        + "'," + b.getJumlah(Integer.parseInt(txtJumlah.getText()), cboSatuan.getSelectedItem().toString()) //7
                        + ")");
                reloadData();
                kosongBarang();
                txtKodeBarang.requestFocus();
            }
        } else {
            int stokdo = DORinciDao.getStokDO(c, txtKdDO.getText(), txtKodePelanggan1.getText(), txtKodeBarang.getText(), txtBatch.getText());
            if (barangDao.cekKodeBarang(c, txtKodeBarang.getText()) != true) {
                JOptionPane.showMessageDialog(null, "Produk Ini Tidak Ada..!!");
                kosongBarang();
                txtKodeBarang.requestFocus();
            } else if (DORinciDao.cekKodeBarangDO(c, txtKdDO.getText(), txtKodePelanggan1.getText(), txtKodeBarang.getText(), txtBatch.getText()) != true) {
                kosongBarang();
                txtKodeBarang.requestFocus();
                throw new JavarieException("Produk Ini Tidak Ada ..!!");
            } else if (stokdo < Integer.parseInt(txtJumlah.getText())) {
                //            kosongBarang();
                txtJumlah.requestFocus();
                throw new JavarieException("Jumlah Retur DO Lebih Besar Dari Jumlah Sisa DO, Cek Laporan History Barang DO ..!!");
            } else {
                if (btnInsertBarang.getText().equals("Insert")) {
                    String s = "select max(no) from rinci";
                    Statement stemp = cm.createStatement();
                    ResultSet r = stemp.executeQuery(s);
                    if (r.next()) {
                        no = r.getInt(1) + 1;
                    } else {
                        no = 1;
                    }
                } else {
                    btnInsertBarang.setText("Insert");
                }

                stat.execute("insert into rinci values("
                        + no //0
                        + ",'" + txtKodeBarang.getText() //1
                        + "','" + txtNamaBarang.getText() //2
                        + "','" + txtBatch.getText() //3
                        ////                + "','" + ((txtBatch.getText().length() > 0) ? txtBatch.getText() : "")
                        + "','" + ((txtBatch.getText().equals("")) ? "" : tglExpire.getText()) //
                        ////                + "','" + tglExpire.getText()
                        + "'," + txtJumlah.getText() //5
                        + ",'" + cboSatuan.getSelectedItem() //6
                        + "'," + b.getJumlah(Integer.parseInt(txtJumlah.getText()), cboSatuan.getSelectedItem().toString()) //7
                        + ")");
                reloadData();
                kosongBarang();
                txtKodeBarang.requestFocus();
            }
        }
    } catch (Exception ex) {
        //    Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}//GEN-LAST:event_btnInsertBarangActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
// TODO add your handling code here:\
    System.out.println(evt.getClickCount());
    if (evt.getClickCount() == 2) {
        if (jTabbedPane1.getSelectedIndex() == 0 || jTabbedPane1.getSelectedIndex() == 1) {
            JTable target = (JTable) evt.getSource();
            int row = target.getSelectedRow();
            btnInsertBarang.setText("Edit");
            prosesPilihBarang();
        } else {
            try {
                prosesPilihDO(c);
            } catch (SQLException ex) {
                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_jTable1MouseClicked

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    //Connection c = null;
    try {
        // TODO add your handling code here:
        //c = koneksi.getKoneksiJ();
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKODEDO.getText().equals("")) || (txtKodePelanggan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKodePelanggan.requestFocus();
            } else if (!KontrolTanggalDao.cekHarian(c, tglDO.getText())) {
                JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            } else {
                String tgal[] = Util.split(tglDO.getText(), "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                if (cekperiodeAda(c, per)) {
                    if (cekperiode(c, per)) {
                        DO d1 = new DO();
                        ID = DODao.getID(c);
                        d1.setID(ID);
                        d1.setKODEDO(DODao.getKODEDO(c));
                        d1.setKODEPELANGGAN(txtKodePelanggan.getText());
                        d1.setTANGGAL(tglDO.getText());
                        d1.setSTATUS("A");
                        d1.setTGLTUTUP(tglDO.getText());
                        d1.setSTATUSAKTIF("1");
                        if (DODao.insertIntoDO(c, d1)) {
                            Statement s = cm.createStatement();
                            ResultSet r = s.executeQuery("select * from rinci");
                            DORinci dr = new DORinci();
                            stok st = new stok();
                            while (r.next()) {
                                dr.setIDDO(ID);
                                dr.setKODEBARANG(r.getString("KODEBARANG"));
                                dr.setJUMLAH(r.getInt("JUMLAH"));
                                dr.setSATUAN(r.getString("SATUAN"));
                                dr.setKODEBATCH(r.getString("BATCH"));
                                dr.setEXPIRE(r.getString("EXPIRE"));
                                if (r.getString("EXPIRE").equals("")) {
                                    dr.setEXPIRE(null);
                                }
                                dr.setJUMLAHKECIL(r.getInt("JUMLAHKECIL"));
                                DORinciDao.insertIntoDORinci(c, dr);
                                st.setIDPENJUALAN(ID);
                                st.setKODEBARANG(r.getString("KODEBARANG"));
                                st.setTANGGAL(tglDO.getText());
                                st.setIN(0);
                                st.setOUT(r.getInt("JUMLAHKECIL"));
                                st.setKODETRANS("D");
                                st.setKODEBATCH(dr.getKODEBATCH());
                                stokDao.insertIntoSTOK(c, st);
//                                if (dr.getKODEBATCH().equals("")) {
//                                    StokDO stokDO = StokDODao.getDetails(c, "select * from STOKDO where KODEBARANG='" + dr.getKODEBARANG() + "'");
//                                    if (stokDO == null) {
//                                        stokDO = new StokDO();
//                                        stokDO.setKODEBARANG(r.getString("KODEBARANG"));
//                                        stokDO.setKODEBATCH("");
//                                        stokDO.setEXPIRE(null);
//                                        stokDO.setSTOK(dr.getJUMLAHKECIL());
//                                        StokDODao.insertIntoSTOKDO(c, stokDO);
//                                    } else {
//                                        stokDO.setSTOK(stokDO.getSTOK() + dr.getJUMLAHKECIL());
//                                        StokDODao.updateSTOKDO(c, stokDO);
//                                    }
//                                } else {
//                                    StokDO stokDO = StokDODao.getDetails(c, "select * from STOKDO where KODEBARANG='" + dr.getKODEBARANG() + "' AND KODEBATCH='" + dr.getKODEBATCH() + "'");
//                                    if (stokDO == null) {
//                                        stokDO = new StokDO();
//                                        stokDO.setKODEBARANG(dr.getKODEBARANG());
//                                        stokDO.setKODEBATCH(dr.getKODEBATCH());
//                                        stokDO.setEXPIRE(dr.getEXPIRE());
//                                        stokDO.setSTOK(dr.getJUMLAHKECIL());
//                                        StokDODao.insertIntoSTOKDO(c, stokDO);
//                                    } else {
//                                        stokDO.setSTOK(stokDO.getSTOK() + dr.getJUMLAHKECIL());
//                                        StokDODao.updateSTOKDO(c, stokDO);
//                                    }
//                                }
                            }
                            r.close();
                            s.close();
                            prosesUpdateLog(c, "Insert");
                            hapusTabel();
                            buatTabel();
                            String nofakDO = txtKODEDO.getText();
                            txtNoDOPrint.setText(nofakDO);

                            kosongDO(c);
                            kosongBarang();
                            reloadData();
                            settingtombol(true, false, false, false, false);
                            JOptionPane.showMessageDialog(this, "Entri DO Berhasil");
                            c.commit();
                            try {
                                if (txtNoDOPrint.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "Isi No Faktur DO yang akan di Print.. !");
                                    txtNoDOPrint.requestFocus();
                                } else {
                                    CetakFakturDO(txtNoDOPrint.getText());
                                }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e.toString());
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                        txtKodePelanggan.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                    txtKodePelanggan.requestFocus();
                }
            }
        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (SQLException ex) {
        try {
            c.rollback();
        } catch (SQLException ex1) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex1);
        }
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
//    finally {
//        if (c != null) {
//            try {
//                c.createStatement().execute("set autocommit true");
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}//GEN-LAST:event_btnInsertActionPerformed

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    //Connection c = null;
    try {
        // TODO add your handling code here:
        //c=koneksi.getKoneksiJ();
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            DODao.deleteFromDO(c, ID);
            prosesUpdateLog(c, "Delete");
            kosongDO(c);
            kosongBarang();
            hapusTabel();
            buatTabel();
            reloadData();
            JOptionPane.showMessageDialog(this, "Data Telah Dihapus");
            settingtombol(true, false, false, false, false);
            c.commit();
        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (SQLException ex) {
        try {
            c.rollback();
        } catch (SQLException ex1) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex1);
        }
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
//    finally {
//        if (c != null) {
//            try {
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}//GEN-LAST:event_btnDeleteActionPerformed

private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
    //Connection c = null;
    try {
        // TODO add your handling code here:
        //c = koneksi.getKoneksiJ();
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah DO akan di Close?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            Statement st = c.createStatement();
            String sql = "SELECT\n"
                    + "     DO.ID AS DO_ID,\n"
                    + "     DO.KODEDO AS DO_KODEDO,\n"
                    + "     DORINCI.KODEBARANG AS DORINCI_KODEBARANG,\n"
                    + "     DORINCI.JUMLAH AS DORINCI_JUMLAH, "
                    + "     DORINCI.JUMLAHKECIL, "
                    + "     DORINCI.KODEBATCH, "
                    + "FROM"
                    + "     DO INNER JOIN  DORINCI ON DO.ID = DORINCI.IDDO\n"
                    + "WHERE"
                    + "     DO.ID = " + ID + "";
            ResultSet rs = st.executeQuery(sql);
            dis.setSTATUS("N");
            dis.setTGLTUTUP(tglDO.getText());
            if (DODao.updateDO(c, dis)) {
                stok st1 = new stok();
                while (rs.next()) {
                    st1.setIDPENJUALAN(ID);
                    st1.setKODEBARANG(rs.getString(3));
                    st1.setTANGGAL(tglDO.getText());
                    st1.setIN(rs.getInt(5));
                    st1.setOUT(0);
                    st1.setKODETRANS("E");
                    st1.setKODEBATCH(rs.getString(6));
                    stokDao.insertIntoSTOK(c, st1);
                }
                prosesUpdateLog(c, "Close");
                hapusTabel();
                buatTabel();
                kosongDO(c);
                kosongBarang();
                reloadData();
                JOptionPane.showMessageDialog(this, "Proses Close DO Sukses");
                settingtombol(true, false, false, false, false);
                c.commit();
            } else {
                JOptionPane.showMessageDialog(this, "Proses Close DO Gagal");
                txtKodePelanggan.requestFocus();
            }
        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (Exception ex) {
        Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
    }
//    finally {
//        if (c != null) {
//            try {
//                c.createStatement().execute("set autocommit true");
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}//GEN-LAST:event_btnTutupActionPerformed

private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            pelanggan p = new pelangganDao(c).getDetails(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            if (jTabbedPane1.getSelectedIndex() == 0) {
                txtKodePelanggan.setText(p.getKODEPELANGGAN());
                txtNamaPelanggan.setText(p.getNAMA());
            } else if (jTabbedPane1.getSelectedIndex() == 1) {
                txtKodePelanggan1.setText(p.getKODEPELANGGAN());
                txtNamaPelanggan1.setText(p.getNAMA());
            }
            jScrollPane2.setVisible(false);
            txtKodeBarang.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
//        finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }
}//GEN-LAST:event_jTable2KeyPressed

private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            Barangstok bs;
            jScrollPane3.setVisible(false);
            barang b = new barangDao().getDetails(c, jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            bs = BarangstokDao.getDetailKodeBarang(c, jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            cboSatuan.removeAllItems();
            for (Iterator<Object> it = b.getItemSatuan().iterator(); it.hasNext();) {
                Object a = it.next();
                if (!a.equals("-")) {
                    cboSatuan.addItem(a);
                }
            }
            txtKodeBarang.setText(b.getKODEBARANG());
            txtNamaBarang.setText(b.getNAMABARANG());
            if (jTable3.getValueAt(jTable3.getSelectedRow(), 2) != null) {
                txtBatch.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString());
                Calendar cld = Calendar.getInstance();
                cld.setTime(d.parse(jTable3.getValueAt(jTable3.getSelectedRow(), 3).toString()));
                tglExpire.setSelectedDate(cld);
            }

            cboSatuan.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
//        finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }
}//GEN-LAST:event_jTable3KeyPressed

private void btnNonaktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonaktifActionPerformed
    //Connection c = null;
    try {
        // TODO add your handling code here:
        // c = koneksi.getKoneksiJ();
        int x = JOptionPane.showConfirmDialog(this, "Non Aktifkan DO ..?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            dis.setSTATUSAKTIF("0");
            DODao.updateDO(c, dis);
            prosesUpdateLog(c, "NonAktif");
            kosongDO(c);
            kosongBarang();
            hapusTabel();
            buatTabel();
            reloadData();
            JOptionPane.showMessageDialog(this, "DO Sudah Non Aktif..");
            settingtombol(true, false, false, false, false);
        } else {
            txtKodePelanggan.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
    }
//    finally {
//        if (c != null) {
//            try {
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}//GEN-LAST:event_btnNonaktifActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        try {
            // TODO add your handling code here:
            stat.close();
            cm.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formInternalFrameClosing

    private void tglExpireOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglExpireOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_tglExpireOnCommit

    private void cboSatuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSatuanItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cboSatuanItemStateChanged

    private void cboSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSatuanKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtJumlah.requestFocus();
        }
    }//GEN-LAST:event_cboSatuanKeyPressed

    private void btnReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturActionPerformed
        // TODO add your handling code here:
        int idreturdo;
        String tgl[] = Util.split(tglDO1.getText(), "-");
        String per = tgl[0] + "." + Integer.parseInt(tgl[1]);
        try {
            c.createStatement().execute("set autocommit false");
            if (!cekperiodeAda(c, per)) {
                throw new Exception("Transaksi Untuk Periode Ini Belum Dibuka.. !");
            }
            if (!cekperiode(c, per)) {
                throw new Exception("Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
            }
            Returdo rdo = new Returdo();
            rdo.setId(0);
            rdo.setKodereturdo(txtKodeRetur.getText());
            rdo.setTanggal(tglDO1.getText());
            rdo.setKodepelanggan(txtKodePelanggan1.getText());
            rdo.setKeterangan("Retur DO Pelanggan " + txtNamaPelanggan1.getText());
            rdo.setIddo(dis.getID());
            rdo.setStatus(0);
            Set<ConstraintViolation<Returdo>> violations = validator.validate(rdo);
            if (violations.size() > 0) {
                String msg = "";
                for (ConstraintViolation<Returdo> constraintViolation : violations) {
                    msg += constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + "\n";
                }
                throw new Exception(msg);
            } else {
                idreturdo = ReturdoDao.insertIntoRETURDO(c, rdo);
                Statement s = cm.createStatement();
                ResultSet r = s.executeQuery("select * from rinci");
                while (r.next()) {
                    Returdorinci rdr = new Returdorinci();
                    rdr.setId(0);
                    rdr.setIdreturdo(idreturdo);
                    rdr.setKodebarang(r.getString("KODEBARANG"));
                    rdr.setJumlah(r.getInt("JUMLAH"));
                    rdr.setSatuan(r.getString("SATUAN"));
                    rdr.setKodebatch(r.getString("BATCH"));
                    if (!rdr.getKodebatch().equals("")) {
                        rdr.setExpire(r.getString("EXPIRE"));
                    } else {
                        rdr.setExpire(null);
                    }
                    rdr.setJumlahkecil(r.getInt("JUMLAHKECIL"));
                    /////
                    ReturdorinciDao.insertIntoRETURDORINCI(c, rdr);
                    ///////////
////                    stok st = new stok();
////                    st.setIDPENJUALAN(idreturdo);
////                    st.setKODEBARANG(rdr.getKodebarang());
////                    st.setTANGGAL(rdo.getTanggal());
////                    st.setIN(rdr.getJumlahkecil());
////                    st.setOUT(0);
////                    st.setKODETRANS("E");
////                    st.setKODEBATCH(rdr.getKodebatch());
////                    stokDao.insertIntoSTOK(c, st);
////                    DODao.eksekusiStatusDORetur(c, dis.getID());
                }
                r.close();
                s.close();
                //////////////////
////                lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
////                lh.setTANGGAL(Util.toDateStringSql(new Date()));
////                lh.setJAM(u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang);
////                lh.setTABEL("TRETURDO");
////                lh.setNOREFF(txtKodeRetur.getText());
////                lh.setAKSI("Insert");
////                lh.setKETERANGAN("Insert Retur DO " + txtKodeRetur.getText());
////                lhdao.insert(c, lh);
                ///////////////////////////////

                hapusTabel();
                buatTabel();
                kosongRetur();
                reloadData();
            }
            c.commit();
            JOptionPane.showMessageDialog(this, "Entri Retur Ok");
        } catch (Exception e) {
            try {
                c.rollback();
                System.out.println(c.isClosed());
            } catch (SQLException ex) {
                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnReturActionPerformed

    private void btnDeleteBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBarangActionPerformed
        // TODO add your handling code here:
        kosongBarang();
    }//GEN-LAST:event_btnDeleteBarangActionPerformed

    private void txtKodePelanggan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelanggan1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_txtKodePelanggan1KeyPressed

    private void txtKodePelanggan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelanggan1ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            jScrollPane2.setVisible(true);
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where KODEPELANGGAN like '" + txtKodePelanggan.getText() + "%' or lower(NAMA) like '%" + txtKodePelanggan.getText().toLowerCase() + "%'");
            jScrollPane2.getViewport().remove(jTable2);
            jTable2 = new JTable(j);
            jTable2.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable2KeyPressed(evt);
                }
            });
            jScrollPane2.getViewport().add(jTable2);
            jScrollPane2.validate();
        } catch (Exception ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtKodePelanggan1ActionPerformed

    private void txtKodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_txtKodePelangganKeyPressed

    private void txtKodePelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelangganActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            jScrollPane2.setVisible(true);
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where STATUSAKTIF='0' AND KODEPELANGGAN like '" + txtKodePelanggan.getText() + "%' or lower(NAMA) like '%" + txtKodePelanggan.getText().toLowerCase() + "%'");
            jTable2.setModel(j);
            jScrollPane2.validate();
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_txtKodePelangganActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        if (txtNoDOPrint.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
            txtNoDOPrint.requestFocus();
        } else {
            try {
                if (RadioPendek.isSelected()) {
                    viewFakturDOPendek(txtNoDOPrint.getText());
                } else if (RadioPanjang.isSelected()) {
                    viewFakturDOPanjang(txtNoDOPrint.getText());
                }
            } catch (Exception ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        try {
            if (txtNoDOPrint.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Isi No Faktur DO yang akan di Print.. !");
                txtNoDOPrint.requestFocus();
            } else {
                CetakFakturDO(txtNoDOPrint.getText());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtNamaPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPelangganActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            jScrollPane2.setVisible(true);
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where STATUSAKTIF='0' AND (KODEPELANGGAN like '%" + txtNamaPelanggan.getText() + "%' or lower(NAMA) like '%" + txtNamaPelanggan.getText().toLowerCase() + "%')");
            jScrollPane2.getViewport().remove(jTable2);
            jTable2 = new JTable(j);
            jTable2.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable2KeyPressed(evt);
                }
            });
            jScrollPane2.getViewport().add(jTable2);
            jScrollPane2.validate();
        } catch (Exception ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_txtNamaPelangganActionPerformed

    private void txtNamaPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaPelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_txtNamaPelangganKeyPressed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void RadioPanjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPanjangActionPerformed
        // TODO add your handling code here:
        pilihanKertasPanjang();
    }//GEN-LAST:event_RadioPanjangActionPerformed

    private void RadioPendekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPendekActionPerformed
        // TODO add your handling code here:
        pilihanKertasPendek();
    }//GEN-LAST:event_RadioPendekActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadioPanjang;
    private javax.swing.JRadioButton RadioPendek;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteBarang;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnInsertBarang;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnNonaktif;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRetur;
    private javax.swing.JButton btnTutup;
    private javax.swing.JComboBox cboSatuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private com.erv.function.PanelCool panelCool1;
    private datechooser.beans.DateChooserCombo tglDO;
    private datechooser.beans.DateChooserCombo tglDO1;
    private datechooser.beans.DateChooserCombo tglExpire;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JFormattedTextField txtJumlah;
    private javax.swing.JTextField txtKODEDO;
    private javax.swing.JTextField txtKdDO;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtKodePelanggan1;
    private javax.swing.JTextField txtKodeRetur;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNamaPelanggan1;
    private javax.swing.JTextField txtNoDOPrint;
    // End of variables declaration//GEN-END:variables

    private void kosongDO(Connection c) {
        txtKODEDO.setText("");
        txtKodePelanggan.setText("");
        txtNamaPelanggan.setText("");
        txtKODEDO.setText(DODao.getKODEDO(c));
    }

    private void kosongBarang() {
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        txtNamaBarang.setEditable(false);
        txtJumlah.setText("");
        txtBatch.setText("");
    }

    private void buatTabel() {
        String sqlCreate = "create table rinci ("
                + "NO int primary key, " //1
                + "KODEBARANG varchar(20), " //2
                + "NAMABARANG varchar(150), " //3
                + "BATCH varchar(20), " //4
                + "EXPIRE varchar(10), " //5
                + "JUMLAH int, " //6
                + "SATUAN varchar(30), " //7
                + "JUMLAHKECIL int)";     //8
        try {
            stat.execute(sqlCreate);
        } catch (SQLException ex) {
        }
    }

    private void settingPosisi() {
        jScrollPane2.setVisible(false);
        jScrollPane2.setSize(450, 150);
        panelCool1.setComponentZOrder(jScrollPane2, 0);
        jScrollPane3.setVisible(false);
        jScrollPane3.setSize(700, 150);
        panelCool1.setComponentZOrder(jScrollPane3, 1);
    }

    void hapusTabel() {
        try {
            stat.execute("drop table rinci");
        } catch (SQLException ex) {
        }
    }

//    void reloadDO(Connection c) {
//        JDBCAdapter j = new JDBCAdapter(c);
//        String sql = "select ID, KODEDO, TANGGAL,KODEPELANGGAN, "
//                + "case STATUS when 'A' then 'Open' when 'N' then 'Close' end as STATUSDO "
//                + "from DO where KODEPELANGGAN='" + jTable4.getValueAt(jTable4.getSelectedRow(), 0) + "' AND STATUSAKTIF='1'";
//        j.executeQuery(sql);
//        jScrollPane1.getViewport().remove(jTable1);
//        jTable1 = new JTable(j);
//        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jTable1MouseClicked(evt);
//            }
//        });
//        jScrollPane1.getViewport().add(jTable1);
//        jScrollPane1.validate();
//    }
    void reloadData() throws SQLException {
        JDBCAdapter j = new JDBCAdapter(cm);
        j.executeQuery("select * from rinci order by no");
        jScrollPane1.getViewport().remove(jTable1);
        jTable1 = new JTable(j);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
////        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TableColumn col = jTable1.getColumnModel().getColumn(0);
        col.setPreferredWidth(8);
        col = jTable1.getColumnModel().getColumn(1);
        col.setPreferredWidth(20);
        col = jTable1.getColumnModel().getColumn(2);
        col.setPreferredWidth(300);
        col = jTable1.getColumnModel().getColumn(3);
        col.setPreferredWidth(50);
        col = jTable1.getColumnModel().getColumn(4);
        col.setPreferredWidth(50);
        col = jTable1.getColumnModel().getColumn(5);
        col.setPreferredWidth(50);
        col = jTable1.getColumnModel().getColumn(6);
        col.setPreferredWidth(50);
        TableColumn col1 = jTable1.getColumnModel().getColumn(6);
        col1.setMinWidth(0);
        col1.setMaxWidth(0);
        jScrollPane1.setFocusable(false);
        jTable1.setFocusable(false);
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane1.repaint();
        j.close();
    }

    void prosesPilihDO(Connection c) throws SQLException {
        int id = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        dis = DODao.getDetails(c, id);
        ID = dis.getID();
        txtKODEDO.setText(dis.getKODEDO());
        tglDO.setText(dis.getTANGGAL().toString());
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("Select * from DORINCI where IDDO=" + ID + "");
        hapusTabel();
        buatTabel();
        while (rs.next()) {
            this.stat.execute("insert into rinci values ('"
                    + rs.getString("KODEBARANG") + "','"
                    + new barangDao().getDetails(c, rs.getString("KODEBARANG")).getNAMABARANG() + "','"
                    + rs.getString("KODEBATCH") + "','"
                    + rs.getString("EXPIRE") + "',"
                    + rs.getInt("JUMLAH") + ",'"
                    + rs.getString("SATUAN") + "',"
                    + rs.getInt("JUMLAHKECIL") + ""
                    + ")"
            );
        }
        reloadData();
        jTabbedPane1.setSelectedIndex(0);
        settingtombol(false, false, false, true, true);
        rs.close();
        st.close();
    }

    void prosesPilihBarang() {
        try {
            // TODO add your handling code here:           
//            JTable target = (JTable) evt.getSource();
//            int row = target.getSelectedRow();
            Statement s = cm.createStatement();
////            ResultSet rs = s.executeQuery("select * from rinci where KODEBARANG='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "' AND BATCH='" + jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString() + "'");
            ResultSet rs = s.executeQuery("select * from rinci where NO=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "");
            if (rs.next()) {
                no = rs.getInt(1);
                txtKodeBarang.setText(rs.getString(2));
                txtNamaBarang.setText(rs.getString(3));
                if (rs.getString("BATCH").length() > 0) {
                    txtBatch.setText(rs.getString("BATCH"));
                    tglExpire.setText(rs.getString("EXPIRE"));
                }
                txtJumlah.setText(rs.getString("JUMLAH"));
                barang b = new barangDao().getDetails(c, rs.getString(2));
                try {
                    cboSatuan.removeAllItems();
                } catch (Exception e) {
                }
                for (Object a : b.getItemSatuan()) {
                    if (!a.equals("-")) {
                        cboSatuan.addItem(a);
                    }
                }
            }
            ////s.execute("delete from rinci where KODEBARANG='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "' AND BATCH='" + jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString() + "'");
            s.execute("delete from rinci where NO='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "'");
            reloadData();
            rs.close();
            s.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void settingtombol(boolean simp, boolean retur, boolean hapus, boolean tutup, boolean nonaktif) {
//        btnInsert.setEnabled(simp);
//        btnRetur.setEnabled(retur);
//        btnDelete.setEnabled(hapus);
//        btnTutup.setEnabled(tutup);
//        btnNonaktif.setEnabled(nonaktif);

        btnInsert.setVisible(simp);
        btnRetur.setVisible(retur);
        btnDelete.setVisible(hapus);
        btnTutup.setVisible(tutup);
        btnNonaktif.setVisible(nonaktif);
    }

    void prosesUpdateLog(Connection c, String aksi) throws SQLException {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        String ketlog = "";
        lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
        lh.setTANGGAL(tanggallog);
        lh.setJAM(jamlog);
        lh.setTABEL("TDO");
        lh.setNOREFF(txtKODEDO.getText());
        lh.setAKSI(aksi);
        if (aksi.equals("Insert")) {
            ketlog = "Insert Data DO " + txtKODEDO.getText();
        } else if (aksi.equals("Close")) {
            ketlog = "Close Data DO " + txtKODEDO.getText();
        } else if (aksi.equals("Delete")) {
            ketlog = "Delete Data DO " + txtKODEDO.getText();
        } else if (aksi.equals("NonAktif")) {
            ketlog = "Non Aktifkan Data DO " + txtKODEDO.getText();
        } else if (aksi.equals("RetuDO")) {
            ketlog = "Insert Retur DO " + txtKODEDO.getText();
        }
        lh.setKETERANGAN(ketlog);
        lhdao.insert(c, lh);
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

    public boolean cekperiode(Connection c, String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSSTOK='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }
        rs.close();
        s.close();
        return hasil1;
    }

//    public void isiComboBatch(List<Barangstokbatch> ls) {
//        //cboBatch.removeAllItems();
//        jPanel2.remove(cboBatch);
//        cboBatch = new JComboBox();
//        expire = new String[ls.size()];
//        int count = 0;
//        for (Iterator<Barangstokbatch> it = ls.iterator(); it.hasNext();) {
//            Barangstokbatch barangstokbatch = it.next();
//            cboBatch.addItem(barangstokbatch.getKODEBATCH());
//            expire[count] = barangstokbatch.getEXPIRE().toString();
//            count++;
//        }
//        Calendar cld = Calendar.getInstance();
//        try {
//            cld.setTime(d.parse(expire[0]));
//        } catch (ParseException ex) {
//            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        tglExpire.setSelectedDate(cld);
//        cboBatch.addItemListener(new java.awt.event.ItemListener() {
//            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                cboBatchItemStateChanged(evt);
//            }
//        });
//        cboBatch.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyPressed(java.awt.event.KeyEvent evt) {
//                cboBatchKeyPressed(evt);
//            }
//        });
//        cboBatch.setFont(resourceMap1.getFont("cboBatch.font")); // NOI18N
//        jPanel2.add(cboBatch);
//        cboBatch.setBounds(410, 20, 100, 20);
//    }
    private void tampilDO(DO d) throws SQLException {
        txtKODEDO.setText(d.getKODEDO());
        txtKodePelanggan.setText(d.getKODEPELANGGAN());
        txtNamaPelanggan.setText(new pelangganDao(c).getDetails(d.getKODEPELANGGAN()).getNAMA());
        tglDO.setText(d.getTANGGAL());
    }

    private void tampilDORinci(List<DORinci> listDO) throws SQLException {
        int no = 0;
        for (DORinci dorinci : listDO) {
            no++;
            barang b = new barangDao().getDetails(c, dorinci.getKODEBARANG());
            stat.execute("insert into rinci values("
                    + no
                    + ",'" + dorinci.getKODEBARANG()
                    + "','" + b.getNAMABARANG()
                    + "','" + ((dorinci.getKODEBATCH().length() > 0) ? dorinci.getKODEBATCH() : "")
                    + "','" + ((dorinci.getEXPIRE() == null) ? "" : dorinci.getEXPIRE())
                    + "'," + dorinci.getJUMLAH()
                    + ",'" + dorinci.getSATUAN()
                    + "'," + b.getJumlah(dorinci.getJUMLAH(), dorinci.getSATUAN())
                    + ")");
        }
        reloadData();
    }

    public void pilihJTabbedPane(JTabbedPane jtp, int pil) {
        for (int i = 0; i < jtp.getTabCount(); i++) {
            if (pil == i) {
                jtp.setEnabledAt(i, true);
            } else {
                jtp.setEnabledAt(i, false);
            }
        }
        jtp.setSelectedIndex(pil);
    }

    private void kosongRetur() {
        txtKodeRetur.setText("");
        txtKdDO.setText("");
        txtKodePelanggan1.setText("");
        txtNamaPelanggan1.setText("");
    }

    void viewFakturDOPendek(String nofakturDO) {
        Map<String, Object> p = ClassPrintDO.cetakfakturMap(c, nofakturDO);
        ViewFakturDO f = new ViewFakturDO(p);
        JavarieSoftView.panelCool1.add(f);
        f.setSize(800, 400);
        f.toFront();
        f.setVisible(true);

    }

    void viewFakturDOPanjang(String nofakturDO) {
        Map<String, Object> p = ClassPrintDOKertasPanjang.cetakfakturMap(c, nofakturDO);
        ViewFakturDOPanjang f = new ViewFakturDOPanjang(p);
        JavarieSoftView.panelCool1.add(f);
        f.setSize(800, 400);
        f.toFront();
        f.setVisible(true);

    }

    void CetakFakturDO(String nofaktur) {
        Map<String, Object> map = ClassPrintDO.cetakfakturMap(c, nofaktur);
        Template template = null;
        try {
            template = new JsonTemplate(ViewFaktur.class.getResourceAsStream("fakturDO.json"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        SimpleEscp simpleEscp = new SimpleEscp("\\\\Mypc-pc\\EPSON LX-300+ /II"); //printer herman    
//        SimpleEscp simpleEscp = new SimpleEscp("Epson LX-300+"); //printer kak novi Epson LX-300+
        SimpleEscp simpleEscp = new SimpleEscp("EPSON LX-310 ESC/P"); //printer kak novi EPSON LX-310 ESC/P

        simpleEscp.print(template, map);
    }

    private void tampilDORetur(Returdo returdo) {
        try {
            txtKodeRetur.setText(returdo.getKodereturdo());
            txtKdDO.setText(DODao.getDetails(c, (returdo.getIddo())).getKODEDO());
            txtKodePelanggan1.setText(returdo.getKodepelanggan());
            txtNamaPelanggan1.setText(new pelangganDao(c).getDetails(returdo.getKodepelanggan()).getNAMA());
            tglDO1.setText(returdo.getTanggal());
        } catch (SQLException ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tampilReturDoRinci(List<Returdorinci> listReturdorinci) throws SQLException {
        int no = 0;
        for (Returdorinci returdorinci : listReturdorinci) {
            no++;
            barang b = new barangDao().getDetails(c, returdorinci.getKodebarang());
            stat.execute("insert into rinci values("
                    + no
                    + ",'" + returdorinci.getKodebarang()
                    + "','" + b.getNAMABARANG()
                    + "','" + ((returdorinci.getKodebatch().length() > 0) ? returdorinci.getKodebatch() : "")
                    + "','" + (returdorinci.getExpire() == null ? "" : returdorinci.getExpire())
                    + "'," + returdorinci.getJumlah()
                    + ",'" + returdorinci.getSatuan()
                    + "'," + b.getJumlah(returdorinci.getJumlah(), returdorinci.getSatuan())
                    + ")");
        }
        reloadData();
    }

    void pilihanKertasPendek() {
        RadioPendek.setSelected(true);
        RadioPanjang.setSelected(false);
    }

    void pilihanKertasPanjang() {
        RadioPendek.setSelected(false);
        RadioPanjang.setSelected(true);
    }

}
