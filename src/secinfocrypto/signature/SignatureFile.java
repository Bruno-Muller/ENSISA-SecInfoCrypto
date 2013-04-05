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
    private boolean lastTestResult;
    
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
    
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Date getLastCheckedDate() {
        return lastChecked;
    }
    
    public void setLastTestResult(boolean result) {
        this.lastTestResult = result;
        this.lastChecked = new Date();
    }
    
    public boolean getLastTestResult() {
        return this.lastTestResult;
    }
}
