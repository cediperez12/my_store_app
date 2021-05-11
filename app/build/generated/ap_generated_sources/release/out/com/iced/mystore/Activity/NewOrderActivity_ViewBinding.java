// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewOrderActivity_ViewBinding implements Unbinder {
  private NewOrderActivity target;

  @UiThread
  public NewOrderActivity_ViewBinding(NewOrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewOrderActivity_ViewBinding(NewOrderActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.new_order_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.listLayout = Utils.findRequiredViewAsType(source, R.id.new_order_list_layout, "field 'listLayout'", NestedScrollView.class);
    target.emptyListLayout = Utils.findRequiredViewAsType(source, R.id.new_order_empty_order_list_layout, "field 'emptyListLayout'", LinearLayout.class);
    target.fabAddOrder = Utils.findRequiredViewAsType(source, R.id.new_order_fab_add_order, "field 'fabAddOrder'", FloatingActionButton.class);
    target.fabPayment = Utils.findRequiredViewAsType(source, R.id.new_order_fab_payment, "field 'fabPayment'", FloatingActionButton.class);
    target.recyclerViewOrderList = Utils.findRequiredViewAsType(source, R.id.new_order_recyclerview, "field 'recyclerViewOrderList'", RecyclerView.class);
    target.tilProduct = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_select_product_til, "field 'tilProduct'", TextInputLayout.class);
    target.txtvStocks = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_stocks_txtv, "field 'txtvStocks'", TextView.class);
    target.txtvCosts = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_cost_txtv, "field 'txtvCosts'", TextView.class);
    target.tilCount = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_count_til, "field 'tilCount'", TextInputLayout.class);
    target.txtvTotalCosts = Utils.findRequiredViewAsType(source, R.id.new_order_total_cost_txtv, "field 'txtvTotalCosts'", TextView.class);
    target.addOrderBtn = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_add_order_btn, "field 'addOrderBtn'", Button.class);
    target.closeSheetBtn = Utils.findRequiredViewAsType(source, R.id.new_order_sheet_close_btn, "field 'closeSheetBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewOrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.listLayout = null;
    target.emptyListLayout = null;
    target.fabAddOrder = null;
    target.fabPayment = null;
    target.recyclerViewOrderList = null;
    target.tilProduct = null;
    target.txtvStocks = null;
    target.txtvCosts = null;
    target.tilCount = null;
    target.txtvTotalCosts = null;
    target.addOrderBtn = null;
    target.closeSheetBtn = null;
  }
}
