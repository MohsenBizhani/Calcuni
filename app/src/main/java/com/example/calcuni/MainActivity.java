package com.example.calcuni;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private TextView resultTextView;
    private StringBuilder currentInput;
    private Double lastResult;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.res);
        currentInput = new StringBuilder();
        lastResult = null;
        operator = "";

        setupNumberButtons();
        setupOperatorButtons();
        setupEqualButton();
        setupClearButton();
        setupDecimalButton();
    }

    private void setupNumberButtons() {
        int[] numberButtonIds = {
                R.id.seven_btn, R.id.eight_btn, R.id.nine_btn,
                R.id.four_btn, R.id.five_btn, R.id.six_btn,
                R.id.one_btn, R.id.two_btn, R.id.three_btn,
                R.id.zero_btn
        };

        for (int buttonId : numberButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(view -> onNumberClick(button));
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtonIds = {
                R.id.divide_btn, R.id.multi_btn, R.id.plus_btn, R.id.minus_btn
        };

        for (int buttonId : operatorButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(view -> onOperatorClick(button));
        }
    }

    private void setupEqualButton() {
        Button equalsButton = findViewById(R.id.equals_btn);
        equalsButton.setOnClickListener(this::onEqualsClick);
    }

    private void setupClearButton() {
        Button clearButton = findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(this::onClearClick);
    }

    private void setupDecimalButton() {
        Button decimalButton = findViewById(R.id.decimal_btn);
        decimalButton.setOnClickListener(this::onDecimalClick);
    }

    private void onNumberClick(Button button) {
        currentInput.append(button.getText());
        resultTextView.setText(currentInput.toString());
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        if (currentInput.length() > 0) {
            if (lastResult != null && !operator.isEmpty()) {
                double currentValue = Double.parseDouble(currentInput.toString());
                lastResult = performOperation(lastResult, currentValue, operator);
                resultTextView.setText(String.valueOf(lastResult));
                currentInput.setLength(0);
            } else if (lastResult == null) {
                lastResult = Double.parseDouble(currentInput.toString());
            }
            operator = button.getText().toString();
            currentInput.setLength(0);
        }
        operator = button.getText().toString();

    }


    public void onEqualsClick(View view) {
        if (lastResult != null && !operator.isEmpty() && currentInput.length() > 0) {
            double currentValue = Double.parseDouble(currentInput.toString());
            lastResult = performOperation(lastResult, currentValue, operator);
            currentInput.setLength(0);
            operator = "";
            resultTextView.setText(String.valueOf(lastResult));
        } else if (lastResult != null && operator.isEmpty() && currentInput.length() == 0) {
            double currentValue = lastResult;
            lastResult = performOperation(lastResult, currentValue, operator);
            resultTextView.setText(String.valueOf(lastResult));
        }
    }

    private void onClearClick(View view) {
        currentInput.setLength(0);
        operator = "";
        resultTextView.setText("");
        lastResult = null;
    }

    private void onDecimalClick(View view) {
        if (currentInput.indexOf(".") == -1) {
            currentInput.append(".");
            resultTextView.setText(currentInput.toString());
        }
    }

    private double performOperation(double first, double second, String operator) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second == 0) {
                    return Double.NaN;
                }
                return first / second;
            default:
                return second;
        }
    }
}
