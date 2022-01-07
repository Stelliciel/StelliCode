package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;

public class Fonction {
    private String affichage="";

    public Fonction(ArrayList<String> lignes)
    {
        String res ="";
        String[] str;

        for (String s : lignes) {
            switch (s) {
                case "tq":
                case "si":
                    int cptSi = 1;

                    break;
                default:
                    res += chercher(s);
            }
        }

        System.out.println(res);
    }


    private String chercher(String str) {
        str = str.trim();
        String[] chercher = str.split(" ");
        return switch (chercher[0]) {
            case "lire" -> Main.getInstance().saisie();
            case "écrire" -> "écrire";//return ecrire(str);}
            case "enChaine" -> "enChaine";//return enChaine(str);}
            case "enEntier" -> "enEntier";//return enEntier(str);}
            case "enRéel" -> "enRéel";//return enReel(str)};
            default -> "non traité\n";
        };
    }

    /*private String enReel(String str)
    {

    }*/

    private String ecrire(String str)
    {
        boolean parenthese = false;
        boolean ecrire     = false;
        StringBuilder res  = new StringBuilder();

        for(int ind=7;ind<str.length();ind++)
        {
            char car = str.charAt(ind);
            //contenu entre ""
            if (car == '\"')
                ecrire = !ecrire;

            //écrire ( "vous êtes ", message )
            if(ecrire)
                res.append(car);

            //Traiter variable  "", message
            if ( car != ' ' && car != ',')
            {
                StringBuilder var = new StringBuilder();
                while (car != ' ') {
                    var.append(car);
                    car = str.charAt(ind);
                    ind++;
                }

                //res += var.getVar().getVal();
            }
        }

        return "ooooo"+res.toString();

    }

    private String enChaine(String str)
    {
        String contenu = entreParenthese(str);
        String res = "";

        //res += Interpreteur.getVariable(contenu).getVal();

        return res;
    }

    private String enEntier(String str)
    {
        String contenu = entreParenthese(str);
        String res = "";

        //Si est un entier on caste
        for(int ind=0;ind<contenu.length();ind++)
            if(contenu.charAt(ind) != '.' || contenu.charAt(ind) != ',') {
                res += contenu.charAt(ind);
            }
        return res;
    }

    public static String entreParenthese(String str)
    {
        return str.substring( str.indexOf("(")+1, str.indexOf(")") ).trim();
    }

    public static String entreGuillemet(String str)
    {
        return str.substring( str.indexOf("\"")+1, str.lastIndexOf("\"") ).trim();
    }


    public static String[] affectation(String ligne) {

        if ( ligne.contains("[") ){
            String[] affectation = new String[3];
            ligne = ligne.replaceAll(" ", "");
            affectation[0] = ligne.substring(0,ligne.indexOf("["));
            affectation[1] = ligne.substring(ligne.indexOf("<--")+3 );
            affectation[2] = ligne.substring(ligne.indexOf("[")+1, ligne.indexOf("]"));

            return affectation;
        }
        else
            return ligne.replaceAll(" ", "").split("<--");
    }


    public static void main(String[] args) {
        String[] test = affectation("tab[1] <-- 5");
        System.out.println("taille:" + test.length);
        for(String t : test ) System.out.println(t);

        test = affectation( "s <-- \"chaine\"");
        System.out.println("taille:" + test.length);
        for(String t : test ) System.out.println(t);


        System.out.println( Fonction.entreGuillemet( "\"chaine\""));
    }
}
