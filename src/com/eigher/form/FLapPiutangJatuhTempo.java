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

import javariesoft.*;
import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author eigher
 */
public class FLapPiutangJatuhTempo extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FlapJualPerSales
     */
    public FLapPiutangJatuhTempo() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
            kosongForm();
        } catch (SQLException ex) {
            Logger.getLogger(FLapPiutangJatuhTempo.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtnamaPelanggan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtkodePelanggan = new javax.swing.JTextField();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        pilihan = new javax.swing.JCheckBox();
        cboStatusPiutang = new javax.swing.JComboBox();
        OptAwal = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        OptTanggal = new javax.swing.JCheckBox();
        OptStatus = new javax.swing.JCheckBox();
        OptPelanggan = new javax.swing.JCheckBox();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapPiutangJatuhTempo.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(80, 130, 460, 110);

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
        btnKeluar.setBounds(370, 170, 150, 40);

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
        btnPreview.setBounds(50, 170, 140, 40);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 110, 110, 20);

        txtnamaPelanggan.setFont(resourceMap.getFont("txtkodePelanggan.font")); // NOI18N
        txtnamaPelanggan.setName("txtnamaPelanggan"); // NOI18N
        txtnamaPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaPelangganActionPerformed(evt);
            }
        });
        txtnamaPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamaPelangganKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamaPelanggan);
        txtnamaPelanggan.setBounds(150, 110, 310, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 80, 120, 20);

        txtkodePelanggan.setEditable(false);
        txtkodePelanggan.setFont(resourceMap.getFont("txtkodePelanggan.font")); // NOI18N
        txtkodePelanggan.setName("txtkodePelanggan"); // NOI18N
        getContentPane().add(txtkodePelanggan);
        txtkodePelanggan.setBounds(470, 110, 70, 21);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(150, 50, 130, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 50, 110, 20);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(280, 50, 30, 15);

        tgl2.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(310, 50, 130, 20);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        pilihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihanActionPerformed(evt);
            }
        });
        getContentPane().add(pilihan);
        pilihan.setBounds(170, 260, 140, 25);

        cboStatusPiutang.setFont(resourceMap.getFont("cboStatusPiutang.font")); // NOI18N
        cboStatusPiutang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lunas", "Belum Lunas" }));
        cboStatusPiutang.setName("cboStatusPiutang"); // NOI18N
        getContentPane().add(cboStatusPiutang);
        cboStatusPiutang.setBounds(150, 80, 140, 21);

        OptAwal.setText(resourceMap.getString("OptAwal.text")); // NOI18N
        OptAwal.setName("OptAwal"); // NOI18N
        OptAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptAwalActionPerformed(evt);
            }
        });
        getContentPane().add(OptAwal);
        OptAwal.setBounds(70, 260, 90, 23);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 10, 120, 16);

        OptTanggal.setFont(resourceMap.getFont("OptTanggal.font")); // NOI18N
        OptTanggal.setText(resourceMap.getString("OptTanggal.text")); // NOI18N
        OptTanggal.setEnabled(false);
        OptTanggal.setName("OptTanggal"); // NOI18N
        getContentPane().add(OptTanggal);
        OptTanggal.setBounds(150, 10, 81, 25);

        OptStatus.setFont(resourceMap.getFont("OptStatus.font")); // NOI18N
        OptStatus.setText(resourceMap.getString("OptStatus.text")); // NOI18N
        OptStatus.setName("OptStatus"); // NOI18N
        getContentPane().add(OptStatus);
        OptStatus.setBounds(240, 10, 81, 25);

        OptPelanggan.setFont(resourceMap.getFont("OptPelanggan.font")); // NOI18N
        OptPelanggan.setText(resourceMap.getString("OptPelanggan.text")); // NOI18N
        OptPelanggan.setName("OptPelanggan"); // NOI18N
        getContentPane().add(OptPelanggan);
        OptPelanggan.setBounds(330, 10, 100, 25);

        btnClear.setFont(resourceMap.getFont("btnClear.font")); // NOI18N
        btnClear.setIcon(resourceMap.getIcon("btnClear.icon")); // NOI18N
        btnClear.setText(resourceMap.getString("btnClear.text")); // NOI18N
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        getContentPane().add(btnClear);
        btnClear.setBounds(200, 170, 160, 40);

        jSeparator1.setName("jSeparator1"); // NOI18N
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(20, 40, 520, 10);

        setBounds(0, 0, 578, 273);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        String stat;
        
        try {
            Connection c = koneksi.getKoneksiJ();
            //jasperPrint = JasperFillManager.fillReport("report\\LapAnalisaPiutang.jasper", parameter, c);
            if (OptTanggal.isSelected() && OptStatus.isSelected() && OptPelanggan.isSelected()) {
                if (cboStatusPiutang.getSelectedIndex() == 0) {
                    stat = "0";
                } else {
                    stat = "1";
                }
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PStatus", stat);
                parameter.put("PPelanggan", txtkodePelanggan.getText());
                URL url = new URL(global.REPORT + "/LapPiutangJTTempoPTglStatusPelanggan.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptStatus.isSelected()) {
                if (cboStatusPiutang.getSelectedIndex() == 0) {
                    stat = "0";
                } else {
                    stat = "1";
                }
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PStatus", stat);
                URL url = new URL(global.REPORT + "/LapPiutangJTTempoPTglStatus.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptPelanggan.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PPelanggan", txtkodePelanggan.getText());
                URL url = new URL(global.REPORT + "/LapPiutangJTTempoPTglPelanggan.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                URL url = new URL(global.REPORT + "/LapPiutangJTTempoPTgl.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            }
            c.close();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtkodePelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        txtnamaPelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        jScrollPane1.setVisible(false);
        btnPreview.requestFocus();
    }
}//GEN-LAST:event_jTable1KeyPressed

private void txtnamaPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamaPelangganKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        jScrollPane1.setVisible(true);
        jTable1.requestFocus();
        jTable1.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane1.setVisible(false);
    }
}//GEN-LAST:event_txtnamaPelangganKeyPressed

private void txtnamaPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaPelangganActionPerformed
// TODO add your handling code here:
    jScrollPane1.setVisible(true);
    reloadData();
}//GEN-LAST:event_txtnamaPelangganActionPerformed

    private void pilihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihanActionPerformed
        // TODO add your handling code here:
//        if (pilihan.isSelected()) {
//            NonaktifText();
//        } else {
//            AktifText();
//        }
    }//GEN-LAST:event_pilihanActionPerformed

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

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        kosongForm();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox OptAwal;
    private javax.swing.JCheckBox OptPelanggan;
    private javax.swing.JCheckBox OptStatus;
    private javax.swing.JCheckBox OptTanggal;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cboStatusPiutang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox pilihan;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodePelanggan;
    private javax.swing.JTextField txtnamaPelanggan;
    // End of variables declaration//GEN-END:variables
void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select KODEPELANGGAN , NAMA, ALAMAT  from PELANGGAN where STATUSAKTIF='0' AND (KODEPELANGGAN like '" + txtnamaPelanggan.getText() + "%' or lower(NAMA) like '%" + txtnamaPelanggan.getText().toLowerCase() + "%')");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
        } catch (Exception ex) {
            Logger.getLogger(FRekapJualPerPelanggan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    void AktifText() {
//        txtkodePelanggan.setEnabled(true);
//        txtnamaPelanggan.setEnabled(true);
//        txtkodePelanggan.requestFocus();
//    }
//
//    void NonaktifText() {
//        txtkodePelanggan.setEnabled(false);
//        txtnamaPelanggan.setEnabled(false);
//    }
//    
    void kosongForm() {
        OptTanggal.setSelected(true);
        txtnamaPelanggan.setText("");
        txtkodePelanggan.setText("");

    }
}
