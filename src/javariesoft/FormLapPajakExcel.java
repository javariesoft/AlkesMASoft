/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.function.PrintfFormat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

/**
 *
 * @author USER
 */
public class FormLapPajakExcel extends javax.swing.JInternalFrame {

    /**
     * Creates new form FormLapPajakExcel
     */
    Connection con;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    public FormLapPajakExcel() {
        initComponents();
        try {
            con = koneksi.getKoneksiJ();
            tglAwal.setDateFormat(d);
            tglAkhir.setDateFormat(d); 
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
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

        jButton1 = new javax.swing.JButton();
        btnExportToCsv = new javax.swing.JButton();
        btnLawanCsv = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tglAkhir = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tglAwal = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        txtNofakturAwal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNoFakturAkhir = new javax.swing.JTextField();
        btnExportPM = new javax.swing.JButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormLapPajakExcel.class);
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

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(10, 180, 120, 23);

        btnExportToCsv.setText(resourceMap.getString("btnExportToCsv.text")); // NOI18N
        btnExportToCsv.setName("btnExportToCsv"); // NOI18N
        btnExportToCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportToCsvActionPerformed(evt);
            }
        });
        getContentPane().add(btnExportToCsv);
        btnExportToCsv.setBounds(140, 180, 130, 23);

        btnLawanCsv.setText(resourceMap.getString("btnLawanCsv.text")); // NOI18N
        btnLawanCsv.setName("btnLawanCsv"); // NOI18N
        btnLawanCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLawanCsvActionPerformed(evt);
            }
        });
        getContentPane().add(btnLawanCsv);
        btnLawanCsv.setBounds(10, 210, 120, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel1.border.titleFont"))); // NOI18N
        jPanel1.setFont(resourceMap.getFont("jPanel1.font")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        tglAkhir.setFieldFont(resourceMap.getFont("tglAkhir.dch_combo_fieldFont")); // NOI18N
        tglAkhir.setLocale(new java.util.Locale("in", "ID", ""));
        tglAkhir.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                tglAkhirOnSelectionChange(evt);
            }
        });
        tglAkhir.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglAkhirOnCommit(evt);
            }
        });
        jPanel1.add(tglAkhir);
        tglAkhir.setBounds(280, 20, 120, 20);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 20, 90, 14);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(250, 20, 15, 14);

        tglAwal.setFieldFont(resourceMap.getFont("tglAwal.dch_combo_fieldFont")); // NOI18N
        tglAwal.setLocale(new java.util.Locale("in", "ID", ""));
        tglAwal.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                tglAwalOnSelectionChange(evt);
            }
        });
        tglAwal.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglAwalOnCommit(evt);
            }
        });
        jPanel1.add(tglAwal);
        tglAwal.setBounds(120, 20, 120, 20);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 50, 110, 14);

        txtNofakturAwal.setText(resourceMap.getString("txtNofakturAwal.text")); // NOI18N
        txtNofakturAwal.setName("txtNofakturAwal"); // NOI18N
        jPanel1.add(txtNofakturAwal);
        txtNofakturAwal.setBounds(120, 50, 160, 20);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(300, 50, 15, 14);

        txtNoFakturAkhir.setText(resourceMap.getString("txtNoFakturAkhir.text")); // NOI18N
        txtNoFakturAkhir.setName("txtNoFakturAkhir"); // NOI18N
        jPanel1.add(txtNoFakturAkhir);
        txtNoFakturAkhir.setBounds(340, 50, 180, 20);

        btnExportPM.setText(resourceMap.getString("btnExportPM.text")); // NOI18N
        btnExportPM.setName("btnExportPM"); // NOI18N
        btnExportPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPMActionPerformed(evt);
            }
        });
        jPanel1.add(btnExportPM);
        btnExportPM.setBounds(120, 80, 160, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 540, 130);

        setBounds(0, 0, 834, 334);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:

            List<List<Object>> data = getDataImportBarang(con);
            Object[] judul = {"OB", "KODE_OBJECT", "NAMA", "HARGA_SATUAN"};
            ListToExcel(judul, data, "ImportBarang.xlsx");
            JOptionPane.showMessageDialog(this, "Export To Excel Ok");
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            // TODO add your handling code here:
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnExportToCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToCsvActionPerformed
        try {
            // TODO add your handling code here:
            QueryToCSV();
            JOptionPane.showMessageDialog(this, "Export Data Barang ke CSV Ok");
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnExportToCsvActionPerformed

    private void btnLawanCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLawanCsvActionPerformed
        try {
            // TODO add your handling code here:
            createLawanToCsv();
            JOptionPane.showMessageDialog(this, "Export Data Lawan ke CSV Ok");
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLawanCsvActionPerformed

    private void tglAkhirOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_tglAkhirOnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tglAkhirOnSelectionChange

    private void tglAkhirOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglAkhirOnCommit
        // TODO add your handling code here:
        //settingTgl();
    }//GEN-LAST:event_tglAkhirOnCommit

    private void tglAwalOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_tglAwalOnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tglAwalOnSelectionChange

    private void tglAwalOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglAwalOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_tglAwalOnCommit

    private void btnExportPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPMActionPerformed
        try {
            // TODO add your handling code here:
            exportPajakMasukanToCsv(tglAwal.getText(), tglAkhir.getText(), txtNofakturAwal.getText(), txtNoFakturAkhir.getText());
            JOptionPane.showMessageDialog(this, "Export Pajak Masukan Ok");
        } catch (SQLException ex) {
            Logger.getLogger(FormLapPajakExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExportPMActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportPM;
    private javax.swing.JButton btnExportToCsv;
    private javax.swing.JButton btnLawanCsv;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private datechooser.beans.DateChooserCombo tglAkhir;
    private datechooser.beans.DateChooserCombo tglAwal;
    private javax.swing.JTextField txtNoFakturAkhir;
    private javax.swing.JTextField txtNofakturAwal;
    // End of variables declaration//GEN-END:variables
    List<List<Object>> getDataImportBarang(Connection c) throws SQLException {
        String sql = "select 'OB' as OB, b.KODEBARANG as KODE_OBJECT, b.namabarang as NAMA, bs.cogs as HARGA_SATUAN "
                + "FROM BARANG b inner join BARANGSTOK bs on b.kodebarang=bs.kodebarang "
                + "";
        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        List<List<Object>> list = new ArrayList<>();
        while (rs.next()) {
            List<Object> newRow = new ArrayList<>();
            newRow.add(rs.getString(1));
            newRow.add(rs.getString(2));
            newRow.add(rs.getString(3));
            newRow.add(rs.getDouble(4));
            list.add(newRow);
        }
        return list;
    }

    public void ListToExcel(Object[] judul, List<List<Object>> data, String filename) throws FileNotFoundException, IOException {
        System.out.println("Start");
        String path = "import/";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;
        for (Object jd : judul) {
            Cell cell = row.createCell(colNum++);
            if (jd instanceof String) {
                cell.setCellValue((String) jd);
            }
        }
        for (List<Object> dataRow : data) {
            row = sheet.createRow(rowNum++);
            colNum = 0;
            for (Object field : dataRow) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }

        }
        FileOutputStream outputStream = new FileOutputStream(path + filename);
        workbook.write(outputStream);
        workbook.close();
        System.out.println("Done");
    }

    private void QueryToCSV() throws SQLException {
        String sql = "select 'OB' as OB, b.KODEBARANG as KODE_OBJECT, b.namabarang as NAMA, bs.cogs as HARGA_SATUAN "
                + "FROM BARANG b inner join BARANGSTOK bs on b.kodebarang=bs.kodebarang "
                + "";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        new Csv().write("import/barang.csv", rs, null);

    }

    private void createLawanToCsv() throws SQLException {
        SimpleResultSet rsHasil = new SimpleResultSet();
        rsHasil.addColumn("LT", Types.VARCHAR, 2, 0);
        rsHasil.addColumn("NPWP", Types.VARCHAR, 20, 0);
        rsHasil.addColumn("NAMA", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("JALAN", Types.VARCHAR, 200, 0);
        rsHasil.addColumn("BLOK", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("NOMOR", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("RT", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("RW", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("KECAMATAN", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("KELURAHAN", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("KABUPATEN", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("PROPINSI", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("KODE_POS", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("NOMOR_TELEPON", Types.VARCHAR, 10, 0);

        String sql = "(select 'LT' as LT, NPWP, NAMA, ALAMAT as JALAN, BLOK, "
                + "NOMOR, RT, RW, KECAMATAN, KELURAHAN, KABUPATEN, "
                + "PROPINSI, KODEPOS as KODE_POS, HP as NOMOR_TELEPON "
                + "from PELANGGAN) "
                + "";
        sql += "UNION ";
        sql += "(select 'LT' as LT, NPWP, NAMA, ALAMAT as JALAN, '-' as BLOK, "
                + "'-' as NOMOR,'-' as RT, '-' as RW, KECAMATAN, KELURAHAN, "
                + "KABUPATEN, PROPINSI, KODEPOS as KODE_POS, NOHP as NOMOR_TELEPON "
                + "from SUPPLIER) "
                + "";

        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        String npwpSebelum = "";
        while (rs.next()) {
            if (!rs.getString(2).trim().equals(npwpSebelum)) {
                Object[] rowData1 = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getString(12),
                    rs.getString(13),
                    rs.getString(14)
                };
                rsHasil.addRow(rowData1);
            }
            npwpSebelum = rs.getString(2).trim();
        }
        new Csv().write("import/lawan.csv", rsHasil, null);
    }

    private void exportPajakMasukanToCsv(String tglAwal, String tglAkhir, String noAwal, String noAkhir) throws SQLException {
        String sql="select 'FM' as FM, 1 as KD_JENIS_TRANSAKSI, "
                + "0 as FG_PENGGANTI, NOFAKTUR as NOMOR_FAKTUR, "
                + "month(TANGGAL) as MASA_PAJAK, year(TANGGAL) as TAHUN_PAJAK,  "
                + "TANGGAL as TANGGAL_FAKTUR, s.NPWP, s.NAMA, s.ALAMAT as ALAMAT_LENGKAP,  "
                + "sum(total - ppn) as JUMLAH_DPP, sum(ppn) as JUMLAH_PPN, "
                + "0 as JUMLAH_PPNBM , 1 as IS_CREDITABLE from PEMBELIAN p "
                + "inner join RINCIPEMBELIAN rp on p.id=IDPEMBELIAN "
                + "inner join SUPPLIER s on p.IDSUPPLIER = s.IDSUPPLIER "
                + "where TANGGAL>='"+tglAwal+"' and TANGGAL<='"+tglAkhir+"' "
                + "group by NOFAKTUR "
                + "order by p.tanggal, p.id ";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        SimpleResultSet rsHasil = new SimpleResultSet();
        rsHasil.addColumn("FM", Types.VARCHAR, 2, 0);
        rsHasil.addColumn("KD_JENIS_TRANSAKSI", Types.VARCHAR, 2, 0);
        rsHasil.addColumn("FG_PENGGANTI", Types.VARCHAR, 1, 0);
        rsHasil.addColumn("NOMOR_FAKTUR", Types.VARCHAR, 13, 0);
        rsHasil.addColumn("MASA_PAJAK", Types.VARCHAR, 5, 0);
        rsHasil.addColumn("TAHUN_PAJAK", Types.VARCHAR, 4, 0);
        rsHasil.addColumn("TANGGAL_FAKTUR", Types.VARCHAR, 10, 0);
        rsHasil.addColumn("NPWP", Types.VARCHAR, 20, 0);
        rsHasil.addColumn("NAMA", Types.VARCHAR, 50, 0);
        rsHasil.addColumn("ALAMAT_LENGKAP", Types.VARCHAR, 200, 0);
        rsHasil.addColumn("JUMLAH_DPP", Types.DOUBLE, 10, 2);
        rsHasil.addColumn("JUMLAH_PPN", Types.DOUBLE, 10, 2);
        rsHasil.addColumn("JUMLAH_PPNBM", Types.DOUBLE, 10, 0);
        rsHasil.addColumn("IS_CREDITABLE", Types.VARCHAR, 2, 0);
        long noFakAwal = Long.parseLong(noAwal);
        long noFakAkhir = Long.parseLong(noAkhir);
        while(rs.next()){
            Object[] rowData1 = {
                rs.getString("FM"),
                rs.getString("KD_JENIS_TRANSAKSI"),
                rs.getString("FG_PENGGANTI"),
                formatFaktur(13,noFakAwal+""),
                rs.getString("MASA_PAJAK"),
                rs.getString("TAHUN_PAJAK"),
                rs.getString("TANGGAL_FAKTUR"),
                rs.getString("NPWP"),
                rs.getString("NAMA"),
                rs.getString("ALAMAT_LENGKAP"),
                rs.getDouble("JUMLAH_DPP"),
                rs.getDouble("JUMLAH_PPN"),
                rs.getDouble("JUMLAH_PPNBM"),
                rs.getString("IS_CREDITABLE")
            };
            rsHasil.addRow(rowData1);
            noFakAwal++;
            if(noFakAwal==noFakAkhir){
                break;
            }
        }
        new Csv().write("import/pajakmasukan.csv", rsHasil, null);
        rs.close();
        rsHasil.close();
        stat.close();
    }

    private Object formatFaktur(int formatLength, String angka) {
        int s = formatLength-angka.length();
        String temp="";
        for(int i=0;i<s;i++){
            temp +="0";
        }
        temp += angka;
        return temp;
    }
}
