/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import com.erv.db.BarangstokDao;
import com.erv.db.DODao;
import com.erv.db.DORinciDao;
import com.erv.db.KontrolTanggalDao;
import com.erv.db.PoDao;
import com.erv.db.PoRinciDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.db.penjualanDao;
import com.erv.db.stokDao;
import com.erv.exception.JavarieException;
import com.erv.function.Util;
import com.erv.model.Barangstok;
import com.erv.model.DO;
import com.erv.model.DORinci;
import com.erv.model.PO;
import com.erv.model.PoRinci;
import com.erv.model.StokDO;
import com.erv.model.pelanggan;
import com.erv.model.penjualan;
import com.erv.model.rincipenjualan;
import com.erv.model.stok;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javariesoft.DialogPenjualanInternal;
import javariesoft.FormDOPO;
import javariesoft.FormPO;
import javariesoft.Fungsi;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class PoController {

    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    FormPO formPO;
    FormDOPO formDOPO;
    DialogPenjualanInternal dialogPenjualanInternal;
    int iddo;
    int idpo = 0;
    PO po;
    PoRinci poRinci;
    Connection con;
    penjualan p;

    public PoController(DialogPenjualanInternal view, Connection con, int idpo) {
        this.dialogPenjualanInternal = view;
        this.idpo = idpo;
        this.con = con;
        try {
            po = PoDao.getPO(con, idpo);
            p = new penjualan();
            List<rincipenjualan> rincijuals = new ArrayList<>();
            p.setFAKTUR(penjualanDao.setFakturPajak(con));
            p.setTANGGAL(po.getTanggal());
            p.setKODEPELANGGAN(po.getKodepelanggan());
            p.setCASH("0");
            p.setTGLLUNAS(po.getTanggal());
            p.setPPN(1);
            p.setDP(0);
            p.setDISKON(0);
            p.setSTATUS("1");
            p.setIDSALES("");
            p.setTAMBAHANTOTAL(0);
            p.setIDBANK(0);
            p.setPELANGGAN(po.getPelanggan().getNAMA());
            p.setJENISTRANS("Rutin");
            p.setSTATUSDO("0");
            p.setONGKOSKIRIM(0);
            p.setDISKONPERSEN(0);
            for (PoRinci porinci : po.getPoRincis()) {
                int iddo = porinci.getIddo();
                DO dis = DODao.getDetails(con, iddo);
                for (DORinci dORinci : dis.getdORincis()) {
                    Barangstok bs = BarangstokDao.getDetailKodeBarang(con, dORinci.getKODEBARANG());
                    rincipenjualan rp = new rincipenjualan();
                    rp.setIDPENJUALAN(0);
                    rp.setKODEBARANG(dORinci.getKODEBARANG());
                    rp.setJUMLAH(dORinci.getJUMLAH());
                    rp.setHARGA(dORinci.getHARGA());
                    rp.setSATUAN(dORinci.getSATUAN());
                    rp.setDISKON(0);
                    rp.setIDDO(iddo);
                    rp.setTOTAL(dORinci.getTOTAL());
                    rp.setPPN(dORinci.getPPN());
                    rp.setCOGS(bs.getCOGS());
                    rp.setKODEBATCH(dORinci.getKODEBATCH());
                    rp.setEXPIRE(dORinci.getEXPIRE());
                    rp.setDISKONP(0);
                    rp.setBONUS("");
                    rincijuals.add(rp);
                }
            }
            p.setRincipenjualans(rincijuals);
        } catch (SQLException ex) {
            Logger.getLogger(PoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PoController(FormPO formPO, Connection con) {
        this.formPO = formPO;
        this.formPO.getTgl().setDateFormat(d);
        this.con = con;
        clearForm();
    }

    public PoController(FormDOPO formDOPO, Connection con) {
        this.formDOPO = formDOPO;
        this.con = con;
    }

    public int getIddo() {
        return iddo;
    }

    public void setIddo(int iddo) {
        this.iddo = iddo;
    }

    public void clearForm() {
        formPO.getTxtKodePO().setText("");
    }

    public void insert() {
        try {
            String tgal[] = Util.split(formPO.getTgl().getText(), "-");
            String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
            if (!Fungsi.cekperiodeAda(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Belum Dibuka.. !");
            }
            if (!Fungsi.cekperiode(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
            }
            if (!KontrolTanggalDao.cekHarian(con, formPO.getTgl().getText())) {
                throw new JavarieException("Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            }
            po = new PO();
            po.setKodepo(formPO.getTxtKodePO().getText());
            po.setTanggal(formPO.getTgl().getText());
            po.setKodepelanggan(formPO.getTxtKodePelanggan().getText());
            po.setNofaktur(""); 
            idpo = PoDao.insertIntoPO(con, po.getKodepo(), po.getTanggal(), po.getKodepelanggan(), "");
            formPO.setId(idpo);
            po.setId(iddo);
            formPO.setPo(po);
            JOptionPane.showMessageDialog(formPO, "Entri PO Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(formPO, "Error :" + ex.getMessage());
        } catch (JavarieException ex) {
            JOptionPane.showMessageDialog(formPO, ex.getMessage());
        }
    }

    public void insertDO(Connection c) throws SQLException {
        poRinci = new PoRinci();
        poRinci.setIdpo(idpo);
        poRinci.setIddo(iddo);
        poRinci.setStatus(0);
        PoRinciDao.insertIntoPORinci(c, poRinci.getIdpo(), iddo, poRinci.getStatus());
        JOptionPane.showMessageDialog(formPO, "Entri PO Rinci Ok");
        viewData(c);
    }

    public void actionEvent(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == formPO.getBtnAddDO()) {

        }
    }

    public void viewData(Connection c) {
        try {
            po = PoDao.getPO(c, formPO.getId());
            idpo = po.getId();
            formPO.getTxtKodePO().setText(po.getKodepo());
            Calendar cld = Calendar.getInstance();
            cld.setTime(d.parse(po.getTanggal()));
            formPO.getTgl().setSelectedDate(cld);
            formPO.getTxtKodePelanggan().setText(po.getKodepelanggan());
            pelangganDao pdao = new pelangganDao(con);
            formPO.getTxtNamaPelanggan().setText(pdao.getDetails(po.getKodepelanggan()).getNAMA());
            DefaultTableModel model = (DefaultTableModel) formPO.getTabelPO().getModel();
            model.setRowCount(0);
            List<PoRinci> poRincis = po.getPoRincis();
            for (PoRinci p : poRincis) {
                Object[] data = {
                    p.getId(),
                    p.getIdpo(),
                    p.getIddo(),
                    p.getStatus()
                };
                model.addRow(data);
            }
        } catch (ParseException ex) {
            Logger.getLogger(PoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDOPO() {
        JTable model = formDOPO.getjTable1();
        DO dis = formDOPO.getDis();
        List<DORinci> dORincis = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            DORinci dORinci = new DORinci();
            dORinci.setIDDO(dis.getID());
            dORinci.setKODEBARANG(model.getValueAt(i, 1).toString());
            dORinci.setJUMLAH((int) model.getValueAt(i, 5));
            dORinci.setSATUAN(model.getValueAt(i, 6).toString());
            dORinci.setKODEBATCH(model.getValueAt(i, 3).toString());
            if (model.getValueAt(i, 4).toString().equals("")) {
                dORinci.setEXPIRE(null);
            } else {
                dORinci.setEXPIRE(model.getValueAt(i, 4).toString());
            }
            dORinci.setJUMLAHKECIL((int) model.getValueAt(i, 7));
            dORinci.setHARGA((double) model.getValueAt(i, 8));
            dORincis.add(dORinci);
        }
        String tgal[] = Util.split(dis.getTANGGAL(), "-");
        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
        try {
            if (!Fungsi.cekperiodeAda(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Belum Dibuka.. !");
            }
            if (!Fungsi.cekperiode(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
            }
            if (!KontrolTanggalDao.cekHarian(con, dis.getTANGGAL())) {
                throw new JavarieException("Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            }

            con.createStatement().execute("set autocommit false");
            DORinciDao.deleteFromDORinci(con, dis.getID());
            stokDao.deleteFromSTOK(con, dis.getID(), "D");
            for (DORinci dORinci : dORincis) {
                DORinciDao.insertIntoDORinci(con, dORinci);
                stok st = new stok();
                st.setIDPENJUALAN(dis.getID());
                st.setKODEBARANG(dORinci.getKODEBARANG());
                st.setTANGGAL(dis.getTANGGAL());
                st.setIN(0);
                st.setOUT(dORinci.getJUMLAHKECIL());
                st.setKODETRANS("D");
                st.setKODEBATCH(dORinci.getKODEBATCH());
                stokDao.insertIntoSTOK(con, st);
            }
            con.commit();
            JOptionPane.showMessageDialog(formDOPO, "Update Data PO Sukses");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(formDOPO, ex.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(formDOPO, ex1.getMessage());
            }
        } catch (JavarieException ex) {
            JOptionPane.showMessageDialog(formDOPO, ex.getMessage());
        } finally {
            try {
                con.createStatement().execute("set autocommit true");
            } catch (SQLException ex) {
                Logger.getLogger(PoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public penjualan getJual() {
        return p;
    }

    public int getIdpo() {
        return idpo;
    }

    public void setIdpo(int idpo) {
        this.idpo = idpo;
    }
}
