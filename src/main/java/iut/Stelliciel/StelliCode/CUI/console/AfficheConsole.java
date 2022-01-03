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
        this.arrString.add("user >>" + s);
    }

    public void AjouterOut(String s){
        this.arrString.add(s);
    }

    public String toString(){
        StringBuilder sRep = new StringBuilder();
        for (String s : arrString) {
            sRep.append(s).append('\n');
        }
        return sRep.toString();
    }
}