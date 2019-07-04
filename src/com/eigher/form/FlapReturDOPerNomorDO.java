/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapPerFaktur.java
 *
 * Created on Jan 31, 2012, 12:07:35 PM
 */

package com.eigher.form;

import com.erv.db.koneksi;
import javariesoft.*;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FlapReturDOPerNomorDO extends javax.swing.JInternalFrame {
    Connection c;
    /** Creates new form FlapPerFaktur */
    public FlapReturDOPerNomorDO() {
        initComponents();
        try {
            c=koneksi.getKoneksiJ();
        } catch (SQLException ex) {
            Logger.getLogger(FlapReturDOPerNomorDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtnofaktur = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FlapReturDOPerNomorDO.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 42, 110, 15);

        txtnofaktur.setFont(resourceMap.getFont("txtnofaktur.font")); // NOI18N
        txtnofaktur.setText(resourceMap.getString("txtnofaktur.text")); // NOI18N
        txtnofaktur.setName("txtnofaktur"); // NOI18N
        getContentPane().add(txtnofaktur);
        txtnofaktur.setBounds(140, 40, 180, 21);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnPreview);
        btnPreview.setBounds(30, 90, 130, 40);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluar);
        btnKeluar.setBounds(170, 90, 140, 40);

        setBounds(0, 0, 366, 187);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
    parameter.put("Pkodereturdo", txtnofaktur.getText());
    try {
         //jasperPrint = JasperFillManager.fillReport("report\\LapFakturDistribusi.jasper", parameter, c);
        URL url = new URL(global.REPORT + "/ReturDO.jasper");
        InputStream in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c); 
        JasperViewer.viewReport(jasperPrint, false);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    } catch (Exception ex) {
        System.out.print(ex.toString());
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnPreviewActionPerformed

private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btnKeluarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtnofaktur;
    // End of variables declaration//GEN-END:variables

}
