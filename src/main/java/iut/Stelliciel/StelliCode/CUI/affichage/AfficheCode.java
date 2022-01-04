package iut.Stelliciel.StelliCode.CUI.affichage;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class AfficheCode {
    private final int nbChiffreSign;
    private final ArrayList<String> arrString;

    public AfficheCode(ArrayList<String> arrString,int nbChiffreSign){
        this.nbChiffreSign = nbChiffreSign;
        this.arrString =arrString;
    }

    private Ansi getLig(int num){
        if(num <= this.arrString.size()){
            return  (ansi().fgRgb(0,255,254).a(arrString.get(num)).reset());}
        else
            return null;
    }

    public String affLig(int num){
        return String.format("%" +nbChiffreSign + "d ",num+1) + this.getLig(num);
    }
}
