package com.ivy.ivyapp.modules.doubanmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.modules.doubanmovies.domain.MoviesListData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivy on 2018/5/14.
 */

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private MoviesListData mDatas;
    LayoutInflater mInflater;

    public MovieAdapter(Context context, MoviesListData datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDatas.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.getSubjects().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_douban_in_theaters_movie, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MoviesListData.SubjectsBean bean = mDatas.getSubjects().get(position);
        holder.mTvTitle.setText(bean.getTitle());
        holder.mTvDirectors.setText("导演：" + bean.getDirectors().get(0).getName());
        if (bean.getCasts().size() > 0) {
            holder.mTvCasts.setText("主演：" + bean.getCasts().get(0).getName());
        } else {
            holder.mTvCasts.setText("主演：???");
        }

        holder.mRbRating.setMax(bean.getRating().getMax() * 10);
        holder.mRbRating.setProgress((int) (bean.getRating().getAverage() * 10.0));
        holder.mTvRating.setText(bean.getRating().getAverage() + "");
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mIvCover;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.rb_rating)
        RatingBar mRbRating;
        @BindView(R.id.tv_rating)
        TextView mTvRating;
        @BindView(R.id.tv_directors)
        TextView mTvDirectors;
        @BindView(R.id.tv_casts)
        TextView mTvCasts;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
