package iut.Stelliciel.StelliCode.metier;

public class Couleur {
    private String  nom;
    private String  coulTxt,coulFond;
    private int     valR,valG,valB;
    private boolean gras;

    public String  getNom     (){ return nom;      }
    public String  getCoulTxt (){ return coulTxt;  }
    public String  getCoulFond(){ return coulFond; }
    public int     getValRFond(){ return valR;     }
    public int     getValGFond(){ return valG;     }
    public int     getValBFond(){ return valB;     }
    public boolean estGras    (){ return gras;     }

    public void setNom     (String  nom ) { this.nom      = nom;  }
    public void setCoulTxt (String  coul) { this.coulTxt  = coul; }
    public void setCoulFond(String  coul) { this.coulFond = coul; }
    public void setValR    (int     valR) { this.valR     = valR; }
    public void setValG    (int     valG) { this.valG     = valG; }
    public void setValB    (int     valB) { this.valB     = valB; }
    public void setGras    (boolean gras) { this.gras     = gras; }

    @Override
    public String toString() {
        return "Couleur{" +
                "nom='" + nom + '\'' +
                ", coulTxt='" + coulTxt + '\'' +
                ", coulFond='" + coulFond + '\'' +
                ", valR=" + valR +
                ", valG=" + valG +
                ", valB=" + valB +
                ", gras=" + gras +
                '}';
    }
}
