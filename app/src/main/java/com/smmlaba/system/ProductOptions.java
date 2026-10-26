package com.smmlaba.system;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.R;
import com.smmlaba.data.App;
import com.smmlaba.product;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes7.dex */
public class ProductOptions {
    private int IndexCart;
    private CheckBox chekbox;
    private EditText edittext;
    private RadioGroup radiogroup;
    private Spinner spinner;
    private EditText textarea;
    private View v;
    private List<CheckBox> list_chekbox = new ArrayList();
    private List<String> list = new ArrayList();

    public ProductOptions(View v, int IndexCart) {
        this.IndexCart = -1;
        this.v = v;
        this.IndexCart = IndexCart;
    }

    public void SetText(List<String> list) {
        this.list = list;
    }

    public void generateData(String type, LinearLayout options, final String param) {
        String value = new String();
        if (this.IndexCart != -1) {
            Product pr = App.cart.getProducts().get(this.IndexCart);
            value = pr.getMessage().get(0).getValueByParam(param);
        }
        switch (type) {
            case "text":
                EditText editText = new EditText(App.getAppContext());
                this.edittext = editText;
                editText.setWidth(App.convertDpToPixels(70.0f));
                this.edittext.setHeight(App.convertDpToPixels(35.0f));
                this.edittext.setBackground(App.getAppContext().getResources().getDrawable(R.drawable.edit_style));
                this.edittext.setPadding(App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f));
                this.edittext.setTextColor(App.getAppContext().getResources().getColor(R.color.bottom_icon));
                this.edittext.setTextSize(20.0f);
                this.edittext.setText(value);
                if (param.equals("count") || param.equals("count_posts")) {
                    this.edittext.setInputType(2);
                    this.edittext.addTextChangedListener(new TextWatcher() { // from class: com.smmlaba.system.ProductOptions.1
                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable s) {
                            if (App.isInteger(ProductOptions.this.edittext.getText().toString())) {
                                switch (param) {
                                    case "count":
                                        if (product.posts) {
                                            product.count = Integer.valueOf(ProductOptions.this.edittext.getText().toString()).intValue();
                                            product.total = (int) (((product.count * product.count_posts) / 1000.0f) * product.price);
                                            break;
                                        } else {
                                            float pre_value = Integer.valueOf(ProductOptions.this.edittext.getText().toString()).intValue();
                                            product.total = (int) ((pre_value / product.kof) * product.price);
                                            break;
                                        }
                                    case "count_posts":
                                        ProductOptions.this.edittext.setInputType(2);
                                        product.posts = true;
                                        product.count_posts = Integer.valueOf(ProductOptions.this.edittext.getText().toString()).intValue();
                                        product.total = (int) (((product.count * product.count_posts) / 1000.0f) * product.price);
                                        break;
                                }
                            }
                            String text_total_first = App.getAppContext().getString(R.string.total);
                            if (product.product_id == 912) {
                                double totols = product.total;
                                if (product.total >= 1000 && product.total < 5000) {
                                    totols = ((double) product.total) * 1.05d;
                                }
                                if (product.total >= 5000 && product.total < 20000) {
                                    totols = ((double) product.total) * 1.1d;
                                }
                                if (product.total >= 20000 && product.total < 30000) {
                                    totols = ((double) product.total) * 1.15d;
                                }
                                if (product.total >= 30000 && product.total < 50000) {
                                    totols = ((double) product.total) * 1.2d;
                                }
                                if (product.total >= 50000 && product.total < 100000) {
                                    totols = ((double) product.total) * 1.25d;
                                }
                                if (product.total >= 100000) {
                                    totols = ((double) product.total) * 1.3d;
                                }
                                product.total = (int) totols;
                                text_total_first = App.getAppContext().getString(R.string.total_balance);
                            }
                            product.ShowPrice.setText(text_total_first + " " + product.total + " " + App.getAppContext().getString(R.string.currency));
                        }
                    });
                }
                options.addView(this.edittext);
                break;
            case "radio":
                this.radiogroup = new RadioGroup(App.getAppContext());
                int index = 0;
                for (int i = 0; i < this.list.size(); i++) {
                    RadioButton radio = new RadioButton(App.getAppContext());
                    radio.setText(this.list.get(i));
                    radio.setTag(Integer.valueOf(i));
                    index = value.equals(this.list.get(i)) ? i : 0;
                    this.radiogroup.addView(radio);
                }
                ((RadioButton) this.radiogroup.getChildAt(index)).setChecked(true);
                options.addView(this.radiogroup);
                break;
            case "select":
                this.spinner = new Spinner(App.getAppContext());
                int index_select = 0;
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(App.getAppContext(), android.R.layout.simple_spinner_dropdown_item, this.list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.spinner.setAdapter((SpinnerAdapter) dataAdapter);
                this.spinner.setBackground(App.getAppContext().getResources().getDrawable(R.drawable.rectangle));
                for (int i2 = 0; i2 < this.list.size(); i2++) {
                    index_select = value.equals(this.list.get(i2)) ? i2 : 0;
                    if (index_select != 0) {
                        this.spinner.setSelection(index_select);
                        options.addView(this.spinner);
                    }
                    break;
                }
                this.spinner.setSelection(index_select);
                options.addView(this.spinner);
                break;
            case "textarea":
                EditText editText2 = new EditText(App.getAppContext());
                this.textarea = editText2;
                editText2.setWidth(App.convertDpToPixels(70.0f));
                this.textarea.setHeight(App.convertDpToPixels(130.0f));
                this.textarea.setBackground(App.getAppContext().getResources().getDrawable(R.drawable.edit_style));
                this.textarea.setPadding(App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f), App.convertDpToPixels(5.0f));
                this.textarea.setTextColor(App.getAppContext().getResources().getColor(R.color.bottom_icon));
                this.textarea.setTextSize(20.0f);
                this.textarea.setText(value);
                if (param.equals("comments")) {
                    this.textarea.addTextChangedListener(new TextWatcher() { // from class: com.smmlaba.system.ProductOptions.2
                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable s) {
                            product.total = ProductOptions.this.textarea.getText().toString().split(IOUtils.LINE_SEPARATOR_UNIX).length;
                            product.ShowPrice.setText(App.getAppContext().getString(R.string.total) + " " + product.total + " " + App.getAppContext().getString(R.string.currency));
                        }
                    });
                }
                options.addView(this.textarea);
                break;
            case "checkbox":
                for (int i3 = 0; i3 < this.list.size(); i3++) {
                    CheckBox checkBox = new CheckBox(App.getAppContext());
                    this.chekbox = checkBox;
                    checkBox.setText(this.list.get(i3));
                    if (value.length() > 3) {
                        this.chekbox.setChecked(value.contains(this.list.get(i3)));
                    }
                    this.list_chekbox.add(this.chekbox);
                    options.addView(this.chekbox);
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:20:0x0040  */
    public String getData(String type) {
        String Data = new String();
        switch (type) {
            case "text":
                return this.edittext.getText().toString();
            case "radio":
                RadioButton radioButton = (RadioButton) this.v.findViewById(this.radiogroup.getCheckedRadioButtonId());
                return radioButton.getText().toString();
            case "select":
                return this.spinner.getSelectedItem().toString();
            case "textarea":
                return this.textarea.getText().toString();
            case "checkbox":
                for (int i = 0; i < this.list_chekbox.size(); i++) {
                    if (this.list_chekbox.get(i).isChecked()) {
                        Data = Data + this.list_chekbox.get(i).getText().toString() + " ";
                    }
                }
                return Data;
            default:
                return Data;
        }
    }
}
