/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.erv.controller.PoController;
import com.erv.db.PoDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.PO;
import com.erv.model.pelanggan;
import datechooser.beans.DateChooserCombo;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author USER
 */
public class FormPO extends javax.swing.JInternalFrame {

    /**
     * Creates new form FormPO
     */
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    PoController controller;
    int id;
    Connection con;
    PO po;

    public FormPO() {
        try {
            initComponents();
            con = koneksi.getKoneksiJ();
            controller = new PoController(this, con);
            settingPosisi();
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);
        } catch (SQLException ex) {
            Logger.getLogger(FormPO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FormPO(int id) {
        try {
            initComponents();
            this.id = id;
            con = koneksi.getKoneksiJ();
            controller = new PoController(this, con);
            po = PoDao.getPO(con, id);
            controller.viewData(con);
            settingPosisi();
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);
        } catch (SQLException ex) {
            Logger.getLogger(FormPO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JTable getTabelPO() {
        return tabelPO;
    }

    public DateChooserCombo getTgl() {
        return tgl;
    }

    public JTextField getTxtKodePO() {
        return txtKodePO;
    }

    public JButton getBtnAddDO() {
        return btnCreatePO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JTextField getTxtKodePelanggan() {
        return txtKodePelanggan;
    }

    public void setTxtKodePelanggan(JTextField txtKodePelanggan) {
        this.txtKodePelanggan = txtKodePelanggan;
    }

    public JTextField getTxtNamaPelanggan() {
        return txtNamaPelanggan;
    }

    public void setTxtNamaPelanggan(JTextField txtNamaPelanggan) {
        this.txtNamaPelanggan = txtNamaPelanggan;
    }

    public PO getPo() {
        return po;
    }

    public void setPo(PO po) {
        this.po = po;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tgl = new datechooser.beans.DateChooserCombo();
        txtKodePO = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        txtKodePelanggan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnCreatePO = new javax.swing.JButton();
        btnAddDO = new javax.swing.JButton();
        btnViewDO = new javax.swing.JButton();
        btnFakturKan = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPO = new javax.swing.JTable();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormPO.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

        jScrollPane2.setFont(resourceMap.getFont("jScrollPane2.font")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(130, 90, 20, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(12, 13, 113, 15);

        tgl.setFieldFont(resourceMap.getFont("tgl.dch_combo_fieldFont")); // NOI18N
        jPanel1.add(tgl);
        tgl.setBounds(130, 40, 130, 20);

        txtKodePO.setFont(resourceMap.getFont("txtKodePO.font")); // NOI18N
        txtKodePO.setText(resourceMap.getString("txtKodePO.text")); // NOI18N
        txtKodePO.setName("txtKodePO"); // NOI18N
        jPanel1.add(txtKodePO);
        txtKodePO.setBounds(130, 10, 130, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 40, 110, 15);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 70, 110, 20);

        txtNamaPelanggan.setFont(resourceMap.getFont("txtNamaPelanggan.font")); // NOI18N
        txtNamaPelanggan.setName("txtNamaPelanggan"); // NOI18N
        txtNamaPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPelangganActionPerformed(evt);
            }
        });
        txtNamaPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaPelangganKeyPressed(evt);
            }
        });
        jPanel1.add(txtNamaPelanggan);
        txtNamaPelanggan.setBounds(130, 70, 300, 22);

        txtKodePelanggan.setEditable(false);
        txtKodePelanggan.setFont(resourceMap.getFont("txtKodePelanggan.font")); // NOI18N
        txtKodePelanggan.setName("txtKodePelanggan"); // NOI18N
        txtKodePelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePelangganActionPerformed(evt);
            }
        });
        txtKodePelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePelangganKeyPressed(evt);
            }
        });
        jPanel1.add(txtKodePelanggan);
        txtKodePelanggan.setBounds(440, 70, 70, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 650, 100);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        btnCreatePO.setFont(resourceMap.getFont("btnCreatePO.font")); // NOI18N
        btnCreatePO.setText(resourceMap.getString("btnCreatePO.text")); // NOI18N
        btnCreatePO.setName("btnCreatePO"); // NOI18N
        btnCreatePO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreatePOActionPerformed(evt);
            }
        });
        jPanel2.add(btnCreatePO);

        btnAddDO.setFont(resourceMap.getFont("btnAddDO.font")); // NOI18N
        btnAddDO.setText(resourceMap.getString("btnAddDO.text")); // NOI18N
        btnAddDO.setName("btnAddDO"); // NOI18N
        btnAddDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDOActionPerformed(evt);
            }
        });
        jPanel2.add(btnAddDO);

        btnViewDO.setFont(resourceMap.getFont("btnViewDO.font")); // NOI18N
        btnViewDO.setText(resourceMap.getString("btnViewDO.text")); // NOI18N
        btnViewDO.setName("btnViewDO"); // NOI18N
        btnViewDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDOActionPerformed(evt);
            }
        });
        jPanel2.add(btnViewDO);

        btnFakturKan.setFont(resourceMap.getFont("btnFakturKan.font")); // NOI18N
        btnFakturKan.setText(resourceMap.getString("btnFakturKan.text")); // NOI18N
        btnFakturKan.setName("btnFakturKan"); // NOI18N
        btnFakturKan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFakturKanActionPerformed(evt);
            }
        });
        jPanel2.add(btnFakturKan);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 110, 650, 40);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel3.border.titleFont"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tabelPO.setFont(resourceMap.getFont("tabelPO.font")); // NOI18N
        tabelPO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "IDPO", "IDDO", "STATUS"
            }
        ));
        tabelPO.setName("tabelPO"); // NOI18N
        jScrollPane1.setViewportView(tabelPO);
        if (tabelPO.getColumnModel().getColumnCount() > 0) {
            tabelPO.getColumnModel().getColumn(0).setMinWidth(0);
            tabelPO.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelPO.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelPO.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("tabelPO.columnModel.title0")); // NOI18N
            tabelPO.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("tabelPO.columnModel.title1")); // NOI18N
            tabelPO.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("tabelPO.columnModel.title2")); // NOI18N
            tabelPO.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("tabelPO.columnModel.title3")); // NOI18N
        }

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 150, 650, 300);

        setBounds(0, 0, 666, 481);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreatePOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreatePOActionPerformed
        // TODO add your handling code here:
        controller.insert();
    }//GEN-LAST:event_btnCreatePOActionPerformed

    private void btnAddDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDOActionPerformed
        // TODO add your handling code here:
        if (po.getNofaktur().length() > 0) {
            JOptionPane.showMessageDialog(this, "PO ini sudah Difakturkan");
        } else {
            FormDOPO p = new FormDOPO(this);
            p.toFront();
            JavarieSoftView.panelCool1.add(p);
            p.setVisible(true);
        }
    }//GEN-LAST:event_btnAddDOActionPerformed

    private void btnViewDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDOActionPerformed
        // TODO add your handling code here:
        try {
            int iddo = Integer.parseInt(tabelPO.getValueAt(tabelPO.getSelectedRow(), 2).toString());
            FormDOPO p = new FormDOPO(this, iddo, id, "view");
            p.toFront();
            JavarieSoftView.panelCool1.add(p);
            p.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnViewDOActionPerformed

    private void txtNamaPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPelangganActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            jScrollPane2.setVisible(true);
            JDBCAdapter j = new JDBCAdapter(con);
            j.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where STATUSAKTIF='0' AND KODEPELANGGAN like '%" + txtNamaPelanggan.getText() + "%' or lower(NAMA) like '%" + txtNamaPelanggan.getText().toLowerCase() + "%'");
            jTable2.setModel(j);
            jScrollPane2.validate();
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtNamaPelangganActionPerformed

    private void txtNamaPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaPelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_txtNamaPelangganKeyPressed

    private void txtKodePelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePelangganActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            jScrollPane2.setVisible(true);
            JDBCAdapter j = new JDBCAdapter(con);
            j.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where STATUSAKTIF='0' AND KODEPELANGGAN like '" + txtKodePelanggan.getText() + "%' or lower(NAMA) like '%" + txtKodePelanggan.getText().toLowerCase() + "%'");
            jTable2.setModel(j);
            jScrollPane2.validate();
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FormDO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtKodePelangganActionPerformed

    private void txtKodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_txtKodePelangganKeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                pelanggan p = new pelangganDao(con).getDetails(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                txtKodePelanggan.setText(p.getKODEPELANGGAN());
                txtNamaPelanggan.setText(p.getNAMA());
                jScrollPane2.setVisible(false);
                txtNamaPelanggan.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void btnFakturKanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFakturKanActionPerformed
        // TODO add your handling code here:
        if (po.getNofaktur().length() > 0) {
            JOptionPane.showMessageDialog(this, "PO ini sudah Difakturkan");
        } else {
            DialogPenjualanInternal p = new DialogPenjualanInternal(id);
            p.toFront();
            JavarieSoftView.panelCool1.add(p);
            p.setVisible(true);
        }
    }//GEN-LAST:event_btnFakturKanActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            // TODO add your handling code here:
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormPO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDO;
    private javax.swing.JButton btnCreatePO;
    private javax.swing.JButton btnFakturKan;
    private javax.swing.JButton btnViewDO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tabelPO;
    private datechooser.beans.DateChooserCombo tgl;
    private javax.swing.JTextField txtKodePO;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtNamaPelanggan;
    // End of variables declaration//GEN-END:variables

    private void settingPosisi() {
        jScrollPane2.setVisible(false);
        jScrollPane2.setSize(450, 150);
    }
}
