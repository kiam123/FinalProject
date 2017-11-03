package tw.edu.fcu.recommendedfood.Data;

import android.view.View;

import java.io.Serializable;

/**
 * Created by kiam on 2017/10/18.
 */

public interface OnItemClickLitener extends Serializable {
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}
