package com.ivy.ivyapp.modules.customui;

import android.view.View;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.modules.customui.ui.SlidingMenuView;
import com.ivy.ivyapp.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/20.
 * 侧滑面板
 */

public class SlidingMenuActivity extends BaseActivity {

    @BindView(R.id.sliding_menuview)
    SlidingMenuView mSlidingMenuView;

    @Override
    protected int getContentView() {
        return R.layout.activity_slidingmenu;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("侧滑面板");
        mActionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("swith stata");
                mSlidingMenuView.swithStata();
            }
        });

    }


    public void onTabClick(View view) {
        UIUtils.showToast("click:" + view.getId());
    }


}
