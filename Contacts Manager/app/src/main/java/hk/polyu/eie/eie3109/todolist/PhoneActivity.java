package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {
    private ListView myPhoneList;
    private SimpleCursorAdapter myCursorAdapter;

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            //Code to query the phone numbers
            final ContentResolver cr = getContentResolver();

            Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME}, null, null
                    , null);

            myCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item, c,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                    new int[]{R.id.TVRow}, 0);
            myPhoneList.setAdapter(myCursorAdapter);

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
        setContentView(R.layout.activity_phone);

        myPhoneList = findViewById(R.id.LVPhoneList);
        showContacts();

        myPhoneList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text= "";
                int arrPos;
                ArrayList<String> phoneNumber = new ArrayList<String>();
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, null, null, null);

                if(position == 0) {
                    arrPos = 0;
                    phones.moveToPosition(arrPos);
                }
                else if (position == 1) {
                    arrPos = 3;
                    phones.moveToPosition(arrPos);
                }
                else if (position == 2) {
                    arrPos = 6;
                    phones.moveToPosition(arrPos);
                }

                for (int i = 0; i < 3;  i++)
                {
                    phoneNumber.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    text += phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(i<2)
                        text+="\n";
                    phones.moveToNext();
                }

                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();

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