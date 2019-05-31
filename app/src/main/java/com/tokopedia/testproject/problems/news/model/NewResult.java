package com.tokopedia.testproject.problems.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewResult {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("totalResults")
    @Expose
    int totalResults;
    @SerializedName("articles")
    @Expose
    List<Articles> articles= null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
