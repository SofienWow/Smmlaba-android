package com.smmlaba.Entinity.customer;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes6.dex */
public class orders {

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("name")
    private String name;

    @SerializedName("order_id")
    private int order_id;

    @SerializedName("total")
    private String total;

    public orders(int order_id, String name, String date_added, String total) {
        this.order_id = order_id;
        this.name = name;
        this.date_added = date_added;
        this.total = total;
    }

    public int getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
