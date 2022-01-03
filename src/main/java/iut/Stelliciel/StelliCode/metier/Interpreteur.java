package iut.Stelliciel.StelliCode.metier;

import iut.Stelliciel.StelliCode.Main;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

public class Interpreteur {

    private ArrayList<String> fichier;
    private final Main ctrl;

    public Interpreteur(Main ctrl, String adresseFichier) {
        this.ctrl    = ctrl;
        this.fichier = Interpreteur.lireFichier(adresseFichier);
    }


    public static ArrayList<String> lireFichier(String adresse) {
        ArrayList<String> fichier = new ArrayList<>();

        try
        {
            Scanner sc = new Scanner ( new FileInputStream ( adresse ) );

            while ( sc.hasNextLine() ) {
                String ligne = sc.nextLine();
                System.out.println(ligne);
                fichier.add(ligne);
            }
            sc.close();
        }
        catch (Exception e){ e.printStackTrace(); }


        return fichier;
    }

    public ArrayList<String> getCode() {
        return this.fichier;
    }

    public int getNbChiffre() {
        return (this.fichier.size()+"").length();
    }
}
