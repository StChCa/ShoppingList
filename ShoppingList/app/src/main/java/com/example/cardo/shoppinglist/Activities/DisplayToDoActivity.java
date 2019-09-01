package com.example.cardo.shoppinglist.Activities;

import com.example.cardo.shoppinglist.GlobalObjects;
import com.example.cardo.shoppinglist.R;

// **************************************************************************
// Generate A list of "All" type. Control user input for this Type.
// **************************************************************************
public class DisplayToDoActivity extends ListActivityBase{

    // What type of list this is.


    public DisplayToDoActivity(){
        super();
        LIST_ID = GlobalObjects.LIST_TYPES.TODO;
        //context = getApplicationContext();

        // Resource Identifiers
        resActivity = R.layout.activity_display_todo;
        resList = R.id.ToDoListList;
    }

}
