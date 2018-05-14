package com.ivy.ivyapp.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.commonlibrary.net.okhttp3.listener.CommonResponseListener;
import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.doubanmovies.activities.DoubanMovieMainActivity;
import com.ivy.ivyapp.net.RequestCenter;

import java.util.Arrays;

import butterknife.BindView;

//import com.ivy.commonlibrary.net.okhttp3.CommonOkHttpClient;

/**
 * Created by Ivy on 2018/5/11.
 */

public class O1Fragment extends BaseFragment {
    @BindView(R.id.lv_datas)
    ListView mLvDatas;

    private String[] datas = {"微博国际版", "酷安", "豆瓣", "豆瓣电影 v4.5.0", "get"};


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
                } else if (text.contains("get")) {
//                    RequestCenter.getBook(1220562, new CommonResponseListener() {
//                        @Override
//                        public void onSuccess(Object responseObj) {
//                            L.v("success" + responseObj.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Object reasonObj) {
//                            L.v("fail:" + reasonObj.toString());
//
//                        }
//                    });
                    RequestCenter.getInTheaters("北京", new CommonResponseListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            L.v("success:" + responseObj.toString());
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            L.v("fail:" + reasonObj.toString());
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void loadData() {

    }
}
