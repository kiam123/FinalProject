package tw.edu.fcu.recommendedfood.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.Adapter.FoodDetailsAdapter;
import tw.edu.fcu.recommendedfood.Adapter.FoodNoteAdapter;
import tw.edu.fcu.recommendedfood.Data.FoodDetailData;
import tw.edu.fcu.recommendedfood.Data.FoodNoteData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

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
    private LinearLayout lnPrompt;
    private RecyclerView noteRecyclerView;
    private RecyclerView detailsRecyclerView;
    private FoodNoteAdapter foodNoteAdapter;
    private FoodDetailsAdapter foodDetailsAdapter;
    LinearLayout lnBlock;
    String date;
    FoodDBHelper foodDBHelper;
    Dialog dialog;
    TextView txtDlgShopName, txtDlgFoodName, txtDlgPrice, txtDlgCalorie, txtDlgCount;
    ImageView imgDlgAdd, imgDlgNeg;
    Button btnCorrect;
    FoodDetailData foodDetailData;
    int pos = -1;
    int count = 0;
    int calorie = 0;
    int price = 0;
    private SharedPreferences sharedPreferences;
    public static final String PREFERENCE = "PREFERENCE";
    public static final String CLICK = "CLICK";
    boolean isClickPrompt;
    private TextView plasticizer, b, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recorder);


        initView();
        initAnimation();
        initDialog();
    }

    public void initDatabase() {
        foodDBHelper = new FoodDBHelper(this);
    }

    public void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mSearchButton = (FloatingActionButton) findViewById(R.id.search_floatingActionButton);
        mGpsButton = (FloatingActionButton) findViewById(R.id.gps_floatingActionButton);
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        mGpsLayout = (LinearLayout) findViewById(R.id.gps_layout);
        imageWindow = (ImageView) findViewById(R.id.image_window);

        lnPrompt = (LinearLayout) findViewById(R.id.ln_prompt);
        lnBlock = (LinearLayout) findViewById(R.id.ln_block);
        lnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchLayout.setVisibility(View.GONE);
                mGpsLayout.setVisibility(View.GONE);
                mSearchButton.setVisibility(View.GONE);
                mGpsButton.setVisibility(View.GONE);
                mGpsLayout.startAnimation(mHideLayout);
                mSearchLayout.startAnimation(mHideLayout);
                fab.startAnimation(mHideButton);
                lnBlock.setVisibility(View.GONE);
            }
        });

        if (!getPreferences()) {
            imageWindow.setVisibility(View.VISIBLE);
            lnPrompt.setVisibility(View.VISIBLE);
        }
        imageWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageWindow.setVisibility(View.GONE);
                lnPrompt.setVisibility(View.GONE);
                setPreferences(true);
            }
        });
    }

    public void initLisView() {
        noteRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_note);
        detailsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_details);

        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodNoteAdapter = new FoodNoteAdapter();
        foodDetailsAdapter = new FoodDetailsAdapter();

        noteRecyclerView.setAdapter(foodNoteAdapter);
        detailsRecyclerView.setAdapter(foodDetailsAdapter);

        foodNoteAdapter.addItem(new FoodNoteData());

        foodDetailsAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                dialog.show();
                pos = position;

                txtDlgShopName.setText(foodDetailsAdapter.getItem(pos).getShopName());
                txtDlgFoodName.setText(foodDetailsAdapter.getItem(pos).getFood());
                txtDlgPrice.setText(foodDetailsAdapter.getItem(pos).getPrice());
                txtDlgCalorie.setText(foodDetailsAdapter.getItem(pos).getCalorie());
                plasticizer.setText(foodDetailsAdapter.getItem(pos).getPlasticizer());
//                b.setText(foodDetailsAdapter.getItem(pos).getB());
//                c.setText(foodDetailsAdapter.getItem(pos).getC());
                txtDlgCount.setText(foodDetailsAdapter.getItem(pos).getQuantity());
                foodDetailData = foodDetailsAdapter.getItem(pos);

                Cursor cursor = foodDBHelper.getData(LoginContext.getLoginContext().getAccount(),foodDetailData.getId());
                cursor.moveToFirst();
                price = Integer.parseInt(cursor.getString(3));
                calorie = Integer.parseInt(cursor.getString(4));
                count = Integer.parseInt(foodDetailData.getQuantity());
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodRecorderActivity.this);
                builder.setMessage("是否刪除 " + foodDetailsAdapter.getItem(position).getFood());
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "確定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                foodDBHelper.deleteData(foodDetailsAdapter.getItem(position).getId());
                                foodDetailsAdapter.deleteItem(position);
                                Toast.makeText(FoodRecorderActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        setDadabase();
    }

    public void setPreferences(boolean isClickPrompt) {
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(CLICK, isClickPrompt)
                .commit();
    }

    public boolean getPreferences() {
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        isClickPrompt = sharedPreferences.getBoolean(CLICK, false);
        return isClickPrompt;
    }

    public void setDadabase() {
        Log.v("aaacoount",LoginContext.getLoginContext().getAccount());
        Cursor res = foodDBHelper.getAllData(LoginContext.getLoginContext().getAccount(),date);
        if (res.getCount() != 0) {
            res.moveToFirst();

            int calorie = Integer.parseInt(res.getString(7)) * Integer.parseInt(res.getString(5));
            int price = Integer.parseInt(res.getString(4)) * Integer.parseInt(res.getString(7));

            foodDetailsAdapter.addItem(new FoodDetailData(res.getString(0), res.getString(2),res.getString(3),
                    price + "", calorie + "", res.getString(6), res.getString(7)));

            while (res.moveToNext()) {
                calorie = Integer.parseInt(res.getString(7)) * Integer.parseInt(res.getString(5));
                price = Integer.parseInt(res.getString(4)) * Integer.parseInt(res.getString(7));
                foodDetailsAdapter.addItem(new FoodDetailData(res.getString(0), res.getString(2),res.getString(3),
                        price + "", calorie + "", res.getString(6), res.getString(7)));
            }
        }
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
                    lnBlock.setVisibility(View.VISIBLE);
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FoodRecorderActivity.this, FoodTextSeachActivity.class);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }
        });

        mGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FoodRecorderActivity.this, FoodGpsSearchActivity.class);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }
        });
    }

    public void initDialog() {
        dialog = new Dialog(FoodRecorderActivity.this, R.style.MyDialog2);
        dialog.setContentView(R.layout.layout_food_dlg_edit);
        txtDlgShopName = (TextView) dialog.findViewById(R.id.txt_shop_name);
        txtDlgFoodName = (TextView) dialog.findViewById(R.id.txt_food_name);
        txtDlgPrice = (TextView) dialog.findViewById(R.id.txt_price);
        txtDlgCalorie = (TextView) dialog.findViewById(R.id.txt_calorie);
        plasticizer = (TextView) dialog.findViewById(R.id.txt_plasticizer);
//        b = (TextView) dialog.findViewById(R.id.b);
//        c = (TextView) dialog.findViewById(R.id.c);
        txtDlgCount = (TextView) dialog.findViewById(R.id.txt_count);
        imgDlgAdd = (ImageView) dialog.findViewById(R.id.img_add);
        imgDlgNeg = (ImageView) dialog.findViewById(R.id.img_neg);
        btnCorrect = (Button) dialog.findViewById(R.id.btn_correct);

        imgDlgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 5) {
                    count += 1;
                    txtDlgCount.setText(count + "");
                    txtDlgCalorie.setText((calorie * count) + "");
                    txtDlgPrice.setText((price * count) + "");
                }
            }
        });
        imgDlgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count -= 1;
                    txtDlgCount.setText(count + "");
                    txtDlgCalorie.setText((calorie * count) + "");
                    txtDlgPrice.setText((price * count) + "");
                }
            }
        });
        btnCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDetailData.setQuantity(count + "");
                foodDetailData.setCalorie((calorie * count) + "");
                foodDetailData.setPrice((price * count) + "");
                foodDBHelper.updateData(foodDetailData.getId(), txtDlgCount.getText().toString());
                foodDetailsAdapter.updateItem(pos, foodDetailData);
                foodDetailsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatabase();
        Intent intent = getIntent();
        date = intent.getStringExtra("DATE");
        initLisView();
    }
}
