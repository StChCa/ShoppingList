package com.example.cardo.shoppinglist.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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

public class ListActivityBase extends AppCompatActivity{

    GlobalObjects.LIST_TYPES LIST_ID;
    ItemCollection masterList = GlobalObjects.MASTER_LIST;
    Context context;
    boolean confirmation = false;

    // list access variables
    private List<String> typedList;
    private ArrayAdapter arrayAdapter;
    private String inName,inDesc,inCat;

    // Resource Identifiers
    int resActivity;
    int resList;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resActivity);

        context = getApplicationContext();

        // list access var initialization
        ItemCollection typedItems = GlobalObjects.getItemsOfType(LIST_ID);
        typedList = typedItems.convertToStringList(typedItems);

        // Identify the List Resource object that displays the list
        lv = findViewById(resList);

        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, typedList);

        lv.setAdapter(arrayAdapter);


        //populateDisplayList(typedList, arrayAdapter);
        updateList(arrayAdapter);

        // On ListItemCLick
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                lv.getSelectedItem();
                // I don't know what the value of "String" is here. WTF? but it works so I roll with it.
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
    // *************************************************************************************
    // Function Name: updateList
    // purpose: Update the list on the screen to reflect changes.
    // *************************************************************************************
    private void updateList(ArrayAdapter arrayAdapter){
        arrayAdapter.notifyDataSetChanged();
    }

    // *************************************************************************************
    // Function Name: addItemAlert
    // purpose: Prompt user with a dialog box to add an item to the MasterList.
    // Adds the item to the list with the GlobalObjects.LIST_TYPE item for this class.
    // *************************************************************************************
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
                    Item itm = new Item(LIST_ID, inName, inDesc, inCat);
                    masterList.addItem(itm);

                    //File file = new File (masterList.getFilePath() + "/masterList.txt");

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

    // *************************************************************************************
    // Function Name: openCalculator
    // purpose: Switch to a CalculatorView that allows user to calculate values.
    // *************************************************************************************
    public void openCalculator(View view) {
        Intent CalculatorActivity = new Intent(ListActivityBase.this, CalculatorView.class);

        startActivity(CalculatorActivity);
    }

}
