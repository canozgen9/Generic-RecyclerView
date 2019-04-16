package com.canozgen.genericrv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


import com.canozgen.genericrv.adapters.GenericRecyclerAdapter;
import com.canozgen.genericrv.adapters.GenericRecyclerClickListener;
import com.canozgen.genericrv.items.GenericRecyclerItem;
import com.canozgen.genericrv.props.GenericDecoration;
import com.canozgen.genericrv.props.GenericLayout;
import com.canozgen.genericrv.props.GenericOrientation;
import com.canozgen.genericrv.viewholders.GenericViewHolder;

import java.util.ArrayList;
import java.util.Objects;

public class GenericRecyclerView<T extends GenericRecyclerItem> extends RecyclerView {


    private GenericDecoration decoration = GenericDecoration.NONE;
    private GenericLayout layout = GenericLayout.LINEAR;
    private GenericOrientation orientation = GenericOrientation.VERTICAL;
    private boolean reverseLayout;
    private int spanCount = 1;
    private GenericRecyclerAdapter<T> genericRecyclerAdapter;

    public GenericRecyclerView(@NonNull Context context) {
        super(context);
    }

    public GenericRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenericRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public GenericRecyclerAdapter<T> getGenericAdapter() {
        return genericRecyclerAdapter;
    }


    public void set(ArrayList<T> items) {
        this.genericRecyclerAdapter.set(items);
    }

    public void add(ArrayList<T> items) {
        this.genericRecyclerAdapter.add(items);
    }

    public void add(T item) {
        this.genericRecyclerAdapter.add(item);
    }

    public void remove() {
        this.genericRecyclerAdapter.remove();
    }

    public void remove(int index) {
        this.genericRecyclerAdapter.remove(index);
    }

    public void remove(T item) {
        this.genericRecyclerAdapter.remove(item);
    }

    public void update(T item) {
        this.genericRecyclerAdapter.update(item);
    }

    public ArrayList<T> getItems() {
        return this.genericRecyclerAdapter.getItems();
    }

    /**
     * BUILDER
     */

    public GenericRecyclerView<T> init(Context context) {
        this.genericRecyclerAdapter = new GenericRecyclerAdapter<>();
        this.genericRecyclerAdapter.context(context);
        return this;
    }

    public GenericLayoutBuilder layout() {
        return new GenericLayoutBuilder();
    }

    public GenericRecyclerView<T> listener(GenericRecyclerClickListener<T> recyclerClickListener) {
        this.genericRecyclerAdapter.listener(recyclerClickListener);
        return this;
    }

    public class GenericLayoutBuilder {
        public GenericLinearLayoutBuilder linear() {
            return new GenericLinearLayoutBuilder();
        }

        public GenericGridLayoutBuilder grid() {
            return new GenericGridLayoutBuilder();
        }
    }

    public class GenericLinearLayoutBuilder extends GenericLayoutBuilder {

        private GenericOrientation orientation = GenericOrientation.VERTICAL;
        private boolean reverse = false;
        private GenericDecoration decoration = GenericDecoration.NONE;

        public GenericLinearLayoutBuilder orientation(GenericOrientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public GenericLinearLayoutBuilder reverse(boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        public GenericLinearLayoutBuilder decoration(GenericDecoration decoration) {
            this.decoration = decoration;
            return this;
        }

        public GenericViewHolderBuilder addView() {
            switch (orientation) {
                case VERTICAL:
                    setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, reverse));
                    break;
                case HORIZONTAL:
                    setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, reverse));
                    break;
            }

            // Build decoration
            if (decoration != GenericDecoration.NONE) {
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

            return new GenericViewHolderBuilder();
        }
    }

    public class GenericGridLayoutBuilder extends GenericLayoutBuilder {
        private GenericOrientation orientation = GenericOrientation.VERTICAL;
        private int spanCount = 1;
        private boolean reverse = false;
        //Todo: decoration

        public GenericGridLayoutBuilder orientation(GenericOrientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public GenericGridLayoutBuilder span(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public GenericGridLayoutBuilder reverse(boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        public GenericViewHolderBuilder addView() {
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
                    return genericRecyclerAdapter.getSpanCount(position);
                }
            });
            return new GenericViewHolderBuilder();
        }
    }

    public class GenericViewHolderBuilder {
        Class<? extends T> itemClass;
        Class<? extends GenericViewHolder<? extends T>> viewHolderClass;
        int resourceID;
        int spanCount = 1;

        public GenericViewHolderBuilder item(Class<? extends T> itemClass) {
            this.itemClass = itemClass;
            return this;
        }

        public GenericViewHolderBuilder holder(Class<? extends GenericViewHolder<? extends T>> viewHolderClass) {
            this.viewHolderClass = viewHolderClass;
            return this;
        }

        public GenericViewHolderBuilder layout(int resourceID) {
            this.resourceID = resourceID;
            return this;
        }

        public GenericViewHolderBuilder span(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public GenericViewHolderBuilder addView() {
            genericRecyclerAdapter.holder(itemClass, viewHolderClass, resourceID, spanCount);
            return new GenericViewHolderBuilder();
        }

        public GenericRecyclerView<T> build() {
            genericRecyclerAdapter.holder(itemClass, viewHolderClass, resourceID, spanCount);
            setAdapter(genericRecyclerAdapter);
            return GenericRecyclerView.this;
        }
    }
}
