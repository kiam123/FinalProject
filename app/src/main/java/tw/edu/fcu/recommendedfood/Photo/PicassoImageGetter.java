package tw.edu.fcu.recommendedfood.Photo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kiam on 4/29/2017.
 */

public class PicassoImageGetter implements Html.ImageGetter {
    final Resources resources;
    final Picasso pablo;
    final TextView textView;
//        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation();

    public PicassoImageGetter(final TextView textView, final Resources resources, final Picasso pablo) {
        this.textView = textView;
        this.resources = resources;
        this.pablo = pablo;
    }

    @Override
    public Drawable getDrawable(final String source) {
        final PicassoImageGetter.BitmapDrawablePlaceHolder result = new PicassoImageGetter.BitmapDrawablePlaceHolder();

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
                    final BitmapDrawable drawable = new BitmapDrawable(resources, bitmap.createScaledBitmap(bitmap,800,800,true));
//                        (Bitmap src, int dstWidth, int dstHeight, boolean filter)
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    result.setDrawable(drawable);
                    result.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    Log.v("Width",""+drawable.getIntrinsicWidth());
                    Log.v("Height",""+drawable.getIntrinsicHeight());

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
