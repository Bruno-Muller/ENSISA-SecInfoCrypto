/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import secinfocrypto.signature.Keys;
import secinfocrypto.signature.SignatureFile;

/**
 *
 * @author bruno
 */
public class DataBase {

    private static final String XML_PATH = "users.xml";
    private static DataBase self;
    private ArrayList<User> users;
    private Semaphore mutex;

    private DataBase() {
        this.mutex = new Semaphore(1);
        this.users = new ArrayList<User>();
    }

    public static DataBase getInstance() throws SAXException, ParserConfigurationException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (DataBase.self == null) {
            DataBase.self = new DataBase();
            self.readXML();
        }
        return DataBase.self;
    }

    public User getUser(String login, String password) throws Exception {
                
        // On cherche le login
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();

            if (user.getLogin().equals(login)) {
                // On a trouvé le login
                // On compare le mot de passe
                if (user.getPassword().equals(password)) {
                    // On a trouvé le bon user
                    return user;
                } else {
                    // Le mot de passe est faux
                    throw new Exception("Wrong password");
                }
            }
        }
        // On a pas trouvé de user
        throw new Exception("Wrong login");
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    private void readXML() throws SAXException, ParserConfigurationException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            this.mutex.acquire();

            // On parse le fichier une seule fois
            File userFile = new File(XML_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(userFile);
            doc.getDocumentElement().normalize();

            // On récupère la liste des users
            NodeList usersList = doc.getElementsByTagName("user");
            for (int temp = 0; temp < usersList.getLength(); temp++) {

                Node userNode = usersList.item(temp);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    // On crée le user et initialise les données
                    User user = new User();
                    user.setLogin(getTagValue("login", userElement));
                    user.setPassword(getTagValue("password", userElement));

                    // On reconstruit la clé public  
                    byte[] publicKey = getBytes("public_key", userElement);

                    // On reconstruit la clé privée   
                    byte[] privateKey = getBytes("private_key", userElement);
                    Keys keys = new Keys();
                    keys.construction(publicKey, privateKey);

                    user.setKeys(keys);


                    // On récupère la liste des fichiers du user
                    ArrayList<SignatureFile> files = new ArrayList<SignatureFile>();
                    NodeList filesList = userElement.getElementsByTagName("file");
                    for (int temp2 = 0; temp2 < filesList.getLength(); temp2++) {

                        Node fileNode = filesList.item(temp2);
                        if (fileNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element fileElement = (Element) fileNode;
                            SignatureFile file = new SignatureFile(new File(getTagValue("path", fileElement)));
                            
                            String tmp = getTagValue("algorithm", fileElement);
                            if (tmp != null) file.setAlgorithm(tmp);
                            
                            tmp = getTagValue("last_check_result", fileElement);
                            if (tmp != null) file.setTestResult(Boolean.parseBoolean(tmp));
                            
                            
                            tmp = getTagValue("last_check_date", fileElement);
                            if (tmp != null) file.setLastCheckedDate(new Date(Long.parseLong(tmp)));
                            
                            tmp = getTagValue("signature_date", fileElement);
                            if (tmp != null)  file.setLastSignedDate(new Date(Long.parseLong(tmp)));
                            
                            file.setSignature(getBytes("signature", fileElement));
                            files.add(file);
                        }
                    }

                    // On a tous les fichiers
                    user.setFiles(files);

                    // On ajoute le user à la liste
                    this.users.add(user);
                }
            }

            this.mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() throws IOException {
        try {
            this.mutex.acquire();

            // On enregistre le fichier
            FileWriter fw = new FileWriter(XML_PATH, false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("<?xml version=\"1.0\"?>");
            bw.newLine();
            bw.append("<users>");


            for (int i = 0; i < this.users.size(); i++) {
                writeUser(bw, this.users.get(i));
            }


            bw.newLine();
            bw.append("</users>");

            bw.flush();
            bw.close();

            this.mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void writeUser(BufferedWriter bw, User user) throws IOException {
        bw.newLine();
        bw.append("<user>");
       
        bw.newLine();
        bw.append("<login>");
        bw.append(user.getLogin());
        bw.append("</login>");
        
        bw.newLine();
        bw.append("<password>");
        bw.append(user.getPassword());
        bw.append("</password>");
        
        bw.newLine();
        bw.append("<public_key>");
        bw.newLine();
        writeBytes(bw, user.getKeys().getPublicKey().getEncoded());
        bw.newLine();
        bw.append("</public_key>");
        
        bw.newLine();
        bw.append("<private_key>");
        bw.newLine();
        writeBytes(bw, user.getKeys().getPrivateKey().getEncoded());
        bw.newLine();
        bw.append("</private_key>");
        
        bw.newLine();
        bw.append("<files>");
        
        for (int i=0; i<user.getFiles().size(); i++) {
            writeFile(bw,user.getFiles().get(i));
        }
        
        bw.newLine();
        bw.append("</files>");

        bw.newLine();
        bw.append("</user>");
    }

    private static void writeFile(BufferedWriter bw, SignatureFile sf) throws IOException {
        bw.newLine();
        bw.append("<file>");
            
        bw.newLine();
        bw.append("<path>");
        bw.append(sf.getFile().getAbsolutePath());
        bw.append("</path>");
        
        bw.newLine();
        bw.append("<algorithm>");
        if (sf.getAlgorithm() != null)
            bw.append(sf.getAlgorithm());
        bw.append("</algorithm>");
        
        bw.newLine();
        bw.append("<last_check_date>");
        if (sf.getLastCheckedDate() != null)
            bw.append(String.valueOf(sf.getLastCheckedDate().getTime()));
        bw.append("</last_check_date>");
        
        bw.newLine();
        bw.append("<last_check_result>");
        
        bw.append(String.valueOf(sf.getLastTestResult()));
        bw.append("</last_check_result>");
        
        bw.newLine();
        bw.append("<signature_date>");
        if (sf.getLastSignedDate() != null)
            bw.append(String.valueOf(sf.getLastSignedDate().getTime()));
        bw.append("</signature_date>");
        
        bw.newLine();
        bw.append("<signature>");
        if (sf.getSignature() != null)
            writeBytes(bw, sf.getSignature());
        bw.newLine();
        bw.append("</signature>");
        
        
        bw.newLine();
        bw.append("</file>");
    }

    private static void writeBytes(BufferedWriter bw, byte[] tab) throws IOException {
        
        for (int i=0; i<tab.length; i++) {
            bw.append("<b>");
            bw.append(String.valueOf(tab[i]));
            bw.append("</b>");
        }
    }

    private static byte[] getBytes(String sTag, Element eElement) {
        byte[] tab = null;

        Node node = eElement.getElementsByTagName(sTag).item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList nodeList = ((Element) node).getElementsByTagName("b");
            tab = new byte[nodeList.getLength()];
            for (int i = 0; i < nodeList.getLength(); i++) {
                tab[i] = Byte.parseByte(nodeList.item(i).getChildNodes().item(0).getNodeValue());
            }
        }
        return tab;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue != null)
            return nValue.getNodeValue();
        return null;
    }
}
