package tw.edu.fcu.recommendedfood.Server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by kiam on 4/9/2017.
 */

public class HomePageDBHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "article.db";
    public static final String TABLE_NAME = "food_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Account";
    public static final String COL_3 = "ARICLE_ID";
    public static final String COL_4 = "COUNT";
    public static final String COL_5 = "TITLE";
    public static final String COL_6 = "CONTENT";

    public HomePageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " VARCHAR(50), " +
                COL_3 + " VARCHAR(50), " +
                COL_4 + " VARCHAR(50), " +
                COL_5 + " VARCHAR(50), " +
                COL_6 + " VARCHAR(50)  " +
                ");";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String account, String ariticleId, String count, String title, String content) {  //加入資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, account);
        contentValues.put(COL_3, ariticleId);
        contentValues.put(COL_4, count);
        contentValues.put(COL_5, title);
        contentValues.put(COL_6, content);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean isDataExist(String articleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_3 + " = '" + articleId + "'", null);//如果找不到就回傳null
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public boolean getAccount(String account) {//取得所有資料，可利用他修改或刪除資料
        boolean flag;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + COL_2 + " = '" + account + "'", null);
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public Cursor getAllData() {//取得所有資料，可利用他修改或刪除資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ARICLE_ID = ?", new String[]{id});
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
