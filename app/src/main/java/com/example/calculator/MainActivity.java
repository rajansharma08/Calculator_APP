package com.example.calculator_task01;
import android.os.Bundle;
import android.view.View;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackopen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAc, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        assignID(buttonC, R.id.button_c);
        assignID(buttonAc, R.id.button_ac);
        assignID(button0, R.id.button_zero);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonDot, R.id.button_dot);
        assignID(buttonDivide, R.id.button_divide);
        assignID(buttonMultiply, R.id.button_multiply);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonMinus, R.id.button_minus);
        assignID(buttonBrackClose, R.id.button_close_bracket);
        assignID(buttonBrackopen, R.id.button_open_bracket);
        assignID(buttonEquals, R.id.button_equals);


    }

    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }

        if (buttonText.equals("C")) {
            // Add condition to check if the string is not empty
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);}
            else{
                dataToCalculate="0";
            }

        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }

    }
    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object result= context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (result == Context.getUndefinedValue()) {
                return "";
            }
            String finalResult = result.toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
        finally {
            Context.exit();
        }
    }
}