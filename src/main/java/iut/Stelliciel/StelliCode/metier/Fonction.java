package iut.Stelliciel.StelliCode.metier;

import java.time.LocalDate;

public class Fonction {


    public static String ajourdhui() {
        String date = LocalDate.now()+"";
        String jour = date.substring( date.indexOf("-")+1 );
        jour = jour.substring(jour.indexOf("-")+1);
        String mois = date.substring( date.indexOf("-")+1 );
        mois = mois.substring(0, mois.indexOf("-") );

        date = jour + "/"+mois+"/"+date.substring(0,date.indexOf("-"));
        return date;
    }

    public static String annee (String date){
        String annee = date.substring( date.indexOf("/")+1 );
        annee = annee.substring(annee.indexOf("/")+1);

        return annee;
    }
    public static String mois (String date){
        String mois = date.substring( date.indexOf("/")+1 );
        mois = mois.substring(0, mois.indexOf("/") );
        return  mois;
    }
    public static String jour (String date){
        return date.substring(0,date.indexOf("/"));
    }

    private String enChaine(String str)
    {
        String contenu = entreParenthese(str);
        String res = "";
        //Changer type si variable
        //res += Interpreteur.getVariable(contenu).getVal();

        return res;
    }

    private String enEntier(String str)
    {
        //Changer type si variable
        return Integer.parseInt(str)+"";
    }

    private String enReel(String str)
    {
        //Changer type si variable
        return Double.parseDouble(str)+"";
    }

    private String plafond(String str)
    {
        return Math.ceil(Double.parseDouble(str))+"";
    }

    private String plancher(String str)
    {
        return Math.floor(Double.parseDouble(str))+"";
    }

    private String hasard(String str)
    {
        return (int)(Math.random()*Integer.parseInt(str))+"";
    }

    private String ord(String str)
    {
        return Integer.parseInt(str)+"";
    }

    private String car(String str) { return (char)Integer.parseInt(str) + ""; }

    private String arrondis(String str)
    {
        return Math.round(Double.parseDouble(str)) +"";
    }

    public static String entreParenthese(String str)
    {
        return str.substring( str.indexOf("(")+1, str.indexOf(")") ).trim();
    }

    public static String entreGuillemet(String str)
    {
        return str.substring( str.indexOf("\"")+1, str.lastIndexOf("\"") );
    }

    public static String[] affectation(String ligne) {
        ligne = ligne.replaceAll(" ", "");
        if (!ligne.contains("[")) {
            System.out.println("here");
            return ligne.split("<--");
        }
        String[] affectation = new String[5];
        affectation[0] = ligne.substring(0,ligne.indexOf("["));
        affectation[1] = ligne.substring(ligne.indexOf("<--")+3 );
        affectation[2] = ligne.substring(ligne.indexOf("[")+1, ligne.indexOf("]"));
        affectation[3] = "0";
        affectation[4] = "0";
        if (ligne.matches("^*\\[*]\\[*]*")) {
            affectation[3] = ligne.substring(ligne.indexOf("[", ligne.indexOf("[")) + 1, ligne.indexOf("]", ligne.indexOf("]")));
        }
        if (ligne.matches("^*\\[*]\\[*]\\[*]*")) {
            affectation[4] = ligne.substring(ligne.indexOf("[", ligne.indexOf(ligne.indexOf("[")))+1, ligne.indexOf("]",ligne.indexOf(ligne.indexOf("]"))));
        }
        System.out.println(affectation[0] + " | " + affectation[1] + " | " + affectation[2] + " | " + affectation[3] + " | " + affectation[4]);
        return affectation;
    }


    public static void main(String[] args) {
        String date = Fonction.ajourdhui();
        System.out.println( date );
        System.out.println( Fonction.jour(date) );
        System.out.println( Fonction.mois(date) );
        System.out.println( Fonction.annee(date) );
    }
}
