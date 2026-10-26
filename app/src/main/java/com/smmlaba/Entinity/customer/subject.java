package com.smmlaba.Entinity.customer;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes6.dex */
public class subject {

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("state")
    private int state;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private int status;

    @SerializedName("subject_id")
    private int subject_id;

    @SerializedName("title")
    private String title;

    public subject(int subject_id, String title, int state, String date_added, int status) {
        this.subject_id = subject_id;
        this.title = title;
        this.state = state;
        this.date_added = date_added;
        this.status = status;
    }

    public int getSubject_id() {
        return this.subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
