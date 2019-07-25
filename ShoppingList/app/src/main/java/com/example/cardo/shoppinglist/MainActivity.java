package com.example.cardo.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

//import com.garmin.android.apps.connectiq.sample.comm.adapter.IQDeviceAdapter;
import com.garmin.android.connectiq.ConnectIQ;
import com.garmin.android.connectiq.ConnectIQ.ConnectIQListener;
import com.garmin.android.connectiq.ConnectIQ.IQConnectType;
import com.garmin.android.connectiq.ConnectIQ.IQDeviceEventListener;
import com.garmin.android.connectiq.ConnectIQ.IQSdkErrorStatus;
import com.garmin.android.connectiq.IQDevice;
import com.garmin.android.connectiq.IQDevice.IQDeviceStatus;
import com.garmin.android.connectiq.exception.InvalidStateException;
import com.garmin.android.connectiq.exception.ServiceUnavailableException;

public class MainActivity extends AppCompatActivity {

    private Context context;
    public static ItemCollection masterList = new ItemCollection();
    private String inName,inDesc,inCat;
    public String path;

    private ConnectIQ mConnectIQ;
    private TextView mEmptyView;
    private IQDeviceAdapter mAdapter;
    private boolean mSdkReady = false;

    private IQDeviceEventListener mDeviceEventListener = new IQDeviceEventListener() {

        @Override
        public void onDeviceStatusChanged(IQDevice device, IQDeviceStatus status) {
            mAdapter.updateDeviceStatus(device, status);
        }

    };
    private ConnectIQListener mListener = new ConnectIQListener() {

        @Override
        public void onInitializeError(IQSdkErrorStatus errStatus) {
            if( null != mEmptyView )
                mEmptyView.setText("ConnectIQListener Error!");
            mSdkReady = false;
        }

        @Override
        public void onSdkReady() {
            loadDevices();
            mSdkReady = true;
        }

        @Override
        public void onSdkShutDown() {
            mSdkReady = false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // context assigned here because it must be done after onCreate
        context = this.getApplicationContext();
        path = context.getFilesDir().getPath();
        setUpCIQ();


        masterList.createFileLoc(path);
//        File dir = new File(path);
//        dir.mkdir();
//
//        File file = new File(path + "/masterList.txt");

        masterList.load();
    }

    private void setUpCIQ() {

        mAdapter = new IQDeviceAdapter(this);
        mConnectIQ = ConnectIQ.getInstance(this, IQConnectType.WIRELESS);
        
        // Initialize the SDK
        mConnectIQ.initialize(this, true, mListener);
    }

    public void openAllList(View view) {
        // open the all list activity
        Intent DisplayAllActivity = new Intent(MainActivity.this, DisplayAllActivity.class);

        startActivity(DisplayAllActivity);
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
                    addListItem(inName, inDesc, inCat);

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

    public void addListItem(String inName, String inDesc, String inCat) {
        // adds item to my master list.

        Item addItem = new Item(inName,inDesc,inCat);
        masterList.addItem(addItem);

        Toast.makeText(context, inName+": " +inDesc +" Was added to: " + inCat, Toast.LENGTH_LONG).show();
    }

    public static ItemCollection getMasterList() {

        return masterList;
    }

    public void loadDevices() {
        // Retrieve the list of known devices
        try {
            List<IQDevice> devices = mConnectIQ.getKnownDevices();

            if (devices != null) {
                System.out.println(devices);
                mAdapter.setDevices(devices);

                // Let's register for device status updates.  By doing so we will
                // automatically get a status update for each device so we do not
                // need to call getStatus()
                for (IQDevice device : devices) {
                    mConnectIQ.registerForDeviceEvents(device, mDeviceEventListener);
                }
            }

        } catch (InvalidStateException e) {
            // This generally means you forgot to call initialize(), but since
            // we are in the callback for initialize(), this should never happen
        } catch (ServiceUnavailableException e) {
            // This will happen if for some reason your app was not able to connect
            // to the ConnectIQ service running within Garmin Connect Mobile.  This
            // could be because Garmin Connect Mobile is not installed or needs to
            // be upgraded.
            if( null != mEmptyView )
                mEmptyView.setText("Service UNNAvailable!");
        }
    }

    public static void TESTsave(File file) {
//
//        FileOutputStream fos = null;
//        try
//        {
//            fos = new FileOutputStream(file);
//        }
//        catch (FileNotFoundException e) {e.printStackTrace();}
//        try
//        {
//            try
//            {
//                // clear the file of whtever was there
//
//                fos.write("".getBytes());
//
//                // first line will be how many items in list
//                fos.write(Integer.toString(masterList.getLength()).getBytes());
//                fos.write("\n".getBytes());
//
//                // Write each item in my list to a line in a csv file
//                for (int i = 0; i < masterList.getLength(); i++){
//
//                    Item target = masterList.valueAt(i);
//
//                    fos.write(target.getName().getBytes());
//                    fos.write(",".getBytes());
//                    fos.write(target.getDescription().getBytes());
//                    fos.write(",".getBytes());
//                    fos.write(target.getCategory().getBytes());
//
//                    if (i < masterList.getLength()){
//                        fos.write("\n".getBytes());
//                    }
//
//                }
//
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
//        finally
//        {
//            try
//            {
//                fos.close();
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
    }

    public static void TESTload(File file) {
//
//        masterList.reset();
//
//        FileInputStream fis = null;
//        try
//        {
//            fis = new FileInputStream(file);
//        }
//        catch (FileNotFoundException e) {e.printStackTrace();}
//        InputStreamReader isr = new InputStreamReader(fis);
//        BufferedReader br = new BufferedReader(isr);
//
//        try
//        {
//            fis.getChannel().position(0);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try
//        {
//            // read first line to know how many times we need to iterate.
//            int numOfItems;
//            numOfItems = Integer.parseInt(br.readLine());
//
//            // read each line and add it to masterList
//            for ( int i = 0; i < numOfItems; i++){
//
//                String line = br.readLine();
//
//                String[] attributes = line.split(",");
//
//                Item inItem = new Item();
//                inItem.setName(attributes[0]);
////                inItem.setDescription(attributes[1]);
////                inItem.setCategory(attributes[2]);
//
//                masterList.addItem(inItem);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
