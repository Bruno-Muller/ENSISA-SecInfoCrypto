/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.user;

import java.io.File;
import java.util.ArrayList;
import secinfocrypto.signature.Keys;
import secinfocrypto.signature.SignatureFile;

/**
 *
 * @author bruno
 */
public class User {
    
    private String login;
    private ArrayList<SignatureFile> files;
    private Keys keys;
    
    User() {
        this.files = new ArrayList<SignatureFile>();
         
        for (int i = 0; i < 4; i++) {
            this.files.add(new SignatureFile(new File("/Users/bruno/Desktop/Programmation_Android.pdf")));
        }
        
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public Keys getKeys() {
        return this.keys;
    }
    
    public ArrayList<SignatureFile> getFiles() {
        return this.files;
    }
}
