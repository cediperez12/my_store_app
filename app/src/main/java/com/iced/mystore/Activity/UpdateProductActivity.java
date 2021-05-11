package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

public class UpdateProductActivity extends AppCompatActivity {

    @BindView(R.id.update_product_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.update_product_til_prod_name)
    TextInputLayout tilProdName;
    @BindView(R.id.update_product_til_prod_cost)
    TextInputLayout tilProdCost;
    @BindView(R.id.update_product_btn_save)
    Button btnSave;

    public static final String UPDATE_PRODUCT_ID = "upd_prod_id";

    private Integer updateId;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateId = getIntent().getIntExtra(UPDATE_PRODUCT_ID,-1);
        database = new Database(this);

        if(updateId != -1){
            ContentValues cvProduct = database.fetch(Database.PRODUCT_TABLE, Product.PRODUCT_ID + " = " + updateId);

            tilProdName.getEditText().setText(cvProduct.getAsString(Product.PRODUCT_NAME));
            tilProdCost.getEditText().setText(Double.toString(cvProduct.getAsDouble(Product.PRODUCT_COST)));

            btnSave.setOnClickListener(i->{
                try{
                    String strProdName = tilProdName.getEditText().getText().toString().trim();
                    Double douProdCost = Double.parseDouble(tilProdCost.getEditText().getText().toString().trim());

                    if(strProdName.isEmpty())
                        throw new Exception("Please add Product name to continue");
                    else if(douProdCost <= 0)
                        throw new Exception("Please add cost");

                    cvProduct.put(Product.PRODUCT_NAME,strProdName);
                    cvProduct.put(Product.PRODUCT_COST,douProdCost);

                    int result = database.getWritableDatabase().update(Database.PRODUCT_TABLE,cvProduct,Product.PRODUCT_ID + " = ?", new String[]{Integer.toString(updateId)});

                    if(result != -1){
                        new MaterialAlertDialogBuilder(i.getContext())
                                .setTitle("Success")
                                .setMessage("Update Success")
                                .setPositiveButton("Okay", (dialog,inti)->{
                                    finish();
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }else
                        throw new Exception("Update fail.");
                }catch (Exception ex){
                    new MaterialAlertDialogBuilder(i.getContext())
                            .setTitle("Error")
                            .setMessage(ex.getMessage())
                            .setPositiveButton("Okay",null)
                            .create()
                            .show();
                }
            });
        }else{
            finish();
        }
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
        finish();
        super.onBackPressed();
    }
}