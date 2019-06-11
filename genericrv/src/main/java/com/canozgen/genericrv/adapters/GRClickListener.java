package com.canozgen.genericrv.adapters;

public interface GRClickListener<T> {
    void onClick(T item, int position, int clickEventCode);
}
