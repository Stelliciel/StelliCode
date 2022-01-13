package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.Console;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private Interpreteur metier;
    private Console      console;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        String saisie = "";

        while (true) {
            this.metier  = new Interpreteur(Console.afficherOption());
            this.console = new Console(this);
        }
    }

    public static Main getInstance() {
        return instance;
    }


    public Parcours getParcours(){ return  metier.getParcours(); }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }
    public HashMap<String, Variable<Object>> getConstantes() { return metier.getLstConstantes(); }
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public void rajoutLecture(EtatLigne e, String saisie) {
        metier.rajoutLecture(e, saisie);
    }

    public String saisie() {
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }

    /* Créer le fichier .var */
    public void traceVariable(ArrayList<String> lstVar) {
        HashMap<String, Variable<Object>> lst = new HashMap<>();
        ArrayList<EtatLigne> lecteur = getParcours().getLecteur();
        HashMap<String, Variable<Object>> lstTmp = lecteur.get(0).getLstVariables();

        for(String nom : lstVar ){
            if ( !lstTmp.get(nom).estTableau() )
                lst.put( nom, lstTmp.get(nom) );
        }

        try
        {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream("../../src/main/resources/"+metier.getSignature() + ".var"), "UTF8" ));

            pw.println( "|" + String.format("%-15s", "Nom")          + "|"
                            + String.format("%-15s", "Valeur")       + "|"
                            + String.format("%-12s", "Numero Ligne") + "|"    );


            for (int cpt = 1; cpt < lecteur.size(); cpt++ ){
                lstTmp = lecteur.get(cpt).getLstVariables();

                int numLigne = lecteur.get(cpt).getNumLigne()+1;

                for(String nom : lstTmp.keySet()){
                    if (lst.containsKey(nom)){
                        String valeur = lstTmp.get(nom).getVal()+"";
                        String valActuel = lst.get(nom).getVal() +"";
                        if ( !valActuel.equals(valeur) ) {
                            lst.replace(nom, lstTmp.get(nom));
                            pw.println( traceVar(nom, valeur, numLigne+"")  );
                        }

                    }
                }
            }
            pw.close();
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    private String traceVar( String nom, String valeur, String numLigne ){
        String trace = "|" + String.format("%-15s", nom) + "|"
                         + String.format("%-15s", valeur)+ "|" + String.format("%-12s", numLigne) + "|";

        return trace;
    }
}

