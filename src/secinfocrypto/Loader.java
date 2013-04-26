/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto;

import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.cli.Crypto_Sujet5;
import secinfocrypto.ui.MainWindow;

/**
 *
 * @author bruno
 */
public abstract class Loader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-nogui")) {
            Crypto_Sujet5.main(args);
        } else {
            Logger.getLogger(Loader.class.getName()).log(Level.FINE, "Graphical Interface");
            MainWindow.main(args);
        }

    }
}
