package com.smmlaba.Entinity.category;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes7.dex */
public class category_item {

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("menu_icon")
    private String menu_icon;

    @SerializedName("name")
    private String name;

    public category_item(String name, String menu_icon, String description, int category_id) {
        this.name = name;
        this.menu_icon = menu_icon;
        this.category_id = category_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenu_icon() {
        return this.menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
