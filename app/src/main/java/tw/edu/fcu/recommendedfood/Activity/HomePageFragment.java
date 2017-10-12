package tw.edu.fcu.recommendedfood.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.ImgurLib.DocumentHelper;
import tw.edu.fcu.recommendedfood.Photo.PicassoImageGetter;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private String[] mTitles = {"收藏", "文章"};
    private TextView txtItem;
    private ImageView imgHeader;
    private Context mContext;
    private Button btnHeader;
    private Uri returnUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    PopupWindow popWindow;

    public HomePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homepage, container, false);

        initFragment();
        initView(viewGroup);
        initOnclickListener();

        return viewGroup;
    }

    //view初始化
    public void initView(ViewGroup viewGroup) {//不一定要用viewGroup，也可以直接使用getView()
        mContext = getActivity();
        imgHeader = (ImageView) viewGroup.findViewById(R.id.img_header);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) viewGroup.findViewById(R.id.tabs);
        txtItem = (TextView) viewGroup.findViewById(R.id.txt_item);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragmentArrayList, mTitles));
        mTabLayout.setupWithViewPager(viewPager);

        initTabLayoutEvent();
    }

    private void initTabLayoutEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    txtItem.setText("所有收藏");
                } else if (tab.getPosition() == 1) {
                    txtItem.setText("所有文章");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //初始化每個頁面的fragment（部落格，記錄食物，待吃，個人主頁）
    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new HomePageCollectArticleFragment());//收藏文章
        fragmentArrayList.add(new HomePageSelfArticleFragment());//自己的文章
//        fragmentArrayList.add(new HomePageFriendsFragment());//朋友
    }

    public void initOnclickListener() {
        imgHeader.setOnClickListener(imgHeaderClickListener);
    }

    private View.OnClickListener imgHeaderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
            btnHeader = (Button) view.findViewById(R.id.button2);
            btnHeader.setText("更換照片");
            btnHeader.setOnClickListener(btnHeaderOnClickListener);

            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画
            popWindow.setTouchable(true);
            popWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });
            popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

            //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
            Log.v("heigh", v.getHeight() + " " + view.getHeight());
            popWindow.showAsDropDown(v, v.getHeight() / 2, -v.getHeight() / 2);
        }
    };

    private View.OnClickListener btnHeaderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");// 相片类型
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data != null) {
                returnUri = data.getData();
                Log.v("filePath", "" + returnUri);
                Picasso.with(getActivity()).load(returnUri).transform(new CircleTransform()).into(imgHeader);
                popWindow.dismiss();
            }
        }
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }


    //設定viewpager的adapter
    class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        String[] mTitles;

        public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList, String[] mTitles) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
            this.mTitles = mTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
