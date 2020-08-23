import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

public class App {
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Queue<String> postSyQueue = new LinkedList<String>();
        Stack<String> operatorStack = new Stack<String>();
        System.out.println("Calculator: ");
        String userInput = in.nextLine();
        StringTokenizer st = new StringTokenizer(userInput);
        System.out.println("The user entered " + userInput);
        Vector<String> userInputSplited = new Vector<>();
        Stack<Double> outputStack = new Stack<Double>();
        double answer = 0;

        while(st.hasMoreTokens()){
            userInputSplited.add(st.nextToken());
        }
        System.out.println("user data splited into " + userInputSplited.size() + " pieces");
        for(int i = 0; i < userInputSplited.size(); i++) {
            System.out.println(userInputSplited.get(i));
        }
        System.out.println("end");

        class Operator {
            private int value;
           
            Operator(){
                value = 0;
            }
           
            public int getOpValue(String operator) {
                switch(operator){
                    case "*": value = 3;
                    break; 
                    case "/": value = 2;
                    break; 
                    case "+": value = 1;
                    break;
                    case "-": value = 0; 
                    break;
                }
            return value;
            }
        }

        in.close();
        Operator sy = new Operator();
        for(int i = 0; i < userInputSplited.size(); i++) {
            char[] chars = userInputSplited.get(i).toCharArray();
            if(Character.isDigit(chars[0])){
                postSyQueue.add((userInputSplited.elementAt(i)));
            } else {
                if(!operatorStack.empty()){
                    while(true) {
                        if(sy.getOpValue(operatorStack.peek()) > sy.getOpValue(userInputSplited.elementAt(i))) {
                            postSyQueue.add(operatorStack.pop());
                        } else {
                            operatorStack.push(userInputSplited.elementAt(i));
                            break;
                        }
                    }
                } else {
                    operatorStack.push(userInputSplited.elementAt(i));
                }
            }
        }

        while (!operatorStack.empty()) {
            postSyQueue.add(operatorStack.pop());
        }

        while(!postSyQueue.isEmpty()) {
            if(Character.isDigit(postSyQueue.peek().charAt(0))) {
                outputStack.push(Double.parseDouble(postSyQueue.poll()));
            } else {
                double num1 = outputStack.pop(),num2 = outputStack.pop();
                switch(postSyQueue.poll()) {
                    case "*": answer = num2 * num1;
                    break; 
                    case "/": answer = num2 / num1;
                    break; 
                    case "+": answer = num2 + num1;
                    break;
                    case "-": answer = num2 - num1; 
                    break;
                }
                outputStack.push(answer);
            }
            
        }
        
        System.out.println("Answer : " + answer);

    }
}
