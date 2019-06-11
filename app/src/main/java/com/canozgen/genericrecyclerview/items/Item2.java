package com.canozgen.genericrecyclerview.items;


import com.canozgen.genericrv.items.GRItem;

public class Item2 implements GRItem {
    private String text;

    public Item2(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Item2 setText(String text) {
        this.text = text;
        return this;
    }
}
