package com.example.cardo.shoppinglist;

import android.provider.Settings;

public class Item {
    String name = "";
    String description = "";
    String category = "";
    GlobalObjects.LIST_TYPES listID = GlobalObjects.LIST_TYPES.ALL;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getCategory(){
        return this.category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public GlobalObjects.LIST_TYPES getListID() { return this.listID; }
    public void setListID(GlobalObjects.LIST_TYPES id) { this.listID = id; }

    public Item(GlobalObjects.LIST_TYPES id, String newName, String desc, String cat){
        listID = id;
        name = newName;
        description = desc;
        category = cat;
    }

    public Item(){

    }

    // *********************************************************************************
    // Name: toSaveString
    // Returns: String ( format: name,description,category,listID )
    // Generate the item to be stored in the CSV for this item.
    // *********************************************************************************
    public String toSaveString(){
        String retString = name+","+description+","+category+","+listID;
        return retString;
    }

    // *********************************************************************************
    // Name: toString
    // Returns: String ( format: Format to be displayed to user )
    // This is for aesthetics. not for manipulating data.
    // *********************************************************************************
    public String toString(){

        // Output string applying commas when necessary
        String retString = "";
        if( !this.getName().isEmpty()){
            retString += this.getName();
        } else {
            retString += "";
        }

        if( !this.getDescription().isEmpty()){
            System.out.println("Rainbow Bridge: " + this.getDescription());
            retString += ", " + this.getDescription();
        } else{
            retString += "";
        }

        if( !this.getCategory().isEmpty() ){
            retString += ", " + this.getCategory();
        } else{
            retString += "";
        }

        return retString;
    }
}
