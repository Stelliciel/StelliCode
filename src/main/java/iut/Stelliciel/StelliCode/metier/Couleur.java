package iut.Stelliciel.StelliCode.metier;

public class Couleur {
    private String  nom;
    private String  coulTxt,coulFond;
    private boolean gras;
    private int couleurFond,couleurText;

    /**
     * get le nom de la couleur
     * @return String, nom de la couleur
     */
    public String  getNom     (){ return nom;      }

    /**
     * get la valeur de la couleur du fond de la couleur
     * @return int, la valeur de la couleur
     */
    public int getCouleurFond() {
        return couleurFond;
    }

    /**
     * get la valeur de la couleur du texte de la couleur
     * @return int, la valeur de la couleur
     */
    public int getCouleurText() {return couleurText;}

    /**
     * set le nom
     * @param nom String, nom
     */
    public void setNom     (String  nom ) { this.nom      = nom;  }

    /**
     * set la couleur du texte
     * @param coul String, la couleur du texte
     */
    public void setCoulTxt (String  coul) { this.coulTxt  = coul; }

    /**
     * set la couleur du fond
     * @param coul String, la couleur du fond
     */
    public void setCoulFond(String  coul) { this.coulFond = coul; }

    /**
     * set la couleur du fond
     * @param couleur int, la couleur du fond
     */
    public void setCouleurFond(int couleur) {
        this.couleurFond = couleur;
    }

    /**
     * set la couleur du texte
     * @param couleur int, la couleur du texte
     */
    public void setCouleurText(int couleur) {
        this.couleurText = couleur;
    }
}
