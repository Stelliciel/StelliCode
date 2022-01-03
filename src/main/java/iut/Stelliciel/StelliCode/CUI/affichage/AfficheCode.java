package iut.Stelliciel.StelliCode.CUI.affichage;

import java.util.ArrayList;

public class AfficheCode {
    private final int nbChiffreSign;
private final ArrayList<String> arrString;

    public AfficheCode(ArrayList<String> arrString,int nbChiffreSign){
        this.nbChiffreSign = nbChiffreSign;
        this.arrString =arrString;
    }

    public String affLig(int num){
        return String.format("-" +nbChiffreSign + "%d",num) + " " + this.arrString.get(num-1);
    }
}
