package com.smmlaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.category.catalog_layout;
import com.smmlaba.Entinity.category.catalog_products;
import com.smmlaba.Entinity.category.category_item;
import com.smmlaba.Entinity.category.store;
import com.smmlaba.adapters.ProductItemAdapter;
import com.smmlaba.adapters.SpinnerAdapter;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class category extends Fragment implements Delay, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ProductItemAdapter Gridadapter;
    private int Selected;
    private SpinnerAdapter adapter;
    private ApiConnect catalog;
    private List<category_item> category_items;
    private List<catalog_layout> categorys;
    private GridView gridView;
    private HorizontalScrollView hz;
    private LinearLayout linLayout;
    private LayoutInflater ltInflater;
    private List<catalog_products> products;
    private String response;
    private store store;
    private View v;
    private boolean State = false;
    private boolean disable = false;
    private int scroll = 0;

    public static category newInstance() {
        category fragment = new category();
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
        if (MainActivity.outBundle.containsKey("category")) {
            this.State = true;
            this.response = MainActivity.outBundle.getString("category");
            this.Selected = MainActivity.outBundle.getInt("category_catalog_id");
            this.scroll = MainActivity.outBundle.getInt("category_layout");
            after(this.response);
            this.State = false;
        } else {
            ApiConnect apiConnect = new ApiConnect("product/category", "", this);
            this.catalog = apiConnect;
            apiConnect.execute(new String[0]);
        }
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String str) {
        int i;
        Object json = Parser.JsonValidate(str);
        this.response = str;
        Gson gson = new Gson();
        this.categorys = new ArrayList();
        this.products = new ArrayList();
        this.category_items = new ArrayList();
        this.linLayout = (LinearLayout) this.v.findViewById(R.id.screen);
        Spinner mySpinner = (Spinner) this.v.findViewById(R.id.spinner);
        this.gridView = (GridView) this.v.findViewById(R.id.grid);
        this.hz = (HorizontalScrollView) this.v.findViewById(R.id.scroll_for_screen);
        this.ltInflater = getLayoutInflater();
        if (json instanceof JSONObject) {
            store storeVar = (store) gson.fromJson(str, store.class);
            this.store = storeVar;
            this.categorys = storeVar.getCatalog_layout();
            this.products = this.store.getCatalog_products();
            this.category_items = this.store.getCategory_item();
            boolean z = false;
            if (!this.State && !this.disable) {
                this.Selected = this.categorys.get(0).getCategory_id().intValue();
            }
            if (this.linLayout.getChildCount() <= 0) {
                int j = 0;
                while (j < this.categorys.size()) {
                    View item = this.ltInflater.inflate(R.layout.catalog_layout_item, this.linLayout, z);
                    item.getLayoutParams().width = -1;
                    item.setPadding(App.Convertdp(1), App.Convertdp(1), App.Convertdp(1), App.Convertdp(1));
                    if (this.categorys.get(j).getCategory_id().intValue() == this.Selected) {
                        item.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.selected));
                    }
                    TextView txt = (TextView) item.findViewById(R.id.txt);
                    txt.setText(Integer.toString(this.categorys.get(j).getCategory_id().intValue()));
                    TextView name = (TextView) item.findViewById(R.id.name);
                    name.setText(this.categorys.get(j).getName());
                    ImageView imageView = (ImageView) item.findViewById(R.id.item_img);
                    int checkExistence = getResources().getIdentifier("item" + this.categorys.get(j).getCategory_id(), "drawable", getActivity().getPackageName());
                    if (checkExistence != 0) {
                        imageView.setImageResource(getResources().getIdentifier("item" + this.categorys.get(j).getCategory_id(), "drawable", getActivity().getPackageName()));
                    } else {
                        App.SetImage(imageView, this.categorys.get(j).getImage());
                    }
                    item.setClickable(true);
                    item.setOnClickListener(this);
                    this.linLayout.addView(item);
                    j++;
                    z = false;
                }
            }
            this.gridView.setNumColumns(common.main_catalog_count.intValue());
            ProductItemAdapter productItemAdapter = new ProductItemAdapter(this.products, App.getAppContext());
            this.Gridadapter = productItemAdapter;
            this.gridView.setAdapter((ListAdapter) productItemAdapter);
            this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.smmlaba.category.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    catalog_products obj = (catalog_products) parent.getItemAtPosition(position);
                    new Fragment();
                    Fragment fragment = product.newInstance();
                    Bundle args = new Bundle();
                    args.putInt("product_id", obj.getProduct_id());
                    args.putString("model", obj.getModel());
                    fragment.setArguments(args);
                    ((MainActivity) category.this.getActivity()).replaceFragment(fragment);
                }
            });
            TextView txt2 = (TextView) this.v.findViewById(R.id.filter);
            txt2.setText(R.string.filter);
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this.category_items, App.getAppContext());
            this.adapter = spinnerAdapter;
            mySpinner.setAdapter((android.widget.SpinnerAdapter) spinnerAdapter);
            mySpinner.setOnItemSelectedListener(this);
            if (this.State && (i = this.scroll) != 0) {
                this.hz.setScrollX(i);
            }
        }
    }

    private void DataSaved() {
        MainActivity.outBundle.putString("category", this.response);
        MainActivity.outBundle.putInt("category_catalog_id", this.Selected);
        MainActivity.outBundle.putInt("category_layout", this.hz.getScrollX());
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView description = (TextView) this.v.findViewById(R.id.description);
        List<catalog_products> p = new ArrayList<>();
        category_item obj = (category_item) parent.getItemAtPosition(position);
        DataSaved();
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

    public void select_logout() {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        DataSaved();
        LinearLayout b = (LinearLayout) v;
        TextView txt = (TextView) b.findViewById(R.id.txt);
        for (int i = 0; i < this.linLayout.getChildCount(); i++) {
            View lay = this.linLayout.getChildAt(i);
            lay.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        }
        int buffer = Integer.parseInt(txt.getText().toString());
        if (this.Selected != buffer) {
            ApiConnect apiConnect = new ApiConnect("product/category", "catalog_id=" + buffer, this);
            this.catalog = apiConnect;
            apiConnect.execute(new String[0]);
            this.disable = true;
            this.Selected = buffer;
        }
        b.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.selected));
    }
}
