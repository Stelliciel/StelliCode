package iut.Stelliciel.StelliCode.metier;

import java.util.Stack;

public class Expression {

    public static int getPriority(char ch)
    {
        if (ch == '+' || ch == '-')
            return 1;
        else if (ch == '*' || ch == '/')
            return 2;
        else if (ch == '^')
            return 3;
        else
            return -1;
    }

    public static String toEPO(String expression)
    {
        Stack<Character> pile = new Stack<>();

        String sortie = "";

        for (int cpt = 0; cpt < expression.length(); ++cpt) {
            char c = expression.charAt(cpt);

            if (Character.isLetterOrDigit(c))
                sortie += c;
            else if ( c == '(' )
                pile.push(c);
            else if ( c == ')' ) {
                while ( !pile.isEmpty() && pile.peek() != '(' )
                    sortie += pile.pop();

                pile.pop();
            }
            else {
                while ( !pile.isEmpty() && getPriority(c) <= getPriority(pile.peek()) ) {
                    sortie += pile.pop();
                }
                pile.push(c);
            }
        }

        while (!pile.isEmpty()) {
            if (pile.peek() == '(')
                return "Erreur";
            sortie += pile.pop();
        }

        return sortie;
    }


    public static double calculer(double var1, String operande, double var2){
        System.out.println("\tCalcul simple :"+ var1+operande+var2);

        switch (operande){
            case "*" -> {return var1*var2;}
            case "+" -> {return var1+var2;}
            case "-" -> {return var1-var2;}
            case "/" -> {return var1/var2;}
            case "^" -> {return Math.pow(var1,var2);}
        }

        return 0;
    }

    public static double caculerEPO(String expression){
        Stack<Double> pile = new Stack<>();

        for(int cpt = 0; cpt < expression.length(); cpt++ )
        {
            char c = expression.charAt(cpt);
            System.out.println("char="+c);

            if( !Character.isDigit(c) ) {
                System.out.println("Operande:"+c);
                double calcul = pile.pop();
                System.out.println("depile de "+calcul);
                if ( pile.isEmpty() ) {
                    System.out.println("pile vide envoie calcul");
                    return calcul;
                }
                else{
                    System.out.println("pile pas vide calcul, on dépile:"+ pile.peek());
                    pile.push(Expression.calculer(  pile.pop(), String.valueOf(c), calcul ));
                    System.out.println("rajout de "+pile.peek());
                }

            }
            else {
                System.out.println("chiffre="+c);
                pile.push(Double.parseDouble(String.valueOf(c)));
                System.out.println("on empile "+c);
            }
        }
        return pile.pop();
    }

    public static void main(String[] args)
    {
        String expression = "5+2/(3-8)^5^2";
        String EPO        = Expression.toEPO(expression);

        System.out.println(EPO);

        System.out.println(Expression.caculerEPO(EPO));
    }
}
