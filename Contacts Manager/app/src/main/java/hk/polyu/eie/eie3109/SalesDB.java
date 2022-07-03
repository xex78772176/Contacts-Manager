package hk.polyu.eie.eie3109;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SalesDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SalesDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_CUSTOMER = "customer";
    public static final String CUSTOMER_ID ="id";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_GENDER ="gender";


    public SalesDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "CREATE TABLE " + TABLE_CUSTOMER +"("
            + CUSTOMER_ID + " INTEGER primary key autoincrement, "
            + CUSTOMER_NAME + " text, "
            + CUSTOMER_GENDER + " text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    String sql = "DROP TABLE IF EXISTS " + TABLE_CUSTOMER;
    sqLiteDatabase.execSQL(sql);
    onCreate(sqLiteDatabase);
    }
}
