package com.ivy.ivyapp.modules.customui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.modules.doubanmovies.activities.DoubanMovieMainActivity;
import com.ivy.ivyapp.utils.UIUtils;

import java.util.Arrays;

import butterknife.BindView;

import static com.ivy.ivyapp.app.MyApplication.getContext;

/**
 * Created by Ivy on 2018/5/17.
 */

public class CustomUiActivity extends BaseActivity {

    @BindView(R.id.lv_datas)
    ListView mLvDatas;
    private String[] datas = {"优酷菜单", "轮播图广告", "下拉选择框", "自定义开关", "下拉刷新ListView", "侧滑面板"};


    @Override
    protected int getContentView() {
        return R.layout.activity_costomui;
    }

    @Override
    protected void initView() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("自定义控件");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.asList(datas));
        mLvDatas.setAdapter(adapter);
        mLvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = datas[position];
                UIUtils.showToast(text);
                if (text.contains("优酷菜单")) {
                    startActivity(new Intent(CustomUiActivity.this, YouKuMenuActivity.class));
                } else if (text.contains("轮播图广告")) {
                    startActivity(new Intent(CustomUiActivity.this, BannerAdActivity.class));
                } else if (text.contains("下拉选择框")) {
                    startActivity(new Intent(CustomUiActivity.this, SpinnerActivity.class));
                } else if (text.contains("自定义开关")) {
                    startActivity(new Intent(CustomUiActivity.this, ToggleViewActivity.class));
                } else if (text.contains("下拉刷新ListView")) {
                    startActivity(new Intent(CustomUiActivity.this, RefreshListViewActivity.class));
                } else if (text.contains("侧滑面板")) {
                    startActivity(new Intent(CustomUiActivity.this, SlidingMenuActivity.class));
                }

            }
        });
    }
}
