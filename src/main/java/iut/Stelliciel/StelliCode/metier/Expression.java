package iut.Stelliciel.StelliCode.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stelliciel
 * @version 1
 */
public class Expression {
    /**
     * retourne si la String est une expression logique
     * @param expression String
     * @return boolean, vrai si la String est une expression logique
     */
    public static boolean estUneExpressionLogique(String expression){
        expression = expression.replaceAll("false", "faux");
        expression = expression.replaceAll("true", "vrai");
        Pattern pattern = Pattern.compile("((vrai)|(faux)|([\\(\\)])|(<=)|(>=)|(<)|(>)|(/=)|(=)|(xou)|(ou)|(et)|(non)|([a-z])|([A-Z]))");
        Matcher matcher = pattern.matcher(expression);

        return matcher.find();
    }

    /**
     * retourne si c'est un calcul
     * @param expression
     * @return boolean,vrai si c'est un calcul
     */
    public static boolean estUneExpression(String expression){
        Pattern pattern = Pattern.compile("(([\\+\\-\\×\\/\\(\\)\\^])|(mod)|(div)|(\\\\/¯))");
        Matcher matcher = pattern.matcher(expression);

        return matcher.find();
    }

    /**
     * calcul les expressions booleenne
     * @param expression
     * @return boolean, renvoie le résultat
     */
    public static boolean calculLogique(String expression) {
        Map<String, Integer> operators = new HashMap<>();
        operators.put("<",  4);
        operators.put(">",  4);
        operators.put("<=", 4);
        operators.put(">=", 4);
        operators.put("=", 4);
        operators.put("/=", 4);
        operators.put("xou",3);
        operators.put("ou", 3);
        operators.put("et", 2);
        operators.put("non",1);

        expression = expression.replaceAll("\\s+","");

        expression = expression.replaceAll("false", "faux");
        expression = expression.replaceAll("true", "vrai");
        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|(vrai)|(faux)|([\\(\\)])|(<=)|(>=)|(<)|(>)|(/=)|(=)|(xou)|(ou)|(et)|(non)|([a-z])|([A-Z]))");
        Matcher matcher = pattern.matcher(expression);

        int cpt = 0;
        List<String> file = new ArrayList<>();
        while(matcher.find()){
            file.add(matcher.group().trim());
            cpt += file.get(file.size() - 1 ).length();
        }



        Stack<String> pileOp = new Stack<>(); //pile pour operateur
        List<String> sortie = new ArrayList<>(); //sortie queue

        for(String expr : file){
            if(operators.containsKey(expr)) {
                while(  !pileOp.empty() && operators.containsKey(pileOp.peek()) &&
                        ( (operators.get(expr) <= operators.get(pileOp.peek())) ||
                          (operators.get(expr) <  operators.get(pileOp.peek()))    )    ) {
                    sortie.add(pileOp.pop());
                }
                pileOp.push(expr);

            }
            else if(expr.equals("(")){
                pileOp.push(expr);
            }
            else if(expr.equals(")")){
                while(!pileOp.empty()){
                    if(!pileOp.peek().equals("(")){
                        sortie.add(pileOp.pop());
                    }
                    else
                        break;
                }
                if(!pileOp.empty()){
                    pileOp.pop();
                }
            }
            else{
                sortie.add(expr);
            }
        }



        while(!pileOp.empty()){
            sortie.add(pileOp.pop());
        }

        for(String s: sortie){
            System.out.println(s);
        }


        Stack<Double> pile = new Stack<>();
        Stack<Boolean> pileSortie = new Stack<>();
        for(String expr : sortie){
            if( !operators.containsKey(expr) && expr.matches("([0-9]*[.])?[0-9]+|(vrai)|(faux)|([a-z])|([A-Z])") ){
                if ( expr.matches("([a-z])|([A-Z])"))
                    pile.push((int) expr.charAt(0) * 1.0);
                else if ( expr.matches("(vrai)|(faux)")){
                    if ( expr.matches("(vrai)"))
                        pileSortie.push( true );
                    else if ( expr.matches("(faux)") )
                        pileSortie.push( false );

                }
                else
                    pile.push(Double.parseDouble(expr));
            }
            else{
                if ( expr.equals("non") ) {
                    Boolean b = !pileSortie.pop();
                    pileSortie.push(b);
                }
                else if(pile.size() > 1){
                    if ( expr.equals("ou") || expr.equals("et") || expr.equals("xou") ){
                        if ( expr.equals("et") ){
                            Boolean b1 = pileSortie.pop();
                            Boolean b2 = pileSortie.pop();

                            pileSortie.push( b2 && b1 );
                        }
                        else if (expr.equals("xou")){
                            Boolean b1 = pileSortie.pop();
                            Boolean b2 = pileSortie.pop();

                            pileSortie.push( b2 ^ b1 );
                        }
                        else{
                            Boolean b1 = pileSortie.pop();
                            Boolean b2 = pileSortie.pop();

                            pileSortie.push( b2 || b1 );
                        }
                    }

                    else {
                        double op1 = pile.pop();
                        double op2 = pile.pop();
                        switch (expr) {
                            case ">":
                                pileSortie.push(op2 > op1);
                                break;
                            case "<":
                                pileSortie.push(op2 < op1);
                                break;
                            case "<=":
                                pileSortie.push(op2 <= op1);
                                break;
                            case ">=":
                                pileSortie.push(op2 >= op1);
                                break;
                            case "=":
                                pileSortie.push(op2 == op1);
                                break;
                            case "/=":
                                pileSortie.push(op2 != op1);
                                break;
                        }

                    }

                }
            }
        }
        return pileSortie.peek();
    }

    /**
     * effectue le calcul et renvoie le résultat
     * @param expression
     * @return boolean, renvoie le résultat
     */
    public static double calculer(String expression) {
        Map<String, Integer> operators = new HashMap<>();
        operators.put("-", 0);
        operators.put("+", 0);
        operators.put("div", 1);
        operators.put("mod", 1);
        operators.put("/", 1);
        operators.put("×", 1);
        operators.put("^", 2);
        operators.put("\\/¯", 2);

        expression = expression.replaceAll("\\s+","");
        expression = expression.replace("(-", "(0-");

        if (expression.startsWith("-")){
            expression = "0" + expression;
        }

        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|([\\+\\-\\×\\/\\(\\)\\^])|(mod)|(div)|(\\\\/¯))");
        Matcher matcher = pattern.matcher(expression);

        int cpt = 0;
        List<String> file = new ArrayList<>();
        while(matcher.find()){
            file.add(matcher.group().trim());
            cpt += file.get(file.size() - 1 ).length();
        }

        Stack<String> pileOp = new Stack<>(); //pile pour operateur
        List<String> sortie = new ArrayList<>(); //sortie queue

        for(String expr : file){
            if(operators.containsKey(expr)){
                while(  !pileOp.empty() && operators.containsKey(pileOp.peek()) &&
                      ( ( operators.get(expr) <= operators.get(pileOp.peek())     && !expr.equals("^") )  ||
                        ( operators.get(expr) <  operators.get(pileOp.peek())     &&  expr.equals("^") )     )    ) {
                    sortie.add(pileOp.pop());
                }
                pileOp.push(expr);

            }
            else if(expr.equals("(")){
                pileOp.push(expr);
            }
            else if(expr.equals(")")){
               while(!pileOp.empty()){
                    if(!pileOp.peek().equals("(")){
                        sortie.add(pileOp.pop());
                    }
                    else
                        break;
                }
                if(!pileOp.empty()){
                    pileOp.pop();
                }
            }
            else{
                sortie.add(expr);
            }
        }

        while(!pileOp.empty()){
            sortie.add(pileOp.pop());
        }


        Stack<Double> pile = new Stack<>();
        for(String expr : sortie){
            if( !operators.containsKey(expr) && expr.matches("([0-9]*[.])?[0-9]+") ){
                pile.push(Double.parseDouble(expr));
            }
            else{
                if ( expr.equals("\\/¯") ) {
                    double op = pile.pop();
                    pile.push(Math.sqrt(op));
                }
                else if(pile.size() > 1){
                        double op1 = pile.pop();
                        double op2 = pile.pop();
                        switch (expr) {
                            case "div":
                                pile.push((double)((int)(op2 / op1)));
                                break;
                            case "+":
                                pile.push(op2 + op1);
                                break;
                            case "-":
                                pile.push(op2 - op1);
                                break;
                            case "mod":
                                pile.push(op2 % op1);
                                break;
                            case "×":
                                pile.push(op2 * op1);
                                break;
                            case "/":
                                pile.push(op2 / op1);
                                break;
                            case "^":
                                pile.push(Math.pow(op2, op1));
                                break;
                        }
                }
            }
        }
        return pile.peek();
    }
}