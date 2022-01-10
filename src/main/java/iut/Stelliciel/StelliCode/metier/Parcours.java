package iut.Stelliciel.StelliCode.metier;

import java.util.ArrayList;

public class Parcours {
    private ArrayList<EtatLigne> lecteur;
    private int pointeur;

    public Parcours() {
        this.pointeur = 0;
        this.lecteur  = new ArrayList<>();
    }

    public ArrayList<EtatLigne> getLecteur() {
        return lecteur;
    }

    public void nouvelleEtat(EtatLigne etatLigne){
        if ( !lecteur.isEmpty() ){
            ArrayList<String> traceAlgo = lecteur.get( lecteur.size() -1 ).getTraceAlgo();

            if ( traceAlgo != null )
                for(String ligne : traceAlgo ) etatLigne.setTraceAlgo( ligne );
        }
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
