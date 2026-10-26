package com.smmlaba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.smmlaba.Entinity.category.category_item;
import com.smmlaba.R;
import com.smmlaba.data.App;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SpinnerAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    Context mContext;
    List<category_item> objects;

    public SpinnerAdapter(List<category_item> objects, Context mContext) {
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

    public int getPosition() {
        return 0;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = this.lInflater.inflate(R.layout.spinner_item, parent, false);
        category_item p = getItemCategory(position);
        ((TextView) item.findViewById(R.id.text)).setText(p.getName());
        if (p.getMenu_icon() != null && p.getMenu_icon().length() > 10) {
            ImageView img = (ImageView) item.findViewById(R.id.img);
            App.SetImage(img, p.getMenu_icon());
            ((TextView) item.findViewById(R.id.text)).setVisibility(8);
            img.setVisibility(0);
            ViewHolder holderimage = new ViewHolder();
            holderimage.imgview = img;
            item.setTag(holderimage);
        }
        return item;
    }

    category_item getItemCategory(int position) {
        return (category_item) getItem(position);
    }

    private static class ViewHolder {
        ImageView imgview;

        private ViewHolder() {
        }
    }
}
