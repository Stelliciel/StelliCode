package iut.Stelliciel.StelliCode.metier;

public class Couleur {
    private String  nom;
    private String  coulTxt,coulFond;
    private int     valRTxt,valGTxt,valBTxt;
    private int     valRFond,valGFond,valBFond;
    private boolean gras;

    public String  getNom     (){ return nom;      }
    public String  getCoulTxt (){ return coulTxt;  }
    public String  getCoulFond(){ return coulFond; }

    public int     getValRFond(){ return valRFond; }
    public int     getValGFond(){ return valGFond; }
    public int     getValBFond(){ return valBFond; }

    public int     getValRText(){ return valRTxt;  }
    public int     getValGText(){ return valGTxt;  }
    public int     getValBText(){ return valBTxt;  }

    public boolean estGras    (){ return gras;     }

    public void setNom     (String  nom ) { this.nom      = nom;  }
    public void setCoulTxt (String  coul) { this.coulTxt  = coul; }
    public void setCoulFond(String  coul) { this.coulFond = coul; }

    public void setValRTxt (int     valR) { this.valRTxt  = valR; }
    public void setValGTxt (int     valG) { this.valGTxt  = valG; }
    public void setValBTxt (int     valB) { this.valBTxt  = valB; }

    public void setValRFond(int     valR) { this.valRFond = valR; }
    public void setValGFond(int     valG) { this.valGFond = valG; }
    public void setValBFond(int     valB) { this.valBFond = valB; }

    public void setGras    (boolean gras) { this.gras     = gras; }

    @Override
    public String toString() {
        return "Couleur{" +
                "nom='" + nom + '\'' +
                ", coulTxt='" + coulTxt + '\'' +
                ", coulFond='" + coulFond + '\'' +
                ", valRTxt=" + valRTxt +
                ", valGTxt=" + valGTxt +
                ", valBTxt=" + valBTxt +
                ", valRFond=" + valRFond +
                ", valGFond=" + valGFond +
                ", valBFond=" + valBFond +
                ", gras=" + gras +
                '}';
    }
}
