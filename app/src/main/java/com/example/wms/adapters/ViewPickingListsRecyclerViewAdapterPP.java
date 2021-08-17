package com.example.wms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.models.PickingItem;
import com.example.wms.R;

import java.util.ArrayList;

public class ViewPickingListsRecyclerViewAdapterPP extends RecyclerView.Adapter<ViewPickingListsRecyclerViewAdapterPP.ViewHolder> implements Filterable {

    Context context;
    private static final String TAG = "ViewPickingListsRecyclerViewAdapterPP";

    ArrayList<PickingItem> currentList = new ArrayList<>();
    ArrayList<PickingItem> currentListBackup = new ArrayList<>();


    private OnPickingListListener mOnPickingListListener;

    public ViewPickingListsRecyclerViewAdapterPP (Context context, OnPickingListListener onPickingListListener){
        this.context = context;
        this.mOnPickingListListener = onPickingListListener;
    }

    public void submitList(ArrayList<PickingItem> listItems){
        currentList.clear();
        currentList.addAll(listItems);
        currentListBackup.clear();
        currentListBackup.addAll(currentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewPickingListsRecyclerViewAdapterPP.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picking_list_layout,parent,false);
        return new ViewHolder(view, mOnPickingListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPickingListsRecyclerViewAdapterPP.ViewHolder holder, int position) {
        if (currentList != null && currentList.size() > 0) {
            PickingItem pl = currentList.get(position);
            holder.bind(pl);
        }
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    @Override
    public Filter getFilter() {
        return pickingListFilter;
    }
    private final Filter pickingListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<PickingItem> filterResult = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                currentList.clear();
                currentList.addAll(currentListBackup);
                notifyDataSetChanged();

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PickingItem pickedItem : currentList){
                    if (String.valueOf(pickedItem.soNumber).contains(filterPattern) || pickedItem.companyName.toLowerCase().contains(filterPattern) ||
                            String.valueOf(pickedItem.date).contains(filterPattern)){
                        filterResult.add(pickedItem);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterResult;
            results.count = filterResult.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<PickingItem> searchResult = (ArrayList<PickingItem>) results.values;
            if (searchResult != null && ! searchResult.isEmpty()){
                currentList.clear();
                currentList.addAll(searchResult);
                notifyDataSetChanged();
            }
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_sn, tv_po_number, tv_company_name, tv_date;
        OnPickingListListener onPickingListListener;

        //ConstraintLayout parentLayout;

        public void bind (PickingItem pl){
            tv_sn.setText(String.valueOf(pl.getSn()));
            tv_po_number.setText(String.valueOf(pl.getSoNumber()));
            tv_company_name.setText(pl.getCompanyName());
            tv_date.setText(pl.getDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPickingListListener.onPickingListClick(pl);
                }
            });
        }

        public ViewHolder(@NonNull View itemView, OnPickingListListener onPickingListListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            this.onPickingListListener = onPickingListListener;
            //parentLayout = itemView.findViewById(R.id.pickingListLayout);

        }

    }

    public interface OnPickingListListener{
        void onPickingListClick(PickingItem pl);
    }

}
