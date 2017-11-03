package tw.edu.fcu.recommendedfood.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
    GameBroadCast broadcast;
    int[] a = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5};/*, R.drawable.a5*/
    int[] b = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5};
    int[] c = {R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5};
    int[] d = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

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

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_game, container, false);
        } else {
//            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
            if (simpleFragmentPagerAdapter1 != null) {
                simpleFragmentPagerAdapter1 = null;
                simpleFragmentPagerAdapter2 = null;
                Toast.makeText(getActivity(), "asdasd", Toast.LENGTH_SHORT).show();
            }
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v("view", "onViewCreated");

        initView(view);
        initAdapter(a, simpleFragmentPagerAdapter1, viewPager1, fragmentArrayList1, txtCount1, txtTotal1, 1);
        initAdapter(b, simpleFragmentPagerAdapter2, viewPager2, fragmentArrayList2, txtCount2, txtTotal2, 2);
        initBroadCast();

    }

    public void initView(View view) {
        viewPager1 = (ViewPager) view.findViewById(R.id.viewpager1);
        viewPager2 = (ViewPager) view.findViewById(R.id.viewpager2);
        txtCount1 = (TextView) view.findViewById(R.id.txt_count1);
        txtTotal1 = (TextView) view.findViewById(R.id.txt_total1);
        txtCount2 = (TextView) view.findViewById(R.id.txt_count2);
        txtTotal2 = (TextView) view.findViewById(R.id.txt_total2);

        simpleFragmentPagerAdapter1 = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        simpleFragmentPagerAdapter2 = new SimpleFragmentPagerAdapter(getChildFragmentManager());
    }

    public void initAdapter(int itemId[], SimpleFragmentPagerAdapter adapter, ViewPager viewPager,
                            ArrayList<Fragment> fragmentArray, final TextView count, TextView total, int pos) {
        total.setText(itemId.length + "");
        fragmentArray.clear();

        for (int i = 0; i < itemId.length; i++) {
            fragmentArray.add(ImageFragment.newInstance(itemId[i], pos));
        }
        adapter.update(fragmentArray);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                count.setText(String.format("%d", position + 1));
            }
        });
    }

    public void setAdapter(SimpleFragmentPagerAdapter adapter, ViewPager viewPager, TextView total, int[] itemId, int pos) {
        ArrayList<Fragment> fragmentArray = new ArrayList<>();
        total.setText(itemId.length + "");

        for (int i = 0; i < itemId.length; i++) {
            fragmentArray.add(ImageFragment.newInstance(itemId[i], pos));
        }

        adapter.update(fragmentArray);
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
                if (pos == 1) {
//                    Toast.makeText(getActivity(), pos + "", Toast.LENGTH_SHORT).show();
                    setAdapter(simpleFragmentPagerAdapter1, viewPager1, txtTotal1, c, 1);
                } else if (pos == 2) {
//                    Toast.makeText(getActivity(), pos + "", Toast.LENGTH_SHORT).show();
                    setAdapter(simpleFragmentPagerAdapter2, viewPager2, txtTotal2, d, 2);
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
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

//        if(this.getView() != null){
//            this.getView().setVisibility(menuVisible ? View.GONE : View.VISIBLE);
//        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


//        getChildFragmentManager().putFragment();
    }

    //設定viewpager的adapter
    class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
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
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for (Fragment f : this.fragmentArrayList) {
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
            }

            this.fragmentArrayList = fragmentArrayList;
            notifyDataSetChanged();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }


    }
}
