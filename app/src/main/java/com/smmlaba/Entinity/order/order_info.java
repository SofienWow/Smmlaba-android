package com.smmlaba.Entinity.order;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class order_info {

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("products")
    private List<order_product> order_product;

    @SerializedName("order_status")
    private String order_status;

    @SerializedName("total")
    private String total;

    public order_info(String date_added, String order_status, String total, String order_id, List<order_product> order_product) {
        this.date_added = date_added;
        this.order_status = order_status;
        this.total = total;
        this.order_id = order_id;
        this.order_product = order_product;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getOrder_status() {
        return this.order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<order_product> getOrder_product() {
        return this.order_product;
    }

    public void setOrder_product(List<order_product> order_product) {
        this.order_product = order_product;
    }
}
