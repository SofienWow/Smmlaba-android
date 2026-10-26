package com.smmlaba;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.google.android.gms.common.internal.ImagesContract;
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
public class product extends Fragment implements Delay {
    public static TextView ShowPrice;
    private ImageButton fav;
    private message message;
    private List<message> message_arr;
    private List<params> params;
    private Product product;
    private TextView product_name;
    private View v;
    public static int product_id = 0;
    public static int total = 0;
    public static int price = 0;
    public static int kof = 0;
    public static boolean posts = false;
    public static float count_posts = 0.0f;
    public static float count = 0.0f;
    private String model = new String();
    private Map<String, TextView> ValidationText = new HashMap();
    private int IndexCart = 0;
    private boolean f = true;
    private String image_url = "";
    private Map<String, ProductOptions> optionsAll = new HashMap();

    public static product newInstance() {
        product fragment = new product();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.product, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            product_id = bundle.getInt("product_id", 0);
            this.model = bundle.getString("model", "");
            this.IndexCart = bundle.getInt("CartIndex", -1);
        }
        if (product_id != 0 && this.model.length() > 0) {
            ApiConnect connect = new ApiConnect("", "", this);
            connect.getProduct(this.model);
            connect.execute(new String[0]);
        }
        App.flag = true;
        ((MainActivity) getActivity()).logo.setVisibility(4);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.bottom_icon), PorterDuff.Mode.SRC_ATOP);
        TextView price_to_show = (TextView) this.v.findViewById(R.id.price_balance);
        ShowPrice = (TextView) this.v.findViewById(R.id.price);
        if (product_id == 912) {
            price_to_show.setVisibility(0);
            ShowPrice.setVisibility(8);
            ShowPrice = price_to_show;
        }
        this.fav = (ImageButton) this.v.findViewById(R.id.favorite);
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        TextView txt = (TextView) this.v.findViewById(R.id.description);
        final TextView lowerprice = (TextView) this.v.findViewById(R.id.lowerprice);
        this.product_name = (TextView) this.v.findViewById(R.id.name_product);
        Button button = (Button) this.v.findViewById(R.id.sendCart);
        total = 0;
        price = 0;
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        TextView sendCart = (TextView) this.v.findViewById(R.id.sendCart);
        if (this.IndexCart != -1) {
            total = App.cart.getProducts().get(this.IndexCart).getMessage().get(0).getTotal();
            if (product_id == 912) {
                ShowPrice.setText(App.getAppContext().getString(R.string.total_balance) + " " + total + " " + App.getAppContext().getString(R.string.currency));
            } else {
                ShowPrice.setText(App.getAppContext().getString(R.string.total) + " " + total + " " + App.getAppContext().getString(R.string.currency));
            }
            sendCart.setText(App.getAppContext().getResources().getText(R.string.cart_edit));
        }
        if (json instanceof JSONObject) {
            Product product = (Product) gson.fromJson(output, Product.class);
            this.product = product;
            this.message_arr = product.getMessage();
            for (int i = 0; i < this.message_arr.size(); i++) {
                if (this.message_arr.get(i).getProduct_id() == product_id) {
                    this.message = this.message_arr.get(i);
                    break;
                }
            }
            message messageVar = this.message;
            if (messageVar != null) {
                this.params = messageVar.getParams();
                price = this.message.getPrice();
                kof = this.message.getKof();
                if (Build.VERSION.SDK_INT >= 24) {
                    txt.setText(Html.fromHtml(this.message.getDescription(), 63));
                } else {
                    txt.setText(Html.fromHtml(this.message.getDescription()));
                }
                this.product_name.setText(this.message.getProduct_name());
                lowerprice.setText(this.message.getLowerprice());
                this.image_url = new String(this.message.getImage());
                App.SetImage((ImageView) this.v.findViewById(R.id.pr), this.message.getImage());
                LinearLayout options = (LinearLayout) this.v.findViewById(R.id.options);
                for (int j = 0; j < this.params.size(); j++) {
                    TextView textView = new TextView(App.getAppContext());
                    textView.setText(this.params.get(j).getName() + " :");
                    textView.setTextSize(15.0f);
                    textView.setLayoutParams(common.linLayoutParam);
                    options.addView(textView);
                    ProductOptions productOptions = new ProductOptions(this.v, this.IndexCart);
                    if (this.params.get(j).getValues() != null) {
                        productOptions.SetText(this.params.get(j).getValues());
                    }
                    productOptions.generateData(this.params.get(j).getType(), options, this.params.get(j).getParam());
                    this.optionsAll.put(this.params.get(j).getParam(), productOptions);
                    TextView tex = new TextView(App.getAppContext());
                    tex.setTextSize(15.0f);
                    tex.setVisibility(8);
                    tex.setTextColor(App.getAppContext().getResources().getColor(R.color.red));
                    tex.setLayoutParams(common.linLayoutParam);
                    this.ValidationText.put(this.params.get(j).getParam(), tex);
                    options.addView(tex);
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
        button.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.product.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                for (int j2 = 0; j2 < product.this.params.size(); j2++) {
                    ProductOptions productOptions2 = (ProductOptions) product.this.optionsAll.get(((params) product.this.params.get(j2)).getParam());
                    ((params) product.this.params.get(j2)).setValue(productOptions2.getData(((params) product.this.params.get(j2)).getType()));
                }
                product.this.message.setParams(product.this.params);
                List<message> pop = new ArrayList<>();
                pop.add(product.this.message);
                product.this.product.setMessage(pop);
                if (!App.cart.add(product.this.product, product.this.ValidationText, product.this.IndexCart)) {
                    product.this.getActivity().onBackPressed();
                }
            }
        });
        Cursor c = App.db.getWritableDatabase().rawQuery("select * from favorite  where product_id = ?", new String[]{Integer.toString(product_id)});
        if (c.moveToFirst()) {
            this.f = true;
            fvimg(true);
        } else {
            this.f = false;
            fvimg(false);
        }
        c.close();
        App.db.close();
        this.fav.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.product.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (!product.this.f) {
                    ContentValues cv = new ContentValues();
                    cv.put("product_id", Integer.valueOf(com.smmlaba.product.product_id));
                    cv.put("model", product.this.model);
                    cv.put("name", String.valueOf(product.this.product_name.getText()));
                    cv.put(ImagesContract.URL, product.this.image_url);
                    cv.put("lowerprice", String.valueOf(lowerprice.getText()));
                    App.db.getWritableDatabase().insert("favorite", null, cv);
                    product productVar = product.this;
                    productVar.fvimg(productVar.f = true);
                } else {
                    App.db.getWritableDatabase().delete("favorite", "product_id=" + com.smmlaba.product.product_id, null);
                    product productVar2 = product.this;
                    productVar2.fvimg(productVar2.f = false);
                }
                App.db.close();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fvimg(boolean fs) {
        ImageButton imageButton;
        int i;
        if (fs) {
            imageButton = this.fav;
            i = R.drawable.star_on;
        } else {
            imageButton = this.fav;
            i = R.drawable.star_off;
        }
        imageButton.setImageResource(i);
    }
}
