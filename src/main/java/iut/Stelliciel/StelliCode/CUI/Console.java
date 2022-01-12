package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

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

    public static final int TAILLE_LARGEUR = 94;


    public Console(Main ctrl){
        this.ctrl = ctrl;
        this.sc   = new Scanner(System.in);
        this.code = ctrl.getCode();
        this.parcours = ctrl.getParcours();
        saisie = " ";
        this.tabVar = new AfficheTab(ctrl);
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

        while ( !saisie.equals("-1") ){

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
            else if ( saisie.startsWith("L") ){
                if ( parcours.seRendreA( Integer.parseInt(saisie.replaceAll("L", ""))) != null ) {
                    e = parcours.seRendreA( Integer.parseInt(saisie.replaceAll("L", "")) );
                }
            }
            else if ( saisie.startsWith("addvar") ){
                demandeVars();
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
            }


            System.out.print(">");
            saisie = sc.nextLine();

            System.out.print("+");
            for (int cpt=0; cpt<TAILLE_LARGEUR-1; cpt++)
                System.out.print("-");
            System.out.println("+");

        }
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
        Console.majConsole();
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
            String num = "|" + String.format("%"+ctrl.getNbChiffre()+"s", (cpt+1))  + " ";;
            String ligne =  String.format("%-60s", code.get(cpt) );
            String ligneVar;
            if ( departNumLigne == 0 ){
                if (cpt < tab.size() )
                    ligneVar = tab.get(cpt);
                else
                    ligneVar = String.format("%30s", " ");
            }
            else {
                if ( cpt - departNumLigne < tab.size() )
                    ligneVar = tab.get(cpt - departNumLigne);
                else
                    ligneVar = String.format("%30s", " ");
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
        if(e.getTraceAlgo() != null && e.getTraceAlgo().size() > 0 ){
            //System.out.println("-----------");
            System.out.println("| CONSOLE |                                                                                   |");

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
        }
        System.out.println(ansi().reset());


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
}
