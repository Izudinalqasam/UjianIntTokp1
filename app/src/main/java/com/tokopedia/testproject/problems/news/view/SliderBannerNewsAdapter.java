package com.tokopedia.testproject.problems.news.view;

import com.tokopedia.testproject.problems.news.model.Articles;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class SliderBannerNewsAdapter extends SliderAdapter {

    List<Articles> imgUrl;

    public SliderBannerNewsAdapter(List<Articles> url){
        imgUrl = url;
    }

    @Override
    public int getItemCount() {
        return imgUrl != null ? imgUrl.size() : 0;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(imgUrl.get(position).getUrlToImage());
    }
}
