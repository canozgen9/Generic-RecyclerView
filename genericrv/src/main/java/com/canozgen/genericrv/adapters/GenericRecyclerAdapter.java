package com.canozgen.genericrv.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.canozgen.genericrv.items.GenericRecyclerItem;
import com.canozgen.genericrv.viewholders.GenericViewHolder;
import com.canozgen.genericrv.viewholders.GenericViewHolderBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericRecyclerAdapter<T extends GenericRecyclerItem> extends RecyclerView.Adapter<GenericViewHolder<T>> {
    public static int ITEM_TYPE = 0;

    private ArrayList<T> items = new ArrayList<>();
    private Context context;
    private GenericRecyclerClickListener<T> recyclerClickListener;


    private HashMap<Integer, HolderWrapper> holders = new HashMap<>();
    private HashMap<Class<? extends T>, Integer> viewTypes = new HashMap<>();

    public GenericRecyclerAdapter<T> context(Context context) {
        this.context = context;
        return this;
    }

    public GenericRecyclerAdapter<T> holder(Class<? extends T> itemClass, Class<? extends GenericViewHolder<? extends T>> viewHolderClass, int resourceID, int spanCount) {
        int viewType = ITEM_TYPE++;
        viewTypes.put(itemClass, viewType);
        holders.put(viewType, new HolderWrapper().setHolderClass(viewHolderClass).setResourceID(resourceID).setSpanCount(spanCount));
        return this;
    }

    public GenericRecyclerAdapter<T> listener(GenericRecyclerClickListener<T> recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
        return this;
    }

    public void set(ArrayList<T> items) {
        this.items.clear();
        this.items.addAll(items);
        this.notifyItemRangeInserted(0, items.size());
    }

    public void add(ArrayList<T> items) {
        this.items.addAll(items);
        this.notifyItemRangeInserted(this.items.size() + 1, items.size());
    }

    public void add(T item) {
        this.items.add(item);
        this.notifyItemInserted(this.items.size() - 1);
    }

    public void remove() {
        this.items.clear();
        this.notifyItemRangeRemoved(0, this.items.size());
    }

    public void remove(int index) {
        this.items.remove(index);
        this.notifyItemRemoved(index);
    }

    public void remove(T item) {
        int index = this.items.indexOf(item);
        this.items.remove(index);
        this.notifyItemRemoved(index);
    }

    public void update(T item) {
        int index = this.items.indexOf(item);
        this.notifyItemChanged(index);
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public int getSpanCount(int position) {
        return holders.get(viewTypes.get(items.get(position).getClass())).getSpanCount();
    }

    @NonNull @Override public GenericViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        HolderWrapper holderWrapper = holders.get(i);
        return new GenericViewHolderBuilder<GenericViewHolder<T>>().typeOf(holderWrapper.getHolderClass()).withContext(context).withLayout(holderWrapper.getResourceID()).build(viewGroup, (position, clickEventCode) -> {
            if (recyclerClickListener != null) {
                recyclerClickListener.onClick(items.get(position), position, clickEventCode);
            }
        });
    }

    @Override public void onBindViewHolder(@NonNull GenericViewHolder<T> genericViewHolder, int i) {
        genericViewHolder.bind(items.get(i), i);
    }

    @Override public int getItemCount() {
        return items.size();
    }


    @Override public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    private class HolderWrapper {
        private Class<? extends GenericViewHolder> holderClass;
        private int ResourceID;
        private int spanCount;

        public Class<? extends GenericViewHolder> getHolderClass() {
            return holderClass;
        }

        public HolderWrapper setHolderClass(Class<? extends GenericViewHolder> holderClass) {
            this.holderClass = holderClass;
            return this;
        }

        public int getResourceID() {
            return ResourceID;
        }

        public HolderWrapper setResourceID(int resourceID) {
            ResourceID = resourceID;
            return this;
        }

        public int getSpanCount() {
            return spanCount;
        }

        public HolderWrapper setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }
    }
}
