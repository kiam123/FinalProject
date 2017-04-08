package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import tw.edu.fcu.recommendedfood.R;

public class FoodRecorderActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private FloatingActionButton mSearchButton;
    private FloatingActionButton mGpsButton;
    private LinearLayout mSearchLayout;
    private LinearLayout mGpsLayout;
    private Animation mShowButton;
    private Animation mShowLayout;
    private Animation mHideButton;
    private Animation mHideLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recorder);

        initView();
        initAnimation();
    }

    public void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mSearchButton = (FloatingActionButton) findViewById(R.id.search_floatingActionButton);
        mGpsButton = (FloatingActionButton) findViewById(R.id.gps_floatingActionButton);
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        mGpsLayout = (LinearLayout) findViewById(R.id.gps_layout);
    }

    public void initAnimation() {
        mShowButton = AnimationUtils.loadAnimation(this, R.anim.show_button);
        mHideButton = AnimationUtils.loadAnimation(this, R.anim.hide_button);
        mHideLayout = AnimationUtils.loadAnimation(this, R.anim.hide_layout);
        mShowLayout = AnimationUtils.loadAnimation(this, R.anim.show_layout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchLayout.getVisibility() == View.VISIBLE && mGpsLayout.getVisibility()
                        == View.VISIBLE) {
                    mSearchLayout.setVisibility(View.GONE);
                    mGpsLayout.setVisibility(View.GONE);
                    mSearchButton.setVisibility(View.GONE);
                    mGpsButton.setVisibility(View.GONE);
                    mGpsLayout.startAnimation(mHideLayout);
                    mSearchLayout.startAnimation(mHideLayout);
                    fab.startAnimation(mHideButton);
                } else {
                    mSearchLayout.setVisibility(View.VISIBLE);
                    mGpsLayout.setVisibility(View.VISIBLE);
                    mSearchButton.setVisibility(View.VISIBLE);
                    mGpsButton.setVisibility(View.VISIBLE);
                    mGpsLayout.startAnimation(mShowLayout);
                    mSearchLayout.startAnimation(mShowLayout);
                    fab.startAnimation(mShowButton);
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FoodRecorderActivity.this, FoodTextSeachActivity.class);
                startActivity(intent);
            }
        });

        mGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FoodRecorderActivity.this, FoodGpsSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
