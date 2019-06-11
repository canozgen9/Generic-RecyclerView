package com.canozgen.genericrecyclerview.items;

import com.canozgen.genericrv.items.GRItem;

public class Item3 implements GRItem {
    private String title;
    private String description;

    public Item3(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Item3 setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item3 setDescription(String description) {
        this.description = description;
        return this;
    }
}
