package tw.edu.fcu.recommendedfood.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Data.OnItemClick;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    ViewPager viewPager1;
    ViewPager viewPager2;
    ArrayList<Fragment> fragmentArrayList1 = new ArrayList<>();
    ArrayList<Fragment> fragmentArrayList2 = new ArrayList<>();
    SimpleFragmentPagerAdapter simpleFragmentPagerAdapter1;
    SimpleFragmentPagerAdapter simpleFragmentPagerAdapter2;
    HashMap<String, String> params = new HashMap<String, String>();
    HttpCall httpCallPost;
    TextView txtCount1, txtTotal1, txtCount2, txtTotal2;
    TextView txtName1, txtName2;
    GameBroadCast broadcast;
    ArrayList<Integer> a = new ArrayList<Integer>(){{add(R.drawable.a1); add(R.drawable.a2); add(R.drawable.a3); add(R.drawable.a4); add(R.drawable.a5);}};
    ArrayList<Integer> b = new ArrayList<Integer>(){{add(R.drawable.b1); add(R.drawable.b2); add(R.drawable.b3); add(R.drawable.b4); add(R.drawable.b5);}};
    ArrayList<Integer> c = new ArrayList<Integer>(){{add(R.drawable.c1); add(R.drawable.c2); add(R.drawable.c3); add(R.drawable.c4); add(R.drawable.c5);}};
    ArrayList<Integer> d = new ArrayList<Integer>(){{add(R.drawable.d1); add(R.drawable.d2); add(R.drawable.d3); add(R.drawable.d4); add(R.drawable.d5); add(R.drawable.d6);}};
    ArrayList<Integer> e = new ArrayList<Integer>(){{add(R.drawable.e1); add(R.drawable.e2); add(R.drawable.e3); add(R.drawable.e4); add(R.drawable.e5);add(R.drawable.e6);add(R.drawable.e7);}};
    ArrayList<Integer> f = new ArrayList<Integer>(){{add(R.drawable.f1); add(R.drawable.f2); add(R.drawable.f3); add(R.drawable.f4); add(R.drawable.f5);}};
    ArrayList<Integer> g = new ArrayList<Integer>(){{add(R.drawable.g1); add(R.drawable.g2); add(R.drawable.g3); add(R.drawable.g4); add(R.drawable.g5); add(R.drawable.g6);}};
    ArrayList<Integer> h = new ArrayList<Integer>(){{add(R.drawable.h1); add(R.drawable.h2); add(R.drawable.h3); add(R.drawable.h4); add(R.drawable.h5);}};
    ArrayList<Integer> j = new ArrayList<Integer>(){{add(R.drawable.i1); add(R.drawable.i2); add(R.drawable.i3); add(R.drawable.i4); }};
    ArrayList<Integer> k = new ArrayList<Integer>(){{add(R.drawable.j1); add(R.drawable.j2); add(R.drawable.j3); add(R.drawable.j4); add(R.drawable.j5); add(R.drawable.j6); add(R.drawable.j7);}};
    ArrayList<ArrayList<Integer>> images = new ArrayList<ArrayList<Integer>>(){{add(a); add(b); add(c); add(d); add(e); add(f); add(g); add(h); add(j); add(k);}};
    ArrayList<String> shopName = new ArrayList<String>() {{add("Fatty's義大利餐廳"); add("N.Y. BAGELS CAFE");
                    add("小蒙牛頂級麻辣養生鍋"); add("高盧專廚法式餐廳"); add("嗆頂級麻辣鴛鴦鍋"); add("茹絲葵牛排餐廳");
                    add("原燒優質原味燒肉"); add("真膳美饌百食匯"); add("義塔-ita義塔創意料理"); add("私房泰泰式料理");}};
    int indexImage1[] = {0,1,2,3,4};
    int indexImage2[] = {5,6,7,8,9};
    int index1 = 0;
    int index2 = 0;
    boolean win = false;

    private FragmentManager fragmentManager;
    public static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";
    private int currentIndex = 0;
    private View mView;

    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("view", "onCreateView");

        mView = inflater.inflate(R.layout.fragment_game, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            ArrayList<Fragment> fragmentArrayList1 = (ArrayList<Fragment>) savedInstanceState.getSerializable("Fragment1");
            ArrayList<Fragment> fragmentArrayList2 = (ArrayList<Fragment>) savedInstanceState.getSerializable("Fragment2");
            index1 = (int) savedInstanceState.getInt("A");
            index2 = (int) savedInstanceState.getInt("B");
            initView(view);
            initAdapter(images.get(indexImage1[index2]), simpleFragmentPagerAdapter1, viewPager1, fragmentArrayList1, txtCount1, txtTotal1, 1);
            initAdapter(images.get(indexImage1[index2]), simpleFragmentPagerAdapter2, viewPager2, fragmentArrayList2, txtCount2, txtTotal2, 2);
        }else {
            initView(view);
            initAdapter(images.get(indexImage1[index1]), simpleFragmentPagerAdapter1, viewPager1, fragmentArrayList1, txtCount1, txtTotal1, 1);
            initAdapter(images.get(indexImage2[index2]), simpleFragmentPagerAdapter2, viewPager2, fragmentArrayList2, txtCount2, txtTotal2, 2);
            initBroadCast();
        }
    }

    public void initView(View view) {
        viewPager1 = (ViewPager) view.findViewById(R.id.viewpager1);
        viewPager2 = (ViewPager) view.findViewById(R.id.viewpager2);
        txtName1 = (TextView) view.findViewById(R.id.txt_name1);
        txtCount1 = (TextView) view.findViewById(R.id.txt_count1);
        txtTotal1 = (TextView) view.findViewById(R.id.txt_total1);
        txtName2 = (TextView) view.findViewById(R.id.txt_name2);
        txtCount2 = (TextView) view.findViewById(R.id.txt_count2);
        txtTotal2 = (TextView) view.findViewById(R.id.txt_total2);

        simpleFragmentPagerAdapter1 = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        simpleFragmentPagerAdapter2 = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        txtName1.setText(shopName.get(index1));
        txtName2.setText(shopName.get(index2+5));
    }

    //初始adapter內容
    public void initAdapter(ArrayList<Integer> itemId, SimpleFragmentPagerAdapter adapter, ViewPager viewPager,
                            ArrayList<Fragment> fragmentArray, final TextView count, TextView total, int pos) {
        total.setText(itemId.size() + "");
        fragmentArray.clear();


        for (int i = 0; i < itemId.size(); i++) {
            Log.v("initData", itemId.get(i) + "");
            fragmentArray.add(GameImageFragment.newInstance(itemId.get(i), pos));
        }
        adapter.update(fragmentArray);//嵌入fragment

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                count.setText(String.format("%d", position + 1));
            }
        });
    }

    //點擊viewpager更新fragment
    public void setAdapter(SimpleFragmentPagerAdapter adapter, ViewPager viewPager, TextView total, ArrayList<Integer> itemId, int pos) {
        ArrayList<Fragment> fragmentArray = new ArrayList<>();
        total.setText(itemId.size() + "");

        for (int i = 0; i < itemId.size(); i++) {
            Log.v("initData2", itemId.get(i) + "");
            fragmentArray.add(GameImageFragment.newInstance(itemId.get(i), pos));
        }

        adapter.update(fragmentArray);//點擊viewpager更新fragment
        viewPager.setCurrentItem(0);
    }

    public static class GameBroadCast extends BroadcastReceiver {
        OnItemClick onItemClickLitener;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                int pos = bundle.getInt("num");
                onItemClickLitener.setOnClick(pos);
            }
        }

        public void setOnItemClickLitener(OnItemClick context) {
            onItemClickLitener = (OnItemClick) context;
        }
    }

    public void initBroadCast() {
        broadcast = new GameBroadCast();
        broadcast.setOnItemClickLitener(new OnItemClick() {
            @Override
            public void setOnClick(int pos) {
                if (pos == 1 && !win) {
                    if((index2+1) < 5) {
                        Toast.makeText(getActivity(),"餐廳:"+shopName.get(index1)+" 勝利",Toast.LENGTH_SHORT).show();
                        setAdapter(simpleFragmentPagerAdapter2, viewPager2, txtTotal2, images.get(indexImage2[++index2]), 2);
                        txtName2.setText(shopName.get(index2+5));
                    } else{
                        Toast.makeText(getActivity(),"餐廳:"+shopName.get(index1)+" 獲得勝利",Toast.LENGTH_SHORT).show();
                        win = true;
                    }
                } else if (pos == 2 && !win) {
                    if((index1+1) < 5) {
                        Toast.makeText(getActivity(),"餐廳:"+shopName.get(index2+5)+" 勝利",Toast.LENGTH_SHORT).show();
                        setAdapter(simpleFragmentPagerAdapter1, viewPager1, txtTotal1, images.get(indexImage1[++index1]), 1);
                        txtName1.setText(shopName.get(index1));
                    } else{
                        Toast.makeText(getActivity(),"餐廳:"+shopName.get(index2+5)+" 獲得勝利",Toast.LENGTH_SHORT).show();
                        win = true;
                    }
                }
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_GAME_LISTENER");
        getActivity().registerReceiver(broadcast, filter);
    }


    public void initIntener() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_blog_connect_MySQL.php");//140.134.26.31
    }

    public void getIntenerImage() {
        params.put("article_id", "");
        httpCallPost.setParams(params);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);

            }
        }.execute(httpCallPost);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("Fragment1", fragmentArrayList1);
        outState.putSerializable("Fragment2", fragmentArrayList2);
        outState.putInt("A", index1);
        outState.putInt("B", index2);
        super.onSaveInstanceState(outState);
//        getChildFragmentManager().putFragment(outState, "Fragment1", this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("onPause","onPause");
        FragmentManager mFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (Fragment f : fragmentArrayList1) {
            Log.v("onPause","1");
            fragmentTransaction.remove(f);
        }
        for (Fragment f : fragmentArrayList2) {
            Log.v("onPause","2");
            fragmentTransaction.remove(f);
        }
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
//        mFragmentManager.popBackStack();
    }

    //設定viewpager的adapter
    class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> fragmentArrayList;
        private FragmentManager mFragmentManager;

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
        }

        public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
            this.fragmentArrayList = fragmentArrayList;
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
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void update(ArrayList<Fragment> fragmentArrayList) {
            if (this.fragmentArrayList != null) {
                Log.v("initData2", "update");
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for (Fragment f : this.fragmentArrayList) {
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commitAllowingStateLoss();
                mFragmentManager.executePendingTransactions();
            }

            this.fragmentArrayList = fragmentArrayList;
            notifyDataSetChanged();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            Log.v("destroyItem123","destroyItem");
        }
    }
}
