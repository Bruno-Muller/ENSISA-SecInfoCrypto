/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 *
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class AddFile {

    public void addFile(String person, String path) {
        try {
            ArrayList<User> users = DataBase.getInstance().getUsers();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getLogin().equals(person)) {
                    user.getFiles().add(new SignatureFile(new File(path)));
                    DataBase.getInstance().save();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(AddFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
