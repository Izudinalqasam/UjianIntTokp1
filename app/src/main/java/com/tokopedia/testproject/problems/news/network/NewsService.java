package com.tokopedia.testproject.problems.news.network;

import com.tokopedia.testproject.problems.news.model.NewResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("everything")
    Observable<NewResult> getEverything(@Query("q") String query, @Query("pageSize") int size, @Query("sortBy") String method);

    @GET("top-headlines")
    Observable<NewResult> getTopHeadLine(@Query("q") String query, @Query("pageSize") int size);
}
