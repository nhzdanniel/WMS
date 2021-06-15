package com.example.wms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewPickingListsRecyclerViewAdapterPP extends RecyclerView.Adapter<ViewPickingListsRecyclerViewAdapterPP.ViewHolder> implements Filterable {

    Context context;
    private static final String TAG = "ViewPickingListsRecyclerViewAdapterPP";

    ArrayList<PickingList> pickingList;
    ArrayList<PickingList> masterPickingList;

    public ViewPickingListsRecyclerViewAdapterPP (Context context, ArrayList<PickingList> pickingList){
        this.context = context;
        this.masterPickingList = pickingList;
        this.pickingList = new ArrayList<>(masterPickingList);
    }

    @NonNull
    @Override
    public ViewPickingListsRecyclerViewAdapterPP.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picking_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPickingListsRecyclerViewAdapterPP.ViewHolder holder, int position) {
        if (pickingList != null && pickingList.size() > 0) {
            PickingList pl = pickingList.get(position);
            holder.tv_po_number.setText(pl.getPoNumber());
            holder.tv_company_name.setText(pl.getCompanyName());
            holder.tv_date.setText(pl.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return pickingList.size();
    }

    @Override
    public Filter getFilter() {
        return pickingListFilter;
    }
    private final Filter pickingListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PickingList> filteredPickingList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredPickingList.addAll(masterPickingList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PickingList pickingList : masterPickingList){
                    if (pickingList.poNumber.toLowerCase().contains(filterPattern) || pickingList.companyName.toLowerCase().contains(filterPattern) ||
                            pickingList.Date.toLowerCase().contains(filterPattern)){
                        filteredPickingList.add(pickingList);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredPickingList;
            results.count = filteredPickingList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pickingList.clear();
            pickingList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_po_number, tv_company_name, tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

}
