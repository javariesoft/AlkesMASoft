/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FPersediaanBrgDagang.java
 *
 * Created on Feb 12, 2012, 10:09:45 PM
 */
package javariesoft;

import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FLapBarangStok extends javax.swing.JInternalFrame {

    com.erv.function.Util u = new com.erv.function.Util();

    /**
     * Creates new form FPersediaanBrgDagang
     */
    public FLapBarangStok() {
        initComponents();
        txtNilaiStok.setText("0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cboIsiQuantity = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtNilaiStok = new javax.swing.JTextField();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapBarangStok.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jButton1.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(30, 70, 150, 40);

        jButton2.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(101, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(190, 70, 150, 40);

        cboIsiQuantity.setFont(resourceMap.getFont("cboIsiQuantity.font")); // NOI18N
        cboIsiQuantity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Besar Dari", "Sama Dengan", "Kecil Dari" }));
        cboIsiQuantity.setName("cboIsiQuantity"); // NOI18N
        cboIsiQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboIsiQuantityActionPerformed(evt);
            }
        });
        getContentPane().add(cboIsiQuantity);
        cboIsiQuantity.setBounds(100, 20, 120, 22);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 20, 70, 16);

        txtNilaiStok.setFont(resourceMap.getFont("txtNilaiStok.font")); // NOI18N
        txtNilaiStok.setText(resourceMap.getString("txtNilaiStok.text")); // NOI18N
        txtNilaiStok.setName("txtNilaiStok"); // NOI18N
        getContentPane().add(txtNilaiStok);
        txtNilaiStok.setBounds(230, 20, 90, 21);

        setBounds(0, 0, 375, 153);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        URL url = null;
        try {
            Connection c = koneksi.getKoneksiJ();
            if (cboIsiQuantity.getSelectedIndex() == 0) {
                parameter.put("Pnilaistok", txtNilaiStok.getText());
                url= new URL(global.REPORT + "/BarangStokBesar.jasper");
            }else if(cboIsiQuantity.getSelectedIndex() == 1){
                parameter.put("Pnilaistok", txtNilaiStok.getText());
                url= new URL(global.REPORT + "/BarangStokSama.jasper");
            }else if(cboIsiQuantity.getSelectedIndex() == 2){
                parameter.put("Pnilaistok", txtNilaiStok.getText());
                url= new URL(global.REPORT + "/BarangStokKecil.jasper");
            }
                        
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
            c.close();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboIsiQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboIsiQuantityActionPerformed
        // TODO add your handling code here:
//        if(cboIsiQuantity.getSelectedIndex()==1){
//            txtNilaiStok.setEnabled(false);
//        }else{
//            txtNilaiStok.setEnabled(true);
//        }
    }//GEN-LAST:event_cboIsiQuantityActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboIsiQuantity;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtNilaiStok;
    // End of variables declaration//GEN-END:variables

}
