package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.CUI;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.LectureCouleur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private final Interpreteur metier;
    private final CUI ihm;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        metier         = new Interpreteur(CUI.getAdresse());
        ihm            = new CUI();
        ihm.afficher();
        String sUser = "-1";
        while (!sUser.equals("q")){
            ihm.afficher();
            ihm.proposeChoix();
            affecterVariables(ihm.getArrNom());
        }
    }

    public Parcours getParcour(){
        return metier.getParcours();
    }

    public void affecterVariables(ArrayList<String> lstNom){
        ihm.sendVar(lstNom,metier.getEtatVar(ihm.getLigEnCour()));
    }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }
    public HashMap<String, Variable<Object>> getConstantes() { return metier.getLstConstantes(); }
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public String saisie()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void main(String[] args) {
        new Main();
    }

    public ArrayList<String> changLig(char dir) {
        return metier.changLig(dir);
    }

    public ArrayList<String> getTextConsole(int lig){
        return metier.getEtatVar(lig).getTraceAlgo();
    }
}

