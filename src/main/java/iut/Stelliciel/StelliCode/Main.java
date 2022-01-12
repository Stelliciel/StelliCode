package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.Console;
import iut.Stelliciel.StelliCode.metier.EtatLigne;
import iut.Stelliciel.StelliCode.metier.Interpreteur;
import iut.Stelliciel.StelliCode.metier.Parcours;
import iut.Stelliciel.StelliCode.metier.Variable;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private final Interpreteur metier;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        metier         = new Interpreteur(new File("C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo"));

        Console console = new Console(this);
    }


    public Parcours getParcours(){ return  metier.getParcours(); }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }
    public HashMap<String, Variable<Object>> getConstantes() { return metier.getLstConstantes(); }
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public void rajoutLecture(EtatLigne e, String saisie) {
        metier.rajoutLecture(e, saisie);
    }
}

