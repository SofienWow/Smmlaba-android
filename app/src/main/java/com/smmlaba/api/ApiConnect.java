package com.smmlaba.api;

import android.os.AsyncTask;
import com.smmlaba.MainActivity;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import ru.yoomoney.sdk.auth.net.HttpHeaders;

/* JADX INFO: loaded from: classes5.dex */
public class ApiConnect extends AsyncTask<String, String, String> {
    private String Method;
    private String data;
    public Delay delegate;
    private final String error;
    public final String http;
    private int index;
    private int response_code;
    private String result;
    private final String servicelog;
    private final String servicepass;
    private String url;
    private final String USER_AGENT = "Mozilla/5.0";
    private String json = new String();
    private boolean type = false;

    public ApiConnect(String url, String data, Delay delegate) {
        this.Method = "GET";
        this.data = "";
        String strLeorio = common.leorio();
        this.error = strLeorio;
        this.delegate = null;
        this.http = "https://smmlaba.com/";
        this.response_code = 0;
        this.index = -1;
        this.servicelog = "applicationcall";
        this.servicepass = "appgetservice";
        String error_1 = common.kuroden();
        this.url = "https://smmlaba.com/api/v1/" + url + ".php?acces_token=" + strLeorio + error_1 + MainActivity.stringFromJNI();
        this.delegate = delegate;
        if (data.length() > 3) {
            this.Method = "POST";
            this.data = data;
        }
    }

    public void SetJson(String json) {
        this.json = json;
    }

    public String GetResult() {
        return this.result;
    }

    public void SetType(boolean type) {
        this.type = type;
    }

    public void SetIndex(int index) {
        this.index = index;
    }

    public String GetUrl() {
        return this.url;
    }

    public void SetUrl(String url) {
        this.url = url;
    }

    public void getProduct(String model) {
        this.url = "https://smmlaba.com/vkapi/v1/?username=applicationcall&apikey=appgetservice&action=services&service=" + model;
        this.Method = "POST";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(String... params) {
        this.result = "Ошибка соединения";
        try {
            URL obj = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(this.Method);
            con.setRequestProperty(HttpHeaders.USER_AGENT, "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestProperty(HttpHeaders.ACCEPT_LANGUAGE, "ru-RU,ru;q=0.9,en;q=0.8");
            if (this.Method.equals("POST") && this.data.length() > 3) {
                if (App.customer.getCustomer_id() == 0 && App.customer.getStatus() == 0) {
                    this.data += "&token=" + App.customer.getToken();
                }
                if (this.json.length() < 3) {
                    con.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("Content-Length", Integer.toString(this.data.length()));
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(this.data);
                    wr.flush();
                    wr.close();
                } else {
                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    try {
                        byte[] input = this.json.getBytes("utf-8");
                        os.write(input, 0, input.length);
                        if (os != null) {
                            os.close();
                        }
                    } catch (Throwable th) {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
            }
            this.response_code = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while (true) {
                String inputLine = in.readLine();
                if (inputLine == null) {
                    break;
                }
                response.append(inputLine);
            }
            in.close();
            this.result = response.toString();
        } catch (Exception e) {
        }
        return this.result;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String result) {
        if (this.delegate != null && Parser.isJSONValid(result)) {
            this.delegate.after(result);
            App.LoadingHide();
        }
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        App.LoadingShow();
        super.onPreExecute();
    }
}
