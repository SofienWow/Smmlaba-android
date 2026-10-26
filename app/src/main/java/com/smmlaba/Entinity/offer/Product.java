package com.smmlaba.Entinity.offer;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes9.dex */
public class Product {

    @SerializedName("message")
    private List<message> message;

    @SerializedName("result")
    private String result;

    public Product(String result, List<message> message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<message> getMessage() {
        return this.message;
    }

    public void setMessage(List<message> message) {
        this.message = message;
    }
}
