package tw.edu.fcu.recommendedfood.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.ArticleBlogAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HomePageDBHelper;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

public class ArticleBlogActivity extends AppCompatActivity {
    String messenge;
    ListView listview;
    ArticleBlogAdapter articleBlogAdapter;
    LinearLayout lnLayout1;
    LinearLayout lnLayout2;
    EditText edtReply;
    EditText edtReply2;
    HashMap<String, String> params = new HashMap<String, String>();
    HashMap<String, String> params2 = new HashMap<String, String>();
    HashMap<String, String> params3 = new HashMap<String, String>();
    HttpCall httpCallPost;
    HttpCall httpCallPost2;
    HttpCall httpCallPost3;
    ArticleData articleData;
    HomePageDBHelper homePageDBHelper;
    Button btn_send1;
    NewTextWatcher newTextWatcher = new NewTextWatcher();
    PopupWindow popWindow;
    ImageView imgFavorite;

    public static final int AdapterArticleUpdate = 1;
    public static final int AdapterCommentUpdate = 2;
    public static final int Update = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_blog);

        initView();
        getIntentData();
        initAdapter();
        initDataBase();
//        getArticle();
        setEdtListener();
        setListenerToRootView();
        getFireBase();

//        getCommand();
//        postCommand();
    }

    public void initDataBase() {
        homePageDBHelper = new HomePageDBHelper(this);
    }

    public void getIntentData() {
        Intent intent = getIntent();

        if (intent != null) {
            articleData = (ArticleData) intent.getSerializableExtra(ArticleClassificationHotFragment.ARTICLEDATA);
        }
    }

    public void getArticle() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_blog_connect_MySQL.php");//140.134.26.31

        params.put("article_id", articleData.getArticleId());
        httpCallPost.setParams(params);
        postToServer(httpCallPost);
    }

    public void postToServer(HttpCall httpCallPost) {
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                final String result = response;
                Log.v("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    String article = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        article = jsonArray.getJSONObject(i).getString("article");
                        articleData.articleBlogData.setHtmlContent(article);

                        articleBlogAdapter.addItem(new ArticleBlogData(articleData.getTitle(), articleData.articleBlogData.getAuthor(), articleData.articleBlogData.getDate(),
                                articleData.articleBlogData.getTime(), articleData.articleBlogData.getHtmlContent(), 0));
                    }
                    params.clear();
//                    articleBlogAdapter.addItem(new ArticleBlogData(1,"Jimreveller","哪裡樸素不知能否解釋一下","04/20 08:23"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
    }

    public void getCommand() {
        httpCallPost2 = new HttpCall();
        httpCallPost2.setMethodtype(HttpCall.POST);
        httpCallPost2.setUrl("http://140.134.26.31/recommended_food_db/command.php");//140.134.26.31

        Log.v("aaaasd", articleData.getArticleId());
        params2.put("Query", articleData.getArticleId());
        httpCallPost2.setParams(params2);
        postToServer2(httpCallPost2);
    }

    public void postCommand() {
        httpCallPost3 = new HttpCall();
        httpCallPost3.setMethodtype(HttpCall.POST);
        httpCallPost3.setUrl("http://140.134.26.31/recommended_food_db/command2.php");//140.134.26.31

        Log.v("aaaasd", articleData.getArticleId());

    }

    public void postToServer2(HttpCall httpCallPost) {
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                final String result = response;
                Log.v("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    String accountId = null;
                    String comments = null;
                    String date = null;
                    String time = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        accountId = jsonArray.getJSONObject(i).getString("account_id");
                        comments = jsonArray.getJSONObject(i).getString("comments");
                        date = jsonArray.getJSONObject(i).getString("date");
                        time = jsonArray.getJSONObject(i).getString("time");
                        articleBlogAdapter.addItem(new ArticleBlogData(1, accountId, comments, date + " " + time));
                    }
                    params.clear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
    }

    public void initView() {
        listview = (ListView) findViewById(R.id.listview);
        lnLayout1 = (LinearLayout) findViewById(R.id.ln_layout1);
        lnLayout2 = (LinearLayout) findViewById(R.id.ln_layout2);
        edtReply = (EditText) findViewById(R.id.edt_reply);
        edtReply2 = (EditText) findViewById(R.id.edt_reply2);
        edtReply.addTextChangedListener(newTextWatcher);
        edtReply2.addTextChangedListener(newTextWatcher);
        btn_send1 = (Button) findViewById(R.id.btn_send1);


//        edtReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                lnLayout1.setVisibility(View.GONE);
//                lnLayout2.setVisibility(View.VISIBLE);
//                if (edtReply2.requestFocus()) {
//                    InputMethodManager imm = (InputMethodManager)
//                            getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(edtReply2, InputMethodManager.SHOW_IMPLICIT);
//                }
//            }
//        });


//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                view.setSelected(true);
//            }
//        });
    }

    public void initAdapter() {
        articleBlogAdapter = new ArticleBlogAdapter(ArticleBlogActivity.this);
        listview.setAdapter(articleBlogAdapter);
    }

    boolean isOpened = false;

    public void setEdtListener() {
        edtReply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setListenerToRootView() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//隱藏鍵盤
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.

                    if (isOpened == true) {
                        Toast.makeText(ArticleBlogActivity.this, "123", Toast.LENGTH_LONG).show();
                        //Do two things, make the view top visible and the editText smaller
                        lnLayout1.setVisibility(View.GONE);
                        lnLayout2.setVisibility(View.VISIBLE);
                        btn_send1.setVisibility(View.GONE);
                        if (edtReply2.requestFocus()) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(edtReply2, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                    isOpened = false;
                } else if (isOpened == false) {
                    edtReply.setText(edtReply2.getText().toString());
                    messenge = edtReply.getText().toString();
                    if (!messenge.equals("")) {
                        btn_send1.setVisibility(View.VISIBLE);
                    }
                    lnLayout1.setVisibility(View.VISIBLE);
                    lnLayout2.setVisibility(View.GONE);
                    isOpened = true;
                }
            }
        });
    }

    public void imgClickMenu(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_article_blog_menu, null, false);
        int windowPos[] = calculatePopWindowPos(v, view);
        int xOff = 30;
        windowPos[0] -= xOff;

        imgFavorite = (ImageView) view.findViewById(R.id.imgFavorite);

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
        Log.v("heigh", view.getHeight() + " " + view.getHeight());
//        popWindow.showAsDropDown(view, view.getHeight() / 2, -view.getHeight() / 2);
        popWindow.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                flag = homePageDBHelper.isDataExist(articleData.getArticleId());

                if (!flag) {
                    Toast.makeText(ArticleBlogActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                } else {
                    homePageDBHelper.insertData(LoginContext.getLoginContext().getAccount(), articleData.getArticleId()
                            , articleData.getCount() + "", articleData.getTitle(), articleData.getContent());
                    Toast.makeText(ArticleBlogActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                homePageDBHelper.close();
            }
        });
    }

    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public void btnCommand(View view) {
        Date myDate = new Date();
        int thisYear = myDate.getYear() + 1900;//thisYear = 2003
        int thisMonth = myDate.getMonth() + 1;//thisMonth = 5
        int thisDate = myDate.getDate();//thisDate = 30
        params3.put("Query", "POST");
        params3.put("account_id", LoginContext.getLoginContext().getAccount());
        params3.put("article_id", articleData.getArticleId());
        params3.put("comments", messenge);
        params3.put("date", thisYear + "/" + thisMonth + "/" + thisDate);
        params3.put("time", new java.text.SimpleDateFormat("HH:mm").format(myDate));
        httpCallPost3.setParams(params3);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                final String result = response;
                Log.v("abcd", response);
                if (result.equals("ok")) {
                    Toast.makeText(ArticleBlogActivity.this, "發送成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ArticleBlogActivity.this, "發送失敗", Toast.LENGTH_LONG).show();
                }
                params3.clear();
            }
        }.execute(httpCallPost3);
    }

    public class NewTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.d("TAG", "afterTextChanged--------------->");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.d("TAG", "beforeTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            messenge = s.toString();
        }
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AdapterArticleUpdate: {
                    String article = (String) msg.obj;
                    articleData.articleBlogData.setHtmlContent(article);
                    articleBlogAdapter.addItem(new ArticleBlogData(articleData.getTitle(), articleData.articleBlogData.getAuthor(), articleData.articleBlogData.getDate(),
                            articleData.articleBlogData.getTime(), articleData.articleBlogData.getHtmlContent(), 0));
                    break;
                }
                case AdapterCommentUpdate: {
                    if (msg.getData() != null) {
                        String accountId = msg.getData().getString("AccountId");
                        String comments = msg.getData().getString("Comments");
                        String date = msg.getData().getString("Date");
                        String time = msg.getData().getString("Time");

                        if (accountId != null && comments != null && date != null && time != null) {
                            articleBlogAdapter.addComment(new ArticleBlogData(1, accountId, comments, date + " " + time));
                        } else {
                        }
                    }
                    break;
                }
                case Update: {
                    Log.v("aaccd3","Update");
                    articleBlogAdapter.updateData();
                    break;
                }
            }
        }
    };

    public void getFireBase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.v("ancs", articleData.getArticleId());
        myRef.child("article_title_table").child(articleData.getArticleId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FireBaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class FireBaseThread extends Thread {
        private DataSnapshot dataSnapshot;

        public FireBaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            String content = "內容";

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if (ds.getKey().equals("article")) {
//                    DataSnapshot dsArticle = ds.child(ds.getKey());
//                    Log.v("accd",ds.getValue()+"");
                    Message msg = new Message();
                    msg.what = AdapterArticleUpdate;
                    msg.obj = (String) ds.getValue();
                    handler.sendMessage(msg);
                } else if (ds.getKey().equals("comments")) {
                    int key = 0;
                    int count = 0;
                    for (DataSnapshot temp : ds.getChildren()) {
                        key++;
                    }

                    for (DataSnapshot ds2 : ds.getChildren()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Date", ds2.child("date").getValue() + "");
                        bundle.putString("AccountId", ds2.child("account_id").getValue() + "");
                        bundle.putString("Time", ds2.child("time").getValue() + "");
                        bundle.putString("Comments", ds2.child("comments").getValue() + "");
//                        Log.v("aaccd",ds2+"");
//                        Log.v("aaccd",
//                                "Date = " + (ds2.child("date").getValue()) +
//                                " AccountId = " + ds2.child("account_id").getValue() +
//                                " Time = " + (ds2.child("time").getValue()) +
//                                " Comments = " + ds2.child("comments").getValue() + "");
                        Message msg = new Message();
                        msg.what = AdapterCommentUpdate;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        count++;
                    }
                    if (key == count) {
                        Message msg = new Message();
                        msg.what = Update;
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }
}
