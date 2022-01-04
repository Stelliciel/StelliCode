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
    public HashMap<String, Variable<Object>> getLstConstantes() {
        return lstConstantes;
    }
    public HashMap<String, Variable<Object>> getLstVariables() {
        return lstVariables;
    }
    public ArrayList<String>                 getCode() {
        return this.fichier;
    }
    public int                               getNbChiffre() {
        return (this.fichier.size()+"").length();
    }

    public void addConstante(String nom, String type, String valeur){ lstConstantes.put(nom, get(nom, type, valeur) ); }
    public void addVariable(String nom, String type, String valeur){ lstVariables.put(nom, get(nom, type, valeur) ); }

    public Object getConstante(String nom) { return  lstConstantes.get(nom).getVal(); }
    public Object getVariable(String nom)  { return  lstVariables.get(nom).getVal();  }

    public void setVariable(String nom, String valeur) { Interpreteur.set(lstVariables.get(nom), valeur);  }

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

            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();
                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }

    public static void main(String[] args) {
        Interpreteur i = new Interpreteur(null, "C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo");
    }


}
