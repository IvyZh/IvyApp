package com.ivy.ivyapp.modules.customui.ui;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;

import java.text.SimpleDateFormat;

/**
 * Created by Ivy on 2018/5/20.
 * 带下拉刷新和上拉加载的自定义ListView
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private final int STATE_REFRESHING = 0;//正在刷新
    private final int STATE_PULL_DOWN_TO_REFRESH = 1;//下拉刷新
    private final int STATE_RELEASE_REFRESH = 2;//释放刷新
    private int mCurrentState = STATE_PULL_DOWN_TO_REFRESH;//当前状态-默认是下拉刷新
    private View mHeaderView;
    private View mFooterView;
    private int mHeaderViewMeasuredHeight;
    private float downY;//按下坐标
    private float moveY;//移动坐标
    private int mTopPadding;
    private TextView mTvRefreshState;
    private TextView mTvLastRefreshTime;
    private ImageView mIvArrow;
    private ProgressBar mPbLoading;
    private long duration = 1000;
    private RotateAnimation rotateUp, rotateDown;
    private OnRefreshListener mOnRefreshListener;
    private int mFooterViewMeasuredHeight;
    private boolean isLoadMoreMode;

    public RefreshListView(Context context) {
        super(context);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        intHeaderView();
        initFooterView();
        initAnimation();
        setOnScrollListener(this);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        rotateUp = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateUp.setDuration(duration);
        rotateUp.setFillAfter(true);

        rotateDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateDown.setDuration(duration);
        rotateDown.setFillAfter(true);
    }

    private void intHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.layout_refresh_listview_header, null);

        mTvRefreshState = mHeaderView.findViewById(R.id.tv_refresh_state_headerview);
        mTvLastRefreshTime = mHeaderView.findViewById(R.id.tv_last_refresh_time_headerview);
        mPbLoading = mHeaderView.findViewById(R.id.pb_listview_header);
        mIvArrow = mHeaderView.findViewById(R.id.iv_arrow_listview_header);


        mHeaderView.measure(0, 0);
        mHeaderViewMeasuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewMeasuredHeight, 0, 0);
        L.v("mHeaderViewMeasuredHeight:" + mHeaderViewMeasuredHeight);
        addHeaderView(mHeaderView);
    }

    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.layout_refresh_listview_footer, null);

        mFooterView.measure(0, 0);
        mFooterViewMeasuredHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);

        addFooterView(mFooterView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                moveY = ev.getY();

                if (mCurrentState == STATE_REFRESHING)
                    return super.onTouchEvent(ev);


                float offset = moveY - downY;

                //L.v("offset:" + offset + ",getFirstVisiblePosition:" + getFirstVisiblePosition());

                if (offset > 0 && getFirstVisiblePosition() == 0) {
                    mTopPadding = (int) (-mHeaderViewMeasuredHeight + offset);
                    mHeaderView.setPadding(0, mTopPadding, 0, 0);
                    if (mTopPadding >= 0 && mCurrentState != STATE_RELEASE_REFRESH) {// 全部显示
                        L.v("切换成 释放刷新");
                        mCurrentState = STATE_RELEASE_REFRESH;
                        updateRefrehState();
                    } else if (mTopPadding < 0 && mCurrentState != STATE_PULL_DOWN_TO_REFRESH) {//显示部分
                        L.v("切换成 下拉刷新");
                        mCurrentState = STATE_PULL_DOWN_TO_REFRESH;
                        updateRefrehState();
                    }
                    return true; // 当前事件被我们处理并消费 TODO 这句话很重要
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    mHeaderView.setPadding(0, 0, 0, 0);
                    mCurrentState = STATE_REFRESHING;
                    updateRefrehState();
                } else if (mCurrentState == STATE_PULL_DOWN_TO_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderViewMeasuredHeight, 0, 0);
                }


                break;
        }


        return super.onTouchEvent(ev);
    }

    /**
     * 更新刷新状态
     */
    private void updateRefrehState() {
        if (mCurrentState == STATE_REFRESHING) {//正在刷新
            mTvRefreshState.setText("正在刷新...");
            mPbLoading.setVisibility(VISIBLE);
            mIvArrow.clearAnimation();
            mIvArrow.setVisibility(INVISIBLE);
            if (mOnRefreshListener != null) {
                mOnRefreshListener.refresh();
            }

        } else if (mCurrentState == STATE_RELEASE_REFRESH) {//释放刷新
            mTvRefreshState.setText("释放刷新");
            mPbLoading.setVisibility(INVISIBLE);
            mIvArrow.setVisibility(VISIBLE);
            mIvArrow.startAnimation(rotateUp);
        } else if (mCurrentState == STATE_PULL_DOWN_TO_REFRESH) {//释放刷新
            mTvRefreshState.setText("下拉刷新");
            mPbLoading.setVisibility(INVISIBLE);
            mIvArrow.setVisibility(VISIBLE);
            mIvArrow.startAnimation(rotateDown);
        }
    }

    public void setRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public void setRefreshComplete() {
        if (isLoadMoreMode) {
            mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);
            isLoadMoreMode = false;
        } else {
            mCurrentState = STATE_PULL_DOWN_TO_REFRESH;
            mHeaderView.setPadding(0, -mHeaderViewMeasuredHeight, 0, 0);
            resetState();//重置HeaderView状态
        }

    }

    /**
     * 重置HeaderView状态
     */
    private void resetState() {
        mTvRefreshState.setText("下拉刷新");
        mPbLoading.setVisibility(INVISIBLE);
        mIvArrow.setVisibility(VISIBLE);
        mTvLastRefreshTime.setText(getTime());
    }

    private String getTime() {
        long timeMillis = System.currentTimeMillis();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeMillis);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (isLoadMoreMode)
            return;

        if (scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount() - 1) {
            isLoadMoreMode = true;
            L.v("加载更多...");
            mFooterView.setPadding(0, 0, 0, 0);
            setSelection(getCount());
            if (mOnRefreshListener != null) {
                mOnRefreshListener.loadMore();
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


    }


    public interface OnRefreshListener {
        void refresh();

        void loadMore();

    }
}
