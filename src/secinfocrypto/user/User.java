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
    private String password;
    private ArrayList<SignatureFile> files;
    private Keys keys;
    
    public User() {
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public void setKeys(Keys keys) {
        this.keys = keys;
    }
    
    public Keys getKeys() {
        return this.keys;
    }
    
    public ArrayList<SignatureFile> getFiles() {
        return this.files;
    }
    
    public void setFiles(ArrayList<SignatureFile> files) {
        this.files = files;
    }
}
