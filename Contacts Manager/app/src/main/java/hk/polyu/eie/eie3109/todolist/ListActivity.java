package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView myToDoList;
    private String[] myStringList;
    private ArrayList<String> myList;
    private ArrayList<String> environmentList;
    private ArrayAdapter<String> myAdapter;
    private int item_loc;
    private SharedPreferences ListsharedPreferences;
    private int arrnum = 0;

    public static final int MODE = Context.MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "MyProfile";
    public static final String PREFERENCE_PACKAGE = "hk.polyu.eie.eie3109.helloworld";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListsharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        TextView TVMessage = findViewById(R.id.TVMessage);

        Context c =null;
        try {
            c = this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPreferences = c.getSharedPreferences(PREFERENCE_NAME, MODE);
            String name = sharedPreferences.getString("Name", "Default Name");
            if (TVMessage != null) {
                TVMessage.setText("Hi, "+name+"!");
            }

            }
        catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        myList = new ArrayList<String>();
        for (int i=0; i<10;i++){
            myList.add("Empty " + i);
            arrnum++;
        }
        myList.set(0, "Return Books to Library");
        myList.set(1, "Meeting with Advisor");

        SharedPreferences.Editor editor = ListsharedPreferences.edit();
        for (int n =0;n <myList.size(); n++) {
            editor.putString("item_"+n, myList.get(n));
        }
        editor.apply();

        environmentList = new ArrayList<String>();
        SharedPreferences prefs =  ListsharedPreferences;
        for (int n = 0; n < arrnum; n++  )
        {
            String environItem = prefs.getString("item_"+n, null);
            environmentList.add(environItem);
        }




        myToDoList = findViewById(R.id.LVList);
        myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                environmentList);
        myToDoList.setAdapter(myAdapter);

        Button BNBack = (Button) findViewById(R.id.BNBack);
        if (BNBack != null) {
            BNBack.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }

        myToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                item_loc = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                ListView options = new ListView(ListActivity.this);
                options.setAdapter(new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_list_item_1,
                        new String[]{"Add a new item", "Edit this item", "Remove this item"}));
                builder.setView(options);

                final Dialog dialog = builder.create();
                dialog.show();

                options.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    dialog.dismiss();
                        if(i==0) {
                            final Dialog dialogForm = new Dialog(ListActivity.this);
                            dialogForm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogForm.setContentView(R.layout.form_operation);

                            TextView TVTitle = dialogForm.findViewById(R.id.TVTitle);
                            final EditText ETText = dialogForm.findViewById(R.id.ETText);
                            Button BNSubmit = dialogForm.findViewById(R.id.BNSubmit);
                            if(TVTitle!=null){
                                TVTitle.setText("Add item");
                            }
                            if(ETText!=null){
                                ETText.setText(environmentList.get(item_loc));
                            }
                            dialogForm.show();

                            if (BNSubmit != null) {
                                BNSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        environmentList.add(item_loc,ETText.getText().toString());
                                        myAdapter.notifyDataSetChanged();
                                        dialogForm.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), ("item " + Integer.toString(item_loc)
                                                + " Add"), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }


                        }
                        if(i==1) {
                            final Dialog dialogForm = new Dialog(ListActivity.this);
                            dialogForm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogForm.setContentView(R.layout.form_operation);

                            TextView TVTitle = dialogForm.findViewById(R.id.TVTitle);
                            final EditText ETText = dialogForm.findViewById(R.id.ETText);
                            Button BNSubmit = dialogForm.findViewById(R.id.BNSubmit);
                            if(TVTitle!=null){
                                TVTitle.setText("Edit this item");
                            }
                            if(ETText!=null){
                                ETText.setText(environmentList.get(item_loc));
                            }
                            dialogForm.show();

                            if (BNSubmit != null) {
                                BNSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        environmentList.set(item_loc,ETText.getText().toString());
                                        myAdapter.notifyDataSetChanged();
                                        dialogForm.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), ("item " + Integer.toString(item_loc)
                                                + " Edit"), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                        if(i==2) {
                            environmentList.remove(item_loc);
                            myAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), ("item " + Integer.toString(item_loc)
                                    + " Remove"), Toast.LENGTH_SHORT).show();
                        }
                    }

            });

                SharedPreferences.Editor editor = ListsharedPreferences.edit();
                editor.clear();
                for(int n =0;n <environmentList.size(); n++) {
                    editor.putString("item_"+n, environmentList.get(n));
                }
                editor.apply();
            }
        });


    }
}