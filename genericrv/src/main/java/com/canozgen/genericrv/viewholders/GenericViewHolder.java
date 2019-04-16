package com.canozgen.genericrv.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.canozgen.genericrv.items.GenericRecyclerItem;


public abstract class GenericViewHolder<T extends GenericRecyclerItem> extends RecyclerView.ViewHolder {
    private ViewHolderClickEventListener clickEventListener;

    public GenericViewHolder(@NonNull View itemView, ViewHolderClickEventListener clickEventListener) {
        super(itemView);
        this.clickEventListener = clickEventListener;
        init();
        assign();
    }

    public abstract void bind(T item, int position);

    public abstract void init();

    public abstract void assign();

    protected View init(int id) {
        return itemView.findViewById(id);
    }

    protected void assignClickEvent(View v, int clickEventCode) {
        v.setOnClickListener(view -> {
            clickEventListener.onClickEvent(getAdapterPosition(), clickEventCode);
        });
    }

    protected void assignClickEvent(View v) {
        assignClickEvent(v, v.getId());
    }

    protected void assignLongClickEvent(View v, int clickEventCode) {
        v.setOnLongClickListener(view -> {
            clickEventListener.onClickEvent(getAdapterPosition(), clickEventCode);
            return true;
        });
    }

    protected void assignLongClickEvent(View v) {
        assignLongClickEvent(v, v.getId());
    }

    protected void triggerClickEvent(int clickEventCode) {
        clickEventListener.onClickEvent(getAdapterPosition(), clickEventCode);
    }

    protected void triggerClickEvent(View v) {
        clickEventListener.onClickEvent(getAdapterPosition(), v.getId());
    }
}
