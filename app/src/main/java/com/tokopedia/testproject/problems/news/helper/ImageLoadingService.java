package com.tokopedia.testproject.problems.news.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoadingService implements ss.com.bannerslider.ImageLoadingService {
    public Context context;

    public ImageLoadingService (Context context){
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context).load(resource).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.placeholderOf(placeHolder))
                .apply(RequestOptions.errorOf(errorDrawable))
                .into(imageView);
    }
}
