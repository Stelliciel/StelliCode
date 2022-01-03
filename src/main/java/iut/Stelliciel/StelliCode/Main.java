package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.metier.Interpreteur;

import java.util.ArrayList;

public class Main {
    private Interpreteur metier;
    private CUI          ihm;

    public Main() {
        metier = new Interpreteur("");
        ihm    = new CUI();
    }

    public ArrayList<String> getFichier(){
        return metier.getFicher();
    }


    public static void main(String[] args) {
        new Main();
    }
}
