package com.smmlaba.data;

import android.widget.LinearLayout;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes10.dex */
public final class common {
    public static final String pack = "com.smmlaba.";
    public static Integer main_catalog_count = 3;
    public static LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(-2, -1);
    public static String httpImage = "https://smmlaba.com/image/";
    public static String store = "https://smmlaba.com/";
    public static String key = "sdopofpf5wer98fERwrdf54f8eRer54";
    public static final List<String> main_fragments = Arrays.asList("com.smmlaba.category", "com.smmlaba.cart", "com.smmlaba.favorite", "com.smmlaba.news", "com.smmlaba.profile", "com.smmlaba.setting");
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", 2);

    public static native String kuroden();

    public static native String leorio();

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
