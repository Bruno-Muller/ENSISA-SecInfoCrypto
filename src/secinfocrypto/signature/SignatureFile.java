/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.signature;

import java.io.File;
import java.util.Date;

/**
 *
 * @author bruno
 */
public class SignatureFile {
    
    private File file;
    private byte[] signature;
    private String algorithm;
    private Date lastChecked;
    
    public SignatureFile(File file) {
        this.file = file;
    }

    public void setFile(File file) {
        this.file = file;
        // TODO algorithm,signature null ?
    }
    
    public File getFile() {
        return file;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public byte[] getSignature() {
        return signature;
    }
    
    public void setSignature(String algorithm, byte[] signature) {
        this.algorithm = algorithm;
        this.signature = signature;
        this.lastChecked = new Date();
    }

    public Date getLastChecked() {
        return lastChecked;
    }
}
