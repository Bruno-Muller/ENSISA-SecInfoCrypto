/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class ListFile {

    public void show(String person) {
        try {
            ArrayList<User> users = DataBase.getInstance().getUsers();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getLogin().equals(person)) {
                    ArrayList<SignatureFile> files = user.getFiles();
                    for (Iterator iti = files.iterator(); iti.hasNext();) {
                        SignatureFile file = (SignatureFile) iti.next();
                        System.out.println("---");
                        System.out.println("Path : " + file.getFile());
                        System.out.println("algorithm : " + file.getAlgorithm());
                        System.out.println("Last checked date : " + file.getLastCheckedDate());
                        System.out.println("Last signed date : " + file.getLastSignedDate());
                        System.out.println("Last test result : " + file.getLastTestResult());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(ListFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
