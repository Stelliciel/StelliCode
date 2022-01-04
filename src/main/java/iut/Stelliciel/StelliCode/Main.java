package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 *
 * penser à mettre sa console en utf-8 (windows : chcp 65001)
 */
public class Main {
    private final Interpreteur metier;
    private final CUI ihm;

    public Main() {
        AnsiConsole.systemInstall();
        try {
            new FileOutputStream("test.algo");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        metier = new Interpreteur(this, "../resources/main/Code.algo");
        ihm    = new CUI(this);

        ihm.afficher();
    }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public static void main(String[] args) {
        new Main();
    }
}
