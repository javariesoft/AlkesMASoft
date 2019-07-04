
import com.erv.db.barangDao;
import com.erv.db.hutangDao;
import com.erv.db.koneksi;
import com.erv.handler.Initiater;
import com.erv.handler.Responder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author erwadi
 */
public class test {
    public static void main(String[] args){
        Initiater initiater = new Initiater();
        Responder responder = new Responder();
        initiater.addListener(responder);
        initiater.sayHello();
        
    }
}
