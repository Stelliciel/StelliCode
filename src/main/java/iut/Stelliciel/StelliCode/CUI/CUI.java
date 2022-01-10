package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.CUI.affichage.AfficheCode;
import iut.Stelliciel.StelliCode.CUI.console.AfficheConsole;
import iut.Stelliciel.StelliCode.CUI.tabVariable.AfficheTab;
import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import  java.lang.ProcessBuilder;
import  java.lang.Process;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CUI {
    private final AfficheTab affTabVar;
    private final AfficheConsole affConsole;
    private final AfficheCode affCode;

    private int numLig1;
    private int ligEnCour;
    private ArrayList<String> arrNom;

    public CUI(){
        this.affTabVar   = new AfficheTab    ();
        this.affConsole  = new AfficheConsole();
        this.affCode     = new AfficheCode   (Main.getInstance().getCode(),Main.getInstance().getNbChiffre());
        this.numLig1     = 0;
        this.ligEnCour   = getLigDebut();
        this.arrNom      = new ArrayList<>();
    }

    public static String adaptTxt(String in){
        if(in.length()<10){return  in;}
        else{
            return in.substring(0,5)+".."+in.substring(in.length()-3,in.length());
        }
    }

    public void demandeVars(){
        this.afficher();
        System.out.println("Quelles variables voulez vous suivre?");
        HashMap<String ,Variable<Object>> lstVar = Main.getInstance().getVariables();
        StringBuilder sRep = new StringBuilder();
        /*A MODIFIER*/
        int numVar = 1;
        for(String nom : lstVar.keySet()){
            if(numVar % 5 == 1 )
                sRep.append('\n');
            sRep.append(numVar).append(" ").append(CUI.adaptTxt(nom)).append("  ");
            numVar ++;
        }
        System.out.println(sRep);
        String inUser = Main.getInstance().saisie();
        if(inUser.matches("\\d+")){
            if(Integer.parseInt(inUser)-1 >=lstVar.size()){
                System.out.println("entrer un nombre valide");
            }
            else{
                int cpt=0;
                for (String s: lstVar.keySet() ) {
                    if (cpt == Integer.parseInt(inUser)-1) {
                        arrNom.add(lstVar.get(s).getNom());
                    }
                    cpt++;
                }
            }
        }else{
            System.out.println("entrer un nombre valide");
        }
    }

    public void scroll(int num){
        this.numLig1 += num;
    }

    public void afficher(){
        String sTabVar = affTabVar.toString();
        StringBuilder affichage = new StringBuilder();
        affichage.append("________________________________________________________________________________________________________________\n");
        for (int i = this.numLig1; i < this.numLig1+40; i++) {
            if ( i < Main.getInstance().getCode().size() )
                affichage.append(this.affLig(i, this.ligEnCour));
        }
        affichage.append("_______________________________________________________________________________________________________________|\n                                                                                                                \nconsole                                                                                                         \n________________________________________________________________________________________________________________\n");
        affichage.append(this.affConsole);
        this.majConsole();
        System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a(affichage.toString()).reset());
    }

    public void proposeChoix(){
        String inUser = Main.getInstance().saisie();
        if(inUser.equals("m")) {
            if (ligEnCour != affCode.getTaillePro() - 1) {
                affConsole.Ajouter(controleur.changLig('f'));
                if(ligEnCour +1 == numLig1 +30){scroll(10);}
                this.ligEnCour++;
                majInOut();
            }
        }else if(inUser.equals("b")) {
            if(ligEnCour != getLigDebut()){
                affConsole.Ajouter(controleur.changLig('b'));
                if(ligEnCour -1 == numLig1 +10 && ligEnCour-1 != 10){scroll(-10);}
                this.ligEnCour--;
                majInOut();}
        }else if (inUser.substring(4).equals("+ bk")) {
            if (inUser.substring(5).matches("\\D+") || Integer.parseInt(inUser.substring(5)) > affCode.getTaillePro()) {
                System.out.println("entrer un nombre inférieur au nombre de ligne");}
            else{
                    //addBK(Integer.parseInt(inUser.substring(5)));
                    // controleur.addBK(Integer.parseInt(inUser.substring(5)));
            }
        } else if(inUser.startsWith("addvar")){
            demandeVars();
        }

        //point d'arret +/-/go bk (x/x/)
        //quitter       q
        //pas a pas     entrée
        //ligne précise Lx
        //stop boucle itteration l-x
        //detail        det var Nom
        //    copie det    pp
        //    quiter       entrée
        //trace
    }

    private void majInOut() {
        affConsole.Ajouter('i',"rest");
    }

    public void sendVar(ArrayList<String> arrNom, EtatLigne lig){
        for (String nom: arrNom) {
            affTabVar.maj(lig.getLstVar().get(nom));
        }
    }

    public ArrayList<String> getArrNom() {
        return arrNom;
    }

    public int getLigDebut(){
        int cpt =0;
        for (String s:affCode.getArrString()) {
            if (s.equals("DEBUT")){return  cpt;}
            cpt++;
        }
        return -1;
    }

    public void majConsole(){
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
            System.out.println(e);
        }
    }

    public int getLigEnCour() {
        return ligEnCour;
    }

    private String affLig(int numLig, int ligEncour,String sTabVar){
        String espace = " ";
        return ("| "+ this.affTabVar.affLig(sTabVar,numLig) + " |" + (this.affCode.affLig(numLig,ligEncour)))+espace.repeat(80-this.affCode.getTaille(numLig))+"|\n";
    }

    public static File getAdresse() {
        File files = afficherOption();
        System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a("File : " + files).reset());
        return files;
    }

    private static File afficherOption() {
        ArrayList<File> file = new ArrayList<>();
        File[] dir = Objects.requireNonNull((new File("../../src/main/resources")).listFiles());
        for (File item: dir)
            if (item.isFile() && item.getName().endsWith(".algo"))
                file.add(item);

        StringBuilder sRep = new StringBuilder();
        sRep.append("Veuillez choisir une option parmit les suivantes :\n");
        for (int i = 1; i-1 <= file.size(); i++) {
            if(i % 5 == 1 )
                sRep.append('\n');
            if (i-1==file.size())
                sRep.append(i).append(" ").append(CUI.adaptTxt("autre")).append("  ");
            else
                sRep.append(i).append(" ").append(CUI.adaptTxt(file.get(i-1).getName().substring(0, file.get(i-1).getName().length()-5))).append("  ");
        }
        while (true) {
            System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a(sRep).reset());
            String inUser = Main.getInstance().saisie();
            if (inUser.matches("^[-+]?\\d+")) {
                if (Integer.parseInt(inUser) - 1 > file.size()) {
                    System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a("entrer un nombre valide ou -1 pour quitter").reset());
                } else if (inUser.equals("-1")) {
                    System.out.println("Vous avez choisie de quitter le programme");
                    System.exit(0);
                } else if (Integer.parseInt(inUser) - 1 == file.size()) {
                    System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a("Donnez le chemin absolue de votre fichier .algo").reset());
                    inUser = Main.getInstance().saisie();
                    File customDir = new File(inUser);
                    if (customDir.exists() && customDir.isFile() && customDir.getName().endsWith(".algo")) {
                        return customDir;
                    } else {
                        System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a("Le chemin absolue du fichier .algo spécifié n'est pas reconnu").reset());
                    }
                } else {
                    for (int i = 0; i<file.size(); i++) {
                        if (i == Integer.parseInt(inUser) - 1) {
                            return file.get(i).getAbsoluteFile();
                        }
                    }
                }
            } else {
                System.out.println(ansi().bgRgb(255,255,255).fgRgb(0,0,0).a(inUser + " n'est pas valide ,entrer un nombre valide ou -1 pour quitter").reset());
            }
        }
    }
}
