package com.smmlaba.Entinity.offer;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes9.dex */
public class message {

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("kof")
    private int kof;

    @SerializedName("lowerprice")
    private String lowerprice;

    @SerializedName("params")
    private List<params> params;

    @SerializedName("price")
    private int price;

    @SerializedName("product_id")
    private int product_id;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName(NotificationCompat.CATEGORY_SERVICE)
    private String service;
    private int total;

    public message(String category, String product_name, String service, int price, String description, String image, int product_id, List<params> params, String lowerprice) {
        this.category = category;
        this.product_name = product_name;
        this.service = service;
        this.price = price;
        this.description = description;
        this.image = image;
        this.product_id = product_id;
        this.params = params;
        this.lowerprice = lowerprice;
    }

    public int getKof() {
        return this.kof;
    }

    public void setKof(int kof) {
        this.kof = kof;
    }

    public String getLowerprice() {
        return this.lowerprice;
    }

    public void setLowerprice(String lowerprice) {
        this.lowerprice = lowerprice;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<params> getParams() {
        return this.params;
    }

    public void setParams(List<params> params) {
        this.params = params;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getValueByParam(String param) {
        String values = new String();
        for (params p : this.params) {
            if (p.getParam().equals(param)) {
                values = p.getValue();
            }
        }
        return values;
    }
}
