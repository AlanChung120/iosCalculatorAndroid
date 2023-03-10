package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonDivide, buttonMultiply, buttonPlus, buttonMinus;
    private TextView result;

    private static final String error = "Math Error";
    private static final int maxChar = 10;
    double answer;
    private boolean secondInput, resultPrev = false;
    private String operation = "";
    private int currentNum = 0;
    String[] numbers = {"0", ""};

    private String formatNum(String num) {
        String numFormatted = "";
        if (num.equals(error)) {
            numFormatted = error;
        } else if (num.contains("E") &&
                (Integer.parseInt(num.substring(num.indexOf('E') + 1)) > 100 ||
                Integer.parseInt(num.substring(num.indexOf('E') + 1)) < -100)) {
            numFormatted = error;
        } else {

            // Round the result
            String numOnly = "";
            int decimalSpot = num.indexOf('.');
            if (decimalSpot != -1 && resultPrev && num.length() > maxChar + 1) {
                int roundTo = 0;
                int eSpot = num.indexOf('E');
                if (eSpot != -1) {
                    numOnly = num.substring(0, eSpot);
                    roundTo = 6;
                } else {
                    numOnly = num;
                    if (num.charAt(0) == '-') {
                        roundTo = maxChar - decimalSpot + 1;
                    } else {
                        roundTo = maxChar - decimalSpot;
                    }
                }
               double toRound = Double.parseDouble(numOnly);
               numOnly = Double.toString(Math.round(toRound * Math.pow(10, roundTo)) / Math.pow(10, roundTo));
               if (eSpot != -1) {
                    if (numOnly.equals("10.0")) {
                        numOnly = "1.0E" + (Integer.parseInt(num.substring(eSpot+1)) + 1);
                    } else {
                        numOnly += num.substring(eSpot);
                    }
               }
               num = numOnly;
            }

            // Add commas
            decimalSpot = num.indexOf('.');
            if (decimalSpot == -1) {
                decimalSpot = num.length();
            }
            if (num.charAt(0) == '-') {
                numFormatted += "-";
                numOnly = num.substring(1, decimalSpot);
            } else {
                numOnly = num.substring(0, decimalSpot);
            }
            for (int i = 0; i < numOnly.length(); i++) {
                if ((numOnly.length() - i) % 3 == 0 && i != 0) {
                    numFormatted += "," + numOnly.charAt(i);
                } else {
                    numFormatted += numOnly.charAt(i);
                }
            }

            // Format decimals
            if (decimalSpot != num.length() &&
                    !(num.endsWith(".0") && resultPrev)) {
                numFormatted += num.substring(decimalSpot);
            }

        }
        return numFormatted;
    }

    // calculate for calculator
    private String calculate(String op, String num1, String num2) {
        if (op.equals("/")) {
            if (num2.equals("0")) {
                num1 = error;
            } else {
                num1 = Double.toString(Double.parseDouble(num1) / Double.parseDouble(num2));
            }
        } else if (op.equals("*")) {
            num1 = Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2));
        } else if (op.equals("+")) {
            num1 = Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2));
        } else if (op.equals("-")) {
            num1 = Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2));
        }
        return num1;
    }

    @Override
    public void onClick(View v) {
        if (secondInput) {
            currentNum = 1;
        }
        if (numbers[0].equals(error)) {
            secondInput = false;
            resultPrev = false;
            operation = "";
            currentNum = 0;
            numbers[0] = "0";
            numbers[1] = "";
        }
        switch (v.getId()) {
            case R.id.buttonAc:
                secondInput = false;
                resultPrev = false;
                operation = "";
                currentNum = 0;
                numbers[0] = "0";
                numbers[1] = "";
                break;
            case R.id.buttonSign:
                if (numbers[1].equals("") && currentNum == 1) {
                    numbers[1] = "0";
                }
                if (numbers[currentNum].charAt(0) == '-') {
                    numbers[currentNum] = numbers[currentNum].substring(1);
                } else {
                    numbers[currentNum] = "-" + numbers[currentNum];
                }
                break;
            case R.id.buttonPercent:
                if (numbers[1].equals("") && currentNum == 1) {
                    numbers[1] = numbers[0];
                }
                answer = Double.parseDouble(numbers[currentNum]);
                answer *= 0.01;
                numbers[currentNum] = Double.toString(answer);
                resultPrev = true;
                break;
            case R.id.buttonDivide:
                if (!operation.equals("") && !numbers[1].equals("") && secondInput) { // behaves like equals
                    numbers[0] = calculate(operation, numbers[0], numbers[1]);
                    resultPrev = true;
                } else {
                    secondInput = true;
                }
                operation = "/";
                currentNum = 0;
                numbers[1] = "";
                break;
            case R.id.buttonMultiply:
                if (!operation.equals("") && !numbers[1].equals("") && secondInput) { // behaves like equals
                    numbers[0] = calculate(operation, numbers[0], numbers[1]);
                    resultPrev = true;
                } else {
                    secondInput = true;
                }
                operation = "*";
                currentNum = 0;
                numbers[1] = "";
                break;
            case R.id.buttonMinus:
                if (!operation.equals("") && !numbers[1].equals("") && secondInput) { // behaves like equals
                    numbers[0] = calculate(operation, numbers[0], numbers[1]);
                    resultPrev = true;
                } else {
                    secondInput = true;
                }
                operation = "-";
                currentNum = 0;
                numbers[1] = "";
                break;
            case R.id.buttonPlus:
                if (!operation.equals("") && !numbers[1].equals("") && secondInput) { // behaves like equals
                    numbers[0] = calculate(operation, numbers[0], numbers[1]);
                    resultPrev = true;
                } else {
                    secondInput = true;
                }
                operation = "+";
                currentNum = 0;
                numbers[1] = "";
                break;
            case R.id.buttonEquals:
                if (numbers[1].equals("")) {
                    numbers[1] = numbers[0];
                }
                numbers[0] = calculate(operation, numbers[0], numbers[1]);
                currentNum = 0;
                secondInput = false;
                resultPrev = true;
                break;
            case R.id.buttonDot:
                if (numbers[1].equals("") && currentNum == 1) {
                    numbers[1] = "0";
                }
                if (resultPrev) {
                    numbers[currentNum] = "0.";
                    resultPrev = false;
                } else if (!numbers[currentNum].contains(".")) {
                    numbers[currentNum] += ".";
                }
                break;
            case R.id.button0:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (!numbers[currentNum].equals("0") && !numbers[currentNum].equals("-0") &&
                        (numbers[currentNum].replace(".","").replace("-","").length() < maxChar)) {
                    numbers[currentNum] += "0";
                }
                break;
            case R.id.button1:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "1");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "1";
                }
                break;
            case R.id.button2:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "2");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "2";
                }
                break;
            case R.id.button3:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "3");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "3";
                }
                break;
            case R.id.button4:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "4");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "4";
                }
                break;
            case R.id.button5:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "5");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "5";
                }
                break;
            case R.id.button6:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "6");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "6";
                }
                break;
            case R.id.button7:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "7");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "7";
                }
                break;
            case R.id.button8:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "8");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "8";
                }
                break;
            case R.id.button9:
                if (resultPrev && currentNum == 0) {
                    resultPrev = false;
                    numbers[0] = "";
                }
                if (numbers[currentNum].equals("0") || numbers[currentNum].equals("-0")) {
                    numbers[currentNum] = numbers[currentNum].replace("0", "9");
                } else if (numbers[currentNum].replace(".","").replace("-","").length() < maxChar) {
                    numbers[currentNum] += "9";
                }
                break;
            default:
                break;
        }

        result.setText(formatNum(numbers[currentNum]));
    }

    // create elements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAc = findViewById(R.id.buttonAc);
        Button buttonSign = findViewById(R.id.buttonSign);
        Button buttonPercent = findViewById(R.id.buttonPercent);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        buttonAc.setOnClickListener(this);
        buttonSign.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        result = findViewById(R.id.result);

    }
}