package com.smmlaba.Entinity.offer;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes9.dex */
public class params {

    @SerializedName("from_number")
    private int from_number;

    @SerializedName("name")
    private String name;

    @SerializedName("param")
    private String param;

    @SerializedName("required")
    private int required;

    @SerializedName("step")
    private int step;

    @SerializedName("to_number")
    private int to_number;

    @SerializedName("type")
    private String type;

    @SerializedName("valid")
    private String valid;

    @SerializedName("validation")
    private int validation;

    @SerializedName("validtext")
    private String validtext;
    private String value = new String();

    @SerializedName("values")
    private List<String> values;

    public params(String name, String param, int required, String type, String validtext, int validation, int to_number, int from_number, int step, String valid, List<String> values) {
        this.name = name;
        this.param = param;
        this.required = required;
        this.type = type;
        this.validtext = validtext;
        this.validation = validation;
        this.to_number = to_number;
        this.from_number = from_number;
        this.step = step;
        this.valid = valid;
        this.values = values;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getRequired() {
        return this.required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidtext() {
        return this.validtext;
    }

    public void setValidtext(String validtext) {
        this.validtext = validtext;
    }

    public int getValidation() {
        return this.validation;
    }

    public void setValidation(int validation) {
        this.validation = validation;
    }

    public int getTo_number() {
        return this.to_number;
    }

    public void setTo_number(int to_number) {
        this.to_number = to_number;
    }

    public int getFrom_number() {
        return this.from_number;
    }

    public void setFrom_number(int from_number) {
        this.from_number = from_number;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getValid() {
        return this.valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
