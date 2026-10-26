package com.smmlaba.Entinity;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes11.dex */
public class newCustomer {

    @SerializedName("error")
    private int error;

    public newCustomer(int error) {
        this.error = error;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
