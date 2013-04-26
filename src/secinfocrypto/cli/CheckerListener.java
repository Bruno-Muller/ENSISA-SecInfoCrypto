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
public class CheckerListener implements SignatureListener<Boolean> {

    SignatureFile file;

    CheckerListener(SignatureFile file) {
        this.file = file;
    }

    @Override
    public void processStart() {
    }

    @Override
    public void processEnd() {
        try {
            DataBase.getInstance().save();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setProgress(int i) {
    }

    @Override
    public void setProcessInformation(String string) {
    }

    @Override
    public void setResult(Boolean result) {
        if (!result) {
            System.out.println(this.file.getFile().getName() + ": The signatures are not the same.");
        }
        else {
            System.out.println(this.file.getFile().getName() + ": The signatures are the same.");
        }
    }

    @Override
    public void exception(Exception e) {
        System.out.print(e.getMessage());
        Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, e);
    }
}
