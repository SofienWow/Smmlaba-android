package com.smmlaba;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.Entinity.order.order_option;
import com.smmlaba.Entinity.order.order_product;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class order_info extends Fragment implements Delay {
    private List<Product> products = new ArrayList();
    private LinearLayout table;
    private View v;

    public static order_info newInstance() {
        order_info fragment = new order_info();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.order_info, container, false);
        this.v = viewInflate;
        this.table = (LinearLayout) viewInflate.findViewById(R.id.table);
        Bundle bundle = getArguments();
        int order_id = bundle.getInt("order_id", 0);
        ApiConnect api = new ApiConnect("orders/order", "id=" + order_id, this);
        api.execute(new String[0]);
        App.flag = true;
        ((MainActivity) getActivity()).logo.setVisibility(4);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.bottom_icon), PorterDuff.Mode.SRC_ATOP);
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        if (json instanceof JSONObject) {
            com.smmlaba.Entinity.order.order_info o = (com.smmlaba.Entinity.order.order_info) gson.fromJson(output, com.smmlaba.Entinity.order.order_info.class);
            new ArrayList();
            new ArrayList();
            List<order_product> product = o.getOrder_product();
            TextView order_id = (TextView) this.v.findViewById(R.id.id_order);
            TextView date_order = (TextView) this.v.findViewById(R.id.date_order);
            TextView order_status = (TextView) this.v.findViewById(R.id.order_status);
            order_id.setText(App.getAppContext().getString(R.string.order_info) + " " + o.getOrder_id());
            date_order.setText(App.getAppContext().getString(R.string.date_added) + " " + o.getDate_added());
            order_status.setText(App.getAppContext().getString(R.string.status) + " " + o.getOrder_status());
            int i = 0;
            while (i < product.size()) {
                LinearLayout row = new LinearLayout(getActivity());
                row.setLayoutParams(common.linLayoutParam);
                row.setBackground(ContextCompat.getDrawable(App.getAppContext(), R.drawable.rectangle));
                row.setOrientation(1);
                TextView txt = new TextView(App.getAppContext());
                txt.setTextAppearance(App.getAppContext(), R.style.header);
                txt.setText(product.get(i).getName());
                Object json2 = json;
                Gson gson2 = gson;
                com.smmlaba.Entinity.order.order_info o2 = o;
                txt.setPadding(App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f));
                txt.setLayoutParams(common.linLayoutParam);
                row.addView(txt);
                List<order_option> option = product.get(i).getOrder_option();
                for (int j = 0; j < option.size(); j++) {
                    TextView txt2 = new TextView(App.getAppContext());
                    txt2.setTextAppearance(App.getAppContext(), R.style.just_text);
                    txt2.setLayoutParams(common.linLayoutParam);
                    String sourceString = "- <b>" + option.get(j).getName() + "</b> : " + option.get(j).getValue();
                    txt2.setText(Html.fromHtml(sourceString));
                    txt2.setPadding(App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f));
                    row.addView(txt2);
                }
                TextView txt3 = new TextView(App.getAppContext());
                txt3.setTextAppearance(App.getAppContext(), R.style.header);
                txt3.setPadding(App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f));
                txt3.setText(App.getAppContext().getString(R.string.total_cart) + ": " + product.get(i).getPrice() + " " + App.getAppContext().getString(R.string.currency));
                row.addView(txt3);
                this.table.addView(row);
                i++;
                json = json2;
                gson = gson2;
                o = o2;
            }
        }
    }
}
