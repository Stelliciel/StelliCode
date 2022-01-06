package iut.Stelliciel.StelliCode.CUI.affichage;

import iut.Stelliciel.StelliCode.metier.Couleur;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class AfficheCode {
    private final int               nbChiffreSign;
    private final ArrayList<String> arrString;
    private final LectureCouleur    lectureCouleur;

    public AfficheCode(ArrayList<String> arrString, int nbChiffreSign, LectureCouleur lectureCouleur){
        this.lectureCouleur = lectureCouleur;
        this.nbChiffreSign  = nbChiffreSign;
        this.arrString      = arrString;
    }

    public  int getTaillePro(){
        return this.arrString.size();
    }

    private Ansi getLig(int num, int ligEnCours) {
        if (num <= arrString.size())
        {
            if (num == ligEnCours)
            {
                System.out.println(LectureCouleur.getCouleur("ligneEnCour"));
                return (ansi().bgRgb(lectureCouleur.getCouleur("ligneEnCour").getValRFond(),
                                     lectureCouleur.getCouleur("ligneEnCour").getValGFond(),
                                     lectureCouleur.getCouleur("ligneEnCour").getValBFond()
                                    ).a(arrString.get(num)).reset().bgRgb(255,255,255).fgRgb(0,0,0));
            } else
                return (ansi().a(arrString.get(num)).reset().bgRgb(255,255,255).fgRgb(0,0,0));
        } else
            return (ansi().a(" ").reset().bgRgb(255,255,255).fgRgb(0,0,0));
    }
    public String affLig(int num, int ligEnCours){
        return String.format("%" +nbChiffreSign + "d ",num+1) + this.getLig(num,ligEnCours);
    }

    public int getTaille(int num){
        return  arrString.get(num).length();
    }
}
