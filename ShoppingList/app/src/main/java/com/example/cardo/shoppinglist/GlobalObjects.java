package com.example.cardo.shoppinglist;

import android.widget.Toast;

public class GlobalObjects {

    public static enum LIST_TYPES {
        ALL,
        SHOPPING,
        GROCERY
    }

    public static ItemCollection MASTER_LIST = new ItemCollection();

    public static void addListItem(String inName, String inDesc, String inCat) {
        // adds item to my master list.

        Item addItem = new Item(LIST_TYPES.ALL, inName,inDesc,inCat);
        MASTER_LIST.addItem(addItem);

        //Toast.makeText(context, inName+": " +inDesc +" Was added to: " + inCat, Toast.LENGTH_LONG).show();
    }

    // *********************************************************************************
    // Function Name: getItemsOfType
    // purpose: Take the MASTER_LIST and return a new list containing only items of type
    // GlobalObject.LIST_TYPES given by user
    // *********************************************************************************
    public static ItemCollection getItemsOfType(GlobalObjects.LIST_TYPES type){
        ItemCollection retCollection = new ItemCollection();

        for (int idx = 0; idx < MASTER_LIST.getLength(); idx++) {

            if ( MASTER_LIST.valueAt(idx).getListID() == type){
                retCollection.addItem(MASTER_LIST.valueAt(idx));
            }
        }

        return retCollection;
    }
}
