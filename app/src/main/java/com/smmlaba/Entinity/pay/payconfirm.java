package com.smmlaba.Entinity.pay;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes6.dex */
public class payconfirm {

    @SerializedName("confirmation_url")
    private String confirmation_url;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private String status;

    public payconfirm(String status, String confirmation_url) {
        this.status = status;
        this.confirmation_url = confirmation_url;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmation_url() {
        return this.confirmation_url;
    }

    public void setConfirmation_url(String confirmation_url) {
        this.confirmation_url = confirmation_url;
    }
}
