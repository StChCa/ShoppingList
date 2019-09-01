package com.example.cardo.shoppinglist.Activities;

import com.example.cardo.shoppinglist.GlobalObjects;
import com.example.cardo.shoppinglist.R;

// **************************************************************************
// Generate A list of "All" type. Control user input for this Type.
// **************************************************************************
public class DisplayWishActivity extends ListActivityBase{

    // What type of list this is.


    public DisplayWishActivity(){
        super();
        LIST_ID = GlobalObjects.LIST_TYPES.WISH;
        //context = getApplicationContext();

        // Resource Identifiers
        resActivity = R.layout.activity_display_wishlist;
        resList = R.id.WishListList;
    }

}
