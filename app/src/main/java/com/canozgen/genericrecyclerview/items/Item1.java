package com.canozgen.genericrecyclerview.items;


import com.canozgen.genericrv.items.GRItem;

public class Item1 implements GRItem {
    private String text;

    public Item1(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Item1 setText(String text) {
        this.text = text;
        return this;
    }
}
