/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.SatuanDao;
import com.erv.db.koneksi;
import com.erv.db.salesDao;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.model.Satuan;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author TI-PNP
 */
public class FormSatuan extends javax.swing.JInternalFrame {

    /**
     * Creates new form FormSatuan
     */
    String aksilog = "";
    Connection c = null;

    public FormSatuan() {
        initComponents();
        kosongkan();
        
        try {
            c=koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit true");
            reloadData();
            cektombol();
        } catch (SQLException ex) {
            Logger.getLogger(FormSatuan.class.getName()).log(Level.SEVERE, null, ex);
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

        panelCool1 = new com.erv.function.PanelCool();
        jLabel2 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSatuan = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtKriteria = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormSatuan.class);
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

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(20, 16, 103, 16);

        txtKode.setFont(resourceMap.getFont("txtKode.font")); // NOI18N
        txtKode.setText(resourceMap.getString("txtKode.text")); // NOI18N
        txtKode.setName("txtKode"); // NOI18N
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKodeKeyTyped(evt);
            }
        });
        panelCool1.add(txtKode);
        txtKode.setBounds(161, 14, 261, 21);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(20, 43, 103, 16);

        txtSatuan.setFont(resourceMap.getFont("txtSatuan.font")); // NOI18N
        txtSatuan.setText(resourceMap.getString("txtSatuan.text")); // NOI18N
        txtSatuan.setName("txtSatuan"); // NOI18N
        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanKeyTyped(evt);
            }
        });
        panelCool1.add(txtSatuan);
        txtSatuan.setBounds(161, 41, 360, 21);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        panelCool1.add(btnInsert);
        btnInsert.setBounds(20, 80, 100, 25);

        btnUpdate.setFont(resourceMap.getFont("btnUpdate.font")); // NOI18N
        btnUpdate.setIcon(resourceMap.getIcon("btnUpdate.icon")); // NOI18N
        btnUpdate.setText(resourceMap.getString("btnUpdate.text")); // NOI18N
        btnUpdate.setName("btnUpdate"); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        panelCool1.add(btnUpdate);
        btnUpdate.setBounds(125, 80, 100, 25);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 150, 580, 376);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelCool1.add(btnDelete);
        btnDelete.setBounds(230, 80, 100, 25);

        btnCancel.setFont(resourceMap.getFont("btnCancel.font")); // NOI18N
        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        panelCool1.add(btnCancel);
        btnCancel.setBounds(380, 80, 100, 25);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        panelCool1.add(btnKeluar);
        btnKeluar.setBounds(490, 80, 100, 25);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        panelCool1.add(jLabel7);
        jLabel7.setBounds(50, 120, 150, 16);

        txtKriteria.setFont(resourceMap.getFont("txtKriteria.font")); // NOI18N
        txtKriteria.setName("txtKriteria"); // NOI18N
        txtKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKriteriaActionPerformed(evt);
            }
        });
        panelCool1.add(txtKriteria);
        txtKriteria.setBounds(190, 120, 220, 22);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setIcon(resourceMap.getIcon("btnFilter.icon")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        panelCool1.add(btnFilter);
        btnFilter.setBounds(430, 120, 100, 25);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelCool1.add(jSeparator1);
        jSeparator1.setBounds(20, 70, 570, 10);

        jSeparator2.setName("jSeparator2"); // NOI18N
        panelCool1.add(jSeparator2);
        jSeparator2.setBounds(20, 110, 570, 10);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 619, 564);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKode.getText().equals("")) || (txtSatuan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKode.requestFocus();
            } else {
                aksilog = "Insert";
                prosesUpdate(0);
                prosesUpdateLog();
                reloadData();
                kosongkan();
            }
        } else {
            txtKode.requestFocus();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Satuan s = SatuanDao.getDetails(c, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtKode.setText(s.getKODE());
            txtSatuan.setText(s.getSATUAN());
        } catch (SQLException ex) {
            Logger.getLogger(FormSales.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Diedit?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKode.getText().equals("")) || (txtSatuan.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtKode.requestFocus();
            } else {
                aksilog = "Update";
                prosesUpdate(1);
                prosesUpdateLog();
                reloadData();
                kosongkan();
                cektombol();
            }
        } else {
            txtKode.requestFocus();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            // TODO add your handling code here:
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                aksilog = "Delete";
                SatuanDao.deleteFromSATUAN(c, txtKode.getText());
                prosesUpdateLog();
                reloadData();
                kosongkan();
                cektombol();
                txtKode.requestFocus();
            } else {
                txtKode.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormSales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        kosongkan();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        if(c!=null){
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormSatuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtKodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyTyped
        // TODO add your handling code here:
        if(txtKode.getText().length()==13){
            JOptionPane.showMessageDialog(null, "Kode Satuan Hanya Boleh 13 Digit.. !");
            evt.consume();
            //txtKode.requestFocus();
        }
    }//GEN-LAST:event_txtKodeKeyTyped

    private void txtSatuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyTyped
        // TODO add your handling code here:
        if(txtSatuan.getText().length()>13){
            JOptionPane.showMessageDialog(null, "Satuan Hanya Boleh 13 Digit.. !");
            evt.consume();
            //txtKode.requestFocus();
        }
    }//GEN-LAST:event_txtSatuanKeyTyped

    private void txtKriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKriteriaActionPerformed
        // TODO add your handling code here:
//        try {
            // TODO add your handling code here:
//            Connection c = koneksi.getKoneksiJ();
            reloadData();
//            c.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(FormJenisBarang.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_txtKriteriaActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
//        try {
//            // TODO add your handling code here:
//            Connection c = koneksi.getKoneksiJ();
            reloadData();
//            c.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(FormJenisBarang.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtKriteria;
    private javax.swing.JTextField txtSatuan;
    // End of variables declaration//GEN-END:variables

    void prosesUpdate(int pil) {
        try {
            Satuan s = new Satuan();
            s.setKODE(txtKode.getText());
            s.setSATUAN(txtSatuan.getText());
            if (pil == 0) {
                SatuanDao.insertIntoSATUAN(c, s);
            } else {
                SatuanDao.updateSATUAN(c, s.getKODE(), s);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void prosesUpdateLog() {
        //java.sql.Date tanggallog;
        String tanggallog;
        Util u = new Util();
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        loghistory lh = new loghistory();
        loghistoryDao lhdao = new loghistoryDao();
        try {
            String ketlog = "";
            lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
            lh.setTANGGAL(tanggallog);
            lh.setJAM(jamlog);
            lh.setTABEL("TSATUAN");
            lh.setNOREFF(txtKode.getText());
            lh.setAKSI(aksilog);
            if (aksilog.equals("Insert")) {
                ketlog = "Insert Data Satuan " + txtKode.getText();
            } else if (aksilog.equals("Update")) {
                ketlog = "Update Data Satuan " + txtKode.getText();
            } else if (aksilog.equals("Delete")) {
                ketlog = "Delete Data Satuan " + txtKode.getText();
            }
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    private void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            String sql = "select * from SATUAN where lower(KODE) like '"
                    + txtKriteria.getText().toLowerCase() + "%' OR lower(SATUAN) like '%"
                    + txtKriteria.getText().toLowerCase() + "%' order by kode";
            j.executeQuery(sql);
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kosongkan() {
        txtKode.setText("");
        txtSatuan.setText("");
        txtKode.requestFocus();
    }
    
    private void settingtombol(boolean simp, boolean edit, boolean hapus) {
        btnInsert.setEnabled(simp);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(hapus);
    }
    
    void cektombol() {
        if (JavarieSoftApp.groupuser.equals("Pembelian")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Penjualan")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
            settingtombol(true, true, false);
        } else if (JavarieSoftApp.groupuser.equals("KaGudang")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Operator")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Accounting")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Asisten Administrator")) {
            settingtombol(true, true, false);
        } else if (JavarieSoftApp.groupuser.equals("Master Data")) {
            settingtombol(true, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Stock Control")) {
            settingtombol(false, false, false);
        }
        
    }
}
