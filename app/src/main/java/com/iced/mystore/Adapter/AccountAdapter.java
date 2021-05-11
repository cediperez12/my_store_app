package com.iced.mystore.Adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.iced.mystore.Activity.ViewOrderActivity;
import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    List<ContentValues> listOfAccounts;

    public AccountAdapter(List<ContentValues> listOfAccounts){
        this.listOfAccounts = listOfAccounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accounts_adapter_layout,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentValues cvAccount = listOfAccounts.get(position);

        holder.txtvAccountNumber.setText("Account ID #" + cvAccount.getAsInteger(Accounting.ACC_ID));
        holder.txtvAccountDate.setText(new SimpleDateFormat("MMMM dd, yyyy hh:mm aa").format(cvAccount.getAsLong(Accounting.ACC_DATE)));
        holder.txtvAccountMoney.setText(NumberFormat.getCurrencyInstance().format(cvAccount.getAsDouble(Accounting.ACC_COST)));
        holder.chipAccountType.setText(AccountingString(cvAccount.getAsString(Accounting.ACC_ACT)));
        holder.btnAccountShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewOrderActivity.class);
                intent.putExtra(ViewOrderActivity.ORDER_NUMBER,cvAccount.getAsInteger(Accounting.ACC_ID));

                if(cvAccount.getAsString(Accounting.ACC_ACT).equals(Account.REVENUE)){
                    intent.putExtra(ViewOrderActivity.ORDER_ACTIVITY,Account.REVENUE);
                }else{
                    intent.putExtra(ViewOrderActivity.ORDER_ACTIVITY,Account.RESTOCK);
                }

                view.getContext().startActivity(intent);
            }
        });
    }

    private String AccountingString(String exp){
        if(exp.equals(Account.EXPIRATION)){
            return "Expiration";
        }else if(exp.equals(Account.RESTOCK)){
            return "Expenses";
        }else if(exp.equals(Account.REVENUE)){
            return "Sales";
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return listOfAccounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtvAccountNumber;
        TextView txtvAccountDate;
        TextView txtvAccountMoney;
        Chip chipAccountType;
        Button btnAccountShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvAccountNumber = itemView.findViewById(R.id.account_adapter_acc_txtv_id);
            txtvAccountDate = itemView.findViewById(R.id.account_adapter_acc_txtv_date);
            txtvAccountMoney = itemView.findViewById(R.id.account_adapter_acc_txtv_total);
            chipAccountType = itemView.findViewById(R.id.account_adapter_chip_acc_type);
            btnAccountShow = itemView.findViewById(R.id.account_adapter_acc_btn_show);
        }
    }

}
