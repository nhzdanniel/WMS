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

import com.example.wms.models.PickingList;
import com.example.wms.R;

import java.util.ArrayList;

public class ViewPickingListsRecyclerViewAdapterPP extends RecyclerView.Adapter<ViewPickingListsRecyclerViewAdapterPP.ViewHolder> implements Filterable {

    Context context;
    private static final String TAG = "ViewPickingListsRecyclerViewAdapterPP";

    ArrayList<PickingList> pickingList;
    ArrayList<PickingList> masterPickingList;

    private OnPickingListListener mOnPickingListListener;

    public ViewPickingListsRecyclerViewAdapterPP (Context context, ArrayList<PickingList> pickingList, OnPickingListListener onPickingListListener){
        this.context = context;
        this.masterPickingList = pickingList;
        this.pickingList = new ArrayList<>(masterPickingList);
        this.mOnPickingListListener = onPickingListListener;
    }

    @NonNull
    @Override
    public ViewPickingListsRecyclerViewAdapterPP.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picking_list_layout,parent,false);
        return new ViewHolder(view, mOnPickingListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPickingListsRecyclerViewAdapterPP.ViewHolder holder, int position) {
        if (pickingList != null && pickingList.size() > 0) {
            PickingList pl = pickingList.get(position);
            holder.tv_sn.setText(String.valueOf(pl.getSn()));
            holder.tv_po_number.setText(String.valueOf(pl.getSoNumber()));
            holder.tv_company_name.setText(pl.getCompanyName());
            holder.tv_date.setText(pl.getDate());

/*            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, )
                }
            });*/
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
                    if (String.valueOf(pickingList.soNumber).contains(filterPattern) || pickingList.companyName.toLowerCase().contains(filterPattern) ||
                            String.valueOf(pickingList.date).contains(filterPattern)){
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_sn, tv_po_number, tv_company_name, tv_date;
        OnPickingListListener onPickingListListener;

        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView, OnPickingListListener onPickingListListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            this.onPickingListListener = onPickingListListener;
            //parentLayout = itemView.findViewById(R.id.pickingListLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPickingListListener.onPickingListClick(getAdapterPosition());
        }
    }

    public interface OnPickingListListener{
        void onPickingListClick(int position);
    }

}
