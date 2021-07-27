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
import com.example.wms.models.PickingListDetails;
import com.example.wms.models.ReceivingListDetails;

import java.util.ArrayList;

public class ViewPickingListDetailsAdapter extends RecyclerView.Adapter<ViewPickingListDetailsAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<PickingListDetails> pickingListDetails;
    ArrayList<PickingListDetails> masterPickingListDetails;

    private OnPickingListDetailsListener mOnPickingListDetailsListener;

    public ViewPickingListDetailsAdapter(Context context, ArrayList<PickingListDetails> pickingListDetails, OnPickingListDetailsListener onPickingListDetailsListener) {
        this.context = context;
        this.masterPickingListDetails = pickingListDetails;
        this.pickingListDetails = new ArrayList<>(masterPickingListDetails);
        this.mOnPickingListDetailsListener = onPickingListDetailsListener;
    }

    @Override
    public Filter getFilter() {
        return pickingListDetailsFilter;
    }
    private final Filter pickingListDetailsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PickingListDetails> filteredPickingListDetails = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredPickingListDetails.addAll(masterPickingListDetails);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PickingListDetails pickingListDetails : masterPickingListDetails){
                    if (pickingListDetails.productName.toLowerCase().contains(filterPattern) || String.valueOf(pickingListDetails.sn).contains(filterPattern) ||
                            String.valueOf(pickingListDetails.upc).contains(filterPattern) || String.valueOf(pickingListDetails.sku).contains(filterPattern) ||
                            pickingListDetails.location.toLowerCase().contains(filterPattern)){
                        filteredPickingListDetails.add(pickingListDetails);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredPickingListDetails;
            results.count = filteredPickingListDetails.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pickingListDetails.clear();
            pickingListDetails.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewPickingListDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picking_list_details_layout,parent,false);
        return new ViewHolder(view, mOnPickingListDetailsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPickingListDetailsAdapter.ViewHolder holder, int position) {
        if (pickingListDetails != null && pickingListDetails.size() > 0) {
            PickingListDetails pld = pickingListDetails.get(position);
            holder.tv_sn.setText(String.valueOf(pld.getSn()));
            holder.tv_location.setText(pld.getLocation());
            holder.tv_upc.setText(String.valueOf(pld.getUpc()));
            holder.tv_product_name.setText(pld.getProductName());
            holder.tv_sku.setText(String.valueOf(pld.getSku()));
            holder.tv_sku_scanned.setText(String.valueOf(pld.getSkuScanned()));

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
        return pickingListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_sn, tv_location, tv_upc, tv_product_name, tv_sku, tv_sku_scanned;
        OnPickingListDetailsListener onPickingListDetailsListener;

        public ViewHolder(@NonNull View itemView, OnPickingListDetailsListener onPickingListDetailsListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_upc = itemView.findViewById(R.id.tv_upc);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_sku = itemView.findViewById(R.id.tv_sku);
            tv_sku_scanned = itemView.findViewById(R.id.tv_sku_scanned);


            this.onPickingListDetailsListener = onPickingListDetailsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPickingListDetailsListener.onPickingListDetailsClick(getAdapterPosition());
        }
    }

    public interface OnPickingListDetailsListener {
        void onPickingListDetailsClick (int position);
    }
}
