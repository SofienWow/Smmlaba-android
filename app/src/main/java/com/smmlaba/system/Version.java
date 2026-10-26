package com.smmlaba.system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import com.google.gson.Gson;
import com.smmlaba.Entinity.Version_base;
import com.smmlaba.R;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import com.smmlaba.data.App;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes7.dex */
public class Version implements Delay {
    private AlertDialog.Builder builder;
    private final double version = 1.2d;

    public Version() {
        ApiConnect p = new ApiConnect("work/version", "", this);
        p.execute(new String[0]);
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        if (json instanceof JSONObject) {
            Version_base ver = (Version_base) gson.fromJson(output, Version_base.class);
            if (ver.getVersion() != 1.2d) {
                AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext());
                this.builder = builder;
                builder.setMessage(Html.fromHtml(App.getAppContext().getString(R.string.version_dialog_alert_end), 63)).setTitle(App.getAppContext().getString(R.string.version_dialog_alert)).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() { // from class: com.smmlaba.system.Version.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = this.builder.create();
                alert.show();
            }
        }
    }
}
