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

    public void Ajouter(ArrayList<String> arrIn){
        if(arrIn != null){
            for (String s:arrIn) {
                Ajouter(s.charAt(0),s.substring(1));
            }
        }
    }

    public String formatConsole(String s){
        if(s.startsWith("utilisateur")){
            return String.format("|%-110s|\n",s);
        }else{
            return String.format("|%110s|\n",s);
        }
    }

    public String toString(){
        if (arrString.isEmpty() || arrString ==null){ return "";}
        else {
            String sRep = "";
            if(arrString.size()>3) {
                sRep += formatConsole(arrString.get(arrString.size() - 3));
                sRep += formatConsole(arrString.get(arrString.size() - 2));
                sRep += formatConsole(arrString.get(arrString.size() - 1));
            } else {
                for (String s:arrString) {
                    sRep += formatConsole(s);
                }
            }
            return sRep;
          
        }
        return sRep.toString();
    }
}