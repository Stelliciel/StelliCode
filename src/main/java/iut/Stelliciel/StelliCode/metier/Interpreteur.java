package iut.Stelliciel.StelliCode.metier;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Interpreteur {

    private final ArrayList<String> fichier;

    private String signature;
    private final HashMap<String, Variable<Object>> lstConstantes;
    private final HashMap<String, Variable<Object>> lstVariables;

    private final Parcours parcours;
    private int   cpt;

    private boolean attenteLecture;


    public Interpreteur(File adresseFichier) {
        this.fichier        = Interpreteur.lireFichier(adresseFichier);

        cpt                 = 0;
        attenteLecture      = false;

        lstConstantes       = new HashMap<>();
        lstVariables        = new HashMap<>();
        parcours            = new Parcours();

        initialisationFichier();

        lectureFichier();

        System.out.println("trace:");
        for(String s: parcours.derniereLigne().getTraceAlgo() ){
            System.out.println(s.substring(1));
        }
    }

    /*--------------------*/
    /* Lecture du fichier */
    private void lectureFichier() {
        if (cpt == 0 ) {
            for (cpt=0; !fichier.get(cpt).contains("DEBUT"); cpt++);

            parcours.nouvelleEtat( nouvelleEtatLigne(cpt) );
        }

        while( parcours.derniereLigne().getNumLigne()+1 < fichier.size() &&
                    !attenteLecture                                          )  {
            String ligne = fichier.get(cpt);
            System.out.println("Lecture:" + (cpt+1) + "|"+ligne);
            traiter( ligne );
            cpt++;
        }
    }

    private void traiter(String ligne){

        if (ligne.contains("<--"))
        {
            System.out.println("\tAffectation:"+(cpt+1)+"|"+ligne);
            String[] separation = Fonction.affectation(ligne);

            separation[1] = remplacerValeurVariable(separation[1]);

            if (separation.length == 3) {
                setTableau(separation[0], Integer.parseInt(separation[2]), separation[1]);
            } else {
                setVariable(separation[0], separation[1]);
            }

            parcours.nouvelleEtat(nouvelleEtatLigne(cpt));
        }
        else if (ligne.contains("écrire"))
        {
            System.out.println("\tEcrire:"+(cpt+1)+"|"+ligne);
            String ecrire = Fonction.entreParenthese(ligne);
            EtatLigne etatLigne = nouvelleEtatLigne(cpt);

            if (ecrire.contains("\",")) {
                String afficher = Fonction.entreGuillemet(ecrire);
                String variable = ecrire.substring(ecrire.indexOf(",") + 1).trim();

                if (estConstante(variable))
                    etatLigne.setTraceAlgo("o"+afficher + " " + getConstante(variable));
                else
                    etatLigne.setTraceAlgo("o"+afficher + " " + getVariable(variable));
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
            System.out.println("\tLire:"+(cpt+1)+"|"+ligne);
            String l = ligne.replaceAll("lire| ", "");
            String var = Fonction.entreParenthese(ligne);

            EtatLigne eL = nouvelleEtatLigne(cpt);
            eL.setLecture(true);
            eL.setNomALire(var);

            attenteLecture = true;
        }
        else if (ligne.contains("si ") )
        {
            System.out.println("\tSi:"+(cpt+1)+"|"+ligne);
            String condition = ligne.replaceAll("si", "").replaceAll("alors", "").trim();
            condition = condition.replaceAll(" ", "");

            condition = remplacerValeurVariable(condition);

            boolean b = Expression.calculLogique(condition);
            EtatLigne eL = nouvelleEtatLigne(cpt);
            eL.setCondition(b);

            parcours.nouvelleEtat(eL);


            if( b ) {
                int i;
                for(i = cpt+1; !(this.fichier.get(i).contains("sinon") || this.fichier.get(i).contains("fsi")); i++) {
                    cpt = i;
                    traiter(this.fichier.get(i));
                }
                while(!(this.fichier.get(i).contains("fsi"))) {
                    cpt = ++i;
                }
            }
            else {
                int i;
                for(i = cpt+1; ! this.fichier.get(i).contains("sinon");i++) cpt=i;
                System.out.println("si/faux ligne:"+(cpt+1)+"|"+this.fichier.get(cpt));
                for(i = cpt+1; !(this.fichier.get(i).contains("fsi")); i++) {
                    cpt = i;
                    traiter(this.fichier.get(i));
                }
            }

            System.out.println("Fin si:" + (cpt) +"|" + fichier.get(cpt));


        }
        else if (ligne.contains("tq ") )
        {
            System.out.println("\tTQ:"+(cpt+1)+"|"+ligne);
            String condition = ligne.replaceAll("tq|alors| ", "");
            condition = remplacerValeurVariable(condition);

            boolean b = Expression.calculLogique(condition);

            System.out.println("\tcondition:"+condition+"->"+b);


            int numLigne = cpt;

            while ( b ) {
                int i = cpt + 1;
                while (!this.fichier.get(i).contains("ftq")) {
                    traiter(fichier.get(i));
                    i++;
                }
                condition = remplacerValeurVariable(fichier.get(numLigne).replaceAll("tq|alors| ", ""));
                System.out.println("\tcondition du tq:"+condition);
                b = Expression.calculLogique(condition);
            }

            for(int i = cpt+1; !this.fichier.get(i).contains("ftq");i++){
                System.out.println("pacoure:"+fichier.get(i));
                cpt=i;
            }


        }
        else {
            parcours.nouvelleEtat( nouvelleEtatLigne(cpt) );
        }


    }

    public void rajoutLecture(String nom, String valeur){
        attenteLecture = false;

        Variable v = parcours.derniereLigne().getLstVariables().get(nom);

        Variable vTemp = get(nom, v.getType(), valeur);
        v.setVal(vTemp.getVal());
        setVariable(nom, valeur);
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
    public void addVariable (String nom, String type)               { lstVariables .put(nom, new Variable<>(nom, type) ); }
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
        Interpreteur.set(lstVariables.get(nom), valeur);}
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
        try{
            Scanner sc = new Scanner(new FileInputStream(adresse), "ISO-8859-1");

            char charPrecedent =' ';
            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();

                fichier.add(ligne);
            }
            sc.close();
        } catch (Exception e){ e.printStackTrace(); }

        return fichier;
    }

    public ArrayList<String> changLig(char dir) {
        if(dir == 'f'){
            return parcours.next().getTraceAlgo();
        }else if(dir == 'b'){
            return parcours.prec().getTraceAlgo();
        }else{
            return null;
        }
    }

    public static void main(String[] args) {
        new Interpreteur(new File("C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo"));
    }
}
