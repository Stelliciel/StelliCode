package iut.Stelliciel.StelliCode.metier;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fonction {

    public static boolean estUnePrimitive(String s) {
        Pattern pattern = Pattern.compile("(enChaine)|(enEntier)|(enReel)|(plafond)|(plancher)|" +
                "(hasard)|(ord)|(car)|(arrondi)|(ajourdhui)|(jour)|(mois)|(annee)");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    public static String primitive(String s) {
        String prim = "";
        String valeur = Fonction.entreParenthese(s);
        if ( s.contains("enChaine") ){
            prim = valeur;
        }
        else if ( s.contains("enEntier") ) {
            prim = Fonction.enEntier( valeur );
        }
        else if ( s.contains("enReel") ) {
            prim = Fonction.enReel( valeur );
        }
        else if ( s.contains("plafond") ) {
            prim = Fonction.plafond( valeur );
        }
        else if ( s.contains("plancher") ) {
            prim = Fonction.plancher( valeur );
        }
        else if ( s.contains("hasard") ) {
            prim = Fonction.hasard( valeur );
        }
        else if ( s.contains("ord") ) {
            prim = Fonction.ord( valeur );
        }
        else if ( s.contains("car") ) {
            prim = Fonction.car( valeur );
        }
        else if ( s.contains("arrondi") ) {
            prim = Fonction.arrondi( valeur );
        }
        else if ( s.contains("aujourdhui") ) {
            prim = Fonction.aujourdhui();
        }
        else if ( s.contains("annee") ) {
            prim = Fonction.annee( valeur );
        }
        else if ( s.contains("mois") ) {
            prim = Fonction.mois( valeur );
        }
        else if ( s.contains("jour") ) {
            prim = Fonction.jour( valeur );
        }
        return prim;
    }
    //fonctionne
    public static String aujourdhui() {
        String date = LocalDate.now()+"";
        String jour = date.substring( date.indexOf("-")+1 );
        jour = jour.substring(jour.indexOf("-")+1);
        String mois = date.substring( date.indexOf("-")+1 );
        mois = mois.substring(0, mois.indexOf("-") );

        date = jour + "/"+mois+"/"+date.substring(0,date.indexOf("-"));
        return "\""+date+"\"";
    }
    //fonctionne pas
    public static String annee (String date){
        String annee = date.substring( date.indexOf("/")+1 );
        annee = annee.substring(annee.indexOf("/")+1);

        return annee;
    }
    //fonctionne pas
    public static String mois (String date){
        String mois = date.substring( date.indexOf("/")+1 );
        mois = mois.substring(0, mois.indexOf("/") );
        return  mois;
    }
    //fonctionne
    public static String jour (String date){
        return date.substring(0,date.indexOf("/"));
    }
    //fonctionne pas
    private static String enChaine(String str)
    {
        String contenu = entreParenthese(str);

        return contenu;
    }
    //fonctionne
    private static String enEntier(String str)
    {
        //Changer type si variable
        return Integer.parseInt(str)+"";
    }
    //fonctionne
    private static String enReel(String str)
    {
        //Changer type si variable
        return Double.parseDouble(str)+"";
    }
    //fonctionne
    private static String plafond(String str)
    {
        return Math.ceil(Double.parseDouble(str))+"";
    }
    //fonctionne
    private static String plancher(String str)
    {
        return Math.floor(Double.parseDouble(str))+"";
    }
    //fonctionne
    private static String hasard(String str)
    {
        return (int)(Math.random()*Integer.parseInt(str))+"";
    }
    //fonctionne
    private static String ord(String str)
    {
        return (int)(str.charAt(0))+"";
    }
    //fonctionne
    private static String car(String str) { return (char)Integer.parseInt(str) + ""; }
    //fonctionne
    private static String arrondi(String str)
    {
        return Math.round(Double.parseDouble(str)) +"";
    }

    public static String entreParenthese(String str)
    {
        System.out.println("C :" + str);
        return str.substring( str.indexOf("(")+1, str.indexOf(")") );
    }

    public static String entreGuillemet(String str)
    {
        return str.substring( str.indexOf("\"")+1, str.lastIndexOf("\"") );
    }

    public static String[] affectation(String ligne) {
        ligne = ligne.replaceAll(" ", "");
        if (!ligne.contains("[")) {
            return ligne.split("<--");
        }
        String[] affectation = new String[5];
        affectation[0] = ligne.substring(0,ligne.indexOf("["));
        affectation[1] = ligne.substring(ligne.indexOf("<--")+3 );
        String[] tab = Fonction.separerInd(ligne);
        affectation[2] = tab[0];
        affectation[3] = tab[1];
        affectation[4] = tab[2];

        /*if (ligne.matches("^*\\[*]\\[*]*")) {
            affectation[3] = ligne.substring(ligne.indexOf("[", ligne.indexOf("[")) + 1, ligne.indexOf("]", ligne.indexOf("]")));
        }
        if (ligne.matches("^*\\[*]\\[*]\\[*]*")) {
            affectation[4] = ligne.substring(ligne.indexOf("[", ligne.indexOf(ligne.indexOf("[")))+1, ligne.indexOf("]",ligne.indexOf(ligne.indexOf("]"))));
        }*/
        return affectation;
    }

    public static String[] separerInd( String ligne ){
        String[] tab = new String[3];
        String tmp     = ligne;
        String    ind = tmp.substring(ligne.indexOf("[")+1, ligne.indexOf("]"));
        tab[0] = ind;
        tmp = tmp.substring( tmp.indexOf("]")+1 );

        if ( tmp.contains("[")){
            ind = tmp.substring(tmp.indexOf("[")+1, tmp.indexOf("]"));
            tab[1] = ind;
            tmp = tmp.substring( tmp.indexOf("]")+1 );

            if(tmp.contains("[")) {
                ind = tmp.substring(tmp.indexOf("[")+1, tmp.indexOf("]"));
                tab[2] = ind;
            }
            else
                tab[2] = "0";

        }
        else{
            tab[1] ="0";
            tab[2] ="0";
        }

        return tab;
    }


    public static void main(String[] args) {
    }

    public static String concatener(String s) {
        String[] tab = s.split("(©)|(\\(c\\))");
        String chaine = "";

        for(String t : tab ) {
            chaine += Fonction.entreGuillemet(t.trim());
        }
        return "\"" + chaine + "\"";
    }
}
