package iut.Stelliciel.StelliCode.CUI.console;

import java.util.ArrayList;

public class AfficheConsole{
    private final ArrayList<String> arrString;

    public AfficheConsole(){
        this.arrString = new ArrayList<>();
    }

    public void Ajouter(char io, String s){
        if(io == 'i'){AjouterIn(s);}
        else{AjouterOut(s);}
    }

    public void AjouterIn(String s){
        this.arrString.add("utilisateur >>" + s);
    }

    public void AjouterOut(String s){
        this.arrString.add(s);
    }

    public void Ajouter(ArrayList<String> arrIn){
        if(arrIn != null){
            for (String s:arrIn) {
                Ajouter(s.charAt(0),s.substring(1));
            }
        }
    }

    public String toString(){
        if (arrString.isEmpty() || arrString ==null){ return "";}
        else {
            StringBuilder sRep = new StringBuilder();
            if(arrString.size()>3) {
                sRep.append(arrString.get(arrString.size() - 3)).append("\n");
                sRep.append(arrString.get(arrString.size() - 2)).append("\n");
                sRep.append(arrString.get(arrString.size() - 1)).append("\n");
            } else {
                for (String s:arrString) {
                    sRep.append(s).append("\n");
                }
            }
            return sRep.toString();
        }
    }
}