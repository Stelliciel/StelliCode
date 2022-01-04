package iut.Stelliciel.StelliCode.metier;

/**
 * @author Gaspard Gordien , Benjamin Cléon, Raphaël Lizot
 * @version 3
 */

public class Variable<E>
{
    private final String nom;
    private final String type;
    private E valeur;
    private Object[] tabValeur;

    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param type le type de primitif de la valeur
     * @param valeur valeur de n'importe quel type primitif (int, double, booléen, caractere, chaine de caractere)
     */
    public Variable( String nom, String type, E valeur )
    {
        this.type   = type;
        this.nom    = nom;
        this.valeur = valeur;
    }

    public Variable(String nom, String type){
        this.type   = type;
        this.nom    = nom;
    }


    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param type le type de primitif du tableau
     * @param taille taille du tableau
     */
    public Variable (String nom, int taille, String type ){
        this.nom = nom;
        this.type = type;
        this.tabValeur = new Object[taille];
    }

    /**@return le string de variable
     */
    public String toString()
    {
        String sRep = nom + ": ";

        if ( estTableau() ){
            sRep += "[";

            StringBuilder sRepBuilder = new StringBuilder(sRep);
            for(Object o : tabValeur) sRepBuilder.append(o).append(",");
            sRepBuilder.deleteCharAt(sRepBuilder.length()-1);
            sRep = sRepBuilder.toString();

            sRep += "]";
        }
        else
            sRep += valeur;

        sRep += "    type=" + type;
        return sRep;
    }

    /** Change la valeur à l'indice donner
     * @param ind indice
     * @param valeur la nouvelle valeur
     */
    public void setIndTab(int ind, E valeur)
    {
        tabValeur[ind] = valeur;
    }

    /** Recupere la valeur de l'indice entrée
     * @param ind indice
     */
    public E getIndTab(int ind){
        if ( ind < tabValeur.length )
            return (E) this.tabValeur[ind];

        return null;
    }

    /**
     * @return Return si la variable est un tableau
     */
    private boolean estTableau() {
        return this.tabValeur != null;
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
    public Object getVal(){
        if (estTableau())
            return tabValeur;
        else
            return this.valeur;
    }

    /** change la valeur
     * @param val nouvelle valeur de la variable
     */
    public void setVal(E val){ this.valeur = val;}
}