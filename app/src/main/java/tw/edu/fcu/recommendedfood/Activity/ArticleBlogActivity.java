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
        html =    "<h1>this is h1</h1>"
                + "<p>This text is normal</p>"
                + "<a href=\"http://i.imgur.com/RyFb0yU.jpg\">http://i.imgur.com/RyFb0yU.jpg</a> "
                + "<img src='http://i.imgur.com/RyFb0yU.jpg' />";

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
