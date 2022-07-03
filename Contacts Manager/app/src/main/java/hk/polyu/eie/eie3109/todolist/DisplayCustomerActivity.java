package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import hk.polyu.eie.eie3109.SalesDB;

public class DisplayCustomerActivity extends AppCompatActivity {
private SalesDB sdb;
private ListView LVCustomerList;
private Cursor c;
private  ArrayList<String> customers_name;
private Cursor number;
private ContentResolver cr;

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            //Code to query the phone numbers
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone._ID};

             cr = getContentResolver();

             c = cr.query(uri,projection, null, null
                    , null);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_customer);
        showContacts();

        sdb = new SalesDB(this,null,null,0);
        SQLiteDatabase db = sdb.getReadableDatabase();
        Cursor results = db.query(SalesDB.TABLE_CUSTOMER,
                new String[]{SalesDB.CUSTOMER_ID, SalesDB.CUSTOMER_NAME, SalesDB.CUSTOMER_GENDER},
                null,null,null,null,null);

        int resultCount = results.getCount();
        customers_name = new ArrayList<String>();
        if(resultCount>0 && results.moveToFirst()){
            for(int i=0;i<resultCount;i++){

                if((results.getString(2).trim()).equals("M") ) {
                    customers_name.add("Mr. "+results.getString(1));
                }
                else {
                    customers_name.add("Ms. "+results.getString(1));
                }
                results.moveToNext();
            }

            results.close();
            sdb.close();

        }
        LVCustomerList = findViewById(R.id.LVCustomerList);
        LVCustomerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, customers_name));
        LVCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean check = false;
                String text = "";
                int Contactcount = c.getCount();
                c.moveToFirst();
                for(int n =0;n<Contactcount;n++){
                    if(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).equals(customers_name.get(i).substring(4))) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                        number = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" = ?",
                                new String[]{name}, null);
                        number.moveToFirst();

                        for(int j=0;j<3;j++){
                            text += number.getString(number.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if(j<2)
                            text+="\n";
                            number.moveToNext();

                        }
                        check =true;
                        break;
                    }
                    c.moveToNext();
                }

                if(check ==true) {
                    Toast.makeText(getApplicationContext(), text,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "phone number not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button BNBack = (Button) findViewById(R.id.BNBack);
        if (BNBack != null) {

            BNBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}