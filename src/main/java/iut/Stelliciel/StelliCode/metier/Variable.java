package iut.Stelliciel.StelliCode.metier;
/**
 * @author Gaspard Gordien , Benjamin Cléon, Raphaël Lizot
 * @version 3
 */

public class Variable<E>
{
    private final String nom;
    private String type;
    private E valeur;

    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param valeur valeur de n'importe quel type primitif (int,double,booléen,charactere,chaine de caractere)
     */
    Variable( String nom, String type, E valeur )
    {
        this.type   = type;
        this.nom    = nom;
        this.valeur = valeur;
    }

    /**@return le string pour rentrer dans le tableau affichTab
     */
    public String toString()
    {
        String sRep = "";

        sRep = nom + ": " + valeur;

        return sRep;
    }

    /**
     * @return le nom de la variable
     */
    public String getNom(){return this.nom;}

    /**
     * @return le type de la variable
     */
    public String getType() {
        return type;
    }

    /**
     * @return la valeur de la variable
     */
    public E getVal(){return this.valeur;}

    /** change la valeur
     * @param val nouvelle valeur de la variable
     */
    public void setVal(E val){ this.valeur = val;}
}