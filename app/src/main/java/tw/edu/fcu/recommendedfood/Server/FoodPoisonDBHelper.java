package tw.edu.fcu.recommendedfood.Server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiam on 2017/10/20.
 */

public class FoodPoisonDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "";
    public static final String TABLE_NAME = "food_poison_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Account";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "COUNT";

    public FoodPoisonDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " VARCHAR(50), " +
                COL_3 + " VARCHAR(50), " +
                COL_4 + " VARCHAR(50)  " +
                ");";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
