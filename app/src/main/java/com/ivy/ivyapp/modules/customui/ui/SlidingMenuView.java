package com.ivy.ivyapp.modules.customui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.ivy.commonlibrary.utils.L;

/**
 * Created by Ivy on 2018/5/20.
 * 测量             摆放     绘制
 * measure   ->  layout  ->  draw
 * |           |          |
 * onMeasure -> onLayout -> onDraw 重写这些方法, 实现自定义控件
 * <p>
 * View流程
 * onMeasure() (在这个方法里指定自己的宽高) -> onDraw() (绘制自己的内容)
 * <p>
 * ViewGroup流程
 * onMeasure() (指定自己的宽高, 所有子View的宽高)-> onLayout() (摆放所有子View) -> onDraw() (绘制内容)
 */

public class SlidingMenuView extends ViewGroup {
    private View mMenuView, mMainView;
    private int mMenuWidth;
    private float downX;
    private float moveX;
    private final int STATA_OPEN = 0;
    private final int STATA_CLOSE = 1;
    private int mCurrentStata = STATA_CLOSE;
    private Scroller mScroll;


    public SlidingMenuView(Context context) {
        super(context);
        init();
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroll = new Scroller(getContext());

    }

    /**
     * 测量并设置 所有子View的宽高
     * widthMeasureSpec: 当前控件的宽度测量规则
     * heightMeasureSpec: 当前控件的高度测量规则
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
        mMenuWidth = mMenuView.getLayoutParams().width;
        mMenuView.measure(mMenuWidth, heightMeasureSpec);
        mMainView.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * changed: 当前控件的尺寸大小, 位置 是否发生了变化
     * left:当前控件 左边距
     * top:当前控件 顶边距
     * right:当前控件 右边界
     * bottom:当前控件 下边界
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMenuView.layout(-mMenuWidth, 0, 0, b);
        mMainView.layout(0, 0, r, b);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                L.v("downX:" + downX);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                int offset = (int) (downX - moveX);
                L.v("moveX:" + moveX + "," + "offset:" + offset + "---getScrollX:" + getScrollX());

//                L.v("mMenuWidth:" + mMenuWidth);//480

                if ((getScrollX() + offset) < -mMenuWidth) {
                    L.v("不能往右拉了");
                    scrollTo(-mMenuWidth, 0);
                } else if (getScrollX() + offset > 0) {
                    L.v("不能往左拉了");
                    scrollTo(0, 0);
                } else {
                    scrollBy(offset, 0);
                }

                downX = moveX;

                break;
            case MotionEvent.ACTION_UP:

                if (getScrollX() > -mMenuWidth / 2.0f) {
                    L.v("main");
                    //scrollTo(0, 0);
                    mCurrentStata = STATA_CLOSE;
                    updateStata();
                } else {
                    mCurrentStata = STATA_OPEN;
//                    scrollTo(-mMenuWidth, 0);
                    updateStata();
                    L.v("menu");
                }


                break;
        }

        return true;
    }

    private void updateStata() {
        int startX = getScrollX();
        int dx;
        if (mCurrentStata == STATA_OPEN) {
            dx = -mMenuWidth - startX;
        } else {
            dx = 0 - startX;
        }
        int duration = Math.abs(dx) * 5;
        mScroll.startScroll(startX, 0, dx, 0, duration);
        invalidate();// 重绘界面 -> drawChild() -> computeScroll();
    }

    @Override
    public void computeScroll() { // 直到duration事件以后, 结束
        super.computeScroll();
        if (mScroll.computeScrollOffset()) {
            // true, 动画还没有结束
            // 获取当前模拟的数据, 也就是要滚动到的位置
            int currX = mScroll.getCurrX();
            scrollTo(currX, 0);
            invalidate(); // 重绘界面-> drawChild() -> computeScroll();循环
        }
    }

    float startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();//TODO  这里一定要用downX，不能引入新变量statrX
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - downX;
                float dy = event.getY() - startY;
                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > 5) { // 水平方向超出一定距离时,才拦截
                    return true;// 拦截此次触摸事件, 界面的滚动
                }

                break;
            case MotionEvent.ACTION_UP:


                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    public void swithStata() {
        if (mCurrentStata == STATA_OPEN) {
            mCurrentStata = STATA_CLOSE;
            scrollTo(0, 0);
        } else {
            mCurrentStata = STATA_OPEN;
            scrollTo(-mMenuWidth, 0);
        }
    }
}
