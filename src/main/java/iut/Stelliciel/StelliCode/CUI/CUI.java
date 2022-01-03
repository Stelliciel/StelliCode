package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.CUI.affichage.AfficheCode;
import iut.Stelliciel.StelliCode.CUI.console.AfficheConsole;
import iut.Stelliciel.StelliCode.CUI.tabVariable.AfficheTab;
import iut.Stelliciel.StelliCode.Main;

import java.io.IOException;

public class CUI {
    private final Main controlleur;
    private final AfficheTab affTabVar;
    private final AfficheConsole affConsole;
    private final AfficheCode affCode;

    public CUI(Main controlleur){
        this.controlleur = controlleur;
        this.affTabVar = new AfficheTab();
        this.affConsole = new AfficheConsole();
        this.affCode = new AfficheCode();
    }

    public void afficher(int val,int nbLig){
        for (int i = 0; i < nbLig; i++) {
            this.affLig(i);
        }
        System.out.println("________________________________________________________________________________\n\nconsole\n________________________________________________________________________________\n");
        System.out.println(this.affConsole);
    }

    private void majConsole(){
        try {
            Runtime.getRuntime().exec(System.getProperty("os.name").contains("win")?"cls":"clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void affLig(int numLig){
        System.out.println(this.affTabVar.affLig(numLig) +" | "+this.affCode.affLig(numLig));
    }
}
