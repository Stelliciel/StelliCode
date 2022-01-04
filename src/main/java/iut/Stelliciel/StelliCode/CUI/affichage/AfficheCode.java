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

    private Ansi getLig(int num,int ligEnCours) {
        if (num <= arrString.size()) {
            if (num == ligEnCours) {
                return (ansi().fgRgb(0, 255, 255).a(arrString.get(num)).reset().a("").fgRgb(255,0,255).a("oui").reset().a("").fgRgb(5,47,245).a("non").reset());
            } else {
                return (ansi().a(arrString.get(num)).reset());
            }
        } else {
            return (ansi().a(" ").reset());
        }
    }
    public String affLig(int num, int ligEnCours){
        return String.format("%" +nbChiffreSign + "d ",num+1) + this.getLig(num,ligEnCours);
    }

    public  int getTaille(int num){
        return  arrString.get(num).length();
    }
}
