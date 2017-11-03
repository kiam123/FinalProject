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
    public static final String COL_2 = "SHOPNAME";
    public static final String COL_3 = "FOODNAME";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "CALORIES";
    public static final String COL_6 = "QUANTITY";
    public static final String COL_7 = "DATE";

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
                COL_7 + " VARCHAR(50)  " +
                ");";
        db.execSQL(SQL);
        /* db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FOODNAME TEXT,CALORIES INTEGER)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String shopName, String foodName, String price,
                              String calories, String quantity, String date) {  //加入資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, shopName);
        contentValues.put(COL_3, foodName);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, calories);
        contentValues.put(COL_6, quantity);
        contentValues.put(COL_7, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean isDataExist(String shopName, String foodName, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_2 + " = '" + shopName + "' and " +
                COL_3 + " = '" + foodName + "' and " +
                COL_7 + " = '" + date + "'", null);//如果找不到就回傳null
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public boolean isDataExist(String shopName, String foodName) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_2 + " = '" + shopName + "' and " +
                COL_3 + " = '" + foodName + "'", null);//如果找不到就回傳null
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public boolean getDateData(String date) {//取得所有資料，可利用他修改或刪除資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_7 + " = '" + date + "'", null);

        return res.getCount()>0;
    }

    public Cursor getAllData(String date) {//取得所有資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_7 + " = '" + date + "'", null);

        return res;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_1 + " = '" + id + "'", null);

        return res;
    }

    public boolean updateData(String id, String quantity) { //修改資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, quantity);
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