package com.iced.mystore.Adapter;

import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Constants.RestockProduct;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewHolder> {

    private List<ContentValues> listOfOrders;
    private String strAccount;

    public ViewOrderAdapter(List<ContentValues> listOfOrders, String strAccount){
        this.listOfOrders = listOfOrders;
        this.strAccount = strAccount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_adapter_layout,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentValues cvOrder = listOfOrders.get(position);

        if(strAccount.equals(Account.REVENUE)){
            Database database = new Database(holder.itemView.getContext());
            ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE, Product.PRODUCT_ID + " = " + cvOrder.getAsInteger(OrderProduct.PRODUCT_ID));

            holder.txtvProdName.setText(cvProduct.getAsString(Product.PRODUCT_NAME));
            holder.txtvCount.setText(Integer.toString(cvOrder.getAsInteger(OrderProduct.ORDER_COUNT)) + " piece(s)");
            holder.txtvCost.setText(NumberFormat.getCurrencyInstance().format(cvOrder.getAsDouble(OrderProduct.ORDER_COST)));
        }else{
            Database database = new Database(holder.itemView.getContext());
            ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE, Product.PRODUCT_ID + " = " + cvOrder.getAsInteger(RestockProduct.PRODUCT_ID));

            holder.txtvProdName.setText(cvProduct.getAsString(Product.PRODUCT_NAME));
            holder.txtvCount.setText(Integer.toString(cvOrder.getAsInteger(RestockProduct.RESTOCK_COUNT)) + " piece(s)");
            holder.txtvCost.setText(NumberFormat.getCurrencyInstance().format(cvOrder.getAsDouble(RestockProduct.RESTOCK_COST)));
        }
    }

    @Override
    public int getItemCount() {
        return listOfOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtvProdName,txtvCount,txtvCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvProdName = itemView.findViewById(R.id.order_adapter_txtv_prodname);
            txtvCount = itemView.findViewById(R.id.order_adapter_txtv_count);
            txtvCost = itemView.findViewById(R.id.order_adapter_txtv_costs);
        }
    }
}
