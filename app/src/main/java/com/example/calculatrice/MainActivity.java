package com.example.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private float op1=0;
    private float op2=0;
    private String op="";
    private Ops operator=null;
    private boolean isOp1=true;
    private boolean isDec=false;
    private int n=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.textView);

        Button btnEgal = (Button)findViewById(R.id.be);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                compute();
            }
        });
    }
    public void compute() {
        if(!isOp1){
            switch(operator) {
                case PLUS : op1 = op1 + op2; break;
                case MOINS : op1 = op1 - op2; break;
                case FOIS : op1 = op1 * op2; break;
                case DIV : {if(op2==0){screen.setText("impossible de diviser sur 0");}
                            else {op1 = op1 / op2;}
                            } break;
                default : return; // do nothing if no operator
            }
            op2 = 0;
            isOp1 = true;
            screen.setText(screen.getText()+"="+ String.format("%9d",op1));
        }
    }
    public void setOperator(View v) {
        switch (v.getId()) {
            case R.id.bplus  : {operator=Ops.PLUS; op="+"; } break;
            case R.id.bmoins : {operator=Ops.MOINS;op="-"; } break;
            case R.id.bfois  : {operator=Ops.FOIS; op="*"; } break;
            case R.id.bdiv   : {operator=Ops.DIV; op="/"; }  break;
            default :
                Toast.makeText(this, "Opérateur non reconnu",Toast.LENGTH_LONG);
                return; // do nothing if no operator
        }
        isOp1=false;
        isDec=false;
        n=1;
        screen.setText(screen.getText()+op);
    }

    public void addNumber(View v){
        try {
            int val = Integer.parseInt(((Button)v).getText().toString());
            if (isOp1) {
                if (isDec) {
                    n=n*10;
                    op1 = op1 +(float) val/n;
                }
                else{
                op1 = op1 * 10 + val;
                }
                updateDisplay();
            }
            else {
                if (isDec) {
                    n=n*10;
                    op2 = op2 +(float) val/n;
                }
                else{
                    op2 = op2 * 10 + val;
                }
                updateDisplay();
            }
        }catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée",Toast.LENGTH_LONG);
        }
    }
    private void updateDisplay() {
        if (isOp1) {
            while ((float)op1/10>0) {
                screen.setText(removeLastCharacter((String) screen.getText()));
            }
            screen.setText(screen.getText()+String.format("%9d",op1));
        } else {
            while (op2/10>0) {
                screen.setText(removeLastCharacter((String) screen.getText()));
            }
            screen.setText(screen.getText()+String.format("%9d",op1));
        }
    }
    public static String removeLastCharacter(String str) {
            str = str.substring(0, str.length()-1);
        return str;
    }
    public void reset(View v) {
        screen.setText("");
        op1=0;
        op2=0;
        operator=null;
        isOp1=true;
    }
    public void decimate(View v) {
        isDec=true;
    }
}