package com.canozgen.genericrv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;


import com.canozgen.genericrv.adapters.GRAdapter;
import com.canozgen.genericrv.adapters.GRClickListener;
import com.canozgen.genericrv.items.GRItem;
import com.canozgen.genericrv.props.GRDecoration;
import com.canozgen.genericrv.props.GRLayout;
import com.canozgen.genericrv.props.GROrientation;
import com.canozgen.genericrv.viewholders.GRViewHolder;

import java.util.ArrayList;

public class GRView<T extends GRItem> extends RecyclerView {
    public static final String TAG = "GRView";

    private GRDecoration decoration = GRDecoration.NONE;
    private GRLayout layout = GRLayout.LINEAR;
    private GROrientation orientation = GROrientation.VERTICAL;
    private boolean reverseLayout;
    private int spanCount = 1;
    private GRAdapter<T> GRAdapter;

    public GRView(@NonNull Context context) {
        super(context);
    }

    public GRView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GRView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GRAdapter<T> getGenericAdapter() {
        return GRAdapter;
    }


    public void set(ArrayList<T> items) {
        this.GRAdapter.set(items);
    }

    public void add(ArrayList<T> items) {
        this.GRAdapter.add(items);
    }

    public void add(T item) {
        this.GRAdapter.add(item);
    }

    public void remove() {
        this.GRAdapter.remove();
    }

    public void remove(int index) {
        this.GRAdapter.remove(index);
    }

    public void remove(T item) {
        this.GRAdapter.remove(item);
    }

    public void update(T item) {
        this.GRAdapter.update(item);
    }

    public ArrayList<T> getItems() {
        return this.GRAdapter.getItems();
    }

    /**
     * BUILDER
     */

    public GRView<T> init(Context context) {
        this.GRAdapter = new GRAdapter<>();
        this.GRAdapter.context(context);
        return this;
    }

    public GRLayoutBuilder layout() {
        return new GRLayoutBuilder();
    }

    public GRView<T> listener(GRClickListener<T> recyclerClickListener) {
        this.GRAdapter.listener(recyclerClickListener);
        return this;
    }

    public GRView<T> search(EditText eTxt, GRFilter.GRFilterListener<T> filterListener) {
        this.GRAdapter.getGRFilter().setGRFilterListener(filterListener);
        eTxt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override public void afterTextChanged(Editable editable) {
                GRAdapter.getFilter().filter(editable.toString());
//                Log.d(TAG, "afterTextChanged: size:" + GRAdapter.getItems().size() + " root size:" + GRAdapter.getRootItems().size());
            }
        });
        return this;
    }

    public class GRLayoutBuilder {
        public GRLinearLayoutBuilder linear() {
            return new GRLinearLayoutBuilder();
        }

        public GRGridLayoutBuilder grid() {
            return new GRGridLayoutBuilder();
        }
    }

    public class GRLinearLayoutBuilder extends GRLayoutBuilder {

        private GROrientation orientation = GROrientation.VERTICAL;
        private boolean reverse = false;
        private GRDecoration decoration = GRDecoration.NONE;

        public GRLinearLayoutBuilder orientation(GROrientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public GRLinearLayoutBuilder reverse(boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        public GRLinearLayoutBuilder decoration(GRDecoration decoration) {
            this.decoration = decoration;
            return this;
        }

        public GRViewHolderBuilder addView() {
            switch (orientation) {
                case VERTICAL:
                    setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, reverse));
                    break;
                case HORIZONTAL:
                    setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, reverse));
                    break;
            }

            // Build decoration
            if (decoration != GRDecoration.NONE) {
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), ((LinearLayoutManager) getLayoutManager()).getOrientation());
                switch (decoration) {
                    case LIGHT:
                        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.generic_recycler_light_divider));
                        break;
                    case DARK:
                        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.generic_recycler_dark_divider));
                        break;
                }
                addItemDecoration(dividerItemDecoration);
            }

            return new GRViewHolderBuilder();
        }
    }

    public class GRGridLayoutBuilder extends GRLayoutBuilder {
        private GROrientation orientation = GROrientation.VERTICAL;
        private int spanCount = 1;
        private boolean reverse = false;
        //Todo: decoration

        public GRGridLayoutBuilder orientation(GROrientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public GRGridLayoutBuilder span(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public GRGridLayoutBuilder reverse(boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        public GRViewHolderBuilder addView() {
            switch (orientation) {
                case VERTICAL:
                    setLayoutManager(new GridLayoutManager(getContext(), spanCount, VERTICAL, reverse));
                    break;
                case HORIZONTAL:
                    setLayoutManager(new GridLayoutManager(getContext(), spanCount, HORIZONTAL, reverse));
                    break;
            }

            ((GridLayoutManager) getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override public int getSpanSize(int position) {
                    return GRAdapter.getSpanCount(position);
                }
            });
            return new GRViewHolderBuilder();
        }
    }

    public class GRViewHolderBuilder {
        Class<? extends T> itemClass;
        Class<? extends GRViewHolder<? extends T>> viewHolderClass;
        int resourceID;
        int spanCount = 1;

        public GRViewHolderBuilder item(Class<? extends T> itemClass) {
            this.itemClass = itemClass;
            return this;
        }

        public GRViewHolderBuilder holder(Class<? extends GRViewHolder<? extends T>> viewHolderClass) {
            this.viewHolderClass = viewHolderClass;
            return this;
        }

        public GRViewHolderBuilder layout(int resourceID) {
            this.resourceID = resourceID;
            return this;
        }

        public GRViewHolderBuilder span(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public GRViewHolderBuilder addView() {
            GRAdapter.holder(itemClass, viewHolderClass, resourceID, spanCount);
            return new GRViewHolderBuilder();
        }

        public GRView<T> build() {
            GRAdapter.holder(itemClass, viewHolderClass, resourceID, spanCount);
            setAdapter(GRAdapter);
            return GRView.this;
        }
    }
}
