package com.tokopedia.testproject.problems.news.presenter;

import android.util.Log;

import com.tokopedia.testproject.problems.news.model.Articles;
import com.tokopedia.testproject.problems.news.model.NewResult;
import com.tokopedia.testproject.problems.news.network.NewsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hendry on 27/01/19.
 */
public class NewsPresenter {

    private final String METHOD_ORDER = "publishedAt";

    private CompositeDisposable composite = new CompositeDisposable();
    private Realm realm = Realm.getDefaultInstance();

    private View view;
    private TopNews topNews;

    public interface View {
        void onSuccessGetNews(List<Articles> articleList);

        void onSuccesGetLocalNews(List<Articles> articleList);

        void onErrorGetNews(Throwable throwable);
    }

    public interface TopNews {
        void onSuccessTopNews(List<Articles> topHeadLineArticle);

        void onErrorTopNews(Throwable throwable);
    }

    public NewsPresenter(NewsPresenter.View view, TopNews topNews) {
        this.view = view;
        this.topNews = topNews;
    }

    public void getEverything(String keyword, int size) {
        NewsDataSource.getService().getEverything(keyword, size, METHOD_ORDER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onNext(NewResult newResult) {
                        view.onSuccessGetNews(newResult.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorGetNews(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTopHeadline(String keyword){
        NewsDataSource.getService().getTopHeadLine(keyword, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onNext(NewResult newResult) {
                        topNews.onSuccessTopNews(newResult.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        topNews.onErrorTopNews(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void saveArticleToLocal(final List<Articles> articles){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(Articles.class);

                    for (Articles a: articles){
                        realm.insert(a);
                    }
                }
            });
        }catch (Exception e){
            String error = e.getLocalizedMessage();
            if (realm != null) {
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
            }
        }

    }

    public void getArticleFromLocal(){
        List<Articles> listArticle = new ArrayList<>();
        try {
            listArticle = realm.where(Articles.class).findAll();
        }catch (Exception e){
            Log.d("NewsPresenter", e.getLocalizedMessage());
        }

        view.onSuccesGetLocalNews(listArticle);
    }

    public void unsubscribe() {
        composite.dispose();
    }
}
