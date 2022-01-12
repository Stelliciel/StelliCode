package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.Variable;

import java.util.ArrayList;
import java.util.HashMap;

public class AfficheTab {

    private final HashMap<String, String> listeVar;
    private final Main ctrl;

    /** constructeur d'un tableau de variable vide
     */
    public AfficheTab(Main ctrl){
        this.ctrl = ctrl;
        this.listeVar = new HashMap<String, String>();
    }

    /**verifie si la variable est dans la liste si c'est le cas modifie la valeur
     * @param lstV liste variable
     */
    public void maj(HashMap<String, Variable<Object>> lstV){
        listeVar.forEach( (k,v) -> {
            listeVar.replace(k,v, lstV.get(k).getVal()+"");
        });
    }

    public void rajouterVar(String nom, Variable<Object> v){
        System.out.println("Rajout de " + nom + " "+ v.valToString());
        listeVar.put(nom, ""+v.getVal());
    }

    public ArrayList<String> getLstVar(){
        ArrayList<String> lstVar = new ArrayList<>();

        listeVar.forEach( (k,v) ->lstVar.add(k));
        return lstVar;
    }

    public ArrayList<String> getTabVar(){
        ArrayList<String> lstLigne = new ArrayList<>();

        lstLigne.add( String.format("%-14s", "Nom") + "|" + String.format("%-15s", "Valeur") );

        listeVar.forEach( (k,v) -> {
            String valeur = "v";
            valeur = valeur.replaceAll("v",listeVar.get(k)) ;
            String ligne = String.format("%-14s", Console.adaptTxt(k)) + "|" + String.format("%-15s", valeur );
            lstLigne.add(ligne);
        });


        return  lstLigne;
    }
}
