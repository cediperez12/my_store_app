package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.iced.mystore.Adapter.ViewOrderAdapter;
import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.Constants.Order;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {

    @BindView(R.id.view_order_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.view_order_txtv_ordernumber)
    TextView txtvOrderNumber;
    @BindView(R.id.view_order_txtv_date)
    TextView txtvDate;
    @BindView(R.id.view_order_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.view_order_txtv_costs)
    TextView txtvCosts;

    private Database database;
    private ContentValues cvOrderNumber;
    private List<ContentValues> listOfOrders;
    private Integer orderNumber;

    public static final String ORDER_NUMBER = "OR-NUMBER";
    public static final String ORDER_ACTIVITY = "OR-ACT";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    private String strAccountActivity;
    private String strDataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);

        strAccountActivity = getIntent().getStringExtra(ORDER_ACTIVITY);
        orderNumber = getIntent().getIntExtra(ORDER_NUMBER,-1);
        cvOrderNumber = database.fetch(Database.ACCOUNTING_TABLE, Accounting.ACC_ID + " = " + orderNumber);

        strDataTable = strAccountActivity.equals(Account.REVENUE) ? Database.ORDER_PRODUCT_TABLE : Database.RESTOCK_PRODUCT_TABLE;

        listOfOrders = database.fetchAll(strDataTable, OrderProduct.ACC_ID + " = " + orderNumber);

        txtvDate.setText(sdf.format(cvOrderNumber.getAsLong(Accounting.ACC_DATE)));
        txtvCosts.setText(currencyFormatter.format(cvOrderNumber.getAsDouble(Accounting.ACC_COST)));
        txtvOrderNumber.setText("Account #" + orderNumber);

        if(strAccountActivity.equals(Account.REVENUE)){
            toolbar.setTitle("Sales");
        }else{
            toolbar.setTitle("Expenses");
        }

        ViewOrderAdapter adapter = new ViewOrderAdapter(listOfOrders,strAccountActivity);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}