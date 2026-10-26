package com.smmlaba.Entinity.customer;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes6.dex */
public class balanceID {

    @SerializedName("customer_reward_id")
    private int customer_reward_id;

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("description")
    private String description;

    @SerializedName("points")
    private String points;

    public balanceID(int customer_reward_id, String description, String date_added, String points) {
        this.customer_reward_id = customer_reward_id;
        this.description = description;
        this.date_added = date_added;
        this.points = points;
    }

    public int getCustomer_reward_id() {
        return this.customer_reward_id;
    }

    public void setCustomer_reward_id(int customer_reward_id) {
        this.customer_reward_id = customer_reward_id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getPoints() {
        return this.points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
