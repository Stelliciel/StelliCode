package iut.Stelliciel.StelliCode.metier;

import java.util.ArrayList;
import java.util.HashMap;

public class EtatLigne {
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;
    private final String signature;

    private boolean condition;
    private boolean pointArret;
    private boolean lecture;

    private final int     numLigne;
    private ArrayList<String> traceAlgo;

    public EtatLigne(String signature, HashMap<String, Variable<Object>> lstConstantes,
                     HashMap<String, Variable<Object>> lstVariables, int numLigne) {
        this.signature     = signature;
        this.lstConstantes = copyVariable(lstConstantes);
        this.lstVariables  = copyVariable(lstVariables);
        this.numLigne = numLigne;
        this.traceAlgo = new ArrayList<>();
    }

    public void setTraceAlgo(String trace){
        this.traceAlgo.add(trace);
    }

    public ArrayList<String> getTraceAlgo(){
        if (traceAlgo.isEmpty())
            return null;
        return traceAlgo;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public void setPointArret(boolean pointArret) {
        this.pointArret = pointArret;
    }

    public void setLecture(boolean lecture) {
        this.lecture = lecture;
    }

    public HashMap<String, Variable<Object>> getLstConstantes() {
        return lstConstantes;
    }
    public HashMap<String, Variable<Object>> getLstVariables() {
        return lstVariables;
    }
    public String getSignature() {
        return signature;
    }

    public boolean isCondition()  { return condition; }
    public boolean isPointArret() { return pointArret; }
    public boolean isLecture()    { return lecture; }
    public int     getNumLigne()  { return numLigne; }


    private HashMap<String, Variable<Object>> copyVariable(HashMap<String, Variable<Object>> lst){
        HashMap<String, Variable<Object>> newLst = new HashMap<>();

        lst.forEach((k,v) -> { newLst.put(k, Variable.copy(v) ); });

        return newLst;
    }
}