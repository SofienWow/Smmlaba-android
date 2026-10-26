package com.smmlaba.Entinity.pay;

import com.google.gson.annotations.SerializedName;
import com.smmlaba.Entinity.offer.Product;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class payment {

    @SerializedName("email")
    private String email;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("products")
    private List<Product> products;

    @SerializedName("reward")
    private int reward;

    @SerializedName("sub_total")
    private int sub_total;

    @SerializedName("token")
    private String token;

    @SerializedName("total")
    private int total;

    public payment(List<Product> products, String email, String token, int sub_total, int total, int reward, String firstname) {
        this.products = new ArrayList();
        this.products = products;
        this.email = email;
        this.token = token;
        this.sub_total = sub_total;
        this.total = total;
        this.reward = reward;
        this.firstname = firstname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSub_total() {
        return this.sub_total;
    }

    public void setSub_total(int sub_total) {
        this.sub_total = sub_total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReward() {
        return this.reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
