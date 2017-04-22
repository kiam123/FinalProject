package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import tw.edu.fcu.recommendedfood.R;

public class FoodTextSeachActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_text_seach);
    }

    public void initView(){
        listView = (ListView) findViewById(R.id.listviewClassification);
    }

    public void imgBackAtivity(View view){
        finish();
    }
}
