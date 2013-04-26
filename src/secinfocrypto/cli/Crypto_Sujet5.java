/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.cli;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import secinfocrypto.signature.SignatureAlgorithm;
import secinfocrypto.user.DataBase;

/**
 * @author CHEVALIER Simon 2A-IR ENSISA <simon.cvr@gmail.com>
 */
public class Crypto_Sujet5 {

    String user = new String();
    File XmlFile = new File("src\\crypto_sujet5\\user.xml");

    public static void main(String[] args) {
        try {
            System.out.println("Cryptographie sujet 5 | Simon CHEVALIER & Bruno MULLER | ENSISA 2A IR 2012-2013");
            System.out.println("-------------------------------------------------------------------------------\n\n");
            Crypto_Sujet5 crypto = new Crypto_Sujet5();
            crypto.login();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Crypto_Sujet5.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void loadMenu() throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException {
        String menu = "GO";
        while (menu.equals("GO")) {
            System.out.println("~~~~ Menu ~~~~");
            System.out.println("1 : Show the list");
            System.out.println("2 : Add a file to the list");
            System.out.println("3 : Remove a file from the list");
            System.out.println("4 : Check file(s)");
            System.out.println("5 : Change a file's signature");
            System.out.println("6 : Sign file(s)");
            System.out.println("7 : Add a new user");
            System.out.println("8 : Exit");

            System.out.print("> Select your choice (number) :");
            Scanner scMenu = new Scanner(System.in);
            int choice = scMenu.nextInt();

            switch (choice) {
                case 1:
                    ListFile file = new ListFile();
                    file.show(this.user);
                    break;

                case 2:
                    AddFile addfile = new AddFile();
                    System.out.println("*** Add a file ***");
                    System.out.print("> Enter the path of your file :");
                    Scanner scadd = new Scanner(System.in);
                    String pathAdd = scadd.nextLine();
                    addfile.addFile(this.user, pathAdd);
                    break;

                case 3:
                    RemoveFile removefile = new RemoveFile();
                    System.out.println("*** Remove a file ***");
                    System.out.print("> Enter the path of your file :");
                    Scanner scRm = new Scanner(System.in);
                    String pathRm = scRm.nextLine();
                    removefile.removeFile(this.user, pathRm);
                    break;

                case 4:
                    CheckFile checkfile = new CheckFile();
                    System.out.println("*** Check file(s) ***");
                    System.out.print("> Enter the number of file(s) you want to check :");
                    Scanner scCheck = new Scanner(System.in);
                    int number = scCheck.nextInt();
                    String[] paths = new String[number];
                    paths[0] = scCheck.nextLine();
                    int i = 1;
                    while (i <= number) {
                        System.out.print("> Enter the path of the file number " + i + " :");
                        paths[i - 1] = scCheck.nextLine();
                        i++;
                    }

                    checkfile.checkFile(this.user, number, paths);
                    break;

                case 5:
                    ChangeSignature changesignature = new ChangeSignature();
                    System.out.println("*** Change a file's signature ***");
                    System.out.print("> Enter the path of your file :");
                    Scanner scChange = new Scanner(System.in);
                    String pathChange = scChange.nextLine();
                    System.out.print("> Enter the type of signature :");
                    String newSign = scChange.nextLine();
                    changesignature.changeSignature(this.user, pathChange, newSign);
                    break;

                case 6:
                    SignFile signfile = new SignFile();
                    System.out.println("*** Sign a file ***");
                    System.out.print("> Enter the path of your file :");
                    Scanner scSign = new Scanner(System.in);
                    String pathSign = scSign.nextLine();
                    System.out.print("Algorithms available : ");
                    for (int ii = 0; ii < SignatureAlgorithm.ALGORITHMS.length; ii++) {
                        System.out.println(ii + ": " + SignatureAlgorithm.ALGORITHMS[ii]);
                    }
                    System.out.print("> Enter the type of signature :");
                    String Sign = scSign.nextLine();
                    signfile.signFile(this.user, pathSign, Sign);
                    break;

                case 7:
                    NewUser adding = new NewUser();
                    System.out.println("*** Add a new user ***");
                    System.out.print("> Enter the login :");
                    Scanner addUser = new Scanner(System.in);
                    String login = addUser.nextLine();
                    System.out.print("> Enter the password :");

                    String password = addUser.nextLine();
                    adding.addUser(login, password);
                    break;

                case 8:
                    System.out.println("bye bye :-)");
                    menu = "out";
                    break;


                default:
                    System.out.println("Error, try again");
                    break;
            }
        }

    }

    private void login() throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException {
        Login login = new Login();
        System.out.println("Identification :");
        System.out.print("> Login :");
        Scanner sc = new Scanner(System.in);
        String user = sc.nextLine();

        System.out.print("> Password :");
        String password = sc.nextLine();
        String mdpCrypte = DataBase.hashPassword(password);

        if (login.checkUser(user, mdpCrypte)) {
            this.user = user;
            System.out.println("----------------------------------------");
            System.out.println("Welcome " + user + ", you are connected.");
            loadMenu();
        } else {
            System.out.println("Unknow or error, end of the program.");
        }
    }
}
