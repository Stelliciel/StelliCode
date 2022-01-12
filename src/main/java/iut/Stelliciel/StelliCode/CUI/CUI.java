/*
package iut.Stelliciel.StelliCode.CUI;

//import iut.Stelliciel.StelliCode.CUI.affichage.AfficheCode;
import iut.Stelliciel.StelliCode.CUI.console.AfficheConsole;
import iut.Stelliciel.StelliCode.CUI.tabVariable.AfficheTab;
import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.Variable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class CUI {
    private final AfficheTab affTabVar;
    private final AfficheConsole affConsole;
    //private final AfficheCode affCode;

    private int numLig1;
    private int ligEnCour;
    private ArrayList<String> arrNom;

    public CUI(){
        this.affTabVar   = new AfficheTab    ();
        this.affConsole  = new AfficheConsole();
        //this.affCode     = new AfficheCode   (Main.getInstance().getCode(),Main.getInstance().getNbChiffre());
        this.numLig1     = 0;
        this.ligEnCour   = getLigDebut();
        this.arrNom      = new ArrayList<>();
    }

    public void demandeVars(){
        this.afficher();
        System.out.println("Quelles variables voulez vous suivre?");
        HashMap<String ,Variable<Object>> lstVar = Main.getInstance().getVariables();
        StringBuilder sRep = new StringBuilder();
        */
/*A MODIFIER*//*

        int numVar = 1;
        for(String nom : lstVar.keySet()){
            if(numVar % 5 == 1 )
                sRep.append('\n');
            sRep.append(numVar).append(" ").append(Console.adaptTxt(nom)).append("  ");
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
                        //affCode.setLstVar(arrNom);
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
        String affichage = "";
        affichage+=("________________________________________________________________________________________________________________\n");
        for (int i = this.numLig1; i < this.numLig1+40; i++) {
            if ( i < Main.getInstance().getCode().size() )
                affichage+=(this.affLig(i, this.ligEnCour, sTabVar));
        }
        affichage+=("_______________________________________________________________________________________________________________|\n                                                                                                                \nconsole                                                                                                         \n________________________________________________________________________________________________________________\n");
        affichage+=(this.affConsole);
        Console.majConsole();
        //System.out.println(ansi().bgRgb(affCode.NOR_FOND).fgRgb(affCode.NOR_TEXT).a(affichage).reset());
    }

    public void proposeChoix(){
        String inUser = Main.getInstance().saisie();
        if(inUser.isEmpty()) {
            if (ligEnCour != affCode.getTaillePro() - 1) {
                this.ligEnCour ++;
                affConsole.Ajouter(Main.getInstance().changLig('f'));
                if(ligEnCour +1 == numLig1 +30){scroll(10);}
                majInOut(Main.getInstance().getTextConsole(ligEnCour));
            }
        }else if(inUser.equals("b")) {
            if(ligEnCour != getLigDebut()){
                this.ligEnCour--;
                affConsole.Ajouter(Main.getInstance().changLig('b'));
                if(ligEnCour -1 == numLig1 +10 && ligEnCour-1 != 10){scroll(-10);
                majInOut(Main.getInstance().getTextConsole(ligEnCour-1));}
            }
        }else if (inUser.substring(4).equals("+ bk")) {
            if (inUser.substring(5).matches("\\D+") || Integer.parseInt(inUser.substring(5)) > affCode.getTaillePro()) {
                System.out.println("entrer un nombre inférieur au nombre de ligne");}
//            else{
//                addBK(Integer.parseInt(inUser.substring(5)));
//                Main.getInstance().addBK(Integer.parseInt(inUser.substring(5)));
//            }
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

    private void majInOut(ArrayList<String> arrS) {
        affConsole.Ajouter(arrS);
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
            if (s.equals("DEBUT")){
                return  cpt;}
            cpt++;
        }
        return -1;
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
                sRep.append(i).append(" ").append(Console.adaptTxt("autre")).append("  ");
            else
                sRep.append(i).append(" ").append(Console.adaptTxt(file.get(i-1).getName().substring(0, file.get(i-1).getName().length()-5))).append("  ");
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

*/
