package com.example.cardo.shoppinglist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemCollection {
    ArrayList<Item> list = new ArrayList<Item>();
    int numItems = 0;
    File localFile = null;

    public void addItem(Item item){
        list.add(item);
        numItems++;
    }

    public void removeItem(Item item){
        list.remove(item);
        numItems--;
    }

    public int getLength(){
        return numItems;
    }

    public Item valueAt(int loc){
        return list.get(loc);
    }

    public List<String> convertToStringList(ItemCollection inList) {

        List<String> outList = new ArrayList<String>();

        for (int i=0; i < inList.getLength(); i++ ){
            outList.add(inList.valueAt(i).toString());
        }

        return outList;
    }

    public void reset() {
        list.clear();
        numItems = 0;
    }

    public int indexOfThisString(String nameDescCat){

        for(int i = 0; i < numItems; i++){
            if (list.get(i).toString().equals(nameDescCat)){
                return i;
            }
        }

        return -1;
    }

    public void createFileLoc(String path){
        File dir = new File(path);
        dir.mkdir();

        File file = new File(path + "/masterList.txt");

        localFile = file;
    }

    public String getFilePath(){
        String retStr = localFile.getPath();

        return retStr;
    }

    public void save(){

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(localFile);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                // clear the file of whtever was there

                fos.write("".getBytes());

                // first line will be how many items in list
                fos.write(Integer.toString(this.getLength()).getBytes());
                fos.write("\n".getBytes());

                // Write each item in my list to a line in a csv file
                for (int i = 0; i < this.getLength(); i++){

                    Item target = this.valueAt(i);

                    fos.write(target.getName().getBytes());
                    fos.write(",".getBytes());
                    fos.write(target.getDescription().getBytes());
                    fos.write(",".getBytes());
                    fos.write(target.getCategory().getBytes());

                    if (i < this.getLength()){
                        fos.write("\n".getBytes());
                    }

                }

            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public void load(){
        this.reset();

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(localFile);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try
        {
            // read first line to know how many times we need to iterate.
            int numOfItems;
            numOfItems = Integer.parseInt(br.readLine());

            // read each line and add it to masterList
            for ( int i = 0; i < numOfItems; i++){

                String line = br.readLine();

                String[] attributes = line.split(",");

                Item inItem = new Item();
                inItem.setName(attributes[0]);
//                inItem.setDescription(attributes[1]);
//                inItem.setCategory(attributes[2]);

                this.addItem(inItem);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
