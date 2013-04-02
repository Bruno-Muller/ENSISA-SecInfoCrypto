/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.user;

import java.util.ArrayList;
import secinfocrypto.signature.SignatureFile;

/**
 *
 * @author bruno
 */
public class DataBase {
    
    public User getUser(String login, String password) throws Exception {
        
        // TODO Login existe pas
        if (!"login".equals(login))
            throw new Exception("Wrong login");
        
        // TODO Mauvais mot de passe
        if (!"password".equals(password))
            throw new Exception("Wrong password");
        
        // TODO récupérer user dans BDD
        User user = new User();
        user.setLogin(login);
        
        
        ArrayList<SignatureFile> files = user.getFiles();
        
        return user;
    }
    
}
