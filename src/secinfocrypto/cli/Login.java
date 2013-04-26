/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.user.DataBase;
import secinfocrypto.user.User;

/**
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class Login {

    String result = new String();

    public boolean checkUser(String login, String password) {
        result = "?";
        ArrayList<User> users;
        try {
            users = DataBase.getInstance().getUsers();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                    result = "OK";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }

        if (this.result.equals("OK")) {
            return true;
        } else {
            return false;
        }
    }
}
