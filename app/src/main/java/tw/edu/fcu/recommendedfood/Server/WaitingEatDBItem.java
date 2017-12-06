package tw.edu.fcu.recommendedfood.Server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CalendarContract;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import tw.edu.fcu.recommendedfood.Activity.WaitingEatAddtoListActivity;
import tw.edu.fcu.recommendedfood.Data.WaitingEatColors;
import tw.edu.fcu.recommendedfood.Data.WaitingEatData;

/**
 * Created by yan on 2017/6/27.
 */

public class WaitingEatDBItem {
    public static final String TABLE_NAME = "Waiting_Eat_Table";
    public static final String COL_1 = "ID"; //編號
    public static final String COL_2 = "DATETIME"; //日期時間
    public static final String COL_3 = "COLOR"; //顏色
    public static final String COL_4 = "TITLE"; //標題
    public static final String COL_5 = "CONTENT"; //內容
    public static final String COL_6 = "LATITUDE"; //經度
    public static final String COL_7 = "LONGITUDE"; //緯度
    public static final String COL_8 = "LASTMODIFY"; //修改

    private WaitingEatDBHelper waitingEatDBHelper;
    private SQLiteDatabase db;

//    public static final String SQL =
//            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
//            COL_1 + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            COL_3 + "  INTEGER NOT NULL, " +
//            COL_4 + "  INTEGER NOT NULL, " +
//            COL_5 + "  TEXT NOT NULL, " +
//            COL_6 + "  TEXT NOT NULL, " +
//            COL_9 + "  REAL, " +
//            COL_10 + "  REAL, " +
//            COL_8 + " INTEGER );" ;

//    public static final String SQL =
//            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
//                    COL_1 + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COL_3 + "   VARCHAR(50),  " +
//                    COL_4 + "   VARCHAR(50),  " +
//                    COL_5 + "   VARCHAR(50),  " +
//                    COL_6 + "   VARCHAR(50),  " +
//                    COL_9 + "   VARCHAR(50),  " +
//                    COL_10 + "   VARCHAR(50),  " +
//                    COL_8 + "   VARCHAR(50) );" ;

    public static final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " VARCHAR(50), " +
            COL_3 + " VARCHAR(50), " +
            COL_4 + " VARCHAR(50), " +
            COL_5 + " VARCHAR(50), " +
            COL_6 + " VARCHAR(50), " +
            COL_7 + " VARCHAR(50), " +
            COL_8 + " VARCHAR(50)  " +
            ");";

    //建構子
    public WaitingEatDBItem(Context context) {
        //建構子
        waitingEatDBHelper = new WaitingEatDBHelper(context);
        db = waitingEatDBHelper.getWritableDatabase();
    }

    //關db
    public void close() {
        db.close();
    }

    public WaitingEatData insert(WaitingEatData data) {
        ContentValues cv = new ContentValues(); //建立新增資料的物件

        cv.put(COL_2, data.getDatetime());
        cv.put(COL_3, data.getColor().parseColor());
        cv.put(COL_4, data.getTitle());
        cv.put(COL_5, data.getContent());
        cv.put(COL_6, data.getLatitude());
        cv.put(COL_7, data.getLongitude());
        cv.put(COL_8, data.getLastModify());

        //新增一筆資料並取得編號  表格名稱,沒指定欄位值的預設值,包裝新增資料的CV
        long id = db.insert(TABLE_NAME, null, cv);

        data.setId(id);//設定編號

        return data;
    }

    public boolean update(WaitingEatData data) {
        ContentValues cv = new ContentValues();//建立修改資料的物件

        cv.put(COL_2, data.getDatetime());
        cv.put(COL_3, data.getColor().parseColor());
        cv.put(COL_4, data.getTitle());
        cv.put(COL_5, data.getContent());
        cv.put(COL_6, data.getLatitude());
        cv.put(COL_7, data.getLongitude());
        cv.put(COL_8, data.getLastModify());

        String key = COL_1 + "=" + data.getId(); //若要修改資料 編號要正確
        return db.update(TABLE_NAME, cv, key, null) > 0;
    }

    public boolean delete(long id) {
        String key = COL_1 + "=" + id;
        return db.delete(TABLE_NAME, key, null) > 0;
    }

    // 讀取資料
    public List<WaitingEatData> getAll() {
        List<WaitingEatData> result = new ArrayList<>();
        //資料表名稱, 欄位名稱, WHERE, WHERE的參數, GROUP BY, HAVING, ORDER BY, 限制回傳的rows數量
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
        cursor.close();
        return result;
    }

    //取得指定編號的資料物件
    public WaitingEatData get(long id) {
        WaitingEatData data = null;
        String key = COL_1 + "=" + id;

        Cursor result = db.query(TABLE_NAME, null, key, null, null, null, null, null);

        if (result.moveToFirst()) { //如果有查詢結果
            data = getRecord(result);
        }
        result.close();
        return data;
    }

    //把Cursor目前資料包裝為物件
    public WaitingEatData getRecord(Cursor cursor) {
        WaitingEatData result = new WaitingEatData();

        result.setId(cursor.getLong(0));
        result.setDatetime(cursor.getLong(1));
        result.setColor(WaitingEatAddtoListActivity.getColors(cursor.getInt(2)));
        result.setTitle(cursor.getString((3)));
        result.setContent(cursor.getString(4));
        result.setLatitude(cursor.getDouble(5));
        result.setLongitude(cursor.getDouble(6));
        result.setLastModify(cursor.getLong(7));

        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);


        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    public List<WaitingEatData> getData(String title) {//取得所有資料，可利用他修改或刪除資料
        List<WaitingEatData> result = new ArrayList<>();
        SQLiteDatabase db = waitingEatDBHelper.getWritableDatabase();


        String key = COL_4 + " LIKE '%"+title+"%'";
        Cursor cursor = db.query(TABLE_NAME, null, key, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
        cursor.close();
        return result;
    }


    public void sample() {
        WaitingEatData item = new WaitingEatData(0, new Date().getTime(), WaitingEatColors.RED, "關於Android Tutorial的事情.", "Hello content", 0, 0, 0);
        WaitingEatData item2 = new WaitingEatData(0, new Date().getTime(), WaitingEatColors.BLUE, "一隻非常可愛的小狗狗!", "她的名字叫「大熱", 25.04719, 121.516981, 0);
        WaitingEatData item3 = new WaitingEatData(0, new Date().getTime(), WaitingEatColors.GREEN, "一首非常好聽的音樂！", "Hello content", 0, 0, 0);
        WaitingEatData item4 = new WaitingEatData(0, new Date().getTime(), WaitingEatColors.ORANGE, "儲存在資料庫的資料", "Hello content", 0, 0, 0);

        insert(item);
        insert(item2);
        insert(item3);
        insert(item4);
    }
}
