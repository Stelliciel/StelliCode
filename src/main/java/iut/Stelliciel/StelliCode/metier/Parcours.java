package iut.Stelliciel.StelliCode.metier;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;

public class Parcours {
    private ArrayList<EtatLigne> lecteur;
    private int pointeur;

    public Parcours() {
        this.pointeur = -1;
        this.lecteur  = new ArrayList<>();
    }

    public ArrayList<EtatLigne> getLecteur() {
        return lecteur;
    }

    public void nouvelleEtat(EtatLigne etatLigne){
        if ( !lecteur.isEmpty() ){
            ArrayList<String> traceAlgo = derniereLigne().getTraceAlgo();
            if ( traceAlgo != null ){
                for (int cpt=0; cpt < traceAlgo.size(); cpt++){
                    etatLigne.getTraceAlgo().add( cpt, traceAlgo.get(cpt) );
                }
            }
        }
        lecteur.add(etatLigne);
    }

    public boolean hasNext() {
        if ( pointeur < lecteur.size()-1 )
            return true;

        return false;
    }

    public EtatLigne next(){
        pointeur++;
        return lecteur.get(pointeur);
    }

    public EtatLigne getEtat() {
        if ( pointeur < 0 )
            return null;
        return lecteur.get(pointeur); }


    public boolean hasPrec() {
        if( pointeur > 0 )
            return true;

        return false;
    }

    public EtatLigne prec() {
        pointeur--;
        return lecteur.get(pointeur);
    }

    public EtatLigne seRendreA(int numLigne){
        if ( numLigne < getLecteur().get(0).getNumLigne() ) {
            pointeur = 0;
            return getEtat();
        }


        if ( numLigne > derniereLigne().getNumLigne() ) {
            pointeur = getLecteur().size()-1;
            return getEtat();
        }

        int cpt = 0;
        while( lecteur.get(cpt).getNumLigne()+1 != numLigne ) {
            if ( cpt < lecteur.size()-1 )
                cpt++;
            else
                return null;
        }
        pointeur = cpt;
        return getEtat();
    }

    public EtatLigne derniereLigne(){
        if ( lecteur.isEmpty() )
            return null;
        return lecteur.get( lecteur.size() -1 );
    }

    public void reecrire(EtatLigne e) {

        ArrayList<EtatLigne> nouvLecteur = new ArrayList<>();
        boolean limite = true;
        for (EtatLigne eTmp: lecteur){
            if ( eTmp == e){
                limite = false;
            }
            if (limite){
                nouvLecteur.add(eTmp);
            }


        }
        EtatLigne eTmp = new EtatLigne(e.getSignature(), e.getLstConstantes(), e.getLstVariables(),e.getNumLigne());
        /*System.out.println("REECRIRE:");
        e.getLstVariables().forEach( (k,v) -> {
            System.out.println( k + " "+ v);
        });*/

        eTmp.setLecture(true);
        eTmp.setNomALire(e.getNomALire());
        for(String ligne : e.getTraceAlgo() )
            eTmp.setTraceAlgo(ligne);
        nouvLecteur.add(  eTmp );
        lecteur = nouvLecteur;
    }
}
