package tw.edu.fcu.recommendedfood.Widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by kiam on 2017/10/12.
 */
public class DragHelperLinearLayout extends LinearLayout {
    ViewDragHelper dragHelper;
    Hashtable<Integer, ArrayList<Integer>> viewHashTable = new Hashtable<Integer, ArrayList<Integer>>();

    public DragHelperLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, 2.0f, new ViewDragCallback(context));
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        Context context;

        public ViewDragCallback(Context context) {
            this.context = context;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        //當手放開以後
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            for (int i = 0; i < getChildCount(); i++) {
                if (releasedChild.getId() == getChildAt(i).getId()) {
                    int top = viewHashTable.get(releasedChild.getId()).get(0);
                    int left = viewHashTable.get(releasedChild.getId()).get(1);
                    dragHelper.settleCapturedViewAt(left, top);
                    invalidate();

                    break;
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            ArrayList<Integer> views = new ArrayList<>();

            views.add(getChildAt(i).getTop());
            views.add(getChildAt(i).getLeft());
            viewHashTable.put(getChildAt(i).getId(), views);
            Log.v("onLayout", ""+getChildAt(i).getId()+" "+views.get(0)+" "+views.get(1));
        }
    }
}