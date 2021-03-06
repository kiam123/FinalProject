package tw.edu.fcu.recommendedfood.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.Data.LogoutState;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private String[] mTitles = {"文章", "收藏"};
    private TextView txtItem;
    private TextView txtAccount;
    private TextView txtName;
    private ImageView imgHeader;
    private Context mContext;
    private Button btnHeader;
    private Button btnLogout;
    private Uri returnUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    PopupWindow popWindow;

    private SharedPreferences settings;
    private static final String data = "RegisterPage1";
    private static final String idField = "ID";
    private static final String secretCodeField = "CODE";
    private static final String checkboxField = "checkbox";

    public HomePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homepage, container, false);

        Log.v("TAG","onCreateView");
        initFragment();
        initView(viewGroup);
        initHeaderOnclickListener();
        setAccount();

        return viewGroup;
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new HomePageSelfArticleFragment());//自己的文章
        fragmentArrayList.add(new HomePageCollectArticleFragment());//收藏文章
//        fragmentArrayList.add(new HomePageFriendsFragment());//朋友
    }

    //view初始化
    public void initView(ViewGroup viewGroup) {//不一定要用viewGroup，也可以直接使用getView()
        mContext = getActivity();
        imgHeader = (ImageView) viewGroup.findViewById(R.id.img_header);
        txtAccount = (TextView) viewGroup.findViewById(R.id.txt_account);
        txtName = (TextView) viewGroup.findViewById(R.id.txt_name);
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
                    txtItem.setText("所有文章");
                } else if (tab.getPosition() == 1) {
                    txtItem.setText("所有收藏");
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

    public void initHeaderOnclickListener() {
        imgHeader.setOnClickListener(imgHeaderClickListener);
    }

    private View.OnClickListener imgHeaderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_homepager_item_popup, null, false);
            btnHeader = (Button) view.findViewById(R.id.btn_change_image);
            btnLogout = (Button) view.findViewById(R.id.btn_logout);
            btnLogout.setOnClickListener(btnLogoutOnClickListener);
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

    private View.OnClickListener btnLogoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popWindow.dismiss();
            settings = getActivity().getSharedPreferences(LoginActivity.data, getActivity().MODE_PRIVATE);
            settings.edit().putBoolean(LoginActivity.checkboxField, false)
                    .commit();

            LoginContext.getLoginContext().setState(new LogoutState());
            LoginContext.getLoginContext().forward(getActivity());
        }
    };

    public void setAccount(){
        txtAccount.setText(LoginContext.getLoginContext().getAccount());
        txtName.setText(LoginContext.getLoginContext().getName());
    }

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
