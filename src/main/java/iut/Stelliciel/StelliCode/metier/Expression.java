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

        System.out.println(Expression.calculer("52+2/(3-8)^5^2"));
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

        //remove all blank spaces
        expression = expression.replaceAll("\\s+","");

        //add a 0 before the "-" in order to consider the "-" as a standalone operator
        expression = expression.replace("(-", "(0-");

        //same thing here
        if (expression.startsWith("-")){
            expression = "0" + expression;
        }

        //read the expression and check if it contains only allowed token
        Pattern pattern = Pattern.compile("((([0-9]*[.])?[0-9]+)|([\\+\\-\\*\\/\\(\\)\\^]))");
        Matcher matcher = pattern.matcher(expression);

        int cpt = 0; //must be equal to the index of the end of the last matching group
        List<String> file = new ArrayList<>();
        while(matcher.find()){
            file.add(matcher.group().trim());//add the token if it's okay
            cpt += file.get(file.size() - 1 ).length();//update the cpt
        }
        

        Stack<String> pileOp = new Stack<>(); //pile pour operateur
        List<String> sortie = new ArrayList<>(); //sortie queue

        for(String expr : file){
            if(operators.containsKey(expr)){
                while(!pileOp.empty() &&
                        operators.containsKey(pileOp.peek())&&
                        ((operators.get(expr) <= operators.get(pileOp.peek()) && !expr.equals("^"))||
                                (operators.get(expr) < operators.get(pileOp.peek()) && expr.equals("^")))) {
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
                if(pile.size() > 1){
                    if ( expr.equals("\\/¯") )
                        pile.push( Math.sqrt(pile.pop()) );
                    else{
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
        }

        return pile.peek();
    }
}