/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.KontrolTanggalDao;
import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.db.piutangDao;
import com.erv.db.piutangbayarDao;
import com.erv.exception.JavarieException;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.model.piutang;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javariesoft.DialogPiutang;
import javariesoft.Fungsi;
import javariesoft.JavarieSoftApp;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author USER
 */
public class PiutangController {

    DialogPiutang dialogPiutang;

    public PiutangController(DialogPiutang dialogPiutang) {
        this.dialogPiutang = dialogPiutang;
        clearForm();
    }

    public void clearForm() {
        dialogPiutang.getTxtNoRef().setText("");
    }

    public void onClickFilter() {
        Connection con = null;
        try {
            con = koneksi.getKoneksiJ();
            JDBCAdapter th = new JDBCAdapter(con);
            //String sql = "SELECT * FROM PIUTANGBAYAR where IDPIUTANG=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
            String sql = "SELECT * FROM PIUTANGBAYAR where ref='" + dialogPiutang.getTxtNoRef().getText() + "'";
            th.executeQuery(sql);
            dialogPiutang.getjTable6().setModel(th);
            dialogPiutang.getjTable6().setFont(new Font("Tahoma", Font.BOLD, 11));
            th.close();

        } catch (SQLException ex) {
            Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deletePK() {
        Connection con = null;
        try {
            con = koneksi.getKoneksiJ();
            Util util = new Util();
            String tglsekarang = Util.toDateStringSql(new Date());
            String per = util.thnsekarang + "." + util.blnsekarang;
            if (!Fungsi.cekperiodeAda(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Belum Dibuka.. !");
            }
            if (!Fungsi.cekperiode(con, per)) {
                throw new JavarieException("Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
            }
            if (!KontrolTanggalDao.cekHarian(con, tglsekarang)) {
                throw new JavarieException("Transaksi Tidak Bisa Dilakukan Karena :\n"
                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
            }
            con = koneksi.getKoneksiJ();
            con.createStatement().execute("set autocommit false");
            String ref = dialogPiutang.getTxtNoRef().getText();
            piutangbayarDao.deleteFromPIUTANGBAYAR(con, ref);
            jurnalDao.deleteJURNAL(con, ref);
            JTable tabel = dialogPiutang.getjTable6();
            for (int i = 0; i < tabel.getRowCount(); i++) {
                piutang p = piutangDao.getDetails(con, Integer.parseInt(tabel.getValueAt(i, 2).toString()));
                p.setSTATUS("1");
                piutangDao.updatePIUTANG(con, p);
            }
            loghistoryDao lhdao = new loghistoryDao();
            Util u = new Util();
            loghistory lh = new loghistory(0,
                    JavarieSoftApp.jenisuser,
                    u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang,
                    u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang,
                    "PIUTANGBAYAR",
                    ref,
                    "DELETE", "DELETE PIUTANG BAYAR REF " + ref);
            lhdao.insert(con, lh);
            con.commit();
            JOptionPane.showMessageDialog(dialogPiutang, "Delete Data Piutang Bayar Ok");
            onClickFilter();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavarieException ex) {
            JOptionPane.showMessageDialog(dialogPiutang, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.createStatement().execute("set autocommit true");
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PiutangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
