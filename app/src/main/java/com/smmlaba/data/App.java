package com.smmlaba.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;
import com.smmlaba.MainActivity;
import com.smmlaba.R;
import com.smmlaba.system.cart;
import com.smmlaba.system.customer;
import com.squareup.picasso.Picasso;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.jose4j.keys.AesKey;

/* JADX INFO: loaded from: classes10.dex */
public abstract class App extends Application {
    public static cart cart;
    public static customer customer;
    public static db db;
    public static boolean flag = false;
    public static boolean flag_register = false;

    public static void initizlization() {
        cart = new cart();
        db = new db(getAppContext());
        log_in();
    }

    public static void log_in() {
        Context appContext = getAppContext();
        getAppContext();
        SharedPreferences pref = appContext.getSharedPreferences("smmlaba.com", 0);
        String em = pref.getString("email", "");
        String ps = pref.getString("password", "");
        customer = new customer(0, "", "", 0, "", "", 0);
        if (em.length() > 3 && ps.length() > 5) {
            customer.login(em, ps);
        }
    }

    public static LayerDrawable getBorders(int bgColor, int borderColor, int left, int top, int right, int bottom) {
        ColorDrawable borderColorDrawable = new ColorDrawable(bgColor);
        ColorDrawable backgroundColorDrawable = new ColorDrawable(borderColor);
        Drawable[] drawables = {borderColorDrawable, backgroundColorDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        layerDrawable.setLayerInset(1, left, top, right, bottom);
        return layerDrawable;
    }

    public static Context getAppContext() {
        return MainActivity.AppContext;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NullPointerException e) {
            return false;
        } catch (NumberFormatException e2) {
            return false;
        }
    }

    public static int Convertdp(int px) {
        float scale = getAppContext().getResources().getDisplayMetrics().density;
        int padding_in_px = (int) ((6 * scale) + 0.5f);
        return padding_in_px;
    }

    public static int convertDpToPixels(float dp) {
        int px = (int) TypedValue.applyDimension(1, dp, getAppContext().getResources().getDisplayMetrics());
        return px;
    }

    public static void SetImage(ImageView img, String link) {
        String path = common.httpImage + link;
        if (link.contains(common.store)) {
            path = link;
        }
        Picasso.get().load(path).error(R.drawable.ic_success).into(img);
    }

    public static String CustomEncode(String str) {
        try {
            return URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static void LoadingShow() {
        MainActivity.avi.setVisibility(0);
        MainActivity.frame.setVisibility(4);
    }

    public static void LoadingHide() {
        MainActivity.avi.setVisibility(8);
        MainActivity.frame.setVisibility(0);
    }

    public static void makeToast(String message) {
        Toast toast = Toast.makeText(getAppContext(), message, 1);
        toast.show();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static final String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(aMessageDigest & UByte.MAX_VALUE);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 15;
            int two_halfs = 0;
            while (true) {
                buf.append((char) ((halfbyte < 0 || halfbyte > 9) ? (halfbyte - 10) + 97 : halfbyte + 48));
                halfbyte = b & 15;
                int two_halfs2 = two_halfs + 1;
                if (two_halfs >= 1) {
                    break;
                }
                two_halfs = two_halfs2;
            }
        }
        return buf.toString();
    }

    private static byte[] encryptxor(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AesKey.ALGORITHM);
        Cipher cipher = Cipher.getInstance(AesKey.ALGORITHM);
        cipher.init(1, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decryptxor(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AesKey.ALGORITHM);
        Cipher cipher = Cipher.getInstance(AesKey.ALGORITHM);
        cipher.init(2, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
}
