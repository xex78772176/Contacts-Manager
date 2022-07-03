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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import hk.polyu.eie.eie3109.SalesDB;

public class AddCustomerActivity extends AppCompatActivity {
    private SalesDB sdb;
    private EditText ETName;
    private Button BNAdd;
    private String gender;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        ETName = findViewById(R.id.ETName);
        BNAdd = (Button) findViewById(R.id.BNAdd);
        radioGroup = findViewById(R.id.RadioGroup);

        if (BNAdd != null) {
            BNAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(radioGroup.getCheckedRadioButtonId()==-1){
                        Toast.makeText(getApplicationContext(),"you have not select gender",
                                Toast.LENGTH_LONG).show();
                    }else{
                        RadioButton selectedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                        gender = selectedButton.getText().toString();

                        sdb = new SalesDB(getApplicationContext(), null, null, 0);
                        SQLiteDatabase db = sdb.getWritableDatabase();

                        ContentValues newValues = new ContentValues();
                        newValues.put(sdb.CUSTOMER_NAME, ETName.getText().toString());
                        newValues.put(sdb.CUSTOMER_GENDER,gender);
                        db.insertOrThrow(sdb.TABLE_CUSTOMER, null, newValues);

                        db.close();
                        BNAdd.setEnabled(false);
                    }

                }
            });
        }
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