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
    ArrayList<ApprovedReceivedList> approvedReceivedList = new ArrayList<>();
    ArrayList<ApprovedReceivedList> masterApprovedReceivedList = new ArrayList<>();
    private OnApprovedReceivedListListener mOnApprovedReceivedListListener;

    public ViewAllApprovedReceivedListsAdapter (Context context, OnApprovedReceivedListListener onApprovedReceivedListListener){
        this.context = context;
        this.mOnApprovedReceivedListListener = onApprovedReceivedListListener;
    }

    public void submitList(ArrayList<ApprovedReceivedList> listItem){
        approvedReceivedList.clear();
        approvedReceivedList.addAll(listItem);
        masterApprovedReceivedList.clear();
        masterApprovedReceivedList.addAll(approvedReceivedList);
        notifyDataSetChanged();
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
            holder.bindArl(arl);
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
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ApprovedReceivedList approvedReceivedList : approvedReceivedList){
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
            ArrayList<ApprovedReceivedList> searchResult = (ArrayList<ApprovedReceivedList>) results.values;
            if (searchResult != null && ! searchResult.isEmpty()){
                approvedReceivedList.clear();
                approvedReceivedList.addAll(searchResult);
                notifyDataSetChanged();
            }
            else {
                approvedReceivedList.clear();
                approvedReceivedList.addAll(masterApprovedReceivedList);
                notifyDataSetChanged();
            }
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sn, tv_po_number, tv_do_number, tv_supplier_name, tv_eta;
        OnApprovedReceivedListListener onApprovedReceivedListListener;
        //ConstraintLayout parentLayout;

        public void bindArl (ApprovedReceivedList arl){
            tv_sn.setText(String.valueOf(arl.getSn()));
            tv_po_number.setText(String.valueOf(arl.getPoNumber()));
            tv_do_number.setText(String.valueOf(arl.getDoNumber()));
            tv_supplier_name.setText(arl.getSupplierName());
            tv_eta.setText(arl.getEta());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onApprovedReceivedListListener.onApprovedReceivedListClick(arl);
                }
            });
        }

        public ViewHolder(@NonNull View itemView, OnApprovedReceivedListListener onApprovedReceivedListListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_supplier_name = itemView.findViewById(R.id.tv_supplier_name);
            tv_eta = itemView.findViewById(R.id.tv_eta);
            tv_do_number = itemView.findViewById(R.id.tv_do_number);
            this.onApprovedReceivedListListener = onApprovedReceivedListListener;
        }
    }

    public interface OnApprovedReceivedListListener{
        void onApprovedReceivedListClick(ApprovedReceivedList arl);
    }
}
