package com.smmlaba.system;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.smmlaba.Entinity.user;
import com.smmlaba.MainActivity;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Autorization;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes7.dex */
public class customer implements Delay {
    private int customer_id;
    private Autorization delegate = null;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private int points;
    private int status;
    private String token;

    public customer(int customer_id, String firstname, String lastname, int points, String email, String token, int status) {
        this.customer_id = customer_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.points = points;
        this.email = email;
        this.token = token;
        this.status = status;
    }

    public void Semafor(Autorization delegate) {
        this.delegate = delegate;
    }

    public boolean login(String email, String password) {
        this.password = password;
        this.email = email;
        this.delegate = null;
        this.token = password;
        this.customer_id = 0;
        ApiConnect p = new ApiConnect("customers/login", "email=" + email + "&password=" + password, this);
        p.execute(new String[0]);
        return false;
    }

    public void logout() {
        this.customer_id = 0;
        this.firstname = "";
        this.lastname = "";
        this.points = 0;
        this.email = "";
        this.token = "";
        this.status = 0;
        this.password = "";
        this.delegate = null;
        saveToken();
        MainActivity.log_out();
        App.cart.cart_logout();
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        if (json instanceof JSONObject) {
            user user = (user) gson.fromJson(output, user.class);
            if (user.getError() == 0) {
                this.customer_id = user.getCustomer_id();
                this.firstname = user.getFirstname();
                this.lastname = user.getLastname();
                this.token = user.getApptoken();
                this.status = user.getStatus();
                this.points = user.getPoints();
                saveToken();
                MainActivity.log_on();
            }
            Autorization autorization = this.delegate;
            if (autorization != null) {
                autorization.tryAutorization();
            }
        }
    }

    private void saveToken() {
        Context appContext = App.getAppContext();
        App.getAppContext();
        SharedPreferences pref = appContext.getSharedPreferences("smmlaba.com", 0);
        SharedPreferences.Editor editPref = pref.edit();
        editPref.putString("email", this.email);
        editPref.putString("password", this.password);
        editPref.commit();
    }

    public String getAppToken() {
        Context appContext = App.getAppContext();
        App.getAppContext();
        SharedPreferences pref = appContext.getSharedPreferences("smmlaba.com", 0);
        return pref.getString("email", "");
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

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
