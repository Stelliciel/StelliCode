package iut.Stelliciel.StelliCode.metier;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;

public class Parcours {
    private ArrayList<EtatLigne> lecteur;
    private int pointeur;

    /**
     * constructeur de parcours
     */
    public Parcours() {
        this.pointeur = -1;
        this.lecteur  = new ArrayList<>();
    }

    /**
     * get le lecteur
     * @return ArrayList &#60{@link EtatLigne}&#62, l'ensemble des états
     */
    public ArrayList<EtatLigne> getLecteur() {
        return lecteur;
    }

    /**
     * ajouter un état de ligne au lecteur
     * @param etatLigne l'{@link EtatLigne} à ajouter
     */
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

    /**
     * test si l'état ligne à un état ligne apres
     * @return boolean si la ligne possede une ligne suivante
     */
    public boolean hasNext() {
        return pointeur < lecteur.size() - 1;
    }

    /**
     * accede au prochain Etat Ligne
     * @return l'{@link EtatLigne} suivant
     */
    public EtatLigne next(){
        pointeur++;
        return lecteur.get(pointeur);
    }

    /**
     * get l'Etat ligne actuel
     * @return l'{@link EtatLigne} en cours
     */
    public EtatLigne getEtat() {
        if ( pointeur < 0 )
            return null;
        return lecteur.get(pointeur); }

    /**
     * test si l'état ligne à un état ligne avant
     * @return boolean si la ligne possede une ligne précédente
     */
    public boolean hasPrec() {
        return pointeur > 0;
    }

    /**
     * accede au précédent Etat Ligne
     * @return l'{@link EtatLigne} précédent
     */
    public EtatLigne prec() {
        pointeur--;
        return lecteur.get(pointeur);
    }

    /**
     * se déplace directement à une ligne
     * @param numLigne int, la ligne à acceder
     * @return l'{@link EtatLigne} de la ligne du numéro donné
     */
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

    /**
     * get l'état ligne de la derniere ligne
     * @return l'{@link EtatLigne} de la derniere ligne
     */
    public EtatLigne derniereLigne(){
        if ( lecteur.isEmpty() )
            return null;
        return lecteur.get( lecteur.size() -1 );
    }

    /**
     * réinisialise à partir d'un "lire"
     * @param e l'{@link EtatLigne} de la ligne avec le "lire"
     */
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


        eTmp.setLecture(true);
        eTmp.setNomALire(e.getNomALire());
        for(String ligne : e.getTraceAlgo() )
            eTmp.setTraceAlgo(ligne);
        nouvLecteur.add(  eTmp );
        lecteur = nouvLecteur;
    }
}
