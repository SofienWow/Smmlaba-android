package com.smmlaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.smmlaba.api.Delay;

/* JADX INFO: loaded from: classes9.dex */
public class Message extends Fragment implements Delay {
    private boolean State = false;
    private boolean disable = false;
    private int scroll = 0;
    private View v;

    public static Message newInstance() {
        Message fragment = new Message();
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
        View viewInflate = inflater.inflate(R.layout.ticket, container, false);
        this.v = viewInflate;
        return viewInflate;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String str) {
    }
}
