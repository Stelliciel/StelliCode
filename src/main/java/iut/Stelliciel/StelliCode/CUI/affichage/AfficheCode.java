package iut.Stelliciel.StelliCode.CUI.affichage;

import java.util.ArrayList;

public class AfficheCode {
    private final int nbChiffreSign;
    private final ArrayList<String> arrString;

    public AfficheCode(ArrayList<String> arrString,int nbChiffreSign){
        this.nbChiffreSign = nbChiffreSign;
        this.arrString =arrString;
    }

    private String getLig(int num){
        if(num <= this.arrString.size())
            return arrString.get(num);
        else
            return " ";
    }

    public String affLig(int num){
        return String.format("%" +nbChiffreSign + "d ",num+1) + this.getLig(num);
    }
}
