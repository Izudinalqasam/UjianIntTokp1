package com.tokopedia.testproject.problems.news.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.helper.CommonHelper;
import com.tokopedia.testproject.problems.news.helper.ImageLoadingService;
import com.tokopedia.testproject.problems.news.model.Articles;
import com.tokopedia.testproject.problems.news.presenter.NewsPresenter;

import java.util.List;

import ss.com.bannerslider.Slider;

public class NewsActivity extends AppCompatActivity implements
        com.tokopedia.testproject.problems.news.presenter.NewsPresenter.View,
        View.OnClickListener, NewsPresenter.TopNews {

    private NewsPresenter newsPresenter;
    private NewsAdapter newsAdapter;

    private LinearLayout layEmptyData, layErrorData;
    private Button btnCobaLagi;
    private RecyclerView recyclerView;
    private ProgressBar pbNews;
    private Slider sliderBannerNews;
    private ImageView imgSearchNews;
    private EditText edtSearchNes;

    private final String KEYWORD_ANDROID = "android";
    private final String KEYWORD_ALL = "all";
    private final int DEFAULT_SIZE = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
    }

    private void initView(){
        newsPresenter = new NewsPresenter(this, this);
        newsAdapter = new NewsAdapter(null);

        layEmptyData = findViewById(R.id.lay_empty_data);
        layErrorData = findViewById(R.id.lay_error_data);
        btnCobaLagi = findViewById(R.id.btn__coba_lagi);
        recyclerView = findViewById(R.id.recyclerView);
        imgSearchNews = findViewById(R.id.img_search_news);
        edtSearchNes = findViewById(R.id.edt_serach_news);
        pbNews = findViewById(R.id.pb_news);
        sliderBannerNews = findViewById(R.id.slider_banner_news);

        recyclerView.setAdapter(newsAdapter);
        newsPresenter.getEverything(KEYWORD_ANDROID, DEFAULT_SIZE);
        newsPresenter.getTopHeadline(KEYWORD_ALL);
        btnCobaLagi.setOnClickListener(this);

        Slider.init(new ImageLoadingService(getBaseContext()));

        showPrograess();

        imgSearchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNews();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)){
                   loadMoreData();
                }
            }
        });
    }

    private void loadMoreData(){
        if (edtSearchNes.getText().toString() != "") {
            newsPresenter.getEverything(edtSearchNes.getText().toString(), 100);
            showProgressSearcAndloadMore();
            Toast.makeText(getBaseContext(), getResources().getString(R.string.load_more_text), Toast.LENGTH_SHORT).show();
        }else {
            newsPresenter.getEverything(KEYWORD_ANDROID, 100);
            Toast.makeText(getBaseContext(), getResources().getString(R.string.load_more_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchNews() {
        newsPresenter.getEverything(edtSearchNes.getText().toString(), 20);
        showProgressSearcAndloadMore();
    }

    @Override
    public void onSuccessGetNews(List<Articles> articleList) {
        if (articleList.size() > 0){
            newsPresenter.saveArticleToLocal(articleList);
            newsAdapter.setArticleList(articleList);
            newsAdapter.notifyDataSetChanged();
            showData();
        }else {
            showEmptySatae();
        }
    }

    @Override
    public void onSuccesGetLocalNews(List<Articles> articleList) {
        newsAdapter.setArticleList(articleList);
        newsAdapter.notifyDataSetChanged();
        showDataOffline();
    }

    @Override
    public void onErrorGetNews(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
        checkInetConnection();
    }

    private void checkInetConnection(){
        if (CommonHelper.getConnectivityStatus(getBaseContext()) == 0){
            newsPresenter.getArticleFromLocal();
        }else {
            showErrorState();
        }
    }

    private void showProgressSearcAndloadMore(){
        layEmptyData.setVisibility(View.GONE);
        layErrorData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        sliderBannerNews.setVisibility(View.VISIBLE);
        pbNews.setVisibility(View.VISIBLE);
        edtSearchNes.setVisibility(View.VISIBLE);
        imgSearchNews.setVisibility(View.VISIBLE);
    }

    private void showDataOffline(){
        layEmptyData.setVisibility(View.GONE);
        layErrorData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        pbNews.setVisibility(View.GONE);
        sliderBannerNews.setVisibility(View.GONE);
        edtSearchNes.setVisibility(View.GONE);
        imgSearchNews.setVisibility(View.GONE);
    }

    private void showErrorState(){
        layEmptyData.setVisibility(View.GONE);
        layErrorData.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        pbNews.setVisibility(View.GONE);
        sliderBannerNews.setVisibility(View.GONE);
        edtSearchNes.setVisibility(View.GONE);
        imgSearchNews.setVisibility(View.GONE);
    }

    private void showEmptySatae(){
        layEmptyData.setVisibility(View.VISIBLE);
        layErrorData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        pbNews.setVisibility(View.GONE);
        sliderBannerNews.setVisibility(View.GONE);
        edtSearchNes.setVisibility(View.GONE);
        imgSearchNews.setVisibility(View.GONE);
    }

    private void showData(){
        layEmptyData.setVisibility(View.GONE);
        layErrorData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        pbNews.setVisibility(View.GONE);
        sliderBannerNews.setVisibility(View.VISIBLE);
        edtSearchNes.setVisibility(View.VISIBLE);
        imgSearchNews.setVisibility(View.VISIBLE);
    }

    private void showPrograess(){
        layEmptyData.setVisibility(View.GONE);
        layErrorData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        sliderBannerNews.setVisibility(View.GONE);
        pbNews.setVisibility(View.VISIBLE);
        edtSearchNes.setVisibility(View.GONE);
        imgSearchNews.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        newsPresenter.getEverything(KEYWORD_ANDROID, DEFAULT_SIZE);
        showPrograess();
    }

    @Override
    public void onSuccessTopNews(List<Articles> topHeadLineArticle) {
        try {
            if (CommonHelper.getConnectivityStatus(getBaseContext()) != 0) {
                if (topHeadLineArticle.size() > 0){
                    sliderBannerNews.setAnimateIndicators(true);
                    sliderBannerNews.setInterval(5000);
                    sliderBannerNews.setAdapter(new SliderBannerNewsAdapter(topHeadLineArticle));
                }
            }
        }catch (Exception e){
            Log.d("NewsActivity", "onSuccessTopNews: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onErrorTopNews(Throwable throwable) {
        try {
            sliderBannerNews.setAdapter(new SliderBannerNewsAdapter(null));
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.d("NewsActivity", "onSuccessTopNews: " + e.getLocalizedMessage());
        }

    }
}
