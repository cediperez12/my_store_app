package com.iced.mystore.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iced.mystore.Constants.Account;
import com.iced.mystore.Constants.Accounting;
import com.iced.mystore.Constants.OrderProduct;
import com.iced.mystore.Constants.Product;
import com.iced.mystore.Constants.RestockProduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "my_store_db";
    public static final Integer DATABASE_VERSION = 6;

    public static final String PRODUCT_TABLE = "tbl_products";
    public static final String ACCOUNTING_TABLE = "tbl_accounting";
    public static final String ORDER_PRODUCT_TABLE = "tbl_order_product";
    public static final String RESTOCK_PRODUCT_TABLE = "tbl_restock_product";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PRODUCT_TABLE + "(" +
                Product.PRODUCT_ID + " INTEGER PRIMARY KEY NOT NULL," +
                Product.PRODUCT_NAME + " TEXT UNIQUE NOT NULL," +
                Product.PRODUCT_COST + " DOUBLE NOT NULL," +
                Product.PRODUCT_LAST_UPDATE + " LONG NOT NULL," +
                Product.PRODUCT_STOCKS + " INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + ACCOUNTING_TABLE + "(" +
                Accounting.ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                Accounting.ACC_ACT + " TEXT NOT NULL," +
                Accounting.ACC_COST + " DOUBLE NOT NULL," +
                Accounting.ACC_DATE + " LONG NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + ORDER_PRODUCT_TABLE + "(" +
                OrderProduct.ORDER_PROD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                OrderProduct.ACC_ID + " DOUBLE NOT NULL," +
                OrderProduct.PRODUCT_ID + " INTEGER NOT NULL," +
                OrderProduct.ORDER_COST + " DOUBLE NOT NULL," +
                OrderProduct.ORDER_COUNT + " INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + RESTOCK_PRODUCT_TABLE + "(" +
                RestockProduct.RESTOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                RestockProduct.PRODUCT_ID + " INTEGER NOT NULL," +
                RestockProduct.ACC_ID + " INTEGER NOT NULL," +
                RestockProduct.RESTOCK_COUNT + " INTEGER NOT NULL," +
                RestockProduct.RESTOCK_COST + " DOUBLE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNTING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ORDER_PRODUCT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESTOCK_PRODUCT_TABLE);
        onCreate(sqLiteDatabase);
    }

    public List<ContentValues> fetchAll(String tableName){
        List<ContentValues> result = new ArrayList<>();

        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM " + tableName,null);
        if(cur.moveToFirst()){
            do{
                ContentValues cv = new ContentValues();

                if(tableName.equals(PRODUCT_TABLE)){
                    cv.put(Product.PRODUCT_ID,cur.getInt(0));
                    cv.put(Product.PRODUCT_NAME,cur.getString(1));
                    cv.put(Product.PRODUCT_COST,cur.getDouble(2));
                    cv.put(Product.PRODUCT_LAST_UPDATE,cur.getLong(3));
                    cv.put(Product.PRODUCT_STOCKS,cur.getInt(4));
                }else if(tableName.equals(ACCOUNTING_TABLE)){
                    cv.put(Accounting.ACC_ID,cur.getInt(0));
                    cv.put(Accounting.ACC_ACT,cur.getString(1));
                    cv.put(Accounting.ACC_COST, cur.getDouble(2));
                    cv.put(Accounting.ACC_DATE, cur.getLong(3));
                }else if(tableName.equals(ORDER_PRODUCT_TABLE)){
                    cv.put(OrderProduct.ORDER_PROD_ID,cur.getInt(0));
                    cv.put(OrderProduct.ACC_ID, cur.getInt(1));
                    cv.put(OrderProduct.PRODUCT_ID, cur.getInt(2));
                    cv.put(OrderProduct.ORDER_COST, cur.getDouble(3));
                    cv.put(OrderProduct.ORDER_COUNT, cur.getDouble(4));
                }else if(tableName.equals(RESTOCK_PRODUCT_TABLE)){
                    cv.put(RestockProduct.RESTOCK_ID,cur.getInt(0));
                    cv.put(RestockProduct.PRODUCT_ID, cur.getInt(1));
                    cv.put(RestockProduct.ACC_ID, cur.getInt(2));
                    cv.put(RestockProduct.RESTOCK_COUNT, cur.getInt(3));
                    cv.put(RestockProduct.RESTOCK_COST,cur.getDouble(4));
                }

                result.add(cv);
            }while (cur.moveToNext());
        }

        return result;
    }

    public List<ContentValues> fetchAll(String tableName, @Nullable String nullable, String orderBy){
        List<ContentValues> result = new ArrayList<>();

        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM " + tableName + " " + orderBy,null);
        if(cur.moveToFirst()){
            do{
                ContentValues cv = new ContentValues();

                if(tableName.equals(PRODUCT_TABLE)){
                    cv.put(Product.PRODUCT_ID,cur.getInt(0));
                    cv.put(Product.PRODUCT_NAME,cur.getString(1));
                    cv.put(Product.PRODUCT_COST,cur.getDouble(2));
                    cv.put(Product.PRODUCT_LAST_UPDATE,cur.getLong(3));
                    cv.put(Product.PRODUCT_STOCKS,cur.getInt(4));
                }else if(tableName.equals(ACCOUNTING_TABLE)){
                    cv.put(Accounting.ACC_ID,cur.getInt(0));
                    cv.put(Accounting.ACC_ACT,cur.getString(1));
                    cv.put(Accounting.ACC_COST, cur.getDouble(2));
                    cv.put(Accounting.ACC_DATE, cur.getLong(3));
                }else if(tableName.equals(ORDER_PRODUCT_TABLE)){
                    cv.put(OrderProduct.ORDER_PROD_ID,cur.getInt(0));
                    cv.put(OrderProduct.ACC_ID, cur.getInt(1));
                    cv.put(OrderProduct.PRODUCT_ID, cur.getInt(2));
                    cv.put(OrderProduct.ORDER_COST, cur.getDouble(3));
                    cv.put(OrderProduct.ORDER_COUNT, cur.getDouble(4));
                }else if(tableName.equals(RESTOCK_PRODUCT_TABLE)){
                    cv.put(RestockProduct.RESTOCK_ID,cur.getInt(0));
                    cv.put(RestockProduct.PRODUCT_ID, cur.getInt(1));
                    cv.put(RestockProduct.ACC_ID, cur.getInt(2));
                    cv.put(RestockProduct.RESTOCK_COUNT, cur.getInt(3));
                    cv.put(RestockProduct.RESTOCK_COST,cur.getDouble(4));
                }

                result.add(cv);
            }while (cur.moveToNext());
        }

        return result;
    }

    public List<ContentValues> fetchAll(String tableName, String where){
        List<ContentValues> result = new ArrayList<>();

        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM " + tableName + " WHERE " + where,null);
        if(cur.moveToFirst()){
            do{
                ContentValues cv = new ContentValues();

                if(tableName.equals(PRODUCT_TABLE)){
                    cv.put(Product.PRODUCT_ID,cur.getInt(0));
                    cv.put(Product.PRODUCT_NAME,cur.getString(1));
                    cv.put(Product.PRODUCT_COST,cur.getDouble(2));
                    cv.put(Product.PRODUCT_LAST_UPDATE,cur.getLong(3));
                    cv.put(Product.PRODUCT_STOCKS,cur.getInt(4));
                }else if(tableName.equals(ACCOUNTING_TABLE)){
                    cv.put(Accounting.ACC_ID,cur.getInt(0));
                    cv.put(Accounting.ACC_ACT,cur.getString(1));
                    cv.put(Accounting.ACC_COST, cur.getDouble(2));
                    cv.put(Accounting.ACC_DATE, cur.getLong(3));
                }else if(tableName.equals(ORDER_PRODUCT_TABLE)){
                    cv.put(OrderProduct.ORDER_PROD_ID,cur.getInt(0));
                    cv.put(OrderProduct.ACC_ID, cur.getInt(1));
                    cv.put(OrderProduct.PRODUCT_ID, cur.getInt(2));
                    cv.put(OrderProduct.ORDER_COST, cur.getDouble(3));
                    cv.put(OrderProduct.ORDER_COUNT, cur.getDouble(4));
                }else if(tableName.equals(RESTOCK_PRODUCT_TABLE)){
                    cv.put(RestockProduct.RESTOCK_ID,cur.getInt(0));
                    cv.put(RestockProduct.PRODUCT_ID, cur.getInt(1));
                    cv.put(RestockProduct.ACC_ID, cur.getInt(2));
                    cv.put(RestockProduct.RESTOCK_COUNT, cur.getInt(3));
                    cv.put(RestockProduct.RESTOCK_COST,cur.getDouble(4));
                }

                result.add(cv);
            }while (cur.moveToNext());
        }

        return result;
    }

    public ContentValues fetch(String tableName, String where){
        ContentValues result = new ContentValues();

        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM " + tableName + " WHERE " + where,null);
        if(cur.moveToFirst()){
            ContentValues cv = new ContentValues();

            if(tableName.equals(PRODUCT_TABLE)){
                cv.put(Product.PRODUCT_ID,cur.getInt(0));
                cv.put(Product.PRODUCT_NAME,cur.getString(1));
                cv.put(Product.PRODUCT_COST,cur.getDouble(2));
                cv.put(Product.PRODUCT_LAST_UPDATE,cur.getLong(3));
                cv.put(Product.PRODUCT_STOCKS,cur.getInt(4));
            }else if(tableName.equals(ACCOUNTING_TABLE)){
                cv.put(Accounting.ACC_ID,cur.getInt(0));
                cv.put(Accounting.ACC_ACT,cur.getString(1));
                cv.put(Accounting.ACC_COST, cur.getDouble(2));
                cv.put(Accounting.ACC_DATE, cur.getLong(3));
            }else if(tableName.equals(ORDER_PRODUCT_TABLE)){
                cv.put(OrderProduct.ORDER_PROD_ID,cur.getInt(0));
                cv.put(OrderProduct.ACC_ID, cur.getInt(1));
                cv.put(OrderProduct.PRODUCT_ID, cur.getInt(2));
                cv.put(OrderProduct.ORDER_COST, cur.getDouble(3));
                cv.put(OrderProduct.ORDER_COUNT, cur.getDouble(4));
            }

            result = cv;
        }

        return result;
    }

    public Long insert(String tableName, ContentValues cv){
        return getWritableDatabase().insert(tableName,null,cv);
    }

    public Double getRevenue(){
        Double result = 0.0;

        Calendar fromDate = Calendar.getInstance();
        fromDate.set(Calendar.SECOND,0);
        fromDate.set(Calendar.MINUTE,0);
        fromDate.set(Calendar.HOUR_OF_DAY,0);

        Calendar toDate = Calendar.getInstance();
        toDate.set(Calendar.HOUR_OF_DAY,23);
        toDate.set(Calendar.MINUTE,59);
        toDate.set(Calendar.SECOND,59);

        Log.d("From",new SimpleDateFormat("MMMM dd, YYYY hh:mm:ss aa").format(fromDate.getTimeInMillis()));
        Log.d("To",new SimpleDateFormat("MMMM dd, YYYY hh:mm:ss aa").format(toDate.getTimeInMillis()));

        Cursor cur = getReadableDatabase().rawQuery("SELECT SUM(" + Accounting.ACC_COST + ") FROM " + Database.ACCOUNTING_TABLE + " WHERE " + Accounting.ACC_ACT + " = '" + Account.REVENUE + "' AND " + Accounting.ACC_DATE + " >= " + fromDate.getTimeInMillis() + " AND " + Accounting.ACC_DATE + " <= " + toDate.getTimeInMillis(),null);
        if(cur.moveToFirst()){
            result = cur.getDouble(0);
            Log.d("Cursor exists?", "Yes");
        }else{
            Log.d("Curosor exists?","No");
        }

        return result;
    }

    public Double getExpenses(){
        Double result = 0.0;

        Calendar fromDate = Calendar.getInstance();
        fromDate.set(Calendar.SECOND,0);
        fromDate.set(Calendar.MINUTE,0);
        fromDate.set(Calendar.HOUR_OF_DAY,0);

        Calendar toDate = Calendar.getInstance();
        toDate.set(Calendar.HOUR_OF_DAY,23);
        toDate.set(Calendar.MINUTE,59);
        toDate.set(Calendar.SECOND,59);

        Log.d("From",new SimpleDateFormat("MMMM dd, YYYY hh:mm:ss aa").format(fromDate.getTimeInMillis()));
        Log.d("To",new SimpleDateFormat("MMMM dd, YYYY hh:mm:ss aa").format(toDate.getTimeInMillis()));

        Cursor cur = getReadableDatabase().rawQuery("SELECT SUM(" + Accounting.ACC_COST + ") FROM " + Database.ACCOUNTING_TABLE + " WHERE " + Accounting.ACC_ACT + " = '" + Account.RESTOCK + "' AND " + Accounting.ACC_DATE + " >= " + fromDate.getTimeInMillis() + " AND " + Accounting.ACC_DATE + " <= " + toDate.getTimeInMillis(),null);
        if(cur.moveToFirst()){
            result = cur.getDouble(0);
            Log.d("Cursor exists?", "Yes");
        }else{
            Log.d("Curosor exists?","No");
        }

        return result;
    }

    public Double getRevenue(Long toDate, Long fromDate){
        String query = Accounting.ACC_DATE + " >= " + fromDate + " AND " + Accounting.ACC_DATE + " <= " + toDate;
        Cursor cur = getWritableDatabase().rawQuery("SELECT SUM(" + Accounting.ACC_COST + ") FROM " + ACCOUNTING_TABLE + " WHERE " + Accounting.ACC_ACT + " = '" + Account.REVENUE + "' AND " + query,null);

        Double result = -1.0;

        if(cur.moveToFirst()){
            result = cur.getDouble(0);
        }

        return result;
    }

    public Double getExpenses(Long toDate, Long fromDate){
        String query = Accounting.ACC_DATE + " >= " + fromDate + " AND " + Accounting.ACC_DATE + " <= " + toDate;
        Cursor cur = getWritableDatabase().rawQuery("SELECT SUM(" + Accounting.ACC_COST + ") FROM " + ACCOUNTING_TABLE + " WHERE " + Accounting.ACC_ACT + " = '" + Account.RESTOCK + "' AND " + query,null);

        Double result = -1.0;

        if(cur.moveToFirst()){
            result = cur.getDouble(0);
        }

        return result;
    }
}
