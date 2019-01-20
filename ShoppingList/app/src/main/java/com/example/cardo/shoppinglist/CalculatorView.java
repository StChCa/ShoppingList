package com.example.cardo.shoppinglist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.R.color;

import java.util.Dictionary;
import java.util.Hashtable;

public class CalculatorView extends AppCompatActivity {

    double costAStr = 00.00;
    double costBStr = 00.00;
    double sizeAStr = 00.00;
    double sizeBStr = 00.00;

    TextView costA;
    TextView costB;
    TextView sizeA;
    TextView sizeB;
    ImageView aStar;
    ImageView bStar;

    // Hash table to keep track of the cost and size values.
    Hashtable<Integer,Double> numLocation = new Hashtable<Integer, Double>();

    int selected = 1;

    public void initialize() {
        numLocation.put(1, costAStr);
        numLocation.put(2, costBStr);
        numLocation.put(3, sizeAStr);
        numLocation.put(4, sizeBStr);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        initialize();

        costA = findViewById(R.id.itemACost);
        costB = findViewById(R.id.itemBCost);
        sizeA = findViewById(R.id.itemASize);
        sizeB = findViewById(R.id.itemBSize);

        // The next lines are nessicayr to prevent keyboard when click on text.
        costA.setShowSoftInputOnFocus(false);
        costB.setShowSoftInputOnFocus(false);
        sizeA.setShowSoftInputOnFocus(false);
        sizeB.setShowSoftInputOnFocus(false);

        aStar = findViewById(R.id.aStar);
        bStar = findViewById(R.id.bStar);
        aStar.setVisibility(View.INVISIBLE);
        bStar.setVisibility(View.INVISIBLE);


    }

    protected double moveDecimal(double value){
        return value * 10;
    }

    protected void updateValues(){
        costA.setText(String.format("%.2f" ,numLocation.get(1)).toString());
        costB.setText(String.format("%.2f" ,numLocation.get(2)).toString());
        sizeA.setText(String.format("%.2f" ,numLocation.get(3)).toString());
        sizeB.setText(String.format("%.2f" ,numLocation.get(4)).toString());
    }

    public void selectCostA(View view) {
        selected = 1;
    }
    public void selectCostB(View view) {
        selected = 2;
    }
    public void selectSizeA(View view) {
        selected = 3;
    }
    public void selectSizeB(View view) {
        selected = 4;
    }

    public void clearAllFields(View view) {
        numLocation.replace(1, numLocation.get(1), 00.00);
        numLocation.replace(2, numLocation.get(2), 00.00);
        numLocation.replace(3, numLocation.get(3), 00.00);
        numLocation.replace(4, numLocation.get(4), 00.00);

        updateValues();
    }

    public void calcBetterValue(View view) {
        aStar.setVisibility(View.INVISIBLE);
        bStar.setVisibility(View.INVISIBLE);

        double aValue = numLocation.get(1)/numLocation.get(3);
        double bValue = numLocation.get(2)/numLocation.get(4);

        if (bValue >= aValue) {
            aStar.setVisibility(View.VISIBLE);
        } else {
            bStar.setVisibility(View.VISIBLE);
        }
    }

    public void num0(View view) {
        double prev = numLocation.get(selected);
        double changed = prev;
        changed = moveDecimal(changed);
        numLocation.replace(selected, prev, changed);

        updateValues();
    }

    public void num1(View view) {
        adjust(.01);
    }

    public void num2(View view) {
        adjust(.02);
    }

    public void num3(View view) {
        adjust(.03);
    }

    public void num4(View view) {
        adjust(.04);
    }

    public void num5(View view) {
        adjust(.05);
    }

    public void num6(View view) {
        adjust(.06);
    }

    public void num7(View view) {
        adjust(.07);
    }

    public void num8(View view) {
        adjust(.08);
    }

    public void num9(View view) {
        adjust(.09);
    }

    public void adjust(double keyValue){
        double prev = numLocation.get(selected);
        double changed = prev;
        changed = moveDecimal(changed);
        changed += keyValue;
        numLocation.replace(selected, prev, changed);

        updateValues();
    }

    public void backSpace(View view){
        double prev = numLocation.get(selected);
        double changed = prev;
        if (00.00 != changed){
            changed /= 10;
        }

        changed = truncDecimals(changed);
        numLocation.replace(selected, prev, changed);
        updateValues();
    }

    /*
     this function truncates back to 2 values. it is nessicary. dont delete it.
     */
    public double truncDecimals(double nonTruncated){
        double truncated;
        double a;
        int i;
        a = nonTruncated * 100;
        i = (int)a;

        truncated = i;
        truncated = (double)truncated / 100;
        return truncated;
    }

}
