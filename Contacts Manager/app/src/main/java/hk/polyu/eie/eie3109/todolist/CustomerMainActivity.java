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
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import hk.polyu.eie.eie3109.SalesDB;

public class CustomerMainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);


        Button BNAdd = (Button) findViewById(R.id.BNAdd);
        if (BNAdd  != null) {
            BNAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CustomerMainActivity.this, AddCustomerActivity.class));
                }
            });
        }

        Button BNDisplay = (Button) findViewById(R.id.BNDisplay);
        if (BNDisplay  != null) {
            BNDisplay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CustomerMainActivity.this, DisplayCustomerActivity.class));
                }
            });
        }





    }
}