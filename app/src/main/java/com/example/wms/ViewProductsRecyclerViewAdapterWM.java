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

public class ViewProductsRecyclerViewAdapterWM extends RecyclerView.Adapter<ViewProductsRecyclerViewAdapterWM.ViewHolder> implements Filterable {

    Context context;
    private static final String TAG = "ViewProductsRecyclerViewAdapterWM";

    ArrayList<Product> productlist;
    ArrayList<Product> masterProductList;

    public ViewProductsRecyclerViewAdapterWM(Context context, ArrayList<Product> productlist) {
        this.context = context;
        this.masterProductList = productlist;
        this.productlist = new ArrayList<>(masterProductList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProductsRecyclerViewAdapterWM.ViewHolder holder, int position) {
        if (productlist != null && productlist.size() > 0) {
            Product product = productlist.get(position);
            holder.tv_name.setText(product.getName());
            holder.tv_quantity.setText(product.getQuantity());
            holder.tv_location.setText(product.getLocation());
            holder.tv_dimensions.setText(product.getDimension());
            holder.tv_id.setText(product.getId());
            holder.tv_uom.setText(product.getUom());
            holder.tv_weight.setText(product.getWeight());
        }
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    @Override
    public Filter getFilter() {
        return productsFilter;
    }
    private final Filter productsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product> filteredProductsList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredProductsList.addAll(masterProductList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product product : masterProductList){
                    if (product.id.toLowerCase().contains(filterPattern) || product.name.toLowerCase().contains(filterPattern) ||
                            product.location.toLowerCase().contains(filterPattern)||product.quantity.toLowerCase().contains(filterPattern) ||
                            product.uom.toLowerCase().contains(filterPattern) || product.weight.toLowerCase().contains(filterPattern) ||
                            product.dimension.toLowerCase().contains(filterPattern)){
                        filteredProductsList.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredProductsList;
            results.count = filteredProductsList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productlist.clear();
            productlist.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productPic;
        TextView tv_name, tv_quantity, tv_location, tv_dimensions, tv_id, tv_uom, tv_weight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productPic = itemView.findViewById(R.id.productPic);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_dimensions = itemView.findViewById(R.id.tv_dimensions);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_uom = itemView.findViewById(R.id.tv_uom);
            tv_weight = itemView.findViewById(R.id.tv_weight);
        }
    }
}
