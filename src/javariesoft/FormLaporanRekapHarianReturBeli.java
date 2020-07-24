/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @author USER
 */
public class FormLaporanRekapHarianReturBeli extends javax.swing.JInternalFrame {

    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form FormLaporanRekapHarianReturBeli
     */
    public FormLaporanRekapHarianReturBeli() {
        initComponents();
        tgl1.setDateFormat(d);
        tgl2.setDateFormat(d);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        tgl2 = new datechooser.beans.DateChooserCombo();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormLaporanRekapHarianReturBeli.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnPreview);
        btnPreview.setBounds(20, 60, 170, 40);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluar);
        btnKeluar.setBounds(220, 60, 170, 40);
        getContentPane().add(tgl2);
        tgl2.setBounds(250, 20, 140, 20);
        getContentPane().add(tgl1);
        tgl1.setBounds(90, 20, 120, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 20, 60, 16);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(220, 20, 30, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        String kode = "";
        try {
            Connection c = koneksi.getKoneksiJ();
            parameter.put("Ptgl1", tgl1.getText());
            parameter.put("Ptgl2", tgl2.getText());
            //System.out.println(new URL(global.REPORT).getPath());
            //parameter.put("SUBREPORT_DIR", new URL(global.REPORT).getPath());
            parameter.put("retur", getRetur(tgl1.getText(), tgl2.getText()));
            URL url = new URL(global.REPORT + "/RekapBeliHarianPTGLRetur.jasper");
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
            c.close();
        } catch (IOException | JRException ex) {
            ex.printStackTrace();
            //System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FormLaporanRekapHarianRetur.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

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
            java.util.logging.Logger.getLogger(FormLaporanRekapHarianReturBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLaporanRekapHarianReturBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLaporanRekapHarianReturBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLaporanRekapHarianReturBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLaporanRekapHarianReturBeli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    // End of variables declaration//GEN-END:variables

    double getRetur(String tgl1, String tgl2){
        double hasil = 0;
        try {
            Connection con = koneksi.getKoneksiJ();
            String sql = "SELECT   "
                    + "     sum(RBR.\"JUMLAH\" * RBR.\"HARGA\" - RBR.\"DISKON\" + RBR.\"PPN\") as TOTAL   "
                    + "FROM   "
                    + "     RETURBELI RB INNER JOIN RETURBELIRINCI RBR ON RB.\"ID\" = RBR.IDRETURBELI  "
                    + "where RB.IDPEMBELIAN in  "
                    + "(select IDPEMBELIAN FROM PEMBELIAN beli where RB.IDPEMBELIAN = beli.id and beli.TANGGAL >= '"+tgl1+"' and beli.TANGGAL <= '"+tgl2+"')   "
                    + "";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()){
                hasil = rs.getDouble(1);
            }
            rs.close();
            stat.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormLaporanRekapHarianRetur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
}
