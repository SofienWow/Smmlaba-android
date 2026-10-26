package com.smmlaba;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.internal.ImagesContract;
import com.smmlaba.Entinity.category.catalog_products;
import com.smmlaba.adapters.ProductItemAdapter;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes9.dex */
public class favorite extends Fragment {
    public static favorite newInstance() {
        favorite fragment = new favorite();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.grid);
        List<catalog_products> products = new ArrayList<>();
        Cursor c = App.db.getWritableDatabase().query("favorite", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            int pridColIndex = c.getColumnIndex("product_id");
            int modelColIndex = c.getColumnIndex("model");
            int urlColIndex = c.getColumnIndex(ImagesContract.URL);
            int lowerpriceColIndex = c.getColumnIndex("lowerprice");
            do {
                catalog_products catalog_products = new catalog_products(c.getString(nameColIndex), c.getString(urlColIndex), c.getInt(pridColIndex), 0, c.getString(modelColIndex), c.getString(lowerpriceColIndex));
                products.add(catalog_products);
            } while (c.moveToNext());
        } else {
            c.close();
        }
        App.db.close();
        gridView.setNumColumns(common.main_catalog_count.intValue());
        ProductItemAdapter Gridadapter = new ProductItemAdapter(products, App.getAppContext());
        gridView.setAdapter((ListAdapter) Gridadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.smmlaba.favorite.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catalog_products obj = (catalog_products) parent.getItemAtPosition(position);
                new Fragment();
                Fragment fragment = product.newInstance();
                Bundle args = new Bundle();
                args.putInt("product_id", obj.getProduct_id());
                args.putString("model", obj.getModel());
                fragment.setArguments(args);
                ((MainActivity) favorite.this.getActivity()).replaceFragment(fragment);
            }
        });
        return v;
    }
}
