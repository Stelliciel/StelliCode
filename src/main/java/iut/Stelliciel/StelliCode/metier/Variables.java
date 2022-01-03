package iut.Stelliciel.StelliCode.metier;

import java.util.HashMap;

public class Variables {
    private HashMap<String, Variable> lstConstantes;
    private HashMap<String, Variable> lstVariables;

    public Variables() {
        lstConstantes = new HashMap<>();
        lstVariables  = new HashMap<>();
    }

    public HashMap<String, Variable> getLstConstantes() {
        return lstConstantes;
    }

    public HashMap<String, Variable> getLstVariables() {
        return lstVariables;
    }

    public void ajout(String nom, String type, String valeur){
        //Vérifie selon le nom de la variable(majuscule ou pas) si c'est une constante
        if ( estConstante(nom) )
        {
            lstConstantes.put(nom, getVariable(nom, type, valeur) );
        }
        else
        {
            lstVariables.put(nom, getVariable(nom, type, valeur) );
        }

    }

    public void set(String nom, String type,String valeur){
        if ( estConstante(nom) )
        {
            lstConstantes.replace(nom, getVariable(nom, type, valeur));
        }
        else{
            lstVariables.replace(nom, getVariable(nom, type, valeur));
        }
    }


    public Object get(String nom) {
        if( estConstante(nom) )
            return lstConstantes.get(nom).getVal();
        else
            return  lstVariables.get(nom).getVal();
    }

    private Variable getVariable(String nom, String type, String valeur){
        Variable var = null;
        switch (type){
            case "entier"    : {
                int val = Integer.parseInt(valeur);
                var = new Variable(nom, val);
            };
            case "character" : {
                char val = valeur.charAt(0);
                var = new Variable(nom, val);
            };
            case "chaine"    : var = new Variable(nom, valeur);
            case "boolean"   : {
                boolean val = Boolean.parseBoolean(valeur);
                var = new Variable(nom, val);
            };
            case "reel"      : {
                double val = Double.parseDouble(valeur);
                var = new Variable(nom, val);
            };
        }

        return var;
    }

    private boolean estConstante(String nomV )
    {
        String testConstante = nomV.toUpperCase();

        return nomV.equals(testConstante);
    }


    public static void main(String[] args) {

        Variables var = new Variables();

        var.ajout("TAILLE", "entier", "16");
        System.out.println(var.get("TAILLE"));

        var.ajout("lettre", "character", "R");
        var.ajout("condition", "boolean", "True");

        HashMap<String, Variable> lst = var.getLstVariables();

        lst.forEach((s, variable) -> System.out.println("Nom:" + s +";"+variable));

        System.out.println(var.get("condition"));




    }


}
