package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;

public class Fonction {
    private String affichage="";

    public Fonction(ArrayList<String> lignes)
    {
        String res ="";
        String[] str;

        for(int i=0; i< lignes.size();i++)
        {
            String s = lignes.get(i);

            switch ( s )
            {
                case "si" :
                    int cptSi = 1;

                    break;
                default   :res += chercher(s);
            }
        }

        System.out.println(res);
    }


    private String chercher(String str) {
        str = str.trim();
        String[] chercher = str.split(" ");
        System.out.println(chercher[0]);
        switch (chercher[0])
        {
            case "lire"     ->{return "lire";}//Main.lire(); break;
            case "écrire"   -> {return "écrire";}//return ecrire(str);}
            case "enChaine" -> {return "enChaine";}//return enChaine(str);}
            case "enEntier" -> {return "enEntier";}//return enEntier(str);}
            case "enRéel"  ->  {return "enRéel";}//return enReel(str)};
            default         -> {return "non traité\n";}
        }
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

        for(int ind=0;ind<contenu.length();ind++)
            res += contenu.charAt(ind);

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

    private String entreParenthese(String str)
    {
        boolean contenu = false;
        String res="";

        for(int ind=0;ind+1<str.length()+1;ind++)
        {
            if(str.substring(ind,ind+1).equals("( "))
                contenu = true;

            if(contenu) {
                while (str.substring(ind,ind+1).equals(" )"))
                {
                    res += str.charAt(ind);
                    ind++;
                }

                return res;
            }
        }

        return null;
    }
}
