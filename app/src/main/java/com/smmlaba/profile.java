package com.smmlaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.smmlaba.api.Autorization;
import com.smmlaba.data.App;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes9.dex */
public class profile extends Fragment implements Autorization {
    private Button btnlogin;
    private EditText emailedit;
    private TextView error;
    private EditText passwordedit;
    private View v;

    public static profile newInstance() {
        profile fragment = new profile();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.profile, container, false);
        this.v = viewInflate;
        this.btnlogin = (Button) viewInflate.findViewById(R.id.log_on);
        this.emailedit = (EditText) this.v.findViewById(R.id.email);
        this.passwordedit = (EditText) this.v.findViewById(R.id.password);
        this.error = (TextView) this.v.findViewById(R.id.error);
        this.btnlogin.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.profile.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String email = profile.this.emailedit.getText().toString();
                String password = profile.this.passwordedit.getText().toString();
                try {
                    password = App.SHA1(password);
                } catch (Throwable th) {
                }
                App.customer.login(email, password);
                App.customer.Semafor(profile.this);
            }
        });
        return this.v;
    }

    @Override // com.smmlaba.api.Autorization
    public void tryAutorization() {
        if (App.customer.getCustomer_id() > 0 && App.customer.getStatus() > 0) {
            isloged();
        } else {
            this.error.setVisibility(0);
        }
    }

    public void isloged() {
        ((MainActivity) getActivity()).replaceFragment(setting.newInstance());
    }
}
