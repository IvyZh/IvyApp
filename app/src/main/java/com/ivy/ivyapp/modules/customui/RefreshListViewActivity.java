package com.ivy.ivyapp.modules.customui;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.modules.customui.ui.RefreshListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivy on 2018/5/20.
 * 下拉选择框
 */

public class RefreshListViewActivity extends BaseActivity {


    @BindView(R.id.lv_datas)
    RefreshListView mLvDatas;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_refresh_listview;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("下拉刷新ListView");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        final ArrayList<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("数据内容：" + i);
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas);
        mLvDatas.setAdapter(mAdapter);

        mLvDatas.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void refresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.add(0, "这是下拉刷新出来的数据");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mLvDatas.setRefreshComplete();
                            }
                        });
                    }

                }).start();

            }

            @Override
            public void loadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.add("这是上拉加载出来的数据");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mLvDatas.setRefreshComplete();
                            }
                        });
                    }

                }).start();
            }
        });
    }


}
