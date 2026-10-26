package com.smmlaba.Entinity;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes11.dex */
public class Version_base {

    @SerializedName("version")
    private double version;

    public Version_base(double version) {
        this.version = version;
    }

    public double getVersion() {
        return this.version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
