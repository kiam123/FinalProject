package tw.edu.fcu.recommendedfood.Server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by kiam on 4/9/2017.
 */

public class HomePageDBHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "";

    public HomePageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
