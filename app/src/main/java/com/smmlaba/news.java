package com.smmlaba;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.smmlaba.Entinity.news.item;
import com.smmlaba.api.ApiConnect;
import com.smmlaba.api.Delay;
import com.smmlaba.api.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes9.dex */
public class news extends Fragment implements Delay {
    private ApiConnect connect;
    private View v;
    private List<item> it = new ArrayList();
    private int limit = 10;
    private boolean hasupload = false;

    static /* synthetic */ int access$312(news x0, int x1) {
        int i = x0.limit + x1;
        x0.limit = i;
        return i;
    }

    public static news newInstance() {
        news fragment = new news();
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.news, container, false);
        ApiConnect apiConnect = new ApiConnect("others/news", "", this);
        this.connect = apiConnect;
        apiConnect.execute(new String[0]);
        final ScrollView scrollView = (ScrollView) this.v.findViewById(R.id.scrollNews);
        scrollView.setScrollY(0);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.smmlaba.news.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                int diff = scrollView.getHeight() + scrollView.getScrollY();
                int bufs = scrollView.getChildAt(0).getHeight();
                if (Math.abs(diff) > bufs && news.this.it.size() == 10 && news.this.hasupload) {
                    news.this.exec();
                    news.access$312(news.this, 10);
                    news.this.hasupload = false;
                }
            }
        });
        return this.v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exec() {
        ApiConnect com2 = new ApiConnect("others/news", "limit=" + this.limit, this);
        com2.execute(new String[0]);
    }

    @Override // com.smmlaba.api.Delay
    public void after(String output) {
        LayoutInflater ltInflater = getLayoutInflater();
        Object json = Parser.JsonValidate(output);
        Gson gson = new Gson();
        LinearLayout linLayout = (LinearLayout) this.v.findViewById(R.id.newsList);
        this.it = new ArrayList();
        if (json instanceof JSONArray) {
            item[] item = (item[]) gson.fromJson(output, item[].class);
            ArrayList<item> arrayList = new ArrayList(Arrays.asList(item));
            this.it = arrayList;
            for (item newsItem : arrayList) {
                View op = ltInflater.inflate(R.layout.news_tem, (ViewGroup) linLayout, false);
                TextView title = (TextView) op.findViewById(R.id.title);
                TextView text = (TextView) op.findViewById(R.id.newsText);
                TextView date = (TextView) op.findViewById(R.id.date);
                title.setText(newsItem.getTitle());
                text.setText(Html.fromHtml(newsItem.getDescription()));
                date.setText(newsItem.getDate_added());
                linLayout.addView(op);
            }
            this.hasupload = true;
        }
    }
}
