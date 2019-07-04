/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapJualPerSales.java
 *
 * Created on Jan 29, 2012, 9:06:20 PM
 */
package javariesoft;

import com.erv.db.koneksi;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FRekapPenjualanHarian extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FlapJualPerSales
     */
    public FRekapPenjualanHarian() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            kosongForm();
        } catch (SQLException ex) {
            Logger.getLogger(FRekapPenjualanHarian.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel3 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        cbPembayaran = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        OptTanggal = new javax.swing.JCheckBox();
        OptOpsiFaktur = new javax.swing.JCheckBox();
        OptPembayaran = new javax.swing.JCheckBox();
        cbOpsiFaktur = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FRekapPenjualanHarian.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
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
        getContentPane().setLayout(null);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(280, 40, 30, 15);

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
        btnKeluar.setBounds(240, 140, 170, 40);

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
        btnPreview.setBounds(40, 140, 170, 40);

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
        tgl2.setBounds(310, 40, 140, 20);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 100, 80, 15);

        cbPembayaran.setFont(resourceMap.getFont("cbPembayaran.font")); // NOI18N
        cbPembayaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tunai", "Kredit", "Bank" }));
        cbPembayaran.setName("cbPembayaran"); // NOI18N
        getContentPane().add(cbPembayaran);
        cbPembayaran.setBounds(150, 100, 130, 20);

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
        OptTanggal.setBounds(150, 7, 81, 25);

        OptOpsiFaktur.setFont(resourceMap.getFont("OptOpsiFaktur.font")); // NOI18N
        OptOpsiFaktur.setText(resourceMap.getString("OptOpsiFaktur.text")); // NOI18N
        OptOpsiFaktur.setName("OptOpsiFaktur"); // NOI18N
        getContentPane().add(OptOpsiFaktur);
        OptOpsiFaktur.setBounds(240, 7, 100, 25);

        OptPembayaran.setFont(resourceMap.getFont("OptPembayaran.font")); // NOI18N
        OptPembayaran.setText(resourceMap.getString("OptPembayaran.text")); // NOI18N
        OptPembayaran.setName("OptPembayaran"); // NOI18N
        getContentPane().add(OptPembayaran);
        OptPembayaran.setBounds(350, 7, 110, 25);

        cbOpsiFaktur.setFont(resourceMap.getFont("cbOpsiFaktur.font")); // NOI18N
        cbOpsiFaktur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pajak", "Non Pajak" }));
        cbOpsiFaktur.setName("cbOpsiFaktur"); // NOI18N
        getContentPane().add(cbOpsiFaktur);
        cbOpsiFaktur.setBounds(150, 70, 130, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 70, 80, 20);

        setBounds(0, 0, 489, 229);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        String kode = "";
        try {
            if (OptTanggal.isSelected() && OptOpsiFaktur.isSelected() && OptPembayaran.isSelected()) {
                if (cbPembayaran.getSelectedIndex() == 0) {
                    kode = "0";
                } else if (cbPembayaran.getSelectedIndex() == 1) {
                    kode = "1";
                } else if (cbPembayaran.getSelectedIndex() == 2) {
                    kode = "2";
                }
                if (cbOpsiFaktur.getSelectedIndex() == 0) {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    parameter.put("pembayaran", kode);
                    URL url = new URL(global.REPORT + "/RekapJualHarianPTGLBayarPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                } else {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    parameter.put("pembayaran", kode);
                    URL url = new URL(global.REPORT + "/RekapJualHarianPTGLBayarNonPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                }
            } else if (OptTanggal.isSelected() && OptOpsiFaktur.isSelected()) {
                if (cbOpsiFaktur.getSelectedIndex() == 0) {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    URL url = new URL(global.REPORT + "/RekapJualHarianPTGLPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                } else {
                    parameter.put("Ptgl1", tgl1.getText());
                    parameter.put("Ptgl2", tgl2.getText());
                    URL url = new URL(global.REPORT + "/RekapJualHarianPTGLNonPajak.jasper");
                    InputStream in = url.openStream();
                    jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                    JasperViewer.viewReport(jasperPrint, false);
                }

            } else if (OptTanggal.isSelected() && OptPembayaran.isSelected()) {
                if (cbPembayaran.getSelectedIndex() == 0) {
                    kode = "0";
                } else if (cbPembayaran.getSelectedIndex() == 1) {
                    kode = "1";
                } else if (cbPembayaran.getSelectedIndex() == 2) {
                    kode = "2";
                }
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                parameter.put("pembayaran", kode);
                URL url = new URL(global.REPORT + "/RekapJualHarianPTGLBayar.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);

            } else if (OptTanggal.isSelected()) {
                parameter.put("Ptgl1", tgl1.getText());
                parameter.put("Ptgl2", tgl2.getText());
                URL url = new URL(global.REPORT + "/RekapJualHarianPTGL.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
            }
//            if (cbPembayaran.getSelectedItem().equals("-")) {
//                parameter.put("Ptgl1", dateChooserCombo1.getText());
//                parameter.put("Ptgl2", dateChooserCombo2.getText());
//                URL url = new URL(global.REPORT + "/RekapJualHarian.jasper");
//                InputStream in = url.openStream();
//                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
//                JasperViewer.viewReport(jasperPrint, false);
//                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            } else {
//
//                if (cbPembayaran.getSelectedIndex() == 1) {
//                    kode = "0";
//                } else if (cbPembayaran.getSelectedIndex() == 2) {
//                    kode = "1";
//                } else if (cbPembayaran.getSelectedIndex() == 3) {
//                    kode = "2";
//                }
//                parameter.put("Ptgl1", dateChooserCombo1.getText());
//                parameter.put("Ptgl2", dateChooserCombo2.getText());
//                parameter.put("pembayaran", kode);
//
//                URL url = new URL(global.REPORT + "/RekapJualHarianFilterBayar.jasper");
//                InputStream in = url.openStream();
//                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
//                JasperViewer.viewReport(jasperPrint, false);
//                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
        } catch (IOException | JRException ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        try {
            // TODO add your handling code here:
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FRekapPenjualanHarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox OptOpsiFaktur;
    private javax.swing.JCheckBox OptPembayaran;
    private javax.swing.JCheckBox OptTanggal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cbOpsiFaktur;
    private javax.swing.JComboBox cbPembayaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    // End of variables declaration//GEN-END:variables
void kosongForm() {
        OptTanggal.setSelected(true);
    }
}
