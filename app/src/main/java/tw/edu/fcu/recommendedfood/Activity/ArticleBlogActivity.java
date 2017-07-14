package tw.edu.fcu.recommendedfood.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import tw.edu.fcu.recommendedfood.Adapter.ArticleBlogAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.R;

public class ArticleBlogActivity extends AppCompatActivity {
    String html;
    ListView listview;
    ArticleBlogAdapter articleBlogAdapter;
    LinearLayout lnLayout1;
    LinearLayout lnLayout2;
    EditText edtReply;
    EditText edtReply2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_blog);

        html =  "<html> <body>" +
                "<p>表特首po，手機排版傷眼見諒。</p>" +
                "<p>本魯的好友，一開始認識就覺得很正，但是又不知道正在哪，所以歸類為樸素系的正妹。</p>" +
                "<p>可以不愛，但請別傷害。</p>" +
                "<a href=\"http://i.imgur.com/WBpYpsI.jpg\">http://i.imgur.com/WBpYpsI.jpg</a> <br>" +
                "<img src='http://i.imgur.com/WBpYpsI.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/5zYT6cb.jpg\">http://i.imgur.com/5zYT6cb.jpg</a> <br>" +
                "<img src='http://i.imgur.com/5zYT6cb.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/LPeOlhE.jpg\">http://i.imgur.com/LPeOlhE.jpg</a> <br>" +
                "<img src='http://i.imgur.com/LPeOlhE.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/0IN4YQw.jpg\">http://i.imgur.com/0IN4YQw.jpg</a> <br>" +
                "<img src='http://i.imgur.com/0IN4YQw.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/WRklUTv.jpg\">http://i.imgur.com/WRklUTv.jpg</a> <br>" +
                "<img src='http://i.imgur.com/WRklUTv.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/blzol5m.jpg\">http://i.imgur.com/blzol5m.jpg</a> <br>" +
                "<img src='http://i.imgur.com/blzol5m.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/DgsxKf5.jpg\">http://i.imgur.com/DgsxKf5.jpg</a> <br>" +
                "<img src='http://i.imgur.com/DgsxKf5.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/Bus1uwx.jpg\">http://i.imgur.com/Bus1uwx.jpg</a> <br>" +
                "<img src='http://i.imgur.com/Bus1uwx.jpg' /> <br>" +
                "<a href=\"http://i.imgur.com/XOdIVZB.jpg\">http://i.imgur.com/XOdIVZB.jpg</a> <br>" +
                "<img src='http://i.imgur.com/XOdIVZB.jpg' /> <br>" +
                "<p>我樸素的定義是不濃豔、不浮誇，相比如今大多網美。</p>" +
                "<p>原標題（樸素系筋開腰軟正妹），不過大部分人對樸素的定義都不同，故在此改掉。</p>" +
                "<p>正妹我朋友是中國廣東珠海人，沒有混血，家裡還有3個姊姊，四姐妹神態相似但是各個</p>" +
                "<p>風格都不同，有機會再分享。</p>" +
                "</body> </html>";

        //

        initView();
        setListenerToRootView();
        initAdapter();
    }

    public void initView(){
        listview = (ListView)findViewById(R.id.listview);
        lnLayout1 = (LinearLayout)findViewById(R.id.ln_layout1);
        lnLayout2 = (LinearLayout)findViewById(R.id.ln_layout2);
        edtReply = (EditText)findViewById(R.id.edt_reply);
        edtReply2 = (EditText)findViewById(R.id.edt_reply2);

        edtReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                lnLayout1.setVisibility(View.GONE);
                lnLayout2.setVisibility(View.VISIBLE);
                if (edtReply2.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtReply2, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                view.setSelected(true);
//            }
//        });
    }

    public void initAdapter(){
        articleBlogAdapter = new ArticleBlogAdapter(ArticleBlogActivity.this);
        listview.setAdapter(articleBlogAdapter);

        articleBlogAdapter.addItem(new ArticleBlogData("[正妹] 樸素系筋開腰軟正妹","guominhon (guominhong)","Thu Apr 20 08:20:46 2017",html,0));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","Jimreveller","哪裡樸素不知能否解釋一下","04/20 08:23"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","fawangching","正","04/20 08:42"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","swatch44","想跟她一起劈腿","04/20 08:43"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","Jimreveller","行 推","04/20 08:45"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","yoyonigo","第一張","04/20 08:45"));
    }

    boolean isOpened = false;

    public void setListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.

                    if (isOpened == false) {
                        //Do two things, make the view top visible and the editText smaller
                        lnLayout1.setVisibility(View.GONE);
                        lnLayout2.setVisibility(View.VISIBLE);
                        if (edtReply2.requestFocus()) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(edtReply2, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                    isOpened = true;
                } else if (isOpened == true) {
                    lnLayout1.setVisibility(View.VISIBLE);
                    lnLayout2.setVisibility(View.GONE);
                    isOpened = false;
                }
            }
        });
    }
}
