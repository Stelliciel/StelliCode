package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.CUI.affichage.AfficheCode;
import iut.Stelliciel.StelliCode.CUI.console.AfficheConsole;
import iut.Stelliciel.StelliCode.CUI.tabVariable.AfficheTab;
import iut.Stelliciel.StelliCode.Main;

import java.io.IOException;
import java.util.Locale;

public class CUI {
    private final Main controlleur;
    private final AfficheTab affTabVar;
    private final AfficheConsole affConsole;
    private final AfficheCode affCode;

    int numLig1;

    public CUI(Main controlleur){
        this.controlleur = controlleur;
        this.affTabVar   = new AfficheTab();
        this.affConsole  = new AfficheConsole();
        this.affCode     = new AfficheCode(controlleur.getCode(),controlleur.getNbChiffre());
        this.numLig1     = 0;
    }

    public void scroll(int num){
        this.numLig1 += num;
    }

    public void afficher(){
        System.out.println("________________________________________________________________________________");
        for (int i = this.numLig1; i < this.numLig1+40; i++) {
            if ( i < controlleur.getCode().size() )
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
        System.out.println("| "+ this.affTabVar.affLig(numLig) + " |" + CUI.corrigeCharSpe(String.format(Locale.US,"%-70s |",this.affCode.affLig(numLig))));
    }

    private static String corrigeCharSpe(String in){
        StringBuilder add = new StringBuilder();
        int cpt;
        cpt = 0;
        for (char c:in.toCharArray()){
            if ((int) c > 127) {
                cpt++;
                if (cpt % 2 == 0) {
                    add.append(" ");
                }
            }
        }
        return in + add;
    }
}
