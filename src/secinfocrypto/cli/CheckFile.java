/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.signature.SignatureChecker;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 *
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class CheckFile {

    public void checkFile(String person, int i, String[] paths) {
        try {
            ArrayList<User> users = DataBase.getInstance().getUsers();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getLogin().equals(person)) {
                    ArrayList<SignatureFile> files = user.getFiles();
                    for (Iterator iti = files.iterator(); iti.hasNext();) {
                        SignatureFile file = (SignatureFile) iti.next();
                        for (int j = 0; j < i; j++) {
                            if (file.getFile().getAbsolutePath().equals(paths[j])) {
                                //the file exists, I can test it.
                                SignatureChecker checker = new SignatureChecker();
                                System.out.println(" ");
                                checker.setSignatureListener(new CheckerListener(file));
                                checker.execute(file, user.getKeys().getPublicKey());

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.append(e.getMessage());
            Logger.getLogger(CheckFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
