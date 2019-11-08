/*
 * JavarieSoftApp.java
 */
package javariesoft;

import com.erv.db.koneksi;
import static com.erv.db.koneksi.IP;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.h2.tools.Server;

/**
 * The main class of the application.
 */
public class JavarieSoftApp extends SingleFrameApplication {

    static Server server;

    public static String VERSI = "VERSI6.9MRTAALKES";
    public static String VERSISERVER = "V-1.9.6.9";
    public static String IPADDRESS = "localhost";
    public static String jenisuser = "";
    public static String groupuser = "";
    public static String REPORT = "file:/C:/report/";

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new JavarieSoftView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of JavarieSoftApp
     */
    public static JavarieSoftApp getApplication() {
        return Application.getInstance(JavarieSoftApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        //String connection = "jdbc:h2:~/satuan";
        Connection c = null;
        try {
            if (!com.erv.fungsi.Fungsi.cekVersi(VERSI)) {
                System.exit(0);
            }
            if (args.length > 0) {
                global.IPADDRESS = args[0];
                global.REPORT = args[1];
            } else {
                Properties p = new Properties();
                p.load(new FileInputStream("javarie.properties"));
                global.IPADDRESS = p.getProperty("IPADDRESS");
                global.REPORT = p.getProperty("REPORT");
            }
            koneksi.IP = global.IPADDRESS;
            koneksi.setUrlJ("jdbc:h2:tcp://" + IP + "/~/dbalkesmrta");
            koneksi.createPoolKoneksi();

            /*if (!com.erv.fungsi.Fungsi.cekVersiServer(VERSISERVER)) {
                System.exit(0);
            }*/
//            String path = new File(".").getCanonicalPath();
//            System.out.println(path);
//            Properties p = new Properties();
//            p.load(new FileInputStream("javarie.properties"));
//            global.IPADDRESS = p.getProperty("IPADDRESS");
//            koneksi.IP = p.getProperty("IPADDRESS");
//            global.REPORT = p.getProperty("REPORT");
            //server = Server.createTcpServer(connection).start();
            System.out.println("Server Database Aktif");
            //launch(JavarieSoftApp.class, args);
//MulaiCdCPU
            c = koneksi.getKoneksiJ();
//            Statement stat=c.createStatement();
//            String h="";
//               byte[] hasil;
//            try {
//                hasil = com.erv.function.Authentication.encryptPassword(com.erv.function.MiscUtils.getCpuID());
//            
//               for(int i=0;i<hasil.length;i++){
//                   h+=hasil[i];
//                }
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(JavarieSoftApp.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(JavarieSoftApp.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            ResultSet rs=stat.executeQuery("select * from KODEAKSES where KODE='"+h+"'");
//            if(rs.next()){
            launch(JavarieSoftApp.class, args);
//            }else{
//                DialogRegister d=new DialogRegister(null, true);
//                    d.setVisible(true);
//            }
//            rs.close();
//            stat.close();
            c.close();
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Belum Aktif");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavarieSoftApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavarieSoftApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
