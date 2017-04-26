package tw.edu.fcu.recommendedfood.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tw.edu.fcu.recommendedfood.R;

public class ArticleBlogActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_blog);

        imageView = (ImageView) findViewById(R.id.imageView);
//        html =    "<h1>this is h1</h1>"
//                + "<p>This text is normal</p>"
//                + "<a href=\"http://i.imgur.com/RyFb0yU.jpg\">http://i.imgur.com/RyFb0yU.jpg</a> "
//                + "<img src='http://i.imgur.com/RyFb0yU.jpg' />";

        html =  "<h1>表特首po，手機排版傷眼見諒。</h1>" +
                "<p>本魯的好友，一開始認識就覺得很正，但是又不知道正在哪，所以歸類為樸素系的正妹。</p>" +
                "<p>可以不愛，但請別傷害。<p>" +
                "<a href=\"http://i.imgur.com/WBpYpsI.jpg\">http://i.imgur.com/WBpYpsI.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/WBpYpsI.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/5zYT6cb.jpg\">http://i.imgur.com/5zYT6cb.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/5zYT6cb.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/LPeOlhE.jpg\">http://i.imgur.com/LPeOlhE.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/LPeOlhE.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/0IN4YQw.jpg\">http://i.imgur.com/0IN4YQw.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/0IN4YQw.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/WRklUTv.jpg\">http://i.imgur.com/WRklUTv.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/WRklUTv.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/blzol5m.jpg\">http://i.imgur.com/blzol5m.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/blzol5m.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/DgsxKf5.jpg\">http://i.imgur.com/DgsxKf5.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/DgsxKf5.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/Bus1uwx.jpg\">http://i.imgur.com/Bus1uwx.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/Bus1uwx.jpg' /><br><br/>"+
                "<a href=\"http://i.imgur.com/XOdIVZB.jpg\">http://i.imgur.com/XOdIVZB.jpg</a><br><br/>" +
                "<img src='http://i.imgur.com/XOdIVZB.jpg' /><br><br/>";

        
//        html =    "表特首po，手機排版傷眼見諒。\n" +
//                "本魯的好友，一開始認識就覺得很正，但是又不知道正在哪，所以歸類為樸素系的正妹。\n" +
//                "\n" +
//                "可以不愛，但請別傷害。\n" +
//                "\n" +
//                "http://i.imgur.com/WBpYpsI.jpg\n" +
//                "http://i.imgur.com/5zYT6cb.jpg\n" +
//                "http://i.imgur.com/LPeOlhE.jpg\n" +
//                "http://i.imgur.com/0IN4YQw.jpg\n" +
//                "http://i.imgur.com/WRklUTv.jpg\n" +
//                "http://i.imgur.com/blzol5m.jpg\n" +
//                "http://i.imgur.com/DgsxKf5.jpg\n" +
//                "http://i.imgur.com/Bus1uwx.jpg\n" +
//                "http://i.imgur.com/XOdIVZB.jpg";

        this.textView = (TextView)this.findViewById(R.id.txt_blog_content);
        PicassoImageGetter picassoImageGetter = new PicassoImageGetter(textView,imageView.getResources(), Picasso.with(this));
        Spanned htmlSpan = Html.fromHtml(html, picassoImageGetter, null);
        textView.setText(htmlSpan);
    }

    public class PicassoImageGetter implements Html.ImageGetter {
        final Resources resources;
        final Picasso pablo;
        final TextView textView;

        public PicassoImageGetter(final TextView textView, final Resources resources, final Picasso pablo) {
            this.textView = textView;
            this.resources = resources;
            this.pablo = pablo;
        }

        @Override
        public Drawable getDrawable(final String source) {
            final BitmapDrawablePlaceHolder result = new BitmapDrawablePlaceHolder();

            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(final Void... meh) {
                    try {
                        return pablo.load(source).get();
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap bitmap) {
                    try {
                        final BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);

                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                        result.setDrawable(drawable);
                        result.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                        textView.setText(textView.getText()); // invalidate() doesn't work correctly...
                    } catch (Exception e) {
                /* nom nom nom*/
                    }
                }

            }.execute((Void) null);

            return result;
        }

        public class BitmapDrawablePlaceHolder extends BitmapDrawable {
            protected Drawable drawable;

            @Override
            public void draw(final Canvas canvas) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }

            public void setDrawable(Drawable drawable) {
                this.drawable = drawable;
            }
        }
    }
}
