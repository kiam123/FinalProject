package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import tw.edu.fcu.recommendedfood.Adapter.ArticleBlogAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.R;

public class ArticleBlogActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    String html;
    ListView listview;
    ArticleBlogAdapter articleBlogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_blog);

//        imageView = (ImageView) findViewById(R.id.imageView);
//        html =    "<h1>this is h1</h1>"
//                + "<p>This text is normal</p>"
//                + "<a href=\"http://i.imgur.com/RyFb0yU.jpg\">http://i.imgur.com/RyFb0yU.jpg</a> "
//                + "<img src='http://i.imgur.com/RyFb0yU.jpg' />";

        html =  "<html> <body>" +
                "<p>表特首po，手機排版傷眼見諒。</p>" +
                "<p>本魯的好友，一開始認識就覺得很正，但是又不知道正在哪，所以歸類為樸素系的正妹。</p>" +
                "<p>可以不愛，但請別傷害。<p>" +
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
                "我樸素的定義是不濃豔、不浮誇，相比如今大多網美。" +
                "原標題（樸素系筋開腰軟正妹），不過大部分人對樸素的定義都不同，故在此改掉。" +
                "正妹我朋友是中國廣東珠海人，沒有混血，家裡還有3個姊姊，四姐妹神態相似但是各個" +
                "風格都不同，有機會再分享。" +
                "</body> </html>";

//        this.textView = (TextView)this.findViewById(R.id.txt_blog_content);
//        PicassoImageGetter picassoImageGetter = new PicassoImageGetter(textView,imageView.getResources(), Picasso.with(this));
//        Spanned htmlSpan = Html.fromHtml(html, picassoImageGetter, null);
//        textView.setText(htmlSpan);

        initView();
        initAdapter();
    }

    public void initView(){
        listview = (ListView)findViewById(R.id.listview);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });
    }

    public void initAdapter(){
        articleBlogAdapter = new ArticleBlogAdapter(ArticleBlogActivity.this);
        listview.setAdapter(articleBlogAdapter);
//        ArticleBlogData(int type, String avatar, String author, String htmlContent, String time)
        articleBlogAdapter.addItem(new ArticleBlogData("[正妹] 樸素系筋開腰軟正妹","guominhon (guominhong)","Thu Apr 20 08:20:46 2017",html,0));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","Jimreveller","哪裡樸素不知能否解釋一下","04/20 08:23"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","fawangching","正","04/20 08:42"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","swatch44","想跟她一起劈腿","04/20 08:43"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","Jimreveller","行 推","04/20 08:45"));
        articleBlogAdapter.addItem(new ArticleBlogData(1,"","yoyonigo","第一張","04/20 08:45"));
    }
}
