package com.smmlaba.Entinity.news;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes3.dex */
public class item {

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("description")
    private String description;

    @SerializedName("news_id")
    private int news_id;

    @SerializedName("title")
    private String title;

    public item(int news_id, String date_added, String title, String description) {
        this.news_id = news_id;
        this.date_added = date_added;
        this.title = title;
        this.description = description;
    }

    public int getNews_id() {
        return this.news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
