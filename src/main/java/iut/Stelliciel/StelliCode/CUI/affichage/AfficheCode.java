/*
package iut.Stelliciel.StelliCode.CUI.affichage;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import org.fusesource.jansi.Ansi;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;

public class AfficheCode {
    private final int nbChiffreSign;
    private final ArrayList<String> arrString;
    private ArrayList<String> lstVar;
    private final LectureCouleur lectureCouleur = new LectureCouleur();
    public final int NOR_FOND   = lectureCouleur.getCouleur("defaut").getCouleurFond();
    public final int NOR_TEXT   = lectureCouleur.getCouleur("defaut").getCouleurText();
    public final int FON_TEXT   = lectureCouleur.getCouleur("fonction").getCouleurText();
    public final int VAR_TEXT   = lectureCouleur.getCouleur("variable").getCouleurText();
    public final int COURS_FOND = lectureCouleur.getCouleur("ligneEnCour").getCouleurFond();
    public final int COND_TEXT  = lectureCouleur.getCouleur("conditionBoucles").getCouleurText();
    public final int COM_TEXT   = lectureCouleur.getCouleur("commentaire").getCouleurText();
    public final int VRAI_COND  = lectureCouleur.getCouleur("condVrai").getCouleurFond();
    public final int FAUX_COND  = lectureCouleur.getCouleur("condFaux").getCouleurFond();
    public final int PRI_TEXT   = lectureCouleur.getCouleur("primitive").getCouleurText();

    public void setLstVar(ArrayList<String> lstVar){this.lstVar = lstVar;}

    public AfficheCode(ArrayList<String> arrString, int nbChiffreSign) {
        this.nbChiffreSign = nbChiffreSign;
        this.arrString = arrString;
        this.lstVar = new ArrayList<>();
    }

    public int getTaillePro() {
        return this.arrString.size();
    }

    private Ansi getLig(int num, int ligEnCours) {
        Parcours parcour = Main.getInstance().getParcours();
        String s = arrString.get(num);
        if (num < arrString.size()) {
            if (num == ligEnCours) {
                //la ligne est la lig en cours
                if (s.equals("")) {
                    return (ansi().bgRgb(COURS_FOND).a(" ").reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
                }

                if(parcour.getLecteur().get(num).isCondition()){
                    //la ligne à une condition (si tq)
                    if(parcour.getLecteur().get(num).isConditionTrue()){
                        if (s.contains("//")){return  ansi().bgRgb(VRAI_COND).a(s.substring(0,s.indexOf("//"))).ansi().fgRgb(COM_TEXT).a(s.substring(s.indexOf("//"))).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND);}
                        else{return ansi().bgRgb(VRAI_COND).a(s).reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT);}
                    }
                    else {
                        if (s.contains("//")){return  ansi().bgRgb(FAUX_COND).a(s.substring(0,s.indexOf("//"))).ansi().fgRgb(COM_TEXT).a(s.substring(s.indexOf("//"))).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND);}
                        else{return ansi().bgRgb(FAUX_COND).a(s).reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT);}
                    }
                }
                return (ansi().bgRgb(COURS_FOND).a(s).reset().bgRgb(
                        NOR_FOND).fgRgb(NOR_TEXT));
            }

            else{
                if (s.equals("")) {return (ansi().bgRgb(NOR_FOND).a(" ").reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));}
                if (ligneSkipper(num,parcour)){return ansi().bgRgb(COM_TEXT).a(s).reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT);}
                }
            //String lig = coloration(s,lstVar);
            return (ansi().a(lig).reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
        } else
            return (ansi().a(" ").reset().bgRgb(NOR_FOND)).fgRgb(NOR_TEXT);
    }

    private boolean ligneSkipper(int cpt,Parcours p) {
        for(EtatLigne e : p.getLecteur() ){
            if(e.getNumLigne() == cpt){
                if(e.estSkipper())
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    public String affLig(int num, int ligEnCours) {
        return String.format("%" + nbChiffreSign + "d ", num + 1) + this.getLig(num, ligEnCours);
    }

    public int getTaille(int num) {
        if (arrString.get(num).length() == 0) {
            return 1;
        }
        return arrString.get(num).length();
    }

    public ArrayList<String> getArrString() {
        return arrString;
    }
}
*/
