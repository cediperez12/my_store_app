package com.iced.mystore.Adapter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.Activity.RestockActivity;
import com.iced.mystore.Activity.UpdateProductActivity;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ContentValues> listOfProducts;

    public ProductAdapter(List<ContentValues> listOfProducts){
        this.listOfProducts = listOfProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentValues cvProduct = listOfProducts.get(position);
        holder.txtvProductName.setText(cvProduct.getAsString(Product.PRODUCT_NAME));
        holder.txtvStocksLeft.setText("STOCK:" +cvProduct.getAsInteger(Product.PRODUCT_STOCKS));
        holder.txtvPrice.setText(NumberFormat.getCurrencyInstance().format(cvProduct.getAsDouble(Product.PRODUCT_COST)));
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(view.getContext(),view);
                menu.setOnMenuItemClickListener((i) -> {
                    Intent intent = new Intent();
                    switch (i.getItemId()){

                        case R.id.dashboard_more_menu_restock:
                            intent = new Intent(view.getContext(),RestockActivity.class);
                            view.getContext().startActivity(intent);
                            break;
                        case R.id.dashboard_more_menu_expired:
                            showExpirationDialog(view,cvProduct);
                            break;
                        case R.id.dashboard_more_menu_update:
                            intent = new Intent(view.getContext(), UpdateProductActivity.class);
                            intent.putExtra(UpdateProductActivity.UPDATE_PRODUCT_ID,cvProduct.getAsInteger(Product.PRODUCT_ID));
                            view.getContext().startActivity(intent);
                            break;
                    }

                    return true;
                });
                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.dashboard_more_menu, menu.getMenu());
                menu.show();
            }
        });
    }

    private void showExpirationDialog(View view, ContentValues cvProduct){
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.expiration_dialog_layout,null,false);

        Dialog dialog = new MaterialAlertDialogBuilder(view.getContext())
                .setView(v)
                .create();

        TextView txtvProductName = v.findViewById(R.id.expiration_dialog_txtv_prodname),
                txtvCount = v.findViewById(R.id.expiration_dialog_txtv_count);
        TextInputLayout tilCount = v.findViewById(R.id.expiration_dialog_til_count);
        Button btnSubmit = v.findViewById(R.id.expiration_dialog_btn_submit),
                btnCancel = v.findViewById(R.id.expiration_dialog_btn_cancel);

        Integer productCount = cvProduct.getAsInteger(Product.PRODUCT_STOCKS);
        String strProductName = cvProduct.getAsString(Product.PRODUCT_NAME);

        txtvProductName.setText(strProductName);
        txtvCount.setText(productCount + " piece(s)");

        btnSubmit.setOnClickListener((i)->{
            try{
                Integer intCounter = Integer.parseInt(tilCount.getEditText().getText().toString().trim());

                if(intCounter <= 0 || intCounter > productCount){
                    throw new Exception();
                }else{
                    Integer newProductCount = productCount - intCounter;

                    if(newProductCount < 0){
                        throw new Exception();
                    }else{
                        cvProduct.put(Product.PRODUCT_STOCKS,newProductCount);

                        Database database = new Database(view.getContext());
                        database.getWritableDatabase().update(Database.PRODUCT_TABLE,cvProduct,Product.PRODUCT_ID + " = ?", new String[]{Integer.toString(cvProduct.getAsInteger(Product.PRODUCT_ID))});
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        btnCancel.setOnClickListener((i)->{
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return listOfProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtvProductName;
        public TextView txtvStocksLeft;
        public TextView txtvPrice;
        public Button btnMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvProductName  = itemView.findViewById(R.id.product_adapter_txtv_prod_name);
            txtvStocksLeft = itemView.findViewById(R.id.product_adapter_txtv_stocks);
            txtvPrice = itemView.findViewById(R.id.product_adapter_txtv_price);
            btnMore = itemView.findViewById(R.id.product_adapter_more_button);
        }
    }

}
