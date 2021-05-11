package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.Adapter.NewOrderAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewOrderActivity extends AppCompatActivity {

    @BindView(R.id.new_order_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.new_order_list_layout)
    NestedScrollView listLayout;
    @BindView(R.id.new_order_empty_order_list_layout)
    LinearLayout emptyListLayout;
    @BindView(R.id.new_order_fab_add_order)
    FloatingActionButton fabAddOrder;
    @BindView(R.id.new_order_fab_payment)
    FloatingActionButton fabPayment;
    @BindView(R.id.new_order_recyclerview)
    RecyclerView recyclerViewOrderList;
    @BindView(R.id.new_order_sheet_select_product_til)
    TextInputLayout tilProduct;
    @BindView(R.id.new_order_sheet_stocks_txtv)
    TextView txtvStocks;
    @BindView(R.id.new_order_sheet_cost_txtv)
    TextView txtvCosts;
    @BindView(R.id.new_order_sheet_count_til)
    TextInputLayout tilCount;
    @BindView(R.id.new_order_total_cost_txtv)
    TextView txtvTotalCosts;
    @BindView(R.id.new_order_sheet_add_order_btn)
    Button addOrderBtn;
    @BindView(R.id.new_order_sheet_close_btn)
    Button closeSheetBtn;

    private BottomSheetBehavior orderBottomSheet;
    private Map<Integer,ContentValues> mapOfOrders;
    private Map<String,ContentValues> mapOfProducts;
    private List<ContentValues> listOfOrders;
    private List<ContentValues> listOfProducts;
    private List<String> strListofProducts;

    private Double totalCosts = 0.0;
    private Integer indexSelectedProduct = -1;

    private Database database;

    private InterstitialAd ad;

    private String PREFERENCE_INTERSTITIAL_AD = "INT_AD_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.bind(this);
        init();
        setupAds();
    }

    private void setupAds(){
        MobileAds.initialize(this);

        AdRequest interstitialAdReqs = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.INTERSTITIAL_ADD_ID),interstitialAdReqs,new InterstitialAdLoadCallback(){
            public void onAdLoaded(@NonNull InterstitialAd intersAd){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NewOrderActivity.this);

                Integer adLoadingProcess = preferences.getInt(PREFERENCE_INTERSTITIAL_AD, -1);
                Log.d("LOADING PROCESS", Integer.toString(adLoadingProcess));

                if(adLoadingProcess == -1){
                    ad = intersAd;
                    ad.show(NewOrderActivity.this);

                    preferences.edit().putInt(PREFERENCE_INTERSTITIAL_AD, ++adLoadingProcess).commit();
                }else{
                    preferences.edit().putInt(PREFERENCE_INTERSTITIAL_AD, ++adLoadingProcess).commit();

                    if(adLoadingProcess == 2)
                        preferences.edit().putInt(PREFERENCE_INTERSTITIAL_AD, -1).commit();
                }
            }

            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError){
                ad = null;
                Log.d("ERROR","LOAD AD ERROR");
            }
        });
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new Database(this);
        mapOfOrders = new HashMap<>();
        listOfOrders = new ArrayList<>();

        listOfProducts = database.fetchAll(Database.PRODUCT_TABLE);
        strListofProducts = createProductNameList(listOfProducts);

        orderBottomSheet = BottomSheetBehavior.from(findViewById(R.id.new_order_bottomsheet));
        orderBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        tilProduct.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    tilCount.setEnabled(false);
                }else{
                    tilCount.setEnabled(true);
                }
            }
        });

        AutoCompleteTextView tilEtxt = (AutoCompleteTextView)tilProduct.getEditText();

        ArrayAdapter<String> selectOrderProduct = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, strListofProducts);
        tilEtxt.setAdapter(selectOrderProduct);
        tilEtxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                indexSelectedProduct = i;

                ContentValues cvProduct = listOfProducts.get(indexSelectedProduct);
                txtvStocks.setText(Integer.toString(cvProduct.getAsInteger(Product.PRODUCT_STOCKS)));
                txtvCosts.setText(NumberFormat.getCurrencyInstance().format(cvProduct.getAsDouble(Product.PRODUCT_COST)));

                if(!tilCount.getEditText().getText().toString().isEmpty()){
                    try{
                        Integer orderCount = Integer.parseInt(tilCount.getEditText().getText().toString());

                        Double cost = orderCount * cvProduct.getAsDouble(Product.PRODUCT_COST);

                        txtvTotalCosts.setText(NumberFormat.getCurrencyInstance().format(cost));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        tilCount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    Integer orderCount = Integer.parseInt(editable.toString());

                    ContentValues cvProduct = listOfProducts.get(indexSelectedProduct);

                    Double cost = orderCount * cvProduct.getAsDouble(Product.PRODUCT_COST);

                    txtvTotalCosts.setText(NumberFormat.getCurrencyInstance().format(cost));
                }catch (Exception ex){
                    txtvTotalCosts.setText(NumberFormat.getCurrencyInstance().format(0.0));
                    ex.printStackTrace();
                }
            }
        });
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

    public void clickAddOrder(View view){
        orderBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void clickClose(View view){
        orderBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void setupOrderList(){
        RecyclerView.Adapter adapter = recyclerViewOrderList.getAdapter();

        if(adapter == null){
            NewOrderAdapter newAdapter = new NewOrderAdapter(mapOfOrders,database);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewOrderList.setHasFixedSize(true);
            recyclerViewOrderList.setLayoutManager(layoutManager);
            recyclerViewOrderList.setAdapter(newAdapter);

            if(mapOfOrders.isEmpty()){
                showEmptyList();
            }else{
                unshowEmptyList();
            }
        }else{
            if(mapOfOrders.isEmpty()){
                showEmptyList();
            }else{
                unshowEmptyList();
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void onClickAddOrder(View view){
        try{
            ContentValues cvProduct = listOfProducts.get(indexSelectedProduct);
            Integer orderCount = Integer.parseInt(tilCount.getEditText().getText().toString().trim());

            if(orderCount > cvProduct.getAsInteger(Product.PRODUCT_STOCKS)){
                throw new Exception("The stocks is insufficient");
            }

            Double cost = orderCount * cvProduct.getAsDouble(Product.PRODUCT_COST);

            ContentValues cvNewOrder = new ContentValues();
            cvNewOrder.put(OrderProduct.PRODUCT_ID,cvProduct.getAsInteger(Product.PRODUCT_ID));
            cvNewOrder.put(OrderProduct.ORDER_COUNT,orderCount);
            cvNewOrder.put(OrderProduct.ORDER_COST,cost);

            if(!mapOfOrders.containsKey(cvProduct.getAsInteger(Product.PRODUCT_ID))){
                listOfOrders.add(cvNewOrder);
            }

            mapOfOrders.put(cvProduct.getAsInteger(Product.PRODUCT_ID),cvNewOrder);

            orderBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

            setupOrderList();
        }catch (Exception ex){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Order")
                    .setMessage(ex.getMessage())
                    .setPositiveButton("Okay",null)
                    .create()
                    .show();
        }
    }

    public void unshowEmptyList(){
        emptyListLayout.setVisibility(View.INVISIBLE);
        listLayout.setVisibility(View.VISIBLE);
    }

    public void showEmptyList(){
        emptyListLayout.setVisibility(View.VISIBLE);
        listLayout.setVisibility(View.INVISIBLE);
    }

    private List<String> createProductNameList(List<ContentValues> productList){
        List<String> result = new ArrayList<String>();
        for(ContentValues cv : productList){
            result.add(cv.getAsString(Product.PRODUCT_NAME));
        }
        return result;
    }

    public void onClickPayment(View view){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payment_layout,null,false);

        TextView txtvDate = dialogView.findViewById(R.id.dialog_payment_total_txtv),
        txtvTotal = dialogView.findViewById(R.id.dialog_payment_total_txtv),
        txtvSubtotal = dialogView.findViewById(R.id.dialog_payment_subtotal_txtv);

        TextInputLayout tilPaymentReceived = dialogView.findViewById(R.id.dialog_payment_payment_received_til);

        Button btnPayment = dialogView.findViewById(R.id.dialog_payment_btn), btnCancel = dialogView.findViewById(R.id.dialog_payment_cancel_btn);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, YYYY");

        txtvDate.setText(dateFormat.format(Calendar.getInstance().getTimeInMillis()));

        totalCosts = 0.0;

        for(Map.Entry e : mapOfOrders.entrySet()){
            ContentValues cvOrder = (ContentValues)e.getValue();

            totalCosts+=cvOrder.getAsDouble(OrderProduct.ORDER_COST);
        }

        txtvTotal.setText(NumberFormat.getCurrencyInstance().format(totalCosts));

        tilPaymentReceived.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    Double change = Double.parseDouble(editable.toString().trim()) - totalCosts;
                    txtvSubtotal.setText(NumberFormat.getCurrencyInstance().format(change));

                    if(change >= 0){
                        btnPayment.setEnabled(true);
                    }else{
                        btnPayment.setEnabled(false);
                    }
                }
            }
        });

        Dialog dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .create();

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cvAccount = new ContentValues();
                cvAccount.put(Accounting.ACC_ACT, Account.REVENUE);
                cvAccount.put(Accounting.ACC_COST, totalCosts);
                cvAccount.put(Accounting.ACC_DATE, Calendar.getInstance().getTimeInMillis());

                Long insertNewAccountId = database.insert(Database.ACCOUNTING_TABLE,cvAccount);

                if(insertNewAccountId != -1){
                    for(Map.Entry e : mapOfOrders.entrySet()){
                        ContentValues cvOrder = (ContentValues)e.getValue();
                        cvOrder.put(OrderProduct.ACC_ID,insertNewAccountId.intValue());

                        ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE,Product.PRODUCT_ID + " = " + cvOrder.getAsInteger(OrderProduct.PRODUCT_ID));

                        cvProduct.put(Product.PRODUCT_STOCKS,cvProduct.getAsInteger(Product.PRODUCT_STOCKS) - cvOrder.getAsInteger(OrderProduct.ORDER_COUNT));

                        database.getWritableDatabase().update(Database.PRODUCT_TABLE,cvProduct,Product.PRODUCT_ID + " = ?", new String[]{Integer.toString(cvProduct.getAsInteger(Product.PRODUCT_ID))});
                        database.insert(Database.ORDER_PRODUCT_TABLE,cvOrder);
                    }

                    new MaterialAlertDialogBuilder(view.getContext())
                            .setTitle("Order")
                            .setMessage("Do you want to create one more orders?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mapOfOrders.clear();
                                    recyclerViewOrderList.getAdapter().notifyDataSetChanged();
                                    showEmptyList();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }else{
                    dialog.dismiss();

                    new MaterialAlertDialogBuilder(view.getContext())
                            .setTitle("Payment")
                            .setMessage("Payment entry fail.")
                            .setPositiveButton("Okay",null)
                            .create()
                            .show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if(!mapOfOrders.isEmpty()){
            dialog.show();
        }else{
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Payment")
                    .setMessage("There are no product in the order. Please add products in the orders first")
                    .setPositiveButton("Okay",null)
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }

    private void setupMapProduct(){
        for(ContentValues cv : listOfProducts){
            //mapOfProducts.put(cv.getAsString(Product.PRODUCT_NAME,))
        }
    }
}