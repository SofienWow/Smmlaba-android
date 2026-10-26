package com.smmlaba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.smmlaba.Entinity.category.catalog_products;
import com.smmlaba.R;
import com.smmlaba.data.App;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ProductItemAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    Context mContext;
    List<catalog_products> objects;

    public ProductItemAdapter(List<catalog_products> objects, Context mContext) {
        this.objects = objects;
        this.mContext = mContext;
        this.lInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.objects.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.objects.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = this.lInflater.inflate(R.layout.product_item, parent, false);
        catalog_products p = getItemCategory(position);
        ((TextView) item.findViewById(R.id.txt)).setText(p.getName());
        ((TextView) item.findViewById(R.id.price)).setText(p.getLowerprice());
        item.setPadding(App.Convertdp(1), App.Convertdp(1), App.Convertdp(1), App.Convertdp(1));
        if (p.getImage() != null && p.getImage().length() > 10) {
            ImageView img = (ImageView) item.findViewById(R.id.pr);
            App.SetImage((ImageView) item.findViewById(R.id.pr), p.getImage());
            ViewHolder holderimage = new ViewHolder();
            holderimage.imgview = img;
            item.setTag(holderimage);
        }
        return item;
    }

    catalog_products getItemCategory(int position) {
        return (catalog_products) getItem(position);
    }

    private static class ViewHolder {
        ImageView imgview;

        private ViewHolder() {
        }
    }
}
