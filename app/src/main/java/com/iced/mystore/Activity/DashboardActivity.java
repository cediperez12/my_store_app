package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.iced.mystore.Adapter.ProductAdapter;
import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.dashboard_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.dashboard_card_today)
    MaterialCardView cardToday;
    @BindView(R.id.dashboard_card_today_date)
    TextView txtvTodayDate;
    @BindView(R.id.dashboard_card_today_sales_layout)
    RelativeLayout layoutSalesToday;
    @BindView(R.id.dashboard_card_today_sales_prompt)
    TextView txtvSalesToday;
    @BindView(R.id.dashboard_card_today_expenses_layout)
    RelativeLayout layoutExpensesToday;
    @BindView(R.id.dashboard_card_today_expenses_prompt)
    TextView txtvExpensesToday;
    @BindView(R.id.dashboard_card_today_total_layout)
    RelativeLayout layoutTotal;
    @BindView(R.id.dashboard_card_today_total_prompt)
    TextView txtvTotalToday;
    @BindView(R.id.dashboard_card_last_order)
    MaterialCardView cardLastOrder;
    @BindView(R.id.dashboard_card_last_order_order_number_txtv)
    TextView txtvLastOrderNumber;
    @BindView(R.id.dashboard_card_last_order_date_txtv)
    TextView txtvLasOrderDate;
    @BindView(R.id.dashboard_card_last_Order_cost_layout)
    RelativeLayout layoutLastOrderCost;
    @BindView(R.id.dashboard_card_last_order_view_btn)
    Button btnViewOrder;
    @BindView(R.id.dashboard_card_last_order_cost_prompt)
    TextView txtvLastOrderCost;
    @BindView(R.id.dashboard_product_list_layout)
    LinearLayout layoutProductList;
    @BindView(R.id.dashboard_card_product_list_recyclerview)
    RecyclerView recyclerViewProductList;
    @BindView(R.id.dashboard_product_empty_layout)
    LinearLayout layouEmptyProductList;
    @BindView(R.id.dashboard_product_empty_create_btn)
    Button btnAddNew;
    @BindView(R.id.dashboard_scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.dashboard_adview)
    AdView bannerAd;

    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, YYYY");
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    private Double todaySales = 0.0, todayExpenses = 0.0, todayTotal = 0.0,lastOrderCost = 1852.0;
    private Integer lastOrderId = 01142000;
    private Boolean isLastOrderExist = false;
    private List<ContentValues> cvProductList = new ArrayList<>();

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        database = new Database(this);

        setSupportActionBar(toolbar);

        setupTodayCard();
        setupLastOrderCard();
        setupProductList();
        setupAds();
    }

    private void setupAds(){
        MobileAds.initialize(this);

        AdRequest bannerAdReq = new AdRequest.Builder().build();
        bannerAd.loadAd(bannerAdReq);
    }

    private void setupProductList(){
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NewProductActivity.class);
                startActivity(intent);
            }
        });

        cvProductList = database.fetchAll(Database.PRODUCT_TABLE,null,"ORDER BY " + Product.PRODUCT_STOCKS + " ASC");

        if(cvProductList.isEmpty()){
            layoutProductList.setVisibility(View.GONE);
            layouEmptyProductList.setVisibility(View.VISIBLE);
        }else{
            scrollView.scrollTo(0,0);

            layoutProductList.setVisibility(View.VISIBLE);
            layouEmptyProductList.setVisibility(View.GONE);

            ProductAdapter adpater = new ProductAdapter(cvProductList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            recyclerViewProductList.setHasFixedSize(true);
            recyclerViewProductList.setLayoutManager(layoutManager);
            recyclerViewProductList.setAdapter(adpater);
        }
    }

    private ContentValues cvLastOder;

    private void setupLastOrderCard(){
        cvLastOder = database.fetch(Database.ACCOUNTING_TABLE,Accounting.ACC_ID + " = (SELECT " + Accounting.ACC_ID + " FROM " + Database.ACCOUNTING_TABLE + " WHERE " + Accounting.ACC_ACT + " = '" + Account.REVENUE + "' ORDER BY " + Accounting.ACC_ID + " DESC LIMIT 1)");
        if(!cvLastOder.keySet().isEmpty()){
            txtvLastOrderNumber.setText("Order #" + cvLastOder.getAsInteger(Accounting.ACC_ID));
            txtvLasOrderDate.setText(sdf.format(cvLastOder.getAsLong(Accounting.ACC_DATE)));
            txtvLastOrderCost.setText(currencyFormatter.format(cvLastOder.getAsDouble(Accounting.ACC_COST)));
            btnViewOrder.setOnClickListener((i)->{
                Intent intent = new Intent(i.getContext(),ViewOrderActivity.class);
                intent.putExtra(ViewOrderActivity.ORDER_NUMBER,cvLastOder.getAsInteger(Accounting.ACC_ID));
                intent.putExtra(ViewOrderActivity.ORDER_ACTIVITY, cvLastOder.getAsString(Accounting.ACC_ACT));
                startActivity(intent);

                Log.d("Account ID", cvLastOder.getAsInteger(OrderProduct.ACC_ID) + " ");
            });
        }else{
            cardLastOrder.setVisibility(View.GONE);
        }

    }

    private void setupTodayCard(){
        //Setup Today Card
        Calendar todayCalendar = Calendar.getInstance();
        txtvTodayDate.setText(sdf.format(todayCalendar.getTimeInMillis()));

        todaySales = database.getRevenue();
        todayExpenses = database.getExpenses();
        todayTotal = todaySales - todayExpenses;

        txtvSalesToday.setText(currencyFormatter.format(todaySales));
        txtvExpensesToday.setText(currencyFormatter.format(todayExpenses));

        if(todayTotal <= 0){
            txtvTotalToday.setText("No revenues yet.");
        }else{
            txtvTotalToday.setText(currencyFormatter.format(todayTotal));
        }
    }

    public void clickFabCreate(View view){
        try{
            PopupMenu menu = new PopupMenu(this,view);
            menu.setOnMenuItemClickListener((m) ->{
                Intent intent = null;
                switch (m.getItemId()){
                    case R.id.dashboard_create_menu_Order:
                        intent = new Intent(getApplicationContext(),NewOrderActivity.class);
                        break;
                    case R.id.dashboard_create_menu_restock:
                        intent = new Intent(getApplicationContext(),RestockActivity.class);
                        break;
                    case R.id.dashboard_create_menu_product:
                        intent = new Intent(getApplicationContext(),NewProductActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            });
            menu.getMenuInflater().inflate(R.menu.dashboard_create_menu,menu.getMenu());
            menu.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTodayCard();
        setupLastOrderCard();
        setupProductList();
        if(recyclerViewProductList.getAdapter() != null){
            recyclerViewProductList.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();

        Log.d("Option","An option is selected");

        switch (item.getItemId()){
            case R.id.dashboard_view_products_menu:
                intent = new Intent(this,AccountingActivity.class);
                break;
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void onClickViewLastOrder(View view){
        if(!cvLastOder.keySet().isEmpty()){
            Intent intent = new Intent(this,ViewOrderActivity.class);
            intent.putExtra(ViewOrderActivity.ORDER_NUMBER,cvLastOder.getAsInteger(OrderProduct.ACC_ID));
            intent.putExtra(ViewOrderActivity.ORDER_ACTIVITY, Account.REVENUE);
            startActivity(intent);

            Log.d("Account ID", cvLastOder.getAsInteger(OrderProduct.ACC_ID) + " ");
        }
    }
}