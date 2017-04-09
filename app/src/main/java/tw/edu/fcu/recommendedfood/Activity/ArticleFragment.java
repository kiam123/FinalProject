package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import tw.edu.fcu.recommendedfood.Adapter.ArticleAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    private ListView listviewClassification;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private ImageView imgToggle;
    private boolean flag = false;//侧滑菜单是否打开的标识
    private ArticleAdapter articleAdapter;
    private ImageView imgSearch;
    private ImageView imgCommand;
    private TextView txtClassificationSet;
    private TextView txtAll, txtChineseStyle, txtHongKongStyle, txtWesternStyle, txtNanyang, txtKoreanStyle,
                        txtJapaneseStyle, txtDrink, txtDessert, txtFastFood, txtRecipe, txtBarbecue, txtConvenienceStore;
    private String classification[] = {"全部", "中式", "港式", "西式", "南洋", "韓式", "日式",
                                        "飲料", "點心", "速食", "食譜", "燒烤", "便利店"};

    public ArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_article, container, false);
        initView(viewGroup);
        initAdapter();

        return viewGroup;
    }

    //初始化view
    public void initView(ViewGroup viewGroup) {
        imgToggle = (ImageView) viewGroup.findViewById(R.id.img_toggle);
        imgCommand = (ImageView) viewGroup.findViewById(R.id.img_command);
        drawerlayout = ((DrawerLayout) viewGroup.findViewById(R.id.drawerlayout));//侧滑的根布局
        navigationview = ((NavigationView) viewGroup.findViewById(R.id.navigationview));//侧滑菜单布局
        imgSearch = (ImageView) viewGroup.findViewById(R.id.img_search);
        txtClassificationSet = (TextView) viewGroup.findViewById(R.id.txt_classification_set);

        initLayoutClassification(viewGroup);

        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//函数来关闭手势滑动
        drawerlayout.addDrawerListener(drawerlayoutListerner);
        imgToggle.setOnClickListener(btnToggleListener);
        imgSearch.setOnClickListener(imgSearchOnClickListener);
        imgCommand.setOnClickListener(imgCommandOnClickListener);
    }

    public void initLayoutClassification(ViewGroup viewGroup){
        txtAll = (TextView) viewGroup.findViewById(R.id.txt_all);
        txtChineseStyle = (TextView) viewGroup.findViewById(R.id.txt_chinese_style);
        txtHongKongStyle = (TextView) viewGroup.findViewById(R.id.txt_hong_kong_style);
        txtWesternStyle = (TextView) viewGroup.findViewById(R.id.txt_western_style);
        txtNanyang = (TextView) viewGroup.findViewById(R.id.txt_nanyang);
        txtKoreanStyle = (TextView) viewGroup.findViewById(R.id.txt_korean_style);
        txtJapaneseStyle = (TextView) viewGroup.findViewById(R.id.txt_japanese_style);
        txtDrink = (TextView) viewGroup.findViewById(R.id.txt_drink);
        txtDessert = (TextView) viewGroup.findViewById(R.id.txt_dessert);
        txtFastFood = (TextView) viewGroup.findViewById(R.id.txt_fast_food);
        txtRecipe = (TextView) viewGroup.findViewById(R.id.txt_recipe);
        txtBarbecue = (TextView) viewGroup.findViewById(R.id.txt_barbecue);
        txtConvenienceStore = (TextView) viewGroup.findViewById(R.id.txt_convenience_store);

        txtAll.setOnClickListener(classificationOnClickListener);
        txtChineseStyle.setOnClickListener(classificationOnClickListener);
        txtHongKongStyle.setOnClickListener(classificationOnClickListener);
        txtWesternStyle.setOnClickListener(classificationOnClickListener);
        txtNanyang.setOnClickListener(classificationOnClickListener);
        txtKoreanStyle.setOnClickListener(classificationOnClickListener);
        txtJapaneseStyle.setOnClickListener(classificationOnClickListener);
        txtDrink.setOnClickListener(classificationOnClickListener);
        txtDessert.setOnClickListener(classificationOnClickListener);
        txtFastFood.setOnClickListener(classificationOnClickListener);
        txtRecipe.setOnClickListener(classificationOnClickListener);
        txtBarbecue.setOnClickListener(classificationOnClickListener);
        txtConvenienceStore.setOnClickListener(classificationOnClickListener);
    }

    private View.OnClickListener classificationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txt_all:
                    txtClassificationSet.setText("全部");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_chinese_style:
                    txtClassificationSet.setText("中式");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_hong_kong_style:
                    txtClassificationSet.setText("港式");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_western_style:
                    txtClassificationSet.setText("西式");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_nanyang:
                    txtClassificationSet.setText("南洋");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_korean_style:
                    txtClassificationSet.setText("韓式");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_japanese_style:
                    txtClassificationSet.setText("日式");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_drink:
                    txtClassificationSet.setText("飲料");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_dessert:
                    txtClassificationSet.setText("點心");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_fast_food:
                    txtClassificationSet.setText("速食");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_recipe:
                    txtClassificationSet.setText("食譜");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_barbecue:
                    txtClassificationSet.setText("燒烤");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
                case R.id.txt_convenience_store:
                    txtClassificationSet.setText("便利店");
                    flag = false;
                    drawerlayout.closeDrawers();
                    break;
            }
        }
    };

    public void initAdapter() {
        articleAdapter = new ArticleAdapter(getActivity());
//        listviewClassification.setAdapter(articleAdapter);
        //TODO listviewClassification 還沒完成 Adapter，也不一定要使用 Adapter

        articleAdapter.addItem(new ArticleData("1", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("2", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("3", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("4", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("5", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("6", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("7", "標題", "內容"));
    }

    private AdapterView.OnItemClickListener articleOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleBlogActivity.class);
            startActivity(intent);
        }
    };

    private DrawerLayout.DrawerListener drawerlayoutListerner = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            //slideOffset 变化范围0~1
            View contentView = drawerlayout.getChildAt(0);//获得content
            View leftView = drawerView;

            contentView.setTranslationX(leftView.getMeasuredWidth() * slideOffset);//平移
        }

        //当侧滑菜单关闭
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            flag = false;
        }

        //当侧滑菜单打开
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            flag = true;
        }
    };

    private View.OnClickListener btnToggleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (flag) {//如果是打开的,就关闭侧滑菜单
                drawerlayout.closeDrawers();
            } else {
                drawerlayout.openDrawer(Gravity.LEFT);//布局中设置从左边打开,这里也要设置为左边打开
            }
        }
    };

    private View.OnClickListener imgSearchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleSeachAll.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener imgCommandOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleCommandActivity.class);
            startActivity(intent);
        }
    };
}
