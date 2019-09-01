package com.example.cardo.shoppinglist.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cardo.shoppinglist.GlobalObjects;
import com.example.cardo.shoppinglist.ItemCollection;
import com.example.cardo.shoppinglist.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Context context;
    public ItemCollection masterList = GlobalObjects.MASTER_LIST;
    private String inName,inDesc,inCat;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // context assigned here because it must be done after onCreate
        context = this.getApplicationContext();
        path = context.getFilesDir().getPath();


        masterList.createFileLoc(path);
//        File dir = new File(path);
//        dir.mkdir();
//
//        File file = new File(path + "/masterList.txt");

        masterList.load();
    }

    public void openAllList(View view) {
        // open the all list activity
        Intent DisplayAllActivity = new Intent(MainActivity.this, com.example.cardo.shoppinglist.Activities.DisplayAllActivity.class);

        startActivity(DisplayAllActivity);
    }

    public void openGroceryList(View view){
        Intent GrocActivity = new Intent(MainActivity.this, com.example.cardo.shoppinglist.Activities.DisplayGroceryActivity.class);

        startActivity(GrocActivity);
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
                    GlobalObjects.addListItem(inName, inDesc, inCat);

                    File file = new File (path + "/masterList.txt");

                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

                    //save(file);
                    masterList.save();

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
}
