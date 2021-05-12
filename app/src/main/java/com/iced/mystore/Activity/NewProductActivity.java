package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

public class NewProductActivity extends AppCompatActivity {

    @BindView(R.id.new_product_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.new_product_til_prod_name)
    TextInputLayout tilProdName;
    @BindView(R.id.new_product_til_prod_cost)
    TextInputLayout tilProdCost;
    @BindView(R.id.new_product_btn_save)
    Button btnSave;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);

        tilProdName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    tilProdCost.setEnabled(true);
                }else{
                    tilProdCost.setEnabled(false);
                }
            }
        });

        tilProdCost.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    btnSave.setEnabled(true);
                }else{
                    btnSave.setEnabled(false);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String strProdName = tilProdName.getEditText().getText().toString().trim();
                    Double dblCost = Double.parseDouble(tilProdCost.getEditText().getText().toString().trim());

                    if(strProdName.isEmpty()){
                        throw new Exception("Do not leave the product name empty.");
                    }else if(dblCost == 0){
                        throw new Exception("You cannot place 0 for the product costs");
                    }

                    ContentValues cvProduct = new ContentValues();
                    cvProduct.put(Product.PRODUCT_NAME,strProdName);
                    cvProduct.put(Product.PRODUCT_COST,dblCost);
                    cvProduct.put(Product.PRODUCT_STOCKS,0);
                    cvProduct.put(Product.PRODUCT_LAST_UPDATE,0);

                    Boolean isSaveSuccess = database.insert(Database.PRODUCT_TABLE,cvProduct) != -1;

                    if(isSaveSuccess){
                        new MaterialAlertDialogBuilder(view.getContext())
                                .setTitle("Submission")
                                .setMessage("Do you want to add another product?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tilProdName.getEditText().setText("");
                                        tilProdCost.getEditText().setText("");
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }else{
                        throw new Exception("Saving unsuccessful.");
                    }

                }catch (Exception ex){
                    new MaterialAlertDialogBuilder(view.getContext())
                            .setTitle("Error")
                            .setMessage(ex.getMessage())
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
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


}