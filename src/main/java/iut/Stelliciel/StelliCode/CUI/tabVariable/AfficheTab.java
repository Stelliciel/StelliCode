package iut.Stelliciel.StelliCode.CUI.tabVariable;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Variable;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Gaspard Gordien
 * @version 1
 */
public class AfficheTab {
    private final HashMap<String, Variable> listeVar;

    /** constructeur d'un tableau de variable vide
     */
    public AfficheTab(){
        this.listeVar = new HashMap<>(1);
    }

    /**verifie si la variable est déja dans la liste si c'est le cas modifie la valeur sinon l'ajoute
     * @param var nouvelle variable
     */
    public void maj(Variable var){
        for(String nom : listeVar.keySet()){
            if(nom.equals(var.getNom())){this.listeVar.get(nom).setVal(var.valToString());}
        }
        this.listeVar.put(var.getNom(), var);
    }

    /**@return le string du tableau affichTab
     */
    public String toString(){
        StringBuilder sRep = new StringBuilder("     nom    |  valeur   \n");
        for(String nom : listeVar.keySet()){
            sRep.append(String.format("%-12s", CUI.adaptTxt(nom))+"|"
                       +String.format("%-11s", CUI.adaptTxt(listeVar.get(nom).valToString()))+'\n');
        }
        System.out.println(sRep.toString());
        return sRep.toString();
    }

    public String affLig(String toutesLig, int num){
        ArrayList<String> arrString = new ArrayList<>();
        arrString.add("");
        int ind = 0;
        for (char c:toutesLig.toCharArray()){
            if(c != '\n'){arrString.add(arrString.remove(ind) + c);}
            else{
                ind++;
                arrString.add("");
            }
        }
        if(num >= ind ){return "                        ";}
        return arrString.get(num);
    }
}
