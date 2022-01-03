package iut.Stelliciel.StelliCode.metier;
/**
 * @author Gaspard Gordien , Benjamin Cléon
 * @version 2
 */

public class Variable<E>
{
    private final String nom;
    private E valeur;

    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param valeur valeur de n'importe quel type primitif (int,double,booléen,charactere,chaine de caractere)
     */
    Variable( String nom, E valeur )
    {
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
     * @return la valeur de la variable
     */
    public E getVal(){return this.valeur;}

    /** change la valeur
     * @param val nouvelle valeur de la variable
     */
    public void setVal(E val){
        this.valeur = val;
    }
}