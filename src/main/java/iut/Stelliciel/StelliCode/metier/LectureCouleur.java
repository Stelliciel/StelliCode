package iut.Stelliciel.StelliCode.metier;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import java.util.*;

public class LectureCouleur {
    private int valR,valG,valB;

    private Element racine;
    private static List<Couleur> lstCouleur;

    public LectureCouleur()
    {
        Document document;
        //File fichierXML = new File("src/main/resources/coloration.xml");
        File fichierXML = new File("../../src/main/resources/coloration.xml");
        SAXBuilder sxb  = new SAXBuilder();
        lstCouleur      = new ArrayList<>();

        try {
            racine = sxb.build(fichierXML).getRootElement();
            chargerCouleur();
        }
        catch (Exception e) {
            System.out.println("erreur");
        }
    }

    public void chargerCouleur()
    {
        List<Element> lstElement = racine.getChildren("element");

        for(Element e:lstElement)
        {
            lstCouleur.add(new Couleur());
            lstCouleur.get(lstCouleur.size()-1).setNom(e.getAttributeValue("type"));

            List<Element> lstCouleurTexte = e.getChildren("couleurTexte");
            List<Element> lstCouleurFond  = e.getChildren("couleurFond" );
            List<Element> lstPoids        = e.getChildren("poids"       );

            for (Element t : lstCouleurTexte) {
                lstCouleur.get(lstCouleur.size()-1).setCoulTxt (t.getAttributeValue("nom"));
                lstCouleur.get(lstCouleur.size()-1).setValRTxt (Integer.parseInt(t.getAttributeValue("valeurR")));
                lstCouleur.get(lstCouleur.size()-1).setValGTxt (Integer.parseInt(t.getAttributeValue("valeurG")));
                lstCouleur.get(lstCouleur.size()-1).setValBTxt (Integer.parseInt(t.getAttributeValue("valeurB")));
            }

            for (Element f : lstCouleurFond) {
                lstCouleur.get(lstCouleur.size()-1).setCoulFond(f.getAttributeValue("nom"));
                lstCouleur.get(lstCouleur.size()-1).setValRFond (Integer.parseInt(f.getAttributeValue("valeurR")));
                lstCouleur.get(lstCouleur.size()-1).setValGFond (Integer.parseInt(f.getAttributeValue("valeurG")));
                lstCouleur.get(lstCouleur.size()-1).setValBFond (Integer.parseInt(f.getAttributeValue("valeurB")));
            }

            for (Element p:lstPoids) {
                lstCouleur.get(lstCouleur.size()-1).setGras(Boolean.parseBoolean(p.getAttributeValue("gras")));
            }
        }
    }

    public static Couleur getCouleur (String nom){

        for(Couleur c : lstCouleur)
        {
            if (c.getNom().equals(nom))
                return  c;
        }

        return null;
    }

    public static void main(String[] args) {
        new LectureCouleur();
    }
}
