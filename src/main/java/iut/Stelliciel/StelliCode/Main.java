package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * penser à mettre sa console en utf-8 (windows : chcp 65001)
 */
public class Main {
    private final Interpreteur metier;
    private final CUI ihm;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;
    private LectureCouleur lectureCouleur;

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        System.out.println("Donnez le chemin absolue de votre fichier .algo");
        //String adresse = "../resources/main/Code.algo";
        String adresse = "../../src/main/resources/Code.algo";
        metier         = new Interpreteur(this, adresse);
        lectureCouleur = new LectureCouleur();
        ihm            = new CUI(this,lectureCouleur);
        ihm.afficher();
        String sUser = "-1";
        while (sUser != "q"){
            ihm.proposeChoix();
            ihm.afficher();
        }

    }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }
    public HashMap<String, Variable<Object>> getConstantes() { return metier.getLstConstantes(); }
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public static String saisie()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void main(String[] args) {
        new Main();
    }
}
