/*
 *
 */
package calculator;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;

//import java.lang.NumberFormatException;


/**
 *
 * @author daniel
 */
public class Calculator extends Application {
    
    /* TO DO NOTES
    - re-organize code to be neater. consider using generics to manage input, which will remove repeating code such as clearing the text fields
        although using more methods might be a nice and easy way to achieve the same effect without much complexity
    - re-organize UI code into separate methods (which I think can be done outside of the start() method. making it easier to make changes of specific 
        aspects of the UI rather than scrolling through all of the mess
    - implement the user-entered calculations (allow them to enter a multi-step calculation/formula and calculate it without having to use UI buttons
    - add a file menu, for exiting and any options that I want to add later (maybe a UI style option and other little tweaks)
    - dark-mode UI style from file menu option
    - add the ability to save their results textarea to a text file if they want a copy for their records or usage in another program (or something) (add to file menu)    
    - add an alert box when an exception is thrown (particularly during the validateInput() method call
    */    
    Stage stage;    //the window
    
    //when a user presses a UI button, this will change to true and perform the calculation
    Boolean addition = false;
    Boolean subtraction = false;
    Boolean multiplication = false;
    Boolean division = false;
    
    Boolean validated = false;
    
    //lock the numeric buttons after a calculation so that the user doesn't append digits to their result
    //before selecting another operation (such as addition) and then entering new values
    Boolean lockNumPad = false;
    
    //ArrayList operators = new ArrayList();    //will store all operators between values for calculating the result later
    //ArrayList values = new ArrayList();       //contains all values that will later be computed in conjunction with the operators ArrayList
    
    Double storedValue = null;    //the value stored in order to compute once the currentValue is given a value
    Double currentValue = null;   //the latest value entered
    Double result = null; //the final result
    
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton, decimalButton;
    TextField inputArea;
    TextArea resultArea;
    
    @Override
    public void start(Stage primaryStage){// throws Exception{
        stage = primaryStage;
        stage.setTitle("Calculator");        
        
        inputArea = new TextField();
        resultArea = new TextArea();
        resultArea.setPrefRowCount(5);
        
        oneButton = new Button("1");
        oneButton.setOnAction(e -> {
           inputArea.appendText("1");
        });
        
        twoButton = new Button("2");
        twoButton.setOnAction(e -> {
           inputArea.appendText("2");           
        });
        
        threeButton = new Button("3");
        threeButton.setOnAction(e -> {
           inputArea.appendText("3");
        });
        
        fourButton = new Button("4");
        fourButton.setOnAction(e -> {
           inputArea.appendText("4");
        });
        
        fiveButton = new Button("5");
        fiveButton.setOnAction(e -> {
           inputArea.appendText("5");
        });
        
        sixButton = new Button("6");
        sixButton.setOnAction(e -> {
           inputArea.appendText("6");
        });
        
        sevenButton = new Button("7");
        sevenButton.setOnAction(e -> {
           inputArea.appendText("7");
        });
        
        eightButton = new Button("8");
        eightButton.setOnAction(e -> {
           inputArea.appendText("8");
        });
        
        nineButton = new Button("9");
        nineButton.setOnAction(e -> {
           inputArea.appendText("9");
        });
        
        zeroButton = new Button("0");
        zeroButton.setOnAction(e -> {
           inputArea.appendText("0"); 
        });
        
        decimalButton = new Button(".");
        decimalButton.setOnAction(e -> {
            inputArea.appendText(".");
            
        });
        
        Button additionButton = new Button("+");
        additionButton.setOnAction(e -> {
            addition = true;
            operatorCheck("+"); // check to see if the user has previously already hit a different operator (changed their mind to this instead)
            validated = validateInput(inputArea.getText());
            if(validated == true){
                if(storedValue == null){                
                        storedValue = Double.parseDouble(inputArea.getText());
                        resultArea.appendText(storedValue + " + ");                
                }
                else{
                    currentValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(currentValue + " + ");
                }
            } // ends validated
            unlockNumberPad();
            inputArea.setText("");            
        });
        
        Button subtractionButton = new Button("-");
        subtractionButton.setOnAction(e -> {
            subtraction = true;
            operatorCheck("-");
            validated = validateInput(inputArea.getText());
            if(validated){
                if(storedValue == null){
                    storedValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(storedValue + " - ");
                }
                else{
                    currentValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(currentValue + " - ");
                }
            }//ends validated
            unlockNumberPad();
            inputArea.setText("");            
        });
        
        Button multiplicationButton = new Button("*");
        multiplicationButton.setOnAction(e -> {
            multiplication = true;
            operatorCheck("*");
            validated = validateInput(inputArea.getText());
            if(validated == true){
                if(storedValue == null){
                    storedValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(storedValue + " * ");
                }
                else{
                    currentValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(currentValue + " * ");
                }
            } //ends validated
            unlockNumberPad();
            inputArea.setText("");            
        });
        
        Button divisionButton = new Button("/");
        divisionButton.setOnAction(e -> {
            division = true;
            operatorCheck("/");
            validated = validateInput(inputArea.getText());
            if(validated == true){
                if(storedValue == null){
                    storedValue = Double.parseDouble(inputArea.getText());
                        resultArea.appendText(storedValue + " / ");
                }
                else{
                    currentValue = Double.parseDouble(inputArea.getText());
                    resultArea.appendText(currentValue + " / ");
                }
            }
            unlockNumberPad();
            inputArea.setText("");            
        });
        
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            unlockNumberPad();
            storedValue = null;
            currentValue = null;
            result = null;
            addition = false;
            subtraction = false;
            multiplication = false;
            division = false;
            inputArea.setText("");
            resultArea.setText("");
            System.out.println("Cleared Calculator Memory");
        });
        
        // I'm may move this into a separate method later
        Button equalsButton = new Button("=");        
        equalsButton.setOnAction((ActionEvent e) -> {
            if(validateInput(inputArea.getText())){
                calculateResult(inputArea, resultArea); //change this later to be Double casts (probably)... or keep it, doesn't matter really because those UI controls are publically declared (but weren't before)
                lockNumberPad();
            }
            //for batch calculation from text area, incomplete functionality right now
            /*
            currentValue = Integer.parseInt(inputArea.getText());   //prepare the last value entered for processing
            values.add(currentValue);   //add the last value entered into the collection for processing
            computeResult(inputArea, resultArea);            
            */
        });        
        
        VBox vBox = new VBox();
        HBox clearButtonHBox = new HBox();
        HBox row1HBox = new HBox();
        HBox row2HBox = new HBox();
        HBox row3HBox = new HBox();
        HBox row4HBox = new HBox();        
        
        ObservableList vBoxList = vBox.getChildren();        
        
        //fill the width of the window
        HBox.setHgrow(clearButton, Priority.ALWAYS);
        HBox.setHgrow(oneButton, Priority.ALWAYS);
        HBox.setHgrow(twoButton, Priority.ALWAYS);
        HBox.setHgrow(threeButton, Priority.ALWAYS);
        HBox.setHgrow(subtractionButton, Priority.ALWAYS);
        
        clearButton.setMaxWidth(Double.MAX_VALUE);        
        oneButton.setMaxWidth(Double.MAX_VALUE);
        twoButton.setMaxWidth(Double.MAX_VALUE);
        threeButton.setMaxWidth(Double.MAX_VALUE);
        subtractionButton.setMaxWidth(Double.MAX_VALUE);
        
        clearButton.setMaxHeight(Double.MAX_VALUE);
        oneButton.setMaxHeight(Double.MAX_VALUE);
        twoButton.setMaxHeight(Double.MAX_VALUE);
        threeButton.setMaxHeight(Double.MAX_VALUE);
        subtractionButton.setMaxHeight(Double.MAX_VALUE);
        clearButton.setMaxHeight(Double.MAX_VALUE);
        
        ObservableList clearButtonHBoxList = clearButtonHBox.getChildren();
        clearButtonHBoxList.addAll(clearButton);
        
        ObservableList row1HBoxList = row1HBox.getChildren();        
        row1HBoxList.addAll(oneButton, twoButton, threeButton, subtractionButton);                
    
        HBox.setHgrow(fourButton, Priority.ALWAYS);
        HBox.setHgrow(fiveButton, Priority.ALWAYS);
        HBox.setHgrow(sixButton, Priority.ALWAYS);
        HBox.setHgrow(additionButton, Priority.ALWAYS);
        
        fourButton.setMaxWidth(Double.MAX_VALUE);
        fiveButton.setMaxWidth(Double.MAX_VALUE);
        sixButton.setMaxWidth(Double.MAX_VALUE);
        additionButton.setMaxWidth(Double.MAX_VALUE);
        
        fourButton.setMaxHeight(Double.MAX_VALUE);
        fiveButton.setMaxHeight(Double.MAX_VALUE);
        sixButton.setMaxHeight(Double.MAX_VALUE);
        additionButton.setMaxHeight(Double.MAX_VALUE);
        
        ObservableList row2HBoxList = row2HBox.getChildren();
        row2HBoxList.addAll(fourButton, fiveButton, sixButton, additionButton);
                
        HBox.setHgrow(sevenButton, Priority.ALWAYS);
        HBox.setHgrow(eightButton, Priority.ALWAYS);
        HBox.setHgrow(nineButton, Priority.ALWAYS);
        HBox.setHgrow(multiplicationButton, Priority.ALWAYS);
        
        sevenButton.setMaxWidth(Double.MAX_VALUE);
        eightButton.setMaxWidth(Double.MAX_VALUE);
        nineButton.setMaxWidth(Double.MAX_VALUE);
        multiplicationButton.setMaxWidth(Double.MAX_VALUE);
        
        sevenButton.setMaxHeight(Double.MAX_VALUE);
        eightButton.setMaxHeight(Double.MAX_VALUE);
        nineButton.setMaxHeight(Double.MAX_VALUE);
        multiplicationButton.setMaxHeight(Double.MAX_VALUE);
        
        ObservableList row3HBoxList = row3HBox.getChildren();
        row3HBoxList.addAll(sevenButton, eightButton, nineButton, multiplicationButton);
                        
        HBox.setHgrow(zeroButton, Priority.ALWAYS);
        HBox.setHgrow(decimalButton, Priority.ALWAYS);
        HBox.setHgrow(divisionButton, Priority.ALWAYS);      
        HBox.setHgrow(equalsButton, Priority.ALWAYS);      
        
        zeroButton.setMaxWidth(Double.MAX_VALUE);
        decimalButton.setMaxWidth(Double.MAX_VALUE);
        divisionButton.setMaxWidth(Double.MAX_VALUE);
        equalsButton.setMaxWidth(Double.MAX_VALUE);
        
        zeroButton.setMaxHeight(Double.MAX_VALUE);
        decimalButton.setMaxHeight(Double.MAX_VALUE);
        divisionButton.setMaxHeight(Double.MAX_VALUE);
        equalsButton.setMaxHeight(Double.MAX_VALUE);
        
        ObservableList row4HBoxList = row4HBox.getChildren();
        row4HBoxList.addAll(zeroButton, decimalButton, divisionButton, equalsButton);
        
        VBox.setMargin(inputArea, new Insets(20,20,0,20));    //Insets(top, right, bottom, left)
        VBox.setMargin(resultArea, new Insets(20,20,20,20));
        VBox.setMargin(clearButtonHBox, new Insets(0,20,0,20));
        VBox.setMargin(row1HBox, new Insets(0,20,0,20));
        VBox.setMargin(row2HBox, new Insets(0,20,0,20));
        VBox.setMargin(row3HBox, new Insets(0,20,0,20));
        VBox.setMargin(row4HBox, new Insets(0,20,20,20));        
        
        Font f = new Font("Times New Roman", 24);
        inputArea.setFont(f);
        resultArea.setFont(f);
        oneButton.setFont(f);
        twoButton.setFont(f);
        threeButton.setFont(f);
        fourButton.setFont(f);
        fiveButton.setFont(f);
        sixButton.setFont(f);
        sevenButton.setFont(f);
        eightButton.setFont(f);
        nineButton.setFont(f);
        zeroButton.setFont(f);
        decimalButton.setFont(f);
        clearButton.setFont(f);
        additionButton.setFont(f);
        subtractionButton.setFont(f);
        multiplicationButton.setFont(f);
        divisionButton.setFont(f);
        equalsButton.setFont(f);
        
        vBox.setSpacing(4.0);
        clearButtonHBox.setSpacing(4.0);
        row1HBox.setSpacing(4.0);
        row2HBox.setSpacing(4.0);
        row3HBox.setSpacing(4.0);
        row4HBox.setSpacing(4.0);
        
        VBox.setVgrow(clearButtonHBox, Priority.ALWAYS);
        VBox.setVgrow(row1HBox, Priority.ALWAYS);
        VBox.setVgrow(row2HBox, Priority.ALWAYS);
        VBox.setVgrow(row3HBox, Priority.ALWAYS);
        VBox.setVgrow(row4HBox, Priority.ALWAYS);
        
        vBoxList.addAll(inputArea, resultArea, clearButtonHBox, row1HBox, row2HBox, row3HBox, row4HBox);
        /*
        // this is just some UI design code that I may use later to change the look and feel.
        oneButton.setPrefSize(50.0, 50.0);
        twoButton.setPrefSize(50.0, 50.0);
        threeButton.setPrefSize(50.0, 50.0);
        
        fourButton.setPrefSize(50.0, 50.0);
        fiveButton.setPrefSize(50.0, 50.0);
        sixButton.setPrefSize(50.0, 50.0);
        sevenButton.setPrefSize(50.0, 50.0);
        eightButton.setPrefSize(50.0, 50.0);
        nineButton.setPrefSize(50.0, 50.0);
        zeroButton.setPrefSize(50.0, 50.0);
        decimalButton.setPrefSize(50.0, 50.0
        
        clearButton.setPrefSize(100.0, 50.0);
        additionButton.setPrefSize(50.0, 50.0);
        subtractionButton.setPrefSize(50.0, 50.0);
        multiplicationButton.setPrefSize(50.0, 50.0);
        divisionButton.setPrefSize(50.0, 50.0);
        equalsButton.setPrefSize(50.0, 50.0);        
        */
        Scene scene = new Scene(vBox, 500, 450);
        stage.setScene(scene);
        stage.show();
        
    }   //ends start() method

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);        
    }
    
    public double addition(Double x, Double y){
        result = x + y;
        System.out.println("X is: " + x + " Y is: " + y);
        storedValue = result;
        addition = false;
        System.out.println("Result is " + result);        
        return result;        
    }
    
    public double subtraction(Double x, Double y){
        System.out.println("X is: " + x + " Y is: " + y);
        result = x - y;
        storedValue = result;
        subtraction = false;
        System.out.println("Result is " + result);        
        return result;        
    }
    
    public double multiplication(Double x, Double y){
        System.out.println("X is: " + x + " Y is: " + y);
        result = x * y;
        storedValue = result;
        multiplication = false;
        System.out.println("Result is " + result);        
        return result;
    }
    
    public double division(Double x, Double y){
        System.out.println("X is: " + x + " Y is: " + y);
        result = x / y;
        storedValue = result;
        division = false;
        System.out.println("Result is " + result);        
        return result;
    }
    
    /*
    * This method will check the current selected operator, such as addition (+), and then allow for the change to another operator
    * when the user has changed their mind as to what operator to use in the current calculation     
    */
    public void operatorCheck(String op){
        if(op.equalsIgnoreCase("+")){
            if(subtraction){
                operatorReset();
                replaceOperator("-","+");
            }
            else if(multiplication){
                operatorReset();
                replaceOperator("*", "+");
            }
            else if(division){
                operatorReset();
                replaceOperator("/", "+");
            }
            addition = true;
        }
        else if(op.equals("-")){
            if(addition){
                operatorReset();
                replaceOperator("+","-");                
            }
            else if(multiplication){
                operatorReset();
                replaceOperator("*", "-");
            }
            else if(division){
                operatorReset();
                replaceOperator("/", "-");
            }
            subtraction = true;
        }
        else if(op.equals("*")){
            if(addition){
                operatorReset();
                replaceOperator("+","*");                
            }
            else if(subtraction){
                operatorReset();
                replaceOperator("-", "*");
            }
            else if(division){
                operatorReset();
                replaceOperator("/", "*");
            }
            multiplication = true;            
        }
        else if(op.equals("/")){
            if(addition){
                operatorReset();
                replaceOperator("+","/");                
            }
            else if(subtraction){
                operatorReset();
                replaceOperator("-", "/");
            }
            else if(multiplication){
                operatorReset();
                replaceOperator("*", "/");
            }
            division = true;  
        }
    }   //ends operatorCheck();
    
    // if a user selects an operator, then selects a different operator, the output text needs to reflect the change.
    // this handles that.
    public void replaceOperator(String oldOp, String newOp){
        String resultAreaText = resultArea.getText();
        int position = resultAreaText.lastIndexOf(oldOp);        
        resultArea.replaceText(position, resultAreaText.length(), newOp + " ");
    }   // ends replaceOperator
    
    //resets all operator booleans to reduce repeating code
    public void operatorReset(){
        addition = false;
        subtraction = false;
        multiplication = false;
        division = false;        
    }
    
    // searches to see which operator is currently active
    // not currently used
    public String operatorSearch(String desiredOp){
        if(addition){
            return "+";
        }
        else if(subtraction){
            return "-";
        }
        else if(multiplication){
            return "*";            
        }
        else if(division){
            return "/";
        }
        return "";
    }   // ends operatorSearch()
    
    /*
    This method will take the input given to the calculator and validate whether it's numeric or not    
    */    
    public boolean validateInput(String inputString){
        Double inputVal = null;
        try{
            inputVal = Double.parseDouble(inputString);
            System.out.println("Validated");
            
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            if(inputVal == null){
                //throw;
                System.out.println("The user has entered a non-viable calculation.");
                System.out.println("Please clear the memory and re-enter input; or edit the input area.");
                return false;
            }
            if(inputVal.isNaN()){ //== Double.NaN){
                System.out.println("The user has entered a non-viable calculation.");
                System.out.println("Please clear the memory and re-enter input; or edit the input area.");
                return false;
            }            
        }
        return true;
    } // ends validateResult method
        
    public void calculateResult(TextField inputArea, TextArea resultArea){
            currentValue = Double.parseDouble(inputArea.getText());

            if(addition){
                result = addition(storedValue, currentValue);
            }
            else if(subtraction){
                result = subtraction(storedValue, currentValue);
            }
            else if(multiplication){
                result = multiplication(storedValue, currentValue);
            }
            else if(division){
                result = division(storedValue, currentValue);
            }

            storedValue = null;

            inputArea.setText(String.valueOf(result));
            resultArea.appendText(currentValue + " = " + result + "\n");
    }//ends compute result
    
    /*
    After performing a calculation (by pressing the '=' key, it locks out the keypad 
    so that the user must enter a new operator (such as "+" key) so that digits 
    are not appended to the result displayed (which would be ignored anyway in the subsequent calculation)    
    */
    public void lockNumberPad(){
        oneButton.setDisable(true);
        twoButton.setDisable(true);
        threeButton.setDisable(true);
        fourButton.setDisable(true);
        fiveButton.setDisable(true);
        sixButton.setDisable(true);
        sevenButton.setDisable(true);
        eightButton.setDisable(true);
        nineButton.setDisable(true);
        zeroButton.setDisable(true);
        decimalButton.setDisable(true);
    }
    
    /*
    This method unlocks the number pad once the user has entered a new operator which then allows for new numeric input
    for the next stage of the user's calculations
    */    
    public void unlockNumberPad(){
        oneButton.setDisable(false);
        twoButton.setDisable(false);
        threeButton.setDisable(false);
        fourButton.setDisable(false);
        fiveButton.setDisable(false);
        sixButton.setDisable(false);
        sevenButton.setDisable(false);
        eightButton.setDisable(false);
        nineButton.setDisable(false);
        zeroButton.setDisable(false);
        decimalButton.setDisable(false);
    }    
}
