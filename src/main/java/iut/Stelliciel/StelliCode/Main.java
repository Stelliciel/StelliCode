package iut.Stelliciel.StelliCode;

import iut.Stelliciel.StelliCode.CUI.Console;
import iut.Stelliciel.StelliCode.metier.*;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private final Interpreteur metier;
    //private final CUI ihm;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static Main instance;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;
        AnsiConsole.systemInstall();
        //metier         = new Interpreteur(new File("C:\\Stelliciel\\StelliCode\\src\\main\\resources\\Code.algo"));
        metier         = new Interpreteur(new File("C:\\Users\\Gaspard\\IdeaProjects\\StelliCode\\src\\main\\resources\\Code.algo"));
        //ihm            = new CUI();
        Console console = new Console(this);
        /*ihm.afficher();
        String sUser = "-1";
        while (!sUser.equals("q")){
            ihm.afficher();
            ihm.proposeChoix();
            affecterVariables(ihm.getArrNom());
        }*/
    }


    public Parcours getParcours(){ return  metier.getParcours(); }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<String> getCode(){
        return metier.getCode();
    }
    public HashMap<String, Variable<Object>> getConstantes() { return metier.getLstConstantes(); }
    public HashMap<String, Variable<Object>> getVariables()  { return metier.getLstVariables(); }
    public Object getVariable(String nom)  { return metier.getVariable(nom); }

    public int getNbChiffre() {
        return  metier.getNbChiffre();
    }

    public String saisie()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public ArrayList<String> changLig(char dir) {
        return metier.changLig(dir);
    }

    public ArrayList<String> getTextConsole(int lig){
        return metier.getEtatVar(lig).getTraceAlgo();
    }

    public void rajoutLecture(EtatLigne e, String saisie) {
        metier.rajoutLecture(e, saisie);
    }
}

