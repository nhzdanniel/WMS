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

import com.example.wms.R;
import com.example.wms.ViewAllApprovedReceivedLists;
import com.example.wms.models.ApprovedReceivedList;
import com.example.wms.models.ReceivingList;

import java.util.ArrayList;

public class ViewAllApprovedReceivedListsAdapter extends RecyclerView.Adapter<ViewAllApprovedReceivedListsAdapter.ViewHolder> implements Filterable {

    Context context;

    ArrayList<ApprovedReceivedList> approvedReceivedList;
    ArrayList<ApprovedReceivedList> masterApprovedReceivedList;

    private OnApprovedReceivedListListener mOnApprovedReceivedListListener;

    public ViewAllApprovedReceivedListsAdapter (Context context, ArrayList<ApprovedReceivedList> approvedReceivedList, OnApprovedReceivedListListener onApprovedReceivedListListener){
        this.context = context;
        this.masterApprovedReceivedList = approvedReceivedList;
        this.approvedReceivedList = new ArrayList<>(masterApprovedReceivedList);
        this.mOnApprovedReceivedListListener = onApprovedReceivedListListener;
    }

    @NonNull
    @Override
    public ViewAllApprovedReceivedListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.approved_received_list_layout,parent,false);
        return new ViewHolder(view, mOnApprovedReceivedListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllApprovedReceivedListsAdapter.ViewHolder holder, int position) {
        if (approvedReceivedList != null && approvedReceivedList.size() > 0) {
            ApprovedReceivedList arl = approvedReceivedList.get(position);
            holder.tv_sn.setText(String.valueOf(arl.getSn()));
            holder.tv_po_number.setText(String.valueOf(arl.getPoNumber()));
            holder.tv_do_number.setText(String.valueOf(arl.getDoNumber()));
            holder.tv_supplier_name.setText(arl.getSupplierName());
            holder.tv_eta.setText(arl.getEta());

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
        return approvedReceivedList.size();
    }

    @Override
    public Filter getFilter() {
        return approvedReceivedListFilter;
    }
    private final Filter approvedReceivedListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ApprovedReceivedList> filteredApprovedReceivedList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredApprovedReceivedList.addAll(masterApprovedReceivedList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ApprovedReceivedList approvedReceivedList : masterApprovedReceivedList){
                    if (String.valueOf(approvedReceivedList.poNumber).contains(filterPattern) || approvedReceivedList.supplierName.toLowerCase().contains(filterPattern) ||
                            String.valueOf(approvedReceivedList.doNumber).contains(filterPattern)){
                        filteredApprovedReceivedList.add(approvedReceivedList);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredApprovedReceivedList;
            results.count = filteredApprovedReceivedList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            approvedReceivedList.clear();
            approvedReceivedList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_sn, tv_po_number, tv_do_number, tv_supplier_name, tv_eta;
        OnApprovedReceivedListListener onApprovedReceivedListListener;
        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView, OnApprovedReceivedListListener onApprovedReceivedListListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_supplier_name = itemView.findViewById(R.id.tv_supplier_name);
            tv_eta = itemView.findViewById(R.id.tv_eta);
            tv_do_number = itemView.findViewById(R.id.tv_do_number);
            this.onApprovedReceivedListListener = onApprovedReceivedListListener;
            //parentLayout = itemView.findViewById(R.id.pickingListLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onApprovedReceivedListListener.onApprovedReceivedListClick(getAdapterPosition());
        }
    }

    public interface OnApprovedReceivedListListener{
        void onApprovedReceivedListClick(int position);
    }
}
