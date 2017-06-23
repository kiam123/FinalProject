package tw.edu.fcu.recommendedfood.Activity;

/**
 * Created by yan on 2017/4/6.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import tw.edu.fcu.recommendedfood.Data.WaitingEatColors;
import tw.edu.fcu.recommendedfood.Data.WaitingEatData;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.WaitingEatDBHelper;

public class WaitingEatAddtoListActivity extends AppCompatActivity {

    private EditText edtTitleText, edtContentText;

    // 啟動功能用的請求代碼
    private static final int START_CAMERA = 0;
    private static final int START_LOCATION = 1;
    private static final int START_ALARM = 2;
    private static final int START_COLOR = 3;

    // 記事物件
    private WaitingEatData waitingEatData;
    WaitingEatDBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitting_eat_item);
      //  SQLite還沒寫完
        // myDb = new WaitingEatDBHelper(this);
        processViews();

        // 取得Intent物件
        Intent intent = getIntent();
        // 讀取Action名稱
        String action = intent.getAction();

        // 如果是修改記事
        if (action.equals("com.example.yan.die_eat.EDIT_ITEM")) {
            // 接收記事物件與設定標題、內容
            waitingEatData = (WaitingEatData) intent.getExtras().getSerializable(
                    "com.example.yan.die_eat.WaitingEatData");
            edtTitleText.setText(waitingEatData.getTitle());
            edtContentText.setText(waitingEatData.getContent());
        }
        // 新增記事
        else {
            waitingEatData = new WaitingEatData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case START_CAMERA:
                    break;
                case START_LOCATION:
                    break;
                case START_ALARM:
                    break;
                // 設定顏色
                case START_COLOR:
                    int colorId = data.getIntExtra(
                            "colorId", WaitingEatColors.LIGHTGREY.parseColor());
                    waitingEatData.setColor(getColors(colorId));
                    break;
            }
        }
    }

    private WaitingEatColors getColors(int color) {
        WaitingEatColors result = WaitingEatColors.LIGHTGREY;

        if (color == WaitingEatColors.BLUE.parseColor()) {
            result = WaitingEatColors.BLUE;
        }
        else if (color == WaitingEatColors.PURPLE.parseColor()) {
            result = WaitingEatColors.PURPLE;
        }
        else if (color == WaitingEatColors.GREEN.parseColor()) {
            result = WaitingEatColors.GREEN;
        }
        else if (color == WaitingEatColors.ORANGE.parseColor()) {
            result = WaitingEatColors.ORANGE;
        }
        else if (color == WaitingEatColors.RED.parseColor()) {
            result = WaitingEatColors.RED;
        }

        return result;
    }

    private void processViews() {
        edtTitleText = (EditText) findViewById(R.id.edt_title_text);
        edtContentText = (EditText) findViewById(R.id.edt_content_text);
    }

    // 點擊確定與取消按鈕都會呼叫這個方法
    public void onSubmit(View view) {
        // 確定按鈕
        if (view.getId() == R.id.ok_teim) {
            // 讀取使用者輸入的標題與內容
            String titleText = edtTitleText.getText().toString();
            String contentText = edtContentText.getText().toString();

            // 設定記事物件的標題與內容
            waitingEatData.setTitle(titleText);
            waitingEatData.setContent(contentText);

            // 如果是修改記事
            if (getIntent().getAction().equals(
                    "com.example.yan.die_eat.EDIT_ITEM")) {
                waitingEatData.setLastModify(new Date().getTime());
            }
            // 新增記事
            else {
                waitingEatData.setDatetime(new Date().getTime());
            }

            Intent result = getIntent();
            // 設定回傳的記事物件
            result.putExtra("com.example.yan.die_eat.WaitingEatData", waitingEatData);
            setResult(Activity.RESULT_OK, result);
        }

        // 結束
        finish();
    }

    // 以後需要擴充的功能
    public void clickFunction(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.take_picture:
                break;
            case R.id.set_location:
                break;
            case R.id.set_alarm:
                break;
            case R.id.select_color:
                // 啟動設定顏色的Activity元件
                startActivityForResult(
                        new Intent(this, WaitingEatColorActivity.class), START_COLOR);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}