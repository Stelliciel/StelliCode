package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * penser à mettre sa console en utf-8 (windows : chcp 65001)
 */
public class Main {
    private final Interpreteur metier;
    private final CUI ihm;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        //ihm.majConsole();
        System.out.println("Donnez le chemin absolue de votre fichier .algo");
        String adresse = "../resources/main/Code.algo";
        metier = new Interpreteur(this, "../resources/main/Code.algo");
        ihm    = new CUI(this);
        ihm.afficher();
        //proposeChoix()
        //point d'arret +/-/go bk (x/x/)
        //quitter       q
        //pas a pas     entrée
        //pas arriere   b
        //ligne précise Lx
        //stop boucle itteration l-x
        //detail        det var Nom
        //    copie det    pp
        //    quiter       entrée
        //trace

        /*
        ihm.demander.doc
        new interpretteur()
        new ihm()

        si demander.doc
        new interpretteur()
        new ihm()

        */
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

    public static void main(String[] args) {
        new Main();
    }
}
