package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import tw.edu.fcu.recommendedfood.R;

public class ArticleSeachAll extends AppCompatActivity {
    private ImageView imgBackActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_seach_all);

        initView();
    }

    public void initView(){

    }

    public void imgBackAtivity(View view){
        finish();
    }
}
