package com.canozgen.genericrv.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import com.canozgen.genericrv.GRFilter;
import com.canozgen.genericrv.items.GRItem;
import com.canozgen.genericrv.viewholders.GRViewHolder;
import com.canozgen.genericrv.viewholders.GRViewHolderBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class GRAdapter<T extends GRItem> extends RecyclerView.Adapter<GRViewHolder<T>> implements Filterable {
    public static int ITEM_TYPE = 0;

    private ArrayList<T> rootItems = new ArrayList<>();
    private ArrayList<T> items = new ArrayList<>();
    private Context context;
    private GRClickListener<T> recyclerClickListener;

    private GRFilter<T> GRFilter = new GRFilter<>(this);

    private HashMap<Integer, GHolderWrapper> holders = new HashMap<>();
    private HashMap<Class<? extends T>, Integer> viewTypes = new HashMap<>();

    public GRAdapter<T> context(Context context) {
        this.context = context;
        return this;
    }

    public GRAdapter<T> holder(Class<? extends T> itemClass, Class<? extends GRViewHolder<? extends T>> viewHolderClass, int resourceID, int spanCount) {
        int viewType = ITEM_TYPE++;
        viewTypes.put(itemClass, viewType);
        holders.put(viewType, new GHolderWrapper().setHolderClass(viewHolderClass).setResourceID(resourceID).setSpanCount(spanCount));
        return this;
    }

    public GRAdapter<T> listener(GRClickListener<T> recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
        return this;
    }

    public void set(ArrayList<T> items) {
        this.set(items, true);
    }

    public void set(ArrayList<T> items, boolean overrideRootItems) {
        if (overrideRootItems) {
            this.rootItems.clear();
            this.rootItems.addAll(items);
        }

        this.items.clear();
        this.items.addAll(items);
        this.notifyItemRangeInserted(0, items.size());
    }

    public void add(ArrayList<T> items) {
        this.rootItems.addAll(items);

        this.items.addAll(items);
        this.notifyItemRangeInserted(this.items.size() + 1, items.size());
    }

    public void add(T item) {
        this.rootItems.add(item);

        this.items.add(item);
        this.notifyItemInserted(this.items.size() - 1);
    }

    public void remove() {
        this.rootItems.clear();

        this.items.clear();
        this.notifyItemRangeRemoved(0, this.items.size());
    }

    public void remove(int index) {
        T item = this.items.get(index);
        this.rootItems.remove(item);
        this.items.remove(index);

        this.notifyItemRemoved(index);
    }

    public void remove(T item) {
        this.rootItems.remove(item);

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

    @NonNull @Override public GRViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GHolderWrapper GHolderWrapper = holders.get(i);
        return new GRViewHolderBuilder<GRViewHolder<T>>().typeOf(GHolderWrapper.getHolderClass()).withContext(context).withLayout(GHolderWrapper.getResourceID()).build(viewGroup, (position, clickEventCode) -> {
            if (recyclerClickListener != null) {
                recyclerClickListener.onClick(items.get(position), position, clickEventCode);
            }
        });
    }

    @Override public void onBindViewHolder(@NonNull GRViewHolder<T> GRViewHolder, int i) {
        GRViewHolder.bind(items.get(i), i);
    }

    @Override public int getItemCount() {
        return items.size();
    }


    @Override public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    @Override public Filter getFilter() {
        return GRFilter;
    }

    public ArrayList<T> getRootItems() {
        return rootItems;
    }

    public GRFilter<T> getGRFilter() {
        return GRFilter;
    }

    private class GHolderWrapper {
        private Class<? extends GRViewHolder> holderClass;
        private int ResourceID;
        private int spanCount;

        public Class<? extends GRViewHolder> getHolderClass() {
            return holderClass;
        }

        public GHolderWrapper setHolderClass(Class<? extends GRViewHolder> holderClass) {
            this.holderClass = holderClass;
            return this;
        }

        public int getResourceID() {
            return ResourceID;
        }

        public GHolderWrapper setResourceID(int resourceID) {
            ResourceID = resourceID;
            return this;
        }

        public int getSpanCount() {
            return spanCount;
        }

        public GHolderWrapper setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }
    }
}
