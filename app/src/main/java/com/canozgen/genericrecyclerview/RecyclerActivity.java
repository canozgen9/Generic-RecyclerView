package com.canozgen.genericrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.canozgen.genericrecyclerview.holders.Test1Holder;
import com.canozgen.genericrecyclerview.holders.Test2Holder;
import com.canozgen.genericrecyclerview.holders.Test3Holder;
import com.canozgen.genericrecyclerview.items.Item1;
import com.canozgen.genericrecyclerview.items.Item2;
import com.canozgen.genericrecyclerview.items.Item3;
import com.canozgen.genericrv.GRView;
import com.canozgen.genericrv.items.GRItem;


public class RecyclerActivity extends AppCompatActivity {
    public static final String TAG = "RecyclerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        GRView<GRItem> recyclerView = findViewById(R.id.RecyclerView);
        EditText etxtSearch = findViewById(R.id.etxtSearch);

        recyclerView.init(RecyclerActivity.this)
                .search(etxtSearch, (query, item) -> {
//                    Log.d(TAG, "onCreate: query: " + query);
                    if (item instanceof Item1) {
                        return ((Item1) item).getText().toUpperCase().contains(query);
                    } else if (item instanceof Item2) {
                        return ((Item2) item).getText().toUpperCase().contains(query);
                    } else if (item instanceof Item3) {
                        return ((Item3) item).getTitle().toUpperCase().contains(query);
                    } else {
                        return false;
                    }
                })
                .listener((item, position, clickEventCode) -> {
                    Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
                })
                .layout().grid().span(6)
                .addView().item(Item1.class).holder(Test1Holder.class).layout(R.layout.item_holder_1).span(1)
                .addView().item(Item2.class).holder(Test2Holder.class).layout(R.layout.item_holder_2).span(4)
                .addView().item(Item3.class).holder(Test3Holder.class).layout(R.layout.item_holder_3).span(5)
                .build();


        for (int i = 0; i < 50; i++) {
            recyclerView.add(new Item1("Item 1 " + i));
            recyclerView.add(new Item2("Item 2 " + i));
            recyclerView.add(new Item3("Item 3 " + i, "Desc"));
        }
    }

}
