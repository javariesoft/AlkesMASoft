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
import com.erv.db.hutangDao;
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
public class FLapDetailHutang extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FlapJualPerSales
     */
    public FLapDetailHutang() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
            kosongForm();
        } catch (SQLException ex) {
            Logger.getLogger(FLapDetailHutang.class.getName()).log(Level.SEVERE, null, ex);
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
        txtnamasupplier = new javax.swing.JTextField();
        txtkodesupplier = new javax.swing.JTextField();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        pilihan = new javax.swing.JCheckBox();
        btnClear = new javax.swing.JButton();
        OptTanggal = new javax.swing.JCheckBox();
        LblFilter = new javax.swing.JLabel();
        OptStatus = new javax.swing.JCheckBox();
        OptSupplier = new javax.swing.JCheckBox();
        cboStatusHutang = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        OptAwal = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapDetailHutang.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
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
        jScrollPane1.setBounds(40, 130, 490, 120);

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
        btnKeluar.setBounds(350, 200, 150, 40);

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
        btnPreview.setBounds(20, 200, 150, 40);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 50, 110, 20);

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
        txtnamasupplier.setBounds(150, 110, 300, 21);

        txtkodesupplier.setEditable(false);
        txtkodesupplier.setFont(resourceMap.getFont("txtkodesupplier.font")); // NOI18N
        txtkodesupplier.setName("txtkodesupplier"); // NOI18N
        getContentPane().add(txtkodesupplier);
        txtkodesupplier.setBounds(460, 110, 70, 21);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(250, 50, 120, 20);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(370, 50, 30, 16);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(400, 50, 130, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 110, 110, 20);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        getContentPane().add(pilihan);
        pilihan.setBounds(140, 300, 140, 25);

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
        btnClear.setBounds(180, 200, 160, 40);

        OptTanggal.setFont(resourceMap.getFont("OptTanggal.font")); // NOI18N
        OptTanggal.setText(resourceMap.getString("OptTanggal.text")); // NOI18N
        OptTanggal.setEnabled(false);
        OptTanggal.setName("OptTanggal"); // NOI18N
        getContentPane().add(OptTanggal);
        OptTanggal.setBounds(150, 10, 81, 25);

        LblFilter.setFont(resourceMap.getFont("LblFilter.font")); // NOI18N
        LblFilter.setText(resourceMap.getString("LblFilter.text")); // NOI18N
        LblFilter.setName("LblFilter"); // NOI18N
        getContentPane().add(LblFilter);
        LblFilter.setBounds(20, 10, 120, 15);

        OptStatus.setFont(resourceMap.getFont("OptStatus.font")); // NOI18N
        OptStatus.setText(resourceMap.getString("OptStatus.text")); // NOI18N
        OptStatus.setName("OptStatus"); // NOI18N
        getContentPane().add(OptStatus);
        OptStatus.setBounds(240, 10, 81, 25);

        OptSupplier.setFont(resourceMap.getFont("OptSupplier.font")); // NOI18N
        OptSupplier.setText(resourceMap.getString("OptSupplier.text")); // NOI18N
        OptSupplier.setName("OptSupplier"); // NOI18N
        getContentPane().add(OptSupplier);
        OptSupplier.setBounds(330, 10, 100, 25);

        cboStatusHutang.setFont(resourceMap.getFont("cboStatusHutang.font")); // NOI18N
        cboStatusHutang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lunas", "Belum Lunas" }));
        cboStatusHutang.setName("cboStatusHutang"); // NOI18N
        getContentPane().add(cboStatusHutang);
        cboStatusHutang.setBounds(150, 80, 140, 22);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 80, 120, 20);

        OptAwal.setText(resourceMap.getString("OptAwal.text")); // NOI18N
        OptAwal.setName("OptAwal"); // NOI18N
        OptAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptAwalActionPerformed(evt);
            }
        });
        getContentPane().add(OptAwal);
        OptAwal.setBounds(150, 50, 90, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(20, 40, 510, 10);

        setBounds(0, 0, 570, 286);
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
            if (OptTanggal.isSelected() && OptStatus.isSelected() && OptSupplier.isSelected()) {
                if (cboStatusHutang.getSelectedIndex() == 0) {
                    stat = "0";
                } else {
                    stat = "1";
                }
                
                double tothutang = hutangDao.getJumlahHutangTglStatusSupplier(c, tgl1.getText(), tgl2.getText() , stat, txtkodesupplier.getText());
                
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PStatus", stat);
                parameter.put("Psupplier", txtkodesupplier.getText());
                parameter.put("TotHutang", tothutang);
                URL url = new URL(global.REPORT + "/LapAnalisaHutangDetailPTglStatusSupplier.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptStatus.isSelected()) {
                if (cboStatusHutang.getSelectedIndex() == 0) {
                    stat = "0";
                } else {
                    stat = "1";
                }
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("PStatus", stat);
                URL url = new URL(global.REPORT + "/LapAnalisaHutangPTglStatus.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected() && OptSupplier.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("Psupplier", txtkodesupplier.getText());
                URL url = new URL(global.REPORT + "/LapAnalisaHutangPTglSupplier.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            } else if (OptTanggal.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                URL url = new URL(global.REPORT + "/LapAnalisaHutangPTgl.jasper");
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
        txtkodesupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        txtnamasupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        jScrollPane1.setVisible(false);
        btnPreview.requestFocus();
    }
}//GEN-LAST:event_jTable1KeyPressed

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

private void txtnamasupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamasupplierActionPerformed
// TODO add your handling code here:
    jScrollPane1.setVisible(true);
    reloadData();
}//GEN-LAST:event_txtnamasupplierActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        kosongForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void OptAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptAwalActionPerformed
        // TODO add your handling code here:
        Calendar cld = Calendar.getInstance();
        try {
            cld.setTime(d.parse("2015-04-01"));
            tgl1.setSelectedDate(cld);
        } catch (ParseException ex) {
            Logger.getLogger(FLapDetailHutang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_OptAwalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblFilter;
    private javax.swing.JCheckBox OptAwal;
    private javax.swing.JCheckBox OptStatus;
    private javax.swing.JCheckBox OptSupplier;
    private javax.swing.JCheckBox OptTanggal;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cboStatusHutang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox pilihan;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodesupplier;
    private javax.swing.JTextField txtnamasupplier;
    // End of variables declaration//GEN-END:variables
void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select IDSUPPLIER , NAMA,ALAMAT  from SUPPLIER where STATUSAKTIF='0' AND (IDSUPPLIER like '" + txtnamasupplier.getText() + "%' or lower(NAMA) like '%" + txtnamasupplier.getText().toLowerCase() + "%')");
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

    void kosongForm() {
        OptTanggal.setSelected(true);
        OptTanggal.setVisible(false);
        OptStatus.setSelected(true);
        OptStatus.setVisible(false);
        OptSupplier.setSelected(true);
        OptSupplier.setVisible(false);
        LblFilter.setVisible(false);
        txtnamasupplier.setText("");
        txtkodesupplier.setText("");

    }
}
