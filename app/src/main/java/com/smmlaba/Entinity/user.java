package com.smmlaba.Entinity;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes11.dex */
public class user {

    @SerializedName("approved")
    private int approved;

    @SerializedName("apptoken")
    private String apptoken;

    @SerializedName("customer_id")
    private int customer_id;

    @SerializedName("error")
    private int error;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("loginza2_identity")
    private String loginza2_identity;

    @SerializedName("loginza2_link")
    private String loginza2_link;

    @SerializedName("balance")
    private int points;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private int status;

    public user(int customer_id, String firstname, String lastname, String loginza2_identity, String loginza2_link, int status, int approved, int error, String apptoken, int points) {
        this.customer_id = customer_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.loginza2_identity = loginza2_identity;
        this.loginza2_link = loginza2_link;
        this.status = status;
        this.approved = approved;
        this.error = error;
        this.apptoken = apptoken;
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCustomer_id() {
        return this.customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLoginza2_identity() {
        return this.loginza2_identity;
    }

    public void setLoginza2_identity(String loginza2_identity) {
        this.loginza2_identity = loginza2_identity;
    }

    public String getLoginza2_link() {
        return this.loginza2_link;
    }

    public void setLoginza2_link(String loginza2_link) {
        this.loginza2_link = loginza2_link;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApproved() {
        return this.approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getApptoken() {
        return this.apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }
}
