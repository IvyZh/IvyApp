package com.ivy.ivyapp.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.MainActivity;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.doubanmovies.activities.DoubanMovieMainActivity;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class O1Fragment extends BaseFragment {
    @BindView(R.id.lv_datas)
    ListView mLvDatas;

    private String[] datas = {"微博国际版", "酷安", "豆瓣", "豆瓣电影 v4.5.0"};


    @Override
    protected int getContentView() {
        return R.layout.fragment_o1;
    }


    @Override
    protected void initView() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(datas));
        mLvDatas.setAdapter(adapter);
        mLvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = datas[position];
                if (text.contains("豆瓣电影")) {
                    startActivity(new Intent(getContext(), DoubanMovieMainActivity.class));
                }
            }
        });
    }


    @Override
    protected void loadData() {

    }
}
