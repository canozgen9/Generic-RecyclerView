package com.canozgen.genericrv.adapters;

public interface GenericRecyclerClickListener<T> {
    void onClick(T item, int position, int clickEventCode);
}
