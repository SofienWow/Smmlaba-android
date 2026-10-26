package com.smmlaba.Entinity.category;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes7.dex */
public class catalog_layout {

    @SerializedName("category_id")
    private Integer category_id;

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("sort_order")
    private Integer sort_order;

    void category(Integer category_id, Integer sort_order, String name, String image) {
        this.category_id = category_id;
        this.sort_order = sort_order;
        this.name = name;
        this.image = image;
    }

    public Integer getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getSort_order() {
        return this.sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
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
}
