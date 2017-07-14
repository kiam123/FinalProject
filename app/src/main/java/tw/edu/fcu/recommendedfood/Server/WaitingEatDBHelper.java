package tw.edu.fcu.recommendedfood.Server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by kiam on 4/9/2017.
 * Modifed by yan on 6/27/2017
 */

public class WaitingEatDBHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "WaitingEat.db"; //DB名稱
    public static final int VERSION = 1; //DB版本
    private static SQLiteDatabase db; //DB物件

    //建構子
    public WaitingEatDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //建構子
    public WaitingEatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static SQLiteDatabase getDb(Context context){
        if (db == null || !db.isOpen()){

//            db=new WaitingEatDBHelper(context,DATABASE_NAME,null,VERSION);
          //  db = new WaitingEatDBHelper(context,DATABASE_NAME,null,VERSION).getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WaitingEatDBItem.SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WaitingEatDBItem.TABLE_NAME);
        onCreate(db);
    }
}
