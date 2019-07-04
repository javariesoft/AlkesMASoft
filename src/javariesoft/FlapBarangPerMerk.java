/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapBarangPerMerk.java
 *
 * Created on Jan 30, 2012, 8:22:44 PM
 */
package javariesoft;

import com.erv.db.koneksi;
import com.eigher.db.usertableDao;
import com.eigher.model.usertable;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FlapBarangPerMerk extends javax.swing.JInternalFrame {
    
    Connection c;
    usertable ut;
    usertableDao utdao;
    /**
     * Creates new form FlapBarangPerMerk
     */
    public FlapBarangPerMerk() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            ut = new usertable();
            utdao = new usertableDao();
        } catch (Exception ex) {
            Logger.getLogger(FlapBarangPerMerk.class.getName()).log(Level.SEVERE, null, ex);
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
        txtkodemerk = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtmerk = new javax.swing.JTextField();
        pilihan = new javax.swing.JCheckBox();
        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FlapBarangPerMerk.class);
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(120, 50, 270, 120);

        txtkodemerk.setEditable(false);
        txtkodemerk.setFont(resourceMap.getFont("txtkodemerk.font")); // NOI18N
        txtkodemerk.setText(resourceMap.getString("txtkodemerk.text")); // NOI18N
        txtkodemerk.setName("txtkodemerk"); // NOI18N
        getContentPane().add(txtkodemerk);
        txtkodemerk.setBounds(120, 60, 240, 21);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 60, 80, 15);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 30, 30, 15);

        txtmerk.setFont(resourceMap.getFont("txtmerk.font")); // NOI18N
        txtmerk.setText(resourceMap.getString("txtmerk.text")); // NOI18N
        txtmerk.setName("txtmerk"); // NOI18N
        txtmerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmerkActionPerformed(evt);
            }
        });
        txtmerk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmerkKeyPressed(evt);
            }
        });
        getContentPane().add(txtmerk);
        txtmerk.setBounds(120, 30, 240, 21);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        getContentPane().add(pilihan);
        pilihan.setBounds(120, 90, 130, 29);

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
        btnPreview.setBounds(40, 125, 150, 40);

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
        btnKeluar.setBounds(210, 125, 150, 40);

        setBounds(0, 0, 412, 211);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtkodemerk.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtmerk.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jScrollPane1.setVisible(false);
            btnPreview.requestFocus();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void txtmerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmerkActionPerformed
        // TODO add your handling code here:
        jScrollPane1.setVisible(true);
        reloadData();
    }//GEN-LAST:event_txtmerkActionPerformed

    private void txtmerkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmerkKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane1.setVisible(true);
            jTable1.requestFocus();
            jTable1.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane1.setVisible(false);
        }
    }//GEN-LAST:event_txtmerkKeyPressed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            HashMap parameter = new HashMap();
            JasperPrint jasperPrint = null;
            if (pilihan.isSelected()) {
                parameter.put("Pmerk", "%");
            } else {
                parameter.put("Pmerk", txtkodemerk.getText());
            }
            
//            ut = utdao.getDetails(c, JavarieSoftApp.jenisuser);
//            if(ut.getGROUPUSER().equals("Apoteker")){
//                URL url = new URL(global.REPORT + "/BarangNoHarga.jasper");
//                InputStream in = url.openStream();
//                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
//                JasperViewer.viewReport(jasperPrint, false);
//            }else{
                URL url = new URL(global.REPORT + "/Barang.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
                JasperViewer.viewReport(jasperPrint, false);
//            }
            
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //jProgressBar1.setValue(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        

    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        try {
            // TODO add your handling code here:
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlapBarangPerMerk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox pilihan;
    private javax.swing.JTextField txtkodemerk;
    private javax.swing.JTextField txtmerk;
    // End of variables declaration//GEN-END:variables

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select * from JENISBARANG where ID like '" + txtmerk.getText() + "%' or lower(JENIS) like '%" + txtmerk.getText().toLowerCase() + "%'");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FlapBarangPerMerk.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
