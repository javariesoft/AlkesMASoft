/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormTutupStok.java
 *
 * Created on Jan 27, 2012, 11:31:06 AM
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;

/**
 *
 * @author erwadi
 */
public class FormTutupStok extends javax.swing.JInternalFrame {

    Connection c;
    Statement stat;
    com.erv.function.Util u = new com.erv.function.Util();
    loghistory lh;
    loghistoryDao lhdao;
    String aksilog = "";

    /**
     * Creates new form FormTutupStok
     */
    public FormTutupStok() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            stat = c.createStatement();
            cboBulan.setSelectedIndex(u.blnsekarang-1);
            txtTahun.setValue(u.thnsekarang);
            lh = new loghistory();
            lhdao = new loghistoryDao();
            if (JavarieSoftApp.groupuser.equals("Accounting")) {
                settingtombol(false, false, false);
            } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
                settingtombol(true, false, false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
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
        cboBulan = new javax.swing.JComboBox();
        btnTutup = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTahun = new javax.swing.JFormattedTextField();
        btnDelete = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        btnUpdate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKodebarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JFormattedTextField();
        txtHargamodal = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnInsert = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormTutupStok.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(260, 490, 110, 16);

        cboBulan.setFont(resourceMap.getFont("cboBulan.font")); // NOI18N
        cboBulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cboBulan.setName("cboBulan"); // NOI18N
        panelCool1.add(cboBulan);
        cboBulan.setBounds(160, 20, 220, 22);

        btnTutup.setFont(resourceMap.getFont("btnTutup.font")); // NOI18N
        btnTutup.setIcon(resourceMap.getIcon("btnTutup.icon")); // NOI18N
        btnTutup.setText(resourceMap.getString("btnTutup.text")); // NOI18N
        btnTutup.setName("btnTutup"); // NOI18N
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });
        panelCool1.add(btnTutup);
        btnTutup.setBounds(420, 130, 140, 30);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 170, 560, 310);

        txtTahun.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtTahun.setFont(resourceMap.getFont("txtTahun.font")); // NOI18N
        txtTahun.setName("txtTahun"); // NOI18N
        panelCool1.add(txtTahun);
        txtTahun.setBounds(390, 20, 70, 21);

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
        btnDelete.setBounds(260, 130, 130, 30);

        btnView.setFont(resourceMap.getFont("btnView.font")); // NOI18N
        btnView.setText(resourceMap.getString("btnView.text")); // NOI18N
        btnView.setName("btnView"); // NOI18N
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        panelCool1.add(btnView);
        btnView.setBounds(480, 20, 80, 23);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(290, 90, 80, 16);

        txtTotal.setEditable(false);
        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setFont(resourceMap.getFont("txtTotal.font")); // NOI18N
        txtTotal.setName("txtTotal"); // NOI18N
        panelCool1.add(txtTotal);
        txtTotal.setBounds(380, 490, 180, 21);

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
        btnUpdate.setBounds(140, 130, 110, 30);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        panelCool1.add(jLabel4);
        jLabel4.setBounds(20, 20, 110, 16);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        panelCool1.add(jLabel5);
        jLabel5.setBounds(20, 60, 110, 16);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        panelCool1.add(jLabel6);
        jLabel6.setBounds(20, 90, 110, 16);

        txtKodebarang.setFont(resourceMap.getFont("txtKodebarang.font")); // NOI18N
        txtKodebarang.setText(resourceMap.getString("txtKodebarang.text")); // NOI18N
        txtKodebarang.setName("txtKodebarang"); // NOI18N
        panelCool1.add(txtKodebarang);
        txtKodebarang.setBounds(160, 60, 80, 21);

        txtNamaBarang.setFont(resourceMap.getFont("txtKodebarang.font")); // NOI18N
        txtNamaBarang.setText(resourceMap.getString("txtNamaBarang.text")); // NOI18N
        txtNamaBarang.setName("txtNamaBarang"); // NOI18N
        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });
        panelCool1.add(txtNamaBarang);
        txtNamaBarang.setBounds(250, 60, 310, 21);

        txtJumlah.setText(resourceMap.getString("txtJumlah.text")); // NOI18N
        txtJumlah.setFont(resourceMap.getFont("txtJumlah.font")); // NOI18N
        txtJumlah.setName("txtJumlah"); // NOI18N
        panelCool1.add(txtJumlah);
        txtJumlah.setBounds(160, 90, 80, 21);

        txtHargamodal.setText(resourceMap.getString("txtHargamodal.text")); // NOI18N
        txtHargamodal.setFont(resourceMap.getFont("txtJumlah.font")); // NOI18N
        txtHargamodal.setName("txtHargamodal"); // NOI18N
        panelCool1.add(txtHargamodal);
        txtHargamodal.setBounds(379, 90, 180, 21);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelCool1.add(jSeparator1);
        jSeparator1.setBounds(10, 120, 570, 10);

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
        btnInsert.setBounds(20, 130, 110, 30);

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
        btnKeluar.setBounds(20, 490, 140, 30);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 595, 563);
    }// </editor-fold>//GEN-END:initComponents

private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
    try {
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(this, "Apakah Tutup Stok Akan di Proses..?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            String period = txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1);
            Statement stat12 = c.createStatement();
            ResultSet rs12 = stat.executeQuery("select * from STOKPERIODE where PERIODE='" + period + "'");
            if (rs12.next()) {
                JOptionPane.showMessageDialog(null, "Tutup Stok Untuk Periode Ini Sudah Dilakukan.. !");
                cboBulan.requestFocus();
            } else {
                aksilog = "InsertTutupStok";
                Statement stat1 = c.createStatement();
                String periode = txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1);
                ResultSet rs = stat1.executeQuery("select bs.KODEBARANG, b.NAMABARANG, bs.STOK, bs.COGS FROM BARANGSTOK bs inner join BARANG b on bs.KODEBARANG = b.KODEBARANG order by 1");
                while (rs.next()) {
                    this.stat.execute("insert into STOKPERIODE values('" + periode + "','" + rs.getString(1) + "'," + rs.getInt(3) + "," + rs.getDouble(4) + ")");

                }
                this.stat.execute("update KONTROLPERIODE set STATUSSTOK='0' where PERIODE='" + periode + "'");
                prosesUpdateLog();
                rs.close();
                stat1.close();
                reloadData();
            }
        } else {
            txtKodebarang.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnTutupActionPerformed

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan di Hapus..?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            aksilog = "DeleteStok";
            this.stat.execute("delete from STOKPERIODE where PERIODE='" + (txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1)) + "'");
            prosesUpdateLog();
            reloadData();
            if (JavarieSoftApp.groupuser.equals("Accounting")) {
                settingtombol(false, false, false);
            } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
                settingtombol(true, false, false);
            }
        } else {
            txtNamaBarang.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnDeleteActionPerformed

private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
// TODO add your handling code here:
    reloadData();
}//GEN-LAST:event_btnViewActionPerformed

private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
    try {
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Diedit?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodebarang.getText().equals("")) || (txtJumlah.getText().equals("")) || (txtHargamodal.getText().equals("")) || (txtTahun.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtNamaBarang.requestFocus();
            } else {
                aksilog = "UpdateStok";
                String periode = txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1);
                stat.execute("update STOKPERIODE set JUMLAH=" + txtJumlah.getValue() + ",COGS=" + txtHargamodal.getValue() + " where PERIODE='" + periode + "' and KODEBARANG='" + txtKodebarang.getText() + "'");
                prosesUpdateLog();
                reloadData();
                if (JavarieSoftApp.groupuser.equals("Accounting")) {
                    settingtombol(false, false, false);
                } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
                    settingtombol(true, false, false);
                }
            }
        } else {
            txtKodebarang.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_btnUpdateActionPerformed

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
// TODO add your handling code here:
    try {
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            if ((txtKodebarang.getText().equals("")) || (txtJumlah.getText().equals("")) || (txtHargamodal.getText().equals("")) || (txtTahun.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                txtNamaBarang.requestFocus();
            } else {
                aksilog = "InsertStok";
                String periode = txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1);
                stat.execute("insert into STOKPERIODE values('" + periode + "','" + txtKodebarang.getText() + "'," + txtJumlah.getValue() + "," + txtHargamodal.getValue() + ")");
                prosesUpdateLog();
                reloadData();
                if (JavarieSoftApp.groupuser.equals("Accounting")) {
                    settingtombol(false, false, false);
                } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
                    settingtombol(true, false, false);
                }
            }
        } else {
            txtNamaBarang.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnInsertActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
// TODO add your handling code here:
    try {
        // TODO add your handling code here:
        String periode[] = com.erv.function.Util.split(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(), ".");
        // s = saldoperiodeDao.getDetails(c, periode[0] + "." + periode[1], jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        txtKodebarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        txtNamaBarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
        txtJumlah.setValue(jTable1.getValueAt(jTable1.getSelectedRow(), 3));
        txtHargamodal.setValue(jTable1.getValueAt(jTable1.getSelectedRow(), 4));
        if (JavarieSoftApp.groupuser.equals("Accounting")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
            settingtombol(false, true, true);
        }
    } catch (Exception ex) {
        Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable1MouseClicked

private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
// TODO add your handling code here:
    reloadDataPerNama();
}//GEN-LAST:event_txtNamaBarangActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox cboBulan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JFormattedTextField txtHargamodal;
    private javax.swing.JFormattedTextField txtJumlah;
    private javax.swing.JTextField txtKodebarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JFormattedTextField txtTahun;
    private javax.swing.JFormattedTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    void reloadDataPerNama() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            String sql = "select s.PERIODE,s.KODEBARANG,b.NAMABARANG,s.JUMLAH,s.COGS  from STOKPERIODE s "
                    + "inner join BARANG b on (s.KODEBARANG=b.KODEBARANG) "
                    + "where s.KODEBARANG like '" + txtNamaBarang.getText() + "%' or lower(b.NAMABARANG) like '%" + txtNamaBarang.getText() + "%' order by s.KODEBARANG";
//            System.out.println(sql);
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
        } catch (SQLException ex) {
            Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadData() {

        JDBCAdapter j = new JDBCAdapter(c);
        String sql = "select s.PERIODE,s.KODEBARANG,b.NAMABARANG,s.JUMLAH,s.COGS  from STOKPERIODE s "
                + "inner join BARANG b on (s.KODEBARANG=b.KODEBARANG) "
                + "where s.PERIODE='" + (txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1)) + "' order by s.KODEBARANG";
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
        try {
            Statement st = c.createStatement();
            String sql1 = "select sum(cogs * jumlah) from STOKPERIODE where PERIODE='" + (txtTahun.getValue() + "." + (cboBulan.getSelectedIndex() + 1)) + "'";
            ResultSet rs = st.executeQuery(sql1);
            if (rs.next()) {
                txtTotal.setValue(rs.getDouble(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormTutupStok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void settingtombol(boolean simp, boolean edit, boolean hapus) {
        btnInsert.setEnabled(simp);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(hapus);
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
            lh.setTABEL("TSTOKPERIODE");
            lh.setNOREFF(txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1));
            if (aksilog.equals("InsertStok")) {
                ketlog = "Insert Data Tutup Stok " + txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1) + " Kode Barang " + txtKodebarang.getText() + " Jumlah " + txtJumlah.getText() + " Harga Modal " + txtHargamodal.getText();
            } else if (aksilog.equals("UpdateStok")) {
                ketlog = "Update Data Tutup Stok " + txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1) + " Kode Barang " + txtKodebarang.getText() + " Jumlah " + txtJumlah.getText() + " Harga Modal " + txtHargamodal.getText();
            } else if (aksilog.equals("DeleteStok")) {
                ketlog = "Delete Data Tutup Stok " + txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1) + " Kode Barang " + txtKodebarang.getText() + " Jumlah " + txtJumlah.getText() + " Harga Modal " + txtHargamodal.getText();
            } else if (aksilog.equals("InsertTutupStok")) {
                ketlog = "Insert Data Tutup Stok Periode " + txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1);
            }
            lh.setAKSI(aksilog);
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }
}
