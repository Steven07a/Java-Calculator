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

        Vector<String> userInputSplited = new Vector<>();
        Stack<Double> outputStack = new Stack<Double>();
        double answer = 0;

        // tokenizes the users input
        while(st.hasMoreTokens()){
            userInputSplited.add(st.nextToken());
        }

        // The operator classes purpose is to give mathimatical operators their priority based off of PEMDAS
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
        
        //This for loop is where the shunting yard process begins basically we turn the expression into reverse polish notation
        for(int i = 0; i < userInputSplited.size(); i++) {
            //takes the tokens we got from the user and splits it into a char array so we can check if it is a number or not
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
        // This takes care of any remaining operators inside the stack
        while (!operatorStack.empty()) {
            postSyQueue.add(operatorStack.pop());
        }

        /* This is where we begin the math process basically the program goes through the outputStack which is in Reverse polish Notation and seperates it into two stacks 
        * This process starts by popping the Queue that is in RPN we continue to pop into the output stack until we get to an operator at which point we pop 2 numbers out of the 
        * output Stack and process that operation afterwards we push the answer of that operation back into the output stack and continue this process until we are done. 
        * The end result should be that we only have one number in out output stack and that is our answer
        */
        
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
        
        System.out.println("Answer : " + outputStack.pop());

    }
}
