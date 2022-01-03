package iut.Stelliciel.StelliCode.CUI.tabVariable;

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
        this.listeVar = new HashMap<>();
    }

    /**verifie si la variable est déja dans la liste si c'est le cas modifie la valeur sinon l'ajoute
     * @param var nouvelle variable
     */
    public void maj(Variable var){
        for(String nom : listeVar.keySet()){
            if(nom.equals(var.getNom())){this.listeVar.get(nom).setVal(var);}
        }
        this.listeVar.put(var.getNom(), var);
    }

    public String affLig(int num){
        ArrayList<String> arrString = new ArrayList<>();
        int ind = 0;
        String toutesLig = this.toString();
        for (char c:toutesLig.toCharArray()){
            if(c != '\n'){arrString.add(arrString.remove(ind) + 'c');}
            else{
                ind++;
                arrString.add(" ");
            }
        }
        if(num > ind ){return "                       ";}
        return arrString.get(num-1);
    }

    /**@return le string du tableau affichTab
     */
    public String toString(){
        StringBuilder sRep = new StringBuilder("+----------+----------+\n|    nom   |  valeur  |\n+----------+----------+\n");
        for(String nom : listeVar.keySet()){
            sRep.append(listeVar.get(nom).toString()).append('\n');
        }
        sRep.append("+----------+----------+\n");
        return sRep.toString();
    }
}
