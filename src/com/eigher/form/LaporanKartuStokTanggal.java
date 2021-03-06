/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanKartuStok.java
 *
 * Created on Jan 12, 2012, 8:13:39 AM
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
public class LaporanKartuStokTanggal extends javax.swing.JInternalFrame {

    com.erv.function.Util u = new com.erv.function.Util();
    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form LaporanKartuStok
     */
    public LaporanKartuStokTanggal() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
        } catch (Exception ex) {
            Logger.getLogger(LaporanKartuStokTanggal.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        txtBarang = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnOk = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tgl1 = new datechooser.beans.DateChooserCombo();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        OptAwal = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(LaporanKartuStokTanggal.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 40, 150, 0);

        txtBarang.setName("txtBarang"); // NOI18N
        txtBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBarangActionPerformed(evt);
            }
        });
        txtBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBarangKeyPressed(evt);
            }
        });
        getContentPane().add(txtBarang);
        txtBarang.setBounds(180, 40, 470, 20);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 70, 670, 220);

        btnOk.setFont(resourceMap.getFont("btnOk.font")); // NOI18N
        btnOk.setIcon(resourceMap.getIcon("btnOk.icon")); // NOI18N
        btnOk.setText(resourceMap.getString("btnOk.text")); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        getContentPane().add(btnOk);
        btnOk.setBounds(20, 300, 130, 40);

        btnExit.setFont(resourceMap.getFont("btnExit.font")); // NOI18N
        btnExit.setIcon(resourceMap.getIcon("btnExit.icon")); // NOI18N
        btnExit.setText(resourceMap.getString("btnExit.text")); // NOI18N
        btnExit.setName("btnExit"); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit);
        btnExit.setBounds(160, 300, 130, 40);

        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 10, 80, 0);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 40, 150, 15);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(280, 10, 130, 20);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(440, 10, 130, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(410, 10, 30, 15);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 10, 110, 15);

        OptAwal.setText(resourceMap.getString("OptAwal.text")); // NOI18N
        OptAwal.setName("OptAwal"); // NOI18N
        OptAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptAwalActionPerformed(evt);
            }
        });
        getContentPane().add(OptAwal);
        OptAwal.setBounds(180, 10, 90, 23);

        setBounds(0, 0, 722, 385);
    }// </editor-fold>//GEN-END:initComponents

private void txtBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarangKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_txtBarangKeyPressed

private void txtBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBarangActionPerformed

    // TODO add your handling code here:
    JDBCAdapter j = new JDBCAdapter(c);
    String sql = "select B.KODEBARANG,B.NAMABARANG,(select jenis from jenisbarang where JENISBARANG.ID=B.IDJENIS) as MERK,bsb.KODEBATCH,bsb.EXPIRE,"
            + "case B.STATUS when 0 then 'Aktif' when 1 then 'Tidak Aktif' end as StatBrg,"
            + "CASEWHEN(KODEBATCH is null,bs.STOK,bsb.STOK) as STOK "
            + "FROM BARANG B inner join BARANGSTOK bs on bs.KODEBARANG=B.KODEBARANG "
            + "left join BARANGSTOKBATCH bsb on bs.ID=bsb.IDBARANGSTOK "
            + "where B.KODEBARANG like '" + txtBarang.getText() + "%' or lower(B.NAMABARANG) like '%" + txtBarang.getText().toLowerCase() + "%'";
    j.executeQuery(sql);
    jScrollPane2.getViewport().remove(jTable2);
    jTable2 = new JTable(j);
    TableColumn col = jTable2.getColumnModel().getColumn(0);
    col.setPreferredWidth(50);
    col = jTable2.getColumnModel().getColumn(1);
    col.setPreferredWidth(300);
    col = jTable2.getColumnModel().getColumn(2);
    col.setPreferredWidth(150);
    jScrollPane2.getViewport().add(jTable2);
    jScrollPane2.repaint();


}//GEN-LAST:event_txtBarangActionPerformed

private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
// TODO add your handling code here:
    cetakLaporan();
}//GEN-LAST:event_btnOkActionPerformed

private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    dispose();
}//GEN-LAST:event_btnExitActionPerformed

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
    private javax.swing.JCheckBox OptAwal;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtBarang;
    // End of variables declaration//GEN-END:variables

    void cetakLaporan() {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        String kodebarang = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
        parameter.put("Pkdbarang", kodebarang);
        parameter.put("tgl1", tgl1.getText());
        parameter.put("tgl2", tgl2.getText());
        URL url;
        try {
//          jasperPrint = JasperFillManager.fillReport("report\\LapStoryStok.jasper", parameter, koneksi.getKoneksiJ());
//          URL url = new URL(global.REPORT + "/LapStoryStokTanggal.jasper");
            boolean cek = com.erv.fungsi.Fungsi.cekTransaksiBarangAda(c, kodebarang, tgl1.getText(), tgl2.getText());
            if (cek) {
                url = new URL(global.REPORT + "/LapStoryStokTanggal.jasper");
            } else {
                url = new URL(global.REPORT + "/LapStoryStokTanggalKosong.jasper");
            }
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
