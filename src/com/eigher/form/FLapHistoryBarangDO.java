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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class FLapHistoryBarangDO extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form LaporanPenjualanHarianForm
     */
    public FLapHistoryBarangDO() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
            kosongForm();
        } catch (Exception ex) {
            Logger.getLogger(FLapHistoryBarangDO.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtnamabarang = new javax.swing.JTextField();
        txtkodebarang = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        BtnClear = new javax.swing.JButton();
        OptAwal = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapHistoryBarangDO.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 20, 60, 15);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(210, 20, 140, 20);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(360, 20, 30, 16);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(390, 20, 140, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 50, 80, 15);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(40, 70, 510, 150);

        txtnamabarang.setFont(resourceMap.getFont("txtnamabarang.font")); // NOI18N
        txtnamabarang.setDisabledTextColor(resourceMap.getColor("txtnamabarang.disabledTextColor")); // NOI18N
        txtnamabarang.setName("txtnamabarang"); // NOI18N
        txtnamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamabarangActionPerformed(evt);
            }
        });
        txtnamabarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamabarangKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamabarang);
        txtnamabarang.setBounds(110, 50, 330, 22);

        txtkodebarang.setEditable(false);
        txtkodebarang.setFont(resourceMap.getFont("txtkodebarang.font")); // NOI18N
        txtkodebarang.setName("txtkodebarang"); // NOI18N
        txtkodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodebarangActionPerformed(evt);
            }
        });
        txtkodebarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodebarangKeyPressed(evt);
            }
        });
        getContentPane().add(txtkodebarang);
        txtkodebarang.setBounds(440, 50, 70, 22);

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
        btnPreview.setBounds(20, 160, 160, 40);

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
        btnKeluar.setBounds(360, 160, 160, 40);

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
        BtnClear.setBounds(190, 160, 160, 40);

        OptAwal.setText(resourceMap.getString("OptAwal.text")); // NOI18N
        OptAwal.setName("OptAwal"); // NOI18N
        OptAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptAwalActionPerformed(evt);
            }
        });
        getContentPane().add(OptAwal);
        OptAwal.setBounds(110, 20, 90, 23);

        setBounds(0, 0, 576, 252);
    }// </editor-fold>//GEN-END:initComponents

private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
// TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
    String kode;
    try {
        Connection c = koneksi.getKoneksiJ();
        parameter.put("tgl1", tgl1.getText());
        parameter.put("tgl2", tgl2.getText());
        parameter.put("PKodeBarang", txtkodebarang.getText());
        URL url = new URL(global.REPORT + "/LapHistoryBarangDO.jasper");
        InputStream in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        JasperViewer.viewReport(jasperPrint, false);
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

    private void txtnamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamabarangActionPerformed
        // TODO add your handling code here:
        jScrollPane1.setVisible(true);
        reloadData();
    }//GEN-LAST:event_txtnamabarangActionPerformed

    private void txtnamabarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamabarangKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane1.setVisible(true);
            jTable1.requestFocus();
            jTable1.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane1.setVisible(false);
        }
    }//GEN-LAST:event_txtnamabarangKeyPressed

    private void txtkodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodebarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodebarangActionPerformed

    private void txtkodebarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodebarangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodebarangKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtkodebarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtnamabarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
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
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodebarang;
    private javax.swing.JTextField txtnamabarang;
    // End of variables declaration//GEN-END:variables

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("select B.KODEBARANG,B.NAMABARANG,(select jenis from jenisbarang where JENISBARANG.ID=B.IDJENIS) as MERK,bsb.KODEBATCH,bsb.EXPIRE,"
                    + "CASEWHEN(KODEBATCH is null,bs.STOK,bsb.STOK) as STOK FROM BARANG B inner join BARANGSTOK bs on bs.KODEBARANG=B.KODEBARANG "
                    + "left join BARANGSTOKBATCH bsb on bs.ID=bsb.IDBARANGSTOK "
                    + "where B.KODEBARANG like '" + txtnamabarang.getText() + "%' or lower(B.NAMABARANG) like '%" + txtnamabarang.getText().toLowerCase() + "%'");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            TableColumn col = jTable1.getColumnModel().getColumn(0);
            col.setPreferredWidth(60);
            col = jTable1.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            col = jTable1.getColumnModel().getColumn(2);
            col.setPreferredWidth(150);
            col = jTable1.getColumnModel().getColumn(3);
            col.setPreferredWidth(80);
            col = jTable1.getColumnModel().getColumn(4);
            col.setPreferredWidth(80);
            col = jTable1.getColumnModel().getColumn(5);
            col.setPreferredWidth(50);
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
            j.close();
        } catch (SQLException ex) {
            Logger.getLogger(FLapHistoryBarangDO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void kosongForm() {
        txtnamabarang.setText("");
        txtkodebarang.setText("");
    }
}
