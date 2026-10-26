package com.smmlaba.system;

import android.widget.TextView;
import com.smmlaba.Entinity.offer.Product;
import com.smmlaba.Entinity.offer.message;
import com.smmlaba.Entinity.offer.params;
import com.smmlaba.R;
import com.smmlaba.data.App;
import com.smmlaba.product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes7.dex */
public class cart {
    private List<Product> products = new ArrayList();
    private List<Product> productsout = new ArrayList();
    private List<params> params = new ArrayList();
    private List<message> msg = new ArrayList();
    private final int validation_number = 10;
    private final int validation_string = 5;
    private final int validation_lenght = 25;
    private final int validation_rows = 15;

    interface StringValidation {
        boolean StringOperation(String[] strArr);
    }

    public boolean add(Product product, Map<String, TextView> ValidationIncorrect, int IndexCart) {
        int val;
        List<message> message = product.getMessage();
        this.msg = message;
        this.params = message.get(0).getParams();
        boolean incorrect = false;
        for (int i = 0; i < this.params.size(); i++) {
            TextView tex = ValidationIncorrect.get(this.params.get(i).getParam());
            if (this.params.get(i).getRequired() == 1 && this.params.get(i).getValue().length() < 1) {
                tex.setText(App.getAppContext().getString(R.string.required_start) + " " + this.params.get(i).getName() + " " + App.getAppContext().getString(R.string.required_end));
                tex.setVisibility(0);
                incorrect = true;
            } else {
                final String value = this.params.get(i).getValue();
                tex.setText(this.params.get(i).getValidtext());
                boolean niceValidation = true;
                switch (this.params.get(i).getValidation()) {
                    case 5:
                        String[] split_1 = this.params.get(i).getValidtext().split("~");
                        String[] split_2 = split_1[0].split(";");
                        if (split_1.length <= 1) {
                            split_1 = split_2;
                        }
                        StringValidation ss = new StringValidation() { // from class: com.smmlaba.system.cart.1
                            @Override // com.smmlaba.system.cart.StringValidation
                            public boolean StringOperation(String[] str) {
                                boolean a2 = false;
                                for (int i2 = 0; i2 < str.length && !(a2 = value.contains(str[i2])); i2++) {
                                }
                                return a2;
                            }
                        };
                        niceValidation = ss.StringOperation(split_1);
                        if (niceValidation) {
                            niceValidation = ss.StringOperation(split_2);
                        }
                        break;
                    case 10:
                        if (App.isInteger(value) && (val = Integer.valueOf(value).intValue()) >= this.params.get(i).getFrom_number() && val <= this.params.get(i).getTo_number()) {
                            niceValidation = true;
                        } else {
                            niceValidation = false;
                        }
                        break;
                    case 15:
                        if (value.length() > 0) {
                            niceValidation = value.split(IOUtils.LINE_SEPARATOR_UNIX).length >= Integer.valueOf(this.params.get(i).getValidtext()).intValue();
                        } else {
                            niceValidation = false;
                        }
                        break;
                    case 25:
                        if (value.length() >= this.params.get(i).getFrom_number() && value.length() <= this.params.get(i).getTo_number()) {
                            niceValidation = true;
                        } else {
                            niceValidation = false;
                        }
                        break;
                }
                tex.setText(this.params.get(i).getValid());
                if (!niceValidation) {
                    incorrect = true;
                    tex.setVisibility(0);
                } else {
                    tex.setVisibility(8);
                }
            }
        }
        if (!incorrect) {
            product.getMessage().get(0).setTotal(com.smmlaba.product.total);
            if (this.msg.get(0).getService().contains("balans")) {
                int val2 = Integer.valueOf(this.params.get(0).getValue()).intValue();
                product.getMessage().get(0).setTotal(val2);
            }
            if (IndexCart != -1) {
                App.makeToast(App.getAppContext().getString(R.string.cart_edit_sample));
                this.products.set(IndexCart, product);
            } else {
                App.makeToast(App.getAppContext().getString(R.string.add_cart));
                this.products.add(product);
            }
        }
        return incorrect;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void clear() {
        this.products = new ArrayList();
        this.productsout = new ArrayList();
        this.params = new ArrayList();
        this.msg = new ArrayList();
    }

    public int getSum() {
        int sum = 0;
        for (Product element : getProducts()) {
            sum += element.getMessage().get(0).getTotal();
        }
        return sum;
    }

    public void cart_logout() {
        if (App.customer.getCustomer_id() == 0) {
            this.productsout = new ArrayList();
            this.productsout = this.products;
            this.products = new ArrayList();
            for (Product element : this.productsout) {
                if (!element.getMessage().get(0).getService().contains("balans")) {
                    this.products.add(element);
                }
            }
        }
    }

    public int getTotal() {
        int totl = 0;
        if (App.customer.getCustomer_id() != 0) {
            for (Product element : getProducts()) {
                if (!element.getMessage().get(0).getService().contains("balans")) {
                    totl += element.getMessage().get(0).getTotal();
                }
            }
        }
        return totl;
    }
}
