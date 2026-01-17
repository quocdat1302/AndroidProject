package com.example.bai3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapbuoi1.R;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    private TextView tvResult;
    private String currentInput = "";
    private String lastInput = "";
    private char lastOperator = ' ';
    private boolean isNewOp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvResult = findViewById(R.id.tvResult);

        // Number buttons
        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            if (isNewOp) {
                currentInput = "";
                isNewOp = false;
            }
            currentInput += b.getText().toString();
            updateDisplay();
        };
        for (int id : numberIds) findViewById(id).setOnClickListener(numberListener);

        // Operator buttons
        findViewById(R.id.btnPlus).setOnClickListener(v -> setOperator('+'));
        findViewById(R.id.btnMinus).setOnClickListener(v -> setOperator('-'));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator('*'));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator('/'));

        // Action buttons
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());
        findViewById(R.id.btnAC).setOnClickListener(v -> {
            currentInput = "";
            lastInput = "";
            lastOperator = ' ';
            isNewOp = true;
            tvResult.setText("0");
        });
        findViewById(R.id.btnComma).setOnClickListener(v -> {
            if (!currentInput.contains(".")) {
                currentInput += (currentInput.isEmpty() ? "0." : ".");
                isNewOp = false;
                updateDisplay();
            }
        });
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                double val = Double.parseDouble(currentInput) * -1;
                currentInput = formatResult(val);
                updateDisplay();
            }
        });
        findViewById(R.id.btnPercent).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                double val = Double.parseDouble(currentInput) / 100;
                currentInput = formatResult(val);
                updateDisplay();
            }
        });
    }

    private void setOperator(char op) {
        if (!currentInput.isEmpty()) {
            if (!lastInput.isEmpty()) {
                calculate();
            }
            lastOperator = op;
            lastInput = currentInput;
            isNewOp = true;
        }
    }

    private void calculate() {
        if (lastOperator == ' ' || lastInput.isEmpty() || currentInput.isEmpty()) return;

        double num1 = Double.parseDouble(lastInput);
        double num2 = Double.parseDouble(currentInput);
        double result = 0;

        switch (lastOperator) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/': 
                if (num2 != 0) result = num1 / num2;
                else {
                    tvResult.setText("Error");
                    currentInput = "";
                    lastInput = "";
                    lastOperator = ' ';
                    isNewOp = true;
                    return;
                }
                break;
        }

        currentInput = formatResult(result);
        tvResult.setText(currentInput.replace(".", ","));
        lastInput = "";
        lastOperator = ' ';
        isNewOp = true;
    }

    private void updateDisplay() {
        if (currentInput.isEmpty()) tvResult.setText("0");
        else tvResult.setText(currentInput.replace(".", ","));
    }

    private String formatResult(double d) {
        if (d == (long) d) return String.format("%d", (long) d);
        return new DecimalFormat("0.########").format(d);
    }
}
