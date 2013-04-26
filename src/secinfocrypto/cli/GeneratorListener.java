/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.util.logging.Level;
import java.util.logging.Logger;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.signature.SignatureListener;
import secinfocrypto.user.DataBase;

/**
 *
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class GeneratorListener implements SignatureListener<byte[]> {

    SignatureFile file;

    GeneratorListener(SignatureFile file) {
        this.file = file;
    }

    @Override
    public void processStart() {
    }

    @Override
    public void processEnd() {
        try {
            DataBase.getInstance().save();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            Logger.getLogger(GeneratorListener.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void setProgress(int i) {
    }

    @Override
    public void setProcessInformation(String string) {
    }

    @Override
    public void setResult(byte[] result) {
        System.out.println(this.file.getFile().getName() + ": Signature generated.");
    }

    @Override
    public void exception(Exception e) {
        System.out.print(e.getMessage());
        Logger.getLogger(GeneratorListener.class.getName()).log(Level.SEVERE, null, e);
    }
}
