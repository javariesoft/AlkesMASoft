/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanPenjualanHarianForm.java
 *
 * Created on Dec 31, 2011, 3:45:06 PM
 */
package com.eigher.form;

import javariesoft.*;
import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class FLapRekapBarangDO extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form LaporanPenjualanHarianForm
     */
    public FLapRekapBarangDO() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
            kosongForm();
        } catch (Exception ex) {
            Logger.getLogger(FLapRekapBarangDO.class.getName()).log(Level.SEVERE, null, ex);
        }
            jScrollPane1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        OptPelanggan = new javax.swing.JCheckBox();
        OptTanggal = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtnamapelanggan = new javax.swing.JTextField();
        txtkodepelanggan = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        BtnClear = new javax.swing.JButton();
        OptAwal = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox();
        OptStatus = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapRekapBarangDO.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 40, 60, 15);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(210, 40, 120, 20);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(340, 40, 30, 16);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(370, 40, 140, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 10, 140, 15);

        jLabel4.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 100, 80, 15);

        OptPelanggan.setFont(resourceMap.getFont("jCheckBox2.font")); // NOI18N
        OptPelanggan.setText(resourceMap.getString("OptPelanggan.text")); // NOI18N
        OptPelanggan.setName("OptPelanggan"); // NOI18N
        getContentPane().add(OptPelanggan);
        OptPelanggan.setBounds(320, 7, 100, 23);

        OptTanggal.setFont(resourceMap.getFont("OptTanggal.font")); // NOI18N
        OptTanggal.setText(resourceMap.getString("OptTanggal.text")); // NOI18N
        OptTanggal.setEnabled(false);
        OptTanggal.setName("OptTanggal"); // NOI18N
        getContentPane().add(OptTanggal);
        OptTanggal.setBounds(150, 7, 81, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(80, 123, 460, 130);

        txtnamapelanggan.setFont(resourceMap.getFont("txtnamapelanggan.font")); // NOI18N
        txtnamapelanggan.setDisabledTextColor(resourceMap.getColor("txtnamapelanggan.disabledTextColor")); // NOI18N
        txtnamapelanggan.setName("txtnamapelanggan"); // NOI18N
        txtnamapelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapelangganActionPerformed(evt);
            }
        });
        txtnamapelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamapelangganKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamapelanggan);
        txtnamapelanggan.setBounds(110, 100, 330, 22);

        txtkodepelanggan.setEditable(false);
        txtkodepelanggan.setFont(resourceMap.getFont("txtkodepelanggan.font")); // NOI18N
        txtkodepelanggan.setName("txtkodepelanggan"); // NOI18N
        txtkodepelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodepelangganActionPerformed(evt);
            }
        });
        txtkodepelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodepelangganKeyPressed(evt);
            }
        });
        getContentPane().add(txtkodepelanggan);
        txtkodepelanggan.setBounds(440, 100, 70, 22);

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
        btnPreview.setBounds(20, 200, 160, 40);

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
        btnKeluar.setBounds(360, 200, 160, 40);

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
        BtnClear.setBounds(190, 200, 160, 40);

        OptAwal.setText(resourceMap.getString("OptAwal.text")); // NOI18N
        OptAwal.setName("OptAwal"); // NOI18N
        OptAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptAwalActionPerformed(evt);
            }
        });
        getContentPane().add(OptAwal);
        OptAwal.setBounds(110, 40, 90, 23);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 70, 80, 15);

        cbStatus.setFont(resourceMap.getFont("cbStatus.font")); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Delivery Order", "Close" }));
        cbStatus.setName("cbStatus"); // NOI18N
        getContentPane().add(cbStatus);
        cbStatus.setBounds(110, 70, 150, 20);

        OptStatus.setFont(resourceMap.getFont("OptStatus.font")); // NOI18N
        OptStatus.setText(resourceMap.getString("OptStatus.text")); // NOI18N
        OptStatus.setName("OptStatus"); // NOI18N
        getContentPane().add(OptStatus);
        OptStatus.setBounds(240, 7, 81, 23);

        setBounds(0, 0, 569, 289);
    }// </editor-fold>//GEN-END:initComponents

private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
// TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
    String kode;
    try {
        Connection c = koneksi.getKoneksiJ();
        if (OptTanggal.isSelected() && OptStatus.isSelected() && OptPelanggan.isSelected()) {
                if (cbStatus.getSelectedIndex() == 0) {
                    kode = "A";
                } else {
                    kode = "N";
                }
                parameter.put("tgl1", tgl1.getText());
                parameter.put("tgl2", tgl2.getText());
                parameter.put("PStatus", kode);
                parameter.put("PPelanggan", txtkodepelanggan.getText());
                URL url = new URL(global.REPORT + "/LapRekapBarangDOPTglStatusPelanggan.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptStatus.isSelected()) {
                if (cbStatus.getSelectedIndex() == 0) {
                    kode = "A";
                } else {
                    kode = "N";
                }
                parameter.put("tgl1", tgl1.getText());
                parameter.put("tgl2", tgl2.getText());
                parameter.put("PStatus", kode);
                URL url = new URL(global.REPORT + "/LapRekapBarangDOPTglStatus.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptPelanggan.isSelected()) {
                parameter.put("tgl1", tgl1.getText());
                parameter.put("tgl2", tgl2.getText());
                parameter.put("PPelanggan", txtkodepelanggan.getText());
                URL url = new URL(global.REPORT + "/LapRekapBarangDOPTglPelanggan.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);

            } else if (OptTanggal.isSelected()) {
                parameter.put("tgl1", tgl1.getText());
                parameter.put("tgl2", tgl2.getText());
                URL url = new URL(global.REPORT + "/LapRekapBarangDOPTgl.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            }
        c.close();
    } catch (Exception ex) {
        System.out.print(ex.toString());
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_btnPreviewActionPerformed

private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btnKeluarActionPerformed

    private void txtnamapelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapelangganActionPerformed
        // TODO add your handling code here:
        jScrollPane1.setVisible(true);
        reloadData();
    }//GEN-LAST:event_txtnamapelangganActionPerformed

    private void txtnamapelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane1.setVisible(true);
            jTable1.requestFocus();
            jTable1.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane1.setVisible(false);
        }
    }//GEN-LAST:event_txtnamapelangganKeyPressed

    private void txtkodepelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodepelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodepelangganActionPerformed

    private void txtkodepelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodepelangganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodepelangganKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtkodepelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtnamapelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jScrollPane1.setVisible(false);
            btnPreview.requestFocus();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void BtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClearActionPerformed
        // TODO add your handling code here:
        kosongForm();

    }//GEN-LAST:event_BtnClearActionPerformed

    private void OptAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptAwalActionPerformed
        // TODO add your handling code here:
        Calendar cld = Calendar.getInstance();
        try {
            cld.setTime(d.parse("2015-04-01"));
            tgl1.setSelectedDate(cld);
        } catch (ParseException ex) {
            Logger.getLogger(FLapRekapBarangDO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_OptAwalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnClear;
    private javax.swing.JCheckBox OptAwal;
    private javax.swing.JCheckBox OptPelanggan;
    private javax.swing.JCheckBox OptStatus;
    private javax.swing.JCheckBox OptTanggal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodepelanggan;
    private javax.swing.JTextField txtnamapelanggan;
    // End of variables declaration//GEN-END:variables

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select KODEPELANGGAN , NAMA, ALAMAT from PELANGGAN where STATUSAKTIF='0' AND (KODEPELANGGAN like '" + txtnamapelanggan.getText() + "%' or lower(NAMA) like '%" + txtnamapelanggan.getText().toLowerCase() + "%')");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FLapRekapBarangDO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void kosongForm() {
        OptTanggal.setSelected(true);
        txtnamapelanggan.setText("");
        txtkodepelanggan.setText("");

    }
}
