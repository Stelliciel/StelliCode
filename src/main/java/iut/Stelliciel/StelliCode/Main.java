package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.Console;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author "Stelliciel"
 * @version 1.0.0
 */
public class Main {
    private Interpreteur metier;
    private static Main instance;

    /**
     * Main inititialse tout l'interpreteur de Pseudo-Code.
     * active les couleur pour l'entierté du programme
     */
    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        String saisie = "";

        while (true) {
            metier  = new Interpreteur(Console.getAdresse());
            new Console();
        }
    }

    /**
     * Récupère le parcours de metier.
     * @return Parcours
     * @see Interpreteur#getParcours()
     */
    public Parcours getParcours(){ return metier.getParcours(); }

    /**
     * Récupère le "this" de la classe Main.<br>
     * Permet d'être appelé depuis n'importe qu'elle classe,
     * évite d'envoyer en paramètre le Main.
     * @return instance
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Récupère le fichier .algo ligne par ligne.
     * @return ArrayList&#60;String&#62; de getCode()
     * @see Interpreteur#getCode()
     */
    public ArrayList<String> getCode(){
        return metier.getCode();
    }

    /**
     * Récupère toute les variables du programme
     * @return HashMap&#60;String, Variable&#60;Objet&#62;&#62;
     * @see Interpreteur#getLstVariables()
     */
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }

    /**
     * Permet de récupérer la longueur du nombre de ligne.<br>
     * Exemple :
     * <ul>
     *     <li>  9 lignes retourne 1</li>
     *     <li> 15 lignes retourne 2</li>
     *     <li>238 lignes retourne 3</li>
     * </ul>
     * @return int de getNbChiffre()
     * @see Interpreteur#getNbChiffre()
     */
    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    /**
     * Retourne la ligne saisie par l'utilisateur
     * @return String
     */
    public String saisie() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Permet d'affecter les variable lors de l'instruction lire()
     * @param e Ligne en cours de lecture
     * @param saisie saisie de l'utilisateur
     * @see Interpreteur#rajoutLecture(EtatLigne, String)
     */
    public void rajoutLecture(EtatLigne e, String saisie) {
        metier.rajoutLecture(e, saisie);
    }

    /**
     * Permet de créer le fichier .var<br>
     * Contient la trace des affectation de variable suivie grace à la commande ADDVAR
     * @param lstVar continent la liste du nom des variable
     */
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

    /**
     * Format la ligne pour affichage dans le .var
     * @param nom de la variable
     * @param valeur de la variable
     * @param numLigne de la variable
     * @return String
     */
    private String traceVar( String nom, String valeur, String numLigne ){
        return "|" + String.format("%-15s", nom) + "|"
                         + String.format("%-15s", valeur)+ "|" + String.format("%-12s", numLigne) + "|";
    }

    /**
     * Le main ne require aucun argument lors de l'execution
     * @param args aucun argument
     */
    public static void main(String[] args) {
        new Main();
    }
}

