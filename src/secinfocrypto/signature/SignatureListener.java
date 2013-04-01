/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.signature;

/**
 *
 * @author bruno
 */
public interface SignatureListener<Result> {
    
    public void setProgress(int i);
    
    public void setProcessInformation(String string);
    
    public void setResult(Result result);
    
}
