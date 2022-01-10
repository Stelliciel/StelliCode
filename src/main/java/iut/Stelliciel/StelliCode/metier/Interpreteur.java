package iut.Stelliciel.StelliCode.metier;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Interpreteur {

    private final ArrayList<String> fichier;
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;
    private final Parcours parcours;
    private String signature;

    public Interpreteur(File adresseFichier) {
        this.fichier        = Interpreteur.lireFichier(adresseFichier);
        lstConstantes       = new HashMap<>();
        lstVariables        = new HashMap<>();
        parcours            = new Parcours();

//        System.out.println("/*----------------*/\n/* Iniatilisation */\n/*----------------*/");
        initialisationFichier();

       /*System.out.println("Signature : " + signature);
        lstConstantes.forEach((k,v) -> {
            System.out.println("Nom de la constante : "+k+"\t || " + v);
        });
        lstVariables.forEach((k,v) -> {
            System.out.println("Nom de la variable  : "+k+"\t || " + v);
        });*/

        lectureFichier();
    }

    /*--------------------*/
    /* Lecture du fichier */
    private void lectureFichier() {
        int cpt;
        for (cpt=0; !fichier.get(cpt).contains("DEBUT"); cpt++) System.out.println("->"+fichier.get(cpt));

        parcours.nouvelleEtat( nouvelleEtatLigne(cpt) );

        while( cpt < fichier.size() ){
            String ligne = fichier.get(cpt);
            SYAlgorithm ope = new SYAlgorithm();
            double      d1  = ope.doTheShuntingYard("8+8/7");

            traiter(cpt, ligne );

            System.out.println(d1);
            System.out.println("true1   : " + ou("8+8/7 > 2 OU 8+8 = 16"));
            System.out.println("true2   : " + ou("3-1 >= 2 OU 8+8 = 16"));
            System.out.println("true3   : " + ou("3-1 < 2 OU 8+8 < 16"));
            System.out.println("false4  : " + ou("3*1 <= 2 OU 8+8 > 16"));
            System.out.println("true5   : " + et("8+8/7 > 8 ET 8+8 = 16"));
            System.out.println("true6   : " + et("8 < 8 ET 8+8 = 16"));

            cpt++;
        }
    }

    private void traiter(int numLigne, String ligne){

        if (ligne.contains("<--")) {
            String[] separation = Fonction.affectation(ligne);

            /*System.out.println("nom:" + separation[0]);
            System.out.println("valeur:" + separation[1]);*/

            if (separation.length == 3) {
//                System.out.println("index:" + separation[2]);
                setTableau(separation[0], Integer.parseInt(separation[2]), separation[1]);
            } else {
                setVariable(separation[0], separation[1]);
            }

            parcours.nouvelleEtat(nouvelleEtatLigne(numLigne));
        } else if (ligne.contains("écrire")) {
            String ecrire = Fonction.entreParenthese(ligne);
            String afficher = Fonction.entreGuillemet(ecrire);

            if (ecrire.contains("\",")) {
                String variable = ecrire.substring(ecrire.indexOf(",") + 1).trim();
                EtatLigne etatLigne = nouvelleEtatLigne(numLigne);

                if (estConstante(variable))
                    etatLigne.setTraceAlgo(afficher + getConstante(variable));
                else
                    etatLigne.setTraceAlgo(afficher + getVariable(variable));

                parcours.nouvelleEtat(etatLigne);
            } else {
                EtatLigne etatLigne = nouvelleEtatLigne(numLigne);
                etatLigne.setTraceAlgo(afficher);
                parcours.nouvelleEtat(etatLigne);
            }
        }else {
            parcours.nouvelleEtat( nouvelleEtatLigne(numLigne) );
        }

    }
    //< > =
    public boolean ou(String ligne)
    {
        //System.out.println("true3   : " + ou("3-1 < 2 OU 8+8 < 16"));
        String tab[] = ligne.toUpperCase().split("OU");
        for(String str : tab)
        {
            if (str.contains("!=") && !estEgal(str))
                return true;
            else if (str.contains("=") && estEgal(str))
                return true;

            if (str.contains("=<") && !estInferieurSuperieur(">",str))
                return true;
            else if (str.contains("<") && estInferieurSuperieur("<",str))
                return true;

            if (str.contains("=>") && !estInferieurSuperieur("<",str))
                return true;
            else if (str.contains(">") && estInferieurSuperieur(">",str))
                return true;

        }
        return false;
    }

    public boolean et(String ligne)
    {
        boolean egal = false;

        String tab[] = ligne.toUpperCase().split("ET");

        for(String str : tab)
        {
            if (str.contains("!=") && estEgal(str))
                return false;
            else if (str.contains("=") && !estEgal(str))
                return false;

            if (str.contains("<") && estInferieurSuperieur("<",str))
                return false;
            else if (str.contains("=>") && !estInferieurSuperieur("<",str))
                return false;

            if (str.contains(">") && !estInferieurSuperieur(">",str))
                return false;
            else if (str.contains("=<") && !estInferieurSuperieur(">",str))
                return false;
        }
        return true;
    }

    public boolean estInferieurSuperieur(String comparateur, String ligne){
        String tab[] = ligne.toUpperCase().split(comparateur);
        System.out.println(comparateur);
        if (tab[0].matches("([0-9]*[.])?[0-9]+")) {
            SYAlgorithm ope = new SYAlgorithm();
            double      d1  = ope.doTheShuntingYard(tab[0]);

            if (tab[1].matches("([0-9]*[.])?[0-9]+")) {
                if(comparateur.equals("=<")) {
                    System.out.println("oui");
                    return !((Double.compare(d1, ope.doTheShuntingYard(tab[1]))) > 0);
                }
                else if (comparateur.equals("<"))
                    return (Double.compare(d1, ope.doTheShuntingYard(tab[1]))) < 0;

                if(comparateur.equals("=>"))
                    return !((Double.compare(d1, ope.doTheShuntingYard(tab[1]))) < 0);
                else if (comparateur.equals(">"))
                    return (Double.compare(d1, ope.doTheShuntingYard(tab[1]))) > 0;
            }
            else
            {
//                Variable var = (Variable) getVariable(tab[1]);
//                return (Double.compare(d1,Double.parseDouble(var.valToString())) < 0);
            }
        }/*else
        {
            if (tab[1].matches("([0-9]*[.])?[0-9]+")) {
                SYAlgorithm ope = new SYAlgorithm();
                Variable    var = (Variable) getVariable(tab[0]);

                if (comparateur.equals("<"))
                    return (Double.compare(Double.parseDouble(var.valToString()), ope.doTheShuntingYard(tab[1]))) < 0;
                if (comparateur.equals(">"))
                    return (Double.compare(Double.parseDouble(var.valToString()), ope.doTheShuntingYard(tab[1]))) > 0;
            }
            else
            {
                Variable var0 = (Variable) getVariable(tab[0]);
                Variable var1 = (Variable) getVariable(tab[1]);
                return (Double.compare(Double.parseDouble(var0.valToString()),Double.parseDouble(var1.valToString())) < 0);
            }
        }*/

        return false;
    }

    public boolean estEgal(String ligne)
    {
        String[] tab = ligne.replaceAll("!=", "=").split("=");

        //est un calcul (dans une chaine)
        if (tab[0].matches("(\\d+[\\.])?[0-9]+")) {
            SYAlgorithm ope = new SYAlgorithm();
            double d1 = ope.doTheShuntingYard(tab[0]);
            if (tab[1].matches("(\\d+[\\.])?[0-9]+")) {
                return (Double.compare(d1, ope.doTheShuntingYard(tab[1]))) == 0;
            }/*
            else
            {
                Variable var = (Variable) getVariable(tab[1]);
                return (Double.compare(d1,Double.parseDouble(var.valToString())) == 0);
            }*/
        }

        //est une chaine
        if(tab[0].contains("\"") || tab[1].contains("\""))
           return estEgal(tab,"\"");

        //est un charactère
        if(tab[0].contains("'") || tab[1].contains("'"))
           return estEgal(tab,"'");

        /*Variable var0 = (Variable) getVariable(tab[0]);
        Variable var1 = (Variable) getVariable(tab[1]);

        return var0.getVal().equals(var1.getVal());*/
        return false;
    }

    private boolean estEgal(String[] tab, String Element)
    {
        //"" ou ''
        if (tab[0].contains(Element)) {
            tab[0] = contenu(Element, Element, tab[0]);
            if(tab[1].contains(Element))
            {
                tab[1] = contenu(Element, Element, tab[1]);
                return tab[0].equals(tab[1]);
            }/*else
            {
                //avec une variable
                Variable var = (Variable) getVariable(tab[1]);
                return var.getVal().equals(tab[0]);
            }*/
        }
        /*else {
            tab[1] = contenu(Element, Element, tab[1]);
            Variable var = (Variable) getVariable(tab[0]);
            return var.getVal().equals(tab[1]);
        }*/
        return false;
    }

    public static String contenu(String element1,String element2,String str)
    {
        return str.substring( str.indexOf(element1), str.lastIndexOf(element2) ).trim();
    }

    public EtatLigne getEtatVar(int lig){
        ArrayList<EtatLigne> arrLig = parcours.getLecteur();
        for (EtatLigne e: arrLig) {
            if(e.getNumLigne() == lig)
                return e;
        }
        return null;
    }

    private EtatLigne nouvelleEtatLigne(int numLigne){
        return new EtatLigne(signature, lstConstantes, lstVariables, numLigne);
    }

    public Parcours getParcours() { return parcours; }
    /* Fin lecture fichier*/
    /*--------------------*/


    /*------------------------------*/
    /* Initialisation des variables */
    private void initialisationFichier() {
        int cpt =0;
        while ( !fichier.get(cpt).equals("DEBUT") ) {
            String[] mots = fichier.get(cpt).split(" ");

            switch (mots[0]){
                case "ALGORITHME" ->  signature = mots[1];
                case "constante:" ->  cpt = ajouterConstante(cpt);
                case "variable:"  ->  cpt = ajouterVariables(cpt);
            }

            if( !fichier.get(cpt).equals("DEBUT"))
                cpt++;
        }
    }

    private int ajouterConstante(int cpt) {
        String ligne = fichier.get(++cpt);

        while ( !(ligne.equals("DEBUT") || ligne.equals("variable:"))){
            String[] mots = ligne.replaceAll(" ", "").split("<--");
            String valeur = mots[1];
            String nom    = mots[0];

            addConstante(nom,getType(valeur),valeur);

            ligne = fichier.get(++cpt);
        }

        return cpt-1;
    }
    private int ajouterVariables(int cpt) {
        String ligne = fichier.get(++cpt);

        while ( !ligne.equals("DEBUT") ){
            String[] mots = ligne.split(":");
            String[] variables = mots[0].replaceAll(" ", "").split(",");
            String type = mots[1];

            if ( type.contains("tableau") ){
                String tailleTab = type.substring(type.indexOf('[')+1,type.indexOf(']'));
                String typeTab   = "";
                if (type.contains("entier")    ) typeTab  = "entier";
                if (type.contains("reel")      ) typeTab  = "reel";
                if (type.contains("boolean")   ) typeTab = "boolean";
                if (type.contains("caracteres") ) typeTab = "caractere";
                if (type.contains("chaine")    ) typeTab = "chaine";

                if ( lstConstantes.containsKey(tailleTab) ) {
                    addTableau(false,variables[0],typeTab,String.valueOf(lstConstantes.get(tailleTab).getVal()));
                }
                else {
                    addTableau(false,variables[0],typeTab, tailleTab);
                }
            }
            else {
                for ( String nom : variables ){
                    addVariable(nom, type.replaceAll(" ", ""));
                }
            }
            ligne = fichier.get(cpt++);
        }

        return cpt-1;
    }

    private String getType(String valeur){
        if ( valeur.charAt(0) == '"' && valeur.charAt(valeur.length()-1) == '"') return "chaine";
        if ( valeur.contains(".,")) return "reel";
        if ( valeur.equals("vrai") || valeur.equals("faux") ) return "boolean";
        if ( valeur.matches("^\\d+$")) return "entier";
        if ( valeur.length() == 1 ) return "caractere";

        return "chaine";
    }
    /* Fin Initialisation des variables */
    /*----------------------------------*/

    /*-----------------------*/
    /* Gestion des variables */
    public HashMap<String, Variable<Object>> getLstConstantes() { return lstConstantes; }
    public HashMap<String, Variable<Object>> getLstVariables()  { return lstVariables;  }
    public ArrayList<String>                 getCode()          { return this.fichier;  }
    public int                               getNbChiffre()     { return (this.fichier.size()+"").length(); }

    public void addConstante(String nom, String type, String valeur){ lstConstantes.put(nom, get(nom, type, valeur) ); }
    public void addVariable (String nom, String type)               { lstVariables .put(nom, new Variable<>(nom, type) );  }
    public void addTableau  (boolean constante, String nom, String type, String taille) {
        if ( constante )
            lstConstantes.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
        else
            lstVariables.put(nom, new Variable<>(nom,Integer.parseInt(taille),type));
    }

    public Object getConstante (String nom) { return  lstConstantes.get(nom).getVal(); }
    public Object getVariable  (String nom) { return  lstVariables .get(nom).getVal(); }
    public Object getIndTableau(String nom, int ind) {
        if( estConstante(nom) )
            return lstConstantes.get(nom).getIndTab(ind);
        else
            return lstVariables.get(nom).getIndTab(ind);
    }

    public void setVariable(String nom, String valeur)          {
        if ( valeur.contains("\""))
            valeur = Fonction.entreGuillemet(valeur);
        Interpreteur.set(lstVariables.get(nom), valeur);  }
    public void setTableau (String nom, int ind, String valeur) {
        if ( valeur.contains("\""))
            valeur = Fonction.entreGuillemet(valeur);
        Variable<Object> v;
        if ( estConstante(nom) )
            v = lstConstantes.get(nom);
        else
            v = lstVariables.get(nom);

        v.setIndTab(ind, convertitValeur(v.getType(),valeur) );
        System.out.println("Changement ind : " + v.getIndTab(ind) );
    }

    private static Object convertitValeur(String type, String valeur){
        switch (type){
            case "entier"    -> { return Integer.parseInt(valeur);     }
            case "caractere" -> { return valeur.charAt(0);             }
            case "boolean"   -> { return Boolean.parseBoolean(valeur); }
            case "reel"      -> { return Double.parseDouble(valeur);   }
            default          -> { return valeur;                       }
        }
    }
    private static boolean estConstante(String nom){ return nom.equals(nom.toUpperCase()); }

    public static void             set(Variable<Object> var, String valeur)   {
        switch (var.getType()){
            case "entier"    -> { int val = Integer.parseInt(valeur);
                var.setVal(val);
            }

            case "caractere" -> { char val = valeur.charAt(0);
                var.setVal(val);
            }

            case "chaine"    -> var.setVal(valeur);

            case "boolean"   -> { boolean val = Boolean.parseBoolean(valeur);
                var.setVal(val);
            }

            case "reel"      -> { double val = Double.parseDouble(valeur);
                var.setVal(val);
            }
        }
    }
    public static Variable<Object> get(String nom, String type, String valeur){
        Variable<Object> var = null;
        switch (type){
            case "entier"    -> {
                int val = Integer.parseInt(valeur);
                var = new Variable<>(nom,type, val);
            }

            case "caractere" -> {
                char val = valeur.charAt(0);
                var = new Variable<>(nom,type, val);
            }

            case "chaine"    -> var = new Variable<>(nom,type, valeur);

            case "boolean"   -> {
                boolean val = Boolean.parseBoolean(valeur);
                var = new Variable<>(nom,type, val);
            }

            case "reel"      -> {
                double val = Double.parseDouble(valeur);
                var = new Variable<>(nom,type, val);
            }
        }

        return var;
    }
    /* Fin gestion des variables */
    /*---------------------------*/


    public static ArrayList<String> lireFichier(File adresse) {
        ArrayList<String> fichier = new ArrayList<>();
        try{
            Scanner sc = new Scanner(new FileInputStream(adresse), "UTF8");

            char charPrecedent =' ';
            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();
                /*for (char c:ligne.toCharArray()) {
                    if(c == '?' && charPrecedent == '?'){

                    charPrecedent = c;
                    }*/

                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }

    public ArrayList<String> changLig(char dir) {
        if(dir == 'f'){
            return parcours.next().getTraceAlgo();
        }else{
            return parcours.prec().getTraceAlgo();
        }
    }
}
