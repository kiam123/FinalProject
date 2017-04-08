package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tw.edu.fcu.recommendedfood.R;

public class ArticleCommandActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_command);

        initView();
    }

    public void initView(){

    }

    public void imgBackAtivity(View view){
        finish();
    }

    public void imgDoneCommand(View view){
        //TODO 需要做完發文以後，部落格要做更新
        finish();
    }
}
