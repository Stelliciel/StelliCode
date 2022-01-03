package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;

public class Fonction {
    private String affichage="";
    public Fonction(ArrayList<String> ligne)
    {
        String[] str;
        for(String s : ligne) {
            primitive(s);
        }
    }

    private void primitive(String str) {
        String[] primitive = str.split(" ");
        switch (primitive[1])
        {
            case "lire"     : //Main.lire(); break;
            case "écrire"   : affichage += ecrire(str); break;
            case "enChaine" :
            case "enEntier" :
            case "enRéel"   :
            default         : System.out.println("non traité");
        }
    }

    private String ecrire(String str) {
        boolean parenthese = false;
        boolean ecrire     = false;
        //boolean virgule    = false;
        StringBuilder res        = new StringBuilder();

        for(int ind=7;ind<str.length();ind++)
        {
            char car = str.charAt(ind);
            //contenu entre ""
            if (car == '\"')
                ecrire = !ecrire;

            /*//vigule passé
            if (car == ',' && !ecrire)
                virgule = true;*/

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

        return res.toString();

    }
}
