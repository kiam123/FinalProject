package tw.edu.fcu.recommendedfood.Server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by kiam on 01/05/2016.
 */
public class FoodDBHelper extends SQLiteOpenHelper implements Serializable {

    public static final String DATABASE_NAME = "food.db";
    public static final String TABLE_NAME = "food_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FOODNAME";
    public static final String COL_3 = "SHOPNAME";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "CALORIES";
    public static final String COL_6 = "DATE";
    public static final String COL_7 = "QUANTITY";
    public static final String COL_8 = "TIME";

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " VARCHAR(50), " +
                COL_3 + " VARCHAR(50), " +
                COL_4 + " VARCHAR(50), " +
                COL_5 + " VARCHAR(50), " +
                COL_6 + " VARCHAR(50), " +
                COL_7 + " VARCHAR(50), " +
                COL_8 + " VARCHAR(50)  " +
                ");";
        db.execSQL(SQL);
        /* db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FOODNAME TEXT,CALORIES INTEGER)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String food_name, String shopName, String price,
                              String calories, String date, String quantity, String time) {  //加入資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, food_name);
        contentValues.put(COL_3, shopName);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, calories);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, quantity);
        contentValues.put(COL_8, time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {//取得所有資料，可利用他修改或刪除資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }

    public Cursor getDayInfo(String date, String time) {//取得當天的資料
        SQLiteDatabase db = this.getWritableDatabase();
        //記得''這個可能會影響sqlite
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_6 + " = '" + date + "' and " +
                COL_8 + " = '" + time + "'", null);//如果找不到就回傳null

        return res;
    }

    public String getId(String food_name, String date, String time) {//取得當天的資料
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "";
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_2 + " = '" + food_name + "' and " +
                COL_6 + " = '" + date + "' and " +
                COL_8 + " = '" + time + "'", null);//如果找不到就回傳null

        res.moveToFirst();
        id = res.getString(0);
        res.close();
        return id;
    }

    public boolean getDaySameInfo(String food_name, String date, String time) {//取得是否是當天的資料
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_2 + " = '" + food_name + "' and " +
                COL_6 + " = '" + date + "' and " +
                COL_8 + " = '" + time + "'", null);//如果找不到就回傳null
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public boolean updateData(String id, String food_name, String date, String quantity) { //修改資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, food_name);
        //contentValues.put(COL_5, calories);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, quantity);
        Log.v("msgData", id + " " + food_name + " " + date + " " + quantity);
        db.update(TABLE_NAME, contentValues, "ID = " + id, null);

        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    @Override
    public synchronized void close() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException e) {

        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
    }
}