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
    private int ligEnCour;

    public CUI(Main controlleur){
        this.controlleur = controlleur;
        this.affTabVar   = new AfficheTab();
        this.affConsole  = new AfficheConsole();
        this.affCode     = new AfficheCode(controlleur.getCode(),controlleur.getNbChiffre());
        this.numLig1     = 0;
        this.ligEnCour  = 0;
    }

    public void nextLigne(){
        this.ligEnCour++;
    }

    public void scroll(int num){
        this.numLig1 += num;
    }

    public void afficher(){
        System.out.println("________________________________________________________________________________");
        for (int i = this.numLig1; i < this.numLig1+40; i++) {
            if ( i < controlleur.getCode().size() )
                this.affLig(i,this.ligEnCour);
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

    private void affLig(int numLig,int ligEncour){
        String espace = " ";
        System.out.print(("| "+ this.affTabVar.affLig(numLig) + " |" + CUI.corrigeCharSpe(this.affCode.affLig(numLig,ligEncour))));
        System.out.println(espace.repeat(80-this.affCode.getTaille(numLig))+"|");
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

    private static String corrigeCouleur(String in){
        String add ="";
        int nbCouleur = (in.length() - in.replace("\u001B","").length());
        System.out.println(nbCouleur);
        if(nbCouleur == 1){nbCouleur--;}
        else {nbCouleur = nbCouleur/2;}

        int i =0;
        while(i < nbCouleur){
            add += "";
            i++;
        }
        return in + add;
    }
    /*add += "                 ";
    add += "                    ";*/
}
