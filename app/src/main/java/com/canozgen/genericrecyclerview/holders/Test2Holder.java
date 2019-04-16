package com.canozgen.genericrecyclerview.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.canozgen.genericrecyclerview.R;
import com.canozgen.genericrecyclerview.items.Item2;
import com.canozgen.genericrv.viewholders.GenericViewHolder;
import com.canozgen.genericrv.viewholders.ViewHolderClickEventListener;

public class Test2Holder extends GenericViewHolder<Item2> {
    private TextView textView;

    public Test2Holder(@NonNull View itemView, ViewHolderClickEventListener clickEventListener) {
        super(itemView, clickEventListener);
    }

    @Override public void init() {
        textView = (TextView) init(R.id.TextView);
    }

    @Override public void assign() {
        assignClickEvent(textView);
    }

    @Override public void bind(Item2 item, int position) {
        textView.setText(item.getText());
    }
}
