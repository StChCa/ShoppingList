package com.example.cardo.shoppinglist.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cardo.shoppinglist.CalculatorView;
import com.example.cardo.shoppinglist.GlobalObjects;
import com.example.cardo.shoppinglist.Item;
import com.example.cardo.shoppinglist.ItemCollection;
import com.example.cardo.shoppinglist.R;

import java.util.List;

// **************************************************************************
// Generate A list of "All" type. Control user input for this Type.
// **************************************************************************
public class DisplayGroceryActivity extends ListActivityTemplate{

    // What type of list this is.
    final GlobalObjects.LIST_TYPES LIST_ID = GlobalObjects.LIST_TYPES.GROCERY;
    Context context;

    // Resource Identifiers
    int resActivity = R.layout.activity_display_grocery;
    int resList = R.id.GroceryListList;

    public DisplayGroceryActivity(){
        super();
        super.resActivity = resActivity;
        super.resList = resList;
    }

}
