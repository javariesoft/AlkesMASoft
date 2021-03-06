/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.jacob.com.Dispatch;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author USER
 */
public class FormJurnalKelompok extends javax.swing.JInternalFrame {

    /**
     * Creates new form FormJurnalKelompok
     */
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    String[] dataAkun;

    public FormJurnalKelompok() {
        initComponents();
        isiCboAkun();
        setting();
        setSize(dim.width, dim.height - 100);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboBulan = new javax.swing.JComboBox<>();
        txtTahun = new javax.swing.JTextField();
        cboAkun = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtKodeJurnal = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnFilter = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelRinciJurnal = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelJurnal = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        ftDebet = new javax.swing.JFormattedTextField();
        ftKredit = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormJurnalKelompok.class);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel1.add(jLabel1);

        cboBulan.setFont(resourceMap.getFont("cboBulan.font")); // NOI18N
        cboBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--->Pilih Bulan<---", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cboBulan.setSelectedIndex(1);
        cboBulan.setToolTipText(resourceMap.getString("cboBulan.toolTipText")); // NOI18N
        cboBulan.setName("cboBulan"); // NOI18N
        jPanel1.add(cboBulan);

        txtTahun.setText(resourceMap.getString("txtTahun.text")); // NOI18N
        txtTahun.setName("txtTahun"); // NOI18N
        txtTahun.setPreferredSize(new java.awt.Dimension(80, 20));
        jPanel1.add(txtTahun);

        cboAkun.setFont(resourceMap.getFont("cboAkun.font")); // NOI18N
        cboAkun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboAkun.setName("cboAkun"); // NOI18N
        jPanel1.add(cboAkun);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setName("jSeparator2"); // NOI18N
        jSeparator2.setPreferredSize(new java.awt.Dimension(5, 30));
        jPanel1.add(jSeparator2);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);

        txtKodeJurnal.setText(resourceMap.getString("txtKodeJurnal.text")); // NOI18N
        txtKodeJurnal.setName("txtKodeJurnal"); // NOI18N
        txtKodeJurnal.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel1.add(txtKodeJurnal);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setPreferredSize(new java.awt.Dimension(5, 30));
        jPanel1.add(jSeparator1);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jPanel1.add(btnFilter);

        btnEdit.setFont(resourceMap.getFont("btnEdit.font")); // NOI18N
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 300));

        tabelRinciJurnal.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("tabelRinciJurnal.border.title"))); // NOI18N
        tabelRinciJurnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelRinciJurnal.setName("tabelRinciJurnal"); // NOI18N
        jScrollPane1.setViewportView(tabelRinciJurnal);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getAccessibleContext().setAccessibleDescription(resourceMap.getString("jScrollPane1.AccessibleContext.accessibleDescription")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 250));

        tabelJurnal.setFont(resourceMap.getFont("tabelJurnal.font")); // NOI18N
        tabelJurnal.setName("tabelJurnal"); // NOI18N
        tabelJurnal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelJurnalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelJurnal);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(452, 300));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 80));
        jPanel5.setLayout(null);

        ftDebet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftDebet.setText(resourceMap.getString("ftDebet.text")); // NOI18N
        ftDebet.setFont(resourceMap.getFont("ftDebet.font")); // NOI18N
        ftDebet.setName("ftDebet"); // NOI18N
        jPanel5.add(ftDebet);
        ftDebet.setBounds(120, 10, 160, 21);

        ftKredit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftKredit.setText(resourceMap.getString("ftKredit.text")); // NOI18N
        ftKredit.setFont(resourceMap.getFont("ftKredit.font")); // NOI18N
        ftKredit.setName("ftKredit"); // NOI18N
        jPanel5.add(ftKredit);
        ftKredit.setBounds(120, 40, 160, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel5.add(jLabel2);
        jLabel2.setBounds(20, 10, 70, 15);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel5.add(jLabel3);
        jLabel3.setBounds(20, 40, 90, 15);

        jPanel4.add(jPanel5);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        try {
            Connection con = koneksi.getKoneksiJ();
            String kodeAkun = cboAkun.getSelectedItem().toString().split("-")[0];
            //String query = "select * from jurnal j inner join rincijurnal  where month(TANGGAL)="+cboBulan.getSelectedIndex()+" and year(tanggal)="+txtTahun.getText()+"";
            String query = "";
            if (txtKodeJurnal.getText().length() > 0) {
                query = "select * from jurnal where kodejurnal like '" + txtKodeJurnal.getText() + "%'";
            } else {
                query = "select j.* from jurnal j inner join RINCIJURNAL rj on j.id = rj.KODEJURNAL "
                        + "where month(TANGGAL)=" + cboBulan.getSelectedIndex() + " and year(tanggal)=" + txtTahun.getText() + " and KODEPERKIRAAN like '" + kodeAkun + "%' "
                        + "";
            }
            JDBCAdapter j = new JDBCAdapter(con);
            j.executeQuery(query);
            jScrollPane2.getViewport().remove(tabelJurnal);
            tabelJurnal = new JTable(j);
            tabelJurnal.setFont(new Font("Tahoma", Font.BOLD, 12));
            tabelJurnal.setRowHeight(20);
            TableColumn col = tabelJurnal.getColumnModel().getColumn(0);
            col.setPreferredWidth(10);
            col = tabelJurnal.getColumnModel().getColumn(1);
            col.setPreferredWidth(30);
            col = tabelJurnal.getColumnModel().getColumn(2);
            col.setPreferredWidth(30);
            col = tabelJurnal.getColumnModel().getColumn(3);
            col.setPreferredWidth(500);
            tabelJurnal.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tabelJurnalMouseClicked(evt);
                }
            });
            jScrollPane2.getViewport().add(tabelJurnal);
            jScrollPane2.repaint();
            j.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormJurnalKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnFilterActionPerformed

    private void tabelJurnalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelJurnalMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            try {
                Connection con = koneksi.getKoneksiJ();
                String kodejurnal = tabelJurnal.getValueAt(tabelJurnal.getSelectedRow(), 0).toString();
                String sql = "select * from rincijurnal where kodejurnal=" + kodejurnal + "";
                JDBCAdapter j = new JDBCAdapter(con);
                j.executeQuery(sql);
                jScrollPane1.getViewport().remove(tabelRinciJurnal);
                tabelRinciJurnal = new JTable(j);
                tabelRinciJurnal.setFont(new Font("Tahoma", Font.BOLD, 12));
                tabelRinciJurnal.setRowHeight(20);
                TableColumn col = tabelRinciJurnal.getColumnModel().getColumn(0);
                col.setPreferredWidth(10);
                col = tabelRinciJurnal.getColumnModel().getColumn(1);
                col.setPreferredWidth(30);
                col = tabelRinciJurnal.getColumnModel().getColumn(2);
                col.setPreferredWidth(30);
                col = tabelRinciJurnal.getColumnModel().getColumn(3);
                col.setPreferredWidth(500);
//                tabelRinciJurnal.addMouseListener(new java.awt.event.MouseAdapter() {
//                    @Override
//                    public void mouseClicked(java.awt.event.MouseEvent evt) {
//                        tabelJurnalMouseClicked(evt);
//                    }
//                });
                jScrollPane1.getViewport().add(tabelRinciJurnal);
                jScrollPane1.repaint();
                cetakDebetKredit(tabelRinciJurnal);
                j.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormJurnalKelompok.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tabelJurnalMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:

        String kodejurnal = tabelJurnal.getValueAt(tabelJurnal.getSelectedRow(), 1).toString();
        System.out.println(kodejurnal.substring(0,2));
        String kd = kodejurnal.substring(0, 2);
        if (kd.equals("KM")
                || kd.equals("KK")
                || kd.equals("BM")
                || kd.equals("BK")
                || kd.equals("JU")) {
            FormJurnal p = new FormJurnal(kodejurnal);
            p.toFront();
            p.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Jurnal ini tidak bisa di edit");
        }
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFilter;
    private javax.swing.JComboBox<String> cboAkun;
    private javax.swing.JComboBox<String> cboBulan;
    private javax.swing.JFormattedTextField ftDebet;
    private javax.swing.JFormattedTextField ftKredit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tabelJurnal;
    private javax.swing.JTable tabelRinciJurnal;
    private javax.swing.JTextField txtKodeJurnal;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables

    private void isiCboAkun() {
        try {
            String sql = "select * from VIEWSHPERKIRAAN";
            Connection con = koneksi.getKoneksiJ();
            ResultSet rs = con.createStatement().executeQuery(sql);
            cboAkun.removeAllItems();
            while (rs.next()) {
                cboAkun.addItem(rs.getString(1) + "-" + rs.getString(2));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormJurnalKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setting() {
        txtTahun.setText("" + new Util().thnsekarang);
        Dimension d = new Dimension(dim.width, (int) dim.height / 3 - 30);
        jScrollPane2.setPreferredSize(d);
        jScrollPane1.setPreferredSize(d);
        jPanel4.setPreferredSize(d);
    }

    private void cetakDebetKredit(JTable table) {
        int row = table.getRowCount();
        double debet = 0;
        double kredit = 0;
        for (int i = 0; i < row; i++) {
            debet += (Double) table.getValueAt(i, 2);
            kredit += (Double) table.getValueAt(i, 3);
        }
        ftDebet.setValue(debet);
        ftKredit.setValue(kredit);
    }
}
