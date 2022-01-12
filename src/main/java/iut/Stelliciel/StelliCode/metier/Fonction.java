package iut.Stelliciel.StelliCode.metier;

public class Fonction {


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
        String[] test = affectation("tab[1] <-- 5");
        System.out.println("taille:" + test.length);
        for(String t : test ) System.out.println(t);

        test = affectation( "s <-- \"chaine\"");
        System.out.println("taille:" + test.length);
        for(String t : test ) System.out.println(t);


        System.out.println( Fonction.entreGuillemet( "\"chaine\""));
    }
}
