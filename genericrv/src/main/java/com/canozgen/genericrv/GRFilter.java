package com.canozgen.genericrv;

import android.util.Log;
import android.widget.Filter;

import com.canozgen.genericrv.adapters.GRAdapter;
import com.canozgen.genericrv.items.GRItem;

import java.util.ArrayList;

import static com.canozgen.genericrv.GRView.TAG;

public class GRFilter<T extends GRItem> extends Filter {

    private GRFilterListener<T> GRFilterListener;
    private GRAdapter<T> GRAdapter;

    public GRFilter(GRAdapter<T> GRAdapter) {
        this.GRAdapter = GRAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

//        Log.d(TAG, "performFiltering: started.");

        if (constraint != null && constraint.length() > 0) {

//            Log.d(TAG, "performFiltering: constraints are ok.");

            constraint = constraint.toString().toUpperCase();
            ArrayList<GRItem> filteredItems = new ArrayList<>();


            if (GRFilterListener != null) {
//                Log.d(TAG, "performFiltering: genericRecyclerFilterListener is ok.");

                for (int i = 0; i < GRAdapter.getRootItems().size(); i++) {
                    T genericRecyclerItem = GRAdapter.getRootItems().get(i);
                    if (GRFilterListener.filter(constraint.toString(), genericRecyclerItem)) {
                        filteredItems.add(genericRecyclerItem);
                    }
                }
            } else {
//                Log.d(TAG, "performFiltering: genericRecyclerFilterListener is null.");
            }

            results.count = filteredItems.size();
            results.values = filteredItems;
        } else {
            results.count = GRAdapter.getRootItems().size();
            results.values = GRAdapter.getRootItems();
        }

        return results;
    }

    @Override protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        try {
            GRAdapter.set((ArrayList<T>) filterResults.values, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GRAdapter.notifyDataSetChanged();
    }

    public interface GRFilterListener<K extends GRItem> {
        boolean filter(String query, K item);
    }

    public void setGRFilterListener(GRFilterListener<T> GRFilterListener) {
        this.GRFilterListener = GRFilterListener;
    }
}
