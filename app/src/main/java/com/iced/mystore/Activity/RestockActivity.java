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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.Adapter.RestockAdapter;
import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Constants.RestockProduct;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestockActivity extends AppCompatActivity {

    @BindView(R.id.restock_toolbar)
    MaterialToolbar toolbar;

    @BindView(R.id.restock_list_layout)
    RelativeLayout nestedListLayout;
    @BindView(R.id.restock_date_txtv)
    TextView txtvDate;
    @BindView(R.id.restock_total_txtv)
    TextView txtvTotal;
    @BindView(R.id.restock_btn_pay)
    Button btnPay;
    @BindView(R.id.restock_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.restock_empty_layout)
    LinearLayout emptyListLayout;

    @BindView(R.id.restock_bottomsheet_close_btn)
    Button btnClose;
    @BindView(R.id.restock_bottomsheet_product_til_dropdown)
    TextInputLayout tilProduct;
    @BindView(R.id.restock_bottomsheet_cost_til)
    TextInputLayout tilCost;
    @BindView(R.id.restock_bottomsheet_count_til)
    TextInputLayout tilCount;
    @BindView(R.id.restock_bottomsheet_add_restock_btn)
    Button btnAddRestock;

    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, YYYY");

    private Map<Integer, ContentValues> restockProductMaps;
    private List<ContentValues> listOfProducts;
    private BottomSheetBehavior bottomSheet;
    private Database database;
    private Integer selectedProductId = -1;

    private RestockAdapter adapter;

    private Double totalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        txtvDate.setText(sdf.format(Calendar.getInstance().getTimeInMillis()));

        database = new Database(this);
        restockProductMaps = new HashMap<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomSheet = BottomSheetBehavior.from(findViewById(R.id.restock_bottomsheet_layout));
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        bottomSheetSetup();
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

    private void bottomSheetSetup(){
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)tilProduct.getEditText();
        listOfProducts = database.fetchAll(Database.PRODUCT_TABLE);
        List<String> strListOfProducts = createListOfProducts(listOfProducts);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,strListOfProducts);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnKeyListener(null);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
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
                    tilCost.setEnabled(false);
                }else{
                    tilCount.setEnabled(true);
                    tilCost.setEnabled(true);
                }
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProductId = (Integer)listOfProducts.get(i).getAsInteger(Product.PRODUCT_ID);
                Log.d("SELECTED PRODUCT",Integer.toString(selectedProductId));
            }
        });

        tilCost.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    btnAddRestock.setEnabled(false);
                }else{
                    btnAddRestock.setEnabled(true);
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
                if(editable.toString().isEmpty()){
                    btnAddRestock.setEnabled(false);
                }else{
                    btnAddRestock.setEnabled(true);
                }
            }
        });
    }

    public void onClickAddToList(View view){
        try{
            Integer count = Integer.parseInt(tilCount.getEditText().getText().toString().trim());
            Double cost = Double.parseDouble(tilCost.getEditText().getText().toString().trim());

            Log.d("SELECTED PRODUCT = ", Integer.toString(selectedProductId));

            if(selectedProductId == -1)
                throw new Exception("Please select a product to restock first.");
            else if(count <= 0)
                throw new Exception("You must put the count of the product");
            else if(cost <= 0)
                throw new Exception("Please add the cost of this restocking");

            ContentValues cvRestock = new ContentValues();
            cvRestock.put(RestockProduct.PRODUCT_ID,selectedProductId);
            cvRestock.put(RestockProduct.RESTOCK_COUNT,count);
            cvRestock.put(RestockProduct.RESTOCK_COST,cost);

            restockProductMaps.put(selectedProductId,cvRestock);

            bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            restockProductRecyclerView();
        }catch (Exception ex){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage(ex.getMessage())
                    .create()
                    .show();
        }
    }

    public void restockButton(View view){
        bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void onClickPay(View view){
        ContentValues cvAccount = new ContentValues();
        cvAccount.put(Accounting.ACC_DATE,Calendar.getInstance().getTimeInMillis());
        cvAccount.put(Accounting.ACC_COST,totalCost);
        cvAccount.put(Accounting.ACC_ACT, Account.RESTOCK);

        Integer accountId = database.insert(Database.ACCOUNTING_TABLE,cvAccount).intValue();

        for(Map.Entry e : restockProductMaps.entrySet()){
            ContentValues cvRestockEntry = (ContentValues)e.getValue();
            cvRestockEntry.put(RestockProduct.ACC_ID,accountId);
            ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE,Product.PRODUCT_ID + " = " + cvRestockEntry.getAsInteger(RestockProduct.PRODUCT_ID));
            database.insert(Database.RESTOCK_PRODUCT_TABLE,cvRestockEntry);

            cvProduct.put(Product.PRODUCT_STOCKS,cvProduct.getAsInteger(Product.PRODUCT_STOCKS) + cvRestockEntry.getAsInteger(RestockProduct.RESTOCK_COUNT));
            database.getWritableDatabase().update(Database.PRODUCT_TABLE,cvProduct,Product.PRODUCT_ID + " = ?",new String[] {Integer.toString(cvProduct.getAsInteger(Product.PRODUCT_ID))});
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle("Restocking complete")
                .setMessage("Do you want to restock again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restockProductMaps.clear();
                        restockProductRecyclerView();
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
    }

    public void showList(){
        nestedListLayout.setVisibility(View.VISIBLE);
        emptyListLayout.setVisibility(View.INVISIBLE);
    }

    public void unshowList(){
        nestedListLayout.setVisibility(View.INVISIBLE);
        emptyListLayout.setVisibility(View.VISIBLE);
    }

    public List<String> createListOfProducts(List<ContentValues> list){
        List<String> result = new ArrayList<>();
        for(ContentValues cv : list){
            result.add(cv.getAsString(Product.PRODUCT_NAME));
        }
        return result;
    }

    private void restockProductRecyclerView(){
        if(adapter == null){
            adapter = new RestockAdapter(restockProductMaps,database);
            LinearLayoutManager manager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

            showList();
        }else{
            adapter.notifyDataSetChanged();

            if(restockProductMaps.isEmpty()){
                unshowList();
            }else{
                showList();
            }
        }

        restockCostTotal();
    }

    public void restockCostTotal(){
        totalCost = 0.0;
        if(!restockProductMaps.isEmpty()){
            for(Map.Entry e : restockProductMaps.entrySet()){
                totalCost+=((ContentValues)e.getValue()).getAsDouble(RestockProduct.RESTOCK_COST);
            }
            txtvTotal.setText(NumberFormat.getCurrencyInstance().format(totalCost));
        }
    }
}