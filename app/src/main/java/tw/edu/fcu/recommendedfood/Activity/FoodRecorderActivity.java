package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Adapter.FoodDetailsAdapter;
import tw.edu.fcu.recommendedfood.Adapter.FoodNoteAdapter;
import tw.edu.fcu.recommendedfood.Data.FoodDetailData;
import tw.edu.fcu.recommendedfood.Data.FoodNoteData;
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
    private ImageView imageWindow;
    private RecyclerView noteRecyclerView;
    private RecyclerView detailsRecyclerView;
    private FoodNoteAdapter foodNoteAdapter;
    private FoodDetailsAdapter foodDetailsAdapter;

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
        imageWindow = (ImageView) findViewById(R.id.image_window);
        noteRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_note);
        detailsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_details);


        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodNoteAdapter = new FoodNoteAdapter();
        foodDetailsAdapter = new FoodDetailsAdapter();

        noteRecyclerView.setAdapter(foodNoteAdapter);
        detailsRecyclerView.setAdapter(foodDetailsAdapter);

        foodNoteAdapter.addItem(new FoodNoteData());
        foodDetailsAdapter.addItem(new FoodDetailData());
        foodDetailsAdapter.addItem(new FoodDetailData());
        foodDetailsAdapter.addItem(new FoodDetailData());
        foodDetailsAdapter.addItem(new FoodDetailData());


        imageWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageWindow.setVisibility(View.GONE);
            }
        });
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
