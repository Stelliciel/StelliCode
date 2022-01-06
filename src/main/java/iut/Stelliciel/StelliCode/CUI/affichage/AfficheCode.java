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
    private final LectureCouleur    lectureCouleur =new LectureCouleur();
    private final int R_FOND = lectureCouleur.getCouleur("defaut").getValRFond();
    private final int G_FOND = lectureCouleur.getCouleur("defaut").getValGFond();
    private final int B_FOND = lectureCouleur.getCouleur("defaut").getValBFond();
    private final int R_TEXT = lectureCouleur.getCouleur("defaut").getValRText();
    private final int G_TEXT = lectureCouleur.getCouleur("defaut").getValGText();
    private final int B_TEXT = lectureCouleur.getCouleur("defaut").getValBText();

    public AfficheCode(ArrayList<String> arrString, int nbChiffreSign){
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
                if(arrString.get(num).equals("")){
                    return (ansi().bgRgb(lectureCouleur.getCouleur("ligneEnCour").getValRFond(),
                                         lectureCouleur.getCouleur("ligneEnCour").getValGFond(),
                                         lectureCouleur.getCouleur("ligneEnCour").getValBFond()
                    ).a(" ").reset().bgRgb(R_FOND,G_FOND,B_FOND).fgRgb(R_TEXT,G_TEXT,B_TEXT));
                }
                return (ansi().bgRgb(lectureCouleur.getCouleur("ligneEnCour").getValRFond(),
                                     lectureCouleur.getCouleur("ligneEnCour").getValGFond(),
                                     lectureCouleur.getCouleur("ligneEnCour").getValBFond()
                                    ).a(arrString.get(num)).reset().bgRgb(
                                     R_FOND,G_FOND,B_FOND).fgRgb(R_TEXT,G_TEXT,B_TEXT));
            } else
                if(arrString.get(num).equals("")){
                    return (ansi().bgRgb(R_FOND,G_FOND,B_FOND).a(" ").reset().bgRgb(R_FOND,G_FOND,B_FOND).fgRgb(R_TEXT,G_TEXT,B_TEXT));
                }
                return (ansi().a(arrString.get(num)).reset().bgRgb(
                                R_FOND,G_FOND,B_FOND)
                        .fgRgb(
                                R_TEXT,G_TEXT,B_TEXT));
        } else
            return (ansi().a(" ").reset().bgRgb(R_FOND,G_FOND,B_FOND)).fgRgb(R_TEXT,G_TEXT,B_TEXT);
    }
    public String affLig(int num, int ligEnCours){
        return String.format("%" +nbChiffreSign + "d ",num+1) + this.getLig(num,ligEnCours);
    }

    public int getTaille(int num){
        if(arrString.get(num).length() == 0){return  1;}
        return  arrString.get(num).length();
    }

    public ArrayList<String> getArrString() {
        return arrString;
    }
}
