package com.ivy.ivyapp.modules.dn.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/21.
 */

public class DnUiActivity extends BaseActivity {
    @BindView(R.id.lv_datas)
    ListView mLvDatas;

    private String[] datas = {"LayoutInflater源码", "RecyclerView", "DrawerLayout侧滑", "NavigationView标准侧滑", "SnackerBar", "SearchView", "Toolbar",
            "Palette", "沉浸式", "CardView", "FloatingActionBar", "CoordinatorLayout", "BehaviorTab", "SVG",

    };

    @Override
    protected int getContentView() {
        return R.layout.activity_dn_ui;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("DN_UI");
        mActionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.asList(datas));
        mLvDatas.setAdapter(adapter);
        mLvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = datas[position];
                if (text.contains("A")) {
//                    startActivity(new Intent(DnUiActivity.this, CustomUiActivity.class));
                } else if (text.contains("B")) {
//                    startActivity(new Intent(DnUiActivity.this, DnUiActivity.class));
                }
            }
        });
    }
}
