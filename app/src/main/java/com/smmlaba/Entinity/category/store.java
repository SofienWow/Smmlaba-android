package com.smmlaba.Entinity.category;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes7.dex */
public class store {

    @SerializedName("catalog_layout")
    private List<catalog_layout> catalog_layout;

    @SerializedName("catalog_products")
    private List<catalog_products> catalog_products;

    @SerializedName("category_item")
    private List<category_item> category_item;

    public store(List<catalog_layout> catalog_layout, List<category_item> category_item, List<catalog_products> catalog_products) {
        this.catalog_layout = catalog_layout;
        this.category_item = category_item;
        this.catalog_products = catalog_products;
    }

    public List<catalog_layout> getCatalog_layout() {
        return this.catalog_layout;
    }

    public void setCatalog_layout(List<catalog_layout> catalog_layout) {
        this.catalog_layout = catalog_layout;
    }

    public List<category_item> getCategory_item() {
        return this.category_item;
    }

    public void setCategory_item(List<category_item> category_item) {
        this.category_item = category_item;
    }

    public List<catalog_products> getCatalog_products() {
        return this.catalog_products;
    }

    public void setCatalog_products(List<catalog_products> catalog_products) {
        this.catalog_products = catalog_products;
    }
}
