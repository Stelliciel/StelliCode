package iut.Stelliciel.StelliCode.metier;

/**
 * @author Stelliciel
 * @credit Benjamin Cléon
 * @version 1
 */
public class Variable<V>
{
    private final String nom;
    private final String type;
    private static int nbVar = 0;
    private int numVar;
    private V valeur;
    private Object[][][] tabValeur;

    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param type le type de primitif de la valeur
     * @param valeur valeur de n'importe quel type primitif (int, double, booléen, caractere, chaine de caractere)
     */
    public Variable( String nom, String type, V valeur ){
        this.type   = type;
        this.nom    = nom;
        this.valeur = valeur;
    }

    /**
     * constructeur de variable sans valeur
     * @param nom le nom est un string
     * @param type le type de primitif de la valeur
     */
    public Variable(String nom, String type){
        this.nom    = nom;
        this.type   = type;
    }


    /**constructeur d'une variable
     * @param nom le nom est un string
     * @param type le type de primitif du tableau
     * @param taille taille du tableau première dimension
     * @param taille2 taille du tableau secondes dimension
     * @param taille3 taille du tableau troisième dimension
     */
    public Variable (String nom, int taille, int taille2, int taille3, String type) {
        this.nom = nom;
        this.type = type;
        if ( taille2 == 0 ) taille2++;
        if ( taille3 == 0 ) taille3++;
        this.tabValeur = new Object[taille][taille2][taille3];
    }

    /** Change la valeur à l'indice donner
     * @param ind indice
     * @param valeur la nouvelle valeur
     */
    public void setIndTab(int ind, int ind2, int ind3, V valeur) {
        System.out.println(valeur);
        tabValeur[ind][ind2][ind3] = valeur;
    }

    /** Recupere la valeur de l'indice entrée
     * @param ind indice
     */
    public V getIndTab(int ind, int ind2, int ind3){
        if (ind < tabValeur.length)
            if (ind2 < tabValeur[ind].length)
                if (ind3 < tabValeur[ind][ind2].length)
                    return (V) this.tabValeur[ind][ind2][ind3];

        return null;
    }

    /**
     * transforme une valeur non-tableau en string
     * @return Le String de la valeur de la variable
     */
    public String valToString(){
        if (this.valeur == null){return "";}
        String sRep ="";
        if ( estTableau() ){
            sRep += "[";

            StringBuilder sRepBuilder = new StringBuilder(sRep);
            for(Object[][] o : tabValeur)
                sRepBuilder.append(o).append(",");
            sRepBuilder.deleteCharAt(sRepBuilder.length()-1);
            sRep = sRepBuilder.toString();

            sRep += "]";
        }
        return switch (this.getType()) {
            case "entier"            -> Integer.parseInt(valeur+"") + "";
            case "reel"              -> Double.parseDouble(valeur+"")+"";
            case "caractere"         -> ""+(valeur+"").charAt(1);
            case "chaine", "booleen" -> valeur+"";
            default                  -> "erreur imposible";
        };
    }

    /**
     * @return Return si la variable est un tableau non null
     */
    public boolean estTableau() {
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
     * @return le tableau de Valeur
     */
    public Object[][][] getTabValeur() { return this.tabValeur;}

    /**
     * @return la valeur de la variable
     */
    public Object getVal(){
        if (estTableau()) {
            return "["+type+"]";
        }
        else
            return valeur;
    }

    /** change la valeur
     * @param val nouvelle valeur de la variable
     */
    public void setVal(V val){ this.valeur = val;}

    /**
     * constructeur d'une variable de tableau
     * @param nom String, le nom du tableau
     * @param type String, le type du tableau
     * @param tabValeur, le tableau
     */
    private Variable(String nom, String type, V[][][] tabValeur){
        this.nom =nom;
        this.type=type;
        this.tabValeur = tabValeur;
    }

    /**
     * copie une variable dans une autre
     * @param v {@link Variable}&#60Object&#62, la variable à copié
     * @return {@link Variable},la nouvelle variable
     */
    public static Variable<Object> copy(Variable<Object> v){
        if ( v.estTableau() )
            return new Variable<>(v.getNom(), v.getType(), v.getTabValeur());
        else
            return new Variable<>(v.getNom(), v.getType(), v.getVal());
    }

    @Override
    public String toString() {
        return "Variable{" + "valeur=" + this.valToString() + '}';
    }
}