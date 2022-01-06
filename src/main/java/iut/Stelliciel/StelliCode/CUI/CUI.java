package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.CUI.affichage.AfficheCode;
import iut.Stelliciel.StelliCode.CUI.console.AfficheConsole;
import iut.Stelliciel.StelliCode.CUI.tabVariable.AfficheTab;
import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.Ansi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import  java.lang.ProcessBuilder;
import  java.lang.Process;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CUI {
    private final Main controleur;
    private final AfficheTab affTabVar;
    private final AfficheConsole affConsole;
    private final AfficheCode affCode;

    int numLig1;
    private int ligEnCour;

    public CUI(Main controleur){
        this.controleur = controleur;
        this.affTabVar   = new AfficheTab();
        this.affConsole  = new AfficheConsole();
        this.affCode     = new AfficheCode(controleur.getCode(),controleur.getNbChiffre());
        this.numLig1     = 0;
        this.ligEnCour   = 0;
    }

    public static String adaptTxt(String in){
        if(in.length()<10){return  in;}
        else{
            return in.substring(0,4)+".."+in.substring(in.length()-3,in.length()-1);
        }
    }

    public void demandeVars(){
        this.afficher();
        System.out.println("Quelles variables voulez vous suivre?");
        HashMap<String ,Variable<Object>> lstVar = controleur.getVariables();
        StringBuilder sRep = new StringBuilder();
        int numVar = 1;
        for(String nom : lstVar.keySet()){
            if(numVar % 5 == 1 ){
                sRep.append('\n');}
            sRep.append(numVar).append(" ").append(CUI.adaptTxt(nom)).append("  ");
            numVar ++;
        }
        System.out.println(sRep);
    }

    public void nextLigne(){
        this.ligEnCour++;
    }

    public void scroll(int num){
        this.numLig1 += num;
    }

    public void afficher(){
        StringBuilder affichage = new StringBuilder();
        affichage.append("________________________________________________________________________________________________________________\n");
        for (int i = this.numLig1; i < this.numLig1+40; i++) {
            if ( i < controleur.getCode().size() )
                affichage.append(this.affLig(i, this.ligEnCour));
        }
        affichage.append("_______________________________________________________________________________________________________________|\n                                                                                                                \nconsole                                                                                                         \n________________________________________________________________________________________________________________\n");
        affichage.append(this.affConsole);
        this.majConsole();
        System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a(affichage.toString()).reset());
    }

    public void proposeChoix(){
        String inUser = Main.saisie();

        if(inUser.equals("m")) {
            if (ligEnCour != affCode.getTaillePro() - 1) {
                //controleur.prochaineLig();
                this.ligEnCour++;
            }
        }else if(inUser.equals("b")) {
            if(ligEnCour != 0){
                //controleur.LignePre();
                this.ligEnCour--;}
        }else if (inUser.substring(4).equals("+ bk")) {
            if (inUser.substring(5).matches("\\D+") || Integer.parseInt(inUser.substring(5)) > affCode.getTaillePro()) {
                System.out.println("entrer un nombre inférieur au nombre de ligne");}
            else{
                    //addBK(Integer.parseInt(inUser.substring(5)));
                    // controleur.addBK(Integer.parseInt(inUser.substring(5)));
            }
        }

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
    }

    public void majConsole(){
        try{
            String operatingSystem = System.getProperty("os.name").toLowerCase();

            if(operatingSystem.contains("win")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private String affLig(int numLig, int ligEncour){
        String espace = " ";
        return ("| "+ this.affTabVar.affLig(numLig) + " |" + (this.affCode.affLig(numLig,ligEncour)))+espace.repeat(80-this.affCode.getTaille(numLig))+"|\n";
    }
}
