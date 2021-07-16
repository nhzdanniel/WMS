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
import com.example.wms.models.PickingList;
import com.example.wms.models.ReceivingList;

import java.util.ArrayList;

public class ViewAllReceivingListAdapter extends RecyclerView.Adapter<ViewAllReceivingListAdapter.ViewHolder> implements Filterable {
    Context context;

    ArrayList<ReceivingList> receivingList;
    ArrayList<ReceivingList> masterReceivingList;

    private OnReceivingListListener mOnReceivingListListener;

    public ViewAllReceivingListAdapter (Context context, ArrayList<ReceivingList> receivingList, OnReceivingListListener onReceivingListListener){
        this.context = context;
        this.masterReceivingList = receivingList;
        this.receivingList = new ArrayList<>(masterReceivingList);
        this.mOnReceivingListListener = onReceivingListListener;
    }

    @NonNull
    @Override
    public ViewAllReceivingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receiving_list_layout,parent,false);
        return new ViewHolder(view, mOnReceivingListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllReceivingListAdapter.ViewHolder holder, int position) {
        if (receivingList != null && receivingList.size() > 0) {
            ReceivingList rl = receivingList.get(position);
            holder.tv_sn.setText(String.valueOf(rl.getSn()));
            holder.tv_po_number.setText(String.valueOf(rl.getPoNumber()));
            holder.tv_supplier_name.setText(rl.getSupplierName());
            holder.tv_eta.setText(rl.getEta());
            holder.tv_status.setText(rl.getStatus());

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
        return receivingList.size();
    }

    @Override
    public Filter getFilter() {
        return receivingListFilter;
    }
    private final Filter receivingListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ReceivingList> filteredReceivingList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredReceivingList.addAll(masterReceivingList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ReceivingList receivingList : masterReceivingList){
                    if (String.valueOf(receivingList.poNumber).contains(filterPattern) || receivingList.supplierName.toLowerCase().contains(filterPattern) ||
                            String.valueOf(receivingList.status).contains(filterPattern)){
                        filteredReceivingList.add(receivingList);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredReceivingList;
            results.count = filteredReceivingList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            receivingList.clear();
            receivingList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_sn, tv_po_number, tv_supplier_name, tv_eta, tv_status;
        OnReceivingListListener onReceivingListListener;
        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView, OnReceivingListListener onReceivingListListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_po_number = itemView.findViewById(R.id.tv_po_number);
            tv_supplier_name = itemView.findViewById(R.id.tv_supplier_name);
            tv_eta = itemView.findViewById(R.id.tv_eta);
            tv_status = itemView.findViewById(R.id.tv_status);
            this.onReceivingListListener = onReceivingListListener;
            //parentLayout = itemView.findViewById(R.id.pickingListLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onReceivingListListener.onReceivingListClick(getAdapterPosition());
        }
    }

    public interface OnReceivingListListener{
        void onReceivingListClick(int position);
    }
}
