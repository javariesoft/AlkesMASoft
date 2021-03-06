/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapJualPerSales.java
 *
 * Created on Jan 29, 2012, 9:06:20 PM
 */
package com.eigher.form;

import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javariesoft.FRekapJualPerPelanggan;
import javariesoft.global;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FRekapReturBeli extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FlapJualPerSales
     */
    public FRekapReturBeli() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            kosongForm();
        } catch (SQLException ex) {
            Logger.getLogger(FRekapReturBeli.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        OptTanggal = new javax.swing.JCheckBox();
        OptSupplier = new javax.swing.JCheckBox();
        OptOpsiFaktur = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtnamasupplier = new javax.swing.JTextField();
        txtkodesupplier = new javax.swing.JTextField();
        cbOpsiFaktur = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        BtnClear = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FRekapReturBeli.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setToolTipText(resourceMap.getString("Form.toolTipText")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(60, 90, 460, 130);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(274, 40, 30, 15);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluar);
        btnKeluar.setBounds(360, 150, 160, 40);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setIcon(resourceMap.getIcon("btnPreview.icon")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnPreview);
        btnPreview.setBounds(52, 150, 150, 40);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(150, 40, 120, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 40, 70, 15);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(304, 40, 120, 20);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 10, 140, 15);

        OptTanggal.setFont(resourceMap.getFont("OptTanggal.font")); // NOI18N
        OptTanggal.setText(resourceMap.getString("OptTanggal.text")); // NOI18N
        OptTanggal.setEnabled(false);
        OptTanggal.setName("OptTanggal"); // NOI18N
        getContentPane().add(OptTanggal);
        OptTanggal.setBounds(150, 7, 81, 23);

        OptSupplier.setFont(resourceMap.getFont("OptSupplier.font")); // NOI18N
        OptSupplier.setText(resourceMap.getString("OptSupplier.text")); // NOI18N
        OptSupplier.setName("OptSupplier"); // NOI18N
        getContentPane().add(OptSupplier);
        OptSupplier.setBounds(240, 7, 90, 23);

        OptOpsiFaktur.setFont(resourceMap.getFont("OptOpsiFaktur.font")); // NOI18N
        OptOpsiFaktur.setText(resourceMap.getString("OptOpsiFaktur.text")); // NOI18N
        OptOpsiFaktur.setName("OptOpsiFaktur"); // NOI18N
        getContentPane().add(OptOpsiFaktur);
        OptOpsiFaktur.setBounds(330, 7, 100, 23);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 70, 110, 20);

        txtnamasupplier.setFont(resourceMap.getFont("txtkodesupplier.font")); // NOI18N
        txtnamasupplier.setName("txtnamasupplier"); // NOI18N
        txtnamasupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamasupplierActionPerformed(evt);
            }
        });
        txtnamasupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamasupplierKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamasupplier);
        txtnamasupplier.setBounds(150, 70, 295, 21);

        txtkodesupplier.setEditable(false);
        txtkodesupplier.setFont(resourceMap.getFont("txtkodesupplier.font")); // NOI18N
        txtkodesupplier.setName("txtkodesupplier"); // NOI18N
        getContentPane().add(txtkodesupplier);
        txtkodesupplier.setBounds(450, 70, 70, 21);

        cbOpsiFaktur.setFont(resourceMap.getFont("cbOpsiFaktur.font")); // NOI18N
        cbOpsiFaktur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pajak", "Non Pajak" }));
        cbOpsiFaktur.setName("cbOpsiFaktur"); // NOI18N
        getContentPane().add(cbOpsiFaktur);
        cbOpsiFaktur.setBounds(150, 100, 120, 20);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 100, 80, 20);

        BtnClear.setFont(resourceMap.getFont("BtnClear.font")); // NOI18N
        BtnClear.setIcon(resourceMap.getIcon("BtnClear.icon")); // NOI18N
        BtnClear.setText(resourceMap.getString("BtnClear.text")); // NOI18N
        BtnClear.setName("BtnClear"); // NOI18N
        BtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClearActionPerformed(evt);
            }
        });
        getContentPane().add(BtnClear);
        BtnClear.setBounds(206, 150, 150, 40);

        setBounds(0, 0, 555, 260);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;

        try {
            Connection c = koneksi.getKoneksiJ();
            if (OptTanggal.isSelected() && OptOpsiFaktur.isSelected() && OptSupplier.isSelected()) {
                if (cbOpsiFaktur.getSelectedIndex() == 0) {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    parameter.put("PSupplier", txtkodesupplier.getText());
                    URL url = new URL(global.REPORT + "/RekapReturBeliPTGLSupplierPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                } else {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    parameter.put("PSupplier", txtkodesupplier.getText());
                    URL url = new URL(global.REPORT + "/RekapReturBeliPTGLSupplierNonPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                }
            } else if (OptTanggal.isSelected() && OptOpsiFaktur.isSelected()) {
                if (cbOpsiFaktur.getSelectedIndex() == 0) {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    URL url = new URL(global.REPORT + "/RekapReturBeliPTGLPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                } else {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    URL url = new URL(global.REPORT + "/RekapReturBeliPTGLNonPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                }

            } else if (OptTanggal.isSelected() && OptSupplier.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PSupplier", txtkodesupplier.getText());
                URL url = new URL(global.REPORT + "/RekapReturBeliPTGLSupplier.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);

            } else if (OptTanggal.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                URL url = new URL(global.REPORT + "/RekapReturBeliPTGL.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            }
            c.close();
        } catch (IOException | SQLException | JRException ex) {
//            System.out.print(ex.toString());
            Logger.getLogger(FRekapReturBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void txtnamasupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamasupplierActionPerformed
        // TODO add your handling code here:
        jScrollPane1.setVisible(true);
        reloadData();
    }//GEN-LAST:event_txtnamasupplierActionPerformed

    private void txtnamasupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamasupplierKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane1.setVisible(true);
            jTable1.requestFocus();
            jTable1.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane1.setVisible(false);
        }
    }//GEN-LAST:event_txtnamasupplierKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtkodesupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtnamasupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jScrollPane1.setVisible(false);
            btnPreview.requestFocus();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void BtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClearActionPerformed
        // TODO add your handling code here:
        kosongForm();
    }//GEN-LAST:event_BtnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnClear;
    private javax.swing.JCheckBox OptOpsiFaktur;
    private javax.swing.JCheckBox OptSupplier;
    private javax.swing.JCheckBox OptTanggal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cbOpsiFaktur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodesupplier;
    private javax.swing.JTextField txtnamasupplier;
    // End of variables declaration//GEN-END:variables

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select IDSUPPLIER , NAMA, ALAMAT  from SUPPLIER where STATUSAKTIF='0' AND (IDSUPPLIER like '" + txtnamasupplier.getText() + "%' or lower(NAMA) like '%" + txtnamasupplier.getText().toLowerCase() + "%')");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
        } catch (Exception ex) {
            Logger.getLogger(FRekapReturBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void kosongForm() {
        OptTanggal.setSelected(true);
        txtnamasupplier.setText("");
        txtkodesupplier.setText("");
        jScrollPane1.setVisible(false);
    }
}
