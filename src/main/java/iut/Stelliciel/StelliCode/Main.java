package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Interpreteur;

import java.util.ArrayList;

public class Main {
    private final Interpreteur metier;
    private final CUI ihm;

    public Main() {
        metier = new Interpreteur(this, "C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo");
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
