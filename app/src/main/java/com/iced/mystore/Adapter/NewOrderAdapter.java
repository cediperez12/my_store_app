package com.iced.mystore.Adapter;

import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iced.mystore.Activity.NewOrderActivity;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    private Map<Integer,ContentValues> orderMap;
    private Database database;

    public NewOrderAdapter(Map<Integer,ContentValues> orderMap, Database database){
        this.orderMap = orderMap;
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_adapter_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentValues cvOrder = orderMap.get(orderMap.keySet().toArray()[position]);
        ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE,Product.PRODUCT_ID + " = " + cvOrder.getAsInteger(OrderProduct.PRODUCT_ID));

        holder.txtvProdName.setText(cvProduct.getAsString(Product.PRODUCT_NAME));
        holder.txtvCount.setText(cvOrder.getAsInteger(OrderProduct.ORDER_COUNT) + " piece(s)");
        holder.txtvCost.setText(NumberFormat.getCurrencyInstance().format(cvOrder.getAsDouble(OrderProduct.ORDER_COST)));
        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderMap.remove(cvProduct.getAsInteger(Product.PRODUCT_ID));
                NewOrderAdapter.this.notifyDataSetChanged();

                NewOrderActivity currentActivity = (NewOrderActivity)view.getContext();

                if(orderMap.isEmpty()){
                    currentActivity.showEmptyList();
                }else{
                    currentActivity.unshowEmptyList();
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
            txtvProdName = itemView.findViewById(R.id.new_order_adapter_txtv_prod_name);
            txtvCount = itemView.findViewById(R.id.new_order_adapter_txtv_count);
            txtvCost = itemView.findViewById(R.id.new_order_adapter_txtv_cost);
            btnClose = itemView.findViewById(R.id.new_order_adapter_btn_close);
            btnDisabled = itemView.findViewById(R.id.new_order_adapter_disabled_btn);
        }
    }
}
