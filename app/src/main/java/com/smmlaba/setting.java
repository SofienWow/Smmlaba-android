package com.smmlaba;

import android.content.res.Resources;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.customer.balanceID;
import com.smmlaba.Entinity.customer.orders;
import com.smmlaba.Entinity.customer.subject;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class setting extends Fragment implements Delay {
    private static List<TextView> allTextView;
    private TextView balance;
    private TextView change;
    private LinearLayout ed;
    private EditText email;
    private EditText name_customer;
    private EditText new_pass;
    private TextView order;
    private String password;
    private Button save;
    private TextView support;
    private TableLayout table;
    private View v;

    public static setting newInstance() {
        setting fragment = new setting();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.setting, container, false);
        this.v = viewInflate;
        this.order = (TextView) viewInflate.findViewById(R.id.orders);
        this.balance = (TextView) this.v.findViewById(R.id.balance);
        this.support = (TextView) this.v.findViewById(R.id.support);
        this.change = (TextView) this.v.findViewById(R.id.change);
        this.ed = (LinearLayout) this.v.findViewById(R.id.izminit);
        this.email = (EditText) this.v.findViewById(R.id.email);
        this.new_pass = (EditText) this.v.findViewById(R.id.pass);
        this.save = (Button) this.v.findViewById(R.id.save);
        EditText editText = (EditText) this.v.findViewById(R.id.name);
        this.name_customer = editText;
        editText.setText(App.customer.getFirstname());
        this.email.setText(App.customer.getEmail());
        this.table = (TableLayout) this.v.findViewById(R.id.tableLayout);
        allTextView = Arrays.asList(this.order, this.balance, this.support, this.change);
        setBorder(this.order);
        ApiConnect api = new ApiConnect("customers/id", "customer_id=" + App.customer.getCustomer_id(), this);
        api.execute(new String[0]);
        this.order.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                setting.setBorder(setting.this.order);
                setting.this.table.setVisibility(0);
                setting.this.ed.setVisibility(8);
                ApiConnect api2 = new ApiConnect("customers/id", "customer_id=" + App.customer.getCustomer_id(), setting.this);
                api2.execute(new String[0]);
            }
        });
        this.balance.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                setting.setBorder(setting.this.balance);
                setting.this.table.setVisibility(0);
                setting.this.ed.setVisibility(8);
                ApiConnect api2 = new ApiConnect("customers/idbalance", "customer_id=" + App.customer.getCustomer_id(), setting.this);
                api2.execute(new String[0]);
            }
        });
        this.support.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                setting.setBorder(setting.this.support);
                setting.this.table.setVisibility(0);
                setting.this.ed.setVisibility(8);
                ApiConnect api2 = new ApiConnect("customers/idsubject", "customer_id=" + App.customer.getCustomer_id(), setting.this);
                api2.execute(new String[0]);
            }
        });
        this.change.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                setting.setBorder(setting.this.change);
                setting.this.table.setVisibility(8);
                setting.this.ed.setVisibility(0);
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                setting settingVar = setting.this;
                settingVar.password = settingVar.new_pass.getText().toString();
                if (setting.this.new_pass.getText().toString().length() > 5) {
                    try {
                        setting settingVar2 = setting.this;
                        settingVar2.password = App.SHA1(settingVar2.password);
                    } catch (Throwable th) {
                    }
                } else {
                    setting.this.password = App.customer.getPassword();
                }
                ApiConnect api2 = new ApiConnect("customers/save", "customer_id=" + App.customer.getCustomer_id() + "&name=" + App.CustomEncode(setting.this.name_customer.getText().toString()) + "&email=" + setting.this.email.getText().toString() + "&password=" + setting.this.password + "&secret=" + App.customer.getToken(), setting.this);
                api2.execute(new String[0]);
            }
        });
        return this.v;
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        byte b;
        Matcher m = Pattern.compile("order_id|subject_id|customer_reward_id|gotovo").matcher(output);
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        this.table.removeAllViews();
        this.table.setGravity(1);
        if (m.find()) {
            switch (m.group()) {
                case "customer_reward_id":
                    b = 3;
                    break;
                case "gotovo":
                    b = 0;
                    break;
                case "subject_id":
                    b = 2;
                    break;
                case "order_id":
                    b = 1;
                    break;
                default:
                    b = -1;
                    break;
            }
            int i = -2;
            switch (b) {
                case 0:
                    App.makeToast(App.getAppContext().getString(R.string.accaunt_message));
                    App.customer.login(this.email.getText().toString(), this.password);
                    break;
                case 1:
                    List<orders> zam = new ArrayList<>();
                    if (json instanceof JSONObject) {
                        orders o = (orders) gson.fromJson(output, orders.class);
                        zam.add(o);
                    }
                    if (json instanceof JSONArray) {
                        orders[] oo = (orders[]) gson.fromJson(output, orders[].class);
                        zam = new ArrayList<>(Arrays.asList(oo));
                    }
                    TableRow tableRow = new TableRow(getActivity());
                    tableRow.addView(textv(App.getAppContext().getString(R.string.table_order_one), true, -1), 0);
                    tableRow.addView(textv(App.getAppContext().getString(R.string.table_order_two), true, -1), 1);
                    tableRow.addView(textv(App.getAppContext().getString(R.string.table_order_three), true, -1), 2);
                    tableRow.addView(textv(App.getAppContext().getString(R.string.table_order_five), true, -1), 3);
                    this.table.addView(tableRow, 0);
                    for (int i2 = 0; i2 < zam.size(); i2++) {
                        TableRow tableRow2 = new TableRow(App.getAppContext());
                        tableRow2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                        tableRow2.addView(textv(Integer.toString(zam.get(i2).getOrder_id()), false, -1), 0);
                        tableRow2.addView(textv(zam.get(i2).getName(), false, -1), 1);
                        tableRow2.addView(textv(zam.get(i2).getDate_added(), false, -1), 2);
                        tableRow2.addView(textv(zam.get(i2).getTotal(), false, -1), 3);
                        tableRow2.setTag(Integer.valueOf(zam.get(i2).getOrder_id()));
                        tableRow2.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.6
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                System.out.println(v.getTag());
                                new Fragment();
                                Fragment fragment = order_info.newInstance();
                                Bundle args = new Bundle();
                                args.putInt("order_id", Integer.parseInt(v.getTag().toString()));
                                fragment.setArguments(args);
                                ((MainActivity) setting.this.getActivity()).replaceFragment(fragment);
                            }
                        });
                        this.table.addView(tableRow2, i2 + 1);
                    }
                    break;
                case 2:
                    Resources resource = App.getAppContext().getResources();
                    List<subject> sam = new ArrayList<>();
                    if (json instanceof JSONObject) {
                        subject s = (subject) gson.fromJson(output, subject.class);
                        sam.add(s);
                    }
                    if (json instanceof JSONArray) {
                        subject[] ss = (subject[]) gson.fromJson(output, subject[].class);
                        sam = new ArrayList<>(Arrays.asList(ss));
                    }
                    TableRow row = new TableRow(getActivity());
                    row.addView(textv(App.getAppContext().getString(R.string.table_support_one), true, -1), 0);
                    row.addView(textv(App.getAppContext().getString(R.string.table_support_two), true, -1), 1);
                    row.addView(textv(App.getAppContext().getString(R.string.table_support_three), true, -1), 2);
                    this.table.addView(row, 0);
                    int i3 = 0;
                    while (i3 < sam.size()) {
                        TableRow row2 = new TableRow(App.getAppContext());
                        row2.setLayoutParams(new ViewGroup.LayoutParams(-1, i));
                        int color = -1;
                        if (sam.get(i3).getState() > 0) {
                            color = resource.getColor(R.color.close);
                        }
                        if (sam.get(i3).getStatus() == 1) {
                            color = resource.getColor(R.color.new_message);
                        }
                        row2.addView(textv(Integer.toString(sam.get(i3).getSubject_id()), false, color), 0);
                        row2.addView(textv(sam.get(i3).getTitle(), false, color), 1);
                        row2.addView(textv(sam.get(i3).getDate_added(), false, color), 2);
                        row2.setTag(Integer.valueOf(sam.get(i3).getSubject_id()));
                        row2.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.setting.7
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                System.out.println(v.getTag());
                            }
                        });
                        this.table.addView(row2, i3 + 1);
                        i3++;
                        i = -2;
                    }
                    break;
                case 3:
                    List<balanceID> bam = new ArrayList<>();
                    if (json instanceof JSONObject) {
                        balanceID b2 = (balanceID) gson.fromJson(output, balanceID.class);
                        bam.add(b2);
                    }
                    if (json instanceof JSONArray) {
                        balanceID[] bb = (balanceID[]) gson.fromJson(output, balanceID[].class);
                        bam = new ArrayList<>(Arrays.asList(bb));
                    }
                    TableRow tableRowb = new TableRow(getActivity());
                    tableRowb.addView(textv(App.getAppContext().getString(R.string.table_balance_one), true, -1), 0);
                    tableRowb.addView(textv(App.getAppContext().getString(R.string.table_balance_two), true, -1), 1);
                    tableRowb.addView(textv(App.getAppContext().getString(R.string.table_balance_three), true, -1), 2);
                    this.table.addView(tableRowb, 0);
                    for (int i4 = 0; i4 < bam.size(); i4++) {
                        TableRow tableRowb2 = new TableRow(App.getAppContext());
                        tableRowb2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                        tableRowb2.addView(textv(bam.get(i4).getDate_added(), false, -1), 0);
                        tableRowb2.addView(textv(bam.get(i4).getDescription(), false, -1), 1);
                        tableRowb2.addView(textv(bam.get(i4).getPoints(), false, -1), 2);
                        this.table.addView(tableRowb2, i4 + 1);
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setBorder(TextView a2) {
        for (TextView item : allTextView) {
            item.setBackgroundResource(0);
        }
        LayerDrawable bottomBorder = App.getBorders(ContextCompat.getColor(App.getAppContext(), R.color.bottom_icon), ContextCompat.getColor(App.getAppContext(), R.color.header), 0, 0, 0, 5);
        a2.setBackground(bottomBorder);
    }

    private static TextView textv(String txt, boolean header, int Color) {
        TextView t = new TextView(App.getAppContext());
        if (header) {
            t.setTextAppearance(App.getAppContext(), R.style.table_header);
            t.setHeight(App.convertDpToPixels(30.0f));
        } else {
            t.setTextAppearance(App.getAppContext(), R.style.table_just_text);
        }
        t.setHeight(App.convertDpToPixels(50.0f));
        LayerDrawable bottomBorder = App.getBorders(ContextCompat.getColor(App.getAppContext(), R.color.bottom_icon), Color, 1, 1, 1, 1);
        t.setBackground(bottomBorder);
        t.setGravity(17);
        t.setPadding(App.convertDpToPixels(6.0f), App.convertDpToPixels(6.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(6.0f));
        t.setText(txt);
        return t;
    }
}
