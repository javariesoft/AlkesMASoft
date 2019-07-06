/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormSupplier.java
 *
 * Created on Dec 13, 2011, 5:52:08 AM
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.db.perkiraanDao;
import com.erv.db.settingDao;
import com.erv.db.supplierDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.perkiraan;
import com.erv.model.supplier;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import java.awt.HeadlessException;
import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author erwadi
 */
public class FormSupplier extends javax.swing.JInternalFrame {

    perkiraan pr;
    perkiraanDao dbpr;
    supplier p;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    MaskFormatter mf1;
    DefaultFormatterFactory formatterFactory;
    Connection c;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";

    /**
     * Creates new form FormSupplier
     */
    public FormSupplier() {
        initComponents();
        kosongkan();
        dateTglReg.setDateFormat(d);
        try {
            c = koneksi.getKoneksiJ();
            p = new supplier();
            pr = new perkiraan();
            dbpr = new perkiraanDao();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            cektombol();
            reloadData(cboAktif.getSelectedIndex());
            cboKriteria.setSelectedIndex(1);
            mf1 = new MaskFormatter("##.###.###.#-###.####");
            mf1.setPlaceholderCharacter('_');
            mf1.setValidCharacters("0123456789");
            mf1.setValueClass(String.class);
            formatterFactory = new DefaultFormatterFactory(mf1);
            txtNPWP.setFormatterFactory(formatterFactory);
        } catch (SQLException ex) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtkodeSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtNohp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtbatasKredit = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        dateTglReg = new datechooser.beans.DateChooserCombo();
        jSeparator1 = new javax.swing.JSeparator();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtKriteria = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblKodeAkun = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtNPWP = new javax.swing.JFormattedTextField();
        cboAktif = new javax.swing.JComboBox();
        cboKriteria = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormSupplier.class);
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

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(30, 45, 120, 16);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(30, 20, 120, 16);

        txtkodeSupplier.setEditable(false);
        txtkodeSupplier.setFont(resourceMap.getFont("txtkodeSupplier.font")); // NOI18N
        txtkodeSupplier.setName("txtkodeSupplier"); // NOI18N
        panelCool1.add(txtkodeSupplier);
        txtkodeSupplier.setBounds(200, 45, 160, 22);

        txtNamaSupplier.setFont(resourceMap.getFont("txtNamaSupplier.font")); // NOI18N
        txtNamaSupplier.setName("txtNamaSupplier"); // NOI18N
        txtNamaSupplier.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamaSupplierFocusLost(evt);
            }
        });
        txtNamaSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupplierActionPerformed(evt);
            }
        });
        txtNamaSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaSupplierKeyPressed(evt);
            }
        });
        panelCool1.add(txtNamaSupplier);
        txtNamaSupplier.setBounds(200, 20, 440, 22);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        panelCool1.add(jLabel8);
        jLabel8.setBounds(30, 80, 120, 16);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtAlamat.setColumns(20);
        txtAlamat.setFont(resourceMap.getFont("txtAlamat.font")); // NOI18N
        txtAlamat.setRows(5);
        txtAlamat.setName("txtAlamat"); // NOI18N
        txtAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtAlamat);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(200, 70, 310, 50);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        panelCool1.add(jLabel5);
        jLabel5.setBounds(30, 124, 120, 16);

        txtNohp.setFont(resourceMap.getFont("txtNohp.font")); // NOI18N
        txtNohp.setName("txtNohp"); // NOI18N
        txtNohp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNohpKeyPressed(evt);
            }
        });
        panelCool1.add(txtNohp);
        txtNohp.setBounds(200, 124, 210, 22);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        panelCool1.add(jLabel6);
        jLabel6.setBounds(30, 150, 120, 16);

        txtbatasKredit.setFont(resourceMap.getFont("txtbatasKredit.font")); // NOI18N
        txtbatasKredit.setName("txtbatasKredit"); // NOI18N
        txtbatasKredit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbatasKreditKeyPressed(evt);
            }
        });
        panelCool1.add(txtbatasKredit);
        txtbatasKredit.setBounds(200, 150, 210, 22);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        panelCool1.add(jLabel4);
        jLabel4.setBounds(30, 228, 120, 16);

        dateTglReg.setFieldFont(resourceMap.getFont("dateTglReg.dch_combo_fieldFont")); // NOI18N
        panelCool1.add(dateTglReg);
        dateTglReg.setBounds(200, 176, 160, 20);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelCool1.add(jSeparator1);
        jSeparator1.setBounds(30, 260, 620, 10);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        panelCool1.add(btnInsert);
        btnInsert.setBounds(30, 270, 90, 25);

        btnUpdate.setFont(resourceMap.getFont("btnUpdate.font")); // NOI18N
        btnUpdate.setIcon(resourceMap.getIcon("btnUpdate.icon")); // NOI18N
        btnUpdate.setText(resourceMap.getString("btnUpdate.text")); // NOI18N
        btnUpdate.setName("btnUpdate"); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        panelCool1.add(btnUpdate);
        btnUpdate.setBounds(120, 270, 100, 25);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelCool1.add(btnDelete);
        btnDelete.setBounds(220, 270, 100, 25);

        btnCancel.setFont(resourceMap.getFont("btnCancel.font")); // NOI18N
        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        panelCool1.add(btnCancel);
        btnCancel.setBounds(350, 270, 110, 25);

        jSeparator2.setName("jSeparator2"); // NOI18N
        panelCool1.add(jSeparator2);
        jSeparator2.setBounds(30, 300, 620, 10);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        panelCool1.add(jLabel7);
        jLabel7.setBounds(30, 310, 50, 16);

        txtKriteria.setFont(resourceMap.getFont("txtKriteria.font")); // NOI18N
        txtKriteria.setName("txtKriteria"); // NOI18N
        txtKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKriteriaActionPerformed(evt);
            }
        });
        panelCool1.add(txtKriteria);
        txtKriteria.setBounds(280, 310, 220, 22);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setIcon(resourceMap.getIcon("btnFilter.icon")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        panelCool1.add(btnFilter);
        btnFilter.setBounds(510, 310, 110, 25);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        panelCool1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 340, 660, 260);

        lblKodeAkun.setFont(resourceMap.getFont("lblKodeAkun.font")); // NOI18N
        lblKodeAkun.setForeground(resourceMap.getColor("lblKodeAkun.foreground")); // NOI18N
        lblKodeAkun.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKodeAkun.setText(resourceMap.getString("lblKodeAkun.text")); // NOI18N
        lblKodeAkun.setName("lblKodeAkun"); // NOI18N
        panelCool1.add(lblKodeAkun);
        lblKodeAkun.setBounds(480, 204, 170, 20);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        panelCool1.add(btnKeluar);
        btnKeluar.setBounds(470, 270, 110, 25);

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setForeground(resourceMap.getColor("jLabel9.foreground")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        panelCool1.add(jLabel9);
        jLabel9.setBounds(30, 176, 120, 16);

        txtNPWP.setFont(resourceMap.getFont("txtNPWP.font")); // NOI18N
        txtNPWP.setName("txtNPWP"); // NOI18N
        txtNPWP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNPWPKeyPressed(evt);
            }
        });
        panelCool1.add(txtNPWP);
        txtNPWP.setBounds(200, 200, 270, 22);

        cboAktif.setFont(resourceMap.getFont("cboAktif.font")); // NOI18N
        cboAktif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktif", "Non Aktif" }));
        cboAktif.setName("cboAktif"); // NOI18N
        panelCool1.add(cboAktif);
        cboAktif.setBounds(100, 310, 78, 22);

        cboKriteria.setFont(resourceMap.getFont("cboKriteria.font")); // NOI18N
        cboKriteria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode", "Nama" }));
        cboKriteria.setName("cboKriteria"); // NOI18N
        panelCool1.add(cboKriteria);
        cboKriteria.setBounds(180, 310, 90, 22);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setForeground(resourceMap.getColor("jLabel10.foreground")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        panelCool1.add(jLabel10);
        jLabel10.setBounds(30, 204, 120, 16);

        cboStatus.setFont(resourceMap.getFont("cboStatus.font")); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktif", "Tidak Aktif" }));
        cboStatus.setName("cboStatus"); // NOI18N
        panelCool1.add(cboStatus);
        cboStatus.setBounds(200, 228, 130, 22);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 802, 638);
    }// </editor-fold>//GEN-END:initComponents

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtNamaSupplier.getText().equals("")) || (txtkodeSupplier.getText().equals("")) || (txtAlamat.getText().equals("")) || (txtNohp.getText().equals("")) || (txtbatasKredit.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtNamaSupplier.requestFocus();
            } else {
                aksilog = "Insert";
                prosesUpdate(0);
                prosesUpdateAkun(0);
                prosesUpdateLog();
                reloadData(cboAktif.getSelectedIndex());
                cektombol();
                kosongkan();
                txtNamaSupplier.requestFocus();
                c.commit();
            }
        } else {
            txtNamaSupplier.requestFocus();
        }
    } catch (SQLException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex1);
        }
    } finally {
//                if (c != null) {
        try {
            c.createStatement().execute("set autocommit true");
//                        c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
//                }
    }
}//GEN-LAST:event_btnInsertActionPerformed

private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Diedit?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtNamaSupplier.getText().equals("")) || (txtkodeSupplier.getText().equals("")) || (txtAlamat.getText().equals("")) || (txtNohp.getText().equals("")) || (txtbatasKredit.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtNamaSupplier.requestFocus();
            } else {
                aksilog = "Update";
                prosesUpdate(1);
                prosesUpdateAkun(1);
                prosesUpdateLog();
                reloadData(cboAktif.getSelectedIndex());
                cektombol();
                kosongkan();
                txtNamaSupplier.requestFocus();
            }
        } else {
            txtNamaSupplier.requestFocus();
        }
    } catch (SQLException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex1);
        }
    } finally {
//                if (c != null) {
        try {
            c.createStatement().execute("set autocommit true");
//                        c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
//                }
    }
}//GEN-LAST:event_btnUpdateActionPerformed

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        // TODO add your handling code here:
        c.createStatement().execute("set autocommit false");
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            aksilog = "Delete";
            supplierDao.deleteFromSUPPLIER(c, txtkodeSupplier.getText());
            dbpr.deleteDetails(lblKodeAkun.getText());
            prosesUpdateLog();
            reloadData(cboAktif.getSelectedIndex());
            cektombol();
            kosongkan();
            txtNamaSupplier.requestFocus();
        } else {
            txtNamaSupplier.requestFocus();
        }
    } catch (SQLException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }   catch (HeadlessException ex) {
        try {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
        } catch (SQLException ex1) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex1);
        }
        } finally {
//                if (c != null) {
        try {
            c.createStatement().execute("set autocommit true");
//                        c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
//                }
    }
}//GEN-LAST:event_btnDeleteActionPerformed

private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
// TODO add your handling code here:
    cektombol();
    kosongkan();
    txtNamaSupplier.requestFocus();
}//GEN-LAST:event_btnCancelActionPerformed

private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
// TODO add your handling code here:
    reloadData(cboAktif.getSelectedIndex());
}//GEN-LAST:event_btnFilterActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    try {
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();
        p = supplierDao.getDetails(c, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        try {
            cal.setTime(d.parse(p.getTGLREG()));
        } catch (ParseException ex) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtkodeSupplier.setText(p.getIDSUPPLIER());
        txtNamaSupplier.setText(p.getNAMA());
        txtAlamat.setText(p.getALAMAT());
        txtNohp.setText(p.getNOHP());
        dateTglReg.setSelectedDate(cal);
        txtbatasKredit.setValue(p.getBATASKREDIT());
        lblKodeAkun.setText(p.getKODEAKUN());
        txtNPWP.setText(p.getNPWP());
        cboStatus.setSelectedIndex(p.getSTATUSAKTIF());
        txtAlamat.requestFocus();
        cektombol();
    } catch (SQLException ex) {
        Logger.getLogger(FormPelanggan.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_jTable1MouseClicked

private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
    try {
        // TODO add your handling code here:
        txtNamaSupplier.setText(txtNamaSupplier.getText().toUpperCase());
        txtkodeSupplier.setText("" + supplierDao.getID(c));
        lblKodeAkun.setText(settingDao.getAkun(c, "HUTANG") + "." + txtkodeSupplier.getText());
        txtAlamat.requestFocus();
    } catch (SQLException ex) {
        Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtNamaSupplierActionPerformed

private void txtAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtNohp.requestFocus();
    }
}//GEN-LAST:event_txtAlamatKeyPressed

private void txtNohpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNohpKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtbatasKredit.requestFocus();
    }
}//GEN-LAST:event_txtNohpKeyPressed

private void txtbatasKreditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbatasKreditKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        btnInsert.requestFocus();
    }
}//GEN-LAST:event_txtbatasKreditKeyPressed

private void txtNamaSupplierFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaSupplierFocusLost
// TODO add your handling code here:
    txtAlamat.requestFocus();
}//GEN-LAST:event_txtNamaSupplierFocusLost

private void txtNamaSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaSupplierKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtAlamat.requestFocus();
    }
}//GEN-LAST:event_txtNamaSupplierKeyPressed

    private void txtKriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKriteriaActionPerformed
        // TODO add your handling code here:
        reloadData(cboAktif.getSelectedIndex());
    }//GEN-LAST:event_txtKriteriaActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtNPWPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNPWPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNPWPKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cboAktif;
    private javax.swing.JComboBox cboKriteria;
    private javax.swing.JComboBox cboStatus;
    private datechooser.beans.DateChooserCombo dateTglReg;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblKodeAkun;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtKriteria;
    private javax.swing.JFormattedTextField txtNPWP;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNohp;
    private javax.swing.JFormattedTextField txtbatasKredit;
    private javax.swing.JTextField txtkodeSupplier;
    // End of variables declaration//GEN-END:variables

    private void kosongkan() {
        txtkodeSupplier.setText("");
        txtNamaSupplier.setText("");
        txtAlamat.setText("");
        txtNohp.setText("");
        txtbatasKredit.setValue(new Integer(0));
        lblKodeAkun.setText("");
        txtNPWP.setText("");
    }

    private void reloadData(int index) {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            String sql = "";
            if (index == 0) {
                sql = "SELECT IDSUPPLIER , NAMA, ALAMAT , NOHP,BATASKREDIT ,TGLREG ,KODEAKUN ,NPWP,case STATUSAKTIF when 0 then 'Aktif' when 1 then 'Tidak Aktif' end as StatAktif FROM SUPPLIER where STATUSAKTIF='0'";
            } else {
                sql = "SELECT IDSUPPLIER , NAMA, ALAMAT , NOHP,BATASKREDIT ,TGLREG ,KODEAKUN ,NPWP,case STATUSAKTIF when 0 then 'Aktif' when 1 then 'Tidak Aktif' end as StatAktif FROM SUPPLIER where STATUSAKTIF='1'";
            }
            if (cboKriteria.getSelectedIndex() == 0) {
                sql += " AND lower(IDSUPPLIER) like '" + txtKriteria.getText().toLowerCase() + "%' ORDER BY NAMA";
            }
            if (cboKriteria.getSelectedIndex() == 1) {
                sql += " AND lower(NAMA) like '%" + txtKriteria.getText().toLowerCase() + "%' ORDER BY NAMA";
            }
            j.executeQuery(sql);
            jScrollPane2.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jScrollPane2.getViewport().add(jTable1);
            jScrollPane2.repaint();
            j.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void prosesUpdate(int pil) throws SQLException {
        p.setIDSUPPLIER(txtkodeSupplier.getText());
        p.setNAMA(txtNamaSupplier.getText());
        p.setALAMAT(txtAlamat.getText());
        p.setNOHP(txtNohp.getText());
        p.setTGLREG(dateTglReg.getText());
        p.setBATASKREDIT(Float.parseFloat(txtbatasKredit.getValue().toString()));
        p.setKODEAKUN(lblKodeAkun.getText());
        p.setNPWP(txtNPWP.getText());
        p.setSTATUSAKTIF(cboStatus.getSelectedIndex());
//        try {
        boolean stat;
        if (pil == 0) {
            stat = supplierDao.insertIntoSUPPLIER(c, p);
        } else {
            stat = supplierDao.updateSUPPLIER(c, p.getIDSUPPLIER(), p);
        }
        if (stat) {
            JOptionPane.showMessageDialog(this, "Update Data Ok");
        } else {
            JOptionPane.showMessageDialog(this, "Update Data Gagal");
        }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, ex.toString());
//        }

    }

    void prosesUpdateAkun(int pil) {
        pr.setKODEPERKIRAAN(lblKodeAkun.getText());
        pr.setNAMAPERKIRAAN(txtNamaSupplier.getText());
        pr.setGRUP(2);
        pr.setTIPE("SD");
        try {
            boolean stat;
            if (pil == 0) {
                stat = dbpr.insert(c, pr);
            } else {
                stat = dbpr.update(c, pr);
            }
            if (!stat) {
                JOptionPane.showMessageDialog(this, "Update Data Akun Ok");
            } else {
                JOptionPane.showMessageDialog(this, "Update Data Akun Gagal");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    private void settingtombol(boolean simp, boolean edit, boolean hapus) {
        btnInsert.setEnabled(simp);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(hapus);
    }

    void prosesUpdateLog() {
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
            lh.setTABEL("TSUPPLIER");
            lh.setNOREFF(txtkodeSupplier.getText());
            lh.setAKSI(aksilog);
            if (aksilog.equals("Insert")) {
                ketlog = "Insert Data Supplier " + txtkodeSupplier.getText();
            } else if (aksilog.equals("Update")) {
                ketlog = "Update Data Supplier " + txtkodeSupplier.getText();
            } else if (aksilog.equals("Delete")) {
                ketlog = "Delete Data Supplier " + txtkodeSupplier.getText();
            }
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    void cektombol() {
        if (JavarieSoftApp.groupuser.equals("Pembelian")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Penjualan")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
            settingtombol(true, true, false);
        } else if (JavarieSoftApp.groupuser.equals("KaGudang")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Operator")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Accounting")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Asisten Administrator")) {
            settingtombol(true, true, false);
        } else if (JavarieSoftApp.groupuser.equals("Master Data")) {
            settingtombol(true, false, false);
        }
    }
}
