package com.ivy.ivyapp.modules.doubanmovies.fragments;

import android.widget.ListView;

import com.ivy.commonlibrary.net.okhttp3.listener.CommonResponseListener;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.doubanmovies.adapter.MovieAdapter;
import com.ivy.ivyapp.modules.doubanmovies.domain.MoviesListData;
import com.ivy.ivyapp.net.RequestCenter;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class InTheatersHotFragment extends BaseFragment {
    @BindView(R.id.lv_datas)
    ListView mListView;

    private MoviesListData mDatas;

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_douban_movie_in_theaters;
    }

    @Override
    protected void loadData() {
        RequestCenter.getInTheaters("上海", new CommonResponseListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mDatas = (MoviesListData) responseObj;
                mListView.setAdapter(new MovieAdapter(getContext(), mDatas));
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }
}
