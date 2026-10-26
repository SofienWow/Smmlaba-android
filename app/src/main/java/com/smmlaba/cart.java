package com.smmlaba;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.smmlaba.Entinity.num;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.Entinity.pay.payment;
import com.smmlaba.adapters.cartitemadapter;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import com.smmlaba.data.common;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes9.dex */
public class cart extends Fragment implements Delay {
    private cartitemadapter adapter;
    private TextView balance_customer;
    private float downX;
    private EditText email;
    private LinearLayout email_incorrect;
    private float limit;
    private LinearLayout linLayout;
    private LayoutInflater ltInflater;
    private float maxX;
    private EditText name;
    private LinearLayout name_incorrect;
    private EditText payment;
    public boolean ready;
    private RecyclerView recyclerView;
    private int total;
    private View v;
    private Paint p = new Paint();
    private boolean move = false;
    private boolean delete = false;
    private final float widthSwipe = App.convertDpToPixels(60.0f);
    private List<Product> products = new ArrayList();

    public static cart newInstance() {
        cart fragment = new cart();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.cart, container, false);
        this.total = 0;
        if (App.customer.getCustomer_id() != 0) {
            LinearLayout logb = (LinearLayout) this.v.findViewById(R.id.customer_two);
            logb.setVisibility(0);
            this.payment = (EditText) this.v.findViewById(R.id.total_balance);
            if (App.cart.getTotal() < App.customer.getPoints()) {
                this.payment.setText(String.valueOf(App.cart.getTotal()));
            } else {
                this.payment.setText(String.valueOf(App.customer.getPoints()));
            }
            this.payment.addTextChangedListener(new TextWatcher() { // from class: com.smmlaba.cart.1
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cart.this.loadModule(s);
                }
            });
        } else {
            LinearLayout abc = (LinearLayout) this.v.findViewById(R.id.abc);
            LinearLayout cba = (LinearLayout) this.v.findViewById(R.id.cba);
            abc.setVisibility(0);
            cba.setVisibility(0);
        }
        this.ltInflater = getLayoutInflater();
        this.balance_customer = (TextView) this.v.findViewById(R.id.balance_customer);
        loadModule(String.valueOf(App.cart.getTotal()));
        List<Product> products = App.cart.getProducts();
        this.products = products;
        if (products.size() > 0) {
            TextView total_sum = (TextView) this.v.findViewById(R.id.sum);
            SetTotal(total_sum);
            this.v.findViewById(R.id.cart_show).setVisibility(0);
            this.v.findViewById(R.id.empty).setVisibility(8);
            this.recyclerView = (RecyclerView) this.v.findViewById(R.id.recycler);
            cartitemadapter cartitemadapterVar = new cartitemadapter();
            this.adapter = cartitemadapterVar;
            this.recyclerView.setAdapter(cartitemadapterVar);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(App.getAppContext(), 1, false));
            enableSwipe();
        } else {
            this.v.findViewById(R.id.cart_show).setVisibility(8);
            this.v.findViewById(R.id.empty).setVisibility(0);
        }
        Button pay = (Button) this.v.findViewById(R.id.paybutton);
        this.name = (EditText) this.v.findViewById(R.id.name_customer);
        this.email = (EditText) this.v.findViewById(R.id.email_customer);
        this.name_incorrect = (LinearLayout) this.v.findViewById(R.id.incorrect_name);
        this.email_incorrect = (LinearLayout) this.v.findViewById(R.id.incorrect_email);
        pay.setOnClickListener(new View.OnClickListener() { // from class: com.smmlaba.cart.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean correct = true;
                if (App.customer.getCustomer_id() == 0) {
                    if (!common.validate(cart.this.email.getText().toString())) {
                        cart.this.email_incorrect.setVisibility(0);
                        correct = false;
                    } else {
                        cart.this.email_incorrect.setVisibility(4);
                    }
                    if (cart.this.name.getText().toString().length() < 3) {
                        cart.this.name_incorrect.setVisibility(0);
                        correct = false;
                    } else {
                        cart.this.name_incorrect.setVisibility(4);
                    }
                }
                if (correct) {
                    Gson gson = new Gson();
                    if (App.customer.getCustomer_id() > 1 && cart.this.payment.getText().toString().length() < 1) {
                        cart.this.payment.setText("0");
                    }
                    payment p = new payment(App.cart.getProducts(), App.customer.getCustomer_id() == 0 ? cart.this.email.getText().toString() : App.customer.getEmail(), App.customer.getCustomer_id() == 0 ? "" : App.customer.getToken(), App.cart.getSum(), cart.this.total, App.customer.getCustomer_id() == 0 ? 0 : Integer.parseInt(cart.this.payment.getText().toString()), App.customer.getCustomer_id() == 0 ? cart.this.name.getText().toString() : App.customer.getFirstname());
                    ApiConnect connect = new ApiConnect("orders/get", "POST", cart.this);
                    connect.SetJson(gson.toJson(p));
                    connect.execute(new String[0]);
                }
            }
        });
        return this.v;
    }

    public void SetTotal(TextView total_sum) {
        total_sum.setText(((Object) App.getAppContext().getResources().getText(R.string.sum_cart)) + " " + App.cart.getSum() + " " + ((Object) App.getAppContext().getResources().getText(R.string.currency)));
    }

    public void loadModule(CharSequence s) {
        if (s.length() == 0) {
            s = "0";
        }
        int max = App.customer.getPoints();
        int value = Integer.parseInt(s.toString());
        if (App.customer.getPoints() > App.cart.getTotal()) {
            max = App.cart.getTotal();
        }
        if (App.customer.getCustomer_id() != 0) {
            InputFilter[] filters = {new InputFilter.LengthFilter(String.valueOf(max).length())};
            this.payment.setFilters(filters);
        }
        if (Integer.parseInt(s.toString()) > max) {
            this.payment.setText(String.valueOf(max));
            value = max;
        }
        int sum = App.cart.getSum();
        if (max != 0) {
            sum -= value;
        }
        this.total = sum;
        this.balance_customer.setText(((Object) App.getAppContext().getResources().getText(R.string.total_table)) + " " + this.total + " " + ((Object) App.getAppContext().getResources().getText(R.string.currency)));
    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, 12) { // from class: com.smmlaba.cart.3
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction != 4) {
                    cart.this.adapter.removeItem(position);
                    TextView total_sum = (TextView) cart.this.v.findViewById(R.id.sum);
                    cart.this.SetTotal(total_sum);
                    CharSequence a2 = App.customer.getCustomer_id() != 0 ? cart.this.payment.getText().toString() : "0";
                    cart.this.loadModule(a2);
                    if (App.cart.getProducts().size() == 0) {
                        cart.this.v.findViewById(R.id.cart_show).setVisibility(8);
                        cart.this.v.findViewById(R.id.empty).setVisibility(0);
                        return;
                    }
                    return;
                }
                Product pr = App.cart.getProducts().get(position);
                new Fragment();
                Fragment fragment = product.newInstance();
                Bundle args = new Bundle();
                args.putInt("product_id", pr.getMessage().get(0).getProduct_id());
                args.putString("model", pr.getMessage().get(0).getService());
                args.putInt("CartIndex", position);
                fragment.setArguments(args);
                ((MainActivity) cart.this.getActivity()).replaceFragment(fragment);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == 1) {
                    View itemView = viewHolder.itemView;
                    if (dX > 0.0f) {
                        cart.this.p.setColor(App.getAppContext().getResources().getColor(R.color.red));
                        RectF background = new RectF(itemView.getLeft(), itemView.getTop(), dX, itemView.getBottom());
                        c.drawRect(background, cart.this.p);
                    } else {
                        cart.this.p.setColor(App.getAppContext().getResources().getColor(R.color.product_items));
                        RectF background2 = new RectF((itemView.getRight() - App.convertDpToPixels(20.0f)) + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        c.drawRect(background2, cart.this.p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Gson gson = new Gson();
        if (Parser.JsonValidate(output) instanceof JSONObject) {
            num num = (num) gson.fromJson(output, num.class);
            if (num.getOrder_id() != 0 && this.total > 0) {
                ((MainActivity) getActivity()).timeToStartCheckout(this.total, num.getOrder_id(), false);
            } else if (num.getOrder_id() != 0) {
                ((MainActivity) getActivity()).success_pay(num.getOrder_id());
            }
        }
    }
}
