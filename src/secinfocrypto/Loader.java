/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto;

import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.ui.MainWindow;

/**
 *
 * @author bruno
 */
public class Loader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            if (args.length > 0 && args[0].equals("-nogui")) {
                System.out.println("Command Line Interface");
            }
            else {
                Logger.getLogger(Loader.class.getName()).log(Level.FINE, "Graphical Interface");
                MainWindow.main(args);
            }
                    
    }
}
