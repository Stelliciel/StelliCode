package iut.Stelliciel.StelliCode.CUI.console;

import java.util.ArrayList;

public class AfficheConsole{
    private final ArrayList<String> arrString;

    public AfficheConsole(){
        this.arrString = new ArrayList<>();
    }

    public void Ajouter(ArrayList<String> arrIn){
        if(arrIn != null){
            for (String s:arrIn) {
                if(s.charAt(0) == 'i'){
                    this.arrString.add("utilisateur >>" + s);
                }
                else{
                    this.arrString.add(s);
                }
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
    }
}