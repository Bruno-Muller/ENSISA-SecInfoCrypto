/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.signature.Keys;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 *
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class NewUser {

    public void addUser(String login, String password) {
        ArrayList<User> users;
        try {
            users = DataBase.getInstance().getUsers();

            User user = new User();
            user.setLogin(login);
            user.setPassword(DataBase.hashPassword(password));
            Keys keys = new Keys();
            keys.generation();
            user.setKeys(keys);
            user.setFiles(new ArrayList<SignatureFile>());

            users.add(user);

            DataBase.getInstance().save();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
