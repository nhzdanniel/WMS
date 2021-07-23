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
import com.example.wms.models.ApprovedReceivedListDetails;
import com.example.wms.models.ReceivingListDetails;

import java.util.ArrayList;

public class ViewApprovedReceivedListDetailsAdapter extends RecyclerView.Adapter<ViewApprovedReceivedListDetailsAdapter.ViewHolder> implements Filterable {

    Context context;

    ArrayList<ApprovedReceivedListDetails> approvedReceivedListDetails;
    ArrayList<ApprovedReceivedListDetails> masterApprovedReceivedListDetails;

    private OnApprovedReceivedListDetailsListener mOnApprovedReceivedListDetailsListener;

    public ViewApprovedReceivedListDetailsAdapter(Context context, ArrayList<ApprovedReceivedListDetails> approvedReceivedListDetails, OnApprovedReceivedListDetailsListener onApprovedReceivedListDetailsListener) {
        this.context = context;
        this.masterApprovedReceivedListDetails = approvedReceivedListDetails;
        this.approvedReceivedListDetails = new ArrayList<>(masterApprovedReceivedListDetails);
        this.mOnApprovedReceivedListDetailsListener = onApprovedReceivedListDetailsListener;
    }

    @Override
    public Filter getFilter() {
        return approvedReceivedListDetailsFilter;
    }
    private final Filter approvedReceivedListDetailsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ApprovedReceivedListDetails> filteredApprovedReceivedListDetails = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredApprovedReceivedListDetails.addAll(masterApprovedReceivedListDetails);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ApprovedReceivedListDetails approvedReceivedListDetails : masterApprovedReceivedListDetails){
                    if (approvedReceivedListDetails.productName.toLowerCase().contains(filterPattern) || approvedReceivedListDetails.location.toLowerCase().contains(filterPattern) ||
                            String.valueOf(approvedReceivedListDetails.sn).contains(filterPattern) || String.valueOf(approvedReceivedListDetails.upc).contains(filterPattern) ||
                            approvedReceivedListDetails.sku.toLowerCase().contains(filterPattern)){
                        filteredApprovedReceivedListDetails.add(approvedReceivedListDetails);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredApprovedReceivedListDetails;
            results.count = filteredApprovedReceivedListDetails.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            approvedReceivedListDetails.clear();
            approvedReceivedListDetails.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewApprovedReceivedListDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.approved_received_list_details_layout,parent,false);
        return new ViewHolder(view, mOnApprovedReceivedListDetailsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewApprovedReceivedListDetailsAdapter.ViewHolder holder, int position) {
        if (approvedReceivedListDetails != null && approvedReceivedListDetails.size() > 0) {
            ApprovedReceivedListDetails arld = approvedReceivedListDetails.get(position);
            holder.tv_sn.setText(String.valueOf(arld.getSn()));
            holder.tv_location.setText(String.valueOf(arld.getLocation()));
            holder.tv_product_name.setText(String.valueOf(arld.getProductName()));
            holder.tv_upc.setText(String.valueOf(arld.getUpc()));
            holder.tv_sku.setText(String.valueOf(arld.getSku()));

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
        return approvedReceivedListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_sn, tv_location, tv_product_name, tv_upc, tv_sku;
        OnApprovedReceivedListDetailsListener onApprovedReceivedListDetailsListener;

        public ViewHolder(@NonNull View itemView, OnApprovedReceivedListDetailsListener onApprovedReceivedListDetailsListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_upc = itemView.findViewById(R.id.tv_upc);
            tv_sku = itemView.findViewById(R.id.tv_sku);

            this.onApprovedReceivedListDetailsListener = onApprovedReceivedListDetailsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onApprovedReceivedListDetailsListener.onApprovedReceivedListDetailsClick(getAdapterPosition());
        }
    }

    public interface OnApprovedReceivedListDetailsListener {
        void onApprovedReceivedListDetailsClick (int position);
    }
}
