package iut.Stelliciel.StelliCode.metier;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stelliciel
 * @version 1
 */
public class Fonction {

    /**
     * retourne si la String est une primitive
     * @param s String
     * @return boolean, vrai si la String est une primitive
     */
    public static boolean estUnePrimitive(String s) {
        Pattern pattern = Pattern.compile("(enChaine)|(enEntier)|(enReel)|(plafond)|(plancher)|" +
                "(hasard)|(ord)|(car)|(arrondi)|(ajourdhui)|(jour)|(mois)|(annee)");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    /**
     * cherche la primitive utilisé et renvoie le résultat de celle-ci
     * @param s, la primitive
     * @return String, renvoie le résultat de la primitive
     */
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

    /**
     * renvoie la date d'aujourd'hui
     * @return String, la date d'aujourd'hui
     */
    public static String aujourdhui() {
        String date = LocalDate.now()+"";
        String jour = date.substring( date.indexOf("-")+1 );
        jour = jour.substring(jour.indexOf("-")+1);
        String mois = date.substring( date.indexOf("-")+1 );
        mois = mois.substring(0, mois.indexOf("-") );

        date = jour + "/"+mois+"/"+date.substring(0,date.indexOf("-"));
        return "\""+date+"\"";
    }

    /**
     * retourne l'année d'une date
     * @param date, au format mm/jj/aaaa
     * @return String, l'année d'une date
     */
    public static String annee (String date){
        String annee = date.substring( date.indexOf("/")+1 );
        annee = annee.substring(annee.indexOf("/")+1);

        return annee;
    }

    /**
     * retourne le mois d'une date
     * @param date, au format mm/jj/aaaa
     * @return String, le mois d'une date
     */
    public static String mois (String date){
        String mois = date.substring( date.indexOf("/")+1 );
        mois = mois.substring(0, mois.indexOf("/") );
        return  mois;
    }

    /**
     * retourne le jour d'une date
     * @param date, au format mm/jj/aaaa
     * @return String, le jour d'une date
     */
    public static String jour (String date){
        return date.substring(0,date.indexOf("/"));
    }

    /**
     * convertie en une chaine un réel ou entier
     * @param str
     * @return String, une chaine un réel ou entier
     */
    private static String enChaine(String str)
    {
        String contenu = entreParenthese(str);

        return contenu;
    }

    /**
     * convertie en un entier une chaine
     * @param str
     * @return String, un entier une chaine
     */
    private static String enEntier(String str)
    {
        //Changer type si variable
        return Integer.parseInt(str)+"";
    }
    /**
     * convertie en un réel une chaine
     * @param str
     * @return String, un réel une chaine
     */
    private static String enReel(String str)
    {
        //Changer type si variable
        return Double.parseDouble(str)+"";
    }

    /**
     * arrondi au supérieur un réel
     * @param str
     * @return String ,arrondi au supérieur un réel
     */
    private static String plafond(String str)
    {
        return Math.ceil(Double.parseDouble(str))+"";
    }
    /**
     * arrondi a l'inférieur un réel
     * @param str
     * @return String, arrondi a l'inférieur un réel
     */
    private static String plancher(String str)
    {
        return Math.floor(Double.parseDouble(str))+"";
    }

    /**
     * Renvoie nombre aléatoire de 0 au nombre passé en paramètre
     * @param str, nombre passé en paramètre
     * @return String, nombre aléatoire de 0 au nombre passé en paramètre
     */
    private static String hasard(String str)
    {
        return (int)(Math.random()*Integer.parseInt(str))+"";
    }

    /**
     * retourne code ascii d'un caractère
     * @param str, caractère
     * @return String, code ascii d'un caractère
     */
    private static String ord(String str)
    {
        return (int)(str.charAt(0))+"";
    }
    /**
     * retourne un caractère avec son code ascii
     * @param str, code ascii
     * @return String, un caractère avec son code ascii
     */
    private static String car(String str) { return (char)Integer.parseInt(str) + ""; }

    /**
     * arrondi le reel en parametre
     * @param str, le reel
     * @return String, le nombre arrondi
     */
    private static String arrondi(String str)
    {
        return Math.round(Double.parseDouble(str)) +"";
    }

    /**
     * retourne le contenu entre des parenthèses
     * @param str
     * @return String, contenu entre des parenthèses
     */
    public static String entreParenthese(String str)
    {
        return str.substring( str.indexOf("(")+1, str.indexOf(")") );
    }
    /**
     * retourne le contenu entre guillement
     * @param str
     * @return String, contenu entre des parenthèses
     */
    public static String entreGuillemet(String str)
    {
        return str.substring( str.indexOf("\"")+1, str.lastIndexOf("\"") );
    }

    /**
     * permet d'affecter une variable
     * @param ligne
     * @return String[], partie séparé par <--
     */
    public static String[] affectation(String ligne) {
//        ligne = ligne.replaceAll(" ", "");
        if (!ligne.split("<--")[0].contains("[")) {
            String[] tab = ligne.split("<--");
            tab[0] = tab[0].trim();
            tab[1] = tab[1].trim();
            return tab;
        }
        String[] affectation = new String[5];
        affectation[0] = ligne.substring(0,ligne.indexOf("<")).trim();
        if ( affectation[0].contains("[") )
            affectation[0] = affectation[0].substring(0, affectation[0].indexOf("["));
        affectation[1] = ligne.substring(ligne.indexOf("<--")+3 );
        String[] tab = Fonction.separerInd(ligne);
        affectation[2] = tab[0];
        affectation[3] = tab[1];
        affectation[4] = tab[2];

        return affectation;
    }

    /**
     * Retourne les index dans les crochets des tableaux
     * @param ligne
     * @return
     */
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


    /**
     * Permet de concatener chaines de caractère
     * @param s
     * @return String, chaines de caractère concatener
     */
    public static String concatener(String s) {
        String[] tab = s.split("(©)|(\\(c\\))");
        String chaine = "";

        for(String t : tab ) {
            chaine += Fonction.entreGuillemet(t.trim());
        }
        return "\"" + chaine + "\"";
    }
}
