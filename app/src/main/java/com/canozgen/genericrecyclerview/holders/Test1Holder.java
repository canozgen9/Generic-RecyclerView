package com.canozgen.genericrecyclerview.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.canozgen.genericrecyclerview.R;
import com.canozgen.genericrecyclerview.items.Item1;
import com.canozgen.genericrv.viewholders.GRViewHolder;
import com.canozgen.genericrv.viewholders.GRViewHolderClickEventListener;

public class Test1Holder extends GRViewHolder<Item1> {
    private TextView textView;

    public Test1Holder(@NonNull View itemView, GRViewHolderClickEventListener clickEventListener) {
        super(itemView, clickEventListener);
    }

    @Override public void init() {
        textView = (TextView) init(R.id.TextView);
    }

    @Override public void assign() {
        assignClickEvent(textView);
    }

    @Override public void bind(Item1 item, int position) {
        textView.setText(item.getText());
    }


}
