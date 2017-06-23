package tw.edu.fcu.recommendedfood.Server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by kiam on 4/9/2017.
 * Modifed by yan on 6/23/2017
 */

public class WaitingEatDBHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "WaitingEat.db";
    public static final String TABLE_NAME = "Waiting_Eat_Table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATETIME";
    public static final String COL_3 = "COLOR";
    public static final String COL_4 = "TITLE";
    public static final String COL_5 = "CONTENT";
    public static final String COL_6 = "FILENAME";
    public static final String COL_7 = "LATITUDE";
    public static final String COL_8 = "LONGITUDE";
    public static final String COL_9 = "LASTMODIFY";

    //Constructor. When it called,the database will be created.
    public WaitingEatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " INTEGER , " +
                COL_3 + " , " + //////////////////WaitingData color
                COL_4 + " VARCHAR(50), " +
                COL_5 + " VARCHAR(100), " +
                COL_6 + " VARCHAR(50), " +
                COL_7 + " DOUBLE, " +
                COL_8 + " DOUBLE,  " +
                COL_9 + " INTEGER,  " +
                ");";
        db.execSQL(SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
