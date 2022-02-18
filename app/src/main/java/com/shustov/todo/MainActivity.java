package com.shustov.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText expressionEditText;

    private Button mThirdActivityButton;
    private ActivityResultLauncher mActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.inputResult);
        expressionEditText = findViewById(R.id.inputExpression);

        View.OnClickListener clickListener = this::buttonClickHandler;

        findViewById(R.id.button1)
                .setOnClickListener(clickListener);
        findViewById(R.id.button2)
                .setOnClickListener(clickListener);
        findViewById(R.id.button3)
                .setOnClickListener(clickListener);
        findViewById(R.id.button4)
                .setOnClickListener(clickListener);
        findViewById(R.id.button5)
                .setOnClickListener(clickListener);
        findViewById(R.id.button6)
                .setOnClickListener(clickListener);
        findViewById(R.id.button7)
                .setOnClickListener(clickListener);
        findViewById(R.id.button8)
                .setOnClickListener(clickListener);
        findViewById(R.id.button9)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonZero)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonClear)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonDivide)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonMultiply)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonPlus)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonMinus)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonDot)
                .setOnClickListener(clickListener);
        findViewById(R.id.buttonEquals)
                .setOnClickListener(clickListener);

        findViewById(R.id.buttonSecondActivity).setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("Text", "Привет от основной активности");

            intent.putExtras(bundle);

            startActivity(intent);
        });

        mThirdActivityButton = findViewById(R.id.buttonThirdActivity);

        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            mThirdActivityButton.setText(intent.getStringExtra("param"));

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Успешно вернулись",
                                    Toast.LENGTH_LONG
                            ).show();

                        }
                    }
                }
        );

        mThirdActivityButton.setOnClickListener(y -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            intent.putExtra("in_param", mThirdActivityButton.getText());
            mActivityResultLauncher.launch(intent);
        });
    }

    private String expression = "";
    private String result = "";

    private void clear() {
        expression = "";
        result = "";
    }

    private void addDigit(String symbol) {
        if (expression.isEmpty()) {
            expression = expression + symbol;
        } else {
            if (expression.equals("0")) {
                expression = symbol;
            } else {
                expression = expression + symbol;
            }
        }
    }

    private void addDot() {

        if (expression.equals("-")) {
            expression = "-0.";
        }

        if (expression.indexOf('.') != -1) {
            return;
        }

        if (expression.isEmpty()) {
            expression = "0.";
        } else {
            expression = expression + ".";
        }

    }

    private void addZero() {
        if (expression.length() == 1 && expression.equals("0")) {
            return;
        }
        expression = expression + "0";
    }

    private void setOperation(String operationSymbol) {

        if (!result.isEmpty() && !expression.isEmpty()) {
            if (!result.contains("*") && !result.contains("+") && !result.contains("-") && !result.contains("/")) {
                result = result + " " + operationSymbol;
                evaluate();
            } else {
                evaluate();
                result = result + " " + operationSymbol;
            }
            return;
        }

        if (expression.equals("-")) {
            return;
        }

        if (result.isEmpty()) {
            if (!expression.isEmpty()) {

                if (expression.endsWith(".")) {
                    expression = expression.substring(0, expression.length() - 1);
                }

                result = expression + " " + operationSymbol;
                expression = "";
            }
            {
                if (operationSymbol.equals("-")) {
                    expression = "-";
                }
            }
        } else {

            String lastSymbol = result.substring(result.length() - 1);

            if (lastSymbol.equals("+")
                    || lastSymbol.equals("-")
                    || lastSymbol.equals("*")
                    || lastSymbol.equals("/")
            ) {
                result = result.substring(0, result.length() - 1) + operationSymbol;
            } else {
                result = result + " " + operationSymbol;
            }
        }

    }

    private void updateDisplayedData() {
        expressionEditText
                .setText(expression);
        resultTextView
                .setText(result);
    }

    private void moveExpressionToResult() {
        if (expression.endsWith(".")) {
            expression = expression.substring(0, expression.length() - 1);

            if (expression.equals("-0")) {
                expression = "0";
            }
        }
        if (expression.equals("-")) {
            expression = "";
        }
        result = expression;
        expression = "";
    }

    private void evaluate() {

        if (result.isEmpty() && !expression.isEmpty()) {
            moveExpressionToResult();
        }
        if (!result.isEmpty() && !expression.isEmpty()
        ) {
            if (!result.contains("+")
                    && !result.contains("-")
                    && !result.contains("*")
                    && !result.contains("/")
            ) {
                moveExpressionToResult();
            }
        }
        if (expression.isEmpty()) return;

        if (result.endsWith("-") && expression.equals("-")) {
            expression = "0";
        }

        double expressionNumber = Double.parseDouble(expression);
        double resultNumber = Double.parseDouble(result.substring(0, result.length() - 2));

        String operation = result.substring(result.length() - 1);

        Log.d("Evaluation", "Operation: " + operation);

        clear();

        switch (operation) {
            case "/": {
                if (expressionNumber == 0.0) {
                    resultTextView.setText(R.string.InfinityMessage);
                } else {
                    String output = String.valueOf(resultNumber / expressionNumber);
                    result = output;
                    resultTextView.setText(output);
                }
                break;
            }
            case "*": {
                String output = String.valueOf(resultNumber * expressionNumber);
                result = output;
                resultTextView.setText(output);
                break;
            }
            case "+": {
                String output = String.valueOf(resultNumber + expressionNumber);
                result = output;
                resultTextView.setText(output);
                break;
            }
            case "-": {
                String output = String.valueOf(resultNumber - expressionNumber);
                result = output;
                resultTextView.setText(output);
                break;
            }
        }

    }


    public void buttonClickHandler(View view) {

        switch (view.getId()) {
            case R.id.button1: {
                addDigit("1");
                updateDisplayedData();
                break;
            }
            case R.id.button2: {
                addDigit("2");
                updateDisplayedData();
                break;
            }
            case R.id.button3: {
                addDigit("3");
                updateDisplayedData();
                break;
            }
            case R.id.button4: {
                addDigit("4");
                updateDisplayedData();
                break;
            }
            case R.id.button5: {
                addDigit("5");
                updateDisplayedData();
                break;
            }
            case R.id.button6: {
                addDigit("6");
                updateDisplayedData();
                break;
            }
            case R.id.button7: {
                addDigit("7");
                updateDisplayedData();
                break;
            }
            case R.id.button8: {
                addDigit("8");
                updateDisplayedData();
                break;
            }
            case R.id.button9: {
                addDigit("9");
                updateDisplayedData();
                break;
            }
            case R.id.buttonZero: {
                addZero();
                break;
            }
            case R.id.buttonDivide: {
                setOperation("/");
                updateDisplayedData();
                break;
            }
            case R.id.buttonMultiply: {
                setOperation("*");
                updateDisplayedData();
                break;
            }
            case R.id.buttonPlus: {
                setOperation("+");
                updateDisplayedData();
                break;
            }
            case R.id.buttonMinus: {
                setOperation("-");
                updateDisplayedData();
                break;
            }
            case R.id.buttonClear: {
                clear();
                updateDisplayedData();
                break;
            }
            case R.id.buttonDot: {
                addDot();
                break;
            }
            case R.id.buttonEquals: {
                evaluate();
                break;
            }

        }
        expressionEditText.setText(expression);
        resultTextView.setText(result);

    }


    private void createTestLog(String activityName, String message) {
        Log.d(
                Common.LOG_TAG,
                activityName + ": " + message
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        createTestLog("Main Activity", "start");
    }

    @Override
    protected void onStop() {
        super.onStop();

        createTestLog("Main Activity", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        createTestLog("Main Activity", "destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        createTestLog("Main Activity", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        createTestLog("Main Activity", "resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        createTestLog("Main Activity", "restart");
    }

}