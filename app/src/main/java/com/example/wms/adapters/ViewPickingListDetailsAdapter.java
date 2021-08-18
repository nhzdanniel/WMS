package com.example.wms.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.models.PickingListDetails;

import java.util.ArrayList;

public class ViewPickingListDetailsAdapter extends RecyclerView.Adapter<ViewPickingListDetailsAdapter.ViewHolder> implements Filterable {

    Context context;
    public ArrayList<PickingListDetails> pickingListDetails = new ArrayList<>();
    ArrayList<PickingListDetails> masterPickingListDetails;

    private OnPickingListDetailsListener mOnPickingListDetailsListener;

    public interface OnPickingListDetailsListener {
        //void onPickingListDetailsClick (int position);
        void onScanClick (int position);
    }

    public void setOnItemClickListener(OnPickingListDetailsListener onPickingListDetailsListener){
        mOnPickingListDetailsListener = onPickingListDetailsListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_sn, tv_location, tv_upc, tv_product_name, tv_sku;
        EditText tv_sku_scanned;
        public Button scanButton;
        OnPickingListDetailsListener onPickingListDetailsListener;

        public ViewHolder(@NonNull View itemView, OnPickingListDetailsListener onPickingListDetailsListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_upc = itemView.findViewById(R.id.tv_upc);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_sku = itemView.findViewById(R.id.tv_sku);
            tv_sku_scanned = itemView.findViewById(R.id.tv_sku_scanned);
            scanButton = itemView.findViewById(R.id.scanButton);

            Log.d("tvsku", String.valueOf(tv_sku));

            //added
            tv_sku_scanned.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    pickingListDetails.get(getAdapterPosition()).setSkuScanned(tv_sku_scanned.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            scanButton.findViewById(R.id.scanButton).setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v) {
                    if (onPickingListDetailsListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onPickingListDetailsListener.onScanClick(position);
                        }
                    }
                    Log.d("scanning", "scanning for " + getAdapterPosition());
                }
            });
        }
    }

    public ViewPickingListDetailsAdapter (ArrayList<PickingListDetails>pickingListDetails){
        this.pickingListDetails.clear();
        this.pickingListDetails.addAll(pickingListDetails);
    }

    public void updateList (int position, String barcodeResult){
        if (position != -1 && barcodeResult != null || barcodeResult != ""){
            PickingListDetails pld = this.pickingListDetails.get(position);
            pld.skuScanned = barcodeResult;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewPickingListDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picking_list_details_layout,parent,false);
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
            //notifyItemChanged(holder.getAdapterPosition());

        }
    }

    @Override
    public int getItemCount() {
        return pickingListDetails.size();
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



}
