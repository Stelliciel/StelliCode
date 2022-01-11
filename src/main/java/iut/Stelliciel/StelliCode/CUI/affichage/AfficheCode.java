package iut.Stelliciel.StelliCode.CUI.affichage;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

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
    public final int PRI_TEXT   = lectureCouleur.getCouleur("primitive").getCouleurText();

    public String  coloration(String s,ArrayList<String> lstVar){
        String[] tabFonct = {"\u00e9crire", " lire"," plancher"," plafond"," enChaine"," enReel"," enEntier"," car "," car("," ord("," ord "};
        String[] tabCond = {" si ", " alors"," sinon "," fsi"," tq "," ftq"," faire"};
        String sRep = s;
        for (String fonct: tabFonct) {
            if(sRep.contains(fonct)){
                sRep = sRep.replaceAll(fonct,ansi().fgRgb(FON_TEXT).a(fonct).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND).toString());
            }
        }
        for (String cond : tabCond) {
            if(sRep.contains(cond)){
                sRep = sRep.replaceAll(cond,ansi().fgRgb(COND_TEXT).a(cond).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND).toString());
            }
        }
        if(lstVar != null && ! lstVar.isEmpty()){
            for (String var:lstVar) {
                sRep = sRep.replaceAll(var,ansi().fgRgb(VAR_TEXT).a(var).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND).toString());
            }
        }
        return sRep;
    }

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
            //si lig en cours
                //cololig en cour
            //sinon color blanc
            //color si etc
        if (num < arrString.size()) {
            //if(skipper)
                //color lig en gris
            //}else{
            if (num == ligEnCours) {
                if (arrString.get(num).equals("")) {
                    return (ansi().bgRgb(COURS_FOND).a(" ").reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
                }
                return (ansi().bgRgb(COURS_FOND).a(arrString.get(num)).reset().bgRgb(
                        NOR_FOND).fgRgb(NOR_TEXT));
            } else if (arrString.get(num).equals("")) {
                return (ansi().bgRgb(NOR_FOND).a(" ").reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
            }
            String lig = coloration(arrString.get(num),null);
            return (ansi().a(lig).reset().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
        } else
            return (ansi().a(" ").reset().bgRgb(NOR_FOND)).fgRgb(NOR_TEXT);
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
