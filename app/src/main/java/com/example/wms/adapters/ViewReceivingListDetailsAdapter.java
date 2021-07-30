package com.example.wms.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.IndividualReceivingList;
import com.example.wms.R;
import com.example.wms.models.ReceivingList;
import com.example.wms.models.ReceivingListDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewReceivingListDetailsAdapter extends RecyclerView.Adapter<ViewReceivingListDetailsAdapter.ViewHolder> implements Filterable {

    Context context;

    public ArrayList<ReceivingListDetails> receivingListDetails;
    ArrayList<ReceivingListDetails> masterReceivingListDetails;

    private OnReceivingListDetailsListener mOnReceivingListDetailsListener;

    public ViewReceivingListDetailsAdapter(Context context, ArrayList<ReceivingListDetails> receivingListDetails, OnReceivingListDetailsListener onReceivingListDetailsListener) {
        this.context = context;
        this.masterReceivingListDetails = receivingListDetails;
        this.receivingListDetails = new ArrayList<>(masterReceivingListDetails);
        this.mOnReceivingListDetailsListener = onReceivingListDetailsListener;
    }

    @Override
    public Filter getFilter() {
        return receivingListDetailsFilter;
    }
    private final Filter receivingListDetailsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ReceivingListDetails> filteredReceivingListDetails = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredReceivingListDetails.addAll(masterReceivingListDetails);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ReceivingListDetails receivingListDetails : masterReceivingListDetails){
                    if (receivingListDetails.productName.toLowerCase().contains(filterPattern) || String.valueOf(receivingListDetails.sn).contains(filterPattern) ||
                              String.valueOf(receivingListDetails.qtyOrdered).contains(filterPattern) ||
                            String.valueOf(receivingListDetails.qtyReceived).contains(filterPattern) || String.valueOf(receivingListDetails.qtyRemaining).contains(filterPattern)){
                        filteredReceivingListDetails.add(receivingListDetails);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredReceivingListDetails;
            results.count = filteredReceivingListDetails.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            receivingListDetails.clear();
            receivingListDetails.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewReceivingListDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receiving_list_details_layout,parent,false);
        return new ViewHolder(view, mOnReceivingListDetailsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewReceivingListDetailsAdapter.ViewHolder holder, int position) {
        if (receivingListDetails != null && receivingListDetails.size() > 0) {
            ReceivingListDetails rld = receivingListDetails.get(position);
            holder.tv_sn.setText(String.valueOf(rld.getSn()));
            holder.tv_product_name.setText(String.valueOf(rld.getProductName()));
            holder.tv_qty_ordered.setText(String.valueOf(rld.getQtyOrdered()));
            holder.tv_qty_received.setText(String.valueOf(rld.getQtyReceived()));
            holder.tv_qty_remaining.setText(String.valueOf(rld.getQtyRemaining()));
            holder.et_year.setText(String.valueOf(rld.getYear()));

            holder.et_month.setText(String.valueOf(rld.getMonth()));            holder.et_day.setText(String.valueOf(rld.getDay()));

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
        return receivingListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_sn, tv_product_name, tv_qty_ordered, tv_qty_remaining;
        EditText tv_qty_received, et_year, et_month, et_day;
        DatePickerDialog.OnDateSetListener dateSetListener;
        OnReceivingListDetailsListener onReceivingListDetailsListener;

        public ViewHolder(@NonNull View itemView, OnReceivingListDetailsListener onReceivingListDetailsListener) {
            super(itemView);

            tv_sn = itemView.findViewById(R.id.tv_sn);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_qty_ordered = itemView.findViewById(R.id.tv_qty_ordered);
            tv_qty_received = itemView.findViewById(R.id.tv_qty_received);
            tv_qty_remaining = itemView.findViewById(R.id.tv_qty_remaining);
            et_year = itemView.findViewById(R.id.et_year);
            et_month = itemView.findViewById(R.id.et_month);
            et_day = itemView.findViewById(R.id.et_day);

            this.onReceivingListDetailsListener = onReceivingListDetailsListener;
            itemView.setOnClickListener(this);

            tv_qty_received.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    receivingListDetails.get(getAdapterPosition()).setQtyReceived((tv_qty_received.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

/*            expiryDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog((Context) onReceivingListDetailsListener,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            dateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    //Log.d(Tag, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                    String date = month + "/" + day + "/" + year;
                    expiryDate.setText(date);
                }
            };*/

        }

        @Override
        public void onClick(View v) {
            //onReceivingListDetailsListener.onReceivingListDetailsClick(getAdapterPosition());
        }
    }

    public interface OnReceivingListDetailsListener {
        void onReceivingListDetailsClick (int position);
    }
}
