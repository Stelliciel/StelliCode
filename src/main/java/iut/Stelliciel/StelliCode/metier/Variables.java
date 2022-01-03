package iut.Stelliciel.StelliCode.metier;

import java.util.HashMap;

public class Variables {
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;

    public Variables() {
        lstConstantes = new HashMap<>();
        lstVariables  = new HashMap<>();
    }

    public HashMap<String, Variable<Object>> getLstConstantes() {
        return lstConstantes;
    }



    public HashMap<String, Variable<Object>> getLstVariables() {
        return lstVariables;
    }

    public void ajout(String nom, String type, String valeur){
        //Vérifie selon le nom de la Variable<>(majuscule ou pas) si c'est une constante
        if ( estConstante(nom) )
        {
            lstConstantes.put(nom, getVariable(nom, type, valeur) );
        }
        else
        {
            lstVariables.put(nom, getVariable(nom, type, valeur) );
        }

    }

    public void set(String nom, String valeur){
        if ( estConstante(nom) )
        {
            setVariable(lstConstantes.get(nom), valeur);
        }
        else{
            setVariable(lstVariables.get(nom), valeur);
        }
    }

    public void setVariable (Variable<Object> var, String valeur)
    {
        switch (var.getType()){
            case "entier"    -> {
                int val = Integer.parseInt(valeur);
                var.setVal(val);
            }

            case "character" -> {
                char val = valeur.charAt(0);
                var.setVal(val);
            }

            case "chaine"    -> var.setVal(valeur);

            case "boolean"   -> {
                boolean val = Boolean.parseBoolean(valeur);
                var.setVal(val);
            }

            case "reel"      -> {
                double val = Double.parseDouble(valeur);
                var.setVal(val);
            }
        }
    }


    public Object get(String nom) {
        if( estConstante(nom) )
            return  lstConstantes.get(nom).getVal();
        else
            return  lstVariables.get(nom).getVal();
    }

    private Variable<Object> getVariable(String nom, String type, String valeur){
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

        HashMap<String, Variable<Object>> lst = var.getLstVariables();

        lst.forEach((s, Variable) -> System.out.println("Nom:" + s +";"+Variable));

        System.out.println(var.get("condition"));

        var.set("condition","false");

        System.out.println(var.get("condition"));




    }


}
