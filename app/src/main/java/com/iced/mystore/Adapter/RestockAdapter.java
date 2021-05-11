package com.iced.mystore.Adapter;

import android.content.ContentValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iced.mystore.Activity.RestockActivity;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Constants.RestockProduct;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestockAdapter extends RecyclerView.Adapter<RestockAdapter.ViewHolder> {

    private Map<Integer, ContentValues> orderMap;
    private Database database;

    public RestockAdapter(Map<Integer,ContentValues> orderMap, Database database){
        this.orderMap = orderMap;
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restock_adapter_layout,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentValues cvRestock = orderMap.get(orderMap.keySet().toArray()[position]);
        ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE, Product.PRODUCT_ID + " = " + cvRestock.getAsInteger(RestockProduct.PRODUCT_ID));

        holder.txtvProdName.setText(cvProduct.getAsString(Product.PRODUCT_NAME));
        holder.txtvCount.setText(Integer.toString(cvRestock.getAsInteger(RestockProduct.RESTOCK_COUNT)) + " piece(s)");
        holder.txtvCost.setText(NumberFormat.getCurrencyInstance().format(cvRestock.getAsDouble(RestockProduct.RESTOCK_COST)));
        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer key = (Integer)orderMap.keySet().toArray()[position];
                orderMap.remove(key);
                RestockAdapter.this.notifyDataSetChanged();

                RestockActivity activity = (RestockActivity)view.getContext();
                if(orderMap.isEmpty()){
                    activity.unshowList();
                }else{
                    activity.showList();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtvProdName, txtvCount, txtvCost;
        public Button btnClose,btnDisabled;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvProdName = itemView.findViewById(R.id.restock_adapter_txtv_prod_name);
            txtvCount = itemView.findViewById(R.id.restock_adapter_txtv_count);
            txtvCost = itemView.findViewById(R.id.restock_adapter_txtv_cost);
            btnClose = itemView.findViewById(R.id.restock_adapter_btn_close);
        }
    }
}
