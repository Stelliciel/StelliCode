package iut.Stelliciel.StelliCode.metier;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Interpreteur {

    private final ArrayList<String> fichier;
    private final ArrayList<String> code;

    private String signature;
    private HashMap<String, Variable<Object>> lstConstantes;
    private HashMap<String, Variable<Object>> lstVariables;

    private final Parcours parcours;
    private int   pointeur;

    private boolean attenteLecture;


    public Interpreteur(File adresseFichier) {
        this.fichier        = Interpreteur.lireFichier(adresseFichier);
        this.code           = Interpreteur.lireCode(adresseFichier);
        pointeur            = 0;
        attenteLecture      = false;

        lstConstantes       = new HashMap<>();
        lstVariables        = new HashMap<>();
        parcours            = new Parcours();

        initialisationFichier();

        lectureFichier();
    }

    /*--------------------*/
    /* Lecture du fichier */
    private void lectureFichier() {
        if (pointeur == 0 ) {
            for (pointeur=0; !fichier.get(pointeur).contains("DEBUT"); pointeur++);

            parcours.nouvelleEtat( new EtatLigne(signature, lstConstantes, lstVariables, pointeur++) );
        }

        while( pointeur < fichier.size() &&
                    !attenteLecture                                          )  {
            String ligne = fichier.get(pointeur);
            traiter( ligne );
            pointeur++;
        }
    }

    private void traiter(String ligne){
        if (ligne.contains("<--"))
        {
            String[] separation = Fonction.affectation(ligne);

            separation[1] = remplacerValeurVariable(separation[1]);

            if ( Fonction.estUnePrimitive(separation[1]) ){
                separation[1] = Fonction.primitive(separation[1]);
            }
            if ( separation[1].contains("©") || separation[1].contains("(c)")){
                separation[1] = Fonction.concatener(separation[1]);
            }

            if (separation.length == 5) {
                setTableau(separation[0], Integer.parseInt(separation[2]), Integer.parseInt(separation[3]), Integer.parseInt(separation[4]), separation[1]);
            } else {
                setVariable(separation[0], separation[1]);
            }

            parcours.nouvelleEtat(nouvelleEtatLigne(pointeur));
        }
        else if (ligne.contains("\u00e9crire") || ligne.contains("écrire") )
        {
            String s  = this.code.get(pointeur);
            String ecrire = Fonction.entreParenthese(s);
            EtatLigne etatLigne = nouvelleEtatLigne(pointeur);

            if (ecrire.contains("\",")) {
                String afficher = Fonction.entreGuillemet(ecrire);
                String variable = ecrire.substring(ecrire.indexOf(",") + 1).trim();

                if (estConstante(variable))
                    etatLigne.setTraceAlgo("o"+afficher + getConstante(variable));
                else
                    etatLigne.setTraceAlgo("o"+afficher + getVariable(variable));
            }
            else {
                if ( ecrire.contains("\"")) {
                    etatLigne.setTraceAlgo("o"+Fonction.entreGuillemet(ecrire));
                }
                else{
                    if (estConstante(ecrire))
                        etatLigne.setTraceAlgo("o" + getConstante(ecrire));
                    else
                        etatLigne.setTraceAlgo("o" + getVariable(ecrire));
                }
            }

            parcours.nouvelleEtat(etatLigne);
        }
        else if (ligne.contains("lire") )
        {
            String l = ligne.replaceAll("lire| ", "");
            String var = Fonction.entreParenthese(ligne);

            EtatLigne eL = nouvelleEtatLigne(pointeur);
            eL.setLecture (true);
            eL.setNomALire(var);
            parcours.nouvelleEtat(eL);
            attenteLecture = true;
        }
        else if (ligne.contains("si ") )
        {
            String condition = ligne.replaceAll("si", "").replaceAll("alors", "").trim();
            condition = condition.replaceAll(" ", "");


            condition = remplacerValeurVariable(condition);
            String tab = ligne.substring(0, ligne.indexOf("s") );

            boolean b = Expression.calculLogique(condition);
            EtatLigne eL = nouvelleEtatLigne(pointeur);
            eL.setCondition(b);

            parcours.nouvelleEtat(eL);
            if( b ) {
                pointeur++;
                while(!(this.fichier.get(pointeur).startsWith( tab + "sinon") ||
                                       this.fichier.get(pointeur).startsWith(tab + "fsi"))) {

                    traiter(this.fichier.get(pointeur));
                    pointeur++;
                }
                while(!(this.fichier.get(pointeur).startsWith(tab + "fsi"))) {

                    EtatLigne eTmp = nouvelleEtatLigne( pointeur );
                    eTmp.skip();
                    parcours.nouvelleEtat(eTmp);
                    pointeur++;
                }
                if ( this.fichier.get(pointeur).startsWith(tab + "fsi"))
                    parcours.nouvelleEtat(nouvelleEtatLigne(pointeur));
            }
            else {
                pointeur++;
                while(  !( this.fichier.get(pointeur).startsWith( tab + "sinon") ||
                           this.fichier.get(pointeur).startsWith(tab + "fsi")       )       ) {
                    EtatLigne eTmp = nouvelleEtatLigne( pointeur );
                    eTmp.skip();
                    parcours.nouvelleEtat(eTmp);
                    pointeur++;
                }
                parcours.nouvelleEtat(nouvelleEtatLigne(pointeur));
                while(!(this.fichier.get(pointeur).startsWith(tab + "fsi"))) {
                    pointeur++;
                    traiter(this.fichier.get(pointeur));
                }
            }
        }
        else if (ligne.contains("tq ") )
        {
            String condition = ligne.replaceAll("tq|alors| ", "");
            condition = remplacerValeurVariable(condition);

            boolean b = Expression.calculLogique(condition);

            EtatLigne e = nouvelleEtatLigne(pointeur);
            e.setCondition(b);

            parcours.nouvelleEtat(e);
            int numLigne = pointeur;

            while ( b ) {
                pointeur = numLigne;
                for ( int i = pointeur+1; !this.fichier.get(i).contains("ftq"); i++ ) {
                    pointeur = i;
                    traiter(fichier.get(i));
                }
                condition = remplacerValeurVariable(fichier.get(numLigne).replaceAll("tq|alors| ", ""));
                b = Expression.calculLogique(condition);
                EtatLigne e2 = nouvelleEtatLigne(numLigne);
                e2.setCondition(b);
                parcours.nouvelleEtat(e2);
            }
            pointeur = numLigne;
            for(int i = pointeur+1; !this.fichier.get(i).contains("ftq");i++){
                pointeur=i;
                EtatLigne eTmp = nouvelleEtatLigne( pointeur );
                eTmp.skip();
                parcours.nouvelleEtat(eTmp);
            }


        }
        else {
            EtatLigne eTmp = nouvelleEtatLigne( pointeur );

            if (ligne.replaceAll(" ", "").equals(""))
                eTmp.skip();

            parcours.nouvelleEtat(eTmp);
        }
    }

    public void rajoutLecture(EtatLigne e, String valeur){
        attenteLecture = false;
        String nom = e.getNomALire();

        ArrayList<String> traceAlgo = e.getTraceAlgo();
        if (  traceAlgo.size() > 0 &&traceAlgo.get( traceAlgo.size()-1).charAt(0) == 'i' )
            traceAlgo.remove( traceAlgo.size()-1);
        e.setTraceAlgo("i"+nom+": "+valeur);


        Variable v = e.getLstVariables().get(nom);

        Variable vTemp = get(nom, v.getType(), valeur);

        v.setVal(vTemp.getVal());
        setVariable(nom, valeur);



        parcours.reecrire(e);
        pointeur = e.getNumLigne()+1;
        lectureFichier();
    }

    public String remplacerValeurVariable(String ligne){
        for(String v : lstConstantes.keySet() ){
            if ( ligne.contains(v) ){
                ligne = ligne.replaceAll(v, lstConstantes.get(v).valToString() );
            }
        }

        for(String v : lstVariables.keySet() ){
            if ( ligne.contains(v) ){
                ligne = ligne.replaceAll(v, lstVariables.get(v).getVal().toString() );
            }
        }

        return ligne;
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
                String[] tailleTab = Fonction.affectation(type);
                String typeTab   = "";
                if (type.contains("entier")   ) typeTab  = "entier";
                if (type.contains("reel")     ) typeTab  = "reel";
                if (type.contains("boolean")  ) typeTab = "boolean";
                if (type.contains("caractere")) typeTab = "caractere";
                if (type.contains("chaine")   ) typeTab = "chaine";

                addTableau(variables[0],typeTab,
                        lstConstantes.containsKey(tailleTab[2])?String.valueOf(lstConstantes.get(tailleTab[2]).getVal()):tailleTab[2],
                        lstConstantes.containsKey(tailleTab[3])?String.valueOf(lstConstantes.get(tailleTab[3]).getVal()):tailleTab[3]!=null?tailleTab[3]:"0",
                        lstConstantes.containsKey(tailleTab[4])?String.valueOf(lstConstantes.get(tailleTab[4]).getVal()):tailleTab[4]!=null?tailleTab[4]:"0");
            } else {
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
    public ArrayList<String>                 getCode()          { return this.code;  }
    public int                               getNbChiffre()     { return (this.fichier.size()+"").length(); }

    public void addConstante(String nom, String type, String valeur){ lstConstantes.put(nom, get(nom, type, valeur) ); }
    public void addVariable (String nom, String type)               { lstVariables .put(nom, new Variable<>(nom, type) ); }

    public void addTableau (String nom, String type, String taille, String taille2, String taille3) {
        lstVariables.put(nom,
                new Variable<>(nom,
                        Integer.parseInt(taille),
                        Integer.parseInt(taille2),
                        Integer.parseInt(taille3),
                        type));
    }

    public Object getConstante (String nom) { return  lstConstantes.get(nom).getVal(); }
    public Object getVariable  (String nom) { return  lstVariables .get(nom).getVal(); }
    public Object getIndTableau(String nom, int ind, int ind2, int ind3) {
        return lstVariables.get(nom).getIndTab(ind, ind2, ind3);
    }

    public void setVariable(String nom, String valeur) {
        if ( valeur.contains("\""))
            valeur = Fonction.entreGuillemet(valeur);
        else if ( valeur.contains("'"))
            valeur = valeur.replaceAll("'", "");
        else if ( Expression.estUneExpressionLogique(valeur) ){
            valeur = Expression.calculLogique(valeur ) +"";
        }
        else if ( Expression.estUneExpression(valeur) ){
            valeur = Expression.calculer(valeur) + "";
        }
        Interpreteur.set(lstVariables.get(nom), valeur);
    }


    public void setTableau (String nom, int ind, int ind2, int ind3, String valeur) {
        if ( valeur.contains("\""))
            valeur = Fonction.entreGuillemet(valeur);
        Variable<Object> v = lstVariables.get(nom);

        v.setIndTab(ind, ind2, ind3, convertitValeur(v.getType(),valeur) );
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

    public static void set(Variable<Object> var, String valeur)   {
        switch (var.getType()){
            case "entier"    -> {
                int val;
                valeur = String.valueOf(Expression.calculer(valeur));
                if(valeur.contains("."))
                    valeur = valeur.substring(0,valeur.indexOf("."));
                val = Integer.parseInt(valeur);
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
        try {
            Scanner sc = new Scanner(new FileInputStream(adresse));

            char charPrecedent =' ';
            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();

                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }
    public static ArrayList<String> lireCode(File adresse) {
        ArrayList<String> fichier = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileInputStream(adresse), StandardCharsets.UTF_8);

            char charPrecedent =' ';
            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();

                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }
}
