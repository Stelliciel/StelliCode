package iut.Stelliciel.StelliCode.metier;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import java.util.*;

public class LectureCouleur {
    private int valR,valG,valB;

    private Element racine;
    private List<Couleur> lstCouleur;

    public LectureCouleur()
    {
        Document document;
        File fichierXML = new File("src/main/resources/coloration.xml");
        SAXBuilder sxb  = new SAXBuilder();
        lstCouleur      = new ArrayList<Couleur>();

        try {
            System.out.println("1");
            document = sxb.build(fichierXML);
            System.out.println("2");
            racine   = document.getRootElement();
            System.out.println("3");
            chargerCouleur();

            for(Couleur c : lstCouleur)
                System.out.println(c);
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
            List<Element> lstCouleurTexte = e.getChildren("couleurTexte");
            List<Element> lstCouleurFond  = e.getChildren("couleurFond" );
            List<Element> lstPoids        = e.getChildren("poids"       );
            lstCouleur.add(new Couleur());
            lstCouleur.get(lstCouleur.size()-1).setNom(e.getAttributeValue("type"));
            //continuer ça

            for (Element t : lstCouleurTexte) {
                lstCouleur.get(lstCouleur.size()-1).setCoulTxt (t.getAttributeValue("nom"));
                lstCouleur.get(lstCouleur.size()-1).setCoulFond(t.getAttributeValue("nom"));
                lstCouleur.get(lstCouleur.size()-1).setValR   (Integer.parseInt(t.getAttributeValue("valeurR")));
                lstCouleur.get(lstCouleur.size()-1).setValG   (Integer.parseInt(t.getAttributeValue("valeurG")));
                lstCouleur.get(lstCouleur.size()-1).setValB   (Integer.parseInt(t.getAttributeValue("valeurB")));
            }

            for (Element f : lstCouleurFond) {
                lstCouleur.get(lstCouleur.size()-1).setCoulTxt(f.getAttributeValue("nom"));
                lstCouleur.get(lstCouleur.size()-1).setValR   (Integer.parseInt(f.getAttributeValue("valeurR")));
                lstCouleur.get(lstCouleur.size()-1).setValG   (Integer.parseInt(f.getAttributeValue("valeurG")));
                lstCouleur.get(lstCouleur.size()-1).setValB   (Integer.parseInt(f.getAttributeValue("valeurB")));
            }

            for (Element p:lstPoids) {
                lstCouleur.get(lstCouleur.size()-1).setGras(Boolean.parseBoolean(p.getAttributeValue("gras")));
            }
        }
    }

    public static void main(String[] args) {
        new LectureCouleur();
    }
}
