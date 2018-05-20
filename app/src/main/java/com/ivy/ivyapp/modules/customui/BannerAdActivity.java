package com.ivy.ivyapp.modules.customui;

import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/20.
 */

public class BannerAdActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.vp_banner)
    ViewPager mVpBanner;
    @BindView(R.id.tv_banner_desc)
    TextView mTvBannerDesc;
    @BindView(R.id.ll_points_container)
    LinearLayout mLlPointsContainer;

    int[] mImgIds;
    String[] mAdDesc;
    List<ImageView> mImageViewList;
    List<View> mPointViewList;
    boolean isDestory = false;
    int oriPosition = 1000000 * 5;// 轮播图初始值位置

    @Override
    protected int getContentView() {
        return R.layout.activity_banner_ad;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("轮播图广告");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mImgIds = new int[]{R.drawable.ic_banner_ad_a, R.drawable.ic_banner_ad_b, R.drawable.ic_banner_ad_c, R.drawable.ic_banner_ad_d, R.drawable.ic_banner_ad_e};
        mAdDesc = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };

        L.v("mImgIds:" + mImgIds.length);

        mImageViewList = new ArrayList<>();
        ImageView imageView;
        mPointViewList = new ArrayList<>();
        View point;
        for (int i = 0; i < mImgIds.length; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(mImgIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(imageView);

            point = new View(this);
            point.setTag(i);
            point.setBackgroundResource(R.drawable.selector_banner_point);
            point.setEnabled(false);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) point.getLayoutParams();// Caused by: java.lang.NullPointerException
//            layoutParams.height = 5;
//            layoutParams.weight = 5;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
                layoutParams.leftMargin = 20;
            point.setLayoutParams(layoutParams);
//            point.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    L.v("click:" + (int) v.getTag());
//                    mVpBanner.setCurrentItem(oriPosition + (int) v.getTag());//The specified child already has a parent. You must call removeView() on the
//                }
//            });
            mPointViewList.add(point);
            mLlPointsContainer.addView(point);
        }
        mPointViewList.get(0).setEnabled(true);
        mTvBannerDesc.setText(mAdDesc[0]);

        mVpBanner.setAdapter(new BannerAdapter());
        mVpBanner.setOnPageChangeListener(this);

        //or
//        int p = 10000000 * mImgIds.length;
        oriPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE % mImgIds.length - 1;// 轮播图初始值位置
        L.v("oriPosition：" + oriPosition);
        mVpBanner.setCurrentItem(oriPosition);

        // 自动轮播

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestory) {
                    SystemClock.sleep(2000);
//                    L.v("current item:" + mVpBanner.getCurrentItem());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVpBanner.setCurrentItem(mVpBanner.getCurrentItem() + 1);
                        }
                    });

                }
            }
        }).start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        // 修正
        position = position % mImgIds.length;
        mTvBannerDesc.setText(mAdDesc[position]);
        for (int i = 0; i < mPointViewList.size(); i++) {
            mPointViewList.get(i).setEnabled(i == position);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            return mImageViewList.size();

            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 修正
            position = position % mImgIds.length;
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }
    }


}
