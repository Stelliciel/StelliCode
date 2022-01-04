package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;

public class Interpreteur {

    private ArrayList<String> fichier;
    private final Main ctrl;
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;

    public Interpreteur(Main ctrl, String adresseFichier) {
        this.ctrl    = ctrl;
        this.fichier = Interpreteur.lireFichier(adresseFichier);
        lstConstantes = new HashMap<>();
        lstVariables  = new HashMap<>();
    }

    /*-----------------------*/
    /* Gestion des variables */
    public HashMap<String, Variable<Object>> getLstConstantes() { return lstConstantes; }
    public HashMap<String, Variable<Object>> getLstVariables()  { return lstVariables;  }
    public ArrayList<String>                 getCode()          { return this.fichier;  }
    public int                               getNbChiffre()     { return (this.fichier.size()+"").length(); }

    public void addConstante(String nom, String type, String valeur){ lstConstantes.put(nom, get(nom, type, valeur) ); }
    public void addVariable(String nom, String type, String valeur) { lstVariables.put(nom, get(nom, type, valeur) );  }
    public void addTableau(boolean constante, String nom, String type, String taille) {
        if ( constante )
            lstConstantes.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
        else
            lstVariables.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
    }

    public Object getConstante(String nom) { return  lstConstantes.get(nom).getVal(); }
    public Object getVariable (String nom) { return  lstVariables .get(nom).getVal(); }
    public Object getIndTableau(String nom, int ind) {
        if( estConstante(nom) )
            return lstConstantes.get(nom).getIndTab(ind);
        else
            return lstVariables.get(nom).getIndTab(ind);
    }

    public void setVariable(String nom, String valeur) { Interpreteur.set(lstVariables.get(nom), valeur);  }
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
            case "character" -> { return valeur.charAt(0);             }
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

            case "character" -> { char val = valeur.charAt(0);
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

            case "character" -> {
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

        System.out.println("Creation variable : " + var);
        return var;
    }
    /* Fin gestion des variables */
    /*---------------------------*/


    public static ArrayList<String> lireFichier(String adresse) {
        ArrayList<String> fichier = new ArrayList<>();
        try{
            Scanner sc = new Scanner ( new FileInputStream ( adresse ) );

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
        Interpreteur i = new Interpreteur(null, "C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo");

        i.addConstante("TAILLE", "entier", "10");
        i.addTableau(false, "tabInt", "entier", String.valueOf(i.getConstante("TAILLE")));

        i.setTableau("tabInt",0,"69");

        System.out.println( i.getIndTableau("tabInt",0) );
        System.out.println( i.getIndTableau("tabInt",1) );
    }


}
