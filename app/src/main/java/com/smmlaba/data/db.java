package com.smmlaba.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes10.dex */
public class db extends SQLiteOpenHelper {
    final int DB_VERSION;
    private SQLiteDatabase database;

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public db(Context context) {
        super(context, "smmlabaDB", (SQLiteDatabase.CursorFactory) null, 2);
        this.DB_VERSION = 2;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS  favorite (id integer primary key autoincrement,model text,product_id integer,url text,lowerprice text,name text);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("alter table favorite add column lowerprice text;");
        }
    }

    public void read() {
        Cursor c = this.database.query("smmlabaDB", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            c.getColumnIndex("id");
            c.getColumnIndex("model");
            c.getColumnIndex("product_id");
            while (c.moveToNext()) {
            }
            return;
        }
        c.close();
    }
}
