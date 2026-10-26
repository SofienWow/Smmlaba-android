package com.smmlaba;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.newCustomer;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class register extends Fragment implements Delay {
    private Button btn;
    private ApiConnect connect;
    private EditText email;
    private TextView email_incorrect;
    private EditText name;
    private TextView name_incorrect;
    private EditText password;
    private TextView password_incorrect;
    private View v;

    public static register newInstance() {
        register fragment = new register();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.register, container, false);
        this.v = viewInflate;
        this.btn = (Button) viewInflate.findViewById(R.id.button);
        this.email = (EditText) this.v.findViewById(R.id.email);
        this.name = (EditText) this.v.findViewById(R.id.name);
        this.password = (EditText) this.v.findViewById(R.id.password);
        this.email_incorrect = (TextView) this.v.findViewById(R.id.email_incorrect);
        this.name_incorrect = (TextView) this.v.findViewById(R.id.name_incorrect);
        this.password_incorrect = (TextView) this.v.findViewById(R.id.password_incorrect);
        App.flag = true;
        ((MainActivity) getActivity()).logo.setVisibility(4);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.bottom_icon), PorterDuff.Mode.SRC_ATOP);
        MainActivity.registerbutton.setVisibility(4);
        this.btn.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.register.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String em = register.this.email.getText().toString();
                boolean correct = true;
                register.this.email_incorrect.setVisibility(8);
                register.this.password_incorrect.setVisibility(8);
                register.this.name_incorrect.setVisibility(8);
                if (register.this.email.getText().toString().length() < 1) {
                    register.this.email_incorrect.setVisibility(0);
                    register.this.email_incorrect.setText(App.getAppContext().getString(R.string.empty_email));
                    correct = false;
                } else if (!common.validate(em)) {
                    register.this.email_incorrect.setVisibility(0);
                    register.this.email_incorrect.setText(App.getAppContext().getString(R.string.incorrect_email_symbol));
                    correct = false;
                }
                if (register.this.name.getText().toString().length() < 1) {
                    register.this.name_incorrect.setVisibility(0);
                    register.this.name_incorrect.setText(App.getAppContext().getString(R.string.empty_name));
                    correct = false;
                }
                if (register.this.password.getText().toString().length() < 1) {
                    register.this.password_incorrect.setVisibility(0);
                    register.this.password_incorrect.setText(App.getAppContext().getString(R.string.empty_pass));
                    correct = false;
                } else if (register.this.password.getText().toString().length() < 6) {
                    register.this.password_incorrect.setVisibility(0);
                    register.this.password_incorrect.setText(App.getAppContext().getString(R.string.lenth_pass));
                    correct = false;
                }
                if (correct) {
                    String pass = new String();
                    try {
                        pass = App.SHA1(register.this.password.getText().toString());
                    } catch (Throwable th) {
                    }
                    ApiConnect p = new ApiConnect("customers/new", "email=" + register.this.email.getText().toString() + "&password=" + pass + "&name=" + register.this.name.getText().toString(), register.this);
                    p.execute(new String[0]);
                }
            }
        });
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        if (json instanceof JSONObject) {
            newCustomer ne = (newCustomer) gson.fromJson(output, newCustomer.class);
            switch (ne.getError()) {
                case 2:
                    this.email_incorrect.setVisibility(0);
                    this.email_incorrect.setText(App.getAppContext().getString(R.string.incorrect_email));
                    break;
                case 3:
                    App.makeToast(App.getAppContext().getString(R.string.succes));
                    ((MainActivity) getActivity()).onBackPressed();
                    break;
            }
        }
    }
}
