package iut.Stelliciel.StelliCode.metier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Stelliciel
 * @version 1
 */
public class EtatLigne {
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;
    private final String signature;

    private final boolean[] condition;
    private boolean lecture;
    private String  nomALire;

    private final int     numLigne;
    private final ArrayList<String> traceAlgo;
    private boolean skip;

    /**
     * constructeur
     * @param signature la signature de la ligne
     * @param lstConstantes les constantes de la ligne
     * @param lstVariables les variables de la ligne
     * @param numLigne le numéro de la ligne
     */
    public EtatLigne(String signature, HashMap<String, Variable<Object>> lstConstantes,
                     HashMap<String, Variable<Object>> lstVariables, int numLigne) {
        skip = false;
        this.signature     = signature;
        this.lstConstantes = copyVariable(lstConstantes);
        this.lstVariables  = copyVariable(lstVariables);
        this.numLigne = numLigne;
        this.traceAlgo = new ArrayList<>();

        condition = new boolean[2];
    }

    /**
     * set la trace de l'algo dans la console
     * @param trace, ce que la console affiche
     */
    public void setTraceAlgo(String trace){
        this.traceAlgo.add(trace);
    }

    /**
     * set la trace de l'algo dans la console
     * @return , ce que la console affiche
     */
    public ArrayList<String> getTraceAlgo(){
        return traceAlgo;
    }

    /**
     * set si la ligne est une condition et si elle est fausse ou vraie
     * @param condition le résultat de la condition
     */
    public void setCondition(boolean condition) {
        this.condition[0] = true;
        this.condition[1] = condition;
    }

    /**
     * le nom de la variable qui est demandé a etre lu
     * @param nomALire, le nom de la variable à lire
     */
    public void setNomALire(String nomALire) {
        this.nomALire = nomALire;
    }

    /**
     * le nom de la variable qui est demandé a etre lu
     * @return le nom de la variable à lire
     */
    public String getNomALire() { return this.nomALire;}

    /**
     * set si il y a une lecture à la ligne
     * @param lecture si il y a une lecture
     */
    public void setLecture(boolean lecture) {
        this.lecture = lecture;
    }

    /**
     * get la liste des constantes
     * @return la liste des constantes
     */
    public HashMap<String, Variable<Object>> getLstConstantes() {
        return lstConstantes;
    }

    /**
     * get la liste des variables
     * @return la liste des variables
     */
    public HashMap<String, Variable<Object>> getLstVariables()  { return lstVariables; }

    /**
     * renvois la signature de la ligne
     * @return la signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * si la ligne est une condition
     * @return si il y a une condition
     */
    public boolean isCondition()    { return condition[0]; }

    /**
     * test si la condition est vraie
     * @return si la condition est vraie
     */
    public boolean isConditionTrue(){ return  condition[1]; }

    /**
     * test si il y a une lecture à cette ligne
     * @return si il y a une lecture
     */
    public boolean isLecture()    { return lecture; }

    /**
     * get le numéro de la ligne
     * @return int, numéro de la ligne
     */
    public int     getNumLigne()  { return numLigne; }

    /**
     *  copie la totalité des variable d'une ligne à l'autre
     * @param lst liste des variable à copié
     * @return HashMap&#60String,{@link Variable} &#60Object&#62&#62
     */
    private HashMap<String, Variable<Object>> copyVariable(HashMap<String, Variable<Object>> lst){
        HashMap<String, Variable<Object>> newLst = new HashMap<>();

        lst.forEach((k,v) -> newLst.put(k, Variable.copy(v) ));

        return newLst;
    }

    /**
     *met le skip de la ligne à true
     */
    public void skip() { this.skip = true; }

    /**
     * test si la ligne est skippé
     * @return si ligne est skippé
     */
    public boolean estSkipper() { return skip; }
}
