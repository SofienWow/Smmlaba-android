package com.smmlaba.Entinity.order;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class order_product {

    @SerializedName("lowerprice")
    private String lowerprice;

    @SerializedName("model")
    private String model;

    @SerializedName("name")
    private String name;

    @SerializedName("option")
    private List<order_option> order_option;

    @SerializedName("price")
    private String price;

    @SerializedName("product_id")
    private int product_id;

    public order_product(String name, int product_id, String price, String model, List<order_option> order_option, String lowerprice) {
        this.name = name;
        this.product_id = product_id;
        this.price = price;
        this.model = model;
        this.order_option = order_option;
        this.lowerprice = lowerprice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<order_option> getOrder_option() {
        return this.order_option;
    }

    public String getLowerprice() {
        return this.lowerprice;
    }

    public void setLowerprice(String lowerprice) {
        this.lowerprice = lowerprice;
    }

    public void setOrder_option(List<order_option> order_option) {
        this.order_option = order_option;
    }
}
