package com.smmlaba;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.Entinity.offer.message;
import com.smmlaba.Entinity.offer.params;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import com.smmlaba.system.ProductOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class moneypay extends Fragment implements Delay {
    public static TextView ShowPrice;
    private ImageButton fav;
    private message message;
    private List<message> message_arr;
    private List<params> params;
    private Product product;
    private TextView product_name;
    private View v;
    public static int total = 0;
    public static int price = 0;
    public static int kof = 0;
    private String model = new String();
    private int product_id = 0;
    private Map<String, TextView> ValidationText = new HashMap();
    private int IndexCart = 0;
    private boolean f = true;
    private String image_url = "";
    private Map<String, ProductOptions> optionsAll = new HashMap();

    public static moneypay newInstance() {
        moneypay fragment = new moneypay();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.product, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.product_id = bundle.getInt("product_id", 0);
            this.model = bundle.getString("model", "");
            this.IndexCart = bundle.getInt("CartIndex", -1);
        }
        if (this.product_id != 0 && this.model.length() > 0) {
            ApiConnect connect = new ApiConnect("", "", this);
            connect.getProduct(this.model);
            connect.execute(new String[0]);
        }
        App.flag = true;
        ((MainActivity) getActivity()).logo.setVisibility(4);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.bottom_icon), PorterDuff.Mode.SRC_ATOP);
        ShowPrice = (TextView) this.v.findViewById(R.id.price);
        ImageButton imageButton = (ImageButton) this.v.findViewById(R.id.favorite);
        this.fav = imageButton;
        imageButton.setVisibility(8);
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        TextView txt = (TextView) this.v.findViewById(R.id.description);
        this.product_name = (TextView) this.v.findViewById(R.id.name_product);
        Button button = (Button) this.v.findViewById(R.id.sendCart);
        total = 0;
        price = 0;
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        TextView sendCart = (TextView) this.v.findViewById(R.id.sendCart);
        if (this.IndexCart != -1) {
            total = App.cart.getProducts().get(this.IndexCart).getMessage().get(0).getTotal();
            sendCart.setText(App.getAppContext().getResources().getText(R.string.cart_edit));
        }
        if (json instanceof JSONObject) {
            Product product = (Product) gson.fromJson(output, Product.class);
            this.product = product;
            this.message_arr = product.getMessage();
            for (int i = 0; i < this.message_arr.size(); i++) {
                if (this.message_arr.get(i).getProduct_id() == this.product_id) {
                    this.message = this.message_arr.get(i);
                    break;
                }
            }
            message messageVar = this.message;
            if (messageVar != null) {
                this.params = messageVar.getParams();
                price = this.message.getPrice();
                kof = this.message.getKof();
                total = this.message.getPrice();
                ShowPrice.setText(App.getAppContext().getString(R.string.total) + " " + total + " " + App.getAppContext().getString(R.string.currency));
                if (Build.VERSION.SDK_INT >= 24) {
                    txt.setText(Html.fromHtml(this.message.getDescription(), 63));
                } else {
                    txt.setText(Html.fromHtml(this.message.getDescription()));
                }
                this.product_name.setText(this.message.getProduct_name());
                this.image_url = new String(this.message.getImage());
                App.SetImage((ImageView) this.v.findViewById(R.id.pr), this.message.getImage());
                LinearLayout options = (LinearLayout) this.v.findViewById(R.id.options);
                for (int j = 0; j < this.params.size(); j++) {
                    TextView textView = new TextView(App.getAppContext());
                    textView.setText(this.params.get(j).getName() + " :");
                    textView.setTextSize(15.0f);
                    textView.setLayoutParams(common.linLayoutParam);
                    options.addView(textView);
                }
            } else {
                button.setVisibility(8);
                ShowPrice.setVisibility(8);
                txt.setVisibility(8);
                ImageView product_img = (ImageView) this.v.findViewById(R.id.pr);
                product_img.setVisibility(8);
                this.product_name.setText(App.getAppContext().getString(R.string.not_found));
            }
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.moneypay.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                List<message> pop = new ArrayList<>();
                pop.add(moneypay.this.message);
                moneypay.this.product.setMessage(pop);
                if (!App.cart.add(moneypay.this.product, moneypay.this.ValidationText, moneypay.this.IndexCart)) {
                    moneypay.this.getActivity().onBackPressed();
                }
            }
        });
    }
}
