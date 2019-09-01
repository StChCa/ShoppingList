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

    public String toString(){
        String retString = name +", "+ description +", " + category + ", List ID = " + listID;
        return retString;
    }
}
