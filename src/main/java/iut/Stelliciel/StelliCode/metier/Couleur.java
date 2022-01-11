package iut.Stelliciel.StelliCode.metier;

public class Couleur {
    private String  nom;
    private String  coulTxt,coulFond;
    private boolean gras;
    private int couleurFond,couleurText;

    public String  getNom     (){ return nom;      }

    public int getCouleurFond() {
        return couleurFond;
    }
    public int getCouleurText() {return couleurText;}

    public boolean estGras    (){ return gras;     }

    public void setNom     (String  nom ) { this.nom      = nom;  }
    public void setCoulTxt (String  coul) { this.coulTxt  = coul; }
    public void setCoulFond(String  coul) { this.coulFond = coul; }

    public void setCouleurFond(int couleur) {
        this.couleurFond = couleur;
    }
    public void setCouleurText(int couleur) {
        this.couleurText = couleur;
    }

    public void setGras    (boolean gras) { this.gras     = gras; }

}
