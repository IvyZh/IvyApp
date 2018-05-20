package com.ivy.ivyapp.modules.customui;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/20.
 * 自定义开关ToggleView-继承View
 * 系统自带的是ToggleButton
 * <p>
 * 绘制是在onResume之后执行
 */

public class ToggleViewActivity extends BaseActivity {
    @BindView(R.id.toggleView)
    ToggleView mToggleView;

    @Override
    protected int getContentView() {
        return R.layout.activity_toggleview;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("自定义开关ToggleView");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToggleView.setSwithBackgroundResource(R.drawable.ic_switch_background);
        mToggleView.setSlideButtonResource(R.drawable.ic_slide_button);
        mToggleView.setState(true);
        mToggleView.setOnSwitchStateChange(new ToggleView.OnStateChangeListener() {

            @Override
            public void onStateChange(boolean state) {
                UIUtils.showToast("state:"+state);
            }
        });
    }


}
