import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Converter{

    // Initializierung der benötigten Attributen
    ArrayList<String> converterInput = new ArrayList<String>();
    ArrayList<String> converterOutput = new ArrayList<String>();
    ArrayList<String> converterTokens = new ArrayList<String>();
    int index;

    // Constructor
    Converter(ArrayList<String> input){
        this.converterInput = input;
        this.index = 0;
    }

    // Methode zum Konvertieren der römischen Zahlen in arabischen/normalen Zahlen
    void convert(){
        while(index<converterInput.size()){
            String value = converterInput.get(index);
            int[] value_array = new int[2];
            if(value.equals("M")){
                value_array = thousands(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("D")){
                value_array = fivehundreds(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("C")){
                value_array = hundreds(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("L")){
                value_array = fifties(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("X")){
                value_array = tens(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("V")){
                value_array = fives(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("I")){
                value_array = ones(converterInput,index);
                index = value_array[0];
                converterOutput.add(String.valueOf(value_array[1]));
                }
            else if(value.equals("(")||value.equals(")")||value.equals("+")||value.equals("-")||value.equals("*")||value.equals("/")||value.equals("%")){
                index ++;
                converterOutput.add(value);
                }
            }
        converterTokens = tokenize(converterOutput);
        ArrayList<Token> tokenList = new ArrayList<Token>();
      int i =0;
      while (i<converterTokens.size()){
          tokenList.add(convertZahlToToken(converterTokens.get(i++)));
      }
        System.out.println("Converter Result:\n"+converterTokens);
        System.out.println("Converter Result mit Tokens:\n"+tokenList);
         // System.out.println(tokenList.get(0).token+" "+tokenList.get(0).zustand+" "+tokenList.get(0).value);
        // System.out.println(tokenList.get(1).token+" "+tokenList.get(1).zustand+" "+tokenList.get(1).value);
        // System.out.println(tokenList.get(2).token+" "+tokenList.get(2).zustand+" "+tokenList.get(2).value);
    }

    static Token convertZahlToToken(String value) {
        TYPE zustand;
        byte token;
        switch (value) {
            case "(":
                zustand = TYPE.OPEN_PAR;
                token = 3;
                break;
            case ")":
                zustand = TYPE.CLOSE_PAR;
                token = 4;
                break;
            case "+":
                zustand = TYPE.PLUS;
                token = 7;
                break;
            case "-":
                zustand = TYPE.MINUS;
                token = 8;
                break;
            case "*":
                zustand = TYPE.MULT;
                token = 5;
                break;
            case "/":
                zustand = TYPE.DIV;
                token = 6;
                break;
            default:
                if (parseInt(value)==1 ||  parseInt(value)==5 || parseInt(value)== 10 || parseInt(value)==50
                || parseInt(value)==100 || parseInt(value)== 500 || parseInt(value)==1000){
                    zustand= TYPE.ZIFFER;
                    token= 1;
                }
                else if (parseInt(value)>0) {
                    zustand = TYPE.ZAHL;
                    token = 2;
                }
                else {
                    zustand=TYPE.NO_TYPE;
                    token=0;
                }
                break;
        }
        Token t = new Token(token, zustand, value);
        return t;
    }

    //Methode zum Anwenden der römsicher Additions- bzw Subtraktionsregel auf dem Array mit arabischen Zahlen
    ArrayList<String> tokenize(ArrayList<String> converterOutput){
        int currentNumber = 0;
        int i=0;
        while(i < converterOutput.size()) {
            String current = converterOutput.get(i);
            if (isOperator(current)) {
                converterTokens.add(current);
                i++;
            }
            else if(i+1 < converterOutput.size()) {
                int currentValue = parseInt(current);
                String next = converterOutput.get(i+1);
                if(!(isOperator(next))){
                    int nextValue = parseInt(next);
                    if(currentNumber >= currentValue && currentValue<nextValue) {
                        currentNumber = currentNumber-currentValue;
                        i = i+1;
                    }
                    else if (currentNumber >= currentValue) {
                        currentNumber = currentNumber + currentValue;
                        i=i+1;
                    }
                    else if(currentNumber < currentValue) {
                        currentNumber = currentValue - currentNumber;
                        i = i+1;
                    }
                }
                else {
                    if (currentNumber >= currentValue) {
                        currentNumber = currentNumber + currentValue;
                    }
                    else if(currentNumber < currentValue) {
                        currentNumber = currentValue - currentNumber;
                    }
                    converterTokens.add(String.valueOf(currentNumber));
                    currentNumber = 0;
                    i++;
                }
            }
            else if(i+1==converterOutput.size()){
                int currentValue = parseInt(current);
                if (currentNumber >= currentValue) {
                        currentNumber = currentNumber + currentValue;
                }
                else if(currentNumber < currentValue) {
                        currentNumber = currentValue - currentNumber;
                }
                converterTokens.add(String.valueOf(currentNumber));
                currentNumber = 0;
                i++;
            }
        }
        return converterTokens;
    }

     public static boolean isOperator(String str) {
        return str.equals("(") || str.equals(")") || str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%");
    }

    static int[] thousands(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1,lookAhead2,lookAhead3;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(converterInput.size()>index+2){
            lookAhead2 = converterInput.get(index+2);
        }
        else {
            lookAhead2 = "";
        }
        if(converterInput.size()>index+3){
            lookAhead3 = converterInput.get(index+3);
        }
        else {
            lookAhead3 = "";
        }
        if(value.equals("M")&&lookAhead1.equals("M")&&lookAhead2.equals("M")&&lookAhead3.equals("M")){
            System.out.println("Semantischer Fehler in Position: "+(index+3)+". Zeichen: "+lookAhead3);
            System.exit(0);
        }
        else if(value.equals("M")&&lookAhead1.equals("M")&&lookAhead2.equals("M")){
            index_va = index+3;
            value_va = 3000;
        }
        else if(value.equals("M")&&lookAhead1.equals("M")){
            index_va = index+2;
            value_va = 2000;
        }
        else if(value.equals("M")){
            index_va = index+1;
            value_va = 1000;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] fivehundreds(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(value.equals("D")&&(lookAhead1.equals("D")||lookAhead1.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+1)+". Zeichen: "+lookAhead1);
            System.exit(0);
        }
        else if(value.equals("D")){
            index_va = index+1;
            value_va = 500;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] hundreds(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1,lookAhead2,lookAhead3;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(converterInput.size()>index+2){
            lookAhead2 = converterInput.get(index+2);
        }
        else {
            lookAhead2 = "";
        }
        if(converterInput.size()>index+3){
            lookAhead3 = converterInput.get(index+3);
        }
        else {
            lookAhead3 = "";
        }
        if(value.equals("C")&&lookAhead1.equals("C")&&lookAhead2.equals("C")&&(lookAhead3.equals("C")||lookAhead3.equals("D")||lookAhead3.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+3)+". Zeichen: "+lookAhead3);
            System.exit(0);
        }
        else if(value.equals("C")&&lookAhead1.equals("C")&&lookAhead2.equals("C")){
            index_va = index+3;
            value_va = 300;
        }
        else if(value.equals("C")&&lookAhead1.equals("C")&&(lookAhead2.equals("D")||lookAhead2.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("C")&&lookAhead1.equals("C")){
            index_va = index+2;
            value_va = 200;
        }
        else if(value.equals("C")&&(lookAhead1.equals("M")||lookAhead1.equals("D")||lookAhead1.equals("L")||lookAhead1.equals("X")||lookAhead1.equals("V")||lookAhead1.equals("I"))&&lookAhead2.equals("C")){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("C")){
            index_va = index+1;
            value_va = 100;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] fifties(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(value.equals("L")&&(lookAhead1.equals("L")||lookAhead1.equals("M")||lookAhead1.equals("D")||lookAhead1.equals("C"))){
            System.out.println("Semantischer Fehler in Position: "+(index+1)+". Zeichen: "+lookAhead1);
            System.exit(0);
        }
        else if(value.equals("L")){
            index_va = index+1;
            value_va = 50;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] tens(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1,lookAhead2,lookAhead3;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(converterInput.size()>index+2){
            lookAhead2 = converterInput.get(index+2);
        }
        else {
            lookAhead2 = "";
        }
        if(converterInput.size()>index+3){
            lookAhead3 = converterInput.get(index+3);
        }
        else {
            lookAhead3 = "";
        }
        if(value.equals("X")&&lookAhead1.equals("X")&&lookAhead2.equals("X")&&(lookAhead3.equals("X")||lookAhead3.equals("L")||lookAhead3.equals("C")||lookAhead3.equals("D")||lookAhead3.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+3)+". Zeichen: "+lookAhead3);
            System.exit(0);
        }
        else if(value.equals("X")&&lookAhead1.equals("X")&&lookAhead2.equals("X")){
            index_va = index+3;
            value_va = 30;
        }
        else if(value.equals("X")&&lookAhead1.equals("X")&&(lookAhead2.equals("L")||lookAhead2.equals("C")||lookAhead2.equals("D")||lookAhead2.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("X")&&lookAhead1.equals("X")){
            index_va = index+2;
            value_va = 20;
        }
        else if(value.equals("X")&&(lookAhead1.equals("M")||lookAhead1.equals("D"))){
            System.out.println("Semantischer Fehler in Position: "+(index+1)+". Zeichen: "+lookAhead1);
            System.exit(0);
        }
        else if(value.equals("X")&&(lookAhead1.equals("C")||lookAhead1.equals("L")||lookAhead1.equals("X")||lookAhead1.equals("V")||lookAhead1.equals("I"))&&lookAhead2.equals("X")){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("X")){
            index_va = index+1;
            value_va = 10;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] fives(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(value.equals("V")&&(lookAhead1.equals("V")||lookAhead1.equals("X")||lookAhead1.equals("L")||lookAhead1.equals("C")||lookAhead1.equals("D")||lookAhead1.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+1)+". Zeichen: "+lookAhead1);
            System.exit(0);
        }
        else if(value.equals("V")){
            index_va = index+1;
            value_va = 5;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

    static int[] ones(ArrayList<String> converterInput, int index){
        int[] va = new int[2];
        int index_va=0; 
        int value_va=0;
        String value = converterInput.get(index);
        String lookAhead1,lookAhead2,lookAhead3;
        if(converterInput.size()>index+1){
            lookAhead1 = converterInput.get(index+1);
        }
        else {
            lookAhead1 = "";
        }
        if(converterInput.size()>index+2){
            lookAhead2 = converterInput.get(index+2);
        }
        else {
            lookAhead2 = "";
        }
        if(converterInput.size()>index+3){
            lookAhead3 = converterInput.get(index+3);
        }
        else {
            lookAhead3 = "";
        }
        if(value.equals("I")&&lookAhead1.equals("I")&&lookAhead2.equals("I")&&(lookAhead3.equals("I")||lookAhead3.equals("V")||lookAhead3.equals("X")||lookAhead3.equals("L")||lookAhead3.equals("C")||lookAhead3.equals("D")||lookAhead3.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+3)+". Zeichen: "+lookAhead3);
            System.exit(0);
        }
        else if(value.equals("I")&&lookAhead1.equals("I")&&lookAhead2.equals("I")){
            index_va = index+3;
            value_va = 3;
        }
        else if(value.equals("I")&&lookAhead1.equals("I")&&(lookAhead2.equals("V")||lookAhead2.equals("X")||lookAhead2.equals("L")||lookAhead2.equals("C")||lookAhead2.equals("D")||lookAhead2.equals("M"))){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("I")&&lookAhead1.equals("I")){
            index_va = index+2;
            value_va = 2;
        }
        else if(value.equals("I")&&(lookAhead1.equals("M")||lookAhead1.equals("D")||lookAhead1.equals("C")||lookAhead1.equals("L"))){
            System.out.println("Semantischer Fehler in Position: "+(index+1)+". Zeichen: "+lookAhead1);
            System.exit(0);
        }
        else if(value.equals("I")&&(lookAhead1.equals("V")||lookAhead1.equals("X"))&&lookAhead2.equals("I")){
            System.out.println("Semantischer Fehler in Position: "+(index+2)+". Zeichen: "+lookAhead2);
            System.exit(0);
        }
        else if(value.equals("I")){
            index_va = index+1;
            value_va = 1;
        }
        va[0] = index_va;
        va[1] = value_va;
        return va;
    }

}
