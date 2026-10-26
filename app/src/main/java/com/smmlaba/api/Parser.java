package com.smmlaba.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Parser {
    public static Object JsonValidate(String data) {
        try {
            Object json = new JSONTokener(data).nextValue();
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
            return true;
        } catch (JSONException e) {
            try {
                new JSONArray(test);
                return true;
            } catch (JSONException e2) {
                return false;
            }
        }
    }
}
