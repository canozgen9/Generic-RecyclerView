package com.canozgen.genericrecyclerview.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.canozgen.genericrecyclerview.R;
import com.canozgen.genericrecyclerview.items.Item3;
import com.canozgen.genericrv.viewholders.GRViewHolder;
import com.canozgen.genericrv.viewholders.GRViewHolderClickEventListener;

public class Test3Holder extends GRViewHolder<Item3> {

    private TextView title, description;

    public Test3Holder(@NonNull View itemView, GRViewHolderClickEventListener clickEventListener) {
        super(itemView, clickEventListener);
    }

    @Override public void bind(Item3 item, int position) {
        title.setText(item.getTitle());
        description.setText(item.getDescription());
    }

    @Override public void init() {
        title = (TextView) init(R.id.Title);
        description = (TextView) init(R.id.Description);
    }

    @Override public void assign() {
        assignClickEvent(title);
        assignClickEvent(description);
    }
}
