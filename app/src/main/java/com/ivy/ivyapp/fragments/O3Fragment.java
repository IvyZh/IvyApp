package com.ivy.ivyapp.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.customui.CustomUiActivity;
import com.ivy.ivyapp.modules.dn.ui.DnUiActivity;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class O3Fragment extends BaseFragment {
    @BindView(R.id.lv_datas)
    ListView mLvDatas;

    private String[] datas = {"权限", "", "C", "D"};


    @Override
    protected int getContentView() {
        return R.layout.fragment_o3;
    }


    @Override
    protected void initView() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(datas));
        mLvDatas.setAdapter(adapter);
        mLvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = datas[position];
                if (text.contains("权限")) {
                    startActivity(new Intent(getContext(), CustomUiActivity.class));
                } else if (text.contains("DN_UI")) {
                    startActivity(new Intent(getContext(), DnUiActivity.class));
                }
            }
        });
    }


    @Override
    protected void loadData() {

    }
}
