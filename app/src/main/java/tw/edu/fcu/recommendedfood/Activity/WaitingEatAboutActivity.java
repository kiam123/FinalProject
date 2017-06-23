package tw.edu.fcu.recommendedfood.Activity;

/**
 * Created by yan on 2017/4/6.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class WaitingEatAboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 結束按鈕
    public void clickOk(View view) {
        // 呼叫這個方法結束Activity元件
        finish();
    }

}