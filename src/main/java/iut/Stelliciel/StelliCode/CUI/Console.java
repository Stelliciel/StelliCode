package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class Console {
    private final Main ctrl;

    private Scanner sc;
    private ArrayList<String> code;
    private Parcours parcours;

    private String saisie;
    private AfficheTab tabVar;

    private final LectureCouleur lectureCouleur = new LectureCouleur();
    public final int NOR_FOND   = LectureCouleur.getCouleur("defaut").getCouleurFond();
    public final int NOR_TEXT   = LectureCouleur.getCouleur("defaut").getCouleurText();
    public final int FON_TEXT   = LectureCouleur.getCouleur("fonction").getCouleurText();
    public final int VAR_TEXT   = LectureCouleur.getCouleur("variable").getCouleurText();
    public final int COURS_FOND = LectureCouleur.getCouleur("ligneEnCour").getCouleurFond();
    public final int COND_TEXT  = LectureCouleur.getCouleur("conditionBoucles").getCouleurText();
    public final int COM_TEXT   = LectureCouleur.getCouleur("commentaire").getCouleurText();
    public final int VRAI_COND  = LectureCouleur.getCouleur("condVrai").getCouleurFond();
    public final int FAUX_COND  = LectureCouleur.getCouleur("condFaux").getCouleurFond();
    public final int LIRE_TEXT  = LectureCouleur.getCouleur("lire").getCouleurText();
    public final int ECRIRE_TEXT= LectureCouleur.getCouleur("ecrire").getCouleurText();
    public final int BK_TEXT    = LectureCouleur.getCouleur("breakPoint").getCouleurText();

    public static final int TAILLE_LARGEUR = 84;

    private ArrayList<Integer> lstPointArret;


    public Console(Main ctrl){
        this.ctrl = ctrl;
        this.sc   = new Scanner(System.in);
        this.code = ctrl.getCode();
        this.parcours = ctrl.getParcours();
        saisie = " ";
        this.tabVar = new AfficheTab(ctrl);
        this.lstPointArret = new ArrayList<Integer>();

        ihm();
    }

    public static String adaptTxt(String in){
        if(in.length()<10){return  in;}
        else{
            return in.substring(0,5)+".."+in.substring(in.length()-3);
        }
    }

    public Ansi colorie(String ligne, int couleurLettre, int couleurFond){
        if ( couleurFond == -1 ) {
            return ansi().fgRgb(couleurLettre).a(ligne).fgRgb(NOR_TEXT).bgRgb(NOR_FOND);
        }
        else if ( couleurLettre == -1 ){
            return ansi().bgRgb(couleurFond).a(ligne).fgRgb(NOR_TEXT).bgRgb(NOR_FOND);
        }
        return ansi().bgRgb(couleurFond).fgRgb(couleurLettre).a(ligne).fgRgb(NOR_TEXT).bgRgb(NOR_FOND);
    }

    public void ihm(){
        EtatLigne e = parcours.next();
        afficher(e);

        while ( !saisie.equals("exit") ){

            if ( saisie.equals("") ) {
                if (parcours.hasNext() ){
                    e = parcours.next();
                }
            }
            else if ( saisie.equals("b") ) {
                if( parcours.hasPrec() ) {
                    e = parcours.prec();
                }
            }
            else if ( saisie.startsWith("L")  ){
                if ( saisie.length() > 1 && saisie.substring(1).matches("\\d+") )
                    if ( parcours.seRendreA( Integer.parseInt(saisie.replaceAll("L", ""))) != null ) {
                        e = parcours.seRendreA( Integer.parseInt(saisie.replaceAll("L", "")) );
                    }
            }
            else if ( saisie.contains("bk") ) {
                String[] tab = saisie.split(" ");

                if ( tab.length == 3 && tab[2].matches("\\d+") ) {
                    switch ( tab[0] ){
                        case "+" -> {
                            if ( !lstPointArret.contains( Integer.parseInt(tab[2]) ) )
                                lstPointArret.add(Integer.parseInt(tab[2]) );
                        }
                        case "-" -> {
                            if ( lstPointArret.contains( Integer.parseInt(tab[2]) ) )
                                lstPointArret.remove( lstPointArret.indexOf(Integer.parseInt(tab[2]) ) );
                        }
                    }
                }
                else if ( tab[0].equals("go") ){
                    int numLigne;
                    for(numLigne=0; lstPointArret.get(numLigne) < e.getNumLigne(); numLigne++ );


                    e = parcours.seRendreA( lstPointArret.get(numLigne) ) ;
                }
            }
            else if ( saisie.startsWith("addvar") ){
                demandeVars();
            }
            else if ( saisie.startsWith("TRACE") ){
                ctrl.traceVariable( tabVar.getLstVar() );
            }
            else if (saisie.startsWith("DET var")){
                String[] tab = saisie.split(" ");
                String nomVal = tab[2];
                if ( e.getLstVariables().containsKey(nomVal) )
                    detailler(e, nomVal);
            }

            afficher(e);

            if ( e.isLecture() ){
                System.out.print("| Saisir la valeur de " + e.getNomALire() + " : ");
                saisie = sc.nextLine();
                while (saisie.equals("") ){
                    System.out.print("| Erreur saisie de " + e.getNomALire() + " : ");
                    saisie = sc.nextLine();
                }

                ctrl.rajoutLecture(e, saisie);

                if (parcours.hasNext() ){
                    e = parcours.next();
                }
                afficher(e);
            }

            System.out.print(">");
            saisie = sc.nextLine();

            trait();
        }
    }

    private void detailler(EtatLigne e, String nomVal) {
        Console.majConsole();
        String aff = "";
        if ( e.getLstVariables().get(nomVal).estTableau() ) {
            Object[][][] tabValeur = e.getLstVariables().get(nomVal).getTabValeur();
            System.out.println(nomVal);

            if (tabValeur[0][0].length > 1){
                aff = ecrireTab(3, tabValeur);
            }
            else if (tabValeur[0].length > 1){
                aff = ecrireTab(2, tabValeur);
            }
            else{
                aff = ecrireTab(1, tabValeur);
            }


        }
        else{
            aff = nomVal + " = " + e.getLstVariables().get(nomVal).getVal();
        }
        System.out.println( aff );

        saisie = sc.nextLine();
        while ( !saisie.equals("") ){
            if ( saisie.equals("PP") ){
                try
                {
                    StringSelection ss = new StringSelection(aff);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
                }
                catch( Exception ex) { ex.printStackTrace(); }
            }
            saisie = sc.nextLine();
        }
    }

    private String ecrireTab(int i, Object[][][] tabValeur) {
        String s = "";
        if ( i == 1 ){
            for(int cpt = 0 ; cpt < tabValeur.length; cpt++ )
                s += "["+cpt+"]="+tabValeur[cpt][0][0]+"\n";
            return s;
        }
        if (i == 2) {
            s+= " ";
            for (int cpt = 0; cpt < tabValeur[0].length; cpt++){
                s+= "|" +String.format("%-10s", cpt);
            }
            s+= "|\n";

            for(int cpt1 = 0 ; cpt1 < tabValeur.length; cpt1++ ){
                s+= cpt1+"|";
                for(int cpt2 = 0 ; cpt2 < tabValeur[0].length; cpt2++ ){
                    s += String.format("%-10s", tabValeur[cpt1][cpt2])+"|";
                }

                s+= "\n";
            }

            return s;
        }
        if (i == 3) {


            for(int cpt1 = 0 ; cpt1 < tabValeur.length; cpt1++ )
            {
                s+= "tab["+cpt1+"][?][?]\n ";
                for (int cpt = 0; cpt < tabValeur[0][0].length; cpt++){
                    s+= "|" +String.format("%-10s", cpt) ;
                }
                s+= "|\n";
                for(int cpt2 = 0 ; cpt2 < tabValeur[0].length; cpt2++ )
                {
                    s+= cpt2+"|";
                    for(int cpt3=0; cpt3 < tabValeur[0][0].length; cpt3++){
                        s += String.format("%-10s", tabValeur[cpt1][cpt2][cpt3] ) + "|";
                    }
                    s+= "\n";
                }
            }

            return s;
        }

        return s;
    }

    public String afficherListeNomVar(ArrayList<String> lst){
        String liste = "";
        String ligne = "";
        int numVar = 1;
        for(String nom : lst){
            ligne += numVar+")"+Console.adaptTxt(nom)+"  ";
            numVar ++;
            if(numVar % 5 == 1 ){
                liste += String.format("%-"+ TAILLE_LARGEUR +"s","|"+ ligne) + "|\n";
                ligne = "";
            }
        }
        if ( numVar % 5 != 1 )
            liste += String.format("%-"+ TAILLE_LARGEUR +"s","|"+ ligne) + "|\n";
        return  liste;
    }

    public static void majConsole(){
        try{
            String operatingSystem = System.getProperty("os.name").toLowerCase();

            if(operatingSystem.contains("win")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> getVariables(){
        ArrayList<String> lstNomVar = new ArrayList<>();
        HashMap<String , Variable<Object>> lstVar = ctrl.getVariables();

        lstVar.forEach( (k,v) ->lstNomVar.add(k));
        return lstNomVar;
    }

    public void demandeVars(){
        System.out.println(String.format("%-"+ TAILLE_LARGEUR +"s","| Quelles variables voulez vous suivre? ( q pour quitter )" ) + "|");
        ArrayList<String> lstNomVar = getVariables();
        HashMap<String , Variable<Object>> lstVar = ctrl.getVariables();

        saisie = "";

        while ( !saisie.equals("q") ){
            System.out.print(afficherListeNomVar(lstNomVar));
            System.out.print("|>");
            saisie = sc.nextLine();
            if(saisie.matches("\\d+")){
                if(Integer.parseInt(saisie)-1 >=lstVar.size()){
                    System.out.println( String.format("%-" + TAILLE_LARGEUR +"s", "| Entrer un nombre valide") +"|");
                }
                else{
                    String nom = "";
                    int cpt=0;
                    for (String s: lstNomVar ) {
                        if (cpt == Integer.parseInt(saisie)-1) {
                            nom = s;
                        }
                        cpt++;
                    }
                    tabVar.rajouterVar(nom, lstVar.get(nom));
                    lstNomVar.remove(nom);
                    System.out.println(String.format("%-"+ TAILLE_LARGEUR +"s","| Quelles variables voulez vous suivre? ( q pour quitter )" ) + "|");
                }
            }else{
                System.out.println( String.format("%-" + TAILLE_LARGEUR +"s", "| Entrer un nombre valide") +"|");
            }
        }

    }

    private void trait(){
        System.out.print(ansi().fgRgb(NOR_TEXT).bgRgb(NOR_FOND).a("+"));
        for (int cpt=0; cpt<TAILLE_LARGEUR-1; cpt++)
            System.out.print("-");
        System.out.println("+");
    }

    public void afficher(EtatLigne e){
        //Console.majConsole();
        trait();
        tabVar.maj(e.getLstVariables());
        ArrayList<String> tab = tabVar.getTabVar();
        //System.out.println(ansi().bgRgb(NOR_FOND).fgRgb(NOR_TEXT));
        int  departNumLigne;
        if ( e.getNumLigne() < 20 )
            departNumLigne = 0;
        else{
            departNumLigne = e.getNumLigne()/20*20;
            if ( code.size() < departNumLigne + 40  )
                departNumLigne = code.size() - 40;
        }

        for (int cpt = departNumLigne;  cpt < departNumLigne+40 && cpt < code.size(); cpt++){
            String num =  String.format("%"+ctrl.getNbChiffre()+"s", (cpt+1));
            for ( Integer i : lstPointArret )
                if ( i == cpt+1 ){
                    num =  colorie(num, BK_TEXT, -1).toString() ;
                }

            num = "|" + String.format("%"+ctrl.getNbChiffre()+"s", num) + " ";

            String ligne =  String.format("%-60s", code.get(cpt) );
            String ligneVar;
            if ( departNumLigne == 0 ){
                if (cpt < tab.size() )
                    ligneVar = tab.get(cpt);
                else
                    ligneVar = String.format("%20s", " ");
            }
            else {
                if ( cpt - departNumLigne < tab.size() )
                    ligneVar = tab.get(cpt - departNumLigne);
                else
                    ligneVar = String.format("%20s", " ");
            }

            if ( cpt == e.getNumLigne() ){
                if ( e.isCondition() ){
                    if ( e.isConditionTrue() ){
                        ligne = colorie(ligne, NOR_TEXT, VRAI_COND).toString();
                    }
                    else{
                        ligne = colorie(ligne, NOR_TEXT, FAUX_COND).toString();
                    }
                }
                else{
                    ligne = colorie(ligne, NOR_TEXT, COURS_FOND).toString();
                }
            }
            else  if ( ligneSkipper(cpt) && cpt <= e.getNumLigne() ) {
                ligne = colorie(ligne, COM_TEXT, -1).toString();
            }
            else
                ligne = coloration(ligne, tabVar.getLstVar());

            System.out.println( ligneVar + num + String.format(Locale.US,"%-60s", ligne )+ "|" );
        }
        trait();

        /*----------- */
        /*| CONSOLE | */
        System.out.println( String.format("%-" +TAILLE_LARGEUR + "s", "| CONSOLE") +"|" );

        trait();

        int cpt;
        for ( cpt = e.getTraceAlgo().size()-3; cpt < e.getTraceAlgo().size(); cpt++){
            if ( cpt >= 0 ) {
                if ( e.getTraceAlgo().get(cpt).charAt(0) == 'o'){
                    System.out.println( String.format("%-" +TAILLE_LARGEUR + "s", "|"+ e.getTraceAlgo().get(cpt).substring(1)) +"|" );
                }
                else
                    System.out.println( String.format("%-" +TAILLE_LARGEUR + "s", "|"+"    ~>" +e.getTraceAlgo().get(cpt).substring(1)) +"|" );
            }
        }

        //System.out.print(ansi().reset());


    }

    public String  coloration(String s,ArrayList<String> lstVar){
        if (s.contains("//")){return  coloration(s.substring(0,s.indexOf("//")),lstVar)+ansi().fgRgb(COM_TEXT).a(s.substring(s.indexOf("//"))).reset().fgRgb(NOR_TEXT).bgRgb(NOR_FOND);}
        String[] tabFonct = {"plancher","plafond","enChaine13","enReel","enEntier","car","ord"};
        String[] tabCond = {"si", "alors","sinon","fsi","tq","ftq","faire"};
        String sRep = s;
        sRep = sRep.replaceAll("\\blire\\b",ansi().fgRgb(LIRE_TEXT).a("lire").fgRgb(NOR_TEXT).toString());
        sRep = sRep.replaceAll("\\b\u00e9crire\\b",ansi().fgRgb(ECRIRE_TEXT).a("\u00e9crire").fgRgb(NOR_TEXT).toString());
        for (String fonct: tabFonct) {
            sRep = sRep.replaceAll("\\b"+fonct+"\\b",ansi().fgRgb(FON_TEXT).a(fonct).fgRgb(NOR_TEXT).toString());
        }
        for (String cond : tabCond) {
            sRep = sRep.replaceAll("\\b"+cond+"\\b",ansi().fgRgb(COND_TEXT).a(cond).fgRgb(NOR_TEXT).toString());
        }
        if(lstVar != null && ! lstVar.isEmpty()){
            for (String var:lstVar) {
                sRep=sRep.replaceAll("\\b"+var+"\\b",ansi().fgRgb(VAR_TEXT).a( var ).fgRgb(NOR_TEXT).toString());
            }
        }
        return sRep;
    }

    private boolean ligneSkipper(int cpt) {
        for(EtatLigne e : parcours.getLecteur() ){
            if(e.getNumLigne() == cpt){
                if(e.estSkipper())
                    return true;
            }
        }

        return false;
    }

    public static File getAdresse() {
        Console.majConsole();
        File files = afficherOption();
        System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a("File : " + files).reset());
        return files;
    }

    public static File afficherOption() {
        ArrayList<File> file = new ArrayList<>();
        File[] dir = Objects.requireNonNull((new File("../../src/main/resources")).listFiles());
        for (File item : dir)
            if (item.isFile() && item.getName().endsWith(".algo"))
                file.add(item);

        StringBuilder sRep = new StringBuilder();
        sRep.append("Veuillez choisir une option parmit les suivantes :\n");
        for (int i = 1; i - 1 <= file.size(); i++) {
            if (i % 5 == 1)
                sRep.append('\n');
            if (i - 1 == file.size())
                sRep.append(i).append(" ").append(Console.adaptTxt("autre")).append("  ");
            else
                sRep.append(i).append(" ").append(Console.adaptTxt(file.get(i - 1).getName().substring(0, file.get(i - 1).getName().length() - 5))).append("  ");
        }
        while (true) {
            System.out.println(ansi().bgRgb(255, 255, 255).fgRgb(0, 0, 0).a(sRep).reset());
            String inUser = Main.getInstance().saisie();
            if (inUser.matches("^[-+]?\\d+")) {
                if (Integer.parseInt(inUser) - 1 > file.size()) {
                    System.out.println(ansi().bgRgb(255, 255, 255).fgRgb(0, 0, 0).a("entrer un nombre valide ou -1 pour quitter").reset());
                } else if (inUser.equals("-1")) {
                    System.out.println("Vous avez choisie de quitter le programme");
                    System.exit(0);
                } else if (Integer.parseInt(inUser) - 1 == file.size()) {
                    System.out.println(ansi().bgRgb(255, 255, 255).fgRgb(0, 0, 0).a("Donnez le chemin absolue de votre fichier .algo").reset());
                    inUser = Main.getInstance().saisie();
                    File customDir = new File(inUser);
                    if (customDir.exists() && customDir.isFile() && customDir.getName().endsWith(".algo")) {
                        return customDir;
                    } else {
                        System.out.println(ansi().bgRgb(255, 255, 255).fgRgb(0, 0, 0).a("Le chemin absolue du fichier .algo spécifié n'est pas reconnu").reset());
                    }
                } else {
                    for (int i = 0; i < file.size(); i++) {
                        if (i == Integer.parseInt(inUser) - 1) {
                            return file.get(i).getAbsoluteFile();
                        }
                    }
                }
            } else {
                System.out.println(ansi().bgRgb(255, 255, 255).fgRgb(0, 0, 0).a(inUser + " n'est pas valide ,entrer un nombre valide ou -1 pour quitter").reset());
            }
        }
    }
}
