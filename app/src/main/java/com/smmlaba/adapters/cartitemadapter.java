package com.smmlaba.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.Entinity.offer.message;
import com.smmlaba.R;
import com.smmlaba.data.App;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class cartitemadapter extends RecyclerView.Adapter<cartitemadapter.MyViewHolder> {
    private LayoutInflater lInflater = (LayoutInflater) App.getAppContext().getSystemService("layout_inflater");
    private List<Product> products;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout linearcart;
        public LinearLayout linlayout;
        LinearLayout options;
        TextView total;
        TextView txt;

        public MyViewHolder(View v) {
            super(v);
            this.linearcart = (LinearLayout) v.findViewById(R.id.linearcart);
            this.options = (LinearLayout) v.findViewById(R.id.options);
            this.img = (ImageView) v.findViewById(R.id.image);
            this.total = (TextView) v.findViewById(R.id.total);
        }
    }

    public cartitemadapter() {
        this.products = new ArrayList();
        this.products = App.cart.getProducts();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.lInflater.inflate(R.layout.cart_item, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        message mMesage = this.products.get(position).getMessage().get(0);
        App.SetImage(holder.img, mMesage.getImage());
        holder.total.setText(mMesage.getTotal() + " " + App.getAppContext().getResources().getString(R.string.currency));
        holder.options.removeAllViews();
        TextView txt = new TextView(App.getAppContext());
        txt.setText(mMesage.getProduct_name());
        txt.setTextColor(App.getAppContext().getResources().getColor(R.color.product_items));
        txt.setTextSize(15.0f);
        txt.setGravity(17);
        holder.options.addView(txt);
        for (int j = 0; j < mMesage.getParams().size(); j++) {
            TextView params = new TextView(App.getAppContext());
            params.setText("- " + mMesage.getParams().get(j).getName() + ": " + mMesage.getParams().get(j).getValue());
            holder.options.addView(params);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.products.size();
    }

    public void removeItem(int position) {
        App.cart.getProducts().remove(position);
        this.products = App.cart.getProducts();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.products.size());
    }
}
