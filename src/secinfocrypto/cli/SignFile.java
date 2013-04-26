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
import secinfocrypto.signature.SignatureGenerator;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 *
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class SignFile {

    public void signFile(String person, String path, String sign) {
        try {
            ArrayList<User> users = DataBase.getInstance().getUsers();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getLogin().equals(person)) {
                    ArrayList<SignatureFile> files = user.getFiles();
                    for (Iterator iti = files.iterator(); iti.hasNext();) {
                        SignatureFile file = (SignatureFile) iti.next();
                        if (file.getFile().getAbsolutePath().equals(path)) {
                            SignatureGenerator generator = new SignatureGenerator();
                            generator.setSignatureListener(new GeneratorListener(file));
                            file.setAlgorithm(sign);
                            generator.execute(file, user.getKeys().getPrivateKey());

                            DataBase.getInstance().save();

                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(SignFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
