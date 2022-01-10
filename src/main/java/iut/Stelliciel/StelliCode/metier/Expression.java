package iut.Stelliciel.StelliCode.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    public static void main(String[] args) {
        //System.out.println(Expression.calculer("52+2/(3-8)^5^2*\\/¯25"));
        //System.out.println(Expression.calculer("\\/¯25"));
        //System.out.println(Expression.calculer("2*3+4/(5+6)"));
        System.out.println(Expression.calculLogique("2<6 et A != a"));
        System.out.println(Expression.calculLogique("A == A"));
    }

    public static boolean calculLogique(String expression) {
        Map<String, Integer> operators = new HashMap<>();
        operators.put("<",  4);
        operators.put(">",  4);
        operators.put("<=", 4);
        operators.put(">=", 4);
        operators.put("==", 4);
        operators.put("!=", 4);
        operators.put("ou", 3);
        operators.put("et", 2);
        operators.put("non",1);

        expression = expression.replaceAll("\\s+","");


        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|([a-z])|([A-Z])|([\\<\\>\\(\\)])|(<=)|(>=)|(==)|(!=)|(ou)|(et)|(non))");
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


        System.out.println();

        Stack<Double> pile = new Stack<>();
        Stack<Boolean> pileSortie = new Stack<>();
        for(String expr : sortie){
            if( !operators.containsKey(expr) && expr.matches("([0-9]*[.])?[0-9]+|([a-z])|([A-Z])") ){
                if ( expr.matches("([a-z])|([A-Z])"))
                    pile.push((int) expr.charAt(0) * 1.0);
                else
                    pile.push(Double.parseDouble(expr));
            }
            else{
                if ( expr.equals("non") ) {
                    Boolean b = !pileSortie.pop();
                    pileSortie.push(b);
                }
                else if(pile.size() > 1){
                    if ( expr.equals("ou") || expr.equals("et") ){
                        if ( expr.equals("et") ){
                            Boolean b1 = pileSortie.pop();
                            Boolean b2 = pileSortie.pop();

                            pileSortie.push( b2 && b1 );
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
                            case "==":
                                pileSortie.push(op2 == op1);
                                break;
                            case "!=":
                                pileSortie.push(op2 != op1);
                                break;
                        }

                    }

                }
            }
        }
        return pileSortie.peek();


    }

    public static double calculer(String expression) {
        Map<String, Integer> operators = new HashMap<>();
        operators.put("-", 0);
        operators.put("+", 0);
        operators.put("%", 1);
        operators.put("/", 1);
        operators.put("*", 1);
        operators.put("^", 2);
        operators.put("\\/¯", 2);

        expression = expression.replaceAll("\\s+","");
        expression = expression.replace("(-", "(0-");

        if (expression.startsWith("-")){
            expression = "0" + expression;
        }

        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|([\\+\\-\\*\\/\\(\\)\\^])|(\\\\/¯))");
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

        for(String s : sortie )
            System.out.println("S : " + s);

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
                            case "+":
                                pile.push(op2 + op1);
                                break;
                            case "-":
                                pile.push(op2 - op1);
                                break;
                            case "%":
                                pile.push(op2 % op1);
                                break;
                            case "*":
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