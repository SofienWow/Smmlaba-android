package com.smmlaba;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.category.catalog_products;
import com.smmlaba.Entinity.category.category_item;
import com.smmlaba.adapters.ProductItemAdapter;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes9.dex */
public class balance extends Fragment implements Delay, AdapterView.OnItemSelectedListener {
    private ProductItemAdapter Gridadapter;
    private ApiConnect catalog;
    private GridView gridView;
    private LinearLayout linLayout;
    private List<catalog_products> products;
    private String response;
    private View v;

    public static balance newInstance() {
        balance fragment = new balance();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.category, container, false);
        ApiConnect apiConnect = new ApiConnect("product/balance", "", this);
        this.catalog = apiConnect;
        apiConnect.execute(new String[0]);
        App.flag = true;
        ((MainActivity) getActivity()).logo.setVisibility(4);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.bottom_icon), PorterDuff.Mode.SRC_ATOP);
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String str) {
        Object json = Parser.JsonValidate(str);
        this.response = str;
        Gson gson = new Gson();
        this.products = new ArrayList();
        this.linLayout = (LinearLayout) this.v.findViewById(R.id.screen);
        Spinner mySpinner = (Spinner) this.v.findViewById(R.id.spinner);
        this.gridView = (GridView) this.v.findViewById(R.id.grid);
        if (json instanceof JSONArray) {
            catalog_products[] prod = (catalog_products[]) gson.fromJson(this.response, catalog_products[].class);
            this.products = new ArrayList(Arrays.asList(prod));
            this.linLayout.setVisibility(8);
            mySpinner.setVisibility(8);
            TextView filter = (TextView) this.v.findViewById(R.id.filter);
            filter.setVisibility(8);
        }
        this.gridView.setNumColumns(common.main_catalog_count.intValue());
        ProductItemAdapter productItemAdapter = new ProductItemAdapter(this.products, App.getAppContext());
        this.Gridadapter = productItemAdapter;
        this.gridView.setAdapter((ListAdapter) productItemAdapter);
        ViewGroup.LayoutParams layoutParams = this.gridView.getLayoutParams();
        layoutParams.height = App.convertDpToPixels(600.0f);
        this.gridView.setLayoutParams(layoutParams);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.smmlaba.balance.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catalog_products obj = (catalog_products) parent.getItemAtPosition(position);
                new Fragment();
                Fragment fragment = moneypay.newInstance();
                Bundle args = new Bundle();
                args.putInt("product_id", obj.getProduct_id());
                args.putString("model", obj.getModel());
                fragment.setArguments(args);
                ((MainActivity) balance.this.getActivity()).replaceFragment(fragment);
            }
        });
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView description = (TextView) this.v.findViewById(R.id.description);
        List<catalog_products> p = new ArrayList<>();
        category_item obj = (category_item) parent.getItemAtPosition(position);
        if (position != 0) {
            for (int i = 0; i < this.products.size(); i++) {
                if (obj.getCategory_id() == this.products.get(i).getCategory_id()) {
                    p.add(this.products.get(i));
                }
            }
            this.Gridadapter = new ProductItemAdapter(p, App.getAppContext());
        } else if (this.products.size() > 1) {
            this.Gridadapter = new ProductItemAdapter(this.products, App.getAppContext());
        }
        if (p.size() == 0 && position != 0) {
            this.gridView.setVisibility(8);
            description.setText(R.string.no_items);
        } else {
            this.gridView.setVisibility(0);
            description.setText("");
        }
        this.gridView.setAdapter((ListAdapter) this.Gridadapter);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
