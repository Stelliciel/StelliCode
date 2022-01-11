package iut.Stelliciel.StelliCode.CUI;

import iut.Stelliciel.StelliCode.Main;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.Parcours;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Console {
    private final Main ctrl;

    private Scanner sc;
    private ArrayList<String> code;
    private Parcours parcours;

    private String saisie;


    public Console(Main ctrl){
        this.ctrl = ctrl;
        this.sc   = new Scanner(System.in);
        this.code = ctrl.getCode();
        this.parcours = ctrl.getParcours();
        saisie = " ";
        ihm();
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

            afficher(e);
            if ( e.isLecture() ){
                System.out.print("Veuillez saisir la valeur de la variable " + e.getNomALire() + " : ");
                saisie = sc.nextLine();

                while (saisie.equals("") ){
                    System.out.print("Veuillez saisir la valeur de la variable " + e.getNomALire() + " : ");
                    saisie = sc.nextLine();
                }
                ctrl.rajoutLecture(e, saisie);
            }

            System.out.print(">");
            saisie = sc.nextLine();

            System.out.print("+");
            for (int cpt=0; cpt<79; cpt++)
                System.out.print("-");
            System.out.print("+");

            System.out.println();

        }
    }


    public void afficher(EtatLigne e){
        System.out.print("+");
        for (int cpt=0; cpt<79; cpt++)
            System.out.print("-");
        System.out.print("+");

        System.out.println();
        for (int cpt =0; cpt < code.size(); cpt++){
            String ligne = "|" + String.format("%"+ctrl.getNbChiffre()+"s", (cpt+1))  + " " + code.get(cpt);
            ligne = String.format(Locale.US,"%-59s", ligne);

            if ( ligneSkipper(cpt) ) {
                ligne = "\u001B[35m" + ligne + "\u001B[0m        ";
            }


            if ( cpt == e.getNumLigne() ){
                if ( e.isCondition() ){
                    if ( e.isConditionTrue() )
                        ligne = "\u001B[42m" + ligne + "\u001B[0m        ";
                    else
                        ligne = "\u001B[41m" + ligne + "\u001B[0m";
                }
                else
                    ligne = "\u001B[46m" + ligne + "\u001B[0m";
            }

            System.out.println( String.format(Locale.US,"%-60s", ligne + "|").replace ( (char) 8239 , ' ' ) );
        }

        if(e.getTraceAlgo() != null && e.getTraceAlgo().size() > 0 ){
            System.out.println("-----------");
            System.out.println("| CONSOLE |");
            System.out.print("+");
            for (int cpt=0; cpt<79; cpt++)
                System.out.print("-");
            System.out.print("+");

            System.out.println();

            int cpt;

            for ( cpt = e.getTraceAlgo().size()-4; cpt > e.getTraceAlgo().size(); cpt++){
                if ( cpt >=0 ) {
                    if ( e.getTraceAlgo().get(cpt).charAt(0) == 'o'){
                        System.out.println( String.format("%80s", e.getTraceAlgo().get(cpt).substring(1)) );
                    }
                    else
                        System.out.println( String.format("%80s", "\t~>" +e.getTraceAlgo().get(cpt).substring(1)) );
                }
            }
        }



    }

    private boolean ligneSkipper(int cpt) {
        for(EtatLigne e : parcours.getLecteur() ){
            if(e.getNumLigne() == cpt){
                if(e.estSkipper())
                    return true;
                else
                    return false;
            }
        }

        return false;
    }
}
