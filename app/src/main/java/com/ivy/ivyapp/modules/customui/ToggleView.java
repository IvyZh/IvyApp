package com.ivy.ivyapp.modules.customui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ivy.commonlibrary.utils.L;

/**
 * Created by Ivy on 2018/5/20.
 */

public class ToggleView extends View {
    private int mSwithBackgroundResource;
    private int mSlideButtonResource;
    private boolean mSwithState = false;
    private Bitmap mBackgroundBitmap;
    private Bitmap mSlideButtonBitmap;
    private Paint mPaint;
    private boolean isTouch;
    private float mTouchX;
    private OnStateChangeListener mOnStateChangeListener;


    public ToggleView(Context context) {
        super(context);
        init();
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        String namespace = "http://schemas.android.com/apk/res/com.ivy.ivyapp.modules.customui.ToggleView";
        String namespace = "http://schemas.android.com/apk/res-auto";
        mSwithState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);
        if (attrs.getAttributeResourceValue(namespace, "switch_background", -1) != -1) {
            mSwithBackgroundResource = attrs.getAttributeResourceValue(namespace, "switch_background", -1);
        }
        if (attrs.getAttributeResourceValue(namespace, "slide_button", -1) != -1) {
            mSlideButtonResource = attrs.getAttributeResourceValue(namespace, "slide_button", -1);
        }
        L.v("atts:" + mSwithBackgroundResource);
        L.v("atts:" + mSlideButtonResource);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

    }


    public void setState(boolean b) {
        mSwithState = b;
    }

    public void setSwithBackgroundResource(int ic_switch_background) {
        mSwithBackgroundResource = ic_switch_background;
    }

    public void setSlideButtonResource(int ic_slide_button) {
        mSlideButtonResource = ic_slide_button;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), mSwithBackgroundResource);
        mSlideButtonBitmap = BitmapFactory.decodeResource(getResources(), mSlideButtonResource);
        setMeasuredDimension(mBackgroundBitmap.getWidth(), mBackgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);


        if (isTouch) {
            mTouchX = mTouchX - mSlideButtonBitmap.getWidth() / 2.0f;
            if (mTouchX < 0) {
                mTouchX = 0;
            }
            if (mTouchX > (mBackgroundBitmap.getWidth() - mSlideButtonBitmap.getWidth())) {
                mTouchX = mBackgroundBitmap.getWidth() - mSlideButtonBitmap.getWidth();
            }

            canvas.drawBitmap(mSlideButtonBitmap, mTouchX, 0, mPaint);


        } else {
            if (mSwithState) {
                int left = mBackgroundBitmap.getWidth() - mSlideButtonBitmap.getWidth();
                canvas.drawBitmap(mSlideButtonBitmap, left, 0, mPaint);
            } else {
                canvas.drawBitmap(mSlideButtonBitmap, 0, 0, mPaint);
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                mTouchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isTouch = true;
                mTouchX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                mTouchX = event.getX();
                boolean preState = mSwithState;

                mSwithState = mTouchX > mBackgroundBitmap.getWidth() / 2.0f;

                if (mOnStateChangeListener != null && preState != mSwithState) {
                    mOnStateChangeListener.onStateChange(mSwithState);
                }
//                if (mTouchX > mBackgroundBitmap.getWidth() / 2.0f) {
//                    mSwithState = true;
//                } else {
//                    mSwithState = false;
//                }

                break;
        }

        invalidate();
        return true;
    }

    public void setOnSwitchStateChange(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }

    interface OnStateChangeListener {
        void onStateChange(boolean state);
    }
}
