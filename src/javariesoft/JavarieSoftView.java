/*
 * JavarieSoftView.java
 */
package javariesoft;

import com.eigher.form.FLapAnalisaPenjualanMerk;
import com.eigher.form.FLapDetailDO;
import com.eigher.form.FLapDetailReturDO;
import com.eigher.form.FLapHistoryBarangDO;
import com.eigher.form.FLapRekapPiutangPelanggan;
import com.eigher.form.FLapRekapBarangDO;
import com.eigher.form.FLapRekapHutangSupplier;
import com.eigher.form.FLapDetailHutang;
import com.eigher.form.FLapDetailPiutang;
import com.eigher.form.FLapRekapPenjualanPerMerk;
import com.eigher.form.FLapSisaDO;
import com.eigher.form.FlapReturDOPerNomorDO;
import com.eigher.form.LaporanKartuStokTanggal;
import com.erv.db.DatabaseBackup;
import com.erv.db.H2DatabaseBackup;
import com.erv.db.koneksi;
import com.erv.view.FLapAnalisaPenjualanMerk1;
import com.erv.view.FormLapPajakBeli;
import com.erv.view.FormLapPajakJual;
import com.erv.view.FormLapPajakReturBeli;
import com.erv.view.FormLapPajakReturJual;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The application's main frame.
 */
public class JavarieSoftView extends FrameView {
//public static String jenisuser="";

     //public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public JavarieSoftView(SingleFrameApplication app) {
        super(app);

        initComponents();
//        jLabel1.setSize(dim.width - 40, 80);
//        jLabel1.setLocation(dim.width / 2 - jLabel1.getWidth() / 2, jLabel1.getY() + jLabel1.getHeight() - 14);
//        jLabel2.setSize(dim.width - 40, 60);
//        jLabel2.setLocation(dim.width / 2 - jLabel2.getWidth() / 2, jLabel2.getY() + jLabel2.getHeight() + 8);
        jLabel1.setSize(dim.width - 40, 80);       
        jLabel1.setLocation(dim.width / 2 - jLabel1.getWidth() /2, jLabel1.getY() + jLabel1.getHeight() - 14);        
        jLabel2.setSize(dim.width - 40, 60);
        jLabel2.setLocation(dim.width / 2 - jLabel2.getWidth() / 2, jLabel2.getY() + jLabel2.getHeight() + 10);
        nonaktif();
        //aktif();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
        }
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        //this.getFrame().setUndecorated(true);
        this.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }

        });
    }

    private void formWindowClosing(WindowEvent evt) {
        try {
            backupDB();
        } catch (SQLException ex) {
            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = JavarieSoftApp.getApplication().getMainFrame();
            aboutBox = new JavarieSoftAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        JavarieSoftApp.getApplication().show(aboutBox);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelCool1 = new com.erv.model.DesktopCool();
        LblJenis = new javax.swing.JLabel();
        LblJenis1 = new javax.swing.JLabel();
        LblJenis2 = new javax.swing.JLabel();
        LblJenis3 = new javax.swing.JLabel();
        LblJenis4 = new javax.swing.JLabel();
        Lblgroup = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        Mlogin = new javax.swing.JMenu();
        Mloginuser = new javax.swing.JMenuItem();
        MlogOf = new javax.swing.JMenuItem();
        Mkonfigurasi = new javax.swing.JMenu();
        MBackupdatabase = new javax.swing.JMenuItem();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        MuserAccount = new javax.swing.JMenuItem();
        MeditPassword = new javax.swing.JMenuItem();
        MaktivaTetap = new javax.swing.JMenuItem();
        Mperkiraan = new javax.swing.JMenuItem();
        Mbarang = new javax.swing.JMenu();
        Msatuan = new javax.swing.JMenuItem();
        Mmerk = new javax.swing.JMenuItem();
        Mkategori = new javax.swing.JMenuItem();
        Mdatabarang = new javax.swing.JMenuItem();
        Mpelanggan = new javax.swing.JMenuItem();
        Msupplier = new javax.swing.JMenuItem();
        Msales = new javax.swing.JMenuItem();
        Mbank = new javax.swing.JMenuItem();
        Msetting = new javax.swing.JMenuItem();
        MsettingStatPeriode = new javax.swing.JMenuItem();
        MKontrolTanggal = new javax.swing.JMenuItem();
        MlogHistory = new javax.swing.JMenuItem();
        Manalisa = new javax.swing.JMenu();
        Manalisastokbrg = new javax.swing.JMenuItem();
        MSearchingBarang = new javax.swing.JMenuItem();
        MNomorPajak = new javax.swing.JMenuItem();
        Mtransaksi = new javax.swing.JMenu();
        Mjurnal = new javax.swing.JMenuItem();
        Mpenjualan = new javax.swing.JMenuItem();
        Mpembelian = new javax.swing.JMenuItem();
        Mhutang = new javax.swing.JMenuItem();
        Mgirokeluar = new javax.swing.JMenuItem();
        Mpiutang = new javax.swing.JMenuItem();
        Mgiromasuk = new javax.swing.JMenuItem();
        MkoreksiStok = new javax.swing.JMenuItem();
        Mmutasibarang = new javax.swing.JMenuItem();
        MRetur = new javax.swing.JMenuItem();
        MDeliveryOrder = new javax.swing.JMenuItem();
        Mtutupstok = new javax.swing.JMenuItem();
        Mtutupbuku = new javax.swing.JMenuItem();
        MTutupHarian = new javax.swing.JMenuItem();
        MPajak = new javax.swing.JMenuItem();
        Mlaporan = new javax.swing.JMenu();
        Mlappembelian = new javax.swing.JMenu();
        MLBPerFaktur = new javax.swing.JMenuItem();
        MLBDetailHarian = new javax.swing.JMenuItem();
        MLBRekapHarian = new javax.swing.JMenuItem();
        MLBPerSupplier = new javax.swing.JMenuItem();
        Mreturpembelian = new javax.swing.JMenu();
        MLRBelifaktur = new javax.swing.JMenuItem();
        MLRBRekapreturbeli = new javax.swing.JMenuItem();
        MlapPenjualan = new javax.swing.JMenu();
        MLJPerFaktur = new javax.swing.JMenuItem();
        MLJDetailHarian = new javax.swing.JMenuItem();
        MLJDetailPerPelanggan = new javax.swing.JMenuItem();
        MLJRekapHarian = new javax.swing.JMenuItem();
        MLJRekapPerSales = new javax.swing.JMenuItem();
        MLJRekapPerPelanggan = new javax.swing.JMenuItem();
        MLJPerMerkSparindo = new javax.swing.JMenuItem();
        MLJPerMerkKemenkes = new javax.swing.JMenuItem();
        MLJRekapPerMerk = new javax.swing.JMenuItem();
        Mreturpenjualan = new javax.swing.JMenu();
        MLRJualfaktur = new javax.swing.JMenuItem();
        MLRJRekapreturjual = new javax.swing.JMenuItem();
        MlapPengiriman = new javax.swing.JMenu();
        MLapPengirimanPerFaktur = new javax.swing.JMenuItem();
        MdetailPengiriman = new javax.swing.JMenuItem();
        MLapRekapPengiriman = new javax.swing.JMenuItem();
        MReturCabang = new javax.swing.JMenu();
        MReturCabangFaktur = new javax.swing.JMenuItem();
        MRekapReturCabang = new javax.swing.JMenuItem();
        Mlapdeliveryorder = new javax.swing.JMenu();
        MLDOFakturDO = new javax.swing.JMenuItem();
        MLDODetailDO = new javax.swing.JMenuItem();
        MLDORekapDO = new javax.swing.JMenuItem();
        MLDOStokDO = new javax.swing.JMenuItem();
        MLDORekapBarangDO = new javax.swing.JMenuItem();
        MLDOHistoryDO = new javax.swing.JMenuItem();
        MLapSisaDO = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        MLapReturDeliveryOrder = new javax.swing.JMenu();
        MLRDO_PerNomorDO = new javax.swing.JMenuItem();
        MLRDO_DetailReturDO = new javax.swing.JMenuItem();
        MlapBarang = new javax.swing.JMenu();
        MLBarangPerMerk = new javax.swing.JMenuItem();
        MLBarangPerKategori = new javax.swing.JMenuItem();
        MLBrgAkhirPeriode = new javax.swing.JMenuItem();
        MLB_KartuStokBulan = new javax.swing.JMenuItem();
        MLB_KartuStokTanggal = new javax.swing.JMenuItem();
        MLB_PersediaanBrgDagang = new javax.swing.JMenuItem();
        MLB_KatalogBarang = new javax.swing.JMenuItem();
        MLB_StokBarang = new javax.swing.JMenuItem();
        MLB_RekapBarangPerMerk = new javax.swing.JMenuItem();
        MlapHutang = new javax.swing.JMenu();
        MLH_DetailHutangPerSupplier = new javax.swing.JMenuItem();
        MLH_RekapHutangPerSupplier = new javax.swing.JMenuItem();
        MLH_RekapHutangALLSupplier = new javax.swing.JMenuItem();
        MlapPiutang = new javax.swing.JMenu();
        MLP_DetailPiutangPersales = new javax.swing.JMenuItem();
        MLP_DetailPiutangPerPelanggan = new javax.swing.JMenuItem();
        MLP_RekapPiutangPerPelanggan = new javax.swing.JMenuItem();
        MLP_RekapPiutangPelanggan = new javax.swing.JMenuItem();
        MLapPerkiraan = new javax.swing.JMenuItem();
        MlapFakturPajak = new javax.swing.JMenuItem();
        MlapTutupBuku = new javax.swing.JMenuItem();
        MlapJurnal = new javax.swing.JMenuItem();
        MLapBukuBesar = new javax.swing.JMenu();
        MLAKT_BukuBesarHarian = new javax.swing.JMenuItem();
        MLAKT_BukuBesarBulanan = new javax.swing.JMenuItem();
        MLAKT_BukuBesarBiayaTahunan = new javax.swing.JMenuItem();
        MlapNeraca = new javax.swing.JMenuItem();
        MlapRugiLaba = new javax.swing.JMenuItem();
        MlapEkuitas = new javax.swing.JMenuItem();
        MLapPajak = new javax.swing.JMenu();
        MLPajakPembelian = new javax.swing.JMenuItem();
        MLPajakReturPembelian = new javax.swing.JMenuItem();
        MLPajakPenjualan = new javax.swing.JMenuItem();
        MLPajakReturPenjualan = new javax.swing.JMenuItem();
        MLapAnalisis = new javax.swing.JMenu();
        MLAnalisisPenjualanMerk = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        Mkeluar = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCool1MouseClicked(evt);
            }
        });
        panelCool1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelCool1MouseMoved(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(JavarieSoftView.class);
        LblJenis.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis.setForeground(resourceMap.getColor("LblJenis.foreground")); // NOI18N
        LblJenis.setText(resourceMap.getString("LblJenis.text")); // NOI18N
        LblJenis.setName("LblJenis"); // NOI18N
        panelCool1.add(LblJenis);
        LblJenis.setBounds(120, 0, 200, 20);

        LblJenis1.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis1.setForeground(resourceMap.getColor("LblJenis1.foreground")); // NOI18N
        LblJenis1.setText(resourceMap.getString("LblJenis1.text")); // NOI18N
        LblJenis1.setName("LblJenis1"); // NOI18N
        panelCool1.add(LblJenis1);
        LblJenis1.setBounds(100, 0, 10, 20);

        LblJenis2.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis2.setForeground(resourceMap.getColor("LblJenis2.foreground")); // NOI18N
        LblJenis2.setText(resourceMap.getString("LblJenis2.text")); // NOI18N
        LblJenis2.setName("LblJenis2"); // NOI18N
        panelCool1.add(LblJenis2);
        LblJenis2.setBounds(10, 0, 80, 20);

        LblJenis3.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis3.setForeground(resourceMap.getColor("LblJenis3.foreground")); // NOI18N
        LblJenis3.setText(resourceMap.getString("LblJenis3.text")); // NOI18N
        LblJenis3.setName("LblJenis3"); // NOI18N
        panelCool1.add(LblJenis3);
        LblJenis3.setBounds(10, 20, 80, 20);

        LblJenis4.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis4.setForeground(resourceMap.getColor("LblJenis4.foreground")); // NOI18N
        LblJenis4.setText(resourceMap.getString("LblJenis4.text")); // NOI18N
        LblJenis4.setName("LblJenis4"); // NOI18N
        panelCool1.add(LblJenis4);
        LblJenis4.setBounds(100, 20, 10, 20);

        Lblgroup.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        Lblgroup.setForeground(resourceMap.getColor("Lblgroup.foreground")); // NOI18N
        Lblgroup.setText(resourceMap.getString("Lblgroup.text")); // NOI18N
        Lblgroup.setName("Lblgroup"); // NOI18N
        panelCool1.add(Lblgroup);
        Lblgroup.setBounds(120, 20, 200, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        panelCool1.add(jLabel1);
        jLabel1.setBounds(90, 20, 640, 50);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(260, 70, 460, 40);

        mainPanel.add(panelCool1, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        Mlogin.setIcon(resourceMap.getIcon("Mlogin.icon")); // NOI18N
        Mlogin.setText(resourceMap.getString("Mlogin.text")); // NOI18N
        Mlogin.setName("Mlogin"); // NOI18N

        Mloginuser.setIcon(resourceMap.getIcon("Mloginuser.icon")); // NOI18N
        Mloginuser.setText(resourceMap.getString("Mloginuser.text")); // NOI18N
        Mloginuser.setName("Mloginuser"); // NOI18N
        Mloginuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MloginuserActionPerformed(evt);
            }
        });
        Mlogin.add(Mloginuser);

        MlogOf.setIcon(resourceMap.getIcon("MlogOf.icon")); // NOI18N
        MlogOf.setText(resourceMap.getString("MlogOf.text")); // NOI18N
        MlogOf.setName("MlogOf"); // NOI18N
        MlogOf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlogOfActionPerformed(evt);
            }
        });
        Mlogin.add(MlogOf);

        menuBar.add(Mlogin);

        Mkonfigurasi.setIcon(resourceMap.getIcon("Mkonfigurasi.icon")); // NOI18N
        Mkonfigurasi.setText(resourceMap.getString("Mkonfigurasi.text")); // NOI18N
        Mkonfigurasi.setName("Mkonfigurasi"); // NOI18N

        MBackupdatabase.setIcon(resourceMap.getIcon("MBackupdatabase.icon")); // NOI18N
        MBackupdatabase.setText(resourceMap.getString("MBackupdatabase.text")); // NOI18N
        MBackupdatabase.setName("MBackupdatabase"); // NOI18N
        MBackupdatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MBackupdatabaseActionPerformed(evt);
            }
        });
        Mkonfigurasi.add(MBackupdatabase);

        menuBar.add(Mkonfigurasi);

        fileMenu.setIcon(resourceMap.getIcon("fileMenu.icon")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        MuserAccount.setIcon(resourceMap.getIcon("MuserAccount.icon")); // NOI18N
        MuserAccount.setText(resourceMap.getString("MuserAccount.text")); // NOI18N
        MuserAccount.setName("MuserAccount"); // NOI18N
        MuserAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MuserAccountActionPerformed(evt);
            }
        });
        fileMenu.add(MuserAccount);

        MeditPassword.setIcon(resourceMap.getIcon("MeditPassword.icon")); // NOI18N
        MeditPassword.setText(resourceMap.getString("MeditPassword.text")); // NOI18N
        MeditPassword.setName("MeditPassword"); // NOI18N
        MeditPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeditPasswordActionPerformed(evt);
            }
        });
        fileMenu.add(MeditPassword);

        MaktivaTetap.setIcon(resourceMap.getIcon("MaktivaTetap.icon")); // NOI18N
        MaktivaTetap.setText(resourceMap.getString("MaktivaTetap.text")); // NOI18N
        MaktivaTetap.setName("MaktivaTetap"); // NOI18N
        MaktivaTetap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaktivaTetapActionPerformed(evt);
            }
        });
        fileMenu.add(MaktivaTetap);

        Mperkiraan.setIcon(resourceMap.getIcon("Mperkiraan.icon")); // NOI18N
        Mperkiraan.setText(resourceMap.getString("Mperkiraan.text")); // NOI18N
        Mperkiraan.setName("Mperkiraan"); // NOI18N
        Mperkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MperkiraanActionPerformed(evt);
            }
        });
        fileMenu.add(Mperkiraan);

        Mbarang.setIcon(resourceMap.getIcon("Mbarang.icon")); // NOI18N
        Mbarang.setText(resourceMap.getString("Mbarang.text")); // NOI18N
        Mbarang.setName("Mbarang"); // NOI18N

        Msatuan.setIcon(resourceMap.getIcon("Msatuan.icon")); // NOI18N
        Msatuan.setText(resourceMap.getString("Msatuan.text")); // NOI18N
        Msatuan.setName("Msatuan"); // NOI18N
        Msatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsatuanActionPerformed(evt);
            }
        });
        Mbarang.add(Msatuan);

        Mmerk.setIcon(resourceMap.getIcon("Mmerk.icon")); // NOI18N
        Mmerk.setText(resourceMap.getString("Mmerk.text")); // NOI18N
        Mmerk.setName("Mmerk"); // NOI18N
        Mmerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MmerkActionPerformed(evt);
            }
        });
        Mbarang.add(Mmerk);

        Mkategori.setIcon(resourceMap.getIcon("Mkategori.icon")); // NOI18N
        Mkategori.setText(resourceMap.getString("Mkategori.text")); // NOI18N
        Mkategori.setName("Mkategori"); // NOI18N
        Mkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MkategoriActionPerformed(evt);
            }
        });
        Mbarang.add(Mkategori);

        Mdatabarang.setIcon(resourceMap.getIcon("Mdatabarang.icon")); // NOI18N
        Mdatabarang.setText(resourceMap.getString("Mdatabarang.text")); // NOI18N
        Mdatabarang.setName("Mdatabarang"); // NOI18N
        Mdatabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MdatabarangActionPerformed(evt);
            }
        });
        Mbarang.add(Mdatabarang);

        fileMenu.add(Mbarang);

        Mpelanggan.setIcon(resourceMap.getIcon("Mpelanggan.icon")); // NOI18N
        Mpelanggan.setText(resourceMap.getString("Mpelanggan.text")); // NOI18N
        Mpelanggan.setName("Mpelanggan"); // NOI18N
        Mpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpelangganActionPerformed(evt);
            }
        });
        fileMenu.add(Mpelanggan);

        Msupplier.setIcon(resourceMap.getIcon("Msupplier.icon")); // NOI18N
        Msupplier.setText(resourceMap.getString("Msupplier.text")); // NOI18N
        Msupplier.setName("Msupplier"); // NOI18N
        Msupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsupplierActionPerformed(evt);
            }
        });
        fileMenu.add(Msupplier);

        Msales.setIcon(resourceMap.getIcon("Msales.icon")); // NOI18N
        Msales.setText(resourceMap.getString("Msales.text")); // NOI18N
        Msales.setName("Msales"); // NOI18N
        Msales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsalesActionPerformed(evt);
            }
        });
        fileMenu.add(Msales);

        Mbank.setIcon(resourceMap.getIcon("Mbank.icon")); // NOI18N
        Mbank.setText(resourceMap.getString("Mbank.text")); // NOI18N
        Mbank.setName("Mbank"); // NOI18N
        Mbank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MbankActionPerformed(evt);
            }
        });
        fileMenu.add(Mbank);

        Msetting.setIcon(resourceMap.getIcon("Msetting.icon")); // NOI18N
        Msetting.setText(resourceMap.getString("Msetting.text")); // NOI18N
        Msetting.setName("Msetting"); // NOI18N
        Msetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsettingActionPerformed(evt);
            }
        });
        fileMenu.add(Msetting);

        MsettingStatPeriode.setIcon(resourceMap.getIcon("MsettingStatPeriode.icon")); // NOI18N
        MsettingStatPeriode.setText(resourceMap.getString("MsettingStatPeriode.text")); // NOI18N
        MsettingStatPeriode.setName("MsettingStatPeriode"); // NOI18N
        MsettingStatPeriode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsettingStatPeriodeActionPerformed(evt);
            }
        });
        fileMenu.add(MsettingStatPeriode);

        MKontrolTanggal.setIcon(resourceMap.getIcon("MKontrolTanggal.icon")); // NOI18N
        MKontrolTanggal.setText(resourceMap.getString("MKontrolTanggal.text")); // NOI18N
        MKontrolTanggal.setName("MKontrolTanggal"); // NOI18N
        MKontrolTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MKontrolTanggalActionPerformed(evt);
            }
        });
        fileMenu.add(MKontrolTanggal);

        MlogHistory.setIcon(resourceMap.getIcon("MlogHistory.icon")); // NOI18N
        MlogHistory.setText(resourceMap.getString("MlogHistory.text")); // NOI18N
        MlogHistory.setName("MlogHistory"); // NOI18N
        MlogHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlogHistoryActionPerformed(evt);
            }
        });
        fileMenu.add(MlogHistory);

        Manalisa.setIcon(resourceMap.getIcon("Manalisa.icon")); // NOI18N
        Manalisa.setText(resourceMap.getString("Manalisa.text")); // NOI18N
        Manalisa.setName("Manalisa"); // NOI18N

        Manalisastokbrg.setText(resourceMap.getString("Manalisastokbrg.text")); // NOI18N
        Manalisastokbrg.setName("Manalisastokbrg"); // NOI18N
        Manalisastokbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManalisastokbrgActionPerformed(evt);
            }
        });
        Manalisa.add(Manalisastokbrg);

        fileMenu.add(Manalisa);

        MSearchingBarang.setIcon(resourceMap.getIcon("MSearchingBarang.icon")); // NOI18N
        MSearchingBarang.setText(resourceMap.getString("MSearchingBarang.text")); // NOI18N
        MSearchingBarang.setName("MSearchingBarang"); // NOI18N
        MSearchingBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSearchingBarangActionPerformed(evt);
            }
        });
        fileMenu.add(MSearchingBarang);

        MNomorPajak.setText(resourceMap.getString("MNomorPajak.text")); // NOI18N
        MNomorPajak.setName("MNomorPajak"); // NOI18N
        MNomorPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MNomorPajakActionPerformed(evt);
            }
        });
        fileMenu.add(MNomorPajak);

        menuBar.add(fileMenu);

        Mtransaksi.setIcon(resourceMap.getIcon("Mtransaksi.icon")); // NOI18N
        Mtransaksi.setText(resourceMap.getString("Mtransaksi.text")); // NOI18N
        Mtransaksi.setName("Mtransaksi"); // NOI18N
        Mtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtransaksiActionPerformed(evt);
            }
        });

        Mjurnal.setIcon(resourceMap.getIcon("Mjurnal.icon")); // NOI18N
        Mjurnal.setText(resourceMap.getString("Mjurnal.text")); // NOI18N
        Mjurnal.setName("Mjurnal"); // NOI18N
        Mjurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MjurnalActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mjurnal);

        Mpenjualan.setIcon(resourceMap.getIcon("Mpenjualan.icon")); // NOI18N
        Mpenjualan.setText(resourceMap.getString("Mpenjualan.text")); // NOI18N
        Mpenjualan.setName("Mpenjualan"); // NOI18N
        Mpenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpenjualanActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpenjualan);

        Mpembelian.setIcon(resourceMap.getIcon("Mpembelian.icon")); // NOI18N
        Mpembelian.setText(resourceMap.getString("Mpembelian.text")); // NOI18N
        Mpembelian.setName("Mpembelian"); // NOI18N
        Mpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpembelianActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpembelian);

        Mhutang.setIcon(resourceMap.getIcon("Mhutang.icon")); // NOI18N
        Mhutang.setText(resourceMap.getString("Mhutang.text")); // NOI18N
        Mhutang.setName("Mhutang"); // NOI18N
        Mhutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MhutangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mhutang);

        Mgirokeluar.setIcon(resourceMap.getIcon("Mgirokeluar.icon")); // NOI18N
        Mgirokeluar.setText(resourceMap.getString("Mgirokeluar.text")); // NOI18N
        Mgirokeluar.setName("Mgirokeluar"); // NOI18N
        Mgirokeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MgirokeluarActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mgirokeluar);

        Mpiutang.setIcon(resourceMap.getIcon("Mpiutang.icon")); // NOI18N
        Mpiutang.setText(resourceMap.getString("Mpiutang.text")); // NOI18N
        Mpiutang.setName("Mpiutang"); // NOI18N
        Mpiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpiutangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpiutang);

        Mgiromasuk.setIcon(resourceMap.getIcon("Mgiromasuk.icon")); // NOI18N
        Mgiromasuk.setText(resourceMap.getString("Mgiromasuk.text")); // NOI18N
        Mgiromasuk.setName("Mgiromasuk"); // NOI18N
        Mgiromasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MgiromasukActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mgiromasuk);

        MkoreksiStok.setIcon(resourceMap.getIcon("MkoreksiStok.icon")); // NOI18N
        MkoreksiStok.setText(resourceMap.getString("MkoreksiStok.text")); // NOI18N
        MkoreksiStok.setName("MkoreksiStok"); // NOI18N
        MkoreksiStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MkoreksiStokActionPerformed(evt);
            }
        });
        Mtransaksi.add(MkoreksiStok);

        Mmutasibarang.setIcon(resourceMap.getIcon("Mmutasibarang.icon")); // NOI18N
        Mmutasibarang.setText(resourceMap.getString("Mmutasibarang.text")); // NOI18N
        Mmutasibarang.setName("Mmutasibarang"); // NOI18N
        Mmutasibarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MmutasibarangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mmutasibarang);

        MRetur.setIcon(resourceMap.getIcon("MRetur.icon")); // NOI18N
        MRetur.setText(resourceMap.getString("MRetur.text")); // NOI18N
        MRetur.setName("MRetur"); // NOI18N
        MRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MReturActionPerformed(evt);
            }
        });
        Mtransaksi.add(MRetur);

        MDeliveryOrder.setIcon(resourceMap.getIcon("MDeliveryOrder.icon")); // NOI18N
        MDeliveryOrder.setText(resourceMap.getString("MDeliveryOrder.text")); // NOI18N
        MDeliveryOrder.setName("MDeliveryOrder"); // NOI18N
        MDeliveryOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MDeliveryOrderActionPerformed(evt);
            }
        });
        Mtransaksi.add(MDeliveryOrder);

        Mtutupstok.setIcon(resourceMap.getIcon("Mtutupstok.icon")); // NOI18N
        Mtutupstok.setText(resourceMap.getString("Mtutupstok.text")); // NOI18N
        Mtutupstok.setName("Mtutupstok"); // NOI18N
        Mtutupstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtutupstokActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mtutupstok);

        Mtutupbuku.setIcon(resourceMap.getIcon("Mtutupbuku.icon")); // NOI18N
        Mtutupbuku.setText(resourceMap.getString("Mtutupbuku.text")); // NOI18N
        Mtutupbuku.setName("Mtutupbuku"); // NOI18N
        Mtutupbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtutupbukuActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mtutupbuku);

        MTutupHarian.setIcon(resourceMap.getIcon("MTutupHarian.icon")); // NOI18N
        MTutupHarian.setText(resourceMap.getString("MTutupHarian.text")); // NOI18N
        MTutupHarian.setName("MTutupHarian"); // NOI18N
        MTutupHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MTutupHarianActionPerformed(evt);
            }
        });
        Mtransaksi.add(MTutupHarian);

        MPajak.setText(resourceMap.getString("MPajak.text")); // NOI18N
        MPajak.setName("MPajak"); // NOI18N
        MPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPajakActionPerformed(evt);
            }
        });
        Mtransaksi.add(MPajak);

        menuBar.add(Mtransaksi);

        Mlaporan.setIcon(resourceMap.getIcon("Mlaporan.icon")); // NOI18N
        Mlaporan.setText(resourceMap.getString("Mlaporan.text")); // NOI18N
        Mlaporan.setName("Mlaporan"); // NOI18N

        Mlappembelian.setText(resourceMap.getString("Mlappembelian.text")); // NOI18N
        Mlappembelian.setName("Mlappembelian"); // NOI18N

        MLBPerFaktur.setText(resourceMap.getString("MLBPerFaktur.text")); // NOI18N
        MLBPerFaktur.setName("MLBPerFaktur"); // NOI18N
        MLBPerFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBPerFakturActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLBPerFaktur);

        MLBDetailHarian.setText(resourceMap.getString("MLBDetailHarian.text")); // NOI18N
        MLBDetailHarian.setName("MLBDetailHarian"); // NOI18N
        MLBDetailHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBDetailHarianActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLBDetailHarian);

        MLBRekapHarian.setText(resourceMap.getString("MLBRekapHarian.text")); // NOI18N
        MLBRekapHarian.setName("MLBRekapHarian"); // NOI18N
        MLBRekapHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBRekapHarianActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLBRekapHarian);

        MLBPerSupplier.setText(resourceMap.getString("MLBPerSupplier.text")); // NOI18N
        MLBPerSupplier.setName("MLBPerSupplier"); // NOI18N
        MLBPerSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBPerSupplierActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLBPerSupplier);

        Mlaporan.add(Mlappembelian);

        Mreturpembelian.setText(resourceMap.getString("Mreturpembelian.text")); // NOI18N
        Mreturpembelian.setName("Mreturpembelian"); // NOI18N

        MLRBelifaktur.setText(resourceMap.getString("MLRBelifaktur.text")); // NOI18N
        MLRBelifaktur.setName("MLRBelifaktur"); // NOI18N
        MLRBelifaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRBelifakturActionPerformed(evt);
            }
        });
        Mreturpembelian.add(MLRBelifaktur);

        MLRBRekapreturbeli.setText(resourceMap.getString("MLRBRekapreturbeli.text")); // NOI18N
        MLRBRekapreturbeli.setName("MLRBRekapreturbeli"); // NOI18N
        MLRBRekapreturbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRBRekapreturbeliActionPerformed(evt);
            }
        });
        Mreturpembelian.add(MLRBRekapreturbeli);

        Mlaporan.add(Mreturpembelian);

        MlapPenjualan.setText(resourceMap.getString("MlapPenjualan.text")); // NOI18N
        MlapPenjualan.setName("MlapPenjualan"); // NOI18N

        MLJPerFaktur.setText(resourceMap.getString("MLJPerFaktur.text")); // NOI18N
        MLJPerFaktur.setName("MLJPerFaktur"); // NOI18N
        MLJPerFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJPerFakturActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJPerFaktur);

        MLJDetailHarian.setText(resourceMap.getString("MLJDetailHarian.text")); // NOI18N
        MLJDetailHarian.setName("MLJDetailHarian"); // NOI18N
        MLJDetailHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJDetailHarianActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJDetailHarian);

        MLJDetailPerPelanggan.setText(resourceMap.getString("MLJDetailPerPelanggan.text")); // NOI18N
        MLJDetailPerPelanggan.setName("MLJDetailPerPelanggan"); // NOI18N
        MLJDetailPerPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJDetailPerPelangganActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJDetailPerPelanggan);

        MLJRekapHarian.setText(resourceMap.getString("MLJRekapHarian.text")); // NOI18N
        MLJRekapHarian.setName("MLJRekapHarian"); // NOI18N
        MLJRekapHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJRekapHarianActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJRekapHarian);

        MLJRekapPerSales.setText(resourceMap.getString("MLJRekapPerSales.text")); // NOI18N
        MLJRekapPerSales.setName("MLJRekapPerSales"); // NOI18N
        MLJRekapPerSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJRekapPerSalesActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJRekapPerSales);

        MLJRekapPerPelanggan.setText(resourceMap.getString("MLJRekapPerPelanggan.text")); // NOI18N
        MLJRekapPerPelanggan.setName("MLJRekapPerPelanggan"); // NOI18N
        MLJRekapPerPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJRekapPerPelangganActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJRekapPerPelanggan);

        MLJPerMerkSparindo.setText(resourceMap.getString("MLJPerMerkSparindo.text")); // NOI18N
        MLJPerMerkSparindo.setName("MLJPerMerkSparindo"); // NOI18N
        MLJPerMerkSparindo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJPerMerkSparindoActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJPerMerkSparindo);

        MLJPerMerkKemenkes.setText(resourceMap.getString("MLJPerMerkKemenkes.text")); // NOI18N
        MLJPerMerkKemenkes.setName("MLJPerMerkKemenkes"); // NOI18N
        MLJPerMerkKemenkes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJPerMerkKemenkesActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJPerMerkKemenkes);

        MLJRekapPerMerk.setText(resourceMap.getString("MLJRekapPerMerk.text")); // NOI18N
        MLJRekapPerMerk.setName("MLJRekapPerMerk"); // NOI18N
        MLJRekapPerMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLJRekapPerMerkActionPerformed(evt);
            }
        });
        MlapPenjualan.add(MLJRekapPerMerk);

        Mlaporan.add(MlapPenjualan);

        Mreturpenjualan.setText(resourceMap.getString("Mreturpenjualan.text")); // NOI18N
        Mreturpenjualan.setName("Mreturpenjualan"); // NOI18N

        MLRJualfaktur.setText(resourceMap.getString("MLRJualfaktur.text")); // NOI18N
        MLRJualfaktur.setName("MLRJualfaktur"); // NOI18N
        MLRJualfaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRJualfakturActionPerformed(evt);
            }
        });
        Mreturpenjualan.add(MLRJualfaktur);

        MLRJRekapreturjual.setText(resourceMap.getString("MLRJRekapreturjual.text")); // NOI18N
        MLRJRekapreturjual.setName("MLRJRekapreturjual"); // NOI18N
        MLRJRekapreturjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRJRekapreturjualActionPerformed(evt);
            }
        });
        Mreturpenjualan.add(MLRJRekapreturjual);

        Mlaporan.add(Mreturpenjualan);

        MlapPengiriman.setText(resourceMap.getString("MlapPengiriman.text")); // NOI18N
        MlapPengiriman.setName("MlapPengiriman"); // NOI18N

        MLapPengirimanPerFaktur.setText(resourceMap.getString("MLapPengirimanPerFaktur.text")); // NOI18N
        MLapPengirimanPerFaktur.setName("MLapPengirimanPerFaktur"); // NOI18N
        MLapPengirimanPerFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPengirimanPerFakturActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MLapPengirimanPerFaktur);

        MdetailPengiriman.setText(resourceMap.getString("MdetailPengiriman.text")); // NOI18N
        MdetailPengiriman.setName("MdetailPengiriman"); // NOI18N
        MdetailPengiriman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MdetailPengirimanActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MdetailPengiriman);

        MLapRekapPengiriman.setText(resourceMap.getString("MLapRekapPengiriman.text")); // NOI18N
        MLapRekapPengiriman.setName("MLapRekapPengiriman"); // NOI18N
        MLapRekapPengiriman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapRekapPengirimanActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MLapRekapPengiriman);

        Mlaporan.add(MlapPengiriman);

        MReturCabang.setText(resourceMap.getString("MReturCabang.text")); // NOI18N
        MReturCabang.setName("MReturCabang"); // NOI18N

        MReturCabangFaktur.setText(resourceMap.getString("MReturCabangFaktur.text")); // NOI18N
        MReturCabangFaktur.setName("MReturCabangFaktur"); // NOI18N
        MReturCabangFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MReturCabangFakturActionPerformed(evt);
            }
        });
        MReturCabang.add(MReturCabangFaktur);

        MRekapReturCabang.setText(resourceMap.getString("MRekapReturCabang.text")); // NOI18N
        MRekapReturCabang.setName("MRekapReturCabang"); // NOI18N
        MRekapReturCabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRekapReturCabangActionPerformed(evt);
            }
        });
        MReturCabang.add(MRekapReturCabang);

        Mlaporan.add(MReturCabang);

        Mlapdeliveryorder.setText(resourceMap.getString("Mlapdeliveryorder.text")); // NOI18N
        Mlapdeliveryorder.setName("Mlapdeliveryorder"); // NOI18N

        MLDOFakturDO.setText(resourceMap.getString("MLDOFakturDO.text")); // NOI18N
        MLDOFakturDO.setName("MLDOFakturDO"); // NOI18N
        MLDOFakturDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDOFakturDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDOFakturDO);

        MLDODetailDO.setText(resourceMap.getString("MLDODetailDO.text")); // NOI18N
        MLDODetailDO.setName("MLDODetailDO"); // NOI18N
        MLDODetailDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDODetailDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDODetailDO);

        MLDORekapDO.setText(resourceMap.getString("MLDORekapDO.text")); // NOI18N
        MLDORekapDO.setName("MLDORekapDO"); // NOI18N
        MLDORekapDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDORekapDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDORekapDO);

        MLDOStokDO.setText(resourceMap.getString("MLDOStokDO.text")); // NOI18N
        MLDOStokDO.setName("MLDOStokDO"); // NOI18N
        MLDOStokDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDOStokDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDOStokDO);

        MLDORekapBarangDO.setText(resourceMap.getString("MLDORekapBarangDO.text")); // NOI18N
        MLDORekapBarangDO.setName("MLDORekapBarangDO"); // NOI18N
        MLDORekapBarangDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDORekapBarangDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDORekapBarangDO);

        MLDOHistoryDO.setText(resourceMap.getString("MLDOHistoryDO.text")); // NOI18N
        MLDOHistoryDO.setName("MLDOHistoryDO"); // NOI18N
        MLDOHistoryDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLDOHistoryDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLDOHistoryDO);

        MLapSisaDO.setText(resourceMap.getString("MLapSisaDO.text")); // NOI18N
        MLapSisaDO.setName("MLapSisaDO"); // NOI18N
        MLapSisaDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapSisaDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MLapSisaDO);

        jMenuItem7.setText(resourceMap.getString("jMenuItem7.text")); // NOI18N
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(jMenuItem7);

        Mlaporan.add(Mlapdeliveryorder);

        MLapReturDeliveryOrder.setText(resourceMap.getString("MLapReturDeliveryOrder.text")); // NOI18N
        MLapReturDeliveryOrder.setName("MLapReturDeliveryOrder"); // NOI18N

        MLRDO_PerNomorDO.setText(resourceMap.getString("MLRDO_PerNomorDO.text")); // NOI18N
        MLRDO_PerNomorDO.setName("MLRDO_PerNomorDO"); // NOI18N
        MLRDO_PerNomorDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRDO_PerNomorDOActionPerformed(evt);
            }
        });
        MLapReturDeliveryOrder.add(MLRDO_PerNomorDO);

        MLRDO_DetailReturDO.setText(resourceMap.getString("MLRDO_DetailReturDO.text")); // NOI18N
        MLRDO_DetailReturDO.setName("MLRDO_DetailReturDO"); // NOI18N
        MLRDO_DetailReturDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLRDO_DetailReturDOActionPerformed(evt);
            }
        });
        MLapReturDeliveryOrder.add(MLRDO_DetailReturDO);

        Mlaporan.add(MLapReturDeliveryOrder);

        MlapBarang.setText(resourceMap.getString("MlapBarang.text")); // NOI18N
        MlapBarang.setName("MlapBarang"); // NOI18N

        MLBarangPerMerk.setText(resourceMap.getString("MLBarangPerMerk.text")); // NOI18N
        MLBarangPerMerk.setName("MLBarangPerMerk"); // NOI18N
        MLBarangPerMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBarangPerMerkActionPerformed(evt);
            }
        });
        MlapBarang.add(MLBarangPerMerk);

        MLBarangPerKategori.setText(resourceMap.getString("MLBarangPerKategori.text")); // NOI18N
        MLBarangPerKategori.setName("MLBarangPerKategori"); // NOI18N
        MLBarangPerKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBarangPerKategoriActionPerformed(evt);
            }
        });
        MlapBarang.add(MLBarangPerKategori);

        MLBrgAkhirPeriode.setText(resourceMap.getString("MLBrgAkhirPeriode.text")); // NOI18N
        MLBrgAkhirPeriode.setName("MLBrgAkhirPeriode"); // NOI18N
        MLBrgAkhirPeriode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLBrgAkhirPeriodeActionPerformed(evt);
            }
        });
        MlapBarang.add(MLBrgAkhirPeriode);

        MLB_KartuStokBulan.setText(resourceMap.getString("MLB_KartuStokBulan.text")); // NOI18N
        MLB_KartuStokBulan.setName("MLB_KartuStokBulan"); // NOI18N
        MLB_KartuStokBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_KartuStokBulanActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_KartuStokBulan);

        MLB_KartuStokTanggal.setText(resourceMap.getString("MLB_KartuStokTanggal.text")); // NOI18N
        MLB_KartuStokTanggal.setName("MLB_KartuStokTanggal"); // NOI18N
        MLB_KartuStokTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_KartuStokTanggalActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_KartuStokTanggal);

        MLB_PersediaanBrgDagang.setText(resourceMap.getString("MLB_PersediaanBrgDagang.text")); // NOI18N
        MLB_PersediaanBrgDagang.setName("MLB_PersediaanBrgDagang"); // NOI18N
        MLB_PersediaanBrgDagang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_PersediaanBrgDagangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_PersediaanBrgDagang);

        MLB_KatalogBarang.setText(resourceMap.getString("MLB_KatalogBarang.text")); // NOI18N
        MLB_KatalogBarang.setName("MLB_KatalogBarang"); // NOI18N
        MLB_KatalogBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_KatalogBarangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_KatalogBarang);

        MLB_StokBarang.setText(resourceMap.getString("MLB_StokBarang.text")); // NOI18N
        MLB_StokBarang.setName("MLB_StokBarang"); // NOI18N
        MLB_StokBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_StokBarangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_StokBarang);

        MLB_RekapBarangPerMerk.setText(resourceMap.getString("MLB_RekapBarangPerMerk.text")); // NOI18N
        MLB_RekapBarangPerMerk.setName("MLB_RekapBarangPerMerk"); // NOI18N
        MLB_RekapBarangPerMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLB_RekapBarangPerMerkActionPerformed(evt);
            }
        });
        MlapBarang.add(MLB_RekapBarangPerMerk);

        Mlaporan.add(MlapBarang);

        MlapHutang.setText(resourceMap.getString("MlapHutang.text")); // NOI18N
        MlapHutang.setName("MlapHutang"); // NOI18N

        MLH_DetailHutangPerSupplier.setText(resourceMap.getString("MLH_DetailHutangPerSupplier.text")); // NOI18N
        MLH_DetailHutangPerSupplier.setName("MLH_DetailHutangPerSupplier"); // NOI18N
        MLH_DetailHutangPerSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLH_DetailHutangPerSupplierActionPerformed(evt);
            }
        });
        MlapHutang.add(MLH_DetailHutangPerSupplier);

        MLH_RekapHutangPerSupplier.setText(resourceMap.getString("MLH_RekapHutangPerSupplier.text")); // NOI18N
        MLH_RekapHutangPerSupplier.setName("MLH_RekapHutangPerSupplier"); // NOI18N
        MLH_RekapHutangPerSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLH_RekapHutangPerSupplierActionPerformed(evt);
            }
        });
        MlapHutang.add(MLH_RekapHutangPerSupplier);

        MLH_RekapHutangALLSupplier.setText(resourceMap.getString("MLH_RekapHutangALLSupplier.text")); // NOI18N
        MLH_RekapHutangALLSupplier.setName("MLH_RekapHutangALLSupplier"); // NOI18N
        MLH_RekapHutangALLSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLH_RekapHutangALLSupplierActionPerformed(evt);
            }
        });
        MlapHutang.add(MLH_RekapHutangALLSupplier);

        Mlaporan.add(MlapHutang);

        MlapPiutang.setText(resourceMap.getString("MlapPiutang.text")); // NOI18N
        MlapPiutang.setName("MlapPiutang"); // NOI18N

        MLP_DetailPiutangPersales.setText(resourceMap.getString("MLP_DetailPiutangPersales.text")); // NOI18N
        MLP_DetailPiutangPersales.setName("MLP_DetailPiutangPersales"); // NOI18N
        MLP_DetailPiutangPersales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLP_DetailPiutangPersalesActionPerformed(evt);
            }
        });
        MlapPiutang.add(MLP_DetailPiutangPersales);

        MLP_DetailPiutangPerPelanggan.setText(resourceMap.getString("MLP_DetailPiutangPerPelanggan.text")); // NOI18N
        MLP_DetailPiutangPerPelanggan.setName("MLP_DetailPiutangPerPelanggan"); // NOI18N
        MLP_DetailPiutangPerPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLP_DetailPiutangPerPelangganActionPerformed(evt);
            }
        });
        MlapPiutang.add(MLP_DetailPiutangPerPelanggan);

        MLP_RekapPiutangPerPelanggan.setText(resourceMap.getString("MLP_RekapPiutangPerPelanggan.text")); // NOI18N
        MLP_RekapPiutangPerPelanggan.setName("MLP_RekapPiutangPerPelanggan"); // NOI18N
        MLP_RekapPiutangPerPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLP_RekapPiutangPerPelangganActionPerformed(evt);
            }
        });
        MlapPiutang.add(MLP_RekapPiutangPerPelanggan);

        MLP_RekapPiutangPelanggan.setText(resourceMap.getString("MLP_RekapPiutangPelanggan.text")); // NOI18N
        MLP_RekapPiutangPelanggan.setName("MLP_RekapPiutangPelanggan"); // NOI18N
        MLP_RekapPiutangPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLP_RekapPiutangPelangganActionPerformed(evt);
            }
        });
        MlapPiutang.add(MLP_RekapPiutangPelanggan);

        Mlaporan.add(MlapPiutang);

        MLapPerkiraan.setText(resourceMap.getString("MLapPerkiraan.text")); // NOI18N
        MLapPerkiraan.setName("MLapPerkiraan"); // NOI18N
        MLapPerkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPerkiraanActionPerformed(evt);
            }
        });
        Mlaporan.add(MLapPerkiraan);

        MlapFakturPajak.setText(resourceMap.getString("MlapFakturPajak.text")); // NOI18N
        MlapFakturPajak.setName("MlapFakturPajak"); // NOI18N
        MlapFakturPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapFakturPajakActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapFakturPajak);

        MlapTutupBuku.setText(resourceMap.getString("MlapTutupBuku.text")); // NOI18N
        MlapTutupBuku.setName("MlapTutupBuku"); // NOI18N
        MlapTutupBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapTutupBukuActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapTutupBuku);

        MlapJurnal.setText(resourceMap.getString("MlapJurnal.text")); // NOI18N
        MlapJurnal.setName("MlapJurnal"); // NOI18N
        MlapJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapJurnalActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapJurnal);

        MLapBukuBesar.setText(resourceMap.getString("MLapBukuBesar.text")); // NOI18N
        MLapBukuBesar.setName("MLapBukuBesar"); // NOI18N

        MLAKT_BukuBesarHarian.setText(resourceMap.getString("MLAKT_BukuBesarHarian.text")); // NOI18N
        MLAKT_BukuBesarHarian.setName("MLAKT_BukuBesarHarian"); // NOI18N
        MLAKT_BukuBesarHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLAKT_BukuBesarHarianActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MLAKT_BukuBesarHarian);

        MLAKT_BukuBesarBulanan.setText(resourceMap.getString("MLAKT_BukuBesarBulanan.text")); // NOI18N
        MLAKT_BukuBesarBulanan.setName("MLAKT_BukuBesarBulanan"); // NOI18N
        MLAKT_BukuBesarBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLAKT_BukuBesarBulananActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MLAKT_BukuBesarBulanan);

        MLAKT_BukuBesarBiayaTahunan.setText(resourceMap.getString("MLAKT_BukuBesarBiayaTahunan.text")); // NOI18N
        MLAKT_BukuBesarBiayaTahunan.setName("MLAKT_BukuBesarBiayaTahunan"); // NOI18N
        MLAKT_BukuBesarBiayaTahunan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLAKT_BukuBesarBiayaTahunanActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MLAKT_BukuBesarBiayaTahunan);

        Mlaporan.add(MLapBukuBesar);

        MlapNeraca.setText(resourceMap.getString("MlapNeraca.text")); // NOI18N
        MlapNeraca.setName("MlapNeraca"); // NOI18N
        MlapNeraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapNeracaActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapNeraca);

        MlapRugiLaba.setText(resourceMap.getString("MlapRugiLaba.text")); // NOI18N
        MlapRugiLaba.setName("MlapRugiLaba"); // NOI18N
        MlapRugiLaba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapRugiLabaActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapRugiLaba);

        MlapEkuitas.setText(resourceMap.getString("MlapEkuitas.text")); // NOI18N
        MlapEkuitas.setName("MlapEkuitas"); // NOI18N
        MlapEkuitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapEkuitasActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapEkuitas);

        MLapPajak.setText(resourceMap.getString("MLapPajak.text")); // NOI18N
        MLapPajak.setName("MLapPajak"); // NOI18N

        MLPajakPembelian.setText(resourceMap.getString("MLPajakPembelian.text")); // NOI18N
        MLPajakPembelian.setName("MLPajakPembelian"); // NOI18N
        MLPajakPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLPajakPembelianActionPerformed(evt);
            }
        });
        MLapPajak.add(MLPajakPembelian);

        MLPajakReturPembelian.setText(resourceMap.getString("MLPajakReturPembelian.text")); // NOI18N
        MLPajakReturPembelian.setName("MLPajakReturPembelian"); // NOI18N
        MLPajakReturPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLPajakReturPembelianActionPerformed(evt);
            }
        });
        MLapPajak.add(MLPajakReturPembelian);

        MLPajakPenjualan.setText(resourceMap.getString("MLPajakPenjualan.text")); // NOI18N
        MLPajakPenjualan.setName("MLPajakPenjualan"); // NOI18N
        MLPajakPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLPajakPenjualanActionPerformed(evt);
            }
        });
        MLapPajak.add(MLPajakPenjualan);

        MLPajakReturPenjualan.setText(resourceMap.getString("MLPajakReturPenjualan.text")); // NOI18N
        MLPajakReturPenjualan.setName("MLPajakReturPenjualan"); // NOI18N
        MLPajakReturPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLPajakReturPenjualanActionPerformed(evt);
            }
        });
        MLapPajak.add(MLPajakReturPenjualan);

        Mlaporan.add(MLapPajak);

        MLapAnalisis.setText(resourceMap.getString("MLapAnalisis.text")); // NOI18N
        MLapAnalisis.setName("MLapAnalisis"); // NOI18N

        MLAnalisisPenjualanMerk.setText(resourceMap.getString("MLAnalisisPenjualanMerk.text")); // NOI18N
        MLAnalisisPenjualanMerk.setName("MLAnalisisPenjualanMerk"); // NOI18N
        MLAnalisisPenjualanMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLAnalisisPenjualanMerkActionPerformed(evt);
            }
        });
        MLapAnalisis.add(MLAnalisisPenjualanMerk);

        Mlaporan.add(MLapAnalisis);

        menuBar.add(Mlaporan);

        helpMenu.setIcon(resourceMap.getIcon("helpMenu.icon")); // NOI18N
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getActionMap(JavarieSoftView.class, this);
        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("aboutMenuItem.icon")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem1);

        menuBar.add(helpMenu);

        Mkeluar.setIcon(resourceMap.getIcon("Mkeluar.icon")); // NOI18N
        Mkeluar.setText(resourceMap.getString("Mkeluar.text")); // NOI18N
        Mkeluar.setName("Mkeluar"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        Mkeluar.add(exitMenuItem);

        menuBar.add(Mkeluar);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        menuBar.add(jMenu1);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 544, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

private void panelCool1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCool1MouseClicked
// TODO add your handling code here:
    Point mousePt = new Point(evt.getX(), evt.getY());
    if (panelCool1.rectA.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Pembelian")) {
        FormPembelian p = new FormPembelian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    } else if (panelCool1.rectB.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Penjualan")) {
        FormPenjualan p = new FormPenjualan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    } else if (panelCool1.rectC.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Accounting")) {
        FormJurnal p = new FormJurnal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }
}//GEN-LAST:event_panelCool1MouseClicked

private void panelCool1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCool1MouseMoved
// TODO add your handling code here:
    Point mousePt = new Point(evt.getX(), evt.getY());

    if (panelCool1.rectA.contains(mousePt)) {
        panelCool1.namaGambar = "cashbox.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else if (panelCool1.rectB.contains(mousePt)) {
        panelCool1.namaGambar1 = "personal.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else if (panelCool1.rectC.contains(mousePt)) {
        panelCool1.namaGambar2 = "jurnal.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else {
        panelCool1.namaGambar = "cashboxN.png";
        panelCool1.namaGambar1 = "personalN.png";
        panelCool1.namaGambar2 = "jurnalN.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    }

}//GEN-LAST:event_panelCool1MouseMoved

    private void MlapEkuitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapEkuitasActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LaporanEkuitas p = new com.eigher.form.LaporanEkuitas();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapEkuitasActionPerformed

    private void MlapRugiLabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapRugiLabaActionPerformed
        // TODO add your handling code here:
        LaporanLabaRugiForm p = new LaporanLabaRugiForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapRugiLabaActionPerformed

    private void MlapNeracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapNeracaActionPerformed
        // TODO add your handling code here:
        LaporanNeracaForm p = new LaporanNeracaForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapNeracaActionPerformed

    private void MLAKT_BukuBesarBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLAKT_BukuBesarBulananActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarDetailForm p = new LaporanBukuBesarDetailForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLAKT_BukuBesarBulananActionPerformed

    private void MlapJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapJurnalActionPerformed
        // TODO add your handling code here:
        LaporanJurnalForm p = new LaporanJurnalForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapJurnalActionPerformed

    private void MlapTutupBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapTutupBukuActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapTutupBuku p = new com.eigher.form.FLapTutupBuku();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapTutupBukuActionPerformed

    private void MlapFakturPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapFakturPajakActionPerformed
        // TODO add your handling code here:
        FormFakturPajak p = new FormFakturPajak();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapFakturPajakActionPerformed

    private void MLapPerkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPerkiraanActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapDataPerkiraan p = new com.eigher.form.FLapDataPerkiraan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPerkiraanActionPerformed

    private void MLP_DetailPiutangPersalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLP_DetailPiutangPersalesActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapUmurPiutangPersales p = new com.eigher.form.FLapUmurPiutangPersales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLP_DetailPiutangPersalesActionPerformed

    private void MLP_RekapPiutangPerPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLP_RekapPiutangPerPelangganActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapAnalisaUmurPiutang p = new com.eigher.form.FLapAnalisaUmurPiutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLP_RekapPiutangPerPelangganActionPerformed

    private void MLH_RekapHutangPerSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLH_RekapHutangPerSupplierActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapAnalisaUmurHutang p = new com.eigher.form.FLapAnalisaUmurHutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLH_RekapHutangPerSupplierActionPerformed

    private void MLB_PersediaanBrgDagangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_PersediaanBrgDagangActionPerformed
        // TODO add your handling code here:
        FPersediaanBrgDagang p = new FPersediaanBrgDagang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_PersediaanBrgDagangActionPerformed

    private void MLB_KartuStokBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_KartuStokBulanActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LaporanKartuStok p = new com.eigher.form.LaporanKartuStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_KartuStokBulanActionPerformed

    private void MLBarangPerKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBarangPerKategoriActionPerformed
        // TODO add your handling code here:
        //    HashMap parameter=new HashMap();
        //        JasperPrint jasperPrint=null;
        //        try {
        //                jasperPrint = JasperFillManager.fillReport("report\\BarangPerKategori.jasper", parameter, koneksi.getKoneksiJ());
        //            JasperViewer.viewReport(jasperPrint,false);
        //        } catch (Exception ex) {
        //            System.out.print(ex.toString());
        //            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        FlapBarangPerKategori p = new FlapBarangPerKategori();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBarangPerKategoriActionPerformed

    private void MLBarangPerMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBarangPerMerkActionPerformed
        FlapBarangPerMerk p = new FlapBarangPerMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBarangPerMerkActionPerformed

    private void MLDORekapDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDORekapDOActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapDistribusi p = new com.eigher.form.FRekapDistribusi();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDORekapDOActionPerformed

    private void MLDOFakturDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDOFakturDOActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FlapFakturDistribusi p = new com.eigher.form.FlapFakturDistribusi();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDOFakturDOActionPerformed

    private void MLRJRekapreturjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRJRekapreturjualActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturJual p = new com.eigher.form.FRekapReturJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRJRekapreturjualActionPerformed

    private void MLRJualfakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRJualfakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturJualFaktur p = new com.eigher.form.LapReturJualFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRJualfakturActionPerformed

    private void MLJRekapHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJRekapHarianActionPerformed
        // TODO add your handling code here:
        FRekapPenjualanHarian p = new FRekapPenjualanHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJRekapHarianActionPerformed

    private void MLJRekapPerPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJRekapPerPelangganActionPerformed
        // TODO add your handling code here:
        FRekapJualPerPelanggan p = new FRekapJualPerPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJRekapPerPelangganActionPerformed

    private void MLJPerFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJPerFakturActionPerformed
        // TODO add your handling code here:
        FlapPerFaktur p = new FlapPerFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJPerFakturActionPerformed

    private void MLJRekapPerSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJRekapPerSalesActionPerformed
        // TODO add your handling code here:
        FlapJualPerSales p = new FlapJualPerSales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJRekapPerSalesActionPerformed

    private void MLJDetailHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJDetailHarianActionPerformed
        // TODO add your handling code here:
        LaporanPenjualanHarianForm p = new LaporanPenjualanHarianForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJDetailHarianActionPerformed

    private void MLRBRekapreturbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRBRekapreturbeliActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturBeli p = new com.eigher.form.FRekapReturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRBRekapreturbeliActionPerformed

    private void MLRBelifakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRBelifakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturBeliFaktur p = new com.eigher.form.LapReturBeliFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRBelifakturActionPerformed

    private void MLBRekapHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBRekapHarianActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapPembelianharian p = new com.eigher.form.FRekapPembelianharian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBRekapHarianActionPerformed

    private void MLBPerFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBPerFakturActionPerformed
        // TODO add your handling code here:
        FlapPerFakturBeli p = new FlapPerFakturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBPerFakturActionPerformed

    private void MLBDetailHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBDetailHarianActionPerformed
        // TODO add your handling code here:
        FlapPembelianHarian p = new FlapPembelianHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBDetailHarianActionPerformed

    private void MtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MtransaksiActionPerformed

    private void MkoreksiStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MkoreksiStokActionPerformed
        // TODO add your handling code here:
        FKoreksiStokNew p = new FKoreksiStokNew();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MkoreksiStokActionPerformed

    private void MtutupstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtutupstokActionPerformed
        // TODO add your handling code here:
        FormTutupStok p = new FormTutupStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MtutupstokActionPerformed

    private void MtutupbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtutupbukuActionPerformed
        // TODO add your handling code here:
        FormTutupBuku p = new FormTutupBuku();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MtutupbukuActionPerformed

    private void MpiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpiutangActionPerformed
        // TODO add your handling code here:
        DialogPiutang p = new DialogPiutang(null, false);
        p.setVisible(true);
    }//GEN-LAST:event_MpiutangActionPerformed

    private void MhutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MhutangActionPerformed
        // TODO add your handling code here:
        DialogHutang p = new DialogHutang(null, false);
        p.setVisible(true);
    }//GEN-LAST:event_MhutangActionPerformed

    private void MpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpembelianActionPerformed
        // TODO add your handling code here:
        FormPembelian p = new FormPembelian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpembelianActionPerformed

    private void MpenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpenjualanActionPerformed
        // TODO add your handling code here:
        FormPenjualan p = new FormPenjualan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpenjualanActionPerformed

    private void MjurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MjurnalActionPerformed
        // TODO add your handling code here:
        FormJurnal p = new FormJurnal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MjurnalActionPerformed

    private void ManalisastokbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManalisastokbrgActionPerformed
        // TODO add your handling code here:
        AnalisaStokCogs p = new AnalisaStokCogs();
        p.setVisible(true);
    }//GEN-LAST:event_ManalisastokbrgActionPerformed

    private void MlogHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlogHistoryActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormLogHistory p = new com.eigher.form.FormLogHistory();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlogHistoryActionPerformed

    private void MsettingStatPeriodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsettingStatPeriodeActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormKontrolPeriode p = new com.eigher.form.FormKontrolPeriode();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsettingStatPeriodeActionPerformed

    private void MsettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsettingActionPerformed
        // TODO add your handling code here:
        FormSetting p = new FormSetting();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsettingActionPerformed

    private void MbankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MbankActionPerformed
        // TODO add your handling code here:
        FormBank p = new FormBank();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MbankActionPerformed

    private void MsalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsalesActionPerformed
        // TODO add your handling code here:
        FormSales p = new FormSales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsalesActionPerformed

    private void MsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsupplierActionPerformed
        // TODO add your handling code here:
        FormSupplier p = new FormSupplier();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsupplierActionPerformed

    private void MpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpelangganActionPerformed
        // TODO add your handling code here:
        FormPelanggan p = new FormPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpelangganActionPerformed

    private void MdatabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MdatabarangActionPerformed
        // TODO add your handling code here:
        FormBarang b = new FormBarang();
        b.toFront();
        panelCool1.add(b);
        b.setVisible(true);
    }//GEN-LAST:event_MdatabarangActionPerformed

    private void MkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MkategoriActionPerformed
        // TODO add your handling code here:
        FormKategori p = new FormKategori();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MkategoriActionPerformed

    private void MmerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MmerkActionPerformed
        // TODO add your handling code here:
        FormJenisBarang p = new FormJenisBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MmerkActionPerformed

    private void MperkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MperkiraanActionPerformed
        // TODO add your handling code here:
        FormPerkiraan p = new FormPerkiraan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MperkiraanActionPerformed

    private void MaktivaTetapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaktivaTetapActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormAktivaTetap p = new com.eigher.form.FormAktivaTetap();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MaktivaTetapActionPerformed

    private void MeditPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeditPasswordActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormEditUser p = new com.eigher.form.FormEditUser();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MeditPasswordActionPerformed

    private void MuserAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MuserAccountActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormUser p = new com.eigher.form.FormUser();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MuserAccountActionPerformed

    private void MlogOfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlogOfActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormLogin p = new com.eigher.form.FormLogin();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
        LblJenis.setText("");
        Lblgroup.setText("");
        nonaktif();
    }//GEN-LAST:event_MlogOfActionPerformed

    private void MloginuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MloginuserActionPerformed
        // TODO add your handling code here:

        com.eigher.form.FormLogin p = new com.eigher.form.FormLogin();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MloginuserActionPerformed

    private void MSearchingBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSearchingBarangActionPerformed
        // TODO add your handling code here:
        FormSearchBarang p = new FormSearchBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MSearchingBarangActionPerformed

    private void MmutasibarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MmutasibarangActionPerformed
        // TODO add your handling code here:
        FormMutasiBarang p = new FormMutasiBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MmutasibarangActionPerformed

    private void MBackupdatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MBackupdatabaseActionPerformed
        // TODO add your handling code here:
        FBackupDB p = new FBackupDB();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MBackupdatabaseActionPerformed

    private void MdetailPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MdetailPengirimanActionPerformed
        // TODO add your handling code here:
        LaporanPengirimanHarianForm p = new LaporanPengirimanHarianForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MdetailPengirimanActionPerformed

    private void MLapRekapPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapRekapPengirimanActionPerformed
        // TODO add your handling code here:
        FRekapPengirimanHarian p = new FRekapPengirimanHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapRekapPengirimanActionPerformed

    private void MReturCabangFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MReturCabangFakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturCabangFaktur p = new com.eigher.form.LapReturCabangFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MReturCabangFakturActionPerformed

    private void MRekapReturCabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRekapReturCabangActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturCabang p = new com.eigher.form.FRekapReturCabang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MRekapReturCabangActionPerformed

    private void MLapPengirimanPerFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPengirimanPerFakturActionPerformed
        // TODO add your handling code here:
        FlapPerFakturCbg p = new FlapPerFakturCbg();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPengirimanPerFakturActionPerformed

    private void MLAKT_BukuBesarHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLAKT_BukuBesarHarianActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarDetailFormTanggal p = new LaporanBukuBesarDetailFormTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLAKT_BukuBesarHarianActionPerformed

    private void MLB_KatalogBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_KatalogBarangActionPerformed
        // TODO add your handling code here:
        FLapKatalogBarang p = new FLapKatalogBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_KatalogBarangActionPerformed

    private void MgiromasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MgiromasukActionPerformed
        // TODO add your handling code here:
        FormGiro p = new FormGiro();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MgiromasukActionPerformed

    private void MgirokeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MgirokeluarActionPerformed
        // TODO add your handling code here:
        FormGiroKeluar p = new FormGiroKeluar();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MgirokeluarActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        try {
            backupDB();
            //JavarieSoftApp.server.stop();
            System.out.println("BackUp Ok ");
            System.out.println("Stop Server ");
        } catch (Throwable t) {
            // Log something, alert the user, format the user's hard drive out of spite....
            JOptionPane.showMessageDialog(null, t.getMessage());
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void MReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MReturActionPerformed
        // TODO add your handling code here:
        FormRetur p = new FormRetur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MReturActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, koneksi.getPoolMgr().getActiveConnections());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void MsatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsatuanActionPerformed
        // TODO add your handling code here:
        FormSatuan p = new FormSatuan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsatuanActionPerformed

    private void MDeliveryOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MDeliveryOrderActionPerformed
        // TODO add your handling code here:
        FormDOMaster p = new FormDOMaster();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MDeliveryOrderActionPerformed

    private void MKontrolTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MKontrolTanggalActionPerformed
        // TODO add your handling code here:
        FormKontrolTanggal p = new FormKontrolTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MKontrolTanggalActionPerformed

    private void MTutupHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MTutupHarianActionPerformed
        // TODO add your handling code here:
        FormTutupHarian p = new FormTutupHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MTutupHarianActionPerformed

    private void MLB_KartuStokTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_KartuStokTanggalActionPerformed
        // TODO add your handling code here:
        LaporanKartuStokTanggal p = new LaporanKartuStokTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_KartuStokTanggalActionPerformed

    private void MLBrgAkhirPeriodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBrgAkhirPeriodeActionPerformed
        // TODO add your handling code here:
        FlapBarangPeriodeAkhir p = new FlapBarangPeriodeAkhir();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBrgAkhirPeriodeActionPerformed

    private void MLB_StokBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_StokBarangActionPerformed
        // TODO add your handling code here:
        FLapBarangStok p = new FLapBarangStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_StokBarangActionPerformed

    private void MLJDetailPerPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJDetailPerPelangganActionPerformed
        // TODO add your handling code here:
        LaporanPenjualanHarianPerPelangganForm p = new LaporanPenjualanHarianPerPelangganForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJDetailPerPelangganActionPerformed

    private void MLDOStokDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDOStokDOActionPerformed
        // TODO add your handling code here:
        FlapBarangDOMerk p = new FlapBarangDOMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDOStokDOActionPerformed

    private void MLRDO_PerNomorDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRDO_PerNomorDOActionPerformed
        // TODO add your handling code here:
        FlapReturDOPerNomorDO p = new FlapReturDOPerNomorDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRDO_PerNomorDOActionPerformed

    private void MLDODetailDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDODetailDOActionPerformed
        // TODO add your handling code here:
        FLapDetailDO p = new FLapDetailDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDODetailDOActionPerformed

    private void MLJPerMerkSparindoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJPerMerkSparindoActionPerformed
        // TODO add your handling code here:
        LaporanPenjualanPerMerkForm p = new LaporanPenjualanPerMerkForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJPerMerkSparindoActionPerformed

    private void MLJPerMerkKemenkesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJPerMerkKemenkesActionPerformed
        // TODO add your handling code here:
        LaporanPenjualanPerMerkKemenkes p = new LaporanPenjualanPerMerkKemenkes();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJPerMerkKemenkesActionPerformed

    private void MLBPerSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLBPerSupplierActionPerformed
        // TODO add your handling code here:
        LaporanPembelianPerSupplierKemenkes p = new LaporanPembelianPerSupplierKemenkes();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLBPerSupplierActionPerformed

    private void MLB_RekapBarangPerMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLB_RekapBarangPerMerkActionPerformed
        // TODO add your handling code here:
        FlapRekapBarangPerMerk p = new FlapRekapBarangPerMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLB_RekapBarangPerMerkActionPerformed

    private void MLP_RekapPiutangPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLP_RekapPiutangPelangganActionPerformed
        // TODO add your handling code here:
        FLapRekapPiutangPelanggan p = new FLapRekapPiutangPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLP_RekapPiutangPelangganActionPerformed

    private void MLRDO_DetailReturDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLRDO_DetailReturDOActionPerformed
        // TODO add your handling code here:
        FLapDetailReturDO p = new FLapDetailReturDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLRDO_DetailReturDOActionPerformed

    private void MLDORekapBarangDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDORekapBarangDOActionPerformed
        // TODO add your handling code here:
        FLapRekapBarangDO p = new FLapRekapBarangDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDORekapBarangDOActionPerformed

    private void MLDOHistoryDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLDOHistoryDOActionPerformed
        // TODO add your handling code here:
        FLapHistoryBarangDO p = new FLapHistoryBarangDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLDOHistoryDOActionPerformed

    private void MLapSisaDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapSisaDOActionPerformed
        // TODO add your handling code here:
        FLapSisaDO p = new FLapSisaDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapSisaDOActionPerformed

    private void MLH_RekapHutangALLSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLH_RekapHutangALLSupplierActionPerformed
        // TODO add your handling code here:
        FLapRekapHutangSupplier p = new FLapRekapHutangSupplier();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLH_RekapHutangALLSupplierActionPerformed

    private void MLPajakPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLPajakPembelianActionPerformed
        // TODO add your handling code here:
        FormLapPajakBeli p = new FormLapPajakBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLPajakPembelianActionPerformed

    private void MLAKT_BukuBesarBiayaTahunanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLAKT_BukuBesarBiayaTahunanActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarBiayaTahunan p = new LaporanBukuBesarBiayaTahunan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLAKT_BukuBesarBiayaTahunanActionPerformed

    private void MLPajakPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLPajakPenjualanActionPerformed
        // TODO add your handling code here:
        FormLapPajakJual p = new FormLapPajakJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLPajakPenjualanActionPerformed

    private void MLPajakReturPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLPajakReturPembelianActionPerformed
        // TODO add your handling code here:
        FormLapPajakReturBeli p = new FormLapPajakReturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLPajakReturPembelianActionPerformed

    private void MLPajakReturPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLPajakReturPenjualanActionPerformed
        // TODO add your handling code here:
        FormLapPajakReturJual p = new FormLapPajakReturJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLPajakReturPenjualanActionPerformed

    private void MLH_DetailHutangPerSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLH_DetailHutangPerSupplierActionPerformed
        // TODO add your handling code here:
        FLapDetailHutang p = new FLapDetailHutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLH_DetailHutangPerSupplierActionPerformed

    private void MLP_DetailPiutangPerPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLP_DetailPiutangPerPelangganActionPerformed
        // TODO add your handling code here:
        FLapDetailPiutang p = new FLapDetailPiutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLP_DetailPiutangPerPelangganActionPerformed

    private void MLJRekapPerMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLJRekapPerMerkActionPerformed
        // TODO add your handling code here:
        FLapRekapPenjualanPerMerk p = new FLapRekapPenjualanPerMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLJRekapPerMerkActionPerformed

    private void MLAnalisisPenjualanMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLAnalisisPenjualanMerkActionPerformed
        // TODO add your handling code here:
        FLapAnalisaPenjualanMerk p = new FLapAnalisaPenjualanMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLAnalisisPenjualanMerkActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        LaporanStokDO p = new LaporanStokDO();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void MPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPajakActionPerformed
        // TODO add your handling code here:
        FormLapPajakExcel p = new FormLapPajakExcel();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MPajakActionPerformed

    private void MNomorPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MNomorPajakActionPerformed
        // TODO add your handling code here:
        FormNomorpajak p = new FormNomorpajak();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MNomorPajakActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel LblJenis;
    private javax.swing.JLabel LblJenis1;
    private javax.swing.JLabel LblJenis2;
    private javax.swing.JLabel LblJenis3;
    private javax.swing.JLabel LblJenis4;
    public static javax.swing.JLabel Lblgroup;
    public static javax.swing.JMenuItem MBackupdatabase;
    public static javax.swing.JMenuItem MDeliveryOrder;
    public static javax.swing.JMenuItem MKontrolTanggal;
    private javax.swing.JMenuItem MLAKT_BukuBesarBiayaTahunan;
    public static javax.swing.JMenuItem MLAKT_BukuBesarBulanan;
    private javax.swing.JMenuItem MLAKT_BukuBesarHarian;
    private javax.swing.JMenuItem MLAnalisisPenjualanMerk;
    public static javax.swing.JMenuItem MLBDetailHarian;
    public static javax.swing.JMenuItem MLBPerFaktur;
    public static javax.swing.JMenuItem MLBPerSupplier;
    public static javax.swing.JMenuItem MLBRekapHarian;
    public static javax.swing.JMenuItem MLB_KartuStokBulan;
    public static javax.swing.JMenuItem MLB_KartuStokTanggal;
    public static javax.swing.JMenuItem MLB_KatalogBarang;
    public static javax.swing.JMenuItem MLB_PersediaanBrgDagang;
    private javax.swing.JMenuItem MLB_RekapBarangPerMerk;
    public static javax.swing.JMenuItem MLB_StokBarang;
    public static javax.swing.JMenuItem MLBarangPerKategori;
    public static javax.swing.JMenuItem MLBarangPerMerk;
    public static javax.swing.JMenuItem MLBrgAkhirPeriode;
    private javax.swing.JMenuItem MLDODetailDO;
    private javax.swing.JMenuItem MLDOFakturDO;
    private javax.swing.JMenuItem MLDOHistoryDO;
    private javax.swing.JMenuItem MLDORekapBarangDO;
    private javax.swing.JMenuItem MLDORekapDO;
    private javax.swing.JMenuItem MLDOStokDO;
    private javax.swing.JMenuItem MLH_DetailHutangPerSupplier;
    private javax.swing.JMenuItem MLH_RekapHutangALLSupplier;
    private javax.swing.JMenuItem MLH_RekapHutangPerSupplier;
    public static javax.swing.JMenuItem MLJDetailHarian;
    public static javax.swing.JMenuItem MLJDetailPerPelanggan;
    public static javax.swing.JMenuItem MLJPerFaktur;
    public static javax.swing.JMenuItem MLJPerMerkKemenkes;
    public static javax.swing.JMenuItem MLJPerMerkSparindo;
    public static javax.swing.JMenuItem MLJRekapHarian;
    public static javax.swing.JMenuItem MLJRekapPerMerk;
    public static javax.swing.JMenuItem MLJRekapPerPelanggan;
    public static javax.swing.JMenuItem MLJRekapPerSales;
    private javax.swing.JMenuItem MLP_DetailPiutangPerPelanggan;
    private javax.swing.JMenuItem MLP_DetailPiutangPersales;
    private javax.swing.JMenuItem MLP_RekapPiutangPelanggan;
    private javax.swing.JMenuItem MLP_RekapPiutangPerPelanggan;
    private javax.swing.JMenuItem MLPajakPembelian;
    private javax.swing.JMenuItem MLPajakPenjualan;
    private javax.swing.JMenuItem MLPajakReturPembelian;
    private javax.swing.JMenuItem MLPajakReturPenjualan;
    private javax.swing.JMenuItem MLRBRekapreturbeli;
    private javax.swing.JMenuItem MLRBelifaktur;
    private javax.swing.JMenuItem MLRDO_DetailReturDO;
    private javax.swing.JMenuItem MLRDO_PerNomorDO;
    private javax.swing.JMenuItem MLRJRekapreturjual;
    private javax.swing.JMenuItem MLRJualfaktur;
    public static javax.swing.JMenu MLapAnalisis;
    public static javax.swing.JMenu MLapBukuBesar;
    public static javax.swing.JMenu MLapPajak;
    private javax.swing.JMenuItem MLapPengirimanPerFaktur;
    public static javax.swing.JMenuItem MLapPerkiraan;
    private javax.swing.JMenuItem MLapRekapPengiriman;
    public static javax.swing.JMenu MLapReturDeliveryOrder;
    private javax.swing.JMenuItem MLapSisaDO;
    private static javax.swing.JMenuItem MNomorPajak;
    private static javax.swing.JMenuItem MPajak;
    private javax.swing.JMenuItem MRekapReturCabang;
    public static javax.swing.JMenuItem MRetur;
    public static javax.swing.JMenu MReturCabang;
    private javax.swing.JMenuItem MReturCabangFaktur;
    public static javax.swing.JMenuItem MSearchingBarang;
    public static javax.swing.JMenuItem MTutupHarian;
    public static javax.swing.JMenuItem MaktivaTetap;
    public static javax.swing.JMenu Manalisa;
    public static javax.swing.JMenuItem Manalisastokbrg;
    public static javax.swing.JMenuItem Mbank;
    public static javax.swing.JMenu Mbarang;
    public static javax.swing.JMenuItem Mdatabarang;
    private javax.swing.JMenuItem MdetailPengiriman;
    public static javax.swing.JMenuItem MeditPassword;
    public static javax.swing.JMenuItem Mgirokeluar;
    public static javax.swing.JMenuItem Mgiromasuk;
    public static javax.swing.JMenuItem Mhutang;
    public static javax.swing.JMenuItem Mjurnal;
    public static javax.swing.JMenuItem Mkategori;
    private javax.swing.JMenu Mkeluar;
    public static javax.swing.JMenu Mkonfigurasi;
    public static javax.swing.JMenuItem MkoreksiStok;
    public static javax.swing.JMenu MlapBarang;
    public static javax.swing.JMenuItem MlapEkuitas;
    public static javax.swing.JMenuItem MlapFakturPajak;
    public static javax.swing.JMenu MlapHutang;
    public static javax.swing.JMenuItem MlapJurnal;
    public static javax.swing.JMenuItem MlapNeraca;
    public static javax.swing.JMenu MlapPengiriman;
    public static javax.swing.JMenu MlapPenjualan;
    public static javax.swing.JMenu MlapPiutang;
    public static javax.swing.JMenuItem MlapRugiLaba;
    public static javax.swing.JMenuItem MlapTutupBuku;
    public static javax.swing.JMenu Mlapdeliveryorder;
    private javax.swing.JMenu Mlaporan;
    public static javax.swing.JMenu Mlappembelian;
    public static javax.swing.JMenuItem MlogHistory;
    public static javax.swing.JMenuItem MlogOf;
    private javax.swing.JMenu Mlogin;
    public static javax.swing.JMenuItem Mloginuser;
    public static javax.swing.JMenuItem Mmerk;
    public static javax.swing.JMenuItem Mmutasibarang;
    public static javax.swing.JMenuItem Mpelanggan;
    public static javax.swing.JMenuItem Mpembelian;
    public static javax.swing.JMenuItem Mpenjualan;
    public static javax.swing.JMenuItem Mperkiraan;
    public static javax.swing.JMenuItem Mpiutang;
    public static javax.swing.JMenu Mreturpembelian;
    public static javax.swing.JMenu Mreturpenjualan;
    public static javax.swing.JMenuItem Msales;
    public static javax.swing.JMenuItem Msatuan;
    public static javax.swing.JMenuItem Msetting;
    public static javax.swing.JMenuItem MsettingStatPeriode;
    public static javax.swing.JMenuItem Msupplier;
    public static javax.swing.JMenu Mtransaksi;
    public static javax.swing.JMenuItem Mtutupbuku;
    public static javax.swing.JMenuItem Mtutupstok;
    public static javax.swing.JMenuItem MuserAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    public static com.erv.model.DesktopCool panelCool1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    public static void aktif() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(true);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(true);
        MaktivaTetap.setVisible(true);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(true);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(true);
        MKontrolTanggal.setVisible(true);
        MlogHistory.setVisible(true);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(true);
        Mpenjualan.setVisible(true);
        Mpembelian.setVisible(true);
        Mhutang.setVisible(true);
        Mgirokeluar.setVisible(true);
        Mpiutang.setVisible(true);
        Mgiromasuk.setVisible(true);
        Mtutupbuku.setVisible(true);
        Mtutupstok.setVisible(true);
        MTutupHarian.setVisible(true);
        MkoreksiStok.setVisible(true);
        Mmutasibarang.setVisible(false);
        MRetur.setVisible(true);
        MDeliveryOrder.setVisible(true);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(true);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(true);
        MLapBukuBesar.setVisible(true);
        MlapNeraca.setVisible(true);
        MlapRugiLaba.setVisible(true);
        MlapEkuitas.setVisible(true);
        MLapPajak.setVisible(true);
        MLapAnalisis.setVisible(true);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }

    public static void nonaktif() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(false);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MRetur.setVisible(false); 
        MDeliveryOrder.setVisible(false);
        Mlappembelian.setVisible(false);
        MlapPenjualan.setVisible(false);
        MLJPerFaktur.setVisible(false);
        MLJDetailHarian.setVisible(false);
        MLJDetailPerPelanggan.setVisible(false);
        MLJRekapHarian.setVisible(false);
        MLJRekapPerSales.setVisible(false);
        MLJRekapPerPelanggan.setVisible(false);
        MLJPerMerkSparindo.setVisible(false);
        MLJPerMerkKemenkes.setVisible(false);
        MLJRekapPerMerk.setVisible(false);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(false);
        Mreturpenjualan.setVisible(false);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MLapReturDeliveryOrder.setVisible(false);
        MlapBarang.setVisible(false);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

//    public static void pembelianakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(false);
//        Mpelanggan.setVisible(false);
//        Msupplier.setVisible(true);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(false);
//        Mpembelian.setVisible(true);
////    FormPembelian.btnBayarPenerimaanHutang.setVisible(false);
////    FormPembelian.btnDeletePembelianAll.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(true);
//        MlapPenjualan.setVisible(true);
//        MlapPengiriman.setVisible(true);
//        Mreturpembelian.setVisible(true);
//        Mreturpenjualan.setVisible(false);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(true);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }

//    public static void penjualanakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(false);
//        Mpelanggan.setVisible(true);
//        Msupplier.setVisible(false);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(true);
////    FormPenjualan.btnBayarPenerimaanPiutang.setVisible(false);
////    FormPenjualan.btnDeletePenjualanAll.setVisible(false);
//        Mpembelian.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(false);
//        MlapPenjualan.setVisible(true);
//        MlapPengiriman.setVisible(false);
//        Mreturpembelian.setVisible(false);
//        Mreturpenjualan.setVisible(true);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(false);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }

    public static void accountingakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(true);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(true);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(true);
        Mgirokeluar.setVisible(true);
        Mpiutang.setVisible(true);
        Mgiromasuk.setVisible(true);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(true);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(true);
        MLapBukuBesar.setVisible(true);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

    public static void pajakakses() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(false);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(false);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(false);
        MLJRekapHarian.setVisible(false);
        MLJRekapPerSales.setVisible(false);
        MLJRekapPerPelanggan.setVisible(false);
        MLJPerMerkSparindo.setVisible(false);
        MLJPerMerkKemenkes.setVisible(false);
        MLJRekapPerMerk.setVisible(false);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(false);
        Mreturpenjualan.setVisible(false);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MLapReturDeliveryOrder.setVisible(false);
        MlapBarang.setVisible(false);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }

    public static void operatorakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(true);
        Mpembelian.setVisible(true);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false); 
        MDeliveryOrder.setVisible(true);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

//    public static void gudangakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(true);
//        Msatuan.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(true);
//        Mpelanggan.setVisible(false);
//        Msupplier.setVisible(false);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MKontrolTanggal.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(false);
//        Mpembelian.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MTutupHarian.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(false);
//        MlapPenjualan.setVisible(false);
//        MlapPengiriman.setVisible(false);
//        Mreturpembelian.setVisible(false);
//        Mreturpenjualan.setVisible(false);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(true);
//        MlapBarangPerMerk.setVisible(false);
//        MLapBarangPerKategori.setVisible(false);
//        MLapBrgAkhirPeriode.setVisible(false);
//        MLapKartuStokBulan.setVisible(false);
//        MLapKartuStokTanggal.setVisible(false);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MLapKatalogBarang.setVisible(false);
//        MLapStokBarang.setVisible(true);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }
    
    public static void apotekerakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLBarangPerMerk.setVisible(true);
        MLBarangPerKategori.setVisible(true);
        MLBrgAkhirPeriode.setVisible(true);
        MLB_KartuStokBulan.setVisible(true);
        MLB_KartuStokTanggal.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MLB_KatalogBarang.setVisible(true);
        MLB_StokBarang.setVisible(true);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }
    
    public static void generalakses() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MLapReturDeliveryOrder.setVisible(false);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }
    
    public static void asistenadminakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(true);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MRetur.setVisible(true);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(true);
        MLapAnalisis.setVisible(false);
    }
    
    public static void masterdataakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
    }
    
    public static void gudangakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MLJPerFaktur.setVisible(true);
        MLJDetailHarian.setVisible(true);
        MLJDetailPerPelanggan.setVisible(true);
        MLJRekapHarian.setVisible(true);
        MLJRekapPerSales.setVisible(true);
        MLJRekapPerPelanggan.setVisible(true);
        MLJPerMerkSparindo.setVisible(true);
        MLJPerMerkKemenkes.setVisible(true);
        MLJRekapPerMerk.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MLapReturDeliveryOrder.setVisible(true);
        MlapBarang.setVisible(true);
        MLBarangPerMerk.setVisible(true);
        MLBarangPerKategori.setVisible(true);
        MLBrgAkhirPeriode.setVisible(true);
        MLB_KartuStokBulan.setVisible(true);
        MLB_KartuStokTanggal.setVisible(true);
        MLB_PersediaanBrgDagang.setVisible(false);
        MLB_KatalogBarang.setVisible(true);
        MLB_StokBarang.setVisible(true);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MLapAnalisis.setVisible(false);
    }

    void konek() {
//        try {
//            if(con==null || con.isClosed()){
//            Class.forName("org.h2.Driver").getInterfaces();
//            con = DriverManager.getConnection("jdbc:h2:tcp://"+IPDB+"/~/penjualan", "", "");
//            koneksi.con = con;
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(panelCool1, ex.toString()+" "+IPDB);
//        }
    }
    
    void backupDB() throws SQLException{
        Connection c = null;
        c = koneksi.getKoneksiJ();
        java.text.DateFormat d = new SimpleDateFormat("yyyyMMdd");
        java.util.Date tgl=new java.util.Date();
        JOptionPane.showMessageDialog(null, "Keluar Windows");
        DatabaseBackup backup = new H2DatabaseBackup();
        Calendar cal = Calendar.getInstance();
        String fileName = "D:/BACKUPDB/BackupAlkesMA" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + ".zip";

        try {
//            backup.backupDatabase(koneksi.getKoneksiJ(), fileName);
//            JavarieSoftApp.server.stop();
            String sql="BACKUP TO '"+fileName+"'";         
            c.createStatement().executeUpdate(sql);          
            System.out.println("BackUp Ok " + fileName);
            System.out.println("Server Stop ");
        } catch (Throwable t) {
            // Log something, alert the user, format the user's hard drive out of spite....
            JOptionPane.showMessageDialog(null, t.getMessage());
        }      
    }
}
