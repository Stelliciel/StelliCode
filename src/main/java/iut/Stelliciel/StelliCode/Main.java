package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.metier.Interpreteur;

public class Main {
    private Interpreteur metier;
    private CUI          ihm;

    public Main() {
        metier = new Interpreteur("");
        ihm    = new CUI();
    }
}
