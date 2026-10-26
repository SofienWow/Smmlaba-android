package com.smmlaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.smmlaba.data.App;

/* JADX INFO: loaded from: classes9.dex */
public class success_pay extends Fragment {
    private View v;

    public static success_pay newInstance() {
        success_pay fragment = new success_pay();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.succes, container, false);
        this.v = viewInflate;
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int order_id = 0;
        if (bundle != null) {
            order_id = bundle.getInt("order_id", 0);
        }
        TextView t = (TextView) this.v.findViewById(R.id.order_id);
        t.setText(((Object) App.getAppContext().getResources().getText(R.string.success_pay)) + Integer.toString(order_id));
    }
}
