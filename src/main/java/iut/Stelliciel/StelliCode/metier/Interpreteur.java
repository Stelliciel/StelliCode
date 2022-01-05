package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;

public class Interpreteur {

    private final ArrayList<String> fichier;
    private final Main ctrl;
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;
    private final Parcours parcours;
    private String signature;

    public Interpreteur(Main ctrl, String adresseFichier) {
        this.ctrl    = ctrl;
        this.fichier = Interpreteur.lireFichier(adresseFichier);
        lstConstantes = new HashMap<>();
        lstVariables  = new HashMap<>();
        parcours      = new Parcours(ctrl);

        System.out.println("/*----------------*/\n/* Iniatilisation */\n/*----------------*/");
        initialisationFichier();

        System.out.println("Signature : " + signature);
        lstConstantes.forEach((k,v) -> {
            System.out.println("Nom de la constante : "+k+"\t || " + v);
        });
        lstVariables.forEach((k,v) -> {
            System.out.println("Nom de la variable  : "+k+"\t || " + v);
        });

        lectureFichier();
    }

    /*--------------------*/
    /* Lecture du fichier */
    private void lectureFichier() {
        int cpt;
        for (cpt=0; !fichier.get(cpt).contains("DEBUT"); cpt++) System.out.println("->"+fichier.get(cpt));

        parcours.nouvelleEtat( nouvelleEtatLigne(cpt) );
    }

    private EtatLigne nouvelleEtatLigne(int numLigne){
        return new EtatLigne(signature, lstConstantes, lstVariables, numLigne);
    }
    /* Fin lecture fichier*/
    /*--------------------*/


    /*------------------------------*/
    /* Initialisation des variables */
    private void initialisationFichier() {
        int cpt =0;
        while ( !fichier.get(cpt).equals("DEBUT") ) {
            String[] mots = fichier.get(cpt).split(" ");

            switch (mots[0]){
                case "ALGORITHME" -> { signature = mots[1]; }
                case "constante:" -> { cpt = ajouterConstante(cpt);}
                case "variable:"  -> { cpt = ajouterVariables(cpt);}
            }

            if( !fichier.get(cpt).equals("DEBUT"))
                cpt++;
        }
    }

    private int ajouterConstante(int cpt) {
        String ligne = fichier.get(++cpt);

        while ( !(ligne.equals("DEBUT") || ligne.equals("variable:"))){
            String[] mots = ligne.replaceAll(" ", "").split("<--");
            String valeur = mots[1];
            String nom    = mots[0];

            addConstante(nom,getType(valeur),valeur);

            ligne = fichier.get(++cpt);
        }

        return cpt-1;
    }
    private int ajouterVariables(int cpt) {
        String ligne = fichier.get(++cpt);

        while ( !ligne.equals("DEBUT") ){
            String[] mots = ligne.split(":");
            String[] variables = mots[0].replaceAll(" ", "").split(",");
            String type = mots[1];

            if ( type.contains("tableau") ){
                String tailleTab = type.substring(type.indexOf('[')+1,type.indexOf(']'));
                String typeTab   = "";
                if (type.contains("entier")    ) typeTab  = "entier";
                if (type.contains("reel")      ) typeTab  = "reel";
                if (type.contains("boolean")   ) typeTab = "boolean";
                if (type.contains("caracteres") ) typeTab = "caractere";
                if (type.contains("chaine")    ) typeTab = "chaine";

                if ( lstConstantes.containsKey(tailleTab) ) {
                    addTableau(false,variables[0],typeTab,String.valueOf(lstConstantes.get(tailleTab).getVal()));
                }
                else {
                    addTableau(false,variables[0],typeTab, tailleTab);
                }
            }
            else {
                for ( String nom : variables ){
                    addVariable(nom, type.replaceAll(" ", ""));
                }
            }

            ligne = fichier.get(cpt++);
        }

        return cpt-1;
    }

    private String getType(String valeur){
        if ( valeur.charAt(0) == '"' && valeur.charAt(valeur.length()-1) == '"') return "chaine";
        if ( valeur.contains(".,")) return "reel";
        if ( valeur.equals("vrai") || valeur.equals("faux") ) return "boolean";
        if ( valeur.matches("^\\d+$")) return "entier";
        if ( valeur.length() == 1 ) return "caractere";

        return "chaine";
    }
    /* Fin Initialisation des variables */
    /*----------------------------------*/

    /*-----------------------*/
    /* Gestion des variables */
    public HashMap<String, Variable<Object>> getLstConstantes() { return lstConstantes; }
    public HashMap<String, Variable<Object>> getLstVariables()  { return lstVariables;  }
    public ArrayList<String>                 getCode()          { return this.fichier;  }
    public int                               getNbChiffre()     { return (this.fichier.size()+"").length(); }

    public void addConstante(String nom, String type, String valeur){ lstConstantes.put(nom, get(nom, type, valeur) ); }
    public void addVariable (String nom, String type)               { lstVariables .put(nom, new Variable<>(nom, type) );  }
    public void addTableau  (boolean constante, String nom, String type, String taille) {
        if ( constante )
            lstConstantes.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
        else
            lstVariables.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
    }

    public Object getConstante (String nom) { return  lstConstantes.get(nom).getVal(); }
    public Object getVariable  (String nom) { return  lstVariables .get(nom).getVal(); }
    public Object getIndTableau(String nom, int ind) {
        if( estConstante(nom) )
            return lstConstantes.get(nom).getIndTab(ind);
        else
            return lstVariables.get(nom).getIndTab(ind);
    }

    public void setVariable(String nom, String valeur)          { Interpreteur.set(lstVariables.get(nom), valeur);  }
    public void setTableau (String nom, int ind, String valeur) {
        Variable<Object> v;
        if ( estConstante(nom) )
            v = lstConstantes.get(nom);
        else
            v = lstVariables.get(nom);

        v.setIndTab(ind, convertitValeur(v.getType(),valeur) );
        System.out.println("Changement ind : " + v.getIndTab(ind) );
    }

    private static Object convertitValeur(String type, String valeur){
        switch (type){
            case "entier"    -> { return Integer.parseInt(valeur);     }
            case "caractere" -> { return valeur.charAt(0);             }
            case "boolean"   -> { return Boolean.parseBoolean(valeur); }
            case "reel"      -> { return Double.parseDouble(valeur);   }
            default          -> { return valeur;                       }
        }
    }
    private static boolean estConstante(String nom){ return nom.equals(nom.toUpperCase()); }

    public static void             set(Variable<Object> var, String valeur)   {
        switch (var.getType()){
            case "entier"    -> { int val = Integer.parseInt(valeur);
                var.setVal(val);
            }

            case "caractere" -> { char val = valeur.charAt(0);
                var.setVal(val);
            }

            case "chaine"    -> var.setVal(valeur);

            case "boolean"   -> { boolean val = Boolean.parseBoolean(valeur);
                var.setVal(val);
            }

            case "reel"      -> { double val = Double.parseDouble(valeur);
                var.setVal(val);
            }
        }
    }
    public static Variable<Object> get(String nom, String type, String valeur){
        Variable<Object> var = null;
        switch (type){
            case "entier"    -> {
                int val = Integer.parseInt(valeur);
                var = new Variable<>(nom,type, val);
            }

            case "caractere" -> {
                char val = valeur.charAt(0);
                var = new Variable<>(nom,type, val);
            }

            case "chaine"    -> var = new Variable<>(nom,type, valeur);

            case "boolean"   -> {
                boolean val = Boolean.parseBoolean(valeur);
                var = new Variable<>(nom,type, val);
            }

            case "reel"      -> {
                double val = Double.parseDouble(valeur);
                var = new Variable<>(nom,type, val);
            }
        }

        return var;
    }
    /* Fin gestion des variables */
    /*---------------------------*/


    public static ArrayList<String> lireFichier(String adresse) {
        ArrayList<String> fichier = new ArrayList<>();
        try{
            Scanner sc = new Scanner(new FileInputStream(adresse), "UTF8");

            char charPrecedent =' ';
            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();
                /*for (char c:ligne.toCharArray()) {
                    if(c == '?' && charPrecedent == '?'){

                    charPrecedent = c;
                    }*/

                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }


    public static void main(String[] args) {

    }
}
