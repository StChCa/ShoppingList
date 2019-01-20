package com.example.cardo.shoppinglist;

public class Item {
    String name = "";
    String description = "";
    String category = "";

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

    public Item(String newName, String desc, String cat){
        name = newName;
        description = desc;
        category = cat;
    }

    public Item(){

    }

    public String toString(){
        String retString = name +", "+ description +", " + category;
        return retString;
    }
}
