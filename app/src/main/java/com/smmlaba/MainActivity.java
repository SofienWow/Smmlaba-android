package com.smmlaba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.smmlaba.Entinity.pay.payconfirm;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import com.smmlaba.system.Version;
import com.wang.avi.AVLoadingIndicatorView;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;
import ru.yoomoney.sdk.kassa.payments.Checkout;
import ru.yoomoney.sdk.kassa.payments.TokenizationResult;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.MockConfiguration;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.TestParameters;

/* JADX INFO: loaded from: classes9.dex */
public class MainActivity extends AppCompatActivity implements Delay {
    public static Context AppContext = null;
    private static final int REQUEST_CODE_TOKENIZE = 9958;
    public static AVLoadingIndicatorView avi;
    public static FrameLayout frame;
    public static LinearLayout logged;
    public static Bundle outBundle = new Bundle();
    private static TextView points;
    public static int price;
    public static Button registerbutton;
    private static TextView user;
    public String backStateName;
    private BottomNavigationView bottomNavigationView;
    public ImageView logo;
    public Toolbar myToolbar;
    private FragmentTransaction transaction;
    private FragmentManager manager = getSupportFragmentManager();
    private int order_id = 0;
    public int balance_id = 912;
    public String balance_model = "balanswallet";

    public static native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        this.myToolbar = toolbar;
        setSupportActionBar(toolbar);
        logged = (LinearLayout) findViewById(R.id.logged);
        points = (TextView) findViewById(R.id.points);
        user = (TextView) findViewById(R.id.user);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.logo = (ImageView) findViewById(R.id.logoimg);
        registerbutton = (Button) findViewById(R.id.new_customer);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        frame = (FrameLayout) findViewById(R.id.frame_layout);
        new Version();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        this.bottomNavigationView = bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: com.smmlaba.MainActivity.1
            @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_cart /* 2131361853 */:
                        selectedFragment = cart.newInstance();
                        break;
                    case R.id.action_category /* 2131361854 */:
                        selectedFragment = category.newInstance();
                        break;
                    case R.id.action_favorite /* 2131361858 */:
                        selectedFragment = favorite.newInstance();
                        break;
                    case R.id.action_news /* 2131361865 */:
                        selectedFragment = news.newInstance();
                        break;
                    case R.id.action_profile /* 2131361866 */:
                        if (App.customer.getCustomer_id() == 0 && App.customer.getStatus() == 0) {
                            selectedFragment = profile.newInstance();
                        } else {
                            selectedFragment = setting.newInstance();
                        }
                        break;
                }
                MainActivity.this.replaceFragment(selectedFragment);
                return true;
            }
        });
        LinearLayout logged2 = (LinearLayout) findViewById(R.id.logged);
        logged2.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.MainActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (!MainActivity.this.backStateName.contains("balance")) {
                    Bundle args = new Bundle();
                    args.putInt("product_id", MainActivity.this.balance_id);
                    args.putString("model", MainActivity.this.balance_model);
                    Fragment balanceFrag = product.newInstance();
                    balanceFrag.setArguments(args);
                    MainActivity.this.replaceFragment(balanceFrag);
                }
            }
        });
        replaceFragment(category.newInstance());
        App.initizlization();
    }

    public void new_customer(View v) {
        replaceFragment(register.newInstance());
    }

    public void exit_customer(View v) {
        if (this.backStateName.contains("setting")) {
            replaceFragment(profile.newInstance());
        }
        if (this.backStateName.contains("cart")) {
            replaceFragment(cart.newInstance());
        }
        App.customer.logout();
    }

    public void timeToStartCheckout(int price2, int order_id, boolean test) {
        this.order_id = order_id;
        price = price2;
        if (!test) {
            Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();
            paymentMethodTypes.add(PaymentMethodType.BANK_CARD);
            PaymentParameters paymentParameters = new PaymentParameters(new Amount(BigDecimal.valueOf(price2), Currency.getInstance("RUB")), ((Object) App.getAppContext().getResources().getText(R.string.product_name)) + "", ((Object) App.getAppContext().getResources().getText(R.string.order_info)) + " " + order_id, "YOOKASSA_SHOP_KEY_REMOVED", "YOOKASSA_SHOP_ID_REMOVED", SavePaymentMethod.OFF, paymentMethodTypes);
            Intent intent = Checkout.createTokenizeIntent(this, paymentParameters);
            startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
        }
        if (test) {
            PaymentParameters paymentParameters2 = new PaymentParameters(new Amount(BigDecimal.valueOf(price2), Currency.getInstance("RUB")), ((Object) App.getAppContext().getResources().getText(R.string.product_name)) + "", ((Object) App.getAppContext().getResources().getText(R.string.order_info)) + " " + order_id, "YOOKASSA_SHOP_KEY_REMOVED", "YOOKASSA_SHOP_ID_REMOVED", SavePaymentMethod.OFF);
            TestParameters testParameters = new TestParameters(true, true, new MockConfiguration(false, true, 5));
            Intent intent2 = Checkout.createTokenizeIntent(this, paymentParameters2, testParameters);
            startActivityForResult(intent2, REQUEST_CODE_TOKENIZE);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case -1:
                    TokenizationResult result = Checkout.createTokenizationResult(data);
                    ApiConnect api = new ApiConnect("orders/sdk", "order_id=" + this.order_id + "&payment_token=" + result.getPaymentToken() + "&amount=" + price, this);
                    api.execute(new String[0]);
                    break;
            }
        }
        if (requestCode == 1) {
            switch (resultCode) {
                case -1:
                    success_pay(this.order_id);
                    break;
                case 1:
                    App.makeToast(((Object) App.getAppContext().getResources().getText(R.string.wrong)) + "");
                    break;
            }
        }
    }

    public void replaceFragment(Fragment frag) {
        this.backStateName = frag.getClass().getName();
        NavigationPress();
        App.LoadingHide();
        boolean fragmentPopped = this.manager.popBackStackImmediate(this.backStateName, 0);
        if (!fragmentPopped && this.manager.findFragmentByTag(this.backStateName) == null) {
            FragmentTransaction fragmentTransactionBeginTransaction = this.manager.beginTransaction();
            this.transaction = fragmentTransactionBeginTransaction;
            fragmentTransactionBeginTransaction.replace(R.id.frame_layout, frag);
            this.transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(this.backStateName).commit();
        }
    }

    public static void log_on() {
        points.setText(" " + Integer.toString(App.customer.getPoints()) + " ");
        user.setText(" " + App.customer.getFirstname() + " ");
        logged.setVisibility(0);
        registerbutton.setVisibility(8);
    }

    public static void log_out() {
        logged.setVisibility(8);
        registerbutton.setVisibility(0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void NavigationPress() {
        if (App.flag) {
            this.logo.setVisibility(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            if (App.customer.getToken().length() < 5) {
                registerbutton.setVisibility(0);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        NavigationPress();
        if (this.manager.getBackStackEntryCount() > 1) {
            FragmentManager fragmentManager = this.manager;
            String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            boolean switch_screen = true;
            for (int i = 0; i < common.main_fragments.size(); i++) {
                if (tag.equals(common.main_fragments.get(i))) {
                    switch_screen = false;
                    break;
                }
            }
            if (switch_screen) {
                FragmentManager fragmentManager2 = this.manager;
                this.backStateName = fragmentManager2.getBackStackEntryAt(fragmentManager2.getBackStackEntryCount() - 2).getName();
                super.onBackPressed();
                return;
            }
            finish();
            return;
        }
        finish();
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Gson gson = new Gson();
        if (Parser.JsonValidate(output) instanceof JSONObject) {
            payconfirm ppp = (payconfirm) gson.fromJson(output, payconfirm.class);
            switch (ppp.getStatus()) {
                case "pending":
                    Intent intent = Checkout.create3dsIntent(this, ppp.getConfirmation_url());
                    startActivityForResult(intent, 1);
                    break;
                case "success":
                    success_pay(this.order_id);
                    break;
                case "canceled":
                    App.makeToast(((Object) App.getAppContext().getResources().getText(R.string.wrong)) + "");
                    break;
            }
        }
    }

    public void success_pay(int order_id) {
        App.cart.clear();
        App.makeToast(((Object) App.getAppContext().getResources().getText(R.string.order_info)) + " " + order_id + " " + ((Object) App.getAppContext().getResources().getText(R.string.complete)));
        new Fragment();
        Fragment fragment = success_pay.newInstance();
        Bundle args = new Bundle();
        args.putInt("order_id", order_id);
        fragment.setArguments(args);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() { // from class: com.smmlaba.MainActivity.3
            @Override // java.lang.Runnable
            public void run() {
                App.log_in();
            }
        }, 2000L);
        replaceFragment(fragment);
    }
}
