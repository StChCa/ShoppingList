package com.example.cardo.shoppinglist;

import android.app.Activity;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayAllActivity extends AppCompatActivity {

    ItemCollection masterList;
    ListView displayList;
    Context context;
    boolean confirmation = false;

    // list access variables
    private List<String> listList;
    private ArrayAdapter arrayAdapter;
    private String inName,inDesc,inCat;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);

        context = getApplicationContext();

        displayList = findViewById(R.id.AllListList);
        masterList = MainActivity.getMasterList();

        // list access var initialization
        listList = masterList.convertToStringList(masterList);


        lv = findViewById(R.id.AllListList);

        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listList);

        lv.setAdapter(arrayAdapter);


        //populateDisplayList(listList, arrayAdapter);
        updateList(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Item selItem = (Item) lv.getSelectedItem(); //
                //String value = selItem.getTheValue(); //getter method

                lv.getSelectedItem();

                Item itemToBeRemoved = masterList.valueAt(masterList.indexOfThisString((String) lv.getItemAtPosition(position)));
                if (confirmRemoval(itemToBeRemoved)) {
                    masterList.removeItem(itemToBeRemoved);
                    masterList.save();
                }
                confirmation = false;
                confirmRemoval(itemToBeRemoved);
                recreate();

                updateList(arrayAdapter);
                Toast.makeText(context, "Removed Item: " + itemToBeRemoved.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private boolean confirmRemoval(Item itemToBeRemoved) {
        return true;
    }

    private void updateList(ArrayAdapter arrayAdapter){
        arrayAdapter.notifyDataSetChanged();

    }

    public void addItemAlert(View view) {
        // Opens an alert to take input for item to be added

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
// Set up the input
        final EditText name = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        name.setHint("Name");
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        layout.addView(name);

        final EditText desc = new EditText(this);
        desc.setHint("Description");
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        layout.addView(desc);

        final EditText cat = new EditText(this);
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        cat.setHint("Category");
        layout.addView(cat);

        builder.setView(layout);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inName = name.getText().toString();
                inDesc = desc.getText().toString();
                inCat = cat.getText().toString();

                if (inName != null && !inName.isEmpty()){
                    Item itm = new Item(inName, inDesc, inCat);
                    masterList.addItem(itm);

                    File file = new File (masterList.getFilePath() + "/masterList.txt");

                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

                    //save(file);
                    masterList.save();

                    recreate();
                } else{
                    Toast.makeText(context, "Name is required. Nothing was added.", Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void openCalculator(View view) {
        Intent CalculatorActivity = new Intent(DisplayAllActivity.this, CalculatorView.class);

        startActivity(CalculatorActivity);
    }
}
