package com.smmlaba.Entinity.category;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes7.dex */
public class catalog_products {

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("image")
    private String image;

    @SerializedName("lowerprice")
    private String lowerprice;

    @SerializedName("model")
    private String model;

    @SerializedName("name")
    private String name;

    @SerializedName("product_id")
    private int product_id;

    public catalog_products(String name, String image, int product_id, int category_id, String model, String lowerprice) {
        this.name = name;
        this.image = image;
        this.product_id = product_id;
        this.category_id = category_id;
        this.model = model;
        this.lowerprice = lowerprice;
    }

    public String getLowerprice() {
        return this.lowerprice;
    }

    public void setLowerprice(String lowerprice) {
        this.lowerprice = lowerprice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
