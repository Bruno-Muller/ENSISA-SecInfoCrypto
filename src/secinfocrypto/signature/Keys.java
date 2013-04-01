/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.signature;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author bruno
 */
public class Keys {
    
    private PrivateKey priKey;
    private PublicKey pubKey;
    
    public PrivateKey getPrivateKey() {
        return priKey;
    }
    
    public PublicKey getPublicKey() {
        return pubKey;
    }

    public void generation() throws NoSuchAlgorithmException {
        
        // Obtenir une instance de KeyPairGenerator spécialisée pour les paires de clés RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        
        // Initialiser le générateur
        kpg.initialize(2048);
        
        // Générer la paire de clés
        KeyPair kp = kpg.generateKeyPair();

        this.priKey = kp.getPrivate();
        this.pubKey = kp.getPublic();
        
        // Récupérer l'encodage (format X.509) de la clé publique
        byte[] ePubKey = kp.getPublic().getEncoded();
        
        // Récupérer l'encodage (format PKCS#8) de la clé privée
        byte[] ePriKey = kp.getPrivate().getEncoded();
    }
    
    public void construction(byte[] ePubKey, byte[] ePriKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
       
        // Obtenir une instance de KeyFactory spécialisée pour les clés RSA
        KeyFactory kf = KeyFactory.getInstance("RSA");
        
        // Reconstruire la clé publique depuis son encodage X.509: ePubKey
        RSAPublicKey pubKey1 = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(ePubKey));
        
        // Reconstruire la clé privée depuis son encodage PKCS#8 : ePriKey
        RSAPrivateKey priKey1 = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(ePriKey));
    }
}
