package com.ivy.ivyapp.modules.customui;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.utils.UIUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/5/20.
 * 下拉选择框
 */

public class SpinnerActivity extends BaseActivity {


    @BindView(R.id.et_content_spinner)
    EditText mEtContentSpinner;
    @BindView(R.id.ib_down_spinner)
    ImageButton mIbDownSpinner;

    List<String> mDatas;
    private PopupWindow mPopupWindow;
    private MyAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_spinner;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("下拉选择框");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        initDatas();
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("10000" + i);
        }
    }


    @OnClick(R.id.ib_down_spinner)
    public void onViewClicked() {

        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }

        ListView listView = new ListView(this);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        listView.setDividerHeight(0);
        listView.setBackgroundResource(R.drawable.ic_spinner_listview_bg);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEtContentSpinner.setText(mDatas.get(position));
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(listView, mEtContentSpinner.getWidth(), 300);
        mPopupWindow.setOutsideTouchable(true);//设置点击外部消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//如果不设置，点击外部不消失
        mPopupWindow.setFocusable(true);//设置完这个属性之后，点击item之后才会有点击按下的背景
        mPopupWindow.showAsDropDown(mEtContentSpinner, 0, 0);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(SpinnerActivity.this, R.layout.item_pull_down_list_spinner, null);
                holder = new ViewHolder();
                holder.tvNumber = convertView.findViewById(R.id.tv_number_listview_item_spinner);
                holder.ibDelete = convertView.findViewById(R.id.ib_delete_listview_item_spinner);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //UIUtils.showToast("click:" + mDatas.get(position));
                    mDatas.remove(position);
                    mAdapter.notifyDataSetChanged();
                    if (mDatas.size() == 0) {
                        mPopupWindow.dismiss();
                    }

                }
            });

            holder.tvNumber.setText(mDatas.get(position));
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tvNumber;
        ImageButton ibDelete;
    }
}
