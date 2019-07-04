/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormLogin.java
 *
 * Created on Mar 13, 2012, 10:56:50 PM
 */
package com.eigher.form;

import javariesoft.*;
import com.erv.db.koneksi;
import com.erv.db.jurnalDao;
import com.erv.model.jurnal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.function.Util;
import java.sql.ResultSet;
import java.sql.Statement;
import javariesoft.JavarieSoftApp;

/**
 *
 * @author eigher
 */
public class FormHapusJual extends javax.swing.JInternalFrame {

    jurnal j;
    jurnalDao dbjurnal;
    Connection c;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    String des = "";

    /** Creates new form FormLogin */
    public FormHapusJual() {
        initComponents();
        try {
            kosong();
            c = koneksi.getKoneksiJ();
            j = new jurnal();
            dbjurnal = new jurnalDao();
            lh = new loghistory();
            lhdao = new loghistoryDao();
        } catch (SQLException ex) {
            Logger.getLogger(FormUser.class.getName()).log(Level.SEVERE, null, ex);
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
        panelCool1 = new com.erv.function.PanelCool();
        txtKodeJurnal = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormHapusJual.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        setClosable(true);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        txtKodeJurnal.setFont(resourceMap.getFont("txtKodeJurnal.font")); // NOI18N
        txtKodeJurnal.setText(resourceMap.getString("txtKodeJurnal.text")); // NOI18N
        txtKodeJurnal.setName("txtKodeJurnal"); // NOI18N
        txtKodeJurnal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeJurnalKeyPressed(evt);
            }
        });
        panelCool1.add(txtKodeJurnal);
        txtKodeJurnal.setBounds(140, 30, 180, 21);

        btnExit.setFont(resourceMap.getFont("btnExit.font")); // NOI18N
        btnExit.setText(resourceMap.getString("btnExit.text")); // NOI18N
        btnExit.setName("btnExit"); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        panelCool1.add(btnExit);
        btnExit.setBounds(250, 170, 130, 23);

        btnHapus.setFont(resourceMap.getFont("btnExit.font")); // NOI18N
        btnHapus.setText(resourceMap.getString("btnHapus.text")); // NOI18N
        btnHapus.setName("btnHapus"); // NOI18N
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelCool1.add(btnHapus);
        btnHapus.setBounds(60, 170, 130, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtKeterangan.setColumns(20);
        txtKeterangan.setFont(resourceMap.getFont("txtKeterangan.font")); // NOI18N
        txtKeterangan.setRows(5);
        txtKeterangan.setName("txtKeterangan"); // NOI18N
        jScrollPane1.setViewportView(txtKeterangan);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(140, 60, 280, 80);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        panelCool1.add(jLabel5);
        jLabel5.setBounds(40, 30, 90, 16);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        panelCool1.add(jLabel8);
        jLabel8.setBounds(40, 60, 90, 16);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 462, 252);
    }// </editor-fold>//GEN-END:initComponents

private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodeJurnal.getText().equals("")) || (txtKeterangan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong.. !");
                txtKodeJurnal.requestFocus();
            } else {
                if ((txtKodeJurnal.getText().substring(0, 1).equals("F")) || (txtKodeJurnal.getText().substring(0, 2).equals("AK")) || (txtKodeJurnal.getText().substring(0, 2).equals("AM")) || (txtKodeJurnal.getText().substring(0, 2).equals("CK")) || (txtKodeJurnal.getText().substring(0, 2).equals("CM"))) {
                    if (dbjurnal.CekJurnal(c, txtKodeJurnal.getText())) {
                        if (dbjurnal.CekJurnalHutangBayar(c, txtKodeJurnal.getText())) {
                            JOptionPane.showMessageDialog(null, "Kode Jurnal ini terhubung dengan pembayaran Hutang khusus.. !");
                            txtKodeJurnal.requestFocus();
                        } else {
                            if (dbjurnal.CekJurnalPiutangBayar(c, txtKodeJurnal.getText())) {
                                JOptionPane.showMessageDialog(null, "Kode Jurnal ini terhubung dengan pembayaran Piutang khusus.. !");
                                txtKodeJurnal.requestFocus();
                            } else {
                                j = dbjurnal.getDataJurnal(c, txtKodeJurnal.getText());
                                String tgal[] = Util.split(j.getTANGGAL().toString(), "-");
                                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                                if (cekperiode(per)) {
                                    aksilog = "DeleteJurnal";
                                    des = j.getDESKRIPSI();
                                    dbjurnal.deleteJURNAL(c, txtKodeJurnal.getText());
                                    prosesUpdateLog();
                                    kosong();
                                    JOptionPane.showMessageDialog(null, "Data Telah Dihapus.. !");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                    txtKodeJurnal.requestFocus();
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Kode Jurnal Ini Tidak Ada..!");
                        txtKodeJurnal.requestFocus();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Kode Jurnal Tidak Benar,, Jurnal yang boleh dihapus adalah jurnal yang dientri oleh accounting dari form jurnal.. !");
                    txtKodeJurnal.requestFocus();
                }
            }
        } else {
            txtKodeJurnal.requestFocus();
        }


    } catch (SQLException ex) {
        Logger.getLogger(FormHapusJual.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnHapusActionPerformed

private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btnExitActionPerformed

private void txtKodeJurnalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeJurnalKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtKeterangan.requestFocus();
    }
}//GEN-LAST:event_txtKodeJurnalKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextArea txtKeterangan;
    private javax.swing.JTextField txtKodeJurnal;
    // End of variables declaration//GEN-END:variables

    void kosong() {
        txtKodeJurnal.setText("");
        txtKeterangan.setText("");
        txtKodeJurnal.requestFocus();
    }

    void prosesUpdateLog() {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        try {

            String ketlog = "";
            lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
            lh.setTANGGAL(tanggallog);
            lh.setJAM(jamlog);
            lh.setTABEL("TJURNAL");
            lh.setNOREFF(txtKodeJurnal.getText());
            if (aksilog.equals("DeleteJurnal")) {
                ketlog = "Delete Data Jurnal " + txtKodeJurnal.getText() + " Deskripsi " + des;
            }
            lh.setAKSI(aksilog);
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }
    
    public boolean cekperiode(String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSBUKU='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }

        return hasil1;


    }
}
