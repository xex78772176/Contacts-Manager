package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "MyProfile";
    public static final String PREFERENCE_PACKAGE = "hk.polyu.eie.eie3109.helloworld";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TVName = findViewById(R.id.TVName);

        Context c =null;
        try {
            c = this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPreferences = c.getSharedPreferences(PREFERENCE_NAME, MODE);
            String name = sharedPreferences.getString("Name", "Default Name");
            if (TVName != null) {
                TVName.setText(name);
            }
            Button BNList = (Button) findViewById(R.id.BNList);
            if (BNList != null) {
                BNList.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                });
            }
            Button BNPhone = (Button) findViewById(R.id.BNPhone);
            if (BNPhone != null) {
                BNPhone.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, PhoneActivity.class));
                    }
                });
            }
            Button BNCustomer = (Button) findViewById(R.id.BNCustomer);
            if (BNCustomer != null) {
                BNCustomer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CustomerMainActivity.class));
                    }
                });
            }




        }catch(PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
    }
}