package com.ivy.commonlibrary.image.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Ivy on 2018/5/14.
 */

public class GlideManager {


    private GlideManager() {
    }


    public static void display(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}
