package com.iced.mystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.iced.mystore.Adapter.AccountAdapter;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.Data.Database;
import com.iced.mystore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.List;

public class AccountingActivity extends AppCompatActivity {

    @BindView(R.id.accounting_toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.accounting_date_txtv)
    TextView txtvDate;
    @BindView(R.id.accounting_sales_txtv)
    TextView txtvSales;
    @BindView(R.id.accounting_expenses_txtv)
    TextView txtvExpenses;
    @BindView(R.id.accounting_revenue_txtv)
    TextView txtvRevenue;
    @BindView(R.id.accounting_btn_change_date)
    Button btnChange;
    @BindView(R.id.accounting_recyclerview)
    RecyclerView recyclerView;

    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    private SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM yyyy");
    private SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private List<ContentValues> listOfContentValues;

    private Database database;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new Database(this);

        MonthlySetupDate();
    }

    private void MonthlySetupDate(){
        Long fromDate, toDate;

        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,0,0
        );
        fromDate = calendar.getTimeInMillis();

        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23,59,59
        );
        toDate = calendar.getTimeInMillis();

        String query = Accounting.ACC_DATE + " >= " + fromDate + " AND " + Accounting.ACC_DATE + " <= " + toDate + " ORDER BY " + Accounting.ACC_DATE + " DESC";

        listOfContentValues = database.fetchAll(Database.ACCOUNTING_TABLE, query);

        txtvDate.setText(sdf.format(calendar.getTimeInMillis()));

        Double sales = database.getRevenue(toDate,fromDate),
        expenses = database.getExpenses(toDate,fromDate),
        revenue = sales-expenses;

        txtvSales.setText(NumberFormat.getCurrencyInstance().format(sales));
        txtvExpenses.setText(NumberFormat.getCurrencyInstance().format(expenses));

        if(revenue <= 0){
            txtvRevenue.setText("No revenue yet.");
        }else{
            txtvRevenue.setText(NumberFormat.getCurrencyInstance().format(revenue));
        }

        if(accountAdapter == null){
            SetupAdapater();
        }else{
            accountAdapter.notifyDataSetChanged();
        }
    }

    private void SetupDate(Long fromDate, Long toDate){
        if(fromDate == toDate){
            txtvDate.setText(sdf.format(fromDate));
        }else{
            txtvDate.setText("From " + sdf.format(fromDate) + " to " + sdf.format(toDate));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fromDate);
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,0,0
        );
        fromDate = calendar.getTimeInMillis();

        calendar.setTimeInMillis(toDate);
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23,59,59
        );
        toDate = calendar.getTimeInMillis();

        String query = Accounting.ACC_DATE + " >= " + fromDate + " AND " + Accounting.ACC_DATE + " <= " + toDate;

        listOfContentValues = database.fetchAll(Database.ACCOUNTING_TABLE, query);

        for(ContentValues cv : listOfContentValues){
            Log.d("Account ID",Integer.toString(cv.getAsInteger(Accounting.ACC_ID)) + " -> " + cv.getAsDouble(Accounting.ACC_COST));
        }

        Double sales = database.getRevenue(toDate,fromDate),
                expenses = database.getExpenses(toDate,fromDate),
                revenue = sales-expenses;

        txtvSales.setText(NumberFormat.getCurrencyInstance().format(sales));
        txtvExpenses.setText(NumberFormat.getCurrencyInstance().format(expenses));

        if(revenue <= 0){
            txtvRevenue.setText("No revenue yet.");
        }else{
            txtvRevenue.setText(NumberFormat.getCurrencyInstance().format(revenue));
        }

        if(accountAdapter == null){
            SetupAdapater();
        }else{
            SetupAdapater();
        }
    }

    private Long fromDate, toDate;

    public void ChangeDate(View view){
        new MaterialAlertDialogBuilder(this)
                .setItems(new String[]{"Single", "Range"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
                            picker.addOnPositiveButtonClickListener(selection -> {
                                fromDate = selection;
                                toDate = selection;

                                SetupDate(fromDate,toDate);
                            });
                            picker.show(getSupportFragmentManager(),null);
                        }else if(i == 1){
                            MaterialDatePicker.Builder<Pair<Long,Long>> dateRangePickerBuilder = MaterialDatePicker.Builder.dateRangePicker();
                            MaterialDatePicker pickerBuilder = dateRangePickerBuilder.build();
                            pickerBuilder.addOnPositiveButtonClickListener(pair ->{
                                Pair<Long,Long> date = (Pair<Long,Long>)pair;

                                fromDate = date.first;
                                toDate = date.second;

                                SetupDate(fromDate,toDate);
                            });
                            pickerBuilder.show(getSupportFragmentManager(),null);
                        }
                    }
                })
                .create()
                .show();
    }

    private void SetupAdapater(){
        accountAdapter = new AccountAdapter(listOfContentValues);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(accountAdapter);
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