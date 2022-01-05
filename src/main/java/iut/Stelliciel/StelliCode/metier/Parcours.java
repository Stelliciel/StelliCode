package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;

public class Parcours {
    private ArrayList<EtatLigne> lecteur;
    private int pointeur;
    private Main ctrl;

    public Parcours(Main ctrl) {
        this.ctrl     = ctrl;
        this.pointeur = 0;
        this.lecteur  = new ArrayList<>();
    }

    public void nouvelleEtat(EtatLigne etatLigne){
        lecteur.add(etatLigne);
    }

    public boolean hasNext() {
        if ( pointeur < lecteur.size() )
            return true;

        return false;
    }

    public EtatLigne next(){
        if ( hasNext() ){
            pointeur++;
            return lecteur.get(pointeur);
        }
        return null;
    }

    public EtatLigne getEtat() {return lecteur.get(pointeur); }

    public EtatLigne prec() {
        if ( pointeur > 0) {
            pointeur--;
            return lecteur.get(pointeur);
        }
        return null;
    }

}
