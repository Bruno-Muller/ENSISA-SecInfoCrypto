/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.signature;

/**
 *
 * @author bruno
 */
public abstract class SignatureAlgorithm<Result> {

    public static final String[] ALGORITHMS = {"NONEwithRSA", "MD2withRSA", "MD5withRSA", "SHA1withRSA", "SHA256withRSA", "SHA384withRSA", "SHA512withRSA", "NONEwithDSA", "SHA1withDSA", "NONEwithECDSA", "SHA1withECDSA", "SHA256withECDSA", "SHA384withECDSA", "SHA512withECDSA"};
    protected SignatureListener signatureListener;
    protected String fileName;
    protected String signatureAlgorithm;

    protected void execute(String fileName, String signatureAlgorithm) {
        this.fileName = fileName;
        this.signatureAlgorithm = signatureAlgorithm;

        new Thread(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                Result result = doInBackground();
                onPostExecute(result);

            }
        }).start();
    }

    public void setSignatureListener(SignatureListener signatureListener) {
        this.signatureListener = signatureListener;
    }

    protected SignatureListener getSignatureListener() {
        return this.signatureListener;
    }

    protected void publishProgress(int i) {
        this.onProgressUpdate(i);
    }

    private void onProgressUpdate(int i) {
        if (this.signatureListener != null) {
            this.signatureListener.setProgress(i);
        }
    }
    
    protected abstract void onPreExecute();
    
    protected abstract Result doInBackground();
    
    protected abstract void onPostExecute(Result result);
}
