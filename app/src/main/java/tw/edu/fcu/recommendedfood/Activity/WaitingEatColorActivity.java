package tw.edu.fcu.recommendedfood.Activity;

/**
 * Created by yan on 2017/4/6.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import tw.edu.fcu.recommendedfood.Data.WaitingEatColors;
import tw.edu.fcu.recommendedfood.R;

public class WaitingEatColorActivity extends Activity {

    private LinearLayout colorGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_eat_color);

        processViews();

        ColorListener listener = new ColorListener();

        for (WaitingEatColors c : WaitingEatColors.values()) {
            Button button = new Button(this);
            button.setId(c.parseColor());
            LinearLayout.LayoutParams layout =
                    new LinearLayout.LayoutParams(128, 128);
            layout.setMargins(6, 6, 6, 6);
            button.setLayoutParams(layout);
            button.setBackgroundColor(c.parseColor());

            button.setOnClickListener(listener);

            colorGallery.addView(button);
        }
    }

    private void processViews() {
        colorGallery = (LinearLayout) findViewById(R.id.color_gallery);
    }

    private class ColorListener implements OnClickListener {
      //  String action = WaitingEatColorActivity.this.getIntent().getAction();

        @Override
        public void onClick(View view) {
            Intent result = getIntent();
            result.putExtra("colorId", view.getId());
            setResult(Activity.RESULT_OK, result);
            finish();
        }

    }

}